package com.heroku.plugin;

import android.util.Log;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Сетевой сервис для работы с AI провайдерами.
 * Поддерживает Google Gemini и OpenAI-совместимые API.
 */
public class NetworkService {
    private static final String TAG = "HerokuNetworkService";
    private static final int TIMEOUT_SECONDS = 60;
    
    // Gemini API endpoints
    private static final String GEMINI_BASE_URL = "https://generativelanguage.googleapis.com";
    private static final String GEMINI_MODELS_ENDPOINT = "/v1beta/models";
    private static final String GEMINI_CHAT_ENDPOINT = "/v1beta/models/%s:generateContent";
    
    private final OkHttpClient httpClient;
    private final SettingsManager settingsManager;
    
    public NetworkService(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }
    
    /**
     * Интерфейс для callbacks при загрузке моделей
     */
    public interface ModelsCallback {
        void onSuccess(List<String> models);
        void onError(String error);
    }
    
    /**
     * Интерфейс для callbacks при получении ответа от AI
     */
    public interface ChatCallback {
        void onResponse(String response);
        void onError(String error);
        void onStreamingChunk(String chunk); // Опционально для стриминга
    }
    
    // === Загрузка моделей Gemini ===
    
    public void loadGeminiModels(final ModelsCallback callback) {
        String apiKey = settingsManager.getGeminiApiKey();
        if (apiKey == null || apiKey.isEmpty()) {
            callback.onError("API ключ Gemini не установлен");
            return;
        }
        
        String url = GEMINI_BASE_URL + GEMINI_MODELS_ENDPOINT + "?key=" + apiKey;
        
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Ошибка загрузки моделей Gemini", e);
                callback.onError("Ошибка сети: " + e.getMessage());
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        if (code == 401 || code == 403) {
                            callback.onError("Неверный API ключ Gemini");
                        } else if (code == 429) {
                            callback.onError("Превышен лимит запросов");
                        } else {
                            callback.onError("Ошибка сервера: " + code);
                        }
                        return;
                    }
                    
                    String responseBody = response.body().string();
                    JSONObject json = new JSONObject(responseBody);
                    JSONArray modelsArray = json.optJSONArray("models");
                    
                    List<String> models = new ArrayList<>();
                    if (modelsArray != null) {
                        for (int i = 0; i < modelsArray.length(); i++) {
                            JSONObject model = modelsArray.getJSONObject(i);
                            String name = model.optString("name", "");
                            // Убираем префикс "models/"
                            if (name.startsWith("models/")) {
                                name = name.substring(7);
                            }
                            // Фильтруем только генеративные модели
                            if (!name.isEmpty() && name.contains("gemini")) {
                                models.add(name);
                            }
                        }
                    }
                    
                    Log.d(TAG, "Загружено моделей Gemini: " + models.size());
                    callback.onSuccess(models);
                    
                } catch (Exception e) {
                    Log.e(TAG, "Ошибка парсинга ответа Gemini", e);
                    callback.onError("Ошибка обработки ответа: " + e.getMessage());
                }
            }
        });
    }
    
    // === Загрузка моделей Custom Provider ===
    
    public void loadCustomProviderModels(final CustomProvider provider, final ModelsCallback callback) {
        if (provider == null || provider.getApiKey() == null || provider.getApiKey().isEmpty()) {
            callback.onError("API ключ не установлен");
            return;
        }
        
        String url = provider.getModelsUrl();
        
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + provider.getApiKey())
                .addHeader("Content-Type", "application/json")
                .get()
                .build();
        
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Ошибка загрузки моделей провайдера", e);
                callback.onError("Ошибка сети: " + e.getMessage());
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (!response.isSuccessful()) {
                        int code = response.code();
                        if (code == 401 || code == 403) {
                            callback.onError("Неверный API ключ");
                        } else if (code == 404) {
                            callback.onError("Endpoint не найден (404)");
                        } else if (code == 429) {
                            callback.onError("Превышен лимит запросов");
                        } else {
                            callback.onError("Ошибка сервера: " + code);
                        }
                        return;
                    }
                    
                    String responseBody = response.body().string();
                    JSONObject json = new JSONObject(responseBody);
                    JSONArray modelsArray = json.optJSONArray("data");
                    
                    List<String> models = new ArrayList<>();
                    if (modelsArray != null) {
                        for (int i = 0; i < modelsArray.length(); i++) {
                            JSONObject model = modelsArray.getJSONObject(i);
                            String id = model.optString("id", "");
                            if (!id.isEmpty()) {
                                models.add(id);
                            }
                        }
                    }
                    
                    Log.d(TAG, "Загружено моделей провайдера: " + models.size());
                    callback.onSuccess(models);
                    
                } catch (Exception e) {
                    Log.e(TAG, "Ошибка парсинга ответа провайдера", e);
                    callback.onError("Ошибка обработки ответа: " + e.getMessage());
                }
            }
        });
    }
    
    // === Тест соединения с Custom Provider ===
    
    public void testCustomProviderConnection(final CustomProvider provider, final ModelsCallback callback) {
        // Пробуем сделать простой запрос к endpoint
        String url = provider.getModelsUrl();
        
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + provider.getApiKey())
                .addHeader("Content-Type", "application/json")
                .get()
                .build();
        
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError("Ошибка подключения: " + e.getMessage());
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    callback.onSuccess(new ArrayList<>()); // Пустой список означает успех
                } else {
                    int code = response.code();
                    if (code == 401 || code == 403) {
                        callback.onError("Неверный API ключ");
                    } else if (code == 404) {
                        callback.onError("Endpoint не найден");
                    } else {
                        callback.onError("Ошибка сервера: " + code);
                    }
                }
            }
        });
    }
    
    // === Отправка сообщения в Gemini ===
    
    public void sendGeminiMessage(String message, String systemPrompt, final ChatCallback callback) {
        String apiKey = settingsManager.getGeminiApiKey();
        String model = settingsManager.getGeminiModel();
        
        if (apiKey == null || apiKey.isEmpty()) {
            callback.onError("API ключ Gemini не установлен");
            return;
        }
        
        String url = GEMINI_BASE_URL + String.format(GEMINI_CHAT_ENDPOINT, model) + "?key=" + apiKey;
        
        try {
            JSONObject jsonRequest = new JSONObject();
            JSONArray contents = new JSONArray();
            
            // Добавляем системный промпт как часть контекста
            if (systemPrompt != null && !systemPrompt.isEmpty()) {
                JSONObject systemMessage = new JSONObject();
                systemMessage.put("role", "user");
                JSONObject systemPart = new JSONObject();
                systemPart.put("text", systemPrompt + "\n\n---\n\n");
                systemMessage.put("parts", new JSONArray().put(systemPart));
                contents.put(systemMessage);
            }
            
            // Добавляем сообщение пользователя
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            JSONObject userPart = new JSONObject();
            userPart.put("text", message);
            userMessage.put("parts", new JSONArray().put(userPart));
            contents.put(userMessage);
            
            jsonRequest.put("contents", contents);
            
            // Параметры генерации
            JSONObject generationConfig = new JSONObject();
            generationConfig.put("temperature", settingsManager.getTemperature());
            jsonRequest.put("generationConfig", generationConfig);
            
            RequestBody body = RequestBody.create(
                    jsonRequest.toString(),
                    MediaType.parse("application/json")
            );
            
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Ошибка отправки сообщения Gemini", e);
                    callback.onError("Ошибка сети: " + e.getMessage());
                }
                
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (!response.isSuccessful()) {
                            int code = response.code();
                            if (code == 401 || code == 403) {
                                callback.onError("Неверный API ключ Gemini");
                            } else if (code == 429) {
                                callback.onError("Превышен лимит запросов");
                            } else {
                                callback.onError("Ошибка сервера: " + code);
                            }
                            return;
                        }
                        
                        String responseBody = response.body().string();
                        JSONObject json = new JSONObject(responseBody);
                        JSONArray candidates = json.optJSONArray("candidates");
                        
                        if (candidates != null && candidates.length() > 0) {
                            JSONObject candidate = candidates.getJSONObject(0);
                            JSONObject content = candidate.optJSONObject("content");
                            if (content != null) {
                                JSONArray parts = content.optJSONArray("parts");
                                if (parts != null && parts.length() > 0) {
                                    String text = parts.getJSONObject(0).optString("text", "");
                                    callback.onResponse(text);
                                    return;
                                }
                            }
                        }
                        
                        callback.onResponse("Пустой ответ от модели");
                        
                    } catch (Exception e) {
                        Log.e(TAG, "Ошибка парсинга ответа Gemini", e);
                        callback.onError("Ошибка обработки ответа: " + e.getMessage());
                    }
                }
            });
            
        } catch (Exception e) {
            Log.e(TAG, "Ошибка создания запроса Gemini", e);
            callback.onError("Ошибка формирования запроса: " + e.getMessage());
        }
    }
    
    // === Отправка сообщения в Custom Provider (OpenAI-compatible) ===
    
    public void sendCustomProviderMessage(CustomProvider provider, String message, 
                                          String systemPrompt, final ChatCallback callback) {
        if (provider == null || provider.getApiKey() == null || provider.getApiKey().isEmpty()) {
            callback.onError("API ключ не установлен");
            return;
        }
        
        String url = provider.getChatUrl();
        
        try {
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("model", provider.getModel());
            
            JSONArray messages = new JSONArray();
            
            // Системный промпт
            if (systemPrompt != null && !systemPrompt.isEmpty()) {
                JSONObject systemMessage = new JSONObject();
                systemMessage.put("role", "system");
                systemMessage.put("content", systemPrompt);
                messages.put(systemMessage);
            }
            
            // Сообщение пользователя
            JSONObject userMessage = new JSONObject();
            userMessage.put("role", "user");
            userMessage.put("content", message);
            messages.put(userMessage);
            
            jsonRequest.put("messages", messages);
            jsonRequest.put("temperature", (double) settingsManager.getTemperature());
            
            RequestBody body = RequestBody.create(
                    jsonRequest.toString(),
                    MediaType.parse("application/json")
            );
            
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", "Bearer " + provider.getApiKey())
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            
            httpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Ошибка отправки сообщения провайдеру", e);
                    callback.onError("Ошибка сети: " + e.getMessage());
                }
                
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        if (!response.isSuccessful()) {
                            int code = response.code();
                            if (code == 401 || code == 403) {
                                callback.onError("Неверный API ключ");
                            } else if (code == 404) {
                                callback.onError("Endpoint не найден");
                            } else if (code == 429) {
                                callback.onError("Превышен лимит запросов");
                            } else {
                                callback.onError("Ошибка сервера: " + code);
                            }
                            return;
                        }
                        
                        String responseBody = response.body().string();
                        JSONObject json = new JSONObject(responseBody);
                        JSONArray choices = json.optJSONArray("choices");
                        
                        if (choices != null && choices.length() > 0) {
                            JSONObject choice = choices.getJSONObject(0);
                            JSONObject msg = choice.optJSONObject("message");
                            if (msg != null) {
                                String text = msg.optString("content", "");
                                callback.onResponse(text);
                                return;
                            }
                        }
                        
                        callback.onResponse("Пустой ответ от модели");
                        
                    } catch (Exception e) {
                        Log.e(TAG, "Ошибка парсинга ответа провайдера", e);
                        callback.onError("Ошибка обработки ответа: " + e.getMessage());
                    }
                }
            });
            
        } catch (Exception e) {
            Log.e(TAG, "Ошибка создания запроса провайдеру", e);
            callback.onError("Ошибка формирования запроса: " + e.getMessage());
        }
    }
}

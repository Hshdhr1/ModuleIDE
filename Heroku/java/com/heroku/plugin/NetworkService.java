package com.heroku.plugin;

import android.util.Log;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Сетевой сервис для работы с AI провайдерами.
 * Поддерживает Gemini, OpenRouter, OnlySQ и другие OpenAI-совместимые API.
 * Реализует потоковые и обычные запросы.
 */
public class NetworkService {
    private static final String TAG = "HerokuNetwork";
    private static final int TIMEOUT_SECONDS = 60;
    
    private final OkHttpClient client;
    private final SettingsManager settingsManager;
    
    // Media types
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    
    public NetworkService(SettingsManager settingsManager) {
        this.settingsManager = settingsManager;
        this.client = new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build();
    }
    
    /**
     * Интерфейс для получения результатов запроса.
     */
    public interface ApiCallback {
        void onSuccess(String response);
        void onError(String error);
        void onStreamingChunk(String chunk);
    }
    
    // === Gemini API ===
    
    /**
     * Загружает список доступных моделей Gemini.
     */
    public void loadGeminiModels(final ApiCallback callback) {
        String apiKey = settingsManager.getGeminiApiKey();
        if (apiKey == null || apiKey.isEmpty()) {
            callback.onError("Не указан API ключ Gemini");
            return;
        }
        
        String url = "https://generativelanguage.googleapis.com/v1/models?key=" + apiKey;
        
        Request request = new Request.Builder()
            .url(url)
            .get()
            .build();
        
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Ошибка загрузки моделей Gemini", e);
                callback.onError("Ошибка сети: " + e.getMessage());
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody body = response.body()) {
                    if (!response.isSuccessful()) {
                        callback.onError("Ошибка API: " + response.code());
                        return;
                    }
                    
                    String json = body.string();
                    JSONObject root = new JSONObject(json);
                    JSONArray modelsArray = root.optJSONArray("models");
                    
                    if (modelsArray != null) {
                        StringBuilder result = new StringBuilder();
                        for (int i = 0; i < modelsArray.length(); i++) {
                            JSONObject model = modelsArray.getJSONObject(i);
                            String name = model.optString("name", "");
                            // Извлекаем только имя модели без префикса
                            if (name.startsWith("models/")) {
                                name = name.substring(7);
                            }
                            if (!name.isEmpty()) {
                                result.append(name).append("\n");
                            }
                        }
                        callback.onSuccess(result.toString().trim());
                    } else {
                        callback.onSuccess("");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Ошибка парсинга моделей Gemini", e);
                    callback.onError("Ошибка обработки ответа: " + e.getMessage());
                }
            }
        });
    }
    
    /**
     * Отправляет сообщение в Gemini API (обычный запрос).
     */
    public void sendGeminiMessage(String message, String systemPrompt, float temperature, final ApiCallback callback) {
        String apiKey = settingsManager.getGeminiApiKey();
        String model = settingsManager.getGeminiModel();
        
        if (apiKey == null || apiKey.isEmpty()) {
            callback.onError("Не указан API ключ Gemini");
            return;
        }
        
        try {
            JSONObject payload = new JSONObject();
            JSONArray contents = new JSONArray();
            
            // Системный промпт как первый элемент
            if (systemPrompt != null && !systemPrompt.isEmpty()) {
                JSONObject systemMsg = new JSONObject();
                systemMsg.put("role", "user");
                JSONArray parts = new JSONArray();
                parts.put(systemPrompt);
                systemMsg.put("parts", parts);
                contents.put(systemMsg);
                
                JSONObject systemResponse = new JSONObject();
                systemResponse.put("role", "model");
                JSONArray responseParts = new JSONArray();
                responseParts.put("Понял. Буду следовать этим инструкциям.");
                systemResponse.put("parts", responseParts);
                contents.put(systemResponse);
            }
            
            // Сообщение пользователя
            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            JSONArray parts = new JSONArray();
            parts.put(message);
            userMsg.put("parts", parts);
            contents.put(userMsg);
            
            payload.put("contents", contents);
            
            JSONObject generationConfig = new JSONObject();
            generationConfig.put("temperature", temperature);
            payload.put("generationConfig", generationConfig);
            
            String url = "https://generativelanguage.googleapis.com/v1/models/" + model + ":generateContent?key=" + apiKey;
            
            RequestBody body = RequestBody.create(payload.toString(), JSON);
            
            Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
            
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Ошибка отправки в Gemini", e);
                    callback.onError("Ошибка сети: " + e.getMessage());
                }
                
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody body = response.body()) {
                        if (!response.isSuccessful()) {
                            String errorBody = body != null ? body.string() : "";
                            if (response.code() == 401) {
                                callback.onError("Неверный API ключ Gemini");
                            } else if (response.code() == 429) {
                                callback.onError("Превышен лимит запросов");
                            } else {
                                callback.onError("Ошибка API: " + response.code() + " " + errorBody);
                            }
                            return;
                        }
                        
                        String json = body.string();
                        JSONObject root = new JSONObject(json);
                        JSONArray candidates = root.optJSONArray("candidates");
                        
                        if (candidates != null && candidates.length() > 0) {
                            JSONObject candidate = candidates.getJSONObject(0);
                            JSONObject content = candidate.optJSONObject("content");
                            if (content != null) {
                                JSONArray parts = content.optJSONArray("parts");
                                if (parts != null && parts.length() > 0) {
                                    String text = parts.getString(0);
                                    callback.onSuccess(text);
                                    return;
                                }
                            }
                        }
                        callback.onSuccess("Пустой ответ от API");
                    } catch (Exception e) {
                        Log.e(TAG, "Ошибка парсинга ответа Gemini", e);
                        callback.onError("Ошибка обработки ответа: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Ошибка создания запроса Gemini", e);
            callback.onError("Ошибка: " + e.getMessage());
        }
    }
    
    /**
     * Отправляет сообщение в Gemini API с потоковым ответом.
     */
    public void sendGeminiMessageStream(String message, String systemPrompt, float temperature, final ApiCallback callback) {
        String apiKey = settingsManager.getGeminiApiKey();
        String model = settingsManager.getGeminiModel();
        
        if (apiKey == null || apiKey.isEmpty()) {
            callback.onError("Не указан API ключ Gemini");
            return;
        }
        
        try {
            JSONObject payload = new JSONObject();
            JSONArray contents = new JSONArray();
            
            if (systemPrompt != null && !systemPrompt.isEmpty()) {
                JSONObject systemMsg = new JSONObject();
                systemMsg.put("role", "user");
                JSONArray parts = new JSONArray();
                parts.put(systemPrompt);
                systemMsg.put("parts", parts);
                contents.put(systemMsg);
                
                JSONObject systemResponse = new JSONObject();
                systemResponse.put("role", "model");
                JSONArray responseParts = new JSONArray();
                responseParts.put("Понял. Буду следовать этим инструкциям.");
                systemResponse.put("parts", responseParts);
                contents.put(systemResponse);
            }
            
            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            JSONArray parts = new JSONArray();
            parts.put(message);
            userMsg.put("parts", parts);
            contents.put(userMsg);
            
            payload.put("contents", contents);
            
            JSONObject generationConfig = new JSONObject();
            generationConfig.put("temperature", temperature);
            payload.put("generationConfig", generationConfig);
            
            String url = "https://generativelanguage.googleapis.com/v1beta/models/" + model + ":streamGenerateContent?alt=sse&key=" + apiKey;
            
            RequestBody body = RequestBody.create(payload.toString(), JSON);
            
            Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
            
            EventSource.Factory factory = EventSources.createFactory(client);
            
            factory.newEventSource(request, new EventSourceListener() {
                @Override
                public void onOpen(EventSource eventSource, Response response) {
                    Log.d(TAG, "SSE соединение открыто");
                }
                
                @Override
                public void onEvent(EventSource eventSource, String id, String type, String data) {
                    try {
                        JSONObject json = new JSONObject(data);
                        JSONArray candidates = json.optJSONArray("candidates");
                        if (candidates != null && candidates.length() > 0) {
                            JSONObject candidate = candidates.getJSONObject(0);
                            JSONObject content = candidate.optJSONObject("content");
                            if (content != null) {
                                JSONArray partsArr = content.optJSONArray("parts");
                                if (partsArr != null && partsArr.length() > 0) {
                                    String chunk = partsArr.getString(0);
                                    callback.onStreamingChunk(chunk);
                                }
                            }
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Ошибка парсинга SSE chunk", e);
                    }
                }
                
                @Override
                public void onFailure(EventSource eventSource, Throwable t, Response response) {
                    Log.e(TAG, "SSE ошибка", t);
                    if (t != null) {
                        callback.onError("Ошибка потока: " + t.getMessage());
                    }
                }
                
                @Override
                public void onClosed(EventSource eventSource) {
                    Log.d(TAG, "SSE соединение закрыто");
                    callback.onSuccess(""); // Пустой успех означает завершение потока
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Ошибка создания stream запроса Gemini", e);
            callback.onError("Ошибка: " + e.getMessage());
        }
    }
    
    // === Custom Provider (OpenAI-compatible) ===
    
    /**
     * Загружает список моделей от custom провайдера.
     */
    public void loadCustomProviderModels(CustomProvider provider, final ApiCallback callback) {
        if (provider == null || !provider.isValid()) {
            callback.onError("Некорректные данные провайдера");
            return;
        }
        
        String url = provider.getEndpoint().replaceAll("/+$", "") + "/models";
        
        Request request = new Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer " + provider.getApiKey())
            .addHeader("Content-Type", "application/json")
            .get()
            .build();
        
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Ошибка загрузки моделей провайдера", e);
                callback.onError("Ошибка сети: " + e.getMessage());
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody body = response.body()) {
                    if (!response.isSuccessful()) {
                        if (response.code() == 401) {
                            callback.onError("Неверный API ключ");
                        } else if (response.code() == 404) {
                            callback.onError("Endpoint не поддерживает /models");
                        } else {
                            callback.onError("Ошибка API: " + response.code());
                        }
                        return;
                    }
                    
                    String json = body.string();
                    JSONObject root = new JSONObject(json);
                    JSONArray modelsArray = root.optJSONArray("data");
                    
                    if (modelsArray != null) {
                        StringBuilder result = new StringBuilder();
                        for (int i = 0; i < modelsArray.length(); i++) {
                            JSONObject model = modelsArray.getJSONObject(i);
                            String id = model.optString("id", "");
                            if (!id.isEmpty()) {
                                result.append(id).append("\n");
                            }
                        }
                        callback.onSuccess(result.toString().trim());
                    } else {
                        callback.onSuccess("");
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Ошибка парсинга моделей провайдера", e);
                    callback.onError("Ошибка обработки ответа: " + e.getMessage());
                }
            }
        });
    }
    
    /**
     * Тестирует соединение с custom провайдером.
     */
    public void testCustomProviderConnection(CustomProvider provider, final ApiCallback callback) {
        if (provider == null || !provider.isValid()) {
            callback.onError("Некорректные данные провайдера");
            return;
        }
        
        // Простой запрос к /models для проверки
        loadCustomProviderModels(provider, new ApiCallback() {
            @Override
            public void onSuccess(String response) {
                callback.onSuccess("Соединение успешно! " + (response.isEmpty() ? "Модели не возвращены." : "Моделей найдено."));
            }
            
            @Override
            public void onError(String error) {
                callback.onError(error);
            }
            
            @Override
            public void onStreamingChunk(String chunk) {}
        });
    }
    
    /**
     * Отправляет сообщение в custom провайдер (OpenAI-compatible формат).
     */
    public void sendCustomProviderMessage(CustomProvider provider, String message, String systemPrompt, 
                                          float temperature, boolean stream, final ApiCallback callback) {
        if (provider == null || !provider.isValid()) {
            callback.onError("Некорректные данные провайдера");
            return;
        }
        
        try {
            JSONObject payload = new JSONObject();
            JSONArray messages = new JSONArray();
            
            // Системный промпт
            if (systemPrompt != null && !systemPrompt.isEmpty()) {
                JSONObject systemMsg = new JSONObject();
                systemMsg.put("role", "system");
                systemMsg.put("content", systemPrompt);
                messages.put(systemMsg);
            }
            
            // Сообщение пользователя
            JSONObject userMsg = new JSONObject();
            userMsg.put("role", "user");
            userMsg.put("content", message);
            messages.put(userMsg);
            
            payload.put("model", provider.getModel());
            payload.put("messages", messages);
            payload.put("temperature", temperature);
            payload.put("stream", stream);
            
            String endpoint = provider.getEndpoint().replaceAll("/+$", "");
            String url = endpoint + "/chat/completions";
            
            RequestBody body = RequestBody.create(payload.toString(), JSON);
            
            Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + provider.getApiKey())
                .addHeader("Content-Type", "application/json");
            
            // Для OpenRouter добавляем дополнительные заголовки
            if ("openrouter".equals(provider.getId())) {
                requestBuilder.addHeader("HTTP-Referer", "https://github.com/heroku-plugin");
                requestBuilder.addHeader("X-Title", "Heroku Plugin");
            }
            
            // Для OnlySQ добавляем заголовок Authorization в формате API 2.0
            if ("onlysq".equals(provider.getId())) {
                // OnlySQ требует заголовок Authorization: Bearer apikey
                // Уже добавлен выше, но можно добавить дополнительные параметры
                // Поддержка режима рассуждений через extra_body
            }
            
            // Добавляем поддержку reasoning для OnlySQ
            JSONObject extraBody = new JSONObject();
            if ("onlysq".equals(provider.getId())) {
                // Режим рассуждений: minimal, low, medium, high
                String reasoningLevel = provider.getReasoningLevel();
                if (reasoningLevel == null || reasoningLevel.isEmpty()) {
                    reasoningLevel = "medium";
                }
                extraBody.put("reasoning", reasoningLevel);
            }
            
            if (extraBody.length() > 0) {
                payload.put("extra_body", extraBody);
            }
            
            Request request = requestBuilder.build();
            
            if (stream) {
                // Потоковый запрос
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "Ошибка stream запроса к провайдеру", e);
                        callback.onError("Ошибка сети: " + e.getMessage());
                    }
                    
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody body = response.body()) {
                            if (!response.isSuccessful()) {
                                String errorBody = body != null ? body.string() : "";
                                if (response.code() == 401) {
                                    callback.onError("Неверный API ключ");
                                } else if (response.code() == 429) {
                                    callback.onError("Превышен лимит запросов");
                                } else {
                                    callback.onError("Ошибка API: " + response.code() + " " + errorBody);
                                }
                                return;
                            }
                            
                            // Читаем SSE поток
                            String line;
                            java.io.BufferedReader reader = new java.io.BufferedReader(
                                new java.io.InputStreamReader(body.byteStream()));
                            
                            while ((line = reader.readLine()) != null) {
                                if (line.startsWith("data: ")) {
                                    String data = line.substring(6);
                                    if ("[DONE]".equals(data)) {
                                        break;
                                    }
                                    try {
                                        JSONObject json = new JSONObject(data);
                                        JSONArray choices = json.optJSONArray("choices");
                                        if (choices != null && choices.length() > 0) {
                                            JSONObject delta = choices.getJSONObject(0).optJSONObject("delta");
                                            if (delta != null) {
                                                String content = delta.optString("content", "");
                                                if (!content.isEmpty()) {
                                                    callback.onStreamingChunk(content);
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        Log.w(TAG, "Ошибка парсинга chunk", e);
                                    }
                                }
                            }
                            callback.onSuccess(""); // Завершение потока
                        } catch (Exception e) {
                            Log.e(TAG, "Ошибка обработки stream ответа", e);
                            callback.onError("Ошибка обработки ответа: " + e.getMessage());
                        }
                    }
                });
            } else {
                // Обычный запрос
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e(TAG, "Ошибка запроса к провайдеру", e);
                        callback.onError("Ошибка сети: " + e.getMessage());
                    }
                    
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try (ResponseBody body = response.body()) {
                            if (!response.isSuccessful()) {
                                String errorBody = body != null ? body.string() : "";
                                if (response.code() == 401) {
                                    callback.onError("Неверный API ключ");
                                } else if (response.code() == 429) {
                                    callback.onError("Превышен лимит запросов");
                                } else {
                                    callback.onError("Ошибка API: " + response.code() + " " + errorBody);
                                }
                                return;
                            }
                            
                            String json = body.string();
                            JSONObject root = new JSONObject(json);
                            JSONArray choices = root.optJSONArray("choices");
                            
                            if (choices != null && choices.length() > 0) {
                                JSONObject choice = choices.getJSONObject(0);
                                JSONObject msg = choice.optJSONObject("message");
                                if (msg != null) {
                                    String content = msg.optString("content", "");
                                    callback.onSuccess(content);
                                    return;
                                }
                            }
                            callback.onSuccess("Пустой ответ от API");
                        } catch (Exception e) {
                            Log.e(TAG, "Ошибка парсинга ответа провайдера", e);
                            callback.onError("Ошибка обработки ответа: " + e.getMessage());
                        }
                    }
                });
            }
        } catch (Exception e) {
            Log.e(TAG, "Ошибка создания запроса к провайдеру", e);
            callback.onError("Ошибка: " + e.getMessage());
        }
    }
}

package com.heroku.plugin;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Менеджер настроек плагина Heroku.
 * Управляет системными промптами, API ключами, провайдерами и другими настройками.
 * Использует SharedPreferences для безопасного хранения данных.
 */
public class SettingsManager {
    private static final String TAG = "HerokuSettings";
    private static final String PREFS_NAME = "heroku_plugin_prefs";
    
    // Ключи для SharedPreferences
    private static final String KEY_SYSTEM_PROMPT = "system_prompt";
    private static final String KEY_GEMINI_API_KEY = "gemini_api_key";
    private static final String KEY_GEMINI_MODEL = "gemini_model";
    private static final String KEY_ACTIVE_PROVIDER = "active_provider";
    private static final String KEY_TEMPERATURE = "temperature";
    private static final String KEY_CUSTOM_PROVIDERS = "custom_providers";
    private static final String KEY_STREAMING_ENABLED = "streaming_enabled";
    private static final String KEY_SYNTAX_HIGHLIGHT = "syntax_highlight";
    
    // Пресеты системных промптов на русском языке
    public static final String PROMPT_HELPFUL_ASSISTANT = 
        "Вы полезный, доброжелательный и компетентный ассистент. Отвечайте точно, понятно и дружелюбно. " +
        "Если не знаете ответа — честно признайтесь. Помогайте пользователю решать задачи эффективно.";
    
    public static final String PROMPT_EXPERT_PROGRAMMER = 
        "Вы эксперт-программист с глубокими знаниями в различных языках программирования, фреймворках и технологиях. " +
        "Давайте точные, оптимизированные решения с объяснениями. Предпочитайте чистый, поддерживаемый код. " +
        "Указывайте на потенциальные проблемы безопасности и производительности.";
    
    public static final String PROMPT_CREATIVE_WRITER = 
        "Вы креативный писатель с богатым воображением. Создавайте яркие, образные тексты. " +
        "Используйте метафоры, эпитеты, играйте со словами. Помогайте пользователю развивать творческие идеи.";
    
    public static final String PROMPT_PSYCHOLOGIST = 
        "Вы эмпатичный психолог-консультант. Выслушивайте внимательно, задавайте уточняющие вопросы. " +
        "Поддерживайте пользователя, помогайте разобраться в чувствах и мыслях. Не ставьте диагнозы.";
    
    public static final String PROMPT_TEACHER = 
        "Вы опытный учитель, который объясняет сложные вещи простым языком. " +
        "Разбивайте материал на шаги, приводите примеры, проверяйте понимание. " +
        "Поощряйте любознательность и самостоятельное мышление.";
    
    public static final String PROMPT_SARCASTIC = 
        "Вы саркастичный помощник с острым умом и язвительным юмором. " +
        "Отвечайте с иронией, но всё же помогайте решить задачу. " +
        "Не переходите границы, сохраняйте дружеский тон.";
    
    private final SharedPreferences prefs;
    private final Context context;
    
    public SettingsManager(Context context) {
        this.context = context.getApplicationContext();
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    
    // === Системный промпт ===
    
    public String getSystemPrompt() {
        return prefs.getString(KEY_SYSTEM_PROMPT, PROMPT_HELPFUL_ASSISTANT);
    }
    
    public void setSystemPrompt(String prompt) {
        prefs.edit().putString(KEY_SYSTEM_PROMPT, prompt).apply();
    }
    
    // === Gemini настройки ===
    
    public String getGeminiApiKey() {
        return prefs.getString(KEY_GEMINI_API_KEY, "");
    }
    
    public void setGeminiApiKey(String key) {
        prefs.edit().putString(KEY_GEMINI_API_KEY, key).apply();
    }
    
    public String getGeminiModel() {
        return prefs.getString(KEY_GEMINI_MODEL, "gemini-pro");
    }
    
    public void setGeminiModel(String model) {
        prefs.edit().putString(KEY_GEMINI_MODEL, model).apply();
    }
    
    // === Температура ===
    
    public float getTemperature() {
        return prefs.getFloat(KEY_TEMPERATURE, 0.7f);
    }
    
    public void setTemperature(float temp) {
        prefs.edit().putFloat(KEY_TEMPERATURE, temp).apply();
    }
    
    // === Активный провайдер ===
    
    public String getActiveProvider() {
        return prefs.getString(KEY_ACTIVE_PROVIDER, "gemini");
    }
    
    public void setActiveProvider(String providerId) {
        prefs.edit().putString(KEY_ACTIVE_PROVIDER, providerId).apply();
    }
    
    // === Потоковый режим и подсветка ===
    
    public boolean isStreamingEnabled() {
        return prefs.getBoolean(KEY_STREAMING_ENABLED, true);
    }
    
    public void setStreamingEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_STREAMING_ENABLED, enabled).apply();
    }
    
    public boolean isSyntaxHighlightEnabled() {
        return prefs.getBoolean(KEY_SYNTAX_HIGHLIGHT, true);
    }
    
    public void setSyntaxHighlightEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_SYNTAX_HIGHLIGHT, enabled).apply();
    }
    
    // === Пользовательские провайдеры ===
    
    /**
     * Сохраняет список пользовательских провайдеров в JSON формате.
     */
    public void saveCustomProviders(List<CustomProvider> providers) {
        try {
            JSONArray array = new JSONArray();
            for (CustomProvider provider : providers) {
                array.put(provider.toJson());
            }
            prefs.edit().putString(KEY_CUSTOM_PROVIDERS, array.toString()).apply();
            Log.d(TAG, "Сохранено провайдеров: " + providers.size());
        } catch (JSONException e) {
            Log.e(TAG, "Ошибка сохранения провайдеров", e);
        }
    }
    
    /**
     * Загружает список пользовательских провайдеров из JSON.
     */
    public List<CustomProvider> loadCustomProviders() {
        List<CustomProvider> providers = new ArrayList<>();
        String json = prefs.getString(KEY_CUSTOM_PROVIDERS, null);
        if (json == null) {
            // Добавляем OpenRouter по умолчанию
            providers.add(createOpenRouterPreset());
            saveCustomProviders(providers);
            return providers;
        }
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                providers.add(CustomProvider.fromJson(obj));
            }
            Log.d(TAG, "Загружено провайдеров: " + providers.size());
        } catch (JSONException e) {
            Log.e(TAG, "Ошибка загрузки провайдеров", e);
        }
        return providers;
    }
    
    /**
     * Создаёт пресет OpenRouter.
     */
    public CustomProvider createOpenRouterPreset() {
        CustomProvider openrouter = new CustomProvider();
        openrouter.setId("openrouter");
        openrouter.setName("OpenRouter");
        openrouter.setEndpoint("https://openrouter.ai/api/v1");
        openrouter.setApiKey("");
        openrouter.setModel("");
        openrouter.setSupportsStreaming(true);
        openrouter.setDescription("Доступ к множеству моделей через единый API");
        return openrouter;
    }
    
    /**
     * Добавляет нового провайдера в список.
     */
    public void addCustomProvider(CustomProvider provider) {
        List<CustomProvider> providers = loadCustomProviders();
        if (provider.getId() == null || provider.getId().isEmpty()) {
            provider.setId("provider_" + System.currentTimeMillis());
        }
        providers.add(provider);
        saveCustomProviders(providers);
    }
    
    /**
     * Обновляет существующего провайдера.
     */
    public void updateCustomProvider(CustomProvider updatedProvider) {
        List<CustomProvider> providers = loadCustomProviders();
        for (int i = 0; i < providers.size(); i++) {
            if (providers.get(i).getId().equals(updatedProvider.getId())) {
                providers.set(i, updatedProvider);
                break;
            }
        }
        saveCustomProviders(providers);
    }
    
    /**
     * Удаляет провайдера по ID.
     */
    public void deleteCustomProvider(String providerId) {
        List<CustomProvider> providers = loadCustomProviders();
        for (int i = 0; i < providers.size(); i++) {
            if (providers.get(i).getId().equals(providerId)) {
                providers.remove(i);
                break;
            }
        }
        saveCustomProviders(providers);
    }
    
    /**
     * Получает провайдера по ID.
     */
    public CustomProvider getCustomProvider(String providerId) {
        List<CustomProvider> providers = loadCustomProviders();
        for (CustomProvider provider : providers) {
            if (provider.getId().equals(providerId)) {
                return provider;
            }
        }
        return null;
    }
    
    /**
     * Сбрасывает все настройки к значениям по умолчанию.
     */
    public void resetToDefaults() {
        prefs.edit().clear().apply();
        // Восстанавливаем пресет OpenRouter
        List<CustomProvider> providers = new ArrayList<>();
        providers.add(createOpenRouterPreset());
        saveCustomProviders(providers);
    }
}

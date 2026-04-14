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
 * Управляет всеми настройками через SharedPreferences.
 */
public class SettingsManager {
    private static final String PREFS_NAME = "heroku_settings";
    private static final String TAG = "HerokuSettingsManager";
    
    // Ключи для системного промпта
    private static final String KEY_SYSTEM_PROMPT = "system_prompt";
    private static final String DEFAULT_SYSTEM_PROMPT = "Вы полезный, умный и дружелюбный ассистент. Отвечайте на русском языке, если не указано иное.";
    
    // Ключи для Gemini
    private static final String KEY_GEMINI_API_KEY = "gemini_api_key";
    private static final String KEY_GEMINI_MODEL = "gemini_model";
    private static final String DEFAULT_GEMINI_MODEL = "gemini-1.5-flash";
    
    // Ключи для активного провайдера
    private static final String KEY_ACTIVE_PROVIDER = "active_provider";
    private static final String PROVIDER_TYPE_GEMINI = "gemini";
    private static final String PROVIDER_TYPE_CUSTOM = "custom";
    
    // Настройки чата
    private static final String KEY_TEMPERATURE = "temperature";
    private static final float DEFAULT_TEMPERATURE = 0.7f;
    
    private final SharedPreferences prefs;
    private final Context context;
    
    public SettingsManager(Context context) {
        this.context = context.getApplicationContext();
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }
    
    // === Системный промпт ===
    
    public String getSystemPrompt() {
        return prefs.getString(KEY_SYSTEM_PROMPT, DEFAULT_SYSTEM_PROMPT);
    }
    
    public void setSystemPrompt(String prompt) {
        prefs.edit().putString(KEY_SYSTEM_PROMPT, prompt).apply();
        Log.d(TAG, "Системный промпт обновлён");
    }
    
    // === Пресеты системных промптов ===
    
    public static String getPresetHelpfulAssistant() {
        return "Вы полезный, умный и дружелюбный ассистент. Отвечайте на русском языке, если не указано иное. Всегда старайтесь помочь пользователю решить его задачу максимально эффективно.";
    }
    
    public static String getPresetExpertProgrammer() {
        return "Вы опытный программист-эксперт со знанием множества языков программирования. Отвечайте на русском языке. Предоставляйте подробные объяснения кода, лучшие практики и оптимизации. Всегда пишите чистый, поддерживаемый код с комментариями.";
    }
    
    public static String getPresetCreativeWriter() {
        return "Вы креативный писатель с богатым воображением. Отвечайте на русском языке. Создавайте увлекательные истории, стихи, сценарии и другой творческий контент. Используйте образный язык и литературные приёмы.";
    }
    
    public static String getPresetPsychologist() {
        return "Вы эмпатичный психолог-консультант. Отвечайте на русском языке. Слушайте внимательно, проявляйте понимание и поддержку. Задавайте уточняющие вопросы и помогайте пользователю разобраться в своих чувствах и мыслях. Не ставьте диагнозы.";
    }
    
    public static String getPresetTeacher() {
        return "Вы опытный учитель-педагог. Отвечайте на русском языке. Объясняйте сложные концепции простым, понятным языком. Используйте аналогии и примеры. Поощряйте обучение и задавайте проверочные вопросы.";
    }
    
    public static String getPresetSarcastic() {
        return "Вы саркастичный, остроумный помощник с чувством юмора. Отвечайте на русском языке. Добавляйте лёгкую иронию и сарказм в свои ответы, но оставайтесь полезными. Не переходите границы вежливости.";
    }
    
    public void applyPreset(String preset) {
        prefs.edit().putString(KEY_SYSTEM_PROMPT, preset).apply();
    }
    
    // === Настройки Gemini ===
    
    public String getGeminiApiKey() {
        return prefs.getString(KEY_GEMINI_API_KEY, "");
    }
    
    public void setGeminiApiKey(String key) {
        prefs.edit().putString(KEY_GEMINI_API_KEY, key).apply();
    }
    
    public String getGeminiModel() {
        return prefs.getString(KEY_GEMINI_MODEL, DEFAULT_GEMINI_MODEL);
    }
    
    public void setGeminiModel(String model) {
        prefs.edit().putString(KEY_GEMINI_MODEL, model).apply();
    }
    
    // === Активный провайдер ===
    
    public String getActiveProviderType() {
        return prefs.getString(KEY_ACTIVE_PROVIDER, PROVIDER_TYPE_GEMINI);
    }
    
    public void setActiveProviderType(String type) {
        prefs.edit().putString(KEY_ACTIVE_PROVIDER, type).apply();
    }
    
    public boolean isGeminiActive() {
        return PROVIDER_TYPE_GEMINI.equals(getActiveProviderType());
    }
    
    // === Температура ===
    
    public float getTemperature() {
        return prefs.getFloat(KEY_TEMPERATURE, DEFAULT_TEMPERATURE);
    }
    
    public void setTemperature(float temp) {
        prefs.edit().putFloat(KEY_TEMPERATURE, temp).apply();
    }
    
    // === Пользовательские провайдеры (JSON хранение) ===
    
    private static final String KEY_CUSTOM_PROVIDERS = "custom_providers";
    
    public List<CustomProvider> getCustomProviders() {
        List<CustomProvider> providers = new ArrayList<>();
        String json = prefs.getString(KEY_CUSTOM_PROVIDERS, "[]");
        try {
            JSONArray array = new JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                CustomProvider provider = new CustomProvider(
                    obj.getString("id"),
                    obj.getString("name"),
                    obj.getString("endpoint"),
                    obj.getString("apiKey"),
                    obj.optString("model", "")
                );
                providers.add(provider);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Ошибка чтения пользовательских провайдеров", e);
        }
        return providers;
    }
    
    public void saveCustomProviders(List<CustomProvider> providers) {
        try {
            JSONArray array = new JSONArray();
            for (CustomProvider provider : providers) {
                JSONObject obj = new JSONObject();
                obj.put("id", provider.getId());
                obj.put("name", provider.getName());
                obj.put("endpoint", provider.getEndpoint());
                obj.put("apiKey", provider.getApiKey());
                obj.put("model", provider.getModel());
                array.put(obj);
            }
            prefs.edit().putString(KEY_CUSTOM_PROVIDERS, array.toString()).apply();
            Log.d(TAG, "Пользовательские провайдеры сохранены: " + providers.size());
        } catch (JSONException e) {
            Log.e(TAG, "Ошибка сохранения пользовательских провайдеров", e);
        }
    }
    
    public void addCustomProvider(CustomProvider provider) {
        List<CustomProvider> providers = getCustomProviders();
        providers.add(provider);
        saveCustomProviders(providers);
    }
    
    public void updateCustomProvider(CustomProvider provider) {
        List<CustomProvider> providers = getCustomProviders();
        for (int i = 0; i < providers.size(); i++) {
            if (providers.get(i).getId().equals(provider.getId())) {
                providers.set(i, provider);
                break;
            }
        }
        saveCustomProviders(providers);
    }
    
    public void deleteCustomProvider(String id) {
        List<CustomProvider> providers = getCustomProviders();
        for (int i = 0; i < providers.size(); i++) {
            if (providers.get(i).getId().equals(id)) {
                providers.remove(i);
                break;
            }
        }
        saveCustomProviders(providers);
    }
    
    public CustomProvider getCustomProviderById(String id) {
        List<CustomProvider> providers = getCustomProviders();
        for (CustomProvider provider : providers) {
            if (provider.getId().equals(id)) {
                return provider;
            }
        }
        return null;
    }
}

package com.heroku.plugin;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Модель пользовательского провайдера (OpenAI-совместимого).
 * Содержит все необходимые параметры для подключения к API.
 */
public class CustomProvider {
    private String id;
    private String name;
    private String endpoint;
    private String apiKey;
    private String model;
    private boolean supportsStreaming;
    private String description;
    
    public CustomProvider() {
        this.supportsStreaming = true;
    }
    
    // Геттеры и сеттеры
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEndpoint() {
        return endpoint;
    }
    
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    public String getApiKey() {
        return apiKey;
    }
    
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public String getModel() {
        return model;
    }
    
    public void setModel(String model) {
        this.model = model;
    }
    
    public boolean isSupportsStreaming() {
        return supportsStreaming;
    }
    
    public void setSupportsStreaming(boolean supportsStreaming) {
        this.supportsStreaming = supportsStreaming;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    /**
     * Преобразует провайдер в JSONObject для сохранения.
     */
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("endpoint", endpoint);
        json.put("apiKey", apiKey);
        json.put("model", model);
        json.put("supportsStreaming", supportsStreaming);
        json.put("description", description != null ? description : "");
        return json;
    }
    
    /**
     * Создаёт провайдер из JSONObject.
     */
    public static CustomProvider fromJson(JSONObject json) throws JSONException {
        CustomProvider provider = new CustomProvider();
        provider.setId(json.optString("id", ""));
        provider.setName(json.optString("name", ""));
        provider.setEndpoint(json.optString("endpoint", ""));
        provider.setApiKey(json.optString("apiKey", ""));
        provider.setModel(json.optString("model", ""));
        provider.setSupportsStreaming(json.optBoolean("supportsStreaming", true));
        provider.setDescription(json.optString("description", ""));
        return provider;
    }
    
    /**
     * Проверяет, заполнены ли все обязательные поля.
     */
    public boolean isValid() {
        return name != null && !name.isEmpty() &&
               endpoint != null && !endpoint.isEmpty() &&
               apiKey != null && !apiKey.isEmpty();
    }
    
    @Override
    public String toString() {
        return name + " (" + endpoint + ")";
    }
}

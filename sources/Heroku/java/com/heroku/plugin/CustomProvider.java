package com.heroku.plugin;

import java.util.UUID;

/**
 * Модель пользовательского провайдера API.
 * Представляет собой OpenAI-совместимый endpoint.
 */
public class CustomProvider {
    private final String id;
    private String name;
    private String endpoint;
    private String apiKey;
    private String model;
    
    public CustomProvider(String name, String endpoint, String apiKey, String model) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.endpoint = endpoint;
        this.apiKey = apiKey;
        this.model = model;
    }
    
    // Конструктор для восстановления из JSON
    public CustomProvider(String id, String name, String endpoint, String apiKey, String model) {
        this.id = id;
        this.name = name;
        this.endpoint = endpoint;
        this.apiKey = apiKey;
        this.model = model;
    }
    
    public String getId() {
        return id;
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
    
    /**
     * Получение полного URL для чата
     */
    public String getChatUrl() {
        String base = endpoint.endsWith("/") ? endpoint : endpoint + "/";
        return base + "chat/completions";
    }
    
    /**
     * Получение URL для списка моделей
     */
    public String getModelsUrl() {
        String base = endpoint.endsWith("/") ? endpoint : endpoint + "/";
        return base + "models";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CustomProvider that = (CustomProvider) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return "CustomProvider{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", endpoint='" + endpoint + '\'' +
                ", model='" + model + '\'' +
                '}';
    }
}

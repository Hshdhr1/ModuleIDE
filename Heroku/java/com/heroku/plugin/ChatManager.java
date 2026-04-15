package com.heroku.plugin;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Менеджер истории чата.
 * Сохраняет и загружает историю сообщений, поддерживает экспорт.
 */
public class ChatManager {
    private static final String TAG = "HerokuChat";
    private static final String CHAT_HISTORY_FILE = "heroku_chat_history.json";
    
    private final Context context;
    private final List<ChatMessage> messages;
    
    /**
     * Типы сообщений в чате.
     */
    public enum MessageType {
        USER,
        AI,
        SYSTEM
    }
    
    /**
     * Модель сообщения чата.
     */
    public static class ChatMessage {
        public MessageType type;
        public String content;
        public long timestamp;
        public String providerId;
        
        public ChatMessage(MessageType type, String content) {
            this.type = type;
            this.content = content;
            this.timestamp = System.currentTimeMillis();
        }
        
        public ChatMessage(MessageType type, String content, String providerId) {
            this(type, content);
            this.providerId = providerId;
        }
        
        public JSONObject toJson() throws JSONException {
            JSONObject json = new JSONObject();
            json.put("type", type.name());
            json.put("content", content);
            json.put("timestamp", timestamp);
            if (providerId != null) {
                json.put("providerId", providerId);
            }
            return json;
        }
        
        public static ChatMessage fromJson(JSONObject json) throws JSONException {
            ChatMessage msg = new ChatMessage(
                MessageType.valueOf(json.optString("type", "USER")),
                json.optString("content", "")
            );
            msg.timestamp = json.optLong("timestamp", System.currentTimeMillis());
            msg.providerId = json.optString("providerId", null);
            return msg;
        }
        
        public String getFormattedTime() {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return sdf.format(new Date(timestamp));
        }
    }
    
    public ChatManager(Context context) {
        this.context = context.getApplicationContext();
        this.messages = new ArrayList<>();
        loadHistory();
    }
    
    /**
     * Добавляет сообщение в историю.
     */
    public void addMessage(ChatMessage message) {
        messages.add(message);
        saveHistory();
    }
    
    /**
     * Добавляет пользовательское сообщение.
     */
    public void addUserMessage(String content) {
        addMessage(new ChatMessage(MessageType.USER, content));
    }
    
    /**
     * Добавляет AI сообщение.
     */
    public void addAiMessage(String content, String providerId) {
        addMessage(new ChatMessage(MessageType.AI, content, providerId));
    }
    
    /**
     * Добавляет системное сообщение.
     */
    public void addSystemMessage(String content) {
        addMessage(new ChatMessage(MessageType.SYSTEM, content));
    }
    
    /**
     * Получает все сообщения.
     */
    public List<ChatMessage> getMessages() {
        return new ArrayList<>(messages);
    }
    
    /**
     * Очищает всю историю.
     */
    public void clearHistory() {
        messages.clear();
        saveHistory();
    }
    
    /**
     * Получает последние N сообщений для контекста.
     */
    public List<ChatMessage> getRecentMessages(int count) {
        int size = messages.size();
        if (size <= count) {
            return new ArrayList<>(messages);
        }
        return messages.subList(size - count, size);
    }
    
    /**
     * Сохраняет историю в файл.
     */
    private void saveHistory() {
        try {
            JSONArray array = new JSONArray();
            for (ChatMessage msg : messages) {
                array.put(msg.toJson());
            }
            
            File file = new File(context.getFilesDir(), CHAT_HISTORY_FILE);
            FileWriter writer = new FileWriter(file);
            writer.write(array.toString());
            writer.close();
            
            Log.d(TAG, "История сохранена: " + messages.size() + " сообщений");
        } catch (Exception e) {
            Log.e(TAG, "Ошибка сохранения истории", e);
        }
    }
    
    /**
     * Загружает историю из файла.
     */
    private void loadHistory() {
        try {
            File file = new File(context.getFilesDir(), CHAT_HISTORY_FILE);
            if (!file.exists()) {
                Log.d(TAG, "Файл истории не найден");
                return;
            }
            
            StringBuilder sb = new StringBuilder();
            java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            
            JSONArray array = new JSONArray(sb.toString());
            messages.clear();
            
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                messages.add(ChatMessage.fromJson(obj));
            }
            
            Log.d(TAG, "История загружена: " + messages.size() + " сообщений");
        } catch (Exception e) {
            Log.e(TAG, "Ошибка загрузки истории", e);
            messages.clear();
        }
    }
    
    /**
     * Экспортирует историю в текстовый файл.
     */
    public String exportHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== История чата Heroku ===\n");
        sb.append("Дата экспорта: ").append(
            new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
                .format(new Date())).append("\n\n");
        
        for (ChatMessage msg : messages) {
            String prefix;
            switch (msg.type) {
                case USER:
                    prefix = "👤 Вы";
                    break;
                case AI:
                    prefix = "🤖 Heroku";
                    break;
                case SYSTEM:
                    prefix = "⚙️ Система";
                    break;
                default:
                    prefix = "?";
            }
            
            sb.append("[").append(msg.getFormattedTime()).append("] ")
              .append(prefix).append(":\n")
              .append(msg.content).append("\n\n");
        }
        
        return sb.toString();
    }
    
    /**
     * Сохраняет экспорт в файл.
     */
    public File saveExportToFile() {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());
            String filename = "heroku_export_" + timestamp + ".txt";
            
            File file = new File(context.getFilesDir(), filename);
            FileWriter writer = new FileWriter(file);
            writer.write(exportHistory());
            writer.close();
            
            Log.d(TAG, "Экспорт сохранён: " + file.getAbsolutePath());
            return file;
        } catch (IOException e) {
            Log.e(TAG, "Ошибка сохранения экспорта", e);
            return null;
        }
    }
    
    /**
     * Форматирует историю для отправки в AI как контекст.
     */
    public String formatContextForAI(int maxMessages) {
        List<ChatMessage> recent = getRecentMessages(maxMessages);
        StringBuilder sb = new StringBuilder();
        
        for (ChatMessage msg : recent) {
            if (msg.type == MessageType.USER) {
                sb.append("User: ").append(msg.content).append("\n");
            } else if (msg.type == MessageType.AI) {
                sb.append("Assistant: ").append(msg.content).append("\n");
            }
        }
        
        return sb.toString();
    }
}

package com.heroku.plugin;

import android.content.Context;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Менеджер истории чата.
 * Сохраняет и загружает историю переписки локально.
 */
public class ChatManager {
    private static final String TAG = "HerokuChatManager";
    private static final String CHAT_HISTORY_FILE = "heroku_chat_history.json";
    private static final int MAX_HISTORY_SIZE = 100;
    
    private final Context context;
    private final List<ChatMessage> messageHistory;
    
    public ChatManager(Context context) {
        this.context = context.getApplicationContext();
        this.messageHistory = new ArrayList<>();
        loadHistory();
    }
    
    /**
     * Модель сообщения чата
     */
    public static class ChatMessage {
        public enum Role {
            USER,
            ASSISTANT,
            SYSTEM
        }
        
        private final String id;
        private final Role role;
        private final String content;
        private final long timestamp;
        
        public ChatMessage(Role role, String content) {
            this.id = java.util.UUID.randomUUID().toString();
            this.role = role;
            this.content = content;
            this.timestamp = System.currentTimeMillis();
        }
        
        // Для восстановления из JSON
        public ChatMessage(String id, String roleStr, String content, long timestamp) {
            this.id = id;
            this.role = Role.valueOf(roleStr);
            this.content = content;
            this.timestamp = timestamp;
        }
        
        public String getId() { return id; }
        public Role getRole() { return role; }
        public String getContent() { return content; }
        public long getTimestamp() { return timestamp; }
        
        public String getFormattedTime() {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            return sdf.format(new Date(timestamp));
        }
        
        public JSONObject toJson() throws org.json.JSONException {
            JSONObject obj = new JSONObject();
            obj.put("id", id);
            obj.put("role", role.name());
            obj.put("content", content);
            obj.put("timestamp", timestamp);
            return obj;
        }
        
        public static ChatMessage fromJson(JSONObject obj) throws org.json.JSONException {
            return new ChatMessage(
                obj.getString("id"),
                obj.getString("role"),
                obj.getString("content"),
                obj.getLong("timestamp")
            );
        }
    }
    
    /**
     * Загрузка истории из файла
     */
    public void loadHistory() {
        File file = new File(context.getFilesDir(), CHAT_HISTORY_FILE);
        if (!file.exists()) {
            Log.d(TAG, "Файл истории не найден, создаём новую историю");
            return;
        }
        
        try (FileReader reader = new FileReader(file)) {
            StringBuilder sb = new StringBuilder();
            char[] buffer = new char[1024];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                sb.append(buffer, 0, read);
            }
            
            String json = sb.toString();
            JSONArray array = new JSONArray(json);
            
            messageHistory.clear();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                ChatMessage msg = ChatMessage.fromJson(obj);
                messageHistory.add(msg);
            }
            
            Log.d(TAG, "История загружена: " + messageHistory.size() + " сообщений");
            
        } catch (Exception e) {
            Log.e(TAG, "Ошибка загрузки истории", e);
            messageHistory.clear();
        }
    }
    
    /**
     * Сохранение истории в файл
     */
    public void saveHistory() {
        try {
            JSONArray array = new JSONArray();
            for (ChatMessage msg : messageHistory) {
                array.put(msg.toJson());
            }
            
            File file = new File(context.getFilesDir(), CHAT_HISTORY_FILE);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(array.toString());
            }
            
            Log.d(TAG, "История сохранена: " + messageHistory.size() + " сообщений");
            
        } catch (Exception e) {
            Log.e(TAG, "Ошибка сохранения истории", e);
        }
    }
    
    /**
     * Добавление сообщения в историю
     */
    public void addMessage(ChatMessage.Role role, String content) {
        ChatMessage msg = new ChatMessage(role, content);
        messageHistory.add(msg);
        
        // Ограничиваем размер истории
        while (messageHistory.size() > MAX_HISTORY_SIZE) {
            messageHistory.remove(0);
        }
        
        saveHistory();
    }
    
    /**
     * Получение всей истории
     */
    public List<ChatMessage> getHistory() {
        return new ArrayList<>(messageHistory);
    }
    
    /**
     * Очистка истории
     */
    public void clearHistory() {
        messageHistory.clear();
        saveHistory();
        Log.d(TAG, "История очищена");
    }
    
    /**
     * Экспорт истории в текстовый файл
     */
    public File exportHistory() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.getDefault());
            String filename = "heroku_export_" + sdf.format(new Date()) + ".txt";
            File file = new File(context.getFilesDir(), filename);
            
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("=== История чата Heroku ===\n");
                writer.write("Дата экспорта: " + sdf.format(new Date()) + "\n\n");
                
                for (ChatMessage msg : messageHistory) {
                    String roleStr = msg.getRole() == ChatMessage.Role.USER ? "Вы" : "AI";
                    writer.write(String.format("[%s] %s:\n%s\n\n", 
                        msg.getFormattedTime(), roleStr, msg.getContent()));
                }
            }
            
            Log.d(TAG, "История экспортирована: " + file.getAbsolutePath());
            return file;
            
        } catch (IOException e) {
            Log.e(TAG, "Ошибка экспорта истории", e);
            return null;
        }
    }
    
    /**
     * Получение последних N сообщений для контекста
     */
    public List<ChatMessage> getRecentMessages(int count) {
        List<ChatMessage> recent = new ArrayList<>();
        int start = Math.max(0, messageHistory.size() - count);
        for (int i = start; i < messageHistory.size(); i++) {
            recent.add(messageHistory.get(i));
        }
        return recent;
    }
}

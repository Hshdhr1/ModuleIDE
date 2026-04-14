package com.heroku.plugin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.Slider;
import java.util.List;

/**
 * Основная активность чата с ИИ.
 * Красивый современный интерфейс для общения с AI.
 */
public class HerokuMainActivity extends AppCompatActivity {
    
    private static final String TAG = "HerokuMainActivity";
    
    // UI компоненты
    private MaterialToolbar toolbar;
    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private ImageButton settingsButton;
    private FloatingActionButton scrollToBottomFab;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout emptyStateLayout;
    private TextView emptyStateText;
    
    // Менеджеры
    private SettingsManager settingsManager;
    private NetworkService networkService;
    private ChatManager chatManager;
    private ChatMessageAdapter messageAdapter;
    
    // Состояние
    private boolean isLoading = false;
    private Handler mainHandler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroku_main);
        
        mainHandler = new Handler(Looper.getMainLooper());
        
        // Инициализация менеджеров
        settingsManager = new SettingsManager(this);
        networkService = new NetworkService(settingsManager);
        chatManager = new ChatManager(this);
        
        // Инициализация UI
        initViews();
        setupToolbar();
        setupRecyclerView();
        setupListeners();
        
        // Загрузка истории
        loadChatHistory();
    }
    
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        settingsButton = findViewById(R.id.settingsButton);
        scrollToBottomFab = findViewById(R.id.scrollToBottomFab);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        emptyStateLayout = findViewById(R.id.emptyStateLayout);
        emptyStateText = findViewById(R.id.emptyStateText);
    }
    
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Heroku");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, HerokuSettingsActivity.class);
            startActivity(intent);
        });
    }
    
    private void setupRecyclerView() {
        messageAdapter = new ChatMessageAdapter(chatManager.getHistory());
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(messageAdapter);
        
        // Автопрокрутка вниз при новых сообщениях
        messageAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                chatRecyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
            }
        });
    }
    
    private void setupListeners() {
        // Отправка сообщения
        sendButton.setOnClickListener(v -> sendMessage());
        
        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            sendMessage();
            return true;
        });
        
        // Кнопка прокрутки вниз
        scrollToBottomFab.setOnClickListener(v -> 
            chatRecyclerView.scrollToPosition(messageAdapter.getItemCount() - 1)
        );
        
        // Обновление по свайпу
        swipeRefresh.setOnRefreshListener(() -> {
            chatManager.loadHistory();
            messageAdapter.updateMessages(chatManager.getHistory());
            swipeRefresh.setRefreshing(false);
        });
    }
    
    private void sendMessage() {
        String text = messageInput.getText().toString().trim();
        
        if (TextUtils.isEmpty(text)) {
            showToast("Введите сообщение");
            return;
        }
        
        if (isLoading) {
            showToast("Подождите завершения предыдущего запроса");
            return;
        }
        
        // Проверка наличия активного провайдера
        if (!checkProviderConfigured()) {
            return;
        }
        
        // Добавляем сообщение пользователя
        chatManager.addMessage(ChatManager.ChatMessage.Role.USER, text);
        messageAdapter.updateMessages(chatManager.getHistory());
        messageInput.setText("");
        
        // Отправляем запрос к AI
        sendToAI(text);
    }
    
    private boolean checkProviderConfigured() {
        if (settingsManager.isGeminiActive()) {
            if (TextUtils.isEmpty(settingsManager.getGeminiApiKey())) {
                showProviderError("Не настроен API ключ Gemini. Откройте настройки.");
                return false;
            }
        } else {
            List<CustomProvider> providers = settingsManager.getCustomProviders();
            if (providers.isEmpty()) {
                showProviderError("Не добавлено пользовательских провайдеров. Откройте настройки.");
                return false;
            }
        }
        return true;
    }
    
    private void showProviderError(String message) {
        new MaterialAlertDialogBuilder(this)
            .setTitle("Требуется настройка")
            .setMessage(message)
            .setPositiveButton("Настройки", (d, which) -> {
                Intent intent = new Intent(this, HerokuSettingsActivity.class);
                startActivity(intent);
            })
            .setNegativeButton("Отмена", null)
            .show();
    }
    
    private void sendToAI(String message) {
        isLoading = true;
        updateLoadingState();
        
        String systemPrompt = settingsManager.getSystemPrompt();
        
        if (settingsManager.isGeminiActive()) {
            // Отправка в Gemini
            networkService.sendGeminiMessage(message, systemPrompt, new NetworkService.ChatCallback() {
                @Override
                public void onResponse(String response) {
                    mainHandler.post(() -> {
                        chatManager.addMessage(ChatManager.ChatMessage.Role.ASSISTANT, response);
                        messageAdapter.updateMessages(chatManager.getHistory());
                        isLoading = false;
                        updateLoadingState();
                    });
                }
                
                @Override
                public void onError(String error) {
                    mainHandler.post(() -> {
                        showToast(error);
                        isLoading = false;
                        updateLoadingState();
                    });
                }
                
                @Override
                public void onStreamingChunk(String chunk) {
                    // Пока не используется
                }
            });
        } else {
            // Отправка в Custom Provider (берём первый из списка как активный)
            // В будущем можно добавить выбор конкретного провайдера
            List<CustomProvider> providers = settingsManager.getCustomProviders();
            if (!providers.isEmpty()) {
                CustomProvider provider = providers.get(0);
                networkService.sendCustomProviderMessage(provider, message, systemPrompt, 
                    new NetworkService.ChatCallback() {
                        @Override
                        public void onResponse(String response) {
                            mainHandler.post(() -> {
                                chatManager.addMessage(ChatManager.ChatMessage.Role.ASSISTANT, response);
                                messageAdapter.updateMessages(chatManager.getHistory());
                                isLoading = false;
                                updateLoadingState();
                            });
                        }
                        
                        @Override
                        public void onError(String error) {
                            mainHandler.post(() -> {
                                showToast(error);
                                isLoading = false;
                                updateLoadingState();
                            });
                        }
                        
                        @Override
                        public void onStreamingChunk(String chunk) {
                            // Пока не используется
                        }
                    }
                );
            }
        }
    }
    
    private void updateLoadingState() {
        sendButton.setEnabled(!isLoading);
        messageInput.setEnabled(!isLoading);
        
        if (isLoading) {
            // Показываем индикатор загрузки
            if (messageAdapter.getItemCount() == 0 || 
                messageAdapter.getLastMessageRole() != ChatManager.ChatMessage.Role.USER) {
                // Добавляем временное сообщение "печатает..."
                // Можно реализовать в будущем
            }
        }
    }
    
    private void loadChatHistory() {
        List<ChatManager.ChatMessage> history = chatManager.getHistory();
        if (history.isEmpty()) {
            emptyStateLayout.setVisibility(View.VISIBLE);
            chatRecyclerView.setVisibility(View.GONE);
            scrollToBottomFab.hide();
        } else {
            emptyStateLayout.setVisibility(View.GONE);
            chatRecyclerView.setVisibility(View.VISIBLE);
            scrollToBottomFab.show();
            messageAdapter.updateMessages(history);
        }
    }
    
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Показать диалог очистки истории
     */
    public void showClearHistoryDialog() {
        new MaterialAlertDialogBuilder(this)
            .setTitle("Очистить историю")
            .setMessage("Вы уверены, что хотите очистить всю историю чата? Это действие нельзя отменить.")
            .setPositiveButton("Да", (d, which) -> {
                chatManager.clearHistory();
                messageAdapter.updateMessages(chatManager.getHistory());
                loadChatHistory();
                showToast("История очищена");
            })
            .setNegativeButton("Отмена", null)
            .show();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Перезагружаем историю на случай изменений
        chatManager.loadHistory();
        messageAdapter.updateMessages(chatManager.getHistory());
        loadChatHistory();
    }
}

/**
 * Адаптер для отображения сообщений в RecyclerView
 */
class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.MessageViewHolder> {
    
    private static final int VIEW_TYPE_USER = 1;
    private static final int VIEW_TYPE_AI = 2;
    
    private List<ChatManager.ChatMessage> messages;
    
    public ChatMessageAdapter(List<ChatManager.ChatMessage> messages) {
        this.messages = messages;
    }
    
    public void updateMessages(List<ChatManager.ChatMessage> newMessages) {
        this.messages = newMessages;
        notifyDataSetChanged();
    }
    
    public ChatManager.ChatMessage.Role getLastMessageRole() {
        if (messages.isEmpty()) return null;
        return messages.get(messages.size() - 1).getRole();
    }
    
    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_USER) {
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_user, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_ai, parent, false);
        }
        return new MessageViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        ChatManager.ChatMessage msg = messages.get(position);
        holder.bind(msg);
    }
    
    @Override
    public int getItemCount() {
        return messages.size();
    }
    
    @Override
    public int getItemViewType(int position) {
        ChatManager.ChatMessage msg = messages.get(position);
        return msg.getRole() == ChatManager.ChatMessage.Role.USER ? VIEW_TYPE_USER : VIEW_TYPE_AI;
    }
    
    static class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView messageText;
        private TextView timeText;
        
        MessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.messageText);
            timeText = itemView.findViewById(R.id.timeText);
        }
        
        void bind(ChatManager.ChatMessage msg) {
            messageText.setText(msg.getContent());
            timeText.setText(msg.getFormattedTime());
        }
    }
}

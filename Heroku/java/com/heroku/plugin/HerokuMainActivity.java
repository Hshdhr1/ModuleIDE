package com.heroku.plugin;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Основная активность чата с ИИ.
 * Красивый современный интерфейс с Material Design 3,
 * потоковыми ответами, подсветкой синтаксиса и анимациями.
 */
public class HerokuMainActivity extends AppCompatActivity {
    
    private SettingsManager settingsManager;
    private NetworkService networkService;
    private ChatManager chatManager;
    
    // UI компоненты
    private RecyclerView chatRecyclerView;
    private EditText messageInput;
    private ImageButton sendButton;
    private ImageButton stopButton;
    private MaterialToolbar toolbar;
    private SwipeRefreshLayout swipeRefresh;
    private View emptyStateView;
    private ProgressBar loadingProgress;
    
    private ChatAdapter chatAdapter;
    private Handler mainHandler;
    
    private boolean isGenerating = false;
    private StringBuilder currentResponse;
    private int currentResponsePosition = -1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroku_main);
        
        mainHandler = new Handler(Looper.getMainLooper());
        settingsManager = new SettingsManager(this);
        networkService = new NetworkService(settingsManager);
        chatManager = new ChatManager(this);
        
        initViews();
        setupToolbar();
        setupRecyclerView();
        setupInput();
        loadChatHistory();
    }
    
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);
        stopButton = findViewById(R.id.stopButton);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        emptyStateView = findViewById(R.id.emptyStateView);
        loadingProgress = findViewById(R.id.loadingProgress);
        
        // Кнопка настроек в toolbar
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.menu_settings) {
                startActivity(new Intent(this, HerokuSettingsActivity.class));
                return true;
            }
            return false;
        });
    }
    
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Heroku AI");
        }
    }
    
    private void setupRecyclerView() {
        chatAdapter = new ChatAdapter();
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(chatAdapter);
        
        // Анимация появления сообщений
        chatRecyclerView.setItemAnimator(new androidx.recyclerview.widget.DefaultItemAnimator());
        
        swipeRefresh.setOnRefreshListener(() -> {
            chatManager.clearHistory();
            chatAdapter.updateMessages(chatManager.getMessages());
            swipeRefresh.setRefreshing(false);
            updateEmptyState();
            Snackbar.make(chatRecyclerView, "История очищена", Snackbar.LENGTH_SHORT).show();
        });
    }
    
    private void setupInput() {
        sendButton.setOnClickListener(v -> sendMessage());
        
        stopButton.setOnClickListener(v -> stopGeneration());
        stopButton.setVisibility(View.GONE);
        
        messageInput.setOnEditorActionListener((v, actionId, event) -> {
            sendMessage();
            return true;
        });
    }
    
    private void loadChatHistory() {
        List<ChatManager.ChatMessage> messages = chatManager.getMessages();
        chatAdapter.updateMessages(messages);
        updateEmptyState();
        
        if (!messages.isEmpty()) {
            scrollToBottom();
        }
    }
    
    private void updateEmptyState() {
        boolean isEmpty = chatAdapter.getItemCount() == 0;
        emptyStateView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        chatRecyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
        
        if (isEmpty) {
            // Анимация появления empty state
            emptyStateView.setAlpha(0f);
            emptyStateView.animate().alpha(1f).setDuration(500).start();
        }
    }
    
    private void sendMessage() {
        String text = messageInput.getText().toString().trim();
        if (text.isEmpty()) {
            Snackbar.make(messageInput, "Введите сообщение", Snackbar.LENGTH_SHORT).show();
            return;
        }
        
        if (isGenerating) {
            Snackbar.make(messageInput, "Дождитесь завершения ответа", Snackbar.LENGTH_SHORT).show();
            return;
        }
        
        // Добавляем сообщение пользователя
        chatManager.addUserMessage(text);
        chatAdapter.addMessage(new ChatManager.ChatMessage(ChatManager.MessageType.USER, text));
        messageInput.setText("");
        scrollToBottom();
        updateEmptyState();
        
        // Отправляем запрос
        sendToAI(text);
    }
    
    private void sendToAI(String message) {
        String activeProvider = settingsManager.getActiveProvider();
        String systemPrompt = settingsManager.getSystemPrompt();
        float temperature = settingsManager.getTemperature();
        boolean streaming = settingsManager.isStreamingEnabled();
        
        showLoading(true);
        currentResponse = new StringBuilder();
        
        if ("gemini".equals(activeProvider)) {
            // Gemini API
            String apiKey = settingsManager.getGeminiApiKey();
            if (apiKey.isEmpty()) {
                showError("Не указан API ключ Gemini в настройках");
                return;
            }
            
            if (streaming) {
                networkService.sendGeminiMessageStream(message, systemPrompt, temperature, 
                    new NetworkService.ApiCallback() {
                        @Override
                        public void onSuccess(String response) {
                            finishGeneration();
                        }
                        
                        @Override
                        public void onError(String error) {
                            mainHandler.post(() -> {
                                showLoading(false);
                                showError(error);
                            });
                        }
                        
                        @Override
                        public void onStreamingChunk(String chunk) {
                            mainHandler.post(() -> appendChunk(chunk));
                        }
                    });
            } else {
                networkService.sendGeminiMessage(message, systemPrompt, temperature,
                    new NetworkService.ApiCallback() {
                        @Override
                        public void onSuccess(String response) {
                            mainHandler.post(() -> {
                                showLoading(false);
                                chatManager.addAiMessage(response, "gemini");
                                chatAdapter.addMessage(new ChatManager.ChatMessage(ChatManager.MessageType.AI, response, "gemini"));
                                scrollToBottom();
                            });
                        }
                        
                        @Override
                        public void onError(String error) {
                            mainHandler.post(() -> {
                                showLoading(false);
                                showError(error);
                            });
                        }
                        
                        @Override
                        public void onStreamingChunk(String chunk) {}
                    });
            }
        } else {
            // Custom provider
            CustomProvider provider = settingsManager.getCustomProvider(activeProvider);
            if (provider == null) {
                showError("Провайдер не найден");
                return;
            }
            
            if (provider.getApiKey().isEmpty()) {
                showError("Не указан API ключ для " + provider.getName());
                return;
            }
            
            networkService.sendCustomProviderMessage(provider, message, systemPrompt, temperature, streaming,
                new NetworkService.ApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        finishGeneration();
                    }
                    
                    @Override
                    public void onError(String error) {
                        mainHandler.post(() -> {
                            showLoading(false);
                            showError(error);
                        });
                    }
                    
                    @Override
                    public void onStreamingChunk(String chunk) {
                        mainHandler.post(() -> appendChunk(chunk));
                    }
                });
        }
    }
    
    private void appendChunk(String chunk) {
        if (currentResponsePosition < 0) {
            // Создаём новое сообщение
            currentResponse.append(chunk);
            ChatManager.ChatMessage msg = new ChatManager.ChatMessage(
                ChatManager.MessageType.AI, 
                currentResponse.toString(),
                settingsManager.getActiveProvider()
            );
            chatAdapter.addMessage(msg);
            currentResponsePosition = chatAdapter.getItemCount() - 1;
        } else {
            // Обновляем существующее
            currentResponse.append(chunk);
            chatAdapter.updateMessage(currentResponsePosition, currentResponse.toString());
        }
        scrollToBottom();
    }
    
    private void finishGeneration() {
        mainHandler.post(() -> {
            showLoading(false);
            if (currentResponse.length() > 0) {
                chatManager.addAiMessage(currentResponse.toString(), settingsManager.getActiveProvider());
            }
            currentResponse = new StringBuilder();
            currentResponsePosition = -1;
        });
    }
    
    private void stopGeneration() {
        isGenerating = false;
        showLoading(false);
        stopButton.setVisibility(View.GONE);
        sendButton.setVisibility(View.VISIBLE);
    }
    
    private void showLoading(boolean show) {
        loadingProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        sendButton.setVisibility(show ? View.GONE : View.VISIBLE);
        stopButton.setVisibility(show ? View.VISIBLE : View.GONE);
        messageInput.setEnabled(!show);
        isGenerating = show;
        
        if (show) {
            // Анимация загрузки
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator rotation = ObjectAnimator.ofFloat(loadingProgress, "rotation", 0f, 360f);
            rotation.setDuration(1000);
            rotation.setRepeatCount(ObjectAnimator.INFINITE);
            animatorSet.play(rotation);
            animatorSet.start();
        }
    }
    
    private void showError(String message) {
        Snackbar.make(chatRecyclerView, message, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.error_color))
            .show();
    }
    
    private void scrollToBottom() {
        int itemCount = chatAdapter.getItemCount();
        if (itemCount > 0) {
            chatRecyclerView.scrollToPosition(itemCount - 1);
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(this, HerokuSettingsActivity.class));
            return true;
        } else if (item.getItemId() == R.id.menu_clear) {
            new AlertDialog.Builder(this)
                .setTitle("Очистить историю")
                .setMessage("Вы уверены, что хотите очистить всю историю чата?")
                .setPositiveButton("Да", (d, w) -> {
                    chatManager.clearHistory();
                    chatAdapter.updateMessages(new ArrayList<>());
                    updateEmptyState();
                })
                .setNegativeButton("Отмена", null)
                .show();
            return true;
        } else if (item.getItemId() == R.id.menu_export) {
            String export = chatManager.exportHistory();
            // Копируем в буфер обмена
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Heroku Export", export);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "История экспортирована в буфер обмена", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * Адаптер для RecyclerView чата.
     */
    private class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
        private List<ChatManager.ChatMessage> messages = new ArrayList<>();
        
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView messageText;
            TextView timeText;
            View messageContainer;
            LinearLayout codeContainer;
            TextView codeText;
            ImageButton copyCodeButton;
            
            ViewHolder(View itemView) {
                super(itemView);
                messageText = itemView.findViewById(R.id.messageText);
                timeText = itemView.findViewById(R.id.timeText);
                messageContainer = itemView.findViewById(R.id.messageContainer);
                codeContainer = itemView.findViewById(R.id.codeContainer);
                codeText = itemView.findViewById(R.id.codeText);
                copyCodeButton = itemView.findViewById(R.id.copyCodeButton);
            }
        }
        
        void updateMessages(List<ChatManager.ChatMessage> newMessages) {
            messages = new ArrayList<>(newMessages);
            notifyDataSetChanged();
        }
        
        void addMessage(ChatManager.ChatMessage message) {
            messages.add(message);
            notifyItemInserted(messages.size() - 1);
        }
        
        void updateMessage(int position, String content) {
            if (position >= 0 && position < messages.size()) {
                messages.get(position).content = content;
                notifyItemChanged(position);
            }
        }
        
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == ChatManager.MessageType.USER.ordinal()) {
                view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_user, parent, false);
            } else {
                view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message_ai, parent, false);
            }
            return new ViewHolder(view);
        }
        
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ChatManager.ChatMessage msg = messages.get(position);
            
            // Применяем подсветку синтаксиса для кода
            SpannableStringBuilder styledText = highlightSyntax(msg.content);
            holder.messageText.setText(styledText);
            holder.timeText.setText(msg.getFormattedTime());
            
            // Анимация появления
            holder.itemView.setAlpha(0f);
            holder.itemView.setScaleX(0.8f);
            holder.itemView.setScaleY(0.8f);
            holder.itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(300)
                .setInterpolator(new OvershootInterpolator())
                .start();
            
            // Обработка кнопок копирования кода
            if (holder.copyCodeButton != null) {
                holder.copyCodeButton.setOnClickListener(v -> {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Code", extractCode(msg.content));
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(HerokuMainActivity.this, "Код скопирован", Toast.LENGTH_SHORT).show();
                });
            }
        }
        
        @Override
        public int getItemCount() {
            return messages.size();
        }
        
        @Override
        public int getItemViewType(int position) {
            return messages.get(position).type.ordinal();
        }
        
        /**
         * Подсветка синтаксиса для кода в сообщениях.
         */
        private SpannableStringBuilder highlightSyntax(String text) {
            SpannableStringBuilder ssb = new SpannableStringBuilder(text);
            
            if (!settingsManager.isSyntaxHighlightEnabled()) {
                return ssb;
            }
            
            // Подсветка блоков кода ```code```
            Pattern codePattern = Pattern.compile("```([\\s\\S]*?)```");
            Matcher codeMatcher = codePattern.matcher(ssb);
            
            while (codeMatcher.find()) {
                int start = codeMatcher.start();
                int end = codeMatcher.end();
                
                // Фон для блока кода
                ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(HerokuMainActivity.this, R.color.code_background)),
                    start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                
                // Моноширинный шрифт
                ssb.setSpan(new StyleSpan(Typeface.MONOSPACE),
                    start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            
            // Подсветка inline кода `code`
            Pattern inlinePattern = Pattern.compile("`([^`]+)`");
            Matcher inlineMatcher = inlinePattern.matcher(ssb);
            
            while (inlineMatcher.find()) {
                int start = inlineMatcher.start();
                int end = inlineMatcher.end();
                
                ssb.setSpan(new BackgroundColorSpan(ContextCompat.getColor(HerokuMainActivity.this, R.color.code_background)),
                    start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new StyleSpan(Typeface.MONOSPACE),
                    start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            
            // Подсветка заголовков # ## ###
            Pattern headerPattern = Pattern.compile("^(#{1,3})\\s+(.+)$", Pattern.MULTILINE);
            Matcher headerMatcher = headerPattern.matcher(ssb);
            
            while (headerMatcher.find()) {
                int start = headerMatcher.start(2);
                int end = headerMatcher.end(2);
                
                ssb.setSpan(new StyleSpan(Typeface.BOLD),
                    start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(HerokuMainActivity.this, R.color.primary)),
                    start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            
            return ssb;
        }
        
        private String extractCode(String text) {
            Pattern codePattern = Pattern.compile("```([\\s\\S]*?)```");
            Matcher matcher = codePattern.matcher(text);
            if (matcher.find()) {
                return matcher.group(1).trim();
            }
            return text;
        }
    }
}

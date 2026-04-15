package ru.yufic.exteraPlugins;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

/* loaded from: /storage/emulated/0/Android/data/com.apktools.app.decompile/files/decompile_temp/jadx/classes64.dex */
public class ChatActivity extends AppCompatActivity {
    private void a(Bundle bundle) {
        ImageView imageView;
        int identifier = getResources().getIdentifier("chat_back", "id", getPackageName());
        if (identifier == 0 || (imageView = findViewById(identifier)) == null) {
            return;
        }
        imageView.setOnClickListener(new View.OnClickListener() { // from class: ru.yufic.exteraPlugins.ChatActivity.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                ChatActivity.this.finish();
            }
        });
    }

    private void b() {
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427359);
        a(bundle);
        b();
    }
}

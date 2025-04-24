package com.souunit.gohabit.view;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.souunit.gohabit.R;

public class FormCadPerfil extends AppCompatActivity {

    int[] avatars = {
            R.drawable.brown_boy,
            R.drawable.brown_girl,
            R.drawable.black_boy,
            R.drawable.black_girl,
            R.drawable.light_brown_boy,
            R.drawable.light_brown_girl,
            R.drawable.malhado_boy,
            R.drawable.malhado_girl,
            R.drawable.white_boy,
            R.drawable.white_girl,
    };

    int currentAvatarIndex = 0;
    ImageView avatarImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_cad_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.form_cad_perfil), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        avatarImageView = findViewById(R.id.avatarImageView);
        avatarImageView.setImageResource(avatars[currentAvatarIndex]);

        findViewById(R.id.leftButton).setOnClickListener(v -> {
            if (currentAvatarIndex > 0) {
                currentAvatarIndex--;
                avatarImageView.setImageResource(avatars[currentAvatarIndex]);
            }
        });

        findViewById(R.id.rightButton).setOnClickListener(v -> {
            if (currentAvatarIndex < avatars.length - 1) {
                currentAvatarIndex++;
                avatarImageView.setImageResource(avatars[currentAvatarIndex]);
            }
        });
    }
}
package com.souunit.gohabit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
            //light_bronw_boy and girl removed for an error inexplicable
            R.drawable.malhado_boy,
            R.drawable.malhado_girl,
            R.drawable.white_boy,
            R.drawable.white_girl,
    };

    int currentAvatarIndex = 0;
    ImageView avatarImageView, backButton;

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

        backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormCadPerfil.this, FormCadastro.class);
                startActivity(intent);
            }
        });
    }
}
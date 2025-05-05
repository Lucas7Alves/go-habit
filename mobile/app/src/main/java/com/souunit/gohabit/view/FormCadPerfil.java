package com.souunit.gohabit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.souunit.gohabit.FormLogin;
import com.souunit.gohabit.R;

import java.util.HashMap;
import java.util.Map;

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
    EditText editTextNome, editTextBio;
    Button buttonConfirm;
    String uid;

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


        editTextBio = findViewById(R.id.editTextBio);
        editTextNome = findViewById(R.id.editTextNome);

        avatarImageView = findViewById(R.id.avatarImageView);
        avatarImageView.setImageResource(avatars[currentAvatarIndex]);


        //carrossel de imagens
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

        //botão de voltar
        backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormCadPerfil.this, FormCadastro.class);
                startActivity(intent);
            }
        });

        buttonConfirm = findViewById(R.id.buttonConfirm);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if (currentUser != null) {
                    uid = currentUser.getUid();
                } else {
                    uid = getIntent().getStringExtra("UID");
                    if (uid == null) {
                        startActivity(new Intent(FormCadPerfil.this, FormLogin.class));
                        finish();
                        return;
                    }
                }
                salvarUsuario();
            }
        });
    }

    private void salvarUsuario() {
        String nome = editTextNome.getText().toString();
        String bio = editTextBio.getText().toString();
        int avatarIndex = currentAvatarIndex;



        Map<String, Object> usuario = new HashMap<>();
        usuario.put("nome", nome);
        usuario.put("bio", bio);
        usuario.put("avatarIndex", avatarIndex);

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(uid)
                .set(usuario)
                .addOnSuccessListener(aVoid -> {
                   startActivity(new Intent(FormCadPerfil.this, PrincipalSolo.class));
                });
    }
}
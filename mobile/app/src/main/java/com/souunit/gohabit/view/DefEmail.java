package com.souunit.gohabit.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.souunit.gohabit.R;

public class DefEmail extends AppCompatActivity {

    private EditText etEmail;
    private Button btnConfirm;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formdef_email);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa os elementos
        etEmail = findViewById(R.id.et_email);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnBack = findViewById(R.id.btn_back);

        // Botão de voltar
        btnBack.setOnClickListener(v -> finish());

        // Botão confirmar
        btnConfirm.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            if (email.isEmpty()) {
                Toast.makeText(DefEmail.this, "Digite seu e-mail", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DefEmail.this, "E-mail definido: " + email, Toast.LENGTH_SHORT).show();
                // TODO: Lógica de envio ou validação do e-mail
            }
        });
    }
}

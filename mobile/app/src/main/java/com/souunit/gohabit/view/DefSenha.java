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

public class DefSenha extends AppCompatActivity {

    private EditText etPassword, etConfirmPassword;
    private Button btnConfirm;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formdef_senha);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializa os elementos da interface
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnConfirm = findViewById(R.id.btn_confirm);
        btnBack = findViewById(R.id.btn_back);

        // Ação botão voltar
        btnBack.setOnClickListener(v -> finish());

        // Ação botão confirmar
        btnConfirm.setOnClickListener(v -> {
            String senha = etPassword.getText().toString().trim();
            String confirmarSenha = etConfirmPassword.getText().toString().trim();

            if (senha.isEmpty() || confirmarSenha.isEmpty()) {
                Toast.makeText(DefSenha.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            } else if (!senha.equals(confirmarSenha)) {
                Toast.makeText(DefSenha.this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DefSenha.this, "Senha definida com sucesso!", Toast.LENGTH_SHORT).show();
                // TODO: Lógica para salvar a nova senha
            }
        });
    }
}

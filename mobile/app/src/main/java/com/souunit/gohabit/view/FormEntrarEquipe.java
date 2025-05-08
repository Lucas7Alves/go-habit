package com.souunit.gohabit.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.souunit.gohabit.R;

public class FormEntrarEquipe extends AppCompatActivity {

    private EditText etTocaName;
    private Button btnAccess, btnCreate;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_entrarequipe);

        // Inicializa os elementos da interface
        etTocaName = findViewById(R.id.et_toca_name);
        btnAccess = findViewById(R.id.btn_access);
        btnCreate = findViewById(R.id.btn_create);
        btnBack = findViewById(R.id.btn_back);

        // Botão de voltar
        btnBack.setOnClickListener(v -> finish());

        // Botão acessar
        btnAccess.setOnClickListener(v -> {
            String nomeToca = etTocaName.getText().toString().trim();
            if (nomeToca.isEmpty()) {
                Toast.makeText(this, "Digite o nome da toca", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Entrando na toca: " + nomeToca, Toast.LENGTH_SHORT).show();
                // TODO: Navegar para outra tela ou lógica de login
            }
        });

        // Botão criar toca
        btnCreate.setOnClickListener(v -> {
            Toast.makeText(this, "Criar nova toca!", Toast.LENGTH_SHORT).show();
            // TODO: Redirecionar para a tela de criação de toca
        });
    }
}

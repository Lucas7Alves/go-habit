package com.souunit.gohabit.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.souunit.gohabit.R;

public class InfoCod extends AppCompatActivity {

    private EditText[] codeInputs;
    private Button btnConfirmar;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infocod);

        btnBack = findViewById(R.id.btn_back);
        btnConfirmar = findViewById(R.id.btn_confirmar);

        codeInputs = new EditText[] {
                findViewById(R.id.code1),
                findViewById(R.id.code2),
                findViewById(R.id.code3),
                findViewById(R.id.code4),
                findViewById(R.id.code5),
                findViewById(R.id.code6),
        };

        // Avança automaticamente para o próximo campo
        for (int i = 0; i < codeInputs.length; i++) {
            final int index = i;
            codeInputs[i].addTextChangedListener(new TextWatcher() {
                @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 1 && index < codeInputs.length - 1) {
                        codeInputs[index + 1].requestFocus();
                    }
                }
            });
        }

        btnBack.setOnClickListener(v -> finish());

        btnConfirmar.setOnClickListener(v -> {
            StringBuilder code = new StringBuilder();
            for (EditText input : codeInputs) {
                String digit = input.getText().toString().trim();
                if (digit.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os dígitos", Toast.LENGTH_SHORT).show();
                    return;
                }
                code.append(digit);
            }

            // Aqui você trata o código inserido
            String finalCode = code.toString();
            Toast.makeText(this, "Código: " + finalCode, Toast.LENGTH_SHORT).show();

            // TODO: Verificar o código com backend ou Firebase
        });
    }
}

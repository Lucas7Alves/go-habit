package com.souunit.gohabit.view;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.souunit.gohabit.R;

public class AlterarCad extends AppCompatActivity {

    private EditText etEmail, etSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formalterarcad);

        // Inicializa os campos
        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);

        // Botão de voltar
        ImageButton btnVoltar = findViewById(R.id.btn_voltar);
        btnVoltar.setOnClickListener(v -> finish());

        // Ativa edição ao tocar no ícone
        ativarCampoAoClicarIcone(etEmail);
        ativarCampoAoClicarIcone(etSenha);
    }

    private void ativarCampoAoClicarIcone(EditText editText) {
        editText.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                final int DRAWABLE_END = 2; // posição do drawableEnd (direita)
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {
                    editText.setEnabled(true);
                    editText.setFocusable(true);
                    editText.setFocusableInTouchMode(true);
                    editText.requestFocus();
                    editText.setSelection(editText.getText().length());
                    return true;
                }
            }
            return false;
        });
    }
}

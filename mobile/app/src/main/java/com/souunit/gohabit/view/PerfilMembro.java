package com.souunit.gohabit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.souunit.gohabit.R;

public class PerfilMembro extends AppCompatActivity {

    private ImageButton btnVoltar;
    private ImageView avatarCoelho;
    private TextView nomeMembro, fraseMembro, pontuacaoValor, ranking;

    private Button meta1, meta2, meta3, meta4, meta5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_membro);

        inicializarComponentes();

        configurarBotaoVoltar();

        carregarDadosDoMembro();

        configurarMetas();
    }

    private void inicializarComponentes() {
        btnVoltar = findViewById(R.id.btnVoltar);
        avatarCoelho = findViewById(R.id.avatarCoelho);
        nomeMembro = findViewById(R.id.nomeMembro);
        fraseMembro = findViewById(R.id.fraseMembro);
        pontuacaoValor = findViewById(R.id.pontuacaoValor);
        ranking = findViewById(R.id.ranking);

        meta1 = findViewById(R.id.meta1);
        meta2 = findViewById(R.id.meta2);
        meta3 = findViewById(R.id.meta3);
        meta4 = findViewById(R.id.meta4);
        meta5 = findViewById(R.id.meta5);
    }

    private void configurarBotaoVoltar() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Encerra a activity e retorna à anterior
            }
        });
    }

    private void carregarDadosDoMembro() {
        // Dados mockados para demonstração
        nomeMembro.setText("Cheshire");
        fraseMembro.setText("A maré vai virar!");
        pontuacaoValor.setText("325");
        ranking.setText("Posição no ranking:\nprimeiro lugar");

        // Você pode buscar dados via Intent ou banco de dados depois
        // Exemplo:
        // String nome = getIntent().getStringExtra("nomeMembro");
    }

    private void configurarMetas() {
        meta1.setEnabled(true);
        meta2.setEnabled(true);

        meta3.setEnabled(false);
        meta4.setEnabled(false);
        meta5.setEnabled(false);

        meta1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meta1.setEnabled(false);
                // Lógica para marcar como concluída
            }
        });

        meta2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                meta2.setEnabled(false);
                // Lógica para marcar como concluída
            }
        });
    }
}

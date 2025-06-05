package com.souunit.gohabit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.souunit.gohabit.R;

public class ListaMembros extends AppCompatActivity {

    private ImageButton btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_membros);

        btnVoltar = findViewById(R.id.btnVoltar);
        configurarBotaoVoltar();
        configurarCliquesNosCards();
    }

    private void configurarBotaoVoltar() {
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void configurarCliquesNosCards() {
        int[] cardIds = {

        };

        String[] nomes = {
                "Você",
                "Cheshire",
                "Bunny0fHearts",
                "WhiteHar3",
                "MadR4bbit"
        };

        for (int i = 0; i < cardIds.length; i++) {
            LinearLayout card = findViewById(cardIds[i]);
            String nomeMembro = nomes[i];

            card.setOnClickListener(v -> {
                Intent intent = new Intent(ListaMembros.this, PerfilMembro.class);
                intent.putExtra("nomeMembro", nomeMembro);
                startActivity(intent);
            });
        }
    }
}

package com.souunit.gohabit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.souunit.gohabit.R;

public class PrincipalSolo extends AppCompatActivity {

    Button btn_team, btn_profile;

    ImageButton btnAdd;

    ImageView finishGoal, finishGoal2, finishGoal3;
    boolean isChecked;
    TextView task1, task2, task3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal_solo);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referenciando e atualizando os textos das tarefas
        task1 = findViewById(R.id.task_text1);
        task2 = findViewById(R.id.task_text2);
        task3 = findViewById(R.id.task_text3);

        task1.setText("Beber 2 litros de água");
        task2.setText("Fazer 20 abdominais");
        task3.setText("Fazer 50 polichinelos");

        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalSolo.this, AddMeta.class);
            }
        });




        // ===== buttons finish goals =====
        //TODO: linkar as metas com o firebase
        //TODO: após concluir deixar um temponentinho verde, fazer um som "pop" e sair da tela
        //TODO: após concluir contabilizar pontuação no firebase

        //codigo base para futuras implementações
        finishGoal = findViewById(R.id.finish_goal);
        finishGoal.setOnClickListener(v -> {
            if (isChecked) {
                finishGoal.setImageResource(R.drawable.checkmarkempty);
            } else {
                finishGoal.setImageResource(R.drawable.checkmarkstilled);
            }
            isChecked = !isChecked;
        });

        finishGoal2 = findViewById(R.id.finish_goal2);

        finishGoal2.setOnClickListener(v -> {
            if (isChecked) {
                finishGoal.setImageResource(R.drawable.checkmarkempty);
            } else {
                finishGoal.setImageResource(R.drawable.checkmarkstilled);
            }
            isChecked = !isChecked;
        });

        finishGoal3 = findViewById(R.id.finish_goal3);

        finishGoal3.setOnClickListener(v -> {
            if (isChecked) {
                finishGoal.setImageResource(R.drawable.checkmarkempty);
            } else {
                finishGoal.setImageResource(R.drawable.checkmarkstilled);
            }
            isChecked = !isChecked;
        });

    }
}

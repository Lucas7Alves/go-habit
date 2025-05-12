package com.souunit.gohabit.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.souunit.gohabit.R;

public class PrincipalSolo extends AppCompatActivity {

    Button btnAdd, btn_team, btn_profile;

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
        })

        // Referenciando e atualizando os textos das tarefas
        task1 = findViewById(R.id.task_text1);
        task2 = findViewById(R.id.task_text2);
        task3 = findViewById(R.id.task_text3);

        task1.setText("Beber 2 litros de água");
        task2.setText("Fazer 20 abdominais");
        task3.setText("Fazer 50 polichinelos");


    }
}

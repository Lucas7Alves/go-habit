package com.souunit.gohabit.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.souunit.gohabit.R;

public class CriarEquipe extends AppCompatActivity {

    private EditText editNomeToca;
    private Button[] btnMetas = new Button[5];

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_equipe);

        editNomeToca = findViewById(R.id.editNomeToca);

        btnMetas[0] = findViewById(R.id.btnMeta1);
        btnMetas[1] = findViewById(R.id.btnMeta2);
        btnMetas[2] = findViewById(R.id.btnMeta3);
        btnMetas[3] = findViewById(R.id.btnMeta4);
        btnMetas[4] = findViewById(R.id.btnMeta5);

        for (Button btn : btnMetas) {
            btn.setOnClickListener(v -> {
                Toast.makeText(this, "Abrir seleção de metas", Toast.LENGTH_SHORT).show();
            });
        }

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        Button btnCadastrar = findViewById(R.id.btnCadastrarEquipe);
        btnCadastrar.setOnClickListener(v -> {
            //a  lógica da criação das equipes
            Toast.makeText(this, "Equipe criada!", Toast.LENGTH_SHORT).show();
        });
    }
}

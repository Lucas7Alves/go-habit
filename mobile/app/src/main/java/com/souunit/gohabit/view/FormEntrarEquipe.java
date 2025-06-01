package com.souunit.gohabit.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.souunit.gohabit.R;

import java.util.List;

public class FormEntrarEquipe extends AppCompatActivity {

    private EditText etTocaName;
    private Button btnAccess, btnCreate;
    private ImageButton btnBack;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_entrarequipe);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();

        // Inicializa os elementos da interface
        etTocaName = findViewById(R.id.et_toca_name);
        btnAccess = findViewById(R.id.btn_access);
        btnCreate = findViewById(R.id.btn_create);
        btnBack = findViewById(R.id.btn_back);

        btnBack.setOnClickListener(v -> finish());

        btnAccess.setOnClickListener(v -> {
            String codigoToca = etTocaName.getText().toString().trim();
            if (codigoToca.isEmpty()) {
                Toast.makeText(FormEntrarEquipe.this, "Digite o código da toca", Toast.LENGTH_SHORT).show();
            } else {
                entrarNaEquipe(codigoToca);
            }
        });

        btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(FormEntrarEquipe.this, CriarEquipe.class);
            startActivity(intent);
        });
    }

    private void entrarNaEquipe(String codigoToca) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se o código existe no Firestore
        db.collection("teams")
                .whereEqualTo("codigo", codigoToca)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                Toast.makeText(FormEntrarEquipe.this,
                                        "Código inválido ou toca não encontrada",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                // Código válido, adicionar usuário à equipe
                                DocumentSnapshot equipe = task.getResult().getDocuments().get(0);
                                String teamsId = equipe.getId();

                                // Verifica se o usuário já é membro
                                List<String> membros = (List<String>) equipe.get("membros");
                                if (membros != null && membros.contains(user.getUid())) {
                                    Toast.makeText(FormEntrarEquipe.this,
                                            "Você já é membro desta toca",
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                // Adiciona o usuário à lista de membros
                                db.collection("teams")
                                        .document(teamsId)
                                        .update("members", FieldValue.arrayUnion(user.getUid()))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(FormEntrarEquipe.this,
                                                            "Você entrou na toca com sucesso!",
                                                            Toast.LENGTH_SHORT).show();

                                                    // Redirecionar para a tela principal da equipe
                                                    Intent intent = new Intent(FormEntrarEquipe.this, InfoEquipe.class);
                                                    intent.putExtra("TEAMS_ID", teamsId);
                                                    startActivity(intent);
                                                    finish();
                                                } else {
                                                    Toast.makeText(FormEntrarEquipe.this,
                                                            "Erro ao entrar na toca: " + task.getException().getMessage(),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(FormEntrarEquipe.this,
                                    "Erro ao verificar código: " + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
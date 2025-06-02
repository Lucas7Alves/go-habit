package com.souunit.gohabit.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.souunit.gohabit.model.Meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // Mostrar indicador de carregamento
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Entrando na equipe...");
        progress.setCancelable(false);
        progress.show();

        db.collection("teams")
                .whereEqualTo("codigo", codigoToca)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (querySnapshot.isEmpty()) {
                        progress.dismiss();
                        Toast.makeText(this, "Toca não encontrada!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DocumentSnapshot teamDoc = querySnapshot.getDocuments().get(0);
                    String teamId = teamDoc.getId();
                    String teamCode = teamDoc.getString("codigo"); // Obtém o código da equipe

                    // Verifica se já é membro
                    db.collection("teams").document(teamId)
                            .collection("members").document(user.getUid())
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    if (!task.getResult().exists()) {
                                        // Adiciona como novo membro
                                        Map<String, Object> member = new HashMap<>();
                                        member.put("userId", user.getUid());
                                        member.put("nome", user.getDisplayName());
                                        member.put("email", user.getEmail());
                                        member.put("pontos", 0);
                                        member.put("joinedAt", FieldValue.serverTimestamp());

                                        db.collection("teams").document(teamId)
                                                .collection("members").document(user.getUid())
                                                .set(member)
                                                .addOnSuccessListener(aVoid -> {
                                                    // Atualiza com o CÓDIGO (não com o ID)
                                                    db.collection("users").document(user.getUid())
                                                            .update("currentTeam", teamCode) // Aqui está a mudança principal
                                                            .addOnSuccessListener(aVoid2 -> {
                                                                progress.dismiss();
                                                                Toast.makeText(this, "Entrou na toca com sucesso!", Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(this, PrincipalSolo.class);
                                                                // Envia ambos ID e código se necessário
                                                                intent.putExtra("TEAM_ID", teamId);
                                                                intent.putExtra("TEAM_CODE", teamCode);
                                                                startActivity(intent);
                                                                finish();
                                                            })
                                                            .addOnFailureListener(e -> {
                                                                progress.dismiss();
                                                                Toast.makeText(this, "Erro ao atualizar usuário", Toast.LENGTH_SHORT).show();
                                                                Log.e("Equipe", "Erro update user", e);
                                                            });
                                                })
                                                .addOnFailureListener(e -> {
                                                    progress.dismiss();
                                                    Toast.makeText(this, "Erro ao entrar na equipe", Toast.LENGTH_SHORT).show();
                                                    Log.e("Equipe", "Erro add member", e);
                                                });
                                    } else {
                                        progress.dismiss();
                                        Toast.makeText(this, "Você já está nesta toca!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                })
                .addOnFailureListener(e -> {
                    progress.dismiss();
                    Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Equipe", "Erro find team", e);
                });
    }
    private void adicionarMembro(String teamId, String userId) {
        db.collection("users").document(userId).get()
                .addOnSuccessListener(userDoc -> {
                    if (userDoc.exists()) {
                        Map<String, Object> member = new HashMap<>();
                        member.put("nome", userDoc.getString("nome"));
                        member.put("pontos", userDoc.get("pontos") != null ? userDoc.get("pontos") : 0);
                        member.put("email", userDoc.getString("email"));
                        member.put("avatarIndex", userDoc.get("avatarIndex") != null ? userDoc.get("avatarIndex") : 0);
                        member.put("userId", userId);
                        member.put("joinedAt", FieldValue.serverTimestamp());

                        db.collection("teams").document(teamId)
                                .collection("members").document(userId)
                                .set(member)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Equipe", "Membro adicionado com sucesso");
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("Equipe", "Erro ao adicionar membro", e);
                                });
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Equipe", "Erro ao obter dados do usuário", e);
                });
    }

    private void atualizarTimeDoUsuario(String teamId) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        db.collection("users").document(user.getUid())
                .update("currentTeam", teamId)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Equipe", "Time do usuário atualizado com sucesso");
                })
                .addOnFailureListener(e -> {
                    Log.e("Equipe", "Erro ao atualizar time do usuário", e);
                });
    }

    public void sincronizarPontos(String userId, String teamId, int novosPontos) {
        db.collection("teams").document(teamId)
                .collection("members").document(userId)
                .update("pontos", novosPontos);

        db.collection("users").document(userId)
                .update("pontos", novosPontos);
    }
}
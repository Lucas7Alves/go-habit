package com.souunit.gohabit.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.souunit.gohabit.R;

public class Perfil extends AppCompatActivity {

    private static final String TAG = "nina123";

    private ImageView avatar;
    private TextView username, bio, teamName;
    private Button leaveTeam;

    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    private final int[] avatars = {
            R.drawable.brown_boy,
            R.drawable.brown_girl,
            R.drawable.black_boy,
            R.drawable.black_girl,
            R.drawable.malhado_boy,
            R.drawable.malhado_girl,
            R.drawable.white_boy,
            R.drawable.white_girl,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        db = FirebaseFirestore.getInstance();

        initViews();
        setupClickListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser == null) {
            finish();
            return;
        }

        loadUserData();
    }

    private void initViews() {
        avatar = findViewById(R.id.avatar);
        username = findViewById(R.id.username);
        bio = findViewById(R.id.bio);
        teamName = findViewById(R.id.team_name);
        leaveTeam = findViewById(R.id.leave_team);

        teamName.setText("Carregando...");
    }

    private void setupClickListeners() {
        findViewById(R.id.button_back).setOnClickListener(v -> finish());

        leaveTeam.setOnClickListener(v -> {
            Toast.makeText(this, "Funcionalidade de sair da toca", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadUserTeam() {
        if (currentUser == null) return;

        db.collection("users").document(currentUser.getUid())
                .get()
                .addOnSuccessListener(userDoc -> {
                    if (userDoc.exists() && userDoc.contains("currentTeam")) {
                        String codigo = userDoc.getString("currentTeam");

                        db.collection("teams")
                                .whereEqualTo("codigo", codigo)
                                .get()
                                .addOnCompleteListener(teamTask -> {
                                    if (teamTask.isSuccessful() && !teamTask.getResult().isEmpty()) {
                                        DocumentSnapshot teamDoc = teamTask.getResult().getDocuments().get(0);
                                        String teamId = teamDoc.getId();
                                        String teamNameFire = teamDoc.getString("name");

                                        // Atualiza a UI
                                        runOnUiThread(() -> {
                                            teamName.setText("Sua toca: " + teamNameFire);
                                            leaveTeam.setVisibility(View.VISIBLE);

                                            // Salva o ID real do time para usar depois
                                            SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                                            preferences.edit().putString("currentTeamId", teamId).apply();
                                        });
                                    } else {
                                        runOnUiThread(() -> {
                                            teamName.setText("Toca não encontrada");
                                            leaveTeam.setVisibility(View.GONE);
                                        });
                                    }
                                });
                    } else {
                        runOnUiThread(() -> {
                            teamName.setText("Você não está em uma toca");
                            leaveTeam.setVisibility(View.GONE);
                        });
                    }
                });
    }

    private void loadUserData() {

        db.collection("users").document(currentUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            updateUI(document);
                        } else {
                            Toast.makeText(Perfil.this, "Dados do usuário não encontrados", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(Perfil.this, "Erro ao carregar perfil", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void updateUI(DocumentSnapshot document) {
        try {
            if (document.contains("nome")) {
                String nome = document.getString("nome");
                username.setText(nome != null ? nome : "Nome não definido");
            }

            if (document.contains("bio")) {
                String userBio = document.getString("bio");
                bio.setText(userBio != null ? userBio : "Bio não definida");
            }

            if (document.contains("avatarIndex")) {
                Long index = document.getLong("avatarIndex");
                if (index != null && index >= 0 && index < avatars.length) {
                    avatar.setImageResource(avatars[index.intValue()]);
                }
            }

            if (document.contains("currentTeam")) {
                String teamCode = document.getString("currentTeam");
                if (teamCode != null && !teamCode.isEmpty()) {
                    loadTeamByCode(teamCode);
                } else {
                    showNoTeam();
                }
            } else {
                showNoTeam();
            }
        } catch (Exception e) {
        }
    }

    private void loadTeamByCode(String teamCode) {

        db.collection("teams")
                .whereEqualTo("codigo", teamCode.trim())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        DocumentSnapshot teamDoc = task.getResult().getDocuments().get(0);

                        String teamNameStr = teamDoc.getString("name");
                        if (teamNameStr != null) {
                            teamName.setText("Sua toca: " + teamNameStr);
                            leaveTeam.setVisibility(View.VISIBLE);
                        } else {
                            showTeamNotFound();
                        }
                    } else {
                        showTeamNotFound();
                    }
                });
    }

    private void loadTeamData(String teamId) {

        db.collection("teams").document(teamId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        DocumentSnapshot teamDoc = task.getResult();
                        if (teamDoc.exists() && teamDoc.contains("name")) {
                            String name = teamDoc.getString("name");
                            teamName.setText(name != null ? "Sua toca: " + name : "Nome da toca inválido");
                            leaveTeam.setVisibility(View.VISIBLE);
                        } else {
                            showTeamNotFound();
                        }
                    } else {
                        showTeamNotFound();
                    }
                });
    }

    private void showNoTeam() {
        teamName.setText("Você não está em uma toca");
        leaveTeam.setVisibility(View.GONE);
    }

    private void showTeamNotFound() {
        teamName.setText("Toca não encontrada");
        leaveTeam.setVisibility(View.GONE);
    }
}
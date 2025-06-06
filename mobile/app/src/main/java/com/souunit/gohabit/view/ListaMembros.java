package com.souunit.gohabit.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.souunit.gohabit.R;

import java.util.List;

public class ListaMembros extends AppCompatActivity {

    private ImageButton btnVoltar;
    private LinearLayout containerMembros;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_membros);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        btnVoltar = findViewById(R.id.btnVoltar);
        containerMembros = findViewById(R.id.containerMembros);

        configurarBotaoVoltar();
        carregarMembrosDaEquipe();
    }

    private void configurarBotaoVoltar() {
        btnVoltar.setOnClickListener(v -> finish());
    }

    private void carregarMembrosDaEquipe() {
        if (currentUser == null) {
            Toast.makeText(this, "Usuário não autenticado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        db.collection("users").document(currentUser.getUid())
                .get()
                .addOnSuccessListener(userDoc -> {
                    if (userDoc.exists() && userDoc.contains("currentTeam")) {
                        String teamCode = userDoc.getString("currentTeam");
                        buscarEquipe(teamCode);
                    } else {
                        Toast.makeText(this, "Você não está em uma equipe", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
                    finish();
                });
    }

    private void buscarEquipe(String teamCode) {
        db.collection("teams")
                .whereEqualTo("codigo", teamCode)
                .limit(1)
                .get()
                .addOnSuccessListener(teamQuery -> {
                    if (!teamQuery.isEmpty()) {
                        teamId = teamQuery.getDocuments().get(0).getId();
                        carregarMembrosOrdenados();
                    } else {
                        Toast.makeText(this, "Equipe não encontrada", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void carregarMembrosOrdenados() {
        db.collection("teams").document(teamId)
                .collection("members")
                .orderBy("pontos", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    containerMembros.removeAllViews();

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        String nome = doc.getString("nome");
                        Long pontos = doc.getLong("pontos");
                        Long avatarIndex = doc.getLong("avatarIndex");
                        String userId = doc.getId();

                        boolean isCurrentUser = userId.equals(currentUser.getUid());
                        criarCardMembro(
                                isCurrentUser ? "Você" : nome,
                                pontos != null ? pontos.intValue() : 0,
                                avatarIndex != null ? avatarIndex.intValue() : 0,
                                isCurrentUser,
                                userId
                        );
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Erro ao carregar membros", Toast.LENGTH_SHORT).show();
                });
    }

    private void criarCardMembro(String nome, int pontos, int avatarIndex, boolean isCurrentUser, String userId) {
        LinearLayout card = new LinearLayout(this);
        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(
                dpToPx(16),
                dpToPx(8),
                dpToPx(16),
                dpToPx(12)
        );
        card.setLayoutParams(cardParams);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setBackground(ContextCompat.getDrawable(this, R.color.selected_chip_color));
        card.setPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8));
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setElevation(dpToPx(2));

        ImageView avatar = new ImageView(this);
        LinearLayout.LayoutParams avatarParams = new LinearLayout.LayoutParams(
                dpToPx(50),
                dpToPx(50)
        );
        avatar.setLayoutParams(avatarParams);
        avatar.setImageResource(obterAvatarPorIndice(avatarIndex));
        avatar.setContentDescription("Avatar de " + nome);
        card.addView(avatar);

        LinearLayout infoLayout = new LinearLayout(this);
        LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        infoParams.setMargins(dpToPx(12), 0, 0, 0);
        infoLayout.setLayoutParams(infoParams);
        infoLayout.setOrientation(LinearLayout.VERTICAL);

        TextView nomeView = new TextView(this);
        nomeView.setText(nome);
        nomeView.setTextColor(ContextCompat.getColor(this, R.color.roxo_principal));
        nomeView.setTextSize(16);
        nomeView.setTypeface(null, Typeface.BOLD);
        infoLayout.addView(nomeView);

        TextView pontosView = new TextView(this);
        pontosView.setText(pontos + " pontos");
        pontosView.setTextColor(ContextCompat.getColor(this, R.color.roxo_principal));
        pontosView.setTextSize(14);
        infoLayout.addView(pontosView);

        card.addView(infoLayout);

        if (!isCurrentUser) {
            ImageButton btnExcluir = new ImageButton(this);
            LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                    dpToPx(32),
                    dpToPx(32)
            );
            btnExcluir.setLayoutParams(btnParams);
            btnExcluir.setBackground(ContextCompat.getDrawable(this, android.R.color.transparent));
            btnExcluir.setImageResource(R.drawable.buttonexclude);
            btnExcluir.setContentDescription("Remover " + nome);
            btnExcluir.setOnClickListener(v -> removerMembro(userId));
            card.addView(btnExcluir);
        }

        card.setOnClickListener(v -> {
            if (!isCurrentUser) {
                Intent intent = new Intent(ListaMembros.this, PerfilMembro.class);
                intent.putExtra("nomeMembro", nome);
                intent.putExtra("pontosMembro", pontos);
                startActivity(intent);
            }
        });

        containerMembros.addView(card);
    }

    private int obterAvatarPorIndice(int index) {
        int[] avatares = {
                R.drawable.brown_boy,
                R.drawable.brown_girl,
                R.drawable.black_boy,
                R.drawable.black_girl,
                R.drawable.malhado_boy,
                R.drawable.malhado_girl,
                R.drawable.white_boy,
                R.drawable.white_girl,
        };
        return avatares[index % avatares.length];
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void removerMembro(String memberId) {
        // Implementar lógica de remoção do membro
        Toast.makeText(this, "Remover membro: " + memberId, Toast.LENGTH_SHORT).show();
    }
}
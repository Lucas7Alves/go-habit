package com.souunit.gohabit.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.souunit.gohabit.R;
import com.souunit.gohabit.adapter.TeamMembersAdapter;
import com.souunit.gohabit.model.TeamMember;

import java.util.ArrayList;
import java.util.List;

public class InfoEquipe extends AppCompatActivity {

    private RecyclerView membersRecyclerView;
    private TeamMembersAdapter adapter;
    private TextView teamCodeTextView, teamNameTextView;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_equipe);

        // Inicializa Firebase
        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Inicializa views
        teamCodeTextView = findViewById(R.id.team_code);
        teamNameTextView = findViewById(R.id.team_name);
        membersRecyclerView = findViewById(R.id.members_list);

        // Configura RecyclerView
        membersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamMembersAdapter(new ArrayList<>());
        membersRecyclerView.setAdapter(adapter);

        // Carrega dados
        if (currentUser != null) {
            loadTeamData();
        } else {
            showAuthError();
        }
    }

    private void loadTeamData() {
        db.collection("users").document(currentUser.getUid())
                .get()
                .addOnSuccessListener(userDoc -> {
                    if (userDoc.exists() && userDoc.contains("currentTeam")) {
                        String teamCode = userDoc.getString("currentTeam");
                        teamCodeTextView.setText(teamCode);
                        loadTeamInfoAndMembers(teamCode);
                    } else {
                        showTeamNotFound();
                    }
                })
                .addOnFailureListener(e -> showLoadError());
    }

    private void loadTeamInfoAndMembers(String teamCode) {
        // Primeiro encontra o ID da equipe pelo código
        db.collection("teams")
                .whereEqualTo("codigo", teamCode)
                .limit(1)
                .get()
                .addOnSuccessListener(teamQuery -> {
                    if (!teamQuery.isEmpty()) {
                        DocumentSnapshot teamDoc = teamQuery.getDocuments().get(0);
                        String teamId = teamDoc.getId();

                        // Carrega nome da equipe
                        teamNameTextView.setText(teamDoc.getString("name"));

                        // Carrega membros da subcoleção
                        loadTeamMembers(teamId);
                    } else {
                        showTeamNotFound();
                    }
                })
                .addOnFailureListener(e -> showLoadError());
    }

    private void loadTeamMembers(String teamId) {
        // Busca na subcoleção members ordenando por pontos
        db.collection("teams").document(teamId)
                .collection("members")
                .orderBy("pontos", Query.Direction.DESCENDING)
                .limit(3)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<TeamMember> members = new ArrayList<>();
                    int position = 1;

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        members.add(new TeamMember(
                                doc.getString("nome"),
                                getPositionString(position),
                                doc.getLong("pontos") != null ? doc.getLong("pontos").intValue() : 0
                        ));
                        position++;
                    }

                    adapter.updateMembers(members);
                })
                .addOnFailureListener(e -> {
                    Log.e("nina123", "loadTeamMembers: ", e);
                    Toast.makeText(this, "Erro ao carregar membros", Toast.LENGTH_SHORT).show();
                });
    }

    private String getPositionString(int position) {
        switch (position) {
            case 1: return "1º";
            case 2: return "2º";
            case 3: return "3º";
            default: return position + "º";
        }
    }

    private void showAuthError() {
        Toast.makeText(this, "Autenticação necessária", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showLoadError() {
        Toast.makeText(this, "Erro ao carregar dados", Toast.LENGTH_SHORT).show();
    }

    private void showTeamNotFound() {
        Toast.makeText(this, "Equipe não encontrada", Toast.LENGTH_SHORT).show();
    }
}
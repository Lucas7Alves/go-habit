package com.souunit.gohabit.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InfoEquipe extends AppCompatActivity {

    private RecyclerView membersRecyclerView;
    private TeamMembersAdapter adapter;
    private TextView teamCodeTextView, teamNameTextView;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private LinearLayout goalsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_equipe);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        teamCodeTextView = findViewById(R.id.team_code);
        teamNameTextView = findViewById(R.id.team_name);
        membersRecyclerView = findViewById(R.id.members_list);
        goalsContainer = findViewById(R.id.goals_container);

        membersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TeamMembersAdapter(new ArrayList<>());
        membersRecyclerView.setAdapter(adapter);

        if (currentUser != null) {
            loadTeamData();
            loadUserGoals();
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
        db.collection("teams")
                .whereEqualTo("codigo", teamCode)
                .limit(1)
                .get()
                .addOnSuccessListener(teamQuery -> {
                    if (!teamQuery.isEmpty()) {
                        DocumentSnapshot teamDoc = teamQuery.getDocuments().get(0);
                        String teamId = teamDoc.getId();
                        teamNameTextView.setText(teamDoc.getString("name"));
                        loadTeamMembers(teamId);
                    } else {
                        showTeamNotFound();
                    }
                })
                .addOnFailureListener(e -> showLoadError());
    }

    private void loadTeamMembers(String teamId) {
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
                    Toast.makeText(this, "Erro ao carregar membros", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadUserGoals() {
        String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
        currentDay = currentDay.substring(0, 3).toLowerCase();

        db.collection("users").document(currentUser.getUid())
                .get()
                .addOnSuccessListener(userDoc -> {
                    if (userDoc.exists() && userDoc.contains("currentTeam")) {
                        String teamCode = userDoc.getString("currentTeam");

                        db.collection("teams")
                                .whereEqualTo("codigo", teamCode)
                                .addSnapshotListener((value, error) -> {
                                    if (error != null) {
                                        return;
                                    }

                                    goalsContainer.removeAllViews();

                                    if (value != null && !value.isEmpty()) {
                                        for (DocumentSnapshot doc : value.getDocuments()) {
                                            List<Object> metasList = (List<Object>) doc.get("metas");
                                            if (metasList != null) {
                                                for (Object metaObj : metasList) {
                                                    if (metaObj instanceof Map) {
                                                        Map<String, Object> metaMap = (Map<String, Object>) metaObj;
                                                        createGoalCard(metaMap);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        TextView emptyView = new TextView(this);
                                        emptyView.setText("Nenhuma meta para hoje");
                                        emptyView.setTextColor(Color.WHITE);
                                        emptyView.setTextSize(16);
                                        emptyView.setGravity(Gravity.CENTER);
                                        goalsContainer.addView(emptyView);
                                    }
                                });

                    } else {
                        showTeamNotFound();
                    }
                });
    }

    private void createGoalCard(Map<String, Object> meta) {
        String title = (String) meta.get("title");
        Long intensity = (Long) meta.get("intensity");

        FrameLayout taskFrame = new FrameLayout(this);
        FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.goal_card_height)
        );
        frameParams.setMargins(0, 0, 0, (int) getResources().getDimension(R.dimen.goal_card_margin));
        taskFrame.setLayoutParams(frameParams);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundResource(R.drawable.basetasks);
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setPadding(
                (int) getResources().getDimension(R.dimen.goal_card_padding),
                0,
                (int) getResources().getDimension(R.dimen.goal_card_padding),
                0
        );

        ImageView finishGoal = new ImageView(this);
        LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.goal_check_size),
                (int) getResources().getDimension(R.dimen.goal_check_size)
        );
        finishGoal.setLayoutParams(checkParams);
        finishGoal.setImageResource(R.drawable.checkmarkempty);
        linearLayout.addView(finishGoal);

        TextView taskText = new TextView(this);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1
        );
        textParams.setMargins(
                (int) getResources().getDimension(R.dimen.goal_text_margin),
                0, 0, 0
        );
        taskText.setLayoutParams(textParams);
        taskText.setTextColor(Color.WHITE);
        taskText.setTypeface(ResourcesCompat.getFont(this, R.font.inter_bold), Typeface.BOLD);
        linearLayout.addView(taskText);

        ImageView progressBar = new ImageView(this);
        FrameLayout.LayoutParams progressParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.goal_progress_height)
        );
        progressParams.gravity = Gravity.BOTTOM;
        progressBar.setLayoutParams(progressParams);

        taskText.setText(title != null ? title : "Meta sem título");

        if (intensity != null) {
            if (intensity == 1) {
                progressBar.setImageResource(R.drawable.greentag);
            } else if (intensity == 2) {
                progressBar.setImageResource(R.drawable.yellowtag);
            } else {
                progressBar.setImageResource(R.drawable.redtag);
            }
        }

        taskFrame.addView(linearLayout);
        taskFrame.addView(progressBar);
        goalsContainer.addView(taskFrame);
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
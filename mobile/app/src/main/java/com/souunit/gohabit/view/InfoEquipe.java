package com.souunit.gohabit.view;

import android.content.Intent;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.souunit.gohabit.R;
import com.souunit.gohabit.adapter.TeamMembersAdapter;
import com.souunit.gohabit.model.TeamMember;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private String teamId;

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
                        teamId = teamDoc.getId();
                        teamNameTextView.setText(teamDoc.getString("name"));
                        loadTeamMembers();
                        loadTeamGoals(teamDoc);
                    } else {
                        showTeamNotFound();
                    }
                })
                .addOnFailureListener(e -> showLoadError());
    }

    private void loadTeamMembers() {
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

    private void loadTeamGoals(DocumentSnapshot teamDoc) {
        String currentDay = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
        currentDay = currentDay.substring(0, 3).toLowerCase();

        List<Map<String, Object>> metas = (List<Map<String, Object>>) teamDoc.get("metas");
        goalsContainer.removeAllViews();

        if (metas != null && !metas.isEmpty()) {
            for (Map<String, Object> meta : metas) {
                List<String> days = (List<String>) meta.get("days");
                if (days != null && days.contains(currentDay)) {
                    createGoalCard(meta);
                }
            }
        } else {
            showEmptyGoalsMessage();
        }
    }

    private void createGoalCard(Map<String, Object> meta) {
        String title = (String) meta.get("title");
        Long intensity = (Long) meta.get("intensity");
        List<String> completedBy = (List<String>) meta.get("completedBy");

        boolean isCompleted = completedBy != null && completedBy.contains(currentUser.getUid());

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
        finishGoal.setImageResource(isCompleted ? R.drawable.checkmarkstilled : R.drawable.checkmarkempty);
        finishGoal.setOnClickListener(v -> completeGoal(meta, finishGoal));
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
        taskText.setText(title != null ? title : "Meta sem título");
        taskText.setTextColor(Color.WHITE);
        taskText.setTypeface(ResourcesCompat.getFont(this, R.font.inter_bold), Typeface.BOLD);
        linearLayout.addView(taskText);

        ImageView editIcon = new ImageView(this);
        LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                (int) getResources().getDimension(R.dimen.goal_check_size),
                (int) getResources().getDimension(R.dimen.goal_check_size)
        );
        editIcon.setLayoutParams(editParams);
        editIcon.setImageResource(R.drawable.taskeditor);
        editIcon.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarMeta.class);
            intent.putExtra("teamId", teamId);
            startActivity(intent);
        });
        linearLayout.addView(editIcon);

        ImageView progressBar = new ImageView(this);
        FrameLayout.LayoutParams progressParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.goal_progress_height)
        );
        progressParams.gravity = Gravity.BOTTOM;
        progressBar.setLayoutParams(progressParams);

        if (intensity != null) {
            progressBar.setImageResource(
                    intensity == 1 ? R.drawable.greentag :
                            intensity == 2 ? R.drawable.yellowtag : R.drawable.redtag);
        }

        taskFrame.addView(linearLayout);
        taskFrame.addView(progressBar);
        goalsContainer.addView(taskFrame);
    }

    private void completeGoal(Map<String, Object> meta, ImageView finishGoal) {
        List<String> completedBy = (List<String>) meta.get("completedBy");
        boolean isCompleted = completedBy != null && completedBy.contains(currentUser.getUid());

        int pointsToAdd;
        Long intensity = (Long) meta.get("intensity");
        if (intensity != null) {
            pointsToAdd = intensity == 1 ? 15 : intensity == 2 ? 35 : 60;
        } else {
            pointsToAdd = 0;
        }

        Map<String, Object> updatedMeta = new HashMap<>(meta);

        List<String> newCompletedBy = new ArrayList<>();
        if (completedBy != null) {
            newCompletedBy.addAll(completedBy);
        }
        newCompletedBy.add(currentUser.getUid());
        updatedMeta.put("completedBy", newCompletedBy);
        updatedMeta.put("lastCompleted", new Date());

        db.runTransaction(transaction -> {

            DocumentReference teamRef = db.collection("teams").document(teamId);

            transaction.update(teamRef, "metas", FieldValue.arrayRemove(meta));
            transaction.update(teamRef, "metas", FieldValue.arrayUnion(updatedMeta));

            DocumentReference memberRef = teamRef.collection("members").document(currentUser.getUid());
            transaction.update(memberRef, "pontos", FieldValue.increment(pointsToAdd));

            return null;
        }).addOnSuccessListener(aVoid -> {
            finishGoal.setImageResource(R.drawable.checkmarkstilled);
            Toast.makeText(this, "Meta concluída! +" + pointsToAdd + " pontos", Toast.LENGTH_SHORT).show();
            loadTeamMembers();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Erro ao completar meta", Toast.LENGTH_SHORT).show();
        });
    }

    private void showEmptyGoalsMessage() {
        TextView emptyView = new TextView(this);
        emptyView.setText("Nenhuma meta para hoje");
        emptyView.setTextColor(Color.WHITE);
        emptyView.setTextSize(16);
        emptyView.setGravity(Gravity.CENTER);
        goalsContainer.addView(emptyView);
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
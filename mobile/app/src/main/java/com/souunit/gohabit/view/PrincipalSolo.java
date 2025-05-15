package com.souunit.gohabit.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.souunit.gohabit.FormLogin;
import com.souunit.gohabit.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PrincipalSolo extends AppCompatActivity {

    Button btn_team, btn_profile;

    ImageButton btnAdd;

    ImageView finishGoal, finishGoal2, finishGoal3;
    boolean isChecked;
    TextView task1, task2, task3, logout;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String uid;
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
        logout = findViewById(R.id.logout);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PrincipalSolo.this, AddMeta.class);
                startActivity(intent);
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
                finishGoal2.setImageResource(R.drawable.checkmarkempty);
            } else {
                finishGoal2.setImageResource(R.drawable.checkmarkstilled);
            }
            isChecked = !isChecked;
        });

        finishGoal3 = findViewById(R.id.finish_goal3);

        finishGoal3.setOnClickListener(v -> {
            if (isChecked) {
                finishGoal3.setImageResource(R.drawable.checkmarkempty);
            } else {
                finishGoal3.setImageResource(R.drawable.checkmarkstilled);
            }
            isChecked = !isChecked;
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //chamada de métodos do firebase
        setUsernameFromFireBase();
        getGoalsFromFirestore();


        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, FormLogin.class));
            finish();
        });
    }

    private void setUsernameFromFireBase() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            uid = currentUser.getUid();
        }

        db.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String nome = documentSnapshot.getString("nome");

                        TextView textNome = findViewById(R.id.username);

                        textNome.setText(nome);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE_ERROR", "Erro ao buscar dados", e);
                });
    }

    private void getGoalsFromFirestore() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            uid = currentUser.getUid();
        }

        String currentDay = new SimpleDateFormat("EEEE", new Locale("en")).format(new Date());
        currentDay = currentDay.substring(0,3);
        String formatCurrentDay = currentDay.toLowerCase();

        LinearLayout container = findViewById(R.id.containerMetas);

        db.collection("users")
                .document(uid)
                .collection("goals")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    container.removeAllViews();

                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        List<String> dias = (List<String>) doc.get("days");

                        if (dias == null) {
                            Toast.makeText(this, "metas nulas", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "metas não nulas", Toast.LENGTH_SHORT).show();
                        }

                        if (dias != null && dias.contains(formatCurrentDay)) {
                            String title = doc.getString("title");

                            TextView card = new TextView(this);
                            card.setText(title);
                            card.setTextSize(18);
                            card.setPadding(20, 20, 20, 20);
                            card.setBackgroundResource(R.drawable.basetasks);
                            card.setTextColor(Color.BLACK);

                            container.addView(card);
                        }
                    }

                    if (container.getChildCount() == 0) {
                        TextView vazio = new TextView(this);
                        vazio.setText("Nenhuma meta para hoje!");
                        vazio.setTextSize(16);
                        vazio.setGravity(Gravity.CENTER);
                        container.addView(vazio);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("ERRO_GOALS", "erro na meta: " + e.getMessage());
                });

    }
}

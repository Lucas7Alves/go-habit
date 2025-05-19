package com.souunit.gohabit.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.souunit.gohabit.FormLogin;
import com.souunit.gohabit.R;
import com.souunit.gohabit.model.MetaStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PrincipalSolo extends AppCompatActivity {

    Button btn_team, btn_profile;

    ImageButton btnAdd;

    boolean isChecked;
    TextView logout;

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
        //TODO: Excluir meta
        //TODO: Editar meta
        //- tela de editar meta
        //- lógica de editar meta
        //TODO: após concluir deixar um temponentinho verde, fazer um som "pop" e sair da tela
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

        Query query =  db.collection("users")
                .document(uid)
                .collection("goals")
                .whereArrayContains("days", formatCurrentDay)
                .whereEqualTo("status", "pending");

        query.addSnapshotListener((value, error) -> {
            if (error != null) {
                Toast.makeText(this, "Erro ao carregar", Toast.LENGTH_SHORT).show();
                return;
            }

            container.removeAllViews();

            int currentId = 0;
            for (DocumentSnapshot doc : value.getDocuments()) {

                currentId =+ 1;
                List<String> dias = (List<String>) doc.get("days");


                if (dias != null && dias.contains(formatCurrentDay) && MetaStatus.PENDING.getValue().equals(doc.getString("status"))) {
                    String title = doc.getString("title");

                    // Criar o FrameLayout (container principal)
                    FrameLayout taskFrame = new FrameLayout(this);
                    FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 340, getResources().getDisplayMetrics()),
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 94, getResources().getDisplayMetrics())
                    );
                    frameParams.gravity = Gravity.CENTER_HORIZONTAL;
                    frameParams.topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
                    taskFrame.setLayoutParams(frameParams);
                    taskFrame.setId(View.generateViewId());

                    // Criar o LinearLayout (conteúdo da tarefa)
                    LinearLayout linearLayout = new LinearLayout(this);
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    ));
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setBackgroundResource(R.drawable.basetasks);
                    linearLayout.setGravity(Gravity.CENTER_VERTICAL);
                    linearLayout.setPadding(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()),
                            0,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()),
                            0
                    );

                    // Criar ImageView (checkmark)
                    ImageView finishGoal = new ImageView(this);
                    LinearLayout.LayoutParams checkParams = new LinearLayout.LayoutParams(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()),
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics())
                    );
                    finishGoal.setLayoutParams(checkParams);
                    finishGoal.setId(View.generateViewId());
                    finishGoal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (isChecked) {
                                finishGoal.setImageResource(R.drawable.checkmarkempty);
                            } else {
                                finishGoal.setImageResource(R.drawable.checkmarkstilled);


                                Map<String, Object> completedDate = new HashMap<>();
                                completedDate.put("completedDate", new Date());

                                db.collection("users")
                                        .document(uid)
                                        .collection("goals")
                                        .document(doc.getId())
                                        .update(completedDate);

                                db.collection("users")
                                        .document(uid)
                                        .collection("goals")
                                        .document(doc.getId())
                                        .update("status", MetaStatus.COMPLETED.getValue())
                                        .addOnSuccessListener(aVoid -> {
                                            Toast.makeText(PrincipalSolo.this, "Meta concluída!", Toast.LENGTH_SHORT).show();
                                        });
                            }
                            isChecked = !isChecked;
                        }
                    });
                    finishGoal.setContentDescription("Feito");
                    finishGoal.setImageResource(R.drawable.checkmarkempty);
                    linearLayout.addView(finishGoal);

                    // Criar TextView (texto da tarefa)
                    TextView taskText = new TextView(this);
                    LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    );
                    textParams.setMargins(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getResources().getDisplayMetrics()),
                            0, 0, 0
                    );
                    taskText.setLayoutParams(textParams);
                    taskText.setId(View.generateViewId());
                    taskText.setText(title);
                    taskText.setTextColor(Color.WHITE);
                    taskText.setTypeface(ResourcesCompat.getFont(this, R.font.inter_bold), Typeface.BOLD);
                    linearLayout.addView(taskText);

                    // Criar ImageView (ícone de editar)
                    ImageView editIcon = new ImageView(this);
                    LinearLayout.LayoutParams editParams = new LinearLayout.LayoutParams(
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, getResources().getDisplayMetrics()),
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 28, getResources().getDisplayMetrics())
                    );
                    editIcon.setLayoutParams(editParams);
                    editIcon.setContentDescription("Editar");
                    editIcon.setImageResource(R.drawable.taskeditor);
                    linearLayout.addView(editIcon);

                    // Adicionar LinearLayout ao FrameLayout
                    taskFrame.addView(linearLayout);

                    // Criar ImageView (barra de progresso)
                    ImageView progressBar = new ImageView(this);
                    FrameLayout.LayoutParams progressParams = new FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics())
                    );
                    progressParams.gravity = Gravity.BOTTOM;
                    progressParams.bottomMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
                    progressBar.setLayoutParams(progressParams);
                    progressBar.setContentDescription("Indicador de progresso");

                    if (doc.getLong("intensity") == (long) 1) {
                        progressBar.setImageResource(R.drawable.greentag);
                    } else if (doc.getLong("intensity") == (long) 2) {
                        progressBar.setImageResource(R.drawable.yellowtag);
                    } else {
                        progressBar.setImageResource(R.drawable.redtag);
                    }

                    // Adicionar barra de progresso ao FrameLayout
                    taskFrame.addView(progressBar);

                    // Adicionar o card completo ao container
                    container.addView(taskFrame);
                }
            }

            if (container.getChildCount() == 0) {
                TextView vazio = new TextView(this);
                vazio.setText("Nenhuma meta para hoje!");
                vazio.setTextSize(16);
                vazio.setGravity(Gravity.CENTER);
                container.addView(vazio);
            }
        });

    }
}

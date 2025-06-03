package com.souunit.gohabit.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.souunit.gohabit.R;
import com.souunit.gohabit.model.Meta;
import com.souunit.gohabit.util.MetasAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class CriarEquipe extends AppCompatActivity {

    private EditText editNomeToca;
    private EditText editTextMeta;
    private ChipGroup weekDaysChipGroup;
    private ImageView dailyCheck, intensityThree, intensityTwo, intensityOne;
    private Button btnAdicionarMeta, btnCadastrarEquipe;
    private RecyclerView rvMetas;

    private boolean isDaily = false;
    private int selectedIntensity = 0;
    private final List<String> selectedDays = new ArrayList<>();
    private final List<Meta> metasList = new ArrayList<>();
    private MetasAdapter metasAdapter;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_equipe);

        editNomeToca = findViewById(R.id.editNomeToca);
        editTextMeta = findViewById(R.id.editTextMeta);
        weekDaysChipGroup = findViewById(R.id.week_days_chip_group);
        dailyCheck = findViewById(R.id.daily);
        intensityThree = findViewById(R.id.intesity_three);
        intensityTwo = findViewById(R.id.intesity_two);
        intensityOne = findViewById(R.id.intesity_one);
        btnAdicionarMeta = findViewById(R.id.btnCadastrarMeta);
        btnCadastrarEquipe = findViewById(R.id.btnCadastrarEquipe);
        rvMetas = findViewById(R.id.rvMetas);

        metasAdapter = new MetasAdapter(metasList);
        rvMetas.setLayoutManager(new LinearLayoutManager(this));
        rvMetas.setAdapter(metasAdapter);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        setupChipGroup();
        setupIntensitySelection();
        setupDailyToggle();
        setupAdicionarMetaButton();
        setupCadastrarEquipeButton();
    }

    private void setupChipGroup() {
        weekDaysChipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            selectedDays.clear();
            for (int id : checkedIds) {
                Chip chip = group.findViewById(id);
                if (chip != null) {
                    selectedDays.add(chip.getTag().toString());
                    chip.setChipBackgroundColorResource(R.color.selected_chip_color);
                }
            }

            for (int i = 0; i < group.getChildCount(); i++) {
                Chip chip = (Chip) group.getChildAt(i);
                if (!checkedIds.contains(chip.getId())) {
                    chip.setChipBackgroundColorResource(R.color.unselected_chip_color);
                }
            }
        });
    }

    private void setupDailyToggle() {
        dailyCheck.setOnClickListener(v -> {
            isDaily = !isDaily;
            dailyCheck.setImageResource(isDaily ? R.drawable.checkmarkstilled : R.drawable.checkmarkempty);

            if (isDaily) {
                selectedDays.clear();
                for (int i = 0; i < weekDaysChipGroup.getChildCount(); i++) {
                    Chip chip = (Chip) weekDaysChipGroup.getChildAt(i);
                    chip.setChecked(true);
                    selectedDays.add(chip.getTag().toString());
                }
            }
        });
    }

    private void setupIntensitySelection() {
        View.OnClickListener intensityListener = v -> {
            intensityOne.setImageResource(R.drawable.checkmarkempty);
            intensityTwo.setImageResource(R.drawable.checkmarkempty);
            intensityThree.setImageResource(R.drawable.checkmarkempty);

            if (v == intensityOne) {
                selectedIntensity = 1;
                intensityOne.setImageResource(R.drawable.checkmarkstilled);
            } else if (v == intensityTwo) {
                selectedIntensity = 2;
                intensityTwo.setImageResource(R.drawable.checkmarkstilled);
            } else if (v == intensityThree) {
                selectedIntensity = 3;
                intensityThree.setImageResource(R.drawable.checkmarkstilled);
            }
        };

        intensityOne.setOnClickListener(intensityListener);
        intensityTwo.setOnClickListener(intensityListener);
        intensityThree.setOnClickListener(intensityListener);
    }

    private void setupAdicionarMetaButton() {
        btnAdicionarMeta.setOnClickListener(v -> {
            String tituloMeta = editTextMeta.getText().toString().trim();

            if (tituloMeta.isEmpty() || selectedDays.isEmpty() || selectedIntensity == 0) {
                Toast.makeText(this, "Preencha todos os campos da meta!", Toast.LENGTH_SHORT).show();
                return;
            }

            Meta novaMeta = new Meta(
                    tituloMeta,
                    isDaily ? getWeekDaysList() : selectedDays,
                    selectedIntensity
            );

            metasList.add(novaMeta);
            metasAdapter.notifyDataSetChanged();

            editTextMeta.setText("");
            weekDaysChipGroup.clearCheck();
            selectedDays.clear();
            selectedIntensity = 0;
            intensityOne.setImageResource(R.drawable.checkmarkempty);
            intensityTwo.setImageResource(R.drawable.checkmarkempty);
            intensityThree.setImageResource(R.drawable.checkmarkempty);
            isDaily = false;
            dailyCheck.setImageResource(R.drawable.checkmarkempty);
        });
    }

    private void setupCadastrarEquipeButton() {
        btnCadastrarEquipe.setOnClickListener(v -> {
            String nomeToca = editNomeToca.getText().toString().trim();
            if (nomeToca.isEmpty()) {
                Toast.makeText(this, "Digite um nome para a toca!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (metasList.isEmpty()) {
                Toast.makeText(this, "Adicione pelo menos uma meta!", Toast.LENGTH_SHORT).show();
                return;
            }

            String code = gerarCodigoEquipe();
            verificarCodigoUnico(code, new CodigoUnicoCallback() {
                @Override
                public void onResult(boolean isUnico) {
                    if (isUnico) {
                        criarEquipeNoFirestore(nomeToca, code);
                    } else {
                        setupCadastrarEquipeButton();
                    }
                }
            });
        });
    }

    private void criarEquipeNoFirestore(String nomeToca, String code) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Criando equipe...");
        progress.setCancelable(false);
        progress.show();

        Map<String, Object> equipe = new HashMap<>();
        equipe.put("name", nomeToca);
        equipe.put("owner", user.getUid());
        equipe.put("createdAt", FieldValue.serverTimestamp());
        equipe.put("codigo", code);

        db.collection("teams")
                .add(equipe)
                .addOnSuccessListener(documentReference -> {
                    String teamId = documentReference.getId();

                    adicionarMembro(teamId, user.getUid())
                            .continueWithTask(task -> {
                                if (task.isSuccessful()) {
                                    return atualizarTimeDoUsuario(code);
                                } else {
                                    throw task.getException();
                                }
                            })
                            .continueWithTask(task -> {
                                if (task.isSuccessful() && !metasList.isEmpty()) {
                                    return adicionarMetas(teamId);
                                } else if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                return Tasks.forResult(null);
                            })
                            .addOnCompleteListener(task -> {
                                progress.dismiss();

                                if (task.isSuccessful()) {
                                    Toast.makeText(this, "Equipe criada com sucesso!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(this, "Erro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.e("nina123", "Erro completo", task.getException());
                                }
                            });
                })
                .addOnFailureListener(e -> {
                    progress.dismiss();
                    Toast.makeText(this, "Erro ao criar equipe: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("nina123", "Erro ao criar equipe", e);
                });
    }

    private String gerarCodigoEquipe() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * caracteres.length());
            codigo.append(caracteres.charAt(index));
        }

        return codigo.toString();
    }

    private List<String> getWeekDaysList() {
        return Arrays.asList("sun", "mon", "tue", "wed", "thu", "fri", "sat");
    }

    private Task<Void> adicionarMembro(String teamId, String userId) {
        DocumentReference userRef = db.collection("users").document(userId);

        return userRef.get().continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }

            DocumentSnapshot userDoc = task.getResult();
            if (!userDoc.exists()) {
                throw new Exception("Usuário não encontrado");
            }

            Map<String, Object> member = new HashMap<>();
            member.put("userId", userId);
            member.put("joinedAt", FieldValue.serverTimestamp());
            member.put("username", userDoc.getString("nome"));
            member.put("pontos", 0);
            member.put("avatarIndex", userDoc.getLong("avatarIndex"));

            return db.collection("teams").document(teamId)
                    .collection("members").document(userId)
                    .set(member);
        });
    }

    private Task<Void> atualizarTimeDoUsuario(String teamCode) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return Tasks.forException(new NullPointerException("Usuário não autenticado"));
        }

        return db.collection("users").document(user.getUid())
                .update("currentTeam", teamCode);
    }

    private Task<Void> adicionarMetas(String teamId) {
        List<Map<String, Object>> metasFirestore = new ArrayList<>();

        for (Meta meta : metasList) {
            Map<String, Object> metaMap = new HashMap<>();
            metaMap.put("title", meta.getTitulo());
            metaMap.put("days", meta.getDias());
            metaMap.put("intensity", meta.getIntensidade());
            metaMap.put("createdAt", new Date().getTime());

            metasFirestore.add(metaMap);
        }

        return db.collection("teams").document(teamId)
                .update("metas", metasFirestore);
    }

    private interface CodigoUnicoCallback {
        void onResult(boolean isUnico);
    }

    private void verificarCodigoUnico(String code, CodigoUnicoCallback callback) {
        db.collection("teams")
                .whereEqualTo("codigo", code)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onResult(task.getResult().isEmpty());
                    } else {
                        callback.onResult(false);
                    }
                });
    }
}
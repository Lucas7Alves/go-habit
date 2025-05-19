package com.souunit.gohabit.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.souunit.gohabit.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditarMeta extends AppCompatActivity {

    private EditText editTextMeta;
    private ChipGroup weekDaysChipGroup;
    private ImageView dailyCheck, intensityThree, intensityTwo, intensityOne;
    private Button saveButton;
    private ImageButton buttonBack;
    private String metaId;

    private boolean isDaily = false;
    private int selectedIntensity = 0; // 0=nada, 1=verde, 2=amarelo, 3=vermelho
    private final List<String> selectedDays = new ArrayList<>();

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_meta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.edit_meta), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        metaId = getIntent().getStringExtra("metaId");


        if (metaId == null || metaId.isEmpty()) {
            Toast.makeText(this, "Erro: ID da meta não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        editTextMeta = findViewById(R.id.editTextMeta);
        weekDaysChipGroup = findViewById(R.id.week_days_chip_group);
        dailyCheck = findViewById(R.id.daily);
        intensityThree = findViewById(R.id.intesity_three);
        intensityTwo = findViewById(R.id.intesity_two);
        intensityOne = findViewById(R.id.intesity_one);
        saveButton = findViewById(R.id.buttonLogin);
        buttonBack = findViewById(R.id.button_back);

        buttonBack.setOnClickListener(v -> finish());

        setupChipGroup();
        setupIntensitySelection();
        setupDailyToggle();
        setupSaveButton();
        loadDataGoalFromFirestore();
    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    private void setupChipGroup() {
        weekDaysChipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {
                selectedDays.clear();
                for (int id : checkedIds) {
                    Chip chip = group.findViewById(id);
                    if (chip != null) {
                        selectedDays.add(chip.getTag().toString());
                        chip.setChipBackgroundColorResource(R.color.selected_chip_color);
                    }
                }

                // Atualiza chips não selecionados
                for (int i = 0; i < group.getChildCount(); i++) {
                    Chip chip = (Chip) group.getChildAt(i);
                    if (!checkedIds.contains(chip.getId())) {
                        chip.setChipBackgroundColorResource(R.color.unselected_chip_color);
                    }
                }
            }
        });
    }

    private void setupDailyToggle() {
        dailyCheck.setOnClickListener(v -> {
            isDaily = !isDaily;
            dailyCheck.setImageResource(isDaily ? R.drawable.checkmarkstilled : R.drawable.checkmarkempty);

            if (isDaily) {
                // Seleciona todos os dias
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

    private void setupSaveButton() {
        saveButton.setOnClickListener(v -> updateGoalFromFirestore(metaId));
    }

    private void updateGoalFromFirestore(String documentUid) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show();
            return;
        }

        String title = editTextMeta.getText().toString().trim();

        // Validações (mantidas do código anterior)
        if (title.isEmpty() || selectedDays.isEmpty() || selectedIntensity == 0) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cria o objeto da meta
        Map<String, Object> goal = new HashMap<>();
        goal.put("title", title);
        goal.put("days", isDaily ? getWeekDaysList() : selectedDays);
        goal.put("intensity", selectedIntensity);

        // Referência aninhada ao usuário
        db.collection("users")
                .document(user.getUid())
                .collection("goals")
                .document(documentUid)
                .update(goal)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Meta salva com sucesso!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Erro: " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private List<String> getWeekDaysList() {
        return Arrays.asList("sun", "mon", "tue", "wed", "thu", "fri", "sat");
    }

    private void loadDataGoalFromFirestore() {
        DocumentReference metaRef = db.collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("goals")
                .document(metaId);

        metaRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String title = document.getString("title");
                    List<String> selectedDays = (List<String>) document.get("days");
                    int intensity = document.getLong("intensity").intValue();
                    Map<String, Object> daysOfWeek = new HashMap<>();

                    daysOfWeek.put("sun", R.id.chip_sun);
                    daysOfWeek.put("mon", R.id.chip_mon);
                    daysOfWeek.put("tue", R.id.chip_tue);  
                    daysOfWeek.put("wed", R.id.chip_wed);
                    daysOfWeek.put("thu", R.id.chip_thu); 
                    daysOfWeek.put("fri", R.id.chip_fri);
                    daysOfWeek.put("sat", R.id.chip_sat);

                    for (int i = 0; i < weekDaysChipGroup.getChildCount(); i++) {
                        Chip chip = (Chip) weekDaysChipGroup.getChildAt(i);
                        String chipTag = chip.getTag().toString();

                        if (selectedDays != null && selectedDays.contains(chipTag)) {
                            chip.setChecked(true);
                        } else {
                            chip.setChecked(false);
                        }
                    }
                    editTextMeta.setText(title);
                    if (intensity == 1) {
                        selectedIntensity = 1;
                        intensityOne.setImageResource(R.drawable.checkmarkstilled);
                    } else if (intensity == 2) {
                        selectedIntensity = 2;
                        intensityTwo.setImageResource(R.drawable.checkmarkstilled);
                    } else if (intensity == 3) {
                        selectedIntensity = 3;
                        intensityThree.setImageResource(R.drawable.checkmarkstilled);
                    }


                }
            } else {
                Toast.makeText(this, "Erro: " + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
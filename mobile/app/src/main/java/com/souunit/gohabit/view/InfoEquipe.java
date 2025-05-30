package com.souunit.gohabit.view;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.souunit.gohabit.R;

public class InfoEquipe extends AppCompatActivity {

    private LinearLayout layoutMetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_equipe);

        layoutMetas = findViewById(R.id.layout_metas); // Verifique se existe esse ID no XML

        // Metas de exemplo
        if (layoutMetas != null) {
            adicionarMeta("Meditar por 20 min", true);
            adicionarMeta("Caminhar por 2km", true);
            adicionarMeta("Dormir 8 horas", false);
            adicionarMeta("Tomar 10 minutos de sol", false);
        }

        // Navegação inferior - adaptado para ImageButtons personalizados
        ImageButton btnAdd = findViewById(R.id.btn_add);
        ImageButton btnTeam = findViewById(R.id.btn_team);
        ImageButton btnProfile = findViewById(R.id.btn_profile);

        if (btnAdd != null) {
            btnAdd.setOnClickListener(v -> {
                // Abrir tela de nova meta
                // startActivity(new Intent(this, NovaMetaActivity.class));
            });
        }

        if (btnTeam != null) {
            btnTeam.setOnClickListener(v -> {
                // Já está na InfoEquipe, talvez ignorar ou dar feedback
            });
        }

        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                // Ir para tela de perfil
                // startActivity(new Intent(this, PerfilActivity.class));
            });
        }
    }

    private void adicionarMeta(String texto, boolean concluida) {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(texto);
        checkBox.setChecked(concluida);
        checkBox.setEnabled(false);
        checkBox.setTextColor(getResources().getColor(R.color.white));
        checkBox.setBackgroundResource(concluida ? R.drawable.bg_meta_check : R.drawable.bg_meta_uncheck);
        checkBox.setButtonDrawable(R.drawable.checkbox_drawable); // ou android:button="@null"
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 16, 0, 16); // espaço maior para melhor visual
        checkBox.setLayoutParams(params);
        layoutMetas.addView(checkBox);
    }
}

package com.souunit.gohabit.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.souunit.gohabit.FormLogin;
import com.souunit.gohabit.R;

public class FormCadastro extends AppCompatActivity {

    Button btnCadastro;
    EditText editTextEmail, editTextPassword, editTextRepeatPassword;

    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout_register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        try {
            FirebaseApp.initializeApp(this);
            Log.d("FIREBASE_INIT", "Firebase inicializado: " + FirebaseAuth.getInstance().getApp().getName());
        } catch (Exception e) {
            Log.e("FIREBASE_INIT", "Erro ao inicializar Firebase", e);
            Snackbar.make(findViewById(R.id.layout_register), "Falha ao conectar com o servidor", Snackbar.LENGTH_LONG).show();
            return; // Impede continuar se o Firebase não inicializar
        }

        btnCadastro = findViewById(R.id.buttonConfirm);
        iniciarComponentes();
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cadastrarUsuario();
            }
        });

        backButton = findViewById(R.id.button_back);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormCadastro.this, FormLogin.class);
                startActivity(intent);
            }
        });

    }

    private void iniciarComponentes() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword);
    }

    private void cadastrarUsuario() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repeatPassword = editTextRepeatPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || repeatPassword.isEmpty()) {
            Snackbar.make(findViewById(R.id.layout_register), "Preencha todos os campos!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Snackbar.make(findViewById(R.id.layout_register), "E-mail inválido!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repeatPassword)) {
            Snackbar.make(findViewById(R.id.layout_register), "As senhas não coincidem!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Snackbar.make(findViewById(R.id.layout_register), "kd o fire", Snackbar.LENGTH_SHORT).show();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Snackbar snackbar;

                if (task.isSuccessful()) {

                    snackbar = Snackbar.make(findViewById(R.id.layout_register), "Cadastro efetuado com sucesso!", Snackbar.LENGTH_SHORT);

                    Intent intent = new Intent(FormCadastro.this, FormCadPerfil.class);
                    startActivity(intent);

                    snackbar.show();
                } else {
                    String erro;
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Senha deve conter no mínimo 6 caracteres.";

                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "Email já cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Email inválido";

                    } catch (Exception e) {
                        erro = "Erro ao cadastrar usuário";
                    }

                    snackbar = Snackbar.make(findViewById(R.id.layout_register), erro, Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }
            }
        }).addOnFailureListener(e -> {
            Log.e("FIREBASE_AUTH", "Erro Firebase", e);
        });
        ;
    }
}
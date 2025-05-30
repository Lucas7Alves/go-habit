package com.souunit.gohabit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.souunit.gohabit.view.FormCadastro;
import com.souunit.gohabit.view.PrincipalSolo;

public class FormLogin extends AppCompatActivity {

    Button btnLogin;
    TextView forgotPassword, textCadastro;
    EditText editTextEmail, editTextPassword;
    FirebaseAuth mAuth;

    //TODO: FAZER FUNCIONAR O CURRENT USER

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FirebaseAuth.getInstance().addAuthStateListener(firebaseAuth -> {
            Log.d("AUTH_STATE", "User: " + (firebaseAuth.getCurrentUser() != null ? "LOGGED" : "NULL"));
        });

        setContentView(R.layout.activity_form_login);

        iniciarComponentes();
        mAuth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String senha = editTextPassword.getText().toString();

            if (email.isEmpty() || senha.isEmpty()) {
                Snackbar.make(findViewById(R.id.main), "Preencha todos os campos!", Snackbar.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(FormLogin.this, PrincipalSolo.class);
                    startActivity(intent);
                    finish();
                } else {
                    String erro = "Erro ao fazer login.";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        erro = "Usuário não encontrado.";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "Usuário ou senha inválido.";
                    } catch (Exception e) {
                        erro = "Erro ao fazer login.";
                    }

                    Snackbar.make(findViewById(R.id.main), erro, Snackbar.LENGTH_SHORT).show();
                }
            });
        });

        textCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(FormLogin.this, FormCadastro.class);
            startActivity(intent);
        });

        forgotPassword.setOnClickListener(v -> {
            //TODO: ir para tela rec cadastro
            Intent intent = new Intent(FormLogin.this, FormCadastro.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(this, PrincipalSolo.class));
            finish();
        }
    }

    private void iniciarComponentes() {
        btnLogin = findViewById(R.id.buttonLogin);
        forgotPassword = findViewById(R.id.forgot_password);
        textCadastro = findViewById(R.id.register_user);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
    }
}
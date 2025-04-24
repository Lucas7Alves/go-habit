package com.souunit.gohabit.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.souunit.gohabit.R;
import com.souunit.gohabit.model.User;

public class FormCadastro extends AppCompatActivity {

    Button btnCadastro;
    EditText editTextEmail, editTextPassword, editTextRepeatPassword;

    User user = new User();

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

        btnCadastro = findViewById(R.id.buttonConfirm);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarComponentes();
                Intent intent = new Intent(FormCadastro.this, FormCadPerfil.class);
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
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String repeatPassword = editTextRepeatPassword.getText().toString();


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
        });
    }
}
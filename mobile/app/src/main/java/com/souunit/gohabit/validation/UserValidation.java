package com.souunit.gohabit.validation;

import com.souunit.gohabit.exception.PasswordException;

public class UserValidation {

    public static void passwordValidate(String password) {
        if (password.length() < 8) {
            throw new PasswordException("A senha deve conter no mínimo 8 caracteres.");
        }
    }

    public static void fieldValidate(String email, String password, String reapetPassword) {
        if (email.isEmpty() || password.isEmpty() || reapetPassword.isEmpty()) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
        }
    }

    public static void repeatPasswordValidate(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("As senhas devem ser iguais.");
        }
    }
}

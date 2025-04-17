package com.souunit.gohabit.validation;

import com.souunit.gohabit.exception.PasswordException;

public class UserValidation {

    public static void passwordValidation(String password) {
        if (password.length() < 8) {
            throw new PasswordException("A senha deve conter no mínimo 8 caracteres.");
        }
    }
}

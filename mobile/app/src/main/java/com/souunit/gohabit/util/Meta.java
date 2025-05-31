package com.souunit.gohabit.model;

import java.util.List;

public class Meta {
    private String titulo;
    private List<String> dias;
    private int intensidade;

    public Meta(String titulo, List<String> dias, int intensidade) {
        this.titulo = titulo;
        this.dias = dias;
        this.intensidade = intensidade;
    }

    // Getters
    public String getTitulo() {
        return titulo;
    }

    public List<String> getDias() {
        return dias;
    }

    public int getIntensidade() {
        return intensidade;
    }
}
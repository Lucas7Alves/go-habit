package com.souunit.gohabit.model;

public class User {

    private String idUser;
    private String nome;
    private String email;
    private String password;
    private String descricao;
    private String imgUrl;
    //was double
    private Integer pontuacao;

    // Required default constructor for Firebase
    public User() {
    }

    public User(String idUser, String nome, String email, String password, String descricao, String imgUrl, Integer pontuacao) {
        this.idUser = idUser;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.descricao = descricao;
    }


    public String getIdUser() {
        return idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }
}

package com.souunit.gohabit.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {

    private String idUser;
    private String nome;
    private String email;
    private String password;
    private String descricao;
    private String imgUrl;
    //was double
    private Integer pontuacao;

    // default constructor for Firebase
    public User() {
    }

    public User(String idUser, String nome, String email, String password, String descricao, String imgUrl, Integer pontuacao) {
        this.idUser = idUser;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.descricao = descricao;
    }

    // for Parcelable

    protected User(Parcel in) {
        idUser = in.readString();
        nome = in.readString();
        email = in.readString();
        password = in.readString();
        descricao = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {

    }


    //getters and setters
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

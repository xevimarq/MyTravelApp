package com.example.mytravelapp;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String email, password, name, cognom;
    boolean hasProfilePhoto;
    private ArrayList<String> identificadorViatje;

    public User(){

    }
    public User(String email, String password, String name, String cognom) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.cognom = cognom;
        this.hasProfilePhoto = false;
        identificadorViatje = new ArrayList<>();
    }

    //el id sera de formato (8 digitos hexadecimales),

    public boolean gethasProfilePhoto() {
        return hasProfilePhoto;
    }
    public void setHasProfilePhoto() {
        hasProfilePhoto = true;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCognom() {
        return cognom;
    }

    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    public boolean checkViatje(String nom){
        if(identificadorViatje.contains(getEmail()+"."+nom)){
            return false;
        }
        return true;
    }

    public ArrayList<String> getIdentificadorViatje() {
        return identificadorViatje;
    }

    public void addViatje(String nom, String inici, String fin){
        String idViatje = nom;
        identificadorViatje.add(idViatje);

    }
    public void setIdentificadorViatje(ArrayList<String> identificadorViatje) {
        this.identificadorViatje = identificadorViatje;
    }
}

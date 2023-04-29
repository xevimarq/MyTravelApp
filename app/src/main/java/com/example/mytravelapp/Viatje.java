package com.example.mytravelapp;

import android.graphics.Bitmap;
import android.net.Uri;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

public class Viatje implements Serializable {

    Viatje(){
    }

    String idViatje, nom;
    String iniciViatje, fiViatje;
    String administrador;
    ArrayList<String> editors;
    ArrayList<Boolean> publicParts;

    Viatje(String nom, String inici, String fi, String email){
        this.nom = nom;
        idViatje=email+"."+nom;
        iniciViatje = inici;
        fiViatje = fi;
        administrador = email;
        editors = new ArrayList<>();
        publicParts = new ArrayList<>();
        //las 4 partes que pueden ser publicas

        publicParts.add(false);
        publicParts.add(false);
        publicParts.add(false);
        publicParts.add(false);

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdViatje() {
        return idViatje;
    }

    public void setIdViatje(String idViatje) {
        this.idViatje = idViatje;
    }

    public String getIniciViatje() {
        return iniciViatje;
    }

    public void setIniciViatje(String iniciViatje) {
        this.iniciViatje = iniciViatje;
    }

    public String getFiViatje() {
        return fiViatje;
    }

    public void setFiViatje(String fiViatje) {
        this.fiViatje = fiViatje;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }

    public ArrayList<String> getEditors() {
        return editors;
    }

    public void setEditors(ArrayList<String> editors) {
        this.editors = editors;
    }

    public ArrayList<Boolean> getPublicParts() {
        return publicParts;
    }

    public void setPublicParts(ArrayList<Boolean> publicParts) {
        this.publicParts = publicParts;
    }
}

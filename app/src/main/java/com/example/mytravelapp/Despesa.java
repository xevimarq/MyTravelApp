package com.example.mytravelapp;

import java.io.Serializable;
import java.util.HashMap;

public class Despesa implements Serializable {

    HashMap<String, Float> mapaDespeses;
    String nomDespesa;

    Despesa(){

    }

    public Despesa(String nom) {
        nomDespesa = nom;
        this.mapaDespeses = new HashMap<>();
    }

    public String getNomDespesa() {
        return nomDespesa;
    }

    public void setNomDespesa(String nomDespesa) {
        this.nomDespesa = nomDespesa;
    }

    public HashMap<String, Float> getMapaDespeses() {
        return mapaDespeses;
    }

    public void setMapaDespeses(HashMap<String, Float> mapaDespeses) {
        this.mapaDespeses = mapaDespeses;
    }


    public void addIndividualDespesa(String nomUsuari, float preu){
        if(mapaDespeses.containsKey(nomUsuari)){
            mapaDespeses.put(nomUsuari, (preu+mapaDespeses.get(nomUsuari)));
        }
        else {
            mapaDespeses.put(nomUsuari, preu);
        }
    }

    public float getIndividualPreu(String nomUsuari){
        if(!mapaDespeses.containsKey(nomUsuari)){
            return 0;
        }
        return mapaDespeses.get(nomUsuari);
    }

}

package com.example.mytravelapp;

import java.io.Serializable;
import java.util.HashMap;

public class Despesa implements Serializable {

    String nomDespesa;
    String nomUsuari;
    Double preu;

    Despesa(){

    }

    public Despesa(String nomDespesa, String nomUsuari, Double preu) {
        this.nomDespesa = nomDespesa;
        this.nomUsuari = nomUsuari;
        this.preu = preu;
    }

    public String getNomDespesa() {
        return nomDespesa;
    }

    public void setNomDespesa(String nomDespesa) {
        this.nomDespesa = nomDespesa;
    }

    public String getNomUsuari() {
        return nomUsuari;
    }

    public void setNomUsuari(String nomUsuari) {
        this.nomUsuari = nomUsuari;
    }

    public Double getPreu() {
        return preu;
    }

    public void setPreu(Double preu) {
        this.preu = preu;
    }

    /*

    codi per si les despeses son multipersonals

    public void setMapaDespeses(HashMap<String, Float> mapaDespeses) {
        this.mapaDespeses = mapaDespeses;
    }

    public HashMap<String, Float> getMapaDespeses() {
        return mapaDespeses;
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
    */

}

package com.example.mytravelapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Controller {

    private static Controller INSTANCE;

    private FirebaseFirestore db;

    private ArrayList<String> idViatjesPublics;

    private Users users;
    private gestorViatjes viatjes;
    User loggedUser;
    //a este metodo se accede desde el users
    public void setLoggedUser(User user){
        loggedUser = user;
    }

    private Controller(){
        db = FirebaseFirestore.getInstance();
        users = new Users(db);
        viatjes = new gestorViatjes(db);
    };

    public static Controller getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Controller();
        }
        return INSTANCE;
    }


    //con estos metodos podemos hacer los recyclerViews para que puedas escrollear por los diferentes viajes.
    //deberia hacer 2 mas y entonces que gestor viajes una vez lea datos los ordene en 3 listas y pasarle a cada adapter una lista.
    //mirar como funciona el onclick (lo mas facil sera mirar el nombre
    RecyclerView rv;
    RecyclerViewAdapter rva;
    public void setRecyclerView(RecyclerView recyclerView, Context con){
        rv = recyclerView;
        rva = new RecyclerViewAdapter(viatjes.getLlistat(), con);

    }
    void setAdapter(){
        rva.setDades(viatjes.getLlistat());
        rv.setAdapter(rva);
    }





    //IAMTHESTORM THAT IS APROACHING
    void addUser(String[] dades) throws Exception {
        try {
            users.addUser(dades);
        }
        catch (Exception e){
            Log.w("a", "catched");
            throw e;
        }
    }

    void login(String email){
        users.login(email);
    }

    String getName(){
        return loggedUser.getName();
    }

    String getSurname(){
        return  loggedUser.getCognom();
    }

    String getMail(){return loggedUser.getEmail();}


    //hay que hacer que haga algun tipo de wait para que tanto el adapter como el cambio de actividad se haga a la vez
    boolean addViatje(String nomViatje, String inici, String fin){
        if(!loggedUser.checkViatje(nomViatje)){
            return false;
        }
        loggedUser.addViatje(loggedUser.getEmail()+"."+nomViatje, inici, fin);
        users.updateUser(loggedUser);
        viatjes.updateViatje(new Viatje(nomViatje, inici,fin, loggedUser.getEmail()));
        return true;
    }

    public void afegirFoto(String nom, Uri path, int select){
        viatjes.afegirFoto(loggedUser.getEmail(),nom, path,select);
    }

    public ArrayList<String> getIdViatjesPublics() {
        return idViatjesPublics;
    }

    public void setIdViatjesPublics(ArrayList<String> idViatjesPublics) {
        this.idViatjesPublics = idViatjesPublics;
    }
}
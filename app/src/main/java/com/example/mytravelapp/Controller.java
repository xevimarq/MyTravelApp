package com.example.mytravelapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Controller {

    private static Controller INSTANCE;
    private gestorDespeses gestorDespeses;

    private FirebaseFirestore db;

    private ArrayList<String> idViatjesPublics;

    //per a controlar els metodes asincrons
    public boolean viatjesCarregats = false;

    private Users users;
    private gestorViatjes viatjes;
    private Imatges imatges;

    private boolean compartits = true;
    private int stateViatjes = 0;

    private int viatjeActual;
    User loggedUser;
    galeriaActivity imatge;
    //a este metodo se accede desde el users
    public void setLoggedUser(User user){
        loggedUser = user;
    }
    public User getLoggedUser(){
        return loggedUser;
    }

    private Controller(){
        db = FirebaseFirestore.getInstance();
        users = new Users(db);
        viatjes = new gestorViatjes(db);
        imatges = new Imatges(db);
    };

    public static Controller getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Controller();
        }
        return INSTANCE;
    }

    public void loadViatjes(){
        viatjes.clearViatjes();
        ArrayList<String> _viatjes= loggedUser.getIdentificadorViatje();
        viatjes.carregar(_viatjes);

    }

    public void updateViatje(Viatje viatje){
        viatjes.updateViatje(viatje, false);
    }

    public Viatje getViatjeActual(){
        return viatjes.getLlistat().get(viatjeActual);
    }

    public void uploadImatges(ArrayList<Uri> listaImagenes){
        Viatje viatjeSeleccionado = getViatjeActual();
        String idViatje = viatjeSeleccionado.getIdViatje();
        Uri u = listaImagenes.get(listaImagenes.size()-1);
        afegirFoto(viatjeSeleccionado.getAdministrador(), viatjeSeleccionado.getNom(),u, 1);
        viatjeSeleccionado.addIdfoto(u.getLastPathSegment());
        viatjes.updateViatje(viatjeSeleccionado, false);
        /*for (Uri u: listaImagenes){
            afegirFoto(viatjeSeleccionado.getAdministrador(), viatjeSeleccionado.getNom(),u, 1);
            viatjeSeleccionado.addIdfoto(u.getLastPathSegment());
            viatjes.updateViatje(viatjeSeleccionado, false);
        }*/
    }
    //con estos metodos podemos hacer los recyclerViews para que puedas escrollear por los diferentes viajes.
    //deberia hacer 2 mas y entonces que gestor viajes una vez lea datos los ordene en 3 listas y pasarle a cada adapter una lista.
    //mirar como funciona el onclick (lo mas facil sera mirar el nombre
    RecyclerView rv;
    RecyclerViewAdapter rva;

    public void setCompartits(boolean compartits) {
        this.compartits = compartits;
    }

    public void setStateViatjes(int stateViatjes) {
        this.stateViatjes = stateViatjes;
        Log.w("aaaaaaww", "uwu");
    }

    public void setRecyclerView(RecyclerView recyclerView, Context con){
        rv = recyclerView;
        rva = new RecyclerViewAdapter(viatjes.getLlistat(), con);

    }
    void setAdapter(){

        ArrayList<Viatje> temp = new ArrayList<>();
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        //todo
        //arreglar
        if(stateViatjes != 0 || !compartits) {
            for (Viatje v : viatjes.getLlistat()){
                if(compartits || v.getAdministrador().equals(loggedUser.getEmail())){
                    Date inici;
                    Date fi;
                    try {
                        inici = df.parse(v.getIniciViatje());
                        fi = df.parse(v.getFiViatje());
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    switch (stateViatjes){
                        case 0:
                            temp.add(v);
                            break;
                        case 1:
                            if(date.after(inici) && date.before(fi)){
                                temp.add(v);
                            }
                            break;
                        case 2:
                            if(date.after(fi)){
                                temp.add(v);
                            }
                            break;
                        case 3:
                            if(date.before(inici)){
                                temp.add(v);
                            }

                            break;
                    }
                }
            }
        }else{
            temp = viatjes.getLlistat();
        }
        rva.setDades(temp);
        rv.setAdapter(rva);
    }
    public String getTripName(int index) {
        Viatje viatje = viatjes.getLlistat().get(index);
        return viatje.getNom();
    }

    public ArrayList<String> getTripDate(){
        ArrayList<Viatje> lista = viatjes.getLlistat();
        ArrayList<String> devolucion = new ArrayList<>();
        for(Viatje v:lista){
            devolucion.add(v.getIniciViatje());
        }
        return devolucion;
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

    void addUser(User user){
        users.addUser(user);
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
    boolean addViatje(String nomViatje, String inici, String fin, Boolean hasPortada){
        if(!loggedUser.checkViatje(nomViatje)){
            return false;
        }
        loggedUser.addViatje(loggedUser.getEmail()+"."+nomViatje, inici, fin);
        users.updateUser(loggedUser);
        viatjes.updateViatje(new Viatje(nomViatje, inici,fin, loggedUser.getEmail(), hasPortada), true);
        setAdapter();
        return true;
    }

    public void afegirFoto(String email,String nom, Uri path, int select){
        viatjes.afegirFoto(email,nom, path,select);
    }

    public ArrayList<String> getIdViatjesPublics() {
        return idViatjesPublics;
    }

    public void setIdViatjesPublics(ArrayList<String> idViatjesPublics) {
        this.idViatjesPublics = idViatjesPublics;
    }

    public void setViatjeActual(int viatje){
        viatjeActual = viatje;
        this.gestorDespeses = new gestorDespeses(getViatjeActual().idViatje);
    }

    public void recyclerViewLlistaDespeses(RecyclerView rv, Context con){
        this.gestorDespeses.updateRecycler(rv, con);
    }
    public void recyclerViewLlistaDespesesDesglosada(RecyclerView rv, Context con){
        this.gestorDespeses.updateRecycler2(rv, con);
    }

    public void addDespesa(String nomD, String nom, Double preu){
        this.gestorDespeses.addDespesa(nomD, nom, preu);
    }

}

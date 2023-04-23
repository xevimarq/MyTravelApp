package com.example.mytravelapp;

import android.util.Log;

public class Controller {

    private static Controller INSTANCE;

    private Users users;
    User loggedUser;
    //a este metodo se accede desde el users
    public void setLoggedUser(User user){
        loggedUser = user;
    }

    private Controller(){
        users = new Users();
    };

    public static Controller getInstance(){
        if(INSTANCE == null){
            INSTANCE = new Controller();
        }
        return INSTANCE;
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



}

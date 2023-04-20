package com.example.mytravelapp;

public class Controller {

    private static Controller INSTANCE;

    private Users users = new Users();
    User loggedUser;

    private Controller(){};

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
            throw e;
        }
    }

    void login(String email, String password) throws Exception {
        try {
            loggedUser = users.login(email,password);
        }
        catch (Exception e){
            throw e;
        }
    }

    String getName(){
        return loggedUser.getName();
    }

    String getSurname(){
        return  loggedUser.getName();
    }



}

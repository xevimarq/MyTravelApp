package com.example.mytravelapp;

import java.util.ArrayList;
import java.util.HashMap;

public class Users {

    HashMap<String,User> registeredUsers;

    Users(){
         registeredUsers = new HashMap<>();
    }

    public void addUser(String[] data) throws Exception{
        if(registeredUsers.containsKey(data[0])){
            throw new Exception("E-mail ja registrat");
        }
        User usuari = new User(data[0], data[1], data[2], data[3]);
        registeredUsers.put(data[0],usuari);
    }


    public User login(String email, String password) throws Exception{
        if(!registeredUsers.containsKey(email)){
            throw new Exception("Email no registrat");
        }
        if (!password.equals(registeredUsers.get(email).getPassword())){
            throw new Exception("Contrasenya incorrecte");
        }
        return registeredUsers.get(email);
    }

    /*

    por si usamos arraylist y no hashmap

    private boolean checkEmail(String email){
        for(User user: registeredUsers){
            if(user.getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }
    */

}

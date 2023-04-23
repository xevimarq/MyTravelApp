package com.example.mytravelapp;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Users {

    //guarda los datos, no los carga aun
    FirebaseFirestore db;
    Users(){
        db = FirebaseFirestore.getInstance();
    }



    public void addUser(String[] data) throws Exception{
        db.collection("users").document(data[0]).set(new User(data[0],data[1],data[2],data[3]));
    }


    public void login(String email){
        db.collection("users").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Controller cr = Controller.getInstance();
                User user = documentSnapshot.toObject(User.class);
                cr.setLoggedUser(user);
            }
        });
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

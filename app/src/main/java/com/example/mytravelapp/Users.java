package com.example.mytravelapp;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Users {

    //guarda los datos, no los carga aun
    FirebaseFirestore db;
    Users(FirebaseFirestore db_){
        db = db_;
    }



    public void updateUser(User user){
        db.collection("users").document(user.getEmail()).set(user);
    }


    public void addUser(String[] data) throws Exception{
        db.collection("users").document(data[0]).set(new User(data[0],data[1],data[2],data[3]));
    }

    public void addUser(User user){
        db.collection("users").document(user.getEmail()).set(user);
    }


    public void login(String email){
        db.collection("users").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Controller cr = Controller.getInstance();
                User user = documentSnapshot.toObject(User.class);
                cr.setLoggedUser(user);
                cr.loadViatjes();
            }
        });
    }

}

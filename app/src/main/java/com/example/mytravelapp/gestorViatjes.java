package com.example.mytravelapp;

import android.net.Uri;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class gestorViatjes {
    FirebaseFirestore db;
    Controller cr;
    FirebaseStorage st;

    ArrayList<Viatje> llistat;

    gestorViatjes(FirebaseFirestore db_){
        db = db_;
        st = FirebaseStorage.getInstance();
        llistat = new ArrayList<>();
    }

    public void updateViatje(Viatje viatje){
        db.collection("viatjes").document(viatje.getIdViatje()).set(viatje);
        llistat.add(viatje);
    }

    public ArrayList<Viatje> getLlistat() {
        return llistat;
    }

    public void afegirFoto(String email, String nom, Uri path, int select){
        Controller cr = Controller.getInstance();
        StorageReference ref;
        if(select == 0){
            ref = st.getReference().child("images/"+email+"/"+nom+"/portada");
        }
        else{
            ref = st.getReference().child("images/" + email + "/" + nom + "/Foto");
        }
        ref.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                cr.setAdapter();
            }
        });
    }

    public boolean addPersona(int viatje, String email){
        if(llistat.get(viatje).getEditors().contains(email)){
            return false;
        }
        return true;
    }
}

package com.example.mytravelapp;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
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

    public void carregar(ArrayList<String> ids){
        for(String id : ids){
            Log.w("uff","uff");
            db.collection("viatjes").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    llistat.add(documentSnapshot.toObject(Viatje.class));
                    Log.w("agregat","siuu");
                    if(ids.get(ids.size()-1).equals(id)){
                        cr = Controller.getInstance();
                        cr.setAdapter();
                    }
                }
            });
        }
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
        ref.putFile(path);
    }

    public boolean addPersona(int viatje, String email){
        if(llistat.get(viatje).getEditors().contains(email)){
            return false;
        }
        return true;
    }
}

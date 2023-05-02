package com.example.mytravelapp;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Imatges {
    FirebaseFirestore db;
    FirebaseStorage st;

    Imatges(FirebaseFirestore db_){
        db = db_;
        st = FirebaseStorage.getInstance();
    }

    public void addImage(String idViatje, ArrayList<Uri> listaImagenes) {
        for (Uri imagen : listaImagenes) {
            String nomImatge = imagen.getLastPathSegment();
            StorageReference ref = st.getReference().child("images/" + idViatje + "/" + nomImatge);
            UploadTask uploadTask = ref.putFile(imagen);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String url = task.getResult().toString();
                    //db.collection("viatjes").document(idViatje).update("identificadorImatge", FieldValue.arrayUnion(url));
                } else {
                    Log.e("Imatges", "Error al subir la imatge", task.getException());
                }
            });
        }
    }


    public void eliminarImatge(String idViatje, String urlImatge) {
        StorageReference ref = st.getReferenceFromUrl(urlImatge);
        ref.delete().addOnSuccessListener(aVoid -> {
            // Eliminar el identificador de la imagen de la lista del viaje en Firestore
            db.collection("viatjes").document(idViatje).update("identificadorImatge", FieldValue.arrayRemove(urlImatge));
        }).addOnFailureListener(exception -> {
            Log.e("Imatges", "Error al eliminar la imatge", exception);
        });
    }

}
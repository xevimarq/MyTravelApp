package com.example.mytravelapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class gestorDespeses {

    FirebaseFirestore fb;
    RecyclerView rv, rvDes;
    Context con, conDes;
    String docId;
    Map<String, Object> mapaDespeses;
    ArrayList<Despesa> despesas;
    LlistaDespesesNoDesglosadesAdapter adapter;
    LlistaDespesesDesglosadesAdapter adapter2;

    public gestorDespeses(String docId) {
        this.docId = docId;
        fb = FirebaseFirestore.getInstance();
        mapaDespeses = new HashMap<>();
        despesas = new ArrayList<>();
        fb.collection("viatjes").document(docId).collection("despeses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<DocumentSnapshot> docs = task.getResult().getDocuments();
                for(DocumentSnapshot doc : docs){
                    if(doc.getId().equals("info")){
                        mapaDespeses = doc.getData();
                    }
                    else {
                        despesas.add(doc.toObject(Despesa.class));
                    }
                }
            }
        });
        //mapaDespeses = fb.collection("viatjes").document(docId).collection("despeses").document("info").get().getResult().toObject(HashMap.class);
        /*
        fb.collection("viatjes").document(docId).collection("despeses").document("info").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()) {
                    mapaDespeses = task.getResult().getData();
                }
            }
        });
        */
    }

    public Map<String, Object> getMapaDespeses() {
        return mapaDespeses;
    }

    public void setMapaDespeses(Map<String, Object> mapaDespeses) {
        this.mapaDespeses = mapaDespeses;
    }

    public void addDespesa(String nomDespesa, String nom, double cost){
        if(mapaDespeses.containsKey(nom)){
            mapaDespeses.replace(nom, (Double) mapaDespeses.get(nom) + cost);
            Log.w("uwu",mapaDespeses.get(nom).toString());
        }
        else {
            mapaDespeses.put(nom,cost);
        }

        Despesa desp = new Despesa(nomDespesa, nom, cost);
        despesas.add(desp);
        fb.collection("viatjes").document(docId).collection("despeses").document("info").set(mapaDespeses);
        fb.collection("viatjes").document(docId).collection("despeses").document(nomDespesa).set(desp);
        updateRecycler(rv, con);
        if(rvDes != null){
            updateRecycler2(rvDes, conDes);
        }

    }

    public void updateRecycler(RecyclerView rv, Context con) {
        if(this.rv == null){
            this.rv = rv;
            this.con = con;
        }
        if (mapaDespeses != null) {
            if (adapter == null) {
                adapter = new LlistaDespesesNoDesglosadesAdapter(mapaDespeses, con);
            }
            adapter.setDades(mapaDespeses);
            rv.setAdapter(adapter);
        }
    }

    public void updateRecycler2(RecyclerView rv, Context con){
        if(this.rvDes == null){
            this.rvDes = rv;
            this.conDes = con;
        }
        if (despesas != null) {
            if (adapter2 == null) {
                adapter2 = new LlistaDespesesDesglosadesAdapter(despesas, conDes);
            }
            adapter2.setDades(despesas);
        }
        if(adapter2!=null){
            rvDes.setAdapter(adapter2);
        }
    }
}

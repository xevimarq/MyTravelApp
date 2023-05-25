package com.example.mytravelapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class LlistaDespesesNoDesglosadesAdapter extends RecyclerView.Adapter<LlistaDespesesNoDesglosadesAdapter.RecyclerViewHolder>{
    private Map<String, Object> dades;
    private Set<String> keys;
    private Controller cr;
    private Context mcontext;

    public LlistaDespesesNoDesglosadesAdapter(Map<String, Object> recyclerDataArrayList, Context mcontext) {
        cr = Controller.getInstance();
        this.dades = recyclerDataArrayList;
        this.keys = dades.keySet();
        this.mcontext = mcontext;
    }

    public void setDades(Map<String, Object> mapa){
        dades = mapa;
        this.keys = dades.keySet();
    }

    @NonNull
    @Override
    public LlistaDespesesNoDesglosadesAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(mcontext).inflate(R.layout.card_despesasimple, parent, false);
        return new LlistaDespesesNoDesglosadesAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LlistaDespesesNoDesglosadesAdapter.RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        /*
        try {
            File localFile = File.createTempFile(viatje.getNom()+"Portada",".jpeg");
            st.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    holder.foto.setImageBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */
        holder.nom.setText(keys.toArray()[position].toString());
        holder.data.setText(dades.get(keys.toArray()[position].toString()).toString());
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return dades.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView nom, data;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            //importante poner bien los id porfa
            nom = itemView.findViewById(R.id.nomUsuari);
            data = itemView.findViewById(R.id.costTotal);
        }

    }
}

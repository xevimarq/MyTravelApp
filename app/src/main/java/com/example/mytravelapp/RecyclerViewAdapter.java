package com.example.mytravelapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{
    private ArrayList<Viatje> dades;
    private Controller cr;
    private Context mcontext;

    public RecyclerViewAdapter(ArrayList<Viatje> recyclerDataArrayList, Context mcontext) {
        cr = Controller.getInstance();
        this.dades = recyclerDataArrayList;
        this.mcontext = mcontext;
    }

    public void setDades(ArrayList<Viatje> viatje){
        dades = viatje;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(mcontext).inflate(R.layout.card_viatge, parent, false);
        return new RecyclerViewAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        // Set the data to textview and imageview.
        Viatje viatje = dades.get(position);
        if(viatje.getHasPortada()) {
            StorageReference st = FirebaseStorage.getInstance().getReference("images/" + viatje.getAdministrador() + "/" + viatje.getNom() + "/portada");
            Glide.with(mcontext).load(st).centerCrop().into(holder.foto);
        }
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
        holder.nom.setText(viatje.getNom());
        holder.data.setText(viatje.getIniciViatje()+"-"+viatje.getFiViatje());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cambiarActivitat = new Intent(mcontext, viatjeActivity.class);
                cambiarActivitat.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cr.setViatjeActual(holder.getAdapterPosition());
                mcontext.startActivity(cambiarActivitat);
            }
        });
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return dades.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView nom, data;
        private ImageView foto;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            //importante poner bien los id porfa
            nom = itemView.findViewById(R.id.nomViatje);
            data = itemView.findViewById(R.id.dataViatje);
            foto = itemView.findViewById(R.id.fotoViatje);
        }
    }
}

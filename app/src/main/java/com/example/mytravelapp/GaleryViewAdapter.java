package com.example.mytravelapp;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class GaleryViewAdapter extends RecyclerView.Adapter<GaleryViewAdapter.ViewHolder> {

    private ArrayList<String> listaImagenes;
    private galeriaActivity ga;
    Context mcontext;
    Controller cr;

    public GaleryViewAdapter(ArrayList<String> listaImagenes, Context con, galeriaActivity g) {
        this.listaImagenes = listaImagenes;
        mcontext = con;
        cr = Controller.getInstance();
        ga = g;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_imatge, parent, false);
        return new ViewHolder(view);
    }
    public void onChanged() {
        // Do nothing
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StorageReference st = FirebaseStorage.getInstance().getReference("images/" + cr.getViatjeActual().getAdministrador() + "/" + cr.getViatjeActual().getNom() + "/" + listaImagenes.get(position));
        Glide.with(mcontext).load(st).centerCrop().into(holder.fotoImageView);
        if(position == getItemCount()- 1){
            ga.carregat();
            Log.w("carregat", "siuu");
        }
    }

    @Override
    public int getItemCount() {
        return listaImagenes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView fotoImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoImageView = itemView.findViewById(R.id.fotoViatje);
        }
    }
}




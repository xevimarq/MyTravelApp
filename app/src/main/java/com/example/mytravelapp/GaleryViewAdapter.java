package com.example.mytravelapp;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class GaleryViewAdapter extends RecyclerView.Adapter<GaleryViewAdapter.ViewHolder> {

    private ArrayList<Uri> listaImagenes;

    public GaleryViewAdapter(ArrayList<Uri> listaImagenes) {
        this.listaImagenes = listaImagenes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_galeria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri imageUri = listaImagenes.get(position);
        holder.fotoImageView.setImageURI(imageUri);
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


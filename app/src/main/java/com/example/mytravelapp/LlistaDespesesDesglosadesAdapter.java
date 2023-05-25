package com.example.mytravelapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class LlistaDespesesDesglosadesAdapter extends RecyclerView.Adapter<LlistaDespesesDesglosadesAdapter.RecyclerViewHolder>{
    private ArrayList<Despesa> dades;
    private Controller cr;
    private Context mcontext;

    public LlistaDespesesDesglosadesAdapter(ArrayList<Despesa> despesas, Context mcontext) {
        cr = Controller.getInstance();
        this.dades = despesas;
        this.mcontext = mcontext;
    }

    public void setDades(ArrayList<Despesa> despesas){
        dades = despesas;
    }

    @NonNull
    @Override
    public LlistaDespesesDesglosadesAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(mcontext).inflate(R.layout.card_despesa, parent, false);
        return new LlistaDespesesDesglosadesAdapter.RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LlistaDespesesDesglosadesAdapter.RecyclerViewHolder holder, int position) {
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
        holder.nom.setText(dades.get(position).getNomUsuari());
        holder.data.setText(dades.get(position).getPreu().toString());
        holder.nomDespesa.setText(dades.get(position).getNomDespesa());
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return dades.size();
    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView nomDespesa, nom, data;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            //importante poner bien los id porfa
            nom = itemView.findViewById(R.id.nomUsuariD);
            data = itemView.findViewById(R.id.costDespesa);
            nomDespesa = itemView.findViewById(R.id.nomDespesa);
        }

    }
}

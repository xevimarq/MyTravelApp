package com.example.mytravelapp;


import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class documentAdapter extends RecyclerView.Adapter<documentAdapter.DocumentViewHolder> {
        private List<Document> documentList;

        // Constructor
        public documentAdapter(List<Document> documentList) {
            this.documentList = documentList;
        }

        // ViewHolder
        public class DocumentViewHolder extends RecyclerView.ViewHolder {
            // Definir las vistas para cada elemento de la lista

            public TextView titleTextView;
            public TextView descriptionTextView;
            public ImageView pdfImageView;

            public DocumentViewHolder(View itemView) {
                super(itemView);
                titleTextView = itemView.findViewById(R.id.titleTextView);
                descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
                pdfImageView = itemView.findViewById(R.id.pdfImageView);
            }
        }

        @Override
        public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // Inflar el diseño de cada elemento de la lista
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_document, parent, false);
            return new DocumentViewHolder(view);
        }

    @Override
    public void onBindViewHolder(DocumentViewHolder holder, int position) {
        // Obtener el documento en la posición actual
        Document document = documentList.get(position);

        // Actualizar las vistas con los datos del documento
        holder.titleTextView.setText(document.getTitle());
        holder.descriptionTextView.setText(document.getDescription());

        // Aquí también puedes agregar la lógica para mostrar el archivo PDF si es necesario
        // Puedes usar la biblioteca PDF Viewer para mostrar el contenido del PDF en una vista

        // Ejemplo de código para abrir el archivo PDF utilizando la biblioteca PDF Viewer
        holder.pdfImageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(document.getPdfFilePath()), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            // Verificar si hay alguna aplicación que pueda manejar el archivo PDF
            if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                v.getContext().startActivity(intent);
            } else {
                Toast.makeText(v.getContext(), "No se puede abrir el archivo PDF", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
        public int getItemCount() {
            return documentList.size();
        }


}

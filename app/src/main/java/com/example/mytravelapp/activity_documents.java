package com.example.mytravelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class activity_documents extends AppCompatActivity {

    Controller controller;
    ImageButton createButton;
    RecyclerView vistaDocs;
    private documentAdapter adapter;
    private List<Document> documentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        controller = Controller.getInstance();
        createButton = findViewById(R.id.addDocs_button);
        vistaDocs = findViewById(R.id.llistaDocs);

        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        vistaDocs.setLayoutManager(layoutManager);
        controller.setRecyclerView(vistaDocs, getApplicationContext());

        createButton.setOnClickListener(this::newDocs);


        List<Document> documentList = new ArrayList<>(); // Aquí debes obtener la lista de documentos que deseas mostrar
        adapter = new documentAdapter(documentList);
        documentAdapter adapter = new documentAdapter(documentList);
        vistaDocs.setAdapter(adapter);
        loadUserDocuments();
    }

    private static final int REQUEST_CODE_FILE = 123;

    void newDocs(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");  // Establece el tipo de archivo que deseas permitir (en este caso, solo PDF)
        startActivityForResult(intent, REQUEST_CODE_FILE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_FILE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri fileUri = data.getData();

            // Subir el archivo a Firebase Storage
            uploadFileToFirebaseStorage(fileUri);
        }
    }

    private void uploadFileToFirebaseStorage(Uri fileUri) {
        // Obtén la referencia al nodo de Firebase Storage donde deseas guardar los documentos del usuario
        // Puedes utilizar algún identificador único del usuario como parte del path
        StorageReference userDocsRef = FirebaseStorage.getInstance().getReference().child("user_documents").child(controller.getMail());

        // Genera un nombre único para el archivo
        String fileName = "document_" + System.currentTimeMillis();

        // Crea una referencia para el archivo en Firebase Storage
        StorageReference fileRef = userDocsRef.child(fileName);

        // Sube el archivo
        fileRef.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Obtiene la URL de descarga del archivo
                    fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();

                        // Crea un objeto Document con la información del archivo
                        Document newDocument = new Document("Nuevo documento", downloadUrl);

                        // Guarda la información del documento en Firebase Firestore
                        saveDocumentToFirestore(newDocument);
                    });
                })
                .addOnFailureListener(e -> {
                    // Error al subir el archivo
                    Toast.makeText(this, "Error al subir el archivo", Toast.LENGTH_SHORT).show();
                });
        loadUserDocuments();
    }

    private void saveDocumentToFirestore(Document document) {
        // Obtén una referencia a la colección de documentos del usuario actual en Firebase Firestore
        // Puedes utilizar algún identificador único del usuario como parte del path
        CollectionReference userDocsCollection = FirebaseFirestore.getInstance().collection("user_documents").document(controller.getMail()).collection("documents");

        // Guarda el documento en Firebase Firestore
        userDocsCollection.add(document)
                .addOnSuccessListener(documentReference -> {
                    // Éxito al guardar el documento en Firestore
                    Toast.makeText(this, "Documento guardado exitosamente", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Error al guardar el documento en Firestore
                    Toast.makeText(this, "Error al guardar el documento", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadUserDocuments() {
        // Obtén una referencia a la colección de documentos del usuario actual en Firebase Firestore
        // Puedes utilizar algún identificador único del usuario como parte del path
        CollectionReference userDocsCollection = FirebaseFirestore.getInstance().collection("user_documents").document(controller.getMail()).collection("documents");

        // Consulta los documentos del usuario
        userDocsCollection.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    // Limpiar la lista actual de documentos
                    documentList.clear();

                    // Iterar sobre los documentos recuperados
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        // Obtener los datos del documento y crear un objeto Document
                        Document document = documentSnapshot.toObject(Document.class);
                        documentList.add(document);
                    }

                    // Notificar al adapter que los datos han cambiado
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Error al obtener los documentos del usuario
                    Toast.makeText(this, "Error al cargar los documentos", Toast.LENGTH_SHORT).show();
                });
    }

    // Metodes menu barra inferior
    public void profileButtonClick(View view){
        startActivity(new Intent(this, profileActivity.class));
    }

    public void homeButtonClick(View view){
        startActivity(new Intent(this, mainviatjesActivty.class));
    }
    public void worldClick(View view){
        startActivity(new Intent(this, esdevenimentActivity.class));
    }
}
package com.example.mytravelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private documentAdapter adapter;
    private List<Document> documentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        controller = Controller.getInstance();
        createButton = findViewById(R.id.addDocs_button);
        vistaDocs = findViewById(R.id.llistaDocs);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        vistaDocs.setLayoutManager(layoutManager);
        controller.setRecyclerView(vistaDocs, getApplicationContext());

        createButton.setOnClickListener(this::newDocs);

        // Crear el adaptador y asignarlo al RecyclerView
        adapter = new documentAdapter(documentList);
        vistaDocs.setAdapter(adapter);

        // Cargar los documentos del usuario desde Firestore
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

            String fileName = getFileNameFromUri(fileUri);

            // Subir a Firebase Storage
            uploadFileToFirebaseStorage(fileUri, fileName);
        }
    }

    private void uploadFileToFirebaseStorage(Uri fileUri, String fileName) {
        // Obtén la referencia al nodo de Firebase Storage donde deseas guardar los documentos del usuario
        // Puedes utilizar algún identificador único del usuario como parte del path
        StorageReference userDocsRef = FirebaseStorage.getInstance().getReference().child("viatjes").child(controller.getViatjeActual().nom);

        // Genera un nombre único para el archivo
        //String fileName = "document_" + System.currentTimeMillis();

        // Crea una referencia para el archivo en Firebase Storage
        StorageReference fileRef = userDocsRef.child(fileName);

        // Sube el archivo
        fileRef.putFile(fileUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Obtiene la URL de descarga del archivo
                    fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String downloadUrl = uri.toString();

                        // Crea un objeto Document con la información del archivo
                        Document newDocument = new Document(fileName, downloadUrl);

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
        CollectionReference userDocsCollection = FirebaseFirestore.getInstance().collection("viatjes").document(controller.getMail()+"."+controller.getViatjeActual().nom).collection("documents");

        // Guarda el documento en Firebase Firestore
        userDocsCollection.add(document)
                .addOnSuccessListener(documentReference -> {
                    // Éxito al guardar el documento en Firestore
                    Toast.makeText(this, "Documento guardado exitosamente", Toast.LENGTH_SHORT).show();

                    loadUserDocuments();
                })
                .addOnFailureListener(e -> {
                    // Error al guardar el documento en Firestore
                    Toast.makeText(this, "Error al guardar el documento", Toast.LENGTH_SHORT).show();
                });
    }

    private String getFileNameFromUri(Uri uri) {
        String fileName = null;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            fileName = cursor.getString(nameIndex);
            cursor.close();
        }
        return fileName;
    }
    private void loadUserDocuments() {
        // Obtén una referencia a la colección de documentos del usuario actual en Firebase Firestore
        // Puedes utilizar algún identificador único del usuario como parte del path
        CollectionReference userDocsCollection = FirebaseFirestore.getInstance().collection("viatjes").document(controller.getMail()+"."+controller.getViatjeActual().nom).collection("documents");

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

    // Metodos menu barra inferior
    public void profileButtonClick(View view){
        startActivity(new Intent(this, profileActivity.class));
    }

    public void goHome(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        controller.login(user.getEmail());
        startActivity(new Intent(this, mainviatjesActivty.class));
    }

    public void calendarClick(View view){
        startActivity(new Intent(this, esdevenimentActivity.class));
    }
}
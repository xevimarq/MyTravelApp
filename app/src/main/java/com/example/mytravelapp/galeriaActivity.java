package com.example.mytravelapp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class galeriaActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    private RecyclerView recyclerView;
    private GaleryViewAdapter adapter;
    private ArrayList<Uri> listaImagenes = new ArrayList<>();
    private ImageButton addButton;

    private Controller controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        controller = Controller.getInstance();
        recyclerView = findViewById(R.id.llistaFotos);
        addButton = findViewById(R.id.add_button);
        adapter = new GaleryViewAdapter(listaImagenes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri imageUri = data.getData();
            Log.d("GaleriaActivity", "Image URI: " + imageUri);
            listaImagenes.add(imageUri);
            adapter.notifyDataSetChanged();
            controller.loadImatges(listaImagenes);
        }
    }

    //metodos para todos los activity con la barra inferior
    public void profileButtonClick(View view){
        startActivity(new Intent(this, profileActivity.class));
    }

    public ArrayList<Uri> getIdentificadorImatges() {
        return this.listaImagenes;
    }
}

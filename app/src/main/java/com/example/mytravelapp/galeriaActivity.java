package com.example.mytravelapp;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class galeriaActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK_IMAGE = 1;

    private RecyclerView recyclerView;
    private GaleryViewAdapter adapter;
    private ArrayList<Uri> listaImagenes = new ArrayList<>();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ArrayList<String> id = new ArrayList<>();
    private ImageButton addButton;
    Viatje viatje;
    private Controller controller;

    private ProgressBar loadingProgress;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        controller = Controller.getInstance();
        viatje = controller.getViatjeActual();
        recyclerView = findViewById(R.id.llistaFotos);
        addButton = findViewById(R.id.addDocs_button);
        id = controller.getViatjeActual().getIdentificadorImatges();
        loadingProgress = findViewById(R.id.loading_progress);
        adapter = new GaleryViewAdapter(id, this, this);
        loadingProgress.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPhoto(v);
            }
        });
    }


    public void uploadPhoto(View view){
        mGetContent.launch("image/*");
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            Uri filepath = uri;
            listaImagenes.add(filepath);
            adapter.notifyDataSetChanged();
            loadingProgress.setVisibility(View.VISIBLE);
            controller.uploadImatges(listaImagenes);
        }
    });

    public void carregat(){
        loadingProgress.setVisibility(View.INVISIBLE);
    }
    /*
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
     */

    //metodos para todos los activity con la barra inferior
    public void profileButtonClick(View view){
        startActivity(new Intent(this, profileActivity.class));
    }

    public ArrayList<Uri> getIdentificadorImatges() {
        return this.listaImagenes;
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

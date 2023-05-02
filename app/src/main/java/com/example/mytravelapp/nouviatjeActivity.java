package com.example.mytravelapp;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.io.IOException;

public class nouviatjeActivity extends AppCompatActivity {
    Controller controller;
    Uri filepath;
    int requestImage = 22;
    Button createButton, uploadButton;
    EditText nom, inici, fi;

    ImageView imatge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouviatge);

        controller = Controller.getInstance();

        imatge = findViewById(R.id.image_viatje);

        createButton = findViewById(R.id.botoCrear);
        createButton.setEnabled(false);
        uploadButton = findViewById(R.id.uploadButton);
        nom = findViewById(R.id.nomViatge);
        inici = findViewById(R.id.iniciText);
        fi = findViewById(R.id.iniciFinal);

        createButton.setOnClickListener(this::createNew);
        uploadButton.setOnClickListener(this::uploadPhoto);
        nom.addTextChangedListener(activarBoto);
        inici.addTextChangedListener(activarBoto);
        fi.addTextChangedListener(activarBoto);


    }

    //listeners per activar el butó
    private TextWatcher activarBoto = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!nom.getText().toString().trim().isEmpty() && !inici.getText().toString().trim().isEmpty() &&
                    !fi.getText().toString().trim().isEmpty() ) {

                createButton.setEnabled(true);
            }
            else{
                createButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    //metodos para todos los activity con la barra inferior
    //añadir finish si son submenus
    public void profileButtonClick(View view){
        startActivity(new Intent(this, profileActivity.class));
        finish();
    }


    public void uploadPhoto(View view){
        mGetContent.launch("image/*");
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    filepath = uri;
                    imatge.setImageURI(filepath);
                }
            });

    public void createNew(View view){
        if(controller.addViatje(nom.getText().toString().trim(), inici.getText().toString().trim(),fi.getText().toString().trim(),filepath!=null)) {
            if(filepath!=null){
                controller.afegirFoto(controller.getMail(),nom.getText().toString().trim(),filepath, 0);

            }
            finish();

        }else {
            Toast.makeText(this,"Ja tens un viatje anomenat així", Toast.LENGTH_SHORT).show();
        }
    }



}

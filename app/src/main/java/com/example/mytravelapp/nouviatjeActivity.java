package com.example.mytravelapp;

import android.app.DatePickerDialog;
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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.DatePicker;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FileDownloadTask;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class nouviatjeActivity extends AppCompatActivity {
    Controller controller;
    Uri filepath;
    int requestImage = 22;
    Button createButton, uploadButton;
    EditText nom, inici, fi;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ImageView imatge;
    TextView iniciText, finalText;
    Calendar calendar;

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
        /*inici = findViewById(R.id.iniciText);
        fi = findViewById(R.id.iniciFinal);*/

        createButton.setOnClickListener(this::createNew);
        uploadButton.setOnClickListener(this::uploadPhoto);
        nom.addTextChangedListener(activarBoto);
        /*inici.addTextChangedListener(activarBoto);
        fi.addTextChangedListener(activarBoto);*/
        iniciText = findViewById(R.id.iniciText);
        finalText = findViewById(R.id.finalText);
        calendar = Calendar.getInstance();

        iniciText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(iniciText);
            }
        });

        finalText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(finalText);
            }
        });

    }
    private void showDatePicker(final TextView textView) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(nouviatjeActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textView.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    //listeners per activar el butó
    private TextWatcher activarBoto = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!nom.getText().toString().trim().isEmpty() && !iniciText.getText().toString().trim().isEmpty() &&
                    !finalText.getText().toString().trim().isEmpty() ) {

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
        if(controller.addViatje(nom.getText().toString().trim(), iniciText.getText().toString().trim(),finalText.getText().toString().trim(),filepath!=null)) {
            if(filepath!=null){
                controller.afegirFoto(controller.getMail(),nom.getText().toString().trim(),filepath, 0);

            }

            finish();

        }else {
            Toast.makeText(this,"Ja tens un viatje anomenat així", Toast.LENGTH_SHORT).show();
        }
    }

    public void goHome(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        controller.login(user.getEmail());
        startActivity(new Intent(this, mainviatjesActivty.class));
    }

}

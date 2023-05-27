package com.example.mytravelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class afegirDespesaActivity extends AppCompatActivity {
    Controller controller;
    Button createButton;
    EditText nom, nomDespesa, preu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddespesa);

        controller = Controller.getInstance();

        createButton = findViewById(R.id.botoCrear);
        createButton.setEnabled(false);
        nomDespesa = findViewById(R.id.nomViatge);
        nom = findViewById(R.id.iniciText);
        preu = findViewById(R.id.iniciFinal);

        createButton.setOnClickListener(this::createNew);
        nom.addTextChangedListener(activarBoto);
        nomDespesa.addTextChangedListener(activarBoto);
        preu.addTextChangedListener(activarBoto);


    }

    //listeners per activar el butó
    private TextWatcher activarBoto = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!nom.getText().toString().trim().isEmpty() && !nomDespesa.getText().toString().trim().isEmpty() &&
                    !preu.getText().toString().trim().isEmpty() ) {

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


    public void createNew(View view){
        controller.addDespesa(nomDespesa.getText().toString().trim(), nom.getText().toString().trim(), Double.parseDouble(preu.getText().toString().trim()));
        finish();
    }
    public void calendarClick(View view){
        startActivity(new Intent(this, esdevenimentActivity.class));
    }

}

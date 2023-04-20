package com.example.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class registerActivity extends AppCompatActivity {

    Controller controller;

    private Button registerButon;
    private EditText email, nom, cognom, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        controller = Controller.getInstance();

        //els guardem a les variables corresponents
        registerButon = findViewById(R.id.registerButon);
        email = findViewById(R.id.emailText);
        nom = findViewById(R.id.nomText);
        cognom = findViewById(R.id.cognomText);
        password = findViewById(R.id.passwordText);

        registerButon.setEnabled(false);

        //afegim el listener
        email.addTextChangedListener(activarBoto);
        nom.addTextChangedListener(activarBoto);
        cognom.addTextChangedListener(activarBoto);
        password.addTextChangedListener(activarBoto);


    }


    //el creem com a metode per a pasar-ho a tots el listners
    //esta atent a que cambi algun text i si estan tots 4 el botó s'activa, així no cal error de camp obligatori.
    private TextWatcher activarBoto = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!email.getText().toString().trim().isEmpty() && !nom.getText().toString().trim().isEmpty() &&
                    !cognom.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty() ) {

                registerButon.setEnabled(true);
            }
            else{
                registerButon.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void register(View view){

        String[] dades = {email.getText().toString().trim(),password.getText().toString().trim(),nom.getText().toString().trim(),cognom.getText().toString().trim()};
        TextView texto = findViewById(R.id.textView);
        texto.setText("a");
        try {
            controller.addUser(dades);
            texto.setText("b");
            startActivity(new Intent(this, loginActivity.class));
            texto.setText(("c"));
        }
        catch (Exception e){
            //pasar-ho com error en bombolla jeje
            e.getMessage();
        }

    }




}

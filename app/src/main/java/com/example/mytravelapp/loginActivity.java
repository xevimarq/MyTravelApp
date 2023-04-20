package com.example.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {
    Controller controller;

    private Button loginButon;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controller = Controller.getInstance();

        //els guardem a les variables corresponents
        loginButon = findViewById(R.id.loginButton);
        email = findViewById(R.id.emailTextLogin);
        password = findViewById(R.id.passwordTextLogin);

        loginButon.setEnabled(false);

        //afegim el listener
        email.addTextChangedListener(activarBoto);
        password.addTextChangedListener(activarBoto);


    }


    //el creem com a metode per a pasar-ho a tots el listners
    //esta atent a que cambi algun text i si estan tots 2 el botó s'activa, així no cal error de camp obligatori.
    private TextWatcher activarBoto = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!email.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("") ) {

                loginButon.setEnabled(true);
            }
            else{
                loginButon.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void login(View view){
        try {
            controller.login(email.getText().toString().trim(), password.getText().toString().trim());
            startActivity(new Intent(this, profileActivity.class));
        }
        catch (Exception e){
            //pasar-ho com error en bombolla jeje
            e.getMessage();
        }

    }

    public void goRegister(View view){
        startActivity(new Intent(this, registerActivity.class));
    }




}

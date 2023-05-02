package com.example.mytravelapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerActivity extends AppCompatActivity {

    Controller controller;

    FirebaseAuth mAuth;

    private Button registerButon;
    private EditText email, nom, cognom, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
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


        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                controller.addUser(dades);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(registerActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void goLogin(View view){
        startActivity(new Intent(this, loginActivity.class));
    }



}

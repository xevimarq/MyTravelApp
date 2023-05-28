package com.example.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {
    Controller controller;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button loginButon;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        controller = Controller.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        if(user != null){
            controller.login(user.getEmail());
            startActivity(new Intent(this, mainviatjesActivty.class));
            finish();
        }

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
            mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                controller.login(email.getText().toString().trim());
                                startActivity(new Intent(loginActivity.this, mainviatjesActivty.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(loginActivity.this, "Error al inciar sesió",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
    }

    public void goRegister(View view){
        startActivity(new Intent(this, registerActivity.class));
    }


}

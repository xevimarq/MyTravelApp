package com.example.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class viatjeActivity extends AppCompatActivity {

    Controller controller = Controller.getInstance();
    Button addButton;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viatje);

        controller = Controller.getInstance();

        addButton = findViewById(R.id.addButton);



    }

    public void iniciarVistaAfegir(View view){
        startActivity(new Intent(this, addusertoviatjeActivity.class));
    }

    public void iniciarGaleria(View view){
        startActivity(new Intent(this, galeriaActivity.class));
    }
    public void iniciarDespeses(View view){
        startActivity(new Intent(this, despesaActivity.class));
    }
    public void goHome(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        controller.login(user.getEmail());
        startActivity(new Intent(this, mainviatjesActivty.class));
    }
    public void profileButtonClick(View view){
        startActivity(new Intent(this, profileActivity.class));
    }
}

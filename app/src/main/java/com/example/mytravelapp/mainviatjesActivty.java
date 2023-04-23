package com.example.mytravelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class mainviatjesActivty extends AppCompatActivity {

    Controller controller;
    ImageButton createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainviatges);

        controller = Controller.getInstance();

        createButton = findViewById(R.id.add_button);

        createButton.setOnClickListener(this::createNew);


    }

    //metodos para todos los activity con la barra inferior
    public void profileButtonClick(View view){
        startActivity(new Intent(this, profileActivity.class));
    }


    public void createNew(View view){
        startActivity(new Intent(this, nouviatjeActivity.class));
    }



}
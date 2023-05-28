package com.example.mytravelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class mainviatjesActivty extends AppCompatActivity {

    Controller controller;
    ImageButton createButton;

    Switch compartits;
    Button acutal, past, futur;
    RecyclerView vistaViatjes;

    int opcio = 0;
    boolean compartit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainviatges);

        controller = Controller.getInstance();

        createButton = findViewById(R.id.addDocs_button);
        vistaViatjes = findViewById(R.id.llistaDocs);

        acutal = findViewById(R.id.actualbuton);
        past = findViewById(R.id.pastbuton);
        futur = findViewById(R.id.futurbuton);

        compartits = findViewById(R.id.switchcompartits);

        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        vistaViatjes.setLayoutManager(layoutManager);
        controller.setRecyclerView(vistaViatjes, getApplicationContext());

        createButton.setOnClickListener(this::createNew);

        acutal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setStateViatjes(1);
                controller.setAdapter();
            }
        });

        past.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setStateViatjes(2);
                controller.setAdapter();
            }
        });

        futur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.setStateViatjes(3);
                controller.setAdapter();
            }
        });

        compartits.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                controller.setCompartits(b);
                controller.setAdapter();
            }
        });

    }

    //metodos para todos los activity con la barra inferior
    public void profileButtonClick(View view){
        startActivity(new Intent(this, profileActivity.class));
    }

    public void goHome(View view){
        Toast.makeText(mainviatjesActivty.this, "Ja ets a Home",
                Toast.LENGTH_SHORT).show();
    }
    public void calendarClick(View view){
        startActivity(new Intent(this, esdevenimentActivity.class));
    }
    public void createNew(View view){
        startActivity(new Intent(this, nouviatjeActivity.class));
    }



}
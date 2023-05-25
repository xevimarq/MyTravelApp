package com.example.mytravelapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class activity_documents extends AppCompatActivity {

    Controller controller;
    ImageButton createButton;
    RecyclerView vistaDocs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);

        controller = Controller.getInstance();
        createButton = findViewById(R.id.addDocs_button);
        vistaDocs = findViewById(R.id.llistaDocs);

        GridLayoutManager layoutManager=new GridLayoutManager(this,1);
        vistaDocs.setLayoutManager(layoutManager);
        controller.setRecyclerView(vistaDocs, getApplicationContext());

        createButton.setOnClickListener(this::newDocs);

    }

    void newDocs(View view){

    }
}
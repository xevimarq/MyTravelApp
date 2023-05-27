package com.example.mytravelapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


import android.content.Intent;
import android.net.Uri;

import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

public class despesaActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ArrayList<String> id = new ArrayList<>();
    private Button addButton, desglose;
    private Controller controller;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despeses);
        controller = Controller.getInstance();
        addButton = findViewById(R.id.addDespesa);
        desglose = findViewById(R.id.botoDespeses);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDespesa();
            }
        });
        desglose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                botoDespeses();
            }
        });
        recyclerView = findViewById(R.id.RecyclerDespesesTotals);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        controller.recyclerViewLlistaDespeses(recyclerView, this);
    }


    public void addDespesa(){
        startActivity(new Intent(this, afegirDespesaActivity.class));
    }

    public void botoDespeses(){
        startActivity(new Intent(this, despesaDesglosadaActivity.class));
    }
    public void calendarClick(View view){
        startActivity(new Intent(this, esdevenimentActivity.class));
    }

}
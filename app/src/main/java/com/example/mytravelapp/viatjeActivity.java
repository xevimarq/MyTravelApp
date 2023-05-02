package com.example.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class viatjeActivity extends AppCompatActivity {

    Controller controller = Controller.getInstance();
    Button addButton;
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


}

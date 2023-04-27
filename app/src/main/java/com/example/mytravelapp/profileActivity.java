package com.example.mytravelapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class profileActivity extends AppCompatActivity {

        Controller controller;
        TextView name, surname;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
            controller = Controller.getInstance();
            name = findViewById(R.id.profileName);
            surname = findViewById(R.id.profileCognom);

            name.setText(controller.getName());
            surname.setText(controller.getSurname());
        }
}

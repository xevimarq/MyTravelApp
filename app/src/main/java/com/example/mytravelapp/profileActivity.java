package com.example.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class profileActivity extends AppCompatActivity {

        Controller controller;
        TextView name, surname, mail;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        private Button logoutButton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
            logoutButton = findViewById(R.id.bottonLogout);
            controller = Controller.getInstance();
            name = findViewById(R.id.profileName);
            surname = findViewById(R.id.profileCognom);
            mail = findViewById(R.id.profileMail);

            name.setText(controller.getName());
            surname.setText(controller.getSurname());
            mail.setText(controller.getMail());
        }
        public void logout(View view){
            mAuth.signOut();
            Intent sortir = new Intent(this, loginActivity.class);
            //esto cierra todas las actividades
            finishAffinity();
            startActivity(sortir);
            finish();
        }
    public void goHome(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        controller.login(user.getEmail());
        startActivity(new Intent(this, mainviatjesActivty.class));
    }
}

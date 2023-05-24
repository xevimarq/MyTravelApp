package com.example.mytravelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class profileActivity extends AppCompatActivity {

        Controller controller;
        TextView name, surname, mail;
        Uri filepath;
        Context mcontext;
        boolean hasProfilePhoto = false;
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        private Button logoutButton;
        private Button fotoButton;
        private ImageView fotoPerfil;
        FirebaseStorage st;
        User loggedUser;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile);
            logoutButton = findViewById(R.id.bottonLogout);
            fotoButton = findViewById(R.id.afegirFoto_button);
            fotoPerfil = findViewById(R.id.imageProfile);

            controller = Controller.getInstance();
            st = FirebaseStorage.getInstance();
            name = findViewById(R.id.profileName);
            surname = findViewById(R.id.profileCognom);
            mail = findViewById(R.id.profileMail);

            fotoButton.setOnClickListener(this::afegirFoto);
            name.setText(controller.getName());
            surname.setText(controller.getSurname());
            mail.setText(controller.getMail());
            loggedUser = controller.getLoggedUser();

            loggedUser.gethasProfilePhoto();
            if(loggedUser.gethasProfilePhoto()){
                //StorageReference st = FirebaseStorage.getInstance().getReference("images/" +controller.getMail()+ "/perfil");
                Glide.with(this).load("images/" +controller.getMail()+ "/perfil").into(fotoPerfil);
            }
        }
        public void logout(View view){
            mAuth.signOut();
            Intent sortir = new Intent(this, loginActivity.class);
            //esto cierra todas las actividades
            finishAffinity();
            startActivity(sortir);
            finish();
        }
    public void afegirFoto(View view){
        mGetContent.launch("image/*");
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
        @Override
        public void onActivityResult(Uri uri) {
            filepath = uri;
            fotoPerfil.setImageURI(filepath);
            StorageReference ref;
            ref = st.getReference().child("images/"+controller.getMail()+"/perfil");
            ref.putFile(filepath);
            loggedUser.setHasProfilePhoto();
        }

    });
    public void goHome(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        controller.login(user.getEmail());
        startActivity(new Intent(this, mainviatjesActivty.class));
    }
}

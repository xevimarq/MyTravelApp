package com.example.mytravelapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class addusertoviatjeActivity extends AppCompatActivity {

    Controller controller = Controller.getInstance();
    Button addButton;
    EditText user;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addusertoviatje);

        db = FirebaseFirestore.getInstance();

        controller = Controller.getInstance();

        addButton = findViewById(R.id.Afegir);
        user = findViewById(R.id.userText);


    }


    public void  afegir(View view){
        db.collection("users").document(user.getText().toString().trim()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    User u = task.getResult().toObject(User.class);
                    controller.getViatjeActual();
                    if(u == null){
                        Toast.makeText(addusertoviatjeActivity.this, "L'usuari no existeix", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (controller.getViatjeActual().getEditors().contains(u.getEmail())) {
                        Toast.makeText(addusertoviatjeActivity.this, "L'usuari ja es editor", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    u.addViatje(controller.getViatjeActual().getIdViatje(), controller.getViatjeActual().getIniciViatje(), controller.getViatjeActual().getFiViatje());
                    controller.addUser(u);
                    controller.getViatjeActual().getEditors().add(u.getEmail());
                    controller.updateViatje(controller.getViatjeActual());
                    finish();
                }else{
                    Toast.makeText(addusertoviatjeActivity.this, "L'usuari no existeix", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }


}

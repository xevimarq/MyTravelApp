package com.example.mytravelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class esdevenimentActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView tripInfoText;
    CalendarView calendarView;
    Controller controller;
    FirebaseFirestore db;
    List<String> tripDates; // Lista de fechas de los viajes en formato de cadena de texto

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esdeveniment);
        controller = Controller.getInstance();
        tripInfoText = findViewById(R.id.trip_info_text);
        calendarView = findViewById(R.id.calendar);

        db = FirebaseFirestore.getInstance();


    }


    public void profileButtonClick(View view) {
        startActivity(new Intent(this, profileActivity.class));
    }
    public void goHome(View view){
        FirebaseUser user = mAuth.getCurrentUser();
        controller.login(user.getEmail());
        startActivity(new Intent(this, mainviatjesActivty.class));
    }
}

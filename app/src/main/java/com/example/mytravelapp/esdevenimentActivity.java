package com.example.mytravelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
    ArrayList<String> tripDates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esdeveniment);
        controller = Controller.getInstance();
        tripInfoText = findViewById(R.id.trip_info_text);
        calendarView = findViewById(R.id.calendar);

        db = FirebaseFirestore.getInstance();
        retrieveTripDates();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = formatDate(year, month, dayOfMonth);
                if (tripDates.contains(selectedDate)) {
                    int index = tripDates.indexOf(selectedDate);
                    String tripName = controller.getTripName(index);
                    tripInfoText.setText(tripName);
                } else {
                    tripInfoText.setText("No hay viaje en esa fecha");
                }
            }
        });
    }
    private void retrieveTripDates() {
        tripDates = controller.getTripDate();
        Log.w("aaa",tripDates.get(1));
    }

    private String formatDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }

    public void profileButtonClick(View view) {
        startActivity(new Intent(this, profileActivity.class));
    }

    public void goHome(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        controller.login(user.getEmail());
        startActivity(new Intent(this, mainviatjesActivty.class));
    }
}

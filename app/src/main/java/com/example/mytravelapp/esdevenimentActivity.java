package com.example.mytravelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

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
    MaterialCalendarView calendarView;
    Controller controller;
    FirebaseFirestore db;
    ArrayList<String> tripDates;
    private int index = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esdeveniment);
        controller = Controller.getInstance();
        tripInfoText = findViewById(R.id.trip_info_text);
        calendarView = findViewById(R.id.calendarView);

        db = FirebaseFirestore.getInstance();
        retrieveTripDates();

        DayViewDecorator tripDateDecorator = new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                Date date = day.getDate();
                String formattedDate = formatDate(date);
                return tripDates.contains(formattedDate);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(5, Color.RED));
            }
        };
        calendarView.addDecorator(tripDateDecorator);
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
                Date selectedDate = date.getDate();
                String formattedDate = formatDate(selectedDate);
                if (tripDates.contains(formattedDate)) {
                    index = tripDates.indexOf(formattedDate);
                    String tripName = controller.getTripName(index);
                    controller.setViatjeActual(index);
                    tripInfoText.setText(tripName);
                } else {
                    tripInfoText.setText("No hay viaje en esa fecha");
                }
            }
        });
        tripInfoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Viatje viatje = controller.getViatjeActual();
                Intent cambiarActivitat = new Intent(esdevenimentActivity.this, viatjeActivity.class);
                cambiarActivitat.putExtra("tripName", viatje.getNom());
                startActivity(cambiarActivitat);

            }
        });
    }

    private void retrieveTripDates() {
        tripDates = controller.getTripDate();
    }

    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }

    public void profileButtonClick(View view) {
        startActivity(new Intent(this, profileActivity.class));
    }

    public void goHome(View view) {
        FirebaseUser user = mAuth.getCurrentUser();
        controller.login(user.getEmail());
        startActivity(new Intent(this, mainviatjesActivty.class));
    }
    public void calendarClick(View view){
        startActivity(new Intent(this, esdevenimentActivity.class));
    }
}

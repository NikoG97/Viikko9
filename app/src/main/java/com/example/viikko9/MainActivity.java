package com.example.viikko9;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    private Finnkino fk = Finnkino.getInstance();
    private Spinner spinner;
    private RecyclerView rView;
    private Context context;
    private Button button;
    private EditText timeToField, timeFromField, date;
    private LocalDate dateNow = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinner);
        context = MainActivity.this;
        timeFromField = findViewById(R.id.timeFrom);
        timeToField = findViewById(R.id.timeTo);
        date = findViewById(R.id.date);
        button = findViewById(R.id.button);
        rView = findViewById(R.id.recyclerView);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //create spinner
        fk.readAreasXML();
        ArrayAdapter<FinnkinoTheater> myAdapter = new ArrayAdapter<FinnkinoTheater>(MainActivity.this,
                android.R.layout.simple_list_item_1, fk.getTheatersList());
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if( date.getText().toString().trim().length() == 0) {
                    date.setText(dateNow.format(formatter));
                }
                if( timeFromField.getText().toString().trim().length() == 0){
                    timeFromField.setText("00:00");
                }
                if( timeToField.getText().toString().trim().length() == 0){
                    timeToField.setText("23:59");
                }
                fk.readScheduleXML( (FinnkinoTheater) parent.getSelectedItem() , date.getText().toString());
                RecAdapter recAdapter = new RecAdapter(context, fk.getMoviesList(
                        timeFromField.getText().toString(), timeToField.getText().toString() ) );
                rView.setAdapter(recAdapter);
                rView.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getSelectedArea(View v) {
        if( date.getText().toString().trim().length() == 0){
            date.setText(dateNow.format(formatter));
        }
        if( timeFromField.getText().toString().trim().length() == 0){
            timeFromField.setText("00:00");
        }
        if( timeToField.getText().toString().trim().length() == 0){
            timeToField.setText("23:59");
        }
        fk.readScheduleXML( (FinnkinoTheater) spinner.getSelectedItem() , date.getText().toString());

        RecAdapter recAdapter = new RecAdapter(this, fk.getMoviesList(
                timeFromField.getText().toString(), timeToField.getText().toString() ) );
        rView.setAdapter(recAdapter);
        rView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void refresh(View v){
        ArrayAdapter<FinnkinoTheater> myAdapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, fk.getTheatersList());
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);
        button.setEnabled(true);

    }

}
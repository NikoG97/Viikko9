package com.example.viikko9;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FinnkinoMovie {

    private String movieID = "";
    private String movieTitle = "";
    private String auditorium = "";
    private LocalDateTime start = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public FinnkinoMovie(String id, String title, String startTime, String audit) {
        movieID = id;
        movieTitle = title;
        auditorium = audit;
        try {
            start = LocalDateTime.parse(startTime);
        } catch (DateTimeException e) {
            e.printStackTrace();
        }
    }

    public String getMovieID() {
        return movieID;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getAuditorium() {
        return auditorium;
    }

    public LocalDateTime getLDT() {
        return start;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getStartTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Alkaa: 'HH:mm");
        if( start == null) {
            return "time not found";
        }
        return start.format(formatter);
    }

    @Override
    public String toString() {
        return movieTitle;
    }
}
package com.example.viikko9;


public class FinnkinoTheater {
    private String theaterID = "";
    private String theaterArea = "";
    public FinnkinoTheater(String id, String area ) {
        theaterID = id;
        theaterArea = area;
    }

    public String getTheaterID() {
        return theaterID;
    }

    public String getTheaterArea() {
        return theaterArea;
    }

    @Override
    public String toString() {
        return theaterArea;
    }

}
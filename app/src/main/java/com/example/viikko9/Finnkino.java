package com.example.viikko9;


import android.os.Build;

import androidx.annotation.RequiresApi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Finnkino {
    private static Finnkino finnkino = new Finnkino();

    private Finnkino() {
    }

    public static Finnkino getInstance() {
        return finnkino;
    }

    private ArrayList<FinnkinoTheater> theaterslist = new ArrayList<>();
    private ArrayList<FinnkinoMovie> movieslist = new ArrayList<>();

    private String theaters[] = {""};


    public ArrayList<FinnkinoTheater> getTheatersList() {
        return theaterslist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<FinnkinoMovie> getMoviesList(String from, String to) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        if (movieslist.size() != 0) {
            try {
                ArrayList<FinnkinoMovie> parsedMovies = new ArrayList<>();
                for (FinnkinoMovie movie : movieslist) {
                    if(movie.getLDT().toLocalTime().isAfter(LocalTime.parse(from, formatter)) &&
                            movie.getLDT().toLocalTime().isBefore(LocalTime.parse(to, formatter) ) ) {
                        parsedMovies.add(movie);
                    }
                }
                return parsedMovies;
            } catch (DateTimeException e) {
                e.printStackTrace();
                return movieslist;
            }
        } else {
            movieslist.add(new FinnkinoMovie("0","no movies found", "notfound",""));
            return movieslist;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void readScheduleXML(FinnkinoTheater theater, String date) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/Schedule/";
            urlString = urlString + ("?area=" + theater.getTheaterID() + "&dt=" + date );
            movieslist = new ArrayList<>();

            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            System.out.println("Root element: "+ doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("Show");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getElementsByTagName("ID").item(0).getTextContent();
                    String title = element.getElementsByTagName("Title").item(0).getTextContent();
                    String showStart = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                    String auditorium = element.getElementsByTagName("TheatreAuditorium").item(0).getTextContent();

                    movieslist.add(new FinnkinoMovie(id,title,showStart,auditorium));
                }

            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }


    public void readAreasXML() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/TheatreAreas";
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("TheatreArea");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String id = element.getElementsByTagName("ID").item(0).getTextContent();
                    String area = element.getElementsByTagName("Name").item(0).getTextContent();
                    theaterslist.add(new FinnkinoTheater(id,area));
                }

            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

    }

}
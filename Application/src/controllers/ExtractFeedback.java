package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import models.Feedback;

public class ExtractFeedback {
    public static ArrayList<Feedback> getAssignmentFeedback(String fileName){
        
        ArrayList<Feedback> f = new <Feedback>ArrayList();
        String line = null;
        
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                    // Seperate date, feedback
                    String[] lineSplit = line.split(",");
                    // Seperate dd, mm, yyyy
                    String[] dateSplit = lineSplit[0].split("/");
                    Calendar date = new GregorianCalendar(Integer.parseInt(dateSplit[2]), 
                            Integer.parseInt(dateSplit[1]), Integer.parseInt(dateSplit[0]));
                    f.add(new Feedback(lineSplit[1], date));
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f;
    }
}

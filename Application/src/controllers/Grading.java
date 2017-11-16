package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Grading {
        public static ArrayList<String> getStudentGrades(String id){
                ArrayList<String> result = new <String>ArrayList();
                String submissionFileName = "";
                int i = 1;

                while((new File(submissionFileName = ("Assignment" + Integer.toString(i) + "Submission.csv")).exists())){
                        try {
                                FileReader fr = new FileReader(submissionFileName);
                                BufferedReader br = new BufferedReader(fr);
                                // Skip first cell
                                String line = br.readLine();
                                // Read all studentIDs
                                while ((line = br.readLine()) != null) {
                                        // Get line
                                        String[] assignmentLine = line.split(",");
                                        // Check if studentID is present
                                        if (assignmentLine[0].equals(id)){
                                                result.add(assignmentLine[getIndexofFinalMark(submissionFileName)]);
                                        }
                                }
                                br.close();
                                fr.close();
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                        i++;
                }
                return result;
        }

        public static int getIndexofFinalMark(String fileName){
                String[] line = {};
                try {
                        FileReader fr = new FileReader(fileName);
                        BufferedReader br = new BufferedReader(fr);
                        // Get First line
                        line = br.readLine().split(",");
                        
                        br.close();
                        fr.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
                // Last column index = length - 1
                return line.length - 1;
        }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Me
 */
public class GradingTest {
    
    static File a1;
    static File a2;
    static File a3;
    static File a4;
    
    
    public GradingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        TestFileHandler.setUp();
        
        String a1Name = "Assignment1Submission.csv";
        String a2Name = "Assignment2Submission.csv";
        String a3Name = "Assignment3Submission.csv";
        String a4Name = "Assignment4Submission.csv";
        a1 = new File(a1Name);
        a2 = new File(a2Name);
        a3 = new File(a3Name);
        a4 = new File(a4Name);
        
        try{
            // A1 empty
            FileWriter fw = new FileWriter(a1Name);
            BufferedWriter bf = new BufferedWriter(fw);
            
            bf.write("StudentID, Question 1 Answer, Average Mark, Number of Tries, Time (in seconds), Final Mark");
            
            bf.close();
            fw.close();
            
            // A2, studentID 1 has 1%
            FileWriter fw2 = new FileWriter(a2Name);
            BufferedWriter bf2 = new BufferedWriter(fw2);
            
            bf2.write("StudentID, Question 1 Answer, Average Mark, Number of Tries, Time (in seconds), Final Mark\n");
            bf2.write("1, Question 1 Answer, 0, 0, 0, 1");
            
            bf2.close();
            fw2.close();
            
            // A3, studentID 1 has 1%, studentID 2 has 2%
            FileWriter fw3 = new FileWriter(a3Name);
            BufferedWriter bf3 = new BufferedWriter(fw3);
            
            bf3.write("StudentID, Question 1 Answer, Average Mark, Number of Tries, Time (in seconds), Final Mark\n");
            bf3.write("1, Question 1 Answer, 0, 0, 0, 1\n");
            bf3.write("2, Question 1 Answer, 0, 0, 0, 2");
            
            bf3.close();
            fw3.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        a1.delete();
        a2.delete();
        a3.delete();
        a4.delete();
        TestFileHandler.tearDown();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getStudentGrades method, of class Grading.
     */
    @Test
    public void testGetStudentGrades() {
        // StudentID 3 has no marks.
        HashMap<Integer, String> result = Grading.getStudentGrades("3");
        assertEquals(result.size(), 0);
        
        // StudentID 1 has 1% on A2, 1% on A3
        result = Grading.getStudentGrades("1");
        System.out.println(result.get(2));
        assertEquals("1%", result.get(2));
        assertEquals("1%", result.get(3));
        
        // StudentID 2 has 2% on A3
        result = Grading.getStudentGrades("2");
        assertEquals("2%", result.get(3));
    }
}

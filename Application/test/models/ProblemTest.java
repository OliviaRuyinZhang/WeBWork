/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;
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
public class ProblemTest {
    
    public ProblemTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of equals method, of class Problem.
     */
    @Test
    public void testEquals() {
        // Same problem
        ArrayList<String> options = new <String>ArrayList();
        options.add("a");
        Problem x = new Problem(1, "Question", options, "a");
        Problem y = new Problem(1, "Question", options, "a");
        assertTrue(x.equals(y));
        
        // Different ID
        x = new Problem(1, "Question", options, "a");
        y = new Problem(2, "Question", options, "a");
        assertFalse(x.equals(y));
        
        // Different problemString
        x = new Problem(1, "Question", options, "a");
        y = new Problem(1, "no", options, "a");
        assertFalse(x.equals(y));
        
        // Differet options
        ArrayList<String> optionsTwo = new <String>ArrayList();
        optionsTwo.add("b");
        x = new Problem(1, "Question", options, "a");
        y = new Problem(1, "Question", optionsTwo, "a");
        assertFalse(x.equals(y));
        
        // Different solutions
        x = new Problem(1, "Question", options, "a");
        y = new Problem(1, "Question", options, "b");
        assertFalse(x.equals(y));
    }

    /**
     * Test of getProblemID method, of class Problem.
     */
    @Test
    public void testGetProblemID() {
        // Problem ID = 1
        ArrayList<String> options = new <String>ArrayList();
        options.add("a");
        Problem x = new Problem(1, "Question", options, "a");
        assertEquals(1, x.getProblemID());
        
        // Problem ID = 2
        x = new Problem(2, "Question", options, "a");
        assertEquals(2, x.getProblemID());
    }
    

    /**
     * Test of setProblemID method, of class Problem.
     */
    @Test
    public void testSetProblemID() {
        // Problem ID = 1
        ArrayList<String> options = new <String>ArrayList();
        options.add("a");
        Problem x = new Problem(1, "Question", options, "a");
        x.setProblemID(1);
        assertEquals(1, x.getProblemID());
        
        // Problem ID = 2
        x.setProblemID(2);
        assertEquals(2, x.getProblemID());
        
        // Problem ID = 800
        x.setProblemID(800);
        assertEquals(800, x.getProblemID());
    }

    /**
     * Test of getProblemString method, of class Problem.
     */
    @Test
    public void testGetProblemString() {
        // Problem String = Question
        ArrayList<String> options = new <String>ArrayList();
        options.add("a");
        Problem x = new Problem(1, "Question", options, "a");
        assertEquals("Question", x.getProblemString());
        
        // Problem ID = Question2
        x = new Problem(1, "Question2", options, "a");
        assertEquals("Question2", x.getProblemString());
    }

    /**
     * Test of setProblemString method, of class Problem.
     */
    @Test
    public void testSetProblemString() {
        // Problem String = Question
        ArrayList<String> options = new <String>ArrayList();
        options.add("a");
        Problem x = new Problem(1, "Question", options, "a");
        x.setProblemString("Question");
        assertEquals("Question", x.getProblemString());
        
        // Problem ID = Question2
        x.setProblemString("Question2");
        assertEquals("Question2", x.getProblemString());
        
        // Problem ID = Question3
        x.setProblemString("Question3");
        assertEquals("Question3", x.getProblemString());
    }

    /**
     * Test of getOptions method, of class Problem.
     */
    @Test
    public void testGetOptions() {
         // Options = ["a"]
        ArrayList<String> options = new <String>ArrayList();
        options.add("a");
        Problem x = new Problem(1, "Question", options, "a");
        ArrayList<String> expectedOptions = new <String>ArrayList();
        expectedOptions.add("a");
        assertEquals(expectedOptions, x.getOptions());
        
        // Options = ["a","b"]
        options.add("b");
        x = new Problem(1, "Question", options, "a");
        expectedOptions.add("b");
        assertEquals(expectedOptions, x.getOptions());
    }

    /**
     * Test of setOptions method, of class Problem.
     */
    @Test
    public void testSetOptions() {
        // Options = ["a"]
        ArrayList<String> options = new <String>ArrayList();
        options.add("a");
        Problem x = new Problem(1, "Question", options, "a");
        x.setOptions(options);
        ArrayList<String> expectedOptions = new <String>ArrayList();
        expectedOptions.add("a");
        assertEquals(expectedOptions, x.getOptions());
        
        // Options = ["a","b"]
        options.add("b");
        x.setOptions(options);
        expectedOptions.add("b");
        assertEquals(expectedOptions, x.getOptions());
        
        // Options = ["a","b", "c"]
        options.add("c");
        x.setOptions(options);
        expectedOptions.add("c");
        assertEquals(expectedOptions, x.getOptions());
    }

    /**
     * Test of getSolution method, of class Problem.
     */
    @Test
    public void testGetSolution() {
        // Solution = a
        ArrayList<String> options = new <String>ArrayList();
        options.add("a");
        Problem x = new Problem(1, "Question", options, "a");
        assertEquals("a", x.getSolution());
        
        // Solution = b
        x = new Problem(1, "Question2", options, "b");
        assertEquals("b", x.getSolution());
    }

    /**
     * Test of setSolution method, of class Problem.
     */
    @Test
    public void testSetSolution() {
        // Solution = "a"
        ArrayList<String> options = new <String>ArrayList();
        options.add("a");
        Problem x = new Problem(1, "Question", options, "a");
        x.setSolution("a");
        assertEquals("a", x.getSolution());
        
        // Solution = "b"
        x.setSolution("b");
        assertEquals("b", x.getSolution());
        
        // Solution = "c"
        x.setSolution("c");
        assertEquals("c", x.getSolution());
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lauri
 */
public class HumanTest {
    
    
    public HumanTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Test
    public void toStringMethodReturnsCorrectForm() {
        Human h = new Human("A", 20, 20);
        assertEquals("A, Age: 20, Adept",h.toString());
    }
    @Test
    public void toStringMethodReturnsCorrectFormWhenSkillIsOver60() {
        Human h = new Human("A", 20, 61);
        assertEquals("A, Age: 20, Masterous",h.toString());
    }
    
    @Test
    public void getFileStringReturnsValuesInCorrectForm() {
        Human h = new Human("A", 20, 61);
        assertEquals("A;20;61",h.getFileString());
    }
    @Test
    public void getFileStringReturnsValuesInCorrectFormWhenHumanIsCreatedWithJustName() {
        Human h = new Human("A");
        assertEquals("A;0;0",h.getFileString());
    }
    @Test
    public void compareToMethodSortsOlderFirst() {
        Human h1 = new Human("A", 20);
        Human h2 = new Human("A", 40);
        assertTrue(h1.compareTo(h2) >0);
    }
    @Test
    public void compareToMethodSortsYoungerLast() {
        Human h1 = new Human("A", 20);
        Human h2 = new Human("A", 40);
        assertTrue(h2.compareTo(h1) <0);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

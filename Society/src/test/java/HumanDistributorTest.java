/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import society.domain.Human;
import society.domain.HumanDistributor;

/**
 *
 * @author Lauri
 */
public class HumanDistributorTest {
    private HumanDistributor hd;
    
    public HumanDistributorTest() {
    }
    
    @Before
    public void setUp() {
        this.hd = new HumanDistributor();
    }
    
    @Test
    public void constructorCreatesListAndMap() {
        assertTrue(this.hd.getList() != null);
        assertTrue(this.hd.getWorkPlaces() != null);
    }
    @Test
    public void addingHumansWork() {
        Human h = new Human("D-503", 30);
        this.hd.addHuman(h);
        assertTrue(this.hd.getList().contains(h));
    }
    @Test
    public void addingHumansWorkDontGiveThemWorkplace() {
        Human h = new Human("D-503", 30);
        this.hd.addHuman(h);
        assertTrue(this.hd.getWorkPlaces().get(h) == null);
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

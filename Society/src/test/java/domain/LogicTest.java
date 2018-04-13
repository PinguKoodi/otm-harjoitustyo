/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import society.domain.Factories;
import society.domain.Human;
import society.domain.HumanDistributor;
import society.domain.Logic;

/**
 *
 * @author Lauri
 */
public class LogicTest {

    private Logic logic;
    private HumanDistributor hD;
    
    public LogicTest() {
    }
    
    @Before
    public void setUp() {
        this.hD = new HumanDistributor();
        this.logic = new Logic(hD);
    }

    @Test
    public void isUnemployedReturnsCorrectValue() {
        assertTrue(!this.logic.isUnemployed());
        this.hD.setHumanFactory(new Human("B", 30), Factories.FARM);
        assertTrue(!this.logic.isUnemployed());
        this.hD.addHuman(new Human("A", 30));
        assertTrue(this.logic.isUnemployed());
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

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
public class MultiplierTest {
    private Multiplier mult;
    private Distributor dist;
    private ResourceManager rM;
    
    public MultiplierTest() {
    }
    
    @Before
    public void setUp() {
        this.dist = new HumanDistributor();
        this.rM = new ResourceManager();
        this.mult = new Multiplier(rM, dist);
    }
    
    @Test
    public void getAllMultipliersReturnsCorrectlySizedArray() {
        assertEquals(4,this.mult.getAllMultipliers().length);
    }
    @Test
    public void getFarmMultipliersReturnsCorrectAmountWhenThereIsNoFarmers() {
        assertEquals(1,this.mult.getFarmMultiplier(),0.1);
    }
    @Test
    public void getFarmMultipliersReturnsCorrectAmountWhenThereIsNoTools() {
        this.dist.setWorkerUnitFactory(new Human("A",20), Factories.FARM);
        assertEquals(1.26,this.mult.getFarmMultiplier(),0.1);
    }
    @Test
    public void getFarmMultipliersReturnsCorrectAmountWhenThereIsLotOfTools() {
        this.dist.setWorkerUnitFactory(new Human("A",20), Factories.FARM);
        this.rM.setOneResource(1000, 1);
        assertEquals(3.6,this.mult.getFarmMultiplier(),0.1);
    }
    @Test
    public void getFarmMultiplierIsIncreasedByScience() {
        this.dist.setWorkerUnitFactory(new Human("A",20), Factories.FARM);
        this.rM.setOneResource(100, 2);
        assertEquals(1.28,this.mult.getFarmMultiplier(),0.1);
    }
    @Test
    public void getArmyMultiplierReturnsCorrectAmount() {
        this.dist.setWorkerUnitFactory(new Human("A",20), Factories.ARMY);
        assertEquals(1.0,this.mult.getArmyMultiplier(),0.1);
    }
    @Test
    public void toolsIncreasesArmyMultiplier() {
        this.dist.setWorkerUnitFactory(new Human("A",20), Factories.ARMY);
        this.rM.setOneResource(100, 1);
        assertEquals(2.0,this.mult.getArmyMultiplier(),0.1);
    }
    @Test
    public void soldierMultiplierReturnsCorrectAmountWhenThereAreNoAdults() {
        assertEquals(0.7,this.mult.getFactoryMultiplier(),0.1);
    }
   

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

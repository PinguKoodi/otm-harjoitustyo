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
public class ResourceManagerTest {
    
    private Logic l;
    private Distributor d;
    private ResourceManager rM;
    
    public ResourceManagerTest() {
    }
    
    @Before
    public void setUp() {
        rM = new ResourceManager();
    }
    
    @Test
    public void initializeResourcesAddsCorrectAmounts() {
        this.rM.initializeResources();
        assertEquals(100.0,this.rM.getFood(),0.1);
        assertEquals(0.0,this.rM.getTools(),0.1);
        assertEquals(0.0,this.rM.getScience(),0.1);
        assertEquals(0.0,this.rM.getGuns(),0.1);
    }
    @Test
    public void settingOneResourceWorks() {
        this.rM.setOneResource(20.0, 2);
        assertEquals(20.0,this.rM.getScience(),0.1);
    }
    @Test
    public void settingAllResourcesWorks() {
        double[] table = new double[4];
        table[3] = 20.0;
        this.rM.setResources(table);
        assertEquals(20.0,this.rM.getGuns(),0.001);
    }
    @Test
    public void resourceLossWorks() {
        this.rM.initializeResources();
        this.rM.setOneResource(1000, 1);
        this.rM.calculateResourceLoss(0);
        assertEquals(95.0,this.rM.getFood(),0.1);
        assertEquals(950.0,this.rM.getTools(),0.1);
    }
    
    

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import society.data.SaveOperator;
import society.domain.Factories;
import society.domain.Human;
import society.domain.HumanDistributor;
import society.domain.Logic;

/**
 *
 * @author Lauri
 */
public class LogicTest {
    //SaveOperator to test Locally
    static public class Operator implements SaveOperator{
        private double[] values;
        private Map<WorkerUnit, Factories> workplaces;
        private Logic logic;
        
        public Operator (Logic l) {
            this.logic = l;
        }
        
        @Override
        public boolean saveGame() {
            this.values = new double[6];
            for(int i = 0;i<6;i++) {
                if(i<4) {
                this.values[i] = this.logic.getResources()[i];
                } else if( i<5) {
                    this.values[i] = this.logic.getYear();
                } else {
                    this.values[i] = this.logic.getHappiness();
                }
            }
            this.workplaces = this.logic.gethD().getWorkPlaces();
            return true;
            
        }
        @Override
        public void switchToLoadFromSave() {
            
        }
        @Override
        public String getGuideText() {
            return "TestingText"; 
        }
        @Override
        public double[] readValuesFromSave() {
            return this.values;
        }
        @Override
        public Map<WorkerUnit, Factories> readHumansFromSave() {
            return this.workplaces;
        }
        public void changeSaveState(double[] values) {
            this.values = values;
        }
        
    }
    private Logic logic;
    private Distributor hD;
    private Operator operator;

    public LogicTest() {
    }

    @Before
    public void setUp() {
        this.hD = new HumanDistributor();
        this.logic = new Logic(hD);
        this.operator = new Operator(this.logic);
        this.logic.setOperator(this.operator);
    }

    @Test
    public void isUnemployedReturnsCorrectValue() {
        assertTrue(!this.logic.isUnemployed());
        this.hD.setWorkerUnitFactory(new Human("B", 30), Factories.FARM);
        assertTrue(!this.logic.isUnemployed());
        this.hD.addWorkerUnit(new Human("A", 30));
        assertTrue(this.logic.isUnemployed());
    }

    @Test
    public void startingNewGameAddsFood() {
        this.logic.startGame(false);
        assertTrue(this.logic.getResources()[0] == 100.0);
    }

    @Test
    public void startingNewGameAddsHumans() {
        this.logic.startGame(false);
        assertTrue(!this.logic.gethD().getList().isEmpty());
    }

    @Test
    public void startingNewGameAssingsHumansToFarm() {
        this.logic.startGame(false);
        assertTrue(!this.logic.gethD().getListOfWorkersAtPlace(Factories.FARM).isEmpty());
    }

    @Test
    public void startingNewGameHavesYearAsZero() {
        this.logic.startGame(false);
        assertTrue(this.logic.getYear() == 0);
    }

    @Test
    public void startingNewGameAndEndingTurnIncreasesYear() {
        this.logic.startGame(false);
        this.logic.endTurn();
        assertTrue(this.logic.getYear() == 1);
    }

    @Test
    public void assignWorkerAssignsUnemployedHumans() {
        Human h = new Human("A", 20);
        this.hD.addWorkerUnit(h);
        this.logic.assignWorker(Factories.FARM);
        assertTrue(this.logic.gethD().getListOfWorkersAtPlace(Factories.FARM).contains(h));
    }

    @Test
    public void assigningWorkerIncreasesProduction() {
        Human h = new Human("A", 20);
        this.hD.addWorkerUnit(h);
        this.logic.assignWorker(Factories.FARM);
        assertTrue(this.logic.getProduction()[0] > 0);
    }

    @Test
    public void getResourcesDisplayCorrectlyTrimsTheAmount() {
        Human h = new Human("A", 20);
        this.hD.addWorkerUnit(h);
        this.logic.assignWorker(Factories.FARM);
        //this.logic.endTurn();
        assertEquals(100.0, this.logic.getResourcesDisplay()[0], 0.5);
    }

    @Test
    public void calculateHappinessChangesHappiness() {
        this.logic.startGame(false);
        this.logic.endTurn();
        assertEquals(47.0, this.logic.getHappiness(), 0);
    }

    @Test
    public void calculateHappinessChangesHappinessWhenFoodIsOut() {
        this.logic.startGame(false);
        Human h = new Human("A", 20);
        Human h1 = new Human("B", 20);
        Human h2 = new Human("C", 20);
        this.hD.addWorkerUnit(h);
        this.hD.addWorkerUnit(h1);
        this.hD.addWorkerUnit(h2);
        this.logic.getResourceManager().setOneResource(-1, 0);
        this.logic.endTurn();
        assertEquals(45.0, this.logic.getHappiness(), 0);
    }

    @Test
    public void getProductionReturnsCorrectProductionAmounts() {
        this.logic.startGame(false);
        this.logic.assignWorker(Factories.FACTORY);
        this.logic.assignWorker(Factories.FACTORY);
        this.logic.assignWorker(Factories.FACTORY);
        this.logic.assignWorker(Factories.LABORATORY);
        double[] prods = this.logic.getProduction();
        assertEquals(0.1, prods[2], 0.05);
        assertEquals(0, prods[3], 0.05);
        assertEquals(1.1, prods[1], 0.05);
        assertEquals(-5.6, prods[0], 0.05);
    }

    @Test
    public void getWorkplaceAsStringMethodReturnsCorrectString() {
        Human h = new Human("A", 20);
        Human h1 = new Human("B", 20);
        this.hD.setWorkerUnitFactory(h, Factories.FARM);
        this.hD.addWorkerUnit(h1);
        assertEquals("null", this.logic.getWorkplaceAsString(h1));
        assertEquals("FARM", this.logic.getWorkplaceAsString(h));
    }

    @Test
    public void endingTurnChangesResourcesCorrectly() {
        Human h = new Human("A", 20);
        Human h1 = new Human("B", 20);
        Human h2 = new Human("C", 20);
        Human h3 = new Human("D", 20);
        Human h4 = new Human("E", 20);
        this.hD.setWorkerUnitFactory(h1, Factories.FARM);
        this.hD.setWorkerUnitFactory(h4, Factories.FARM);
        this.hD.setWorkerUnitFactory(h3, Factories.FARM);
        this.hD.setWorkerUnitFactory(h2, Factories.LABORATORY);
        this.logic.endTurn();
        assertTrue(this.logic.getResources()[0] > 0);
        assertTrue(this.logic.getResources()[2] > 0);
        assertTrue(this.logic.getResources()[3] == 0);
    }

    @Test
    public void endingTurnIncreasesToolsCorrectly() {
        Human h = new Human("A", 20);
        this.hD.setWorkerUnitFactory(h, Factories.FACTORY);
        this.logic.endTurn();
        assertTrue(this.logic.getResources()[1] > 0);
    }
    
    @Test
    public void loadingGameFromSaveReturnsValuesCorrectly() {
        this.hD.setWorkerUnitFactory(new Human("A",20), Factories.FARM);
        this.operator.saveGame();
        this.operator.changeSaveState(new double[]{130.0,50,10,0,5,50});
        this.logic.loadSaveData();
        double[] resources = this.logic.getResources();
        assertEquals(130.0,resources[0],0.1);
        assertEquals(50.0,resources[1],0.1);
        assertEquals(10.0,resources[2],0.1);
        assertEquals(0,resources[3],0.1);
        assertEquals(5,this.logic.getYear(),0.1);
        assertEquals(50,this.logic.getHappiness(),0.1);
    }
    @Test
    public void loadingGameFromSaveSetsHumansCorrectly() {
        Human h1 = new Human("A",20);
        this.hD.setWorkerUnitFactory(h1, Factories.FARM);
        this.operator.saveGame();
        this.logic.startGame(true);
        assertEquals("FARM",this.logic.getWorkplaceAsString(h1));
    }
    @Test
    public void loadingGameFromSaveDoesntAddAdditionalHumans() {
        Human h1 = new Human("A",20);
        this.hD.setWorkerUnitFactory(h1, Factories.FARM);
        this.operator.saveGame();
        this.logic.startGame(true);
        assertEquals(1,this.logic.gethD().getPopulation());
    }
    @Test
    public void getProductinDisplayReturnsProducitonInCorrectForm() {
        this.logic.startGame(false);
        this.logic.assignWorker(Factories.FACTORY);
        this.logic.assignWorker(Factories.FACTORY);
        this.logic.assignWorker(Factories.FACTORY);
        this.logic.assignWorker(Factories.LABORATORY);
        double[] prods = this.logic.getProductionDisplay();
        assertEquals(0.1, prods[2], 0.0);
        assertEquals(0, prods[3], 0.0);
        assertEquals(1.1, prods[1], 0.0);
        assertEquals(-5.6, prods[0], 0.0);
    }
    @Test
    public void saveToFileReturnsCorrectvalue() {
        this.logic.startGame(false);
        assertTrue(this.logic.saveToFile());
    }
    
    
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}


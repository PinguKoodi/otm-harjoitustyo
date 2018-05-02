package society.domain;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import society.domain.Factories;
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
        this.hd.addWorkerUnit(h);
        assertTrue(this.hd.getList().contains(h));
    }

    @Test
    public void addingHumansWorkDontGiveThemWorkplace() {
        Human h = new Human("D-503", 30);
        this.hd.addWorkerUnit(h);
        assertTrue(this.hd.getWorkPlaces().get(h) == null);
    }

    @Test
    public void settingFactoryWorks() {
        Human h = new Human("D-503", 30);
        Human h2 = new Human("D-54", 30);
        this.hd.addWorkerUnit(h);
        this.hd.setWorkerUnitFactory(h, Factories.FARM);
        assertTrue(this.hd.getWorkPlaces().get(h) == Factories.FARM);
        this.hd.setWorkerUnitFactory(h2, Factories.FACTORY);
        assertTrue(this.hd.getWorkPlaces().get(h2) == Factories.FACTORY);
        assertTrue(this.hd.getList().contains(h2));
    }

    @Test
    public void getNumberOfWorkersReturnsZeroCorrectly() {
        assertEquals(0, this.hd.getNumberOfWorkers()[0]);
        assertEquals(0, this.hd.getNumberOfWorkers()[1]);
        assertEquals(0, this.hd.getNumberOfWorkers()[2]);
        assertEquals(0, this.hd.getNumberOfWorkers()[3]);
    }

    @Test
    public void getNumberOfWorkersReturnsCorrectAmounts() {
        this.hd.setWorkerUnitFactory(new Human("I"), Factories.FARM);
        this.hd.setWorkerUnitFactory(new Human("I"), Factories.ARMY);
        this.hd.setWorkerUnitFactory(new Human("I"), Factories.LABORATORY);
        this.hd.setWorkerUnitFactory(new Human("I"), Factories.FARM);
        this.hd.setWorkerUnitFactory(new Human("I"), Factories.FACTORY);
        this.hd.setWorkerUnitFactory(new Human("I"), Factories.FARM);
        this.hd.setWorkerUnitFactory(new Human("I"), Factories.FACTORY);
        this.hd.addWorkerUnit(new Human("U"));
        assertEquals(3, this.hd.getNumberOfWorkers()[0]);
        assertEquals(2, this.hd.getNumberOfWorkers()[1]);
        assertEquals(1, this.hd.getNumberOfWorkers()[2]);
        assertEquals(1, this.hd.getNumberOfWorkers()[3]);
    }

    @Test
    public void getPopulationReturnsCorrectAmount() {
        this.hd.addWorkerUnit(new Human("D-503", 30));
        this.hd.addWorkerUnit(new Human("D-504", 30));
        this.hd.addWorkerUnit(new Human("D-505", 30));
        assertEquals(3, this.hd.getPopulation());
    }

    @Test
    public void getAdultsReturnsCorrectAmount() {
        this.hd.addWorkerUnit(new Human("D-503", 10));
        this.hd.addWorkerUnit(new Human("D-504", 30));
        this.hd.addWorkerUnit(new Human("D-505", 30));
        assertEquals(2, this.hd.numberOfAdults());
    }

    @Test
    public void getChildrenReturnsCorrectAmount() {
        this.hd.addWorkerUnit(new Human("D-503", 10));
        this.hd.addWorkerUnit(new Human("D-504", 10));
        this.hd.addWorkerUnit(new Human("D-505", 30));
        assertEquals(2, this.hd.numberOfChilds());
    }

    @Test
    public void getUnemployedReturnsNullCorrectly() {
        this.hd.setWorkerUnitFactory(new Human("D-505", 30), Factories.FARM);
        assertEquals(null, this.hd.getUnemployed());
    }

    @Test
    public void getUnemployedReturnsUnemployedHuman() {
        Human testSubject = new Human("D-505", 30);
        this.hd.addWorkerUnit(testSubject);
        assertEquals(testSubject, this.hd.getUnemployed());
        this.hd.setWorkerUnitFactory(new Human("I"), Factories.FARM);
        this.hd.setWorkerUnitFactory(new Human("I"), Factories.ARMY);
        this.hd.addWorkerUnit(new Human("D-503", 10));
        assertEquals(testSubject, this.hd.getUnemployed());
    }

    @Test
    public void getListOfWorkersReturnsCorrectSizedList() {
        Human h1 = new Human("I");
        Human h2 = new Human("I");
        this.hd.setWorkerUnitFactory(h1, Factories.FARM);
        this.hd.setWorkerUnitFactory(h2, Factories.ARMY);
        this.hd.addWorkerUnit(new Human("D-503", 10));
        this.hd.addWorkerUnit(new Human("D-503", 30));
        List testList = this.hd.getListOfWorkers();
        assertEquals(2, testList.size());
    }

    @Test
    public void getListOfWorkersReturnsListWithRightWorkers() {
        Human h1 = new Human("I");
        Human h2 = new Human("I");
        this.hd.setWorkerUnitFactory(h1, Factories.FARM);
        this.hd.setWorkerUnitFactory(h2, Factories.ARMY);
        Human h3 = new Human("D-503", 10);
        this.hd.addWorkerUnit(h3);
        this.hd.addWorkerUnit(new Human("D-503", 30));
        List testList = this.hd.getListOfWorkers();
        assertTrue(testList.contains(h2));
        assertTrue(testList.contains(h1));
        assertTrue(!testList.contains(h3));
    }

    @Test
    public void listOfWorkersAtPlaceReturnsCorrectListsWithFarm() {
        Human h1 = new Human("I");
        Human h2 = new Human("I");
        this.hd.setWorkerUnitFactory(h1, Factories.FARM);
        this.hd.setWorkerUnitFactory(h2, Factories.ARMY);
        Human h3 = new Human("D-503", 10);
        this.hd.addWorkerUnit(h3);
        this.hd.addWorkerUnit(new Human("D-503", 30));
        List testList = this.hd.getListOfWorkersAtPlace(Factories.FARM);
        assertEquals(1, testList.size());
        assertTrue(testList.contains(h1));
    }

    @Test
    public void makingPeopleOneYearOlderMakesPeopleAge() {
        Human h1 = new Human("I", 30);
        Human h2 = new Human("I", 10);
        this.hd.setWorkerUnitFactory(h1, Factories.FARM);
        this.hd.addWorkerUnit(h2);
        this.hd.makeOneYearOlder();
        assertEquals(31, h1.getAge());
        assertEquals(11, h2.getAge());
    }

    @Test
    public void makingPeopleOneYearOlderGivesPeopleExperience() {
        Human h1 = new Human("I", 30);
        Human h2 = new Human("I", 20);
        this.hd.setWorkerUnitFactory(h1, Factories.FARM);
        this.hd.setWorkerUnitFactory(h2, Factories.ARMY);
        this.hd.makeOneYearOlder();
        assertEquals(1, h1.getExperience());
        assertEquals(1, h2.getExperience());
    }

    @Test
    public void makingPeopleOneYearOlderWillKillPeopleOverHundred() {
        Human h1 = new Human("I", 100);
        this.hd.addWorkerUnit(h1);
        this.hd.makeOneYearOlder();
        assertTrue(!this.hd.getList().contains(h1));
    }

    @Test
    public void numberOfUnemployedReturnsCorrectAmount() {
        this.hd.setWorkerUnitFactory(new Human("I", 50), Factories.FARM);
        this.hd.setWorkerUnitFactory(new Human("I", 20), Factories.ARMY);
        assertEquals(0, this.hd.numberOfUnemployed());
        this.hd.addWorkerUnit(new Human("Lazy", 20));
        assertEquals(1, this.hd.numberOfUnemployed());
    }

    @Test
    public void numberOfUnemployedDoesntCountKids() {
        this.hd.setWorkerUnitFactory(new Human("I", 20), Factories.ARMY);
        this.hd.addWorkerUnit(new Human("Lazy", 20));
        this.hd.addWorkerUnit(new Human("Lazy2", 20));
        this.hd.addWorkerUnit(new Human("Child", 10));
        assertEquals(2, this.hd.numberOfUnemployed());
    }

    @Test
    public void getHumansDisplayReturnsCorrectString() {
        Human h1 = new Human("A", 20);
        Human h2 = new Human("B", 20);
        this.hd.addWorkerUnit(h1);
        this.hd.setWorkerUnitFactory(h2, Factories.FARM);
        String display = this.hd.getWorkerUnitsDisplay();
        assertEquals("Total population: 2\nA, Age: 20, Unskilled, unemployed\nB, Age: 20, Unskilled, FARM\n", display);
    }

    @Test
    public void killPeopleWontKillIfThereIsNoShortage() {
        Human h1 = new Human("A", 20);
        Human h2 = new Human("B", 20);
        this.hd.addWorkerUnit(h1);
        this.hd.setWorkerUnitFactory(h2, Factories.FARM);
        this.hd.kill(10);
        assertEquals(2, this.hd.getList().size());
    }

    @Test
    public void killPeopleRemovesPeople() {
        Human h1 = new Human("A", 20);
        Human h2 = new Human("B", 20);
        this.hd.addWorkerUnit(h1);
        this.hd.setWorkerUnitFactory(h2, Factories.FARM);
        this.hd.kill(-1);
        assertEquals(1, this.hd.getList().size());
    }
    @Test
    public void soldierPercentReturnCorrectValueWhenThereIsSoldiers() {
        this.hd.addWorkerUnit(new Human("A",20));
        this.hd.setWorkerUnitFactory(new Human("I", 20), Factories.ARMY);
        assertEquals(0.50,this.hd.soldierPercent(),0.0);
    }
    @Test
    public void soldierPercentReturnCorrectValueWhenThereIsNotSoldiers() {
        this.hd.addWorkerUnit(new Human("A",20));
        this.hd.setWorkerUnitFactory(new Human("I", 20), Factories.FARM);
        assertEquals(0.0,this.hd.soldierPercent(),0.0);
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

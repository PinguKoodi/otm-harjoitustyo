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
        this.hD.addHuman(h);
        this.logic.assignWorker(Factories.FARM);
        assertTrue(this.logic.gethD().getListOfWorkersAtPlace(Factories.FARM).contains(h));
    }

    @Test
    public void assigningWorkerIncreasesProduction() {
        Human h = new Human("A", 20);
        this.hD.addHuman(h);
        this.logic.assignWorker(Factories.FARM);
        assertTrue(this.logic.getProduction()[0] > 0);
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

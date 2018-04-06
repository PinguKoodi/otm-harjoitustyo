/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;

import java.util.List;
import java.util.Random;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author pyylauri
 */
public class Logic {

    private HumanDistributor hD;
    private double[] resources;
    private int year;
    private Random rng;
    private Multiplier mult;
    private double happiness;

    public Logic(HumanDistributor hD) {
        this.hD = hD;
        this.resources = new double[4];
        this.resources[0] = 100;
        this.year = 0;
        this.rng = new Random();
        this.mult = new Multiplier(this, this.hD);
        this.happiness = 50;
    }

    public void createFirstHumans() {
        for (int i = 0; i < 12; i++) {
            Human h = new Human("A" + i, 18 + i);
            if (i % 2 == 0) {
                this.hD.setHumanFactory(h, Factories.FARM);
            } else {
                this.hD.setHumanFactory(h, Factories.FACTORY);
            }

        }
        this.hD.addHuman(new Human("D12", 20));
    }

    public void assignWorker(Factories f) {
        if (this.isUnemployed()) {
            Human h = this.hD.getUnemployed();
            this.hD.setHumanFactory(h, f);
        }
    }

    public void endTurn() {
        int adults = this.hD.numberOfAdults();
//        List<Human> listOfWorkers = this.hD.getListOfWorkers();
//        for(Human h: listOfWorkers) { 
//        }
//Vaihtoehtoinen ratkaisu
        boolean foodIsOut = this.resources[0] < 0;
        int[] numberOfWorkers = this.hD.getNumberOfWorkers();
        // WIP
        for (int i = 0; i < 4; i++) {
            double multiplier = 3;
            if (i == 1) {
                multiplier = this.mult.getFactoryMultiplier();
            }
            this.resources[i] = this.resources[i] + numberOfWorkers[i] * multiplier;
        }
        this.resources[0] = this.resources[0] - this.hD.getList().size();
        calculateHappiness(foodIsOut);
        if (foodIsOut && this.resources[0] < 0) {
            this.hD.kill(this.resources[0]);
        }
        if (this.hD.getList().isEmpty()) {
            return;
        }
        this.hD.makeOneYearOlder();
        int babies = adults / 10;
        for (int i = 0; i < babies; i++) {
            this.hD.addHuman(new Human("A" + this.rng.nextInt(1000)));
        }
        year++;
        for (int i = 0; i < 4; i++) {
            System.out.println(i + ". " + "Workers: " + numberOfWorkers[i] + ", Storage: " + resources[i]);
        }
        System.out.println("Year " + year + "   Unemployed: " + this.hD.numberOfUnemployed() + "Children " + this.hD.numberOfChilds());

    }

    public boolean isUnemployed() {
        return this.hD.numberOfUnemployed() > 0;
    }

    public double[] getResources() {
        return resources;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private void calculateHappiness(boolean foodIsOut) {
        double amountOfPeople = this.hD.getList().size();
        if (foodIsOut) {
            this.happiness -= 10;
        } else if (resources[0] > amountOfPeople) {
            this.happiness += 5;
        }
        this.happiness += (resources[1] / amountOfPeople) - 1;
        this.happiness += resources[2] - year * 2;
        if (this.hD.getListOfWorkersAtPlace(Factories.ARMY).size() > amountOfPeople * 0.2) {
            this.happiness += -5;
        }

    }
}

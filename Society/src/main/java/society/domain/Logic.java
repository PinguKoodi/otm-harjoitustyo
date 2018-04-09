/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;

import java.text.DecimalFormat;
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
        for (int i = 1; i < 4; i++) {
            this.resources[i] = 0;
        }
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
                this.hD.addHuman(h);
            }

        }
        this.hD.addHuman(new Human("D-503", 20));
        this.hD.addHuman(new Human("I-330", 20));
        this.hD.addHuman(new Human("O-90", 20));
        this.hD.addHuman(new Human("U-180", 20));
    }

    public void assignWorker(Factories f) {
        if (this.isUnemployed()) {
            Human h = this.hD.getUnemployed();
            this.hD.setHumanFactory(h, f);
        }
    }

    public boolean endTurn() {
        int adults = this.hD.numberOfAdults();
//        List<Human> listOfWorkers = this.hD.getListOfWorkers();
//        for(Human h: listOfWorkers) { 
//        }
//Vaihtoehtoinen ratkaisu
        boolean foodIsOut = this.resources[0] < 0;
        int[] numberOfWorkers = this.hD.getNumberOfWorkers();
        // WIP
        for (int i = 0; i < 4; i++) {
            if (numberOfWorkers[i] > 0) {
                double multiplier = this.mult.getMultiplier(i);
                this.resources[i] = this.resources[i] + (numberOfWorkers[i] * multiplier);
            }
        }
        this.resources[0] = this.resources[0] - this.hD.getList().size();
        calculateHappiness(foodIsOut);
        if (foodIsOut && this.resources[0] < 0) {
            this.hD.kill(this.resources[0]);
        }
        if (this.hD.getList().isEmpty()) {
            return true;
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
        return false;
    }

    public boolean isUnemployed() {
        return this.hD.numberOfUnemployed() > 0;
    }

    public double[] getResources() {
        return resources;
    }

    public double[] getResourcesDisplay() {
        double[] trimmed = new double[4];
        for (int i = 0; i < 4; i++) {
            trimmed[i] = Math.round(this.resources[i] * 10);
            trimmed[i] = trimmed[i] / 10;
        }
        return trimmed;
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
        this.happiness += ((resources[1] / amountOfPeople) - 1);
        this.happiness += (resources[2] - year * 2);
        if (this.hD.getListOfWorkersAtPlace(Factories.ARMY).size() > amountOfPeople * 0.2) {
            this.happiness += -5;
        }
        this.happiness = Math.max(0, happiness);
        this.happiness = Math.min(100, happiness);

    }

    public double getHappiness() {
        double temp = Math.round(happiness * 10);
        temp /= 10;
        return temp;
    }
}

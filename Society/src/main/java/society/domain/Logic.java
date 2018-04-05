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
    
    
    public Logic(HumanDistributor hD) {
        this.hD = hD;
        this.resources = new double[4];
        this.resources[0] = 100;
        this.year = 0;
        this.rng = new Random();
        this.mult = new Multiplier(this, this.hD);
    }
    public void assignWorker(Factories f) {
        Human h = this.hD.getUnemployed();
        this.hD.setHumanFactory(h, f);
    }
    
    public void endTurn() {
        int adults = this.hD.numberOfAdults();
//        List<Human> listOfWorkers = this.hD.getListOfWorkers();
//        for(Human h: listOfWorkers) { 
//        }
//Vaihtoehtoinen ratkaisu
        boolean foodIsOut = this.resources[0] <0;
        int[] numberOfWorkers = this.hD.getNumberOfWorkers();
        // WIP
        for(int i = 0;i<4;i++) {
            double multiplier = 2;
            if(i== 1) {
                multiplier = this.mult.getFactoryMultiplier();
            }
            this.resources[i] = this.resources[i] + numberOfWorkers[i]* multiplier;
        }
        this.resources[0] = this.resources[0] - this.hD.getList().size();
        if(foodIsOut && this.resources[0] < 0) {
            this.hD.kill(this.resources[0]);
        }
        this.hD.makeOneYearOlder();
        int babies = adults/10;
        for(int i = 0;i<babies;i++) {
            this.hD.addHuman(new Human("A" + this.rng.nextInt(1000)));
        }
        year++;
        for(int i = 0;i<4;i++) {
            System.out.println(i+". " + "Workers: " + numberOfWorkers[i] + ", Storage: " + resources[i]);
        }
        System.out.println("Year " + year + "   Unemployed: "+ this.hD.numberOfUnemployed() + "Children " + this.hD.numberOfChilds());
        
    }
    
    public boolean isUnemployed() {
        return this.hD.numberOfUnemployed()>0;
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
    
}

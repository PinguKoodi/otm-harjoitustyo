/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;

import java.util.List;

/**
 *
 * @author pyylauri
 */
public class Logic {
    private HumanDistributor hD;
    private double[] resources;
    
    public Logic(HumanDistributor hD) {
        this.hD = hD;
        this.resources = new double[4];
        this.resources[0] = 100;
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
        double multiplier = 3; // WIP
        for(int i = 0;i<4;i++) {
            this.resources[i] = this.resources[i] + numberOfWorkers[i]* multiplier;
        }
        if(foodIsOut && this.resources[0] < 0) {
            this.hD.kill(this.resources[0]);
        }
        int babies = adults/10;
        for(int i = 0;i<babies;i++) {
            this.hD.addHuman(new Human("Random"));
        }
        
    }
    
    public boolean isUnemployed() {
        return this.hD.numberOfUnemployed()>0;
    }

    public double[] getResources() {
        return resources;
    }
    
}

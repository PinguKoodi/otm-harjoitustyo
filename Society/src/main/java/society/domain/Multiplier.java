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
public class Multiplier {
    private Logic l;
    private HumanDistributor hD;
    
    public Multiplier(Logic l, HumanDistributor hD) {
        this.l = l;
        this.hD = hD;
    }
    public double getFactoryMultiplier() {
        double science = this.l.getResources()[2];
        science /= 100;
        int totalXP = 0;
        List<Human> list = this.hD.getListOfWorkersAtPlace(Factories.FACTORY);
        for(Human h : this.hD.getListOfWorkersAtPlace(Factories.FACTORY)) {
            totalXP += h.getExperience();
        }
        double totalMult = 1;
        if(list.size() != 0) {
            totalMult = totalXP / list.size();
        }
        totalMult *= science;
        double soldierPercent = this.hD.getNumberOfWorkers()[3]/this.hD.numberOfAdults();
        if(soldierPercent > 0.15 || soldierPercent < 0.05) {
            totalMult *= 0.9;
        }
        return totalMult;
    }
    public double getFarmMultiplier() {
        return 2;
    }
}

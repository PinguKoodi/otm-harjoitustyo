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

    public double getMultiplier(int i) {
        switch (i) {
            case 0:
                return this.getFarmMultiplier();
            case 1:
                return this.getFactoryMultiplier();
            case 2:
                return this.getLaboratoryMultiplier();
            case 3:
                return this.getArmyMultiplier();
        }
        return 1;
    }

    

    public double getFarmMultiplier() {
        double mult = 2.0;
        double experience = 0;
        int farmers = this.hD.getListOfWorkersAtPlace(Factories.FARM).size();
        for (Human h : this.hD.getListOfWorkersAtPlace(Factories.FARM)) {
            experience += h.getExperience();
        }
        experience = experience/farmers/100;
        experience += 1;
        mult *= experience;
        mult *= soldierMultiplier();
        double tools = this.l.getResources()[1];
        if (tools < farmers) {
            mult *= 0.7;
        } else {
            tools = tools / farmers;
            tools /= 50;
            tools += 1;
            mult *= tools;
        }
        return mult;
    }
    
    public double getFactoryMultiplier() {
        double science = this.l.getResources()[2];
        science /= 100;
        science += 1;
        int totalXP = 0;
        List<Human> list = this.hD.getListOfWorkersAtPlace(Factories.FACTORY);
        for (Human h : this.hD.getListOfWorkersAtPlace(Factories.FACTORY)) {
            totalXP += h.getExperience();
        }
        double totalMult = 1;
        if (!list.isEmpty()) {
            double temp = totalXP / list.size();
            temp /= 100;
            temp += 1;
            totalMult *= temp;
        }
        totalMult *= science;
        totalMult *= soldierMultiplier();
        return totalMult;
    }

    public double getLaboratoryMultiplier() {
        double mult = 0.1;
        double experience = 0;
        int scientists = this.hD.getListOfWorkersAtPlace(Factories.LABORATORY).size();
        for (Human h : this.hD.getListOfWorkersAtPlace(Factories.LABORATORY)) {
            experience = experience + Math.pow(h.getExperience() * 0.1, 2);
        }
        experience /= scientists;
        experience /= 100;
        experience++;
        mult *= experience;
        return mult;
    }

    public double getArmyMultiplier() {
        double mult = 1;
        double tools = this.l.getResources()[1];
        int soldiers = this.hD.getListOfWorkersAtPlace(Factories.ARMY).size();
        tools = tools / soldiers;
        tools /= 100;
        tools++;
        tools = Math.min(tools, 2);
        mult *= tools;
        return mult;
    }
    private double soldierMultiplier() {
        if (this.hD.numberOfAdults() != 0) {
            double soldierPercent = this.hD.getNumberOfWorkers()[3] / this.hD.numberOfAdults();
            if (soldierPercent > 0.2 || soldierPercent < 0.05) {
                return 0.9;
            }
        }
        return 1;
    }
}

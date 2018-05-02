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

    private ResourceManager resourceM;
    private Distributor hD;

    public Multiplier(ResourceManager rM, Distributor dist) {
        this.resourceM = rM;
        this.hD = dist;
    }

    /**
     * Calculates and returns all four multipliers for different factories
     *
     * @return double array which contains four different multipliers
     */
    public double[] getAllMultipliers() {
        double[] mults = new double[4];
        for (int i = 0; i < 4; i++) {
            int[] numberOfWorkers = this.hD.getNumberOfWorkers();
            if (numberOfWorkers[i] > 0) {
                mults[i] = this.getMultiplier(i);
            } else {
                mults[i] = 0;
            }
        }
        return mults;
    }

    /**
     * Returns a specific multiplier
     *
     * @param i the factory which multiplier is wished to be computed
     * @return double valued multiplier for the specific factory
     */
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

    /**
     * Calculates and return production multiplier for Farm
     *
     * @return multiplier for farm
     */
    public double getFarmMultiplier() {
        double experience = 0;
        List<WorkerUnit> farmers = this.hD.getListOfWorkersAtPlace(Factories.FARM);
        if (farmers.isEmpty()) { 
            return 1; 
        }
        for (WorkerUnit h : farmers) {
            experience += h.getExperience();
        }
        double mult = 2.0 * ((experience / farmers.size() / 100) + 1);
        mult *= soldierMultiplier();
        double tools = this.resourceM.getTools();
        if (tools < farmers.size() * 0.875) {
            mult *= 0.7;
        } else {
            mult *= Math.min(tools / farmers.size() * 0.8, 2);
        }
        mult *= (Math.sqrt(this.resourceM.getScience()) / 500) + 1;
        return mult;
    }

    /**
     * Calculates and return production multiplier for Factory
     *
     * @return multiplier for Factory
     */
    public double getFactoryMultiplier() {
        double science = this.resourceM.getScience();
        science /= 80;
        science += 1;
        int totalXP = 0;
        List<WorkerUnit> list = this.hD.getListOfWorkersAtPlace(Factories.FACTORY);
        for (WorkerUnit h : list) {
            totalXP += h.getExperience();
        }
        double totalMult = 0.7;
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

    /**
     * Calculates and return production multiplier for Laboratory
     *
     * @return multiplier for Laboratory
     */
    public double getLaboratoryMultiplier() {
        double mult = 0.1;
        double experience = 0;
        List<WorkerUnit> list = this.hD.getListOfWorkersAtPlace(Factories.LABORATORY);
        int scientists = list.size();
        for (WorkerUnit h : list) {
            experience = experience + Math.pow(h.getExperience() * 0.1, 2);
        }
        experience /= scientists;
        experience /= 100;
        experience++;
        mult *= experience;
        mult *= ((scientists / 100) + 1);
        return mult;
    }

    /**
     * Calculates and return production multiplier for Army
     *
     * @return multiplier for Army
     */
    public double getArmyMultiplier() {
        double mult = 1;
        double tools = this.resourceM.getTools();
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
            double soldierPercent = this.hD.soldierPercent();
            if (soldierPercent > 0.2 || soldierPercent < 0.05) {
                return 0.9;
            }
        }
        return 1;
    }
}

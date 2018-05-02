/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import society.domain.*;

/**
 *
 * @author pyylauri
 */
public class HumanDistributor implements Distributor {

    static final Comparator<WorkerUnit> A = (WorkerUnit wu1, WorkerUnit wu2) -> wu2.getAge() - wu1.getAge();
    private List<WorkerUnit> list;
    private Map<WorkerUnit, Factories> workPlaces;

    public HumanDistributor() {
        this.list = new ArrayList();
        this.workPlaces = new HashMap();
    }
    /**
     * Adds a workerUnit to the distributor, and sets it unemployed
     * @param h workerUnit that is to be added
     */
    public void addWorkerUnit(WorkerUnit h) {
        this.list.add(h);
        this.workPlaces.put(h, null);
    }
    /**
     * Sets a given workerUnit to the factory that is also given as a parameter
     * Also, if the worker isn't already known in the distributor, it will be
     * added to it.
     * @param h workerUnit which workplace is to be set
     * @param f Factory where the worker is to be set
     */
    public void setWorkerUnitFactory(WorkerUnit h, Factories f) {
        if (!this.list.contains(h)) {
            this.list.add(h);
        }
        this.workPlaces.put(h, f);
    }
    /**
     * Returns the amount of people in the distributor
     * @return integer that is the population
     */
    public int getPopulation() {
        return this.list.size();
    }

    public List<WorkerUnit> getList() {
        return list;
    }
    /**
     * Return list of workers that are actually in a workplace, and not 
     * unemployed
     * @return List containing all workers that have workplace.
     */
    public List<WorkerUnit> getListOfWorkers() {
        List<WorkerUnit> workerList = new ArrayList();
        for (WorkerUnit h : this.list) {
            if (this.workPlaces.get(h) != null) {
                workerList.add(h);
            }
        }
        return workerList;
    }
    /**
     * Return a list of workers working in the factory given in the parameter
     * @param f Factory which workers are wanted
     * @return List containing all workerUnits working in the certain factory
     */
    public List<WorkerUnit> getListOfWorkersAtPlace(Factories f) {
        List<WorkerUnit> workerList = new ArrayList();
        for (WorkerUnit h : this.list) {
            if (this.workPlaces.get(h) == f) {
                workerList.add(h);
            }
        }
        return workerList;
    }

    private int amountOfReproducablePeople() {
        int rightAged = 0;
        for (WorkerUnit h : this.list) {
            if (h.getAge() < 45 && h.getAge() > 18) {
                rightAged++;
            }
        }
        rightAged /= 2;
        return Math.max(1, rightAged);
    }
    /**
     * Calculates an amount of babies to be born using food and random 
     * generator, and adds them to the distributor's database. 
     * @param foodProd amount of food produced in the current year
     */
    public void makeBabies(double foodProd) {
        int amountOfMothers = amountOfReproducablePeople();
        Random rng = new Random();
        double babiesValue = rng.nextInt(Math.max(1, (int) Math.log(amountOfMothers)) * 3);
        if (foodProd < 0.0) {
            babiesValue /= 2;
        }
        int babies = (int) babiesValue;
        for (int i = 0; i < babies; i++) {
            int value = rng.nextInt(25) + 65;
            char letter = (char) value;
            this.addWorkerUnit(new Human(letter + "-" + rng.nextInt(10000)));
        }
    }

    public Map<WorkerUnit, Factories> getWorkPlaces() {
        return workPlaces;
    }
    /**
     * Gives numberOfWorkers in different workplaces in a integer array
     * @return integer array that haves number of workers in each workplace
     */
    public int[] getNumberOfWorkers() {
        int[] workerAmounts = new int[4];
        for (WorkerUnit h : this.workPlaces.keySet()) {
            if (null != this.workPlaces.get(h)) {
                switch (this.workPlaces.get(h)) {
                    case FARM:workerAmounts[0] = workerAmounts[0] + 1;
                        break;
                    case FACTORY:workerAmounts[1] = workerAmounts[1] + 1;
                        break;
                    case LABORATORY:workerAmounts[2] = workerAmounts[2] + 1;
                        break;
                    case ARMY:workerAmounts[3] = workerAmounts[3] + 1;
                        break;
                    default:
                        break;
                }
            }
        }
        return workerAmounts;
    }
    /**
     * Counts the number of humans that are under 18
     * @return amount of children
     */
    public int numberOfChilds() {
        int childs = 0;
        for (WorkerUnit h : this.list) {
            if (h.getAge() < 18) {
                childs++;
            }
        }
        return childs;
    }
    /**
     * Counts amount of people who are at least 18 and don't have workplace
     * @return integer telling the number of adults without workplace
     */
    public int numberOfUnemployed() {
        int[] numberOfWorkers = this.getNumberOfWorkers();
        int sum = 0;
        for (int i = 0; i < numberOfWorkers.length; i++) {
            sum += numberOfWorkers[i];
        }
        sum += this.numberOfChilds();
        return this.list.size() - sum;
    }
    /**
     * Total number of adults, which is calculated by subtracting amount of
     * children from total population
     * @return amount of adults
     */
    public int numberOfAdults() {
        return this.list.size() - this.numberOfChilds();
    }
    /**
     * Returns a random workerUnit that is unemployed, or if there is none,
     * returns a null
     * @return workerUnit that is Unemployed or null
     */
    public WorkerUnit getUnemployed() {
        for (WorkerUnit h : this.workPlaces.keySet()) {
            if (this.workPlaces.get(h) == null && h.getAge() >= 18) {
                return h;
            }
        }
        return null;
    }
    /**
     * Makes all people in Distributor one year older, and if they are over
     * 80, may also kill them, depending on a Random generator.
     */
    public void makeOneYearOlder() {
        List<WorkerUnit> toBeKilled = new ArrayList();
        for (WorkerUnit h : this.list) {
            h.growOlder();
            if (h.getAge() > 80) {
                Random r = new Random();
                if (r.nextInt(20) + h.getAge() > 100) {
                    toBeKilled.add(h);
                    this.workPlaces.remove(h);
                }
            }
        }
        this.list.removeAll(toBeKilled);
    }
    /**
     * Kills people until there is no food shortage
     * @param shortage amount of food shortage
     */
    public void kill(double shortage) {
        while (shortage < 0 && !this.list.isEmpty()) {
            WorkerUnit dead = this.list.remove(0);
            this.workPlaces.remove(dead);
            shortage++;
        }
    }
    /**
     * Kills all people
     */
    public void killAll() {
        this.list = new ArrayList();
        this.workPlaces = new HashMap();
    }
    /**
     * Returns a String form where total population and a list of all workers
     * are written
     * @return String containing all humans.
     */
    public String getWorkerUnitsDisplay() {
        String display = new String();
        display += ("Total population: " + this.list.size() + "\n");
        for (WorkerUnit h : this.list) {
            if (this.workPlaces.get(h) != null) {
                display += h.toString() + ", " + this.workPlaces.get(h).toString() + "\n";
            } else {
                display += h.toString() + ", unemployed\n";
            }
        }
        return display;
    }
    /**
     * Sorts the data list by people's age, so that oldest one is first
     */
    public void sortByAge() {
        Collections.sort(this.list, A);
    }
    /**
     * Computes and return the percentage of soldiers in total population
     * @return percentage of soldiers
     */
    public double soldierPercent() {
        if (this.getNumberOfWorkers()[3] == 0) {
            return 0;
        }
        return this.getNumberOfWorkers()[3] / (double) this.list.size();
    }

}

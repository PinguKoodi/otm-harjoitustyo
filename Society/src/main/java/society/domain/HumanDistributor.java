/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author pyylauri
 */
public class HumanDistributor {
    private List<Human> list;
    private Map<Human, Factories> workPlaces; 
    
    public HumanDistributor () {
        this.list = new ArrayList();
        this.workPlaces = new HashMap();
    }
    
    public void addHuman(Human h) {
        this.list.add(h);
        this.workPlaces.put(h, null);
    }
    public void setHumanFactory(Human h, Factories f) {
        if(!this.list.contains(h)) {
            this.list.add(h);
        }
        this.workPlaces.put(h, f);
    }
    public int getPopulation() {
        return this.list.size();
    }
    
    public List<Human> getList() {
        return list;
    }
    public List<Human> getListOfWorkers() {
        List<Human> workerList = new ArrayList();
        for(Human h: this.list) {
            if(this.workPlaces.get(h) != null) {
                workerList.add(h);
            }
        }
        return workerList;
    }
    public List<Human> getListOfWorkersAtPlace(Factories f) {
        List<Human> workerList = new ArrayList();
        for(Human h: this.list) {
            if(this.workPlaces.get(h) == f) {
                workerList.add(h);
            }
        }
        return workerList;
    }

    public Map<Human, Factories> getWorkPlaces() {
        return workPlaces;
    }
    public int[] getNumberOfWorkers() {
        int[] workerAmounts = new int[4];
        for(Human h:this.workPlaces.keySet()) {
            if(null != this.workPlaces.get(h)) switch (this.workPlaces.get(h)) {
                case FARM:
                    workerAmounts[0] = workerAmounts[0] +1;
                    break;
                case FACTORY:
                    workerAmounts[1] = workerAmounts[1]+1;
                    break;
                case LABORATORY:
                    workerAmounts[2] = workerAmounts[2]+1;
                    break;
                case ARMY:
                    workerAmounts[3] = workerAmounts[3]+1;
                    break;
                default:
                    break;
            }
        }
        return workerAmounts;
    }
    
    public int numberOfChilds() {
        int childs = 0;
        for(Human h :this.list) {
            if(h.getAge() < 18) {
                childs++;
            }
        }
        return childs;
    }
    
    public int numberOfUnemployed() {
        int[] numberOfWorkers = this.getNumberOfWorkers();
        int sum = 0;
        for(int i = 0;i<numberOfWorkers.length;i++) {
            sum += numberOfWorkers[i];
        }
        sum += this.numberOfChilds();
        return this.list.size() - sum;
    }
    public int numberOfAdults() {
        return this.list.size() - this.numberOfChilds();
    }
    public Human getUnemployed() {
        for(Human h: this.workPlaces.keySet()) {
            if(this.workPlaces.get(h) == null && h.getAge() >= 18) {
                return h;
            }
        }
        return null;
    }
    public void makeOneYearOlder() {
        List<Human> toBeKilled = new ArrayList();
        for(Human h :this.list) {
            h.growOlder();
            if(h.getAge() > 70) {
                Random r = new Random();
                if(r.nextInt(30)+h.getAge()<100) {
                    toBeKilled.add(h);
                }       
            }
        }
        this.list.removeAll(toBeKilled);
    }
    
    public void kill(double shortage) {
        while(shortage <0 && !this.list.isEmpty()) {
            Human dead = this.list.remove(0);
            this.workPlaces.remove(dead);
            shortage++;
        }
    }
}

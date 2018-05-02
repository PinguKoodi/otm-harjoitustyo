/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Lauri
 */
public interface Distributor {
    public void addWorkerUnit(WorkerUnit wu);
    public void setWorkerUnitFactory(WorkerUnit wu, Factories f);
    public int getPopulation();
    public List<WorkerUnit> getList();
    public int[] getNumberOfWorkers();
    public void killAll();
    public void kill(double n);
    public List getListOfWorkersAtPlace(Factories f);
    public int numberOfAdults();
    public void sortByAge();
    public WorkerUnit getUnemployed();
    public void makeOneYearOlder();
    public int numberOfUnemployed();
    public void makeBabies(double prod);
    public Map<WorkerUnit, Factories> getWorkPlaces();
    public String getWorkerUnitsDisplay();
    public int numberOfChilds();
    public double soldierPercent();
}

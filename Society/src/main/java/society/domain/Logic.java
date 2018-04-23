/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;
import java.util.List;
import java.util.Map;
import java.util.Random;
import society.data.FileOperator;

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
    private FileOperator operator;

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
        this.operator = new FileOperator(this);
    }

    public void startGame(boolean loadedGame) {
        if (loadedGame) {
            operator.switchToLoadFromSave();
        }
        this.loadHumans();
        if (this.hD.getList().isEmpty()) { //Fail-safe is loading doesn't work
            this.createFirstHumans();
        }
//        this.createFirstHumans();
        operator.switchToLoadFromSave();
    }

    public void loadHumans() {
        double[] values = operator.readValuesFromFile();
        Map<Human, Factories> humans = operator.readHumansFromFile();
        for (int i = 0; i < 6; i++) {
            if (i < 4) {
                resources[i] = values[i];
            } else if (i == 4) {
                this.year = (int) values[i];
            } else {
                this.happiness = values[i];
            }
        }
        for (Human h : humans.keySet()) {
            this.hD.addHuman(h);
            if (humans.get(h) != null) {
                this.hD.setHumanFactory(h, humans.get(h));
            }
        }
        this.hD.sortHumansByAge();

    }

    public void assignWorker(Factories f) {
        if (this.isUnemployed()) {
            Human h = this.hD.getUnemployed();
            this.hD.setHumanFactory(h, f);
        }
    }

    public void saveToFile() {
        operator.saveToFile();
    }

    public boolean endTurn() {
        boolean foodIsOut = this.resources[0] < 0;
        double[] prods = this.getProduction();
        for (int i = 0; i < 4; i++) {
            this.resources[i] += prods[i];
        }
        calculateResourceLoss();
        calculateHappiness(foodIsOut);
        if (foodIsOut && this.resources[0] < 0) {
            this.hD.kill(this.resources[0]);
        }
        if (this.hD.getList().isEmpty()) {
            return true;
        }
        this.hD.makeOneYearOlder();
        this.makeBabies(prods[0]);
        year++;
        return false;
    }

    public boolean isUnemployed() {
        return this.hD.numberOfUnemployed() > 0;
    }

    public double[] getResources() {
        return resources;
    }

    public double[] getResourcesDisplay() {
        return trimDoubleArray(this.resources);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private void calculateHappiness(boolean foodIsOut) {
        double amountOfPeople = this.hD.getPopulation();
        double change = 0;
        if (foodIsOut) {
            change -= 5;
        } else if (resources[0] > amountOfPeople) {
            change += 2;
        }
        change += ((resources[1] / amountOfPeople) - 1);
        change += Math.max(resources[2] - year * 2, -0.2);
        if (this.hD.getListOfWorkersAtPlace(Factories.ARMY).size() > amountOfPeople * 0.2) {
            change += -5;
        }
        change = Math.max(-5, change);
        change = Math.min(5, change);
        this.happiness += change;
        this.happiness = Math.max(0, happiness);
        this.happiness = Math.min(120, happiness);
    }

    public double getHappiness() {
        double temp = Math.round(happiness * 10);
        temp /= 10;
        return temp;
    }

    public HumanDistributor gethD() {
        return hD;
    }

    public String getGuideText() {
        return this.operator.getGuideText();
    }

    public void createFirstHumans() {
        for (int i = 0; i < 15; i++) {
            if (i < 10) {
                this.hD.setHumanFactory(new Human("Firstborn" + i, 20 + i, 10 + i), Factories.FARM);
            } else {
                this.hD.addHuman(new Human("Firstborn" + i, 20 + i));
            }
        }
    }

    public double[] getProduction() {
        double[] prods = new double[4];
        double[] multipliers = this.mult.getAllMultipliers();
        int[] workers = this.hD.getNumberOfWorkers();
        for (int i = 0; i < 4; i++) {
            double production = workers[i] * multipliers[i];
            if (i == 0) {
                production -= this.hD.getPopulation();
            } else if (i == 1) {
                production -= this.hD.getListOfWorkersAtPlace(Factories.FARM).size() * 0.10;
                production = Math.max(production, 0);
            }
            prods[i] = production;
        }
        return prods;
    }

    public double[] getProductionDisplay() {
        return trimDoubleArray(this.getProduction());
    }

    public double[] trimDoubleArray(double[] array) {
        double[] trimmed = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            trimmed[i] = Math.round(array[i] * 10);
            trimmed[i] = trimmed[i] / 10;
        }
        return trimmed;
    }

    private int makeBabies(double foodProd) {
        int amountOfMothers = this.hD.amountOfReproducablePeople();
        double babiesValue = rng.nextInt(Math.max(1, (int) Math.log(amountOfMothers)) * 3);
        if (foodProd < 0.0) {
            babiesValue /= 2;
        }
        int babies = (int) babiesValue;
        for (int i = 0; i < babies; i++) {
            int value = rng.nextInt(25) + 65;
            char letter = (char) value;
            this.hD.addHuman(new Human(letter + "-" + this.rng.nextInt(10000)));
        }
        return babies;
    }

    private void calculateResourceLoss() {
        int soldiers = this.hD.getListOfWorkersAtPlace(Factories.ARMY).size();
        int resourceSum = 0;
        for (int i = 0; i < 4; i++) {
            resourceSum += resources[i];
        }
        resourceSum /= 1000;
        if (resourceSum > soldiers) {
            for (int i = 0; i < 4; i++) {
                resources[i] = resources[i] * 0.95;
            }
        }
    }

    public String getWorkplaceAsString(Human h) {
        if (this.hD.getWorkPlaces().get(h) == null) {
            return "null";
        } else {
            return this.hD.getWorkPlaces().get(h).toString();
        }
    }
}

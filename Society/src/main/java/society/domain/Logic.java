package society.domain;

import java.io.File;
import java.util.Map;
import java.util.Random;
import society.data.FileOperator;
import society.data.SaveOperator;

/**
 *
 * @author pyylauri
 */
public class Logic {

    private Distributor hD;
    private ResourceManager resources;
    private int year;
    private Random rng;
    private Multiplier mult;
    private double happiness;
    private SaveOperator operator;

    public Logic(Distributor dist) {
        this.hD = dist;
        this.rng = new Random();
        this.operator = new FileOperator(this);
        this.resources = new ResourceManager();
        this.resources.initializeResources();
        this.mult = new Multiplier(this.resources, this.hD);
    }

    public Logic(Distributor dist, File file) {
        this.hD = dist;
        this.rng = new Random();
        this.operator = new FileOperator(this, file);
        this.resources = new ResourceManager();
        this.resources.initializeResources();
        this.mult = new Multiplier(this.resources, this.hD);
    }

    /**
     * Starts the game by setting resources to initial values and creating first
     * group of people, or by loading the values from a save file.
     *
     * @param loadedGame if true, game will load the state from save file
     */
    public void startGame(boolean loadedGame) {
        this.year = 0;
        this.happiness = 50;
        this.resources.initializeResources();
        this.hD.killAll();
        if (loadedGame) {
            operator.switchToLoadFromSave();
            this.loadSaveData();
        } else {
            createFirstWorkerUnits();
        }
        operator.switchToLoadFromSave();
        this.mult.setDifficulty(operator.getDifficulty());
    }

    /**
     * Loads humans and values from a save file and adds them to the distributor
     */
    public void loadSaveData() {
        double[] values = operator.readValuesFromSave();
        Map<WorkerUnit, Factories> humans = operator.readHumansFromSave();
        for (int i = 0; i < 6; i++) {
            if (i < 4) {
                this.resources.setOneResource(values[i], i);
            } else if (i == 4) {
                this.year = (int) values[i];
            } else {
                this.happiness = values[i];
            }
        }
        for (WorkerUnit wu : humans.keySet()) {
            this.hD.addWorkerUnit(wu);
            if (humans.get(wu) != null) {
                this.hD.setWorkerUnitFactory(wu, humans.get(wu));
            }
        }
        this.hD.sortByAge();
    }

    /**
     * Assign a random unemployed worker to the specified factory given as
     * parameter. If there is no unemployed workers, won't do anything.
     *
     * @param f factory where unemployed person is assigned
     */
    public void assignWorker(Factories f) {
        if (this.isUnemployed()) {
            WorkerUnit wU = this.hD.getUnemployed();
            this.hD.setWorkerUnitFactory(wU, f);
        }
    }

    /**
     * Saves the current gameState to file
     *
     * @return true if saving is successful, false otherwise
     */
    public boolean saveToFile() {
        return operator.saveGame();
    }

    /**
     * Ends a game turn, which forwards the year by one. Assigned workers will
     * produce resources and everyone will age Possible resource loss will
     * happen, and babies may be born, depending on the circumstances
     *
     * @return returns true if everyone is dead and game will end, false
     * otherwise
     */
    public boolean endTurn() {
        boolean foodIsOut = this.resources.getFood() < 0;
        double[] prods = this.getProduction();
        this.resources.addProduction(prods);
        this.resources.calculateResourceLoss(this.hD.getNumberOfWorkers()[3]);
        calculateHappiness();
        if (foodIsOut && this.resources.getFood() < 0) {
            this.hD.kill(this.resources.getFood());
        }
        if (this.hD.getList().isEmpty()) {
            return true;
        }
        this.hD.makeOneYearOlder();
        this.hD.makeBabies(prods[0]);
        year++;
        return false;
    }

    /**
     * Checks whether there is unemployed workers
     *
     * @return boolean value describing whether there is unemployed or not
     */
    public boolean isUnemployed() {
        return this.hD.numberOfUnemployed() > 0;
    }

    /**
     * Return resources from resource class
     *
     * @return double array containing resource values
     */
    public double[] getResources() {
        return this.resources.getResources();
    }

    /**
     * Returns resources in a rounded from where there is one decimal
     *
     * @return double array containing trimmed values
     */
    public double[] getResourcesDisplay() {
        return trimDoubleArray(this.resources.getResources());
    }

    public int getYear() {
        return year;
    }

    private void calculateHappiness() {
        int amountOfPeople = this.hD.getPopulation();
        double change = this.resources.calculateResourceEffectOnHappiness(amountOfPeople);
        if (this.hD.soldierPercent() < 0.05 || this.hD.soldierPercent() > 0.2) {
            change -= 3;
        }
        change = Math.max(-5, change);
        change = Math.min(5, change);
        this.happiness += change;
        this.happiness = Math.max(0, happiness);
        this.happiness = Math.min(100, happiness);
    }

    /**
     * Rounds happiness to one decimal
     *
     * @return happiness value rounded to one decimal
     */
    public double getHappiness() {
        double temp = Math.round(happiness * 10);
        temp /= 10;
        return temp;
    }

    public Distributor gethD() {
        return hD;
    }

    public String getGuideText() {
        return this.operator.getGuideText();
    }

    /**
     * Creates a set of worker Units
     */
    public void createFirstWorkerUnits() {
        int amount = this.operator.getStartingHumans();
        for (int i = 0; i < amount; i++) {
            if (i < 8) {
                this.hD.setWorkerUnitFactory(new Human("A-" + i, 20 + i, 10 + i), Factories.FARM);
            } else if (i < 10) {
                this.hD.addWorkerUnit(new Human("I-" + i, i * 2 - 5));
            } else {
                this.hD.addWorkerUnit(new Human("X-" + i, 20));
            }
        }
        this.hD.sortByAge();
    }

    /**
     * Calculates year's production by using multipliers and amount of workers.
     * Will return the values of production as an array.
     *
     * @return array of year's production values.
     */
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

    /**
     * Calculates to one decimal rounded values of resources and returns them in
     * an array
     *
     * @return double array containing trimmed values
     */
    public double[] getProductionDisplay() {
        return trimDoubleArray(this.getProduction());
    }

    /**
     * Trims a given array so that all values have just one decimal number
     *
     * @param array to be trimmed
     * @return double array that has its values trimmed
     */
    public double[] trimDoubleArray(double[] array) {
        double[] trimmed = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            trimmed[i] = Math.round(array[i] * 10);
            trimmed[i] = trimmed[i] / 10;
        }
        return trimmed;
    }

    /**
     * Returns a String version of the workerUnit's workplace
     *
     * @param h workerUnit which workplaces is wanted
     * @return Workplace in a String form
     */
    public String getWorkplaceAsString(WorkerUnit h) {
        if (this.hD.getWorkPlaces().get(h) == null) {
            return "null";
        } else {
            return this.hD.getWorkPlaces().get(h).toString();
        }
    }

    public ResourceManager getResourceManager() {
        return resources;
    }

    public void setOperator(SaveOperator operator) {
        this.operator = operator;
    }

    public SaveOperator getOperator() {
        return operator;
    }

}

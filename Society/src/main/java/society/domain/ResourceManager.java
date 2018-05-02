/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;

/**
 *
 * @author Lauri
 */
public class ResourceManager {

    private double[] resources;

    public ResourceManager() {
        this.resources = new double[4];
    }

    public double[] getResources() {
        return resources;
    }

    public void setResources(double[] resources) {
        this.resources = resources;
    }

    /**
     * Sets one specific resource amount to the given value
     *
     * @param r the amount of resource
     * @param i indicator as to which resource will be changed
     */
    public void setOneResource(double r, int i) {
        this.resources[i] = r;
    }

    /**
     * Adds production as given in the parameter array to the resource values
     *
     * @param prods double array that contains the production values
     */
    public void addProduction(double[] prods) {
        for (int i = 0; i < 4; i++) {
            this.resources[i] += prods[i];
        }
    }

    /**
     * Sets the starting amounts to resources
     */
    public void initializeResources() {
        this.resources = new double[4];
        this.resources[0] = 100;
        for (int i = 1; i < 4; i++) {
            this.resources[i] = 0;
        }

    }

    /**
     * Returns the amount of food in storage
     *
     * @return amount of food
     */
    public double getFood() {
        return this.resources[0];
    }

    /**
     * Returns the amount of Tools in storage
     *
     * @return amount of Tools
     */
    public double getTools() {
        return this.resources[1];
    }

    /**
     * Returns the amount of Science in storage
     *
     * @return amount of Science
     */
    public double getScience() {
        return this.resources[2];
    }

    /**
     * Returns the amount of Guns in storage
     *
     * @return amount of Guns
     */
    public double getGuns() {
        return this.resources[3];
    }

    /**
     * Calculates how much resources will be lost during a year, depending on
     * the number of soldiers player haves, given as the parameter
     *
     * @param soldiers Amount of soldiers player currently haves
     */
    public void calculateResourceLoss(int soldiers) {
        int resourceSum = 0;
        for (int i = 0; i < 4; i++) {
            resourceSum += resources[i];
        }
        resourceSum /= 1000;
        if (resourceSum > soldiers) {
            for (int i = 0; i < 4; i++) {
                if (i != 2) {
                    resources[i] = resources[i] * 0.95;
                }
            }
        }
    }

    /**
     * Calculates if there is enough resources to keep people happy, and how
     * much the happiness will change based on the amount of resources
     *
     * @param population current amount of population, used to determine if
     * there is enough people
     * @return double value describing the change to happiness
     */
    public double calculateResourceEffectOnHappiness(int population) {
        double value = 0;
        if (resources[0] < 0) {
            value -= 5;
        } else if (resources[0] > population) {
            value += 3;
        }
        if (resources[1] > population) {
            value += 2;
        }
        if (resources[3] < population) {
            value -= 3;
        }
        if (resources[2] > population) {
            value += 2;
        }
        return value;
    }

}

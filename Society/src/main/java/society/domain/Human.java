/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.domain;

/**
 *
 * @author pyylauri
 */
public class Human implements WorkerUnit, Comparable<WorkerUnit> {

    private String name;
    private int age;
    private int experience;

    public Human(String name) {
        this.name = name;
        this.age = 0;
        this.experience = 0;
    }

    public Human(String name, int age) {
        this.name = name;
        this.age = age;
        this.experience = 0;
    }

    public Human(String name, int age, int experience) {
        this.name = name;
        this.age = age;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    /**
     * Grows the age and experience of human by one.
     */
    public void growOlder() {
        this.age++;
        this.experience += 1;
    }

    /**
     * Creates a string describing the human
     *
     * @return String which haves all data of human
     */
    @Override
    public String toString() {
        String skillLevel;
        if (experience < 20) {
            skillLevel = "Unskilled";
        } else if (experience < 40) {
            skillLevel = "Adept";
        } else if (experience < 60) {
            skillLevel = "Professional";
        } else {
            skillLevel = "Masterous";
        }
        return this.name + ", Age: " + this.age + ", " + skillLevel;
    }

    /**
     * Creates a string that haves details of human that can be used to save it
     *
     * @return String which haves all attributes separated by ;
     */
    public String getFileString() {
        return this.name + ";" + this.age + ";" + this.experience;
    }

    /**
     * Compares Human to another one by their age
     *
     * @param wu
     * @return
     */
    @Override
    public int compareTo(WorkerUnit wu) {
        if (wu.getClass().equals(this.getClass())) {
            return -this.age + wu.getAge();
        }
        return -1;
    }

}

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
public class Human implements Comparable<Human>{

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

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void growOlder() {
        this.age++;
        this.experience += 1;
    }

    @Override
    public String toString() {
        return this.name + ", Age: " + this.age + ", experience: " + this.experience;
    }

    public String getFileString() {
        return this.name + ";" + this.age + ";" + this.experience;
    }
    
    @Override
    public int compareTo(Human h) {
        return -this.age+h.getAge();
    }
}

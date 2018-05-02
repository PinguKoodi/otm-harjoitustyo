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
public interface WorkerUnit {
    public String toString();
    public String getFileString();
    public int compareTo(WorkerUnit wu);
    public void growOlder();
    public int getAge();
    public int getExperience();
    
}

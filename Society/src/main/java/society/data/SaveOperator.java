/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.data;

import java.util.Map;
import society.domain.Factories;
import society.domain.Human;
import society.domain.WorkerUnit;

/**
 *
 * @author Lauri
 */
public interface SaveOperator {
    public boolean saveGame();
    public void switchToLoadFromSave();
    public String getGuideText();
    public double[] readValuesFromSave();
    public Map<WorkerUnit, Factories> readHumansFromSave();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.data;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import society.domain.Distributor;
import society.domain.Factories;
import society.domain.Human;
import society.domain.HumanDistributor;
import society.domain.Logic;

/**
 *
 * @author Lauri
 */
public class FileOperatorTest {

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private FileOperator fileO;
    private Logic logic;
    private Distributor hD;
    private File configFile;
    private File saveFile;

    public FileOperatorTest() {
    }

    @Before
    public void setUp() throws Exception {
        this.hD = new HumanDistributor();
        configFile = testFolder.newFile("config.txt");
        try (FileWriter file = new FileWriter(configFile.getAbsolutePath())) {
            file.write("firstValues=easyStartData.txt\n");
            file.write("data=testSaveData.txt\n");
            file.write("humans=saveHumans.txt\n");
            file.write("guide=guide.txt\n");
            file.write("startingHumans=19\n");
            file.write("difficulty=1.0");
            file.close();
        }
        this.logic = new Logic(this.hD, configFile);
        saveFile = testFolder.newFile("testSaveData.txt");
        this.fileO = new FileOperator(this.logic, configFile);
        this.logic.setOperator(fileO);
        
        this.fileO.setProperties(configFile);

    }
    @Test
    public void configFileContainsText() throws Exception{
        Scanner reader = new Scanner(this.configFile);
        assertTrue(reader.hasNext());
    }
    @Test
    public void configInTheOperatorContainsText() throws Exception{
        Scanner reader = new Scanner(this.fileO.getConfig());
        assertTrue(reader.hasNext());
    }

    @Test
    public void getGuideTextMethodReturnsSomething() {
        assertTrue(this.fileO.getGuideText().length() > 10);
    }

    @Test
    public void getGuideTextStringBeginsCorrectly() {
        assertEquals("In this game", this.fileO.getGuideText().substring(0, 12));
    }

    @Test
    public void getDifficultyReturnsCorrectValueInitially() {
        assertEquals(1.0, this.fileO.getDifficulty(), 0.001);
    }

    @Test
    public void saveToFileReturnsTrueIfSavingIsSuccessful() {
        this.logic.startGame(false);
        //this.fileO.setProperties(configFile);
        this.fileO.switchToLoadFromSave();
        assertTrue(this.fileO.saveGame());
    }

    @Test
    public void readValuesFromSaveReturnsCorrectValuesWhenNothingIsSaved() {
        assertEquals(0.0, this.fileO.readValuesFromSave()[0], 0.001);
    }

    @Test
    public void readValuesFromSaveReturnsCorrectValuesWhenStartSituationIsSaved() {
        this.logic.startGame(false);
        this.fileO.setProperties(configFile);
        this.fileO.switchToLoadFromSave();
        this.logic.saveToFile();

        assertEquals(100.0, this.fileO.readValuesFromSave()[0], 0.001);
    }

    @Test
    public void humansAreReadFromASave() throws Exception {
        //saveFile.delete();
        //saveFile = testFolder.newFile("saveData.txt");

        this.hD.addWorkerUnit(new Human("A-2", 20));
        this.fileO.setProperties(configFile);
        this.fileO.switchToLoadFromSave();
        this.logic.saveToFile();
        assertEquals(1,this.fileO.readHumansFromSave().size());
    }
    
    @Test
    public void humansAreReadFromASaveWhenThereIsMultipleWorkers() throws Exception {
        //saveFile.delete();
        //saveFile = testFolder.newFile("saveData.txt");

        this.hD.addWorkerUnit(new Human("A-2", 20));
        this.hD.addWorkerUnit(new Human("A-3", 20));
        this.hD.setWorkerUnitFactory(new Human("A-4", 20),Factories.FARM);
        this.fileO.setProperties(configFile);
        this.fileO.switchToLoadFromSave();
        this.logic.saveToFile();
        assertEquals(3,this.fileO.readHumansFromSave().size());
    }
    @Test
    public void savingGamesavesTheYear() {
        this.logic.startGame(false);
        this.logic.endTurn();
        this.logic.saveToFile();
        assertEquals(1,this.fileO.readValuesFromSave()[4],0.0001);
    }

    @Test
    public void getStartingHumansReturns17WhenItIsNotSpecified() {
        this.fileO.setProperties(null);
        assertEquals(17, this.fileO.getStartingHumans());
    }
    
    @Test
    public void constructorCreatesFileOperator() {
        this.fileO = new FileOperator(this.logic);
        assertTrue(this.fileO != null);
    }
    
    @Test
    public void constructorCreatesFileOperatorAndConfigFileExists() {
        this.fileO = new FileOperator(this.logic);
        assertTrue(this.fileO.getDifficulty() != 0);
    }
    @Test
    public void constructorCreatesANewConfigFile() {
        this.fileO = new FileOperator(this.logic);
        this.fileO.setProperties(configFile);
        assertTrue(this.fileO.getConfig().exists());
    }

    @After
    public void tearDown() {
        this.configFile.delete();
        this.saveFile.delete();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}

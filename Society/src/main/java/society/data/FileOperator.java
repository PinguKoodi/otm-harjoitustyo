/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import society.domain.Factories;
import society.domain.Human;
import society.domain.Logic;
import society.ui.Main;

import java.io.StringWriter;
import java.io.PrintWriter;
import society.domain.WorkerUnit;

/**
 *
 * @author Lauri
 */
public class FileOperator implements SaveOperator {

    private Logic l;
    private File data;
    private File humans;
    private File config;
    private Properties properties;
    private File guide;
    private String dataString;
    private String humanString;

    /**
     * Creates Fileoperator that loads configurations from a resources folder
     * and saves the logic
     *
     * @param logic Saves the logic give as parameter, to know where to find
     * data to be saved
     */
    public FileOperator(Logic logic) {
        this.l = logic;
        try {
            properties = new Properties();
            File jarFile = new File(FileOperator.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            String dataFilePath = jarFile.getParent() + File.separator + "config.txt";
            FileInputStream configStream = new FileInputStream(dataFilePath);
            properties.load(configStream);
//            dataString = properties.getProperty("firstValues");
//            this.data = new File(this.getClass().getResource("/files/" + dataString).toURI());
            //humanString = properties.getProperty("firstHumans");
            //this.humans = new File(this.getClass().getResource("/files/" + humanString).toURI());
        } catch (Exception e) {
            createNewConfigFile();
        }
    }

    /**
     * If the config file should be specified in the constructor
     *
     * @param logic Logic that gives data to operator
     * @param config file where configurations will be saved
     */
    public FileOperator(Logic logic, File config) {
        this.l = logic;
        try {
            properties = new Properties();
            FileInputStream configStream = new FileInputStream(config);
            properties.load(configStream);
//            dataString = properties.getProperty("firstValues");
//            this.data = new File(this.getClass().getResource("/files/" + dataString).toURI());
            //humanString = properties.getProperty("firstHumans");
            //this.humans = new File(this.getClass().getResource("/files/" + humanString).toURI());
        } catch (Exception e) {
            createNewConfigFile();
        }
    }

    /**
     * Sets a different properties file for the operator to use, mostly test
     * purposes
     *
     * @param file File which the configurations are read from
     */
    public void setProperties(File file) {
        try {
            this.properties = new Properties();
            properties.load(new FileInputStream(file));
            this.config = file;
        } catch (Exception e) {

        }
    }

    public File getConfig() {
        return config;
    }

    /**
     * Switches the system to load from save file rather than from starting file
     */
    public void switchToLoadFromSave() {
        try {
            dataString = properties.getProperty("data");
//            dataString = ClassLoader.getSystemClassLoader().getResource(".").getPath() + dataString;
            File jarFile = new File(FileOperator.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            String dataFilePath = jarFile.getParent() + File.separator + dataString;
            this.data = new File(dataFilePath);
        } catch (Exception e) {

        }

    }

    /**
     * Gets the number of humans that are generated at the beginning of the game
     *
     * @return integer of the number of humans
     */
    public int getStartingHumans() {
        try {
            int amount = Integer.parseInt(this.properties.getProperty("startingHumans"));
            if (amount > 0) {
                return amount;
            }
            return 17;
        } catch (Exception e) {
            return 17;
        }
    }

    /**
     * Return the difficulty level, which tells how much resource production is
     * reduced
     *
     * @return the difficulty level
     */
    public double getDifficulty() {
        try {
            return Double.parseDouble(this.properties.getProperty("difficulty"));
        } catch (Exception e) {
            return 1.0;
        }
    }

    /**
     * Writes the current gamestate in the logic-Class to a text file
     *
     * @return true if saving was successful, false if not
     */
    public boolean saveGame() {
        try {
            data.createNewFile();
            File jarFile = new File(FileOperator.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            String dataFilePath = jarFile.getParent() + File.separator + dataString;
            Path dataPath = Paths.get(dataFilePath);

            Files.write(dataPath, getFileString(), TRUNCATE_EXISTING);
            return true;
        } catch (Exception e) {

        }
        return false;
    }

    private List<String> getFileString() {
        String toBeWrited = "";
        List<String> lines = new ArrayList();
        for (int i = 0; i < 4; i++) {
            toBeWrited += this.l.getResourcesDisplay()[i] + ";";
        }
        toBeWrited = toBeWrited + this.l.getYear() + ";" + this.l.getHappiness();
        lines.add(toBeWrited);
        for (WorkerUnit h : this.l.gethD().getList()) {
            String humanLine = h.getFileString() + ";" + this.l.getWorkplaceAsString(h);
            lines.add(humanLine);
        }
        return lines;
    }

    /**
     * Reads the resource amounts, year and happiness from a save file that is
     * known to the class
     *
     * @return array containing the values
     */
    public double[] readValuesFromSave() {
        double[] table = new double[6];
        try {
            Scanner reader = new Scanner(this.data);
            String[] line = reader.nextLine().split(";");
            for (int i = 0; i < line.length; i++) {
                table[i] = Double.parseDouble(line[i]);
            }
            reader.close();
        } catch (Exception e) {

        }
        return table;
    }

    /**
     * Reads workerUnits from the file known to the unit.
     *
     * @return Map containing the WorkerUnits as keys, and their workplaces as
     * their corresponding values
     */
    public Map<WorkerUnit, Factories> readHumansFromSave() {
        Map<WorkerUnit, Factories> map = new HashMap();
        try {
            Scanner reader = new Scanner(this.data);
            reader.nextLine();
            while (reader.hasNext()) {
                String[] line = reader.nextLine().split(";");
                if (line[3].equals("null")) {
                    map.put(new Human(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2])), null);
                } else {
                    map.put(new Human(line[0], Integer.parseInt(line[1]), Integer.parseInt(line[2])), Factories.valueOf(line[3]));
                }
            }
        } catch (Exception e) {

        }
        return map;
    }

    /**
     * Reads the guide text from a file in the resources folder
     *
     * @return String which is the text
     */
    public String getGuideText() {
        String text = "";
        try {
            Scanner reader = new Scanner(this.getClass().getResourceAsStream("/files/guide.txt"));
            while (reader.hasNext()) {
                text += reader.nextLine() + "\n";
            }
        } catch (Exception e) {

        }
        return text;
    }

    /**
     * Creates a new configuration file if it doesnt exists and writes default
     * conditions to it
     */
    private void createNewConfigFile() {
        try {
            File jarFile = new File(FileOperator.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            String dataFilePath = jarFile.getParent() + File.separator + "config.txt";
            this.config = new File(dataFilePath);
            this.config.createNewFile();
            List<String> listToWrite = new ArrayList();
            listToWrite.add("firstValues=easyStartData.txt");
            listToWrite.add("data=saveData.txt");
            listToWrite.add("guide=guide.txt");
            listToWrite.add("startingHumans=17");
            listToWrite.add("difficulty=1.0");
            Files.write(this.config.toPath(), listToWrite);
            FileInputStream configStream = new FileInputStream(dataFilePath);
            properties.load(configStream);
            dataString = properties.getProperty("firstValues");
            this.data = new File(this.getClass().getResource("/files/" + dataString).toURI());
        } catch (Exception e) {

        }
    }
}

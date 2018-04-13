/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import society.domain.Factories;
import society.domain.Human;
import society.domain.Logic;

/**
 *
 * @author Lauri
 */
public class FileOperator {

    private Logic l;
    private File data;
    private File humans;
    private File config;
    private Properties properties;
    private File guide;

    public FileOperator(Logic logic) {
        this.l = logic;
        try {
            config = new File("src/main/resources/config.txt");
            properties = new Properties();
            properties.load(new FileInputStream(this.config));
            String dataString = properties.getProperty("firstValues");
            this.data = new File("src/main/resources/"+dataString);
            String humansString = properties.getProperty("firstHumans");
            this.humans = new File("src/main/resources/"+humansString);
            this.guide = new File("src/main/resources/"+properties.getProperty("guide"));
        } catch (Exception e) {

        }
    }

    public void switchToLoadFromSave() {
        this.data = new File("src/main/resources/"+properties.getProperty("data"));
        this.humans = new File("src/main/resources/"+properties.getProperty("humans"));

    }

    public void saveValuesToFile() {
        try {
            data.createNewFile();
            humans.createNewFile();
            FileWriter fileW = new FileWriter(data);
            String toBeWrited = "";
            for (int i = 0; i < 4; i++) {
                toBeWrited += this.l.getResourcesDisplay()[i] + ";";
            }
            toBeWrited = toBeWrited + this.l.getYear() + ";" + this.l.getHappiness();
            fileW.write(toBeWrited);
            fileW.close();
        } catch (Exception e) {
        }
    }

    public void saveHumansToFile() {
        try {
            FileWriter fw = new FileWriter(humans);
            String toBeWrited = "";
            for (Human h : this.l.gethD().getList()) {
                toBeWrited += h.getFileString() + ";" + this.l.gethD().getWorkPlaces().get(h) + "\n";
            }
            fw.write(toBeWrited);
            fw.close();
        } catch (Exception e) {

        }
    }

    public double[] readValuesFromFile() {
        double[] table = new double[6];
        try {
            Scanner reader = new Scanner(data);
            String[] line = reader.nextLine().split(";");
            for (int i = 0; i < line.length; i++) {
                table[i] = Double.parseDouble(line[i]);
            }
        } catch (Exception e) {

        }
        return table;
    }

    public Map<Human, Factories> readHumansFromFile() {
        Map<Human, Factories> map = new HashMap();
        try {
            Scanner reader = new Scanner(humans);
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

    public String getGuideText() {
        String text = "";
        try {
            Scanner reader = new Scanner(guide);
            while (reader.hasNext()) {
                text += reader.nextLine() + "\n";
            }
        } catch (Exception e) {

        }
        return text;
    }
}

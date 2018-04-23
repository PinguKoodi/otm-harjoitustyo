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
    private String dataString;
    private String humanString;

    public FileOperator(Logic logic) {
        this.l = logic;
        try {
            properties = new Properties();
            properties.load(this.getClass().getResourceAsStream("/files/config.txt"));
            dataString = properties.getProperty("firstValues");
            this.data = new File(this.getClass().getResource("/files/" + dataString).toURI());
            //humanString = properties.getProperty("firstHumans");
            //this.humans = new File(this.getClass().getResource("/files/" + humanString).toURI());
        } catch (Exception e) {
            System.out.println("ErrorStart");
        }
    }

    public void switchToLoadFromSave() {
        try {
            dataString = properties.getProperty("data");
            humanString = properties.getProperty("humans");
            this.data = new File("files/" + dataString);
            this.humans = new File("files/" + humanString);
        } catch (Exception e) {
            System.out.println("ErrorSwitch");
        }

    }

    public void saveToFile() {
        try {
            Path dataPath = Paths.get("files/" + dataString);
            String toBeWrited = "";
            List<String> lines = new ArrayList();
            for (int i = 0; i < 4; i++) {
                toBeWrited += this.l.getResourcesDisplay()[i] + ";";
            }
            toBeWrited = toBeWrited + this.l.getYear() + ";" + this.l.getHappiness();
            lines.add(toBeWrited);
            for (Human h : this.l.gethD().getList()) {
                String humanLine = h.getFileString() + ";" + this.l.getWorkplaceAsString(h);
                lines.add(humanLine);
            }
            Files.write(dataPath, lines, TRUNCATE_EXISTING);
        } catch (Exception e) {
            System.out.println("ErrorSaveValues");
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
            reader.close();
        } catch (Exception e) {

        }
        return table;
    }

    public Map<Human, Factories> readHumansFromFile() {
        Map<Human, Factories> map = new HashMap();
        try {
            Scanner reader = new Scanner(data);
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
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.ui;

import society.domain.HumanDistributor;
import society.domain.Logic;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import society.domain.Factories;
import society.domain.Human;

/**
 *
 * @author pyylauri
 */
public class Main extends Application {

    private Logic logic;
    private HumanDistributor hD;

    @Override
    public void init() throws Exception {
        this.hD = new HumanDistributor();
        this.logic = new Logic(hD);
        this.logic.createFirstHumans();

    }

    @Override
    public void start(Stage window) {
        GridPane startSet = new GridPane();
        startSet.setMinWidth(150);
        startSet.setVgap(20);
        Button beginGame = new Button("Start new game");
        HBox bGBtn = new HBox(10);
        startSet.setAlignment(Pos.CENTER);
        bGBtn.getChildren().add(beginGame);
        bGBtn.setAlignment(Pos.CENTER_RIGHT);
        startSet.add(bGBtn, 0, 0);
        Button exitGame = new Button("Exit game");
        startSet.add(exitGame, 0, 2);
        Button loadGame = new Button("Load game");
//        startSet.add(loadGame, 0, 1);
        
        
        
        Scene start = new Scene(startSet);
        
        int[] workers = this.hD.getNumberOfWorkers();
        double[] resources = this.logic.getResourcesDisplay();
        GridPane setting = new GridPane();
        setting.setVgap(20);
        setting.setHgap(20);
        setting.setPadding(new Insets(25, 25, 25, 25));
        Label farmers = new Label("Farmers:" + workers[0]);
        Label workersL = new Label("Workers:" + workers[1]);
        Label scientists = new Label("Scientists:" + workers[2]);
        Label soldiers = new Label("Soldiers:" + workers[3]);
        setting.add(farmers, 1, 1);
        setting.add(workersL, 1, 3);
        setting.add(scientists, 3, 1);
        setting.add(soldiers, 3, 3);
        Label foodTools = new Label("Food:" + resources[0]);
        Label tools = new Label("Tools:" + resources[1]);
        Label scienceGuns = new Label("Science:" + resources[2]);
        Label guns = new Label("Guns:" + resources[3]);
        foodTools.setMinWidth(100);
        tools.setMinWidth(100);
        scienceGuns.setMinWidth(100);
        guns.setMinWidth(100);
        setting.add(foodTools, 1, 0);
        setting.add(tools, 2, 0);
        setting.add(scienceGuns, 3, 0);
        setting.add(guns, 4, 0);
//        setting.add(new Label("Tools:" + this.logic.getResources()[1]), 2, 0);
//        setting.add(new Label("Science:" + this.logic.getResources()[2]), 3, 0);
//        setting.add(new Label("Guns:" + this.logic.getResources()[3]), 4, 0);
        Label yearL = new Label("Year: " + this.logic.getYear());
        setting.add(yearL, 1, 5);
        Label unemployed = new Label("Unemployed: " + this.hD.numberOfUnemployed());
        setting.add(unemployed, 2, 5);
        Label children = new Label("Children: " + this.hD.numberOfChilds());
        setting.add(children, 3, 5);
        Label happiness = new Label("Happiness: " + this.logic.getHappiness());
        setting.add(happiness, 4, 5);
        Button btn = new Button("End Turn");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);
        setting.add(hbBtn, 4, 6);

        Button farmB = new Button("Assing to Farm");
        HBox hFarmB = new HBox(10);
        hFarmB.getChildren().add(farmB);
        setting.add(hFarmB, 1, 2);
        farmB.setOnAction((event) -> {
            logic.assignWorker(Factories.FARM);
            farmers.setText("Farmers: " + this.hD.getNumberOfWorkers()[0]);
            unemployed.setText("Unemployed: " + this.hD.numberOfUnemployed());
        });

        Button factoryB = new Button("Assing to Factory");
        HBox hFactoryB = new HBox(10);
        hFactoryB.getChildren().add(factoryB);
        setting.add(hFactoryB, 1, 4);
        factoryB.setOnAction((event) -> {
            logic.assignWorker(Factories.FACTORY);
            workersL.setText("Workers: " + this.hD.getNumberOfWorkers()[1]);
            unemployed.setText("Unemployed: " + this.hD.numberOfUnemployed());
        });
        Button laboratoryB = new Button("Assing to Laboratory");
        HBox hLaboratoryB = new HBox(10);
        hLaboratoryB.getChildren().add(laboratoryB);
        setting.add(hLaboratoryB, 3, 2);
        laboratoryB.setOnAction((event) -> {
            logic.assignWorker(Factories.LABORATORY);
            scientists.setText("Scientists: " + this.hD.getNumberOfWorkers()[2]);
            unemployed.setText("Unemployed: " + this.hD.numberOfUnemployed());
        });

        Button armyB = new Button("Assing to Army");
        HBox hArmyB = new HBox(10);
        hArmyB.getChildren().add(armyB);
        setting.add(hArmyB, 3, 4);
        armyB.setOnAction((event) -> {
            logic.assignWorker(Factories.ARMY);
            soldiers.setText("Soldiers: " + this.hD.getNumberOfWorkers()[3]);
            unemployed.setText("Unemployed: " + this.hD.numberOfUnemployed());
        });
        Button showHumans = new Button("Human overview");
        setting.add(showHumans, 1, 6);
        showHumans.setOnAction((event) -> {
            Stage humanList = new Stage();
            humanList.setTitle("List of Humans");
            ScrollPane box = new ScrollPane();
            box.setMaxHeight(600);
            box.setMinWidth(300);
            String text = this.hD.getHumansDisplay();
            box.setContent(new Text(text));
            Scene listScene = new Scene(box);
            humanList.setScene(listScene);
            humanList.show();
        });
        
        BorderPane setting2 = new BorderPane();
        setting2.setMinSize(50,50);
        setting2.setTop(new Label("You have lost"));
        
        Button endGame = new Button("Exit game");
        endGame.setOnAction((event) -> {
            window.close();
        });
        setting2.setCenter(new Label("You got in total " + this.logic.getResourcesDisplay()[2] + " science points and your reign lasted for "
                + this.logic.getYear() + " years."));
        setting2.setBottom(endGame);
        Scene view2 = new Scene(setting2);
        Scene view = new Scene(setting);
        beginGame.setOnAction((event) -> {
            window.setScene(view);
        });
        exitGame.setOnAction((event) -> {
            window.close();
        });
        loadGame.setOnAction((event) -> {
            
        });
        
        btn.setOnAction((event) -> {
            if (this.logic.endTurn()) {
                setting2.setCenter(new Label("You got in total " + this.logic.getResourcesDisplay()[2] + " science points and your reign lasted for "
                + this.logic.getYear() + " years."));
                window.setScene(view2);
            }
            int[] workers1 = this.hD.getNumberOfWorkers();
            double[] resources1 = this.logic.getResourcesDisplay();
            foodTools.setText("Food:" + resources1[0]);
            tools.setText("Tools:" + resources1[1]);
            scienceGuns.setText("Science:" + resources1[2]);
            guns.setText("Guns:" + resources1[3]);
            farmers.setText("Farmers: " + workers1[0]);
            workersL.setText("Workers: " + workers1[1]);
            scientists.setText("Scientists: " + workers1[2]);
            soldiers.setText("Soldiers: " + workers1[3]);
            yearL.setText("Year: " + this.logic.getYear());
            unemployed.setText("Unemployed: " + this.hD.numberOfUnemployed());
            children.setText("Children: " + this.hD.numberOfChilds());
            happiness.setText("Happiness: " + this.logic.getHappiness());
        });
        window.setScene(start);
        window.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}

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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
        int[] workers = this.hD.getNumberOfWorkers();
        double[] resources = this.logic.getResources();
        GridPane setting = new GridPane();
        setting.setVgap(20);
        setting.setHgap(20);
        setting.setPadding(new Insets(25, 25, 25, 25));
        Label farmers =new Label("Farmers:" + workers[0]);
        Label workersL =new Label("Workers:" + workers[1]);
        Label scientists =new Label("Scientists:" + workers[2]);
        Label soldiers =new Label("Soldiers:" + workers[3]);
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
        setting.add(guns, 4,0);
//        setting.add(new Label("Tools:" + this.logic.getResources()[1]), 2, 0);
//        setting.add(new Label("Science:" + this.logic.getResources()[2]), 3, 0);
//        setting.add(new Label("Guns:" + this.logic.getResources()[3]), 4, 0);
        Label yearL = new Label("Year: " + this.logic.getYear());
        setting.add(yearL, 1, 5);
        Label unemployed = new Label("Unemployed: " + this.hD.numberOfUnemployed());
        setting.add(unemployed, 2, 5);
        Label children = new Label("Children: " + this.hD.numberOfChilds());
        setting.add(children, 3,5);
        Button btn = new Button("End Turn");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(btn);
        setting.add(hbBtn, 4, 5);
        
        Button farmB = new Button("Assing to Farm");
        HBox hFarmB = new HBox(10);
        hFarmB.getChildren().add(farmB);
        setting.add(hFarmB, 1, 2);
        farmB.setOnAction((event) -> {
            logic.assignWorker(Factories.FARM);
            farmers.setText("Farmers:" + this.hD.getNumberOfWorkers()[0]);
            unemployed.setText("Unemployed: " + this.hD.numberOfUnemployed());
        });
        
        
        btn.setOnAction((event) -> {
            this.logic.endTurn();
            int[] workers1 = this.hD.getNumberOfWorkers();
            double[] resources1 = this.logic.getResources();
            foodTools.setText("Food:" + resources1[0]);
            tools.setText("Tools:" + resources1[1]);
            scienceGuns.setText("Science:" + resources[2]);
            guns.setText("Guns:" + resources[3]);
            farmers.setText("Farmers:" + workers[0]);
            workersL.setText("Workers:" + workers[1]);
            scientists.setText("Scientists:" + workers[2]);
            soldiers.setText("Soldiers:" + workers[3]);
            yearL.setText("Year: " + this.logic.getYear());
            unemployed.setText("Unemployed: " + this.hD.numberOfUnemployed());
            children.setText("Children: " + this.hD.numberOfChilds());
        });

        Scene view = new Scene(setting);

        window.setScene(view);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

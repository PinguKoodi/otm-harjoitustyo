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
        Human h = new Human("Kalle", 25);
        this.hD.setHumanFactory(h, Factories.LABORATORY);
        h= new Human("Ville", 25);
        this.hD.setHumanFactory(h, Factories.FARM);

    }

    @Override
    public void start(Stage window) {
        int[] workers = this.hD.getNumberOfWorkers();
        double[] resources = this.logic.getResources();
        GridPane setting = new GridPane();
        setting.setVgap(20);
        setting.setHgap(20);
        setting.setPadding(new Insets(25, 25, 25, 25));
        setting.add(new Label("Farmers:" + workers[0]), 1, 1);
        setting.add(new Label("Workers:" + workers[1]), 1, 2);
        setting.add(new Label("Scientists:" + workers[2]), 2, 1);
        setting.add(new Label("Soldiers:" + workers[3]), 2, 2);
        setting.add(new Label("Food:" + resources[0]
                + "   Tools:" + resources[1]), 1, 0);
        setting.add(new Label("Science:" + resources[2]
                + "   Guns:" + resources[3]), 2, 0);
//        setting.add(new Label("Tools:" + this.logic.getResources()[1]), 2, 0);
//        setting.add(new Label("Science:" + this.logic.getResources()[2]), 3, 0);
//        setting.add(new Label("Guns:" + this.logic.getResources()[3]), 4, 0);
        setting.add(new Label("Year: " + this.logic.getYear()), 1, 3);
        Button btn = new Button("End Turn");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        setting.add(hbBtn, 2, 3);
        btn.setOnAction((event) -> {
            this.logic.endTurn();
        });

        Scene view = new Scene(setting);

        window.setScene(view);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

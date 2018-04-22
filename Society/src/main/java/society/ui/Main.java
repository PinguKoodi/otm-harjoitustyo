/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package society.ui;

import javafx.scene.input.KeyEvent;
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
import javafx.scene.input.KeyCode;
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
    private Label farmers;
    private Label workersL;
    private Label scientists;
    private Label soldiers;
    private Label foodTools;
    private Label tools;
    private Label scienceGuns;
    private Label guns;
    private Label yearL;
    private Label unemployed;
    private Label children;
    private Label happiness;
    private Label foodProd;
    private Label toolProd;
    private Label scienceProd;
    private Label gunProd;

    @Override
    public void init() throws Exception {
        this.hD = new HumanDistributor();
        this.logic = new Logic(hD);

    }

    @Override
    public void start(Stage window2) {
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
        startSet.add(exitGame, 0, 3);
        Button loadGame = new Button("Load game");
        startSet.add(loadGame, 0, 1);
        Button help = new Button("Game guide");
        startSet.add(help, 0, 2);
        Scene start = new Scene(startSet);
        beginGame.setOnAction((event) -> {
            this.logic.startGame(false);
            gameWindow();
            window2.close();
        });
        exitGame.setOnAction((event) -> {
            window2.close();
        });
        loadGame.setOnAction((event) -> {
            this.logic.startGame(true);
            gameWindow();
            window2.close();
        });
        help.setOnAction((event) -> {
            gameGuideWindow();
        });
        window2.setScene(start);
        window2.show();
    }

    private void gameGuideWindow() {
        Stage guideWindow = new Stage();
        guideWindow.setTitle("Guide for noobs");
        VBox guide = new VBox();
        Button closeGuide = new Button("Close guide");
        closeGuide.setOnAction((event) -> {
            guideWindow.close();
        });
        Text guideText = new Text();
        guideText.setText(this.logic.getGuideText());
        guide.getChildren().add(guideText);
        guide.getChildren().add(closeGuide);
        Scene guideScene = new Scene(guide);
        guideWindow.setScene(guideScene);
        guideWindow.show();
    }

    private void gameWindow() {
        Stage window = new Stage();
        window.setTitle("Society");
        initializeLabels();
        GridPane setting = new GridPane();
        setting.setVgap(20);
        setting.setHgap(20);
        setting.setPadding(new Insets(25, 25, 25, 25));
        setting.add(farmers, 1, 1);
        setting.add(workersL, 1, 3);
        setting.add(scientists, 3, 1);
        setting.add(soldiers, 3, 3);
        setting.add(children, 3, 5);
        setting.add(unemployed, 2, 5);
        setting.add(yearL, 1, 5);
        setting.add(happiness, 4, 5);
        setting.add(foodTools, 1, 0);
        setting.add(tools, 2, 0);
        setting.add(scienceGuns, 3, 0);
        setting.add(guns, 4, 0);
        setting.add(foodProd, 1, 0);
        setting.add(toolProd, 2, 0);
        setting.add(scienceProd, 3, 0);
        setting.add(gunProd, 4, 0);
//        setting.add(new Label("Tools:" + this.logic.getResources()[1]), 2, 0);
//        setting.add(new Label("Science:" + this.logic.getResources()[2]), 3, 0);
//        setting.add(new Label("Guns:" + this.logic.getResources()[3]), 4, 0);
        Button endTurn = new Button("End Turn");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(endTurn);
        setting.add(hbBtn, 4, 6);
        Button saveGame = new Button("Save game");
        setting.add(saveGame, 2, 6);
        saveGame.setOnAction((event) -> {
            this.logic.saveToFile();
        });

        Button farmB = new Button("Assing to Farm");
        HBox hFarmB = new HBox(10);
        hFarmB.getChildren().add(farmB);
        setting.add(hFarmB, 1, 2);
        farmB.setOnAction((event) -> {
            logic.assignWorker(Factories.FARM);
            updateInfo();
        });
        Button factoryB = new Button("Assing to Factory");
        HBox hFactoryB = new HBox(10);
        hFactoryB.getChildren().add(factoryB);
        setting.add(hFactoryB, 1, 4);
        factoryB.setOnAction((event) -> {
            logic.assignWorker(Factories.FACTORY);
            updateInfo();
        });
        Button laboratoryB = new Button("Assing to Lab");
        HBox hLaboratoryB = new HBox(10);
        hLaboratoryB.getChildren().add(laboratoryB);
        setting.add(hLaboratoryB, 3, 2);
        laboratoryB.setOnAction((event) -> {
            logic.assignWorker(Factories.LABORATORY);
            updateInfo();
        });

        Button armyB = new Button("Assing to Army");
        HBox hArmyB = new HBox(10);
        hArmyB.getChildren().add(armyB);
        setting.add(hArmyB, 3, 4);
        armyB.setOnAction((event) -> {
            logic.assignWorker(Factories.ARMY);
            updateInfo();
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
        setting2.setMinSize(50, 50);
        setting2.setTop(new Label("You have lost"));
        Button endGameOnGameScreen = new Button("Exit game");
        endGameOnGameScreen.setOnAction((event) -> {
            window.close();
        });
        setting.add(endGameOnGameScreen, 3, 6);

        Button endGame = new Button("Exit game");
        endGame.setOnAction((event) -> {
            window.close();
        });
        setting2.setCenter(new Label("You got in total " + this.logic.getResourcesDisplay()[2] + " science points and your reign lasted for "
                + this.logic.getYear() + " years."));
        setting2.setBottom(endGame);
        Scene view2 = new Scene(setting2);
        Scene view = new Scene(setting);
        window.setScene(view);

        endTurn.setOnAction((event) -> {
            if (this.logic.endTurn()) {
                setting2.setCenter(new Label("You got in total " + this.logic.getResourcesDisplay()[2] + " science points and your reign lasted for "
                        + this.logic.getYear() + " years."));
                window.setScene(view2);
            }
            updateInfo();

        });

        view.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    endTurn.fire();
                }
            }
        });
        window.show();
    }

    private void updateInfo() {
        int[] workers1 = this.hD.getNumberOfWorkers();
        double[] resources1 = this.logic.getResourcesDisplay();
        double[] prods = this.logic.getProductionDisplay();
        foodTools.setText("Food: " + resources1[0]);
        tools.setText("Tools: " + resources1[1]);
        scienceGuns.setText("Science: " + resources1[2]);
        guns.setText("Guns: " + resources1[3]);
        farmers.setText("Farmers: " + workers1[0]);
        workersL.setText("Workers: " + workers1[1]);
        scientists.setText("Scientists: " + workers1[2]);
        soldiers.setText("Soldiers: " + workers1[3]);
        yearL.setText("Year: " + this.logic.getYear());
        unemployed.setText("Unemployed: " + this.hD.numberOfUnemployed());
        children.setText("Children: " + this.hD.numberOfChilds());
        happiness.setText("Happiness: " + this.logic.getHappiness());
        if (prods[0] < 0) {
            foodProd.setText("(" + prods[0] + ")");
            foodProd.setTextFill(Color.web("FF0000"));
        } else {
            foodProd.setText("(+" + (prods[0]) + ")");
            foodProd.setTextFill(Color.web("00CC00"));
        }
        toolProd.setText("(+" + prods[1] + ")");
        scienceProd.setText("(+" + prods[2] + ")");
        gunProd.setText("(+" + prods[3] + ")");
    }

    private void initializeLabels() {
        int[] workers = this.hD.getNumberOfWorkers();
        double[] resources = this.logic.getResourcesDisplay();
        double[] prods = this.logic.getProductionDisplay();
        farmers = new Label("Farmers: " + workers[0]);
        workersL = new Label("Workers: " + workers[1]);
        scientists = new Label("Scientists: " + workers[2]);
        soldiers = new Label("Soldiers: " + workers[3]);
        foodTools = new Label("Food: " + resources[0]);
        tools = new Label("Tools: " + resources[1]);
        scienceGuns = new Label("Science: " + resources[2]);
        guns = new Label("Guns: " + resources[3]);
        yearL = new Label("Year: " + this.logic.getYear());
        unemployed = new Label("Unemployed: " + this.hD.numberOfUnemployed());
        children = new Label("Children: " + this.hD.numberOfChilds());
        happiness = new Label("Happiness: " + this.logic.getHappiness());
        foodProd = new Label("(+" + (prods[0]) + ")");
        toolProd = new Label("(+" + prods[1] + ")");
        scienceProd = new Label("(+" + prods[2] + ")");
        gunProd = new Label("(+" + prods[3] + ")");
        if (prods[0] < 0) {
            foodProd.setText("(" + prods[0] + ")");
            foodProd.setTextFill(Color.web("FF0000"));
        } else {
            foodProd.setTextFill(Color.web("00CC00"));
        }
        foodProd.setTranslateX(70.0);
        toolProd.setTextFill(Color.web("00CC00"));
        toolProd.setTranslateX(70.0);
        scienceProd.setTextFill(Color.web("00CC00"));
        scienceProd.setTranslateX(70.0);
        gunProd.setTextFill(Color.web("00CC00"));
        gunProd.setTranslateX(70.0);
        foodTools.setMinWidth(100);
        tools.setMinWidth(100);
        scienceGuns.setMinWidth(100);
        guns.setMinWidth(100);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

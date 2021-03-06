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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import society.domain.Distributor;
import society.domain.Factories;

/**
 *
 * @author pyylauri
 */
public class Main extends Application {

    private Logic logic;
    private Distributor hD;
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
    private Text humanList;

    @Override
    public void init() throws Exception {
        this.hD = new HumanDistributor();
        this.logic = new Logic(hD);

    }

    @Override
    public void start(Stage window2) {
        GridPane startSet = new GridPane();
        startSet.setVgap(20);
        startSet.setPadding(new Insets(20, 20, 20, 20));
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
        for (Node n : startSet.getChildren()) {
            GridPane.setHalignment(n, HPos.CENTER);
        }
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
        guideWindow.setTitle("Guide for beginners");
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
        guide.setAlignment(Pos.CENTER);
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
        setting.add(scientists, 2, 1);
        setting.add(soldiers, 2, 3);
        setting.add(children, 2, 5);
        setting.add(unemployed, 1, 5);
        setting.add(yearL, 1, 0);
        setting.add(happiness, 4, 5);
        setting.add(foodTools, 3, 1);
        setting.add(tools, 3, 2);
        setting.add(scienceGuns, 3, 3);
        setting.add(guns, 3, 4);
        setting.add(foodProd, 4, 1);
        setting.add(toolProd, 4, 2);
        setting.add(scienceProd, 4, 3);
        setting.add(gunProd, 4, 4);
        Button saveGame = new Button("Save game");
        setting.add(saveGame, 2, 6);
        saveGame.setOnAction((event) -> {
            fileSavedWindow(this.logic.saveToFile());
        });
        Button farmB = new Button("Assing to Farm");
        HBox hFarmB = new HBox(10);
        hFarmB.getChildren().add(farmB);
        farmB.setOnAction((event) -> {
            logic.assignWorker(Factories.FARM);
            updateInfo();
        });
        Button factoryB = new Button("Assing to Factory");
        HBox hFactoryB = new HBox(10);
        hFactoryB.getChildren().add(factoryB);
        factoryB.setOnAction((event) -> {
            logic.assignWorker(Factories.FACTORY);
            updateInfo();
        });
        Button laboratoryB = new Button("Assing to Lab");
        HBox hLaboratoryB = new HBox(10);
        hLaboratoryB.getChildren().add(laboratoryB);
        laboratoryB.setOnAction((event) -> {
            logic.assignWorker(Factories.LABORATORY);
            updateInfo();
        });

        Button armyB = new Button("Assing to Army");
        HBox hArmyB = new HBox(10);
        hArmyB.getChildren().add(armyB);
        armyB.setOnAction((event) -> {
            logic.assignWorker(Factories.ARMY);
            updateInfo();
        });
        setting.add(hLaboratoryB, 2, 2);
        setting.add(hArmyB, 2, 4);
        setting.add(hFactoryB, 1, 4);
        setting.add(hFarmB, 1, 2);

        Button showHumans = new Button("Human overview");
        setting.add(showHumans, 3, 0);
        Stage humanListStage = new Stage();
        showHumans.setOnAction((event) -> {
            humanListStage.setTitle("List of Humans");
            ScrollPane box = new ScrollPane();
            box.setMaxHeight(600);
            box.setMinWidth(300);
            String text = this.hD.getWorkerUnitsDisplay();
            this.humanList.setText(text);
            box.setContent(this.humanList);
            Scene listScene = new Scene(box);
            humanListStage.setScene(listScene);
            humanListStage.show();
        });
        Button help = new Button("Game guide");
        setting.add(help, 4, 0);
        help.setOnAction((event) -> {
            gameGuideWindow();
        });

        Button endGameOnGameScreen = new Button("Main menu");
        endGameOnGameScreen.setOnAction((event) -> {
            humanListStage.close();
            start(new Stage());
            window.close();
        });
        setting.add(endGameOnGameScreen, 3, 6);

        Scene view = new Scene(setting);
        window.setScene(view);
        Button endTurn = new Button("End Turn");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(endTurn);
        setting.add(hbBtn, 4, 6);
        endTurn.setOnAction((event) -> {
            if (this.logic.endTurn()) {
                endGameScreen(window);
            }
            updateInfo();

        });

        view.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                endTurn.fire();
            } else if (event.getCode() == KeyCode.A) {
                farmB.fire();
            } else if (event.getCode() == KeyCode.S) {
                factoryB.fire();
            } else if (event.getCode() == KeyCode.D) {
                laboratoryB.fire();
            } else if (event.getCode() == KeyCode.F) {
                armyB.fire();
            }
        });
        window.show();
    }

    private void fileSavedWindow(boolean saveCompleted) {
        Stage savedWindow = new Stage();
        BorderPane savedSetting = new BorderPane();
        Button closeWindow = new Button("Ok");
        Text fileSavedText = new Text();
        fileSavedText.setText("Your game has been saved");
        if (!saveCompleted) {
            fileSavedText.setText(("There was error saving your game"));
        }
        savedSetting.setCenter(fileSavedText);
        savedSetting.setBottom(closeWindow);
        BorderPane.setAlignment(closeWindow, Pos.CENTER);
        BorderPane.setMargin(closeWindow, new Insets(5, 5, 5, 5));
        BorderPane.setMargin(fileSavedText, new Insets(5, 5, 5, 5));
        closeWindow.setOnAction((event) -> {
            savedWindow.close();
        });
        savedSetting.setMinHeight(60);
        savedWindow.setScene(new Scene(savedSetting));
        savedWindow.show();
    }

    private void endGameScreen(Stage stage) {

        BorderPane setting2 = new BorderPane();
        setting2.setMinSize(50, 50);
        Text topText = new Text("You have lost");
        topText.setFill(Color.RED);
        topText.setFont(Font.font(Font.getDefault().getName(), FontWeight.BOLD, 30));
        setting2.setTop(topText);
        BorderPane.setAlignment(topText, Pos.CENTER);
        Button endGame = new Button("Exit game");
        BorderPane.setAlignment(endGame, Pos.CENTER);
        endGame.setOnAction((event) -> {
            stage.close();
        });
        Label midLabel = new Label("You got in total " + this.logic.getResourcesDisplay()[2] + " science points and your reign lasted for "
                + this.logic.getYear() + " years.");
        setting2.setCenter(midLabel);
        setting2.setBottom(endGame);
        setting2.setPadding(new Insets(0, 0, 20, 0));
        midLabel.setPadding(new Insets(20, 0, 20, 0));
        Scene view2 = new Scene(setting2);
        stage.setScene(view2);
    }

    private void initializeLabels() {
        int[] workers = this.hD.getNumberOfWorkers();
        double[] resources = this.logic.getResourcesDisplay();
        double[] prods = this.logic.getProductionDisplay();
        this.humanList = new Text(this.hD.getWorkerUnitsDisplay());
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
            foodProd.setTextFill(Color.RED);
        } else {
            foodProd.setTextFill(Color.GREEN);
        }
        if (this.hD.soldierPercent() < 0.1) {
            this.soldiers.setTextFill(Color.RED);
        } else {
            this.soldiers.setTextFill(Color.BLACK);
        }

        toolProd.setTextFill(Color.GREEN);
        scienceProd.setTextFill(Color.GREEN);
        gunProd.setTextFill(Color.GREEN);
        foodTools.setMinWidth(110);
        tools.setMinWidth(110);
        scienceGuns.setMinWidth(110);
        guns.setMinWidth(110);
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
            foodProd.setTextFill(Color.RED);
        } else {
            foodProd.setText("(+" + (prods[0]) + ")");
            foodProd.setTextFill(Color.GREEN);
        }
        if (this.hD.soldierPercent() < 0.1) {
            this.soldiers.setTextFill(Color.RED);
        } else {
            this.soldiers.setTextFill(Color.BLACK);
        }
        toolProd.setText("(+" + prods[1] + ")");
        scienceProd.setText("(+" + prods[2] + ")");
        gunProd.setText("(+" + prods[3] + ")");
        this.humanList.setText(this.hD.getWorkerUnitsDisplay());
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}

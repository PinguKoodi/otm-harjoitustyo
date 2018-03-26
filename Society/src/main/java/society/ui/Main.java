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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import society.domain.Factories;
import society.domain.Human;
/**
 *
 * @author pyylauri
 */
public class Main extends Application{
    private Logic logic;
    private HumanDistributor hD;
    
    @Override
    public void init() throws Exception {
        this.hD = new HumanDistributor();
        this.logic = new Logic(hD);
        Human h = new Human("Kalle", 25);
        this.hD.setHumanFactory(h, Factories.LABORATORY);
        
    }
    
    
    @Override
    public void start(Stage window) {
        
        
        GridPane setting = new GridPane();
        setting.setVgap(50);
        setting.setHgap(50);
        setting.add(new Label("Farmers:" + this.hD.getNumberOfWorkers()[0]), 1, 1);
        setting.add(new Label("Workers:" + this.hD.getNumberOfWorkers()[1]), 1, 2);
        setting.add(new Label("Scientists:" + this.hD.getNumberOfWorkers()[2]), 2, 1);
        setting.add(new Label("Soldiers:" + this.hD.getNumberOfWorkers()[3]), 2, 2);
//        setting.add(new Label("Food:" + this.logic.getResources()[0]), 1, 1);
//        setting.add(new Label("Tools:" + this.logic.getResources()[1]), 1, 2);
//        setting.add(new Label("Science:" + this.logic.getResources()[2]), 2, 1);
//        setting.add(new Label("Guns:" + this.logic.getResources()[3]), 2, 2);
        
        
        Scene view = new Scene(setting);

        window.setScene(view);
        window.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

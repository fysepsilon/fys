/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Paras
 */
public class FYS extends Application {
    
    public static Stage parentWindow;
    
    @Override
    public void start(Stage stage) throws Exception {
        parentWindow = stage; 
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void changeToAnotherFXML(String changeToWindow) throws IOException{
        Parent window1;
        window1 = FXMLLoader.load(getClass().getResource(changeToWindow));
        Stage mainStage;
        mainStage = FYS.parentWindow;
        mainStage.getScene().setRoot(window1);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

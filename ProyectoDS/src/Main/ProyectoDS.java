/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Israel
 */
public class ProyectoDS extends Application {
    public static Stage window;
    
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root;
        window = stage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProyectoDS.class.getResource("vistaControl/login/FXMLDocument.fxml"));
        
        root = (BorderPane) loader.load();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

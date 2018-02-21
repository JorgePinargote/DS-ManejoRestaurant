/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Interfaces;

import Main.ProyectoDS;
import Main.model.Trabajador;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class InAdministrador extends InterGraficas {

    public InAdministrador(Trabajador trabajador) {
        super(trabajador);
    }

    @Override
    public void crear() {
        Stage stage = new Stage();
        AnchorPane root;
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProyectoDS.class.getResource("vistaControl/Administrador/PrincipalA.fxml"));
        try {
            root = (AnchorPane) loader.load();
             Scene scene = new Scene(root);
             stage.setScene(scene);
             stage.show();
             System.out.println("entro");
        } catch (IOException ex) {
            System.out.println("error");
            ex.printStackTrace();
        }
    }
    
}

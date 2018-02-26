/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Cajero;

import Main.Login;
import Main.ProyectoDS;
import Main.model.Persona;
import Main.model.detallePedido;
import Main.vistaControl.Mesero.NuevoItemController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class PrincipalCController implements Initializable {
    
    @FXML
    private Label lblnombre;
    
    //TABCOMENSALES
    @FXML
    private TableView<Persona> tbclientes;
    
    @FXML
    private TableColumn<Persona, String> nombreColumn;
    @FXML
    private TableColumn<Persona, String> apellidoColumn;
    @FXML
    private TableColumn<Persona, String> cedulaColumn;
    @FXML
    private TableColumn<Persona, String> direccionColumn;
    @FXML
    private TableColumn<Persona, String> telefonoColumn;
    @FXML
    private Button btnnuevo;
    @FXML
    private Button btneditar;
    @FXML
    private Button btnaddtarjeta;
    
    
    @FXML
    private CobrarController cobrarController;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Login login = Login.getInstance();
        lblnombre.setText(login.getTrabajador().getNombre() +" "+ login.getTrabajador().getApellido());
        
        Persona.CargarPersonAgain();
        tbclientes.setItems(Persona.getPersonData());
        
        nombreColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
        apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().getApellidoProperty());
        cedulaColumn.setCellValueFactory(cellData -> cellData.getValue().getCedulaProperty());
        direccionColumn.setCellValueFactory(cellData -> cellData.getValue().getDireccionProperty());
        telefonoColumn.setCellValueFactory(cellData -> cellData.getValue().getTelefonoProperty());
 
    }
    
    public void handleNuevo(){
        Stage stage = new Stage();
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProyectoDS.class.getResource("vistaControl/Cajero/Nuevo_Editar_Cliente.fxml"));
        
        try {
             root = (AnchorPane) loader.load();
             Scene scene = new Scene(root);
             
             NuevoEditarClienteController Controller = loader.getController();
             Controller.setMyStage(stage);
             
             stage.setScene(scene);
             stage.show();
             
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleEditar(){
        Persona persona = tbclientes.getSelectionModel().getSelectedItem();
        if(persona != null){
            Stage stage = new Stage();
            AnchorPane root;
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ProyectoDS.class.getResource("vistaControl/Cajero/Nuevo_Editar_Cliente.fxml"));

            try {
                 root = (AnchorPane) loader.load();
                 Scene scene = new Scene(root);

                 NuevoEditarClienteController Controller = loader.getController();


                 Controller.setPerson(persona);
                 Controller.setMyStage(stage);
                 //NuevoController.setDetalles(detalles);
                 //NuevoController.setMystage(stage);

                 stage.setScene(scene);
                 stage.show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
    public void handleAddTarjeta(){
            Persona persona = tbclientes.getSelectionModel().getSelectedItem();
            if(persona!=null){
                Stage stage = new Stage();
                AnchorPane root;
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ProyectoDS.class.getResource("vistaControl/Cajero/MetVisa.fxml"));

                try {
                     root = (AnchorPane) loader.load();
                     Scene scene = new Scene(root);

                     MetVisaController Controller = loader.getController();

                     Controller.setPersona(persona);
                     Controller.setStage(stage);

                     stage.setScene(scene);
                     stage.show();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
                
    }
    
}

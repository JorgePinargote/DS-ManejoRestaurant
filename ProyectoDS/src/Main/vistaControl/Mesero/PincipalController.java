/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Mesero;

import Main.Help.CuentaConverter;
import Main.Help.MesaConverter;
import Main.Login;
import Main.ProyectoDS;
import Main.model.Categoria;
import Main.model.Cuenta;
import Main.model.MenuElemento;
import Main.model.Mesa;
import Main.model.Mesero;
import Main.model.Pedido;
import Main.model.Persona;
import Main.model.detallePedido;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author Jorge Pinargote
 */
public class PincipalController implements Initializable{
    
    private ObservableList<detallePedido> detalles = FXCollections.observableArrayList();
    private ObservableList<Cuenta> cuentas = FXCollections.observableArrayList();
     
    @FXML
    private Label lblnombre;
    
    //TAB 1 
    @FXML
    private ChoiceBox<Mesa> chmesa;
    @FXML
    private CheckBox chbpreferencial;
    @FXML 
    private ChoiceBox<Cuenta> chcuenta;
    @FXML
    private TableView<detallePedido> tbdetalle;
    @FXML
    private TableColumn<detallePedido, String> menuColumn;
    @FXML
    private TableColumn<detallePedido, String> categoriaColumn;
    @FXML
    private TableColumn<detallePedido, Number> cantColumn;
    @FXML
    private TableColumn<detallePedido, String> obsColumn;
    @FXML
    private Button btnadditem;
    @FXML
    private Button btneliminar;
    @FXML
    private Button btnsend;
    
    @FXML
    private EstadoController estadoController;

    @FXML
    private ListosController listosController;
    
    
    
    public void handleadditem(){
        Stage stage = new Stage();
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProyectoDS.class.getResource("vistaControl/Mesero/NuevoItem.fxml"));
        
        try {
             root = (AnchorPane) loader.load();
             Scene scene = new Scene(root);
             
             NuevoItemController NuevoController = loader.getController();
             NuevoController.setDetalles(detalles);
             NuevoController.setMystage(stage);
             
             stage.setScene(scene);
             stage.show();
             
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
         Login login = Login.getInstance();
         lblnombre.setText(login.getTrabajador().getNombre() +" "+ login.getTrabajador().getApellido());
         if(login.getTrabajador() instanceof Mesero)((Mesero)login.getTrabajador()).starCheck();
         
         chmesa.setConverter(new MesaConverter());
         Mesa.cargarMesaAgain();
         chmesa.setItems(Mesa.getMesaData());
         
         chcuenta.setConverter(new CuentaConverter());
         chcuenta.setItems(cuentas);
         
         chmesa.getSelectionModel().selectedItemProperty()
                .addListener((v,oldValue,newValue)-> Mesa.reinitCuentas((Mesa)newValue,cuentas));
      
         tbdetalle.setItems(detalles);
         menuColumn.setCellValueFactory(cellData -> cellData.getValue().getItem().getNombreProperty());
         categoriaColumn.setCellValueFactory(cellData -> cellData.getValue().getItem().getCategoria().getNombreProperty());
         cantColumn.setCellValueFactory(cellData -> cellData.getValue().getCantidadProperty());
         obsColumn.setCellValueFactory(cellData -> cellData.getValue().getObservacionesProperty());
         
    }
    
     
     public void handleEliminardetalle(){
         int index = tbdetalle.getSelectionModel().getSelectedIndex();
         if(index>=0){
             detalles.remove(index);
         }
     }
    
    

     private class ConverterchCliente extends StringConverter<Persona> {
       Persona persona; 
            
       @Override
        public String toString(Persona persona) {
            this.persona = persona;
            return persona.getNombre() + " " + persona.getApellido() + " " + persona.getCedula();
        }
        
       @Override
        public Persona fromString(String string) {
           return this.persona;
        }
        
    }
     
     
     public void handleSendPedido(){
        Mesa mesa = chmesa.getSelectionModel().getSelectedItem();
        Cuenta cuenta = chcuenta.getSelectionModel().getSelectedItem();

        int nueva = chcuenta.getSelectionModel().getSelectedIndex();
        
        if(cuenta.getIdcuenta()== 0){
            int id = mesa.createNewCuenta();
            cuenta.setIdcuenta(id);
            chcuenta.getItems().remove(nueva);
            
            chcuenta.getItems().add(0,new Cuenta());
            chcuenta.getItems().add(cuenta);
        }
        
        Pedido pedido = new Pedido();
        
        for(int i=0;i<detalles.size();i++){
            pedido.addDetalle(detalles.get(i));
        }
        detalles.clear();
        pedido.setPreferencial(chbpreferencial.isSelected());
        
        int idpedido = cuenta.sendPedido(pedido);
        pedido.setIdpedido(idpedido);
        cuenta.addPedido(pedido);
        pedido.sendDetalles();
        
        Login login = Login.getInstance();
        Mesero mesero = (Mesero)login.getTrabajador();
        mesero.addPedido(pedido);
        
     }
     
     
    
    
    
    

    
}

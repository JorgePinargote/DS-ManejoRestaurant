/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Mesero;

import Main.Help.CuentaConverter;
import Main.Help.MesaConverter;
import Main.Help.PedidoConverter;
import Main.ProyectoDS;
import Main.model.Cuenta;
import Main.model.Mesa;
import Main.model.Pedido;
import Main.model.detallePedido;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class EstadoController implements Initializable{
    private ObservableList<Cuenta> cuentas = FXCollections.observableArrayList();
    private ObservableList<Pedido> pedidos = FXCollections.observableArrayList();
    
    private ObservableList<detallePedido> eliminados = FXCollections.observableArrayList();
    private ObservableList<detallePedido> agregados = FXCollections.observableArrayList();
    
    private ObservableList<detallePedido> detalles = FXCollections.observableArrayList();
    
    
  
    @FXML
    private ChoiceBox chMesa2;
    @FXML
    private ChoiceBox chcuenta2;
    @FXML
    private ChoiceBox<Pedido> chpedido;
    @FXML
    private Label lblestado;
    @FXML
    private Label lbltiempo;
    @FXML
    private TableView tbdetalle2;
    
    @FXML
    private TableColumn<detallePedido, String> menuColumn2;
    @FXML
    private TableColumn<detallePedido, String> categoriaColumn2;
    @FXML
    private TableColumn<detallePedido, Number> cantColumn2;
    @FXML
    private TableColumn<detallePedido, String> obsColumn2;
    
    
    @FXML
    private Button btnadditem2;
    @FXML
    private Button btneliminar2;
    @FXML
    private Button btneliminarpedido;
    @FXML
    private Button btnupdatepedido; 
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chMesa2.setConverter(new MesaConverter());
        Mesa.cargarMesaAgain();
        chMesa2.setItems(Mesa.getMesaData());
        
        chcuenta2.setConverter(new CuentaConverter());
        chcuenta2.setItems(cuentas);
        
        chMesa2.getSelectionModel().selectedItemProperty()
                .addListener((v,oldValue,newValue)-> Mesa.changeCuentas((Mesa)newValue,cuentas));
        
        chpedido.setConverter(new PedidoConverter());
        chpedido.setItems(pedidos);
        
        
        chcuenta2.getSelectionModel().selectedItemProperty()
                .addListener((v,oldValue,newValue)-> Cuenta.changePedidos((Cuenta)newValue,pedidos));
        
        chpedido.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Pedido>() {
            @Override
            public void changed(ObservableValue<? extends Pedido> observable, Pedido oldValue, Pedido newValue) {
               Pedido.ChangeDetalles(newValue, detalles);
               if(newValue!=null){
                    lbltiempo.setText((int)newValue.getTiempolisto() + " min");
                    agregados.clear();
                    agregados.addAll(detalles);
                    if(newValue.isAtendiendo()){
                        lblestado.setText("Si");
                        btnadditem2.setDisable(true);
                        btneliminar2.setDisable(true);
                        btneliminarpedido.setDisable(true);
                        btnupdatepedido.setDisable(true);
                    }else{
                        lblestado.setText("No");
                        btnadditem2.setDisable(false);
                        btneliminar2.setDisable(false);
                        btneliminarpedido.setDisable(false);
                        btnupdatepedido.setDisable(false);
                    }
               }else{
                   lbltiempo.setText(" ");
                   lblestado.setText(" ");
                   agregados.clear();
               }
            }
        });
            
        tbdetalle2.setItems(agregados);
        menuColumn2.setCellValueFactory(cellData -> cellData.getValue().getItem().getNombreProperty());
        categoriaColumn2.setCellValueFactory(cellData -> cellData.getValue().getItem().getCategoria().getNombreProperty());
        cantColumn2.setCellValueFactory(cellData -> cellData.getValue().getCantidadProperty());
        obsColumn2.setCellValueFactory(cellData -> cellData.getValue().getObservacionesProperty());
        
    }
    
    public void handleadditem2(){
        Stage stage = new Stage();
        AnchorPane root;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ProyectoDS.class.getResource("vistaControl/Mesero/NuevoItem.fxml"));
        
        try {
             root = (AnchorPane) loader.load();
             Scene scene = new Scene(root);
             
             NuevoItemController NuevoController = loader.getController();
             NuevoController.setDetalles(agregados);
             NuevoController.setMystage(stage);
             
             stage.setScene(scene);
             stage.show();
             
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void handleEliminardetalle(){
         int index = tbdetalle2.getSelectionModel().getSelectedIndex();
         if(index>=0){
            eliminados.add(agregados.remove(index));  
         }
    }
    
    public void eliminarPedido(){
        Pedido pedido = chpedido.getSelectionModel().getSelectedItem(); 
        pedido.deletePedido();
        pedidos.remove(pedido);
    }
    
    public void UpdatePedido(){
        detallePedido.deleteDetalles(eliminados);
        
        
        Pedido pedido = chpedido.getSelectionModel().getSelectedItem();
        
        
        pedido.getItemsPedidos().removeAll(eliminados);
        detalles.removeAll(eliminados);
        agregados.removeAll(detalles);
        
        for(detallePedido detalle : agregados){
            pedido.addDetalle(detalle);
        }
        
        pedido.sendDetalles(agregados);
        pedido.update();
        
        
    }
     
}

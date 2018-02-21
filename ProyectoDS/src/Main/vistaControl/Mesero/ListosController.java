/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Mesero;

import Main.Help.infoEntrega;
import Main.Login;
import Main.model.Mesero;
import Main.model.detallePedido;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Jorge Pinargote
 */
public class ListosController implements Initializable {
    
    @FXML
    private Button btnentregar;
    @FXML
    private TableView<infoEntrega> tbentregar;
    @FXML
    
    private TableColumn<infoEntrega, Number> mesaColumn;
    @FXML
    private TableColumn<infoEntrega, Number> cuentaColumn;
    @FXML
    private TableColumn<infoEntrega, Number> pedidoColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        Login login = Login.getInstance();
        Mesero mesero = (Mesero)login.getTrabajador();
        
        tbentregar.setItems(mesero.getPedidosListos());
        mesaColumn.setCellValueFactory(cellData -> cellData.getValue().getMesa());
        cuentaColumn.setCellValueFactory(cellData -> cellData.getValue().getCuenta());
        pedidoColumn.setCellValueFactory(cellData -> cellData.getValue().getPedido());
  
    }
    
    public void handleEntregar(){
        int index = tbentregar.getSelectionModel().getSelectedIndex();
        if(index>=0){
            Login login = Login.getInstance();
            Mesero mesero = (Mesero)login.getTrabajador();
            mesero.getPedidosListos().remove(index);
        }
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Cocinero;

import Main.Login;
import Main.model.Cocinero;
import Main.model.Pedido;
import Main.model.detallePedido;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Jorge Pinargote
 */
public class PrincipalMController implements Initializable {
    Cocinero cocinero;
    
    @FXML 
    private Label lblnombre;
    
    //TAB1
    @FXML 
    private TableView<Pedido> tbanormal;
    @FXML
    private TableColumn<Pedido, Number> npedido;
    @FXML
    private TableColumn<Pedido, Number> nestimado;
 
    
    
    @FXML
    private TableView<Pedido> tbpreferencial;
    @FXML
    private TableColumn<Pedido, Number> ppedido;
    @FXML
    private TableColumn<Pedido, Number> pestimado;
    
    
    
    @FXML
    private Button btnsiguiente;
    
    
    //TAB2
    @FXML
    private TableView tbquitar;
    @FXML
    private Button btnquitar;
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //To change body of generated methods, choose Tools | Templates.
      Login login = Login.getInstance();
      lblnombre.setText(login.getTrabajador().getNombre() +" "+ login.getTrabajador().getApellido());
      
      cocinero = (Cocinero)login.getTrabajador();
      cocinero.cargarPedidos(0);//normales
      cocinero.cargarPedidos(1);//preferenciales
      
      tbanormal.setItems(cocinero.getPedidosNormal());
      tbpreferencial.setItems(cocinero.getPedidosPrioridad());
      
      npedido.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
      nestimado.setCellValueFactory(cellData -> cellData.getValue().getTiempoProperty());
      
      ppedido.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
      pestimado.setCellValueFactory(cellData -> cellData.getValue().getTiempoProperty());
    }
    
    public void handleSiguiente(){
    
    
    }
    
}

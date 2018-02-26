/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Cocinero;

import Main.Login;
import Main.ProyectoDS;
import Main.model.Cocinero;
import Main.model.MenuElemento;
import Main.model.Pedido;
import Main.model.detallePedido;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class PrincipalMController implements Initializable {
    Cocinero cocinero;
    ObservableList<MenuElemento> allitems = FXCollections.observableArrayList();;
    
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
    private TableView<MenuElemento> tbquitar;
    @FXML
    private TableColumn<MenuElemento, String> menuColumn;
    @FXML
    private TableColumn<MenuElemento, String> categoriaColumn;
    @FXML
    private TableColumn<MenuElemento, Number> disponiblesColumn;
    
    
    @FXML
    private Button btnquitar;
    
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      //To change body of generated methods, choose Tools | Templates.
      Login login = Login.getInstance();
      lblnombre.setText(login.getTrabajador().getNombre() +" "+ login.getTrabajador().getApellido());
      if(login.getTrabajador() instanceof Cocinero){
            cocinero = (Cocinero)login.getTrabajador();
            cocinero.cargarPedidos(0);//normales
            cocinero.cargarPedidos(1);//preferenciales
      }
      
      
      tbanormal.setItems(cocinero.getPedidosNormal());
      tbpreferencial.setItems(cocinero.getPedidosPrioridad());
      
      npedido.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
      nestimado.setCellValueFactory(cellData -> cellData.getValue().getTiempoProperty());
      
      ppedido.setCellValueFactory(cellData -> cellData.getValue().getIdProperty());
      pestimado.setCellValueFactory(cellData -> cellData.getValue().getTiempoProperty());
      
      //tab 2 
      
      MenuElemento.setAllItems(allitems);
      tbquitar.setItems(allitems);
      
      menuColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
      categoriaColumn.setCellValueFactory(cellData -> cellData.getValue().getCategoria().getNombreProperty());
      disponiblesColumn.setCellValueFactory(cellData -> cellData.getValue().getUnidadesRestantesProperty());
      
      
    }
    
    public void handleSiguiente(){
        if(!(cocinero.isNormalEmpty() && cocinero.isPrioridadEmpty())){
                cocinero.atengerSiguiente();
                Stage stage = new Stage();
                AnchorPane root;

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ProyectoDS.class.getResource("vistaControl/Cocinero/Atendiendo.fxml"));

                try {
                     root = (AnchorPane) loader.load();
                     Scene scene = new Scene(root);

                     AtendiendoController Controller = loader.getController();
                     Controller.setMystage(stage);

                     stage.setScene(scene);
                     stage.show();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
    }
    
    
    public void handleQuitar(){
        int item= tbquitar.getSelectionModel().getSelectedIndex();
        if(item >= 0){
            MenuElemento menuitem = allitems.remove(item);
            menuitem.Quitar();
        }
    }
    
}

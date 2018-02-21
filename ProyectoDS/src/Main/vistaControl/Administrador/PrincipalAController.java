/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Administrador;

import Main.Conexion;
import Main.Login;
import Main.model.Mesa;
import Main.model.Trabajador;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class PrincipalAController implements Initializable {
    
   
    
    @FXML
    private Label lblnombre;
    
   
    
    ObservableList<String> ambienteslist = FXCollections.observableArrayList("VIP","NORMAL");
    
    //TABAMBIENTE
    @FXML
    private TableView<Mesa> tbmesasilla; 
    @FXML
    private ChoiceBox<String> chambiente;
    @FXML
    private Button btnaddmesa; 
    @FXML
    private Button btnaddsilla; 
    @FXML
    private Button btnquitarmesa; 
    @FXML
    private Button btnquitarsilla;
    @FXML
    private Button btneliminaramb;
    @FXML
    private TableColumn<Mesa,SimpleStringProperty> nombreMesa;
    @FXML
    private TableColumn<Mesa,SimpleStringProperty> numSillas; 
    
    @FXML
    private TableColumn<Mesa,SimpleStringProperty> ambiente;
    
    
    //TABUSUARIO
    @FXML
    private TableView tbusuario;
    @FXML
    private Button btnnuevo; 
    @FXML
    private Button btneditar; 
    @FXML
    private Button btnhabilitar;
    @FXML
    private Button btneliminar;
    
    //TABREPORTES
    @FXML
    private RadioButton rdbplato;
    @FXML
    private RadioButton rdbmesero;
    @FXML
    private RadioButton rdbambiente;
    @FXML
    private RadioButton rdbcategoria;
    @FXML
    private ChoiceBox chplatorep;
    @FXML
    private ChoiceBox chmeseroep;
    @FXML
    private ChoiceBox chambienterep;
    @FXML
    private ChoiceBox chcategoriarep;
    @FXML
    private DatePicker dpdesde;
    @FXML
    private DatePicker dphasta;
    @FXML 
    private Button btngenerar;
    
    
    @FXML
    public void agregarMesa(){
    
        
        tbmesasilla.setItems(this.getMesass());
    
    }
    
    @FXML
    
    public void ButtonClicked(){
        ObservableList<Mesa> row , allRows;
        allRows = tbmesasilla.getItems();
        row = tbmesasilla.getSelectionModel().getSelectedItems();         
        row.forEach(allRows::remove);
    }
    
    @FXML
    public void agregarSilla(){
        
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        ObservableList<Mesa> row , allRows;
        allRows = tbmesasilla.getItems();
        row = tbmesasilla.getSelectionModel().getSelectedItems();
        try{
            row.get(0).agregarSilla();
            System.out.println(row.get(0).getNumeroDeSillas());
            tbmesasilla . getColumns (). get ( 0 ). setVisible ( false );
            tbmesasilla. getColumns (). get ( 0 ). setVisible ( true );
            Statement stm = conectar.createStatement();
            //ResultSet rst = stm.executeQuery("update sares.mesa set sillas = "+Integer.parseInt(row.get(0).getNumeroDeSillas())+" where id_mesa = "+row.get(0).getNumeroDeMesas());
            PreparedStatement pst = conectar.prepareStatement("update sares.mesa set sillas = "+Integer.parseInt(row.get(0).getNumeroDeSillas())+" where id_mesa = "+row.get(0).getNumeroDeMesa());
            //rst.next();
            pst.executeUpdate();

            System.out.println(row.get(0).getNumeroDeSillas());
        }catch(Exception e){
            System.out.println("entroaqui");
        }
    }
    
     @FXML
    public void quitarSilla(){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        ObservableList<Mesa> row , allRows;
        allRows = tbmesasilla.getItems();
        row = tbmesasilla.getSelectionModel().getSelectedItems();
        try{
            row.get(0).quitarSilla();
            tbmesasilla . getColumns (). get ( 0 ). setVisible ( false );
            tbmesasilla. getColumns (). get ( 0 ). setVisible ( true );
            Statement stm = conectar.createStatement();
            PreparedStatement pst = conectar.prepareStatement("update sares.mesa set sillas = "+Integer.parseInt(row.get(0).getNumeroDeSillas())+" where id_mesa = "+row.get(0).getNumeroDeMesa());
            pst.executeUpdate();
            


            System.out.println(row.get(0).getNumeroDeSillas());
        }catch (Exception e){}
    }
    
    @FXML
    public void agregarUsuario(ActionEvent event) throws IOException{
    
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Nuevo_Editar.fxml"));
       Stage stage = new Stage();
       Scene scene = new Scene(parent);
       stage.setScene(scene);
       stage.showAndWait();
       //NuevoEditarController.temp = new Trabajador();
       
    }
    
    @FXML
    public void editarUsuario(ActionEvent event) throws IOException{
    
       ((Node) event.getSource()).getScene().getWindow().hide();
       Parent parent = FXMLLoader.load(getClass().getResource("Nuevo_Editar.fxml"));
       Stage stage = new Stage();
       Scene scene = new Scene(parent);
       
        ObservableList<Trabajador> row , allRows;
        allRows = tbusuario.getItems();
        row = tbusuario.getSelectionModel().getSelectedItems();
        //NuevoEditarController.temp = new Trabajador();
        
       
       
       stage.setScene(scene);
       stage.showAndWait();
       
    }
    
    
    public void setUpChoiceBox(){
    
         chambiente.setItems(ambienteslist);
        chambiente.setValue("NORMAL");
        chambiente.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Conexion conexion = Conexion.getInstance();
                Connection conectar = conexion.getConexion();
                ObservableList<Mesa> row , allRows;
                allRows = tbmesasilla.getItems();
                row = tbmesasilla.getSelectionModel().getSelectedItems();
                try{
                    row.get(0).setAmbiente(new SimpleStringProperty(ambienteslist.get(newValue.intValue())));
                    tbmesasilla . getColumns (). get ( 0 ). setVisible ( false );
                    tbmesasilla. getColumns (). get ( 0 ). setVisible ( true );
                    Statement stm = conectar.createStatement();
                    String ambitemp = row.get(0).getAmbiente();
                    PreparedStatement pst = conectar.prepareStatement("update sares.mesa set ambiente = "+ambitemp+" where id_mesa = "+row.get(0).getNumeroDeMesa());
                    System.out.println(ambitemp);
                    pst.executeUpdate();
            
                }catch(Exception e){}
            }
        });
    
    
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //To change body of generated methods, choose Tools | Templates.
        
        Login login = Login.getInstance();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        this.setUpChoiceBox();
        lblnombre.setText(login.getTrabajador().getNombre() +" "+ login.getTrabajador().getApellido());
        nombreMesa.setCellValueFactory(new PropertyValueFactory<Mesa,SimpleStringProperty>("numeroDeMesas"));
        numSillas.setCellValueFactory(new PropertyValueFactory<Mesa,SimpleStringProperty>("numeroDeSillas"));
        ambiente.setCellValueFactory(new PropertyValueFactory<Mesa,SimpleStringProperty>("ambiente") );
        
        
    }
    
    
    
    public ObservableList<Mesa> getMesass(){
    
            ObservableList<Mesa> mesas = FXCollections.observableArrayList();
            Conexion conexion = Conexion.getInstance();
            Connection conectar = conexion.getConexion();
        try {
            Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from mesa ");
                ResultSetMetaData rsMd = rst.getMetaData();
                rst.next();
                System.out.println(rst.getObject(3).getClass());
                do{
                    Mesa mesa = new Mesa(new SimpleStringProperty(String.valueOf(rst.getObject(1))),new SimpleStringProperty(String.valueOf(rst.getObject(2))),new SimpleStringProperty(String.valueOf(rst.getObject(3))));
                    mesas.add(mesa);
                }while(rst.next());
        } catch (SQLException ex) {
            System.out.println("entro aqui");
            Logger.getLogger(PrincipalAController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
            
            
            return mesas;
    }
    
}

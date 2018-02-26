/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Cocinero;

import Main.Conexion;
import Main.Login;
import Main.model.Cocinero;
import Main.model.Pedido;
import Main.model.detallePedido;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 *
 * @author Jorge Pinargote
 */
public class AtendiendoController implements Initializable {
    Cocinero cocinero;
    Stage stage;
    
    @FXML
    private TableView<detallePedido> tbatendiendo;
    
    @FXML
    private TableColumn<detallePedido, String> menuColumn;
    @FXML
    private TableColumn<detallePedido, String> categoriaColumn;
    @FXML
    private TableColumn<detallePedido, Number> cantColumn;
    @FXML
    private TableColumn<detallePedido, String> obsColumn;
    
    
    
    @FXML
    private Button btnlisto;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Login login = Login.getInstance();
        if(login.getTrabajador() instanceof Cocinero)cocinero = (Cocinero)login.getTrabajador();
        Pedido pedido = cocinero.getAtendiendo();
        
        if(pedido!=null){
            pedido.getMyDetalles();
            tbatendiendo.setItems(pedido.getItemsPedidos());
        }
        menuColumn.setCellValueFactory(cellData -> cellData.getValue().getItem().getNombreProperty());
        categoriaColumn.setCellValueFactory(cellData -> cellData.getValue().getItem().getCategoria().getNombreProperty());
        cantColumn.setCellValueFactory(cellData -> cellData.getValue().getCantidadProperty());
        obsColumn.setCellValueFactory(cellData -> cellData.getValue().getObservacionesProperty());   
    }

    public void setCocinero(Cocinero cocinero) {
        this.cocinero = cocinero;
    }
    
    public void setMystage(Stage stage){
        this.stage = stage;
    }

    public void setListo(){
        if(cocinero.getAtendiendo()!=null){
            Conexion conexion = Conexion.getInstance();
            Connection conectar = conexion.getConexion();
            try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("Update pedido set listo = ? where id_pedido = ?");
                        pst.setBoolean(1,true);
                        pst.setInt(2,cocinero.getAtendiendo().getIdpedido());
                    
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("pedido actualizado");
                        }else{
                            System.out.println("Error al actualizar");
                        }
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
            
            cocinero.setAtendiendo(null);
            this.stage.close();
        
        }
    
    }
    
}

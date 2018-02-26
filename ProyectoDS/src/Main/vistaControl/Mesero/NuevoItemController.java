/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Mesero;

import Main.model.Bebida;
import Main.model.Categoria;
import Main.model.Combo;
import Main.model.MenuElemento;
import Main.model.Mesa;
import Main.model.Persona;
import Main.model.Plato;
import Main.model.detallePedido;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 *
 * @author Jorge Pinargote
 */
public class NuevoItemController implements Initializable {
    
    private ObservableList<detallePedido> detalles;
    
    private Stage mystage;
    
    @FXML
    private ChoiceBox<Categoria> chcategoria;
    @FXML
    private TextField txtcantidad;
    @FXML
    private TableView<MenuElemento> tbmenu;
    @FXML
    private TextArea txaobservaciones;
    
    @FXML
    private TableColumn<MenuElemento, String> menuColumn;
    @FXML
    private TableColumn<MenuElemento,Number> dispColumn;
    
    ObservableList<MenuElemento> itemsMostrados = FXCollections.observableArrayList();

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Categoria.cargarCategorias();
        Bebida.cargarBebidas();
        Plato.cargarPlatos();
        Combo.cargarCombos();
        
        chcategoria.setConverter(new ConvertCategoria());
        chcategoria.setItems(Categoria.getCategorias());
        
        menuColumn.setCellValueFactory(cellData -> cellData.getValue().getNombreProperty());
        dispColumn.setCellValueFactory(cellData -> cellData.getValue().getUnidadesRestantesProperty());
        
        tbmenu.setItems(itemsMostrados);
        
        chcategoria.getSelectionModel().selectedItemProperty()
                .addListener((v,oldValue,newValue)->MenuElemento.getItemsByCategoria((Categoria)newValue, itemsMostrados));
    }

    public void setDetalles(ObservableList<detallePedido> detalles) {
        this.detalles = detalles;
    }
    
    public void setMystage(Stage mystage) {
        this.mystage = mystage;
    }
     
    private class ConvertCategoria extends StringConverter<Categoria> {
       Categoria categoria; 
            
       @Override
        public String toString(Categoria categoria) {
            this.categoria = categoria;
            return categoria.getNombre()+": "+ categoria.getDescripcion();
        }
        
       @Override
        public Categoria fromString(String string) {
           return this.categoria;
        }
        
    }
    
    
    public void handleAgregar(){
        MenuElemento item = tbmenu.getSelectionModel().getSelectedItem();
        String text = txtcantidad.getText().trim();
        String obs = txaobservaciones.getText();
        
        if(item!=null && !text.equals("") && obs!=null){
             int cantidad = Integer.parseInt(text);
             detallePedido detallep = new detallePedido(item,obs,cantidad);
             detalles.add(detallep);
             
             mystage.close();
        
        }        
    }
    
    
}

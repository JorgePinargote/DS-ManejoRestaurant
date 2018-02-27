/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.vistaControl.Cajero;

import Main.Help.CuentaConverter;
import Main.Help.MesaConverter;
import Main.Help.MetodoConverter;
import Main.Help.PersonaConverter;
import Main.model.Cuenta;
import Main.model.Mesa;
import Main.model.PagoEfectivo;
import Main.model.PagoTarjetas;
import Main.model.Pedido;
import Main.model.Persona;
import Main.model.StrategyPago;
import Main.model.detalleCuenta;
import Main.model.detallePedido;
import Main.model.pagoElectronico;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Jorge Pinargote
 */
public class CobrarController implements Initializable {
    
    private ObservableList<Cuenta> cuentas = FXCollections.observableArrayList();
    private ObservableList<detalleCuenta> detallescuentas = FXCollections.observableArrayList();
    private ObservableList<StrategyPago> metodo = FXCollections.observableArrayList();
    
    //TABCOBRAR
    @FXML
    private ChoiceBox chmesa;
    
    @FXML
    private ChoiceBox<Cuenta> chcuenta;
    
    @FXML
    private ChoiceBox<Persona> chcliente;
    
    
    @FXML
    private TableView<detalleCuenta> tbcuentas;
    @FXML
    private TableColumn<detalleCuenta, String> nombreColumn1;
    @FXML
    private TableColumn<detalleCuenta, String> apellidoColumn;
    @FXML
    private TableColumn<detalleCuenta, String> metodoColumn;
    @FXML
    private TableColumn<detalleCuenta, Number> subtotalColumn;
    @FXML
    private TableColumn<detalleCuenta, Number> descColumn;
    @FXML
    private TableColumn<detalleCuenta, Number> totalColumn;
    
    
    
    @FXML
    private TextField txtdescuento;
    
    
    @FXML 
    private ChoiceBox<StrategyPago> chmetodo;
    
    
    @FXML
    private Label lbliva;
    @FXML
    private TextField txtcantidad;
    @FXML
    private Label lbltotalcuenta;
    @FXML
    private Label lbltotalpagar;
    @FXML
    private Label lblsaldo;
    double saldo = 0;
    
    @FXML 
    private Button btncobrar;
    
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chmesa.setConverter(new MesaConverter());
        Mesa.cargarMesaAgain();
        chmesa.setItems(Mesa.getMesaData());
        
        chcuenta.setConverter(new CuentaConverter());
        chcuenta.setItems(cuentas);
        
        chmesa.getSelectionModel().selectedItemProperty()
                .addListener((v,oldValue,newValue)-> Mesa.changeCuentas((Mesa)newValue,cuentas));
        
        chcuenta.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<Cuenta>() {
            @Override
            public void changed(ObservableValue<? extends Cuenta> observable, Cuenta oldValue, Cuenta newValue) {
                if(newValue!=null){
                    Cuenta.changeDetalles(newValue,detallescuentas); 
                    lbltotalcuenta.setText("" + newValue.getValortotal());
                    saldo = newValue.getSaldo();
                    lblsaldo.setText("" + saldo);
                }else{
                     lbltotalcuenta.setText("");
                     lblsaldo.setText("");
                     detallescuentas.clear();
                
                }
            }
        });
        
        
        tbcuentas.setItems(detallescuentas);
        
        nombreColumn1.setCellValueFactory(cellData -> cellData.getValue().getPersona().getNombreProperty());
        apellidoColumn.setCellValueFactory(cellData -> cellData.getValue().getPersona().getApellidoProperty());
        metodoColumn.setCellValueFactory(cellData -> cellData.getValue().getPago().getDescripcion());
        subtotalColumn.setCellValueFactory(cellData -> cellData.getValue().getSubTotalProperty());
        descColumn.setCellValueFactory(cellData -> cellData.getValue().getDescProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().getTotalProperty());
        
        Persona.CargarPersonAgain();
        chcliente.setConverter(new PersonaConverter());
        chcliente.setItems(Persona.getPersonData());
        
        metodo.add(new PagoTarjetas());
        metodo.add(new pagoElectronico());
        metodo.add(new PagoEfectivo());
        
        chmetodo.setConverter(new MetodoConverter());
        chmetodo.setItems(metodo);
        
        txtdescuento.textProperty().addListener((observable, oldValue, newValue) -> {
             showTotal(newValue);
        });
        
        
    }
    
    
    public void reinitMetodos(){
        metodo.clear();
        metodo.add(new PagoTarjetas());
        metodo.add(new pagoElectronico());
        metodo.add(new PagoEfectivo());
    }
    
    public void showTotal(String descuento){
        if(isValidInput()){
           try{
                Double subtotal = Double.parseDouble(txtcantidad.getText());
                Double total = subtotal - (subtotal*Double.parseDouble(descuento))/100;
                lbltotalpagar.setText("" + total);
           
           }catch(Exception ex){
               System.out.println("ingrese un numero");
           } 
          
        } 
         
    
    }
    
    public void handleCobrar(){
         if(isValidInput()){
             StrategyPago pago = chmetodo.getSelectionModel().getSelectedItem();
             Persona persona = chcliente.getSelectionModel().getSelectedItem();
             Cuenta cuenta = chcuenta.getSelectionModel().getSelectedItem();
             if(pago!=null && persona!=null && cuenta!=null ){
                 Double subtotal = Double.parseDouble(txtcantidad.getText());
                 double descuento = Double.parseDouble(txtdescuento.getText());
                 Double total = subtotal - (subtotal*descuento)/100;
                 
                 lblsaldo.setText("" + (saldo - subtotal));
                 
                 detalleCuenta detalle = new detalleCuenta(persona,subtotal,descuento,total); 
                 cuenta.sendDetalle(detalle);
                 detalle.Cobrar(pago);
                 txtcantidad.setText("");
                 txtdescuento.setText("");
                 lbltotalpagar.setText("");
                 reinitMetodos();
                 detallescuentas.add(detalle);
                 
             }    
         
         }
    
    }
    
    public boolean isValidInput(){
       if (txtcantidad.getText() == null || txtcantidad.getText().length() == 0) {
            return false;
       }
       
       if (txtdescuento.getText() == null || txtdescuento.getText().length() == 0) {
            return false;
       }
       
       return true;
    }
    
    
    
}

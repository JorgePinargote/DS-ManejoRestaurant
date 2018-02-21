/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Help;

import Main.model.Cuenta;
import Main.model.Pedido;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Jorge Pinargote
 */
public class infoEntrega {
    IntegerProperty mesa;
    IntegerProperty cuenta;
    IntegerProperty pedido;
    
    public infoEntrega(Pedido pedido){
        this.pedido = new SimpleIntegerProperty(pedido.getIdpedido());
        this.cuenta = new SimpleIntegerProperty(pedido.getMyCuenta());
        this.mesa = new SimpleIntegerProperty(Cuenta.getMymesa(this.cuenta.get()));
    }

    public IntegerProperty getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa.set(mesa);
    }

    public IntegerProperty getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta.set(cuenta);
    }

    public IntegerProperty getPedido() {
        return pedido;
    }

    public void setPedido(int pedido) {
        this.pedido.set(pedido);
    }
    
    
}

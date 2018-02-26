/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jorge Pinargote
 */
public abstract class StrategyPago{
    DoubleProperty cantidadPagada;
    
    public abstract void Cobrar(Persona persona, int idcuenta);
    public abstract StringProperty getDescripcion();

    public void setCantidad(double cantidad) {
        this.cantidadPagada = new SimpleDoubleProperty(cantidad);
    }
    
    
}

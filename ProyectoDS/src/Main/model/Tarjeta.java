/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import java.util.Date;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jorge Pinargote
 */
public class Tarjeta {
    
    IntegerProperty idtarjeta;
    StringProperty nombre;
    StringProperty numero;
    StringProperty cvv;
    Date expira;
    
     public Tarjeta(int idtarjeta, String nombre, String numero, String cvv, Date expira) {
        this.idtarjeta = new SimpleIntegerProperty(idtarjeta);
        this.nombre = new SimpleStringProperty(nombre);
        this.numero = new SimpleStringProperty(numero);
        this.cvv = new SimpleStringProperty(cvv);
        this.expira = expira;
    }
    
    public Tarjeta(String nombre, String numero, String cvv, Date expira) {
        this.idtarjeta = new SimpleIntegerProperty(0);
        this.nombre = new SimpleStringProperty(nombre);
        this.numero = new SimpleStringProperty(numero);
        this.cvv = new SimpleStringProperty(cvv);
        this.expira = expira;
    }
    
    public int getIdtarjeta() {
        return idtarjeta.get();
    }

    public String getNombre() {
        return nombre.get();
    }

    public String getNumero() {
        return numero.get();
    }

    public String getCvv() {
        return cvv.get();
    }

    public Date getExpira() {
        return expira;
    }
    
    
    
}

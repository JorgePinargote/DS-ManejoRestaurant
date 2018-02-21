/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Interfaces;

import Main.model.Trabajador;

/**
 *
 * @author Jorge Pinargote
 */
public abstract class InterGraficas {
    Trabajador trabajador;
    
    public InterGraficas(Trabajador trabajador){
        this.trabajador=trabajador;
    }
    
    public abstract void crear();
    
}

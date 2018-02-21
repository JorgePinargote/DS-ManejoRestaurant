/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Interfaces;

import Main.model.Administrador;
import Main.model.Cajero;
import Main.model.Cocinero;
import Main.model.Mesero;
import Main.model.Trabajador;

/**
 *
 * @author Jorge Pinargote
 */
public class ConcretGrafica implements FactoryGrafica {

    @Override
    public InterGraficas ShowInterface(Trabajador trabajador) {
        if(trabajador instanceof Mesero){
            return new InMesero(trabajador);
        }
        if(trabajador instanceof Cocinero){
           return new InCocinero(trabajador);
        }
        if(trabajador instanceof Cajero){
           return new InCajero(trabajador);
        }
        if(trabajador instanceof Administrador){
           return new InAdministrador(trabajador);
        }
        return null;
    }
    
    
    
}

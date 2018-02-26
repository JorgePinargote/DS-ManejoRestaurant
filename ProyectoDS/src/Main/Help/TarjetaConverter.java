/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Help;

import Main.model.Cuenta;
import Main.model.Tarjeta;
import javafx.util.StringConverter;

/**
 *
 * @author Jorge Pinargote
 */
public class TarjetaConverter extends StringConverter<Tarjeta> {
    Tarjeta tarjeta; 
       
       @Override
        public String toString(Tarjeta tarjeta) {
            this.tarjeta = tarjeta;
            return "" + this.tarjeta.getNombre() + " ";
        }
        
       @Override
        public Tarjeta fromString(String string) {
           return this.tarjeta;
        }
}

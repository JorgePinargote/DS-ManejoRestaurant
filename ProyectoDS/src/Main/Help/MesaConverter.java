/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Help;

import Main.model.Mesa;
import javafx.util.StringConverter;

/**
 *
 * @author Jorge Pinargote
 */
public class MesaConverter extends StringConverter<Mesa> {
       Mesa mesa;   
       @Override
        public String toString(Mesa mesa) {
            this.mesa = mesa;
            return "Mesa: "+mesa.getNumeroDeMesa() +" Ambiente: "+ mesa.getAmbiente();
        }
        
       @Override
        public Mesa fromString(String string) {
           return this.mesa;
        }
}

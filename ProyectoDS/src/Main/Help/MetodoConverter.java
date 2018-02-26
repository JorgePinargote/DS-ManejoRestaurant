/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Help;

import Main.model.Cuenta;
import Main.model.StrategyPago;
import javafx.util.StringConverter;

/**
 *
 * @author Jorge Pinargote
 */
public class MetodoConverter extends StringConverter<StrategyPago>  {
    StrategyPago pago; 
       
       @Override
        public String toString(StrategyPago pago) {
            this.pago = pago;
            return "" + this.pago.getDescripcion().get();
        }
        
       @Override
        public StrategyPago fromString(String string) {
           return this.pago;
        }
}

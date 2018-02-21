/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Help;

import Main.model.Cuenta;
import javafx.util.StringConverter;

/**
 *
 * @author Jorge Pinargote
 */
public class CuentaConverter extends StringConverter<Cuenta>  {
    Cuenta cuenta; 
       
       @Override
        public String toString(Cuenta cuenta) {
            this.cuenta = cuenta;
            if(this.cuenta.getIdcuenta()==0)return "Nueva Cuenta";
            return "" + this.cuenta.getIdcuenta();
        }
        
       @Override
        public Cuenta fromString(String string) {
           return this.cuenta;
        }
}

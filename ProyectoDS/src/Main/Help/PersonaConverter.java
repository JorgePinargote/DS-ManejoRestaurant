/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Help;

import Main.model.Cuenta;
import Main.model.Persona;
import javafx.util.StringConverter;

/**
 *
 * @author Jorge Pinargote
 */
public class PersonaConverter extends StringConverter<Persona>  {
    Persona persona; 
       
       @Override
        public String toString(Persona persona) {
            this.persona = persona;
            return "" + this.persona.getNombre() + " " + this.persona.getApellido() + " " + this.persona.getCedula();
        }
        
       @Override
        public Persona fromString(String string) {
           return this.persona;
        }
    
}

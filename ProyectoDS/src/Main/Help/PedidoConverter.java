/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.Help;

import Main.model.Mesa;
import Main.model.Pedido;
import javafx.util.StringConverter;

/**
 *
 * @author Jorge Pinargote
 */

public class PedidoConverter extends StringConverter<Pedido> {
       Pedido pedido;   
       @Override
        public String toString(Pedido pedido) {
            this.pedido = pedido;
            return "" + this.pedido.getIdpedido();
        }
        
       @Override
        public Pedido fromString(String string) {
           return this.pedido;
        }
}

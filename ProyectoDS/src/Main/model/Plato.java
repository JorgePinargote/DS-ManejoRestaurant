/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jorge Pinargote
 */
public class Plato extends MenuElemento{

    StringProperty ingredientes;
    
    private static ObservableList<MenuElemento> platos = FXCollections.observableArrayList();
    
    public Plato(int id,String nombre, int tiempoDePreparacion, String descripcion, int unidadesRestantes, double precio) {
        super(id,nombre, tiempoDePreparacion, descripcion, unidadesRestantes, precio);
    }
    

    @Override
    public void setTiempoPreparacion(int time) {
        this.tiempoDePreparacion.set(time);
    }

    @Override
    public int getTiempoDePreparacion() {
        return this.tiempoDePreparacion.get();
    }

    @Override
    public void setUnidadesRestantes(int cantidad) {
        this.unidadesRestantes.set(cantidad);
    }

    @Override
    public int getUnidadesRestantes() {
        return this.unidadesRestantes.get();
    }

    @Override
    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    @Override
    public String getDescripcion() {
        return this.descripcion.get();
    }

    @Override
    public void add(MenuElemento item) {}

    @Override
    public void remove(MenuElemento item) {}

    @Override
    public MenuElemento getChild(int index) {return null;}

    public static ObservableList<MenuElemento> getPlatos() {
        return platos;
    }
    
    
    
    
    
    public static void cargarPlatos(){
        platos.clear();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from menuitem where plato=1 and disponibles > 0");
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    
                    int id = (int)rst.getObject(1);
                    String nombre = (String)rst.getObject(2);
                    double precio = (double)rst.getObject(6);
                    
                    int idcategoria = (int)rst.getObject(7);
                    int disponibles = (int)rst.getObject(8);
                    String descripcion = (String)rst.getObject(9);
                    int tiempoEstimado = (int)rst.getObject(10);
                    
                    Plato plato = new Plato(id,nombre,tiempoEstimado,descripcion,disponibles,precio); 
                    Categoria categoria = Categoria.getCategoriabyId(idcategoria);
                    plato.setCategoria(categoria);
                    platos.add(plato);
     
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
         
         
     }
    
    
    
    

    
}

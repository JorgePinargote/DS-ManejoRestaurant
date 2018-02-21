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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jorge Pinargote
 */
public class Combo extends MenuElemento{
    
    boolean completo;
    
    private static ObservableList<MenuElemento> comboItems = FXCollections.observableArrayList();
    
    private ObservableList<MenuElemento> items;

    public Combo(int id,String nombre, double precio,String descripcion) {
        super(id,nombre,precio,descripcion);
        items = FXCollections.observableArrayList();
        completo = true;
    }
    

    @Override
    public void setTiempoPreparacion(int time) {
        int totaltime = 0;
        
        for (MenuElemento item : items) {
            totaltime = totaltime + item.getTiempoDePreparacion();
        }
        this.tiempoDePreparacion.set(totaltime + time); 
    }

    @Override
    public int getTiempoDePreparacion() {
        return this.tiempoDePreparacion.get();
    }

    
    @Override
    public void setUnidadesRestantes(int cantidad) {
        int maxcant = Integer.MAX_VALUE;
        
        for (MenuElemento item : items) {
            if(item.unidadesRestantes.get() < maxcant) maxcant=item.unidadesRestantes.get();
        }
        
        if(cantidad<maxcant){
            this.unidadesRestantes.set(cantidad);
        }else{
            this.unidadesRestantes.set(maxcant);        
        }
    }
    
    public void setUnidadesRestantes() {
        int maxcant = Integer.MAX_VALUE;
        
        for (MenuElemento item : items) {
            if(item.unidadesRestantes.get() < maxcant) maxcant=item.unidadesRestantes.get();
        }
        
        this.unidadesRestantes.set(maxcant);        
        
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
        String finalDescripcion = descripcion.get();
        for (MenuElemento item : items) {
            finalDescripcion = finalDescripcion + item.descripcion + " \n";
        }
       return finalDescripcion;
    }

    @Override
    public void add(MenuElemento item) {
        items.add(item);
    }

    @Override
    public void remove(MenuElemento item) {
        items.remove(item);
    }

    @Override
    public MenuElemento getChild(int index) {
        return items.get(index);
    }

    
    public static void cargarCombos(){
        comboItems.clear();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from menuitem where combo = 1 and disponibles > 0");
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    
                    int id = (int)rst.getObject(1);
                    String nombre = (String)rst.getObject(2);
                    double precio = (double)rst.getObject(6);
                    int idcategoria = (int)rst.getObject(7);
                    String descripcion = (String)rst.getObject(9);
                    int disponible = (int)rst.getObject(8);
                    int tiempoEstimado = (int)rst.getObject(10);

                    
                    Combo combo = new Combo(id,nombre,precio,descripcion); 
                    Categoria categoria = Categoria.getCategoriabyId(idcategoria);
                    combo.setCategoria(categoria);
                    combo.getMysubItems();
                    
                    combo.setTiempoPreparacion(tiempoEstimado);
                    combo.setUnidadesRestantes();
                    
                    if(combo.completo)comboItems.add(combo);
                }
                
                
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
         
     }

    public static ObservableList<MenuElemento> getComboItems() {
        return comboItems;
    }
    
    public void getMysubItems(){    
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from defcombos where idcombo= "+ this.idmenu.get());
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    
                    int iditem = (int)rst.getObject(3);
                    
                    MenuElemento bebida = MenuElemento.getItemById(iditem, Bebida.getBebidas());
                    MenuElemento plato = MenuElemento.getItemById(iditem, Plato.getPlatos());
                    
                    if(bebida != null) {
                        this.items.add(bebida);
                    }else{
                        completo = false;
                    }
                    
                    
                    if(plato != null){
                        this.items.add(plato);
                    }else{
                        completo = false;
                    }                   
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    }
 
    

    
    
    
}

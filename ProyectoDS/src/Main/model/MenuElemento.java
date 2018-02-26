/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main.model;

import Main.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Israel
 */
public abstract class MenuElemento {
    
    
    protected IntegerProperty idmenu;
    protected StringProperty nombre;
    protected Categoria categoria;
    protected IntegerProperty tiempoDePreparacion;
    protected StringProperty descripcion;
    protected IntegerProperty unidadesRestantes;
    protected DoubleProperty precio;
    
    
     public MenuElemento(int id,String nombre, int tiempoDePreparacion, String descripcion, int unidadesRestantes, double precio) {
        this.idmenu = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.tiempoDePreparacion = new SimpleIntegerProperty(tiempoDePreparacion);
        this.descripcion = new SimpleStringProperty(descripcion);
        this.unidadesRestantes = new SimpleIntegerProperty(unidadesRestantes);
        this.precio = new SimpleDoubleProperty(precio);
       
    }
    
    public MenuElemento(int id,String nombre,double precio,String descripcion) {
        this.idmenu = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.precio = new SimpleDoubleProperty(precio);
        this.tiempoDePreparacion = new SimpleIntegerProperty(0);
        this.unidadesRestantes = new SimpleIntegerProperty(0);
    }
    

    public String getNombre() {
        return nombre.get();
    }
    
    public StringProperty getNombreProperty(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public Categoria getCategoria() {
        return categoria;
    }
    
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public double getPrecio() {
        return precio.get();
    }
    
    public DoubleProperty getPrecioProperty(){
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio.set(precio);
    }
    
    public IntegerProperty getTiempoPreparacionProperty(){
        return this.tiempoDePreparacion;
    }
    
     public IntegerProperty getUnidadesRestantesProperty(){
        return this.unidadesRestantes;
    }
     
    public int getIdMenu(){
        return this.idmenu.get();
    }
     
    
    public static MenuElemento getItemById(int id,ObservableList<MenuElemento> lista){
        
        for (MenuElemento item : lista) {
            if(item.idmenu.get() == id)return item;    
        }
        
        return null;
    }
    
    public static MenuElemento getItemById(int id){
        
        for (MenuElemento item : Bebida.getBebidas()) {
            if(item.idmenu.get() == id)return item;    
        }
        
        for (MenuElemento item : Plato.getPlatos()) {
            if(item.idmenu.get() == id)return item;    
        }

        for (MenuElemento item : Combo.getComboItems()) {
            if(item.idmenu.get() == id)return item;    
        }
        
        return null;
    }
    
    
    
    
    public static void getItemsByCategoria(Categoria categoria,ObservableList<MenuElemento> itembycategory){
         
        itembycategory.clear();
        
        for (MenuElemento item : Bebida.getBebidas()) {
            if(item.categoria.equals(categoria))itembycategory.add(item);    
        }
        
        for (MenuElemento item : Plato.getPlatos()) {
            if(item.categoria.equals(categoria))itembycategory.add(item);    
        }
        
        for (MenuElemento item : Combo.getComboItems()) {
            if(item.categoria.equals(categoria))itembycategory.add(item);    
        }
        
    }
    
    public static void setAllItems(ObservableList<MenuElemento> items){
        for (MenuElemento item : Bebida.getBebidas()) {
             items.add(item);
        }
        
        for (MenuElemento item : Plato.getPlatos()) {
             items.add(item);
        }
        
        for (MenuElemento item : Combo.getComboItems()) {
             items.add(item);   
        }
    
    }    
    
    public void Quitar(){
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
            try{
                    PreparedStatement pst = 
                        conectar.prepareStatement("Update menuitem set disponibles = ? where id_menuItem = ?");
                        pst.setInt(1, 0);
                        pst.setInt(2, this.getIdMenu());
                    
                        int res = pst.executeUpdate();
                        
                        if(res>0){
                            System.out.println("Menu quitado");
                        }else{
                            System.out.println("Error al quitar");
                        }
                        
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
    
    
    }
    
    public abstract void setTiempoPreparacion(int time);
    
    public abstract int getTiempoDePreparacion();
     
    
    public abstract void setUnidadesRestantes(int cantidad);
    
    public abstract int getUnidadesRestantes();
    
    
    public abstract void setDescripcion(String descripcion);
     
    public abstract  String getDescripcion();
    
    
    public abstract void add(MenuElemento item);

    public abstract void remove(MenuElemento item);
    
    public abstract MenuElemento getChild(int index); 
    
    
 
    
    
}

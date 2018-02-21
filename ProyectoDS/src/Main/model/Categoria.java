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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jorge Pinargote
 */
public class Categoria {
    int idcategoria;
    StringProperty nombre;
    StringProperty descripcion;
    
    private static ObservableList<Categoria> categorias =  FXCollections.observableArrayList();

    public Categoria(int idcategoria, String nombre, String descripcion) {
        this.idcategoria = idcategoria;
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    public String getNombre() {
        return nombre.get();
    }
    
    public StringProperty getNombreProperty() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    
    
    public String getDescripcion() {
        return descripcion.get();
    }
    
    public StringProperty getDescripcionProperty() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    public static ObservableList<Categoria> getCategorias() {
        return categorias;
    }
  
   
    public static void cargarCategorias(){
        categorias.clear();
        Conexion conexion = Conexion.getInstance();
        Connection conectar = conexion.getConexion();
        
            try{
                Statement stm = conectar.createStatement();
                ResultSet rst = stm.executeQuery("Select * from categoria");
                ResultSetMetaData rsMd = rst.getMetaData();
                
                while(rst.next()){
                    int id = (int)rst.getObject(1);
                    String nombre = (String)rst.getObject(2);
                    String descripcion = (String)rst.getObject(3);
                    
                    categorias.add(new Categoria(id,nombre,descripcion));
                }
                
            }catch(Exception ex){
                System.out.println("consulta no se realizo");
                ex.printStackTrace();
            }
     }
    
    
    public static Categoria getCategoriabyId(int id){
        for (Categoria categoria : categorias) {
            if(categoria.idcategoria == id)return categoria;
        }
        
        return null;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Categoria other = (Categoria) obj;
        if (this.idcategoria != other.idcategoria) {
            return false;
        }
        return true;
    }
    
    
}

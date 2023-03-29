/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.swing.JTextField;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 *
 * @author j_c-s
 */
@Table("Productos")
public class producto {
    @Id
    @Column("cod")
    private Long cod;
    @Column("nombre")
    private String nombre;
    @Column("precio")
    private double precio;
    @Column("inventario")
    private int inventario;

    public producto(Long cod, String nombre, double precio, int inventario) {
        this.cod = cod;
        this.nombre = nombre;
        this.precio = precio;
        this.inventario = inventario;
    }

    public static producto crearProducto(String nombre, double precio, int inventario) {
        return new producto(null, nombre, precio, inventario);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public Long getCod() {
        return cod;
    }
    
    public void setCod(long cod){
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getInventario() {
        return inventario;
    }
    
    @Override
    public String toString(){
        return "Producto{"+"cod="+cod+", nombre="+nombre+", precio="+precio+", inventario="+inventario+"}";
    }

}

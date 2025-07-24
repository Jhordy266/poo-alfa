/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.Objects;

/**
 *
 * @author leona
 */
public class Producto {

    private int Id_Producto;
    private int Id_Categoria;
    private String categoriaNombre;
    private String marcaNombre;
    private int Id_Marca;
    private String Nombre;
    private String Descripcion;
    private double Precio_U;
    private int Stock;

    public Producto() {
    }

    // Constructor for inserting (without id_Producto)
    public Producto(int Id_Categoria, String categoriaNombre, int Id_Marca, String marcaNombre, String Nombre, String Descripcion, double Precio_U, int Stock) {
        this.Id_Categoria = Id_Categoria;
        this.categoriaNombre = categoriaNombre;
        this.Id_Marca = Id_Marca;
        this.marcaNombre = marcaNombre;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Precio_U = Precio_U;
        this.Stock = Stock;
    }

    public Producto(int Id_Producto, int Id_Categoria, String categoriaNombre, int Id_Marca, String marcaNombre, String Nombre, String Descripcion, double Precio_U, int Stock) {
        this.Id_Producto = Id_Producto;
        this.Id_Categoria = Id_Categoria;
        this.categoriaNombre = categoriaNombre;
        this.Id_Marca = Id_Marca;
        this.marcaNombre = marcaNombre;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Precio_U = Precio_U;
        this.Stock = Stock;
    }

    public int getId_Producto() {
        return Id_Producto;
    }

    public void setId_Producto(int Id_Producto) {
        this.Id_Producto = Id_Producto;
    }

    public int getId_Categoria() {
        return Id_Categoria;
    }

    public void setId_Categoria(int Id_Categoria) {
        this.Id_Categoria = Id_Categoria;
    }

    public String getcategoriaNombre() {
        return categoriaNombre;
    }

    public void setcategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    public int getId_Marca() {
        return Id_Marca;
    }

    public void setId_Marca(int Id_Marca) {
        this.Id_Marca = Id_Marca;
    }

    public String getmarcaNombre() {
        return marcaNombre;
    }

    public void setmarcaNombre(String marcaNombre) {
        this.marcaNombre = marcaNombre;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public double getPrecio_U() {
        return Precio_U;
    }

    public void setPrecio_U(double Precio_U) {
        this.Precio_U = Precio_U;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Producto producto = (Producto) o;
        return Id_Producto == producto.Id_Producto;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id_Producto);
    }
    
    public void actualizarStock(int cantidadVendida) {
        if (this.Stock >= cantidadVendida) {
            this.Stock -= cantidadVendida;
        } else {
            System.out.println("No hay suficiente stock.");
        }
    }

    // Método para mostrar información del producto
    public void mostrarInfo() {
        System.out.println("Producto ID: " + this.Id_Producto);
        System.out.println("Nombre: " + this.Nombre);
        System.out.println("Stock disponible: " + this.Stock);
        System.out.println("Precio: " + this.Precio_U);
    }
}

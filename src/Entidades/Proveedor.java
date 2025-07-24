/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author leona
 */
public class Proveedor {
    
    private int Id_Proveedor;
    private String Nombre;
    private String Contacto;
    private String Telefono;

    public Proveedor() {
    }

    public Proveedor(int Id_Proveedor, String Nombre, String Contacto, String Telefono) {
        this.Id_Proveedor = Id_Proveedor;
        this.Nombre = Nombre;
        this.Contacto = Contacto;
        this.Telefono = Telefono;
    }

    public int getId_Proveedor() {
        return Id_Proveedor;
    }

    public void setId_Proveedor(int Id_Proveedor) {
        this.Id_Proveedor = Id_Proveedor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getContacto() {
        return Contacto;
    }

    public void setContacto(String Contacto) {
        this.Contacto = Contacto;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }
    
    
}

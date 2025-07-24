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
public class Tipo_Cliente {
    private int Id_TipoCliente;
    private String Nombre;

    public Tipo_Cliente() {
    }

    public Tipo_Cliente(int Id_TipoCliente, String Nombre) {
        this.Id_TipoCliente = Id_TipoCliente;
        this.Nombre = Nombre;
    }

    public int getId_TipoCliente() {
        return Id_TipoCliente;
    }

    public void setId_TipoCliente(int Id_TipoCliente) {
        this.Id_TipoCliente = Id_TipoCliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    @Override
    public String toString() {
        return Nombre; 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Tipo_Cliente tipoCliente = (Tipo_Cliente) obj;
        return Id_TipoCliente == tipoCliente.Id_TipoCliente; 
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id_TipoCliente);
    }
    
}

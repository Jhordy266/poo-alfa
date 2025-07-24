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
public class Marca {

    private int Id_Marca;
    private String Nombre;

    public Marca() {
    }

    public Marca(int Id_Marca, String Nombre) {
        this.Id_Marca = Id_Marca;
        this.Nombre = Nombre;
    }

    public int getId_Marca() {
        return Id_Marca;
    }

    public void setId_Marca(int Id_Marca) {
        this.Id_Marca = Id_Marca;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public String toString() {
        return Nombre; // This is what will be displayed in the JComboBox
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Marca marca = (Marca) obj;
        return Id_Marca == marca.Id_Marca; // Compare by ID
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id_Marca);
    }
}

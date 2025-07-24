package Entidades;

import java.util.Objects;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author leona
 */
public class Categoria {

    private int Id_Categoria;
    private String Nombre;
    private String Descripcion;
    private Boolean Estado;

    public Categoria(int Id_Categoria, String Nombre, String Descripcion, Boolean Estado) {
        this.Id_Categoria = Id_Categoria;
        this.Nombre = Nombre;
        this.Descripcion = Descripcion;
        this.Estado = Estado;
    }

    public Categoria(int Id_Categoria, String Nombre) {
        this.Id_Categoria = Id_Categoria;
        this.Nombre = Nombre;
    }

    public Categoria() {
    }

    public int getId_Categoria() {
        return Id_Categoria;
    }

    public void setId_Categoria(int Id_Categoria) {
        this.Id_Categoria = Id_Categoria;
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

    public Boolean isEstado() {
        return Estado;
    }

    public void setEstado(Boolean Estado) {
        this.Estado = Estado;
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
        Categoria categoria = (Categoria) obj;
        return Id_Categoria == categoria.Id_Categoria; 
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id_Categoria);
    }
}

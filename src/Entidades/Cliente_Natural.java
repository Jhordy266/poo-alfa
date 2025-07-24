/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author leona
 */
public class Cliente_Natural {
    
    private int Id_CNatural;
    private String Nombre;
    private String DNI;

    public Cliente_Natural() {
    }

    public Cliente_Natural(int Id_CNatural, String Nombre, String DNI) {
        this.Id_CNatural = Id_CNatural;
        this.Nombre = Nombre;
        this.DNI = DNI;
    }

    public int getId_CNatural() {
        return Id_CNatural;
    }

    public void setId_CNatural(int Id_CNatural) {
        this.Id_CNatural = Id_CNatural;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
    
    
}

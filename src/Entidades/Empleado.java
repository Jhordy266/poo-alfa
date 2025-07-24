/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author leona
 */
public class Empleado {
   
    private int Id_Empleado;
    private String Nombres;
    private String DNI;
    private String Telefono;
    private String Turno;
    private double Salario;

    public Empleado() {
    }

    public Empleado(int Id_Empleado, String Nombres, String DNI, String Telefono, String Turno, double Salario) {
        this.Id_Empleado = Id_Empleado;
        this.Nombres = Nombres;
        this.DNI = DNI;
        this.Telefono = Telefono;
        this.Turno = Turno;
        this.Salario = Salario;
    }

    public int getId_Empleado() {
        return Id_Empleado;
    }

    public void setId_Empleado(int Id_Empleado) {
        this.Id_Empleado = Id_Empleado;
    }

    public String getNombres() {
        return Nombres;
    }

    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getTurno() {
        return Turno;
    }

    public void setTurno(String Turno) {
        this.Turno = Turno;
    }

    public double getSalario() {
        return Salario;
    }

    public void setSalario(double Salario) {
        this.Salario = Salario;
    }
    
    
}

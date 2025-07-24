/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

/**
 *
 * @author leona
 */
public class DemoCnx {
    public static void main(String[] args) {
        Conexion cnx = new Conexion();
        cnx.conectar();
        if(cnx.cnx != null){
            System.out.println("Conexión exitosa");
        }else{
            System.out.println("Conexión fallida");
        }
    }
}

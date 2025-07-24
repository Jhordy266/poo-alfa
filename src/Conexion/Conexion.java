/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author leona
 */
public class Conexion {

    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String DB = "FERRETERIA";
    private final String USER = "root";
    private final String PASSWORD = "root";


    public Connection cnx;
    public static Conexion instancia;

    public Conexion() {
        this.cnx = null;
        // Intenta cargar el driver una sola vez cuando se crea la instancia
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: El driver JDBC (" + DRIVER + ") no se encontró. Verifica las librerías.");
            // No System.exit(0) aquí. Deja que el error se propague o se maneje en el punto de uso.
            System.err.println("Driver JDBC no encontrado: " + e.getMessage());
        }
    }

    public Connection conectar() {
        try {
            // Si la conexión es nula o está cerrada, intenta reconectar
            if (this.cnx == null || this.cnx.isClosed()) {
                this.cnx = DriverManager.getConnection(URL + DB, USER, PASSWORD);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + e.getMessage());
            this.cnx = null; // Asegura que cnx sea nulo si la conexión falló
            // No System.exit(0). Devuelve null y el ClienteDAO lo manejará.
            System.err.println("Error SQL al conectar: " + e.getMessage());
        }
        return this.cnx; // Retornará null si la conexión falló
    }

    public void desconectar() {
        try {
            if (this.cnx != null && !this.cnx.isClosed()) { // Verifica que no sea null y no esté ya cerrada
                this.cnx.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al desconectar de la base de datos: " + e.getMessage());
            // No System.exit(0). Solo reporta el error.
            System.err.println("Error SQL al desconectar: " + e.getMessage());
        } finally {
            this.cnx = null; // Limpia la referencia después de intentar cerrar
        }
    }
    
    public synchronized static Conexion getInstancia(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
}

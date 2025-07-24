/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.Conexion;
import Entidades.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Leonardo
 */
public class UsuarioDAO {

    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;

    public UsuarioDAO() {
        this.CNX = Conexion.getInstancia();
    }

    public boolean validarUsuario(String usuario, String clave) {
        boolean existe = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "SELECT 1 FROM usuario WHERE usuario = ? AND clave = ?"
            );
            ps.setString(1, usuario);
            ps.setString(2, clave);
            rs = ps.executeQuery();
            if (rs.next()) {
                existe = true; // Solo valida existencia, no necesita traer nada
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CNX.desconectar();
        }
        return existe;
    }

}

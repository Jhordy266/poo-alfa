/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.Conexion;
import Datos.Interfaces.IClienteNatural;
import Entidades.Cliente_Natural;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.SQLException;
/**
 *
 * @author leona
 */
public class Cliente_NaturalDAO implements IClienteNatural{
    
    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean confirmacion;

    public Cliente_NaturalDAO(){
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public List<Cliente_Natural> listar(String texto) {
        List<Cliente_Natural> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT * FROM Cliente_Natural WHERE Nombre LIKE ?");
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Cliente_Natural(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
        return registros;
    }

    @Override
    public boolean insertar(Cliente_Natural clienten) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement("INSERT INTO Cliente_Natural (Nombre,DNI)  VALUES (?,?)");
            ps.setString(1, clienten.getNombre());
            ps.setString(2, clienten.getDNI());
            if (ps.executeUpdate() > 0) {
                confirmacion = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return confirmacion;
    }

    @Override
    public boolean eliminar(Cliente_Natural clienten) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "DELETE FROM Cliente_Natural WHERE Id_CNatural = ?"
            );
            ps.setInt(1, clienten.getId_CNatural());
            if (ps.executeUpdate() > 0) {
                confirmacion = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return confirmacion;
    }

    @Override
    public boolean modificar(Cliente_Natural clienten) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "UPDATE Categorias SET Nombre = ?, DNI = ? WHERE Id_CNatural = ?"
            );
            ps.setString(1, clienten.getNombre());
            ps.setString(2, clienten.getDNI());
            ps.setInt(3, clienten.getId_CNatural());
            if (ps.executeUpdate() > 0) {
                confirmacion = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            CNX.desconectar();
        }
        return confirmacion;
    }
    
    
}

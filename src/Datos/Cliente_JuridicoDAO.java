/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.Conexion;
import Datos.Interfaces.IClienteJuridico;
import Entidades.Cliente_Juridico;
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
public class Cliente_JuridicoDAO implements IClienteJuridico{
    
    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean confirmacion;

    public Cliente_JuridicoDAO(){
        this.CNX = Conexion.getInstancia();
    }
    
    @Override
    public List<Cliente_Juridico> listar(String texto) {
        List<Cliente_Juridico> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT * FROM Cliente_Juridico WHERE Razon_Social LIKE ?");
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Cliente_Juridico(rs.getInt(1), rs.getString(2), rs.getString(3)));
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
    public boolean insertar(Cliente_Juridico clientej) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement("INSERT INTO Cliente_Juridico (Razon_Social,RUC)  VALUES (?,?)");
            ps.setString(1, clientej.getRaz_Social());
            ps.setString(2, clientej.getRUC());
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
    public boolean eliminar(Cliente_Juridico clientej) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "DELETE FROM Cliente_Juridico WHERE Id_CJuridico = ?"
            );
            ps.setInt(1, clientej.getId_CJuridico());
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
    public boolean modificar(Cliente_Juridico clientej) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "UPDATE Categorias SET Raz_Social = ?, RUC = ? WHERE Id_CJuridico = ?"
            );
            ps.setString(1, clientej.getRaz_Social());
            ps.setString(2, clientej.getRUC());
            ps.setInt(3, clientej.getId_CJuridico());
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

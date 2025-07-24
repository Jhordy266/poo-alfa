/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.Conexion;
import Entidades.Tipo_Cliente;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Datos.Interfaces.ITipoCliente;
import java.sql.SQLException;
/**
 *
 * @author leona
 */
public class TipoClienteDAO implements ITipoCliente{
    
    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean confirmacion;

    public TipoClienteDAO() {
        this.CNX = Conexion.getInstancia();
    }
    
    @Override
    public boolean modificar(Tipo_Cliente tipocliente) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "UPDATE Tipocliente SET Nombre = ? WHERE Id_TipoCliente = ?"
            );
            ps.setString(1, tipocliente.getNombre());
            ps.setInt(2, tipocliente.getId_TipoCliente());
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
    public boolean eliminar(Tipo_Cliente tipocCliente) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "DELETE FROM Tipo_Cliente WHERE Id_TipoCliente = ?"
            );
            ps.setInt(1, tipocCliente.getId_TipoCliente());
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
    public boolean insertar(Tipo_Cliente tipo_Cliente) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement("INSERT INTO TipoCliente (Nombre)  VALUES (?)");
            ps.setString(1, tipo_Cliente.getNombre());
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
    public List<Tipo_Cliente> listar(String texto) {
        List<Tipo_Cliente> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT * FROM TipoCliente WHERE Nombre LIKE ?");
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Tipo_Cliente(rs.getInt(1), rs.getString(2)));
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
    
   public List<Tipo_Cliente> seleccionar() {
        List<Tipo_Cliente> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT Id_TipoCliente, Nombre FROM Tipo_Cliente");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Tipo_Cliente(rs.getInt(1), rs.getString(2)));
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
}

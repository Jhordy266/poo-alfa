/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.Conexion;
import Entidades.Tipo_Comprobante;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Datos.Interfaces.ITipoComprobante;
import java.sql.SQLException;
import java.sql.Connection;

/**
 *
 * @author leona
 */
public class TipoComprobanteDAO implements ITipoComprobante {

    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean confirmacion;
    private Connection con; 

    public TipoComprobanteDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public boolean modificar(Tipo_Comprobante tipocomprobante) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "UPDATE Tipocomprobante SET Tipo = ? WHERE Id_Tipocomprobante = ?"
            );
            ps.setString(1, tipocomprobante.getTipo());
            ps.setInt(2, tipocomprobante.getId_TipoComprobante());
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
    public boolean eliminar(Tipo_Comprobante tipocomprobante) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "DELETE FROM TipoComprobante WHERE Id_TipoComprobante = ?"
            );
            ps.setInt(1, tipocomprobante.getId_TipoComprobante());
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
    public boolean insertar(Tipo_Comprobante tipocomprobante) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement("INSERT INTO Tipocomprobante (Tipo)  VALUES (?,?,?)");
            ps.setString(1, tipocomprobante.getTipo());
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
    public List<Tipo_Comprobante> listar(String texto) {
        List<Tipo_Comprobante> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT * FROM Tipocomprobante WHERE Tipo LIKE ?");
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Tipo_Comprobante(rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4)));
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
    
    public List<Tipo_Comprobante> seleccionartipo() {
        List<Tipo_Comprobante> lista = new ArrayList<>();
        String sql = "SELECT Id_TipoComprobante, Tipo FROM TipoComprobante"; // Ajusta nombres de columnas
        try {
            con = CNX.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Tipo_Comprobante(rs.getInt("Id_Tipocomprobante"), rs.getString("Tipo")));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar tipos de comprobante: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) CNX.desconectar(); // Asumo que desconectar cierra la conexión
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos en listarTiposComprobante: " + e.getMessage());
            }
        }
        return lista;
    
    }
    
    

}

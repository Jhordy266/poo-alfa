/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.Conexion;
import Datos.Interfaces.IMarca;
import Entidades.Marca;
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
public class MarcaDAO implements IMarca {

    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean confirmacion;

    public MarcaDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public List<Marca> listar(String texto) {
        List<Marca> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT * FROM Marca WHERE Nombre LIKE ?");
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Marca(rs.getInt(1), rs.getString(2)));
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
    public boolean insertar(Marca marca) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement("INSERT INTO Marca (Nombre)  VALUES (?,?)");
            ps.setInt(1, marca.getId_Marca());
            ps.setString(2, marca.getNombre());
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
    public boolean eliminar(Marca marca) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "DELETE FROM Marca WHERE Id_Marca = ?"
            );
            ps.setInt(1, marca.getId_Marca());
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
    public boolean modificar(Marca marca) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "UPDATE Marca SET Nombre = ? WHERE Id_Marca = ?"
            );
            ps.setString(1, marca.getNombre());
            ps.setInt(2, marca.getId_Marca());
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

    public List<Marca> seleccionarmar() {
        List<Marca> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT Id_Marca, Nombre FROM Marca");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Marca(rs.getInt(1), rs.getString(2)));
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

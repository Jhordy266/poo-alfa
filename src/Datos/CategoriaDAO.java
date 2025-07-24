/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.Conexion;
import Entidades.Categoria;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Datos.Interfaces.ICategoria;
import java.sql.SQLException;

/**
 *
 * @author leona
 */
public class CategoriaDAO implements ICategoria {

    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean confirmacion;

    public CategoriaDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public boolean modificar(Categoria categoria) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "UPDATE Categoria SET Nombre = ?, Descripcion = ?, Estado = ? WHERE Id_Categoria = ?"
            );
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setBoolean(3, categoria.isEstado());
            ps.setInt(4, categoria.getId_Categoria());
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
    public boolean eliminar(Categoria categoria) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "DELETE FROM Categoria WHERE Id_Categoria = ?"
            );
            ps.setInt(1, categoria.getId_Categoria());
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
    public boolean insertar(Categoria categoria) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement("INSERT INTO Categoria (Nombre,Descripcion,Estado)  VALUES (?,?,1)");
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getDescripcion());
            ps.setBoolean(3, categoria.isEstado());
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
    public List<Categoria> listar(String texto) {
        List<Categoria> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT * FROM Categoria WHERE Nombre LIKE ?");
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Categoria(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getBoolean(4)));
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

    public List<Categoria> seleccionar() {
        List<Categoria> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT Id_Categoria, Nombre FROM Categoria");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Categoria(rs.getInt(1), rs.getString(2)));
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

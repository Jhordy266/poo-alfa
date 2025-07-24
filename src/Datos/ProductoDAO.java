/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.Conexion;
import Datos.Interfaces.IProducto;
import Entidades.Producto;
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
public class ProductoDAO implements IProducto {

    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean confirmacion;

    public ProductoDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public List<Producto> listar(String texto) {
        List<Producto> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT a.Id_Producto, a.Id_Categoria, b.NOMBRE AS CATEGORIA, a.Id_Marca, c.Nombre AS MARCA, a.NOMBRE AS PRODUCTO, a.DESCRIPCION, a.PRECIO, a.STOCK FROM producto a INNER JOIN categoria b ON a.Id_Categoria = b.Id_Categoria INNER JOIN Marca c ON a.Id_Marca = c.Id_Marca WHERE a.NOMBRE LIKE ? ORDER BY a.Nombre ASC");
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Producto(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getDouble(8), rs.getInt(9)));
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
    public boolean insertar(Producto producto) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement("INSERT INTO Producto (Id_Categoria,Id_Marca,Nombre,Descripcion,Precio_Unitario,Stock)  VALUES (?,?,?,?,?,?)");
            ps.setInt(1, producto.getId_Categoria());
            ps.setInt(2, producto.getId_Marca());
            ps.setString(3, producto.getNombre());
            ps.setString(4, producto.getDescripcion());
            ps.setDouble(5, producto.getPrecio_U());
            ps.setInt(6, producto.getStock());
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
    public boolean eliminar(Producto producto) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "DELETE FROM Producto WHERE Id_Producto = ?"
            );
            ps.setInt(1, producto.getId_Producto());
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
    public boolean modificar(Producto producto) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "UPDATE Producto SET Nombre = ?, Id_Categoria = ?, Id_Marca = ?, Descripcion = ?, Precio_Unitario = ?, Stock = ? WHERE Id_Producto = ?"
            );
            ps.setString(1, producto.getNombre());
            ps.setInt(2, producto.getId_Categoria());
            ps.setInt(3, producto.getId_Marca());
            ps.setString(4, producto.getDescripcion());
            ps.setDouble(5, producto.getPrecio_U());
            ps.setInt(6, producto.getStock());
            ps.setInt(7, producto.getId_Producto());
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

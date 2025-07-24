/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos;

import Conexion.Conexion;
import Entidades.Empleado;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Datos.Interfaces.IEmpleado;
import java.sql.SQLException;
/**
 *
 * @author leona
 */
public class EmpleadoDAO implements IEmpleado{
    
    private final Conexion CNX;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean confirmacion;

    public EmpleadoDAO() {
        this.CNX = Conexion.getInstancia();
    }

    @Override
    public List<Empleado> listar(String texto) {
        List<Empleado> registros = new ArrayList<>();
        try {
            ps = CNX.conectar().prepareStatement("SELECT * FROM Empleado WHERE Nombres LIKE ?");
            ps.setString(1, "%" + texto + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                registros.add(new Empleado(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6)));
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
    public boolean insertar(Empleado empleado) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement("INSERT INTO Empleado (Nombres,DNI,Telefono,Turno,Salario)  VALUES (?,?,?,?,?)");
            ps.setString(1, empleado.getNombres());
            ps.setString(2, empleado.getDNI());
            ps.setString(3, empleado.getTelefono());
            ps.setString(4, empleado.getTurno());
            ps.setDouble(5, empleado.getSalario());
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
    public boolean eliminar(Empleado empleado) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "DELETE FROM Empleado WHERE Id_Empleado = ?"
            );
            ps.setInt(1, empleado.getId_Empleado());
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
    public boolean modificar(Empleado empleado) {
        confirmacion = false;
        try {
            ps = CNX.conectar().prepareStatement(
                    "UPDATE Empleado SET Nombres = ?, DNI = ?, Telefono = ?, Turno = ?, Salario = ? WHERE Id_Empleado = ?"
            );
            ps.setString(1, empleado.getNombres());
            ps.setString(2, empleado.getDNI());
            ps.setString(3, empleado.getTelefono());
            ps.setString(4, empleado.getTurno());
            ps.setDouble(5, empleado.getSalario());
            ps.setInt(6, empleado.getId_Empleado());
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

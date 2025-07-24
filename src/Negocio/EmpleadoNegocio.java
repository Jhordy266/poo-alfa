/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.EmpleadoDAO;
import Entidades.Empleado;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author leona
 */
public class EmpleadoNegocio {
    
    private final EmpleadoDAO DATOS;
    private DefaultTableModel dtm;

    public EmpleadoNegocio() {
        this.DATOS = new EmpleadoDAO();
    }
    
    public DefaultTableModel listar(String texto) {
        List<Empleado> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));

        String[] columnas = {"Id Empleado","Nombres", "DNI", "Telefono", "Turno", "Salario"};
        this.dtm = new DefaultTableModel(null, columnas);
        String[] registro = new String[6];
        for (Empleado item : lista) {
            registro[0] = Integer.toString(item.getId_Empleado());
            registro[1] = item.getNombres();
            registro[2] = item.getDNI();
            registro[3] = item.getTelefono();
            registro[4] = item.getTurno();
            registro[5] = Double.toString(item.getSalario());
            this.dtm.addRow(registro);
        }
        return (this.dtm);

    }

    public String insertar(Empleado empleado) {
        if (DATOS.insertar(empleado)) {
            return "Ok";
        } else {
            return "Error en la insercción";
        }
    }

    
    public String modificar(Empleado empleado) {
        if (DATOS.modificar(empleado)) {
            return "Ok";
        } else {
            return "Error al modificar la empleado";
        }
    }

    public String eliminar(int id) {
        Empleado empleado = new Empleado();
        empleado.setId_Empleado(id);  
        if (DATOS.eliminar(empleado)) {
            return "Ok";
        } else {
            return "Error al eliminar la empleado";
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.ProveedorDAO;
import Entidades.Proveedor;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author leona
 */
public class ProveedorNegocio {
    
    private final ProveedorDAO DATOS;
    private DefaultTableModel dtm;

    public ProveedorNegocio() {
        this.DATOS = new ProveedorDAO();
    }
    
    public DefaultTableModel listar(String texto) {
        List<Proveedor> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));

        String[] columnas = {"Id_Proveedor", "Nombre", "Contacto", "Telefono"};
        this.dtm = new DefaultTableModel(null, columnas);
        String[] registro = new String[4];
        for (Proveedor item : lista) {
            registro[0] = Integer.toString(item.getId_Proveedor());
            registro[1] = item.getNombre();
            registro[2] = item.getContacto();
            registro[3] = item.getTelefono();
            this.dtm.addRow(registro);
        }
        return (this.dtm);

    }

    public String insertar(Proveedor proveedor) {
        if (DATOS.insertar(proveedor)) {
            return "Proveedor registrada con exito";
        } else {
            return "Error en la insercción";
        }
    }

    
    public String modificar(Proveedor proveedor) {
        if (DATOS.modificar(proveedor)) {
            return "Proveedor modificada con éxito";
        } else {
            return "Error al modificar la proveedor";
        }
    }

    public String eliminar(int id) {
        Proveedor proveedor = new Proveedor();
        proveedor.setId_Proveedor(id);  
        if (DATOS.eliminar(proveedor)) {
            return "Categoría eliminada con éxito";
        } else {
            return "Error al eliminar la categoría";
        }
    }
}

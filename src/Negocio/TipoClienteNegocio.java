/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.TipoClienteDAO;
import Entidades.Tipo_Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author leona
 */
public class TipoClienteNegocio {
    
    private final TipoClienteDAO DATOS;
    private DefaultTableModel dtm;

    public TipoClienteNegocio() {
        this.DATOS = new TipoClienteDAO();
    }
    
    public DefaultTableModel listar(String texto) {
        List<Tipo_Cliente> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));

        String[] columnas = {"Id_TipoCliente", "Nombre"};
        this.dtm = new DefaultTableModel(null, columnas);
        String[] registro = new String[2];
        for (Tipo_Cliente item : lista) {
            registro[0] = Integer.toString(item.getId_TipoCliente());
            registro[1] = item.getNombre();
            this.dtm.addRow(registro);
        }
        return (this.dtm);

    }

    public String insertar(Tipo_Cliente tipo_Cliente) {
        if (DATOS.insertar(tipo_Cliente)) {
            return "Tipo de cleinte registrado con exito";
        } else {
            return "Error en la insercción";
        }
    }

    
    public String modificar(Tipo_Cliente tipo_Cliente) {
        if (DATOS.modificar(tipo_Cliente)) {
            return "Tipo de cliente modificado con éxito";
        } else {
            return "Error al modificar el tipo de cliente";
        }
    }

    public String eliminar(int id) {
        Tipo_Cliente tipo_Cliente = new Tipo_Cliente();
        tipo_Cliente.setId_TipoCliente(id);  
        if (DATOS.eliminar(tipo_Cliente)) {
            return "Tipo de cliente eliminado con éxito";
        } else {
            return "Error al eliminar el tipo de cliente";
        }
    }
}

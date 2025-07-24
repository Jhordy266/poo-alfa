/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.ClienteDAO;
import Datos.TipoClienteDAO;
import Entidades.Cliente;
import Entidades.Tipo_Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author leona
 */
public class ClienteNegocio {

    private final ClienteDAO DATOS;
    private final TipoClienteDAO DATOSTC;
    private DefaultTableModel dtm;

    public ClienteNegocio() {
        this.DATOS = new ClienteDAO();
        this.DATOSTC = new TipoClienteDAO();
    }

    public DefaultTableModel listar(String texto) {
        List<Cliente> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));

        String[] columnas = {"Id_Cliente", "Tipo Cliente", "Nombre/Razón Social", "DNI/RUC", "Dirección", "Teléfono"};
        this.dtm = new DefaultTableModel(null, columnas);
        String[] registro = new String[6];

        for (Cliente item : lista) {
            registro[0] = String.valueOf(item.getId_Cliente());
            registro[1] = item.getTipoCliente() != null ? item.getTipoCliente().getNombre() : "N/A";
            registro[2] = item.getNombreORazonSocial();
            registro[3] = item.getDniORuc();
            registro[4] = item.getDireccion();
            registro[5] = item.getTelefono();
            this.dtm.addRow(registro);
        }
        return this.dtm;
    }

    public String insertar(Cliente cliente) {
        if (DATOS.insertar(cliente)) {
            return "Ok";
        } else {
            return "Error en la inserción";
        }
    }

    public String modificar(Cliente cliente) {
        if (DATOS.modificar(cliente)) {
            return "OK";
        } else {
            return "Error al modificar el cliente";
        }
    }

    public String eliminar(int id) {
        Cliente cliente = new Cliente();
        cliente.setId_Cliente(id);
        if (DATOS.eliminar(cliente)) {
            return "OK";
        } else {
            return "Error al eliminar el cliente";
        }
    }

    public DefaultComboBoxModel<Tipo_Cliente> seleccionar() {
        DefaultComboBoxModel<Tipo_Cliente> items = new DefaultComboBoxModel<>();
        List<Tipo_Cliente> lista = new ArrayList<>();
        lista = DATOSTC.seleccionar();
        for (Tipo_Cliente item : lista) {
            items.addElement(new Tipo_Cliente(item.getId_TipoCliente(), item.getNombre()));
        }
        return items;
    }


    public int[] obtenerIdDetalleCliente(int idCliente) {
        return DATOS.obtenerIdDetalleCliente(idCliente);
    }

    public Cliente obtenerClientePorId(int idCliente) {
        return DATOS.obtener(idCliente);
    }
}

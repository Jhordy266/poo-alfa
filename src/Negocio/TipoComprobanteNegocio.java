/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.TipoComprobanteDAO;
import Entidades.Tipo_Comprobante;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author leona
 */
public class TipoComprobanteNegocio {

    private final TipoComprobanteDAO DATOS;
    private DefaultTableModel dtm;

    public TipoComprobanteNegocio() {
        this.DATOS = new TipoComprobanteDAO();
    }

    public DefaultTableModel listar(String texto) {
        List<Tipo_Comprobante> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));

        String[] columnas = {"Id_Tipocomprobante", "Tipo"};
        this.dtm = new DefaultTableModel(null, columnas);
        String[] registro = new String[2];
        for (Tipo_Comprobante item : lista) {
            registro[0] = Integer.toString(item.getId_TipoComprobante());
            registro[1] = item.getTipo();
            this.dtm.addRow(registro);
        }
        return (this.dtm);

    }

    public String insertar(Tipo_Comprobante tipo_Comprobante) {
        if (DATOS.insertar(tipo_Comprobante)) {
            return "Ok";
        } else {
            return "Error en la insercción";
        }
    }

    public String modificar(Tipo_Comprobante tipo_Comprobante) {
        if (DATOS.modificar(tipo_Comprobante)) {
            return "OK";
        } else {
            return "Error al modificar la comprobante";
        }
    }

    public String eliminar(int id) {
        Tipo_Comprobante tipo_Comprobante = new Tipo_Comprobante();
        tipo_Comprobante.setId_TipoComprobante(id);
        if (DATOS.eliminar(tipo_Comprobante)) {
            return "OK";
        } else {
            return "Error al eliminar el comprobante";
        }
    }
}

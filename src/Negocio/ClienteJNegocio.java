/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.Cliente_JuridicoDAO;
import Entidades.Cliente_Juridico;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author leona
 */
public class ClienteJNegocio {
    
    private final Cliente_JuridicoDAO DATOS;
    private DefaultTableModel dtm;

    public ClienteJNegocio() {
        this.DATOS = new Cliente_JuridicoDAO();
    }
    
    public DefaultTableModel listar(String texto) {
        List<Cliente_Juridico> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));

        String[] columnas = {"Id_CJuridico", "Raz_Social", "RUC"};
        this.dtm = new DefaultTableModel(null, columnas);
        String[] registro = new String[3];
        for (Cliente_Juridico item : lista) {
            registro[0] = Integer.toString(item.getId_CJuridico());
            registro[1] = item.getRaz_Social();
            registro[2] = item.getRUC();
            this.dtm.addRow(registro);
        }
        return (this.dtm);

    }

    public String insertar(Cliente_Juridico clientej) {
        if (DATOS.insertar(clientej)) {
            return "Ok";
        } else {
            return "Error en la insercción";
        }
    }

    public String modificar(Cliente_Juridico clientej) {
        if (DATOS.modificar(clientej)) {
            return "OK";
        } else {
            return "Error al modificar el cliente juridico";
        }
    }

    public String eliminar(int id) {
        Cliente_Juridico clientej = new Cliente_Juridico();
        clientej.setId_CJuridico(id);
        if (DATOS.eliminar(clientej)) {
            return "OK";
        } else {
            return "Error al eliminar el cliente";
        }
    }
}

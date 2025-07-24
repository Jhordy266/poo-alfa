/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.Cliente_NaturalDAO;
import Entidades.Cliente_Natural;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author leona
 */
public class ClienteNNegocio {
    
    private final Cliente_NaturalDAO DATOS;
    private DefaultTableModel dtm;

    public ClienteNNegocio() {
        this.DATOS = new Cliente_NaturalDAO();
    }
    
    public DefaultTableModel listar(String texto) {
        List<Cliente_Natural> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));

        String[] columnas = {"Id_CNatural", "Nombre", "DNI"};
        this.dtm = new DefaultTableModel(null, columnas);
        String[] registro = new String[3];
        for (Cliente_Natural item : lista) {
            registro[0] = Integer.toString(item.getId_CNatural());
            registro[1] = item.getNombre();
            registro[2] = item.getDNI();
            this.dtm.addRow(registro);
        }
        return (this.dtm);

    }
    
    public String insertar(Cliente_Natural clienten) {
        if (DATOS.insertar(clienten)) {
            return "Cliente Natural registrado con exito";
        } else {
            return "Error en la insercción";
        }
    }

    public String modificar(Cliente_Natural clienten) {
        if (DATOS.modificar(clienten)) {
            return "Cliente Natural modificado con éxito";
        } else {
            return "Error al modificar el cliente natural";
        }
    }

    public String eliminar(int id) {
        Cliente_Natural clienten = new Cliente_Natural();
        clienten.setId_CNatural(id);
        if (DATOS.eliminar(clienten)) {
            return "Cliente eliminado con éxito";
        } else {
            return "Error al eliminar el cliente";
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.MarcaDAO;
import Entidades.Marca;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author leona
 */
public class MarcaNegocio {
    
    private final MarcaDAO DATOS;
    private DefaultTableModel dtm;

    public MarcaNegocio() {
        this.DATOS = new MarcaDAO();
    }
    
    public DefaultTableModel listar(String texto) {
        List<Marca> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));

        String[] columnas = {"Id_Marca", "Marca"};
        this.dtm = new DefaultTableModel(null, columnas);
        String[] registro = new String[2];
        for (Marca item : lista) {
            registro[0] = Integer.toString(item.getId_Marca());
            registro[1] = item.getNombre();
            this.dtm.addRow(registro);
        }
        return (this.dtm);

    }

    public String insertar(Marca marca) {
        if (DATOS.insertar(marca)) {
            return "Marca registrada con exito";
        } else {
            return "Error en la insercción";
        }
    }

    /*AÑADIR MODIFICAR,ELIMINAR*/
    
    public String modificar(Marca marca) {
        if (DATOS.modificar(marca)) {
            return "Marca modificada con éxito";
        } else {
            return "Error al modificar la marca";
        }
    }

    public String eliminar(int id) {
        Marca marca = new Marca();
        marca.setId_Marca(id);  
        if (DATOS.eliminar(marca)) {
            return "Marca eliminada con éxito";
        } else {
            return "Error al eliminar la marca";
        }
    }
}

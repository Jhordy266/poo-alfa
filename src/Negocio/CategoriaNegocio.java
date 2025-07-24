/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.CategoriaDAO;
import Entidades.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author leona
 */
public class CategoriaNegocio {

    private final CategoriaDAO DATOS;
    private DefaultTableModel dtm;

    public CategoriaNegocio() {
        this.DATOS = new CategoriaDAO();
    }

    public DefaultTableModel listar(String texto) { 
        List<Categoria> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));

        String[] columnas = {"Id_Categoria", "Nombre Categoria", "Descripción", "Estado"};
        this.dtm = new DefaultTableModel(null, columnas);
        String[] registro = new String[4];
        String estado;
        for(Categoria item : lista){
            if(item.isEstado()){
                estado = "Activo";
            }else{
                estado = "Inactivo";
            }
            registro[0] = Integer.toString(item.getId_Categoria());
            registro[1] = item.getNombre();
            registro[2] = item.getDescripcion();
            registro[3] = estado;
            this.dtm.addRow(registro);
        }
        return (this.dtm);
    }

    public String insertar(Categoria categoria) {
        if (DATOS.insertar(categoria)) {
            return "Ok";
        } else {
            return "Error en la insercción";
        }
    }

    
    public String modificar(Categoria categoria) {
        if (DATOS.modificar(categoria)) {
            return "Ok";
        } else {
            return "Error al modificar la categoría";
        }
    }

    public String eliminar(int id) {
        Categoria categoria = new Categoria();
        categoria.setId_Categoria(id);  
        if (DATOS.eliminar(categoria)) {
            return "Ok";
        } else {
            return "Error al eliminar la categoría";
        }
    }
    
    
}

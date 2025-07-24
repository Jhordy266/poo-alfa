/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.ProductoDAO;
import Datos.CategoriaDAO;
import Datos.MarcaDAO;
import Entidades.Producto;
import Entidades.Categoria;
import Entidades.Marca;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author leona
 */
public class ProductoNegocio {

    private final ProductoDAO DATOS;
    private final CategoriaDAO DATOSCAT;
    private final MarcaDAO DATOSMAR;
    private Producto obj;
    private DefaultTableModel dtm;

    public ProductoNegocio() {
        this.DATOS = new ProductoDAO();
        this.DATOSCAT = new CategoriaDAO();
        this.DATOSMAR = new MarcaDAO();
        this.obj = new Producto();
    }

    public DefaultTableModel listar(String texto) {
        List<Producto> lista = new ArrayList<>();
        lista.addAll(DATOS.listar(texto));

        String[] columnas = {"Id_Producto", "Id_Categoria", "Categoria", "Id_Marca", "Marca", "Nombre", "Descripcion", "Precio_Unitario", "Stock"};
        this.dtm = new DefaultTableModel(null, columnas);
        String[] registro = new String[9];
        for (Producto item : lista) {
            registro[0] = Integer.toString(item.getId_Producto());
            registro[1] = Integer.toString(item.getId_Categoria());
            registro[2] = item.getcategoriaNombre();
            registro[3] = Integer.toString(item.getId_Marca());
            registro[4] = item.getmarcaNombre();
            registro[5] = item.getNombre();
            registro[6] = item.getDescripcion();
            registro[7] = Double.toString(item.getPrecio_U());
            registro[8] = Integer.toString(item.getStock());
            this.dtm.addRow(registro);
        }
        return (this.dtm);

    }

    public String insertar(Producto producto) {
        if (DATOS.insertar(producto)) {
            return "Ok";
        } else {
            return "Error en la insercción";
        }
    }

    public String modificar(Producto producto) {
        if (DATOS.modificar(producto)) {
            return "Ok";
        } else {
            return "Error al modificar el producto";
        }
    }

    public String eliminar(int id) {
        Producto producto = new Producto();
        producto.setId_Producto(id);
        if (DATOS.eliminar(producto)) {
            return "Ok";
        } else {
            return "Error al eliminar el producto";
        }
    }

    public DefaultComboBoxModel<Categoria> seleccionar() {
        DefaultComboBoxModel<Categoria> items = new DefaultComboBoxModel<>();
        List<Categoria> lista = new ArrayList<>();
        lista = DATOSCAT.seleccionar();
        for (Categoria item : lista) {
            items.addElement(new Categoria(item.getId_Categoria(), item.getNombre()));
        }
        return items;
    }

    public DefaultComboBoxModel<Marca> seleccionarmar() {
        DefaultComboBoxModel<Marca> items = new DefaultComboBoxModel<>();
        List<Marca> lista = new ArrayList<>();
        lista = DATOSMAR.seleccionarmar();
        for (Marca item : lista) {
            items.addElement(new Marca(item.getId_Marca(), item.getNombre()));
        }
        return items;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import Datos.ProductoDAO;
import Datos.TipoComprobanteDAO;
import Datos.VentaDAO;
import Entidades.Producto;
import Entidades.DetalleVenta;
import Entidades.Venta;
import Entidades.Cliente;
import Entidades.Empleado;
import Entidades.Tipo_Comprobante;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author leona
 */
public class VentaNegocio {

    private final VentaDAO DATOS;
    private final ProductoDAO DATOSART;

    private Venta obj;
    private DefaultTableModel dtm;

    public VentaNegocio() {
        this.DATOS = new VentaDAO();
        this.DATOSART = new ProductoDAO();
        this.obj = new Venta();
    }

    public DefaultTableModel listar(String texto) {
        List<Venta> lista = new ArrayList();
        lista.addAll(DATOS.listarVentas(texto));

        String[] titulos = {"Id", "Cliente ID", "Cliente", "Tipo Comprobante", "Serie", "Número", "Fecha", "Impuesto", "Total", "Estado"};
        this.dtm = new DefaultTableModel(null, titulos);

        String[] registro = new String[10];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (Venta item : lista) {
            registro[0] = Integer.toString(item.getId());
            registro[1] = Integer.toString(item.getPersonaId());
            registro[2] = item.getPersonaNombre();
            registro[3] = item.getTipoComprobante();
            registro[4] = item.getSerieComprobante();
            registro[5] = item.getNumComprobante();
            registro[6] = sdf.format(item.getFecha());
            registro[7] = Double.toString(item.getImpuesto());
            registro[8] = Double.toString(item.getTotal());
            registro[9] = item.getEstado();

            this.dtm.addRow(registro);
        }
        return this.dtm;
    }

    public DefaultTableModel listarDetalle(int id) {
        List<DetalleVenta> lista = new ArrayList();
        lista.addAll(DATOS.listarDetallesVenta(id));

        String[] titulos = {"ID", "ARTICULO", "STOCK", "CANTIDAD", "PRECIO", "DESCUENTO", "SUBTOTAL"};
        this.dtm = new DefaultTableModel(null, titulos);

        for (DetalleVenta item : lista) {
            String[] registro = new String[7]; 
            registro[0] = Integer.toString(item.getArticuloId());
            registro[1] = item.getArticuloNombre();
            registro[2] = Integer.toString(item.getArticuloStock());
            registro[3] = Integer.toString(item.getCantidad());
            registro[4] = (item.getPrecio() != null) ? String.format("%.2f", item.getPrecio()) : "0.00";
            registro[5] = (item.getDescuento() != null) ? String.format("%.2f", item.getDescuento()) : "0.00";
            registro[6] = (item.getSubTotal() != null) ? String.format("%.2f", item.getSubTotal()) : "0.00";
            this.dtm.addRow(registro);
        }
        return this.dtm;
    }

    public String ultimoSerie(String tipoComprobante) {
        return this.DATOS.ultimoSerie(tipoComprobante);
    }

    public String ultimoNumero(String tipoComprobante, String serieComprobante) {
        return this.DATOS.ultimoNumero(tipoComprobante, serieComprobante);
    }

    public String insertarVenta(int personaId, String tipoComprobante, String serieComprobante, String numComprobante, double impuesto, double total, DefaultTableModel modeloDetalles) {
        if (DATOS.existeVenta(serieComprobante, numComprobante)) {
            return "El registro ya existe.";
        } else {

            obj.setPersonaId(personaId);
            obj.setTipoComprobante(tipoComprobante);
            obj.setSerieComprobante(serieComprobante);
            obj.setNumComprobante(numComprobante);
            obj.setImpuesto(impuesto);
            obj.setTotal(total);

            List<DetalleVenta> detalles = new ArrayList();
            int articuloId;
            int cantidad;
            double precio;
            double descuento;
            /*double subtotal;*/

            for (int i = 0; i < modeloDetalles.getRowCount(); i++) {
                articuloId = Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 0)));
                cantidad = Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 3)));
                precio = Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 4)).replace(",", "."));
                descuento = Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 5)).replace(",", "."));
                /*subtotal = Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 6)).replace(",", "."));*/
                detalles.add(new DetalleVenta(articuloId, cantidad, precio, descuento));
            }

            obj.setDetalles(detalles);

            if (DATOS.insertarVenta(obj)) {
                return "OK";
            } else {
                return "Error en el registro.";
            }
        }
    }

    public String anular(int id) {
        if (DATOS.anularVenta(id)) {
            return "OK";
        } else {
            return "No se puede anular el registro";
        }
    }

}

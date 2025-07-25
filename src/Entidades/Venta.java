/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.sql.Date;
import java.util.List;

/**
 *
 * @author Leonardo
 */
public class Venta {

    private int id;
    private int personaId;
    private int empleadoId;
    private String empleadoNombre;
    private String personaNombre;/*Cliente*/
    private String tipoComprobante;/*Tipo de comprobante*/
    private String serieComprobante;
    private String numComprobante;
    private Date fecha;
    private double impuesto;
    private double total;
    private String estado;
    private List<DetalleVenta> detalles;

    public Venta() {
    }

    public Venta(int id, int personaId, String personaNombre, String tipoComprobante, String serieComprobante, String numComprobante, Date fecha, double impuesto, double total, String estado, List<DetalleVenta> detalles) {
        this.id = id;
        this.personaId = personaId;
        this.personaNombre = personaNombre;
        this.tipoComprobante = tipoComprobante;
        this.serieComprobante = serieComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.impuesto = impuesto;
        this.total = total;
        this.estado = estado;
        this.detalles = detalles;
    }

    public Venta(int id, int personaId, String personaNombre, String tipoComprobante, String serieComprobante, String numComprobante, Date fecha, double impuesto, double total, String estado) {
        this.id = id;
        this.personaId = personaId;
        this.personaNombre = personaNombre;
        this.tipoComprobante = tipoComprobante;
        this.serieComprobante = serieComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.impuesto = impuesto;
        this.total = total;
        this.estado = estado;
    }

    public Venta(int id, int personaId, int empleadoId, String personaNombre, String tipoComprobante, String serieComprobante, String numComprobante, Date fecha, double impuesto, double total, String estado, List<DetalleVenta> detalles) {
        this.id = id;
        this.personaId = personaId;
        this.empleadoId = empleadoId;
        this.personaNombre = personaNombre;
        this.tipoComprobante = tipoComprobante;
        this.serieComprobante = serieComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.impuesto = impuesto;
        this.total = total;
        this.estado = estado;
        this.detalles = detalles;
    }

    public Venta(int id, int personaId, int empleadoId, String personaNombre, String tipoComprobante, String serieComprobante, String numComprobante, Date fecha, double impuesto, double total, String estado) {
        this.id = id;
        this.personaId = personaId;
        this.empleadoId = empleadoId;
        this.personaNombre = personaNombre;
        this.tipoComprobante = tipoComprobante;
        this.serieComprobante = serieComprobante;
        this.numComprobante = numComprobante;
        this.fecha = fecha;
        this.impuesto = impuesto;
        this.total = total;
        this.estado = estado;
    }

    public Venta(int id, int personaId, int empleadoId, String personaNombre, String empleadoNombre, String tipoComprobante, String serieComprobante, String numComprobante, Date fecha, double impuesto, double total, String estado) {
        this(id, personaId, empleadoId, personaNombre, tipoComprobante, serieComprobante, numComprobante, fecha, impuesto, total, estado);
        this.empleadoNombre = empleadoNombre;
    }

    public Venta(int id, int personaId, int empleadoId, String personaNombre, String empleadoNombre, String tipoComprobante, String serieComprobante, String numComprobante, Date fecha, double impuesto, double total, String estado, List<DetalleVenta> detalles) {
        this(id, personaId, empleadoId, personaNombre, empleadoNombre, tipoComprobante, serieComprobante, numComprobante, fecha, impuesto, total, estado);
        this.detalles = detalles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    public String getEmpleadoNombre() {
        return empleadoNombre;
    }

    public void setEmpleadoNombre(String empleadoNombre) {
        this.empleadoNombre = empleadoNombre;
    }

    public String getPersonaNombre() {
        return personaNombre;
    }

    public void setPersonaNombre(String personaNombre) {
        this.personaNombre = personaNombre;
    }

    public String getTipoComprobante() {
        return tipoComprobante;
    }

    public void setTipoComprobante(String tipoComprobante) {
        this.tipoComprobante = tipoComprobante;
    }

    public String getSerieComprobante() {
        return serieComprobante;
    }

    public void setSerieComprobante(String serieComprobante) {
        this.serieComprobante = serieComprobante;
    }

    public String getNumComprobante() {
        return numComprobante;
    }

    public void setNumComprobante(String numComprobante) {
        this.numComprobante = numComprobante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }
}

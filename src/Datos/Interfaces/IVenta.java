/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Datos.Interfaces;

import java.util.List;

/**
 *
 * @author Aldo
 */
public interface IVenta<T, D> {

    public List<T> listarVentas(String filtro);

    public List<D> listarDetallesVenta(int idVenta);

    public boolean insertarVenta(T venta);

    public boolean anularVenta(int idVenta);

    public boolean existeVenta(String numeroSerie, String numeroComprobante);
}

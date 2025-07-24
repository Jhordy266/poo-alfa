/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Datos.Interfaces;

import Entidades.Tipo_Comprobante;
import java.util.List;

/**
 *
 * @author leona
 */
public interface ITipoComprobante {

    public List<Tipo_Comprobante> listar(String texto);

    public boolean insertar(Tipo_Comprobante tipocomprobante);

    public boolean eliminar(Tipo_Comprobante tipocomprobante);

    public boolean modificar(Tipo_Comprobante tipocomprobante);
}

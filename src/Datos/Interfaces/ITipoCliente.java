/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Datos.Interfaces;

import Entidades.Tipo_Cliente;
import java.util.List;
/**
 *
 * @author leona
 */
public interface ITipoCliente {
    
    public List<Tipo_Cliente> listar(String texto);

    public boolean insertar(Tipo_Cliente tipocomprobante);

    public boolean eliminar(Tipo_Cliente tipocomprobante);

    public boolean modificar(Tipo_Cliente tipocomprobante);
}

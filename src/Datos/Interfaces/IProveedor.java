/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Datos.Interfaces;

import Entidades.Proveedor;
import java.util.List;
/**
 *
 * @author leona
 */
public interface IProveedor {
    
    public List<Proveedor> listar(String texto);

    public boolean insertar(Proveedor proveedor);

    public boolean eliminar(Proveedor proveedor);

    public boolean modificar(Proveedor proveedor);
}

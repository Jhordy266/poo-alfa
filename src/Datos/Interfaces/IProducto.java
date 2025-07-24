/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Datos.Interfaces;

import Entidades.Producto;
import java.util.List;
/**
 *
 * @author leona
 */
public interface IProducto {
    
    public List<Producto> listar(String texto);

    public boolean insertar(Producto producto);

    public boolean eliminar(Producto producto);

    public boolean modificar(Producto producto);
}

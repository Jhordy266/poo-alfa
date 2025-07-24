/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Datos.Interfaces;

import Entidades.Cliente_Natural;
import java.util.List;
/**
 *
 * @author leona
 */
public interface IClienteNatural {
    
    public List<Cliente_Natural> listar(String texto);
    
    public boolean insertar(Cliente_Natural clienten);
    
    public boolean eliminar(Cliente_Natural clienten);
    
    public boolean modificar(Cliente_Natural clienten);
}

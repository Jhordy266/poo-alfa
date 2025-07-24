/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Datos.Interfaces;

import Entidades.Cliente;
import java.util.List;

/**
 *
 * @author leona
 */
public interface ICliente {

    public List<Cliente> listar(String texto);

    public boolean insertar(Cliente cliente);

    public boolean eliminar(Cliente cliente);

    public boolean modificar(Cliente cliente);
}

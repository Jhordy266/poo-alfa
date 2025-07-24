/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Datos.Interfaces;

import Entidades.Cliente_Juridico;
import java.util.List;
/**
 *
 * @author leona
 */
public interface IClienteJuridico {
    
    public List<Cliente_Juridico> listar(String texto);
    
    public boolean insertar(Cliente_Juridico clientej);
    
    public boolean eliminar(Cliente_Juridico clientej);
    
    public boolean modificar(Cliente_Juridico clientej);
}

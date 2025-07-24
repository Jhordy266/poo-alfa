  /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Datos.Interfaces;

import Entidades.Categoria;
import java.util.List;

/**
 *
 * @author leona
 */
public interface ICategoria {

    public List<Categoria> listar(String texto);

    public boolean insertar(Categoria categoria);

    public boolean eliminar(Categoria categoria);
    
    public boolean modificar(Categoria categoria);

}

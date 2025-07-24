/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Datos.Interfaces;

import Entidades.Marca;
import java.util.List;

/**
 *
 * @author leona
 */
public interface IMarca {

    public List<Marca> listar(String texto);

    public boolean insertar(Marca marca);

    public boolean eliminar(Marca marca);

    public boolean modificar(Marca marca);
}

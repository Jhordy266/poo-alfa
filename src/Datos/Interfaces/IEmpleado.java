/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Datos.Interfaces;

import Entidades.Empleado;
import java.util.List;

/**
 *
 * @author leona
 */
public interface IEmpleado {

    public List<Empleado> listar(String texto);

    public boolean insertar(Empleado empleado);

    public boolean eliminar(Empleado empleado);

    public boolean modificar(Empleado empleado);
}

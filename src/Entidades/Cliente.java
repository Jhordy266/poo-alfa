package Entidades;

import java.util.Objects;
import Entidades.Cliente_Natural;
import Entidades.Cliente_Juridico;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author leona
 */
public class Cliente {

    private int Id_Cliente;

    private String Direccion;
    private String Telefono;
    private String nombre;
    private String documento;

    private int Id_TipoCliente;
    private int Id_CNatural;
    private int Id_CJuridico;

    // Referencias a objetos relacionados (pueden ser null si no aplican)
    private Tipo_Cliente tipoCliente;
    private Cliente_Natural clienteNatural;
    private Cliente_Juridico clienteJuridico;

    // Constructor vacío
    public Cliente() {
    }

    public Cliente(int Id_Cliente, String Direccion, String Telefono) {
        this.Id_Cliente = Id_Cliente;
        this.Direccion = Direccion;
        this.Telefono = Telefono;
    }

    public Cliente(int Id_Cliente, int Id_CNatural, int Id_CJuridico, int Id_TipoCliente, String nombre, String documento, String Direccion, String Telefono) {
        this.Id_Cliente = Id_Cliente;
        this.Id_CNatural = Id_CNatural;
        this.Id_CJuridico = Id_CJuridico;
        this.Id_TipoCliente = Id_TipoCliente;
        this.nombre = nombre;
        this.documento = documento;
        this.Direccion = Direccion;
        this.Telefono = Telefono;
    }

    public int getId_Cliente() {
        return Id_Cliente;
    }

    public void setId_Cliente(int Id_Cliente) {
        this.Id_Cliente = Id_Cliente;
    }

    // Getters y Setters para nombre y documento (ya los tenías, solo los añado para completar)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    // Getters y Setters para los NUEVOS atributos id_TipoCliente, id_CNatural, id_CJuridico
    public int getId_TipoCliente() {
        return Id_TipoCliente;
    }

    public void setId_TipoCliente(int Id_TipoCliente) {
        this.Id_TipoCliente = Id_TipoCliente;
    }

    public int getId_CNatural() {
        return Id_CNatural;
    }

    public void setId_CNatural(int Id_CNatural) {
        this.Id_CNatural = Id_CNatural;
    }

    public int getId_CJuridico() {
        return Id_CJuridico;
    }

    public void setId_CJuridico(int Id_CJuridico) {
        this.Id_CJuridico = Id_CJuridico;
    }

    public Tipo_Cliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(Tipo_Cliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public Cliente_Natural getClienteNatural() {
        return clienteNatural;
    }

    public void setClienteNatural(Cliente_Natural clienteNatural) {
        this.clienteNatural = clienteNatural;
    }

    public Cliente_Juridico getClienteJuridico() {
        return clienteJuridico;
    }

    public void setClienteJuridico(Cliente_Juridico clienteJuridico) {
        this.clienteJuridico = clienteJuridico;
    }

    // Optional: Methods to get the consolidated name and DNI/RUC
    public String getNombreORazonSocial() {
        if (clienteNatural != null) {
            return clienteNatural.getNombre();
        } else if (clienteJuridico != null) {
            return clienteJuridico.getRaz_Social();
        }
        return null; // Or throw an exception, depending on your error handling
    }

    public String getDniORuc() {
        if (clienteNatural != null) {
            return clienteNatural.getDNI();
        } else if (clienteJuridico != null) {
            return clienteJuridico.getRUC();
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cliente cliente = (Cliente) o;
        return Id_Cliente == cliente.Id_Cliente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id_Cliente);
    }

    public String getNombreMostrable() {
        if (clienteNatural != null) {
            return clienteNatural.getNombre();
        } else if (clienteJuridico != null) {
            return clienteJuridico.getRaz_Social();
        } else {
            return "Cliente sin nombre";
        }
    }

    public String getDocumentoMostrable() {
        if (clienteNatural != null) {
            return clienteNatural.getDNI();
        } else if (clienteJuridico != null) {
            return clienteJuridico.getRUC();
        } else {
            return "-";
        }
    }
}

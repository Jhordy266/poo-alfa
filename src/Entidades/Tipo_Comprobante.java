/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author leona
 */
public class Tipo_Comprobante {

    private int Id_TipoComprobante;
    private String Tipo;
    private String serie;
    private String numero;

    public Tipo_Comprobante() {
    }

    public Tipo_Comprobante(int Id_TipoComprobante, String Tipo, String serie, String numero) {
        this.Id_TipoComprobante = Id_TipoComprobante;
        this.Tipo = Tipo;
        this.serie = serie;
        this.numero = numero;
    }

    public Tipo_Comprobante(int Id_TipoComprobante, String Tipo) {
        this.Id_TipoComprobante = Id_TipoComprobante;
        this.Tipo = Tipo;
    }
    
    

    

    public int getId_TipoComprobante() {
        return Id_TipoComprobante;
    }

    public void setId_TipoComprobante(int Id_TipoComprobante) {
        this.Id_TipoComprobante = Id_TipoComprobante;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    @Override
    public String toString() {
        return Tipo; 
    }

}

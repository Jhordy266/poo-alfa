/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author leona
 */
public class Cliente_Juridico {
    private int Id_CJuridico;
    private String Raz_Social;
    private String RUC;

    public Cliente_Juridico() {
    }

    public Cliente_Juridico(int Id_CJuridico, String Raz_Social, String RUC) {
        this.Id_CJuridico = Id_CJuridico;
        this.Raz_Social = Raz_Social;
        this.RUC = RUC;
    }

    public int getId_CJuridico() {
        return Id_CJuridico;
    }

    public void setId_CJuridico(int Id_CJuridico) {
        this.Id_CJuridico = Id_CJuridico;
    }

    public String getRaz_Social() {
        return Raz_Social;
    }

    public void setRaz_Social(String Raz_Social) {
        this.Raz_Social = Raz_Social;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }
    
    
    
    
}

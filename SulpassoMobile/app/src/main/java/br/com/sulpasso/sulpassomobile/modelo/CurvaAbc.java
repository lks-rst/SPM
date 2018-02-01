package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 08/11/2016 - 11:49 as part of the project SulpassoMobile.
 */
public class CurvaAbc
{
    private Cliente cliente;
    private float peso_1;
    private float peso_2;
    private float fat_1;
    private float fat_2;
    private float cont_1;
    private float cont_2;

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public float getPeso_1() { return peso_1; }

    public void setPeso_1(float peso_1) { this.peso_1 = peso_1; }

    public float getPeso_2() { return peso_2; }

    public void setPeso_2(float peso_2) { this.peso_2 = peso_2; }

    public float getFat_1() { return fat_1; }

    public void setFat_1(float fat_1) { this.fat_1 = fat_1; }

    public float getFat_2() { return fat_2; }

    public void setFat_2(float fat_2) { this.fat_2 = fat_2; }

    public float getCont_1() { return cont_1; }

    public void setCont_1(float cont_1) { this.cont_1 = cont_1; }

    public float getCont_2() { return cont_2; }

    public void setCont_2(float cont_2) { this.cont_2 = cont_2; }
}
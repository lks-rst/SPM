package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 08/11/2016 - 13:44 as part of the project SulpassoMobile.
 */
public class PrecosClientes
{
    private int cliente;
    private int produto;
    private float valor;

    public int getCliente() { return cliente; }

    public void setCliente(int cliente) { this.cliente = cliente; }

    public int getProduto() { return produto; }

    public void setProduto(int produto) { this.produto = produto; }

    public float getValor() { return valor; }

    public void setValor(float valor) { this.valor = valor; }
}
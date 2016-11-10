package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

import java.util.ArrayList;

/**
 * Created by Lucas on 08/11/2016 - 09:56 as part of the project SulpassoMobile.
 */
public class PrePedido
{
    private Cliente cliente;
    private ArrayList<ItensVendidos> itensDevolvidos;
    private String data;

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public ArrayList<ItensVendidos> getItensDevolvidos() { return itensDevolvidos; }

    public void setItensDevolvidos(ArrayList<ItensVendidos> itensDevolvidos)
    {
        this.itensDevolvidos = itensDevolvidos;
    }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }
}

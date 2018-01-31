package br.com.sulpasso.sulpassomobile.modelo;

import java.util.ArrayList;

/**
 * Created by Lucas on 08/11/2016 - 09:56 as part of the project SulpassoMobile.
 */
public class PrePedido
{
    private Cliente cliente;
    private ArrayList<PrePedidoItem> itensDevolvidos;
    private String data;

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public ArrayList<PrePedidoItem> getItensVendidos() { return itensDevolvidos; }

    public void setItensPrePedido(ArrayList<PrePedidoItem> itensDevolvidos)
    {
        this.itensDevolvidos = itensDevolvidos;
    }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }
}
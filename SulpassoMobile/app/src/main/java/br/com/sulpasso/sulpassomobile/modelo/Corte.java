package br.com.sulpasso.sulpassomobile.modelo;

import java.util.ArrayList;

/**
 * Created by Lucas on 08/11/2016 - 11:55 as part of the project SulpassoMobile.
 */
public class Corte
{
    private Cliente cliente;
    private ArrayList<ItensVendidos> itensCortados;
    private String data;

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public ArrayList<ItensVendidos> getItensCortados() { return itensCortados; }

    public void setItensCortados(ArrayList<ItensVendidos> itensCortados) { this.itensCortados = itensCortados; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }
}
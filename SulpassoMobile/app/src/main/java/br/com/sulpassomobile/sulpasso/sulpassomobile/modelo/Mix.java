package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

import java.util.ArrayList;

/**
 * Created by Lucas on 08/11/2016 - 13:36 as part of the project SulpassoMobile.
 */
public class Mix
{
    private Tipologia tipologia;
    private ArrayList<Item> itens;
    private String tipo;

    public Tipologia getTipologia() { return tipologia; }

    public void setTipologia(Tipologia tipologia) { this.tipologia = tipologia; }

    public ArrayList<Item> getItens() { return itens; }

    public void setItens(ArrayList<Item> itens) { this.itens = itens; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }
}
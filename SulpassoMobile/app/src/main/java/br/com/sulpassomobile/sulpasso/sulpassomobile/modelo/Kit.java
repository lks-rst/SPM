package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

import java.util.ArrayList;

/**
 * Created by Lucas on 08/11/2016 - 11:43 as part of the project SulpassoMobile.
 */
public class Kit
{
    private String kit;
    private ArrayList<Item> itens;
    private String descricao;
    private float quantidade;
    private float valor;

    public String getKit() { return kit; }

    public void setKit(String kit) { this.kit = kit; }

    public ArrayList<Item> getItens() { return itens; }

    public void setItens(ArrayList<Item> itens) { this.itens = itens; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public float getQuantidade() { return quantidade; }

    public void setQuantidade(float quantidade) { this.quantidade = quantidade; }

    public float getValor() { return valor; }

    public void setValor(float valor) { this.valor = valor; }
}
package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

import java.util.List;

/**
 * Created by Lucas on 10/10/2016.
 */
public class CampanhaProduto
{
    private int codigo;
    private int quantidade;
    private float desconto;
    private List<Integer> itens;

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public float getDesconto() { return desconto; }

    public void setDesconto(float desconto) { this.desconto = desconto; }

    public List<Integer> getItens() { return itens; }

    public void setItens(List<Integer> itens) { this.itens = itens; }
}

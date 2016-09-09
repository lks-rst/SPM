package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import java.util.HashMap;

import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ItensVendidos;

/**
 * Created by Lucas on 01/08/2016.
 */
public class InserirItemPedidos
{
    private String configuracaoVendaItem;
    private HashMap<String, String> dadosVendaItem;
    private float valor;
    private float desconto;
    private int quantidade;
    private float acrescimo;

    private Item item;

    public InserirItemPedidos(String configuracaoVendaItem)
    {
        this.configuracaoVendaItem = configuracaoVendaItem;
    }

    public void setValor(float valor) { this.valor = valor; }

    public void setDesconto(float desconto) { this.desconto = desconto; }

    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public void setAcrescimo(float acrescimo) { this.acrescimo = acrescimo; }

    public void setDadosVendaItem(HashMap<String, String>  dadosVendaItem)
    {
        this.dadosVendaItem = dadosVendaItem;
    }

    public void setItem(Item item) { this.item = item; }

    public Item getItem() { return item; }

    public String getValor() { return this.buscarDadosVendaItem(1); }

    public float calcularTotal()
    {
        return (this.valor
                - (this.valor * (this.desconto / 100))
                + (this.valor * (this.acrescimo / 100)))
                * this.quantidade ;
    }

    public ItensVendidos confirmarItem()
    {
        if(this.verificarQuantidade())
            if(this.verificarDesconto())
                if(this.verificarValor())
                {
                    ItensVendidos item = new ItensVendidos();
                    item.setItem(this.item.getCodigo());
                    item.setQuantidade(this.quantidade);
                    item.setValorTabela(Float.parseFloat(this.getValor()));
                    item.setValorLiquido(this.valor);
                    item.setDesconto(this.desconto);
                    item.setTotal(this.calcularTotal());
                    return item;
                }
                else
                    return null;
            else
                return null;
        else
            return null;
    }

    private String buscarDadosVendaItem(int dado)
    {
        switch (dado)
        {
            case 1 :
                return this.dadosVendaItem.get("TABELA");
            default :
                return "--";
        }
    }

    private Boolean verificarValor() {
        return true;
    }

    private Boolean verificarQuantidade() {
        return true;
    }

    private Boolean verificarDesconto() {
        return true;
    }

    private void calcularFlex() { /*****/ }
}
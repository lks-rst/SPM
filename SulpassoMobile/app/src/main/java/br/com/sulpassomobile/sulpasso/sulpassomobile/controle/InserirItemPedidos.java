package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.HashMap;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;

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

    public Boolean temMinimo()
    {
        return Float.parseFloat(this.dadosVendaItem.get("MINIMO")) > 0 ? true : false;
    }

    public Boolean temPromocao()
    {
        return Boolean.parseBoolean(this.dadosVendaItem.get("PROMOCAO"));
    }

    public String getValor() { return this.buscarDadosVendaItem(1); }

    public String buscarMinimo() { return this.buscarDadosVendaItem(2); }

    public String getQtdMinimaVenda() { return this.buscarDadosVendaItem(3); }

    public String getUnidade() { return this.buscarDadosVendaItem(4); }

    public String getUnidadeVenda() { return this.buscarDadosVendaItem(5); }

    public float calcularTotal()
    {
        return (this.valor
                - (this.valor * (this.desconto / 100))
                + (this.valor * (this.acrescimo / 100)))
                * this.quantidade ;
    }

    public float diferencaFlex(Context ctx)
    {
        float minimo = Float.parseFloat(this.buscarDadosVendaItem(2));
        float minimoPromocional = this.verificarPromocoes(ctx);
        float tabela = Float.parseFloat(this.buscarDadosVendaItem(1));
        float diferenca = 0;

        minimo = minimoPromocional > 0 ?
                (minimo < minimoPromocional ? minimo : minimoPromocional) : minimo;
        minimo = minimo < tabela ? minimo : tabela;

        diferenca = minimo - this.valor;

        return diferenca;
    }

    public ItensVendidos confirmarItem(float desconto, boolean percentual)
    {
        if(this.verificarQuantidade())
            if(this.verificarDesconto(desconto, percentual))
                if(this.verificarValor(desconto, percentual))
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
            case 2 :
                return this.dadosVendaItem.get("MINIMO");
            case 3 :
                return this.dadosVendaItem.get("QTDMINIMA");
            case 4 :
                return this.dadosVendaItem.get("UNIDADE");
            case 5 :
                return this.dadosVendaItem.get("UNVENDA");
            default :
                return "--";
        }
    }

    private Boolean verificarValor(float desconto, boolean percentual) {
        if(percentual)
        {
            float valordesconto;
            float valorTabela = Float.parseFloat(buscarDadosVendaItem(1));

            valordesconto = valorTabela - (valorTabela * desconto / 100);

            if(this.valor < valordesconto)
                return false;
            else
                return true;
        }
        else
            return true;
    }

    private Boolean verificarQuantidade()
    {
        if(quantidade % Integer.parseInt(this.buscarDadosVendaItem(3)) == 0)
            return true;
        else
            return false;
    }

    private Boolean verificarDesconto(float desconto, boolean percentual)
    {
        if(!percentual)
            if(this.desconto < desconto)
                return true;
            else
                return false;
        else
            return true;
    }

    private void calcularFlex() { /*****/ }

    private float verificarPromocoes(Context ctx)
    {
        PromocaoDataAccess pda = new PromocaoDataAccess(ctx);
        Promocao p = null;

        try { p = pda.buscarPromocao(this.item.getCodigo(), this.quantidade); }
        catch (GenercicException e) { e.printStackTrace(); }

        if(p != null) { return p.getValor(); }
        else { return -1; }
    }
}
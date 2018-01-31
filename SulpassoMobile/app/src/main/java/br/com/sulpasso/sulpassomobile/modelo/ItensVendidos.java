package br.com.sulpasso.sulpassomobile.modelo;

import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 17/08/2016.
 */
public class ItensVendidos
{
    private int item;
    private float valorTabela;
    private float valorDigitado;
    private float quantidade;
    private float descontoCG;
    private float descontoCP;
    private float desconto;
    private float valorLiquido;
    private float flex;
    private float total;

    public int getItem() { return item; }

    public void setItem(int item) { this.item = item; }

    public float getValorTabela() { return valorTabela; }

    public void setValorTabela(float valorTabela) { this.valorTabela = valorTabela; }

    public float getValorDigitado() { return valorDigitado; }

    public void setValorDigitado(float valorDigitado) { this.valorDigitado = valorDigitado; }

    public float getQuantidade() { return quantidade; }

    public void setQuantidade(float quantidade) { this.quantidade = quantidade; }

    public float getDescontoCG() { return descontoCG; }

    public void setDescontoCG(float descontoCG) { this.descontoCG = descontoCG; }

    public float getDescontoCP() { return descontoCP; }

    public void setDescontoCP(float descontoCP) { this.descontoCP = descontoCP; }

    public float getDesconto() { return desconto; }

    public void setDesconto(float desconto) { this.desconto = desconto; }

    public float getValorLiquido() { return valorLiquido; }

    public void setValorLiquido(float valorLiquido) { this.valorLiquido = valorLiquido; }

    public float getFlex() { return flex; }

    public void setFlex(float flex) { this.flex = flex; }

    public float getTotal() { return total; }

    public void setTotal(float total) { this.total = total; }

    @Override
    public String toString()
    {
        return "ItensVendidos{" +
            "item=" + item +
            ", valorTabela=" + valorTabela +
            ", valorDigitado=" + valorDigitado +
            ", quantidade=" + quantidade +
            ", descontoCG=" + descontoCG +
            ", descontoCP=" + descontoCP +
            ", desconto=" + desconto +
            ", valorLiquido=" + valorLiquido +
            ", flex=" + flex +
            ", total=" + total +
            '}';
    }

    public String toDisplay() {
        ManipulacaoStrings ms = new ManipulacaoStrings();

        return /*item +*/
            " - V.:" + ms.comEsquerda(String.valueOf(valorTabela), "0", 8) +
            " - Q.:" + ms.comEsquerda(String.valueOf(quantidade), "0", 6) +
            /*" - " + descontoCG +*/
            " - T.:" + ms.comEsquerda(String.valueOf(total), "0", 8);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItensVendidos that = (ItensVendidos) o;

        return item == that.item;
    }

    @Override
    public int hashCode() { return item; }
}

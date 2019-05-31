package br.com.sulpasso.sulpassomobile.modelo;

import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;
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
    private boolean digitadoSenha;
    private boolean descontoCampanha;
    private float valorMinimo;
    private float quantidadeEspecifica;

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

    public boolean isDigitadoSenha() { return digitadoSenha; }

    public void setDigitadoSenha(boolean digitadoSenha) { this.digitadoSenha = digitadoSenha; }

    public float getValorMinimo() { return valorMinimo; }

    public void setValorMinimo(float valorMinimo) { this.valorMinimo = valorMinimo; }

    public boolean isDescontoCampanha() { return descontoCampanha; }

    public void setDescontoCampanha(boolean descontoCampanha) { this.descontoCampanha = descontoCampanha; }

    public float getQuantidadeEspecifica() { return quantidadeEspecifica; }

    public void setQuantidadeEspecifica(float quantidadeEspecifica) { this.quantidadeEspecifica = quantidadeEspecifica; }

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

    public String toDisplay()
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();

        return /*item +*/
            "\nV.:" + ms.comEsquerda(Formatacao.format2d(valorDigitado), " ", 8) +
            " - Q.:" + ms.comEsquerda(Formatacao.format2d(quantidade), " ", 8) +
            /*" - " + descontoCG +*/
            " - T.:" + ms.comEsquerda(Formatacao.format2d(total), " ", 8);
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

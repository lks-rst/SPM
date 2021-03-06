package br.com.sulpasso.sulpassomobile.modelo;

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
    private List<String> itensDesc;
    private float descontoAplicado;
    private float quantidadeVendida;

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public float getDesconto() { return desconto; }

    public void setDesconto(float desconto) { this.desconto = desconto; }

    public List<Integer> getItens() { return itens; }

    public void setItens(List<Integer> itens) { this.itens = itens; }

    public float getDescontoAplicado() { return descontoAplicado; }

    public void setDescontoAplicado(float descontoAplicado) { this.descontoAplicado = descontoAplicado; }

    public float getQuantidadeVendida() { return quantidadeVendida; }

    public void setQuantidadeVendida(float quantidadeVendida) { this.quantidadeVendida = quantidadeVendida; }

    public List<String> getItensDesc() { return itensDesc; }

    public void setItensDesc(List<String> itensDesc) { this.itensDesc = itensDesc; }

    @Override
    public String toString()
    {
        return "CampanhaProduto{" +
                "codigo=" + codigo +
                ", quantidade=" + quantidade +
                ", desconto=" + desconto +
                ", itens=" + itens +
                '}';
    }

    public String toDisplay()
    {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.delete(0, sBuilder.length());

        for(Integer i : this.itens)
        {
            sBuilder.append(codigo);
            sBuilder.append(" - ");
            sBuilder.append(i);
            sBuilder.append(" - ");
            sBuilder.append(itensDesc.get(this.itens.indexOf(i)));
            sBuilder.append(" -\n ");
            sBuilder.append(" Qtd.:");
            sBuilder.append(quantidade);
            sBuilder.append(" Desc.:");
            sBuilder.append(desconto);
        }

        return sBuilder.toString();
    }
}
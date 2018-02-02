package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Estoque
{
    private Integer codigoProduto;
    private Double estoque;
    private Integer loja;

    public Integer getCodigoProduto() { return codigoProduto; }

    public void setCodigoProduto(Integer codigoProduto) { this.codigoProduto = codigoProduto; }

    public Double getEstoque() { return estoque; }

    public void setEstoque(Double estoque) { this.estoque = estoque; }

    public Integer getLoja() { return loja; }

    public void setLoja(Integer loja) { this.loja = loja; }

    @Override
    public String toString()
    {
        return "Estoque{" +
                "codigoProduto=" + codigoProduto +
                ", estoque=" + estoque +
                ", loja=" + loja +
                '}';
    }
}

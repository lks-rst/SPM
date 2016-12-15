package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Preco
{
    private Integer codigoItem;
    private Integer tabelaPrecos;
    private Double preco;

    public Integer getCodigoItem() { return codigoItem; }

    public void setCodigoItem(Integer codigoItem) { this.codigoItem = codigoItem; }

    public Integer getTabelaPrecos() { return tabelaPrecos; }

    public void setTabelaPrecos(Integer tabelaPrecos) { this.tabelaPrecos = tabelaPrecos; }

    public Double getPreco() { return preco; }

    public void setPreco(Double preco) { this.preco = preco; }

    @Override
    public String toString()
    {
        return "Preco{" +
                "codigoItem=" + codigoItem +
                ", tabelaPrecos=" + tabelaPrecos +
                ", preco=" + preco +
                '}';
    }

    public String toDisplay()
    {
        return "Preço{ Tab.:" +  tabelaPrecos + " Val.: " + preco + '}';
    }
}

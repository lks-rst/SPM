package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 15/09/2016.
 */
public class Promocao
{
    private int item;
    private float quantidade;
    private float valor;

    public float getQuantidade() { return quantidade; }

    public void setQuantidade(float quantidade) { this.quantidade = quantidade; }

    public int getItem() { return item; }

    public void setItem(int item) { this.item = item; }

    public float getValor() { return valor; }

    public void setValor(float valor) { this.valor = valor; }

    @Override
    public String toString()
    {
        return "Promocao{" +
                "quantidade=" + quantidade +
                ", item=" + item +
                ", valor=" + valor +
                '}';
    }

    public String toDisplay()
    {
        return "Promocao{" + quantidade + " - " + valor + "}";
    }
}

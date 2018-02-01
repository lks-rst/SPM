package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 17/11/2017 - 10:22 as part of the project SulpassoMobile.
 */
public class PrePedidoItem
{
    private int item;
    private float valorDigitado;
    private float quantidade;
    private float total;
    private String descricao;
    private String data;


    public int getItem() { return item; }

    public void setItem(int item) { this.item = item; }

    public float getValorDigitado() { return valorDigitado; }

    public void setValorDigitado(float valorDigitado) { this.valorDigitado = valorDigitado; }

    public float getQuantidade() { return quantidade; }

    public void setQuantidade(float quantidade) { this.quantidade = quantidade; }

    public float getTotal() { return total; }

    public void setTotal(float total) { this.total = total; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    @Override
    public String toString() {
        return "PrePedidoItem{" +
                "item=" + item +
                ", valorDigitado=" + valorDigitado +
                ", quantidade=" + quantidade +
                ", total=" + total +
                ", descricao='" + descricao + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public String toDisplay() {
        return item +
                " - " + descricao +
                "\n -V. " + valorDigitado +
                " Q. " + quantidade +
                " T. " + total +
                " D. " + data;
    }
}

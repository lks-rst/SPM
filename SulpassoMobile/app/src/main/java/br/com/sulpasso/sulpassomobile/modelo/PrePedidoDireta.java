package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 08/11/2016 - 11:06 as part of the project SulpassoMobile.
 */
public class PrePedidoDireta
{
    private int cliente;
    private String tipo;
    private int pedido;
    private int nota;
    private int produto;
    private int quantidade_s;
    private int quantidade_e;
    private float valor_s;
    private float valor_e;
    private String data_nota;

    public int getCliente() { return cliente; }

    public void setCliente(int cliente) { this.cliente = cliente; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getPedido() { return pedido; }

    public void setPedido(int pedido) { this.pedido = pedido; }

    public int getNota() { return nota; }

    public void setNota(int nota) { this.nota = nota; }

    public int getProduto() { return produto; }

    public void setProduto(int produto) { this.produto = produto; }

    public int getQuantidade_s() { return quantidade_s; }

    public void setQuantidade_s(int quantidade_s) { this.quantidade_s = quantidade_s; }

    public int getQuantidade_e() { return quantidade_e; }

    public void setQuantidade_e(int quantidade_e) { this.quantidade_e = quantidade_e; }

    public float getValor_s() { return valor_s; }

    public void setValor_s(float valor_s) { this.valor_s = valor_s; }

    public float getValor_e() { return valor_e; }

    public void setValor_e(float valor_e) { this.valor_e = valor_e; }

    public String getData_nota() { return data_nota; }

    public void setData_nota(String data_nota) { this.data_nota = data_nota; }
}
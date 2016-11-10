package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 08/11/2016 - 13:50 as part of the project SulpassoMobile.
 */
public class Status
{
    private int status;
    private int pedido;
    private int numero;
    private String data;

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public int getPedido() { return pedido; }

    public void setPedido(int pedido) { this.pedido = pedido; }

    public int getNumero() { return numero; }

    public void setNumero(int numero) { this.numero = numero; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }
}
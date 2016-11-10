package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 08/11/2016 - 11:34 as part of the project SulpassoMobile.
 */
public class Gravosos
{
    private Item item;
    private float quantidade;
    private String fabricacao;
    private String validade;
    private int dias;

    public Item getItem() { return item; }

    public void setItem(Item item) { this.item = item; }

    public float getQuantidade() { return quantidade; }

    public void setQuantidade(float quantidade) { this.quantidade = quantidade; }

    public String getFabricacao() { return fabricacao; }

    public void setFabricacao(String fabricacao) { this.fabricacao = fabricacao; }

    public String getValidade() { return validade; }

    public void setValidade(String validade) { this.validade = validade; }

    public int getDias() { return dias; }

    public void setDias(int dias) { this.dias = dias; }
}
package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 10/10/2016.
 */
public class TiposVenda
{
    private String referencia;
    private String descricao;
    private float minimo;

    public String getReferencia() { return referencia; }

    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public float getMinimo() { return minimo; }

    public void setMinimo(float minimo) { this.minimo = minimo; }
}

package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Natureza
{
    private int codigo;
    private String descricao;
    private float minimo;
    private String prazo;
    private int banco;
    private char venda;

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public float getMinimo() { return minimo; }

    public void setMinimo(float minimo) { this.minimo = minimo; }

    public String getPrazo() { return prazo; }

    public void setPrazo(String prazo) { this.prazo = prazo; }

    public int getBanco() { return banco; }

    public void setBanco(int banco) { this.banco = banco; }

    public char getVenda() { return venda; }

    public void setVenda(char venda) { this.venda = venda; }

    @Override
    public String toString()
    {
        return "Natureza{" +
                "codigo=" + codigo +
                ", descricao='" + descricao + '\'' +
                ", minimo=" + minimo +
                ", prazo='" + prazo + '\'' +
                ", banco=" + banco +
                ", venda=" + venda +
                '}';
    }

    public String toDisplay()
    {
        return codigo + " - " + descricao + " - " + minimo;
    }
}

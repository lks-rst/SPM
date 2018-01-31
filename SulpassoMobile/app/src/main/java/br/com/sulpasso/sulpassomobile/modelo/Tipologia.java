package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 08/11/2016 - 11:27 as part of the project SulpassoMobile.
 */
public class Tipologia
{
    private int tipologia;
    private String descricao;

    public int getTipologia() { return tipologia; }

    public void setTipologia(int tipologia) { this.tipologia = tipologia; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return "Tipologia{" +
                "tipologia=" + tipologia +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    public String toDisplay() { return tipologia + " - " + descricao; }
}
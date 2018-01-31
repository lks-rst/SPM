package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 10/10/2016.
 */
public class Grupo
{
    private int grupo;
    private int subGrupo;
    private int divisao;
    private String descricao;

    public int getGrupo() { return grupo; }

    public void setGrupo(int grupo) { this.grupo = grupo; }

    public int getSubGrupo() { return subGrupo; }

    public void setSubGrupo(int subGrupo) { this.subGrupo = subGrupo; }

    public int getDivisao() { return divisao; }

    public void setDivisao(int divisao) { this.divisao = divisao; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString()
    {
        return "Grupo{" +
                "grupo=" + grupo +
                ", subGrupo=" + subGrupo +
                ", divisao=" + divisao +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    public String toDisplay()
    {
        return  grupo +
                " - " + subGrupo +
                " - " + divisao +
                " - " + descricao;
    }
}
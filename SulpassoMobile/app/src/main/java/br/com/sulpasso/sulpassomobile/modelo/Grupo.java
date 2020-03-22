package br.com.sulpasso.sulpassomobile.modelo;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grupo)) return false;

        Grupo grupo1 = (Grupo) o;

        if (getGrupo() != grupo1.getGrupo()) return false;
        if (getSubGrupo() != grupo1.getSubGrupo()) return false;
        return getDivisao() == grupo1.getDivisao();

    }

    @Override
    public int hashCode() {
        int result = getGrupo();
        result = 31 * result + getSubGrupo();
        result = 31 * result + getDivisao();
        return result;
    }
}
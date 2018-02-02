package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Prazo
{
    private int codigo;
    private String desdobramento;
    private int tabela;

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getDesdobramento() { return desdobramento; }

    public void setDesdobramento(String desdobramento) { this.desdobramento = desdobramento; }

    public int getTabela() { return tabela; }

    public void setTabela(int tabela) { this.tabela = tabela; }

    @Override
    public String toString()
    {
        return "Prazo{" +
                "codigo=" + codigo +
                ", desdobramento='" + desdobramento + '\'' +
                ", tabela=" + tabela +
                '}';
    }

    public String toDisplay() { return codigo + " - " + desdobramento + " - " + tabela; }
}

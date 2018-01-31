package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 10/10/2016.
 */
public class Banco
{
    private int codigo;
    private String banco;

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getBanco() { return banco; }

    public void setBanco(String banco) { this.banco = banco; }

    @Override
    public String toString() {
        return "Banco{" +
                "codigo=" + codigo +
                ", banco='" + banco + '\'' +
                '}';
    }

    public String toDisplay() { return codigo + " - " + banco; }
}

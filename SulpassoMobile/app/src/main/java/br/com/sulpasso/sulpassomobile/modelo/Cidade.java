package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 10/10/2016.
 */
public class Cidade
{
    private int codigo;
    private String nome;
    private String uf;
    private String cep;

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getUf() { return uf; }

    public void setUf(String uf) { this.uf = uf; }

    public String getCep() { return cep; }

    public void setCep(String cep) { this.cep = cep; }

    @Override
    public String toString() {
        return "Cidade{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", uf='" + uf + '\'' +
                ", cep='" + cep + '\'' +
                '}';
    }

    public String toDisplay() { return codigo + " - " + nome + " - " + cep; }
}

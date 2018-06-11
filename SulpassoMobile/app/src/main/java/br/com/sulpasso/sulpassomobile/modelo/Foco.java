package br.com.sulpasso.sulpassomobile.modelo;

import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 06/06/2018 - 15:40 as part of the project SulpassoMobile.
 */
public class Foco
{
    private int codigo;
    private String referencia;
    private String descricao;
    private String complemento;
    private int clientes;
    private float valor;
    private float volume;
    private float contribuicao;


    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getReferencia() { return referencia; }

    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getComplemento() { return complemento; }

    public void setComplemento(String complemento) { this.complemento = complemento; }

    public int getClientes() { return clientes; }

    public void setClientes(int clientes) { this.clientes = clientes; }

    public float getValor() { return valor; }

    public void setValor(float valor) { this.valor = valor; }

    public float getVolume() { return volume; }

    public void setVolume(float volume) { this.volume = volume; }

    public float getContribuicao() { return contribuicao; }

    public void setContribuicao(float contribuicao) { this.contribuicao = contribuicao; }

    @Override
    public String toString() {
        return "Foco{" +
                "codigo=" + codigo +
                ", referencia='" + referencia + '\'' +
                ", descricao='" + descricao + '\'' +
                ", complemento='" + complemento + '\'' +
                ", clientes=" + clientes +
                ", valor=" + valor +
                ", volume=" + volume +
                ", contribuicao=" + contribuicao +
                '}';
    }

    public String toDisplay() {
        ManipulacaoStrings ms = new ManipulacaoStrings();

        return ms.comEsquerda(String.valueOf(codigo), " ", 7) +
                ms.comEsquerda(referencia, " ", 10) +
                ms.comEsquerda(descricao, " ", 30) +
                ms.comEsquerda(complemento, " ", 14) +
                "\n" +
                ms.comDireita(" Cl. " + clientes, " ", 10) +
                ms.comDireita(" Va. " + valor, " ", 10) +
                ms.comDireita(" Vo. " + volume, " ", 10) +
                ms.comDireita(" Co. " + contribuicao, " ", 10);
    }
}
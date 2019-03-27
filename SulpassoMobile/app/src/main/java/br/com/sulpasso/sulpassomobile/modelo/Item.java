package br.com.sulpasso.sulpassomobile.modelo;

import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Item
{
    private Integer codigo;
    private String barras;
    private String descricao;
    private String referencia;
    private String complemento;
    private String unidade;
    private String unidadeVenda;
    private Double desconto;
    private Integer minimoVenda;
    private String flex;
    private Integer quantidadeCaixa;
    private float contribuicao;
    private float peso;
    private float pesoCd;
    /* Tributação foi ignorada */
    private float faixa;
    private Integer grupo;
    private Integer subGrupo;
    private Integer divisao;

    private float custo;
    private String tvd;

    private float estoque;

    private String aplicacao;

    private boolean destaque;

    public Integer getCodigo() { return codigo; }

    public void setCodigo(Integer codigo) { this.codigo = codigo; }

    public String getReferencia() { return referencia; }

    public void setReferencia(String referencia) { this.referencia = referencia; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getComplemento() { return complemento; }

    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getUnidade() { return unidade; }

    public void setUnidade(String unidade) { this.unidade = unidade; }

    public String getUnidadeVenda() { return unidadeVenda; }

    public void setUnidadeVenda(String unidadeVenda) { this.unidadeVenda = unidadeVenda; }

    public Integer getMinimoVenda() { return minimoVenda; }

    public void setMinimoVenda(Integer minimoVenda) { this.minimoVenda = minimoVenda; }

    public String getBarras() { return barras; }

    public void setBarras(String barras) { this.barras = barras; }

    public Double getDesconto() { return desconto; }

    public void setDesconto(Double desconto) { this.desconto = desconto; }

    public String getFlex() { return flex; }

    public void setFlex(String flex) { this.flex = flex; }

    public Integer getQuantidadeCaixa() { return quantidadeCaixa; }

    public void setQuantidadeCaixa(Integer quantidadeCaixa) { this.quantidadeCaixa = quantidadeCaixa; }

    public float getContribuicao() { return contribuicao; }

    public void setContribuicao(float contribuicao) { this.contribuicao = contribuicao; }

    public float getPeso() { return peso; }

    public void setPeso(float peso) { this.peso = peso; }

    public float getPesoCd() { return pesoCd; }

    public void setPesoCd(float pesoCd) { this.pesoCd = pesoCd; }

    public float getFaixa() { return faixa; }

    public void setFaixa(float faixa) { this.faixa = faixa; }

    public Integer getGrupo() { return grupo; }

    public void setGrupo(Integer grupo) { this.grupo = grupo; }

    public Integer getSubGrupo() { return subGrupo; }

    public void setSubGrupo(Integer subGrupo) { this.subGrupo = subGrupo; }

    public Integer getDivisao() { return divisao; }

    public void setDivisao(Integer divisao) { this.divisao = divisao; }

    public float getCusto() { return custo; }

    public void setCusto(float custo) { this.custo = custo; }

    public String getTvd() { return tvd; }

    public void setTvd(String tvd) { this.tvd = tvd; }

    public float getEstoque() { return estoque; }

    public void setEstoque(float estoque) { this.estoque = estoque; }

    public String getAplicacao() { return aplicacao; }

    public void setAplicacao(String aplicacao) { this.aplicacao = aplicacao; }

    public boolean isDestaque() { return destaque; }

    public void setDestaque(boolean destaque) { this.destaque = destaque; }

    @Override
    public String toString() {
        return "Item{" +
                "codigo=" + codigo +
                ", barras='" + barras + '\'' +
                ", descricao='" + descricao + '\'' +
                ", referencia='" + referencia + '\'' +
                ", complemento='" + complemento + '\'' +
                ", unidade='" + unidade + '\'' +
                ", unidadeVenda='" + unidadeVenda + '\'' +
                ", desconto=" + desconto +
                ", minimoVenda=" + minimoVenda +
                ", flex='" + flex + '\'' +
                ", quantidadeCaixa=" + quantidadeCaixa +
                ", contribuicao=" + contribuicao +
                ", peso=" + peso +
                ", pesoCd=" + pesoCd +
                ", faixa=" + faixa +
                ", grupo=" + grupo +
                ", subGrupo=" + subGrupo +
                ", divisao=" + divisao +
                ", custo=" + custo +
                ", tvd='" + tvd + '\'' +
                ", estoque=" + estoque +
                ", aplicacao='" + aplicacao + '\'' +
                '}';
    }

    public String toDisplay()
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();
        return ms.comEsquerda(String.valueOf(codigo), " ", 10) + " - " +
                ms.comDireita(referencia, " ", 10) + " - " +
                ms.comDireita(descricao, " ", 30) + " - " +
                ms.comDireita(complemento, " ", 15);
    }
}

package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 05/12/2016 - 09:12 as part of the project SulpassoMobile.
 */
public class ConfiguradorUsuario
{
    /*
        TODO: Remover as inicializações dos dados após verificação;
     */
    private int codigo;
    private String nome;
    private String senha;
    private Boolean tipoFlex;
    private float valorFlex;
    private float pedidoMinimo;
    private float descontoCliente;
    private float descontoItem;
    private float descontoPedido;
    private float contribuicaoIdeal;
    private int tabelaMinimo;
    private int tabelaTroca;
    private int roteiro;
    private int tipoOredenacao;
    private int tipoBusca;
    private float saldo;
    private float comissao;
    private float contribuicao;

    public ConfiguradorUsuario()
    {
        /*
        this.codigo = 15;
        this.nome = "Lucas";
        this.senha = "-----";
        this.tipoFlex = false;
        this.valorFlex = 50;
        this.pedidoMinimo = 100;
        this.descontoCliente = 0;
        this.descontoItem = 10;
        this.descontoPedido = 0;
        this.contribuicaoIdeal = 100;
        this.tabelaMinimo = 50;
        this.tabelaTroca = 8;
        this.roteiro = 1;
        this.tipoOredenacao = 1;
        this.tipoBusca = 1;
        this.saldo = 600;
        this.comissao = 1000;
        this.contribuicao = 20;
        */
    }

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() { return senha; }

    public void setSenha(String senha) { this.senha = senha; }

    public Boolean getTipoFlex() { return tipoFlex; }

    public void setTipoFlex(Boolean tipoFlex) { this.tipoFlex = tipoFlex; }

    public float getValorFlex() { return valorFlex; }

    public void setValorFlex(float valorFlex) { this.valorFlex = valorFlex; }

    public float getPedidoMinimo() { return pedidoMinimo; }

    public void setPedidoMinimo(float pedidoMinimo) { this.pedidoMinimo = pedidoMinimo; }

    public float getDescontoCliente() { return descontoCliente; }

    public void setDescontoCliente(float descontoCliente) { this.descontoCliente = descontoCliente; }

    public float getDescontoItem() { return descontoItem; }

    public void setDescontoItem(float descontoItem) { this.descontoItem = descontoItem; }

    public float getDescontoPedido() { return descontoPedido; }

    public void setDescontoPedido(float descontoPedido) { this.descontoPedido = descontoPedido; }

    public float getContribuicaoIdeal() { return contribuicaoIdeal; }

    public void setContribuicaoIdeal(float contribuicaoIdeal) { this.contribuicaoIdeal = contribuicaoIdeal; }

    public int getTabelaMinimo() { return tabelaMinimo; }

    public void setTabelaMinimo(int tabelaMinimo) { this.tabelaMinimo = tabelaMinimo; }

    public int getTabelaTroca() { return tabelaTroca; }

    public void setTabelaTroca(int tabelaTroca) { this.tabelaTroca = tabelaTroca; }

    public int getRoteiro() { return roteiro; }

    public void setRoteiro(int roteiro) { this.roteiro = roteiro; }

    public int getTipoOredenacao() { return tipoOredenacao; }

    public void setTipoOredenacao(int tipoOredenacao) { this.tipoOredenacao = tipoOredenacao; }

    public int getTipoBusca() { return tipoBusca; }

    public void setTipoBusca(int tipoBusca) { this.tipoBusca = tipoBusca; }

    public float getSaldo() { return saldo; }

    public void setSaldo(float saldo) { this.saldo = saldo; }

    public float getComissao() { return comissao; }

    public void setComissao(float comissao) { this.comissao = comissao; }

    public float getContribuicao() { return contribuicao; }

    public void setContribuicao(float contribuicao) { this.contribuicao = contribuicao; }

    @Override
    public String toString() {
        return "{" +
                "\"codigo\":\"" + codigo +
                "\", \"nome\":\"" + nome + '"' +
                ", \"senha\":\"" + senha + '"' +
                ", \"tipoFlex\":\"" + tipoFlex +
                "\", \"valorFlex\":\"" + valorFlex +
                "\", \"pedidoMinimo\":\"" + pedidoMinimo +
                "\", \"descontoCliente\":\"" + descontoCliente +
                "\", \"descontoItem\":\"" + descontoItem +
                "\", \"descontoPedido\":\"" + descontoPedido +
                "\", \"contribuicaoIdeal\":\"" + contribuicaoIdeal +
                "\", \"tabelaMinimo\":\"" + tabelaMinimo +
                "\", \"tabelaTroca\":\"" + tabelaTroca +
                "\", \"roteiro\":\"" + roteiro +
                "\", \"tipoOredenacao\":\"" + tipoOredenacao +
                "\", \"tipoBusca\":\"" + tipoBusca +
                "\", \"saldo\":\"" + saldo +
                "\", \"comissao\":\"" + comissao +
                "\", \"contribuicao\":\"" + contribuicao +
                "\"}";
    }
}
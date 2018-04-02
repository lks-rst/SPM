package br.com.sulpasso.sulpassomobile.modelo;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Venda
{
    private Integer codigo;
    private Integer codigoCliente;
    private String data;
    private String hora;
    private String observacao;
    private Double desconto;
    private String observacaDesconto;
    private String observacaoNota;
    private Double valor;
    private Integer tabela;
    private Integer banco;
    private String tipo;
    private ArrayList<ItensVendidos> itens;
    private Cliente cliente;
    private Prazo prazo;
    private Integer natureza;
    private int enviado;

    public Venda() { this.itens = new ArrayList<>(); }

    public Integer getCodigo() { return codigo; }

    public void setCodigo(Integer codigo) { this.codigo = codigo; }

    public Integer getCodigoCliente() { return codigoCliente; }

    public void setCodigoCliente(Integer codigoCliente) { this.codigoCliente = codigoCliente; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public String getHora() { return hora; }

    public void setHora(String hora) { this.hora = hora; }

    public String getObservacao() { return observacao != null ? observacao : ""; }

    public void setObservacao(String observacao) { this.observacao = observacao; }

    public Double getDesconto() { return desconto; }

    public void setDesconto(Double desconto) { this.desconto = desconto; }

    public String getObservacaDesconto() { return observacaDesconto != null ? observacaDesconto : ""; }

    public void setObservacaDesconto(String observacaDesconto) { this.observacaDesconto = observacaDesconto; }

    public String getObservacaoNota() { return observacaoNota != null ? observacaoNota : ""; }

    public void setObservacaoNota(String observacaoNota) { this.observacaoNota = observacaoNota; }

    public Double getValor() { return valor; }

    public void setValor(Double valor) { this.valor = valor; }

    public Integer getTabela() { return tabela; }

    public void setTabela(Integer tabela) { this.tabela = tabela; }

    public ArrayList<ItensVendidos> getItens() { return itens; }

    public void setItens(ArrayList<ItensVendidos> itens) { this.itens = itens; }

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Integer getBanco() { return banco; }

    public void setBanco(Integer banco) { this.banco = banco; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public Prazo getPrazo() { return prazo; }

    public void setPrazo(Prazo prazo) { this.prazo = prazo; }

    public Integer getNatureza() { return natureza; }

    public void setNatureza(Integer natureza) { this.natureza = natureza; }

    public int getEnviado() { return enviado; }

    public void setEnviado(int enviado) { this.enviado = enviado; }

    @Override
    public String toString()
    {
        return "Venda{" +
                "codigo=" + codigo +
                ", codigoCliente=" + codigoCliente +
                ", data='" + data + '\'' +
                ", hora='" + hora + '\'' +
                ", observacao='" + observacao + '\'' +
                ", desconto=" + desconto +
                ", observacaDesconto='" + observacaDesconto + '\'' +
                ", observacaoNota='" + observacaoNota + '\'' +
                ", valor=" + valor +
                ", tabela=" + tabela +
                ", itens=" + itens +
                '}';
    }

    public String toDisplay()
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();

        return " " +
            ms.comEsquerda(String.valueOf(codigo), "0", 4) +
            " - " + ms.comEsquerda(String.valueOf(cliente.getCodigoCliente()), "0", 7) +
            " - " + ms.comDireita(cliente.getRazao().trim(), " ", 20) +
            " - " + data +
            " - V.: " + ms.comEsquerda(Formatacao.format2d(valor), "0", 8) +
            " - D.:" + Formatacao.format2d(desconto); //+
            //" - " + itens +
            //'}';
    }
    /*

    public void addItem(ItensVendidos item) { this.itens.add(item); }

    public void removeItem(int item)
    {
        int position = -1;

        for(ItensVendidos i : this.itens)
        {
            if (i.buscarItem() == item)
            {
                position = this.itens.indexOf(i);
                break;
            }
        }

        this.itens.remove(position);
    }

    public void alterItem(ItensVendidos item)
    {
        int position = -1;

        for(ItensVendidos i : this.itens)
        {
            if (i.buscarItem() == item.buscarItem())
            {
                position = this.itens.indexOf(i);
                break;
            }
        }

        this.itens.set(position, item);
    }

     */
}
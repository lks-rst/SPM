package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.HashMap;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;

/**
 * Created by Lucas on 01/08/2016.
 */
public class InserirItemPedidos
{
    private String configuracaoVendaItem;
    private HashMap<String, String> dadosVendaItem;
    private float valor;
    private float desconto;
    private float quantidade;
    private float acrescimo;

    private Item item;

    public InserirItemPedidos(String configuracaoVendaItem)
    {
        this.configuracaoVendaItem = configuracaoVendaItem;
    }

    public void setValor(float valor) { this.valor = valor; }

    public void setDesconto(float desconto) { this.desconto = desconto; }

    public void setQuantidade(float quantidade) { this.quantidade = quantidade; }

    public void setAcrescimo(float acrescimo) { this.acrescimo = acrescimo; }

    public void setDadosVendaItem(HashMap<String, String>  dadosVendaItem)
    {
        this.dadosVendaItem = dadosVendaItem;
    }

    public void setItem(Item item)
    {
        this.item = item;
        this.valor = 0;
        this.desconto = 0;
        this.acrescimo = 0;
        this.quantidade = 0;
    }

    public Item getItem() { return item; }

    public Boolean temMinimo()
    {
        return Float.parseFloat(this.dadosVendaItem.get("MINIMO")) > 0 ? true : false;
    }

    public Boolean temPromocao()
    {
        return Boolean.parseBoolean(this.dadosVendaItem.get("PROMOCAO"));
    }

    public String getValor() { return this.buscarDadosVendaItem(1); }

    public String buscarMinimo() { return this.buscarDadosVendaItem(2); }

    public String getQtdMinimaVenda() { return this.buscarDadosVendaItem(3); }

    public String getUnidade() { return this.buscarDadosVendaItem(4); }

    public String getUnidadeVenda() { return this.buscarDadosVendaItem(5); }

    public String getCodigoBarras() { return this.buscarDadosVendaItem(6); }

    public String getQtdCaixa() { return this.buscarDadosVendaItem(7); }

    public String getValorUnitario() { return this.buscarDadosVendaItem(8); }

    public float calcularTotal()
    {
        return (this.valor
                - (this.valor * (this.desconto / 100))
                + (this.valor * (this.acrescimo / 100)))
                * this.quantidade ;
    }

    public float calcularTotal(float quantidade, float valor, float desconto, float grupo, float produtos, float acrescimo)
    {
        return (valor
                - (valor * (desconto / 100))
                - (valor * (grupo/ 100))
                - (valor * (produtos / 100))
                + (valor * (acrescimo / 100)))
                * quantidade ;
    }

    public float diferencaFlex(Context ctx)
    {
        float minimo = Float.parseFloat(this.buscarDadosVendaItem(2));
        float minimoPromocional = this.verificarPromocoes(ctx);
        float tabela = Float.parseFloat(this.buscarDadosVendaItem(1));
        float diferenca = 0;

        minimo = minimoPromocional > 0 ?
                (minimo < minimoPromocional ? minimo : minimoPromocional) : minimo;
        minimo = (minimo > 0 && minimo < tabela) ? minimo : tabela;

        diferenca = minimo - this.valor;

        return diferenca;
    }

    public ItensVendidos confirmarItem(float desconto, boolean percentual, Context context)
    {
        if(this.verificarQuantidade())
            if(this.verificarDesconto(desconto, percentual, context))
                if(this.verificarValor(desconto, percentual, context))
                {
                    ItensVendidos item = new ItensVendidos();
                    item.setItem(this.item.getCodigo());
                    item.setQuantidade(this.quantidade);
                    item.setValorTabela(Float.parseFloat(this.getValor()));
                    item.setValorLiquido(this.valor);
                    item.setValorDigitado(this.valor);
                    item.setDesconto(this.desconto);
                    item.setTotal(this.calcularTotal());
                    item.setFlex(this.diferencaFlex(context));

                    EfetuarPedidos.erro = false;
                    EfetuarPedidos.strErro = " ";

                    return item;
                }
                else
                {
                    EfetuarPedidos.erro = true;
                    EfetuarPedidos.strErro = "Valor abaixo do permitido!\nPor favor verifique.";
                    return null;
                }
            else
            {
                EfetuarPedidos.erro = true;
                EfetuarPedidos.strErro = "Desconto acima do permitido!\nPor favor verifique.";
                return null;
            }
        else
        {
            EfetuarPedidos.erro = true;
            EfetuarPedidos.strErro = "Verifique a quantidade digitada!\nPor favor verifique.";
            return null;
        }
    }

    private String buscarDadosVendaItem(int dado)
    {
        switch (dado)
        {
            case 1 :
                return this.dadosVendaItem.get("TABELA");
            case 2 :
                return this.dadosVendaItem.get("MINIMO");
            case 3 :
                return this.dadosVendaItem.get("QTDMINIMA");
            case 4 :
                return this.dadosVendaItem.get("UNIDADE");
            case 5 :
                return this.dadosVendaItem.get("UNVENDA");
            case 6 :
                return this.dadosVendaItem.get("BARRAS");
            case 7 :
                return this.dadosVendaItem.get("QTDCAIXA");
            case 8 :
                float quantidade = Float.parseFloat(this.dadosVendaItem.get("QTDCAIXA"));
                float valor = Float.parseFloat(this.dadosVendaItem.get("TABELA"));
                float unitario = 0;

                try { unitario = quantidade > 0 ? (valor / quantidade) : valor; }
                catch (Exception e){ unitario = valor; }

                return String.valueOf(unitario);
            default :
                return "--";
        }
    }

    private Boolean verificarValor(float desconto, boolean percentual, Context context) {
        if(percentual)
            return true;
        else
        {
            float valordesconto;
            float minimo = Float.parseFloat(this.buscarDadosVendaItem(2));
            float minimoPromocional = this.verificarPromocoes(context);
            float tabela = Float.parseFloat(this.buscarDadosVendaItem(1));

            minimo = minimoPromocional > 0 ?
                    (minimo < minimoPromocional ? minimo : minimoPromocional) : minimo;
            minimo = (minimo > 0 && minimo < tabela) ? minimo : tabela;

            valordesconto = minimo - (minimo * desconto / 100);

            if(this.valor >= valordesconto)
                return true;
            else
                return this.verificarPromocoes(context, this.valor);
        }
    }

    private Boolean verificarQuantidade()
    {
        if(quantidade % Integer.parseInt(this.buscarDadosVendaItem(3)) == 0)
            return true;
        else
            return false;
    }

    private Boolean verificarDesconto(float desconto, boolean percentual, Context context)
    {
        if(percentual)
            if(this.desconto < desconto)
                return true;
            else
                return this.verificarPromocoes(context, this.valor - (this.valor * desconto / 100));
        else
            return true;
    }

    private float verificarPromocoes(Context ctx)
    {
        PromocaoDataAccess pda = new PromocaoDataAccess(ctx);
        Promocao p = null;

        try { p = pda.buscarPromocao(this.item.getCodigo(), this.quantidade); }
        catch (GenercicException e) { e.printStackTrace(); }

        if(p != null) { return p.getValor(); }
        else { return -1; }
    }

    private boolean verificarPromocoes(Context ctx, float valor)
    {
        PromocaoDataAccess pda = new PromocaoDataAccess(ctx);
        Promocao p = null;

        try { p = pda.buscarPromocao(this.item.getCodigo(), this.quantidade); }
        catch (GenercicException e) { e.printStackTrace(); }

        if(p != null) { return p.getValor() <= valor; }
        else { return false; }
    }
}
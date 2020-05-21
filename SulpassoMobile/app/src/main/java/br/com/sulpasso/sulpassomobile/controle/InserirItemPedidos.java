package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.HashMap;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;

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
    private float peso;

    private Item item;

    public InserirItemPedidos(String configuracaoVendaItem)
    {
        this.configuracaoVendaItem = configuracaoVendaItem;
    }

    public void setValor(float valor) { this.valor = valor; }

    public void setDesconto(float desconto) { this.desconto = desconto; }

    public void setQuantidade(float quantidade) { this.quantidade = quantidade; }

    public float getQuantidade() { return this.quantidade; }

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
        this.peso = item.getPeso();
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

    public float getValorDigitado() { return this.valor; }

    public float getQuantidadeDigitado() { return this.quantidade; }

    public String getValor()
    {
        if(this.valor > 0)
            return Formatacao.format2d(this.valor);
        else
            return Formatacao.format2d(Float.parseFloat(this.buscarDadosVendaItem(1)));
    }

    public String getValorTabela()
    {
        return this.buscarDadosVendaItem(1);
    }

    public String buscarMinimo() { return this.buscarDadosVendaItem(2); }

    public String getQtdMinimaVenda() { return this.buscarDadosVendaItem(3); }

    public String getUnidade() { return this.buscarDadosVendaItem(4); }

    public String getUnidadeVenda() { return this.buscarDadosVendaItem(5); }

    public String getCodigoBarras() { return this.buscarDadosVendaItem(6); }

    public String getQtdCaixa() { return this.buscarDadosVendaItem(7); }

    public String getValorUnitario() { return this.buscarDadosVendaItem(8); }

    public String getEstoque() { return this.buscarDadosVendaItem(9); }

    public float calcularTotal()
    {
        if(this.item.getUnidade().equals("KG") && !this.item.getUnidadeVenda().equals("KG"))
        {
            return ((this.valor
                - (this.valor * (this.desconto / 100))
                + (this.valor * (this.acrescimo / 100)))
                * this.quantidade) * this.item.getFaixa();
        }
        else
        {
            return (this.valor
                - (this.valor * (this.desconto / 100))
                + (this.valor * (this.acrescimo / 100)))
                * this.quantidade;
        }
    }

    public float calcularTotalDescCamp(float quantidade, float valor, float desconto, float grupo, float produtos, float acrescimo, int item, Context ctx)
    {
        ItemDataAccess ida = new ItemDataAccess(ctx);
        if(ida.vendaKilo(item))
        {
            return ((valor
                    - (valor * (desconto / 100))
                    - (valor * (grupo/ 100))
                    - (valor * (produtos / 100))
                    + (valor * (acrescimo / 100)))
                    * quantidade) * ida.getFaixa(item);
        }
        else
        {
            return (valor
                    - (valor * (desconto / 100))
                    - (valor * (grupo/ 100))
                    - (valor * (produtos / 100))
                    + (valor * (acrescimo / 100)))
                    * quantidade;
        }
        /*
        if(ida.vendaKilo(item))
        {
            return ((this.valor
                    - (this.valor * (this.desconto / 100))
                    - (this.valor * (grupo/ 100))
                    - (this.valor * (produtos / 100))
                    + (this.valor * (this.acrescimo / 100)))
                    * this.quantidade) * this.item.getFaixa();
        }
        else
        {
            return (this.valor
                    - (this.valor * (this.desconto / 100))
                    - (this.valor * (grupo/ 100))
                    - (this.valor * (produtos / 100))
                    + (this.valor * (this.acrescimo / 100)))
                    * this.quantidade;
        }
        */
    }

    public float diferencaFlex(Context ctx)
    {//essa função deve ser calculada com base no peso do item
        float minimo = Float.parseFloat(this.buscarDadosVendaItem(2));
        float minimoPromocional = this.verificarPromocoes(ctx);
        float tabela = Float.parseFloat(this.buscarDadosVendaItem(1));
        float diferenca = 0;

        float minimoAcessivel = 0;

        if(minimoPromocional > 0)
        {
            if(minimo > 0)
            {
                if(minimo < minimoPromocional)
                {
                    minimoAcessivel = minimo;
                }
                else
                {
                    minimoAcessivel = minimoPromocional;
                }
            }
            else
            {
                minimoAcessivel = minimoPromocional;
            }
        }
        else
        {
            if(minimo > 0)
            {
                minimoAcessivel = minimo;
            }
            else
            {
                minimoAcessivel = tabela;
            }
        }

        minimoAcessivel = (minimoAcessivel > 0 && minimoAcessivel < tabela) ? minimoAcessivel : tabela;
        diferenca = minimoAcessivel - this.valor;

        if(this.item.getUnidade().equals("KG") && !this.item.getUnidadeVenda().equals("KG"))
        {
            diferenca = diferenca * this.item.getFaixa();
        }
        /*
        else
        {
            diferenca = (this.valor
                    - (this.valor * (this.desconto / 100))
                    + (this.valor * (this.acrescimo / 100)))
                    * this.quantidade;
        }
        */


        return diferenca;
    }

    public boolean valorMaximo(Context ctx, Class tipoPedido)
    {
        float tabela = Float.parseFloat(this.buscarDadosVendaItem(1));

        if(tipoPedido == Troca.class)
            return (this.valor <= tabela);
        else
            return (this.valor <= (tabela * 2));

    }

    public ItensVendidos confirmarItem(float desconto, boolean percentual, Context context, boolean senha, int maximo, Class tipoPedido)
    {
        if(!senha)
        {
            if(this.verificarQuantidade(maximo))
                if(this.verificarDesconto(desconto, percentual, context, 0))
                    if(this.verificarValor(desconto, percentual, context, tipoPedido, 0))
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
                        item.setDigitadoSenha(false);
                        item.setValorMinimo(Float.parseFloat(this.buscarDadosVendaItem(2)));
                        item.setDescontoCampanha(false);
                        item.setDescontoCG(0);
                        item.setDescontoCP(0);
                        item.setPeso(this.peso);

                        EfetuarPedidos.erro = false; //Na linha de baixo deve ser acrescentado um calculo relacionando também ao peso do produto calcularTotalDesconto
                        EfetuarPedidos.strErro = "Valor de flex gerado no item " + Formatacao.format2d(((item.getFlex() * -1) * item.getQuantidade()));

                        return item;
                    }
                    else
                    {
                        EfetuarPedidos.erro = true;
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
                EfetuarPedidos.strErro = "Verifique a quantidade digitada!";
                return null;
            }
        }
        else
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
            item.setDigitadoSenha(true);
            item.setValorMinimo(Float.parseFloat(this.buscarDadosVendaItem(2)));
            item.setDescontoCampanha(false);
            item.setDescontoCG(0);
            item.setDescontoCP(0);
            item.setPeso(this.peso);

            EfetuarPedidos.erro = false;
            EfetuarPedidos.strErro = "";

            return item;
        }
    }

    public ItensVendidos confirmarItem(float desconto, boolean percentual, Context context, boolean senha, int natureza, int empresa, int maximo, Class tipoPedido, String tipoVenda, int especialTipo)
    {
        if(!senha)
        {
            if(tipoPedido == Troca.class || (tipoPedido == AlteracaoPedidos.class && tipoVenda.equalsIgnoreCase("tr")) || this.verificarQuantidade(maximo))
                if(tipoPedido == Troca.class || (tipoPedido == AlteracaoPedidos.class && tipoVenda.equalsIgnoreCase("tr")) || this.verificarDesconto(desconto, percentual, context, especialTipo))
                    if(tipoPedido == Troca.class || (tipoPedido == AlteracaoPedidos.class && tipoVenda.equalsIgnoreCase("tr")) || this.verificarValor(desconto, percentual, context, tipoPedido, especialTipo))
                    {
                        ItensVendidos item = new ItensVendidos();
                        item.setItem(this.item.getCodigo());
                        item.setReferencia(this.item.getReferencia());
                        item.setDescricao(this.item.getDescricao());
                        item.setComplemento(this.item.getComplemento());
                        item.setQuantidade(this.quantidade);

                        item.setValorTabela(Float.parseFloat(buscarDadosVendaItem(1)));
                        item.setValorLiquido(Float.parseFloat(this.getValor()));
                        item.setValorDigitado(Float.parseFloat(this.getValor()));

                        item.setPeso(this.peso);
                        item.setContribuicao(this.item.getContribuicao());

                        if(tipoPedido == Troca.class)
                            item.setDesconto(0);
                        else
                            item.setDesconto(this.desconto);

                        item.setTotal(this.calcularTotal());

                        if (natureza == 21 && empresa == 7) { item.setFlex(this.calcularTotal()); }
                        else { item.setFlex(this.diferencaFlex(context)); }

                        item.setDigitadoSenha(false);
                        item.setValorMinimo(Float.parseFloat(this.buscarDadosVendaItem(2)));
                        item.setDescontoCampanha(false);
                        item.setDescontoCG(0);
                        item.setDescontoCP(0);

                        EfetuarPedidos.erro = false; //Na linha de baixo deve ser acrescentado um calculo relacionando também ao peso do produto calcularTotalDesconto

                        if(tipoPedido != Troca.class)
                            EfetuarPedidos.strErro = "Valor de flex gerado no item " + Formatacao.format2d(((item.getFlex() * -1) * item.getQuantidade()));

                        return item;
                    }
                    else
                    {
                        EfetuarPedidos.erro = true;
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
                EfetuarPedidos.strErro = "Verifique a quantidade digitada!";
                return null;
            }
        }
        else
        {
            ItensVendidos item = new ItensVendidos();
            item.setItem(this.item.getCodigo());
            item.setReferencia(this.item.getReferencia());
            item.setDescricao(this.item.getDescricao());
            item.setComplemento(this.item.getComplemento());
            item.setQuantidade(this.quantidade);
            item.setValorTabela(Float.parseFloat(this.getValor()));
            item.setValorLiquido(this.valor);
            item.setValorDigitado(this.valor);
            item.setDesconto(0);
            item.setTotal(this.calcularTotal());
            item.setFlex(0);
            item.setDigitadoSenha(true);
            item.setValorMinimo(Float.parseFloat(this.buscarDadosVendaItem(2)));
            item.setDescontoCampanha(false);
            item.setDescontoCG(0);
            item.setDescontoCP(0);
            item.setPeso(this.peso);
            item.setContribuicao(this.item.getContribuicao());

            EfetuarPedidos.erro = false;
            EfetuarPedidos.strErro = "";

            return item;
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
                float valor = 0;
                float unitario = 0;

                valor = this.valor > 0 ? this.valor : Float.parseFloat(this.dadosVendaItem.get("TABELA"));

                if(this.dadosVendaItem.get("UNIDADE").equalsIgnoreCase("UN") && this.dadosVendaItem.get("UNVENDA").equalsIgnoreCase("UN"))
                {
                    unitario = valor;
                }
                else
                {
                    try { unitario = quantidade > 0 ? (valor / quantidade) : valor; }
                    catch (Exception e){ unitario = valor; }
                }

                return Formatacao.format2d(unitario);
            case 9 :
                return this.dadosVendaItem.get("ESTOQUE");
            default :
                return "--";
        }
    }

    private Boolean verificarValor(float desconto, boolean percentual, Context context, Class tipoPedido, int especial)
    {
        Boolean atualizarValores = false;

        if(this.valorMaximo(context, tipoPedido))
        {
            if(percentual || tipoPedido == Troca.class)
                return true;
            else
            {
                if((especial == 0) && (this.valor != Float.parseFloat(this.buscarDadosVendaItem(1))))
                {
                    EfetuarPedidos.strErro = "Não é permitido alterar o valor de clientes especiais!";
                    return false;
                }
                else
                {
                    if(especial == -1)
                    {
                        atualizarValores = true;
                    }
                    else
                    {
                        if(this.valor > Float.parseFloat(this.buscarDadosVendaItem(1)))
                        {
                            if(especial == 1 || especial == 2)
                                atualizarValores = true;
                            else
                            {
                                EfetuarPedidos.strErro = "Para clientes especiais não é permitido alterar valores acima do valor de tabela!";
                                atualizarValores = false;
                                return false;
                            }
                        }
                        else
                        {
                            if(this.valor < Float.parseFloat(this.buscarDadosVendaItem(1)))
                            {
                                if(especial == 2 || especial == 3)
                                    atualizarValores = true;
                                else
                                {
                                    EfetuarPedidos.strErro = "Para clientes especiais não é permitido alterar valores abaixo do valor de tabela!";
                                    atualizarValores = false;
                                    return false;
                                }
                            }
                            else
                            {
                                atualizarValores = true;
                            }
                        }
                    }

                    if(atualizarValores)
                    {
                        float valordesconto;
                        float minimo = Float.parseFloat(this.buscarDadosVendaItem(2));
                        float minimoPromocional = this.verificarPromocoes(context);
                        float tabela = Float.parseFloat(this.buscarDadosVendaItem(1));
                        float minimoAcessivel = 0;

                        if(minimoPromocional > 0)
                        {
                            if(minimo > 0)
                            {
                                if(minimo < minimoPromocional)
                                {
                                    minimoAcessivel = minimo;
                                }
                                else
                                {
                                    minimoAcessivel = minimoPromocional;
                                }
                            }
                            else
                            {
                                minimoAcessivel = minimoPromocional;
                            }
                        }
                        else
                        {
                            if(minimo > 0)
                            {
                                minimoAcessivel = minimo;
                            }
                            else
                            {
                                minimoAcessivel = tabela;
                            }
                        }

                        /*
                        minimoAcessivel = minimoPromocional > 0 ?
                                (minimo < minimoPromocional ? minimo : minimoPromocional) : minimo;
                        */

                        minimoAcessivel = (minimoAcessivel > 0 && minimoAcessivel < tabela) ? minimoAcessivel : tabela;

                        valordesconto = minimoAcessivel - (minimoAcessivel * desconto / 100);

                        if(this.valor >= valordesconto)
                            return true;
                        else
                            return this.verificarPromocoes(context, this.valor);
                    }
                    else
                    {
                        EfetuarPedidos.strErro = "Verifique as restrições de alteração de valores para clientes especiais!";
                        return false;
                    }
                }
            }
        }
        else
        {
            EfetuarPedidos.strErro = "Valor acima do permitido!";
            return false;
        }

    }

    public Boolean verificarQuantidade(int maximo)
    {
        if(this.quantidade > 0 && this.quantidade % Integer.parseInt(this.buscarDadosVendaItem(3)) == 0 && this.quantidade <= maximo)
            return true;
        else
            return false;
    }

    private Boolean verificarDesconto(float desconto, boolean percentual, Context context, int especial)
    {
        if(percentual)
            if(!(especial == 3 || especial == 2))
                return false;
            else
                if(this.desconto < desconto )
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

        if(p != null)
        {
            if(p.getValor() <= valor){ return false; }
            else
            {
                EfetuarPedidos.strErro = "Valor abaixo do permitido!\nPor favor verifique.";
                return false;
            }
        }
        else
        {
            EfetuarPedidos.strErro = "Valor abaixo do permitido!\nPor favor verifique.";
            return false;
        }
    }
}
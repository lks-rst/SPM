package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaProduto;
import br.com.sulpasso.sulpassomobile.modelo.Grupo;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.modelo.PrePedidoItem;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.persistencia.queries.CidadeDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.GrupoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaItens;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;


/**
 * Created by Lucas on 02/08/2016.
 */
public abstract class EfetuarPedidos
{
    public static boolean erro = false;
    public static String strErro = "";
    public static boolean senha = false;

    protected int codigoNatureza;
    protected int codigoPrazo;
    protected int tabela;

    protected ConfigurarSistema controleConfiguracao;
    protected ConsultarClientes controleClientes;
    protected ConsultarProdutos controleProdutos;
    protected SalvarPedido controleSalvar;
    protected InserirItemPedidos controleDigitacao;

    protected Venda venda;

    protected ArrayList<Natureza> listaNaturezas;
    protected ArrayList<Prazo> listaPrazos;
    protected ArrayList<ItensVendidos> itensVendidos;

    protected ArrayList<CampanhaGrupo> campanhaGrupos;
    protected ArrayList<CampanhaProduto> campanhaProdutos;

    protected Context context;

    protected boolean isReturnedFromDialog = false;
    protected boolean returnedValueFromDialog = false;

    protected ArrayList<Grupo> grupos;
    protected ArrayList<Grupo> sGrupos;
    protected ArrayList<Grupo> divisoes;

    protected String strTipoVenda;

    protected int grupo;
    protected int subGrupo;
    protected int divisao;

    protected PrePedido prePedido;

    private GrupoDataAccess gda;
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    public EfetuarPedidos(Context ctx, String tipo)
    {
        this.strTipoVenda = tipo;

        this.context = ctx;
        this.controleConfiguracao = new ConfigurarSistema(ctx);
        this.controleClientes = new ConsultarClientes(ctx);
        this.controleProdutos = new ConsultarProdutos(ctx);
        this.controleSalvar = new SalvarPedido();
        this.controleDigitacao = new InserirItemPedidos(this.getConfiguracaoVendaItem());

        this.gda = new GrupoDataAccess(context);

        this.itensVendidos = new ArrayList<>();
        this.venda = new Venda();

        this.grupo = 0;
        this.subGrupo = 0;
        this.divisao = 0;

        try { this.controleConfiguracao.carregarConfiguracoesVenda(); }
        catch (GenercicException exeption)
        {
            exeption.printStackTrace();
            System.out.println("Erro ao carregar os dados de configuração de venda");
        }
    }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/

/**************************************************************************************************/
/************************************ABSTRACT METHODS**********************************************/
/**************************************************************************************************/
    public abstract ArrayList<String> listarCLientes(int tipo, String dados) throws GenercicException;

    public abstract ArrayList<String> listarNaturezas(Boolean especial) throws GenercicException;

    public abstract ArrayList<String> listarPrazos(int position) throws GenercicException;

    public abstract Boolean permitirClick(int id);

    public abstract String selecionarCliente(int posicao);

    public abstract int buscarNatureza();

    public abstract int buscarPrazo();

    public abstract void setPrazo(int posicao);

    public abstract Boolean temValorMinimo();

    public abstract Boolean temPromocao();

    public abstract void buscarPromocoes();

    public abstract String buscarMinimoTabela();

    public abstract Boolean alteraValor(String campo);

    public abstract Boolean alteraValorFim(int campo);

    public abstract String getValor();

    public abstract String getQtdMinimaVenda();

    public abstract String getUnidade();

    public abstract String getUnidadeVenda();

    public abstract String getCodigoBarras();

    public abstract String getQtdCaixa();

    public abstract String getValorUnitario();

    public abstract String getEstoque();

    public abstract String getMarkup();

    public abstract String calcularTotal();

    public abstract void setQuantidade(String quantidade);

    public abstract Boolean confirmarItem();

    public abstract int finalizarPedido();

    public abstract String buscarDadosVenda(int campo);

    public abstract String buscarDadosCliente(int campo);

    public abstract int verificarTabloides();

    public abstract int aplicarDescontoTabloide(float percentual, int posicao);

    protected abstract Boolean naturezaIsClicable();

    protected abstract Boolean prazoIsClicable();

    protected abstract Boolean naturezaIsClicableEnd();

    protected abstract Boolean prazoIsClicableEnd();

    protected abstract Boolean finalizarItem();

    protected abstract void getNaturezasList(Boolean especial) throws GenercicException;

    protected abstract void getPrazosList(String prazo) throws GenercicException;

    public abstract ArrayList<CampanhaGrupo> getCampanhaGrupos();

    public abstract boolean verificarPrepedido();

    public abstract PrePedido detalharPrePedido();

    public abstract ArrayList<String> listarCidades();

    public abstract float calcularPpc(float valor, float markup, float desconto);

    public abstract void buscarVenda(int codVenda);
/**************************************************************************************************/
/****************************************FINAL METHODS*********************************************/
/**************************************************************************************************/
    protected final String buscarCidade(int codigo)
    {
        CidadeDataAccess cda = new CidadeDataAccess(this.context);
        try { return cda.getByData(codigo).getNome(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    protected final String buscarEstado(int codigo)
    {
        CidadeDataAccess cda = new CidadeDataAccess(this.context);
        try { return cda.getByData(codigo).getUf(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    public final void setValor(String valor)
    {
        try { this.controleDigitacao.setValor(Float.parseFloat(valor)); }
        catch (Exception e){ this.controleDigitacao.setValor(Float.parseFloat("0")); }
    }

    public final void setDesconto(String desconto) { this.controleDigitacao.setDesconto(Float.parseFloat(desconto));}

    public final void setAcrescimo(String acrescimo) { this.controleDigitacao.setAcrescimo(Integer.parseInt(acrescimo)); }

    @NonNull
    public final String calcularPPC(String mkp, String vlr)
    {
        float markup = Float.parseFloat(mkp);
        float valor = Float.parseFloat(vlr);
        float ppc = valor + (valor * ( markup / 100));

        return String.valueOf(ppc);
    }

    public final void indicarTotal(float total) { this.controleSalvar.setTotal(total);}

    public final String getItem() { return this.controleDigitacao.getItem().toDisplay(); }

    public final int getTabela() { return this.tabela; }

    public final String getDesdobramentoPrazo()
    {
        int i = 0;

        for(i = 0; i < this.listaPrazos.size(); i++)
        {
            if (this.listaPrazos.get(i).getCodigo() == this.codigoPrazo)
                break;
        }

        return i <= this.listaPrazos.size() ? this.listaPrazos.get(i).getDesdobramento() : "--";
    }

    public final int itensVendidos() { return this.itensVendidos.size(); }

    public final float valorVendido()
    {
        float valor = 0;
        for(int i = 0; i < this.itensVendidos.size(); i++)
            valor += this.itensVendidos.get(i).getTotal();

        return valor;
    }

    public final String listarVendidos()
    {
        String itens = "";
        for(int i = 0; i < this.itensVendidos.size(); i++)
            itens += (this.itensVendidos.get(i).toString() + "\n");

        return itens;
    }

    public final String cabecahoPedido(int campo)
    {
        String valor = "";

        switch (campo)
        {
            case R.id.ffpEdtCliente:
            case R.id.flirEdtCliente :
                try { valor = this.venda.getCliente().toDisplay(); }
                catch (Exception e) { valor = "--"; }
                break;
            case R.id.ffpEdtCidade:
            case R.id.flirEdtCidade :
                try { valor = this.buscarCidade(this.venda.getCliente().getCodigoCidade()); }
                catch (Exception e) { valor = "--"; }
                break;
            case R.id.ffpEdtTab:
            case R.id.flirEdtTabela :
                valor = String.valueOf(this.venda.getTabela());
                break;
            case R.id.flirEdtNaturesa :
                valor = this.listaNaturezas.get(this.buscarNatureza()).getDescricao();
                break;
            case R.id.flirEdtTipo :
                valor = "PD";
                break;
            case R.id.flirEdtItens :
                valor = String.valueOf(this.itensVendidos.size());
                break;
            case R.id.flirEdtValor :
                valor = String.valueOf(this.valorVendido());
                break;
            case R.id.flirEdtVolume :
                valor = "--";
                break;
            case R.id.flirEdtCont :
                valor = "--";
                break;
        }

        return valor;
    }

    public final ArrayList<String> listarItens() throws GenercicException
    {
        return this.controleProdutos.buscarItens(this.tabela);
    }

    public final ArrayList<String> listarResumo() throws GenercicException
    {
        ArrayList<String> itens = new ArrayList<>();

        for (int i = 0; i < this.itensVendidos.size(); i++)
        {
            itens.add(this.controleProdutos.getItemStr(this.itensVendidos.get(i).getItem()) +
                    this.itensVendidos.get(i).toDisplay());
        }

        return itens;
    }

    public final void indicarTipoBuscaItem(int tipo) { this.controleProdutos.setSearchType(TiposBuscaItens.getTipoFromInt(tipo)); }

    public final void indicarDadosBuscaItens(String dados){ this.controleProdutos.setSearchData(dados); }

    public final void selecionarItem(int posicao)
    {
        this.controleDigitacao.setItem(this.controleProdutos.getItem(posicao));
        this.controleDigitacao.setDadosVendaItem(this.controleProdutos.dadosVenda
                (posicao, this.tabela, this.controleConfiguracao.getConfigUsr().getTabelaMinimo()));
    }

    public final void selecionarItemPre(int posicao)
    {
        this.controleDigitacao.setItem(this.converterItem(this.prePedido.getItensVendidos().get(posicao)));
        this.controleDigitacao.setDadosVendaItem(this.controleProdutos.dadosVendaPre
                (this.prePedido.getItensVendidos().get(posicao).getItem(), this.tabela,
                        this.controleConfiguracao.getConfigUsr().getTabelaMinimo()));
    }

    public void alterarItem(int posicao)
    {
        int codigo = this.itensVendidos.get(posicao).getItem();
        int newP = this.controleProdutos.getItemPosicao(codigo);

        this.controleDigitacao.setItem(this.controleProdutos.getItem(newP));
        this.controleDigitacao.setDadosVendaItem(this.controleProdutos.dadosVenda
                (newP, this.tabela, this.controleConfiguracao.getConfigUsr().getTabelaMinimo()));
    }

    public void removerItem(int posicao)
    {
        this.itensVendidos.remove(posicao);
    }

    public String getDescricaoItem(int posicao)
    {
        int codigo = this.itensVendidos.get(posicao).getItem();
        int newP = this.controleProdutos.getItemPosicao(codigo);

        return this.controleProdutos.getItem(newP).getDescricao();
    }

    public final int restoreClient() { return this.controleClientes.restoreClient(); }

    public final void setDescontoPedido(String desconto) { this.controleSalvar.setDesconto(Float.parseFloat(desconto));}

    public final void setFrete(String desconto) { this.controleSalvar.setFrete(Float.parseFloat(desconto));}

    public final float recalcularTotalPedido() { return this.controleSalvar.calcularTotal(); }

    protected final String getPrazoNatureza(int position)
    {
        this.codigoNatureza = this.listaNaturezas.get(position).getCodigo();
        return this.listaNaturezas.get(position).getPrazo();
    }

    @org.jetbrains.annotations.Contract(pure = true)
    protected final String getConfiguracaoVendaItem() { return "Configuracao de venda dos itens"; }

    protected final String dataSistema()
    {
        Date today;
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");
        today = new Date();
        return this.strDataBanco(sf.format(today));
    }

    protected final String horaSistema()
    {
        Date today;
        int m;
        int s;
        int h;
        ManipulacaoStrings ms = new ManipulacaoStrings();

        today = new Date();
        h = today.getHours();
        m = today.getMinutes();
        s = today.getSeconds();

        String sf = ms.comEsquerda("" + h, "0", 2) + ":" + ms.comEsquerda("" + m, "0", 2) + ":" +
                ms.comEsquerda("" + s, "0", 2) ;

        return this.strDataBanco(sf);
    }

    protected final String strDataBanco(String data)
    {
        String nova_data = "";
        String[] datas;

        try
        {
            datas = data.split("/");
            nova_data = datas[2] + "-" + datas[1] + "-" + datas[0];
        }
        catch (Exception e) { nova_data = data;
        }
        return nova_data;
    }

    protected final Boolean clientIsClicable() { return this.itensVendidos.size() <= 0; }

    protected final float calcularTotal(float quantidade, float valor, float desconto, float grupo, float produtos, float acrescimo)
    {
        return (valor
                - (valor * (desconto / 100))
                - (valor * (grupo/ 100))
                - (valor * (produtos / 100))
                + (valor * (acrescimo / 100)))
                * quantidade ;
    }

    public final void addObs(String s, int tipo)
    {
        if(tipo == 1)
        {
            this.venda.setObservacao(s);
        }
        else if(tipo == 2)
        {
            this.venda.setObservacaoNota(s);
        }
        else if(tipo == 0)
        {
            this.venda.setObservacaDesconto(s);
        }
    }

    /**
     * Esse código está duplicado em outra classe
     * @return
     * @throws GenercicException
     */
    public final ArrayList<String> listarGrupos() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();

        this.buscarGrupos();

        for(Grupo g : this.grupos) { retorno.add(g.toDisplay()); }
        retorno.add(0, this.context.getApplicationContext().getResources().getString(R.string.str_grupo_zero));

        return retorno;
    }

    /**
     * Esse código está duplicado em outra classe
     * @return
     * @throws GenercicException
     */
    public final ArrayList<String> listarSubGrupos() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();

        this.buscarSubGrupos();

        for(Grupo g : this.sGrupos) { retorno.add(g.toDisplay()); }
        retorno.add(0, this.context.getApplicationContext().getResources().getString(R.string.str_sub_zero));

        return retorno;
    }

    /**
     * Esse código está duplicado em outra classe
     * @return
     * @throws GenercicException
     */
    public final ArrayList<String> listarDivisoes() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();

        this.buscarDivisoes();

        for(Grupo g : this.divisoes) { retorno.add(g.toDisplay()); }
        retorno.add(0, this.context.getApplicationContext().getResources().getString(R.string.str_div_zero));

        return retorno;
    }

    public final void indicarGrupo(int posicao)
    {
        this.grupo = this.grupos.get(posicao -1).getGrupo();
        this.controleProdutos.setGrupo(this.grupo);
        this.subGrupo = 0;
        this.divisao = 0;
        this.controleProdutos.setSubGrupo(this.subGrupo);
        this.controleProdutos.setDivisao(this.divisao);
    }

    public final void indicarSubGrupo(int posicao)
    {
        this.subGrupo = this.sGrupos.get(posicao -1).getSubGrupo();
        this.controleProdutos.setSubGrupo(this.subGrupo);
        this.divisao = 0;
        this.controleProdutos.setDivisao(this.divisao);
    }

    public final void indicarDivisao(int posicao)
    {
        this.divisao = this.divisoes.get(posicao -1).getDivisao();
        this.controleProdutos.setDivisao(this.divisao);
    }

    public final boolean insereDePrePedido()
    {
        return false;
    }

    public final int getCitCod(int position)
    {
        return this.controleClientes.cidadeCod(position -1);
    }

    public final boolean vendaKilo()
    {
        if(controleDigitacao.getItem().getUnidade().equals("KG") &&
            controleDigitacao.getItem().getUnidadeVenda().equals("KG")) { return true; }
        else { return false; }
    }

    public float calculoContribuicao(float preco)
    {
        float contribuicao = this.controleDigitacao.getItem().getContribuicao();
        float custo = this.controleDigitacao.getItem().getCusto();

        float x;
        float a;
        float cont;
        float pCont;

        x = Math.abs((contribuicao / 100) -1);
        a = preco * x;
        cont = (a - custo);

        pCont = (cont * 100)/preco;

        return pCont;
    }
/**************************************************************************************************/
/*********************************NON ABSTRACT OR FINAL METHODS************************************/
/**************************************************************************************************/
    public void buscarTipoCliente() { this.controleProdutos.setSearchData(this.venda.getCliente().getTipo()); }

    public void setSearchType(TiposBuscaItens type)
{
    this.controleProdutos.setSearchType(type);
}

    public void setSearchData(String data)
    {
        this.controleProdutos.setSearchData(data);
    }

    public int buscarConsultaAbertura()
    {
        this.setSearchType(TiposBuscaItens.getTipoFromInt(this.controleConfiguracao.getConfigUsr().getTipoBusca()));
        return this.controleConfiguracao.getConfigUsr().getTipoBusca();
    }

    public float buscarValorItemDigitando()
    {
        return this.controleDigitacao.getValorDigitado();
    }

    public float buscarQuantidadeItemDigitando()
    {
        return this.controleDigitacao.getQuantidadeDigitado();
    }

    public void selecionarCliente() { /*****/ }

    public void selecionarNatureza() { /*****/ }

    public void selecionarPrazo() { /*****/ }

    public void aplicarDesconto(float desconto) { /*****/ }

    private void buscarGrupos() throws GenercicException { this.grupos = this.gda.getAll(); }

    private void buscarSubGrupos() throws GenercicException { this.sGrupos = this.gda.getAll(this.grupo); }

    private void buscarDivisoes() throws GenercicException { this.divisoes = this.gda.getAll(this.grupo, this.subGrupo); }

    private Item converterItem(PrePedidoItem item)
    {
        Item retorno;

        retorno = new Item();
/*

        retorno.setCodigo(item.getItem());
        retorno.setDescricao(item.getDescricao());
        retorno.setReferencia("");
        retorno.setComplemento("");
*/

        ItemDataAccess ida = new ItemDataAccess(this.context);
        try {
            retorno = ida.buscarItemCodigo(item.getItem());
        } catch (GenercicException e) {
            e.printStackTrace();
        }

        return retorno;
    }
}
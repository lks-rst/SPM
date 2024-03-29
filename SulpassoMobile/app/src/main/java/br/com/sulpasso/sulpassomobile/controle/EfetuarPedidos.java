package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.database.SQLException;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Banco;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaProduto;
import br.com.sulpasso.sulpassomobile.modelo.ContasReceber;
import br.com.sulpasso.sulpassomobile.modelo.Gravosos;
import br.com.sulpasso.sulpassomobile.modelo.Grupo;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.Mix;
import br.com.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.modelo.PrePedidoItem;
import br.com.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.persistencia.queries.BancoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.CidadeDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ContasReceberDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.DevolucaoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.GrupoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.MixDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaItens;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 02/08/2016.
 */
public abstract class EfetuarPedidos
{
    private GrupoDataAccess gda;

    private boolean titulosExibidos = false;

    private ArrayList<ContasReceber> contas;

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

    protected ArrayList<ItensVendidos> itensDevolvidos;

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

    public static boolean erro = false;
    public static String strErro = "";
    public static boolean senha = false;

    public int tipoConsultaItens = -1;
    public String parametrosBuscaItens = "";

    protected ArrayList<Mix> mixFaltando;

    public int posicaoItemSelecionado;

    private int quantidadeRemover;
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
        this.venda.setData(this.dataSistema());
        this.venda.setHora(this.horaSistema());

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
/************************************ABSTRACT METHODS**********************************************/
/**************************************************************************************************/
    protected abstract Boolean naturezaIsClicable();

    protected abstract Boolean prazoIsClicable();

    protected abstract Boolean naturezaIsClicableEnd();

    protected abstract Boolean prazoIsClicableEnd();

    protected abstract Boolean finalizarItem();

    protected abstract void getNaturezasList(Boolean especial) throws GenercicException;

    protected abstract void getPrazosList(String prazo) throws GenercicException;

    public abstract ArrayList<String> listarCLientes(int tipo, String dados) throws GenercicException;

    public abstract ArrayList<String> listarNaturezas(Boolean especial) throws GenercicException;

    public abstract ArrayList<String> listarPrazos(int position) throws GenercicException;

    public abstract ArrayList<String> listarPrazos(boolean especial) throws GenercicException;

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

    public abstract String getValorTabela();

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

    public abstract int finalizarPedido(Boolean justificar);

    public abstract String buscarDadosVenda(int campo);

    public abstract String buscarDadosCliente(int campo);

    public abstract int verificarTabloides();

    public abstract int verificarCampanhas();

    public abstract boolean buscarPorCampanhas(int pos);

    public abstract int aplicarDescontoTabloide(float percentual, int posicao, int tipo);

    public abstract ArrayList<CampanhaGrupo> getCampanhaGrupos();

    public abstract ArrayList<CampanhaProduto> getCampanhaProdutos();

    public abstract boolean verificarPrepedido();

    public abstract PrePedido detalharPrePedido();

    public abstract ArrayList<String> listarCidades();

    public abstract float calcularPpc(float valor, float markup, float desconto);

    public abstract void buscarVenda(int codVenda);

    public abstract Boolean mostraFlexItem();

    public abstract Boolean mostraFlexVenda();
/**************************************************************************************************/
/****************************************FINAL METHODS*********************************************/
/**************************************************************************************************/
    public final ArrayList<String> listarMotivos() throws GenercicException
    {
        ArrayList<String> lista = this.controleClientes.exibirMotivos();

        return lista;
    }

    protected final Boolean verificarNaturezaBrinde(int position)
    {
        if(this.listaNaturezas.get(position).getCodigo() == 21 &&
                this.controleConfiguracao.getConfigEmp().getCodigo() == 7)
            if(!this.verificarSaldoG())
                return false;
            else
                return true;
        else
            return true;
    }

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

    protected final String buscarBanco(int codigo)
    {
        BancoDataAccess bda = new BancoDataAccess(this.context);
        try { return ((Banco) bda.getByData(codigo).get(0)).getBanco(); }
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

    protected final String getPrazoNatureza(int position)
    {
        this.codigoNatureza = this.listaNaturezas.get(position).getCodigo();
        return this.listaNaturezas.get(position).getPrazo();
    }

    @NonNull
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

    @NonNull
    protected final Boolean clientIsClicable() { return this.itensVendidos.size() <= 0; }

    @Contract(pure = true)
    protected final float calcularTotal(float quantidade, float valor, float desconto, float grupo, float produtos, float acrescimo, int item)
    {
        return this.controleDigitacao.calcularTotalDescCamp(quantidade, valor, desconto, grupo, produtos, acrescimo, item, context);
        /*
        return (valor
                - (valor * (desconto / 100))
                - (valor * (grupo/ 100))
                - (valor * (produtos / 100))
                + (valor * (acrescimo / 100)))
                * quantidade ;
        */
    }

    protected final boolean verificarMix()
    {
        if(this.controleConfiguracao.getConfigTel().getMixIdeal())
        {
            MixDataAccess mda = new MixDataAccess(this.context);
            try
            {
                ArrayList<Mix> mixesCliente = mda.getByData(Integer.parseInt(this.venda.getCliente().getTipo()));
                boolean completo = false;

                if (mixesCliente != null && mixesCliente.size() > 0)
                {
                    for(Mix m : mixesCliente)
                    {
                        ArrayList<Item> itens = m.getItens();

                        for(Item it : itens)
                        {
                            completo = false;

                            for(ItensVendidos iv : this.venda.getItens())
                            {
                                if(iv.getItem() == it.getCodigo())
                                {
                                    completo = true;
                                    break;
                                }
                            }

                            if (!completo)
                                break;
                        }

                        if (!completo)
                            break;
                    }

//                    return completo;
                    return true;
                }
                else
                    return false;
            }
            catch (Exception e) { return false; }
        }
        else
        {
            return false;
        }
    }

    public final void setValor(String valor)
    {
        try
        {
            if(valor.toString().indexOf('.') != (valor.toString().length() - 1))
            {
                this.controleDigitacao.setValor(Float.parseFloat(valor));
            }
            else
            {
                this.controleDigitacao.setValor(Float.parseFloat(valor.substring(0, valor.length() - 1)));
            }
        }
        catch (Exception e)
        {
            this.controleDigitacao.setValor(Float.parseFloat("0"));
        }
    }

    public final void setDesconto(String desconto) { this.controleDigitacao.setDesconto(Float.parseFloat(desconto));}

    public final void setAcrescimo(String acrescimo) { this.controleDigitacao.setAcrescimo(Integer.parseInt(acrescimo)); }

    @NonNull
    public final String calcularPPC(String mkp, String vlr)
    {
        float markup = Float.parseFloat(mkp);
        float valor = Float.parseFloat(vlr);
        float ppc = valor + (valor * ( markup / 100));

        return Formatacao.format2d(ppc);
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
                float volume = 0;
                for(ItensVendidos iv : this.itensVendidos)
                //for(ItensVendidos iv : this.venda.getItens())
                {
                    volume += iv.getPeso() * iv.getQuantidade();
                }
                //valor = "--";
                valor = "" + volume;
                break;
            case R.id.flirEdtCont :
                float cont = 0;
                for(ItensVendidos iv : this.itensVendidos)
                //for(ItensVendidos iv : this.venda.getItens())
                {
                    cont += iv.getContribuicao();
                }

                try{ cont /= this.itensVendidos.size(); }
                catch (SQLException ex) { cont = -1; }

                //valor = "--";
                valor = "" + cont;
                break;
            case R.id.ffpEdtObsCpd :
                try { valor = this.venda.getObservacao(); }
                catch (Exception e) { valor = ""; }
                break;
            case R.id.ffpEdtObsNfe :
                try { valor = this.venda.getObservacaoNota(); }
                catch (Exception e) { valor = ""; }
                break;
        }

        return valor;
    }

    public final ArrayList<String> listarItens() throws GenercicException
    {
        return this.controleProdutos.buscarItens(this.tabela, this.venda.getCliente().getCodigoCliente(),
                this.controleConfiguracao.getConfigUsr().getTipoOredenacao());
    }

    public final ArrayList<Gravosos> listarItens2() throws GenercicException
    {
        Boolean found = false;
        ArrayList<Gravosos> itens = new ArrayList<>();
        itens = this.controleProdutos.buscarItens2(this.tabela, this.venda.getCliente().getCodigoCliente(),
                this.controleConfiguracao.getConfigUsr().getTipoOredenacao());

        for(int i = 0; i <  itens.size(); i++)
        {
            found = false;

            for(ItensVendidos iv : this.itensVendidos)
            {
                if (iv.getItem() == itens.get(i).getItem().getCodigo())
                {
                    found = true;
                    break;
                }
            }

            itens.get(i).setSold(found);
        }

        return itens;
    }

    public final ArrayList<String> listarResumo() throws GenercicException
    {
        ArrayList<String> itens = new ArrayList<>();

        for (int i = 0; i < this.itensVendidos.size(); i++)
        {
            /*
            itens.add(this.controleProdutos.getItemStr(this.itensVendidos.get(i).getItem()) +
                    this.itensVendidos.get(i).toDisplay());
            */
            itens.add(this.itensVendidos.get(i).toDisplay());
        }

        return itens;
    }

    public final void indicarTipoBuscaItem(int tipo) { this.controleProdutos.setSearchType(TiposBuscaItens.getTipoFromInt(tipo)); }

    public final void indicarDadosBuscaItens(String dados){ this.controleProdutos.setSearchData(dados); }

    public final void selecionarItem(int posicao)
    {
        this.posicaoItemSelecionado = posicao;
        this.controleDigitacao.setItem(this.controleProdutos.getItem(posicao));
        this.controleDigitacao.setDadosVendaItem(this.controleProdutos.dadosVenda
                (posicao, this.tabela, this.controleConfiguracao.getConfigUsr().getTabelaMinimo()));
    }

    public final void selecionarItemPre(int posicao)
    {
        int posicaoLista = this.controleProdutos.getItemPosicao(this.prePedido.getItensVendidos().get(posicao).getItem());

        if(posicaoLista >= 100 || posicaoLista < 0)
        {
            Item item = new Item();
            try
            {
                item = controleProdutos.getItemCodigo(this.prePedido.getItensVendidos().get(posicao).getItem());
                this.controleDigitacao.setItem(item);
                this.controleDigitacao.setDadosVendaItem(this.controleProdutos.dadosVendaCodigo
                        (item.getCodigo(), this.tabela, this.controleConfiguracao.getConfigUsr().getTabelaMinimo()));
            }
            catch (GenercicException e) { e.printStackTrace(); }
        }
        else
        {
            this.selecionarItem(posicaoLista);
        }

        /*
        this.controleDigitacao.setItem(this.converterItem(this.prePedido.getItensVendidos().get(posicao)));
        this.controleDigitacao.setDadosVendaItem(this.controleProdutos.dadosVendaPre
            (this.prePedido.getItensVendidos().get(posicao).getItem(), this.tabela,
            this.controleConfiguracao.getConfigUsr().getTabelaMinimo()));
        */
    }

    public final Boolean solicitarSenha()
    {
        //TODO: Mover isso para configuração

        Toast.makeText(this.context, "Código da empresa configurada: " + this.controleConfiguracao.getConfigEmp().getCodigo(), Toast.LENGTH_SHORT).show();
        return
                this.controleConfiguracao.getConfigEmp().getCodigo() == 3
                        ||
                this.controleConfiguracao.getConfigEmp().getCodigo() == 2
                        ||
                this.controleConfiguracao.getConfigEmp().getCodigo() == 5
                    ? true : false;
    }

    public final int restoreClient() { return this.controleClientes.restoreClient(); }

    public final void setDescontoPedido(String desconto) { this.controleSalvar.setDesconto(Float.parseFloat(desconto));}

    public final void setFrete(String desconto) { this.controleSalvar.setFrete(Float.parseFloat(desconto));}

    public final float recalcularTotalPedido() { return this.controleSalvar.calcularTotal(); }

    public final boolean recalcularValor(boolean alterar)
    {
        float totalPedido = this.controleSalvar.getTotal();
        if(this.getClass() == AlteracaoPedidos.class || this.getClass() == Troca.class)
        {
            this.controleSalvar.setTotal(totalPedido);
            return false;
        }
        else
        {
            if(this.controleConfiguracao.getConfigVda().getRecalcularPreco() && alterar)
            {
                totalPedido = 0;
                ArrayList<ItensVendidos> livAlt = new ArrayList<>();
                ArrayList<ItensVendidos> liv = this.itensVendidos;
                ItemDataAccess ida = new ItemDataAccess(this.context);

                HashMap<String, String> dadosVendaItem;

                for(ItensVendidos iv : liv)
                {
                    dadosVendaItem = this.controleProdutos.dadosVendaAlteracao
                            (iv.getItem(), this.tabela, this.controleConfiguracao.getConfigUsr().getTabelaMinimo());

                    float minimo = 0;
                    float total = 0;
                    float faixa = 1;

                    iv.setValorTabela(Float.parseFloat(dadosVendaItem.get("TABELA")));
                    minimo = Float.parseFloat(dadosVendaItem.get("MINIMO"));
                    if (minimo <= 0 || minimo > iv.getValorTabela())
                        minimo = iv.getValorTabela();

                    iv.setValorMinimo(minimo);
                    iv.setValorDigitado(iv.getValorTabela());
                    iv.setValorLiquido(iv.getValorTabela());

                    faixa = ida.buscarFaixa(iv.getItem());

                    if(dadosVendaItem.get("UNIDADE").equals("KG") && !dadosVendaItem.get("UNVENDA").equals("KG"))
                        total = iv.getValorTabela() * iv.getQuantidade() * faixa;
                    else
                        total = iv.getValorTabela() * iv.getQuantidade();

                    iv.setTotal(total);

                    livAlt.add(iv);

                    totalPedido += total;
                }

                liv.clear();
                liv.addAll(livAlt);

                this.controleSalvar.setTotal(totalPedido);

                return true;
            }
            else
            {
                this.controleSalvar.setTotal(totalPedido);
                return false;
            }
           // this.venda.setValor((double)totalPedido);
        }
    }

    public final void addObs(String s, int tipo)
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();
        s = ms.removeCaracteresEspeciais(s);

        if(tipo == 1) { this.venda.setObservacao(s); }
        else if(tipo == 2) { this.venda.setObservacaoNota(s); }
        else if(tipo == 0) { this.venda.setObservacaDesconto(s); }
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

    @Contract(value = " -> false", pure = true)
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

    public final int codigoMotivo(int position)
    {
        this.venda.setJustificativa(controleClientes.codigoMotivo(position));
        return controleClientes.codigoMotivo(position);
    }

    public final boolean controlaRoteiro()
    {
        return controleConfiguracao.getConfigVda().getGerenciarVisitas();
    }

    public final int posicaoUltimoItemSelecionado() { return this.posicaoItemSelecionado; }

    public final Boolean verificarJustificativa()
    {
        return this.controleSalvar.verificarJustificativa(this.venda.getJustificativa(),
                this.controleConfiguracao.getConfigVda().getGerenciarVisitas(),
                this.venda.getCliente().getVisita());
    }

    public final Boolean verifyItens()
    {
        Boolean validNumber;

        //validNumber = this.controleProdutos.verifyItens(this.controleConfiguracao.getConfigVda().getMinimoItensDiferentes());
        validNumber = this.controleConfiguracao.getConfigVda().getMinimoItensDiferentes() <= this.itensVendidos.size();

        Log.d("VALOR", validNumber.toString());
        return this.controleConfiguracao.getConfigVda().getMinimoItensDiferentes() <= this.itensVendidos.size();


       // return this.controleProdutos.verifyItens(this.controleConfiguracao.getConfigVda().getMinimoItensDiferentes());
    }


    public final Boolean verificarQuantidade()
    {
        return this.controleDigitacao.verificarQuantidade(this.controleConfiguracao.getConfigHor().getMaximoItens());
    }
/**************************************************************************************************/
/*********************************NON ABSTRACT OR FINAL METHODS************************************/
/**************************************************************************************************/
    public int quantidadeItem(int posicao)
    {
        for(ItensVendidos i : this.itensVendidos)
            if(this.controleProdutos.getItem(posicao).getCodigo() == i.getItem())
                return (int) this.itensVendidos.get(this.itensVendidos.indexOf(i)).getQuantidade();

        return 0;
    }

    public int consultaClientesAbertura() { return this.controleConfiguracao.consultaClientesAbertura(); }

    private void buscarGrupos() throws GenercicException
    {
        this.grupos = this.gda.getAllCliente(this.venda.getCliente().getCodigoCliente());
    }

    private void buscarSubGrupos() throws GenercicException { this.sGrupos = this.gda.getAll(this.grupo); }

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

    private void buscarDivisoes() throws GenercicException { this.divisoes = this.gda.getAll(this.grupo, this.subGrupo); }

    public void buscarTipoCliente() { this.controleProdutos.setSearchData(this.venda.getCliente().getTipo()); }

    public void setSearchType(TiposBuscaItens type)
    {
        this.controleProdutos.setSearchType(type);
        this.tipoConsultaItens = type.getValue();
    }

    public void setSearchData(String data)
    {
        this.controleProdutos.setSearchData(data);
    }

    public int buscarConsultaAbertura()
    {
        if(tipoConsultaItens == -1)
        {
            if(this.controleProdutos.getSearchType() != TiposBuscaItens.getTipoFromInt(this.controleConfiguracao.getConfigUsr().getTipoBusca()))
            {
                this.setSearchType(TiposBuscaItens.getTipoFromInt(this.controleConfiguracao.getConfigUsr().getTipoBusca()));
            }

            return this.controleConfiguracao.getConfigUsr().getTipoBusca();
        }
        else
        {
            if(this.controleProdutos.getSearchType() != TiposBuscaItens.getTipoFromInt(this.controleConfiguracao.getConfigUsr().getTipoBusca()))
            {
                this.setSearchType(TiposBuscaItens.getTipoFromInt(tipoConsultaItens));
            }

            return tipoConsultaItens;
        }
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


        this.controleDigitacao.getItem().setContribuicaoCalc(pCont);

        return pCont;
    }

    public boolean verificarTitulos()
    {
        boolean possui = this.controleClientes.possuiTitulos(this.venda.getCliente().getCodigoCliente());

        ContasReceberDataAccess crda = new ContasReceberDataAccess(this.context);

        try
        {
            this.contas = crda.getByData(this.venda.getCodigoCliente());

        }
        catch (GenercicException e) { /*****/ }

        return  possui;
    }

    public boolean verificarDevolucoes()
    {
        boolean possui = this.controleClientes.possuiDevolucoes(this.venda.getCliente().getCodigoCliente());

        DevolucaoDataAccess dda = new DevolucaoDataAccess(this.context);

        try
        {
            this.itensDevolvidos = dda.getByData(this.venda.getCodigoCliente());

        }
        catch (GenercicException e) { /*****/ }

        return  possui;
    }

    public ArrayList<String> buscarTitulosItens()
    {
        if(this.verificarTipo() == 3)
        {
            String str_ret = "";
            ArrayList<String> ret = new ArrayList<>();

            try
            {

                for(ContasReceber c : this.contas)
                {
                    str_ret = c.getDocumento() + " - " + c.getEmissao() + " - " + c.getVencimento() + " - " +
                            c.getValor() + " - " + c.getTipo();

                    ret.add(str_ret);
                }
            }
            catch (Exception e) { ret.add("Não foram encontrados itens para exibição"); }


            return ret;
        }
        else
        {
            String str_ret = "";
            ArrayList<String> ret = new ArrayList<>();

            ItemDataAccess ida = new ItemDataAccess(this.context);
            ManipulacaoStrings ms = new ManipulacaoStrings();

            for(ItensVendidos i : this.itensDevolvidos)
            {
                Item it;
                str_ret = "";
                str_ret += i.getItem();

                try
                {
                    it = ida.buscarItemCodigo(i.getItem());

                    str_ret += " - " + ms.comDireita(it.getReferencia(), " ", 10).trim() + " . " +
                            ms.comDireita(it.getDescricao(), " ", 25).trim() + " . " +
                            ms.comDireita(it.getComplemento(), " ", 15).trim() + " - ";
                }
                catch (GenercicException e) { str_ret += " -  .  .  - "; }

                str_ret += i.getQuantidade();

                ret.add(str_ret);
            }

            return ret;
        }
    }

    public int verificarTipo()
    {
        ArrayList<String> detalhes = new ArrayList<>();

        int tipo = 3;

        try
        {
            if(this.contas.size() <= 0 || this.titulosExibidos == true){ tipo = 2; }
        } catch (Exception e ) { tipo = 2; }


        return tipo;
    }

    public ArrayList<String> buscarDetalhes()
    {
        ArrayList<String> detalhes = new ArrayList<>();

        int tipo = this.verificarTipo();

        switch (tipo)
        {
            case 2:/*DEVOLUÇÕES*/
                float valorDevolvido = 0;
                detalhes.add(this.venda.getCliente().getRazao());
                detalhes.add("Qtd.: " + String.valueOf(this.itensDevolvidos.size()));

                for(int i = 0; i < this.itensDevolvidos.size(); i++)
                    valorDevolvido += this.itensDevolvidos.get(i).getValorLiquido();

                detalhes.add("Valor: " + String.valueOf(valorDevolvido));
                break;
            case 3:/*TITULOS*/
                float total = 0;
                float naoVencidos = 0;
                float vencidos = 0;
                detalhes.add(this.venda.getCliente().getRazao());
                detalhes.add("Qtd.: " + String.valueOf(this.contas.size()));

                for(ContasReceber c : this.contas)
                {
                    total += c.getValor();
                    Date dVencimento = new Date(c.getVencimento());
                    Date dHoje = new Date();
                    int comp = dVencimento.compareTo(dHoje);

                    if (comp == 1) { naoVencidos += c.getValor(); }
                    else { vencidos += c.getValor(); }
                }

                detalhes.add("Valor total: " + String.valueOf(total));
                detalhes.add("N. Vencidos: " + String.valueOf(naoVencidos));
                detalhes.add("Vencidos: " + String.valueOf(vencidos));
                break;
        }

        return detalhes;
    }

    public void removerItem(int posicao)
    {
        alterarCampanhasTabloides(posicao);
        if(this.itensVendidos.get(posicao).getFlex() > 0 && !this.itensVendidos.get(posicao).isDigitadoSenha())
        {
            this.controleConfiguracao.setSaldoAtual(this.controleConfiguracao.getSaldoAtual() +
                    (this.itensVendidos.get(posicao).getFlex() * this.itensVendidos.get(posicao).getQuantidade()));
        }

        this.itensVendidos.remove(posicao);
    }

    public void alterarCampanhasTabloides(int posicao) //pensar depois
    {
        updateTabloides(posicao);
        updateCampanhas(posicao);
    }

    protected void updateTabloides(int posicao)
    {
        Grupo gItem = this.controleProdutos.getGrupoItem(this.itensVendidos.get(posicao).getItem());

        if(this.campanhaGrupos != null && this.campanhaGrupos.size() > 0)
        {
            for (CampanhaGrupo cg : this.campanhaGrupos)/*O código nesse for está duplicado resolver*/
            {
                if(cg.getGrupo().equals(gItem))
                {
                    int totalCampanha = cg.getQuantidadeVendida();
                    totalCampanha -= this.itensVendidos.get(posicao).getQuantidade();

                    if(totalCampanha < cg.getQuantidade() && cg.getDescontoAplicado() > 0)
                    {
                        for(ItensVendidos iv : this.itensVendidos)
                        {
                            Grupo gIv = this.controleProdutos.getGrupoItem(iv.getItem());
                            if(gIv.equals(cg.getGrupo()))
                            {
                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setDescontoCG(0);
                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).
                                        setDescontoCampanha(this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCP() > 0);

                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setValorLiquido(
                                        this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getValorDigitado());

                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setTotal(this.calcularTotal
                                        (
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getQuantidade(),
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getValorDigitado(),
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDesconto(),
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCG(),
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCP(), 0,
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getItem()
                                        ));
                            }
                        }

                        this.campanhaGrupos.get(this.campanhaGrupos.indexOf(cg)).setQuantidadeVendida(totalCampanha);
                        this.campanhaGrupos.get(this.campanhaGrupos.indexOf(cg)).setDescontoAplicado(0);
                    }
                }
            }
        }
    }

    protected void updateCampanhas(int posicao)
    {
        Grupo gItem = this.controleProdutos.getGrupoItem(this.itensVendidos.get(posicao).getItem());

        if(this.campanhaProdutos != null && this.campanhaProdutos.size() > 0)
        {
            for (CampanhaProduto cp : this.campanhaProdutos)
            {
                for(Integer item : cp.getItens())
                {
                    if(item == this.itensVendidos.get(posicao).getItem())
                    {
                        float totalCampanha = cp.getQuantidadeVendida();
                        totalCampanha -= this.itensVendidos.get(posicao).getQuantidade();

                        if(totalCampanha < cp.getQuantidade()/* && cp.getDescontoAplicado() > 0*/)
                        {
                            for(ItensVendidos iv : this.itensVendidos)
                            {
                                Grupo gIv = this.controleProdutos.getGrupoItem(iv.getItem());
                                if(iv.getItem() == item)
                                {
                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setDescontoCP(0);
                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).
                                            setDescontoCampanha(this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCG() > 0);

                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setValorLiquido(
                                            this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getValorDigitado());

                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setTotal(this.calcularTotal
                                            (
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getQuantidade(),
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getValorDigitado(),
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDesconto(),
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCG(),
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCP(), 0,
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getItem()
                                            ));
                                }
                            }

                            this.campanhaProdutos.get(this.campanhaProdutos.indexOf(cp)).setQuantidadeVendida(totalCampanha);
                            this.campanhaProdutos.get(this.campanhaProdutos.indexOf(cp)).setDescontoAplicado(0);
                        }
                    }
                }
            }
        }
    }


    public String getDescricaoItem(int posicao)
    {
        int codigo = this.itensVendidos.get(posicao).getItem();
        //int newP = this.controleProdutos.getItemPosicao(codigo);

        String[] separated = this.controleProdutos.getItemStr(codigo).split(" . ");

        return separated[1].trim();

        //return this.controleProdutos.getItem(newP).getDescricao();
    }

    public void alterarItem(int posicao)
    {
        alterarCampanhasTabloides(posicao);

        int codigo = this.itensVendidos.get(posicao).getItem();
        int newP = this.controleProdutos.getItemPosicao(codigo);

        this.controleDigitacao.setItem(this.controleProdutos.getItemAlteracao
                (codigo, tabela, this.controleConfiguracao.getConfigUsr().getTipoOredenacao()));
        this.controleDigitacao.setDadosVendaItem(this.controleProdutos.dadosVendaAlteracao
                (codigo, this.tabela, this.controleConfiguracao.getConfigUsr().getTabelaMinimo()));

        /*
        this.controleDigitacao.setDadosVendaItem(this.controleProdutos.dadosVenda
                (newP, this.tabela, this.controleConfiguracao.getConfigUsr().getTabelaMinimo()));
        */
    }

    public int pesquisaInicial()
    {
        return this.controleConfiguracao.getConfigUsr().getTipoBusca();
    }

    public ArrayList<String> exibirPromocoes()
    {
        PromocaoDataAccess pda = new PromocaoDataAccess(this.context);
        ArrayList<Promocao> promocoes = new ArrayList<>();
        try { promocoes = pda.buscarPromocao(this.controleDigitacao.getItem().getCodigo()); }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> lista = new ArrayList<>();

        if(promocoes.size() > 0)
            for (Promocao p : promocoes)
                lista.add(p.toDisplay());
        else
            lista.add(this.context.getString(R.string.sem_promocoes));

        return lista;
        /*
        Toast.makeText(this.context
                ,"Valores promocionais encontrados para o item:\n" + lista.toString()
                , Toast.LENGTH_LONG).show();
        */
    }

    public void acertarSaldo()
    {
        float atual = this.controleConfiguracao.getSaldoAtual();
        float atualizar = 0;
        float valor = Float.parseFloat(String.valueOf(this.venda.getDesconto()));
        atualizar = atual + valor;
        this.controleConfiguracao.setSaldoAtual(atualizar);
        this.venda.setDesconto(0.0);
    }

    public Boolean verificarSaldo()
    {
        //TODO: Talvez seja necessário retorna o saldo caso se selecione uma natureza diferente.
        //Ps.: Isso não existia na versão anterior do sistema.
        float atual = this.controleConfiguracao.getSaldoAtual();
        float valor = Float.parseFloat(String.valueOf(this.controleSalvar.getTotal()));
        Boolean aceito = atual >= valor;

        if(aceito)
            this.controleConfiguracao.setSaldoAtual(atual - valor);

        return aceito;
    }

    public Boolean verificarSaldoG()
    {
        //TODO: Talvez seja necessário retorna o saldo caso se selecione uma natureza diferente.
        //Ps.: Isso não existia na versão anterior do sistema.
        float atual = this.controleConfiguracao.getSaldoAtual();
        float valor = Float.parseFloat(String.valueOf(this.controleSalvar.getTotal()));

        float flexItensAgora = 0;

        for(ItensVendidos iv : this.itensVendidos)
        {
            flexItensAgora += iv.getFlex();
        }

        atual += flexItensAgora;

        Boolean aceito = atual >= valor;

        if(aceito)
            this.controleConfiguracao.setSaldoAtual(atual - valor);

        return aceito;
    }


    public int getQuantidadeRemover() {
        return quantidadeRemover;
    }

    public void setQuantidadeRemover(int quantidadeRemover) {
        this.quantidadeRemover = quantidadeRemover;
    }

}
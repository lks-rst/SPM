package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.CampanhaProduto;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CidadeDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 02/08/2016.
 */
public abstract class EfetuarPedidos
{
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
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    public EfetuarPedidos(Context ctx)
    {
        this.context = ctx;
        this.controleConfiguracao = new ConfigurarSistema(ctx);
        this.controleClientes = new ConsultarClientes(ctx);
        this.controleProdutos = new ConsultarProdutos(ctx);
        this.controleSalvar = new SalvarPedido();
        this.controleDigitacao = new InserirItemPedidos(this.getConfiguracaoVendaItem());

        this.itensVendidos = new ArrayList<>();
        this.venda = new Venda();

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
    public void selecionarCliente() { /*****/ }

    public void selecionarNatureza() { /*****/ }

    public void selecionarPrazo() { /*****/ }

    public void aplicarDesconto(float desconto) { /*****/ }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    public ArrayList<String> listarCLientes() throws GenercicException
    {
        return this.controleClientes.exibirLista();
    }

    public ArrayList<String> listarNaturezas(Boolean especial) throws GenercicException
    {
        this.getNaturezasList(especial);
        ArrayList<String> lista = new ArrayList<>();
        for(Natureza n : this.listaNaturezas) lista.add(n.toDisplay());

        return lista;
    }

    public ArrayList<String> listarPrazos(int position) throws GenercicException
    {
        String prazo = this.getPrazoNatureza(position);
        this.getPrazosList(prazo);
        ArrayList<String> lista = new ArrayList<>();
        for(Prazo p : this.listaPrazos) lista.add(p.toDisplay());

        return lista;
    }

    public Boolean permitirClick(int id)
    {
        Boolean click = false;

        switch (id)
        {
            case R.id.fdcSpnrClientes :
                click = this.clientIsClicable();
            break;
            case R.id.fdcSpnrNaturezas :
                click = this.naturezaIsClicable();
            break;
            case R.id.fdcSpnrPrazos :
                click = this.prazoIsClicable();
            break;
            case R.id.ffpSpnrNaturezas :
                click = this.naturezaIsClicableEnd();
            break;
            case R.id.ffpSpnrPrazos :
                click = this.prazoIsClicableEnd();
            break;
        }

        return click;
    }

    public int getTabela() { return this.tabela; }

    public String getDesdobramentoPrazo()
    {
        int i = 0;

        for(i = 0; i < this.listaPrazos.size(); i++)
        {
            if (this.listaPrazos.get(i).getCodigo() == this.codigoPrazo)
                break;
        }

        return i <= this.listaPrazos.size() ? this.listaPrazos.get(i).getDesdobramento() : "--";
    }

    public String selecionarCliente(int posicao)
    {
        this.venda.setCliente(this.controleClientes.getCliente(posicao));
        this.codigoNatureza = this.controleClientes.getCliente(posicao).getNatureza();
        this.codigoPrazo = this.controleClientes.getCliente(posicao).getPrazo();

        return this.controleClientes.getCliente(posicao).toString();
    }

    public int buscarNatureza()
    {
        for (Natureza n : this.listaNaturezas)
            if (n.getCodigo() == this.codigoNatureza)
                return this.listaNaturezas.indexOf(n);

        return -1;
    }

    public int buscarPrazo()
    {
        for (Prazo p : this.listaPrazos)
            if (p.getCodigo() == this.codigoPrazo)
                return this.listaPrazos.indexOf(p);

        return -1;
    }

    public void setPrazo(int posicao)
    {
        this.codigoPrazo = this.listaPrazos.get(posicao).getCodigo();

        for (Prazo p : this.listaPrazos)
        {
            if (p.getCodigo() == this.codigoPrazo)
            {
                this.tabela = this.listaPrazos.get(this.listaPrazos.indexOf(p)).getTabela();
                this.venda.setTabela(this.tabela);
                break;
            }
        }
    }

    public int itensVendidos() { return this.itensVendidos.size(); }

    public float valorVendido()
    {
        float valor = 0;
        for(int i = 0; i < this.itensVendidos.size(); i++)
            valor += this.itensVendidos.get(i).getTotal();

        return valor;
    }

    public String listarVendidos()
    {
        String itens = "";
        for(int i = 0; i < this.itensVendidos.size(); i++)
            itens += (this.itensVendidos.get(i).toString() + "\n");

        return itens;
    }

    public String cabecahoPedido(int campo)
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

    public ArrayList<String> listarItens() throws GenercicException
    {
        return this.controleProdutos.buscarItens(this.tabela);
    }

    public ArrayList<String> listarResumo() throws GenercicException
    {
        ArrayList<String> itens = new ArrayList<>();

        for (int i = 0; i < this.itensVendidos.size(); i++)
        {
            itens.add(this.itensVendidos.get(i).toDisplay());
        }

        return itens;
    }

    public void indicarTipoBuscaItem(int tipo) { this.controleProdutos.setSearchType(tipo); }

    public void indicarDadosBuscaItens(String dados){ this.controleProdutos.setSearchData(dados); }

    public void selecionarItem(int posicao)
    {
        this.controleDigitacao.setItem(this.controleProdutos.getItem(posicao));
        this.controleDigitacao.setDadosVendaItem(this.controleProdutos.dadosVenda
                (posicao, this.tabela, this.controleConfiguracao.getConfigUsr().getTabelaMinimo()));
    }

    public Boolean temValorMinimo()
    {
        return this.controleDigitacao.temMinimo();
    }

    public Boolean temPromocao()
    {
        return this.controleDigitacao.temPromocao();
    }

    public void buscarPromocoes()
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

        Toast.makeText(this.context
                ,"Valores promocionais encontrados para o item:\n" + lista.toString()
                , Toast.LENGTH_LONG).show();
    }

    public String buscarMinimoTabela()
    {
        Toast.makeText(this.context
                ,"Valor minimo encontrado para o item: " + this.controleDigitacao.buscarMinimo()
                , Toast.LENGTH_LONG).show();

        return this.controleDigitacao.buscarMinimo();
    }

    public String getItem() { return this.controleDigitacao.getItem().toDisplay(); }

    public Boolean alteraValor(String campo)
    {
        return this.controleConfiguracao.alteraValor(campo);
        /*
        if(campo.equalsIgnoreCase("v")) { return this.controleConfiguracao.alteraValor(campo); }
        else
        {
            return !this.controleConfiguracao.alteraValor(campo);
        }
        */
    }

    public Boolean alteraValorFim(int campo)
    {
        return this.controleConfiguracao.alteraValorFim(campo);
    }

    public String getValor() { return this.controleDigitacao.getValor(); }

    public String getQtdMinimaVenda() { return this.controleDigitacao.getQtdMinimaVenda(); }

    public String getUnidade() { return this.controleDigitacao.getUnidade(); }

    public String getUnidadeVenda() { return this.controleDigitacao.getUnidadeVenda(); }

    public String getCodigoBarras() { return this.controleDigitacao.getCodigoBarras(); }

    public String getQtdCaixa() { return this.controleDigitacao.getQtdCaixa(); }

    public String getValorUnitario() { return this.controleDigitacao.getValorUnitario(); }

    public String getMarkup() { return this.controleConfiguracao.getMarkup(); }

    public String calcularTotal() { return String.valueOf(this.controleDigitacao.calcularTotal()); }

    public void setQuantidade(String quantidade) { this.controleDigitacao.setQuantidade(Integer.parseInt(quantidade)); }

    public void setValor(String valor)
    {
        try { this.controleDigitacao.setValor(Float.parseFloat(valor)); }
        catch (Exception e){ this.controleDigitacao.setValor(Float.parseFloat("0")); }
    }

    public void setDesconto(String desconto) { this.controleDigitacao.setDesconto(Float.parseFloat(desconto));}

    public void setAcrescimo(String acrescimo) { this.controleDigitacao.setAcrescimo(Integer.parseInt(acrescimo)); }

    public String calcularPPC(String mkp, String vlr)
    {
        float markup = Float.parseFloat(mkp);
        float valor = Float.parseFloat(vlr);
        float ppc = valor + (valor * ( markup / 100));

        return String.valueOf(ppc);
    }

    public Boolean confirmarItem()
    {
        Boolean alteracao = false;
        int posicao = -1;

        ItensVendidos item = this.controleDigitacao.confirmarItem(
            this.controleConfiguracao.descontoMaximo(), this.controleConfiguracao.alteraValor("d"), this.context);

        if(item != null)
        {
            if(this.finalizarItem())
            {
                for (ItensVendidos i : this.itensVendidos)
                {
                    if (item.equals(i))
                    {
                        alteracao = true;
                        posicao = this.itensVendidos.indexOf(i);
                    }
                }

                if (alteracao)
                {
                    if(this.itensVendidos.get(posicao).getFlex() > 0)
                    {
                        this.controleConfiguracao.setSaldoAtual(this.controleConfiguracao.getSaldoAtual() +
                            (this.itensVendidos.get(posicao).getFlex() * this.itensVendidos.get(posicao).getQuantidade()));
                    }

                    this.itensVendidos.set(posicao, item);
                    /*
                    TODO: Ajustar as quantidades das campanhas relacionadas ao item;
                    TODO: Verificar os descontos das campanhas alteradas;
                     */
                    Toast.makeText(context, "Item alterado!", Toast.LENGTH_LONG).show();
                }
                else { this.itensVendidos.add(item); }

                try
                {
                    if(this.itensVendidos.get(posicao).getFlex() > 0)
                    {
                        this.controleConfiguracao.setSaldoAtual(this.controleConfiguracao.getSaldoAtual() -
                            (this.itensVendidos.get(posicao).getFlex() * this.itensVendidos.get(posicao).getQuantidade()));
                    }
                }
                catch (Exception exeption)
                {
                    if(this.itensVendidos.get(0).getFlex() > 0)
                    {
                        this.controleConfiguracao.setSaldoAtual(this.controleConfiguracao.getSaldoAtual() -
                            (this.itensVendidos.get(0).getFlex() * this.itensVendidos.get(0).getQuantidade()));
                    }
                }

                return true;
            }
            else return false;
        }
        else return false;
    }

    public void indicarTotal(float total) { this.controleSalvar.setTotal(total);}

    public int restoreClient() { return this.controleClientes.restoreClient(); }

    public void setDescontoPedido(String desconto) { this.controleSalvar.setDesconto(Float.parseFloat(desconto));}

    public void setFrete(String desconto) { this.controleSalvar.setFrete(Float.parseFloat(desconto));}

    public float recalcularTotalPedido() { return this.controleSalvar.calcularTotal(); }

    public int finalizarPedido()
    {
        NaturezaDataAccess nda = new NaturezaDataAccess(this.context);

        if(this.controleSalvar.verificarMinimo(nda.buscarNatureza(this.codigoNatureza).getMinimo()
                , this.controleConfiguracao.pedidoMinimo()))
        {
            if(this.controleSalvar.verificarSaldo(this.controleConfiguracao.getSaldoAtual()))
            {
                this.venda.setItens(this.itensVendidos);
                this.venda.setValor(Double.parseDouble(String.valueOf(this.valorVendido())));
                this.venda.setCodigoCliente(this.controleClientes.getCodigoClienteSelecionado());
                this.venda.setDesconto(Double.parseDouble(String.valueOf(this.controleSalvar.getDesconto())));
                this.venda.setData(this.dataSistema());
                this.venda.setHora(this.horaSistema());
                this.venda.setNatureza(this.codigoNatureza);
                /*
                TOD: Acrescentar tipo de venda a abertura do pedido;
                 */
                this.venda.setTipo("PD");

                Prazo p = new Prazo();
                p.setCodigo(this.codigoPrazo);
                this.venda.setPrazo(p);

                if(this.controleSalvar.salvarPedido(this.context, this.venda))
                {
                    this.controleSalvar.atualizarSaldo(this.context, this.controleConfiguracao.getSaldoAtual());
                    return 1;
                }
                else
                {
                    Toast.makeText(context, "ATENÇÃO!\nOcorreu uma falha ao salvar os dados.", Toast.LENGTH_LONG).show();
                    return 0;
                }
            }
            else
            {
                Toast.makeText(context, "ATENÇÃO!\nValor vendido abaixo do valor minimo de venda"
                        , Toast.LENGTH_LONG).show();
                return 0;
            }
        }
        else
        {
            Toast.makeText(context, "ATENÇÃO!\nValor vendido abaixo do valor minimo de venda"
                    , Toast.LENGTH_LONG).show();
            return 0;
        }
    }

    public String buscarDadosVenda(int campo)
    {
        //return this.controleClientes.buscarDadosCliente(campo);
        String retorno;
        switch (campo)
        {
            case R.id.fdcEdtDca :
                retorno = String.format(this.context.getResources().getString(R.string.str_flex)
                        , String.valueOf(this.controleConfiguracao.buscarFlex()));
                break;
            default:
                retorno = "--";
        }
        return retorno;
    }

    public String buscarDadosCliente(int campo)
    {
        //return this.controleClientes.buscarDadosCliente(campo);
        String retorno;
        switch (campo)
        {
            case R.id.fdcEdtMedia :
                retorno = String.format(this.context.getResources().getString(R.string.str_media)
                    , String.valueOf(this.venda.getCliente().getMediaCompras()));
                break;
            case R.id.fdcEdtQuantidade :
                retorno = String.format(this.context.getResources().getString(R.string.str_qtd)
                    , String.valueOf(this.venda.getCliente().getMediaIndustrializados()));
                break;
            case R.id.fdcEdtLimite :
                retorno = String.valueOf(this.venda.getCliente().getLimiteCredito());
                break;
            case R.id.fdcEdtReal :
                retorno = String.valueOf(this.venda.getCliente().getRealizado());
                break;
            case R.id.fdcEdtMeta :
                retorno = String.valueOf(this.venda.getCliente().getMetaPeso());
                break;
            case R.id.fdcEdtUltima :
                retorno = this.venda.getCliente().getDataUltimaCompra();
                break;
            case R.id.fdcEdtCel :
                retorno = this.venda.getCliente().getCelular();
                break;
            case R.id.fdcEdtFone :
                retorno = this.venda.getCliente().getTelefone();
                break;
            case R.id.fdcEdtEnd :
                retorno = this.venda.getCliente().getEndereco();
                break;
            case R.id.fdcEdtUf :
                retorno = this.buscarEstado(this.venda.getCliente().getCodigoCidade());
                break;
            case R.id.fdcEdtCidade :
                retorno = this.buscarCidade(this.venda.getCliente().getCodigoCidade());
                break;
            default:
                retorno = "--";
        }
        return retorno;
    }

    public int verificarTabloides()
    {
        int codigo = this.itensVendidos.get(this.itensVendidos.size() - 1).getItem();
        Item produto = null;
        try { produto = this.controleProdutos.getItemCodigo(codigo); }
        catch (GenercicException e)
        {
            produto = null;
            e.printStackTrace();
        }

        if(produto == null)
            return -1;

        int posicaoGrupo = -1;

        if(this.campanhaGrupos != null && this.campanhaGrupos.size() > 0)
        {
            for(CampanhaGrupo c : this.campanhaGrupos)
            {
                if ((c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() ==
                        produto.getSubGrupo() && c.getGrupo().getDivisao() == produto.getDivisao()) ||
                    (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() ==
                            produto.getSubGrupo() && c.getGrupo().getDivisao() == 0) ||
                    (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() ==
                            0 && c.getGrupo().getDivisao() == 0))
                {
                    posicaoGrupo = this.campanhaGrupos.indexOf(c);
                    break;
                }
            }
        }
        else
        {
            this.campanhaGrupos = new ArrayList<>();
            ConsultaMinimosGravososKitsCampanhas campanhas = new ConsultaMinimosGravososKitsCampanhas(this.context);
            try
            {
                CampanhaGrupo camp = campanhas.buscarCampanha(codigo);

                if(camp == null)
                    posicaoGrupo = -1;
                else
                {
                    this.campanhaGrupos.add(camp);
                    posicaoGrupo = 0;
                }
            }
            catch (GenercicException e)
            {
                e.printStackTrace();
                posicaoGrupo = -1;
            }
        }

        if(posicaoGrupo != -1)
        {
            this.campanhaGrupos.get(posicaoGrupo).setQuantidadeVendida(
                this.campanhaGrupos.get(posicaoGrupo).getQuantidadeVendida() +
                (int) this.itensVendidos.get(this.itensVendidos.size() -1).getQuantidade());

            if(this.campanhaGrupos.get(posicaoGrupo).getQuantidadeVendida() >= this.campanhaGrupos.get(posicaoGrupo).getQuantidade())
            {
                if(this.campanhaGrupos.get(posicaoGrupo).getDescontoAplicado() > 0)
                {
                    this.itensVendidos.get(this.itensVendidos.size() - 1).setDescontoCG(this.campanhaGrupos.get(posicaoGrupo).getDescontoAplicado());

                    this.itensVendidos.get(this.itensVendidos.size() - 1).setValorLiquido(
                        this.itensVendidos.get(this.itensVendidos.size() - 1).getValorDigitado() -
                        ((this.itensVendidos.get(this.itensVendidos.size() - 1).getValorDigitado() *
                        this.campanhaGrupos.get(posicaoGrupo).getDescontoAplicado()) / 100));

                    this.itensVendidos.get(this.itensVendidos.size() - 1).setTotal(this.calcularTotal
                    (
                        this.itensVendidos.get(this.itensVendidos.size() - 1).getQuantidade(),
                        this.itensVendidos.get(this.itensVendidos.size() - 1).getValorDigitado(),
                        this.itensVendidos.get(this.itensVendidos.size() - 1).getDesconto(),
                        this.itensVendidos.get(this.itensVendidos.size() - 1).getDescontoCG(),
                        this.itensVendidos.get(this.itensVendidos.size() - 1).getDescontoCP(), 0
                    ));

                    return -1;
                }
                else
                    return posicaoGrupo;
            }
            else
                return -1;
        }
        else { return -1; }
    }

    public int aplicarDescontoTabloide(float percentual, int posicao)
    {
        if(percentual >= 0)
        {
            if (this.campanhaGrupos.get(posicao).getDesconto() >= percentual)
            {
                this.campanhaGrupos.get(posicao).setDescontoAplicado(percentual);

                for(int i = 0; i < this.itensVendidos.size(); i++)
                {
                    Item produto = null;
                    try { produto = this.controleProdutos.getItemCodigo(this.itensVendidos.get(i).getItem()); }
                    catch (GenercicException e) { e.printStackTrace(); }

                    if((this.campanhaGrupos.get(posicao).getGrupo().getGrupo() == produto.getGrupo() &&
                            this.campanhaGrupos.get(posicao).getGrupo().getSubGrupo() == produto.getSubGrupo() &&
                            this.campanhaGrupos.get(posicao).getGrupo().getDivisao() == produto.getDivisao()) ||
                        (this.campanhaGrupos.get(posicao).getGrupo().getGrupo() == produto.getGrupo() &&
                            this.campanhaGrupos.get(posicao).getGrupo().getSubGrupo() == produto.getSubGrupo() &&
                            this.campanhaGrupos.get(posicao).getGrupo().getDivisao() == 0) ||
                        (this.campanhaGrupos.get(posicao).getGrupo().getGrupo() == produto.getGrupo() &&
                            this.campanhaGrupos.get(posicao).getGrupo().getSubGrupo() == 0 &&
                            this.campanhaGrupos.get(posicao).getGrupo().getDivisao() == 0))
                    {
                        this.itensVendidos.get(i).setDescontoCG(percentual);
                        this.itensVendidos.get(i).setValorLiquido(this.itensVendidos.get(i)
                            .getValorDigitado() - ((this.itensVendidos.get(i).getValorDigitado() * percentual) / 100));
                        this.itensVendidos.get(i).setTotal(this.calcularTotal(
                            this.itensVendidos.get(i).getQuantidade(), this.itensVendidos.get(i).getValorDigitado(),
                            this.itensVendidos.get(i).getDesconto(), this.itensVendidos.get(i).getDescontoCG(),
                            this.itensVendidos.get(i).getDescontoCP(), 0));
                        /*
                        this.itensVendidos.get(i).setTotal(
                            this.itensVendidos.get(i).getValorLiquido() * this.itensVendidos.get(i).getQuantidade());
                         */
                    }
                }
                return -1;
            }
            else
            {
                Toast.makeText(context, "Desconto acima do permitido", Toast.LENGTH_LONG).show();
                return posicao;
            }
        }
        else
        {
            Toast.makeText(context, "Desconto não pode ser zero (0)!", Toast.LENGTH_LONG).show();
            return posicao;
        }
    }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    protected String buscarCidade(int codigo)
    {
        CidadeDataAccess cda = new CidadeDataAccess(this.context);
        try { return cda.getByData(codigo).getNome(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            return "";
        }
    }

    protected String buscarEstado(int codigo)
    {
        CidadeDataAccess cda = new CidadeDataAccess(this.context);
        try { return cda.getByData(codigo).getUf(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            return "";
        }
    }
    protected void getNaturezasList(Boolean especial) throws GenercicException
    {
        NaturezaDataAccess nda = new NaturezaDataAccess(this.context);

        if (especial)
            this.listaNaturezas = nda.buscarRestrito();
        else
            this.listaNaturezas = nda.buscarTodos();
    }

    protected void getPrazosList(String prazo) throws GenercicException
    {
        PrazoDataAccess pda = new PrazoDataAccess(this.context);

        if (prazo.equalsIgnoreCase("0") || prazo.equalsIgnoreCase("a"))
            this.listaPrazos = pda.buscarRestrito();
        else
            this.listaPrazos = pda.buscarTodos();
    }

    protected String getPrazoNatureza(int position)
    {
        this.codigoNatureza = this.listaNaturezas.get(position).getCodigo();
        return this.listaNaturezas.get(position).getPrazo();
    }

    protected String getConfiguracaoVendaItem() { return "Configuracao de venda dos itens"; }

    protected String dataSistema()
    {
        Date today;
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");
        today = new Date();
        return this.strDataBanco(sf.format(today));
    }

    protected String horaSistema()
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

    protected String strDataBanco(String data)
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

    protected Boolean clientIsClicable() { return this.itensVendidos.size() <= 0; }

    protected Boolean naturezaIsClicable()
    {
        boolean clienteLiberado = this.controleClientes.clienteAlteraTabela();

        if(clienteLiberado && (this.itensVendidos.size() <= 0))
        {
            if(this.controleConfiguracao.getConfigVda().getAlteraNaturezaInicio()) { return true; }
            else { return false; }
        }
        else { return false; }
    }

    protected Boolean prazoIsClicable()
    {
        boolean clienteLiberado = this.controleClientes.clienteAlteraTabela();

        if(clienteLiberado && (this.itensVendidos.size() <= 0))
        {
            if(this.controleConfiguracao.getConfigVda().getAlteraPrazoInicio()) { return true; }
            else { return false; }
        }
        else { return false; }
    }

    protected Boolean naturezaIsClicableEnd()
    {
        boolean clienteLiberado = this.controleClientes.clienteAlteraTabela();

        if(clienteLiberado)
        {
            if(this.controleConfiguracao.getConfigVda().getAlteraNaturezaFim()) { return true; }
            else { return false; }
        }
        else { return false; }
    }

    protected Boolean prazoIsClicableEnd()
    {
        boolean clienteLiberado = this.controleClientes.clienteAlteraTabela();

        if(clienteLiberado)
        {
            if(this.controleConfiguracao.getConfigVda().getAlteraPrazoFim()) { return true; }
            else { return false; }
        }
        else { return false; }
    }

    protected Boolean finalizarItem()
    {
        if(this.controleConfiguracao.formaDesconto() == 0)
        {
            if (this.controleConfiguracao.contribuicaoIdeal())
                return true;
            else
                return false;
        }
        else
        {
            float saldo = this.controleConfiguracao.getSaldoAtual();
            if(saldo - this.controleDigitacao.diferencaFlex(this.context) >= 0)
                return true;
            else
                return false;
        }
    }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    public ArrayList<CampanhaGrupo> getCampanhaGrupos() { return campanhaGrupos; }

    protected float calcularTotal(float quantidade, float valor, float desconto, float grupo, float produtos, float acrescimo)
    {
        return (valor
                - (valor * (desconto / 100))
                - (valor * (grupo/ 100))
                - (valor * (produtos / 100))
                + (valor * (acrescimo / 100)))
                * quantidade ;
    }

    public void addObs(String s, int tipo)
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
}
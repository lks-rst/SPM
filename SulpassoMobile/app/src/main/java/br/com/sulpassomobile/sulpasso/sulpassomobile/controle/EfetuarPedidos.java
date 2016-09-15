package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;

/*
    TODO: Buscar tabela minima da configuração da venda (118)
 */
/**
 * Created by Lucas on 02/08/2016.
 */
public class EfetuarPedidos
{
    private int codigoNatureza;
    private int codigoPrazo;
    private int tabela;

    private ConfigurarSistema controleConfiguracao;
    private ConsultarClientes controleClientes;
    private ConsultarProdutos controleProdutos;
    private SalvarPedido controleSalvar;
    private InserirItemPedidos controleDigitacao;

    private Venda venda;

    private ArrayList<Natureza> listaNaturezas;
    private ArrayList<Prazo> listaPrazos;
    private ArrayList<ItensVendidos> itensVendidos;

    private Context context;
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
                click = this.naturezaPrazoIsClicable();
                break;
            case R.id.fdcSpnrPrazos :
                click = this.naturezaPrazoIsClicable();
                break;
        }

        return click;
    }

    public int getTabela() { return this.tabela; }

    public String selecionarCliente(int posicao)
    {
        this.codigoNatureza = this.controleClientes.getCliente(posicao).getCodigoCliente();
        this.codigoPrazo = this.controleClientes.getCliente(posicao).getCodigoCliente();

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

    public ArrayList<String> listarItens() throws GenercicException
    {
        return this.controleProdutos.buscarItens(this.tabela);
    }

    public void indicarTipoBuscaItem(int tipo) { this.controleProdutos.setSearchType(tipo); }

    public void indicarDadosBuscaItens(String dados){ this.controleProdutos.setSearchData(dados); }

    public void selecionarItem(int posicao)
    {
        this.controleDigitacao.setItem(this.controleProdutos.getItem(posicao));
        this.controleDigitacao.setDadosVendaItem(
            this.controleProdutos.dadosVenda(posicao, this.tabela, 2));
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
        Toast.makeText(this.context
                ,"Valores promocionais encontrados para o item: "
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
        if(campo.equalsIgnoreCase("v")) { return this.controleConfiguracao.alteraValor(campo); }
        else
        {
            return !this.controleConfiguracao.alteraValor(campo);
        }
    }

    public String getValor() { return this.controleDigitacao.getValor(); }

    public String getQtdMinimaVenda() { return this.controleDigitacao.getQtdMinimaVenda(); }

    public String getUnidade() { return this.controleDigitacao.getUnidade(); }

    public String getUnidadeVenda() { return this.controleDigitacao.getUnidadeVenda(); }

    public String calcularTotal() { return String.valueOf(this.controleDigitacao.calcularTotal()); }

    public void setQuantidade(String quantidade) { this.controleDigitacao.setQuantidade(Integer.parseInt(quantidade)); }

    public void setValor(String valor)
    {
        try { this.controleDigitacao.setValor(Float.parseFloat(valor)); }
        catch (Exception e){ this.controleDigitacao.setValor(Float.parseFloat("0")); }
    }

    public void setDesconto(String desconto) { this.controleDigitacao.setDesconto(Float.parseFloat(desconto));}

    public void setAcrescimo(String acrescimo) { this.controleDigitacao.setAcrescimo(Integer.parseInt(acrescimo)); }

    public Boolean confirmarItem()
    {
        Boolean alteracao = false;
        int posicao = -1;

        ItensVendidos item = this.controleDigitacao.confirmarItem(
            this.controleConfiguracao.descontoMaximo(), this.controleConfiguracao.alteraValor("v"));
        if (item != null)
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
                    this.itensVendidos.set(posicao, item);
                else
                {
                    this.itensVendidos.add(item);
                    Toast.makeText(context, "Item alterado!", Toast.LENGTH_LONG).show();
                }

                this.controleConfiguracao.setSaldoAtual(this.controleDigitacao.diferencaFlex());

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
            this.venda = new Venda();
            this.venda.setItens(this.itensVendidos);
            this.venda.setValor(Double.parseDouble(String.valueOf(this.valorVendido())));
            this.venda.setCodigoCliente(this.controleClientes.getCodigoClienteSelecionado());
            this.venda.setDesconto(Double.parseDouble(String.valueOf(this.controleSalvar.getDesconto())));
            this.venda.setData(this.dataSistema());

            if(this.controleSalvar.salvarPedido(this.context, this.venda)) { return 1; }
            else
            {
                Toast.makeText(context, "ATENÇÃO!\nOcorreu uma falha ao salvar os dados.", Toast.LENGTH_LONG).show();
                return 0;
            }
        }
        else
        {
            Toast.makeText(context, "ATENÇÃO!\nVocê deve vender no minimo", Toast.LENGTH_LONG).show();
            return 0;
        }
    }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    private void getNaturezasList(Boolean especial) throws GenercicException
    {
        NaturezaDataAccess nda = new NaturezaDataAccess(this.context);

        if (especial)
            this.listaNaturezas = nda.buscarRestrito();
        else
            this.listaNaturezas = nda.buscarTodos();
    }

    private void getPrazosList(String prazo) throws GenercicException
    {
        PrazoDataAccess pda = new PrazoDataAccess(this.context);

        if (prazo.equalsIgnoreCase("0") || prazo.equalsIgnoreCase("a"))
            this.listaPrazos = pda.buscarTodos();
        else
            this.listaPrazos = pda.buscarRestrito();
    }

    private String getPrazoNatureza(int position)
    {
        this.codigoNatureza = this.listaNaturezas.get(position).getCodigo();
        return this.listaNaturezas.get(position).getPrazo();
    }

    private String getConfiguracaoVendaItem() { return "Configuracao de venda dos itens"; }

    private String dataSistema()
    {
        Date today;
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");
        today = new Date();
        return this.strDataBanco(sf.format(today));
    }

    private String strDataBanco(String data){
        String nova_data = "";
        String[] datas;

        try {
            datas = data.split("/");
            nova_data = datas[2] + "-" + datas[1] + "-" + datas[0];
        } catch (Exception e) {
            nova_data = data;
        }
        return nova_data;
    }

    private Boolean clientIsClicable() { return this.itensVendidos.size() > 0; }

    private Boolean naturezaPrazoIsClicable()
    {
        boolean clienteLiberado = this.controleClientes.clienteAlteraTabela();

        if(clienteLiberado && (this.itensVendidos.size() <= 0)) { return true; }
        else { return false; }
    }

    private Boolean finalizarItem()
    {
        if(this.controleConfiguracao.formaDesconto() == 0)
            if(this.controleConfiguracao.contribuicaoIdeal())
                return true;
            else
                return false;
        else
        {
            float saldo = this.controleConfiguracao.getSaldoAtual();
            if(saldo - this.controleDigitacao.diferencaFlex() >= 0)
                return true;
            else
                return false;
        }
    }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
}
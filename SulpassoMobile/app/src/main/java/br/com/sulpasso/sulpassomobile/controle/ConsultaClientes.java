package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.ContasReceber;
import br.com.sulpasso.sulpassomobile.modelo.Corte;
import br.com.sulpasso.sulpassomobile.modelo.CurvaAbc;
import br.com.sulpasso.sulpassomobile.modelo.Devolucao;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ContasReceberDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.CorteDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.CurvaAbcDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.DevolucaoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PrePedidoDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 25/11/2016 - 08:41 as part of the project SulpassoMobile.
 */
public class ConsultaClientes implements br.com.sulpasso.sulpassomobile.controle.interfaces.ConsultaClientes
{
    private Context ctx;
    private ArrayList<String> lista;
    private ArrayList<Cliente> clientes;
    private ArrayList<Devolucao> devolucoes;
    private ArrayList<Corte> cortes;
    private ArrayList<PrePedido> pedidos;
    private ArrayList<ContasReceber> contas;

    private PrePedido prePedidoDetalhe;

    public ConsultaClientes(Context ctx) { this.ctx = ctx; }

/**********************************CLASS FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para busca de todos os clientes, utilizado para consultas de aniversários e curva abc
     * @return
     */
    private ArrayList<String> buscarClientes()
    {
        ClienteDataAccess cda = new ClienteDataAccess(this.ctx);
        try { this.clientes = cda.getAll(); }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> retorno = new ArrayList<>();

        for(Cliente c : this.clientes) { retorno.add(c.toDisplay()); }

        return retorno;
    }

    private ArrayList<String> buscarTitulos()
    {
        ContasReceberDataAccess crda = new ContasReceberDataAccess(this.ctx);

        try { this.clientes = crda.buscarContas(); }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> retorno = new ArrayList<>();
        for(Cliente c : this.clientes) { retorno.add(c.toDisplay()); }

        return retorno;
    }

    private ArrayList<String> buscarDevolucao()
    {
        DevolucaoDataAccess dda = new DevolucaoDataAccess(this.ctx);
        this.clientes.clear();

        try
        {
            this.devolucoes = dda.getAll();
            for(Devolucao d : this.devolucoes) { this.clientes.add(d.getCliente()); }
        }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> retorno = new ArrayList<>();
        for(Cliente c : this.clientes) { retorno.add(c.toDisplay()); }

        return retorno;
    }

    private ArrayList<String> buscarCorte()
    {
        CorteDataAccess cda = new CorteDataAccess(this.ctx);

        try
        {
            this.cortes = cda.getAll();
            for(Corte d : this.cortes) { this.clientes.add(d.getCliente()); }
        }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> retorno = new ArrayList<>();
        for(Cliente c : this.clientes) { retorno.add(c.toDisplay()); }

        return retorno;
    }

    private ArrayList<String> buscarCorteItens(int posicao)
    {
        String str_ret = "";
        ArrayList<String> ret = new ArrayList<>();
        ArrayList<ItensVendidos> ic = this.cortes.get(posicao).getItensCortados();

        ItemDataAccess ida = new ItemDataAccess(this.ctx);
        ManipulacaoStrings ms = new ManipulacaoStrings();

        for(ItensVendidos i : ic)
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

    private ArrayList<String> buscarDevolucaoItens(int posicao)
    {
        String str_ret = "";
        ArrayList<String> ret = new ArrayList<>();
        ArrayList<ItensVendidos> ic = this.devolucoes.get(posicao).getItensDevolvidos();

        ItemDataAccess ida = new ItemDataAccess(this.ctx);
        ManipulacaoStrings ms = new ManipulacaoStrings();

        for(ItensVendidos i : ic)
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

    private ArrayList<String> buscarTitulosItens(int posicao)
    {
        String str_ret = "";
        ArrayList<String> ret = new ArrayList<>();

        int codigo = this.clientes.get(posicao).getCodigoCliente();

        ContasReceberDataAccess crda = new ContasReceberDataAccess(this.ctx);

        try
        {
            this.contas = crda.getByData(codigo);

            for(ContasReceber c : this.contas)
            {
                str_ret = c.getDocumento() + " - " + c.getEmissao() + " - " + c.getVencimento() + " - " +
                    c.getValor() + " - " + c.getTipo();

                ret.add(str_ret);
            }
        }
        catch (GenercicException e) { ret.add("Não foram encontrados itens para exibição"); }


        return ret;
    }

    private ArrayList<String> buscarPrePedido()
    {
        PrePedidoDataAccess pda = new PrePedidoDataAccess(this.ctx);
        try { this.clientes = pda.getAllClients(); }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> retorno = new ArrayList<>();
        for(Cliente c : this.clientes) { retorno.add(c.toDisplay()); }

        return retorno;
    }

    private ArrayList<String> buscarPositivacao(boolean semana, boolean positivado)
    {
        ClienteDataAccess cda = new ClienteDataAccess(this.ctx);
        try { this.clientes = cda.buscarPositivacao(semana, positivado); }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> retorno = new ArrayList<>();

        for(Cliente c : this.clientes) { retorno.add(c.toDisplay()); }

        return retorno;
    }

    private PrePedido buscarDetalhesPrepedido(int cliente)
    {
        ArrayList<PrePedido> pp = new ArrayList<>();

        PrePedidoDataAccess pda = new PrePedidoDataAccess(this.ctx);
        try { pp = pda.getByData(this.clientes.get(cliente).getCodigoCliente()); }
        catch (GenercicException e) { e.printStackTrace(); }

        return pp.get(0);
    }

    private CurvaAbc buscarCurvaAbc(int cliente)
    {
        ArrayList<CurvaAbc> abc = new ArrayList<>();
        CurvaAbcDataAccess abcdac = new CurvaAbcDataAccess(this.ctx);
        try
        {
            abc = abcdac.getByData(this.clientes.get(cliente).getCodigoCliente());
            if(abc != null && abc.size() > 0) { return abc.get(0); }
            else { return new CurvaAbc(); }
        }
        catch (GenercicException e)
        {
            e.printStackTrace();
            return new CurvaAbc();
        }
    }
/*******************************END OF CLASS FUNCTIONAL METHODS************************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    @Override
    public ArrayList<String> buscarListaClientes() { return this.buscarClientes(); }

    @Override
    public ArrayList<String> buscarListaTitulos() { return this.buscarTitulos(); }

    @Override
    public ArrayList<String> buscarListaDevolucao() { return this.buscarDevolucao(); }

    @Override
    public ArrayList<String> buscarListaCorte() { return this.buscarCorte(); }

    @Override
    public ArrayList<String> buscarItensCorte(int posicao) { return this.buscarCorteItens(posicao); }

    @Override
    public ArrayList<String> buscarItensDevolucao(int posicao) { return this.buscarDevolucaoItens(posicao); }

    @Override
    public ArrayList<String> buscarItensTitulos(int posicao) { return this.buscarTitulosItens(posicao); }

    @Override
    public ArrayList<String> buscarDetalhes(int posicao, int tipo)
    {
        ArrayList<String> detalhes = new ArrayList<>();

        switch (tipo)
        {
            case 1: /*CORTES*/
                detalhes.add(this.cortes.get(posicao).getCliente().getRazao());
                break;
            case 2:/*DEVOLUÇÕES*/
                float valorDevolvido = 0;
                detalhes.add(this.devolucoes.get(posicao).getCliente().getRazao());
                detalhes.add("Qtd.: " + String.valueOf(this.devolucoes.get(posicao).getItensDevolvidos().size()));

                for(int i = 0; i < this.devolucoes.get(posicao).getItensDevolvidos().size(); i++)
                    valorDevolvido += this.devolucoes.get(posicao).getItensDevolvidos().get(i).getValorLiquido();

                detalhes.add("Valor: " + String.valueOf(valorDevolvido));
                break;
            case 3:/*TITULOS*/
                float total = 0;
                float naoVencidos = 0;
                float vencidos = 0;
                detalhes.add(this.clientes.get(posicao).getRazao());
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

    @Override
    public ArrayList<String> buscarListaPrePedido() { return this.buscarPrePedido(); }

    @Override
    public ArrayList<String> buscarListaPositivacao(boolean semana, boolean positivado)
    {
        return this.buscarPositivacao(semana,  positivado);
    }

    @Override
    public String totalPositivacao()
    {
        return String.valueOf(this.clientes.size());
    }

    @Override
    public ArrayList<String> buscarListaClientes(int tipo, String cliente) { return null; }

    @Override
    public ArrayList<String> buscarListaTitulos(int tipo, String cliente) { return null; }

    @Override
    public ArrayList<String> buscarListaDevolucao(int tipo, String cliente) { return null; }

    @Override
    public ArrayList<String> buscarListaCorte(int tipo, String cliente) { return null; }

    @Override
    public ArrayList<String> buscarListaPrePedido(int tipo, String cliente) { return null; }

    @Override
    public ArrayList<String> buscarListaPositivacao(int tipo, String cliente) { return null; }

    @Override
    public ArrayList<String> buscarListaClientes(int tipo, int cidade) { return null; }

    @Override
    public ArrayList<String> buscarListaTitulos(int tipo, int cidade) { return null; }

    @Override
    public ArrayList<String> buscarListaDevolucao(int tipo, int cidade) { return null; }

    @Override
    public ArrayList<String> buscarListaCorte(int tipo, int cidade) { return null; }

    @Override
    public ArrayList<String> buscarListaPrePedido(int tipo, int cidade) { return null; }

    @Override
    public ArrayList<String> buscarListaPositivacao(int tipo, int cidade) { return null; }

    @Override
    public ArrayList<String> detalharCorte(int cliente) { return new ArrayList<>(); }

    @Override
    public ArrayList<String> detalharDevolucao(int cliente) { return new ArrayList<>(); }

    @Override
    public ArrayList<String> detalharTitulos(int cliente) { return new ArrayList<>(); }

    @Override
    public ArrayList<String> detalhesConsulta(int cliente, int tipo) { return new ArrayList<>(); }

    @Override
    public CurvaAbc detalharAbc(int cliente)
    {
        CurvaAbc abc = new CurvaAbc();
        abc = this.buscarCurvaAbc(cliente);

        return abc;
    }

    @Override
    public PrePedido detalharPrePedido(int cliente)
    {
        this.prePedidoDetalhe = this.buscarDetalhesPrepedido(cliente);
        return this.prePedidoDetalhe;
    }

    public String buscarDescricaoItem(int posicao)
    {
        return this.prePedidoDetalhe.getItensVendidos().get(posicao).getDescricao();
    }
/*********************************END OF ITERFACES METHODS*****************************************/
}
package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Corte;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Devolucao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ContasReceberDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CorteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.DevolucaoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrePedidoDataAccess;

/**
 * Created by Lucas on 25/11/2016 - 08:41 as part of the project SulpassoMobile.
 */
public class ConsultaClientes implements br.com.sulpassomobile.sulpasso.sulpassomobile.controle.interfaces.ConsultaClientes
{
    /*
        TODO: Mudar a forma de busca das listas para utilizar apenas ClienteDataAccess para retornar os cliente.
        TODO: Criar callback de click nas listas para exibir os itens relacionados a cada cliente.
     */
    private Context ctx;
    private ArrayList<String> lista;
    private ArrayList<Cliente> clientes;
    private ArrayList<Devolucao> devolucoes;
    private ArrayList<Corte> cortes;
    private ArrayList<PrePedido> pedidos;

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

    private ArrayList<String> buscarPrePedido()
    {
        PrePedidoDataAccess pda = new PrePedidoDataAccess(this.ctx);
        try { this.clientes = pda.getAllClients(); }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> retorno = new ArrayList<>();
        for(Cliente c : this.clientes) { retorno.add(c.toDisplay()); }

        return retorno;
    }

    private ArrayList<String> buscarPositivacao()
    {
        ClienteDataAccess cda = new ClienteDataAccess(this.ctx);
        try { this.clientes = cda.getAll(); }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> retorno = new ArrayList<>();
        retorno.add("BUSCA POSITOVAÇÃO INTERFACE");
        for(Cliente c : this.clientes) { retorno.add(c.toDisplay()); }

        return retorno;
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
    public ArrayList<String> buscarListaPrePedido() { return this.buscarPrePedido(); }

    @Override
    public ArrayList<String> buscarListaPositivacao() { return this.buscarPositivacao(); }

    @Override
    public ArrayList<String> buscarListaClientes(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaTitulos(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaDevolucao(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaCorte(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaPrePedido(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaPositivacao(int tipo, String cliente) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaClientes(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaTitulos(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaDevolucao(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaCorte(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaPrePedido(int tipo, int cidade) {
        return null;
    }

    @Override
    public ArrayList<String> buscarListaPositivacao(int tipo, int cidade) {
        return null;
    }
/*********************************END OF ITERFACES METHODS*****************************************/
}
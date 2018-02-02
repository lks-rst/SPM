package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 02/08/2016.
 */
public class ConsultarPedidos
{
    private ArrayList<Venda> enviadas;
    private ArrayList<Venda> naoEnviadas;
    private ArrayList<Venda> todas;
    private VendaDataAccess vda;

    public ConsultarPedidos(Context ctx)
    {
        this.vda = new VendaDataAccess(ctx);
    }

    public ArrayList<String> listarPedidos(int tipo, String data)
    {
        switch (tipo)
        {
            case 0 :
                this.naoEnviadas = null;
                this.enviadas = null;
                this.todas = null;
                this.buscarTodos(data);
                return this.listarTodosPedidos();
            case 1 :
                this.naoEnviadas = null;
                this.enviadas = null;
                this.todas = null;
                this.buscarEnviados(data);
                return this.listarPedidosEnviados();
            case 2 :
                this.naoEnviadas = null;
                this.enviadas = null;
                this.todas = null;
                this.buscarNaoEnviados(data);
                return this.listarPedidosNaoEnviados();
            default :
                this.naoEnviadas = null;
                this.enviadas = null;
                this.todas = null;
                this.buscarTodos(data);
                return this.listarTodosPedidos();
        }
    }

    public ArrayList<String> listarItens(int posicao)
    {
        if(enviadas == null)
            if(naoEnviadas == null)
                return exibirItens(todas.get(posicao).getItens());
            else
                return exibirItens(naoEnviadas.get(posicao).getItens());
        else
            return exibirItens(enviadas.get(posicao).getItens());
    }

    private ArrayList<String> exibirItens(ArrayList<ItensVendidos> itens)
    {
        ArrayList<String> retorno = new ArrayList<>();
        ManipulacaoStrings ms = new ManipulacaoStrings();

        for(ItensVendidos item : itens)
        {
            retorno.add(ms.comEsquerda(String.valueOf(item.getItem()), " ", 10) + " - " +
                ms.comEsquerda(vda.recuperarDescricao(item.getItem()), " ", 20) +
                item.toDisplay());
        }

        return retorno;
    }

    private ArrayList<String> listarPedidosEnviados()
    {
        ArrayList<String> lista = new ArrayList<>();

        for(Venda v : this.enviadas)
            lista.add(v.toDisplay());

        return lista;
    }

    private ArrayList<String> listarPedidosNaoEnviados()
    {
        ArrayList<String> lista = new ArrayList<>();

        for(Venda v : this.naoEnviadas)
            lista.add(v.toDisplay());

        return lista;
    }

    private ArrayList<String> listarTodosPedidos()
    {
        ArrayList<String> lista = new ArrayList<>();

        for(Venda v : this.todas)
            lista.add(v.toDisplay());

        return lista;
    }

    private void buscarEnviados(String data)
    {
        this.vda.setSearchType(0);
        this.vda.setSearchData(data);

        try { this.enviadas = (ArrayList<Venda>) this.vda.getByData(); }
        catch (GenercicException e) { this.todas = new ArrayList<>(); }
    }

    private void buscarNaoEnviados(String data)
    {
        this.vda.setSearchType(1);
        this.vda.setSearchData(data);

        try { this.naoEnviadas = (ArrayList<Venda>) this.vda.getByData(); }
        catch (GenercicException e) { this.todas = new ArrayList<>(); }
    }

    private void buscarTodos(String data)
    {
        try { this.todas = (ArrayList<Venda>) this.vda.getAll(); }
        catch (GenercicException e) { this.todas = new ArrayList<>(); }
    }
}
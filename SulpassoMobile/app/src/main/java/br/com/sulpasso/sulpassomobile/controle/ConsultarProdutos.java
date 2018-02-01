package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Gravosos;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaItens;


/*
    TODO: Criar função para busca do percentual maximo de desconto em um item (configuração do item).
    TODO: Criar função para busca do valor minimo possivel de venda do item.
    TODO: Unir a busca de dados de venda de um item em uma unica função (está com uma para os itens normais e uma para os itens de pre pedido).
*/

/**
 * Created by Lucas on 01/08/2016.
 */
public class ConsultarProdutos
{
    private TiposBuscaItens searchType;
    private int grupo;
    private int subGrupo;
    private int divisao;
    private int tabela;
    private String searchData;

    private ItemDataAccess ida;
    private ArrayList<Item> lista;
    private ArrayList<Gravosos> gravosos;

    public ConsultarProdutos(Context ctx)
    {
        this.ida = new ItemDataAccess(ctx);
        this.lista = new ArrayList<>();
        this.searchType = TiposBuscaItens.TODOS;
        this.grupo = 0;
        this.subGrupo = 0;
        this.divisao = 0;
        this.searchData = "";
    }

    public void setSearchType(TiposBuscaItens searchType) { this.searchType = searchType; }

    public void setSearchData(String searchData) { this.searchData = searchData; }

    public void setGrupo(int grupo) { this.grupo = grupo; }

    public void setSubGrupo(int subGrupo) { this.subGrupo = subGrupo; }

    public void setDivisao(int divisao) { this.divisao = divisao; }

    public String selecionarItem(int posicao) { return this.lista.get(posicao).toString(); }

    public Item getItem(int posicao)
    {
        if(this.searchType == TiposBuscaItens.GRAVOSOS)
            return this.gravosos.get(posicao).getItem();
        else
            return this.lista.get(posicao);
    }

    public int getItemPosicao(int codigo)
    {
        int i = 0;
        int posicao = 0;

        for (i = 0; i < this.lista.size(); i++)
        {
            Item t = this.lista.get(i);

            if(t.getCodigo() == codigo)
            {
                posicao = i;
                break;
            }
        }

        return posicao;
    }

    public Item getItemCodigo(int codigo) throws GenercicException { return this.buscarItemCodigo(codigo); }

    public HashMap<String, String> dadosVenda(int posicao, int tabela, int minimo)
    {
        if(this.searchType == TiposBuscaItens.GRAVOSOS)
            return this.getDadosVenda(this.gravosos.get(posicao).getItem().getCodigo(), tabela, minimo);
        else
            return this.getDadosVenda(this.lista.get(posicao).getCodigo(), tabela, minimo);
    }

    public HashMap<String, String> dadosVendaPre(int codigo, int tabela, int minimo)
    {
        return this.getDadosVenda(codigo, tabela, minimo);
    }

    public ArrayList<String> buscarItens() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();
        retorno.add("SELECIONE UM ITEM");

        this.listar();

        for(Item i : this.lista)
        {
            retorno.add(i.toString());
        }

        return retorno;
    }

    public ArrayList<String> buscarItens(int tabela) throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();
        /*
        retorno.add("SELECIONE UM ITEM");
        */


        if(this.searchType == TiposBuscaItens.GRAVOSOS)
        {
            this.listar(tabela);

            for(Gravosos i : this.gravosos)
            {
                retorno.add(i.toDisplay());
            }
        }
        else // TODO: Remover esse else (acredito que tudo seja resolvido no próximo
        if(this.searchType == TiposBuscaItens.MIX)
        {
            this.listar(tabela);

            for(Item i : this.lista)
            {
                retorno.add(i.toDisplay());
            }
        }
        else
        {
            this.listar(tabela);

            for(Item i : this.lista)
            {
                retorno.add(i.toDisplay());
            }
        }

        return retorno;
    }

    public String getItemStr(int codigo) { return this.ida.getItemStr(codigo); }

    private void listar() throws GenercicException
    {
        if(this.searchType.getValue() <= 0) { this.listarTodos(); }
        else { this.listarBusca(); }
    }

    private void listar(int tabela) throws GenercicException
    {
        if(this.searchType.getValue() <= 0) { this.listarTodos(tabela); }
        else
        {
            if(this.searchType.getValue() == 4)
            {
                String busca = "";
                String g = "";
                String s = "";
                String d = "";

                g = String.valueOf(this.grupo);
                s = String.valueOf(this.subGrupo);
                d = String.valueOf(this.divisao);

                g = g.length() == 2 ? g : ("0" + g);
                s = s.length() == 2 ? s : ("0" + s);
                d = d.length() == 2 ? d : ("0" + d);

                busca = g + s + d;

                this.setSearchData(busca);
            }

            this.listarBusca(tabela);
        }
    }

    private Item buscarItemCodigo(int codigo) throws GenercicException { return this.ida.buscarItemCodigo(codigo); }

    private void listarTodos() throws GenercicException { this.lista = this.ida.getAll(); }

    private void listarTodos(int tabela) throws GenercicException { this.lista = this.ida.getAll(tabela); }

    private void listarBusca() throws GenercicException { this.lista = this.ida.getByData(); }

    private void listarBusca(int tabela) throws GenercicException
    {
        this.ida.setSearchType(this.searchType.getValue());
        this.ida.setSearchData(this.searchData);

        if(this.searchType == TiposBuscaItens.GRAVOSOS)
        {
            this.gravosos = this.ida.getByDataG(tabela);
        }
        else
        {
            this.lista = this.ida.getByData(tabela);
        }
    }

    private HashMap<String, String>  getDadosVenda(int codigo, int tabela, int minimo)
    {
        return this.ida.dadosVenda(codigo, tabela, minimo);
    }
}
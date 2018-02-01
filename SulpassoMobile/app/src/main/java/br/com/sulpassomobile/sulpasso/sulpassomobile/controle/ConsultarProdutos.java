package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;


/*
    TODO: Criar função para busca do percentual maximo de desconto em um item (configuração do item).
    TODO: Criar função para busca do valor minimo possivel de venda do item.
*/

/**
 * Created by Lucas on 01/08/2016.
 */
public class ConsultarProdutos
{
    private int searchType;
    private int grupo;
    private int subGrupo;
    private int divisao;
    private int tabela;
    private String searchData;

    private ItemDataAccess ida;
    private ArrayList<Item> lista;

    public ConsultarProdutos(Context ctx)
    {
        this.ida = new ItemDataAccess(ctx);
        this.lista = new ArrayList<>();
        this.searchType = 0;
        this.grupo = 0;
        this.subGrupo = 0;
        this.divisao = 0;
        this.searchData = "";
    }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public void setSearchData(String searchData) { this.searchData = searchData; }

    public void setGrupo(int grupo) { this.grupo = grupo; }

    public void setSubGrupo(int subGrupo) { this.subGrupo = subGrupo; }

    public void setDivisao(int divisao) { this.divisao = divisao; }

    public String selecionarItem(int posicao) { return this.lista.get(posicao).toString(); }

    public Item getItem(int posicao) { return this.lista.get(posicao); }

    public Item getItemCodigo(int codigo) throws GenercicException { return this.buscarItemCodigo(codigo); }

    public HashMap<String, String> dadosVenda(int posicao, int tabela, int minimo)
    {
        return this.getDadosVenda(this.lista.get(posicao).getCodigo(), tabela, minimo);
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

        this.listar(tabela);

        for(Item i : this.lista)
        {
            retorno.add(i.toDisplay());
        }

        return retorno;
    }

    private void listar() throws GenercicException
    {
        if(this.searchType <= 0) { this.listarTodos(); }
        else { this.listarBusca(); }
    }

    private void listar(int tabela) throws GenercicException
    {
        if(this.searchType <= 0) { this.listarTodos(tabela); }
        else { this.listarBusca(tabela); }
    }

    private Item buscarItemCodigo(int codigo) throws GenercicException { return this.ida.buscarItemCodigo(codigo); }

    private void listarTodos() throws GenercicException { this.lista = this.ida.getAll(); }

    private void listarTodos(int tabela) throws GenercicException { this.lista = this.ida.getAll(tabela); }

    private void listarBusca() throws GenercicException { this.lista = this.ida.getByData(); }

    private void listarBusca(int tabela) throws GenercicException { this.lista = this.ida.getByData(); }

    private HashMap<String, String>  getDadosVenda(int codigo, int tabela, int minimo)
    {
        return this.ida.dadosVenda(codigo, tabela, minimo);
    }
}
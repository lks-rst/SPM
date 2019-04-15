package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Gravosos;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaItens;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

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
        int posicao = -1;

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

    public Item getItemAlteracao(int codigo, int tabela, int orderBy)
    {
        ArrayList<Item> t = null;

        this.ida.setSearchData(String.valueOf(codigo));
        this.ida.setSearchType(7);

        try { t = this.ida.getByData(tabela, orderBy); }
        catch (GenercicException e) { e.printStackTrace(); }

        return t.get(0);
    }

    public Item getItemCodigo(int codigo) throws GenercicException { return this.buscarItemCodigo(codigo); }

    public HashMap<String, String> dadosVenda(int posicao, int tabela, int minimo)
    {
        if(this.searchType == TiposBuscaItens.GRAVOSOS)
            return this.getDadosVenda(this.gravosos.get(posicao).getItem().getCodigo(), tabela, minimo);
        else
            return this.getDadosVenda(this.lista.get(posicao).getCodigo(), tabela, minimo);
    }

    public HashMap<String, String> dadosVendaCodigo(int codigo, int tabela, int minimo)
    {
        return this.getDadosVenda(codigo, tabela, minimo);
    }

    public HashMap<String, String> dadosVendaAlteracao(int codigo, int tabela, int minimo)
    {
        return this.getDadosVenda(codigo, tabela, minimo);
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

    public ArrayList<String> buscarItens(int tabela, int cliente, int orderBy) throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();
        /*
        retorno.add("SELECIONE UM ITEM");
        */

        if(this.searchType == TiposBuscaItens.GRAVOSOS)
        {
            this.listar(tabela, orderBy);

            this.removerRestricoes(cliente);

            for(Gravosos i : this.gravosos)
            {
                retorno.add(i.toDisplay());
            }
        }
        else
        if(this.searchType == TiposBuscaItens.MIX)
        {
            this.listar(tabela, orderBy);

            this.removerRestricoes(cliente);

            for(Item i : this.lista)
            {
                retorno.add(i.toDisplay());
            }
        }
        else
        {
            this.listar(tabela, orderBy);

            this.removerRestricoes(cliente);

            for(Item i : this.lista)
            {
                retorno.add(i.toDisplay());
            }
        }

        return retorno;
    }

    public ArrayList<Gravosos> buscarItens2(int tabela, int cliente, int orderBy) throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();
        /*
        retorno.add("SELECIONE UM ITEM");
        */

        if(this.searchType == TiposBuscaItens.GRAVOSOS)
        {
            this.listar(tabela, orderBy);

            this.removerRestricoes(cliente);
        }
        else
        if(this.searchType == TiposBuscaItens.MIX)
        {
            this.listar(tabela, orderBy);

            this.removerRestricoes(cliente);

            this.gravosos = new ArrayList<>();

            for(Item i : this.lista)
            {
                Gravosos g;
                g = new Gravosos();
                g.setItem(i);
                this.gravosos.add(g);
            }
        }
        else
        {
            this.listar(tabela, orderBy);

            this.removerRestricoes(cliente);

            this.gravosos = new ArrayList<>();

            int cont = 0;
            for(Item i : this.lista)
            {
                ++cont;

                Gravosos g;
                g = new Gravosos();
                g.setItem(i);
                g.setSold(false);

                this.gravosos.add(g);
            }
        }

        return this.gravosos;
    }

    public ArrayList<String> buscarItens(int tabela, int orderBy) throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();
        /*
        retorno.add("SELECIONE UM ITEM");
        */

        if(this.searchType == TiposBuscaItens.GRAVOSOS)
        {
            this.listar(tabela, orderBy);

            for(Gravosos i : this.gravosos)
            {
                retorno.add(i.toDisplay());
            }
        }
        else
        if(this.searchType == TiposBuscaItens.MIX)
        {
            this.listar(tabela, orderBy);

            for(Item i : this.lista)
            {
                retorno.add(i.toDisplay());
            }
        }
        else
        {
            this.listar(tabela, orderBy);

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

    private void listar(int tabela, int orderBy) throws GenercicException
    {
        if(this.searchType.getValue() <= 0) { this.listarTodos(tabela, orderBy); }
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

                ManipulacaoStrings ms = new ManipulacaoStrings();


                g = ms.comEsquerda(g, "0", 3);
                s = ms.comEsquerda(s, "0", 3);
                d = ms.comEsquerda(d, "0", 3);

                busca = g + s + d;

                this.setSearchData(busca);
            }

            this.listarBusca(tabela, orderBy);
        }
    }

    private Item buscarItemCodigo(int codigo) throws GenercicException { return this.ida.buscarItemCodigo(codigo); }

    private void listarTodos() throws GenercicException { this.lista = this.ida.getAll(); }

    private void listarTodos(int tabela, int orderBy) throws GenercicException { this.lista = this.ida.getAll(tabela, orderBy); }

    private void listarBusca() throws GenercicException { this.lista = this.ida.getByData(); }

    private void listarBusca(int tabela, int orderBy) throws GenercicException
    {
        this.ida.setSearchType(this.searchType.getValue());
        this.ida.setSearchData(this.searchData);

        if(this.searchType == TiposBuscaItens.PRE)
        {
            this.ida.setSearchType(TiposBuscaItens.TODOS.getValue());
        }

        if(this.searchType == TiposBuscaItens.GRAVOSOS)
        {
            this.gravosos = this.ida.getByDataG(tabela);
        }
        else
        {
            this.lista = this.ida.getByData(tabela, orderBy);
        }
    }

    private HashMap<String, String>  getDadosVenda(int codigo, int tabela, int minimo)
    {
        return this.ida.dadosVenda(codigo, tabela, minimo);
    }

    private void removerRestricoes(int cliente)
    {
        ArrayList itensrestritos = this.ida.verificarRestricoes(cliente);
        boolean encontrado;

        if(this.searchType == TiposBuscaItens.GRAVOSOS)
        {
            if(itensrestritos.size() > 0)
            {
                ArrayList<Gravosos> gravososRestritos = new ArrayList<>();

                for(Gravosos g : this.gravosos)
                {
                    encontrado = false;
                    for(int i = 0; i < itensrestritos.size(); i++)
                    {
                        if(g.getItem().getCodigo() == itensrestritos.get(i))
                        {
                            encontrado = true;
                            break;
                        }
                    }

                    if(!encontrado)
                        gravososRestritos.add(g);
                }

                this.gravosos = gravososRestritos;
            }
        }
        else
        {
            if(itensrestritos.size() > 0)
            {
                ArrayList<Item> listaRestritos = new ArrayList<>();

                for(Item it : this.lista)
                {
                    encontrado = false;
                    for(int i = 0; i < itensrestritos.size(); i++)
                    {
                        int val = (int) itensrestritos.get(i);
                        if(it.getCodigo() == val)
                        {
                            encontrado = true;
                            break;
                        }
                    }

                    if(encontrado)
                        listaRestritos.add(it);
                }

                this.lista = listaRestritos;
            }
        }
    }
}
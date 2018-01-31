package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpasso.sulpassomobile.util.modelos.GenericItemFour;

/**
 * Created by Lucas on 02/08/2016.
 */
public class ConsultaPromocoes
{
    private Context ctx;
    private PromocaoDataAccess pda;

    private ArrayList<GenericItemFour> lista;
    private ArrayList<Promocao> listaPromocoes;

    private String searchData;

    private int searchType;

    public ConsultaPromocoes(Context context)
    {
        this.ctx = context;

        this.pda = new PromocaoDataAccess(context);
        this.lista = new ArrayList<>();;

        this.searchData = "";

        this.searchType = 0;
    }

    public String getSearchData() { return searchData; }

    public void setSearchData(String searchData) { this.searchData = searchData; }

    public int getSearchType() { return searchType; }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public ArrayList<String> buscarPromocoes() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();

        if(this.searchType == 3) { this.buscarGeneric(); }
        else if(this.searchType == 0) { this.buscarTodos(); }
        else { this.buscar(); }

        if(this.searchType == 3)
        {
            for(GenericItemFour i : this.lista) { retorno.add(i.toDisplay()); }
        }
        else
        {
            for(Promocao i : this.listaPromocoes) { retorno.add(i.toDisplay()); }
        }

        return retorno;
    }

    private void buscar() throws GenercicException
    {
        if(this.searchType <= 0) { this.listarTodos(); }
        else { this.listarBusca(); }
    }

    private void buscarTodos() throws GenercicException
    {
        if(this.searchType <= 0) { this.listarTodos(); }
        else { this.listarBusca(); }
    }

    private void buscarGeneric() throws GenercicException
    {
        if(this.searchType <= 0) { this.listarTodos(); }
        else { this.listarGeneric(); }
    }

    private void listarTodos() throws GenercicException { this.listaPromocoes = this.pda.buscarTodos(); }

    private void listarGeneric() throws GenercicException { this.lista = this.pda.buscarTodosG(); }

    private void listarBusca() throws GenercicException
    {
        this.pda.setSearchType(this.searchType);
        this.pda.setSearchData(this.searchData);

        this.listaPromocoes = this.pda.getByData();

        this.pda.setSearchType(0);
        this.pda.setSearchData("");
    }
}
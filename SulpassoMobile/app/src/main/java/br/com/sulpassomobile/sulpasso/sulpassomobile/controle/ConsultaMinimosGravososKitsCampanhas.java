package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.CampanhaProduto;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Gravosos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Kit;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CampanhaGrupoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CampanhaProdutoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.GravososDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.KitDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrecoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.util.modelos.GenericItemFour;

/**
 * Created by Lucas on 22/11/2016 - 17:11 as part of the project SulpassoMobile.
 */
public class ConsultaMinimosGravososKitsCampanhas
{
    private Context ctx;

    private String searchData;

    private int searchType;
    private int searchTypeMain;

    public ConsultaMinimosGravososKitsCampanhas(Context context)
    {
        this.ctx = context;

        this.searchData = "";

        this.searchType = 0;
        this.searchTypeMain = 0;
    }

    public void setSearchData(String searchData) { this.searchData = searchData; }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public void setSearchTypeMain(int searchTypeMain) { this.searchTypeMain = searchTypeMain; }

    public CampanhaGrupo buscarCampanha(int codigo) throws GenercicException { return this.buscarCampanhasGrupos(codigo); }

    public ArrayList<String> loadData() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();

        if(this.searchTypeMain == 0)
        {
            ArrayList<GenericItemFour> lista = this.buscarMinimos();
            for(GenericItemFour i : lista) { retorno.add(i.toDisplay()); }
        }
        else if(this.searchTypeMain == 1)
        {
            ArrayList<Gravosos> lista = this.buscarGravosos();
            for(Gravosos i : lista) { retorno.add(i.toDisplay()); }
        }
        else if(this.searchTypeMain == 2)
        {
            ArrayList<Kit> lista = this.buscarKits();
            for(Kit i : lista) { retorno.add(i.toDisplay()); }
        }
        else if(this.searchTypeMain == 3)
        {
            ArrayList<CampanhaProduto> lista = this.buscarCampanhasProdutos();
            for(CampanhaProduto i : lista) { retorno.add(i.toDisplay()); }
        }
        else
        {
            ArrayList<CampanhaGrupo> lista = this.buscarCampanhasGrupos();
            for(CampanhaGrupo i : lista) { retorno.add(i.toDisplay()); }
        }

        return retorno;
    }

    public ArrayList<Kit> loadKits() throws GenercicException { return this.buscarKits(); }

    private ArrayList<Kit> buscarKits() throws GenercicException
    {
        KitDataAccess kad = new KitDataAccess(ctx);
        return kad.getAll();
    }

    private ArrayList<Gravosos> buscarGravosos() throws GenercicException
    {
        GravososDataAccess gda = new GravososDataAccess(ctx);
        return gda.getAll();
    }

    private ArrayList<CampanhaProduto> buscarCampanhasProdutos() throws GenercicException
    {
        CampanhaProdutoDataAccess cpda = new CampanhaProdutoDataAccess(ctx);
        return cpda.getAll();
    }

    private ArrayList<CampanhaGrupo> buscarCampanhasGrupos() throws GenercicException
    {
        CampanhaGrupoDataAccess cgda = new CampanhaGrupoDataAccess(ctx);
        return cgda.getAll();
    }

    private ArrayList<GenericItemFour> buscarMinimos() throws GenercicException
    {
        PrecoDataAccess pda = new PrecoDataAccess(ctx);
        pda.setSearchType(2);
        pda.setSearchData("50");
        return pda.buscarMinimo();
    }

    private CampanhaGrupo buscarCampanhasGrupos(int produto) throws GenercicException
    {
        CampanhaGrupoDataAccess cgda = new CampanhaGrupoDataAccess(ctx);
        return cgda.getByData(produto);
    }
}
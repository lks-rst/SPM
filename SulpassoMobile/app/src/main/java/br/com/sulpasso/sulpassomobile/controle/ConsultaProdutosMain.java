package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Grupo;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.modelo.Preco;
import br.com.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpasso.sulpassomobile.persistencia.queries.GrupoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PrecoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;

/**
 * Created by Lucas on 16/11/2016 - 15:30 as part of the project SulpassoMobile.
 */
public class ConsultaProdutosMain
{
    private Context ctx;
    private ItemDataAccess ida;
    private GrupoDataAccess gda;

    private ArrayList<Item> lista;
    private ArrayList<Grupo> grupos;
    private ArrayList<Grupo> sGrupos;
    private ArrayList<Grupo> divisoes;

    private String searchData;

    private int searchType;
    private int grupo;
    private int subGrupo;
    private int divisao;

    public ConsultaProdutosMain(Context context)
    {
        this.ctx = context;

        this.ida = new ItemDataAccess(context);
        this.gda = new GrupoDataAccess(context);
        this.lista = new ArrayList<>();
        this.grupos = new ArrayList<>();
        this.sGrupos = new ArrayList<>();
        this.divisoes = new ArrayList<>();

        this.searchData = "";

        this.searchType = 0;
        this.grupo = 0;
        this.subGrupo = 0;
        this.divisao = 0;
    }

    public String getSearchData() { return searchData; }

    public void setSearchData(String searchData) { this.searchData = searchData; }

    public int getSearchType() { return searchType; }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public int getGrupo() { return grupo; }

    public void setGrupo(int grupo) { this.grupo = grupo; }

    public void indicarGrupo(int posicao)
    {
        this.grupo = this.grupos.get(posicao).getGrupo();
        this.subGrupo = 0;
        this.divisao = 0;
    }

    public int getSubGrupo() { return subGrupo; }

    public void setSubGrupo(int subGrupo) { this.subGrupo = subGrupo; }

    public void indicarSubGrupo(int posicao)
    {
        this.subGrupo = this.sGrupos.get(posicao).getSubGrupo();
        this.divisao = 0;
    }

    public int getDivisao() { return divisao; }

    public void setDivisao(int divisao) { this.divisao = divisao; }

    public void indicarDivisao(int posicao) { this.divisao = this.divisoes.get(posicao).getDivisao(); }

    public int selecionarItem(int posicao) { return this.lista.get(posicao).getCodigo(); }

    public ArrayList<String> buscarItens() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();

        if(this.searchType == 4)
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

        this.listar();

        for(Item i : this.lista) { retorno.add(i.toDisplay()); }

        return retorno;
    }

    /**
     * Esse código está duplicado em outra classe
     * @return
     * @throws GenercicException
     */
    public ArrayList<String> listarGrupos() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();

        this.buscarGrupos();

        for(Grupo g : this.grupos) { retorno.add(g.toDisplay()); }
        retorno.add(0, this.ctx.getApplicationContext().getResources().getString(R.string.str_grupo_zero));

        return retorno;
    }

    /**
     * Esse código está duplicado em outra classe
     * @return
     * @throws GenercicException
     */
    public ArrayList<String> listarSubGrupos() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();

        this.buscarSubGrupos();

        for(Grupo g : this.sGrupos) { retorno.add(g.toDisplay()); }
        retorno.add(0, this.ctx.getApplicationContext().getResources().getString(R.string.str_sub_zero));

        return retorno;
    }

    /**
     * Esse código está duplicado em outra classe
     * @return
     * @throws GenercicException
     */
    public ArrayList<String> listarDivisoes() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();

        this.buscarDivisoes();

        for(Grupo g : this.divisoes) { retorno.add(g.toDisplay()); }
        retorno.add(0, this.ctx.getApplicationContext().getResources().getString(R.string.str_div_zero));

        return retorno;
    }

    public Item carregarItem(int posicaoItem) { return this.lista.get(posicaoItem); }

    public ArrayList<String> carregarTabeas(int item) throws GenercicException
    {
        PrecoDataAccess pda = new PrecoDataAccess(this.ctx);

        ArrayList<String> lista = new ArrayList<>();
        ArrayList<Preco> precos = new ArrayList<>();

        pda.setSearchType(1);
        pda.setSearchData(String.valueOf(item));

        precos = pda.buscarRestrito();

        for(Preco p : precos)
        {
            lista.add(p.toDisplay());
        }

        return lista;
    }

    public ArrayList<String> carregarPromocoes(int item) throws GenercicException
    {
        PromocaoDataAccess pda = new PromocaoDataAccess(this.ctx);

        ArrayList<String> lista = new ArrayList<>();
        ArrayList<Promocao> promocoes = new ArrayList<>();

        pda.setSearchType(1);

        promocoes = pda.buscarPromocao(item);

        for(Promocao p : promocoes)
        {
            lista.add(p.toString());
        }

        return lista;
    }

    private void listar() throws GenercicException
    {
        if(this.searchType <= 0) { this.listarTodos(); }
        else { this.listarBusca(); }
    }

    private void listarTodos() throws GenercicException { this.lista = this.ida.getAll(); }

    private void listarBusca() throws GenercicException
    {
        this.ida.setSearchType(this.searchType);
        this.ida.setSearchData(this.searchData);

        this.lista = this.ida.getByData();

        this.ida.setSearchType(0);
        this.ida.setSearchData("");
    }

    private void buscarGrupos() throws GenercicException { this.grupos = this.gda.getAll(); }

    private void buscarSubGrupos() throws GenercicException { this.sGrupos = this.gda.getAll(this.grupo); }

    private void buscarDivisoes() throws GenercicException { this.divisoes = this.gda.getAll(this.grupo, this.subGrupo); }
}
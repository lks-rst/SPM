package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpasso.sulpassomobile.modelo.Foco;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 05/06/2018 - 14:21 as part of the project SulpassoMobile.
 */
public class ConsultaFoco
{
    private Context ctx;
    private ArrayList<Foco> itensFoco;

    private String searchData;

    private int searchType;
    private int searchTypeMain;

    public ConsultaFoco(Context context)
    {
        this.ctx = context;

        this.searchData = "";

        this.searchType = 0;
        this.searchTypeMain = 0;
    }

    public void setSearchData(String searchData) { this.searchData = searchData; }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public void setSearchTypeMain(int searchTypeMain) { this.searchTypeMain = searchTypeMain; }

    public ArrayList<Item> listarItens(String descricao)
    {
        ItemDataAccess ida = new ItemDataAccess(this.ctx);
        ida.setSearchType(2);
        ida.setSearchData(descricao);

        try { return ida.getByData(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Foco> buscarFoco()
    {
        String data_sistema = "";
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");

        ManipulacaoStrings ms = new ManipulacaoStrings();
        Date today = new Date();
        data_sistema = sf.format(today);

        String inicio;
        String fim;
        inicio = fim = ms.dataBanco(data_sistema);

        ItemDataAccess ida = new ItemDataAccess(this.ctx);
        ida.setSearchType(2);

        this.itensFoco = new ArrayList<>();
        this.itensFoco = ida.buscarFoco(inicio, fim);

        return this.itensFoco;
    }

    public void inserirItensFoco(ArrayList<Item> foco)
    {
        ItemDataAccess ida = new ItemDataAccess(this.ctx);

        ArrayList<Foco> lFoco = new ArrayList<>();

        for(Item i : foco)
        {
            Foco f = new Foco();
            f.setCodigo(i.getCodigo());

            lFoco.add(f);
        }

        if(lFoco.size() > 0)
            ida.insertFoco(lFoco);
    }

    public void removerItem(int posicao)
    {
        ItemDataAccess ida = new ItemDataAccess(this.ctx);
        ida.removerItem(this.itensFoco.get(posicao).getCodigo());
    }
}
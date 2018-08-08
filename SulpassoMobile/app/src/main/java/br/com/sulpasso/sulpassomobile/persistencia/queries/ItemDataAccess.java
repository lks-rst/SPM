package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Foco;
import br.com.sulpasso.sulpassomobile.modelo.Gravosos;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 15/08/2016.
 */
public class ItemDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private String searchData;

    public ItemDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public void setSearchData(String searchData) { this.searchData = searchData; }

    public ArrayList<Item> getAll() throws GenercicException
    {
        return this.searchAll();
    }

    public ArrayList<Item> getAll(int tabela) throws GenercicException
    {
        return this.searchAll(tabela);
    }

    public ArrayList<Item> getByData() throws GenercicException
    {
        switch (this.searchType)
        {
            case 1:
            case 2:
            case 3:
                return this.searchByData(-1);
            case 4:
                return this.searchByGroup(-1);
            case 6:
                return this.searchByMix(-1);
            default :
                return this.searchAll();
        }
    }

    public ArrayList<Item> getByData(int tabela) throws GenercicException
    {
        switch (this.searchType)
        {
            case 1:
            case 2:
            case 3:
                return this.searchByData(tabela);
            case 4:
                return this.searchByGroup(tabela);
            case 6:
                return this.searchByMix(tabela);
            default :
                return this.searchAll(tabela);
        }
    }

    public ArrayList<Gravosos> getByDataG(int tabela) throws GenercicException
    {
        switch (this.searchType)
        {
            case 5:
                return this.searchByGravosos(tabela);
            default :
                return new ArrayList<>();
        }
    }

    public Item buscarItemCodigo(int codigo) throws GenercicException { return this.searchCod(codigo); }

    public Boolean inserir(String data) throws GenercicException
    {
        return this.inserir(this.dataConverter(data));
    }

    public Boolean inserir(Item item) throws GenercicException
    {
        return this.insert(item);
    }

    public HashMap<String, String>  dadosVenda(int item, int tabela, int minimo)
    {
        return this.searchSaleData(item, tabela, minimo);
    }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    public String getItemStr(int codigo)
    {
        String ret = "";

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA);
        this.sBuilder.append(" AS REF, ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
        this.sBuilder.append(" AS DESC, ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO);
        this.sBuilder.append(" AS COMP ");

        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" WHERE ");

        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(codigo);
        this.sBuilder.append("'");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        ManipulacaoStrings ms = new ManipulacaoStrings();

        ret = (ms.comDireita(c.getString(c.getColumnIndex("REF")), " ", 10).trim() + " . " +
                ms.comDireita(c.getString(c.getColumnIndex("DESC")), " ", 25).trim() + " . " +
                ms.comDireita(c.getString(c.getColumnIndex("COMP")), " ", 15).trim());

        return ret;
    }

    public void insertFoco(ArrayList<Foco> itens) { for(Foco f : itens) { this.inserirFoco(f); } }

    public Boolean updateItem(String item) throws InsertionExeption
    {
        int codigoItem;
        String aplicacao;

        codigoItem = Integer.parseInt(item.substring(0, 7));
        aplicacao = (item.substring(7)).trim();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.APLICACAO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(aplicacao);
        this.sBuilder.append("' WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigoItem);
        this.sBuilder.append(";");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private void inserirFoco(Foco f)
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Foco.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Foco.CODIGO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(f.getCodigo());
        this.sBuilder.append("');");

        try { this.db.execSQL(this.sBuilder.toString()); }
        catch (Exception e) { /*****/ }
    }

    private Boolean insert(Item item) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CONTRIBUICAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.PESO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.PESOCD);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FAIXA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.GRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.SUBGRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DIVISAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CUSTO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(item.getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getBarras());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getReferencia());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getDescricao());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getComplemento());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getUnidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getUnidadeVenda());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getDesconto());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getMinimoVenda());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getFlex());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getQuantidadeCaixa());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getContribuicao());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getPeso());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getPesoCd());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getFaixa());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getGrupo());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getSubGrupo());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getDivisao());
        this.sBuilder.append("', '");
        this.sBuilder.append(item.getCusto());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private ArrayList<Item> searchByData(int tabela) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        if(tabela == -1)
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT * FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
            this.sBuilder.append(" WHERE ");
        }
        else
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT * FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
            this.sBuilder.append(" JOIN ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
            this.sBuilder.append(" ON (");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
            this.sBuilder.append(".");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRODUTO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
            this.sBuilder.append(".");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
            this.sBuilder.append(") WHERE (");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
            this.sBuilder.append(".");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(tabela);
            this.sBuilder.append(") AND ");
        }

        switch (this.searchType)
        {
            case 1:
                this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
                this.sBuilder.append(" LIKE ('");
                this.sBuilder.append(this.searchData);
                this.sBuilder.append("%')");
                break;
            case 2:
                this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
                this.sBuilder.append(" LIKE ('%");
                this.sBuilder.append(this.searchData);
                this.sBuilder.append("%')");
                break;
            case 3:
                this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA);
                this.sBuilder.append(" LIKE ('");
                this.sBuilder.append(this.searchData);
                this.sBuilder.append("%')");
                break;
            default:
                this.sBuilder.append(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
                this.sBuilder.append(" LIKE ('");
                this.sBuilder.append(this.searchData);
                this.sBuilder.append("%')");
                break;
        }

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));
            item.setFaixa(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FAIXA)));
            item.setPeso(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.PESO)));
            item.setPesoCd(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.PESOCD)));

            item.setContribuicao(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CONTRIBUICAO)));
            item.setCusto(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CUSTO)));
            item.setAplicacao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.APLICACAO)));



            StringBuilder sb = new StringBuilder();
            sb.delete(0, this.sBuilder.length());
            sb.append("SELECT * FROM ");
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);
            sb.append(" WHERE ");
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.PRODUTO);
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);
            sb.append(" = ");
            sb.append(item.getCodigo());

            try {
                Cursor d = this.db.rawQuery(this.sBuilder.toString(), null);
                d.moveToFirst();
              
                item.setEstoque(
                    d.getFloat(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.ESTOQUE)));
            }
            catch (Exception x) { item.setEstoque(0); }


            lista.add(item);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Item> searchByGroup(int tabela) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());

        if(tabela >= 0)
        {
            this.sBuilder.append("SELECT * FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
            this.sBuilder.append(" JOIN ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
            this.sBuilder.append(" ON (");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
            this.sBuilder.append(".");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRODUTO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
            this.sBuilder.append(".");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
            this.sBuilder.append(") WHERE (");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
            this.sBuilder.append(".");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(tabela);
            this.sBuilder.append(")");

            this.sBuilder.append(" AND (");

            int grupo = 0;
            int subGrupo = -1;
            int divisao = -1;

            try { grupo = Integer.parseInt(this.searchData.substring(0, 2).trim()); }
            catch (Exception exception) { grupo = 0; }

            try { subGrupo = Integer.parseInt(this.searchData.substring(2, 4).trim()); }
            catch (Exception exception) { subGrupo = 0; }

            try { divisao = Integer.parseInt(this.searchData.substring(4, 6).trim()); }
            catch (Exception exception) { divisao = 0; }

            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.GRUPO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(grupo);
            if(subGrupo > 0)
            {
                this.sBuilder.append("' AND ");
                this.sBuilder.append(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.SUBGRUPO);
                this.sBuilder.append(" = '");
                this.sBuilder.append(subGrupo);

                if(divisao > 0)
                {
                    this.sBuilder.append("' AND ");
                    this.sBuilder.append(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DIVISAO);
                    this.sBuilder.append(" = '");
                    this.sBuilder.append(divisao);
                }
            }
        }
        else
        {
            this.sBuilder.append("SELECT * FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
            this.sBuilder.append(" WHERE (");

            int grupo = 0;
            int subGrupo = -1;
            int divisao = -1;

            try { grupo = Integer.parseInt(this.searchData.substring(0, 2).trim()); }
            catch (Exception exception) { grupo = 0; }

            try { subGrupo = Integer.parseInt(this.searchData.substring(2, 4).trim()); }
            catch (Exception exception) { subGrupo = 0; }

            try { divisao = Integer.parseInt(this.searchData.substring(4, 6).trim()); }
            catch (Exception exception) { divisao = 0; }

            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.GRUPO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(grupo);
            if(subGrupo > 0)
            {
                this.sBuilder.append("' AND ");
                this.sBuilder.append(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.SUBGRUPO);
                this.sBuilder.append(" = '");
                this.sBuilder.append(subGrupo);

                if(divisao > 0)
                {
                    this.sBuilder.append("' AND ");
                    this.sBuilder.append(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DIVISAO);
                    this.sBuilder.append(" = '");
                    this.sBuilder.append(divisao);
                }
            }
        }
        this.sBuilder.append("');");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));

            item.setContribuicao(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CONTRIBUICAO)));
            item.setCusto(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CUSTO)));
            item.setAplicacao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.APLICACAO)));



            StringBuilder sb = new StringBuilder();
            sb.delete(0, this.sBuilder.length());
            sb.append("SELECT * FROM ");
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);
            sb.append(" WHERE ");
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.PRODUTO);
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);
            sb.append(" = ");
            sb.append(item.getCodigo());

            Cursor d = this.db.rawQuery(this.sBuilder.toString(), null);
            d.moveToFirst();


            try {
                item.setEstoque(
                        d.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.ESTOQUE)));
            }
            catch (Exception x) { item.setEstoque(0); }


            lista.add(item);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Gravosos> searchByGravosos(int tabela) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());

        this.sBuilder.append("SELECT ");
        this.sBuilder.append("grav.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM);
        this.sBuilder.append(" AS codigo, ");
        this.sBuilder.append("grav.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.QUANTIDADE);
        this.sBuilder.append(" AS quantidade, ");
        this.sBuilder.append("grav.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.FABRICACAO);
        this.sBuilder.append(" AS fab, ");
        this.sBuilder.append("grav.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.VALIDADE);
        this.sBuilder.append(" AS vali, ");
        this.sBuilder.append("grav.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.DIAS);
        this.sBuilder.append(" AS dias, ");
        this.sBuilder.append("item.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA);
        this.sBuilder.append(" AS ref, ");
        this.sBuilder.append("item.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
        this.sBuilder.append(" AS desc, ");
        this.sBuilder.append("item.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO);
        this.sBuilder.append(" AS comp ");
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.TABELA);
        this.sBuilder.append(" AS grav JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" AS item ON grav.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM);
        this.sBuilder.append(" = item.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Gravosos gravoso = new Gravosos();

            Item item = new Item();
            item.setCodigo(c.getInt(0));
            item.setReferencia(c.getString(5));
            item.setDescricao(c.getString(6));
            item.setComplemento(c.getString(7));

            gravoso.setItem(item);
            gravoso.setQuantidade(c.getFloat(1));
            gravoso.setFabricacao(c.getString(2));
            gravoso.setDias(c.getInt(4));
            gravoso.setValidade(c.getString(3));

            lista.add(gravoso);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Item> searchByMix(int tabela) throws ReadExeption
    {
        ArrayList lista = new ArrayList();
        String tipo = this.searchData;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT prod.* FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" AS prod JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TABELA);
        this.sBuilder.append(" ON ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.PRODUTO);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TIPOLOGIA);
        this.sBuilder.append(" = ");
        this.sBuilder.append(Integer.parseInt(this.searchData));

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));
            item.setAplicacao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.APLICACAO)));



            StringBuilder sb = new StringBuilder();
            sb.delete(0, this.sBuilder.length());
            sb.append("SELECT * FROM ");
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);
            sb.append(" WHERE ");
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.PRODUTO);
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);
            sb.append(" = ");
            sb.append(item.getCodigo());

            Cursor d = this.db.rawQuery(this.sBuilder.toString(), null);
            d.moveToFirst();


            try {
                item.setEstoque(
                        d.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.ESTOQUE)));
            }
            catch (Exception x) { item.setEstoque(0); }


            lista.add(item);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Item> searchByData(int type, int data) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" WHERE ");
        /*switch*/
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(dataSearchToInt(0));
        this.sBuilder.append(";");
        /*switch*/


        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));

            lista.add(item);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Item> searchAll(int tabela) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(" ON (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRODUTO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(") WHERE (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(tabela);
        this.sBuilder.append(")");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));
            item.setFaixa(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FAIXA)));
            item.setPeso(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.PESO)));
            item.setPesoCd(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.PESOCD)));

            lista.add(item);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Item> searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));

            item.setContribuicao(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CONTRIBUICAO)));
            item.setCusto(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CUSTO)));
            item.setAplicacao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.APLICACAO)));



            StringBuilder sb = new StringBuilder();
            sb.delete(0, this.sBuilder.length());
            sb.append("SELECT * FROM ");
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);
            sb.append(" WHERE ");
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.PRODUTO);
            sb.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);
            sb.append(" = ");
            sb.append(item.getCodigo());

            Cursor d = this.db.rawQuery(this.sBuilder.toString(), null);
            d.moveToFirst();


            try {
                item.setEstoque(
                        d.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.ESTOQUE)));
            }
            catch (Exception x) { item.setEstoque(0); }


            lista.add(item);
            c.moveToNext();
        }

        return lista;
    }

    private Item dataConverter(String item)
    {
        Item i = new Item();

        i.setCodigo(Integer.parseInt(item.substring(2, 9)));
        i.setReferencia(item.substring(9, 19));
        i.setDescricao(item.substring(19, 49));
        i.setComplemento(item.substring(49, 63));
        i.setGrupo(Integer.parseInt(item.substring(63, 66)));
        i.setSubGrupo(Integer.parseInt(item.substring(66, 69)));
        i.setDivisao(Integer.parseInt(item.substring(69, 72)));
        i.setUnidade(item.substring(72, 74));
        i.setUnidadeVenda(item.substring(74, 76));
        i.setQuantidadeCaixa(Integer.parseInt(item.substring(76, 82)) / 100);
        i.setMinimoVenda(Integer.parseInt(item.substring(82, 86)));
        i.setFaixa(Float.parseFloat(item.substring(86, 90)) / 100);
        i.setPeso(Float.parseFloat(item.substring(90, 96)) / 100);
        i.setPesoCd(Float.parseFloat(item.substring(96, 102)) / 100);
        i.setBarras(item.substring(102, 116));
        i.setFlex(item.substring(118, 119));
        i.setContribuicao(Float.parseFloat(item.substring(151, 157)) / 1000);
        i.setCusto(Float.parseFloat(item.substring(143, 151)) / 100);

        return i;
    }

    private int dataSearchToInt(int type)
    {
        return 0;
    }

    private Item searchCod(int codigo) throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" WHERE ");

        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(codigo);
        this.sBuilder.append("'");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        Item item = new Item();

        item.setCodigo(
                c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
        item.setBarras(
                c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
        item.setDescricao(
                c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
        item.setReferencia(
                c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
        item.setComplemento(
                c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
        item.setUnidadeVenda(
                c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
        item.setUnidade(
                c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
        item.setDesconto(
                c.getDouble(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
        item.setMinimoVenda(
                c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
        item.setFlex(
                c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
        item.setQuantidadeCaixa(
                c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));

        item.setGrupo(
                c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.GRUPO)));
        item.setSubGrupo(
                c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.SUBGRUPO)));
        item.setDivisao(
                c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DIVISAO)));

        item.setContribuicao(
                c.getFloat(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CONTRIBUICAO)));
        item.setCusto(
                c.getFloat(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CUSTO)));

        return item;
    }

    private HashMap<String, String> searchSaleData(int codigo, int tabela, int minimo)
    {
        HashMap<String, String> saleMap = new HashMap<String, String>();
        Cursor c;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(" ON (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRODUTO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(") WHERE (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(tabela);
        this.sBuilder.append(") AND (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigo);
        this.sBuilder.append(");");

        c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            saleMap.put("TABELA",
                String.valueOf(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRECO))));

            saleMap.put("QTDMINIMA",
                String.valueOf(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO))));
            saleMap.put("UNIDADE",
                String.valueOf(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE))));
            saleMap.put("UNVENDA",
                String.valueOf(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA))));

            saleMap.put("BARRAS",
                String.valueOf(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS))));
            saleMap.put("QTDCAIXA",
                String.valueOf(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA))));

            c.moveToNext();
        }

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" ON (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRODUTO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(") WHERE (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(minimo);
        this.sBuilder.append(") AND (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigo);
        this.sBuilder.append(");");

        float valorMinimo = 0;
        c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            valorMinimo = c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRECO));

            c.moveToNext();
        }
        saleMap.put("MINIMO", String.valueOf(valorMinimo));

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT COUNT (*) FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);
        this.sBuilder.append(" WHERE (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigo);
        this.sBuilder.append(");");

        float promos = 0;
        c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        promos = c.getInt(0);

        saleMap.put("PROMOCAO", String.valueOf(promos > 0 ? true : false));

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);
        this.sBuilder.append(" WHERE (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.PRODUTO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigo);
        this.sBuilder.append(");");

        float est = 0;
        c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { est = c.getInt(0); }
        catch (Exception e) { /*****/ }

        saleMap.put("ESTOQUE", String.valueOf(est));

        return saleMap;
    }

    private Boolean apagar(Item d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(d.getCodigo());
            this.sBuilder.append("'");
        }

        this.sBuilder.append(";");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    public ArrayList verificarRestricoes(int client)
    {
        ArrayList ret = new ArrayList<>();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.GRUPO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.TABELA);
        this.sBuilder.append(" WHERE (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(client);
        this.sBuilder.append(")");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
            this.sBuilder.append(" FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
            this.sBuilder.append(" WHERE (");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.GRUPO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.GRUPO)));
            this.sBuilder.append(")");


            Cursor d = this.db.rawQuery(this.sBuilder.toString(), null);

            d.moveToFirst();
            for(int j = 0; j < d.getCount(); j++)
            {
                ret.add(d.getInt((d.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO))));
                d.moveToNext();
            }

            c.moveToNext();
        }

        return ret;
    }

    public ArrayList<Foco> buscarFoco(String inicio, String fim)
    {
        ArrayList ret = new ArrayList<>();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT f.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Foco.CODIGO);
        this.sBuilder.append(", i.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA);
        this.sBuilder.append(", i.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
        this.sBuilder.append(", i.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Foco.TABELA);
        this.sBuilder.append(" AS f ");
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" AS i ");
        this.sBuilder.append(" ON f.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Foco.CODIGO);
        this.sBuilder.append(" = i.");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        for(int j = 0; j < c.getCount(); j++)
        {
            Foco f = new Foco();
            f.setCodigo(c.getInt(0));
            f.setReferencia(c.getString(1));
            f.setDescricao(c.getString(2));
            f.setComplemento(c.getString(3));

            f.setClientes(buscarNrClientesFoco(f.getCodigo(), inicio, fim));
            f.setValor(valorValorFoco(f.getCodigo(), inicio, fim));
            f.setVolume(buscarVolumeFoco(f.getCodigo(), inicio, fim));
            f.setContribuicao(valorContribuicaoFoco(f.getCodigo(), inicio, fim));

            ret.add(f);
            c.moveToNext();
        }

        return ret;
    }

    public boolean removerItem(int codigo)
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Foco.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Foco.CODIGO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(codigo);
        this.sBuilder.append("'");
        this.sBuilder.append(";");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { return false; }
    }

    private int buscarNrClientesFoco(int produto, String inicio, String fim)
    {
        int valor = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT COUNT(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM);
        this.sBuilder.append(") FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" ON ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM);
        this.sBuilder.append(" = '");
        this.sBuilder.append(produto);
        this.sBuilder.append("' AND date(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA);
        this.sBuilder.append(") BETWEEN ('");
        this.sBuilder.append(inicio);
        this.sBuilder.append("') AND ('");
        this.sBuilder.append(fim);
        this.sBuilder.append("');");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        try { valor = (int) c.getFloat(0); }
        catch (Exception e) { valor = 0; }

        return valor;
    }

    private float valorValorFoco(int produto, String inicio, String fim)
    {
        float valor = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT SUM(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TOTAL);
        this.sBuilder.append(") FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" ON ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM);
        this.sBuilder.append(" = '");
        this.sBuilder.append(produto);
        this.sBuilder.append("' AND date(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA);
        this.sBuilder.append(") BETWEEN ('");
        this.sBuilder.append(inicio);
        this.sBuilder.append("') AND ('");
        this.sBuilder.append(fim);
        this.sBuilder.append("');");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { valor = c.getFloat(0); }
        catch (Exception e) { valor = 0; }

        return valor;
    }

    private float buscarVolumeFoco(int produto, String inicio, String fim)
    {
        float volume = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT SUM(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.QUANTIDADE);
        this.sBuilder.append("), ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.PESO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);

        this.sBuilder.append(" ON ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);

        this.sBuilder.append(" AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);

        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM);
        this.sBuilder.append(" = '");
        this.sBuilder.append(produto);
        this.sBuilder.append("' AND date(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA);
        this.sBuilder.append(") BETWEEN ('");
        this.sBuilder.append(inicio);
        this.sBuilder.append("') AND ('");
        this.sBuilder.append(fim);
        this.sBuilder.append("');");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        try { volume = c.getInt(0) * c.getFloat(1); }
        catch (Exception e) { volume = 0; }

        return volume;
    }

    private float valorContribuicaoFoco(int produto, String inicio, String fim)
    {
        /*
        float valor = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT SUM(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TOTAL);
        this.sBuilder.append(") FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" ON ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM);
        this.sBuilder.append(" = '");
        this.sBuilder.append(produto);
        this.sBuilder.append("' AND date(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA);
        this.sBuilder.append(") BETWEEN ('");
        this.sBuilder.append(inicio);
        this.sBuilder.append("') AND ('");
        this.sBuilder.append(fim);
        this.sBuilder.append("');");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { valor = c.getFloat(0); }
        catch (Exception e) { valor = 0; }

        return valor;
        */
        return 0;
    }
}
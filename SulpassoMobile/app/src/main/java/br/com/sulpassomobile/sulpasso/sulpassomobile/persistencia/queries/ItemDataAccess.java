package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

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
                return this.searchByData();
            case 4:
                return this.searchByGroup();
            case 5:
                return this.searchByGravosos();
            case 6:
                return this.searchByMix();
            default :
                return this.searchAll();
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

    private Boolean insert(Item item) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CONTRIBUICAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.PESO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.PESOCD);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.FAIXA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.GRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.SUBGRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DIVISAO);
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
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private ArrayList<Item> searchByData() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" WHERE ");


        switch (this.searchType)
        {
            case 1:
                this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
                this.sBuilder.append(" LIKE ('");
                this.sBuilder.append(this.searchData);
                this.sBuilder.append("%')");
                break;
            case 2:
                this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
                this.sBuilder.append(" LIKE ('%");
                this.sBuilder.append(this.searchData);
                this.sBuilder.append("%')");
                break;
            case 3:
                this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA);
                this.sBuilder.append(" LIKE ('%");
                this.sBuilder.append(this.searchData);
                this.sBuilder.append("')");
                break;
        }

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));

            lista.add(item);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Item> searchByGroup() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" WHERE ");

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
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.GRUPO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(grupo);
        if(subGrupo > 0)
        {
            this.sBuilder.append("' AND ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.SUBGRUPO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(subGrupo);

            if(divisao > 0)
            {
                this.sBuilder.append("' AND ");
                this.sBuilder.append(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DIVISAO);
                this.sBuilder.append(" = '");
                this.sBuilder.append(divisao);
            }
        }

        this.sBuilder.append("';");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));

            lista.add(item);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Item> searchByGravosos() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));

            lista.add(item);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Item> searchByMix() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));

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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" WHERE ");
        /*switch*/
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
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
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));

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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(" ON (");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRODUTO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(") WHERE (");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO);
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
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));

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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Item item = new Item();

            item.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
            item.setBarras(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
            item.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
            item.setReferencia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
            item.setComplemento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
            item.setUnidadeVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
            item.setUnidade(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
            item.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
            item.setMinimoVenda(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
            item.setFlex(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
            item.setQuantidadeCaixa(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));

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
        i.setBarras(item.substring(102, 116));
        i.setMinimoVenda(Integer.parseInt(item.substring(82, 86)));
        i.setFaixa(Float.parseFloat(item.substring(86, 90)) / 100);
        i.setPeso(Float.parseFloat(item.substring(90, 96)) / 100);
        i.setPesoCd(Float.parseFloat(item.substring(96, 102)) / 100);
        i.setFlex(item.substring(118, 119));
        i.setContribuicao(Float.parseFloat(item.substring(151, 157)) / 100);

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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" WHERE ");

        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(codigo);
        this.sBuilder.append("'");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        Item item = new Item();

        item.setCodigo(
                c.getInt(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO)));
        item.setBarras(
                c.getString(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS)));
        item.setDescricao(
                c.getString(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));
        item.setReferencia(
                c.getString(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA)));
        item.setComplemento(
                c.getString(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO)));
        item.setUnidadeVenda(
                c.getString(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA)));
        item.setUnidade(
                c.getString(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE)));
        item.setDesconto(
                c.getDouble(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCONTO)));
        item.setMinimoVenda(
                c.getInt(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO)));
        item.setFlex(
                c.getString(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.FLEX)));
        item.setQuantidadeCaixa(
                c.getInt(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA)));

        item.setGrupo(
                c.getInt(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.GRUPO)));
        item.setSubGrupo(
                c.getInt(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.SUBGRUPO)));
        item.setDivisao(
                c.getInt(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.DIVISAO)));

        return item;
    }

    private HashMap<String, String> searchSaleData(int codigo, int tabela, int minimo)
    {
        HashMap<String, String> saleMap = new HashMap<String, String>();
        Cursor c;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(" ON (");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRODUTO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(") WHERE (");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(tabela);
        this.sBuilder.append(") AND (");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigo);
        this.sBuilder.append(");");

        c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            saleMap.put("TABELA",
                String.valueOf(c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRECO))));

            saleMap.put("QTDMINIMA",
                String.valueOf(c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.MINIMO))));
            saleMap.put("UNIDADE",
                String.valueOf(c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADE))));
            saleMap.put("UNVENDA",
                String.valueOf(c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.UNIDADEVENDA))));

            saleMap.put("BARRAS",
                String.valueOf(c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.BARRAS))));
            saleMap.put("QTDCAIXA",
                String.valueOf(c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.QUANTIDADECAIXA))));

            c.moveToNext();
        }

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" ON (");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRODUTO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(") WHERE (");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(minimo);
        this.sBuilder.append(") AND (");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(".");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigo);
        this.sBuilder.append(");");

        float valorMinimo = 0;
        c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            valorMinimo = c.getFloat(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRECO));

            c.moveToNext();
        }
        saleMap.put("MINIMO", String.valueOf(valorMinimo));

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT COUNT (*) FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);
        this.sBuilder.append(" WHERE (");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigo);
        this.sBuilder.append(");");

        float promos = 0;
        c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        promos = c.getInt(0);

        saleMap.put("PROMOCAO", String.valueOf(promos > 0 ? true : false));

        return saleMap;
    }

    private Boolean apagar(Item d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
            this.sBuilder.append(" = '");
            d.getCodigo();
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
}
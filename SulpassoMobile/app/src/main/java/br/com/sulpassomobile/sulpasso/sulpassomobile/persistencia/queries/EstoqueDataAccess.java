package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Estoque;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 15/08/2016.
 */
public class EstoqueDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private int searchData;

    public EstoqueDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public ArrayList<Estoque> buscarTodos() throws GenercicException
    {
        return this.searchAll();
    }

    public ArrayList<Estoque> buscarRestrito() throws GenercicException
    {
        return this.searchByData(this.searchType);
    }

    public Boolean inserir(String data) throws GenercicException
    {
        return this.inserir(this.dataConverter(data));
    }

    public Boolean inserir(Estoque estoque) throws GenercicException
    {
        return this.insertEstoque(estoque);
    }

    private Boolean insertEstoque(Estoque estoque) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.ESTOQUE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.EMPRESA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.PRODUTO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(estoque.getEstoque());
        this.sBuilder.append("', '");
        this.sBuilder.append(estoque.getLoja());
        this.sBuilder.append("', '");
        this.sBuilder.append(estoque.getCodigoProduto());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private ArrayList<Estoque> searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Estoque estoque = new Estoque();

            estoque.setCodigoProduto(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.PRODUTO)));
            estoque.setEstoque(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.ESTOQUE)));
            estoque.setLoja(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.EMPRESA)));

            lista.add(estoque);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Estoque> searchByData(int type) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Estoque estoque = new Estoque();

            estoque.setCodigoProduto(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.PRODUTO)));
            estoque.setEstoque(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque.ESTOQUE)));

            lista.add(estoque);
            c.moveToNext();
        }

        return lista;
    }

    private Estoque dataConverter(String estoque)
    {
        Estoque e = new Estoque();

        e.setCodigoProduto(Integer.parseInt(estoque.substring(2, 9)));
        e.setEstoque(Double.parseDouble(estoque.substring(9, 22)) / 100);
        e.setLoja(Integer.parseInt(estoque.substring(22)));

        return e;
    }
}
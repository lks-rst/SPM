package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Preco;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 15/08/2016.
 */
public class PrecoDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private int searchData;

    public PrecoDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public ArrayList<Preco> buscarTodos() throws GenercicException
    {
        return this.searchAll();
    }

    public ArrayList<Preco> buscarRestrito() throws GenercicException
    {
        return this.searchByData(this.searchType);
    }

    public Boolean inserir(String data) throws GenercicException
    {
        return this.inserir(this.dataConverter(data));
    }

    public Boolean inserir(Preco preco) throws GenercicException
    {
        return this.insertPreco(preco);
    }

    private Boolean insertPreco(Preco preco) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRECO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRODUTO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(preco.getTabelaPrecos());
        this.sBuilder.append("', '");
        this.sBuilder.append(preco.getPreco());
        this.sBuilder.append("', '");
        this.sBuilder.append(preco.getCodigoItem());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private ArrayList<Preco> searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Preco preco = new Preco();

            preco.setTabelaPrecos(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO)));
            preco.setPreco(
                c.getDouble(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRECO)));
            preco.setCodigoItem(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRODUTO)));

            lista.add(preco);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Preco> searchByData(int type) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Preco preco = new Preco();

            preco.setCodigoItem(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.CODIGO)));
            preco.setPreco(
                c.getDouble(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco.PRECO)));

            lista.add(preco);
            c.moveToNext();
        }

        return lista;
    }

    private Preco dataConverter(String preco)
    {
        Preco p = new Preco();

        p.setTabelaPrecos(Integer.parseInt(preco.substring(9, 11)));
        p.setCodigoItem(Integer.parseInt(preco.substring(2, 9)));
        p.setPreco(Double.parseDouble(preco.substring(11)) / 100);

        return p;
    }
}
package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 15/08/2016.
 */
public class PrazoDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private int searchData;

    public PrazoDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public int getSearchData() { return searchData; }

    public void setSearchData(int searchData) { this.searchData = searchData; }

    public ArrayList<Prazo> buscarTodos() throws GenercicException
    {
        return this.searchAll();
    }

    public ArrayList<Prazo> buscarRestrito() throws GenercicException
    {
        return this.searchByData(this.searchType);
    }

    public Boolean inserir(String data) throws GenercicException
    {
        return this.inserir(this.dataConverter(data));
    }

    public Boolean inserir(Prazo prazo) throws GenercicException
    {
        return this.insertPrazo(prazo);
    }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private Boolean insertPrazo(Prazo prazo) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.TAB);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.DESDOBRAMENTO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(prazo.getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(prazo.getTabela());
        this.sBuilder.append("', '");
        this.sBuilder.append(prazo.getDesdobramento());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private ArrayList<Prazo> searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.DESDOBRAMENTO);
        this.sBuilder.append(" NOT LIKE '000-000-000-000'");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Prazo prazo = new Prazo();

            prazo.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.CODIGO)));
            prazo.setDesdobramento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.DESDOBRAMENTO)));
            prazo.setTabela(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.TAB)) );

            lista.add(prazo);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }

    private ArrayList<Prazo> searchByData(int type) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.TABELA);
        this.sBuilder.append(" WHERE ");

        if(this.searchType == 1)
        {
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.CODIGO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(this.searchData);
            this.sBuilder.append("'");
        }
        else
        {
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.DESDOBRAMENTO);
            this.sBuilder.append(" LIKE '000-000-000-000'");
        }

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Prazo prazo = new Prazo();

            prazo.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.CODIGO)));
            prazo.setDesdobramento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.DESDOBRAMENTO)));
            prazo.setTabela(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.TAB)) );

            lista.add(prazo);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }

    private Prazo dataConverter(String prazo)
    {
        Prazo p = new Prazo();

        p.setCodigo(Integer.parseInt(prazo.substring(2, 5)));
        p.setTabela(Integer.parseInt(prazo.substring(5, 7)));
        p.setDesdobramento(prazo.substring(7, 22));

        return p;
    }

    private Boolean apagar(Prazo d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.CODIGO);
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
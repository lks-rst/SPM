package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.TiposVenda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 13/10/2016.
 */
public class TipoVendaDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public TipoVendaDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public List getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList getByData(String g) throws GenercicException { return this.searchByData(g); }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(TiposVenda tiposVenda) throws GenercicException { return this.inserirTiposVenda(tiposVenda); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private TiposVenda dataConverter(String tiposVenda)
    {
        TiposVenda tv = new TiposVenda();
        tv.setReferencia(tiposVenda.substring(2, 4).trim());
        tv.setDescricao(tiposVenda.substring(4, 30).trim());
        tv.setMinimo(Float.parseFloat(tiposVenda.substring(39, 47).trim()) / 100);

        return tv;
    }

    private Boolean inserirTiposVenda(TiposVenda tiposVenda) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.DESCRICAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.MINIMO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(tiposVenda.getReferencia());
        this.sBuilder.append("', '");
        this.sBuilder.append(tiposVenda.getDescricao());
        this.sBuilder.append("', '");
        this.sBuilder.append(tiposVenda.getMinimo());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private ArrayList searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            TiposVenda tiposVenda = new TiposVenda();

            tiposVenda.setReferencia(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.CODIGO)));
            tiposVenda.setDescricao(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.DESCRICAO)));
            tiposVenda.setMinimo(
                c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.MINIMO)));


            lista.add(tiposVenda);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchByData(String g) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.CODIGO);
        this.sBuilder.append(" LIKE(' ");
        this.sBuilder.append(g);
        this.sBuilder.append(" '); ");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            TiposVenda tiposVenda = new TiposVenda();

            tiposVenda.setReferencia(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.CODIGO)));
            tiposVenda.setDescricao(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.DESCRICAO)));
            tiposVenda.setMinimo(
                c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.MINIMO)));


            lista.add(tiposVenda);
            c.moveToNext();
        }

        return lista;
    }

    private Boolean apagar(TiposVenda d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda.CODIGO);
            this.sBuilder.append(" LIKE '");
            d.getReferencia();
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
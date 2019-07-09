package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Visita;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 16/11/2017 - 10:52 as part of the project SulpassoMobile.
 */
public class VisitaDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private String searchData;

    public VisitaDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public int getSearchType() { return searchType; }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public String getSearchData() { return searchData; }

    public void setSearchData(String searchData) { this.searchData = searchData; }

    public List getAll() throws GenercicException { return this.searchAll(); }

    public Boolean insert(Visita visita) throws GenercicException { return this.inserirVisita(visita); }

    @NonNull
    private Boolean inserirVisita(Visita visita) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.PEDIDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.MOT);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.DATA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.HORA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.DIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.VDA);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(visita.getPed());
        this.sBuilder.append("', '");
        this.sBuilder.append(visita.getCli());
        this.sBuilder.append("', '");
        this.sBuilder.append(visita.getMotivo());
        this.sBuilder.append("', '");
        this.sBuilder.append(visita.getData());
        this.sBuilder.append("', '");
        this.sBuilder.append(visita.getHora());
        this.sBuilder.append("', '");
        this.sBuilder.append(visita.getDia());
        this.sBuilder.append("', '");
        this.sBuilder.append(visita.getVenda());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private List searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Visita visita = new Visita();

            visita.setPed(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.PEDIDO)));
            visita.setCli(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.CLIENTE)));
            visita.setData(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.DATA)));
            visita.setHora(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.HORA)));
            visita.setDia(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.DIA)));
            visita.setVenda(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.VDA)));
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }

    public Visita getByData() throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.PEDIDO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(this.getSearchData());

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        Visita visita = new Visita();

        for(int i = 0; i < c.getCount(); i++)
        {
            visita.setPed(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.PEDIDO)));
            visita.setCli(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.CLIENTE)));
            visita.setData(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.DATA)));
            visita.setHora(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.HORA)));
            visita.setDia(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.DIA)));
            visita.setVenda(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.VDA)));

            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return visita;
    }

    public List getGetNotSent() throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.ENVIADO);
        this.sBuilder.append(" = 'F'");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        Visita visita = new Visita();
        ArrayList<Visita> visitas = new ArrayList();

        for(int i = 0; i < c.getCount(); i++)
        {
            visita.setPed(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.PEDIDO)));
            visita.setCli(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.CLIENTE)));
            visita.setData(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.DATA)));
            visita.setHora(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.HORA)));
            visita.setDia(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.DIA)));
            visita.setVenda(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita.VDA)));

            visitas.add(visita);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return visitas;
    }
}
package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Corte;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 10/11/2016 - 17:24 as part of the project SulpassoMobile.
 */
public class CorteDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public CorteDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public List getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList getByData(int g) throws GenercicException
    {
        return this.searchByData(g);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Corte corte) throws GenercicException { return this.inserirGrupo(corte); }

    private Corte dataConverter(String data)
    {
        Corte corte = new Corte();
        Cliente cli = new Cliente();
        ItensVendidos item = new ItensVendidos();
        ArrayList<ItensVendidos> itens = new ArrayList<>();

        cli.setCodigoCliente(Integer.parseInt(data.substring(12, 19).trim()));
        item.setItem(Integer.parseInt(data.substring(19, 26).trim()));
        item.setQuantidade(Float.parseFloat(data.substring(26, 31).trim()) / 31);
        itens.add(item);

        corte.setCliente(cli);
        corte.setItensCortados(itens);
        corte.setData(data.substring(2, 12).trim());

        return corte;
    }

    private Boolean inserirGrupo(Corte corte) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.DATA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.PRODUTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.QUANTIDADE);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(corte.getData());
        this.sBuilder.append("', '");
        this.sBuilder.append(corte.getCliente().getCodigoCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(corte.getItensCortados().get(0).getItem());
        this.sBuilder.append("', '");
        this.sBuilder.append(corte.getItensCortados().get(0).getQuantidade());
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Corte corte = new Corte();
            Cliente cli = new Cliente();
            ItensVendidos item = new ItensVendidos();
            ArrayList<ItensVendidos> itens = new ArrayList<>();

            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.CLIENTE)));
            item.setItem(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.PRODUTO)));
            item.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.QUANTIDADE)));
            itens.add(item);

            corte.setCliente(cli);
            corte.setItensCortados(itens);
            corte.setData(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.DATA)));

            lista.add(corte);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchByData(int g) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Corte corte = new Corte();
            Cliente cli = new Cliente();
            ItensVendidos item = new ItensVendidos();
            ArrayList<ItensVendidos> itens = new ArrayList<>();

            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.CLIENTE)));
            item.setItem(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.PRODUTO)));
            item.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.QUANTIDADE)));
            itens.add(item);

            corte.setCliente(cli);
            corte.setItensCortados(itens);
            corte.setData(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Corte.DATA)));

            lista.add(corte);
            c.moveToNext();
        }

        return lista;
    }
}
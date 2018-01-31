package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.modelo.Mix;
import br.com.sulpasso.sulpassomobile.modelo.Tipologia;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 10/11/2016 - 13:36 as part of the project SulpassoMobile.
 */
public class MixDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public MixDataAccess(Context context)
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

    public Boolean insert(Mix mix) throws GenercicException { return this.inserirGrupo(mix); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private Mix dataConverter(String data)
    {
        Mix mix = new Mix();
        Tipologia tipo = new Tipologia();

        tipo.setTipologia(Integer.parseInt(data.substring(2, 6).trim()));

        ArrayList<Item> itens = new ArrayList<>();
        Item i = new Item();
        i.setCodigo(Integer.parseInt(data.substring(6, 13).trim()));
        itens.add(i);

        mix.setTipo(data.substring(13, 14).trim());
        mix.setItens(itens);
        mix.setTipologia(tipo);

        return mix;
    }

    private Boolean inserirGrupo(Mix mix) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TIPOLOGIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.PRODUTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TIPO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(mix.getTipologia().getTipologia());
        this.sBuilder.append("', '");
        this.sBuilder.append(mix.getItens().get(0).getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(mix.getTipo());
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
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Mix m = new Mix();
            Tipologia t = new Tipologia();
            t.setTipologia(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TIPOLOGIA)));

            ArrayList<Item> itens = new ArrayList<>();
            Item item = new Item();
            item.setCodigo(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.PRODUTO));
            itens.add(item);

            m.setTipologia(t);
            m.setItens(itens);
            m.setTipo(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TIPO)));

            lista.add(m);
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
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TIPOLOGIA);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TIPO);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Mix m = new Mix();
            Tipologia t = new Tipologia();
            t.setTipologia(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TIPOLOGIA)));

            ArrayList<Item> itens = new ArrayList<>();
            Item item = new Item();
            item.setCodigo(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.PRODUTO));
            itens.add(item);

            m.setTipologia(t);
            m.setItens(itens);
            m.setTipo(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TIPO)));

            lista.add(m);
            c.moveToNext();
        }

        return lista;
    }
    private Boolean apagar(Mix d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix.TIPOLOGIA);
            this.sBuilder.append(" = '");
            d.getTipologia().getTipologia();
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
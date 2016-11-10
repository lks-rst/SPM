package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Gravosos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 10/11/2016 - 14:00 as part of the project SulpassoMobile.
 */
public class GravososDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public GravososDataAccess(Context context)
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

    public Boolean insert(Gravosos gravoso) throws GenercicException { return this.inserirGrupo(gravoso); }

    private Gravosos dataConverter(String data)
    {
        Gravosos gravoso = new Gravosos();

        Item i = new Item();
        i.setCodigo(Integer.parseInt(data.substring(2, 9).trim()));

        gravoso.setItem(i);
        gravoso.setQuantidade(Float.parseFloat(data.substring(9, 16).trim()) / 100);
        gravoso.setFabricacao(data.substring(16, 26).trim());
        gravoso.setDias(Integer.parseInt(data.substring(26, 32).trim()));
        gravoso.setValidade(data.substring(32, 42).trim());

        return gravoso;
    }

    private Boolean inserirGrupo(Gravosos gravoso) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.FABRICACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.DIAS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.VALIDADE);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(gravoso.getItem());
        this.sBuilder.append("', '");
        this.sBuilder.append(gravoso.getQuantidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(gravoso.getFabricacao());
        this.sBuilder.append("', '");
        this.sBuilder.append(gravoso.getDias());
        this.sBuilder.append("', '");
        this.sBuilder.append(gravoso.getValidade());
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Gravosos gravoso = new Gravosos();

            Item item = new Item();
            item.setCodigo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM)));

            gravoso.setItem(item);
            gravoso.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.QUANTIDADE)));
            gravoso.setFabricacao(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.FABRICACAO)));
            gravoso.setDias(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.DIAS)));
            gravoso.setValidade(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.VALIDADE)));

            lista.add(gravoso);
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Gravosos gravoso = new Gravosos();

            Item item = new Item();
            item.setCodigo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM)));

            gravoso.setItem(item);
            gravoso.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.QUANTIDADE)));
            gravoso.setFabricacao(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.FABRICACAO)));
            gravoso.setDias(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.DIAS)));
            gravoso.setValidade(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.VALIDADE)));

            lista.add(gravoso);
            c.moveToNext();
        }

        return lista;
    }
}
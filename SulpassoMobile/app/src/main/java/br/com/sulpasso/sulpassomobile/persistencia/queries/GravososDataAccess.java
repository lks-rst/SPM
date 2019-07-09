package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Gravosos;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

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

    public ArrayList<Gravosos> getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList getByData(int g) throws GenercicException
    {
        return this.searchByData(g);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Gravosos gravoso) throws GenercicException { return this.inserirGrupo(gravoso); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private Gravosos dataConverter(String data)
    {
        Gravosos gravoso = new Gravosos();

        Item i = new Item();
        i.setCodigo(Integer.parseInt(data.substring(2, 9).trim()));

        gravoso.setItem(i);
        gravoso.setQuantidade(Float.parseFloat(data.substring(9, 18).trim()) / 100);
        gravoso.setFabricacao(data.substring(18, 28).trim());
        gravoso.setDias(Integer.parseInt(data.substring(28, 34).trim()));
        gravoso.setValidade(data.substring(34, 44).trim());

        return gravoso;
    }

    private Boolean inserirGrupo(Gravosos gravoso) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.FABRICACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.DIAS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.VALIDADE);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(gravoso.getItem().getCodigo());
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

        /*
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.TABELA);
        */

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

            /*
            item.setCodigo(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM)));

            gravoso.setItem(item);
            gravoso.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.QUANTIDADE)));
            gravoso.setFabricacao(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.FABRICACAO)));
            gravoso.setDias(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.DIAS)));
            gravoso.setValidade(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.VALIDADE)));
            */

            lista.add(gravoso);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }

    private ArrayList searchByData(int g) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Gravosos gravoso = new Gravosos();

            Item item = new Item();
            item.setCodigo(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM)));

            gravoso.setItem(item);
            gravoso.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.QUANTIDADE)));
            gravoso.setFabricacao(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.FABRICACAO)));
            gravoso.setDias(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.DIAS)));
            gravoso.setValidade(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.VALIDADE)));

            lista.add(gravoso);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }
    private Boolean apagar(Gravosos d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos.ITEM);
            this.sBuilder.append(" = '");
            d.getItem().getCodigo();
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
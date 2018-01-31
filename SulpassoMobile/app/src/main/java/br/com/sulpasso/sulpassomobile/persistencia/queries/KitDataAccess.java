package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.modelo.Kit;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 10/11/2016 - 16:04 as part of the project SulpassoMobile.
 */
public class KitDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public KitDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public ArrayList<Kit> getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList getByData(int g) throws GenercicException
    {
        return this.searchByData(g);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Kit kit) throws GenercicException { return this.inserirGrupo(kit); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private Kit dataConverter(String data)
    {
        Kit kit = new Kit();
        Item item = new Item();
        item.setCodigo(Integer.parseInt(data.substring(12, 19).trim()));
        ArrayList<Item> itens = new ArrayList<>();
        itens.add(item);
        kit.setKit(data.substring(2, 12).trim());
        kit.setItens(itens);
        kit.setDescricao(data.substring(19, 49).trim());
        kit.setQuantidade(Float.parseFloat(data.substring(49, 56).trim()) / 100);
        kit.setValor(Float.parseFloat(data.substring(56, 63).trim()) / 100);

        return kit;
    }

    private Boolean inserirGrupo(Kit kit) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.KIT);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.ITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.DESC);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.VALOR);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(kit.getKit());
        this.sBuilder.append("', '");
        this.sBuilder.append(kit.getItens().get(0).getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(kit.getDescricao());
        this.sBuilder.append("', '");
        this.sBuilder.append(kit.getQuantidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(kit.getValor());
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
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        /*
            TODO: Verificar melhor forma de fazer a união dos kits;
         */
        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Kit kit = new Kit();
            Item item = new Item();
            item.setCodigo(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.ITEM)));
            ArrayList<Item> itens = new ArrayList<>();

            kit.setKit(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.KIT)));
            kit.setItens(itens);
            kit.setDescricao(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.DESC)));
            kit.setQuantidade(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.QUANTIDADE)));
            kit.setValor(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.VALOR)));

            lista.add(kit);
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
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Kit kit = new Kit();
            Item item = new Item();
            item.setCodigo(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.ITEM)));
            ArrayList<Item> itens = new ArrayList<>();

            kit.setKit(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.KIT)));
            kit.setItens(itens);
            kit.setDescricao(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.DESC)));
            kit.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.QUANTIDADE)));
            kit.setValor(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.VALOR)));

            lista.add(kit);
            c.moveToNext();
        }

        return lista;
    }

    private Boolean apagar(Kit d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit.KIT);
            this.sBuilder.append(" LIKE '");
            d.getKit();
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
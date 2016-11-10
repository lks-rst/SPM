package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Banco;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 13/10/2016.
 */
public class BancoDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public BancoDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    /*
        TODO: Remover após testes de inserção. Não há (hoje) no sistema nada que necessite uma busca por todos os bancos
     */
    public List getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList getByData(int g) throws GenercicException
    {
        return this.searchByData(g);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Banco banco) throws GenercicException { return this.inserirBanco(banco); }

    private Banco dataConverter(String banco)
    {
        Banco g = new Banco();
        g.setBanco(banco.substring(5).trim());
        g.setCodigo(Integer.parseInt(banco.substring(2, 5)));

        return g;
    }

    private Boolean inserirBanco(Banco banco) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Banco.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Banco.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Banco.BANCO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(banco.getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(banco.getBanco());
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Banco.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Banco banco = new Banco();

            banco.setBanco(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Banco.BANCO)));
            banco.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Banco.CODIGO)));


            lista.add(banco);
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Banco.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Banco.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Banco banco = new Banco();

            banco.setBanco(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Banco.BANCO)));
            banco.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Banco.CODIGO)));


            lista.add(banco);
            c.moveToNext();
        }

        return lista;
    }
}
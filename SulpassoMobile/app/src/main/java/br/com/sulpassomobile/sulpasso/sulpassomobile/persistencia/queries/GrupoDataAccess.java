package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Grupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 13/10/2016.
 */
public class GrupoDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public GrupoDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public ArrayList<Grupo> getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList<Grupo> getAll(int grupo) throws GenercicException { return this.searchAll(grupo); }

    public ArrayList<Grupo> getAll(int grupo, int subGrupo) throws GenercicException { return this.searchAll(grupo, subGrupo); }

    public ArrayList getByData(int g, int s, int v) throws GenercicException
    {
        if(s == 0) { return this.searchByData(g); }
        else if(v == 0) { return this.searchByData(g, s); }
        else { return this.searchByData(g, s, v); }
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Grupo grupo) throws GenercicException { return this.inserirGrupo(grupo); }

    private Grupo dataConverter(String grupo)
    {
        Grupo g = new Grupo();
        g.setGrupo(Integer.parseInt(grupo.substring(2, 5)));
        g.setSubGrupo(Integer.parseInt(grupo.substring(5, 8)));
        g.setDivisao(Integer.parseInt(grupo.substring(8, 11)));
        g.setDescricao(grupo.substring(11).trim());

        return g;
    }

    private Boolean inserirGrupo(Grupo grupo) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DESC);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(grupo.getGrupo());
        this.sBuilder.append("', '");
        this.sBuilder.append(grupo.getSubGrupo());
        this.sBuilder.append("', '");
        this.sBuilder.append(grupo.getDivisao());
        this.sBuilder.append("', '");
        this.sBuilder.append(grupo.getDescricao());
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
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB);
        this.sBuilder.append(" = ");
        this.sBuilder.append(0);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV);
        this.sBuilder.append(" = ");
        this.sBuilder.append(0);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Grupo grupo = new Grupo();

            grupo.setGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO)));
            grupo.setSubGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB)));
            grupo.setDivisao(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV)));
            grupo.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DESC)));


            lista.add(grupo);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchAll(int g) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB);
        this.sBuilder.append(" <> ");
        this.sBuilder.append(0);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV);
        this.sBuilder.append(" = ");
        this.sBuilder.append(0);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Grupo grupo = new Grupo();

            grupo.setGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO)));
            grupo.setSubGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB)));
            grupo.setDivisao(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV)));
            grupo.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DESC)));


            lista.add(grupo);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchAll(int g, int s) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB);
        this.sBuilder.append(" = ");
        this.sBuilder.append(s);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV);
        this.sBuilder.append(" <> ");
        this.sBuilder.append(0);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Grupo grupo = new Grupo();

            grupo.setGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO)));
            grupo.setSubGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB)));
            grupo.setDivisao(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV)));
            grupo.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DESC)));


            lista.add(grupo);
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Grupo grupo = new Grupo();

            grupo.setGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO)));
            grupo.setSubGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB)));
            grupo.setDivisao(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV)));
            grupo.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DESC)));


            lista.add(grupo);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchByData(int g, int s) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB);
        this.sBuilder.append(" = ");
        this.sBuilder.append(s);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Grupo grupo = new Grupo();

            grupo.setGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO)));
            grupo.setSubGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB)));
            grupo.setDivisao(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV)));
            grupo.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DESC)));


            lista.add(grupo);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchByData(int g, int s, int d) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB);
        this.sBuilder.append(" = ");
        this.sBuilder.append(s);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV);
        this.sBuilder.append(" = ");
        this.sBuilder.append(d);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Grupo grupo = new Grupo();

            grupo.setGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO)));
            grupo.setSubGrupo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB)));
            grupo.setDivisao(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV)));
            grupo.setDescricao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DESC)));


            lista.add(grupo);
            c.moveToNext();
        }

        return lista;
    }
}
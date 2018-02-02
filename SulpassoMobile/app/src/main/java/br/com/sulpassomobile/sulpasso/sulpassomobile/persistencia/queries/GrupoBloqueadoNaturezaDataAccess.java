package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Grupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.GrupoBloqueadoNatureza;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 10/11/2016 - 17:42 as part of the project SulpassoMobile.
 */
public class GrupoBloqueadoNaturezaDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public GrupoBloqueadoNaturezaDataAccess(Context context)
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

    public Boolean insert(GrupoBloqueadoNatureza g) throws GenercicException { return this.inserirGrupo(g); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private GrupoBloqueadoNatureza dataConverter(String data)
    {
        GrupoBloqueadoNatureza grupoBloqueadoNatureza = new GrupoBloqueadoNatureza();
        Natureza c = new Natureza();
        Grupo g = new Grupo();
        ArrayList<Grupo> grupos = new ArrayList<>();

        c.setCodigo(Integer.parseInt(data.substring(11, 14).trim()));
        g.setGrupo(Integer.parseInt(data.substring(2, 5).trim()));
        g.setSubGrupo(Integer.parseInt(data.substring(5, 8).trim()));
        g.setDivisao(Integer.parseInt(data.substring(8, 11).trim()));
        grupos.add(g);

        grupoBloqueadoNatureza.setNatureza(c);
        grupoBloqueadoNatureza.setGrupos(grupos);

        return grupoBloqueadoNatureza;
    }

    private Boolean inserirGrupo(GrupoBloqueadoNatureza g) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.NATUREZA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.GRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.SUB);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.DIV);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(g.getNatureza().getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(g.getGrupos().get(0).getGrupo());
        this.sBuilder.append("', '");
        this.sBuilder.append(g.getGrupos().get(0).getSubGrupo());
        this.sBuilder.append("', '");
        this.sBuilder.append(g.getGrupos().get(0).getDivisao());
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            GrupoBloqueadoNatureza grupoBloqueadoNatureza = new GrupoBloqueadoNatureza();
            Natureza cli = new Natureza();
            Grupo g = new Grupo();
            ArrayList<Grupo> grupos = new ArrayList<>();

            cli.setCodigo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.NATUREZA)));
            g.setGrupo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.GRUPO)));
            g.setSubGrupo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.SUB)));
            g.setDivisao(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.DIV)));
            grupos.add(g);

            grupoBloqueadoNatureza.setNatureza(cli);
            grupoBloqueadoNatureza.setGrupos(grupos);

            lista.add(grupoBloqueadoNatureza);
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.NATUREZA);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            GrupoBloqueadoNatureza grupoBloqueadoNatureza = new GrupoBloqueadoNatureza();
            Natureza cli = new Natureza();
            Grupo grupo = new Grupo();
            ArrayList<Grupo> grupos = new ArrayList<>();

            cli.setCodigo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.NATUREZA)));
            grupo.setGrupo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.GRUPO)));
            grupo.setSubGrupo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.SUB)));
            grupo.setDivisao(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.DIV)));
            grupos.add(grupo);

            grupoBloqueadoNatureza.setNatureza(cli);
            grupoBloqueadoNatureza.setGrupos(grupos);

            lista.add(grupoBloqueadoNatureza);
            c.moveToNext();
        }

        return lista;
    }

    private Boolean apagar(GrupoBloqueadoNatureza d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza.GRUPO);
            this.sBuilder.append(" = '");
            d.getGrupos();
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
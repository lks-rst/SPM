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
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Grupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.GrupoBloqueadoCliente;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 10/11/2016 - 16:59 as part of the project SulpassoMobile.
 */
public class GrupoBloqueadoClienteDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public GrupoBloqueadoClienteDataAccess(Context context)
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

    public Boolean insert(GrupoBloqueadoCliente g) throws GenercicException { return this.inserirGrupo(g); }

    private GrupoBloqueadoCliente dataConverter(String data)
    {
        GrupoBloqueadoCliente grupoBloqueadoCliente = new GrupoBloqueadoCliente();
        Cliente c = new Cliente();
        Grupo g = new Grupo();
        ArrayList<Grupo> grupos = new ArrayList<>();

        c.setCodigoCliente(Integer.parseInt(data.substring(2, 9).trim()));
        g.setGrupo(Integer.parseInt(data.substring(9, 12).trim()));
        g.setSubGrupo(Integer.parseInt(data.substring(12, 15).trim()));
        g.setDivisao(Integer.parseInt(data.substring(15, 18).trim()));
        grupos.add(g);

        grupoBloqueadoCliente.setCliente(c);
        grupoBloqueadoCliente.setGrupos(grupos);

        return grupoBloqueadoCliente;
    }

    private Boolean inserirGrupo(GrupoBloqueadoCliente g) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.GRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.SUB);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.DIV);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(g.getCliente().getCodigoCliente());
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            GrupoBloqueadoCliente grupoBloqueadoCliente = new GrupoBloqueadoCliente();
            Cliente cli = new Cliente();
            Grupo g = new Grupo();
            ArrayList<Grupo> grupos = new ArrayList<>();

            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.CLIENTE)));
            g.setGrupo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.GRUPO)));
            g.setSubGrupo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.SUB)));
            g.setDivisao(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.DIV)));
            grupos.add(g);

            grupoBloqueadoCliente.setCliente(cli);
            grupoBloqueadoCliente.setGrupos(grupos);

            lista.add(grupoBloqueadoCliente);
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
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            GrupoBloqueadoCliente grupoBloqueadoCliente = new GrupoBloqueadoCliente();
            Cliente cli = new Cliente();
            Grupo grupo = new Grupo();
            ArrayList<Grupo> grupos = new ArrayList<>();

            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.CLIENTE)));
            grupo.setGrupo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.GRUPO)));
            grupo.setSubGrupo(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.SUB)));
            grupo.setDivisao(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente.DIV)));
            grupos.add(grupo);

            grupoBloqueadoCliente.setCliente(cli);
            grupoBloqueadoCliente.setGrupos(grupos);

            lista.add(grupoBloqueadoCliente);
            c.moveToNext();
        }

        return lista;
    }
}
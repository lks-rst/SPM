package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.CurvaAbc;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 10/11/2016 - 15:23 as part of the project SulpassoMobile.
 */
public class CurvaAbcDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public CurvaAbcDataAccess(Context context)
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

    public Boolean insert(CurvaAbc abc) throws GenercicException { return this.inserirGrupo(abc); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private CurvaAbc dataConverter(String data)
    {
        CurvaAbc curva = new CurvaAbc();

        Cliente c = new Cliente();
        c.setCodigoCliente(Integer.parseInt(data.substring(2, 9).trim()));

        curva.setCliente(c);
        curva.setPeso_1(Float.parseFloat(data.substring(9, 15).trim()) / 100);
        curva.setPeso_2(Float.parseFloat(data.substring(27, 33).trim()) / 100);
        curva.setCont_1(Float.parseFloat(data.substring(21, 27).trim()) / 100);
        curva.setCont_2(Float.parseFloat(data.substring(39, 45).trim()) / 100);
        curva.setFat_1(Float.parseFloat(data.substring(15, 21).trim()) / 100);
        curva.setFat_2(Float.parseFloat(data.substring(33, 39).trim()) / 100);

        return curva;
    }

    private Boolean inserirGrupo(CurvaAbc abc) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.PESO1);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.FATURAMENTO1);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CONTRIBUICAO1);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.PESO2);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.FATURAMENTO2);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CONTRIBUICAO2);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(abc.getCliente().getCodigoCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(abc.getPeso_1());
        this.sBuilder.append("', '");
        this.sBuilder.append(abc.getFat_1());
        this.sBuilder.append("', '");
        this.sBuilder.append(abc.getCont_1());
        this.sBuilder.append("', '");
        this.sBuilder.append(abc.getPeso_2());
        this.sBuilder.append("', '");
        this.sBuilder.append(abc.getFat_2());
        this.sBuilder.append("', '");
        this.sBuilder.append(abc.getCont_2());
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
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            CurvaAbc curva = new CurvaAbc();

            Cliente cli = new Cliente();
            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CLIENTE)));

            curva.setCliente(cli);
            curva.setPeso_1(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.PESO1)));
            curva.setPeso_2(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.PESO2)));
            curva.setCont_1(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CONTRIBUICAO1)));
            curva.setCont_2(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CONTRIBUICAO2)));
            curva.setFat_1(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.FATURAMENTO1)));
            curva.setFat_2(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.FATURAMENTO2)));

            lista.add(curva);
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
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            CurvaAbc curva = new CurvaAbc();

            Cliente cli = new Cliente();
            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CLIENTE)));

            curva.setCliente(cli);
            curva.setPeso_1(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.PESO1)));
            curva.setPeso_2(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.PESO2)));
            curva.setCont_1(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CONTRIBUICAO1)));
            curva.setCont_2(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CONTRIBUICAO2)));
            curva.setFat_1(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.FATURAMENTO1)));
            curva.setFat_2(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.FATURAMENTO2)));

            lista.add(curva);
            c.moveToNext();
        }

        return lista;
    }
    private Boolean apagar(CurvaAbc d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc.CLIENTE);
            this.sBuilder.append(" = '");
            d.getCliente();
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
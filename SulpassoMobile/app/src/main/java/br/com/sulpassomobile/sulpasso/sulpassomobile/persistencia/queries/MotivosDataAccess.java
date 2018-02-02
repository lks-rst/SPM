package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Motivos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 09/11/2016 - 11:39 as part of the project SulpassoMobile.
 */
public class MotivosDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public MotivosDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Motivos motivo) throws GenercicException { return this.inserir(motivo); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private Motivos dataConverter(String motivo)
    {
        Motivos m = new Motivos();
        m.setCodigo(Integer.parseInt(motivo.substring(2, 5).trim()));
        m.setMotivo(motivo.substring(5, 35).trim());

        return m;
    }

    private Boolean inserir(Motivos motivo) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Motivos.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Motivos.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Motivos.DESCRICAO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(motivo.getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(motivo.getMotivo());
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
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Motivos.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Motivos m = new Motivos();

            m.setMotivo(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Motivos.DESCRICAO)));
            m.setCodigo(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Motivos.CODIGO)));


            lista.add(m);
            c.moveToNext();
        }

        return lista;
    }
    private Boolean apagar(Motivos d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Motivos.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Motivos.CODIGO);
            this.sBuilder.append(" = '");
            d.getCodigo();
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
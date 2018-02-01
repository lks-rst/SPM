package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cidade;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 13/10/2016.
 */
public class CidadeDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public CidadeDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public ArrayList getAll() throws GenercicException { return this.searchAll(); }

    public Cidade getByData(int cod) throws GenercicException { return this.searchByData(cod); }

    public ArrayList getByData() throws GenercicException { return new ArrayList(); }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Cidade cidade) throws GenercicException { return this.inserirCidade(cidade); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private Cidade dataConverter(String cidade)
    {
        Cidade c = new Cidade();

        c.setCodigo(Integer.parseInt(cidade.substring(2, 6).trim()));
        c.setNome(cidade.substring(6, 20).trim());
        c.setUf(cidade.substring(20, 22).trim());
        c.setCep(cidade.substring(22, 31).trim());

        return c;
    }

    private Boolean inserirCidade(Cidade cidade) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.UF);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CEP);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(cidade.getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(cidade.getNome());
        this.sBuilder.append("', '");
        this.sBuilder.append(cidade.getUf());
        this.sBuilder.append("', '");
        this.sBuilder.append(cidade.getCep());
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Cidade cidade = new Cidade();

            cidade.setCodigo(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CODIGO)));
            cidade.setNome(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CIDADE)));
            cidade.setUf(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.UF)));
            cidade.setCep(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CEP)));

            lista.add(cidade);
            c.moveToNext();
        }

        return lista;
    }

    private Cidade searchByData(int cod) throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(cod);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        Cidade cidade = new Cidade();

        cidade.setCodigo(
                c.getInt(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CODIGO)));
        cidade.setNome(
                c.getString(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CIDADE)));
        cidade.setUf(
                c.getString(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.UF)));
        cidade.setCep(
                c.getString(c.getColumnIndex(
                        br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CEP)));
        return cidade;
    }

    private Boolean apagar(Cidade d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CODIGO);
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
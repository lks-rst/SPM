package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 15/08/2016.
 */
public class NaturezaDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private int searchData;

    public NaturezaDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public int getSearchData() { return searchData; }

    public void setSearchData(int searchData) { this.searchData = searchData; }

    public ArrayList<Natureza> buscarTodos() throws GenercicException
    {
        return this.searchAll();
    }

    public Natureza buscarNatureza(int codigo)
    {
        return this.getNature(codigo);
    }

    public ArrayList<Natureza> buscarRestrito() throws GenercicException
    {
        return this.searchByData(this.searchType);
    }

    public ArrayList<Natureza> buscarAVista() throws GenercicException
    {
        return this.searchByData(-1);
    }

    public Boolean inserir(String data) throws GenercicException
    {
        return this.inserir(this.dataConverter(data));
    }

    public Boolean inserir(Natureza natureza) throws GenercicException
    {
        return this.insertNatureza(natureza);
    }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private Boolean insertNatureza(Natureza natureza) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.DESCRICAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.PRAZO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.MINIMO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.BANCO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.VENDA);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(natureza.getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(natureza.getDescricao());
        this.sBuilder.append("', '");
        this.sBuilder.append(natureza.getPrazo());
        this.sBuilder.append("', '");
        this.sBuilder.append(natureza.getMinimo());
        this.sBuilder.append("', '");
        this.sBuilder.append(natureza.getBanco());
        this.sBuilder.append("', '");
        this.sBuilder.append(natureza.getVenda());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private ArrayList<Natureza> searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(br.com.sulpasso
                .sulpassomobile.persistencia.tabelas.Natureza.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Natureza natureza = new Natureza();

            natureza.setCodigo(
                    c.getInt(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.CODIGO)));
            natureza.setDescricao(
                    c.getString(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.DESCRICAO)));
            natureza.setPrazo(
                    c.getString(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.PRAZO)));
            natureza.setMinimo(
                    c.getFloat(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.MINIMO)));
            natureza.setBanco(
                    c.getInt(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.BANCO)));
            natureza.setVenda(
                    c.getString(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.VENDA)).charAt(0));

            lista.add(natureza);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }

    private ArrayList<Natureza> searchByData(int type) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        if(type == -1)
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT * FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.PRAZO);
            this.sBuilder.append(" LIKE 'A' OR ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.PRAZO);
            this.sBuilder.append(" LIKE 'a' OR ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.PRAZO);
            this.sBuilder.append(" LIKE '0';");
        }
        else
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT * FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.CODIGO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(this.searchData);
            this.sBuilder.append("';");
        }

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Natureza natureza = new Natureza();

            natureza.setCodigo(
                    c.getInt(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.CODIGO)));
            natureza.setDescricao(
                    c.getString(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.DESCRICAO)));
            natureza.setPrazo(
                    c.getString(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.PRAZO)));
            natureza.setMinimo(
                    c.getFloat(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.MINIMO)));
            natureza.setBanco(
                    c.getInt(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.BANCO)));
            natureza.setVenda(
                    c.getString(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.VENDA)).charAt(0));

            lista.add(natureza);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }

    private Natureza dataConverter(String natureza)
    {
        Natureza n = new Natureza();

        int banco;

        try { banco = Integer.parseInt(natureza.substring(32).trim()); }
        catch (Exception e) { banco = 0; }

        n.setCodigo(Integer.parseInt(natureza.substring(2, 5)));
        n.setDescricao(natureza.substring(5, 25));
        n.setPrazo(natureza.substring(25, 26));
        n.setVenda(natureza.substring(26, 27).charAt(0));
        n.setMinimo(Float.parseFloat(natureza.substring(27, 32)) / 100);
        n.setBanco(banco);

        return n;
    }

    private Natureza getNature(int codigo)
    {
        Natureza n = null;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(br.com.sulpasso
                .sulpassomobile.persistencia.tabelas.Natureza.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(br.com.sulpasso
                .sulpassomobile.persistencia.tabelas.Natureza.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigo);


        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            n = new Natureza();

            n.setCodigo(
                    c.getInt(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.CODIGO)));
            n.setDescricao(
                    c.getString(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.DESCRICAO)));
            n.setPrazo(
                    c.getString(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.PRAZO)));
            n.setMinimo(
                    c.getFloat(c.getColumnIndex(br.com.sulpasso
                            .sulpassomobile.persistencia.tabelas.Natureza.MINIMO)));

            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return n;
    }

    private Boolean apagar(Natureza d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.CODIGO);
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
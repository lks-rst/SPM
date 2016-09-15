package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 15/09/2016.
 */
public class PromocaoDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private int searchData;

    public PromocaoDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public void setSearchData(int searchData) { this.searchData = searchData; }

    public ArrayList<Promocao> buscarTodos() throws GenercicException
    {
        return this.searchAll();
    }

    public ArrayList<Promocao> getByData() throws GenercicException
    {
        return this.searchByData();
    }

    public Boolean inserir(String data) throws GenercicException
    {
        return this.inserirPromocao(this.dataConverter(data));
    }

    public Boolean inserir(Promocao promocao) throws GenercicException
    {
        return this.inserirPromocao(promocao);
    }

    private Promocao dataConverter(String promocao)
    {
        Promocao p = new Promocao();

        p.setItem(Integer.parseInt(promocao.substring(2, 9)));
        p.setQuantidade(Integer.parseInt(promocao.substring(9, 16)) / 100);
        p.setValor(Float.parseFloat(promocao.substring(16)) / 100);

        return p;
    }

    private Boolean inserirPromocao(Promocao promocao) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert into ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.VALOR);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(promocao.getItem());
        this.sBuilder.append("', '");
        this.sBuilder.append(promocao.getQuantidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(promocao.getValor());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private ArrayList<Promocao> searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Promocao promocao = new Promocao();

            promocao.setItem(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM)));
            promocao.setQuantidade(
                c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE)));
            promocao.setValor(
                c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.VALOR)));

            lista.add(promocao);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<Promocao> searchByData() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(searchData);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Promocao prmoocao = new Promocao();

            prmoocao.setItem(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM)));
            prmoocao.setQuantidade(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE)));
            prmoocao.setValor(
                c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.VALOR)));

            lista.add(prmoocao);
            c.moveToNext();
        }

        return lista;
    }
}
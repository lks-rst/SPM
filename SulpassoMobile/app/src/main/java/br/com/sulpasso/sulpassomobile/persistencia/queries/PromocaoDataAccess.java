package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item;
import br.com.sulpasso.sulpassomobile.util.modelos.GenericItemFour;

/**
 * Created by Lucas on 15/09/2016.
 */
public class PromocaoDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private String searchData;

    public PromocaoDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public void setSearchData(String searchData) { this.searchData = searchData; }

    public ArrayList<Promocao> buscarTodos() throws GenercicException
    {
        return this.searchAll();
    }

    public ArrayList<GenericItemFour> buscarTodosG() throws GenercicException
    {
        return this.searchAllG();
    }

    public Promocao buscarPromocao(int item, float quantidade) throws GenercicException
    {
        return this.searchPromo(item, quantidade);
        /*
        if(quantidade > 0)
            return this.searchPromo(item, quantidade);
        else
            return this.searchPromo(item, quantidade);
        */
    }

    public ArrayList<Promocao> buscarPromocao(int item) throws GenercicException
    {
        return this.searchPromo(item);
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

    public Boolean delete() throws GenercicException { return this.apagar(null); }

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
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.VALOR);
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
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Promocao promocao = new Promocao();

            promocao.setItem(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM)));
            promocao.setQuantidade(
                c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE)));
            promocao.setValor(
                c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.VALOR)));

            lista.add(promocao);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList<GenericItemFour> searchAllG() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.VALOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(Item.DESCRICAO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(Item.TABELA);
        this.sBuilder.append(" ON ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(Item.CODIGO);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            GenericItemFour promocao = new GenericItemFour();

            promocao.setDataOne(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM)));
            promocao.setDataTwo(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE)));
            promocao.setDataThre(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.VALOR)));
            promocao.setDataFour(c.getString(c.getColumnIndex(Item.DESCRICAO)));

            lista.add(promocao);
            c.moveToNext();
        }

        return lista;
    }

    private Promocao searchPromo(int item, float quantidade) throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT MAX(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE);
        this.sBuilder.append(") FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(item);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE);
        this.sBuilder.append(" <= ");
        this.sBuilder.append(quantidade);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(item);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(c.getInt(0));

        c = this.db.rawQuery(this.sBuilder.toString(), null);

        if(c.getCount() > 0)
        {
            c.moveToFirst();

            Promocao promocao = new Promocao();

            promocao.setItem(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM)));
            promocao.setQuantidade(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE)));
            promocao.setValor(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.VALOR)));

            return promocao;
        }
        else
            return null;
    }

    private ArrayList<Promocao> searchPromo(int item) throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(item);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        ArrayList<Promocao> lista = new ArrayList<>();
        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++)
        {
            Promocao promocao = new Promocao();

            promocao.setItem(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM)));
            promocao.setQuantidade(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE)));
            promocao.setValor(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.VALOR)));

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
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
        this.sBuilder.append(" = ");
        this.sBuilder.append(searchData);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Promocao prmoocao = new Promocao();

            prmoocao.setItem(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM)));
            prmoocao.setQuantidade(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.QUANTIDADE)));
            prmoocao.setValor(
                c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.VALOR)));

            lista.add(prmoocao);
            c.moveToNext();
        }

        return lista;
    }

    private Boolean apagar(Promocao d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao.ITEM);
            this.sBuilder.append(" = '");
            d.getItem();
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
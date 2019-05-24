package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaProduto;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 13/10/2016.
 */
public class CampanhaProdutoDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private int searchData;

    public CampanhaProdutoDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public ArrayList getAll() throws GenercicException { return this.searchAll(); }

    public CampanhaProduto getByData(int codigo) throws GenercicException { return this.searchCamp(codigo); }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(CampanhaProduto campanhaProduto) throws GenercicException { return this.inserirCampanhaProduto(campanhaProduto); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private CampanhaProduto dataConverter(String campanhaProduto)
    {
        CampanhaProduto cg = new CampanhaProduto();
        ArrayList<Integer> item = new ArrayList<>();

        cg.setCodigo(Integer.parseInt(campanhaProduto.substring(2, 7).trim()));
        cg.setQuantidade(Integer.parseInt(campanhaProduto.substring(7, 13).trim()));
        item.add(new Integer(campanhaProduto.substring(13, 20).trim()));
        cg.setItens(item);
        cg.setDesconto(Float.parseFloat(campanhaProduto.substring(20, 25).trim()) / 100);

        return cg;
    }

    private Boolean inserirCampanhaProduto(CampanhaProduto campanhaProduto) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.ITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.DESCONTO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(campanhaProduto.getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(campanhaProduto.getQuantidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(campanhaProduto.getItens().get(0));
        this.sBuilder.append("', '");
        this.sBuilder.append(campanhaProduto.getDesconto());
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
        this.sBuilder.append("SELECT DISTINCT(");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
        this.sBuilder.append(") FROM ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            CampanhaProduto campanhaProduto = new CampanhaProduto();

            campanhaProduto.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO)));

            lista.add(campanhaProduto);
            c.moveToNext();
        }

        for(int i = 0; i < lista.size(); i++)
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.QUANTIDADE);
            this.sBuilder.append(", ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.DESCONTO);
            this.sBuilder.append(" FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(((CampanhaProduto) lista.get(i)).getCodigo());

            c = this.db.rawQuery(this.sBuilder.toString(), null);
            c.moveToFirst();

            ((CampanhaProduto) lista.get(i)).setQuantidade(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.QUANTIDADE)));
            ((CampanhaProduto) lista.get(i)).setDesconto(
                c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.DESCONTO)));
        }

        for(int i = 0; i < lista.size(); i++)
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.ITEM);
            this.sBuilder.append(" FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(((CampanhaProduto) lista.get(i)).getCodigo());

            c = this.db.rawQuery(this.sBuilder.toString(), null);
            c.moveToFirst();

            ArrayList<Integer> item = new ArrayList<>();
            for(int d = 0; d < c.getCount(); d++)
            {
                item.add(new Integer(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.ITEM))));

                c.moveToNext();
            }

            ((CampanhaProduto) lista.get(i)).setItens(item);
        }

        return lista;
    }

    private Boolean apagar(CampanhaProduto d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
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

    private CampanhaProduto searchCamp(int codigo) throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.ITEM);
        this.sBuilder.append(" = '");
        this.sBuilder.append(codigo);
        this.sBuilder.append("';");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        if(c.getCount() > 0)
        {
            c.moveToFirst();

            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT * FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(c.getInt(0));
            this.sBuilder.append("';");

            CampanhaProduto campanhaProdutos = new CampanhaProduto();

            c = this.db.rawQuery(this.sBuilder.toString(), null);

            c.moveToFirst();

            campanhaProdutos.setCodigo(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO)));
            campanhaProdutos.setQuantidade(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.QUANTIDADE)));
            campanhaProdutos.setDesconto(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.DESCONTO)));

            ArrayList<Integer> itens = new ArrayList<>();
            for(int i = 0; i < c.getCount(); i++)
            {
                itens.add(c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.ITEM)));
                c.moveToNext();
            }

            campanhaProdutos.setItens(itens);


            return campanhaProdutos;
        }
        else { return null; }
    }

}
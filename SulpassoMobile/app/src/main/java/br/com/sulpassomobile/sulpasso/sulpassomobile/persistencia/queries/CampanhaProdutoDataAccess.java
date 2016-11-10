package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.CampanhaProduto;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

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

    public ArrayList getByData() throws GenercicException { return new ArrayList(); }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(CampanhaProduto campanhaProduto) throws GenercicException { return this.inserirCampanhaProduto(campanhaProduto); }

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
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.ITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.DESCONTO);
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
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
        this.sBuilder.append(") FROM ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            CampanhaProduto campanhaProduto = new CampanhaProduto();

            campanhaProduto.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO)));

            lista.add(campanhaProduto);
            c.moveToNext();
        }

        for(int i = 0; i < lista.size(); i++)
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.QUANTIDADE);
            this.sBuilder.append(", ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.DESCONTO);
            this.sBuilder.append(" FROM ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(((CampanhaProduto) lista.get(i)).getCodigo());

            c.moveToFirst();
            ((CampanhaProduto) lista.get(i)).setQuantidade(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.QUANTIDADE)));
            ((CampanhaProduto) lista.get(i)).setDesconto(
                c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.DESCONTO)));
        }

        for(int i = 0; i < lista.size(); i++)
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.ITEM);
            this.sBuilder.append(" FROM ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.CODIGO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(((CampanhaProduto) lista.get(i)).getCodigo());

            c.moveToFirst();
            for(int d = 0; d < c.getCount(); d++)
            {
                ArrayList<Integer> item = new ArrayList<>();
                item.add(new Integer(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto.ITEM))));

                ((CampanhaProduto) lista.get(i)).setItens(item);

                c.moveToNext();
            }
        }

        return lista;
    }
}
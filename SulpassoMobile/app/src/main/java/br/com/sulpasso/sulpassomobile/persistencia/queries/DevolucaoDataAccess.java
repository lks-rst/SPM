package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.Devolucao;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 09/11/2016 - 10:28 as part of the project SulpassoMobile.
 */
public class DevolucaoDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public DevolucaoDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public ArrayList getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList getByData(int g) throws GenercicException
    {
        return this.searchByData(g);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Devolucao devolucao) throws GenercicException { return this.inserirDevolucao(devolucao); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private Devolucao dataConverter(String devolucao)
    {
        Devolucao d = new Devolucao();
        Cliente c = new Cliente();
        c.setCodigoCliente(Integer.parseInt(devolucao.substring(2, 9).trim()));

        d.setCliente(c);
        d.setDocumento(devolucao.substring(9, 18).trim());
        d.setDataDevolucao(devolucao.substring(38, 48).trim());
        d.setMotivo(devolucao.substring(48, 78).trim());

        ItensVendidos i = new ItensVendidos();
        i.setItem(Integer.parseInt(devolucao.substring(18, 25).trim()));
        i.setQuantidade(Float.parseFloat(devolucao.substring(25, 31)) / 100);
        i.setValorLiquido(Float.parseFloat(devolucao.substring(31, 38)) / 100);

        ArrayList<ItensVendidos> itens = new ArrayList<>();
        itens.add(i);

        d.setItensDevolvidos(itens);

        return d;
    }

    @NonNull
    private Boolean inserirDevolucao(Devolucao devolucao) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DOCUMENTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.PRODUTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.VALOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DATA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.MOTIVO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(devolucao.getCliente().getCodigoCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(devolucao.getDocumento());
        this.sBuilder.append("', '");
        this.sBuilder.append(devolucao.getItensDevolvidos().get(0).getItem());
        this.sBuilder.append("', '");
        this.sBuilder.append(devolucao.getItensDevolvidos().get(0).getQuantidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(devolucao.getItensDevolvidos().get(0).getValorLiquido());
        this.sBuilder.append("', '");
        this.sBuilder.append(devolucao.getDataDevolucao());
        this.sBuilder.append("', '");
        this.sBuilder.append(devolucao.getMotivo());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private ArrayList searchAll() throws GenercicException
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT DISTINCT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DOCUMENTO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Devolucao d = new Devolucao();
            d.setDocumento(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DOCUMENTO)));

            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DATA);
            this.sBuilder.append(", ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.MOTIVO);
            this.sBuilder.append(", ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.CLIENTE);
            this.sBuilder.append(", ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA);
            this.sBuilder.append(", ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO);
            this.sBuilder.append(" FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.TABELA);

            this.sBuilder.append(" JOIN ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
            this.sBuilder.append(" ON ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.CLIENTE);
            this.sBuilder.append(" = ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);

            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DOCUMENTO);
            this.sBuilder.append(" LIKE '");
            this.sBuilder.append(d.getDocumento());
            this.sBuilder.append("';");

            Cursor cursor = this.db.rawQuery(this.sBuilder.toString(), null);
            cursor.moveToFirst();

            d.setDataDevolucao(cursor.getString(cursor.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DATA)));
            d.setMotivo(cursor.getString(cursor.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.MOTIVO)));


            Cliente cliente = new Cliente();

            cliente.setCodigoCliente(cursor.getInt(cursor.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.CLIENTE)));
            cliente.setFantasia(cursor.getString(cursor.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA)));
            cliente.setRazao(cursor.getString(cursor.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));

            d.setCliente(cliente);

            d.setItensDevolvidos(this.searchByData(d.getDocumento()));

            cursor.close();
            SQLiteDatabase.releaseMemory();

            lista.add(d);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }

    private ArrayList searchByData(int g) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.PRODUTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.VALOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" ON ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.PRODUTO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            ItensVendidos item = new ItensVendidos();
            item.setItem(c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.PRODUTO)));
            item.setQuantidade(c.getFloat(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.QUANTIDADE)));
            item.setValorLiquido(c.getFloat(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.VALOR)));

            lista.add(item);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }

    private ArrayList searchByData(String g) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.PRODUTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.VALOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.COMPLEMENTO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" ON ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.PRODUTO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DOCUMENTO);
        this.sBuilder.append(" LIKE '");
        this.sBuilder.append(g);
        this.sBuilder.append("';");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            ItensVendidos item = new ItensVendidos();
            item.setItem(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.PRODUTO)));
            item.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.QUANTIDADE)));
            item.setValorLiquido(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.VALOR)));

            lista.add(item);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }

    private Boolean apagar(Devolucao d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.CLIENTE);
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
package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.PrePedidoDireta;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 09/11/2016 - 15:11 as part of the project SulpassoMobile.
 */
public class PrePedidoDiretaDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public PrePedidoDiretaDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public ArrayList getByData(int g) throws GenercicException
    {
        return this.searchByData(g);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(PrePedidoDireta prePedidoDireta) throws GenercicException { return this.inserir(prePedidoDireta); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private PrePedidoDireta dataConverter(String data)
    {
        PrePedidoDireta pre = new PrePedidoDireta();

        pre.setCliente(Integer.parseInt(data.substring(2, 9).trim()));
        pre.setTipo(data.substring(9, 11).trim());
        pre.setPedido(Integer.parseInt(data.substring(11, 17).trim()));
        pre.setNota(Integer.parseInt(data.substring(17, 26).trim()));
        pre.setProduto(Integer.parseInt(data.substring(26, 33).trim()));
        pre.setQuantidade_s(Integer.parseInt(data.substring(33, 38).trim()));
        pre.setQuantidade_e(Integer.parseInt(data.substring(38, 43).trim()));
        pre.setValor_s(Float.parseFloat(data.substring(43, 49).trim()) / 100);
        pre.setValor_e(Float.parseFloat(data.substring(49, 55).trim()) / 100);
        pre.setData_nota(data.substring(55, 65).trim());

        return pre;
    }

    private Boolean inserir(PrePedidoDireta pre) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.TIPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.PEDIDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.NOTA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.PRODUTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.QUANTIDADE2);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.VALOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.VALOR2);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.DATA);

        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(pre.getCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getTipo());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getPedido());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getNota());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getProduto());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getQuantidade_s());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getQuantidade_e());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getValor_s());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getValor_e());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getData_nota());
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
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {

            PrePedidoDireta pre = new PrePedidoDireta();

            pre.setCliente(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.CLIENTE)));
            pre.setTipo(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.TIPO)));
            pre.setPedido(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.PEDIDO)));
            pre.setNota(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.NOTA)));
            pre.setProduto(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.PRODUTO)));
            pre.setQuantidade_s(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.QUANTIDADE)));
            pre.setQuantidade_e(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.QUANTIDADE2)));
            pre.setValor_s(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.VALOR)));
            pre.setValor_e(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.VALOR2)));
            pre.setData_nota(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.DATA)));

            lista.add(pre);
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
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            PrePedidoDireta pre = new PrePedidoDireta();

            pre.setCliente(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.CLIENTE)));
            pre.setTipo(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.TIPO)));
            pre.setPedido(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.PEDIDO)));
            pre.setNota(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.NOTA)));
            pre.setProduto(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.PRODUTO)));
            pre.setQuantidade_s(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.QUANTIDADE)));
            pre.setQuantidade_e(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.QUANTIDADE2)));
            pre.setValor_s(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.VALOR)));
            pre.setValor_e(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.VALOR2)));
            pre.setData_nota(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.DATA)));

            lista.add(pre);
            c.moveToNext();
        }

        return lista;
    }
    private Boolean apagar(PrePedidoDireta d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta.CLIENTE);
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
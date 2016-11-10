package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 09/11/2016 - 11:54 as part of the project SulpassoMobile.
 */
public class PrePedidoDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public PrePedidoDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    /*
        TODO: Remover após testes de inserção.
     */
    public List getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList getByData(int g) throws GenercicException
    {
        return this.searchByData(g);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(PrePedido prePedido) throws GenercicException { return this.inserir(prePedido); }

    private PrePedido dataConverter(String data)
    {
        PrePedido pre = new PrePedido();
        Cliente c = new Cliente();
        c.setCodigoCliente(Integer.parseInt(data.substring(2, 9).trim()));

        ItensVendidos i = new ItensVendidos();
        i.setItem(Integer.parseInt(data.substring(9, 16).trim()));
        i.setQuantidade(Float.parseFloat(data.substring(16, 20).trim()) / 100);
        i.setValorLiquido(Float.parseFloat(data.substring(30, 36).trim()) / 100);

        ArrayList<ItensVendidos> itens = new ArrayList<>();
        itens.add(i);

        pre.setData(data.substring(20, 30).trim());
        pre.setCliente(c);
        pre.setItensDevolvidos(itens);

        return pre;
    }

    private Boolean inserir(PrePedido pre) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.PRODUTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.DATA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.VALOR);

        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(pre.getCliente().getCodigoCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getItensDevolvidos().get(0).getItem());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getItensDevolvidos().get(0).getQuantidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getData());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getItensDevolvidos().get(0).getValorLiquido());
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            PrePedido pre = new PrePedido();
            Cliente cli = new Cliente();
            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE)));

            ItensVendidos item = new ItensVendidos();
            item.setItem(c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.PRODUTO)));
            item.setQuantidade(c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.QUANTIDADE)));
            item.setValorLiquido(c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.VALOR)));

            ArrayList<ItensVendidos> itens = new ArrayList<>();
            itens.add(item);

            pre.setData(c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.DATA)));
            pre.setCliente(cli);
            pre.setItensDevolvidos(itens);

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
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            PrePedido pre = new PrePedido();
            Cliente cli = new Cliente();
            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE)));

            ItensVendidos item = new ItensVendidos();
            item.setItem(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.PRODUTO)));
            item.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.QUANTIDADE)));
            item.setValorLiquido(c.getFloat(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.VALOR)));

            ArrayList<ItensVendidos> itens = new ArrayList<>();
            itens.add(item);

            pre.setData(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.DATA)));
            pre.setCliente(cli);
            pre.setItensDevolvidos(itens);

            lista.add(pre);
            c.moveToNext();
        }

        return lista;
    }
}
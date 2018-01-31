package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.modelo.PrePedidoItem;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

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

    public ArrayList getAllClients() throws GenercicException { return this.searchAllClients(); }

    public ArrayList getByData(int g) throws GenercicException
    {
        return this.searchByData(g);
    }

    public boolean getClienteByCod(int codigo) throws GenercicException
    {
        return this.clientByCod(codigo);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(PrePedido prePedido) throws GenercicException { return this.inserir(prePedido); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private PrePedido dataConverter(String data)
    {
        PrePedido pre = new PrePedido();
        Cliente c = new Cliente();
        c.setCodigoCliente(Integer.parseInt(data.substring(2, 9).trim()));

        PrePedidoItem i = new PrePedidoItem();
        i.setItem(Integer.parseInt(data.substring(9, 16).trim()));
        i.setQuantidade(Float.parseFloat(data.substring(16, 20).trim()) / 100);
        i.setValorDigitado(Float.parseFloat(data.substring(30, 36).trim()) / 100);

        ArrayList<PrePedidoItem> itens = new ArrayList<>();
        itens.add(i);

        pre.setData(data.substring(20, 30).trim());
        pre.setCliente(c);
        pre.setItensPrePedido(itens);

        return pre;
    }

    private Boolean inserir(PrePedido pre) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.PRODUTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.DATA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.VALOR);

        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(pre.getCliente().getCodigoCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getItensVendidos().get(0).getItem());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getItensVendidos().get(0).getQuantidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getData());
        this.sBuilder.append("', '");
        this.sBuilder.append(pre.getItensVendidos().get(0).getValorDigitado());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private ArrayList searchAllClients() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT DISTINCT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.TABELA);
        this.sBuilder.append(" JOIN ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
        this.sBuilder.append(" ON ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);
        this.sBuilder.append(" ORDER BY ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Cliente cli = new Cliente();
            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE)));
            cli.setFantasia(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA)));
            cli.setRazao(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));

            lista.add(cli);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            PrePedido pre = new PrePedido();
            Cliente cli = new Cliente();
            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE)));

            PrePedidoItem item = new PrePedidoItem();
            item.setItem(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.PRODUTO)));
            item.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.QUANTIDADE)));
            item.setValorDigitado(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.VALOR)));

            ArrayList<PrePedidoItem> itens = new ArrayList<>();
            itens.add(item);

            pre.setData(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.DATA)));
            pre.setCliente(cli);
            pre.setItensPrePedido(itens);

            lista.add(pre);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchByData(int g) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT pp.*, ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);

        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.TABELA);
        this.sBuilder.append(" AS pp");

        this.sBuilder.append(" JOIN (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(") ON (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.PRODUTO);
        this.sBuilder.append(")");

        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        PrePedido opre = null;
        Cliente ocli = null;
        ArrayList<PrePedidoItem> itens = new ArrayList<>();
        PrePedido pre = new PrePedido();
        Cliente cli = new Cliente();

        for(int i = 0; i < c.getCount(); i++)
        {
            pre = new PrePedido();
            cli = new Cliente();

            cli.setCodigoCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE)));
            cli.setRazao(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));

            PrePedidoItem item = new PrePedidoItem();
            item.setItem(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.PRODUTO)));
            item.setQuantidade(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.QUANTIDADE)));
            item.setValorDigitado(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.VALOR)));
            item.setData(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.DATA)));

            item.setDescricao(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO)));

            if(opre == null)
            {
                itens = new ArrayList<>();
                opre = pre;
                ocli = cli;
                itens.add(item);
            }
            else
            {
                if (ocli.getCodigoCliente().equals(cli.getCodigoCliente()))
                {
                    itens.add(item);
                }
                else
                {
                    opre.setCliente(ocli);
                    opre.setItensPrePedido(itens);

                    lista.add(opre);

                    opre = pre;
                    ocli = cli;
                }
            }
            c.moveToNext();
        }

        pre.setCliente(cli);
        pre.setItensPrePedido(itens);
        lista.add(pre);

        return lista;
    }

    private Boolean apagar(PrePedido d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);
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

    private boolean clientByCod(int cod) throws ReadExeption
    {
        boolean retorno = false;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT count(*) ");
        /*
        this.sBuilder.append("SELECT pp.*, ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
        */


        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.TABELA);
        this.sBuilder.append(" AS pp");

        /*
        this.sBuilder.append(" JOIN (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(") ON (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.PRODUTO);
        this.sBuilder.append(")");
        */

        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(cod);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        try
        {
            int t = 0;

            t = c.getInt(0);

            retorno = t > 0 ? true : false;
        }
        catch (Exception d) { retorno = false; }

        return retorno;
    }
}
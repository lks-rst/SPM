package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Devolucao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

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

    /*
        TODO: Remover após testes de inserção. Não há (hoje) no sistema nada que necessite uma busca por todos os devolucaos
     */
    public List getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList getByData(int g) throws GenercicException
    {
        return this.searchByData(g);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Devolucao devolucao) throws GenercicException { return this.inserirDevolucao(devolucao); }

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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DOCUMENTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.PRODUTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.VALOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DATA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.MOTIVO);
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

    /*
        TODO: É preciso alterar esses dois metodos para buscarem de maneira separada cada um dos clientes retornando os itens como a lista
     */
    private ArrayList searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Devolucao d = new Devolucao();
            Cliente cli = new Cliente();
            cli.setCodigoCliente(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.CLIENTE)));
            d.setCliente(cli);

            d.setDocumento(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DOCUMENTO)));
            d.setDataDevolucao(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DATA)));
            d.setMotivo(
                c.getString(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.MOTIVO)));

            ItensVendidos item = new ItensVendidos();
            item.setItem(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.PRODUTO)));
            item.setQuantidade(
                c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.QUANTIDADE)));
            item.setValorLiquido(
                c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.VALOR)));

            ArrayList<ItensVendidos> itens = new ArrayList<>();
            itens.add(item);

            d.setItensDevolvidos(itens);

            lista.add(d);
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Devolucao d = new Devolucao();
            Cliente cli = new Cliente();
            cli.setCodigoCliente(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.CLIENTE)));
            d.setCliente(cli);

            d.setDocumento(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DOCUMENTO)));
            d.setDataDevolucao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.DATA)));
            d.setMotivo(
                    c.getString(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.MOTIVO)));

            ItensVendidos item = new ItensVendidos();
            item.setItem(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.PRODUTO)));
            item.setQuantidade(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.QUANTIDADE)));
            item.setValorLiquido(
                    c.getFloat(c.getColumnIndex(
                            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao.VALOR)));

            ArrayList<ItensVendidos> itens = new ArrayList<>();
            itens.add(item);

            d.setItensDevolvidos(itens);

            lista.add(d);
            c.moveToNext();
        }

        return lista;
    }
}
package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 15/08/2016.
 */
/*
    TODO: Verificar se remove a parte de busca e insercao dos itens vendidos dessa classe para uma especializada
 */
public class VendaDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private String searchData;

    public VendaDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public void setSearchData(String searchData) { this.searchData = searchData; }

    public int buscarCodigo() throws ReadExeption { return this.getCodigo(); }

    public List getAll() throws GenercicException { return this.searchAll(); }

    public List getByData() throws GenercicException
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = ");

        if(searchType == 1) { this.sBuilder.append(0); }
        else { this.sBuilder.append(1); }

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Venda venda = new Venda();

            venda.setCodigo(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO)));
            venda.setData(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA)));
            venda.setHora(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.HORA)));
            venda.setValor(
                c.getDouble(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TOTAL)));
            venda.setDesconto(
                c.getDouble(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DESCONTO)));
            venda.setTabela(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TAB)));
            venda.setBanco(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.BANCO)));
            venda.setTipo(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TIPO)));
            venda.setNatureza(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.NATUREZA)));
            venda.setObservacaDesconto(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.JUSTIFICATIVA)));
            venda.setObservacao(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.OBSERVACAO)));
            venda.setObservacaoNota(
                c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.OBSERVACAONOTA)));

            int codPrazo = c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.PRAZO));

            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT ");
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO);
            this.sBuilder.append(", ");
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA);
            this.sBuilder.append(" FROM ");
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CLIENTE)));

            Cursor cursor = this.db.rawQuery(this.sBuilder.toString(), null);
            cursor.moveToFirst();

            Cliente cliente = new Cliente();
            cliente.setRazao(cursor.getString(cursor.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));
            cliente.setFantasia(cursor.getString(cursor.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA)));
            cliente.setCodigoCliente(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CLIENTE)));

            venda.setCliente(cliente);

            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT ");
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.DESDOBRAMENTO);
            this.sBuilder.append(" FROM ");
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.CODIGO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(codPrazo);

            Cursor cursorPrazos = this.db.rawQuery(this.sBuilder.toString(), null);
            cursorPrazos.moveToFirst();

            Prazo p = new Prazo();
            p.setCodigo(codPrazo);

            if(cursorPrazos.getCount() > 0)
            {
                p.setDesdobramento(cursorPrazos.getString(cursorPrazos.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo.DESDOBRAMENTO)));
            }
            else { p.setDesdobramento("000-000-000-000"); }
            venda.setPrazo(p);

            lista.add(venda);
            c.moveToNext();
        }

        for(Object v : lista)
        {
            ArrayList<ItensVendidos> itens = new ArrayList<>();
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT * FROM ");
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(((Venda) v).getCodigo());

            c = this.db.rawQuery(this.sBuilder.toString(), null);

            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++)
            {
                ItensVendidos item = new ItensVendidos();

                item.setItem(
                    c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM)));
                item.setValorTabela(
                    c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORTABELA)));
                item.setValorLiquido(
                    c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORLIQUIDO)));
                item.setQuantidade(
                    c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.QUANTIDADE)));
                item.setTotal(
                    c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TOTAL)));

                itens.add(item);
                c.moveToNext();
            }
            ((Venda) v).setItens(itens);
            lista.set(lista.indexOf(v), v);
        }

        return lista;
    }

    public Boolean insert(String data) throws GenercicException { return false; }

    public Boolean insert(Venda venda) throws GenercicException { return this.inserirVenda(venda); }

    public void atualizarSaldo(float saldo) throws GenercicException { this.saldo(saldo); }

    private Venda dataConverter(String venda)
    {
        return new Venda();
    }

    private int getCodigo() throws ReadExeption
    {
        int retorno = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT MAX(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(") FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        try
        {
            c.moveToFirst();
            retorno = c.getInt(0);
        }
        catch (Exception e) { retorno = 0; }

        return retorno + 1;
    }

    private Boolean inserirVenda(Venda venda) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TAB);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.OBSERVACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.OBSERVACAONOTA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.JUSTIFICATIVA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DESCONTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TOTAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.HORA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.PRAZO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.BANCO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TIPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.NATUREZA);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(venda.getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getCodigoCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getTabela());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getObservacao());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getObservacaoNota());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getObservacaDesconto());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getDesconto());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getValor());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getHora());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getData());
        this.sBuilder.append("', '");
        this.sBuilder.append(0);
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getPrazo().getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getBanco());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getTipo());
        this.sBuilder.append("', '");
        this.sBuilder.append(venda.getNatureza());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            try
            {
                this.insertItens(venda.getItens(), venda.getCodigo());
                return true;
            }
            catch (GenercicException e)
            {
                this.removeItem(venda.getCodigo());
                throw new InsertionExeption(e.getMessage());
            }
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private List getByData(String data, int type) throws ReadExeption
    {
        return new ArrayList();
    }

    private List searchAll() throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Venda venda = new Venda();

            venda.setCodigo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO)));
            venda.setData(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA)));
            venda.setHora(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.HORA)));
            venda.setValor(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TOTAL)));
            venda.setDesconto(
                    c.getDouble(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DESCONTO)));

            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO);
            this.sBuilder.append(", ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA);
            this.sBuilder.append(" FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CLIENTE)));

            Cursor cursor = this.db.rawQuery(this.sBuilder.toString(), null);

            if(cursor.getCount() > 0)
            {
                cursor.moveToFirst();

                Cliente cliente = new Cliente();
                cliente.setRazao(cursor.getString(cursor.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));
                cliente.setFantasia(cursor.getString(cursor.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA)));
                cliente.setCodigoCliente(c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CLIENTE)));

                venda.setCliente(cliente);

                lista.add(venda);
            }

            c.moveToNext();
        }

        for(Object v : lista)
        {
            ArrayList<ItensVendidos> itens = new ArrayList<>();
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT * FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(((Venda) v).getCodigo());

            c = this.db.rawQuery(this.sBuilder.toString(), null);

            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++)
            {
                ItensVendidos item = new ItensVendidos();

                item.setItem(
                        c.getInt(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM)));
                item.setValorTabela(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORTABELA)));
                item.setValorLiquido(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORLIQUIDO)));
                item.setQuantidade(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.QUANTIDADE)));
                item.setTotal(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TOTAL)));

                itens.add(item);
                c.moveToNext();
            }
            ((Venda) v).setItens(itens);
            lista.set(lista.indexOf(v), v);
        }

        return lista;
    }

    private boolean insertItens(ArrayList<ItensVendidos> itens, int pedido) throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORTABELA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORDIGITADO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.DESCONTOCG);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.DESCONTOCP);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.DESCONTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORLIQUIDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.FLEX);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TOTAL);
        this.sBuilder.append(") VALUES ");

        boolean first = true;

        for(ItensVendidos i : itens)
        {
            if(!first)
            {
                this.sBuilder.append(",");
            }
            this.sBuilder.append("('");
            this.sBuilder.append(i.getItem());
            this.sBuilder.append("', '");
            this.sBuilder.append(pedido);
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getValorTabela());
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getValorDigitado());
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getQuantidade());
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getDescontoCG());
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getDescontoCP());
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getDesconto());
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getValorLiquido());
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getFlex());
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getTotal());
            this.sBuilder.append("')");
            first = false;
        }

        this.sBuilder.append(";");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private void removeItem(int codigo) throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigo);
        this.sBuilder.append(";");

        try
        {
            this.db.execSQL(this.sBuilder.toString());

            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("DELETE FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(codigo);
            this.sBuilder.append(";");

            try
            {
                this.db.execSQL(this.sBuilder.toString());
            }
            catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private void saldo(float saldo) throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SALDO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(saldo);
        this.sBuilder.append(";");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    public int totalVendasAbertas()
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT COUNT()");
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(0);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { return c.getInt(0); }
        catch (Exception exception) { return 1; }
    }

    public void atualizarVendas() throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(1);
        this.sBuilder.append("' WHERE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(0);
        this.sBuilder.append("';");

        try {
            db.execSQL(this.sBuilder.toString());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String recuperarDescricao(int codigo)
    {
        String retorno = "";
        String descricao = "";
        String referencia = "";

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.REFERENCIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.DESCRICAO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(codigo);
        this.sBuilder.append("';");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        if(c.getCount() > 0)
        {
            descricao = c.getString(c.getColumnIndex(Item.DESCRICAO));
            referencia = c.getString(c.getColumnIndex(Item.REFERENCIA));
        }

        ManipulacaoStrings ms = new ManipulacaoStrings();
        descricao = ms.comDireita(descricao, " ", 20);
        referencia = ms.comDireita(referencia, " ", 7);

        retorno = referencia + " - " + descricao;

        return retorno;
    }
}
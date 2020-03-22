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
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipularArquivos;

/**
 * Created by Lucas on 15/08/2016.
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
        String[] datas = this.searchData.split("%%");
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" WHERE (");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = ");

        if(searchType == 1)
        {
            this.sBuilder.append(0);

            this.sBuilder.append(" OR ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
            this.sBuilder.append(" = 2 ");
        }
        else { this.sBuilder.append(1); }

        this.sBuilder.append(") AND date(");


        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA);
        this.sBuilder.append(") BETWEEN '");
        this.sBuilder.append(datas[0]);
        this.sBuilder.append("' AND '");
        this.sBuilder.append(datas[1]);
        this.sBuilder.append("' AND ");

        /*
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA);
        this.sBuilder.append(" = '");
        this.sBuilder.append(this.searchData);
        this.sBuilder.append("' AND ");

        */

        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.EXCLUIDO);
        this.sBuilder.append(" = 0;");

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
            venda.setEnviado(
                c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO)));

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
                cursor.close();
                SQLiteDatabase.releaseMemory();

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
                cursorPrazos.close();
                SQLiteDatabase.releaseMemory();

                lista.add(venda);
            }

            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

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
            c.close();
            SQLiteDatabase.releaseMemory();
        }

        return lista;
    }

    public List getByDataEnviar() throws GenercicException
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" WHERE (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = ");

        if(searchType == 1)
        {
            this.sBuilder.append(" 0");

            this.sBuilder.append(" OR ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
            this.sBuilder.append(" = 2 ");
        }
        else { this.sBuilder.append(1); }

        this.sBuilder.append(") AND (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.EXCLUIDO);
        this.sBuilder.append(" = 0);");

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
            venda.setEnviado(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO)));

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
                cursor.close();
                SQLiteDatabase.releaseMemory();

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
                cursorPrazos.close();
                SQLiteDatabase.releaseMemory();

                lista.add(venda);
            }

            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

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
                item.setValorDigitado(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORDIGITADO)));

                item.setDescontoCG(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.DESCONTOCG)));
                item.setDescontoCP(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.DESCONTOCP)));

                item.setDigitadoSenha(
                        (c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.SENHA))
                            == 1) ? true : false);
                item.setDescontoCampanha(
                        (c.getInt(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.CAMPANHAS))
                                == 1) ? true : false);
                item.setValorMinimo(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.MINIMO)));
                item.setQuantidadeEspecifica(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.QUANTIDADE_ESPECIFICA)));

                itens.add(item);
                c.moveToNext();
            }
            ((Venda) v).setItens(itens);
            lista.set(lista.indexOf(v), v);
            c.close();
            SQLiteDatabase.releaseMemory();
        }

        return lista;
    }

    public List getByInterval(int inicio, int fim) throws GenercicException
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(" >= ");
        this.sBuilder.append(inicio);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(" <= ");
        this.sBuilder.append(fim);
        this.sBuilder.append(";");

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

            }
            else venda.setCliente(new Cliente());
            cursor.close();
            SQLiteDatabase.releaseMemory();

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
            cursorPrazos.close();
            SQLiteDatabase.releaseMemory();

            lista.add(venda);
            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

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
                item.setValorDigitado(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORDIGITADO)));

                item.setDigitadoSenha(
                        (c.getInt(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.SENHA))
                                == 1) ? true : false);
                item.setDescontoCampanha(
                        (c.getInt(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.CAMPANHAS))
                                == 1) ? true : false);
                item.setValorMinimo(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.MINIMO)));
                item.setQuantidadeEspecifica(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.QUANTIDADE_ESPECIFICA)));

                itens.add(item);
                c.moveToNext();
            }
            ((Venda) v).setItens(itens);
            lista.set(lista.indexOf(v), v);
            c.close();
            SQLiteDatabase.releaseMemory();
        }

        return lista;
    }

    public Boolean insert(String data) throws GenercicException { return false; }

    public Boolean insert(Venda venda) throws GenercicException { return this.inserirVenda(venda); }

    public void atualizarSaldo(float saldo) throws GenercicException { this.saldo(saldo); }

    public void excluirPedido(int cod) throws GenercicException { this.removeItem(cod); }

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
        /*
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.EXCLUIDO);
        this.sBuilder.append(" = 0;");
        */

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        try
        {
            c.moveToFirst();
            retorno = c.getInt(0);
        }
        catch (Exception e) { retorno = 0; }
        c.close();
        SQLiteDatabase.releaseMemory();

        return retorno + 1;
    }

    private Boolean inserirVenda(Venda venda) throws InsertionExeption
    {
        /* https://stackoverflow.com/questions/418898/sqlite-upsert-not-insert-or-replace */
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert OR REPLACE into ");
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
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.EXCLUIDO);
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

        ManipulacaoStrings ms = new ManipulacaoStrings();

        this.sBuilder.append(ms.dataBanco(venda.getData()));
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
        this.sBuilder.append("', '0');");

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
        String[] datas = this.searchData.split("%%");
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" WHERE date(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA);
        this.sBuilder.append(") between '");
        this.sBuilder.append(datas[0]);
        this.sBuilder.append("' AND '");
        this.sBuilder.append(datas[1]);
        this.sBuilder.append("' AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.EXCLUIDO);
        this.sBuilder.append(" = 0;");


        /*

        this.sBuilder.append(" = '");
        this.sBuilder.append(this.searchData);
        this.sBuilder.append("' AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.EXCLUIDO);
        this.sBuilder.append(" = 0;");
         */

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
            venda.setEnviado(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO)));
            venda.setObservacao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.OBSERVACAO)));

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
            cursor.close();
            SQLiteDatabase.releaseMemory();

            c.moveToNext();
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        for(Object v : lista)
        {
            ArrayList<ItensVendidos> itens = new ArrayList<>();
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT IV.*, ITEM.DescricaoProduto AS id, ITEM.ReferenciaProduto AS ir, ITEM.Complemento AS ic FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);

            this.sBuilder.append(" AS IV JOIN ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
            this.sBuilder.append(" AS ITEM ON ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM);
            this.sBuilder.append(" = ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);

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
                item.setValorDigitado(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORDIGITADO)));


                item.setDescricao(c.getString(c.getColumnIndex("id")));
                item.setReferencia(c.getString(c.getColumnIndex("ir")));
                item.setComplemento(c.getString(c.getColumnIndex("ic")));


                itens.add(item);
                c.moveToNext();
            }
            ((Venda) v).setItens(itens);
            lista.set(lista.indexOf(v), v);
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return lista;
    }

    private boolean insertItens(ArrayList<ItensVendidos> itens, int pedido) throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(pedido);

        try
        {
            this.db.execSQL(this.sBuilder.toString());
        }
        catch (final Exception e)
        {
            String s = e.getMessage();

            new Thread(new Runnable() {
                public void run() {
                    ManipularArquivos arquivos = new ManipularArquivos(ctx);
                    arquivos.addStringErro("Erro ao excluir itens de pedido alterado\n");
                    arquivos.addStringErro(e.getMessage());
                    arquivos.criarArquivoErros();
                }
            }).start();
        }

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

        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.SENHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.CAMPANHAS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.MINIMO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.QUANTIDADE_ESPECIFICA);

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

            this.sBuilder.append("', '");
            this.sBuilder.append(i.isDigitadoSenha() ? 1 : 0);
            this.sBuilder.append("', '");
            this.sBuilder.append(i.isDescontoCampanha() ? 1 : 0);
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getValorMinimo());
            this.sBuilder.append("', '");
            this.sBuilder.append(i.getQuantidadeEspecifica());

            this.sBuilder.append("')");
            first = false;
        }

        this.sBuilder.append(";");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e)
        {
            String s = e.getMessage();
            throw new InsertionExeption(e.getMessage());
        }
    }

    private void removeItem(int codigo) throws GenercicException
    {
        /*
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
        */
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.EXCLUIDO);
        this.sBuilder.append(" = 1 WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codigo);
        this.sBuilder.append(";");

        try { this.db.execSQL(this.sBuilder.toString()); }
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
        this.sBuilder.append(" WHERE (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = 0 OR ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = 2) AND (");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.EXCLUIDO);
        this.sBuilder.append(" = 0);");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { return c.getInt(0); }
        catch (Exception exception) { return 1; }
        finally
        {
            if(c != null)
            {
                c.close();
                SQLiteDatabase.releaseMemory();
            }
        }
    }

    public void atualizarVendas(int to) throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(to);
        this.sBuilder.append("' WHERE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = '");
        this.sBuilder.append("0' OR ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = '");
        this.sBuilder.append("2';");

        try {
            db.execSQL(this.sBuilder.toString());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void atualizarVendas(int inicio, int fim, int to) throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.ENVIO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(to);
        this.sBuilder.append("' WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(" >= '");
        this.sBuilder.append(inicio);
        this.sBuilder.append("' AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(" <= '");
        this.sBuilder.append(fim);
        this.sBuilder.append("';");

        try { db.execSQL(this.sBuilder.toString()); }
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
        c.close();
        SQLiteDatabase.releaseMemory();

        ManipulacaoStrings ms = new ManipulacaoStrings();
        descricao = ms.comDireita(descricao, " ", 20);
        referencia = ms.comDireita(referencia, " ", 7);

        retorno = referencia + " - " + descricao;

        return retorno;
    }

    public float totalPedidos(String sqlWhere)
    {
        float retorno = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT count(*) AS total");
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(sqlWhere);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        if(c.getCount() > 0) { retorno = c.getFloat(c.getColumnIndex("total")); }
        c.close();
        SQLiteDatabase.releaseMemory();

        return retorno;
    }

    public float valorPedidos(String sqlWhere)
    {
        float retorno = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT SUM(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TOTAL);
        this.sBuilder.append(") AS total");
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(sqlWhere);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        if(c.getCount() > 0) { retorno = c.getFloat(c.getColumnIndex("total")); }
        c.close();
        SQLiteDatabase.releaseMemory();

        return retorno;
    }

    public float mediaItens(String sqlWhere)
    {
        float retorno = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT count(*)");
        this.sBuilder.append(" AS total");
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(sqlWhere);
        this.sBuilder.append(" AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        if(c.getCount() > 0) { retorno = c.getFloat(c.getColumnIndex("total")); }
        c.close();
        SQLiteDatabase.releaseMemory();

        return retorno;
    }

    public float totalClientes(String sqlWhere)
    {
        float retorno = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT count(distinct ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CLIENTE);
        this.sBuilder.append(") AS total");
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);
        this.sBuilder.append(sqlWhere);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        if(c.getCount() > 0) { retorno = c.getFloat(c.getColumnIndex("total")); }
        c.close();
        SQLiteDatabase.releaseMemory();

        return retorno;
    }

    public Venda buscarVendaAlteracao(int codVenda)
    {
        Venda v = new Venda();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TABELA);;
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(codVenda);
        this.sBuilder.append(";");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        if(c.getCount() > 0)
        {
            c.moveToFirst();

            Prazo p = new Prazo();

            v.setCodigoCliente(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CLIENTE)));
            v.setCodigo(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.CODIGO)));
            v.setData(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DATA)));
            v.setHora(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.HORA)));
            v.setObservacao(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.OBSERVACAO)));
            v.setDesconto(c.getDouble(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DESCONTO)));
            v.setDesconto(c.getDouble(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.DESCONTO)));
            v.setObservacaDesconto(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.JUSTIFICATIVA)));
            v.setObservacaoNota(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.OBSERVACAONOTA)));
            v.setValor(c.getDouble(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TOTAL)));
            v.setTabela(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TAB)));
            v.setBanco(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.BANCO)));
            v.setTipo(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.TIPO)));
            p.setCodigo(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.PRAZO)));
            v.setPrazo(p);
            v.setNatureza(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda.NATUREZA)));


            c.close();
            SQLiteDatabase.releaseMemory();

            this.sBuilder.delete(0, this.sBuilder.length());
            ArrayList<ItensVendidos> itens = new ArrayList<>();
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT IV.*, ITEM.DescricaoProduto AS id, ITEM.ReferenciaProduto AS ir, ITEM.Complemento AS ic FROM ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);

            this.sBuilder.append(" AS IV JOIN ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.TABELA);
            this.sBuilder.append(" AS ITEM ON ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.ITEM);
            this.sBuilder.append(" = ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item.CODIGO);

            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(codVenda);

            c = this.db.rawQuery(this.sBuilder.toString(), null);

            c.moveToFirst();
            for(int i = 0; i < c.getCount(); i++)
            {
                ItensVendidos item = new ItensVendidos();

                item.setDescontoCG(0);
                item.setDescontoCP(0);

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
                item.setValorDigitado(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.VALORDIGITADO)));

                item.setDescontoCP(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.DESCONTOCP)));
                item.setDescontoCG(
                        c.getFloat(c.getColumnIndex(
                                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.DESCONTOCG)));

                item.setDescontoCampanha((item.getDescontoCG() > 0 || item.getDescontoCP() > 0));

                item.setDescricao(c.getString(c.getColumnIndex("id")));
                item.setReferencia(c.getString(c.getColumnIndex("ir")));
                item.setComplemento(c.getString(c.getColumnIndex("ic")));

                itens.add(item);
                c.moveToNext();
            }

            v.setItens(itens);
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return v;
    }

    public float buscarFlexItens(int codVenda)
    {
        float flex = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.FLEX);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.QUANTIDADE);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.PEDIDO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(codVenda);
        this.sBuilder.append("' AND ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.SENHA);
        this.sBuilder.append(" = '0';");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        if(c.getCount() > 0)
        {
            c.moveToFirst();

            for(int i = 0; i < c.getCount(); i++)
            {
                float flexItem = c.getFloat(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.FLEX));
                if(flexItem > 0)
                {
                    flex += (c.getFloat(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.FLEX)) *
                            c.getFloat(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos.QUANTIDADE)));
                }

                c.moveToNext();
            }
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return flex;
    }

    public float buscarSaldoAtual()
    {
        float flex = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SALDO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(";");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        if(c.getCount() > 0)
        {
            c.moveToFirst();

            for(int i = 0; i < c.getCount(); i++)
            {
                flex += c.getFloat(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SALDO));

                c.moveToNext();
            }
        }
        c.close();
        SQLiteDatabase.releaseMemory();

        return flex;
    }
}
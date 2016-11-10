package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ContasReceber;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 10/11/2016 - 08:46 as part of the project SulpassoMobile.
 */
public class ContasReceberDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public ContasReceberDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public List getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList getByData(int g) throws GenercicException
    {
        return this.searchByData(g);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(ContasReceber conta) throws GenercicException { return this.inserirGrupo(conta); }

    private ContasReceber dataConverter(String data)
    {
        ContasReceber conta = new ContasReceber();

        conta.setCliente(Integer.parseInt(data.substring(2, 9).trim()));
        conta.setDocumento(data.substring(9, 21).trim());
        conta.setEmissao(data.substring(21, 31).trim());
        conta.setVencimento(data.substring(31, 41).trim());
        conta.setValor(Float.parseFloat(data.substring(41, 49).trim()) / 100);
        conta.setTipo(data.substring(49, 51).trim());

        return conta;
    }

    private Boolean inserirGrupo(ContasReceber conta) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.CLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.DOC);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.EMISSAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.VENCIMENTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.VALOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.TIPO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(conta.getCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getDocumento());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmissao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVencimento());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getValor());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTipo());
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            ContasReceber conta = new ContasReceber();

            conta.setCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.CLIENTE)));
            conta.setDocumento(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.DOC)));
            conta.setEmissao(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.EMISSAO)));
            conta.setVencimento(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.VENCIMENTO)));
            conta.setValor(c.getFloat(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.VALOR)));
            conta.setTipo(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.TIPO)));

            lista.add(conta);
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
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.CLIENTE);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            ContasReceber conta = new ContasReceber();

            conta.setCliente(c.getInt(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.CLIENTE)));
            conta.setDocumento(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.DOC)));
            conta.setEmissao(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.EMISSAO)));
            conta.setVencimento(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.VENCIMENTO)));
            conta.setValor(c.getFloat(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.VALOR)));
            conta.setTipo(c.getString(c.getColumnIndex(
                    br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber.TIPO)));

            lista.add(conta);
            c.moveToNext();
        }

        return lista;
    }
}
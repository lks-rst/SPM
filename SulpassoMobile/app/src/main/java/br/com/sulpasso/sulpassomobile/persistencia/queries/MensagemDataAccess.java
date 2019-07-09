package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Mensagem;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 10/11/2016 - 14:49 as part of the project SulpassoMobile.
 */
public class MensagemDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public MensagemDataAccess(Context context)
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

    public Boolean insert(Mensagem mensagem) throws GenercicException { return this.inserirMensagem(mensagem); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    private Mensagem dataConverter(String data)
    {
        Mensagem m = new Mensagem();
        
        m.setUsuario(data.substring(2, 16));
        m.setEnvio(data.substring(16, 26));
        m.setValidade(data.substring(26, 36));
        m.setAssunto(data.substring(36, 66));
        m.setMensagem(data.substring(66));

        return m;
    }

    private Boolean inserirMensagem(Mensagem mensagem) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.MENSAGEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.VALIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.ENVIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.ASSUNTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.USUARIO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(mensagem.getMensagem());
        this.sBuilder.append("', '");
        this.sBuilder.append(mensagem.getValidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(mensagem.getEnvio());
        this.sBuilder.append("', '");
        this.sBuilder.append(mensagem.getAssunto());
        this.sBuilder.append("', '");
        this.sBuilder.append(mensagem.getUsuario());
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
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.TABELA);

        Cursor c = null;
        try
        {
            c = this.db.rawQuery(this.sBuilder.toString(), null);
            c.moveToFirst();

            for(int i = 0; i < c.getCount(); i++)
            {
                Mensagem m = new Mensagem();

                m.setAssunto(c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.ASSUNTO)));
                m.setUsuario(c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.USUARIO)));
                m.setEnvio(c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.ENVIO)));
                m.setValidade(c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.VALIDADE)));
                m.setMensagem(c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.MENSAGEM)));
                m.setCodigo(c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.CODIGO)));

                lista.add(m);
                c.moveToNext();
            }
        }
        catch (Exception e) { throw new ReadExeption("Possível falta de memória no aparelho."); }
        finally
        {
            if(c != null)
            {
                c.close();
                SQLiteDatabase.releaseMemory();
            }
        }

        return lista;
    }

    private ArrayList searchByData(int g) throws ReadExeption
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(g);

        Cursor c = null;
        try
        {
            c = this.db.rawQuery(this.sBuilder.toString(), null);
            c.moveToFirst();

            for(int i = 0; i < c.getCount(); i++)
            {
                Mensagem m = new Mensagem();

                m.setAssunto(c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.ASSUNTO)));
                m.setUsuario(c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.USUARIO)));
                m.setEnvio(c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.ENVIO)));
                m.setValidade(c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.VALIDADE)));
                m.setMensagem(c.getString(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.MENSAGEM)));
                m.setCodigo(c.getInt(c.getColumnIndex(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.CODIGO)));

                lista.add(m);
                c.moveToNext();
            }
        }
        catch (Exception e) { throw new ReadExeption("Possível falta de memória no aparelho."); }
        finally
        {
            if(c != null)
            {
                c.close();
                SQLiteDatabase.releaseMemory();
            }
        }

        return lista;
    }
    private Boolean apagar(Mensagem d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem.CODIGO);
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
}
package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Meta;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 09/11/2016 - 16:45 as part of the project SulpassoMobile.
 */
public class MetaDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public MetaDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Meta meta) throws GenercicException { return this.inserir(meta); }

    public Boolean delete() throws GenercicException { return this.apagar(null); }

    public ArrayList getAll() throws GenercicException { return this.searchAll(); }

    public int getDiasUteis()
    {
        int dias = 25;
        /*
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Meta meta = new Meta();

            meta.setFamilia(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.FAMILIA)));
            meta.setRealizado_c(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CLIENTE_R)));
            meta.setMeta_c(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CLIENTE_M)));
            meta.setRealizado_v(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.VOLUME_R)));
            meta.setMeta_v(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.VOLUME_M)));
            meta.setRealizado_co(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CONTRIBUICAO_R)));
            meta.setMeta_co(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CONTRIBUICAO_M)));
            meta.setRealizado_f(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.FATURAMENTO_R)));
            meta.setMeta_f(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.FATURAMENTO_M)));

            c.moveToNext();
        }

        if (c.getCount() > 0){
            c.moveToFirst();
            dias = c.getInt(0);
        }
        */
        return dias;
    }

    private Meta dataConverter(String data)
    {
        Meta meta = new Meta();

        meta.setFamilia(data.substring(2, 32).trim());
        meta.setRealizado_c(Integer.parseInt(data.substring(32, 35).trim()));
        meta.setMeta_c(Integer.parseInt(data.substring(35, 38).trim()));
        meta.setRealizado_v(Float.parseFloat(data.substring(38, 44).trim()) / 100);
        meta.setMeta_v(Float.parseFloat(data.substring(44, 50).trim()) / 100);
        meta.setRealizado_co(Float.parseFloat(data.substring(50, 56).trim()) / 100);
        meta.setMeta_co(Float.parseFloat(data.substring(56, 62).trim()) / 100);
        meta.setRealizado_f(Float.parseFloat(data.substring(62, 68).trim()) / 100);
        meta.setMeta_f(Float.parseFloat(data.substring(68, 74).trim()) / 100);

        return meta;
    }

    private Boolean inserir(Meta meta) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.FAMILIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CLIENTE_R);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CLIENTE_M);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.VOLUME_R);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.VOLUME_M);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CONTRIBUICAO_R);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CONTRIBUICAO_M);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.FATURAMENTO_R);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.FATURAMENTO_M);

        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(meta.getFamilia());
        this.sBuilder.append("', '");
        this.sBuilder.append(meta.getRealizado_c());
        this.sBuilder.append("', '");
        this.sBuilder.append(meta.getMeta_c());
        this.sBuilder.append("', '");
        this.sBuilder.append(meta.getRealizado_v());
        this.sBuilder.append("', '");
        this.sBuilder.append(meta.getMeta_v());
        this.sBuilder.append("', '");
        this.sBuilder.append(meta.getRealizado_co());
        this.sBuilder.append("', '");
        this.sBuilder.append(meta.getMeta_co());
        this.sBuilder.append("', '");
        this.sBuilder.append(meta.getRealizado_f());
        this.sBuilder.append("', '");
        this.sBuilder.append(meta.getMeta_f());
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
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Meta meta = new Meta();

            meta.setFamilia(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.FAMILIA)));
            meta.setRealizado_c(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CLIENTE_R)));
            meta.setMeta_c(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CLIENTE_M)));
            meta.setRealizado_v(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.VOLUME_R)));
            meta.setMeta_v(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.VOLUME_M)));
            meta.setRealizado_co(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CONTRIBUICAO_R)));
            meta.setMeta_co(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.CONTRIBUICAO_M)));
            meta.setRealizado_f(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.FATURAMENTO_R)));
            meta.setMeta_f(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.FATURAMENTO_M)));

            lista.add(meta);
            c.moveToNext();
        }

        return lista;
    }

    private Boolean apagar(Meta d)  throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.TABELA);

        if(d != null)
        {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta.FAMILIA);
            this.sBuilder.append(" LIKE '");
            d.getFamilia();
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
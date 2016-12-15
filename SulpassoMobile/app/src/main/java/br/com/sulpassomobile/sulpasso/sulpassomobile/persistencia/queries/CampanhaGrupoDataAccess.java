package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Grupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 13/10/2016.
 */
public class CampanhaGrupoDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public CampanhaGrupoDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public ArrayList getAll() throws GenercicException { return this.searchAll(); }

    public ArrayList getByData() throws GenercicException { return new ArrayList(); }

    public Boolean insert(String data) throws GenercicException
    {
        return this.insert(this.dataConverter(data));
    }

    public Boolean insert(CampanhaGrupo campanhaGrupo) throws GenercicException { return this.inserirCampanhaGrupo(campanhaGrupo); }

    private CampanhaGrupo dataConverter(String campanhaGrupo)
    {
        CampanhaGrupo cg = new CampanhaGrupo();
        Grupo g = new Grupo();

        g.setGrupo(Integer.parseInt(campanhaGrupo.substring(2, 5)));
        g.setSubGrupo(Integer.parseInt(campanhaGrupo.substring(5, 8)));
        g.setDivisao(Integer.parseInt(campanhaGrupo.substring(8, 11)));

        cg.setGrupo(g);
        cg.setQuantidade(Integer.parseInt(campanhaGrupo.substring(11, 17)));
        cg.setDesconto(Float.parseFloat(campanhaGrupo.substring(17).trim()) / 100);

        return cg;
    }

    private Boolean inserirCampanhaGrupo(CampanhaGrupo campanhaGrupo) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.GRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.SUB);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.DIV);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.QUANTIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.DESCONTO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(campanhaGrupo.getGrupo().getGrupo());
        this.sBuilder.append("', '");
        this.sBuilder.append(campanhaGrupo.getGrupo().getSubGrupo());
        this.sBuilder.append("', '");
        this.sBuilder.append(campanhaGrupo.getGrupo().getDivisao());
        this.sBuilder.append("', '");
        this.sBuilder.append(campanhaGrupo.getQuantidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(campanhaGrupo.getDesconto());
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
            br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for(int i = 0; i < c.getCount(); i++)
        {
            Grupo g = new Grupo();
            CampanhaGrupo campanhaGrupo = new CampanhaGrupo();

            g.setGrupo(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.GRUPO)));
            g.setSubGrupo(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.SUB)));
            g.setDivisao(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.DIV)));
            campanhaGrupo.setGrupo(g);
            campanhaGrupo.setDesconto(
                c.getFloat(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.DESCONTO)));
            campanhaGrupo.setQuantidade(
                c.getInt(c.getColumnIndex(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo.QUANTIDADE)));

            lista.add(campanhaGrupo);
            c.moveToNext();
        }

        for(int i = 0; i < lista.size(); i++)
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("SELECT ");
            this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DESC);
            this.sBuilder.append(" FROM ");
            this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.TABELA);
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.GRUPO);
            this.sBuilder.append(" = ");
            this.sBuilder.append(((CampanhaGrupo)lista.get(i)).getGrupo().getGrupo());
            this.sBuilder.append(" AND ");
            this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.SUB);
            this.sBuilder.append(" = ");
            this.sBuilder.append(((CampanhaGrupo)lista.get(i)).getGrupo().getSubGrupo());
            this.sBuilder.append(" AND ");
            this.sBuilder.append(
                br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Grupo.DIV);
            this.sBuilder.append(" = ");
            this.sBuilder.append(((CampanhaGrupo)lista.get(i)).getGrupo().getDivisao());

            c = this.db.rawQuery(this.sBuilder.toString(), null);

            c.moveToFirst();

            ((CampanhaGrupo)lista.get(i)).getGrupo().setDescricao(c.getString(0));
        }

        return lista;
    }
}
package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Cliente;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Estoque;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Item;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Natureza;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Prazo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Preco;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Promocao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas.Venda;

/**
 * Created by Lucas on 02/08/2016.
 */
public class SimplySaleDataBase extends SQLiteOpenHelper
{
    private List<String> scriptSQLCreate;
    private static final String DB_NAME = "simplySale.db";
    private static final int DB_VERSION = 1;

    public SimplySaleDataBase(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.scriptSQLCreate = new ArrayList<String>();
        this.scriptSQLCreate.add(Cliente.CriarTabela());
        this.scriptSQLCreate.add(Estoque.CriarTabela());
        this.scriptSQLCreate.add(Item.CriarTabela());
        this.scriptSQLCreate.add(Natureza.CriarTabela());
        this.scriptSQLCreate.add(Prazo.CriarTabela());
        this.scriptSQLCreate.add(Preco.CriarTabela());
        this.scriptSQLCreate.add(ItensVendidos.CriarTabela());
        this.scriptSQLCreate.add(Venda.CriarTabela());
        this.scriptSQLCreate.add(Promocao.CriarTabela());
    }

    /**
     * Create the data base and this tables.
     * This method is called from the Android system
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        for (String script : scriptSQLCreate)
        {
            db.execSQL(script);
        }
    }

    /**
     * Whe the db version is altered this method is called.
     * This method is called from the Android system
     * @param db
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }
}
package br.com.sulpasso.sulpassomobile.persistencia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Agenda;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Atividade;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Banco;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaGrupo;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.CampanhaProduto;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cidade;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.ContasReceber;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Corte;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.CurvaAbc;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Devolucao;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Estoque;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Foco;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Gravosos;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Grupo;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoCliente;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.GrupoBloqueadoNatureza;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Item;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.ItensVendidos;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Kit;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mensagem;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Meta;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Mix;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Motivos;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Prazo;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedido;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrePedidoDireta;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Preco;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.PrecosClientes;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Promocao;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Status;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Tipologia;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.TiposVenda;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Usuario;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Visita;

/**
 * Created by Lucas on 02/08/2016.
 */
public class SimplySaleDataBase extends SQLiteOpenHelper
{
    private List<String> scriptSQLCreate;
    private static final String DB_NAME = "simplySale.db";
    private static final int DB_VERSION = 25;

    public SimplySaleDataBase(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
        this.scriptSQLCreate = new ArrayList<String>();

        this.scriptSQLCreate.add(Agenda.CriarTabela());
        this.scriptSQLCreate.add(Atividade.CriarTabela());
        this.scriptSQLCreate.add(Banco.CriarTabela());
        this.scriptSQLCreate.add(CampanhaGrupo.CriarTabela());
        this.scriptSQLCreate.add(CampanhaProduto.CriarTabela());
        this.scriptSQLCreate.add(Cidade.CriarTabela());
        this.scriptSQLCreate.add(Cliente.CriarTabela());
        this.scriptSQLCreate.add(ContasReceber.CriarTabela());
        this.scriptSQLCreate.add(Corte.CriarTabela());
        this.scriptSQLCreate.add(CurvaAbc.CriarTabela());
        this.scriptSQLCreate.add(Devolucao.CriarTabela());
        this.scriptSQLCreate.add(Estoque.CriarTabela());
        this.scriptSQLCreate.add(Gravosos.CriarTabela());
        this.scriptSQLCreate.add(Grupo.CriarTabela());
        this.scriptSQLCreate.add(GrupoBloqueadoCliente.CriarTabela());
        this.scriptSQLCreate.add(GrupoBloqueadoNatureza.CriarTabela());
        this.scriptSQLCreate.add(Item.CriarTabela());
        this.scriptSQLCreate.add(ItensVendidos.CriarTabela());
        this.scriptSQLCreate.add(Kit.CriarTabela());
        this.scriptSQLCreate.add(Mensagem.CriarTabela());
        this.scriptSQLCreate.add(Meta.CriarTabela());
        this.scriptSQLCreate.add(Mix.CriarTabela());
        this.scriptSQLCreate.add(Motivos.CriarTabela());
        this.scriptSQLCreate.add(Natureza.CriarTabela());
        this.scriptSQLCreate.add(Prazo.CriarTabela());
        this.scriptSQLCreate.add(Preco.CriarTabela());
        this.scriptSQLCreate.add(PrecosClientes.CriarTabela());
        this.scriptSQLCreate.add(PrePedido.CriarTabela());
        this.scriptSQLCreate.add(PrePedidoDireta.CriarTabela());
        this.scriptSQLCreate.add(Promocao.CriarTabela());
        this.scriptSQLCreate.add(Status.CriarTabela());
        this.scriptSQLCreate.add(Tipologia.CriarTabela());
        this.scriptSQLCreate.add(TiposVenda.CriarTabela());
        this.scriptSQLCreate.add(Usuario.CriarTabela());
        this.scriptSQLCreate.add(Venda.CriarTabela());
        this.scriptSQLCreate.add(Configurador.CriarTabela());
        this.scriptSQLCreate.add(ClienteNovo.CriarTabela());
        this.scriptSQLCreate.add(Visita.CriarTabela());
        this.scriptSQLCreate.add(Foco.CriarTabela());
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
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        if(oldVersion <= 10)
            db.execSQL(Visita.CriarTabela());

        if(oldVersion <= 11)
            db.execSQL(Item.AlterarTabela());

        if(oldVersion <= 12)
            db.execSQL(Venda.AlterarTabela());

        if(oldVersion <= 13)
            db.execSQL(Configurador.AlterarTabela());

        if(oldVersion <= 14)
            db.execSQL(Foco.CriarTabela());

        if(oldVersion <= 15)
            db.execSQL(Item.AlterarTabela2());

        if(oldVersion <= 16)
        {
            try { db.execSQL(Configurador.AlterarTabela2()); }
            catch (Exception e) { /*****/ }
        }

        if(oldVersion <= 17)
        {
            try { db.execSQL(Configurador.AlterarTabela2()); }
            catch (Exception e) { /*****/ }
        }

        if(oldVersion <= 18)
        {
            try
            {
                db.execSQL(ItensVendidos.AlterarTabela());
                db.execSQL(ItensVendidos.AlterarTabela2());
                db.execSQL(ItensVendidos.AlterarTabela3());
                db.execSQL(ItensVendidos.AlterarTabela4());
            }
            catch (Exception e) { /*****/ }
        }

        if(oldVersion <= 19)
        {
            try
            {
                db.execSQL(Item.AlterarTabela3());
            }
            catch (Exception e) { /*****/ }
        }

        if(oldVersion <= 23)
        {
            try
            {
                db.execSQL(Configurador.AlterarTabela3());
            }
            catch (Exception e) { /*****/ }
        }

        if(oldVersion <= 24)
        {
            try
            {
                db.execSQL(Configurador.AlterarTabela4());
            }
            catch (Exception e) { /*****/ }
        }
    }
}
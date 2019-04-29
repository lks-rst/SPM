package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.exeption.UpdateExeption;
import br.com.sulpasso.sulpassomobile.modelo.Configurador;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorConexao;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorEmpresa;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorHorarios;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorTelas;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorUsuario;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorVendas;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 05/12/2016 - 11:33 as part of the project SulpassoMobile.
 */
public class ConfiguradorDataAccess
{
    /*
    TODO: Rever essa classe. Está muito grande, deve estar executando operações indevidas.
     */
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;

    public ConfiguradorDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public Configurador getAll() throws GenercicException { return this.searchAll(); }

    public ConfiguradorEmpresa getEmpresa() throws GenercicException { return this.searchEmpresa(); }

    public ConfiguradorUsuario getUsuario() throws GenercicException { return this.searchUsuario(); }

    public float getMeta(int tipo) throws GenercicException { return this.searchMetaTotal(tipo); }

    public ConfiguradorVendas getVenda() throws GenercicException { return this.searchVenda(); }

    public ConfiguradorHorarios getHorario() throws GenercicException { return this.searchHorarios(); }

    public ConfiguradorConexao getConexao() throws GenercicException { return this.searchConexao(); }

    public ConfiguradorTelas getTelas() throws GenercicException { return this.searchTelas(); }

    public Boolean insert(String data) throws GenercicException { return this.insert(this.dataConverter(data)); }

    public Boolean insert(Configurador conta) throws GenercicException { return this.inserirConfigurador(conta); }

    public Boolean update(Configurador conta) throws GenercicException { return this.updateConfigurador(conta); }

    public Boolean insert(ConfiguradorEmpresa conta) throws GenercicException { return this.inserirConfiguradorEmpresa(conta); }

    public Boolean insert(ConfiguradorUsuario conta) throws GenercicException { return this.inserirConfiguradorUsuario(conta); }

    public Boolean insert(ConfiguradorVendas conta) throws GenercicException { return this.inserirConfiguradorVenda(conta); }

    public Boolean insert(ConfiguradorConexao conta) throws GenercicException { return this.inserirConfiguradorConexao(conta); }

    public Boolean insert(ConfiguradorHorarios conta) throws GenercicException { return this.inserirConfiguradorHorario(conta); }

    public Boolean insert(ConfiguradorTelas conta) throws GenercicException { return this.inserirConfiguradorTelas(conta); }

    public Boolean access(String usr, String pswd)
    {
        return this.login(usr, pswd);
    }

    public boolean verificarConfiguracao()
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT COUNT()");
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        return c.getCount() > 0 && c.getInt(0) > 0;
    }

    public int buscarSequencias(int item)
    {
        switch (item)
        {
            case 0 :
                return this.buscarSequenciaPw();
            case 1 :
                return this.buscarSequenciaVendas();
            default :
                return this.buscarSequenciaClientes();
        }
    }

    public String buscarValidade()
    {
        return this.getValidade();
    }

    public void atualizarSequencias(int item) throws GenercicException
    {
        switch (item)
        {
            case 0 :
                this.updateSequenciaPw(this.buscarSequenciaPw());
                break;
            case 1 :
                this.updateSequenciaVendas(this.buscarSequenciaVendas());
                break;
            default :
                this.updateSequenciaClientes(this.buscarSequenciaClientes());
                break;
        }
    }

    public Boolean updateAcesso(String time) throws GenercicException
    {
        return this.atualizarAcesso(time);
    }

    public Boolean updateVersao(String versao) throws GenercicException
    {
        return this.atualizarVersao(versao);
    }

    public void updateValidade(String s) throws UpdateExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALIDADE);
        this.sBuilder.append(" = '");
        this.sBuilder.append(s);
        this.sBuilder.append("';");

        try { this.db.execSQL(this.sBuilder.toString()); }
        catch (Exception exception) { throw new UpdateExeption(exception.getMessage()); }
    }

    public void updateSaldo(String s) throws UpdateExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SALDO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(Float.parseFloat(s) / 100);
        this.sBuilder.append("';");

        try { this.db.execSQL(this.sBuilder.toString()); }
        catch (Exception exception) { throw new UpdateExeption(exception.getMessage()); }
    }

    public void updateComissao(String s) throws UpdateExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.COMISSAO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(Float.parseFloat(s) / 100);
        this.sBuilder.append("';");

        try { this.db.execSQL(this.sBuilder.toString()); }
        catch (Exception exception) { throw new UpdateExeption(exception.getMessage()); }
    }

    public void updateVenda(String s) throws UpdateExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDA);
        this.sBuilder.append(" = '");
        this.sBuilder.append(Float.parseFloat(s) / 100);
        this.sBuilder.append("';");

        try { this.db.execSQL(this.sBuilder.toString()); }
        catch (Exception exception) { throw new UpdateExeption(exception.getMessage()); }
    }

    public void updateContribuicao(String s) throws UpdateExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(Float.parseFloat(s) / 1000000);
        this.sBuilder.append("';");

        try { this.db.execSQL(this.sBuilder.toString()); }
        catch (Exception exception) { throw new UpdateExeption(exception.getMessage()); }
    }

    public Boolean atualizarAcesso(String acesso) throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIMEATUALIZACAO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(acesso);
        this.sBuilder.append("'");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception exception) { throw new UpdateExeption(exception.getMessage()); }
    }

    public Boolean atualizarVersao(String versao) throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ATUALIZADO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(versao);
        this.sBuilder.append("'");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception exception) { throw new UpdateExeption(exception.getMessage()); }
    }

    public String buscaarEmpresa()
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMPRESACLIENTE);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { return c.getString(0); }
        catch (Exception exception) { return "NOS"; }
    }

    public String enviarEmail() throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ENVIADO_EMAIL);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { return c.getString(0); }
        catch (Exception exception) { throw new ReadExeption("Data nula ou inválida"); }
    }

    public int buscarCodigoUsuario()
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO_USUARIO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { return c.getInt(0); }
        catch (Exception exception) { return -1; }
    }

    public void alterarDataEmail(String data) throws UpdateExeption
    {
        this.updateDataEmail(data);
    }

    private void updateDataEmail(String s) throws UpdateExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ENVIADO_EMAIL);
        this.sBuilder.append(" = '");
        this.sBuilder.append(s);
        this.sBuilder.append("';");

        try { this.db.execSQL(this.sBuilder.toString()); }
        catch (Exception exception) { throw new UpdateExeption(exception.getMessage()); }
    }

    private Configurador dataConverter(String data)
    {
        Configurador conf = new Configurador();
        ConfiguradorEmpresa empresa = new ConfiguradorEmpresa();
        ConfiguradorUsuario usr = new ConfiguradorUsuario();
        ConfiguradorVendas vendas = new ConfiguradorVendas();
        ConfiguradorHorarios horarios = new ConfiguradorHorarios();
        ConfiguradorConexao conexao = new ConfiguradorConexao();
        ConfiguradorTelas telas = new ConfiguradorTelas();

        conf.setEmpresa(empresa);
        conf.setUsuario(usr);
        conf.setVendas(vendas);
        conf.setHorarios(horarios);
        conf.setConexao(conexao);
        conf.setTelas(telas);

        return conf;
    }

    private Boolean inserirConfigurador(Configurador conta) throws InsertionExeption
    {
        try
        {
            if ( /*inserirConfiguradorEmpresa(conta.getEmpresa()) &&
                inserirConfiguradorUsuario(conta.getUsuario()) &&
                inserirConfiguradorVenda(conta.getVendas()) &&
                inserirConfiguradorHorario(conta.getHorarios()) &&
                inserirConfiguradorConexao(conta.getConexao()) &&
                inserirConfiguradorTelas(conta.getTelas()) */ inserirConfiguradorFull(conta))
                return true;
            else
                return false;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private Boolean updateConfigurador(Configurador conta) throws InsertionExeption
    {
        try
        {
            if ( updateConfiguradorFull(conta))
                return true;
            else
                return false;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private Boolean inserirConfiguradorFull(Configurador conta) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ENDERECO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FONE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SITE);
        this.sBuilder.append(",");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LOGIN);
        this.sBuilder.append(",");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO_USUARIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME_USUARIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SENHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOFLEX);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALORFLEX);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOMINIMO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOCLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOPEDIDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAOIDEAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELAMINIMO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELATROCA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ROTEIRO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOORDENACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOBUSCA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SALDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.COMISSAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LOGIN);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.GERENCIARVISITAS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDERDEVEDORES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERANATUREZAINICIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERANATUREZAFIM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRAZOINICIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRAZOFIM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.RECALCULARPRECO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRECOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LIMITECREDITO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXGERAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXVENDA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTROLAESTOQUE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOGRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FRETE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SOLICITASENHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ESPECIALALTERAVALOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MINIMOPRAZO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTROLAGPS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPODESCONTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.INICIOMANHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FINALMANHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.INICIOTARDE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FINALTARDE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TEMPOPEDIDOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TEMPOCLIENTES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MAXIMOITENS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.HORA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CLIENTES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ATUALIZACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIMEATUALIZACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERFTP);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERFTP2);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERWEB);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FTPUSER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FTPPSWD);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.UPLOADFOLDER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DOWNLOADFOLDER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAILSENDER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAILPSWD);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL1);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL2);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL3);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONECTIONTYPE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMPRESACLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EFETUATROCA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDASDIRETA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MIXIDEAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MOSTRAMETA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CADASTROCLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOFOCO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAPEDIDOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAITENS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISACLIENTES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAGERAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TELAINICIAL);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(conta.getEmpresa().getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getNome());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getEndereco());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getFone());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getCidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getEmail());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getSite());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getLogin());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getNome());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getSenha());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getTipoFlex() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getValorFlex());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getPedidoMinimo());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getDescontoCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getDescontoItem());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getDescontoPedido());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getContribuicaoIdeal());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getTabelaMinimo());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getTabelaTroca());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getRoteiro());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getTipoOredenacao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getTipoBusca());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getSaldo());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getComissao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getContribuicao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getLogin());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getGerenciarVisitas() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getVenderDevedores() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getAlteraNaturezaInicio() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getAlteraNaturezaFim() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getAlteraPrazoInicio() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getAlteraPrazoFim() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getRecalcularPreco() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getAlteraPrecos() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getLimiteCredito() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getTipoFlex() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getFlexVenda() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getFlexItem() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getControlaEstoque() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getDescontoGrupo() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getFrete() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getSolicitaSenha() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getEspecialAlteraValor() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getMinimoPrazo() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getControlaGps() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getDescontoPecentual() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getValidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getInicioManha());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getFinalManha());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getInicioTarde());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getFinalTarde());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getTempoPedidos());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getTempoClientes());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getMaximoItens());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getHoraSistema());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getPedidos());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getClientes());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getAtualizacao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getDataAtualizacao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getServerFtp());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getServerFtp2());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getServerWeb());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getFtpUser());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getFtpPswd());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getUploadFolder());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getDownloadFolder());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmailSender());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmailPswd());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmail1());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmail2());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmail3());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getConectionType());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmpresa());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getEfetuaTroca() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getVendasDireta() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getMixIdeal() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getMostraMeta() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getCadastroCliente() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getTipoFoco() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getPesquisaPedidos());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getPesquisaItens());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getPesquisaClientes());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getPesquisaGeral());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getTelaInicial());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private Boolean updateConfiguradorFull(Configurador conta) throws InsertionExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(";");
        this.db.execSQL(this.sBuilder.toString());

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ENDERECO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FONE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SITE);
        this.sBuilder.append(",");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LOGIN);
        this.sBuilder.append(",");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO_USUARIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME_USUARIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SENHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOFLEX);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALORFLEX);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOMINIMO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOCLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOPEDIDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAOIDEAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELAMINIMO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELATROCA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ROTEIRO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOORDENACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOBUSCA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SALDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.COMISSAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LOGIN);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.GERENCIARVISITAS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDERDEVEDORES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERANATUREZAINICIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERANATUREZAFIM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRAZOINICIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRAZOFIM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.RECALCULARPRECO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRECOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LIMITECREDITO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXGERAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXVENDA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTROLAESTOQUE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOGRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FRETE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SOLICITASENHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ESPECIALALTERAVALOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MINIMOPRAZO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTROLAGPS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPODESCONTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.INICIOMANHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FINALMANHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.INICIOTARDE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FINALTARDE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TEMPOPEDIDOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TEMPOCLIENTES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MAXIMOITENS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.HORA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CLIENTES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ATUALIZACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIMEATUALIZACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERFTP);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERFTP2);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERWEB);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FTPUSER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FTPPSWD);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.UPLOADFOLDER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DOWNLOADFOLDER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAILSENDER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAILPSWD);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL1);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL2);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL3);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONECTIONTYPE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMPRESACLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EFETUATROCA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDASDIRETA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MIXIDEAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MOSTRAMETA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CADASTROCLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOFOCO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAPEDIDOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAITENS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISACLIENTES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAGERAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TELAINICIAL);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(conta.getEmpresa().getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getNome());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getEndereco());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getFone());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getCidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getEmail());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getSite());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getLogin());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getCodigo());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getNome());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getSenha());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getTipoFlex() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getValorFlex());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getPedidoMinimo());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getDescontoCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getDescontoItem());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getDescontoPedido());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getContribuicaoIdeal());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getTabelaMinimo());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getTabelaTroca());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getRoteiro());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getTipoOredenacao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getTipoBusca());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getSaldo());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getComissao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getUsuario().getContribuicao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getEmpresa().getLogin());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getGerenciarVisitas() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getVenderDevedores() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getAlteraNaturezaInicio() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getAlteraNaturezaFim() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getAlteraPrazoInicio() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getAlteraPrazoFim() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getRecalcularPreco() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getAlteraPrecos() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getLimiteCredito() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getTipoFlex() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getFlexVenda() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getFlexItem() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getControlaEstoque() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getDescontoGrupo() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getFrete() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getSolicitaSenha() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getEspecialAlteraValor() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getMinimoPrazo() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getControlaGps() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getDescontoPecentual() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getVendas().getValidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getInicioManha());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getFinalManha());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getInicioTarde());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getFinalTarde());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getTempoPedidos());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getTempoClientes());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getMaximoItens());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getHoraSistema());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getPedidos());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getClientes());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getAtualizacao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getHorarios().getDataAtualizacao());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getServerFtp());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getServerFtp2());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getServerWeb());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getFtpUser());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getFtpPswd());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getUploadFolder());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getDownloadFolder());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmailSender());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmailPswd());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmail1());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmail2());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmail3());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getConectionType());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getConexao().getEmpresa());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getEfetuaTroca() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getVendasDireta() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getMixIdeal() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getMostraMeta() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getCadastroCliente() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getTipoFoco() == true ? 1 : 0);
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getPesquisaPedidos());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getPesquisaItens());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getPesquisaClientes());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getPesquisaGeral());
        this.sBuilder.append("', '");
        this.sBuilder.append(conta.getTelas().getTelaInicial());
        this.sBuilder.append("');");

        try
        {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private Boolean inserirConfiguradorEmpresa(ConfiguradorEmpresa conta) throws InsertionExeption
    {
        try
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("UPDATE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
            this.sBuilder.append(" SET ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getCodigo());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getNome());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ENDERECO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getEndereco());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FONE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getFone());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CIDADE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getCidade());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getEmail());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SITE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getSite());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LOGIN);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getLogin());
            this.sBuilder.append("';");

            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private Boolean inserirConfiguradorUsuario(ConfiguradorUsuario conta) throws InsertionExeption
    {
        try
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("UPDATE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
            this.sBuilder.append(" SET ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO_USUARIO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getCodigo());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME_USUARIO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getNome());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SENHA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getSenha());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOFLEX);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getTipoFlex());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALORFLEX);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getValorFlex());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOMINIMO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getPedidoMinimo());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOCLIENTE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getDescontoCliente());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOITEM);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getDescontoItem());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOPEDIDO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getDescontoPedido());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAOIDEAL);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getContribuicaoIdeal());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELAMINIMO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getTabelaMinimo());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELATROCA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getTabelaTroca());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ROTEIRO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getRoteiro());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOORDENACAO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getTipoOredenacao());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOBUSCA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getTipoBusca());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SALDO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getSaldo());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.COMISSAO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getComissao());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getContribuicao());
            this.sBuilder.append("';");

            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private Boolean inserirConfiguradorVenda(ConfiguradorVendas conta) throws InsertionExeption
    {
        try
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append(" UPDATE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
            this.sBuilder.append(" SET ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.GERENCIARVISITAS);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getGerenciarVisitas() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDERDEVEDORES);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getVenderDevedores() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERANATUREZAINICIO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getAlteraNaturezaInicio() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERANATUREZAFIM);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getAlteraNaturezaFim() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRAZOINICIO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getAlteraPrazoInicio() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRAZOFIM);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getAlteraPrazoFim() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.RECALCULARPRECO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getRecalcularPreco() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRECOS);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getAlteraPrecos() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LIMITECREDITO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getLimiteCredito() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXGERAL);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getTipoFlex() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXVENDA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getFlexVenda() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXITEM);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getFlexItem() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTROLAESTOQUE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getControlaEstoque() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOGRUPO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getDescontoGrupo() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FRETE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getFrete() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SOLICITASENHA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getSolicitaSenha() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ESPECIALALTERAVALOR);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getEspecialAlteraValor() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MINIMOPRAZO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getMinimoPrazo() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTROLAGPS);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getControlaGps() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPODESCONTO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getDescontoPecentual() ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALIDADE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getValidade());
            this.sBuilder.append("';");

            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private Boolean inserirConfiguradorHorario(ConfiguradorHorarios conta) throws InsertionExeption
    {
        try
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("UPDATE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
            this.sBuilder.append(" SET ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.INICIOMANHA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getInicioManha());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FINALMANHA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getFinalManha());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.INICIOTARDE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getInicioTarde());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FINALTARDE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getFinalTarde());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TEMPOPEDIDOS);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getTempoPedidos());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TEMPOCLIENTES);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getTempoClientes());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MAXIMOITENS);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getMaximoItens());
            this.sBuilder.append("', ");/*
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.HORA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getHoraSistema());
            this.sBuilder.append("', ");*/
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOS);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getPedidos());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CLIENTES);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getClientes());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ATUALIZACAO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getAtualizacao());/*
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIMEATUALIZACAO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getDataAtualizacao());*/
            this.sBuilder.append("';");

            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private Boolean inserirConfiguradorConexao(ConfiguradorConexao conta) throws InsertionExeption
    {
        try
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("UPDATE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
            this.sBuilder.append(" SET ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERFTP);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getServerFtp());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERFTP2);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getServerFtp2());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERWEB);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getServerWeb());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FTPUSER);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getFtpUser());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FTPPSWD);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getFtpPswd());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.UPLOADFOLDER);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getUploadFolder());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DOWNLOADFOLDER);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getDownloadFolder());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAILSENDER);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getEmailSender());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAILPSWD);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getEmailPswd());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL1);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getEmail1());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL2);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getEmail2());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL3);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getEmail3());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMPRESACLIENTE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getEmpresa());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONECTIONTYPE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getConectionType());
            this.sBuilder.append("';");

            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private Boolean inserirConfiguradorTelas(ConfiguradorTelas conta) throws InsertionExeption
    {
        try
        {
            this.sBuilder.delete(0, this.sBuilder.length());
            this.sBuilder.append("UPDATE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
            this.sBuilder.append(" SET ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EFETUATROCA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getEfetuaTroca() == true ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDASDIRETA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getVendasDireta() == true ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MIXIDEAL);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getMixIdeal() == true ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MOSTRAMETA);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getMostraMeta() == true ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CADASTROCLIENTE);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getCadastroCliente() == true ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOFOCO);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getTipoFoco() == true ? 1 : 0);
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAPEDIDOS);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getPesquisaPedidos());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAITENS);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getPesquisaItens());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISACLIENTES);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getPesquisaClientes());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAGERAL);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getPesquisaGeral());
            this.sBuilder.append("', ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TELAINICIAL);
            this.sBuilder.append(" = '");
            this.sBuilder.append(conta.getTelaInicial());
            this.sBuilder.append("';");

            this.db.execSQL(this.sBuilder.toString());
            return true;
        }
        catch (Exception e) { throw new InsertionExeption(e.getMessage()); }
    }

    private Configurador searchAll() throws ReadExeption
    {
        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        Configurador conta = new Configurador();

        conta.setEmpresa(this.searchEmpresa());
        conta.setUsuario(this.searchUsuario());
        conta.setVendas(this.searchVenda());
        conta.setHorarios(this.searchHorarios());
        conta.setConexao(this.searchConexao());
        conta.setTelas(this.searchTelas());

        return conta;
    }

    private ConfiguradorEmpresa searchEmpresa() throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ENDERECO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FONE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SITE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LOGIN);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ATUALIZADO);

        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = null;

        try
        {
            c = this.db.rawQuery(this.sBuilder.toString(), null);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        c.moveToFirst();

        ConfiguradorEmpresa conta = new ConfiguradorEmpresa();

        conta.setCodigo(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO)));
        conta.setNome(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME)));
        conta.setEndereco(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ENDERECO)));
        conta.setFone(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FONE)));
        conta.setCidade(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CIDADE)));
        conta.setEmail(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL)));
        conta.setSite(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SITE)));
        conta.setLogin(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LOGIN)));
        conta.setAtualizado(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ATUALIZADO)));


        return conta;
    }

    private ConfiguradorUsuario searchUsuario() throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO_USUARIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME_USUARIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SENHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOFLEX);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALORFLEX);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOMINIMO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOCLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOPEDIDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAOIDEAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELAMINIMO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELATROCA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ROTEIRO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOORDENACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOBUSCA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SALDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.COMISSAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAO);

        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        ConfiguradorUsuario conta = new ConfiguradorUsuario();

        conta.setCodigo(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CODIGO_USUARIO)));
        conta.setNome(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME_USUARIO)));
        conta.setSenha(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SENHA)));
        conta.setTipoFlex(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOFLEX)) == 1);
        conta.setValorFlex(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALORFLEX)));
        conta.setPedidoMinimo(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOMINIMO)));
        conta.setDescontoCliente(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOCLIENTE)));
        conta.setDescontoItem(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOITEM)));
        conta.setDescontoPedido(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOPEDIDO)));
        conta.setContribuicaoIdeal(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAOIDEAL)));
        conta.setTabelaMinimo(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELAMINIMO)));
        conta.setTabelaTroca(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELATROCA)));
        conta.setRoteiro(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ROTEIRO)));
        conta.setTipoOredenacao(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOORDENACAO)));
        conta.setTipoBusca(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOBUSCA)));
        conta.setSaldo(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SALDO)));
        conta.setComissao(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.COMISSAO)));
        conta.setContribuicao(c.getFloat(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAO)));

        return conta;
    }

    private float searchMetaTotal(int tipo) throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");

        if(tipo == 0)
        {
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDA);
        }
        else if(tipo == 1)
        {
            this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.COMISSAO);
        }
        else
        {
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTRIBUICAO);
        }

        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        float meta = 0;

        meta = c.getFloat(0);

        return meta;
    }

    private ConfiguradorVendas searchVenda() throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.GERENCIARVISITAS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDERDEVEDORES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERANATUREZAINICIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERANATUREZAFIM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRAZOINICIO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRAZOFIM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.RECALCULARPRECO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRECOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LIMITECREDITO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXGERAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXVENDA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXITEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTROLAESTOQUE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOGRUPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FRETE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SOLICITASENHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ESPECIALALTERAVALOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MINIMOPRAZO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTROLAGPS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPODESCONTO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALIDADE);

        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(";");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        ConfiguradorVendas conta = new ConfiguradorVendas();

        try {
            conta.setGerenciarVisitas(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.GERENCIARVISITAS)) == 1);
            conta.setVenderDevedores(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDERDEVEDORES)) == 1);
            conta.setAlteraNaturezaInicio(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERANATUREZAINICIO)) == 1);
            conta.setAlteraNaturezaFim(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERANATUREZAFIM)) == 1);
            conta.setAlteraPrazoInicio(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRAZOINICIO)) == 1);
            conta.setAlteraPrazoFim(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRAZOFIM)) == 1);
            conta.setRecalcularPreco(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.RECALCULARPRECO)) == 1);
            conta.setAlteraPrecos(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ALTERAPRECOS)) == 1);
            conta.setLimiteCredito(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.LIMITECREDITO)) == 1);
            conta.setTipoFlex(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXGERAL)) == 1);
            conta.setFlexVenda(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXVENDA)) == 1);
            conta.setFlexItem(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FLEXITEM)) == 1);
            conta.setControlaEstoque(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTROLAESTOQUE)) == 1);
            conta.setDescontoGrupo(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DESCONTOGRUPO)) == 1);
            conta.setFrete(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FRETE)) == 1);
            conta.setSolicitaSenha(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SOLICITASENHA)) == 1);
            conta.setEspecialAlteraValor(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ESPECIALALTERAVALOR)) == 1);
            conta.setMinimoPrazo(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MINIMOPRAZO)) == 1);
            conta.setControlaGps(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONTROLAGPS)) == 1);
            conta.setDescontoPecentual(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPODESCONTO)) == 1);
            conta.setValidade(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALIDADE)));

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        return conta;
    }

    private ConfiguradorHorarios searchHorarios() throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT  ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.INICIOMANHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FINALMANHA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.INICIOTARDE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FINALTARDE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TEMPOPEDIDOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TEMPOCLIENTES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MAXIMOITENS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.HORA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CLIENTES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ATUALIZACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIMEATUALIZACAO);

        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        ConfiguradorHorarios conta = new ConfiguradorHorarios();

        conta.setInicioManha(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.INICIOMANHA)));
        conta.setFinalManha(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FINALMANHA)));
        conta.setInicioTarde(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.INICIOTARDE)));
        conta.setFinalTarde(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FINALTARDE)));
        conta.setTempoPedidos(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TEMPOPEDIDOS)));
        conta.setTempoClientes(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TEMPOCLIENTES)));
        conta.setMaximoItens(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MAXIMOITENS)));
        conta.setHoraSistema(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.HORA)));
        conta.setPedidos(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOS)));
        conta.setClientes(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CLIENTES)));
        conta.setAtualizacao(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ATUALIZACAO)));
        conta.setDataAtualizacao(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIMEATUALIZACAO)));


        return conta;
    }

    private ConfiguradorConexao searchConexao() throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERFTP);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERFTP2);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERWEB);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FTPUSER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FTPPSWD);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.UPLOADFOLDER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DOWNLOADFOLDER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAILSENDER);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAILPSWD);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL1);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL2);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL3);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONECTIONTYPE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMPRESACLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ENVIADO_EMAIL);

        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        ConfiguradorConexao conta = new ConfiguradorConexao();

        conta.setServerFtp(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERFTP)));
        conta.setServerFtp2(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERFTP2)));
        conta.setServerWeb(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SERVERWEB)));
        conta.setFtpUser(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FTPUSER)));
        conta.setFtpPswd(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.FTPPSWD)));
        conta.setUploadFolder(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.UPLOADFOLDER)));
        conta.setDownloadFolder(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.DOWNLOADFOLDER)));
        conta.setEmailSender(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAILSENDER)));
        conta.setEmailPswd(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAILPSWD)));
        conta.setEmail1(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL1)));
        conta.setEmail2(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL2)));
        conta.setEmail3(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMAIL3)));
        conta.setConectionType(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CONECTIONTYPE)));
        conta.setEmpresa(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EMPRESACLIENTE)));
        conta.setDataEmailEnviado(c.getString(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ENVIADO_EMAIL)));

        return conta;
    }

    private ConfiguradorTelas searchTelas() throws ReadExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EFETUATROCA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDASDIRETA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MIXIDEAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MOSTRAMETA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CADASTROCLIENTE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOFOCO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAPEDIDOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAITENS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISACLIENTES);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAGERAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TELAINICIAL);

        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        ConfiguradorTelas conta = new ConfiguradorTelas();

        conta.setEfetuaTroca(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.EFETUATROCA)) == 1);
        conta.setVendasDireta(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VENDASDIRETA)) == 1);
        conta.setMixIdeal(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MIXIDEAL)) == 1);
        conta.setMostraMeta(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.MOSTRAMETA)) == 1);
        conta.setCadastroCliente(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CADASTROCLIENTE)) == 1);
        conta.setTipoFoco(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TIPOFOCO)) == 1);
        conta.setPesquisaPedidos(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAPEDIDOS)));
        conta.setPesquisaItens(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAITENS)));
        conta.setPesquisaClientes(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISACLIENTES)));
        conta.setPesquisaGeral(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PESQUISAGERAL)));
        conta.setTelaInicial(c.getInt(c.getColumnIndex(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TELAINICIAL)));


        return conta;
    }

    private boolean login(String usr, String pswd)
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT COUNT()");
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.NOME_USUARIO);
        this.sBuilder.append(" LIKE '");
        this.sBuilder.append(usr);
        this.sBuilder.append("' AND ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.SENHA);
        this.sBuilder.append(" LIKE '");
        this.sBuilder.append(pswd);
        this.sBuilder.append("';");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        return c.getCount() == 1 && c.getInt(0) == 1;
    }

    private int buscarSequenciaClientes()
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CLIENTES);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { return c.getInt(0); }
        catch (Exception exception) { return 1; }
    }

    private int buscarSequenciaVendas()
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOS);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { return c.getInt(0); }
        catch (Exception exception) { return 1; }
    }

    private int buscarSequenciaPw()
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ATUALIZACAO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { return c.getInt(0); }
        catch (Exception exception) { return 1; }
    }

    private String getValidade()
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.VALIDADE);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { return c.getString(0); }
        catch (Exception exception) { return "--"; }
    }

    private void updateSequenciaClientes(int i) throws UpdateExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.CLIENTES);
        this.sBuilder.append(" = ");
        this.sBuilder.append(i + 1);

        try { this.db.execSQL(this.sBuilder.toString()); }
        catch (Exception exception) { throw new UpdateExeption(exception.getMessage()); }
    }

    private void updateSequenciaVendas(int i) throws UpdateExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.PEDIDOS);
        this.sBuilder.append(" = '");
        this.sBuilder.append(i + 1);
        this.sBuilder.append("';");

        try { this.db.execSQL(this.sBuilder.toString()); }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            exception.printStackTrace();
            throw new UpdateExeption(exception.getMessage());
        }
    }

    private void updateSequenciaPw(int i) throws UpdateExeption
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador.ATUALIZACAO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(i + 1);

        try { this.db.execSQL(this.sBuilder.toString()); }
        catch (Exception exception) { throw new UpdateExeption(exception.getMessage()); }
    }
}
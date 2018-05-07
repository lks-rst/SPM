package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.ClienteNovo;
import br.com.sulpasso.sulpassomobile.modelo.Configurador;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorConexao;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorEmpresa;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorHorarios;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorTelas;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorUsuario;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorVendas;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ClienteNovoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.FtpConnect;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipularArquivos;

/**
 * Created by Lucas on 02/08/2016 - 17:53 as part of the project SulpassoMobile.
 *
 */
public class AtualizarSistema
{
    private Context context;
    private JSONObject jsonObject;

    private ManipularArquivos arquivos;

    private int nrTabelas;
    private int posTabela;
    private boolean tabelas;

    private boolean erros;

    private String empresa;
    private int usr;

    private Configurador configurador;

    private int nr_arqs_pw = 0;
    private ArrayList<Venda> listaVendas;
    private ArrayList<ClienteNovo> listaNovos;

    public AtualizarSistema(Context ctx)
    {
        this.context = ctx;
        this.arquivos = new ManipularArquivos(ctx);
        this.posTabela = -1;
        this.tabelas = false;
        this.erros = false;
    }

    public boolean atualizar(int parte)
    {
        switch (parte)
        {
            case 0 :
                this.loadPrimaryData(1, 0);
                return true;
            case 1 :
                this.loadSistemData();
                return true;
            case 2 :
                this.loadSellData();
                return true;
            case 3 :
                return this.loadFile();
            case 4 :
                return this.verificarArquivo();
            case 5 :
                this.separarArquivo();
                return true;
            case 6 :
                return this.loadInquiry();
            case 7 :
                this.inserirConfiguracoes();
                return true;
            case 8 :
                return true;
            case 9 :
                this.carregarVendas();
                return true;
            case 10 :
                this.escreverVendas();
                return true;
            case 11 :
                return this.sendSells(1);
            case 12 :
                this.atualizarVendas(true);
                return true;
            case 13 :
                carregarClientes();
                return true;
            case 14 :
                this.sendClients();
                return true;
            case 15 :
                this.sendControlData();
                return true;
            case 16 :
                criarArquivoClientes();
                return true;
            case 17 :
                atualizarClientes();
                return true;
            case 20 :
                return downloadConfiguracao();
            case 21 :
                this.atualizarVendas(false);
                return true;
            default:
                return true;
        }
    }

    private boolean downloadConfiguracao()
    {
        ConfiguradorConexao server = new ConfiguradorConexao();
        server.setConectionType(0);
        server.setDownloadFolder("ENVIO");
        server.setFtpPswd("sulpasso2802");
        server.setFtpUser("sulpasso");
        server.setServerFtp("sulpasso.com.br");
        server.setUploadFolder("BAIXA");

        FtpConnect conect = new FtpConnect(server);

        if(conect.Conectar())
        {
            if(conect.baixarConfiguracao(this.empresa, this.usr)) { return true; }
            else
            {
                this.arquivos.addStringErro("Erro ao buscar arquivos de configuração");
                return false;
            }
        }
        else
        {
            this.erros = true;
            this.arquivos.addStringErro("Impossível conectar ao servidor.");
            return false;
        }
    }

    private void atualizarClientes()
    {
        ClienteNovoDataAccess cnda = new ClienteNovoDataAccess(this.context);
        VendaDataAccess vda = new VendaDataAccess(this.context);

        try { cnda.atualizarClientes(); }
        catch (GenercicException e) { this.arquivos.addStringErro(e.getMessage()); }

        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        try { cda.atualizarSequencias(2); }
        catch (GenercicException e) { this.arquivos.addStringErro(e.getMessage()); }
    }

    private void criarArquivoClientes()
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();
        int sequencia;
        int usuario;
        String nomeArquivo;

        sequencia = cda.buscarSequencias(2);
        usuario = cda.buscarCodigoUsuario();

        nomeArquivo = ms.comEsquerda("" + usuario, "0", 4) + ms.comEsquerda("" + sequencia, "0", 4)+ ".cli" ;

        this.arquivos.setNomeArquivo(nomeArquivo);
        this.arquivos.escreverClientes(this.listaNovos);
    }

    private void atualizarVendas(boolean ok)
    {
        VendaDataAccess vda = new VendaDataAccess(this.context);

        if(ok)
        {
            try { vda.atualizarVendas(1); }
            catch (GenercicException e) { this.arquivos.addStringErro(e.getMessage()); }

            ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
            try { cda.atualizarSequencias(1); }
            catch (GenercicException e) { this.arquivos.addStringErro(e.getMessage()); }
        }
        else
        {
            try { vda.atualizarVendas(2); }
            catch (GenercicException e) { this.arquivos.addStringErro(e.getMessage()); }
        }
    }

    private void escreverVendas()
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();
        int sequencia;
        int usuario;
        String nomeArquivo;

        sequencia = cda.buscarSequencias(1);
        usuario = cda.buscarCodigoUsuario();

        nomeArquivo = ms.comEsquerda("" + usuario, "0", 4) + ms.comEsquerda("" + sequencia, "0", 4)+ ".ped" ;

        this.arquivos.setNomeArquivo(nomeArquivo);
        this.arquivos.escreverVendas(this.listaVendas);
    }

    private void carregarVendas()
    {
        VendaDataAccess vda = new VendaDataAccess(this.context);
        vda.setSearchType(1);
        try { this.listaVendas = (ArrayList<Venda>) vda.getByDataEnviar(); }
        catch (GenercicException e)
        {
            this.arquivos.addStringErro(e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarClientes()
    {
        ClienteNovoDataAccess cnda = new ClienteNovoDataAccess(this.context);

        cnda.setSearchType(0);
        try { this.listaNovos = (ArrayList<ClienteNovo>) cnda.getByData(); }
        catch (GenercicException e)
        {
            this.arquivos.addStringErro(e.getMessage());
            e.printStackTrace();
        }
    }

    private void inserirConfiguracoes() { this.arquivos.inserirConfiguracao(this.configurador); }

    public void criarArquivoErros()
    {
        this.erros = this.arquivos.criarArquivoErros();
    }

    public void setUsr(int usr) { this.usr = usr; }

    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public int getNrTabelas() { return nrTabelas; }

    public int getPosTabela() { return posTabela; }

    public void setPosTabela(int posTabela) { this.posTabela = posTabela; }

    public boolean isTabelas() { return tabelas; }

    private void loadPrimaryData(int empresa, int vendedor)
    {
        this.jsonObject = this.arquivos.ArquivoConfiguracao();
    }

    private void loadSistemData()
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);

        this.configurador = new Configurador();

        try
        {
            this.configurador.setEmpresa(new ConfiguradorEmpresa(
                    (JSONObject) ((JSONObject) jsonObject.get("Configurador")).get("empresa")));
        }
        catch (JSONException e) { this.arquivos.addStringErro(e.getMessage()); }

        try
        {
            this.configurador.setConexao(new ConfiguradorConexao(
                    (JSONObject) ((JSONObject) jsonObject.get("Configurador")).get("conexao")));

        }
        catch (JSONException e) { this.arquivos.addStringErro(e.getMessage()); }

        try
        {
            this.configurador.setTelas(new ConfiguradorTelas(
                    (JSONObject) ((JSONObject) jsonObject.get("Configurador")).get("telas")));

        }
        catch (JSONException e) { this.arquivos.addStringErro(e.getMessage()); }
    }

    private void loadSellData()
    {
        if(this.configurador == null)
            this.configurador = new Configurador();

        try
        {
            this.configurador.setUsuario(new ConfiguradorUsuario(
                    (JSONObject) ((JSONObject) jsonObject.get("Configurador")).get("usuario")));

        }
        catch (JSONException e) { this.arquivos.addStringErro(e.getMessage()); }

        try
        {
            this.configurador.setVendas(new ConfiguradorVendas(
                    (JSONObject) ((JSONObject) jsonObject.get("Configurador")).get("vendas")));

        }
        catch (JSONException e) { this.arquivos.addStringErro(e.getMessage()); }

        try
        {
            this.configurador.setHorarios(new ConfiguradorHorarios(
                    (JSONObject) ((JSONObject) jsonObject.get("Configurador")).get("horarios")));

        }
        catch (JSONException e) { this.arquivos.addStringErro(e.getMessage()); }
    }

    private boolean loadFile()
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();
        ConfiguradorConexao server = new ConfiguradorConexao();
        try { server = cda.getConexao(); }
        catch (GenercicException e) { e.printStackTrace(); }

        int sequencia;
        int usuario;
        String nomeArquivo;

        sequencia = cda.buscarSequencias(0);
        usuario = cda.buscarCodigoUsuario();

        if(server.getConectionType() == 0 || server.getConectionType() == 2)
        {
            FtpConnect conect = new FtpConnect(server);

            ArrayList<String> arqs_pw = new ArrayList<String>();
            Boolean status = false;

            nomeArquivo = "Pw" + ms.comEsquerda("" + usuario, "0", 4) + "." + ms.comEsquerda("" + sequencia, "0", 3);

            if(conect.Conectar())
            {
                if(server.getConectionType() == 0)
                {
                    if(conect.MudarDiretorio(server.getDownloadFolder()))
                    {
                        arqs_pw = conect.ListarArquivos(sequencia, usuario);

                        if (arqs_pw.size() > 0){ status = true; }

                        if (status)
                        {
                            this.arquivos.excluirArquivosLocal();

                            nr_arqs_pw = arqs_pw.size() - 1;


                            conect.BaixarSd(arqs_pw.get(0), arqs_pw.get(0));

                            nomeArquivo = "Pw" + ms.comEsquerda("" + usuario, "0", 4) + "." + ms.comEsquerda("" + (sequencia + 1), "0", 3);
                            this.arquivos.setNomeArquivo(nomeArquivo);
                            return true;
                        }
                        else
                        {
                            this.arquivos.addStringErro("Não foram encontrados arquivos de atualização");
                            this.erros = true;
                            return false;
                        }
                    }
                    else
                    {
                        this.arquivos.addStringErro("Diretório de arquivos não encontrado");
                        this.erros = true;
                        return false;
                    }
                }
                else
                {
                    if(conect.MudarDiretorio("CLIENTES"))
                    {
                        if(conect.MudarDiretorio(server.getEmpresa()))
                        {
                            if(conect.MudarDiretorio(String.valueOf(usuario)))
                            {
                                if(conect.MudarDiretorio(server.getDownloadFolder()))
                                {
                                    arqs_pw = conect.ListarArquivos(sequencia, usuario);

                                    if (arqs_pw.size() > 0){ status = true; }

                                    if (status)
                                    {
                                        this.arquivos.excluirArquivosLocal();

                                        nr_arqs_pw = arqs_pw.size() - 1;

                                        conect.BaixarSd(arqs_pw.get(0), arqs_pw.get(0));

                                        nomeArquivo = "Pw" + ms.comEsquerda("" + usuario, "0", 4) + "." + ms.comEsquerda("" + (sequencia + 1), "0", 3);
                                        this.arquivos.setNomeArquivo(nomeArquivo);
                                        return true;
                                    }
                                    else
                                    {
                                        this.arquivos.addStringErro("Não foram encontrados arquivos de atualização");
                                        this.erros = true;
                                        return false;
                                    }
                                }
                                else
                                {
                                    this.arquivos.addStringErro("Diretório de arquivos não encontrado");
                                    this.erros = true;
                                    return false;
                                }
                            }
                            else
                            {
                                this.arquivos.addStringErro("Diretório de arquivos não encontrado");
                                this.erros = true;
                                return false;
                            }
                        }
                        else
                        {
                            this.arquivos.addStringErro("Diretório de arquivos não encontrado");
                            this.erros = true;
                            return false;
                        }
                    }
                    else
                    {
                        this.arquivos.addStringErro("Diretório de arquivos não encontrado");
                        this.erros = true;
                        return false;
                    }
                }
            }
            else
            {
                this.arquivos.addStringErro("Impossível conectar ao servidor.");
                this.erros = true;
                return false;
            }
        }
        else
        {
            if(server.getConectionType() == 1)
            {
                this.arquivos.addStringErro("Configuração para acesso via WS.");
                return false;
            }
            else
            {
                if(server.getConectionType() == 3)
                {
                    this.arquivos.addStringErro("Arquivos deverão ser inserido diretamente na pasta do sistema no dispositivo móvel para atualização.");
                    return false;
                }
                else if(server.getConectionType() == 10)
                {
                    nomeArquivo = "Pw" + ms.comEsquerda("" + usuario, "0", 4) + "." + ms.comEsquerda("" + (sequencia + 1), "0", 3);
                    this.arquivos.setNomeArquivo(nomeArquivo);

                    return true;
                }
                else
                {
                    this.arquivos.addStringErro("Acesso ao servidor configrado de maneira incorreta. Por favor verifique");
                    return false;
                }
            }
        }
    }

    private boolean verificarArquivo()
    {
        return this.arquivos.VerificarArquivo();
    }

    private void separarArquivo()
    {
        try { this.arquivos.separarStrings(); }
        catch (GenercicException e) { e.printStackTrace(); }
    }

    private boolean loadInquiry()
    {
        this.tabelas = true;
        this.posTabela++;
        this.arquivos.executar(this.posTabela);

        return true;
    }

    private boolean sendSells(int tipo)
    {
        boolean status = false;

        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();
        ConfiguradorConexao server = new ConfiguradorConexao();
        try { server = cda.getConexao(); }
        catch (GenercicException e) { e.printStackTrace(); }

        int sequencia;
        int usuario;
        String nomeArquivo;

        sequencia = cda.buscarSequencias(1);
        usuario = cda.buscarCodigoUsuario();

        nomeArquivo = ms.comEsquerda("" + usuario, "0", 4) + ms.comEsquerda("" + sequencia, "0", 4)+ ".ped" ;


        if(server.getConectionType() == 0)
        {
            FtpConnect conect = new FtpConnect(server);

            ArrayList<String> arqs_pw = new ArrayList<String>();

            if(conect.Conectar())
            {
                if(conect.MudarDiretorio(server.getUploadFolder()))
                {
                    status = conect.Mandar(nomeArquivo, nomeArquivo);
                }
                else
                {
                    arquivos.addStringErro("Erro ao mudar de diretorio. Pasta de pedidos não encontrada no servidor");
                    status = false;
                }
            }
            else
            {
                arquivos.addStringErro("Erro ao conectar com o servidor.");
                status = false;
            }
        }
        else if(server.getConectionType() == 10)
        {
            status = true;
        }

        return status;
    }

    private void sendClients()
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();
        ConfiguradorConexao server = new ConfiguradorConexao();
        try { server = cda.getConexao(); }
        catch (GenercicException e) { e.printStackTrace(); }

        int sequencia;
        int usuario;
        String nomeArquivo;

        sequencia = cda.buscarSequencias(2);
        usuario = cda.buscarCodigoUsuario();

        nomeArquivo = ms.comEsquerda("" + usuario, "0", 4) + ms.comEsquerda("" + sequencia, "0", 3)+ ".cli" ;

        if(server.getConectionType() == 0)
        {
            FtpConnect conect = new FtpConnect(server);

            ArrayList<String> arqs_pw = new ArrayList<String>();
            Boolean status = false;

            if(conect.Conectar())
            {
                if(conect.MudarDiretorio(server.getUploadFolder()))
                {
                    conect.Mandar(nomeArquivo, nomeArquivo);
                }
                else
                {
                    arquivos.addStringErro("Erro ao mudar de diretorio. Pasta de pedidos não encontrada no servidor");
                }
            }
            else
            {
                arquivos.addStringErro("Erro ao conectar com o servidor.");
            }
        }
        else if(server.getConectionType() == 10)
        {

        }
        /*
        this.arquivos.setNomeArquivo(nomeArquivo);

        this.arquivos.VerificarArquivo();
        */
    }

    private void sendControlData()
    { }

    public void verificarErros()
    {
        String message1 = "Foram encontrados erros ao executar o arquivo. Verifique o arquivo criado.";
        String message2 = "Não foram encontrados erros ao executar o arquivo.";

        Toast t = Toast.makeText(this.context, this.erros ? message1 : message2, Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
        t.show();
    }

    public String finalizarTabelas()
    {
        this.tabelas = false;

        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);

        try { cda.atualizarSequencias(0); }
        catch (GenercicException e)
        {
            this.arquivos.addStringErro(e.getMessage());
            e.printStackTrace();
        }

        return "";
    }

    public void setTabelas(boolean tabelas) { this.tabelas = tabelas; }

    public int buscarUsuario()
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        this.setUsr(cda.buscarCodigoUsuario());
        return this.usr;
    }

    public String buscarEmpresa()
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        this.setEmpresa(cda.buscaarEmpresa());
        return this.empresa;
    }
}
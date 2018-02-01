package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Configurador;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorConexao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorEmpresa;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorHorarios;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorTelas;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorUsuario;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorVendas;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.util.funcoes.FtpConnect;
import br.com.sulpassomobile.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;
import br.com.sulpassomobile.sulpasso.sulpassomobile.util.funcoes.ManipularArquivos;

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
                this.sendSells(1);
                return true;
            case 12 :
                this.atualizarVendas();
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
            this.arquivos.addStringErro("Impossível conectar ao servidor.");
            return false;
        }
    }

    private void atualizarClientes()
    {

    }

    private void criarArquivoClientes()
    {

    }

    private void atualizarVendas()
    {
        VendaDataAccess vda = new VendaDataAccess(this.context);
        try { vda.atualizarVendas(); }
        catch (GenercicException e) { this.arquivos.addStringErro(e.getMessage()); }

        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        try { cda.atualizarSequencias(1); }
        catch (GenercicException e) { this.arquivos.addStringErro(e.getMessage()); }
    }

    private void escreverVendas()
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();
        int sequencia;
        int usuario;
        String nomeArquivo;

        sequencia = cda.buscarSequencias(1);
        usuario = 6;

        nomeArquivo = ms.comEsquerda("" + usuario, "0", 4) + ms.comEsquerda("" + sequencia, "0", 3)+ ".ped" ;

        this.arquivos.setNomeArquivo(nomeArquivo);
        this.arquivos.escreverVendas(this.listaVendas);
    }

    private void carregarVendas()
    {
        VendaDataAccess vda = new VendaDataAccess(this.context);
        vda.setSearchType(1);
        try { this.listaVendas = (ArrayList<Venda>) vda.getByData(); }
        catch (GenercicException e)
        {
            this.arquivos.addStringErro(e.getMessage());
            e.printStackTrace();
        }
    }

    private void carregarClientes()
    {
        VendaDataAccess vda = new VendaDataAccess(this.context);
        vda.setSearchType(1);
        try { this.listaVendas = (ArrayList<Venda>) vda.getByData(); }
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
        /*
        TODO: Separar essa parte da atualização em diversas sub etapas para um controle mais fino de possiveis erros
         */
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();
        ConfiguradorConexao server = new ConfiguradorConexao();
        try { server = cda.getConexao(); }
        catch (GenercicException e) { e.printStackTrace(); }

        if(server.getConectionType() == 0 || server.getConectionType() == 2)
        {
            FtpConnect conect = new FtpConnect(server);

            ArrayList<String> arqs_pw = new ArrayList<String>();
            Boolean status = false;

            int sequencia;
            int usuario;
            String nomeArquivo;

            sequencia = cda.buscarSequencias(0);
            usuario = cda.buscarCodigoUsuario();

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

                            for (int i = 0; i < arqs_pw.size(); i++)
                            {
                            /*
                            TODO: Remover o for dessa parte do código?;
                             */
                                conect.BaixarSd(arqs_pw.get(i), arqs_pw.get(i));
                                break;
                            }

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

                                        for (int i = 0; i < arqs_pw.size(); i++)
                                        {
                                            /*
                                            TODO: Remover o for dessa parte do código?;
                                             */
                                            conect.BaixarSd(arqs_pw.get(i), arqs_pw.get(i));
                                            break;
                                        }

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
            this.arquivos.addStringErro("Não esta configurado com conexão via FTP.");
            return false;
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

    private void sendSells(int tipo)
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();
        ConfiguradorConexao server = new ConfiguradorConexao();
        try { server = cda.getConexao(); }
        catch (GenercicException e) { e.printStackTrace(); }

        if(server.getConectionType() == 0)
        {
            FtpConnect conect = new FtpConnect(server);

            ArrayList<String> arqs_pw = new ArrayList<String>();
            Boolean status = false;

            int sequencia;
            int usuario;
            String nomeArquivo;

            sequencia = cda.buscarSequencias(1);
            usuario = 6;

            nomeArquivo = ms.comEsquerda("" + usuario, "0", 4) + ms.comEsquerda("" + sequencia, "0", 3)+ ".ped" ;

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
        else
        {

        }
        /*
        this.arquivos.setNomeArquivo(nomeArquivo);

        this.arquivos.VerificarArquivo();
        */
    }

    private void sendClients()
    { }

    private void sendControlData()
    { }

    public void verificarErros()
    {
        String message1 = "Foram encontrados erros ao executar o arquivo. Verifique o arquivo criado.";
        String message2 = "Não foram encontrados erros ao executar o arquivo.";

        Toast.makeText(this.context, this.erros ? message1 : message2, Toast.LENGTH_LONG).show();
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
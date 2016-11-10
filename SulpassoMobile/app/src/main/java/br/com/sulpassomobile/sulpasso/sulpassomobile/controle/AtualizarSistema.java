package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.widget.Toast;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.util.funcoes.ManipularArquivos;

/**
 * Created by Lucas on 02/08/2016 - 17:53 as part of the project SulpassoMobile.
 *
 */
public class AtualizarSistema
{
    private Context context;

    private ManipularArquivos arquivos;

    private int nrTabelas;
    private int posTabela;
    private boolean tabelas;

    private boolean erros;

    public AtualizarSistema(Context ctx)
    {
        this.context = ctx;
        this.arquivos = new ManipularArquivos(ctx);
        this.posTabela = -1;
        this.tabelas = false;
        this.erros = false;
    }

    public String atualizar(int parte)
    {
        switch (parte)
        {
            case 0 :
                this.loadPrimaryData(1, 0);
                return "Carregando configuração da empresa e vendedor.";
            case 1 :
                this.loadSistemData(0);
                return "Carregando configuração de trabalho e dados.";
            case 2 :
                this.loadSellData();
                return "Carregando configurações de vendas.";
            case 3 :
                this.loadFile();
                return "Executando download dos dados.";
            case 4 :
                this.verificarArquivo();
                return "Verificando consistencia dos dados.";
            case 5 :
                this.separarArquivo();
                return "Separando dados para atualização.";
            case 6 :
                return this.loadInquiry();
            case 7 :
                return "";
            case 8 :
                return "";
            case 9 :
                return "";
            case 10 :
                return "";
            case 11 :
                this.sendSells(1);
                return "";
            case 12 :
                return "";
            case 13 :
                return "";
            case 14 :
                this.sendClients();
                return "";
            case 15 :
                this.sendControlData();
                return "";
            default:
                return "";
        }
    }

    public void criarArquivoErros()
    {
        this.erros = this.arquivos.criarArquivoErros();
    }

    public int getNrTabelas() { return nrTabelas; }

    public int getPosTabela() { return posTabela; }

    public void setPosTabela(int posTabela) { this.posTabela = posTabela; }

    public boolean isTabelas() { return tabelas; }

    private void loadPrimaryData(int empresa, int vendedor)
    { }

    private void loadSistemData(int vendedor)
    { }

    private void loadSellData()
    { }

    private void loadFile()
    {
        this.arquivos.VerificarArquivo();
    }

    private void verificarArquivo()
    {
        this.arquivos.VerificarArquivo();
    }

    private void separarArquivo()
    {
        try { this.arquivos.separarStrings(); }
        catch (GenercicException e) { e.printStackTrace(); }
    }

    private String loadInquiry()
    {
        this.tabelas = true;
        this.posTabela++;
        this.arquivos.executar(this.posTabela);

        return "Nome tabela" + this.posTabela;
    }

    private void sendSells(int tipo)
    { }

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
        return "Finalizando processo de atualização dos dados";
    }
}
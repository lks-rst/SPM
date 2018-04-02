package br.com.sulpasso.sulpassomobile.modelo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lucas on 05/12/2016 - 09:16 as part of the project SulpassoMobile.
 */
public class ConfiguradorTelas
{
    private Boolean efetuaTroca;
    private Boolean vendasDireta;
    private Boolean mixIdeal;
    private Boolean mostraMeta;
    private Boolean cadastroCliente;
    private Boolean tipoFoco;
    private int pesquisaPedidos;
    private int pesquisaItens;
    private int pesquisaClientes;
    private int pesquisaGeral;
    private int telaInicial;

    public ConfiguradorTelas()
    {
        /*
        this.efetuaTroca = false;
        this.vendasDireta = false;
        this.mixIdeal = false;
        this.mostraMeta = false;
        this.cadastroCliente = false;
        this.tipoFoco = false;
        this.pesquisaItens = 0;
        this.pesquisaPedidos = 0;
        this.pesquisaClientes = 1;
        this.pesquisaGeral = 1;
        this.telaInicial = 0;
        */
    }

    public ConfiguradorTelas(JSONObject empresa) throws JSONException
    {
        this.efetuaTroca = (empresa.getInt("efetuaTroca") == 0 ? true : false);
        this.vendasDireta = (empresa.getInt("vendasDireta") == 0 ? true : false);
        this.mixIdeal = (empresa.getInt("mixIdeal") == 0 ? true : false);
        this.mostraMeta = (empresa.getInt("mostraMeta") == 0 ? true : false);
        this.cadastroCliente = (empresa.getInt("cadastroCliente") == 0 ? true : false);
        this.tipoFoco = (empresa.getInt("tipoFoco") == 0 ? true : false);
        this.pesquisaItens = empresa.getInt("pesquisaPedidos");
        this.pesquisaPedidos = empresa.getInt("pesquisaItens");
        this.pesquisaClientes = empresa.getInt("pesquisaClientes");
        this.pesquisaGeral = empresa.getInt("pesquisaGeral");
        this.telaInicial = empresa.getInt("telaInicial");
    }

    public Boolean getEfetuaTroca() { return efetuaTroca; }

    public void setEfetuaTroca(Boolean efetuaTroca) { this.efetuaTroca = efetuaTroca; }

    public Boolean getVendasDireta() { return vendasDireta; }

    public void setVendasDireta(Boolean vendasDireta) { this.vendasDireta = vendasDireta; }

    public Boolean getMixIdeal() { return mixIdeal; }

    public void setMixIdeal(Boolean mixIdeal) { this.mixIdeal = mixIdeal; }

    public Boolean getMostraMeta() { return mostraMeta; }

    public void setMostraMeta(Boolean mostraMeta) { this.mostraMeta = mostraMeta; }

    public Boolean getCadastroCliente() { return cadastroCliente; }

    public void setCadastroCliente(Boolean cadastroCliente) { this.cadastroCliente = cadastroCliente; }

    public Boolean getTipoFoco() { return tipoFoco; }

    public void setTipoFoco(Boolean tipoFoco) { this.tipoFoco = tipoFoco; }

    public int getPesquisaPedidos() { return pesquisaPedidos; }

    public void setPesquisaPedidos(int pesquisaPedidos) { this.pesquisaPedidos = pesquisaPedidos; }

    public int getPesquisaItens() { return pesquisaItens; }

    public void setPesquisaItens(int pesquisaItens) { this.pesquisaItens = pesquisaItens; }

    public int getPesquisaClientes() { return pesquisaClientes; }

    public void setPesquisaClientes(int pesquisaClientes) { this.pesquisaClientes = pesquisaClientes; }

    public int getPesquisaGeral() { return pesquisaGeral; }

    public void setPesquisaGeral(int pesquisaGeral) { this.pesquisaGeral = pesquisaGeral; }

    public int getTelaInicial() { return telaInicial; }

    public void setTelaInicial(int telaInicial) { this.telaInicial = telaInicial; }

    @Override
    public String toString() {
        return "{" +
                "\"efetuaTroca\":\"" + efetuaTroca +
                "\", \"vendasDireta\":\"" + vendasDireta +
                "\", \"mixIdeal\":\"" + mixIdeal +
                "\", \"mostraMeta\":\"" + mostraMeta +
                "\", \"cadastroCliente\":\"" + cadastroCliente +
                "\", \"tipoFoco\":\"" + tipoFoco +
                "\", \"pesquisaPedidos\":\"" + pesquisaPedidos +
                "\", \"pesquisaItens\":\"" + pesquisaItens +
                "\", \"pesquisaClientes\":\"" + pesquisaClientes +
                "\", \"pesquisaGeral\":\"" + pesquisaGeral +
                "\", \"telaInicial\":\"" + telaInicial +
                "\"}";
    }
}
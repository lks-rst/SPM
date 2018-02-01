package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lucas on 05/12/2016 - 09:16 as part of the project SulpassoMobile.
 */
public class ConfiguradorHorarios
{
    private String inicioManha;
    private String finalManha;
    private String inicioTarde;
    private String finalTarde;
    private int tempoPedidos;
    private int tempoClientes;
    private int maximoItens;
    private String horaSistema;
    private int pedidos;
    private int clientes;
    private int atualizacao;
    private String dataAtualizacao;

    public ConfiguradorHorarios()
    {
        /*
        this.inicioManha = "07:30";
        this.finalManha = "12:00";
        this.inicioTarde = "13:30";
        this.finalTarde = "06:00";
        this.tempoPedidos = 180;
        this.tempoClientes = 60;
        this.maximoItens = 999;
        this.horaSistema = "07-12-2016 09:48";
        this.pedidos = 1;
        this.clientes = 1;
        this.atualizacao = 1;
        this.dataAtualizacao = "07-12-2016 09:48";
        */
    }

    public ConfiguradorHorarios(JSONObject horario) throws JSONException
    {
        this.inicioManha = horario.getString("inicioManha");
        this.finalManha = horario.getString("finalManha");
        this.inicioTarde = horario.getString("inicioTarde");
        this.finalTarde = horario.getString("finalTarde");
        this.tempoPedidos = horario.getInt("tempoPedidos");
        this.tempoClientes = horario.getInt("tempoClientes");
        this.maximoItens = horario.getInt("maximoItens");
        this.horaSistema = horario.getString("horaSistema");
        this.pedidos = horario.getInt("pedidos");
        this.clientes = horario.getInt("clientes");
        this.atualizacao = horario.getInt("atualizacao");
        this.dataAtualizacao = horario.getString("dataAtualizacao");
    }

    public String getInicioManha() { return inicioManha; }

    public void setInicioManha(String inicioManha) { this.inicioManha = inicioManha; }

    public String getFinalManha() { return finalManha; }

    public void setFinalManha(String finalManha) { this.finalManha = finalManha; }

    public String getInicioTarde() { return inicioTarde; }

    public void setInicioTarde(String inicioTarde) { this.inicioTarde = inicioTarde; }

    public String getFinalTarde() { return finalTarde; }

    public void setFinalTarde(String finalTarde) { this.finalTarde = finalTarde; }

    public int getTempoPedidos() { return tempoPedidos; }

    public void setTempoPedidos(int tempoPedidos) { this.tempoPedidos = tempoPedidos; }

    public int getTempoClientes() { return tempoClientes; }

    public void setTempoClientes(int tempoClientes) { this.tempoClientes = tempoClientes; }

    public int getMaximoItens() { return maximoItens; }

    public void setMaximoItens(int maximoItens) { this.maximoItens = maximoItens; }

    public String getHoraSistema() { return horaSistema; }

    public void setHoraSistema(String horaSistema) { this.horaSistema = horaSistema; }

    public int getPedidos() { return pedidos; }

    public void setPedidos(int pedidos) { this.pedidos = pedidos; }

    public int getClientes() { return clientes; }

    public void setClientes(int clientes) { this.clientes = clientes; }

    public int getAtualizacao() { return atualizacao; }

    public void setAtualizacao(int atualizacao) { this.atualizacao = atualizacao; }

    public String getDataAtualizacao() { return dataAtualizacao; }

    public void setDataAtualizacao(String dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    @Override
    public String toString() {
        return "{" +
                "\"inicioManha\":\"" + inicioManha + '"' +
                ", \"finalManha\":\"" + finalManha + '"' +
                ", \"inicioTarde\":\"" + inicioTarde + '"' +
                ", \"finalTarde\":\"" + finalTarde + '"' +
                ", \"tempoPedidos\":\"" + tempoPedidos +
                "\", \"tempoClientes\":\"" + tempoClientes +
                "\", \"maximoItens\":\"" + maximoItens +
                "\", \"horaSistema\":\"" + horaSistema +
                "\", \"pedidos\":\"" + pedidos +
                "\", \"clientes\":\"" + clientes +
                "\", \"atualizacao\":\"" + atualizacao +
                "\", \"dataAtualizacao\":\"" + dataAtualizacao +
                "\"}";
    }
}
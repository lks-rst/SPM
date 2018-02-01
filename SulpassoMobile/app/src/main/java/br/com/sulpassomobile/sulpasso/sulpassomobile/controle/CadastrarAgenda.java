package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import java.util.List;

/**
 * Created by Lucas on 01/08/2016.
 */
public class CadastrarAgenda
{
    private ConsultarClientes consultaClientes;
    private int cliente;
    private String data;
    private String hora;

    public CadastrarAgenda(ConsultarClientes consultaClientes)
    {
        this.consultaClientes = consultaClientes;
    }

    public void setData(String data) { this.data = data; }

    public void setHora(String hora) { this.hora = hora; }

    public void setCliente(int cliente) { this.cliente = cliente; }

    public Boolean adcionarAgenda(){ return false; }

    public List<String> exibirLista(){ return null;}
}

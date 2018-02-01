package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.List;

import br.com.sulpasso.sulpassomobile.modelo.Agenda;

/**
 * Created by Lucas on 02/08/2016.
 */
public class ConsultarAgenda
{
    private List<Agenda> lista;
    private ConsultarClientes consultaClientes;
    private int tipoBusca;
    private String dadosBusca;

    public ConsultarAgenda(Context ctx)
    {
        this.consultaClientes = new ConsultarClientes(ctx);
        this.tipoBusca = 0;
        this.dadosBusca = "";
        this.listarTodos();
    }

    public void setTipoBusca(int tipoBusca) { this.tipoBusca = tipoBusca; }

    public void setDadosBusca(String dadosBusca) { this.dadosBusca = dadosBusca; }

    public int buscarCodigoAgenda(int posicao){ return 0;}

    private void listarPorCliente(int cliente){ }

    private void listarTodos(){ }

    private void listarPorData(String data){ }

    public void excluirItem(int codigoAgenda){ }

    public List<String> exibrItens(){ return null;}
}

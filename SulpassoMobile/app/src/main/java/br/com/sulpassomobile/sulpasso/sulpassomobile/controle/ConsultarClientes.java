package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaCliente;

import static br.com.sulpassomobile.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaCliente.TODOS;

/**
 * Created by Lucas on 02/08/2016.
 */
public class ConsultarClientes
{
    private int tipoBusca;
    private String dadosBusca;
    private int codigoClienteSelecionado;

    private List<Cliente> lista;
    private ClienteDataAccess cda;

    public ConsultarClientes(Context ctx)
    {
        this.setDadosBusca("");
        this.setTipoBusca(TODOS.getValue());
        this.cda = new ClienteDataAccess(ctx);
    }

    public int getCodigoClienteSelecionado() { return this.codigoClienteSelecionado; }

    public void setTipoBusca(int tipoBusca) { this.tipoBusca = tipoBusca; }

    public void setDadosBusca(String dadosBusca) { this.dadosBusca = dadosBusca; }

    public ArrayList<String> exibirLista() throws GenercicException
    {
        ArrayList<String> retorno = new ArrayList<>();
        retorno.add("SELECIONE UM CLIENTE");

        this.listarClientes();
        for(Cliente c : this.lista) { retorno.add(c.toDisplay()); }

        return retorno;
    }

    public int getCodigoCliente(int posicao) { return this.lista.get(posicao).getCodigoCliente(); }

    public Cliente getCliente(int posicao)
    {
        this.codigoClienteSelecionado = this.lista.get(posicao).getCodigoCliente();
        return this.lista.get(posicao);
    }

    public void indicarCliente(int posicao)
    {
        this.codigoClienteSelecionado = this.lista.get(posicao).getCodigoCliente();
    }

    public int getClienteData(int type)
    {
        int retorno = 0;
        for(Cliente c : this.lista)
        {
            if(c.getCodigoCliente() == this.codigoClienteSelecionado)
            {
                switch (type)
                {
                    case 1:
                        retorno = c.getPrazo();
                        break;
                    case 2:
                        retorno = c.getNatureza();
                        break;
                }
                break;
            }
        }

        return retorno;
    }

    public Boolean clienteAlteraTabela()
    {
        Boolean liberado = false;
        try
        {
            Cliente c = this.cda.buscarCliente(this.codigoClienteSelecionado);

            if(!(c.getAlteraPrazo() == 'N'))
                liberado = true;

            if(c.getSituacao() == 'B')
                liberado = false;

            if(c.getEspecial() == 'E')
                liberado = false;
        }
        catch (ReadExeption readExeption)
        {
            readExeption.printStackTrace();
            liberado = false;
        }
        return liberado;
    }

    public int restoreClient()
    {
        int i;
        for (i = 0; i < this.lista.size(); i++)
        {
            if(this.lista.get(i).getCodigoCliente() == this.getCodigoClienteSelecionado())
                break;
        }

        return i;
    }

    private void listarClientes() throws GenercicException
    {
        TiposBuscaCliente tb = TiposBuscaCliente.getTipoFromInt(tipoBusca);
        switch (tb)
        {
            case TODOS :
                System.out.println("Busca por todos os clientes");
                this.listarTodos();
                break;
            case AGENDA :
            case RAZAO :
            case FANTASIA :
            case CIDADE :
            case VISITA :
            default:
                System.out.println("Busca por de clientes por visita");
                this.listarPesquisa();
                break;
        }
    }

    private void listarPesquisa() throws GenercicException
    {
        this.cda.setSearchData(this.dadosBusca);
        this.cda.setSearchType(this.tipoBusca);
        this.lista = this.cda.getByData();
    }

    private void listarTodos() throws GenercicException { this.lista = this.cda.getAll(); }

    private int dataToInt() { return Integer.parseInt(this.dadosBusca); }
}

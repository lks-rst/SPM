package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Cidade;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.Motivos;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.MotivosDataAccess;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaCliente;

import static br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaCliente.TODOS;

/**
 * Created by Lucas on 02/08/2016.
 */
public class ConsultarClientes
{
    private int tipoBusca;
    private String dadosBusca;
    private int codigoClienteSelecionado;

    private List<Cliente> lista;
    private List<Motivos> motivos;
    private List<Cidade> cidades;
    private ClienteDataAccess cda;
    private MotivosDataAccess mda;

    public ConsultarClientes(Context ctx)
    {
        this.setDadosBusca("");
        this.setTipoBusca(TODOS.getValue());
        this.cda = new ClienteDataAccess(ctx);
        this.mda = new MotivosDataAccess(ctx);
    }

    public int getCodigoClienteSelecionado() { return this.codigoClienteSelecionado; }

    public int getBancoCliente()
    {
        int banco;

        banco = this.cda.buscarBanco(this.codigoClienteSelecionado, 0);

        if(banco >= 0)
            banco = this.cda.buscarBanco(this.codigoClienteSelecionado, 1);

        return banco;
    }

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

    public ArrayList<String> exibirLista(int tipo, String dados) throws GenercicException
    {
        this.setDadosBusca(dados);
        this.setTipoBusca(tipo);

        if(tipo == 0) { this.listarTodos(); }
        else { this.listarClientes(); }

        ArrayList<String> retorno = new ArrayList<>();
        retorno.add("SELECIONE UM CLIENTE");

        for(Cliente c : this.lista) { retorno.add(c.toDisplay()); }

        return retorno;
    }

    public ArrayList<String> exibirMotivos() throws GenercicException
    {
        this.setDadosBusca("");
        this.setTipoBusca(0);

        this.listarMotivos();

        ArrayList<String> retorno = new ArrayList<>();
        retorno.add("SELECIONE UM MOTIVO");

        for(Motivos m : this.motivos) { retorno.add(m.toDisplay()); }

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
        try
        {
            Cliente c = this.cda.buscarCliente(this.codigoClienteSelecionado);

            if((String.valueOf(c.getAlteraPrazo()).equalsIgnoreCase("N")))
                return false;

            if(String.valueOf(c.getSituacao()).contentEquals("B"))
                return false;

            if(String.valueOf(c.getEspecial()).equalsIgnoreCase("E"))
                return false;

            return true;
        }
        catch (ReadExeption readExeption)
        {
            readExeption.printStackTrace();
            return false;
        }
    }

    public int restoreClient()
    {
        int pos = 0;
        for (int i = 0; i < this.lista.size(); i++)
        {
            if(this.lista.get(i).getCodigoCliente() == this.getCodigoClienteSelecionado())
            {
                pos = i + 1;
                break;
            }
        }

        return pos;
    }

    public ArrayList<String> listarCidades()
    {
        ArrayList<String> retorno = new ArrayList();

        this.cidades = this.cda.buscarCidades();

        retorno.add("Escolha uma cidade");
        for(Cidade c : this.cidades) { retorno.add(c.toDisplay()); }

        return retorno;
    }

    public int cidadeCod(int position)
    {
        return this.cidades.get(position).getCodigo();
    }

    public boolean possuiTitulos(int cliente) { return this.verificarTitulos(cliente); }

    public boolean possuiDevolucoes(int cliente) { return this.verificarDevolucoes(cliente); }

    public int codigoMotivo(int posicao)
    {
        return this.motivos.get(posicao).getCodigo();
    }

    private void listarClientes() throws GenercicException
    {
        TiposBuscaCliente tb = TiposBuscaCliente.getTipoFromInt(tipoBusca);

        if(tb == TODOS) { this.listarTodos(); }
        else { this.listarPesquisa(); }
        /*
        switch (tb)
        {
            case TODOS :
                this.listarTodos();
                break;
            case AGENDA :
                this.listarPesquisa();
                break;
            case RAZAO :
                this.listarPesquisa();
                break;
            case FANTASIA :
                this.listarPesquisa();
                break;
            case CIDADE :
                this.listarPesquisa();
                break;
            case VISITA :
                this.listarPesquisa();
                break;
            default:
                this.listarPesquisa();
                break;
        }
        */
    }

    private void listarMotivos() throws GenercicException
    {
        this.buscarMotivos();
    }

    private void listarPesquisa() throws GenercicException
    {
        this.cda.setSearchData(this.dadosBusca);
        this.cda.setSearchType(this.tipoBusca);
        this.lista = this.cda.getByData();
    }

    private void buscarMotivos() throws GenercicException
    {
        this.motivos = this.mda.getAll();
    }

    private void listarTodos() throws GenercicException { this.lista = this.cda.getAll(); }

    private int dataToInt() { return Integer.parseInt(this.dadosBusca); }

    private boolean verificarTitulos(int cliente) { return this.cda.clientePossuiTitulos(cliente); }

    private boolean verificarDevolucoes(int cliente) { return this.cda.clientePossuiDevolucoes(cliente); }
}
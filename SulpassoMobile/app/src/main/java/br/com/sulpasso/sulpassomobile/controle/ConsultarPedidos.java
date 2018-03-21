package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 02/08/2016.
 */
public class ConsultarPedidos
{
    private ArrayList<Venda> enviadas;
    private ArrayList<Venda> naoEnviadas;
    private ArrayList<Venda> todas;
    private VendaDataAccess vda;

    private int posicaoPedido;

    public ConsultarPedidos(Context ctx)
    {
        this.vda = new VendaDataAccess(ctx);
    }

    public ArrayList<String> listarPedidos(int tipo, String data)
    {
        this.naoEnviadas = null;
        this.enviadas = null;
        this.todas = null;

        switch (tipo)
        {
            case 0 :
                this.buscarTodos(data);
                return this.listarTodosPedidos();
            case 1 :
                this.buscarEnviados(data);
                return this.listarPedidosEnviados();
            case 2 :
                this.buscarNaoEnviados(data);
                return this.listarPedidosNaoEnviados();
            default :
                this.buscarTodos(data);
                return this.listarTodosPedidos();
        }
    }

    public ArrayList<String> listarItens(int posicao)
    {
        if(enviadas == null)
            if(naoEnviadas == null)
                return exibirItens(todas.get(posicao).getItens());
            else
                return exibirItens(naoEnviadas.get(posicao).getItens());
        else
            return exibirItens(enviadas.get(posicao).getItens());
    }

    public void reenvioPedidos(int inicio, int fim)
    {

    }

    public void copiaPedidos(int inicio, int fim)
    {

    }

    public boolean excluirPedido()
    {
        int cod = 0;

        switch (this.tipoBusca())
        {
            case 0:
                cod = this.todas.get(this.posicaoPedido).getCodigo();
                break;
            case 1:
                break;
            case 2:
                cod = this.naoEnviadas.get(this.posicaoPedido).getCodigo();
                break;
        }

        try { this.vda.excluirPedido(cod); }
        catch (GenercicException e) { cod = 0; }

        return cod > 0;
    }

    public int alterarPedido()
    {
        int cod = 0;

        switch (this.tipoBusca())
        {
            case 0:
                cod = this.todas.get(this.posicaoPedido).getCodigo();
                break;
            case 1:
                break;
            case 2:
                cod = this.naoEnviadas.get(this.posicaoPedido).getCodigo();
                break;
        }

        return cod;
    }

    public int tipoBusca()
    {
        if(this.naoEnviadas != null)
            return 1;
        else if(this.naoEnviadas != null)
            return 2;
        else
            return 0;
    }

    private ArrayList<String> exibirItens(ArrayList<ItensVendidos> itens)
    {
        ArrayList<String> retorno = new ArrayList<>();
        ManipulacaoStrings ms = new ManipulacaoStrings();

        for(ItensVendidos item : itens)
        {
            retorno.add(ms.comEsquerda(String.valueOf(item.getItem()), " ", 10) + " - " +
                ms.comEsquerda(vda.recuperarDescricao(item.getItem()), " ", 20) +
                item.toDisplay());
        }

        return retorno;
    }

    private ArrayList<String> listarPedidosEnviados()
    {
        ArrayList<String> lista = new ArrayList<>();

        for(Venda v : this.enviadas)
            lista.add(v.toDisplay());

        return lista;
    }

    private ArrayList<String> listarPedidosNaoEnviados()
    {
        ArrayList<String> lista = new ArrayList<>();

        for(Venda v : this.naoEnviadas)
            lista.add(v.toDisplay());

        return lista;
    }

    private ArrayList<String> listarTodosPedidos()
    {
        ArrayList<String> lista = new ArrayList<>();

        for(Venda v : this.todas)
            lista.add(v.toDisplay());

        return lista;
    }

    private void buscarEnviados(String data)
    {
        this.vda.setSearchType(0);
        this.vda.setSearchData(data);

        try { this.enviadas = (ArrayList<Venda>) this.vda.getByData(); }
        catch (GenercicException e) { this.todas = new ArrayList<>(); }
    }

    private void buscarNaoEnviados(String data)
    {
        this.vda.setSearchType(1);
        this.vda.setSearchData(data);

        try { this.naoEnviadas = (ArrayList<Venda>) this.vda.getByData(); }
        catch (GenercicException e) { this.todas = new ArrayList<>(); }
    }

    private void buscarTodos(String data)
    {
        try { this.todas = (ArrayList<Venda>) this.vda.getAll(); }
        catch (GenercicException e) { this.todas = new ArrayList<>(); }
    }

    public int getPosicaoPedido() { return posicaoPedido; }

    public void setPosicaoPedido(int posicaoPedido) { this.posicaoPedido = posicaoPedido; }
}
package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;
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
    private int tipoBusca;

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

    public ArrayList<Venda> listarPedidosV(int tipo, String data)
    {
        this.naoEnviadas = null;
        this.enviadas = null;
        this.todas = null;

        this.setTipoBusca(tipo);

        if(data.length() < 10)
        {

            Calendar calendario = Calendar.getInstance();
            int year = calendario.get(Calendar.YEAR);
            int month = (calendario.get(Calendar.MONTH) + 1);
            int day = calendario.get(Calendar.DAY_OF_MONTH);

            ManipulacaoStrings ms = new ManipulacaoStrings();
            data = ms.comEsquerda(String.valueOf(day), "0" , 2) + "/" + ms.comEsquerda(String.valueOf(month), "0" , 2) + "/" + year;
            data = ms.dataBanco(data);

        }

        switch (tipo)
        {
            case 0 :
                this.buscarTodos(data);
                return this.todas;
            case 1 :
                this.buscarEnviados(data);
                return this.enviadas;
            case 2 :
                this.buscarNaoEnviados(data);
                return this.naoEnviadas;
            default :
                this.buscarTodos(data);
                return this.todas;
        }
    }

    public ArrayList<Venda> listarPedidosV(String data)
    {
        return this.listarPedidosV(this.tipoBusca, data);
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
        Double descontoPedido = new Double(0);

        switch (this.tipoBusca())
        {
            case 0:
                descontoPedido = this.todas.get(this.posicaoPedido).getDesconto();
                cod = this.todas.get(this.posicaoPedido).getCodigo();
                break;
            case 1:
                break;
            case 2:
                descontoPedido = this.naoEnviadas.get(this.posicaoPedido).getDesconto();
                cod = this.naoEnviadas.get(this.posicaoPedido).getCodigo();
                break;
        }

        try
        {
            float flex = 0;
            float flexPedido = descontoPedido.floatValue();
            float flexItens = this.vda.buscarFlexItens(cod);
            float saldo = this.vda.buscarSaldoAtual();

            flex = flexPedido + flexItens;
            saldo = saldo + flex;

            this.vda.excluirPedido(cod);
            this.vda.atualizarSaldo(saldo);
        }
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
        this.vda.setSearchData(data);

        try { this.todas = (ArrayList<Venda>) this.vda.getAll(); }
        catch (GenercicException e) { this.todas = new ArrayList<>(); }
    }

    public int getPosicaoPedido() { return posicaoPedido; }

    public void setPosicaoPedido(int posicaoPedido) { this.posicaoPedido = posicaoPedido; }

    public boolean pedidoJaEnviado()
    {
        boolean enviado = false;

        if(this.naoEnviadas != null)
            enviado = false;
        else if(this.enviadas != null)
            enviado = true;
        else if(this.todas != null)
            enviado = this.todas.get(this.posicaoPedido).getEnviado() == 1 ? true : false;


        return enviado;
    }

    public int getTipoBusca() { return tipoBusca; }

    public void setTipoBusca(int tipoBusca) { this.tipoBusca = tipoBusca; }

    public String totalizadorVendas(int tipo)
    {
        int quantidade = 0;
        float total = 0;
        float volume = 0;
        float contribuicao = 0;

        switch (this.tipoBusca)
        {
            case 0:
                quantidade = this.todas.size();
                for(Venda v : this.todas)
                {
                    total += v.getValor();
                }
                break;
            case 1:
                quantidade = this.enviadas.size();
                for(Venda v : this.enviadas)
                {
                    total += v.getValor();
                }
                break;
            case 2:
                quantidade = this.naoEnviadas.size();
                for(Venda v : this.naoEnviadas)
                {
                    total += v.getValor();
                }
                break;
        }

        switch (tipo)
        {
            case 1:
                return Formatacao.format2d(quantidade);
            case 2:
                return Formatacao.format2d(total);
            case 3:
                return Formatacao.format2d(volume);
            case 4:
                return Formatacao.format2d(contribuicao);
            default:
                return Formatacao.format2d(quantidade);
        }
    }
}
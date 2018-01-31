package br.com.sulpasso.sulpassomobile.controle.interfaces;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.modelo.CurvaAbc;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;

/**
 * Created by Lucas on 24/11/2016 - 15:47 as part of the project SulpassoMobile.
 */
public interface ConsultaClientes
{
    public ArrayList<String> buscarListaClientes();
    public ArrayList<String> buscarListaTitulos();
    public ArrayList<String> buscarListaDevolucao();
    public ArrayList<String> buscarListaCorte();
    public ArrayList<String> buscarListaPrePedido();
    public ArrayList<String> buscarListaPositivacao(boolean semana, boolean positivado);
    public String totalPositivacao();

    public ArrayList<String> buscarListaClientes(int tipo, String cliente);
    public ArrayList<String> buscarListaTitulos(int tipo, String cliente);
    public ArrayList<String> buscarListaDevolucao(int tipo, String cliente);
    public ArrayList<String> buscarListaCorte(int tipo, String cliente);
    public ArrayList<String> buscarListaPrePedido(int tipo, String cliente);
    public ArrayList<String> buscarListaPositivacao(int tipo, String cliente);

    public ArrayList<String> buscarListaClientes(int tipo, int cidade);
    public ArrayList<String> buscarListaTitulos(int tipo, int cidade);
    public ArrayList<String> buscarListaDevolucao(int tipo, int cidade);
    public ArrayList<String> buscarListaCorte(int tipo, int cidade);
    public ArrayList<String> buscarListaPrePedido(int tipo, int cidade);
    public ArrayList<String> buscarListaPositivacao(int tipo, int cidade);

    public ArrayList<String> detalharCorte(int cliente);
    public CurvaAbc detalharAbc(int cliente);
    public ArrayList<String> detalharDevolucao(int cliente);
    public PrePedido detalharPrePedido(int cliente);
    public ArrayList<String> detalharTitulos(int cliente);
    public ArrayList<String> detalhesConsulta(int cliente, int tipo);

    public ArrayList<String> buscarItensCorte(int posicao);
    public ArrayList<String> buscarItensDevolucao(int posicao);
    public ArrayList<String> buscarItensTitulos(int posicao);
    public ArrayList<String> buscarDetalhes(int posicao, int tipo);
}
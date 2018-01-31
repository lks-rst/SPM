package br.com.sulpassomobile.sulpasso.sulpassomobile.controle.interfaces;

import java.util.ArrayList;

/**
 * Created by Lucas on 24/11/2016 - 15:47 as part of the project SulpassoMobile.
 */
public interface ConsultaClientes
{
    public ArrayList<String> buscarListaClientes();
    public ArrayList<String> buscarListaTitulos();
    ArrayList<String> buscarListaDevolucao();
    ArrayList<String> buscarListaCorte();
    ArrayList<String> buscarListaPrePedido();
    ArrayList<String> buscarListaPositivacao();

    public ArrayList<String> buscarListaClientes(int tipo, String cliente);
    public ArrayList<String> buscarListaTitulos(int tipo, String cliente);
    ArrayList<String> buscarListaDevolucao(int tipo, String cliente);
    ArrayList<String> buscarListaCorte(int tipo, String cliente);
    ArrayList<String> buscarListaPrePedido(int tipo, String cliente);
    ArrayList<String> buscarListaPositivacao(int tipo, String cliente);

    public ArrayList<String> buscarListaClientes(int tipo, int cidade);
    public ArrayList<String> buscarListaTitulos(int tipo, int cidade);
    ArrayList<String> buscarListaDevolucao(int tipo, int cidade);
    ArrayList<String> buscarListaCorte(int tipo, int cidade);
    ArrayList<String> buscarListaPrePedido(int tipo, int cidade);
    ArrayList<String> buscarListaPositivacao(int tipo, int cidade);
}
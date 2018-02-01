package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

import java.util.ArrayList;

/**
 * Created by Lucas on 08/11/2016 - 13:47 as part of the project SulpassoMobile.
 */
public class GrupoBloqueadoCliente
{
    private Cliente cliente;
    private ArrayList<Grupo> grupos;

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public ArrayList<Grupo> getGrupos() { return grupos; }

    public void setGrupos(ArrayList<Grupo> grupos) { this.grupos = grupos; }
}
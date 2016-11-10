package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

import java.util.ArrayList;

/**
 * Created by Lucas on 08/11/2016 - 13:49 as part of the project SulpassoMobile.
 */
public class GrupoBloqueadoNatureza
{
    private Natureza natureza;
    private ArrayList<Grupo> grupos;

    public Natureza getNatureza() { return natureza; }

    public void setNatureza(Natureza natureza) { this.natureza = natureza; }

    public ArrayList<Grupo> getGrupos() { return grupos; }

    public void setGrupos(ArrayList<Grupo> grupos) { this.grupos = grupos; }
}
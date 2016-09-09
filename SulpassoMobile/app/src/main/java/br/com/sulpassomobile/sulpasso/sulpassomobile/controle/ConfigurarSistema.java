package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by Lucas on 02/08/2016.
 */
public class ConfigurarSistema
{
    private Context context;

    private HashMap<String, String> configuracoesVenda;

    public float pedidoMinimo()
    {
        return this.getValorMinimo();
    }

    private float getValorMinimo()
    {
        return 50;
    }
}

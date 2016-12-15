package br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.controle.interfaces.ConsultaClientes;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.ConsultasClientes;

/**
 * Created by Lucas on 24/11/2016 - 15:11 as part of the project SulpassoMobile.
 */
public class ConsultaClientesDevolucao extends Fragment
{
    /*
        TODO: Verificar a cor de apresentação da lista;
     */
    private ConsultaClientes callback;

    public ConsultaClientesDevolucao(){}
    /**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.callback = null;
        try
        {
            this.callback = (ConsultaClientes) getTargetFragment();
            return inflater.inflate(R.layout.fragment_consulta_clientes_listas, /*container, false*/null);
        }
        catch (ClassCastException e)
        {
            Log.e(this.getClass().getSimpleName(), "Callback ConsultaClientes must be implemented by target fragment!", e);
            throw e;
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();
        this.setUpLayout();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        try { listarItens(); }
        catch (GenercicException e) { e.printStackTrace(); }
    }


    public void listarItens() throws GenercicException
    {
        ((ListView) (getActivity().findViewById(R.id.liFcclSimples))).setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                ((ConsultasClientes) getActivity()).buscarListaDevolucao()
            )
        );
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/

/*********************************END OF ITERFACES METHODS*****************************************/
}
package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.Adapters.AdapterMensagens;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.views.ConsultasGerenciais;
import br.com.sulpasso.sulpassomobile.views.Inicial;

/**
 * Created by Lucas on 20/03/2017 - 09:29 as part of the project SulpassoMobile.
 */
public class ConsultaGerencialMensagem extends Fragment
{
    public ConsultaGerencialMensagem(){}
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
        return inflater.inflate(R.layout.fragment_consulta_gerencial_mensagens, /*container, false*/null);
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
        AdapterMensagens adapter;
        try
        {
            adapter = new AdapterMensagens(getActivity().getApplicationContext(), ((ConsultasGerenciais) getActivity()).buscarListaMensagens());
        }
        catch (Exception ex)
        {
            adapter = new AdapterMensagens(getActivity().getApplicationContext(), ((Inicial) getActivity()).buscarListaMensagens());
        }

        ((ListView) (getActivity().findViewById(R.id.liFcgMensagem))).setAdapter(adapter);

        /*

        ((ListView) (getActivity().findViewById(R.id.liFcgMensagem))).setAdapter
            (
                new ArrayAdapter<String>
                    (
                        getActivity().getApplicationContext(),
                        android.support.design.R.layout.support_simple_spinner_dropdown_item,
                        ((ConsultasGerenciais) getActivity()).buscarListaMensagens()
                    )
            );
         */
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/

/*********************************END OF ITERFACES METHODS*****************************************/
}
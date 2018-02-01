package br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.controle.ConsultaMinimosGravososKitsCampanhas;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;

/**
 * Created by Lucas on 22/11/2016 - 15:26 as part of the project SulpassoMobile.
 */
public class ConsultaItensMinimos extends Fragment
{
    private ConsultaMinimosGravososKitsCampanhas consulta;

    public ConsultaItensMinimos(){}
    /**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        consulta = new ConsultaMinimosGravososKitsCampanhas(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_consulta_itens_minimos, /*container, false*/null);
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
        this.consulta.setSearchTypeMain(0);

        ((ListView) (getActivity().findViewById(R.id.liFcimProdutos))).setAdapter
                (
                        new ArrayAdapter<String>
                                (
                                        getActivity().getApplicationContext(),
                                        android.support.design.R.layout.support_simple_spinner_dropdown_item,
                                        this.consulta.loadData()
                                )
                );
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/

/*********************************END OF ITERFACES METHODS*****************************************/
}
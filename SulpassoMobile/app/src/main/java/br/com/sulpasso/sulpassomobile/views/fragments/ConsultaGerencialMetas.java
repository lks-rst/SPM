package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.ConsultaGerencial;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.views.ConsultasGerenciais;

/**
 * Created by Lucas on 27/02/2018 - 10:49 as part of the project SulpassoMobile.
 */
public class ConsultaGerencialMetas extends Fragment
{
    public ConsultaGerencialMetas(){}
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
        return inflater.inflate(R.layout.fragment_consulta_gerencial_metas, /*container, false*/null);
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
        br.com.sulpasso.sulpassomobile.views.fragments.Adapters.ConsultaGerencialMetas adapter;
        try
        {
            adapter = new br.com.sulpasso.sulpassomobile.views.fragments.Adapters.ConsultaGerencialMetas
                    (getActivity().getApplicationContext(),
                            ((ConsultasGerenciais) getActivity()).buscarListaMetas(),
                            ((ConsultasGerenciais) getActivity()).buscarMetaIdeal());
        }
        catch (Exception ex)
        {
            adapter = new br.com.sulpasso.sulpassomobile.views.fragments.Adapters.ConsultaGerencialMetas
                    (getActivity().getApplicationContext(),
                            ((ConsultasGerenciais)/*(Inicial)*/ getActivity()).buscarListaMetas(),
                            ((ConsultasGerenciais)/*(Inicial)*/ getActivity()).buscarMetaIdeal());
        }

        ((ListView) (getActivity().findViewById(R.id.liFcgMetas))).setAdapter(adapter);

        ((ListView) (getActivity().findViewById(R.id.liFcgMetas))).setOnItemClickListener(exibirMetas);

        ((EditText) (getActivity().findViewById(R.id.edFcgVda))).setText(((ConsultasGerenciais) getActivity()).buscarMetaTotal(0));

        ((EditText) (getActivity().findViewById(R.id.edFcgComis))).setText(((ConsultasGerenciais) getActivity()).buscarMetaTotal(1));

        ((EditText) (getActivity().findViewById(R.id.edFcgContrib))).setText(((ConsultasGerenciais) getActivity()).buscarMetaTotal(2));
/*

        ((ListView) (getActivity().findViewById(R.id.liFcgMetas))).setAdapter
        (
            new ArrayAdapter<String>
                (
                    getActivity().getApplicationContext(),
                    android.support.design.R.layout.support_simple_spinner_dropdown_item,
                    ((ConsultasGerenciais) getActivity()).buscarListaMetasS()
                )
        );
*/

    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
    AdapterView.OnItemClickListener exibirMetas = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            ((TextView) (getActivity().findViewById(R.id.edFcgPesom))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.PESO, ConsultaGerencial.META)));
            ((TextView) (getActivity().findViewById(R.id.edFcgClim))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.CLIENTE, ConsultaGerencial.META)));
            ((TextView) (getActivity().findViewById(R.id.edFcgFatm))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.FATURAMENTO, ConsultaGerencial.META)));
            ((TextView) (getActivity().findViewById(R.id.edFcgContm))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.CONTRIBUICAO, ConsultaGerencial.META)));

            ((TextView) (getActivity().findViewById(R.id.edFcgPesor))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.PESO, ConsultaGerencial.REALIZADO)));
            ((TextView) (getActivity().findViewById(R.id.edFcgClir))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.CLIENTE, ConsultaGerencial.REALIZADO)));
            ((TextView) (getActivity().findViewById(R.id.edFcgFatr))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.FATURAMENTO, ConsultaGerencial.REALIZADO)));
            ((TextView) (getActivity().findViewById(R.id.edFcgContr))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.CONTRIBUICAO, ConsultaGerencial.REALIZADO)));

            ((TextView) (getActivity().findViewById(R.id.edFcgPesop))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.PESO, ConsultaGerencial.PERCENTUAL)));
            ((TextView) (getActivity().findViewById(R.id.edFcgClip))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.CLIENTE, ConsultaGerencial.PERCENTUAL)));
            ((TextView) (getActivity().findViewById(R.id.edFcgFatp))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.FATURAMENTO, ConsultaGerencial.PERCENTUAL)));
            ((TextView) (getActivity().findViewById(R.id.edFcgContp))).
                    setText((((ConsultasGerenciais) getActivity()).
                    buscarMeta(position, ConsultaGerencial.CONTRIBUICAO, ConsultaGerencial.PERCENTUAL)));
        }
    };
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/

/*********************************END OF ITERFACES METHODS*****************************************/
}
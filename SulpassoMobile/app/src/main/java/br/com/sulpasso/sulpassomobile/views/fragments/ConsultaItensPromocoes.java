package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.ConsultaPromocoes;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;

/**
 * Created by Lucas on 18/11/2016 - 17:53 as part of the project SulpassoMobile.
 */
public class ConsultaItensPromocoes extends Fragment
{
    /*
        TODO: Criar um agrupamento do retorno pelo item da promoção;
     */
    private ConsultaPromocoes consulta;

    public ConsultaItensPromocoes(){}
/**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        consulta = new ConsultaPromocoes(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_consulta_itens_promocoes, /*container, false*/null);
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
        this.consulta.setSearchType(3);

        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item,
                this.consulta.buscarPromocoes());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ((ListView) (getActivity().findViewById(R.id.liFcipPromocoes))).setAdapter(adapter);
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/

/*********************************END OF ITERFACES METHODS*****************************************/
}
package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.views.ConsultasPedidos;

/**
 * Created by Lucas on 03/01/2017 - 14:45 as part of the project SulpassoMobile.
 */
public class ConsultaPedidosLista extends Fragment
{
/**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_consulta_pedidos_lista, /*container, false*/null);
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
        ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setAdapter(
                new ArrayAdapter<String>(getActivity().getApplicationContext(),
                        android.support.design.R.layout.support_simple_spinner_dropdown_item,
                        ((ConsultasPedidos) getActivity()).getControle().listarPedidos(0, "")));

        ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setOnItemClickListener(clickPedido);
    }


    public void listarItens(ArrayList<String> lista) throws GenercicException
    {
        if(getActivity().findViewById(R.id.frame_itens).getVisibility() == View.VISIBLE)
        {
            ((ListView) getActivity().findViewById(R.id.liAcpItens)).setAdapter
            (
                new ArrayAdapter<String>
                (
                    getActivity().getApplicationContext(),
                    android.support.design.R.layout.support_simple_spinner_dropdown_item,
                    lista
                )
            );
        }
        else
        {
            ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setAdapter
            (
                new ArrayAdapter<String>
                (
                    getActivity().getApplicationContext(),
                    android.support.design.R.layout.support_simple_spinner_dropdown_item,
                    lista
                )
            );
        }
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
    private AdapterView.OnItemClickListener clickPedido = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            getActivity().findViewById(R.id.frame_pedidos).setVisibility(View.GONE);
            getActivity().findViewById(R.id.frame_itens).setVisibility(View.VISIBLE);

            ((ListView) getActivity().findViewById(R.id.liAcpItens)).setAdapter
            (
                new ArrayAdapter<String>
                (
                    getActivity().getApplicationContext(),
                    android.support.design.R.layout.support_simple_spinner_dropdown_item,
                    ((ConsultasPedidos) getActivity()).getControle().listarItens(position)
                )
            );
        }
    };
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/

/*********************************END OF ITERFACES METHODS*****************************************/
/**************************************************************************************************/

/**************************************************************************************************/
}
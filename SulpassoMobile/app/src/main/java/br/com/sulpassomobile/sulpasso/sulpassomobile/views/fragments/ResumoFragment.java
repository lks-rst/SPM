package br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.Pedido;

/**
 * Created by Lucas on 03/10/2016.
 */
public class ResumoFragment extends Fragment
{
    private ListView fliSpnrItens;

    public ResumoFragment(){}
    /**********************************FRAGMENT LIFE CICLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_lista_itens_resumo, /*container, false*/null);
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
        if(!getActivity().getLocalClassName().equals("views.Pedido"))
        {
            throw new ClassCastException(getActivity().toString()
                    + " must be Pedido.class calss");
        }
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        ((EditText) getActivity().findViewById(R.id.flirEdtCliente)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCliente));
        ((EditText) getActivity().findViewById(R.id.flirEdtCidade)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCidade));
        ((EditText) getActivity().findViewById(R.id.flirEdtNaturesa)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtNaturesa));
        ((EditText) getActivity().findViewById(R.id.flirEdtTabela)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtTabela));
        ((EditText) getActivity().findViewById(R.id.flirEdtTipo)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtTipo));
        ((ListView) getActivity().findViewById(R.id.flirLiItens)).setAdapter
            (
                new ArrayAdapter<String>
                    (
                        getActivity().getApplicationContext(),
                        android.support.design.R.layout.support_simple_spinner_dropdown_item,
                        ((Pedido) getActivity()).listarResumo()
                    )
            );

    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
}

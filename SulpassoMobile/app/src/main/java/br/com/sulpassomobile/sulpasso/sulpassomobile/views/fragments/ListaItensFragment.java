package br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.Pedido;

/*
    Todo: Montar pesquisa dos itens;
    Todo: Montar forma de ordenação dos itens (fora do configurador);
 */

/**
 * Created by Lucas on 17/08/2016.
 */
public class ListaItensFragment extends Fragment
{
    private ListView fliSpnrItens;

    public ListaItensFragment(){}
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
        return inflater.inflate(R.layout.fragment_lista_itens_busca, /*container, false*/null);
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

        /*
        try { activity = (Pedido) getActivity();}
        catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString()
                    + " must be Pedido.class calss");
        }
        */
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        this.fliSpnrItens = (ListView) (getActivity().findViewById(R.id.flibLiItens));
        this.fliSpnrItens.setOnItemClickListener(selectingItems);
        this.listarItens();

        /*
        ((EditText) (getActivity().findViewById(R.id.flibEdtSearch)))
                .setText(String.valueOf(((Pedido) getActivity()).itensVendidos()));
        */
    }

    public void listarItens()
    {
        this.fliSpnrItens.setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                ((Pedido) getActivity()).listarItens(0, "")
            )
        );
    }

    public void ajustarLayout() { /*****/ }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
    private AdapterView.OnItemClickListener selectingItems = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
//            if(position > 0)
//            {
              ((Pedido) getActivity()).selecionarItem(position);
//                ((Pedido) getActivity()).selecionarItem(position - 1);
//                ((TextView) getActivity().findViewById(R.id.fliTxtDados)).setText(((Pedido) getActivity()).selecionarItem(position - 1));
//                ajustarLayout();
//            }
        }
    };

    private AdapterView.OnItemSelectedListener selectingItem = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            if(position > 0)
            {
                ((Pedido) getActivity()).selecionarItem(position - 1);
//                ((TextView) getActivity().findViewById(R.id.fliTxtDados)).setText(((Pedido) getActivity()).selecionarItem(position - 1));
//                ajustarLayout();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { /******/ }
    };
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
}

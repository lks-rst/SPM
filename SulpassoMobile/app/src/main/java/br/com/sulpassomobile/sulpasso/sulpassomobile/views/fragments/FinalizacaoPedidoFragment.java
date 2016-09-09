package br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.Pedido;

/*
    Todo: Montar tela de aresentação das promoções (campanhas e tabloides);
    Todo: Verificar valor minimo de venda;
    Todo: Verificar se há mix ideal / cobertura multipla esperada para o cliente;
 */
/**
 * Created by Lucas on 17/08/2016.
 */
public class FinalizacaoPedidoFragment extends Fragment
{
    private Pedido activity;

    public FinalizacaoPedidoFragment(){}
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
        return inflater.inflate(R.layout.fragment_finalizacao_pedido, /*container, false*/null);
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
        else { ((Pedido) getActivity()).indicarToatalPedido(); }
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        ((EditText) (getActivity().findViewById(R.id.ffpEdtTotalItens)))
                .setText(String.valueOf(((Pedido) getActivity()).valorVendido()));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtTotalPedido)))
                .setText(String.valueOf(((Pedido) getActivity()).valorVendido()));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtDesconto))).addTextChangedListener(recalcularTotal);
    }

    public void listarClientes()
    {
        /*try
        {
            this.listItens.setAdapter(new DistribuicaoItensAdapter(
                    this.mCallback.listarClientes(),
                    getActivity().getApplication().getApplicationContext()));

            this.listClientes.setClickable(this.listClientes.getSelectedItemPosition() == 0);
            this.listClientes.setEnabled(this.listClientes.getSelectedItemPosition() == 0);
            this.edtValor.setText(this.mCallback.getValor());
        }
        catch (Exception e){ e.printStackTrace(); }*/
    }

    public void ajustarLayout(String valor)
    {
        ((EditText) (getActivity().findViewById(R.id.ffpEdtTotalPedido))).setText(valor);
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
    private TextWatcher recalcularTotal = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.toString().length() > 0 && s.toString().indexOf('.') != (s.toString().length() - 1))
                ((Pedido) getActivity()).indicarDescontoPedido(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) { /*****/ }
    };
}
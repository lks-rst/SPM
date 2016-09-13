package br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.Pedido;

/*
    Todo: Montar corretamente a tela com os devidos campos para digitação dos dados do item;
    Todo: Apresenta os dados de venda do item na tela;
    Todo: Criar função para verificação do saldo / contribuição;
    Todo: Criar função de solicitação de senha;
 */

/**
 * Created by Lucas on 17/08/2016.
 */
public class DigitacaoItemFragment extends Fragment
{
    public DigitacaoItemFragment(){}
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
        return inflater.inflate(R.layout.fragment_digitacao, /*container, false*/null);
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
        ((EditText) (getActivity().findViewById(R.id.fdEdtQuantidade)))
                .addTextChangedListener(digitacaoQuantidade);
        ((EditText) (getActivity().findViewById(R.id.fdEdtValor)))
                .addTextChangedListener(digitacaoValor);
        ((EditText) (getActivity().findViewById(R.id.fdEdtDesconto)))
                .addTextChangedListener(digitacaoDesconto);
        ((EditText) (getActivity().findViewById(R.id.fdEdtAcrescimo)))
                .addTextChangedListener(digitacaoAcrescimo);

        ((TextView) (getActivity().findViewById(R.id.fdTxtDados)))
                .setText(((Pedido) getActivity()).getItem());
        ((TextView) (getActivity().findViewById(R.id.fdTxtValor)))
                .setText(((Pedido) getActivity()).getValor());

        ((EditText) (getActivity().findViewById(R.id.fdEdtValor)))
                .setText(((Pedido) getActivity()).getValor());

        ((EditText) (getActivity().findViewById(R.id.fdEdtAcrescimo)))
                .setHint("0");
        ((EditText) (getActivity().findViewById(R.id.fdEdtDesconto)))
                .setHint("0");

        ((EditText) (getActivity().findViewById(R.id.fdEdtValor)))
                .setEnabled(((Pedido) getActivity()).alteraValor("v"));
        ((EditText) (getActivity().findViewById(R.id.fdEdtAcrescimo)))
                .setEnabled(!(((Pedido) getActivity()).alteraValor("d")));
        ((EditText) (getActivity().findViewById(R.id.fdEdtDesconto)))
                .setEnabled(!(((Pedido) getActivity()).alteraValor("a")));
    }

    private void exibirTotal()
    {
        ((TextView) (getActivity().findViewById(R.id.fdTxtTotal)))
                .setText(((Pedido) getActivity()).calcularTotalItem());
    }
/**************************************************************************************************/
/*****************************   END OF FRAGMENT FUNCTIONAL METHODS   *****************************/
/**************************************************************************************************/
/*****************************     CLICK LISTENERS FOR THE UI         *****************************/
/**************************************************************************************************/
    private TextWatcher digitacaoQuantidade = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.toString().length() > 0 && s.toString().indexOf('.') != (s.toString().length() - 1))
                ((Pedido) getActivity()).digitarQuantidade(s.toString());

            exibirTotal();
        }

        @Override
        public void afterTextChanged(Editable s) { /*****/ }
    };

    private TextWatcher digitacaoValor = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.toString().length() > 0 && s.toString().indexOf('.') != (s.toString().length() - 1))
                ((Pedido) getActivity()).digitarValor(s.toString());

            exibirTotal();
        }

        @Override
        public void afterTextChanged(Editable s) { /*****/ }
    };

    private TextWatcher digitacaoDesconto = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.toString().length() > 0 && s.toString().indexOf('.') != (s.toString().length() - 1))
                ((Pedido) getActivity()).digitarDesconto(s.toString());

            exibirTotal();
        }

        @Override
        public void afterTextChanged(Editable s) { /*****/ }
    };

    private TextWatcher digitacaoAcrescimo = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.toString().length() > 0 && s.toString().indexOf('.') != (s.toString().length() - 1))
                ((Pedido) getActivity()).digitarAcrescimo(s.toString());

            exibirTotal();
        }

        @Override
        public void afterTextChanged(Editable s) { /*****/ }
    };
/**************************************************************************************************/
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/**************************************************************************************************/
}

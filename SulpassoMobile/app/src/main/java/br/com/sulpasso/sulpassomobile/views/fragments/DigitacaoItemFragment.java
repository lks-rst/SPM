package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.views.Pedido;

/*
    Todo: Criar função para verificação do saldo / contribuição;
    Todo: Criar função de solicitação de senha;
    Todo: Carregar PPC na inicialização do fragmento;
    Todo: Carregar contribuição;
 */

/**
 * Created by Lucas on 17/08/2016.
 */
public class DigitacaoItemFragment extends Fragment
{
    public DigitacaoItemFragment(){}
/**************************************************************************************************/
/**********************************FRAGMENT LIFE CICLE*********************************************/
/**************************************************************************************************/
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
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
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
        if(!getActivity().getLocalClassName().equals("br.com.sulpasso.sulpassomobile.views.Pedido"))
        {
            throw new ClassCastException(getActivity().toString()
                    + " must be Pedido.class calss");
        }
    }
/**************************************************************************************************/
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************FRAGMENT ACCESS METHODS******************************************/
/**************************************************************************************************/
    public void indicarMinimo(String valor)
    {
        ((EditText) (getActivity().findViewById(R.id.fdEdtValor))).setText(valor);
    }
/**************************************************************************************************/
/******************************END OF FRAGMENT ACCESS METHODS**************************************/
/**************************************************************************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
/**************************************************************************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        /*
        Inserção dos valores nos campos
         */
        ((EditText) (getActivity().findViewById(R.id.fdEdtValor)))
                .setText("0");

        ((EditText) (getActivity().findViewById(R.id.fdEdtValor)))
                .setText("0");
        ((TextView) (getActivity().findViewById(R.id.fdEdtDados)))
                .setText(((Pedido) getActivity()).getItem());
        ((EditText) (getActivity().findViewById(R.id.fdEdtUnidade)))
                .setText(((Pedido) getActivity()).getUnidade());
        ((EditText) (getActivity().findViewById(R.id.fdEdtUnVda)))
                .setText(((Pedido) getActivity()).getUnidadeVenda());
        ((EditText) (getActivity().findViewById(R.id.fdEdtQtdVda)))
                .setText(((Pedido) getActivity()).getQtdMinimaVenda());
        ((EditText) (getActivity().findViewById(R.id.fdEdtBarras)))
                .setText(((Pedido) getActivity()).getCodigoBarras());
        ((EditText) (getActivity().findViewById(R.id.fdEdtQtdCaixa)))
                .setText(((Pedido) getActivity()).getQtdCaixa());
        ((EditText) (getActivity().findViewById(R.id.fdEdtUnitario)))
                .setText(((Pedido) getActivity()).getValorUnitario());
        ((EditText) (getActivity().findViewById(R.id.fdEdtMkp)))
                .setText(((Pedido) getActivity()).getMarkup());
        ((EditText) (getActivity().findViewById(R.id.fdEdtAcrescimo)))
                .setHint("0");
        ((EditText) (getActivity().findViewById(R.id.fdEdtDesconto)))
                .setHint("0");

        /*
        Indicação de calbacks de escuta a alteração de valores nos campos
         */
        ((EditText) (getActivity().findViewById(R.id.fdEdtQuantidade)))
                .addTextChangedListener(digitacaoQuantidade);
        ((EditText) (getActivity().findViewById(R.id.fdEdtValor)))
                .addTextChangedListener(digitacaoValor);
        ((EditText) (getActivity().findViewById(R.id.fdEdtDesconto)))
                .addTextChangedListener(digitacaoDesconto);
        ((EditText) (getActivity().findViewById(R.id.fdEdtAcrescimo)))
                .addTextChangedListener(digitacaoAcrescimo);
        ((EditText) (getActivity().findViewById(R.id.fdEdtMkp)))
                .addTextChangedListener(alteracaoMkp);


        /******************************************************************************************/
        ((EditText) (getActivity().findViewById(R.id.fdEdtValor)))
                .setText(((Pedido) getActivity()).getValor());
        /******************************************************************************************/

        /*
        Bloqueio ou liberação de campos da tela de acordo com as configurações da venda
         */
        ((getActivity().findViewById(R.id.fdEdtValor)))
                .setEnabled(((Pedido) getActivity()).alteraValor("v"));
        ((getActivity().findViewById(R.id.fdEdtAcrescimo)))
                .setEnabled((((Pedido) getActivity()).alteraValor("a")));
        ((getActivity().findViewById(R.id.fdEdtDesconto)))
                .setEnabled((((Pedido) getActivity()).alteraValor("d")));

        ((LinearLayout) ((EditText) (getActivity().findViewById(R.id.fdEdtAcrescimo))).getParent())
                .setVisibility(((((Pedido) getActivity()).alteraValor("a"))) ? View.VISIBLE : View.GONE);
        ((LinearLayout) ((EditText) (getActivity().findViewById(R.id.fdEdtDesconto))).getParent())
                .setVisibility(((((Pedido) getActivity()).alteraValor("d"))) ? View.VISIBLE : View.GONE);

        ((getActivity().findViewById(R.id.fdBtnMinimos)))
                .setEnabled(((Pedido) getActivity()).temValorMinimo());
        ((getActivity().findViewById(R.id.fdBtnPromocoes)))
                .setEnabled(((Pedido) getActivity()).temPromocao());
        ((getActivity().findViewById(R.id.fdBtnMinimos)))
                .setClickable(((Pedido) getActivity()).temValorMinimo());
        ((getActivity().findViewById(R.id.fdBtnPromocoes)))
                .setClickable(((Pedido) getActivity()).temPromocao());

        ((getActivity().findViewById(R.id.fdBtnValorTabela)))
                .setVisibility(((((Pedido) getActivity()).alteraValor("v"))) ? View.VISIBLE : View.GONE);
        ((getActivity().findViewById(R.id.fdBtnMinimos)))
                .setVisibility(((((Pedido) getActivity()).temValorMinimo())) ? View.VISIBLE : View.GONE);
        ((Button) (getActivity().findViewById(R.id.fdBtnPromocoes)))
                .setVisibility(((((Pedido) getActivity()).temPromocao())) ? View.VISIBLE : View.GONE);

        ((EditText) (getActivity().findViewById(R.id.fdEdtCnt))).setText(
                (((Pedido) getActivity()).calculoContribuicao(Float.parseFloat(
                ((EditText) (getActivity().findViewById(R.id.fdEdtValor))).getText().toString()))));

        ((EditText) (getActivity().findViewById(R.id.fdEdtPpc))).setText(
            (((Pedido) getActivity()).calcularPpc(
                ((EditText) (getActivity().findViewById(R.id.fdEdtValor))).getText().toString(),
                ((EditText) (getActivity().findViewById(R.id.fdEdtMkp))).getText().toString(), "0")));

    }

    private void exibirTotal()
    {
        ((TextView) (getActivity().findViewById(R.id.fdEdtTotal)))
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
        public void afterTextChanged(Editable s)
        {
            boolean vk = ((Pedido) getActivity()).vendaKilo();

            if(vk)
            {

            }
            else
            {
                String t = ((EditText) (getActivity().findViewById(R.id.fdEdtQuantidade)))
                        .getText().toString();

                if(t.length() > 0 && t.indexOf('.') == (t.length() - 1))
                {
                    t = t.substring(0, t.length() - 1);
                    ((EditText) (getActivity().findViewById(R.id.fdEdtQuantidade))).setText(t);
                }
            }
        }
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

    private TextWatcher alteracaoMkp = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.toString().length() > 0 && s.toString().indexOf('.') != (s.toString().length() - 1))
            {
                ((EditText) (getActivity().findViewById(R.id.fdEdtPpc)))
                    .setText(((Pedido) getActivity()).calcularPPC
                    (
                        ((EditText) (getActivity().findViewById(R.id.fdEdtMkp))).getText().toString(),
                        ((EditText) (getActivity().findViewById(R.id.fdEdtValor))).getText().toString()
                    ));
            }
        }

        @Override
        public void afterTextChanged(Editable s) { /*****/ }
    };
/**************************************************************************************************/
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/**************************************************************************************************/
}
package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.views.Pedido;

/*
    Todo: Montar tela de aresentação das promoções (campanhas e tabloides [montar da mesma maneira que esta no sistema (a cada item durante a digitação)]);
    Todo: Verificar se há mix ideal / cobertura multipla esperada para o cliente;
 */
/**
 * Created by Lucas on 17/08/2016.
 */
public class FinalizacaoPedidoFragment extends Fragment
{
    private Spinner ffpSpnrNaturezas;
    private Spinner ffpSpnrPrazos;
    private Spinner ffpSpnrJustificativa;

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
        if(!getActivity().getLocalClassName().equals("br.com.sulpasso.sulpassomobile.views.Pedido"))
        {
            throw new ClassCastException(getActivity().toString()
                    + " must be Pedido.class calss");
        }
        else { ((Pedido) getActivity()).indicarToatalPedido(); }
    }
/**************************************************************************************************/
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************FRAGMENT ACCESS METHODS******************************************/
/**************************************************************************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        /*
        Inserção dos valores nos campos
         */
        ((EditText) (getActivity().findViewById(R.id.ffpEdtTotalItens)))
            .setText(String.valueOf(((Pedido) getActivity()).valorVendido()));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtTotalPedido)))
            .setText(String.valueOf(((Pedido) getActivity()).valorVendido()));

        ((EditText) (getActivity().findViewById(R.id.ffpEdtDesconto))).setText(String.valueOf(0));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtFrete))).setText(String.valueOf(0));

        ((EditText) (getActivity().findViewById(R.id.ffpEdtCliente)))
            .setText(String.valueOf(((Pedido) getActivity()).cabecahoPedido(R.id.ffpEdtCliente)));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtCidade)))
            .setText(String.valueOf(((Pedido) getActivity()).cabecahoPedido(R.id.ffpEdtCidade)));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtTab)))
            .setText(String.valueOf(((Pedido) getActivity()).cabecahoPedido(R.id.ffpEdtTab)));

        this.ffpSpnrNaturezas = (Spinner) (getActivity().findViewById(R.id.ffpSpnrNaturezas));
        this.ffpSpnrPrazos = (Spinner) (getActivity().findViewById(R.id.ffpSpnrPrazos));
        this.ffpSpnrJustificativa = (Spinner) (getActivity().findViewById(R.id.ffpSpnrJustificativa));

        this.ffpSpnrNaturezas.setAdapter
        (
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item
                ,((Pedido) getActivity()).listarNaturezas(false))
        );

        this.ffpSpnrNaturezas.setSelection(((Pedido) getActivity()).buscarNatureza());

        this.ffpSpnrNaturezas.setOnItemSelectedListener(selectingData);
        this.ffpSpnrPrazos.setOnItemSelectedListener(selectingData);

        this.ffpSpnrNaturezas.setClickable(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrNaturezas));
        this.ffpSpnrNaturezas.setEnabled(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrNaturezas));
        this.ffpSpnrPrazos.setClickable(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrPrazos));
        this.ffpSpnrPrazos.setEnabled(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrPrazos));

        /*
        Indicação de calbacks de escuta a alteração de valores nos campos
         */
        ((EditText) (getActivity().findViewById(R.id.ffpEdtDesconto)))
                .addTextChangedListener(recalcularTotal);
        ((EditText) (getActivity().findViewById(R.id.ffpEdtFrete)))
                .addTextChangedListener(recalcularFrete);

        ((EditText) (getActivity().findViewById(R.id.ffpEdtObsCpd)))
                .addTextChangedListener(observacoes);
        ((EditText) (getActivity().findViewById(R.id.ffpEdtObsNfe)))
                .addTextChangedListener(observacoes);

        this.ffpSpnrJustificativa.setOnItemSelectedListener(selectingData);

        ((LinearLayout) (getActivity().findViewById(R.id.ffpEdtDesconto)).getParent()).setVisibility
            (((((Pedido) getActivity()).alteraValorFim(R.id.ffpEdtDesconto))) ? View.VISIBLE : View.GONE);
        ((LinearLayout) (getActivity().findViewById(R.id.ffpEdtFrete)).getParent()).setVisibility
            (((((Pedido) getActivity()).alteraValorFim(R.id.ffpEdtFrete))) ? View.VISIBLE : View.GONE);
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

    public void ajustarPrazos(int posicao)
    {
        this.ffpSpnrPrazos.setAdapter(
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                ((Pedido) getActivity()).listarPrazos(posicao)));

        this.ffpSpnrPrazos.setSelection(((Pedido) getActivity()).buscarPrazo());
        this.ffpSpnrPrazos.setOnItemSelectedListener(selectingData);

        this.ffpSpnrPrazos.setClickable(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrPrazos));
        this.ffpSpnrPrazos.setEnabled(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrPrazos));
    }

    public void bloquearClicks()
    {
        this.ffpSpnrNaturezas.setClickable(((Pedido) getActivity()).permitirClick(R.id.fdcSpnrNaturezas));
        this.ffpSpnrNaturezas.setEnabled(((Pedido) getActivity()).permitirClick(R.id.fdcSpnrNaturezas));
        this.ffpSpnrPrazos.setClickable(((Pedido) getActivity()).permitirClick(R.id.fdcSpnrPrazos));
        this.ffpSpnrPrazos.setEnabled(((Pedido) getActivity()).permitirClick(R.id.fdcSpnrPrazos));
    }
/**************************************************************************************************/
/******************************END OF FRAGMENT ACCESS METHODS**************************************/
/**************************************************************************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
/**************************************************************************************************/

/**************************************************************************************************/
/*****************************   END OF FRAGMENT FUNCTIONAL METHODS   *****************************/
/**************************************************************************************************/
/*****************************     CLICK LISTENERS FOR THE UI         *****************************/
/**************************************************************************************************/
    private TextWatcher observacoes = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /***/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { /***/ }

        @Override
        public void afterTextChanged(Editable s)
        {
            if((getActivity().findViewById(R.id.ffpEdtObsCpd)).hasFocus())
            {
                ((Pedido) getActivity()).acrescentarObservacao(s.toString(), 1);
            }
            else if((getActivity().findViewById(R.id.ffpEdtObsNfe)).hasFocus())
            {
                ((Pedido) getActivity()).acrescentarObservacao(s.toString(), 2);
            }
        }
    };

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

    private TextWatcher recalcularFrete = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.toString().length() > 0 && s.toString().indexOf('.') != (s.toString().length() - 1))
                ((Pedido) getActivity()).indicarFretePedido(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) { /*****/ }
    };

    private AdapterView.OnItemSelectedListener selectingData = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (parent.getId())
            {
                case R.id.ffpSpnrNaturezas :
                    ajustarPrazos(position);
                    break;
                case R.id.ffpSpnrPrazos :
                    /*
                    ((EditText) getActivity().findViewById(R.id.fdcEdtTabela))
                        .setText(String.format(((Pedido) getActivity()).getApplicationContext()
                        .getResources().getString(R.string.str_tab), String.valueOf(String
                        .valueOf(((Pedido) getActivity()).selecionarPrazo(position)))));
                    ((EditText) getActivity().findViewById(R.id.fdcEdtPrazo)).setText(String.format
                        (((Pedido) getActivity()).getApplicationContext().getResources()
                        .getString(R.string.str_prazo), ((Pedido) getActivity()).selecionarPrazo()));
                    */
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { /******/ }
    };
/**************************************************************************************************/
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/**************************************************************************************************/
}
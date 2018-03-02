package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;
import br.com.sulpasso.sulpassomobile.views.ConsultasGerenciais;

/**
 * Created by Lucas on 27/02/2018 - 10:50 as part of the project SulpassoMobile.
 */
public class ConsultaGerencialGraficos extends Fragment
{
    public ConsultaGerencialGraficos(){}
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
        return inflater.inflate(R.layout.fragment_consulta_gerencial_graficos, /*container, false*/null);
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
        ((TextView) ((getActivity().findViewById(R.id.graficos_total_pedidos_1)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(1, 1));
        ((TextView) ((getActivity().findViewById(R.id.graficos_total_pedidos_2)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(1, 2));
        ((TextView) ((getActivity().findViewById(R.id.graficos_total_pedidos_3)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(1, 3));

        ((TextView) ((getActivity().findViewById(R.id.graficos_clientes_1)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(2, 1));
        ((TextView) ((getActivity().findViewById(R.id.graficos_clientes_2)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(2, 2));
        ((TextView) ((getActivity().findViewById(R.id.graficos_clientes_3)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(2, 3));

        ((TextView) ((getActivity().findViewById(R.id.graficos_ped_med_1)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(3, 1));
        ((TextView) ((getActivity().findViewById(R.id.graficos_ped_med_2)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(3, 2));
        ((TextView) ((getActivity().findViewById(R.id.graficos_ped_med_3)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(3, 3));

        ((TextView) ((getActivity().findViewById(R.id.graficos_med_itens_1)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(4, 1));
        ((TextView) ((getActivity().findViewById(R.id.graficos_med_itens_2)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(4, 2));
        ((TextView) ((getActivity().findViewById(R.id.graficos_med_itens_3)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(4, 3));

        ((TextView) ((getActivity().findViewById(R.id.graficos_frequencia_1)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(5, 1));
        ((TextView) ((getActivity().findViewById(R.id.graficos_frequencia_2)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(5, 2));
        ((TextView) ((getActivity().findViewById(R.id.graficos_frequencia_3)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(5, 3));

        ((TextView) ((getActivity().findViewById(R.id.graficos_acelerador_1)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(6, 1));
        ((TextView) ((getActivity().findViewById(R.id.graficos_acelerador_2)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(6, 2));
        ((TextView) ((getActivity().findViewById(R.id.graficos_acelerador_3)))).
                setWidth(((ConsultasGerenciais) getActivity()).percentualGrafico(6, 3));
        /******************************************************************************************/
        ((TextView) ((getActivity().findViewById(R.id.graficos_total_pedidos_1)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(1, 1)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_total_pedidos_2)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(1, 2)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_total_pedidos_3)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(1, 3)));

        ((TextView) ((getActivity().findViewById(R.id.graficos_clientes_1)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(2, 1)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_clientes_2)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(2, 2)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_clientes_3)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(2, 3)));

        ((TextView) ((getActivity().findViewById(R.id.graficos_ped_med_1)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(3, 1)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_ped_med_2)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(3, 2)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_ped_med_3)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(3, 3)));

        ((TextView) ((getActivity().findViewById(R.id.graficos_med_itens_1)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(4, 1)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_med_itens_2)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(4, 2)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_med_itens_3)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(4, 3)));

        ((TextView) ((getActivity().findViewById(R.id.graficos_frequencia_1)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(5, 1)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_frequencia_2)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(5, 2)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_frequencia_3)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(5, 3)));

        ((TextView) ((getActivity().findViewById(R.id.graficos_acelerador_1)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(6, 1)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_acelerador_2)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(6, 2)));
        ((TextView) ((getActivity().findViewById(R.id.graficos_acelerador_3)))).
                setText(Formatacao.format2d(((ConsultasGerenciais) getActivity()).percentualGrafico(6, 3)));
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/

/*********************************END OF ITERFACES METHODS*****************************************/
}
package br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.Inicial;

/**
 * Created by Lucas on 12/12/2016 - 10:40 as part of the project SulpassoMobile.
 */
public class InicialCliente extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicial_cliente, null);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        try{ this.setUpLayout(); }
        catch (Exception e) { }
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
        ((TextView) (getActivity().findViewById(R.id.txtFicNome)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicNome)));
        ((TextView) (getActivity().findViewById(R.id.txtFicSecond)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicSecond)));
        ((TextView) (getActivity().findViewById(R.id.txtFicFone)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicFone)));
        ((TextView) (getActivity().findViewById(R.id.txtFicMail)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicMail)));
        ((TextView) (getActivity().findViewById(R.id.txtFicWeb)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicWeb)));
        ((TextView) (getActivity().findViewById(R.id.txtFicVendedor)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.txtFicVendedor)));
        /*
            TODO: Esse campo esta retornando em branco pois não esta sendo carregado os dados do vendedor no controle da tela inicial
         */
    }
}
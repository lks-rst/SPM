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
 * Created by Lucas on 12/12/2016 - 10:39 as part of the project SulpassoMobile.
 */
public class IncialDesenvolvedor extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_inicial_desenv, null);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        this.setUpLayout();
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
        /*
            TODO: Renomear os campos dos layouts dos fragmentos da tela inicial.
         */
        ((TextView) (getActivity().findViewById(R.id.tela_inicial_txt_validade)))
                .setText(String.valueOf(((Inicial) getActivity()).desenvolvedor(R.id.tela_inicial_txt_validade)));
        ((TextView) (getActivity().findViewById(R.id.tela_inicial_txt_versao)))
                .setText(String.valueOf(((Inicial) getActivity()).desenvolvedor(R.id.tela_inicial_txt_versao)));

    }

}
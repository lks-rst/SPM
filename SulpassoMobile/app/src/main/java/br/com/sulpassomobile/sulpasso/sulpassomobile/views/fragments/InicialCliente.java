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
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_inicial_cliente, null);

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
        ((TextView) (getActivity().findViewById(R.id.tela_inicial_txt_emp_nome)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.tela_inicial_txt_emp_nome)));
        ((TextView) (getActivity().findViewById(R.id.tela_inicial_txt_emp_sub)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.tela_inicial_txt_emp_sub)));
        ((TextView) (getActivity().findViewById(R.id.tela_inicial_txt_emp_fone)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.tela_inicial_txt_emp_fone)));
        ((TextView) (getActivity().findViewById(R.id.tela_inicial_txt_emp_mail)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.tela_inicial_txt_emp_mail)));
        ((TextView) (getActivity().findViewById(R.id.tela_inicial_txt_emp_page)))
                .setText(String.valueOf(((Inicial) getActivity()).dadosEmpresaCliente(R.id.tela_inicial_txt_emp_page)));

    }

}
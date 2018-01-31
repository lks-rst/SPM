package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.views.Inicial;

/**
 * Created by Lucas on 12/12/2016 - 10:39 as part of the project SulpassoMobile.
 */
public class IncialDesenvolvedor extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_inicial_desenv, null);

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        try{ this.setUpLayout(); }
        catch (Exception e) { }
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
        ((TextView) (getActivity().findViewById(R.id.fidTxtValidade)))
                .setText(String.valueOf(((Inicial) getActivity()).desenvolvedor(R.id.fidTxtValidade)));
        ((TextView) (getActivity().findViewById(R.id.fidTxtVersao)))
                .setText(String.valueOf(((Inicial) getActivity()).desenvolvedor(R.id.fidTxtVersao)));
    }

    public void reloadFields() { this.setUpLayout(); }
}
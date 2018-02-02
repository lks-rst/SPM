package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.interfaces.ConsultaClientes;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.views.ConsultasClientes;

/**
 * Created by Lucas on 24/11/2016 - 15:11 as part of the project SulpassoMobile.
 */
public class ConsultaClientesPositivacao extends Fragment
{
    private ConsultaClientes callback;

    public ConsultaClientesPositivacao(){}
/**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.callback = null;
        try
        {
            this.callback = (ConsultaClientes) getTargetFragment();
            //return inflater.inflate(R.layout.fragment_consulta_clientes_positivacaot, /*container, false*/null);
            return inflater.inflate(R.layout.fragment_consulta_clientes_positivacaot, /*container, false*/null);
        }
        catch (ClassCastException e)
        {
            Log.e(this.getClass().getSimpleName(), "Callback ConsultaClientes must be implemented by target fragment!", e);
            throw e;
        }
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
        getActivity().findViewById(R.id.semana).setOnClickListener(alteracaoStatus);
        getActivity().findViewById(R.id.mez).setOnClickListener(alteracaoStatus);
        getActivity().findViewById(R.id.positivados).setOnClickListener(alteracaoStatus);
        getActivity().findViewById(R.id.n_positivados).setOnClickListener(alteracaoStatus);

        try { this.listarItens(); }
        catch (GenercicException e) { e.printStackTrace(); }
    }

    public void listarItens() throws GenercicException
    {
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item,
                ((ConsultasClientes) getActivity()).buscarListaPositivacao(
                        ((CheckBox) getActivity().findViewById(R.id.semana)).isChecked(),
                        ((CheckBox) getActivity().findViewById(R.id.positivados)).isChecked()));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ((ListView) (getActivity().findViewById(R.id.liFccpClientes))).setAdapter(adapter);

        ((EditText) getActivity().findViewById(R.id.n_clientes)).
                setText(((ConsultasClientes) getActivity()).totalPositivacao());
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
private View.OnClickListener alteracaoStatus = new View.OnClickListener()
{
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.semana:
                if(!((CheckBox) getActivity().findViewById(R.id.semana)).isChecked())
                {
                    ((CheckBox) getActivity().findViewById(R.id.semana)).setChecked(true);
                }
                else { ((CheckBox) getActivity().findViewById(R.id.mez)).setChecked(false); }
                break;

            case R.id.mez:
                if(!((CheckBox) getActivity().findViewById(R.id.mez)).isChecked())
                {
                    ((CheckBox) getActivity().findViewById(R.id.mez)).setChecked(true);
                }
                else { ((CheckBox) getActivity().findViewById(R.id.semana)).setChecked(false); }
                break;

            case R.id.positivados:
                if(!((CheckBox) getActivity().findViewById(R.id.positivados)).isChecked())
                {
                    ((CheckBox) getActivity().findViewById(R.id.positivados)).setChecked(true);
                }
                else { ((CheckBox) getActivity().findViewById(R.id.n_positivados)).setChecked(false); }
                break;

            case R.id.n_positivados:
                if(!((CheckBox) getActivity().findViewById(R.id.n_positivados)).isChecked())
                {
                    ((CheckBox) getActivity().findViewById(R.id.n_positivados)).setChecked(true);
                }
                else { ((CheckBox) getActivity().findViewById(R.id.positivados)).setChecked(false); }
                break;
        }

        try { listarItens(); }
        catch (GenercicException e) { e.printStackTrace(); }
    }
};
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/

/*********************************END OF ITERFACES METHODS*****************************************/
}
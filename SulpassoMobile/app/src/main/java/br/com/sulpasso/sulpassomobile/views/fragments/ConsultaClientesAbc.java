package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.interfaces.ConsultaClientes;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.CurvaAbc;
import br.com.sulpasso.sulpassomobile.views.ConsultasClientes;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.ConsultaAbc;

/**
 * Created by Lucas on 24/11/2016 - 15:11 as part of the project SulpassoMobile.
 */
public class ConsultaClientesAbc extends Fragment implements  ConsultaAbc.Callback
{
    private ConsultaClientes callback;

    private int posicaoCLiente;

    public ConsultaClientesAbc(){}
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
        this.callback = null;
        try
        {
            this.callback = (ConsultaClientes) getTargetFragment();
            //return inflater.inflate(R.layout.fragment_consulta_clientes_abc, /*container, false*/null);
            return inflater.inflate(R.layout.fragment_consulta_clientes_listas, /*container, false*/null);
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
        ((ListView) (getActivity().findViewById(R.id.liFcclSimples))).setOnItemClickListener(selecionadoCliente);

        try { listarItens(); }
        catch (GenercicException e) { e.printStackTrace(); }
    }

    public void listarItens() throws GenercicException
    {
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item,
                ((ConsultasClientes) getActivity()).buscarListaClientes());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ((ListView) (getActivity().findViewById(R.id.liFcclSimples))).setAdapter(adapter);
    }

    private void apresentarDialog()
    {
        ConsultaAbc dialog = new ConsultaAbc();
        dialog.setTargetFragment(this, 1); //request code
        dialog.show(getFragmentManager(), "DIALOG");
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/

    private AdapterView.OnItemClickListener selecionadoCliente = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            posicaoCLiente = position;
            apresentarDialog();
        }
    };

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/

/*********************************END OF ITERFACES METHODS*****************************************/
    @Override
    public CurvaAbc abcCliente() { return ((ConsultasClientes) getActivity()).detalharAbc(posicaoCLiente); }
}
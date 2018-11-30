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
import android.widget.Toast;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.interfaces.ConsultaClientes;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.views.ConsultasClientes;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.DetalhesPrepedido;

/**
 * Created by Lucas on 24/11/2016 - 15:12 as part of the project SulpassoMobile.
 */
public class ConsultaClientesPrePedido extends Fragment implements DetalhesPrepedido.Callback
{
    private ConsultaClientes callback;

    private int posicaoCLiente;

    public ConsultaClientesPrePedido(){}
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
        try
        {
            listarItens();
            ((ListView) (getActivity().findViewById(R.id.liFcclSimples)))
                    .setOnItemClickListener(selecionadoCliente);
        }
        catch (GenercicException e) { e.printStackTrace(); }
    }

    public void listarItens() throws GenercicException
    {
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item,
                ((ConsultasClientes) getActivity()).buscarListaPrePedido());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ((ListView) (getActivity().findViewById(R.id.liFcclSimples))).setAdapter(adapter);
    }

    private void apresentarDialog()
    {
        DetalhesPrepedido dialog = new DetalhesPrepedido();
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
    @Override
    public PrePedido detalhesPrePedido()
    {
        return ((ConsultasClientes) getActivity()).detalharPrePedido(this.posicaoCLiente);
    }

    @Override
    public void exibirDescricaoProduto(int posicao)
    {
        String descricao = ((ConsultasClientes) getActivity()).buscarDescricaoItem(posicao);
        Toast.makeText(getActivity().getApplicationContext(), "CHAMADO PELA ALERT DE PRE_PEDIDO --- \n" + descricao, Toast.LENGTH_LONG).show();
    }
/*********************************END OF ITERFACES METHODS*****************************************/
}
package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.views.Pedido;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.AlterarExcluir;

/**
 * Created by Lucas on 03/10/2016.
 */
public class ResumoFragment extends Fragment implements AlterarExcluir.Callback
{
    private ListView fliSpnrItens;

    private int posicaoAlterar;

    public ResumoFragment(){}
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
        return inflater.inflate(R.layout.fragment_lista_itens_resumo, /*container, false*/null);
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
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        ((EditText) getActivity().findViewById(R.id.flirEdtCliente)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCliente));
        ((EditText) getActivity().findViewById(R.id.flirEdtCidade)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCidade));
        ((EditText) getActivity().findViewById(R.id.flirEdtNaturesa)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtNaturesa));
        ((EditText) getActivity().findViewById(R.id.flirEdtTabela)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtTabela));
        ((EditText) getActivity().findViewById(R.id.flirEdtTipo)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtTipo));
        ((ListView) getActivity().findViewById(R.id.flirLiItens)).setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                    R.layout.default_list_item,
                ((Pedido) getActivity()).listarResumo()
            )
        );
        ((ListView) getActivity().findViewById(R.id.flirLiItens)).setOnItemClickListener(selectingItems);
        ((EditText) getActivity().findViewById(R.id.flirEdtItens)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtItens));
        ((EditText) getActivity().findViewById(R.id.flirEdtValor)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtValor));
        ((EditText) getActivity().findViewById(R.id.flirEdtVolume)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtVolume));
        ((EditText) getActivity().findViewById(R.id.flirEdtCont)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCont));
    }

    private void apresentarDialog()
    {
        AlterarExcluir dialog = new AlterarExcluir();
        dialog.setTargetFragment(this, 1); //request code
        dialog.show(getFragmentManager(), "DIALOG");
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
    private AdapterView.OnItemClickListener selectingItems = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            posicaoAlterar = position;
            apresentarDialog();
        }
    };

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    @Override
    public String descricaoItem() {  return ((Pedido) getActivity()).descricaoItem(posicaoAlterar); }

    @Override
    public void fazer(int opt)
    {
        if(opt == 1)
        {
            ((Pedido) getActivity()).alterarItem(posicaoAlterar, opt);

            Toast.makeText(getActivity().getApplicationContext(), "Clicado no botao excluir", Toast.LENGTH_LONG).show();
            ((ListView) getActivity().findViewById(R.id.flirLiItens)).setAdapter
            (
                new ArrayAdapter<String>
                (
                    getActivity().getApplicationContext(),
                    R.layout.default_list_item,
                    ((Pedido) getActivity()).listarResumo()
                )
            );
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(), "Clicado no botao alterar", Toast.LENGTH_LONG).show();
            ((Pedido) getActivity()).alterarItem(posicaoAlterar, opt);
        }
    }
/*********************************END OF ITERFACES METHODS*****************************************/
}
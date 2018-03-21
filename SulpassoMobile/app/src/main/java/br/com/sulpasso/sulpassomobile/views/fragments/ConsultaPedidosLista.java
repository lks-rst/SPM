package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.views.ConsultasPedidos;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.MenuPedidoNaoEnviado;

/**
 * Created by Lucas on 03/01/2017 - 14:45 as part of the project SulpassoMobile.
 */
public class ConsultaPedidosLista extends Fragment implements MenuPedidoNaoEnviado.CallbackMenuPedidoNaoEnviado
{
/**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_consulta_pedidos_lista, /*container, false*/null);
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
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item,
                ((ConsultasPedidos) getActivity()).getControle().listarPedidos(0, ""));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setAdapter(adapter);

        ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setOnItemClickListener(clickPedido);
        ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setOnItemLongClickListener(alterarExcluir);
    }

    public void listarItens(ArrayList<String> lista) throws GenercicException
    {
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(),
                R.layout.spinner_item, lista);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        if(getActivity().findViewById(R.id.frame_itens).getVisibility() == View.VISIBLE)
        {
            ((ListView) getActivity().findViewById(R.id.liAcpItens)).setAdapter(adapter);
        }
        else
        {
            ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setAdapter(adapter);
        }
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
    private AdapterView.OnItemClickListener clickPedido = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            getActivity().findViewById(R.id.frame_pedidos).setVisibility(View.GONE);
            getActivity().findViewById(R.id.frame_itens).setVisibility(View.VISIBLE);

            ArrayAdapter adapter = new ArrayAdapter(
                    getActivity().getApplicationContext(),R.layout.spinner_item,
                    ((ConsultasPedidos) getActivity()).getControle().listarItens(position));
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

            ((ListView) getActivity().findViewById(R.id.liAcpItens)).setAdapter(adapter);
        }
    };

    private AdapterView.OnItemLongClickListener alterarExcluir = new AdapterView.OnItemLongClickListener()
    {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
        {
//            ((ConsultasPedidos) getActivity()).menu_pedido_nao_enviado(position);
            apresentarAcoes(position);

            return false;
        }
    };
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    private void apresentarAcoes(int posicao)
    {
        ((ConsultasPedidos) getActivity()).indicarPosicaoPedido(posicao);
        MenuPedidoNaoEnviado dialog = new MenuPedidoNaoEnviado();
        dialog.setTargetFragment(this, 1); //request code
        dialog.show(getFragmentManager(), "DIALOG");
    }

    @Override
    public void indicarAcao(int acao)
    {
        ((ConsultasPedidos) getActivity()).direcionarAcao(acao);
    }

    @Override
    public int getPedido() {
        return 0;
    }

/*********************************END OF ITERFACES METHODS*****************************************/
/**************************************************************************************************/

/**************************************************************************************************/
}
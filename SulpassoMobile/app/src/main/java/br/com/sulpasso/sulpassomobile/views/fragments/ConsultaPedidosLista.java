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

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.views.ConsultasPedidos;
import br.com.sulpasso.sulpassomobile.views.fragments.Adapters.AdapterPedidos;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.AlertDataPedidos;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.MenuPedidoNaoEnviado;

/**
 * Created by Lucas on 03/01/2017 - 14:45 as part of the project SulpassoMobile.
 */
public class ConsultaPedidosLista extends Fragment implements MenuPedidoNaoEnviado.CallbackMenuPedidoNaoEnviado, AlertDataPedidos.AlterarData
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
        /*
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item,
                ((ConsultasPedidos) getActivity()).getControle().listarPedidos(0, ""));

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        */

        AdapterPedidos adapter = new AdapterPedidos(
            getActivity().getApplicationContext(),
            ((ConsultasPedidos) getActivity()).getControle().listarPedidosV(0, ""));

        ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setAdapter(adapter);

        ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setOnItemClickListener(clickPedido);
        ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setOnItemLongClickListener(alterarExcluir);

        ((EditText)  getActivity().findViewById(R.id.edtQtdPed)).setText(
                ((ConsultasPedidos) getActivity()).getControle().totalizadorVendas(1));
        ((EditText)  getActivity().findViewById(R.id.edtTotPed)).setText(
                ((ConsultasPedidos) getActivity()).getControle().totalizadorVendas(2));
        ((EditText)  getActivity().findViewById(R.id.edtVolPed)).setText(
                ((ConsultasPedidos) getActivity()).getControle().totalizadorVendas(3));
        ((EditText)  getActivity().findViewById(R.id.edtContPed)).setText(
                ((ConsultasPedidos) getActivity()).getControle().totalizadorVendas(4));

        ((getActivity().findViewById(R.id.fcpBtnData))).setOnClickListener(alterDate);
    }

    public void listarItens(ArrayList<Venda> lista) throws GenercicException
    {
        /*
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(),
                R.layout.spinner_item, lista);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        */

        AdapterPedidos adapter = new AdapterPedidos(
                getActivity().getApplicationContext(), lista);

        if(getActivity().findViewById(R.id.frame_itens).getVisibility() == View.VISIBLE)
        {
            ((ListView) getActivity().findViewById(R.id.liAcpItens)).setAdapter(adapter);
        }
        else
        {
            ((ListView) getActivity().findViewById(R.id.liAcpPedidos)).setAdapter(adapter);
        }

        ((EditText)  getActivity().findViewById(R.id.edtQtdPed)).setText(
                ((ConsultasPedidos) getActivity()).getControle().totalizadorVendas(1));
        ((EditText)  getActivity().findViewById(R.id.edtTotPed)).setText(
                ((ConsultasPedidos) getActivity()).getControle().totalizadorVendas(2));
        ((EditText)  getActivity().findViewById(R.id.edtVolPed)).setText(
                ((ConsultasPedidos) getActivity()).getControle().totalizadorVendas(3));
        ((EditText)  getActivity().findViewById(R.id.edtContPed)).setText(
                ((ConsultasPedidos) getActivity()).getControle().totalizadorVendas(4));
    }

    private void selecionarData()
    {
        AlertDataPedidos dialog = new AlertDataPedidos();
        dialog.setTargetFragment(this, 1); //request code
        dialog.show(getFragmentManager(), "DIALOG");
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

    private View.OnClickListener alterDate = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            selecionarData();
        }
    };
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    private void apresentarAcoes(int posicao)
    {
        boolean abrirMenu;
        ((ConsultasPedidos) getActivity()).indicarPosicaoPedido(posicao);

        abrirMenu = ((ConsultasPedidos) getActivity()).abrirMenu();

        if(abrirMenu)
        {
            MenuPedidoNaoEnviado dialog = new MenuPedidoNaoEnviado();
            dialog.setTargetFragment(this, 1); //request code
            dialog.show(getFragmentManager(), "DIALOG");
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(),
                "Pedidos já transmitidos nõ podem ser alterados ou excluidos." +
                "\nPara reenvio de pedidos utilize as opções do menu.",
                Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void indicarAcao(int acao) { ((ConsultasPedidos) getActivity()).direcionarAcao(acao); }

    @Override
    public int getPedido() {
        return 0;
    }

    @Override
    public void indicarNovaData(String data)
    {
        ((ConsultasPedidos) getActivity()).buscarPedidosData(data);
    }
/*********************************END OF ITERFACES METHODS*****************************************/
/**************************************************************************************************/

/**************************************************************************************************/
}
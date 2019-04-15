package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.modelo.PrePedidoItem;

/**
 * Created by Lucas on 16/11/2017 - 16:56 as part of the project SulpassoMobile.
 */
public class DetalhesPrepedido extends DialogFragment
{
    private Callback callback;
    private PrePedido item;
    private ArrayList<String> itens;

    public static interface Callback
    {
        public PrePedido detalhesPrePedido();
        public void exibirDescricaoProduto(int posicao);
    }

    public DetalhesPrepedido() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        itens = new ArrayList<>();
        View view = inflater.inflate(R.layout.alert_detalhes_prepedido, container);

        this.callback = null;
        try
        {
            this.callback = (Callback) getTargetFragment();
            this.item = this.callback.detalhesPrePedido();

            for(PrePedidoItem iv : item.getItensVendidos()) { this.itens.add(iv.toDisplay()); }

            ArrayAdapter adapter = new ArrayAdapter(
                    getActivity().getApplicationContext(), R.layout.spinner_item,
                    itens);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            ((ListView) view.findViewById(R.id.dpp_lista)).setAdapter(adapter);

            ((ListView) view.findViewById(R.id.dpp_lista)).setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    try { callback.exibirDescricaoProduto(position); }
                    catch (Exception erro)
                    {
                        Toast t = Toast.makeText(getActivity().getApplicationContext(),
                            "Desculpe, ocorreu um erro ao tentar acessar o item do pré pedido.\nPor favor, refaça sua busca.",
                            Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                        t.show();
                    }
                    encerrar();
                }
            });

            getDialog().setTitle(R.string.tlt_pre_pedido + " -- " + item.getCliente().getCodigoCliente() + " - " + item.getCliente().getRazao());
        }
        catch (ClassCastException cce)
        {
            Log.e(this.getClass().getSimpleName(), "Callback of this class must be implemented by target fragment!", cce);
            throw cce;
        }
        catch (NullPointerException nex)
        {
            Log.e(this.getClass().getSimpleName(), "Callback of this class must be implemented by target fragment!", nex);
            getDialog().setTitle(R.string.tlt_pre_pedido + " -- ERRO AO CARREGAR");
        }
        catch (Exception e)
        {
            Log.e(this.getClass().getSimpleName(), "Callback of this class must be implemented by target fragment!", e);
            getDialog().setTitle(R.string.tlt_pre_pedido + " -- ERRO AO CARREGAR");
        }

        return view;
    }

    private void encerrar() { this.dismiss(); }
}
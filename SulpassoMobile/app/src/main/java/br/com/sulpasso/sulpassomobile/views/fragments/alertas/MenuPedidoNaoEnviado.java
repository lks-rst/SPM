package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;

/**
 * Created by Lucas on 19/03/2018 - 11:25 as part of the project SulpassoMobile.
 */
public class MenuPedidoNaoEnviado extends DialogFragment
{
    private CallbackMenuPedidoNaoEnviado callback;

    public static interface CallbackMenuPedidoNaoEnviado
    {
        public void indicarAcao(int acao);
        public int getPedido();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.alert_menu_pedido_nao_enviado, container);

        ArrayList<String> lista = new ArrayList<>();
        lista.add("ALTERAR");
        lista.add("EXCLUÍR");
        //http://pt.broculos.net/2013/09/how-to-change-spinner-text-size-color.html#.WYIjmVGQy01
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(),
                R.layout.spinner_item_pedido_nao_enviado, lista);

        this.callback = null;
        try
        {
            this.callback = (CallbackMenuPedidoNaoEnviado) getTargetFragment();
        }
        catch (ClassCastException e)
        {
            this.callback = null;
            Log.e(this.getClass().getSimpleName(), "Callback of this class must be implemented by target fragment!", e);
            throw e;
        }

        ((ListView) view.findViewById(R.id.lvAlertMpne)).setAdapter(adapter);
        ((ListView) view.findViewById(R.id.lvAlertMpne)).setOnItemClickListener(
            new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    if(callback != null)
                        callback.indicarAcao(position);

                    encerrar();
                }
            }
        );

        getDialog().setTitle(R.string.tlt_alterar_excluir_p);
        return view;
    }

    private void encerrar() { this.dismiss(); }
}
package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;

/**
 * Created by Lucas on 04/10/2018 - 14:43 as part of the project SulpassoMobile.
 */
public class AlertJustificativaPedido extends DialogFragment
{
    private Callback callback;
    private PrePedido item;
    private ArrayList<String> itens;

    public static interface Callback
    {
        public void justificarPedido(int opcao);
    }

    public AlertJustificativaPedido() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.callback = (Callback) getTargetFragment();

        itens = new ArrayList<>();
        View view = inflater.inflate(R.layout.alert_justificar_visita, container);

        (view.findViewById(R.id.alert_justificar)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                callback.justificarPedido(1);
                encerrar();
            }
        });

        (view.findViewById(R.id.alert_continuar)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                callback.justificarPedido(0);
                encerrar();
            }
        });

        return view;
    }

    private void encerrar() { this.dismiss(); }
}
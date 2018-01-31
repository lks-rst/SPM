package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.sulpasso.sulpassomobile.R;

/**
 * Created by Lucas on 26/01/2018 - 14:34 as part of the project SulpassoMobile.
 */
public class AlterarExcluir extends DialogFragment
{
    private Callback callback;

    public static interface Callback
    {
        public String descricaoItem();
        public void fazer(int opt);
    }

    public AlterarExcluir() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.alert_alterar_excluir, container);

        this.callback = null;
        try
        {
            this.callback = (Callback) getTargetFragment();

            getDialog().setTitle(R.string.tlt_alterar_excluir);
        }
        catch (Exception e) { encerrar(); }

        ((TextView) view.findViewById(R.id.txtDescricaoItem)).setText(callback.descricaoItem());

        (view.findViewById(R.id.btnExcluirItem)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                callback.fazer(1);
                encerrar();
            }
        });

        (view.findViewById(R.id.btnAlterarItem)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                callback.fazer(0);
                encerrar();
            }
        });

        return view;
    }

    private void encerrar() { this.dismiss(); }
}
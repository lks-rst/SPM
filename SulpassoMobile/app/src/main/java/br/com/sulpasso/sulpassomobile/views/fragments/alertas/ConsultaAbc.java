package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.CurvaAbc;

/**
 * Created by Lucas on 10/01/2018 - 17:21 as part of the project SulpassoMobile.
 */
public class ConsultaAbc extends DialogFragment
{
    private Callback callback;
    private CurvaAbc abc;

    public static interface Callback
    {
        public CurvaAbc abcCliente();
    }

    public ConsultaAbc() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.callback = (Callback) getTargetFragment();

        View view = inflater.inflate(R.layout.alert_consulta_abc, container);
        this.abc = this.callback.abcCliente();

        ((EditText) view.findViewById(R.id.edt_abc_peso1)).setText(String.valueOf(this.abc.getPeso_1()));
        ((EditText) view.findViewById(R.id.edt_abc_peso2)).setText(String.valueOf(this.abc.getPeso_2()));
        ((EditText) view.findViewById(R.id.edt_abc_fat1)).setText(String.valueOf(this.abc.getFat_1()));
        ((EditText) view.findViewById(R.id.edt_abc_fat2)).setText(String.valueOf(this.abc.getFat_2()));
        ((EditText) view.findViewById(R.id.edt_abc_cont1)).setText(String.valueOf(this.abc.getCont_1()));
        ((EditText) view.findViewById(R.id.edt_abc_cont2)).setText(String.valueOf(this.abc.getCont_2()));

        return view;
    }

    private void encerrar() { this.dismiss(); }
}
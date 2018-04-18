package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.CurvaAbc;

/**
 * Created by Lucas on 29/01/2018 - 15:42 as part of the project SulpassoMobile.
 */
public class AlertCortesDevolucaoTitulos extends DialogFragment
{
    private Callback callback;
    private CurvaAbc abc;

    public static interface Callback
    {
        public int buscarTipo();

        public ArrayList<String> buscarItens();
        public ArrayList<String> buscarDetalhes();
    }

    public AlertCortesDevolucaoTitulos() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.callback = (Callback) getTargetFragment();

        ArrayList<String> detalhes = new ArrayList<>();

        View view = inflater.inflate(R.layout.alert_cortes_devolucao_titulos, container);

        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item,
                callback.buscarItens());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ((ListView) view.findViewById(R.id.acdt_li_itens)).setAdapter(adapter);

        detalhes = callback.buscarDetalhes();

        if(callback.buscarTipo() != 1)
        {
            ((EditText) view.findViewById(R.id.acdt_edt_qtd)).setText(detalhes.get(1));
            ((EditText) view.findViewById(R.id.acdt_edt_total)).setText(detalhes.get(2));

            if (callback.buscarTipo() == 2)
            {
                view.findViewById(R.id.acdt_li_t1).setVisibility(View.VISIBLE);
                getDialog().setTitle(getActivity().getApplicationContext().getString(R.string.tlt_devoucao) + " -- " + detalhes.get(0));
            } else
            {
                ((EditText) view.findViewById(R.id.acdt_edt_vencdos)).setText(detalhes.get(3));
                ((EditText) view.findViewById(R.id.acdt_edt_nVencidos)).setText(detalhes.get(4));

                view.findViewById(R.id.acdt_li_t1).setVisibility(View.VISIBLE);
                view.findViewById(R.id.acdt_li_t2).setVisibility(View.VISIBLE);

                getDialog().setTitle(getActivity().getApplicationContext().getString(R.string.tlt_titulos) + " -- " + detalhes.get(0));
            }
        }
        else { getDialog().setTitle(getActivity().getApplicationContext().getString(R.string.tlt_corte) + " -- " + detalhes.get(0)); }

        return view;
    }
}
package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.CurvaAbc;

/**
 * Created by Lucas on 02/04/2018 - 11:38 as part of the project SulpassoMobile.
 */
public class AlertDetalhesCliente extends DialogFragment
{
    private Callback callback;
    private CurvaAbc abc;

    public static interface Callback
    {
        public ArrayList<String> buscarCliente();
    }

    public AlertDetalhesCliente() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.callback = (Callback) getTargetFragment();

        ArrayList<String> detalhes = new ArrayList<>();
        detalhes = this.callback.buscarCliente();

        View view = inflater.inflate(R.layout.alert_detalhes_cliente, container);


        ((EditText) view.findViewById(R.id.aacEdtFant)).setText(detalhes.get(0));
        ((EditText) view.findViewById(R.id.aacEdtBairro)).setText(detalhes.get(1));
        ((EditText) view.findViewById(R.id.aacEdtCnpj)).setText(detalhes.get(2));
        ((EditText) view.findViewById(R.id.aacEdtCod)).setText(detalhes.get(3));
        ((EditText) view.findViewById(R.id.aacEdtIe)).setText(detalhes.get(4));
        ((EditText) view.findViewById(R.id.aacEdtBanco)).setText(detalhes.get(5));
        ((EditText) view.findViewById(R.id.aacEdtCep)).setText(detalhes.get(6));
        ((EditText) view.findViewById(R.id.aacEdtAniv)).setText(detalhes.get(7));
        ((EditText) view.findViewById(R.id.aacEdtRota)).setText(detalhes.get(8));
        ((EditText) view.findViewById(R.id.aacEdtMail)).setText(detalhes.get(9));
        ((EditText) view.findViewById(R.id.aacEdtContact)).setText(detalhes.get(10));
        ((EditText) view.findViewById(R.id.aacEdtMsg)).setText(detalhes.get(11));

        ((CheckBox) view.findViewById(R.id.aacCbxSeg)).setChecked((detalhes.get(12).indexOf("2") != -1));
        ((CheckBox) view.findViewById(R.id.aacCbxTer)).setChecked((detalhes.get(12).indexOf("3") != -1));
        ((CheckBox) view.findViewById(R.id.aacCbxQua)).setChecked((detalhes.get(12).indexOf("4") != -1));
        ((CheckBox) view.findViewById(R.id.aacCbxQui)).setChecked((detalhes.get(12).indexOf("5") != -1));
        ((CheckBox) view.findViewById(R.id.aacCbxSex)).setChecked((detalhes.get(12).indexOf("6") != -1));
        ((CheckBox) view.findViewById(R.id.aacCbxSab)).setChecked((detalhes.get(12).indexOf("7") != -1));

        (view.findViewById(R.id.aacCbxSeg)).setVisibility((detalhes.get(12).indexOf("2") != -1) ? View.VISIBLE : View.GONE);
        (view.findViewById(R.id.aacCbxTer)).setVisibility((detalhes.get(12).indexOf("3") != -1) ? View.VISIBLE : View.GONE);
        (view.findViewById(R.id.aacCbxQua)).setVisibility((detalhes.get(12).indexOf("4") != -1) ? View.VISIBLE : View.GONE);
        (view.findViewById(R.id.aacCbxQui)).setVisibility((detalhes.get(12).indexOf("5") != -1) ? View.VISIBLE : View.GONE);
        (view.findViewById(R.id.aacCbxSex)).setVisibility((detalhes.get(12).indexOf("6") != -1) ? View.VISIBLE : View.GONE);
        (view.findViewById(R.id.aacCbxSab)).setVisibility((detalhes.get(12).indexOf("7") != -1) ? View.VISIBLE : View.GONE);

        getDialog().setTitle(getActivity().getApplicationContext().getString(R.string.tlt_titulo_detalhes));

        return view;
    }
}
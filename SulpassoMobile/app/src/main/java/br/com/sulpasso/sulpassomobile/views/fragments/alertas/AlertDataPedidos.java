package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 16/04/2018 - 09:20 as part of the project SulpassoMobile.
 */
public class AlertDataPedidos extends DialogFragment
{
    private AlterarData callback;

    public static interface AlterarData
    {
        public void indicarNovaData(String data);
    }

    public AlertDataPedidos() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.alert_data_pedidos, container);

        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.adpDtp);
        this.callback = null;

        try
        {
            this.callback = (AlterarData) getTargetFragment();

            getDialog().setTitle(R.string.tlt_alterar_excluir);
        }
        catch (Exception e) { encerrar(); }

        (view.findViewById(R.id.adpBtnConf)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                ManipulacaoStrings ms = new ManipulacaoStrings();
                String data = ms.comEsquerda(String.valueOf(day), "0" , 2) + "/" + ms.comEsquerda(String.valueOf(month), "0" , 2) + "/" + year;
                data = ms.dataBanco(data);

                Toast.makeText(getActivity().getApplicationContext(), "Data selecionada= " + data, Toast.LENGTH_LONG).show();
                callback.indicarNovaData(data);
                encerrar();
            }
        });
/*
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
*/


//        getDialog().setTitle(R.string.tlt_datas_pedidos);
        return view;
    }

    private void encerrar() { this.dismiss(); }
}
package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;

/**
 * Created by Lucas on 30/11/2018 - 09:39 as part of the project SulpassoMobile.
 */
public class AlertaPromocoes extends DialogFragment
{
    private ExibirPromocoes callback;

    public static interface ExibirPromocoes
    {
        public ArrayList<String> buscarMix();
    }

    public AlertaPromocoes() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.mix_faltante, container);
        ((TextView) view.findViewById(R.id.amfTxtMsg)).setText("PROMOÇÕES DO ITEM");

        ArrayList<String> mixFaltante = new ArrayList<String>();

        try
        {
            this.callback = (ExibirPromocoes) getTargetFragment();
            mixFaltante = this.callback.buscarMix();

            getDialog().setTitle(R.string.tlt_datas_promos);
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(),
                    "O item não possui promoções ou ocorreu um erro na consulta", Toast.LENGTH_LONG).show();

            encerrar();
        }

        ((ListView) view.findViewById(R.id.amfLvItens)).setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                R.layout.default_list_item,
                mixFaltante
            )
        );

        /*
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.adpDtp);this.callback = null;

        try
        {
            this.callback = (ExibirMixFaltante) getTargetFragment();

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
        */


        getDialog().setTitle(R.string.tlt_datas_promos);
        return view;
    }

    private void encerrar() { this.dismiss(); }
}
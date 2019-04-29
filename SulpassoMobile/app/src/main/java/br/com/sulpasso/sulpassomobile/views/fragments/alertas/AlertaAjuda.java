package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.Item;

/**
 * Created by Lucas on 22/04/2019 - 16:15 as part of the project SulpassoMobile.
 */
public class AlertaAjuda extends DialogFragment
{
    private ExibirMixFaltante callback;

    public static interface ExibirMixFaltante
    {
        public ArrayList<Item> buscarMix();
        public void confirmarSalvamento(boolean gravar);
    }

    public AlertaAjuda() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.mix_faltante, container);

        ArrayList<String> mixFaltante = new ArrayList<String>();
        ArrayList<Item> mixItens = new ArrayList<Item>();

        try
        {
            this.callback = (ExibirMixFaltante) getTargetFragment();

            getDialog().setTitle(R.string.tlt_alterar_excluir);
        }
        catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Não foi possivel carregar os itens do mix do cliente", Toast.LENGTH_LONG).show();

            encerrar();
        }


        for(int i = 0; i < mixItens.size(); i++) { mixFaltante.add(mixItens.get(i).toDisplay()); }

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


        getDialog().setTitle(R.string.tlt_datas_pedidos);
        return view;
    }

    private void encerrar() { this.dismiss(); }
}
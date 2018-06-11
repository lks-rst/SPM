package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.Item;

/**
 * Created by Lucas on 05/06/2018 - 14:55 as part of the project SulpassoMobile.
 */
public class AlertItensFoco extends DialogFragment
{
    private CallbackFoco callback;

    private Spinner spnrFiltrados;
    private ListView lvAdicionados;

    private ArrayList<Item> adicionados;
    private ArrayList<Item> consulta;

    public static interface CallbackFoco
    {
        public ArrayList<Item> buscarItens(String descricao);
        public void isnerirItens(ArrayList<Item> foco);
    }

    public AlertItensFoco() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.alert_select_itens_foco, container);

        this.callback = null;
        try
        {
            this.callback = (CallbackFoco) getTargetFragment();
            this.adicionados = new ArrayList<>();

            getDialog().setTitle(R.string.tlt_alterar_excluir);
        }
        catch (Exception e)
        {
            Toast t = Toast.makeText(getActivity().getApplicationContext(), null, Toast.LENGTH_LONG);
            t.setText("Ocorreu um problema no sistema.\nPor favor entre em contato com seu supervisor.");
            t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
            t.show();

            encerrar();
        }

        spnrFiltrados = (Spinner) view.findViewById(R.id.asifSpnrFiltered);
        lvAdicionados = (ListView) view.findViewById(R.id.asifLvAdd);

        view.findViewById(R.id.asifBtnConf).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(adicionados != null && adicionados.size() > 0)
                {
                    callback.isnerirItens(adicionados);
                    encerrar();
                }
                else { encerrar(); }
            }
        });

        ((EditText) view.findViewById(R.id.asifEdtSearch)).addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { /*****/ }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(s.length() > 2)
                {
                    consulta = null;
                    //listarItens(s.toString());
                    consulta = callback.buscarItens(s.toString());

                    if (consulta != null)
                    {
                        ArrayList<String> itens = new ArrayList<String>();

                        itens.add("Selcione um item");
                        for(Item i : consulta)
                        {
                            itens.add(i.toDisplay());
                        }

                        ArrayAdapter adapter = new ArrayAdapter(
                                getActivity().getApplicationContext(), R.layout.spinner_item, itens);
                        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

                        spnrFiltrados.setAdapter(adapter);

                        spnrFiltrados.setVisibility(View.VISIBLE);
                    }
                    else { spnrFiltrados.setVisibility(View.GONE); }

                }
                else { spnrFiltrados.setVisibility(View.GONE); }
            }
        });

        spnrFiltrados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(position > 0)
                {
                    adicionados.add(consulta.get(position -1));

                    ArrayList<String> itens = new ArrayList<String>();

                    for(Item i : adicionados) { itens.add(i.toDisplay()); }

                    ArrayAdapter adapter = new ArrayAdapter(
                            getActivity().getApplicationContext(), R.layout.spinner_item, itens);
                    adapter.setDropDownViewResource(R.layout.default_list_item);

                    lvAdicionados.setAdapter(adapter);

                    if(adicionados.size() > 0)
                        lvAdicionados.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { /*****/ }
        });

        lvAdicionados.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adicionados.remove(position);

                if(adicionados.size() > 0)
                {
                    ArrayList<String> itens = new ArrayList<String>();

                    for(Item i : adicionados) { itens.add(i.toDisplay()); }

                    ArrayAdapter adapter = new ArrayAdapter(
                            getActivity().getApplicationContext(), R.layout.spinner_item, itens);
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

                    lvAdicionados.setAdapter(adapter);

                    lvAdicionados.setVisibility(View.VISIBLE);
                }
                else { lvAdicionados.setVisibility(View.GONE); }
            }
        });

        return view;
    }

    private void encerrar() { this.dismiss(); }
}
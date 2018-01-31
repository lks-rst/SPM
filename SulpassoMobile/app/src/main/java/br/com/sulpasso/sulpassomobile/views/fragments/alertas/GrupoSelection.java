package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;

/**
 * Created by Lucas on 18/11/2016 - 14:05 as part of the project SulpassoMobile.
 */
public class GrupoSelection extends DialogFragment implements View.OnClickListener
{
    private EditText mEditText;
    private Button acceptButton;
    private Button cancelButton;

    private Spinner grupoSpinner;
    private Spinner subSpinner;
    private Spinner divSpinner;

    private Callback callback;

    public static interface Callback
    {
        public void accept();

        public ArrayList<String> carregarGrupos();
        public ArrayList<String> carregarSubGrupos();
        public ArrayList<String> carregarDivisoes();

        public void indicarGrupo(int pos);
        public void indicarSubGrupo(int pos);
        public void indicarDivisao(int pos);
    }

    public GrupoSelection() { /* Empty constructor required for DialogFragment */ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.alert_grupo_selection, container);
        this.acceptButton = (Button) view.findViewById(R.id.btnAlertConfirmar);
        this.cancelButton = (Button) view.findViewById(R.id.btnAlertCancelar);
        this.grupoSpinner = (Spinner) view.findViewById(R.id.spnrAlertGrupo);
        this.subSpinner = (Spinner) view.findViewById(R.id.spnrAlertSub);
        this.divSpinner = (Spinner) view.findViewById(R.id.spnrAlertDivsao);

        this.acceptButton.setOnClickListener(this);
        this.cancelButton.setOnClickListener(this);

        this.callback = null;
        try
        {
            this.callback = (Callback) getTargetFragment();
        }
        catch (ClassCastException e)
        {
            Log.e(this.getClass().getSimpleName(), "Callback of this class must be implemented by target fragment!", e);
            throw e;
        }

        //http://pt.broculos.net/2013/09/how-to-change-spinner-text-size-color.html#.WYIjmVGQy01
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(),
                R.layout.spinner_item,
                this.callback.carregarGrupos());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.grupoSpinner.setAdapter(adapter);

        ArrayAdapter sAdapter = new ArrayAdapter(
                getActivity().getApplicationContext(),
                R.layout.spinner_item,
                this.callback.carregarSubGrupos());
        sAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.subSpinner.setAdapter(sAdapter);

        ArrayAdapter dAdapter = new ArrayAdapter(
                getActivity().getApplicationContext(),
                R.layout.spinner_item,
                this.callback.carregarDivisoes());
        dAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.divSpinner.setAdapter(dAdapter);

        this.grupoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                if(arg2 > 0)
                {
                    callback.indicarGrupo(arg2);

                    ArrayAdapter sAdapter = new ArrayAdapter(
                            getActivity().getApplicationContext(),
                            R.layout.spinner_item,
                            callback.carregarSubGrupos());
                    sAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                    subSpinner.setAdapter(sAdapter);
                }
                else { /*****/ }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { /*****/ }
        });

        this.subSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                if(arg2 > 0)
                {
                    callback.indicarSubGrupo(arg2);

                    ArrayAdapter dAdapter = new ArrayAdapter(
                            getActivity().getApplicationContext(),
                            R.layout.spinner_item,
                            callback.carregarDivisoes());
                    dAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                    divSpinner.setAdapter(dAdapter);
                }
                else { /*****/ }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { /*****/ }
        });

        this.divSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                if(arg2 > 0) { callback.indicarDivisao(arg2); }
                else { /*****/ }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) { /*****/ }
        });

        getDialog().setTitle(R.string.tlt_busca_grupo);
        return view;
    }

    @Override
    public void onClick(View v)
    {
        if (callback != null)
        {
            if (v == acceptButton)
            {
                callback.accept();
                this.dismiss();
            }
            else if (v == cancelButton) { this.dismiss(); }
        }
    }
}
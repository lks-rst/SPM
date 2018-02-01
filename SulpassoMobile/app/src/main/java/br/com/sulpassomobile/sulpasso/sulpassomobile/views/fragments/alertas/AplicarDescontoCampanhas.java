package br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;

/**
 * Created by Lucas on 02/01/2017 - 10:27 as part of the project SulpassoMobile.
 */
public class AplicarDescontoCampanhas extends DialogFragment implements View.OnClickListener
{
    private EditText mEditText;
    private TextView lbl;
    private Button acceptButton;
    private Button cancelButton;

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

    public AplicarDescontoCampanhas() { /* Empty constructor required for DialogFragment */ }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.alert_desconto_campanhas, container);
        this.acceptButton = (Button) view.findViewById(R.id.btnAlertConfirmar);
        this.cancelButton = (Button) view.findViewById(R.id.btnAlertCancelar);
        this.mEditText = (EditText) view.findViewById(R.id.edtAlertValor);
        this.lbl = (TextView) view.findViewById(R.id.lblAlertMsg);

        this.acceptButton.setOnClickListener(this);
        this.cancelButton.setOnClickListener(this);

        this.callback = null;

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

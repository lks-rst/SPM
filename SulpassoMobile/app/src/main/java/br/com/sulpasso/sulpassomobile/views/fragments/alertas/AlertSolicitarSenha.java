package br.com.sulpasso.sulpassomobile.views.fragments.alertas;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.sulpasso.sulpassomobile.R;

/**
 * Created by Lucas on 23/03/2018 - 16:22 as part of the project SulpassoMobile.
 */
public class AlertSolicitarSenha extends DialogFragment implements View.OnClickListener
{
    private EditText mEditText;
    private TextView lbl;
    private Button acceptButton;
    private Button cancelButton;

    private Callback callback;

    public static interface Callback
    {
        public String buscarChave();
        public void indicarSenha(String senha);
    }

    public AlertSolicitarSenha() { /* Empty constructor required for DialogFragment */ }

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

        try
        {
            this.callback = (Callback) getTargetFragment();

            getDialog().setTitle(R.string.tlt_alterar_excluir);
        }
        catch (Exception e) { encerrar(); }

        return view;
    }

    @Override
    public void onClick(View v)
    {
        if (callback != null)
        {
            if (v == acceptButton)
            {
                callback.indicarSenha(" ");
                this.dismiss();
            }
            else if (v == cancelButton) { this.dismiss(); }
        }
    }

    private void encerrar() { this.dismiss(); }
}

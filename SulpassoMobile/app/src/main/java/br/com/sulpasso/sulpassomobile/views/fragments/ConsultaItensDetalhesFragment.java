package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.modelo.Item;

/**
 * Created by Lucas on 21/11/2016 - 09:40 as part of the project SulpassoMobile.
 */
public class ConsultaItensDetalhesFragment extends DialogFragment
{
    /*
        TODO: Verificar as promoções;
        TODO: Vincular a busca do item a busca das aplicações para passar a informaçao correta;
        TODO: Alterar a cor do texto das listas;
     */
    private ConsultaItensDetalhes callback;

    public static interface ConsultaItensDetalhes
    {
        public Item buscarItem();
        public ArrayList<String> buscarTabelas(int item);
        public ArrayList<String> buscarPromocoes(int item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_consulta_itens_detalhe, container);

        this.callback = null;
        try
        {
            this.callback = (ConsultaItensDetalhes) getTargetFragment();
        }
        catch (ClassCastException e)
        {
            Log.e(this.getClass().getSimpleName(), "The interface of this class must be implemented by target fragment!", e);
            throw e;
        }

        Item i = this.callback.buscarItem();

        ((EditText) view.findViewById(R.id.edtAlertDescricao)).setText(i.getCodigo() + " - " + i.getDescricao());
        ((EditText) view.findViewById(R.id.edtAlertReferencia)).setText(i.getReferencia());
        ((EditText) view.findViewById(R.id.edtAlertComplemento)).setText(i.getComplemento());
        ((EditText) view.findViewById(R.id.edtAlertUnidade)).setText(i.getUnidade());
        ((EditText) view.findViewById(R.id.edtAlertUniVenda)).setText(i.getUnidadeVenda());
        ((EditText) view.findViewById(R.id.edtAlertCaixa)).setText(i.getQuantidadeCaixa() + "");
        ((EditText) view.findViewById(R.id.edtAlertQuantidade)).setText(i.getMinimoVenda() + "");
        ((EditText) view.findViewById(R.id.edtAlertBarras)).setText(i.getBarras());
        ((EditText) view.findViewById(R.id.edtAlertEstoque)).setText("Estoque");
        ((EditText) view.findViewById(R.id.edtAlertAplicacao)).setText("Aplicação do item");

        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item,
                this.callback.buscarPromocoes(i.getCodigo()));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ((ListView) view.findViewById(R.id.liAlertPromocoes)).setAdapter(adapter);

        ArrayAdapter adapter2 = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item,
                this.callback.buscarTabelas(i.getCodigo()));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        ((ListView) view.findViewById(R.id.liAlertTabelas)).setAdapter(adapter2);

        getDialog().setTitle(R.string.tlt_detalhes);
        return view;
    }
}
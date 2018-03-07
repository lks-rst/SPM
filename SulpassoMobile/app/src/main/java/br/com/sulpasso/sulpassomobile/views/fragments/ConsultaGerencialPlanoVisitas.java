package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.views.ConsultasGerenciais;

/**
 * Created by Lucas on 27/02/2018 - 10:50 as part of the project SulpassoMobile.
 */
public class ConsultaGerencialPlanoVisitas extends Fragment
{
    public ConsultaGerencialPlanoVisitas(){}
    /**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_consulta_gerencial_plano_visitas, /*container, false*/null);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        this.setUpLayout();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        try { listarItens(); }
        catch (GenercicException e) { e.printStackTrace(); }
    }

    public void listarItens() throws GenercicException
    {
        ((TextView) (getActivity().findViewById(R.id.lblFcgPvData))).addTextChangedListener(alterada_data);
        ((TextView) (getActivity().findViewById(R.id.lblFcgPvData))).setText(((ConsultasGerenciais) getActivity()).buscarDataAtual());
    }

    public void apresentarPlano(ArrayList<String> plano)
    {
/***************** SEGUNDA *********/
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNrcSeg))).setText(plano.get(0));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNcvSeg))).setText(plano.get(1));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvPrcSeg))).setText(plano.get(2));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvFdcSeg))).setText(plano.get(3));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVicSeg))).setText(plano.get(4));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNpcSeg))).setText(plano.get(5));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvValpSeg))).setText(plano.get(6));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVolpSeg))).setText(plano.get(7));
/***************** TERÇA *********/
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNrcTer))).setText(plano.get(8));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNcvTer))).setText(plano.get(9));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvPrcTer))).setText(plano.get(10));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvFdcTer))).setText(plano.get(11));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVicTer))).setText(plano.get(12));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNpcTer))).setText(plano.get(13));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvValpTer))).setText(plano.get(14));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVolpTer))).setText(plano.get(15));
/***************** QUARTA *********/
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNrcQua))).setText(plano.get(16));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNcvQua))).setText(plano.get(17));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvPrcQua))).setText(plano.get(18));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvFdcQua))).setText(plano.get(19));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVicQua))).setText(plano.get(20));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNpcQua))).setText(plano.get(21));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvValpQua))).setText(plano.get(22));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVolpQua))).setText(plano.get(23));
/***************** QUINTA *********/
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNrcQui))).setText(plano.get(24));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNcvQui))).setText(plano.get(25));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvPrcQui))).setText(plano.get(26));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvFdcQui))).setText(plano.get(27));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVicQui))).setText(plano.get(28));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNpcQui))).setText(plano.get(29));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvValpQui))).setText(plano.get(30));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVolpQui))).setText(plano.get(31));
/***************** SEXTA *********/
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNrcSex))).setText(plano.get(32));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNcvSex))).setText(plano.get(33));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvPrcSex))).setText(plano.get(34));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvFdcSex))).setText(plano.get(35));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVicSex))).setText(plano.get(36));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNpcSex))).setText(plano.get(37));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvValpSex))).setText(plano.get(38));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVolpSex))).setText(plano.get(39));
/***************** SABADO *********/
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNrcSab))).setText(plano.get(40));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNcvSab))).setText(plano.get(41));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvPrcSab))).setText(plano.get(42));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvFdcSab))).setText(plano.get(43));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVicSab))).setText(plano.get(44));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNpcSab))).setText(plano.get(45));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvValpSab))).setText(plano.get(46));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVolpSab))).setText(plano.get(47));
/***************** S VISITA *********/
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNrcSdv))).setText(plano.get(48));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNcvSdv))).setText(plano.get(49));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvPrcSdv))).setText(plano.get(50));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvFdcSdv))).setText(plano.get(51));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVicSdv))).setText(plano.get(52));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNpcSdv))).setText(plano.get(53));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvValpSdv))).setText(plano.get(54));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVolpSdv))).setText(plano.get(55));
/***************** TOTAIS *********/
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNrcTot))).setText(plano.get(56));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNcvTot))).setText(plano.get(57));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvPrcTot))).setText(plano.get(58));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvFdcTot))).setText(plano.get(59));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVicTot))).setText(plano.get(60));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvNpcTot))).setText(plano.get(61));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvValpTot))).setText(plano.get(62));
        ((EditText) (getActivity().findViewById(R.id.edtFcgPvVolpTot))).setText(plano.get(63));
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
    private TextWatcher alterada_data = new TextWatcher()
    {
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { /*****/ }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) { /*****/ }

        @Override
        public void afterTextChanged(Editable arg0)
        {
            Toast.makeText(getActivity().getApplicationContext(), "Novo valora da data exibido pelo fragmento >>> " + arg0.toString(), Toast.LENGTH_LONG).show();
            ((ConsultasGerenciais) getActivity()).buscarPlanoVisitas(arg0.toString());
        }
    };
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    public void indcarNovaData(String data)
    {
        ((TextView) (getActivity().findViewById(R.id.lblFcgPvData))).setText(data);
    }
/*********************************END OF ITERFACES METHODS*****************************************/
}
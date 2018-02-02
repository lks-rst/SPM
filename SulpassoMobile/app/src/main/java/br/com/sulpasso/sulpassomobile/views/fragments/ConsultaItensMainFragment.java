package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import br.com.sulpasso.sulpassomobile.controle.ConsultaProdutosMain;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.GrupoSelection;

/**
 * Created by Lucas on 16/11/2016 - 15:11 as part of the project SulpassoMobile.
 */
public class ConsultaItensMainFragment extends Fragment implements GrupoSelection.Callback, ConsultaItensDetalhesFragment.ConsultaItensDetalhes
{
    private ListView fcipLiItens;
    private Spinner filter;
    private EditText query;

    private ConsultaProdutosMain consulta;

    private int posicaoItem;

    public ConsultaItensMainFragment(){}

    /**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        consulta = new ConsultaProdutosMain(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_consulta_itens_principal, /*container, false*/null);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_filtro_itens, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.itens_desc_ini:
                ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setHint(
                        getActivity().getResources().getString(R.string.str_busca) + " " +
                                getActivity().getResources().getString(R.string.hnt_descIni));
                this.consulta.setSearchType(1);
                break;
            case R.id.itens_desc_any:
                ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setHint(
                        getActivity().getResources().getString(R.string.str_busca) + " " +
                                getActivity().getResources().getString(R.string.hnt_descAny));
                this.consulta.setSearchType(2);
                break;
            case R.id.itens_ref:
                ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setHint(
                        getActivity().getResources().getString(R.string.str_busca) + " " +
                                getActivity().getResources().getString(R.string.hnt_ref));
                this.consulta.setSearchType(3);
                break;
            case R.id.itens_grupo:
                ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setHint(
                        getActivity().getResources().getString(R.string.str_busca) + " " +
                                getActivity().getResources().getString(R.string.hnt_grp));
                this.consulta.setSearchType(4);

                GrupoSelection dialog = new GrupoSelection();
                dialog.setTargetFragment(this, 1); //request code
                dialog.show(getFragmentManager(), "DIALOG");
                break;
            /*
            case R.id.itens_s_grupo:
                ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setHint(
                        getActivity().getResources().getString(R.string.str_busca) + " " +
                                getActivity().getResources().getString(R.string.hnt_sgrp));
                this.consulta.setSearchType(4);
                break;
            case R.id.itens_divisao:
                ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setHint(
                        getActivity().getResources().getString(R.string.str_busca) + " " +
                                getActivity().getResources().getString(R.string.hnt_div));
                this.consulta.setSearchType(4);
                break;
            */
            default:
                break;
        }

        try { this.listarItens(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Erro ao buscar os itens", Toast.LENGTH_LONG).show();
        }

        ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setText("");

        return false;
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        this.fcipLiItens = (ListView) (getActivity().findViewById(R.id.fcipLiItens));
        this.fcipLiItens.setOnItemClickListener(selectingItems);

//        this.filter = (Spinner) (getActivity().findViewById(R.id.fcipSpnrFiltro));

        try { this.listarItens(); }
        catch (GenercicException e)
        {
            e.printStackTrace();

            Toast.makeText(getActivity().getApplicationContext(), "Erro ao buscar os itens", Toast.LENGTH_LONG).show();
        }

        ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).addTextChangedListener(search);

        ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setHint(
                getActivity().getResources().getString(R.string.str_busca) + " " +
                        getActivity().getResources().getString(R.string.hnt_descAny));
    }

    public void listarItens() throws GenercicException
    {
        this.fcipLiItens.setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                R.layout.default_list_item,
                consulta.buscarItens()
            )
        );
    }

    public void ajustarLayout() { /*****/ }

    private void abrirDetalhes()
    {
        ConsultaItensDetalhesFragment detalhes = new ConsultaItensDetalhesFragment();
        detalhes.setTargetFragment(this, 1); //request code
        detalhes.show(getFragmentManager(), "DETALHES");
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
    private AdapterView.OnItemClickListener selectingItems = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            posicaoItem = position;
            abrirDetalhes();
        }
    };

    private AdapterView.OnItemSelectedListener selectingItem = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { if(position > 0) { /*****/ } }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { /******/ }
    };

    private TextWatcher search = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { /*****/ }

        @Override
        public void afterTextChanged(Editable s)
        {
            if(s.toString().length() >= 2)
            {
                consulta.setSearchData(s.toString().toUpperCase());

                try { listarItens(); }
                catch (GenercicException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Erro ao buscar os itens", Toast.LENGTH_LONG).show();
                }
            }
        }
    };
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    @Override
    public void accept()
    {
        try { this.listarItens(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> carregarGrupos()
    {
        try { return this.consulta.listarGrupos(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),
                getActivity().getResources().getString(R.string.lista_invalida), Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<String> carregarSubGrupos()
    {
        try { return consulta.listarSubGrupos(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),
                getActivity().getResources().getString(R.string.lista_invalida), Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<String> carregarDivisoes()
    {
        try { return consulta.listarDivisoes(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),
                getActivity().getResources().getString(R.string.lista_invalida), Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
    }

    @Override
    public void indicarGrupo(int pos) { this.consulta.indicarGrupo(pos - 1); }

    @Override
    public void indicarSubGrupo(int pos) { this.consulta.indicarSubGrupo(pos - 1); }

    @Override
    public void indicarDivisao(int pos) { this.consulta.indicarDivisao(pos - 1); }

    @Override
    public Item buscarItem() { return this.consulta.carregarItem(posicaoItem); }

    @Override
    public ArrayList<String> buscarTabelas(int item)
    {
        try { return this.consulta.carregarTabeas(item); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            ArrayList<String> ret = new ArrayList<>();
            ret.add(getResources().getString(R.string.lista_invalida));
            return ret;
        }
    }

    @Override
    public ArrayList<String> buscarPromocoes(int item)
    {
        try { return this.consulta.carregarPromocoes(item); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            ArrayList<String> ret = new ArrayList<>();
            ret.add(getResources().getString(R.string.sem_promocoes));
            return ret;
        }
    }
/*********************************END OF ITERFACES METHODS*****************************************/
}
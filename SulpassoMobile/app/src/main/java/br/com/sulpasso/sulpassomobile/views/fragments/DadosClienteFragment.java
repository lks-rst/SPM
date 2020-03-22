package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.util.customViews.SpinnerFilter;
import br.com.sulpasso.sulpassomobile.views.Pedido;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.AlertCortesDevolucaoTitulos;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.AlertDetalhesCliente;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.AlertJustificativaPedido;

/**
 * Created by Lucas on 17/08/2016.
 */
public class DadosClienteFragment extends Fragment implements AlertDetalhesCliente.Callback, AlertCortesDevolucaoTitulos.Callback, AlertJustificativaPedido.Callback
{
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    private Pedido activity;
    private Spinner fdcSpnrClientes;
    private Spinner fdcSpnrNaturezas;
    private Spinner fdcSpnrPrazos;
    private Spinner fdcSpnrMotivos;

    private SpinnerFilter spnrFil;

    private Boolean ESPECIAL = true;

    ArrayList<String> full;
    ArrayList<String> source;

    public DadosClienteFragment(){}
/**************************************************************************************************/
/**********************************FRAGMENT LIFE CICLE*********************************************/
/**************************************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_dados_cliente, /*container, false*/null);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        full = new ArrayList<>();
        source = new ArrayList<>();

        this.setUpLayout();

        // Create an object of the Android_Gesture_Detector  Class
        Android_Gesture_Detector android_gesture_detector = new Android_Gesture_Detector();
        // Create a GestureDetector
        gestureDetector = new GestureDetector(getActivity().getApplicationContext(), android_gesture_detector);

        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        try
        {
            activity = (Pedido) getActivity();

            getView().setFocusableInTouchMode(true);
            getView().requestFocus();

            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            ((Pedido) getActivity()).verificarEncerramento(0);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString()
                    + " must be Pedido.class calss");
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        this.setUpLayout();

        this.activity.verificarVenda();

        this.fdcSpnrClientes.setSelection(this.activity.restoreClient());
        this.bloquearClicks();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_clientes, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.clientes_agenda:
                limparCampos();
                activity.listarClientes(10);
                break;
            case R.id.clientes_visita:
                limparCampos();
                activity.listarClientes(5);
                break;
            case R.id.clientes_todos:
                limparCampos();
                activity.listarClientes(0);
                break;
            case R.id.clientes_cidade:
                limparCampos();
                activity.listarClientes(3, "");
                break;
            case R.id.clientes_razao:
                limparCampos();
                activity.listarClientes(2, "");
                break;
            case R.id.clientes_fantasia:
                limparCampos();
                activity.listarClientes(1, "");
                break;

            default:
                break;
        }
        /*
        try { this.listarItens(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(), "Erro ao buscar os itens", Toast.LENGTH_LONG).show();
        }

        ((EditText) (getActivity().findViewById(R.id.fcipEdtSearch))).setText("");
        */
        return false;
    }
/**************************************************************************************************/
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************FRAGMENT ACCESS METHODS******************************************/
/**************************************************************************************************/
    public void ajustarLayout()
    {
        ESPECIAL = activity.buscarDadosCliente(R.id.fdcTxtStatus).substring(0, 1).equalsIgnoreCase("E");

        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item, activity.listarNaturezas(ESPECIAL));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.fdcSpnrNaturezas.setAdapter(adapter);

        if(activity.controlaRoteiro())
        {
            this.fdcSpnrMotivos.setOnItemSelectedListener(justificando);
            activity.listarMotivos();
            this.fdcSpnrMotivos.setVisibility(View.VISIBLE);
        }

        if(this.activity.verificarTitulos())
        {
            AlertCortesDevolucaoTitulos dialog = new AlertCortesDevolucaoTitulos();
            dialog.setTargetFragment(this, 1); //request code
            dialog.show(getFragmentManager(), "DIALOG");
        }

        if(this.activity.verificarDevolucoes())
        {
            AlertCortesDevolucaoTitulos dialog = new AlertCortesDevolucaoTitulos();
            dialog.setTargetFragment(this, 1); //request code
            dialog.show(getFragmentManager(), "DIALOG");
        }

        /*
        this.fdcSpnrNaturezas.setAdapter(
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                activity.listarNaturezas(!ESPECIAL)));
        */

        this.fdcSpnrNaturezas.setSelection(this.activity.buscarNatureza());

        this.fdcSpnrNaturezas.setOnItemSelectedListener(selectingData);

        this.fdcSpnrNaturezas.setClickable(this.activity.permitirClick(R.id.fdcSpnrNaturezas));
        this.fdcSpnrNaturezas.setEnabled(this.activity.permitirClick(R.id.fdcSpnrNaturezas));
        this.fdcSpnrPrazos.setClickable(this.activity.permitirClick(R.id.fdcSpnrPrazos));
        this.fdcSpnrPrazos.setEnabled(this.activity.permitirClick(R.id.fdcSpnrPrazos));

        ((EditText) (getActivity().findViewById(R.id.fdcEdtCidade)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtCidade));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtUf)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtUf));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtEnd)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtEnd));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtFone)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtFone));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtCel)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtCel));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtUltima)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtUltima));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtMeta)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtMeta));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtReal)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtReal));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtLimite)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtLimite));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtQuantidade)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtQuantidade));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtMedia)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtMedia));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtDca)))
                .setText(activity.buscarDadosVenda(R.id.fdcEdtDca));

        ((getActivity().findViewById(R.id.fdcBtnDetalhes))).setVisibility(View.VISIBLE);
    }
    public void ajustarLayout2()
    {
        ESPECIAL = activity.buscarDadosCliente(R.id.fdcTxtStatus).substring(0, 1).equalsIgnoreCase("E");

        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item, activity.listarNaturezas(ESPECIAL));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.fdcSpnrNaturezas.setAdapter(adapter);

        if(activity.controlaRoteiro())
        {
            this.fdcSpnrMotivos.setOnItemSelectedListener(justificando);
            activity.listarMotivos();
            this.fdcSpnrMotivos.setVisibility(View.GONE);
        }

        if(this.activity.verificarTitulos())
        {
            AlertCortesDevolucaoTitulos dialog = new AlertCortesDevolucaoTitulos();
            dialog.setTargetFragment(this, 1); //request code
            dialog.show(getFragmentManager(), "DIALOG");
        }

        if(this.activity.verificarDevolucoes())
        {
            AlertCortesDevolucaoTitulos dialog = new AlertCortesDevolucaoTitulos();
            dialog.setTargetFragment(this, 1); //request code
            dialog.show(getFragmentManager(), "DIALOG");
        }

        /*
        this.fdcSpnrNaturezas.setAdapter(
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                activity.listarNaturezas(!ESPECIAL)));
        */

        this.fdcSpnrNaturezas.setSelection(this.activity.buscarNatureza());

        this.fdcSpnrNaturezas.setOnItemSelectedListener(selectingData);

        this.fdcSpnrNaturezas.setClickable(this.activity.permitirClick(R.id.fdcSpnrNaturezas));
        this.fdcSpnrNaturezas.setEnabled(this.activity.permitirClick(R.id.fdcSpnrNaturezas));
        this.fdcSpnrPrazos.setClickable(this.activity.permitirClick(R.id.fdcSpnrPrazos));
        this.fdcSpnrPrazos.setEnabled(this.activity.permitirClick(R.id.fdcSpnrPrazos));

        ((EditText) (getActivity().findViewById(R.id.fdcEdtCidade)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtCidade));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtUf)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtUf));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtEnd)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtEnd));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtFone)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtFone));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtCel)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtCel));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtUltima)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtUltima));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtMeta)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtMeta));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtReal)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtReal));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtLimite)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtLimite));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtQuantidade)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtQuantidade));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtMedia)))
                .setText(activity.buscarDadosCliente(R.id.fdcEdtMedia));
        ((EditText) (getActivity().findViewById(R.id.fdcEdtDca)))
                .setText(activity.buscarDadosVenda(R.id.fdcEdtDca));

        ((getActivity().findViewById(R.id.fdcBtnDetalhes))).setVisibility(View.VISIBLE);
    }

    public void ajustarPrazos(int posicao)
    {
        ArrayAdapter adapter = null;

        if(ESPECIAL)
        {
            adapter = new ArrayAdapter(
                    getActivity().getApplicationContext(),
                    R.layout.spinner_item,
                    activity.listarPrazos());
        }
        else
        {
            adapter = new ArrayAdapter(
                    getActivity().getApplicationContext(),
                    R.layout.spinner_item,
                    activity.listarPrazos(posicao));
        }

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.fdcSpnrPrazos.setAdapter(adapter);

        /*
        this.fdcSpnrPrazos.setAdapter(
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                activity.listarPrazos(posicao)));
        */

        this.fdcSpnrPrazos.setSelection(this.activity.buscarPrazo());
        this.fdcSpnrPrazos.setOnItemSelectedListener(selectingData);

        this.fdcSpnrPrazos.setClickable(this.activity.permitirClick(R.id.fdcSpnrPrazos));
        this.fdcSpnrPrazos.setEnabled(this.activity.permitirClick(R.id.fdcSpnrPrazos));
    }

    public void bloquearClicks()
    {
        this.fdcSpnrNaturezas.setClickable(this.activity.permitirClick(R.id.fdcSpnrNaturezas));
        this.fdcSpnrNaturezas.setEnabled(this.activity.permitirClick(R.id.fdcSpnrNaturezas));
        this.fdcSpnrPrazos.setClickable(this.activity.permitirClick(R.id.fdcSpnrPrazos));
        this.fdcSpnrPrazos.setEnabled(this.activity.permitirClick(R.id.fdcSpnrPrazos));
        this.fdcSpnrClientes.setClickable(this.activity.permitirClick(R.id.fdcSpnrClientes));
        this.fdcSpnrClientes.setEnabled(this.activity.permitirClick(R.id.fdcSpnrClientes));
    }

    public void apresentarLista(ArrayList<String> itens, int tipo, Context ctx)
    {
        /*
        Context ctx = null;
        if(getActivity() == null)
            ctx = getContext();
        else
            ctx = getActivity().getApplicationContext();
        */

        if(tipo == 1)
        {
            /*
            ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itens);
             */

            /*
            ArrayAdapter adapter = new ArrayAdapter(ctx, R.layout.spinner_item, itens);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            this.fdcSpnrClientes.setAdapter(adapter);

            this.spnrFil.AddList(ctx, itens);
            */
            this.full = itens;
            toFull();
        }
        else
        {
            ArrayAdapter adapterMotivos = new ArrayAdapter(ctx, R.layout.spinner_item, itens);
            adapterMotivos.setDropDownViewResource(R.layout.spinner_dropdown_item);
            this.fdcSpnrMotivos.setAdapter(adapterMotivos);
        }

    }
/**************************************************************************************************/
/******************************END OF FRAGMENT ACCESS METHODS**************************************/
/**************************************************************************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
/**************************************************************************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        this.fdcSpnrClientes = (Spinner) (getActivity().findViewById(R.id.fdcSpnrClientes));
        this.fdcSpnrNaturezas = (Spinner) (getActivity().findViewById(R.id.fdcSpnrNaturezas));
        this.fdcSpnrPrazos = (Spinner) (getActivity().findViewById(R.id.fdcSpnrPrazos));
        this.fdcSpnrMotivos = (Spinner) (getActivity().findViewById(R.id.fdcSpnrMotivos));
        this.fdcSpnrMotivos.setVisibility(View.GONE);

        this.spnrFil = (SpinnerFilter) (getActivity().findViewById(R.id.spnrFilter));

        this.fdcSpnrClientes.setOnItemSelectedListener(selectingData);

        ((EditText) (getActivity().findViewById(R.id.filterBusca))).addTextChangedListener(toFilter);


        //http://pt.broculos.net/2013/09/how-to-change-spinner-text-size-color.html#.WYIjmVGQy01
        activity.listarClientes(activity.consultaClientesInicial());

        ((getActivity().findViewById(R.id.fdcBtnDetalhes))).setOnClickListener(detalhes);

        ((getActivity().findViewById(R.id.fdcBtnDetalhes))).setVisibility(View.GONE);
        /*
        this.fdcSpnrClientes.setAdapter( new ArrayAdapter<String>(
            getActivity().getApplicationContext(),
                R.layout.spinner_item,
                activity.listarClientes(0, "")));
        */

        try{ (getActivity().findViewById(R.id.srvRollMain)).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }
        try{ (getActivity().findViewById(R.id.llMainCliData)).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }

        this.fdcSpnrClientes.setOnTouchListener(gestureListener);
        this.fdcSpnrNaturezas.setOnTouchListener(gestureListener);
        this.fdcSpnrPrazos.setOnTouchListener(gestureListener);

        (getActivity().findViewById(R.id.fdcEdtCidade)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtUf)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtEnd)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtFone)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtCel)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtUltima)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtMeta)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtReal)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtLimite)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtQuantidade)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtMedia)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.fdcEdtDca)).setOnTouchListener(gestureListener);
    }

    private void limparCampos()
    {
        ((EditText) (getActivity().findViewById(R.id.fdcEdtCidade))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtUf))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtEnd))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtFone))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtCel))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtUltima))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtMeta))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtReal))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtLimite))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtQuantidade))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtMedia))).setText("");
        ((EditText) (getActivity().findViewById(R.id.fdcEdtDca))).setText("");

        this.fdcSpnrNaturezas.setAdapter(
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<String>()));

        this.fdcSpnrPrazos.setAdapter(
            new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.support.design.R.layout.support_simple_spinner_dropdown_item,
                new ArrayList<String>()));
    }

    private void apresentarDetalhes()
    {
        AlertDetalhesCliente dialog = new AlertDetalhesCliente();
        dialog.setTargetFragment(this, 1); //request code
        dialog.show(getFragmentManager(), "DIALOG");
    }

    private void confirmarJustificativa()
    {
        AlertJustificativaPedido dialog = new AlertJustificativaPedido();
        dialog.setTargetFragment(this, 1); //request code
        dialog.show(getFragmentManager(), "DIALOG");
    }
/**************************************************************************************************/
/*****************************   END OF FRAGMENT FUNCTIONAL METHODS   *****************************/
/**************************************************************************************************/
/*****************************     CLICK LISTENERS FOR THE UI         *****************************/
/**************************************************************************************************/
    private AdapterView.OnItemSelectedListener selectingData = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (parent.getId())
            {
                case R.id.fdcSpnrClientes :
                    if(position > 0)
                    {
                        ((EditText) getActivity().findViewById(R.id.fdcEdtTabela)).setText("");

                        String selected = source.get(position);
                        String trueItem = "";

                        int truePosition;
                        for(truePosition = 0; truePosition < full.size(); truePosition++)
                        {
                            trueItem = full.get(truePosition);

                            if(trueItem.indexOf(selected) != -1)
                                break;
                        }

                        activity.selecionarCliente(truePosition -1);
//                        activity.setSearchType(TiposBuscaItens.FAIL);
                    }
                break;
                case R.id.fdcSpnrNaturezas :
                    ajustarPrazos(position);
                break;
                case R.id.fdcSpnrPrazos :
                    ((EditText) getActivity().findViewById(R.id.fdcEdtTabela))
                        .setText(String.format(activity.getApplicationContext()
                        .getResources().getString(R.string.str_tab)
                        , String.valueOf(String.valueOf(activity.selecionarPrazo(position)))));
                    ((EditText) getActivity().findViewById(R.id.fdcEdtPrazo)).setText(String.format
                            (activity.getApplicationContext().getResources()
                            .getString(R.string.str_prazo), activity.selecionarPrazo()));
                break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { /******/ }
    };

    private AdapterView.OnItemSelectedListener justificando = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            if (position > 0 && activity.codigoMotivo(position) != 500)
            {
                confirmarJustificativa();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { /******/ }
    };

    private AdapterView.OnItemSelectedListener fakeSelection = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (((Spinner) parent).getId())
            {
                case R.id.fdcSpnrNaturezas :
                    fdcSpnrNaturezas.setOnItemSelectedListener(selectingData);
                    break;
                case R.id.fdcSpnrPrazos :
                    fdcSpnrPrazos.setOnItemSelectedListener(selectingData);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { /******/ }
    };

    private View.OnClickListener detalhes = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            apresentarDetalhes();
        }
    };

    private TextWatcher toFilter = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.toString().trim().length() >= 1)
            {
                if(fdcSpnrClientes.getSelectedItemPosition() < 1)
                    filter(s.toString().toUpperCase());
            }
            else
            {
                Toast.makeText(getActivity().getApplicationContext(), "A consulta não pode ser realizada apenas com espaços", Toast.LENGTH_LONG).show();

                if(fdcSpnrClientes.getSelectedItemPosition() < 1)
                    toFull();
            }
        }
    };

    protected void filter(String pattern)
    {
        this.source.clear();
        this.source.add("SELECIONE UM CLIENTE");

        String item;

        for(int i = 0; i < this.full.size(); i++)
        {
            item = this.full.get(i);

            String[] itens = item.split(" - ");

            if(itens.length > 1)
            {
                if(itens[1].indexOf(pattern) == 0)
                    this.source.add(this.full.get(i));
            }
            /*
            item = this.full.get(i);

            if(item.indexOf(pattern) != -1)
                this.source.add(this.full.get(i));
            */
        }

        if(this.source.size() == 1)
        {
            this.source.clear();
            this.source.add("NENHUM CLIENTE CORESPONDENTE");
        }

        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, source);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.fdcSpnrClientes.setAdapter(adapter);
    }

    private void toFull()
    {
        this.source.clear();;
        this.source.addAll(full);
        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.spinner_item, source);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.fdcSpnrClientes.setAdapter(adapter);
    }
/**************************************************************************************************/
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/**************************************************************************************************/
/**************************************************************************************************/
/*****************************************INTERFACES METHODS***************************************/
/**************************************************************************************************/
    @Override
    public ArrayList<String> buscarCliente() { return this.activity.buscarAdicionais(); }

    @Override
    public int buscarTipo() { return ((Pedido) getActivity()).buscarTipo(); }

    @Override
    public ArrayList<String> buscarItens() { return ((Pedido) getActivity()).buscarItens(); }

    @Override
    public ArrayList<String> buscarDetalhes()
    {
        return ((Pedido) getActivity()).buscarDetalhes();
    }

    @Override
    public void justificarPedido(int opcao)
    {
        if(opcao == 0)
        {
            activity.alterarFragmento(1);
        }
        else
        {
            activity.salvarPedido(true);
        }
    }
/**************************************************************************************************/
/************************************END OF INTERFACES METHODS*************************************/
/**************************************************************************************************/
    private class Android_Gesture_Detector implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener
    {
        @Override
        public boolean onDown(MotionEvent e) { return false; }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) { return false; }

        @Override
        public boolean onSingleTapUp(MotionEvent e) { return false; }

        @Override
        public void onShowPress(MotionEvent e) { /*****/ }

        @Override
        public boolean onDoubleTap(MotionEvent e) { return false; }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) { return false; }

        @Override
        public void onLongPress(MotionEvent e) { /*****/ }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
        {
                /*
                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                int width = size.x;
                int height = size.y;
                int scrollUpBegin = (int) height - ((height * 20) / 100);
                int scrollDownBegin = (int) height - (height - ((height * 20) / 100));
                int scrollEnd = (int) height - ((height * 50) / 100);
                */

            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            e1M = e1;
            e2M = e2;
            velocityXM = velocityX;
            velocityYM = velocityY;

            /*
            Handler handler1 = new Handler(Looper.getMainLooper());
            handler1.postDelayed(r, 1);

            return true;
            */
            if(Math.abs(velocityXM) > Math.abs(velocityYM))
            {
                if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                {
                    Resources resources = getResources();
                    Configuration config = resources.getConfiguration();
                    DisplayMetrics dm = resources.getDisplayMetrics();
                    // Note, screenHeightDp isn't reliable
                    // (it seems to be too small by the height of the status bar),
                    // but we assume screenWidthDp is reliable.
                    // Note also, dm.widthPixels,dm.heightPixels aren't reliably pixels
                    // (they get confused when in screen compatibility mode, it seems),
                    // but we assume their ratio is correct.
                    double screenWidthInPixels = (double)config.screenWidthDp * dm.density;
                    double halfScreenWidthInPixels = screenWidthInPixels / 50;
                    float x1, x2, xd;

                    x1 = e1M.getX();
                    x2 = e2M.getX();
                    xd = x2 - x1;

                    if (e1M.getX() < e2M.getX()/* && xd > halfScreenWidthInPixels*/) { apresentarDetalhes(); } //Left to Right swipe
                    else
                    {
                        if (e1M.getX() > e2M.getX()/* && Math.abs(xd) > halfScreenWidthInPixels*/) //Right to Left swipe
                        {
                            if(((getActivity().findViewById(R.id.fdcSpnrMotivos))).getVisibility() == View.GONE ||
                                    ((getActivity().findViewById(R.id.fdcSpnrMotivos))).getVisibility() == View.INVISIBLE ||
                                    activity.verificarJustificativa()) { activity.alterarFragmento(1); }
                            else
                            {
                                Toast.makeText(activity.getApplicationContext(),
                                        "ATENÇÃO!\nEscolha uma justificativa para o pedido fora da data padrão."
                                        , Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(activity.getApplicationContext(),
                                    "ATENÇÃO!\nPara passar para a próxima tela, o movimento deve cobrir mais da metade da tela do dispositivo."
                                    , Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else { Toast.makeText( activity.getApplicationContext(), "Escolha um cliente para prosseguir", Toast.LENGTH_LONG).show(); }
            }
            else
            {
                if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                {
                    if (e1.getY() < e2.getY()) { apresentarDetalhes(); } //Up to Down swipe
                    if (e1.getY() > e2.getY()) //Down to Up swipe
                    {
                        if(activity.verificarJustificativa()) { activity.alterarFragmento(1); }
                        else
                        {
                            Toast.makeText(activity.getApplicationContext(),
                                    "ATENÇÃO!\nEscolha uma justificativa para o pedido fora da data padrão."
                                    , Toast.LENGTH_LONG).show();
                        }
                    }
                }
                else { Toast.makeText( activity.getApplicationContext(), "Escolha um cliente para prosseguir", Toast.LENGTH_LONG).show(); }
            }

            return true;
        }
    }

    MotionEvent e1M;
    MotionEvent e2M;
    float velocityXM;
    float velocityYM;

    final Runnable r = new Runnable() {
        public void run() {
            try
            {
                if(Math.abs(velocityXM) > Math.abs(velocityYM))
                {
                    if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                    {
                        Resources resources = getResources();
                        Configuration config = resources.getConfiguration();
                        DisplayMetrics dm = resources.getDisplayMetrics();
                        // Note, screenHeightDp isn't reliable
                        // (it seems to be too small by the height of the status bar),
                        // but we assume screenWidthDp is reliable.
                        // Note also, dm.widthPixels,dm.heightPixels aren't reliably pixels
                        // (they get confused when in screen compatibility mode, it seems),
                        // but we assume their ratio is correct.
                        double screenWidthInPixels = (double)config.screenWidthDp * dm.density;
                        double halfScreenWidthInPixels = screenWidthInPixels / 40;
                        float x1, x2, xd;

                        x1 = e1M.getX();
                        x2 = e2M.getX();
                        xd = x2 - x1;

                        if (e1M.getX() < e2M.getX() && xd > halfScreenWidthInPixels) { apresentarDetalhes(); } //Left to Right swipe
                        else
                        {
                            if (e1M.getX() > e2M.getX() && Math.abs(xd) > halfScreenWidthInPixels) //Right to Left swipe
                            {
                                if(((getActivity().findViewById(R.id.fdcSpnrMotivos))).getVisibility() == View.GONE ||
                                        ((getActivity().findViewById(R.id.fdcSpnrMotivos))).getVisibility() == View.INVISIBLE ||
                                        activity.verificarJustificativa()) { activity.alterarFragmento(1); }
                                else
                                {
                                    Toast.makeText(activity.getApplicationContext(),
                                            "ATENÇÃO!\nEscolha uma justificativa para o pedido fora da data padrão."
                                            , Toast.LENGTH_LONG).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(activity.getApplicationContext(),
                                    "ATENÇÃO!\nPara passar para a próxima tela, o movimento deve cobrir mais da metade da tela do dispositivo."
                                    , Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    else { Toast.makeText( activity.getApplicationContext(), "Escolha um cliente para prosseguir", Toast.LENGTH_LONG).show(); }
                }
                else
                {
                    if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                    {
                        Resources resources = getResources();
                        Configuration config = resources.getConfiguration();
                        DisplayMetrics dm = resources.getDisplayMetrics();
                        // Note, screenHeightDp isn't reliable
                        // (it seems to be too small by the height of the status bar),
                        // but we assume screenWidthDp is reliable.
                        // Note also, dm.widthPixels,dm.heightPixels aren't reliably pixels
                        // (they get confused when in screen compatibility mode, it seems),
                        // but we assume their ratio is correct.
                        double screenWidthInPixels = (double)config.screenWidthDp * dm.density;
                        double halfScreenWidthInPixels = screenWidthInPixels / 1;
                        float x1, x2, xd;

                        x1 = e1M.getX();
                        x2 = e2M.getX();
                        xd = x2 - x1;

                        if (e1M.getX() < e2M.getX() && xd > halfScreenWidthInPixels) //Left to Right swipe { apresentarDetalhes(); } //Up to Down swipe
                        if (e1M.getY() > e2M.getY() && Math.abs(xd) > halfScreenWidthInPixels) //Down to Up swipe
                        {
                            if(activity.verificarJustificativa()) { activity.alterarFragmento(1); }
                            else
                            {
                                Toast.makeText(activity.getApplicationContext(),
                                        "ATENÇÃO!\nEscolha uma justificativa para o pedido fora da data padrão."
                                        , Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    else { Toast.makeText( activity.getApplicationContext(), "Escolha um cliente para prosseguir", Toast.LENGTH_LONG).show(); }
                }
            }
            catch (Exception e) { /*****/ }
        }
    };
}
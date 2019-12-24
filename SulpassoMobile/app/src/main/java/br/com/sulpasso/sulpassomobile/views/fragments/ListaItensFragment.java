package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.Adapters.AdapterItensPedido;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaItens;
import br.com.sulpasso.sulpassomobile.views.Pedido;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.DetalhesPrePedidoValores;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.DetalhesPrepedido;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.GrupoSelection;

/*
    Todo: Criar campo de configuração apra inserção direta de prepedido;
 */

/**
 * Created by Lucas on 17/08/2016.
 */
public class ListaItensFragment extends Fragment implements
        GrupoSelection.Callback, DetalhesPrepedido.Callback, DetalhesPrePedidoValores.Callback
{
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    private ListView fliLiItens;

    private String pesquisaAtual;

    private long memoryNeeded = 0;

    public ListaItensFragment(){}
/**********************************FRAGMENT LIFE CICLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        memoryNeeded = totalRamMemorySize() / 10;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_lista_itens_busca, /*container, false*/null);
    }

    @Override
    public void onStart()
    {
        super.onStart();

        // Create an object of the Android_Gesture_Detector  Class
        Android_Gesture_Detector android_gesture_detector = new Android_Gesture_Detector();
        // Create a GestureDetector
        gestureDetector = new GestureDetector(getActivity().getApplicationContext(), android_gesture_detector);

        pesquisaAtual = "";

        gestureListener = new View.OnTouchListener()
        {
            public boolean onTouch(View v, MotionEvent event)
            {
                if (gestureDetector.onTouchEvent(event)) { return true; }
                return false;
            }
        };
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        String cn = getActivity().getLocalClassName();

        if(!getActivity().getLocalClassName().equals("br.com.sulpasso.sulpassomobile.views.Pedido"))
        {
            throw new ClassCastException(getActivity().toString()
                    + " must be Pedido.class calss");
        }
        else
        {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();

            getView().setOnKeyListener(new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN)
                    {
                        if (keyCode == KeyEvent.KEYCODE_BACK)
                        {
                            ((Pedido) getActivity()).verificarEncerramento(0);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
        /*
        try { activity = (Pedido) getActivity();}
        catch (ClassCastException e)
        {
            throw new ClassCastException(getActivity().toString()
                    + " must be Pedido.class calss");
        }
        */
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_pedidos_filtro_itens, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.itens_promo:
                ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).setHint(
                        getActivity().getResources().getString(R.string.str_busca) + " " +
                                getActivity().getResources().getString(R.string.str_promocao));
                ((Pedido) getActivity()).setSearchType(TiposBuscaItens.PROMO);
                this.listarItens();
                break;
            case R.id.itens_desc_ini:
                ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).setHint(
                    getActivity().getResources().getString(R.string.str_busca) + " " +
                        getActivity().getResources().getString(R.string.hnt_descIni));
                ((Pedido) getActivity()).setSearchType(TiposBuscaItens.DESC_INI);
                break;
            case R.id.itens_ref:
                ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).setHint(
                    getActivity().getResources().getString(R.string.str_busca) + " " +
                        getActivity().getResources().getString(R.string.hnt_ref));
                ((Pedido) getActivity()).setSearchType(TiposBuscaItens.REF);
                break;
            case R.id.itens_grupo:
                ((Pedido) getActivity()).setSearchType(TiposBuscaItens.GRUPO);
                GrupoSelection dialog = new GrupoSelection();
                dialog.setTargetFragment(this, 1); //request code
                dialog.show(getFragmentManager(), "DIALOG");
                break;

            case R.id.itens_pre_pedido:
                ((Pedido) getActivity()).setSearchType(TiposBuscaItens.PRE);
                this.manipulacaoPrePedido();
                break;
            case R.id.itens_gravosos:
                ((Pedido) getActivity()).setSearchType(TiposBuscaItens.GRAVOSOS);
                this.listarItens();
                break;
            case R.id.itens_mix:
                ((Pedido) getActivity()).setSearchType(TiposBuscaItens.MIX);
                ((Pedido) getActivity()).buscarTipoCliente();
                this.listarItens();
                break;
            case R.id.itens_desc_any:
                ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).setHint(
                    getActivity().getResources().getString(R.string.str_busca) + " " +
                        getActivity().getResources().getString(R.string.hnt_descAny));
                ((Pedido) getActivity()).setSearchType(TiposBuscaItens.DESC_ANY);
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

    @Override
    public void onResume()
    {
        super.onResume();

        /*
        try {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();

            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            ((Pedido) getActivity()).verificarEncerramento();
                            return true;
                        }
                    }
                    return false;
                }
            });

        }
        catch (Exception ex) { Toast.makeText(getActivity().getApplicationContext(), "Erro ao carregar keylistener", Toast.LENGTH_LONG).show();}
        */

        this.setUpLayout();
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        this.fliLiItens = (ListView) (getActivity().findViewById(R.id.flibLiItens));
        this.fliLiItens.setOnItemClickListener(selectingItems);

//        if(!((Pedido) getActivity()).verifyItens())
//        {
            this.listarItens();
//        }

        String hint = getActivity().getResources().getString(R.string.str_busca) + " ";

        switch (((Pedido) getActivity()).buscarTipoConsulta())
        {
            case 1 :
                hint += getActivity().getResources().getString(R.string.hnt_descIni);
                break;
            case 2 :
                hint += getActivity().getResources().getString(R.string.hnt_descAny);
                break;
            case 3 :
                hint += getActivity().getResources().getString(R.string.hnt_ref);
                break;
            case 7 :
                this.manipulacaoPrePedido();
                break;
            default :
                break;
        }

        ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).setHint(hint);

        /*
        ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).addTextChangedListener(search);
        */

        ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT)
                {
                    long memoryFree = freeRamMemorySize();
                    if(memoryFree >= memoryNeeded)
                    {
                        if(!pesquisaAtual.equalsIgnoreCase(((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).getEditableText().toString()))
                        {
                            ((Pedido) getActivity()).setPesquisar(false);
                            Editable s = ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).getEditableText();

                            pesquisaAtual = s.toString().toUpperCase();

                            ((Pedido) getActivity()).setSearchData(pesquisaAtual);

                            listarItens();
                            return true;
                        }
                        else
                        {
                            Toast.makeText(getActivity().getApplicationContext(), "Espere a consulta anterior terminar antes de iniciar a próxima", Toast.LENGTH_LONG).show();
                            return true;
                        }
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(), "Atenção!\nMemória insuficiente para realizar a pesquisa, para prosseguir encerre algumas aplicações.", Toast.LENGTH_LONG).show();
                        return true;
                    }
                }
                return false;
            }
        });

        ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).setOnFocusChangeListener(alteracaoFoco);

        /*
        ((EditText) (getActivity().findViewById(R.id.flibEdtSearch)))
                .setText(String.valueOf(((Pedido) getActivity()).itensVendidos()));
        */

        try{ (getActivity().findViewById(R.id.scrListItem)).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }
        try{ (getActivity().findViewById(R.id.relMainListItem)).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }
        try{ (getActivity().findViewById(R.id.llMainListItem)).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }

        this.fliLiItens.setOnTouchListener(gestureListener);
        getActivity().findViewById(R.id.flibEdtSearch).setOnTouchListener(gestureListener);
    }

    public void listarItens()
    {
        /*
        TODO: VErificar uma forma melhor de apresentar estes itens;
         */
        AdapterItensPedido adapter = new AdapterItensPedido(getActivity().getApplicationContext(), ((Pedido) getActivity()).listarItens2());

        this.fliLiItens.setAdapter(adapter);

        try { this.fliLiItens.setSelection(((Pedido) getActivity()).posicaoUltimoItemSelecionado()); }
        catch (Exception e) { this.fliLiItens.setSelection(0); }

        /*
        this.fliLiItens.setSelection(4);

        this.fliLiItens.setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                R.layout.default_list_item,
                ((Pedido) getActivity()).listarItens()
            )
        );

        ((Pedido) getActivity()).setPesquisar(true);
        */
    }

    public void ajustarLayout() { /*****/ }

    private void apresentarDialog()
    {
        if(((Pedido) getActivity()).insereDePrePedido())
        {
            DetalhesPrePedidoValores dialog = new DetalhesPrePedidoValores();
            dialog.setTargetFragment(this, 1); //request code
            dialog.show(getFragmentManager(), "DIALOG");
        }
        else
        {
            DetalhesPrepedido dialog = new DetalhesPrepedido();
            dialog.setTargetFragment(this, 1); //request code
            dialog.show(getFragmentManager(), "DIALOG");
        }
    }

    private void manipulacaoPrePedido()
    {
        boolean pre = ((Pedido) getActivity()).buscarPrePedidos();

        if (pre) { apresentarDialog(); }
        else
        {
            ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).setHint(
                    "Não encontrado o pre pedido");
        }
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
    private View.OnFocusChangeListener alteracaoFoco = new View.OnFocusChangeListener()
    {
        @Override
        public void onFocusChange(View view, boolean b)
        {
            if(view.hasFocus())
            {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }

            view.setOnKeyListener(new View.OnKeyListener()
            {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    //Find the currently focused view, so we can grab the correct window token from it.
                    if(imm.isAcceptingText())
                    {
                        if (event.getAction() == KeyEvent.ACTION_DOWN)
                        {
                            if (keyCode == KeyEvent.KEYCODE_BACK)
                            {
                                ((Pedido) getActivity()).verificarEncerramento(0);
                                return true;
                            }
                        }
                    }
                    else
                    {
                        if (event.getAction() == KeyEvent.ACTION_DOWN)
                        {
                            if (keyCode == KeyEvent.KEYCODE_BACK)
                            {
                                ((Pedido) getActivity()).verificarEncerramento(2);
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });
        }
    };

    private AdapterView.OnItemClickListener selectingItems = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
//            if(position > 0)
//            {
              ((Pedido) getActivity()).selecionarItem(position);
//                ((Pedido) getActivity()).selecionarItem(position - 1);
//                ((TextView) getActivity().findViewById(R.id.fliTxtDados)).setText(((Pedido) getActivity()).selecionarItem(position - 1));
//                ajustarLayout();
//            }
        }
    };

    private AdapterView.OnItemSelectedListener selectingItem = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            if(position > 0)
            {
                ((Pedido) getActivity()).selecionarItem(position - 1);
//                ((TextView) getActivity().findViewById(R.id.fliTxtDados)).setText(((Pedido) getActivity()).selecionarItem(position - 1));
//                ajustarLayout();
            }
        }

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
                ((Pedido) getActivity()).setSearchData(s.toString().toUpperCase());

                listarItens();
            }
        }
    };
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    @Override
    public void accept()
    {
        this.fliLiItens.setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                R.layout.default_list_item,
                ((Pedido) getActivity()).listarItens(4, "")
            )
        );
    }

    @Override
    public ArrayList<String> carregarGrupos()
    {
        try { return ((Pedido) getActivity()).listarGrupos(); }
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
        try { return ((Pedido) getActivity()).listarSubGrupos(); }
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
        try { return ((Pedido) getActivity()).listarDivisoes(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            Toast.makeText(getActivity().getApplicationContext(),
                    getActivity().getResources().getString(R.string.lista_invalida), Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }
    }

    @Override
    public void indicarGrupo(int pos) { ((Pedido) getActivity()).indicarGrupo(pos); }

    @Override
    public void indicarSubGrupo(int pos) { ((Pedido) getActivity()).indicarSubGrupo(pos); }

    @Override
    public void indicarDivisao(int pos) { ((Pedido) getActivity()).indicarDivisao(pos); }

/**************************************************************************************************/
    @Override
    public PrePedido detalhesPrePedido()
    {
        return ((Pedido) getActivity()).detalharPrePedido();
    }

    @Override
    public void exibirDescricaoProduto(int posicao)
    {
        ((Pedido) getActivity()).selecionarItemPre(posicao);
    }
/**************************************************************************************************/
/*********************************END OF ITERFACES METHODS*****************************************/
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
            if(!doingSomething)
            {
                e1M = e1;
                e2M = e2;
                velocityXM = velocityX;
                velocityYM = velocityY;

                doingSomething = true;

                Handler handler1 = new Handler(Looper.getMainLooper());
                handler1.postDelayed(r, 500);
            }

            return false;
            /*
            if(Math.abs(velocityXM) > Math.abs(velocityYM))
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

                if (e1M.getX() < e2M.getX() && xd > halfScreenWidthInPixels) //Left to Right swipe
                {//Se já incluiu um item no pedido, não pode voltar a tela de clientes
                    *//*
                    if((((Pedido)getActivity()).permitirClick(R.id.fdcSpnrClientes)))
                        ((Pedido) getActivity()).alterarFragmento(0);
                    else
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Não e possível alterar o cliente após a inclusão de um item.\nPara verificação avance para o resumo do pedido.",
                                Toast.LENGTH_LONG).show();
                    *//*
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Não e possível alterar o cliente.\nPara verificação avance para o resumo do pedido.",
                            Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (e1M.getX() > e2M.getX() && Math.abs(xd) > halfScreenWidthInPixels) //Right to Left swipe
                    {//Se ainda não incluiu itens, não há resumo nem raão para encerrar o pedido
                        if(!((Pedido)getActivity()).permitirClick(R.id.fdcSpnrClientes))
                            ((Pedido) getActivity()).alterarFragmento(4);
                        else
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Só é possível avançar após a inserção de um item no pedido.",
                                    Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "ATENÇÃO!\nPara passar para a próxima tela, o movimento deve cobrir mais da metade da tela do dispositivo."
                                , Toast.LENGTH_LONG).show();
                    }
                }

                return true;
            }
            else
            { //Basicamente, não respnde a rodar para cima ou para baixo.
                *//*
                if (e1.getY() < e2.getY()) //Up to Down swipe
                {
                    //if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                        ((Pedido) getActivity()).alterarFragmento(0);
                }
                if (e1.getY() > e2.getY()) { ((Pedido) getActivity()).alterarFragmento(4); } //Down to Up swipe
                *//*
                return false;
            }
            */
        }
    }

    MotionEvent e1M;
    MotionEvent e2M;
    float velocityXM;
    float velocityYM;
    Boolean doingSomething = false;

    final Runnable r = new Runnable() {
        public void run() {
            try
            {
                if(Math.abs(velocityXM) > Math.abs(velocityYM))
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
                    double halfScreenWidthInPixels = screenWidthInPixels / 100;
                    float x1, x2, xd;

                    x1 = e1M.getX();
                    x2 = e2M.getX();
                    xd = x2 - x1;

                    if (e1M.getX() < e2M.getX() && xd > halfScreenWidthInPixels) //Left to Right swipe
                    {//Se já incluiu um item no pedido, não pode voltar a tela de clientes
                    /*
                    if((((Pedido)getActivity()).permitirClick(R.id.fdcSpnrClientes)))
                        ((Pedido) getActivity()).alterarFragmento(0);
                    else
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Não e possível alterar o cliente após a inclusão de um item.\nPara verificação avance para o resumo do pedido.",
                                Toast.LENGTH_LONG).show();
                    */
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Não e possível alterar o cliente.\nPara verificação avance para o resumo do pedido.",
                                Toast.LENGTH_LONG).show();
                    }
                    if (e1M.getX() > e2M.getX() && Math.abs(xd) > halfScreenWidthInPixels) //Right to Left swipe
                    {//Se ainda não incluiu itens, não há resumo nem raão para encerrar o pedido
                        if(!((Pedido)getActivity()).permitirClick(R.id.fdcSpnrClientes))
                            ((Pedido) getActivity()).alterarFragmento(4);
                        else
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Só é possível avançar após a inserção de um item no pedido.",
                                    Toast.LENGTH_LONG).show();
                    }

                    doingSomething = false;
                }
                else
                { //Basicamente, não respnde a rodar para cima ou para baixo.
                /*
                if (e1.getY() < e2.getY()) //Up to Down swipe
                {
                    //if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                        ((Pedido) getActivity()).alterarFragmento(0);
                }
                if (e1.getY() > e2.getY()) { ((Pedido) getActivity()).alterarFragmento(4); } //Down to Up swipe
                */
                    doingSomething = false;
                }
            }
            catch (Exception e) { /*****/ }
        }
    };


    private long freeRamMemorySize()
    {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(getActivity().ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;

        return availableMegs;
    }


    private long totalRamMemorySize() {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getActivity().getSystemService(getActivity().ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.totalMem / 1048576L;
        return availableMegs;
    }
}
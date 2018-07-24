package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaItens;
import br.com.sulpasso.sulpassomobile.views.Pedido;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.DetalhesPrePedidoValores;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.DetalhesPrepedido;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.GrupoSelection;

/*
    Todo: Verificar o filtro Mix;
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

    public ListaItensFragment(){}
/**********************************FRAGMENT LIFE CICLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        String cn = getActivity().getLocalClassName();

        if(!getActivity().getLocalClassName().equals("br.com.sulpasso.sulpassomobile.views.Pedido"))
        {
            throw new ClassCastException(getActivity().toString()
                    + " must be Pedido.class calss");
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
        this.listarItens();

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

        ((EditText) (getActivity().findViewById(R.id.flibEdtSearch))).addTextChangedListener(search);

        /*
        ((EditText) (getActivity().findViewById(R.id.flibEdtSearch)))
                .setText(String.valueOf(((Pedido) getActivity()).itensVendidos()));
        */

        //TODO: Verificar (está relacionado ao item cadastrado como à fazer na tela inial)
        /*TEM QUE ACRESCENTAR ESSE ITEM PARA TODOS OS COMPONENTES DA TELA PARA QUE NÃO IMPORTA ONDE SEJA ACIONADO O MOVIMENTO A RESPOSTA SEJA IGUAL*/
        /*AINDA PRECISO VERIFICAR OS RETORNOS SE DEVEM MESMO SER TRUE OU PODE SER FALSE*/
        (getActivity().findViewById(R.id.llMainListItem)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.relMainListItem)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.scrListItem)).setOnTouchListener(gestureListener);

        this.fliLiItens.setOnTouchListener(gestureListener);
        getActivity().findViewById(R.id.flibEdtSearch).setOnTouchListener(gestureListener);
    }

    public void listarItens()
    {
        this.fliLiItens.setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                R.layout.default_list_item,
                ((Pedido) getActivity()).listarItens()
            )
        );
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
        public void onShowPress(MotionEvent e) { Log.d("Gesture ", " onShowPress"); }

        @Override
        public boolean onDoubleTap(MotionEvent e) { return false; }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) { return false; }

        @Override
        public void onLongPress(MotionEvent e) { Log.d("Gesture ", " onLongPress"); }

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
            Log.d("Gesture ", " onScroll");

            if (e1.getY() < e2.getY())
            {
                Log.d("Gesture ", " Scroll Down");
                    /*
                    if (e1.getY() < scrollDownBegin && e2.getY() <= scrollEnd)
                    {
                        Log.d("Gesture ", " Scroll Down");
                    }
                    else { Log.d("Gesture ", " Scroll Down -- To Lower"); }
                    */
            }
            if (e1.getY() > e2.getY())
            {
                Log.d("Gesture ", " Scroll Up");
                    /*
                    if (e1.getY() > scrollUpBegin) { Log.d("Gesture ", " Scroll Up -- To high"); }
                    else { Log.d("Gesture ", " Scroll Up"); }
                    */
            }

            return false;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if(Math.abs(velocityX) > Math.abs(velocityY))
            {
                if (e1.getX() < e2.getX()) //Left to Right swipe
                {
                    //if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                        ((Pedido) getActivity()).alterarFragmento(0);
                }
                if (e1.getX() > e2.getX()) { ((Pedido) getActivity()).alterarFragmento(4); } //Right to Left swipe

                return true;
            }
            else
            {
                /*
                if (e1.getY() < e2.getY()) //Up to Down swipe
                {
                    //if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                        ((Pedido) getActivity()).alterarFragmento(0);
                }
                if (e1.getY() > e2.getY()) { ((Pedido) getActivity()).alterarFragmento(4); } //Down to Up swipe
                */

                return false;
            }
        }
    }
}
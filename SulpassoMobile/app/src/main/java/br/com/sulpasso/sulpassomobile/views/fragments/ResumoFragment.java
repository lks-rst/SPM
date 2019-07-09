package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.views.Pedido;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.AlterarExcluir;

/**
 * Created by Lucas on 03/10/2016.
 */
public class ResumoFragment extends Fragment implements AlterarExcluir.Callback
{
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    private ListView fliSpnrItens;

    private int posicaoAlterar;

    public ResumoFragment(){}
    /**********************************FRAGMENT LIFE CICLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_lista_itens_resumo, /*container, false*/null);
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


        this.setUpLayout();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        if(!getActivity().getLocalClassName().equals("br.com.sulpasso.sulpassomobile.views.Pedido"))
        {
            throw new ClassCastException(getActivity().toString()
                    + " must be Pedido.class calss");
        }
        else
        {
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
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************FRAGMENT ACCESS METHODS******************************************/
/**************************************************************************************************/
    public void atualizarResumo()
    {
        ((ListView) getActivity().findViewById(R.id.flirLiItens)).setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                R.layout.default_list_item,
                ((Pedido) getActivity()).listarResumo()
            )
        );

        ((EditText) getActivity().findViewById(R.id.flirEdtItens)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtItens));
        ((EditText) getActivity().findViewById(R.id.flirEdtValor)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtValor));
        ((EditText) getActivity().findViewById(R.id.flirEdtVolume)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtVolume));
        ((EditText) getActivity().findViewById(R.id.flirEdtCont)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCont));
    }
/**************************************************************************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        try {

            ((EditText) getActivity().findViewById(R.id.flirEdtCliente)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCliente));
            ((EditText) getActivity().findViewById(R.id.flirEdtCidade)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCidade));
            ((EditText) getActivity().findViewById(R.id.flirEdtNaturesa)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtNaturesa));
            ((EditText) getActivity().findViewById(R.id.flirEdtTabela)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtTabela));
            ((EditText) getActivity().findViewById(R.id.flirEdtTipo)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtTipo));

        }
        catch (Exception e)
        {
            Toast.makeText(getActivity().getApplicationContext(), "Selecione um cliente para realizar um pedido.", Toast.LENGTH_LONG).show();
            ((Pedido) getActivity()).alterarFragmento(0);

        }
        ((ListView) getActivity().findViewById(R.id.flirLiItens)).setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                    R.layout.default_list_item,
                ((Pedido) getActivity()).listarResumo()
            )
        );
//        ((ListView) getActivity().findViewById(R.id.flirLiItens)).setOnItemClickListener(selectingItems);
        ((ListView) getActivity().findViewById(R.id.flirLiItens)).setOnItemLongClickListener(selectingItems2);
        ((EditText) getActivity().findViewById(R.id.flirEdtItens)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtItens));
        ((EditText) getActivity().findViewById(R.id.flirEdtValor)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtValor));
        ((EditText) getActivity().findViewById(R.id.flirEdtVolume)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtVolume));
        ((EditText) getActivity().findViewById(R.id.flirEdtCont)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCont));

        try{ (getActivity().findViewById(R.id.scrListItem)).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }
        try{ (getActivity().findViewById(R.id.scrMainListRes)).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }
        try{ (getActivity().findViewById(R.id.llMainListRes)).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }


        (getActivity().findViewById(R.id.flirLiItens)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.flirEdtItens)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.flirEdtValor)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.flirEdtVolume)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.flirEdtCont)).setOnTouchListener(gestureListener);

        (getActivity().findViewById(R.id.flirEdtCliente)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.flirEdtCidade)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.flirEdtNaturesa)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.flirEdtTabela)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.flirEdtTipo)).setOnTouchListener(gestureListener);
    }

    private void apresentarDialog()
    {
        AlterarExcluir dialog = new AlterarExcluir();
        dialog.setTargetFragment(this, 1); //request code
        dialog.show(getFragmentManager(), "DIALOG");
    }
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/

    private AdapterView.OnItemLongClickListener selectingItems2 = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            posicaoAlterar = i;
            apresentarDialog();
            return false;
        }
    };


    private AdapterView.OnItemClickListener selectingItems = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            posicaoAlterar = position;
            apresentarDialog();
        }
    };

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    @Override
    public String descricaoItem() {  return ((Pedido) getActivity()).descricaoItem(posicaoAlterar); }

    @Override
    public void fazer(int opt)
    {
        if(opt == 1)
        {
            ((Pedido) getActivity()).alterarItem(posicaoAlterar, opt);
        }
        else
        {
            ((Pedido) getActivity()).alterarItem(posicaoAlterar, opt);
        }
    }
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
                {
                    //if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                    ((Pedido) getActivity()).alterarFragmento(1);
                }
                else
                {
                    if (e1M.getX() > e2M.getX() && Math.abs(xd) > halfScreenWidthInPixels)//Right to Left swipe
                    {
                        ((Pedido) getActivity()).alterarFragmento(3);
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
            else { return false; }
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
                    {
                        //if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                        ((Pedido) getActivity()).alterarFragmento(1);
                    }
                    if (e1M.getX() > e2M.getX() && Math.abs(xd) > halfScreenWidthInPixels) { ((Pedido) getActivity()).alterarFragmento(3); } //Right to Left swipe

                    doingSomething = false;
                }
                else { /*****/ doingSomething = false;}
            }
            catch (Exception e) { /*****/ }
        }
    };
}
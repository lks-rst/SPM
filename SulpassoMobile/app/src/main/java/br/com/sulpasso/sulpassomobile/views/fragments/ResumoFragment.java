package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
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
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        ((EditText) getActivity().findViewById(R.id.flirEdtCliente)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCliente));
        ((EditText) getActivity().findViewById(R.id.flirEdtCidade)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCidade));
        ((EditText) getActivity().findViewById(R.id.flirEdtNaturesa)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtNaturesa));
        ((EditText) getActivity().findViewById(R.id.flirEdtTabela)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtTabela));
        ((EditText) getActivity().findViewById(R.id.flirEdtTipo)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtTipo));
        ((ListView) getActivity().findViewById(R.id.flirLiItens)).setAdapter
        (
            new ArrayAdapter<String>
            (
                getActivity().getApplicationContext(),
                    R.layout.default_list_item,
                ((Pedido) getActivity()).listarResumo()
            )
        );
        ((ListView) getActivity().findViewById(R.id.flirLiItens)).setOnItemClickListener(selectingItems);
        ((EditText) getActivity().findViewById(R.id.flirEdtItens)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtItens));
        ((EditText) getActivity().findViewById(R.id.flirEdtValor)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtValor));
        ((EditText) getActivity().findViewById(R.id.flirEdtVolume)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtVolume));
        ((EditText) getActivity().findViewById(R.id.flirEdtCont)).setText(((Pedido) getActivity()).cabecahoPedido(R.id.flirEdtCont));

        (getActivity().findViewById(R.id.llMainListRes)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.scrMainListRes)).setOnTouchListener(gestureListener);
        (getActivity().findViewById(R.id.relMainListRes)).setOnTouchListener(gestureListener);

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

            Toast.makeText(getActivity().getApplicationContext(), "Clicado no botao excluir", Toast.LENGTH_LONG).show();
            ((ListView) getActivity().findViewById(R.id.flirLiItens)).setAdapter
            (
                new ArrayAdapter<String>
                (
                    getActivity().getApplicationContext(),
                    R.layout.default_list_item,
                    ((Pedido) getActivity()).listarResumo()
                )
            );
        }
        else
        {
            Toast.makeText(getActivity().getApplicationContext(), "Clicado no botao alterar", Toast.LENGTH_LONG).show();
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
                    ((Pedido) getActivity()).alterarFragmento(1);
                }
                if (e1.getX() > e2.getX()) { ((Pedido) getActivity()).alterarFragmento(3); } //Right to Left swipe

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
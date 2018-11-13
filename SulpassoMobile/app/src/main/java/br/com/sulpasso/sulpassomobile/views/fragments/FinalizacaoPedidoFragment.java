package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.views.Pedido;

/**
 * Created by Lucas on 17/08/2016.
 */
public class FinalizacaoPedidoFragment extends Fragment
{
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    private Spinner ffpSpnrNaturezas;
    private Spinner ffpSpnrPrazos;
    private Spinner ffpSpnrJustificativa;

    public FinalizacaoPedidoFragment(){}
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
        return inflater.inflate(R.layout.fragment_finalizacao_pedido, /*container, false*/null);
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
            ((Pedido) getActivity()).indicarToatalPedido();

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
/**************************************************************************************************/
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************FRAGMENT ACCESS METHODS******************************************/
/**************************************************************************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        /*
        Inserção dos valores nos campos
         */
        ((EditText) (getActivity().findViewById(R.id.ffpEdtTotalItens)))
            .setText(String.valueOf(((Pedido) getActivity()).valorVendido()));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtTotalPedido)))
            .setText(String.valueOf(((Pedido) getActivity()).valorVendido()));

        ((EditText) (getActivity().findViewById(R.id.ffpEdtDesconto))).setText(String.valueOf(0));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtFrete))).setText(String.valueOf(0));

        ((EditText) (getActivity().findViewById(R.id.ffpEdtCliente)))
            .setText(String.valueOf(((Pedido) getActivity()).cabecahoPedido(R.id.ffpEdtCliente)));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtCidade)))
            .setText(String.valueOf(((Pedido) getActivity()).cabecahoPedido(R.id.ffpEdtCidade)));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtTab)))
            .setText(String.valueOf(((Pedido) getActivity()).cabecahoPedido(R.id.ffpEdtTab)));

        ((EditText) (getActivity().findViewById(R.id.ffpEdtObsCpd)))
                .setText(String.valueOf(((Pedido) getActivity()).cabecahoPedido(R.id.ffpEdtObsCpd)));
        ((EditText) (getActivity().findViewById(R.id.ffpEdtObsNfe)))
                .setText(String.valueOf(((Pedido) getActivity()).cabecahoPedido(R.id.ffpEdtObsNfe)));

        this.ffpSpnrNaturezas = (Spinner) (getActivity().findViewById(R.id.ffpSpnrNaturezas));
        this.ffpSpnrPrazos = (Spinner) (getActivity().findViewById(R.id.ffpSpnrPrazos));
        this.ffpSpnrJustificativa = (Spinner) (getActivity().findViewById(R.id.ffpSpnrJustificativa));

        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item, ((Pedido) getActivity()).listarNaturezas(false));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        this.ffpSpnrNaturezas.setAdapter(adapter);

        this.ffpSpnrNaturezas.setSelection(((Pedido) getActivity()).buscarNatureza());

        this.ffpSpnrNaturezas.setOnItemSelectedListener(selectingData);
        this.ffpSpnrPrazos.setOnItemSelectedListener(selectingData);

        this.ffpSpnrNaturezas.setClickable(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrNaturezas));
        this.ffpSpnrNaturezas.setEnabled(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrNaturezas));
        this.ffpSpnrPrazos.setClickable(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrPrazos));
        this.ffpSpnrPrazos.setEnabled(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrPrazos));

        /*
        Indicação de calbacks de escuta a alteração de valores nos campos
         */
        ((EditText) (getActivity().findViewById(R.id.ffpEdtDesconto)))
                .addTextChangedListener(recalcularTotal);
        ((EditText) (getActivity().findViewById(R.id.ffpEdtFrete)))
                .addTextChangedListener(recalcularFrete);

        ((EditText) (getActivity().findViewById(R.id.ffpEdtObsCpd)))
                .addTextChangedListener(observacoes);
        ((EditText) (getActivity().findViewById(R.id.ffpEdtObsNfe)))
                .addTextChangedListener(observacoes);

        this.ffpSpnrJustificativa.setOnItemSelectedListener(selectingData);

        ((LinearLayout) (getActivity().findViewById(R.id.ffpEdtDesconto)).getParent()).setVisibility
            (((((Pedido) getActivity()).alteraValorFim(R.id.ffpEdtDesconto))) ? View.VISIBLE : View.GONE);
        ((LinearLayout) (getActivity().findViewById(R.id.ffpEdtFrete)).getParent()).setVisibility
            (((((Pedido) getActivity()).alteraValorFim(R.id.ffpEdtFrete))) ? View.VISIBLE : View.GONE);


        try{ getActivity().findViewById(R.id.llMainFinal).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }
        try{ getActivity().findViewById(R.id.scrMainFinal).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }
        try{ getActivity().findViewById(R.id.relMainFinal).setOnTouchListener(gestureListener); }
        catch (Exception e) { /*****/ }


        ((getActivity().findViewById(R.id.ffpEdtDesconto))).setOnTouchListener(gestureListener);
        ((getActivity().findViewById(R.id.ffpEdtFrete))).setOnTouchListener(gestureListener);
        ((getActivity().findViewById(R.id.ffpEdtObsCpd))).setOnTouchListener(gestureListener);
        ((getActivity().findViewById(R.id.ffpEdtObsNfe))).setOnTouchListener(gestureListener);
        this.ffpSpnrJustificativa.setOnTouchListener(gestureListener);

        this.ffpSpnrNaturezas.setOnTouchListener(gestureListener);
        this.ffpSpnrPrazos.setOnTouchListener(gestureListener);
        this.ffpSpnrJustificativa.setOnTouchListener(gestureListener);

        ((getActivity().findViewById(R.id.ffpEdtTotalItens))).setOnTouchListener(gestureListener);
        ((getActivity().findViewById(R.id.ffpEdtTotalPedido))).setOnTouchListener(gestureListener);

        ((getActivity().findViewById(R.id.ffpEdtCliente))).setOnTouchListener(gestureListener);
        ((getActivity().findViewById(R.id.ffpEdtCidade))).setOnTouchListener(gestureListener);
        ((getActivity().findViewById(R.id.ffpEdtTab))).setOnTouchListener(gestureListener);

        ((EditText) (getActivity().findViewById(R.id.ffpEdtDesconto))).setOnFocusChangeListener(alteracaoFoco);
        ((EditText) (getActivity().findViewById(R.id.ffpEdtFrete))).setOnFocusChangeListener(alteracaoFoco);
        ((EditText) (getActivity().findViewById(R.id.ffpEdtObsCpd))).setOnFocusChangeListener(alteracaoFoco);
        ((EditText) (getActivity().findViewById(R.id.ffpEdtObsNfe))).setOnFocusChangeListener(alteracaoFoco);
    }

    public void listarClientes()
    {
        /*try
        {
            this.listItens.setAdapter(new DistribuicaoItensAdapter(
                    this.mCallback.listarClientes(),
                    getActivity().getApplication().getApplicationContext()));

            this.listClientes.setClickable(this.listClientes.getSelectedItemPosition() == 0);
            this.listClientes.setEnabled(this.listClientes.getSelectedItemPosition() == 0);
            this.edtValor.setText(this.mCallback.getValor());
        }
        catch (Exception e){ e.printStackTrace(); }*/
    }

    public void ajustarLayout(String valor)
    {
        ((EditText) (getActivity().findViewById(R.id.ffpEdtTotalPedido))).setText(valor);
    }

    public void ajustarPrazos(int posicao)
    {
        ArrayAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(), R.layout.spinner_item,
                ((Pedido) getActivity()).listarPrazos(posicao));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        this.ffpSpnrPrazos.setAdapter(adapter);

        this.ffpSpnrPrazos.setSelection(((Pedido) getActivity()).buscarPrazo());
        this.ffpSpnrPrazos.setOnItemSelectedListener(selectingData);

        this.ffpSpnrPrazos.setClickable(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrPrazos));
        this.ffpSpnrPrazos.setEnabled(((Pedido) getActivity()).permitirClick(R.id.ffpSpnrPrazos));
    }

    public void bloquearClicks()
    {
        this.ffpSpnrNaturezas.setClickable(((Pedido) getActivity()).permitirClick(R.id.fdcSpnrNaturezas));
        this.ffpSpnrNaturezas.setEnabled(((Pedido) getActivity()).permitirClick(R.id.fdcSpnrNaturezas));
        this.ffpSpnrPrazos.setClickable(((Pedido) getActivity()).permitirClick(R.id.fdcSpnrPrazos));
        this.ffpSpnrPrazos.setEnabled(((Pedido) getActivity()).permitirClick(R.id.fdcSpnrPrazos));
    }
/**************************************************************************************************/
/******************************END OF FRAGMENT ACCESS METHODS**************************************/
/**************************************************************************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
/**************************************************************************************************/

/**************************************************************************************************/
/*****************************   END OF FRAGMENT FUNCTIONAL METHODS   *****************************/
/**************************************************************************************************/
/*****************************     CLICK LISTENERS FOR THE UI         *****************************/
/**************************************************************************************************/
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
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                ((Pedido) getActivity()).verificarEncerramento(0);
                                return true;
                            }
                        }
                    }
                    else
                    {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                ((Pedido) getActivity()).verificarEncerramento(0);
                                return true;
                            }
                        }
                    }
                    return false;
                }
            });
        }
    };

    private TextWatcher observacoes = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /***/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { /***/ }

        @Override
        public void afterTextChanged(Editable s)
        {
            if((getActivity().findViewById(R.id.ffpEdtObsCpd)).hasFocus())
            {
                ((Pedido) getActivity()).acrescentarObservacao(s.toString(), 1);
            }
            else if((getActivity().findViewById(R.id.ffpEdtObsNfe)).hasFocus())
            {
                ((Pedido) getActivity()).acrescentarObservacao(s.toString(), 2);
            }
        }
    };

    private TextWatcher recalcularTotal = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.toString().length() > 0 && s.toString().indexOf('.') != (s.toString().length() - 1))
                ((Pedido) getActivity()).indicarDescontoPedido(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) { /*****/ }
    };

    private TextWatcher recalcularFrete = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { /*****/ }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if(s.toString().length() > 0 && s.toString().indexOf('.') != (s.toString().length() - 1))
                ((Pedido) getActivity()).indicarFretePedido(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) { /*****/ }
    };

    private AdapterView.OnItemSelectedListener selectingData = new AdapterView.OnItemSelectedListener()
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            switch (parent.getId())
            {
                case R.id.ffpSpnrNaturezas :
                    ajustarPrazos(position);
                    break;
                case R.id.ffpSpnrPrazos :
                    /*
                    ((EditText) getActivity().findViewById(R.id.fdcEdtTabela))
                        .setText(String.format(((Pedido) getActivity()).getApplicationContext()
                        .getResources().getString(R.string.str_tab), String.valueOf(String
                        .valueOf(((Pedido) getActivity()).selecionarPrazo(position)))));
                    ((EditText) getActivity().findViewById(R.id.fdcEdtPrazo)).setText(String.format
                        (((Pedido) getActivity()).getApplicationContext().getResources()
                        .getString(R.string.str_prazo), ((Pedido) getActivity()).selecionarPrazo()));
                    */
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) { /******/ }
    };
/**************************************************************************************************/
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
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
                    ((Pedido) getActivity()).alterarFragmento(4);
                    //if(((getActivity().findViewById(R.id.fdcBtnDetalhes))).getVisibility() == View.VISIBLE)
                    //((Pedido) getActivity()).alterarFragmento(0);
                }
                if (e1.getX() > e2.getX()) { /*((Pedido) getActivity()).alterarFragmento(4);*/ } //Right to Left swipe

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
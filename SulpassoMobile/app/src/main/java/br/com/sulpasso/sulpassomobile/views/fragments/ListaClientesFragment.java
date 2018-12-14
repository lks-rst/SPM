package br.com.sulpasso.sulpassomobile.views.fragments;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.AtualizarSistema;
import br.com.sulpasso.sulpassomobile.controle.CadastrarClientes;
import br.com.sulpasso.sulpassomobile.views.CadastroClientesFragmentalized;

/**
 * Created by Lucas on 30/11/2018 - 16:50 as part of the project SulpassoMobile.
 */
public class ListaClientesFragment extends Fragment
{
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    private CadastrarClientes controle;
    private AtualizarSistema controleAtualizacao;

    protected String displayMessage;

    public ListaClientesFragment(){}
/**********************************FRAGMENT LIFE CICLE*********************************************/
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_cadastro_lista_cadastrados, /*container, false*/null);
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
    }

    @Override
    public void onResume()
    {
        super.onResume();

        this.controle = new CadastrarClientes(getActivity().getApplicationContext());

        this.setUpLayout();
    }
/**************************************************************************************************/
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************FRAGMENT ACCESS METHODS******************************************/
/**************************************************************************************************/


/**************************************************************************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
/**************************************************************************************************/
    /**
     * Metodo para vinculação do layout e inicialização dos itens de UI
     */
    private void setUpLayout()
    {
        /*
        ((ListView) getActivity().findViewById(R.id.liAcpClientes)).setAdapter(
                new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,
                this.controle.listarClientes(getActivity().getApplicationContext())));
        */

        ((ListView) getActivity().findViewById(R.id.liAcpClientes)).setAdapter(
                new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.default_list_item,
                this.controle.listarClientes(getActivity().getApplicationContext())));
        ((ListView) getActivity().findViewById(R.id.liAcpClientes)).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                controleAtualizacao = new AtualizarSistema(getActivity().getApplicationContext());
                new Clientes().execute();

                return false;
            }
        });
        /*
        try
        {
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
        */
//        this.fliLiItens.setOnTouchListener(gestureListener);
//        getActivity().findViewById(R.id.textViewapagarclientes).setOnTouchListener(gestureListener);
//        getActivity().findViewById(R.id.liAcpClientes).setOnTouchListener(gestureListener);
    }
/**************************************************************************************************/
/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
/**************************************************************************************************/


/**************************************************************************************************/
/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
/**************************************************************************************************/


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
                    ((CadastroClientesFragmentalized) getActivity()).alterarFragmento(0);
                }
                if (e1.getX() > e2.getX()) { /*((CadastroClientesFragmentalized) getActivity()).alterarFragmento(1);*/ } //Right to Left swipe

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

    private class Clientes extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... params)
        {
            int nrClientes;

            displayMessage = "Verificando se há clientes não enviados.";
            publishProgress();

            nrClientes = 3;

            if(nrClientes > 0)
            {
                displayMessage = "Buscando dados de clientes.";
                publishProgress();
                if(controleAtualizacao.atualizar(131))
                {
                    displayMessage = "Criando arquivo de clientes.";
                    publishProgress();
                    if(controleAtualizacao.atualizar(16))
                    {
                        displayMessage = "Enviando arquivo de clientes.";
                        publishProgress();
                        if(controleAtualizacao.atualizar(14))
                        {
                            displayMessage = "Atualizando clientes enviadas.";
                            publishProgress();
                            if(controleAtualizacao.atualizar(17))
                            {
                                displayMessage = "Clientes transmitidos com sucesso.";
                                controleAtualizacao.criarArquivoErros();
                                publishProgress();
                            }
                            else
                            {
                                displayMessage = "Não foi possivel atualizar os clientes novos enviadas.\nPor favor comunique seu supervisor.";
                                controleAtualizacao.criarArquivoErros();
                                publishProgress();
                            }
                        }
                        else
                        {
                            displayMessage = "Não foi possivel conectar-se com o servidor.\nPor favor tente mais tarde.";
                            controleAtualizacao.criarArquivoErros();
                            publishProgress();

                            controleAtualizacao.atualizar(18);
                        }
                    }
                    else
                    {
                        displayMessage = "Não foi possível criar o arquivo de clientes.\nPor favor tente mais tarde.";
                        controleAtualizacao.criarArquivoErros();
                        publishProgress();

                        controleAtualizacao.atualizar(18);
                    }
                }
                else
                {
                    displayMessage = "Ocorreu um erro ao buscar os dados dos clientes.\nPor favor tente mais tarde.";
                    controleAtualizacao.criarArquivoErros();
                    publishProgress();

                    controleAtualizacao.atualizar(18);
                }
            }
            else
            {
                displayMessage = "Não existem clentes novos cadastrados.";
                controleAtualizacao.criarArquivoErros();
                publishProgress();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
            Toast.makeText(getActivity().getApplicationContext(), displayMessage, Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void result)
        {

        }
    }
}
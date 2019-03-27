package br.com.sulpasso.sulpassomobile.views;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.AlteracaoPedidos;
import br.com.sulpasso.sulpassomobile.controle.CadastrarClientes;
import br.com.sulpasso.sulpassomobile.controle.EfetuarPedidos;
import br.com.sulpasso.sulpassomobile.controle.PedidoNormal;
import br.com.sulpasso.sulpassomobile.controle.Troca;
import br.com.sulpasso.sulpassomobile.controle.VendaDireta;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Atividade;
import br.com.sulpasso.sulpassomobile.modelo.Banco;
import br.com.sulpasso.sulpassomobile.modelo.Cidade;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.modelo.Tipologia;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TipoVenda;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaItens;
import br.com.sulpasso.sulpassomobile.util.funcoes.SenhaLiberacao;
import br.com.sulpasso.sulpassomobile.views.fragments.DadosClienteFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.DigitacaoItemFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.FinalizacaoPedidoFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.FormularioClientesFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.ListaClientesFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.ListaItensFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.ResumoFragment;

/**
 * Created by Lucas on 05/12/2018 - 13:40 as part of the project SulpassoMobile.
 */
public class CadastroClientesFragmentalized extends AppCompatActivity
{
    private GestureDetector gestureDetector;

    private View fragmentsContainer;

    private EfetuarPedidos controlePedido;

    Boolean obrigatorio;
    int empresa = -1;

    ArrayList<String> str_uf = new ArrayList<String>();
    ArrayList<String> str_atividades = new ArrayList<String>();
    ArrayList<String> str_bancos = new ArrayList<String>();
    ArrayList<String> str_cidade = new ArrayList<String>();
    ArrayList<String> str_natureza = new ArrayList<String>();
    ArrayList<String> str_tipologias = new ArrayList<String>();
    ArrayList<Atividade> array_atividades = new ArrayList<Atividade>();

    ArrayList<Banco> array_bancos = new ArrayList<Banco>();
    ArrayList<Cidade> array_cidade = new ArrayList<Cidade>();
    ArrayList<Natureza> array_natureza  = new ArrayList<Natureza>();
    ArrayList<Tipologia> array_tipologias = new ArrayList<Tipologia>();
    ArrayList<Cliente> array_clientes = new ArrayList<Cliente>();
    ArrayList<String> str_clientes = new ArrayList<String>();

    private CadastrarClientes controle;
    /**********************************ACTIVITY LIFE CICLE*********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente_fragmentalized);

        this.controle = new CadastrarClientes(getApplicationContext());

        displayView(0);

        fragmentsContainer = findViewById(R.id.frame_container);

        /*
        Android_Gesture_Detector android_gesture_detector = new Android_Gesture_Detector();
        // Create a GestureDetector
        gestureDetector = new GestureDetector(this, android_gesture_detector);
        */
    }

    @Override
    protected void onResume() { super.onResume(); }

    @Override
    protected void onRestart()
    {
        super.onRestart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
//        Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() { super.onDestroy(); }

    @Override
    public void onBackPressed()
    {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1){ finish(); }
        else { super.onBackPressed(); }
    }
/***************************END METHODS ACTIVITY LIFE CICLE****************************************/
/********************************BUTTON CLICKS AT THE UI*******************************************/
    public void alterarData(View v)
    {
        displayView(1);
    }

    public void alterarFragmento(int position) { this.displayView(position); }
/******************************END OF BUTTON CLICKS AT THE UI**************************************/
/*********************************METHODS FOR DATA ACCESS******************************************/
    public ArrayList<String> retornarListaItens(int tipo)
    {
        switch (tipo)
        {
            case 0:
                return str_atividades;
            case 1:
                return str_bancos;
            case 2:
                return str_natureza;
            case 3:
                return str_cidade;
            case 4:
                return str_tipologias;
            case 5:
                return str_uf;
            case 6:
                return str_clientes;
            default:
                return new ArrayList<String>();
        }
    }

    private void encerrar()
    {
        finish();
    }
/******************************END OF METHODS FOR DATA ACCESS**************************************/
/************************************End the Overridin*********************************************/
/***************************Methods to make class services direct *********************************/
    /**
     * Calback para interceptar os movimentos na tela
     * /
    private class Android_Gesture_Detector implements GestureDetector.OnGestureListener,
            GestureDetector.OnDoubleTapListener
    {
        @Override
        public boolean onDown(MotionEvent e) { return true; }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) { return true; }

        @Override
        public boolean onSingleTapUp(MotionEvent e) { return true; }

        @Override
        public void onShowPress(MotionEvent e) { Log.d("Gesture ", " onShowPress"); }

        @Override
        public boolean onDoubleTap(MotionEvent e) { return true; }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) { return true; }

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
            * /
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
                * /
            }
            if (e1.getY() > e2.getY())
            {
                Log.d("Gesture ", " Scroll Up");
                /*
                if (e1.getY() > scrollUpBegin) { Log.d("Gesture ", " Scroll Up -- To high"); }
                else { Log.d("Gesture ", " Scroll Up"); }
                * /
            }

            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
        {
            if (e1.getX() < e2.getX())
            {
                Log.d("Gesture ", "Left to Right swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");
                Log.d("Gesture ", "Left to Right swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");

                if (e2.getX() - e1.getX() > 25)
                {
                    displayView(1);
                }
            }
            if (e1.getX() > e2.getX())
            {
                Log.d("Gesture ", "Right to Left swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityX) + " pixels/second");

                displayView(0);
            }
            if (e1.getY() < e2.getY())
            {
                Log.d("Gesture ", "Up to Down swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
            }
            if (e1.getY() > e2.getY())
            {
                Log.d("Gesture ", "Down to Up swipe: " + e1.getX() + " - " + e2.getX());
                Log.d("Speed ", String.valueOf(velocityY) + " pixels/second");
            }
            return true;
        }
    }
     */
    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position)
    {
//        update the main content by replacing fragments
        String title = "CLIENTES NOVOS:";
        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment = new ListaClientesFragment();
                title += " Lista Cadastros";
                break;
            case 1:
                fragment = new FormularioClientesFragment();
                title += " Formulário Cadastro";
                break;
            default:
                Toast.makeText(this, "Clicado na posição -1", Toast.LENGTH_LONG).show();
                break;
        }
        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                if(position == 2)
                {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                            .replace(R.id.frame_container_clientes, fragment)./*addToBackStack(null).*/commit();
                }
                else
                {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                            .replace(R.id.frame_container_clientes, fragment).commit();
                }
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                if(position == 2)
                {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                            .replace(R.id.frame_container_clientes, fragment).addToBackStack(null).commit();
                }
                else
                {
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                            .replace(R.id.frame_container_clientes, fragment).commit();
                }
            }
            else
            {
                if(position == 2)
                {
                    fragmentManager.beginTransaction().replace(R.id.frame_container_clientes, fragment).addToBackStack(null).commit();
                }
                else
                {
                    fragmentManager.beginTransaction().replace(R.id.frame_container_clientes, fragment).commit();
                }
            }
        }
        else { Log.e("MainActivity", "Error in creating fragment"); }

        setTitle(title);
    }

    public void aplicarDescontoTabloide(final int posicao)
    {
        String titulo = "Campanha Grupos";
        String mensagem = "Para esse grupo é permitido " + this.controlePedido.getCampanhaGrupos().get(posicao).getDesconto() + "% de desconto.";

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.setCancelable(false);

        final EditText input = new EditText(getApplicationContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);

        alert.setView(input);

        alert.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                verificarTabloides(input.getText().toString(), posicao);
            }
        });

        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which){ /*JUST IGNORE THIS BUTTON IT IS HERE ONLY FOR BETTER VISUALIZATION*/ }
        });

        alert.show();
    }

    private void verificarTabloides(String s, int posicao)
    {
        float valor = 0;

        try { valor = Float.parseFloat(s); }
        catch (Exception e){ }

        int retorno = this.controlePedido.aplicarDescontoTabloide(valor, posicao, 0);

        if(retorno > -1)
            this.aplicarDescontoTabloide(retorno);
    }

    private void buscar_clientes(final int tipo)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        int posicao = 0;

        ArrayList<String> cidades;
        ArrayAdapter<String> adapter_ciadades;

        String array_spinner[];
        array_spinner = new String[3];

        array_spinner[0]="Clientes Fantasia";
        array_spinner[1]="Clientes Razao Social";
        array_spinner[2]="Clientes Cidade";

        String array_mensagem[];
        array_mensagem = new String[3];

        array_mensagem[0]="Digite o nome fantasia.";
        array_mensagem[1]="Digite a razao social";
        array_mensagem[2]="Digite a cidade";

        final EditText input = new EditText(this);
        final Spinner spnr_cidades = new Spinner(this);

        switch (tipo)
        {
            case 1: //FANTASIA
                posicao = 0;
                spnr_cidades.setVisibility(View.GONE);
                break;
            case 2: //RAZÃO
                posicao = 1;
                spnr_cidades.setVisibility(View.GONE);
                break;
            case 3: //CIDADE
                posicao = 2;
                cidades = new ArrayList<>();
                adapter_ciadades = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.spinner_item, controlePedido.listarCidades());
                adapter_ciadades.setDropDownViewResource(R.layout.spinner_dropdown_item);

                spnr_cidades.setAdapter(adapter_ciadades);
                input.setVisibility(View.GONE);
                break;

            default:
                break;
        }

        alert.setTitle(array_spinner[posicao]);
        alert.setMessage(array_mensagem[posicao]);

        alert.setPositiveButton("SIM", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                if(tipo == 3)
                {
                    try
                    {
                        apresentarLista(controlePedido.listarCLientes(tipo, String.valueOf(
                                controlePedido.getCitCod(spnr_cidades.getSelectedItemPosition()))), 1);
                    } catch (GenercicException e)
                    {
                        apresentarLista(new ArrayList<String>(), 1);
                    }
                }
                else
                {
                    try
                    {
                        apresentarLista(controlePedido.listarCLientes(tipo, input.getText().toString()), 1);
                    } catch (GenercicException e)
                    {
                        apresentarLista(new ArrayList<String>(), 1);
                    }
                }
            }
        });
        alert.setNegativeButton("NÃO", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                apresentarLista(new ArrayList<String>(), 1);
            }
        });

        if(tipo == 3)
            alert.setView(spnr_cidades);
        else
            alert.setView(input);

        alert.show();
    }

    private void apresentarLista(ArrayList<String> itens, int tipo)
    {
        FragmentManager fragmentManager = getFragmentManager();
        DadosClienteFragment fragment;

        try
        {

//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
//            {
//                fragment = new DadosClienteFragment();

//                fragmentManager.beginTransaction()
//                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
//                        .replace(R.id.frame_container, fragment)./*addToBackStack(null).*/commit();
                /*
                fragmentManager.beginTransaction().remove(fragment).commit();
                fragmentManager.beginTransaction().add(R.id.frame_container, fragment).commit();
                */
//            }
//            else
            fragment = (DadosClienteFragment) fragmentManager.findFragmentById(R.id.frame_container);

            if (fragment != null) { fragment.apresentarLista(itens, tipo, getApplicationContext()); }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Erro ao carregar dados\n" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void apresentarListaResumo()
    {
        FragmentManager fragmentManager = getFragmentManager();
        ResumoFragment fragment;

        try
        {
            fragment = (ResumoFragment) fragmentManager.findFragmentById(R.id.frame_container);

            if (fragment != null) { fragment.atualizarResumo(); }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Erro ao carregar dados", Toast.LENGTH_LONG).show();
        }
    }
}
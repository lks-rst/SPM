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
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.AlteracaoPedidos;
import br.com.sulpasso.sulpassomobile.controle.EfetuarPedidos;
import br.com.sulpasso.sulpassomobile.controle.PedidoNormal;
import br.com.sulpasso.sulpassomobile.controle.Troca;
import br.com.sulpasso.sulpassomobile.controle.VendaDireta;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TipoVenda;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaItens;
import br.com.sulpasso.sulpassomobile.views.fragments.DadosClienteFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.DigitacaoItemFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.FinalizacaoPedidoFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.ListaItensFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.ResumoFragment;

/*
    Todo: Criar o fragmento com os dados adicionais do cliente;
    TOdo: Verificar as outras formas de venda (diretas e trocas) ao menos dois pedidos em cada.
*/

public class Pedido extends AppCompatActivity
{
    private GestureDetector gestureDetector;

    private View fragmentsContainer;

    private EfetuarPedidos controlePedido;

    private String[] fragTitles;
/**********************************ACTIVITY LIFE CICLE*********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        Intent call = getIntent();

        int tipoVenda = call.getIntExtra("TIPOVENDA", -1);
        String direta = call.getStringExtra("DIRETA");

        // load slide menu items
        fragTitles = getResources().getStringArray(R.array.fragTitles);

        // on first time display view for first nav item
        if (savedInstanceState == null)
        {
            if(tipoVenda == TipoVenda.NORMAL.getValue())
                this.controlePedido = new PedidoNormal(getApplicationContext(), "PD");
            else if(tipoVenda == TipoVenda.DIRETA.getValue())
                this.controlePedido = new VendaDireta(getApplicationContext(), direta);
            else if(tipoVenda == TipoVenda.TROCA.getValue())
                this.controlePedido = new Troca(getApplicationContext(), "TR");
            else if(tipoVenda == TipoVenda.ALTERACAO.getValue())
                this.controlePedido = new AlteracaoPedidos(getApplicationContext(), direta);

            /*
            Toast.makeText(getApplicationContext(), "Tipo de controle de vendas aberto: \n" +
                    this.controlePedido.getClass(), Toast.LENGTH_LONG).show();
            */
            displayView(0);
        }

        fragmentsContainer = findViewById(R.id.frame_container);

        /*
        this.createMenu();

        // on first time display view for first nav item
        if (savedInstanceState == null) { displayView(0); }

        fragmentsContainer = findViewById(R.id.frame_container);
        progressBar = findViewById(R.id.progress);

        this.controlLiquidacao = new ControlDistribuicao(getApplicationContext());
        */

        // Create an object of the Android_Gesture_Detector  Class
        Android_Gesture_Detector android_gesture_detector = new Android_Gesture_Detector();
        // Create a GestureDetector
        gestureDetector = new GestureDetector(this, android_gesture_detector);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        /*
        Log.i("Already Open", "" + alreadyOpen);

        if(!alreadyOpen)
        {
            mDrawerLayout.openDrawer(mDrawerList);
            Handler h = new Handler();
            h.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    mDrawerLayout.closeDrawer(mDrawerList);
                }
            }, 2000);

            alreadyOpen = true;
        }
        */
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
    public void selecionarCliente(View v)
    {
        displayView(1);
    }

    public void selecionarItem(View v) { displayView(3);/*displayView(2);*/ }

    public void abrirResumo(View v) { displayView(4);/*displayView(2);*/ }

    public void confirmarDigitacao(View v)
    {
        if(this.controlePedido.confirmarItem())
        {
            getFragmentManager().popBackStackImmediate();
            int ret = -1;
            ret = this.controlePedido.verificarTabloides();
            if(ret != -1)
            {
                aplicarDescontoTabloide(ret);
                Toast.makeText(getApplicationContext(), "Desconto aplicado", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void exibirPromocoes(View v) { this.controlePedido.buscarPromocoes(); }

    public void buscarMinimoTabela(View v)
    {
        FragmentManager fragmentManager = getFragmentManager();
        DigitacaoItemFragment fragment;

        try
        {
            fragment = (DigitacaoItemFragment) fragmentManager.findFragmentById(R.id.frame_container);

            if (fragment != null) { fragment.indicarMinimo(this.controlePedido.buscarMinimoTabela()); }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Erro ao carregar dados", Toast.LENGTH_LONG).show();
        }
    }

    public void buscarValorTabela(View v)
    {
        FragmentManager fragmentManager = getFragmentManager();
        DigitacaoItemFragment fragment;

        try
        {
            fragment = (DigitacaoItemFragment) fragmentManager.findFragmentById(R.id.frame_container);

            if (fragment != null) { fragment.indicarMinimo(this.getValor()); }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Erro ao carregar dados", Toast.LENGTH_LONG).show();
        }
    }

    public void finalizar(View v) { if(this.controlePedido.finalizarPedido() == 1) { finish(); } }

    public String calculoContribuicao(float preco)
    {
        return  String.valueOf(this.controlePedido.calculoContribuicao(preco));
    }

    public String calcularPpc(String valor, String markup, String desconto)
    {
        return String.valueOf(this.controlePedido.calcularPpc(
            Float.parseFloat(valor), Float.parseFloat(markup),  Float.parseFloat(desconto)));
    }
/******************************END OF BUTTON CLICKS AT THE UI**************************************/
/*********************************METHODS FOR DATA ACCESS******************************************/
    public void listarClientes(int tipo, String dados) { this.buscar_clientes(tipo); }

    public void listarClientes(int tipo)
    {
        try { this.apresentarLista(this.controlePedido.listarCLientes(tipo, "")); }
        catch (GenercicException ge)
        {
            Toast.makeText(getApplicationContext(), ge.getMessage(), Toast.LENGTH_LONG).show();
            this.apresentarLista(new ArrayList<String>());
        }
    }

    public void selecionarCliente(int position)
    {
        this.controlePedido.selecionarCliente(position);

        FragmentManager fragmentManager = getFragmentManager();
        DadosClienteFragment fragment;

        try
        {
            fragment = (DadosClienteFragment) fragmentManager.findFragmentById(R.id.frame_container);

            if (fragment != null) { fragment.ajustarLayout(); }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Erro ao carregar dados", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<String> listarNaturezas(Boolean especial)
    {
        try { return this.controlePedido.listarNaturezas(especial); }
        catch (GenercicException ge)
        {
            Toast.makeText(getApplicationContext(), ge.getMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    public ArrayList<String> listarPrazos(int position)
    {
        try { return this.controlePedido.listarPrazos(position); }
        catch (GenercicException ge)
        {
            Toast.makeText(getApplicationContext(), ge.getMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    public String buscarDadosCliente(int campo)
    {
        return this.controlePedido.buscarDadosCliente(campo);
    }

    public String buscarDadosVenda(int campo)
    {
        return this.controlePedido.buscarDadosVenda(campo);
    }

    public Boolean permitirClick(int id) { return this.controlePedido.permitirClick(id); }

    public int buscarNatureza()
    {
        return this.controlePedido.buscarNatureza();
    }

    public int buscarPrazo()
    {
        return this.controlePedido.buscarPrazo();
    }

    public void selecionarNatureza(int position) { /*****/ }

    public int selecionarPrazo(int position)
    {
        this.controlePedido.setPrazo(position);
        return this.controlePedido.getTabela();
    }

    public String selecionarPrazo()
    {
        return this.controlePedido.getDesdobramentoPrazo();
    }

    public int itensVendidos() { return this.controlePedido.itensVendidos(); }

    public float valorVendido() { return this.controlePedido.valorVendido(); }

    public String listarVendidos() { return this.controlePedido.listarVendidos(); }

    public String cabecahoPedido(int campo) { return this.controlePedido.cabecahoPedido(campo); }

    public ArrayList<String> listarItens(int tipo, String dados)
    {
        this.controlePedido.indicarTipoBuscaItem(tipo);
        this.controlePedido.indicarDadosBuscaItens(dados);

        try { return this.controlePedido.listarItens(); }
        catch (GenercicException ge)
        {
            Toast.makeText(getApplicationContext(), ge.getMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    public ArrayList<String> listarItens()
    {
        try { return this.controlePedido.listarItens(); }
        catch (GenercicException ge)
        {
            Toast.makeText(getApplicationContext(), ge.getMessage(), Toast.LENGTH_LONG).show();
            return new ArrayList<>();
        }
    }

    public ArrayList<String> listarResumo()
    {
        try { return this.controlePedido.listarResumo(); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void alterarItem(int posicao, int opt)
    {
        if(opt == 1) { this.controlePedido.removerItem(posicao); }
        else
        {
            this.controlePedido.alterarItem(posicao);
            displayView(2);
        }
    }

    public String descricaoItem(int posicao) { return this.controlePedido.getDescricaoItem(posicao); }

    public void selecionarItem(int position)
    {
        this.controlePedido.selecionarItem(position);
        displayView(2);
    }

    public void selecionarItemPre(int position)
    {
        this.controlePedido.selecionarItemPre(position);
        displayView(2);
    }

    public String getItem() { return this.controlePedido.getItem(); }

    public Boolean temValorMinimo() { return this.controlePedido.temValorMinimo(); }

    public Boolean temPromocao() { return this.controlePedido.temPromocao(); }

    public Boolean alteraValor(String campo) { return this.controlePedido.alteraValor(campo); }

    public Boolean alteraValorFim(int campo) { return this.controlePedido.alteraValorFim(campo); }

    public String getValor() { return this.controlePedido.getValor(); }

    public String getQtdMinimaVenda() { return this.controlePedido.getQtdMinimaVenda(); }

    public String getCodigoBarras() { return this.controlePedido.getCodigoBarras(); }

    public String getQtdCaixa() { return this.controlePedido.getQtdCaixa(); }

    public String getValorUnitario() { return this.controlePedido.getValorUnitario(); }

    public String getMarkup() { return this.controlePedido.getMarkup(); }

    public String getUnidade() { return this.controlePedido.getUnidade(); }

    public String getUnidadeVenda() { return this.controlePedido.getUnidadeVenda(); }

    public String calcularTotalItem() { return this.controlePedido.calcularTotal(); }

    public void digitarQuantidade(String quantidade) { this.controlePedido.setQuantidade(quantidade); }

    public boolean vendaKilo() { return this.controlePedido.vendaKilo(); }

    public void digitarValor(String valor) { this.controlePedido.setValor(valor); }

    public void digitarDesconto(String desconto) { this.controlePedido.setDesconto(desconto); }

    public void digitarAcrescimo(String acrescimo) { this.controlePedido.setAcrescimo(acrescimo); }

    public String calcularPPC(String mkp, String vlr) { return this.controlePedido.calcularPPC(mkp, vlr); }

    public int restoreClient() { return this.controlePedido.restoreClient(); }

    public void indicarToatalPedido()
    {
        this.controlePedido.indicarTotal(this.valorVendido());
    }

    public void indicarDescontoPedido(String desconto)
    {
        this.controlePedido.setDescontoPedido(desconto);
        this.exibirTotalPedido();
    }

    public void indicarFretePedido(String frete)
    {
        this.controlePedido.setFrete(frete);
        this.exibirTotalPedido();
    }

    public void exibirTotalPedido()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FinalizacaoPedidoFragment fragment;

        try
        {
            fragment = (FinalizacaoPedidoFragment) fragmentManager
                    .findFragmentById(R.id.frame_container);

            if (fragment != null)
            {
                fragment.ajustarLayout(String.valueOf(this.controlePedido.recalcularTotalPedido()));
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Erro ao carregar dados", Toast.LENGTH_LONG).show();
        }
    }

    public void verificarPromocoes() { /*****/ }

    public void consultarValor() { /*****/ }

    public void confirmarInsercao()
    {
        if(this.controlePedido.confirmarItem())
            displayView(2);
        else
            Toast.makeText(getApplicationContext(), "Verifique as informações inseridas",
                    Toast.LENGTH_LONG).show();
    }

    public void informarSenha() { /*****/ }

    public void exibirInformacoes() { /*****/ }

    public void salvarPedido() { /*****/ }

    public void acrescentarObservacao(String s, int tipo)
    {
        this.controlePedido.addObs(s, tipo);
    }

    public GestureDetector getGestureDetector() {
        return gestureDetector;
    }

    public ArrayList<String> listarGrupos() throws GenercicException
    {
        return this.controlePedido.listarGrupos();
    }

    public ArrayList<String> listarSubGrupos() throws GenercicException
    {
        return this.controlePedido.listarSubGrupos();
    }

    public ArrayList<String> listarDivisoes() throws GenercicException
    {
        return this.controlePedido.listarDivisoes();
    }

    public void setSearchType(TiposBuscaItens type)
    {
        this.controlePedido.setSearchType(type);
    }

    public void setSearchData(String data)
    {
        this.controlePedido.setSearchData(data);
    }

    public int buscarTipoConsulta()
    {
        return this.controlePedido.buscarConsultaAbertura();
    }

    public void indicarGrupo(int pos) { this.controlePedido.indicarGrupo(pos); }

    public void indicarSubGrupo(int pos) { this.controlePedido.indicarSubGrupo(pos); }

    public void indicarDivisao(int pos) { this.controlePedido.indicarDivisao(pos); }

    public void buscarTipoCliente() { this.controlePedido.buscarTipoCliente(); }

    public boolean buscarPrePedidos() { return this.controlePedido.verificarPrepedido(); }

    public PrePedido detalharPrePedido() { return this.controlePedido.detalharPrePedido(); }

    public boolean inserDePrePedido() { return this.controlePedido.inserDePrePedido(); }
/******************************END OF METHODS FOR DATA ACCESS**************************************/
/************************************End the Overridin*********************************************/
/******************************Methods to make class services direct ******************************/
    /**
     * Calback para interceptar os movimentos na tela
     */
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

    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position)
    {
//        update the main content by replacing fragments
        String title = getString(R.string.telaPedido);
        Fragment fragment = null;

        switch (position)
        {
            case 0:
                fragment = new DadosClienteFragment();
                title += fragTitles[position];
                break;
            case 1:
                fragment = new ListaItensFragment();
                title += fragTitles[position];
                break;
            case 2:
                fragment = new DigitacaoItemFragment();
                title += fragTitles[position];
                break;
            case 3:
                fragment = new FinalizacaoPedidoFragment();
                title += fragTitles[position];
                break;
            case 4:
                fragment = new ResumoFragment();
                title += fragTitles[position];
                break;
            case 5:
                break;
            default:
                Toast.makeText(this, "Clicado na posição -1", Toast.LENGTH_LONG).show();
//                    title = fragTitles[position];
                break;
        }
        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            }
            else
            {
                fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            }
        }
        else { Log.e("MainActivity", "Error in creating fragment"); }

        /*
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawer(mDrawerList);
        */
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

        int retorno = this.controlePedido.aplicarDescontoTabloide(valor, posicao);

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
                            controlePedido.getCitCod(spnr_cidades.getSelectedItemPosition()))));
                    } catch (GenercicException e)
                    {
                        apresentarLista(new ArrayList<String>());
                    }
                }
                else
                {
                    try
                    {
                        apresentarLista(controlePedido.listarCLientes(tipo, input.getText().toString()));
                    } catch (GenercicException e)
                    {
                        apresentarLista(new ArrayList<String>());
                    }
                }
            }
        });
        alert.setNegativeButton("NÃO", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                apresentarLista(new ArrayList<String>());
            }
        });

        if(tipo == 3)
            alert.setView(spnr_cidades);
        else
            alert.setView(input);

        alert.show();
    }

    private void apresentarLista(ArrayList<String> itens)
    {
        FragmentManager fragmentManager = getFragmentManager();
        DadosClienteFragment fragment;

        try
        {
            fragment = (DadosClienteFragment) fragmentManager.findFragmentById(R.id.frame_container);

            if (fragment != null) { fragment.apresentarLista(itens); }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),
                    "Erro ao carregar dados", Toast.LENGTH_LONG).show();
        }
    }
}
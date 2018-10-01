package br.com.sulpasso.sulpassomobile.views;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.ConsultarPedidos;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorConexao;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TipoVenda;
import br.com.sulpasso.sulpassomobile.util.funcoes.FtpConnect;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipularArquivos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensGravosos;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensPromocoes;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaPedidosLista;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.GrupoSelection;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.MenuPedidoNaoEnviado;

public class ConsultasPedidos extends AppCompatActivity
{
    private ConsultarPedidos controle;

    private int inicio;
    private int fim;

    private ArrayList<Venda> listaVendas;

    private String[] fragTitles;
/**********************************FRAGMENT LIFE CYCLE*********************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);

        fragTitles = getResources().getStringArray(R.array.fragTitlesConsultaPedidos);

        this.controle = new ConsultarPedidos(getApplicationContext());

        displayView(R.layout.fragment_consulta_pedidos_lista);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_consulta_pedidos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        FragmentManager fragmentManager;
        Fragment fragment = null;

        switch (item.getItemId())
        {
            case R.id.consulta_pedidos_todos :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
                else
                {
                    findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                    findViewById(R.id.frame_itens).setVisibility(View.GONE);

                    try {
                        ((ConsultaPedidosLista) fragment).listarItens(this.controle.listarPedidosV(0, ""));
                    } catch (GenercicException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.consulta_pedidos_resumo :

                break;
            case R.id.consulta_pedidos_nao :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
                else
                {
                    findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                    findViewById(R.id.frame_itens).setVisibility(View.GONE);

                    try {
                        ((ConsultaPedidosLista) fragment).listarItens(this.controle.listarPedidosV(2, ""));
                    } catch (GenercicException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.consulta_pedidos_direta :

                break;
            case R.id.consulta_pedidos_enviados :
                fragmentManager = getFragmentManager();

                try
                {
                    fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                }

                if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
                else
                {
                    findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                    findViewById(R.id.frame_itens).setVisibility(View.GONE);

                    try
                    {
                        ((ConsultaPedidosLista) fragment).listarItens(this.controle.listarPedidosV(1, ""));
                    }
                    catch (GenercicException e) { e.printStackTrace(); }
                }
                break;
            case R.id.consulta_pedidos_copia :
                intervalo_pediods(0, "Copia de pedidos");
                break;
            case R.id.consulta_pedidos_reenviar :
                intervalo_pediods(1, "Reenvio de pedidos");
                break;
            default:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
/********************************END OF FRAGMENT LIFE CICLE****************************************/
/*******************************FRAGMENT FUNCTIONAL METHODS****************************************/
    /**
     * Diplaying fragment view for selected nav drawer list item
     */
    private void displayView(int position)
    {
//        update the main content by replacing fragments
        String title = getString(R.string.telasConsulta);
        Fragment fragment = null;

        getFragmentManager().popBackStackImmediate();
        switch (position)
        {
            case R.layout.fragment_consulta_pedidos_lista :
                fragment = new ConsultaPedidosLista();
                title += fragTitles[0];
                break;
            case R.layout.fragment_consulta_pedidos_diretas :
                fragment = new ConsultaItensPromocoes();
                title += fragTitles[1];
                break;
            case R.layout.fragment_consulta_pedidos_resumo :
                fragment = new ConsultaItensGravosos();
                title += fragTitles[2];
                break;

            default:
                Toast.makeText(this, "A opção selecionada não e reconhecida pelo sistema", Toast.LENGTH_LONG).show();
                break;
        }
        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.frame_consultas, fragment).addToBackStack(null).commit();
            } else
            {
                fragmentManager.beginTransaction().replace(R.id.frame_consultas, fragment).addToBackStack(null).commit();
            }
        }
        else { Log.e("MainActivity", "Error in creating fragment"); }
        setTitle(title);
    }

    public void direcionarAcao(int acaoPedido)
    {
        switch (acaoPedido)
        {
            case 0:
                int pedidoAlterar = this.controle.alterarPedido();
                if(pedidoAlterar > 0)
                {
                    Toast.makeText(this, "Iniciar alteração de pedido", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getApplicationContext(), Pedido.class);
                    i.putExtra("ALTERACAO", true);
                    i.putExtra("CODIGO", pedidoAlterar);
                    startActivity(i);
                }
                break;

            case 1:
                if(this.controle.excluirPedido())
                {
                    FragmentManager fragmentManager;
                    Fragment fragment = null;

                    fragmentManager = getFragmentManager();

                    try
                    {
                        fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
                    }

                    if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
                    else
                    {
                        findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                        findViewById(R.id.frame_itens).setVisibility(View.GONE);

                        try
                        {
                            ((ConsultaPedidosLista) fragment).listarItens(
                                    this.controle.listarPedidosV(this.controle.tipoBusca(), ""));
                        }
                        catch (GenercicException e) { e.printStackTrace(); }
                    }
                }
                break;

            default:
                break;
        }
    }

    public void indicarPosicaoPedido(int posicao) { this.controle.setPosicaoPedido(posicao); }

    public boolean abrirMenu() { return !this.controle.pedidoJaEnviado(); }

/********************************END OF FRAGMENT FUNCTIONAL METHODS********************************/
/*************************************CLICK LISTENERS FOR THE UI***********************************/
    public void intervalo_pediods(final int tipo, String titulo)
    {
        final Dialog alert = new Dialog(this);
        alert.setTitle(titulo);

        alert.setContentView(R.layout.alert_pedidos_enviados);

        final EditText edt_inicio = (EditText) alert.findViewById(R.id.alert_inicio);
        final EditText edt_fim = (EditText) alert.findViewById(R.id.alert_fim);
        Button btn_confirmar = (Button) alert.findViewById(R.id.alert_confirmar);

        alert.show();
        btn_confirmar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                if (edt_inicio.getText().toString().length() > 0)
                {
                    try
                    {
                        inicio = Integer.parseInt(edt_inicio.getText().toString());
                        if (edt_fim.getText().toString().length() > 0)
                        {
                            try
                            {
                                fim = Integer.parseInt(edt_fim.getText().toString());
                                if (fim < inicio) fim = inicio;
                                if (tipo == 1)
                                {
                                    alert.dismiss();
                                    new GerarVendas().execute();/*Toast.makeText(getApplicationContext(), "Reenvio", Toast.LENGTH_LONG).show();*/
                                }
                                else
                                        /*new GerarCopia().execute();*/Toast.makeText(getApplicationContext(), "Copia", Toast.LENGTH_LONG).show();

                                alert.dismiss();
                            } catch (Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Digite apenas numeros", Toast.LENGTH_LONG).show();
                                alert.dismiss();
                            }
                        }
                        else
                        {
                            fim = inicio;
                            if (tipo == 1)
                            {
                                alert.dismiss();
                                new GerarVendas().execute();/*Toast.makeText(getApplicationContext(), "Reenvio", Toast.LENGTH_LONG).show();*/
                            }
                            else /*new GerarCopia().execute();*/Toast.makeText(getApplicationContext(), "Copia", Toast.LENGTH_LONG).show();

                            alert.dismiss();
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "Digite apenas numeros", Toast.LENGTH_LONG).show();
                        alert.dismiss();
                    }
                }
            }
        });
    }
/*

    public void menu_pedido_nao_enviado(final int cod_ped, final int pos_ped)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
        alert.setMessage("");
        alert.setCancelable(false);

        String array_spinner[];
        array_spinner = new String[2];

        array_spinner[0]="ALTERAR";
        array_spinner[1]="EXCLUIR";

        final ListView input = new ListView(getApplicationContext());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, array_spinner);
        input.setAdapter(adapter);

        input.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                direcionarAcao(arg2, pos_ped);
            }
        });
*/
/*

        alert.setNeutralButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                direcionarAcao(cod_ped);
            }
        });
*//*


        alert.setView(input);

        alert.show();
    }
*/

/**********************************END OF CLICK LISTENERS FOR THE UI*******************************/
/*************************************METHODS FROM THE INTERFACES**********************************/
    public ConsultarPedidos getControle() { return controle; }

    public void buscarPedidosData(String data)
    {
        FragmentManager fragmentManager;
        Fragment fragment = null;
        fragmentManager = getFragmentManager();

        try
        {
            fragment = (ConsultaPedidosLista) fragmentManager.findFragmentById(R.id.frame_consultas);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Erro ao carregar dados", Toast.LENGTH_LONG).show();
        }

        if (fragment == null) { displayView(R.layout.fragment_consulta_pedidos_lista); }
        else
        {
            findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
            findViewById(R.id.frame_itens).setVisibility(View.GONE);

            try {
                ((ConsultaPedidosLista) fragment).listarItens(this.controle.listarPedidosV(data));
            } catch (GenercicException e) {
                e.printStackTrace();
            }
        }
    }
/*********************************END OF ITERFACES METHODS*****************************************/
/**************************************************************************************************/
    @Override
    public void onBackPressed()
    {
        try
        {
            if(findViewById(R.id.frame_pedidos).getVisibility() == View.GONE)
            {
                findViewById(R.id.frame_pedidos).setVisibility(View.VISIBLE);
                findViewById(R.id.frame_itens).setVisibility(View.GONE);
            }
            else
            {
                super.onBackPressed();
            }
        }
        catch (Exception e)
        {
            super.onBackPressed();
        }
    }

/**************************************************************************************************/

    private class GerarVendas extends AsyncTask<Void, Void, Void>{
        ProgressDialog dialog;
        Boolean status;

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            System.out.println("GERANDO ARQUIVOS...");
            dialog = new ProgressDialog(getApplicationContext());
            dialog.setMessage("GERANDO ARQUIVOS DE PEDIDOS...");

            Toast.makeText(getApplicationContext(), "GERANDO ARQUIVOS DE PEDIDOS...", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "GERANDO ARQUIVOS DE PEDIDOS...", Toast.LENGTH_LONG).show();
//            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            VendaDataAccess vda = new VendaDataAccess(getApplicationContext());
            vda.setSearchType(1);
            try { listaVendas = (ArrayList<Venda>) vda.getByInterval(inicio, fim); }
            catch (GenercicException e) { e.printStackTrace(); }


            ConfiguradorDataAccess cda = new ConfiguradorDataAccess(getApplicationContext());
            ManipulacaoStrings ms = new ManipulacaoStrings();
            int sequencia;
            int usuario;
            String nomeArquivo;

            sequencia = cda.buscarSequencias(1);
            usuario = cda.buscarCodigoUsuario();

            nomeArquivo = ms.comEsquerda("" + usuario, "0", 4) + ms.comEsquerda("" + sequencia, "0", 4)+ ".ped" ;


            ManipularArquivos arquivos = new ManipularArquivos(getApplicationContext());
            arquivos.setNomeArquivo(nomeArquivo);
            arquivos.escreverVendas(listaVendas);


            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            new EnviarArquivos().execute();
        }
    }

    private class EnviarArquivos extends AsyncTask<Void, Void, Void>{
        ProgressDialog dialog;
        Boolean status;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            System.out.println("ENVIANDO ARQUIVOS...");
            dialog = new ProgressDialog(getApplicationContext());
            dialog.setMessage("ENVIANDO ARQUIVOS...");

            Toast.makeText(getApplicationContext(), "ENVIANDO ARQUIVOS...", Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "ENVIANDO ARQUIVOS...", Toast.LENGTH_LONG).show();
//            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            ManipularArquivos arquivos = new ManipularArquivos(getApplicationContext());
            try {
                ConfiguradorDataAccess cda = new ConfiguradorDataAccess(getApplicationContext());
                ManipulacaoStrings ms = new ManipulacaoStrings();
                ConfiguradorConexao server = new ConfiguradorConexao();
                try { server = cda.getConexao(); }
                catch (GenercicException e) { e.printStackTrace(); }

                int sequencia;
                int usuario;
                String nomeArquivo;

                sequencia = cda.buscarSequencias(1);
                usuario = cda.buscarCodigoUsuario();

                nomeArquivo = ms.comEsquerda("" + usuario, "0", 4) + ms.comEsquerda("" + sequencia, "0", 4)+ ".ped" ;


                if(server.getConectionType() == 0)
                {
                    FtpConnect conect = new FtpConnect(server);

                    ArrayList<String> arqs_pw = new ArrayList<String>();
                    Boolean status = false;
                    if(conect.Conectar())
                    {
                        if(conect.MudarDiretorio(server.getUploadFolder()))
                        {
                            VendaDataAccess vda = new VendaDataAccess(getApplicationContext());

                            if(conect.Mandar(nomeArquivo, nomeArquivo))
                            {
                                try { vda.atualizarVendas(inicio, fim, 1); }
                                catch (GenercicException e) { /*****/ }

                                try { cda.atualizarSequencias(1); }
                                catch (GenercicException e) { /*****/ }
                            }
                            else { /*****/ }
                        }
                        else
                        {
                            arquivos.addStringErro("Erro ao mudar de diretorio. Pasta de pedidos não encontrada no servidor");
                        }
                    }
                    else
                    {
                        arquivos.addStringErro("Erro ao conectar com o servidor.");
                    }
                }
                else if(server.getConectionType() == 10)
                {

                }

            } catch (Exception e) {
                System.out.println("Erro ao exercutar copia dos arquivos");
                status = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "ENVIADO ARQUIVOS...", Toast.LENGTH_LONG).show();
        }
    }
}
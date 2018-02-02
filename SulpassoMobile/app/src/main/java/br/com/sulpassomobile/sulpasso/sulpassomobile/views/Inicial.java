package br.com.sulpassomobile.sulpasso.sulpassomobile.views;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.controle.TelaInicial;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Mensagem;
import br.com.sulpassomobile.sulpasso.sulpassomobile.util.Enumarations.TipoVenda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaGerencialMensagem;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaItensKits;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaPedidosLista;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaPedidosResumo;

public class Inicial extends AppCompatActivity
{
    private TelaInicial controle;
    private final int REQUEST_LOGIN = 0;
    private final int REQUEST_CONFIG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        try { this.controle = new TelaInicial(getApplicationContext()); }
        catch (Exception e) { }

        if(this.controle == null)
        {
            Intent atualizar = new Intent(getApplicationContext(), Atualizacao.class);
            startActivityForResult(atualizar, REQUEST_CONFIG);
        }
        else
        {

            if(validar_data_sistema(4))
            {
                if (this.controle.controleAcesso())
                {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(i, REQUEST_LOGIN);
                }
                else
                {
                    this.fragmentoCentral();

                    this.indicarAcesso();
                }
            }
            else
            {
                this.mensagem("Por favor,\nverifique a data e hora de seu sipositvo antes de iniciar o sistema");
                finish();
            }
        }
/*
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus = doWork(mProgressStatus);
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgressStatus++;
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start();
*/

//        inserirDadosHardCoded();
//        listDataHardCoded();
//        ((TextView) findViewById(R.id.vendas)).setText(this.listDataHardCoded());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.inicial_agenda :
                break;
            case R.id.inicial_atualizar :
                Intent ia = new Intent(getApplicationContext(), Atualizacao.class);
                startActivity(ia);
                break;
            case R.id.inicial_clientes :
                break;

            case R.id.consultas_clientes :
                Intent cc = new Intent(getApplicationContext(), ConsultasClientes.class);
                startActivity(cc);
                break;
            case R.id.consultas_gerenciais :
                Intent cg = new Intent(getApplicationContext(), ConsultasGerenciais.class);
                startActivity(cg);
                break;
            case R.id.consultas_itens :
                Intent ci = new Intent(getApplicationContext(), ConsultasItens.class);
                startActivity(ci);
                break;
            case R.id.consultas_pedidos:
                Intent cp = new Intent(getApplicationContext(), ConsultasPedidos.class);
                startActivity(cp);
                break;

            case R.id.inicial_pedidos :
                if(this.validar_data_sistema(5))
                {
                    if(this.validar_hora_sistema())
                    {
                        switch (this.controle.tipoVenda())
                        {
                            case 13 :
                                this.dialogo(3);
                                //this.dialogo_sem_direta();
                                break;
                            case 12 :
                                this.dialogo(2);
                                //this.dialogo_sem_troca();
                                break;
                            case 123 :
                                this.dialogo(1);
                                break;
                            default :
                                this.aberturaVendas(TipoVenda.NORMAL.getValue(), null);
                                break;
                        }
                    }
                    else
                    {
                        this.mensagem("Você não possui permissão para efetuar pedidos nesse hora");
                    }
                }
                else
                {
                    this.mensagem("Preços desatualizados.\nAtualize os dados antes de proseguir.");
                }
                break;
            case R.id.inicial_sair :
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == REQUEST_LOGIN)
        {
            if (resultCode == RESULT_OK)
            {
                Toast.makeText(Inicial.this, "BEM VINDO", Toast.LENGTH_LONG).show();

                this.fragmentoCentral();

                this.indicarAcesso();
            }
            else
            {
                //onDestroy();
                finish();
            }
        }
        if (requestCode == REQUEST_CONFIG)
        {
            Toast.makeText(Inicial.this, "Para validar as configurações o sistema deve ser reiniciado", Toast.LENGTH_LONG).show();
            finish();
        }
    }
/**************************************************************************************************/
/********************************END OF ACTIVITY LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************ACTIVITY ACCESS METHODS******************************************/
/**************************************************************************************************/
    private void fragmentoCentral()
    {
        Fragment fragment = null;
        switch (this.controle.fragmentoCentral())
        {
            /*
                TODO: Criar frgments consultas METAS, GRAFICOS, PRODUTOS FOCO, PLANO VISITAS, POSITIVACAO
             */
            case 0:
                fragment = new ConsultaGerencialMensagem();
                break;
            case 1:
                fragment = new ConsultaItensKits();
                break;
            case 2:
                fragment = new ConsultaPedidosLista();
                break;
            case 3:
                fragment = new ConsultaPedidosResumo();
                break;
            case 4:
                fragment = new ConsultaItensKits();
                break;
            case 5:
                fragment = new ConsultaItensKits();
                break;
            case 6:
                fragment = new ConsultaItensKits();
                break;
            case 7:
                fragment = new ConsultaItensKits();
                break;
            default:
                break;
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.fragmentInicial, fragment).addToBackStack(null).commit();
            }
            else
            {
                fragmentManager.beginTransaction().replace(R.id.fragmentInicial, fragment).addToBackStack(null).commit();
            }
        }
        else
        {
            System.out.println("Não existem dados para exibir na tela inicial");
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    public ArrayList<Mensagem> buscarListaMensagens()
    {
        return this.controle.buscarMensagens();
    }

    private Boolean validar_data_sistema(int tipo)
    {
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sf2;
        sf2 = new SimpleDateFormat("dd-MM-yyyy kk:mm");
        Date today = new Date();
        Date aday = null;
        String data_banco = "";
        String data_sistema = "";

        Boolean data_valida = false;

        data_banco = this.controle.dataHora(tipo);
        data_sistema = sf.format(today);

        try
        {
            if(tipo == 4)
            {
                aday = sf2.parse(data_banco);
            }
            else
            {
                sf2 = new SimpleDateFormat("dd-MM-yyyy");
                aday = sf2.parse(data_banco);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
/*
        today.setHours(00);
        today.setMinutes(00);
        today.setSeconds(00);
*/
        if (tipo == 5)
        {
            if (today.compareTo(aday) < 0)
                data_valida = true;
            else
                data_valida = false;
        } else
        {
            if (today.compareTo(aday) < 0)
                data_valida = false;
            else
                data_valida = true;
        }

        return data_valida;
    }

    private Boolean validar_hora_sistema()
    {
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("HH:mm");
        Date now = new Date();
        Date begin = null;
        Date end = null;
        String hora_agora = "" + now.getHours() + ":" + now.getMinutes();
        String hora_inicio_m = "";
        String hora_fim_m = "";
        String hora_inicio_t = "";
        String hora_fim_t = "";
/*
        Boolean data_valida = false;
        Banco_Consulta_Configuracao buscar_configuracao = new Banco_Consulta_Configuracao(this__);

        buscar_configuracao.open();
        hora_inicio = buscar_configuracao.buscar_hora(1);
        hora_fim = buscar_configuracao.buscar_hora(0);
        buscar_configuracao.close();
*/
        Boolean data_valida = false;

        hora_inicio_m = this.controle.dataHora(0);
        hora_fim_m = this.controle.dataHora(2);
        hora_inicio_t = this.controle.dataHora(1);
        hora_fim_t = this.controle.dataHora(3);
        try
        {
            if(now.getHours() >= 12)
            {
                now = sf.parse(hora_agora);
                begin = sf.parse(hora_inicio_t);
                end = sf.parse(hora_fim_t);
            }
            else
            {
                now = sf.parse(hora_agora);
                begin = sf.parse(hora_inicio_m);
                end = sf.parse(hora_fim_m);
            }
        }
        catch (Exception e) { }

        if ((now.compareTo(begin) >= 0) && (now.compareTo(end) < 0))
            data_valida = true;
        else
            data_valida = false;

        return data_valida;
    }

    private void indicarAcesso()
    {
        Date today = new Date();
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd-MM-yyyy kk:mm");

        String d = "" + sf.format(today);

        this.controle.novoAcesso(d);
    }

    private void mensagem(String texto)
    {
        Toast t = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
        t.show();
    }

    private void aberturaVendas(int tv, String direta)
    {
        if(tv == TipoVenda.DIRETA.getValue() && direta == null)
        {
            buscarSelecaoDiretas(tv);
        }
        else
        {
            Intent i = new Intent(getApplicationContext(), Pedido.class);
            i.putExtra("TIPOVENDA", tv);
            i.putExtra("DIRETA", direta);
            startActivity(i);
        }
    }

    private void dialogo (final int opt)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("TIPO VENDA");
        alert.setCancelable(false);

        final int[] selecao = new int[1];
        selecao[0] = TipoVenda.NORMAL.getValue(); //Inicialização para caso sejá apenas clicado no botão OK sem selecionar um tipo de venda

        String array_spinner[];

        switch (opt)
        {
            case 1 :
                array_spinner = new String[3];

                array_spinner[0]="Pedido";
                array_spinner[1]="Venda Direta";
                array_spinner[2]="Troca";
                break;
            case 2 :
                array_spinner = new String[2];

                array_spinner[0]="Pedido";
                array_spinner[1]="Venda Direta";
                break;
            case 3 :
                array_spinner = new String[2];

                array_spinner[0]="Pedido";
                array_spinner[1]="Troca";
                break;
            default :
                array_spinner = new String[3];

                array_spinner[0]="Pedido";
                array_spinner[1]="Venda Direta";
                array_spinner[2]="Troca";
                break;
        }

        final ListView input = new ListView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, array_spinner);
        input.setAdapter(adapter);

        input.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
            {
                selecao[0] = arg2;
            }
        });

        alert.setNeutralButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (opt)
                {
                    case 1 : //Normal / Diretas / Troca
                    case 2 : //Normal / Diretas
                        aberturaVendas(selecao[0], null);
                        break;
                    case 3 : //Normal / Troca
                        if(selecao[0] == 0)
                            aberturaVendas(selecao[0], null);
                        else
                            aberturaVendas(2, null);
                        /*
                          Indiferente do tipo de venda selecionado é passado null para que seja
                          selecionado o tipo de venda direta em um ponto único e que faça isso de maneira
                          exclusiva sem atrapalhar o restante do processo e que não seja necessário
                          a criação de dois metodos para a chamada a nova activity;
                         */
                        break;
                }
            }
        });

        alert.setView(input);

        alert.show();
    }

    private void buscarSelecaoDiretas(final int tv)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Tipo Venda Direta");
        alert.setCancelable(false);
        final String[] retorno = new String[1];
        retorno[0] = "--";

        final ArrayList<String> array_spinner = controle.buscarDiretasStr();

        final ListView input = new ListView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, array_spinner);
        input.setAdapter(adapter);

        input.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
            {
                retorno[0] = array_spinner.get(arg2);
                System.out.println(retorno[0]);
            }
        });

        alert.setNeutralButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(retorno[0].equalsIgnoreCase("--"))
                    buscarSelecaoDiretas(tv);
                else
                    aberturaVendas(tv, retorno[0]);
            }
        });

        alert.setView(input);

        alert.show();
    }
/**************************************************************************************************/
/********************************END OF ACTIVITY LIFE CICLE****************************************/
/**************************************************************************************************/
/*********************************ACTIVITY ACCESS METHODS******************************************/
/**************************************************************************************************/
    public String dadosEmpresaCliente(int campo)
    {
        String retorno = "";
        switch (campo)
        {
            case R.id.txtFicNome:
                retorno = this.controle.nomeEmpresa();
                break;
            case R.id.txtFicSecond:
                retorno = this.controle.enderecoEmpresa();
                break;
            case R.id.txtFicFone:
                retorno = this.controle.foneEmpresa();
                break;
            case R.id.txtFicMail:
                retorno = this.controle.emailEmpresa();
                break;
            case R.id.txtFicWeb:
                retorno = this.controle.siteEmpresa();
                break;
            case R.id.txtFicVendedor:
                retorno = this.controle.vendedor();
                break;
            default:
                retorno = "";
                break;
        }
        return retorno;
    }

    public String desenvolvedor(int campo)
    {
        String retorno = "";
        switch (campo)
        {
            case R.id.fidTxtValidade:
                retorno = "Validade: " + this.controle.validade();
                break;
            case R.id.fidTxtVersao:
                retorno = this.controle.versao();
                break;
            default:
                retorno = "";
                break;
        }
        return retorno;
    }
}
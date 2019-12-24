package br.com.sulpasso.sulpassomobile.views;

import android.Manifest;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.controle.ConsultaGerencial;
import br.com.sulpasso.sulpassomobile.controle.TelaInicial;
import br.com.sulpasso.sulpassomobile.modelo.Mensagem;
import br.com.sulpasso.sulpassomobile.modelo.Meta;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TipoVenda;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;
import br.com.sulpasso.sulpassomobile.util.funcoes.Permissions;
import br.com.sulpasso.sulpassomobile.util.funcoes.SenhaLiberacao;
import br.com.sulpasso.sulpassomobile.util.services.Email;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaGerencialMensagem;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaGerencialMetas;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensKits;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaItensMainFragment;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaPedidosLista;
import br.com.sulpasso.sulpassomobile.views.fragments.ConsultaPedidosResumo;
import me.drakeet.materialdialog.MaterialDialog;

/*TODO: Verificar todos os cursores se estão sendo encerrados e a memória liberada
            c.close();
            SQLiteDatabase.releaseMemory();
*/
public class Inicial extends AppCompatActivity
{
    private TelaInicial controle;
    private final int REQUEST_LOGIN = 0;
    private final int REQUEST_CONFIG = 1;

    private boolean acessoConfirmado;

    private SenhaLiberacao sl;
    private String chave;
    private String senha;

    private boolean solicitarSenhaHora;

    private MaterialDialog mMaterialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        this.acessoConfirmado = false;

        Permissions.checkAndRequestPermissions(this);

        if(Permissions.hasPermissions(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            try
            {
                int version;

                try { version = Integer.valueOf(Build.VERSION.SDK); }
                catch (Exception ev){ version = 3; }

                File systemFolder;

                if(version >= 19) { systemFolder = new File("/storage/emulated/0/MobileVenda", "ERROS.txt"); }
                else { systemFolder = new File("sdcard/MobileVenda", "ERROS.txt"); }

                if(!(systemFolder.exists() && systemFolder.isDirectory()))
                {
                    if(version >= 19)
                    {
                        systemFolder = new File("/storage/emulated/0/MobileVenda", "ERROS.txt");
                        systemFolder.mkdirs();
                    }
                    else
                    {
                        systemFolder = new File("sdcard/MobileVenda", "ERROS.txt");
                        systemFolder.mkdirs();
                    }

                /*
                File outputFile = new File(wallpaperDirectory, filename);
                FileOutputStream fos = new FileOutputStream(outputFile);
                */
                }
                else { /*****/ }
            }
            catch(Exception e) { /*****/ }
        }
        else
        {
            Permissions.checkAndRequestPermissions(this);
        }

        try { this.controle = new TelaInicial(getApplicationContext()); }
        catch (Exception e) { }

        if(this.controle == null)
        {
            Intent atualizar = new Intent(getApplicationContext(), br.com.sulpasso.sulpassomobile.views.Atualizacao.class);
            startActivityForResult(atualizar, REQUEST_CONFIG);
        }
        else
        {
            if(validar_data_sistema(4))
            {
//TODO: Verificar as formas de atualização automáticas
//                showUpdateAppDialog();
//                showUpdateAppDialog("1.98SN");
//                showUpdateAppDialog(917);
//                new VersionRequester().execute();

                this.iniciarSistema();
            }
            else
            {
                this.solicitarSenhaHora = true;
                this.mensagem("Por favor,\nverifique a data e hora de seu dipositvo antes de iniciar o sistema");
//                finish();
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
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();

        try { this.controle = new TelaInicial(getApplicationContext()); }
        catch (Exception e) { }

        if(this.controle == null)
        {
            Intent atualizar = new Intent(getApplicationContext(), br.com.sulpasso.sulpassomobile.views.Atualizacao.class);
            startActivityForResult(atualizar, REQUEST_CONFIG);
        }
        else
        {
//            this.serviceIntialize();
            if(validar_data_sistema(4))
            {
                if(this.acessoConfirmado)
                {
                    this.fragmentoCentral();

                    this.indicarAcesso();
                }
                else
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
            }
            else
            {
                this.solicitarSenhaHora = true;
                this.mensagem("Por favor,\nverifique a data e hora de seu dipositvo antes de iniciar o sistema");
//                finish();
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        /*
        try { this.controle = new TelaInicial(getApplicationContext()); }
        catch (Exception e) { }

        if(this.controle == null)
        {
            Intent atualizar = new Intent(getApplicationContext(), br.com.sulpasso.sulpassomobile.views.Atualizacao.class);
            startActivityForResult(atualizar, REQUEST_CONFIG);
        }
        else
        {
            this.serviceIntialize();
            if(validar_data_sistema(4))
            {
                if(this.acessoConfirmado)
                {
                    this.fragmentoCentral();

                    this.indicarAcesso();
                }
                else
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
            }
            else
            {
                this.mensagem("Por favor,\nverifique a data e hora de seu dipositvo antes de iniciar o sistema");
//                finish();
            }
        }
        */
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
        Toast t;

        switch (item.getItemId())
        {
            /*
            case R.id.inicial_agenda :

                t = Toast.makeText(getApplicationContext(), "AGENDA DE CLIENTES INDISPONIVEL", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();

                break;
            */
            case R.id.inicial_atualizar :
                Intent ia = new Intent(getApplicationContext(), Atualizacao.class);
                startActivity(ia);
                break;
            case R.id.inicial_clientes :
//                Intent ic = new Intent(getApplicationContext(), CadastroClientes.class);
                Intent ic = new Intent(getApplicationContext(), CadastroClientesFragmentalized.class);
                startActivity(ic);
                /*
                t = Toast.makeText(getApplicationContext(), "CADASTRO CLIENTES INDISPONIVEL", Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                t.show();
                */
                break;
            case R.id.inicial_ajuda :
                exibirAjuda();
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
                        this.solicitarSenhaHora = false;
                        this.mensagem("Você não possui permissão para efetuar pedidos nesse hora");
                    }
                }
                else
                {
                    this.solicitarSenhaHora = false;
                    this.mensagem("Preços desatualizados.\nAtualize os dados antes de prosseguir.");
                }
                break;
            /*
            case R.id.inicial_sair :
                onDestroy();
                break;
            */
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
                this.acessoConfirmado = true;

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
    private void exibirAjuda()
    {
        //TODO: Alterar essa mensagem de acordo com cada atualização;
        String mensagem;
        /* MENSAGEM DA VERSÃO 1.98
        mensagem = "CADASTRO CLIENTES: Correção das mensagens de campos não preenchidos.\n" +
                "KITS: Ajustado a apresentação dos Kits na consulta (Consultas -> Itens -> Mais Opções -> Kits).\n" +
                "VALOR UNITÁRIO: Modificado forma de calculo do valor unitário na digitação dos itens.\n" +
                "SOLICITAR SENHA: Funcionalidade inserida para cliente específico.\n" +
                "SELEÇÃO DE NATUREZA: Corrigido a seleção das naturezas e prazos no inicio do pedido.\n" +
                "DIGITAÇAO DE SENHA: Corrigido erro nos aparelhos com android acima da versão 7.5.\n" +
                "PASTA DO SISTEMA: Criação automática da pasta para armazenamento dos pedidos ao se iniciar " +
                    "o sistema (considerando que as permições tenham sido aceitas pelo usuário).\n" +
                "JUSTIFICATIVA: Corrigido o erro ao inserir justificativa no inicio do pedido (ainda" +
                " falta corrigir a justificativa no final do pedido).\n" +
                "AJUDA: Essa tela será apresentada sempre que houver uma atualização do sistema ou através do menu (Ajuda).";

        mensagem = "PRMOÇÕES: Corrigido o erro ao inserir o valor promocional em um item.\n" +
                "CAMPANHAS: Ajuste no tratamento das campanhas permitindo mais de uma campanha por grupo.\n" +
                "CAMPANHAS: Correçao da falha ao tratar a quantidade das campanhas (tanto por grupo quanto por produto).\n" +
                "SENHA: Correção da aplicação dos descotos liberados na alteração do pedido.\n" +
                "ALTERAÇAO PEDIDOS: Correção na verificação de data e hora na alteração dos pedidos.\n" +
                "ABC CLIENTES: Correção no erro de apresentação da consulta ABC.\n" +
                "PRE PEDIDO: Corrigido o erro ao inserir itens vendido por kilo direto pelo pre pedido";
        */

        mensagem = "LISTA ITENS: Em função do grande número de erros que estava ocorrendo na digitação do pedido o layout da lista de itens foi reduzido ao minimo de informações necessárias para que se execute o pedido.\n" +
                "ALTERAÇÃO DE TELAS PEDIDO: Também em função dos erros que vinham ocorrendo foi realizado um bloqueio na tela de clientes. Após selecionar o cliente e passar para a tela de itens não é mais possível retornar para a seleção de clientes.\n" +
                "Esperamos que as modificações realizadas resolvam de uma vez os prolemas de o sistema travar no meio do pedido enquanto trabalhamos para retornar as funcionalidades sem arcar com os problemas.";

        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(mensagem).setTitle("AJUDA ATUALIZAÇÃO");

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void fragmentoCentral()
    {
        Fragment fragment = null;
        switch (this.controle.fragmentoCentral())
        {
            case 0:
                fragment = new ConsultaGerencialMensagem();
                break;
            case 1:
                fragment = new ConsultaItensMainFragment();
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
            case 16:
                fragment = new ConsultaGerencialMetas();
                break;
            default:
                break;
        }

        /*
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
        */
        if(fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            /*
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
            */

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        //            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1)
            {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.fragmentInicial, fragment).commit();
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
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
            aday = today;
            e.printStackTrace();
        }

        if (tipo == 5)
        {
            today.setHours(00);
            today.setMinutes(00);
            today.setSeconds(00);

            int compara = aday.compareTo(today);

            int daya, dayt, montha, montht, yeara, yeart;

            daya = aday.getDate();
            dayt = today.getDate();
            montha = aday.getMonth();
            montht = today.getMonth();
            yeara = aday.getYear();
            yeart = today.getYear();

            compara = yeara > yeart ? 1 : yeara == yeart ?
                (montha > montht ? 1 : montha == montht ? (daya >= dayt ? 1 : -1) : -1) : -1;

            if (compara >= 0)
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


        if(this.solicitarSenhaHora)
        {
            this.sl = new SenhaLiberacao();
            this.chave = sl.getChave();
            solicitarSenha(Integer.parseInt(this.chave));
        }
    }

    private void aberturaVendas(int tv, String direta)
    {
        if(tv == TipoVenda.DIRETA.getValue() && direta == null)
        {
            buscarSelecaoDiretas(tv);
        }
        else
        {
            Intent i;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
//                i = new Intent(getApplicationContext(), PedidoNoFragments.class);
                i = new Intent(getApplicationContext(), Pedido.class);
            }
            else
            {
                i = new Intent(getApplicationContext(), Pedido.class);
            }

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
        //Inicialização para caso seja apenas clicado no botão OK sem selecionar um tipo de venda
        selecao[0] = TipoVenda.NORMAL.getValue();

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,android.R.layout.simple_spinner_dropdown_item, array_spinner);
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
                        break;
                /*
                  Indiferente do tipo de venda selecionado é passado null [aberturaVendas()] para que seja
                  selecionado o tipo de venda direta em um ponto único e que faça isso de maneira
                  exclusiva sem atrapalhar o restante do processo e que não seja necessário
                  a criação de dois metodos para a chamada à nova activity;
                 */
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

    private void serviceIntialize()
    {
        //TODO: É necessário reverificar essa funcionalidade pois pode estar causando problemas.
        Intent _service = new Intent(this, Email.class);
        boolean running = false;

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if(Email.class.getName().equals(service.service.getClassName()))
            {
                running = true;
                break;
            }
        }

        if (running){ /*Toast.makeText(this, "Running", Toast.LENGTH_LONG).show();*/ }
        else
        {
            startService(_service);
            /*Toast.makeText(this, "Staring", Toast.LENGTH_LONG).show();*/
        }
    }

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

    /*****
     https://stackoverflow.com/questions/5015094/how-to-determine-device-screen-size-category-small-normal-large-xlarge-usin
    *****/
    public String desenvolvedor(int campo)
    {
        String retorno = "";
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;
        int size = (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK);
        int mask = getResources().getConfiguration().screenLayout;

        switch (campo)
        {
            case R.id.fidTxtValidade:
                if(size <= Configuration.SCREENLAYOUT_SIZE_NORMAL)
                {
                    retorno = this.controle.validade();
                }
                else
                {
                    retorno = "Validade: " + this.controle.validade();
                }

                break;
            case R.id.fidTxtVersao:
                retorno = this.controle.versao();

                if(size <= Configuration.SCREENLAYOUT_SIZE_NORMAL)
                {
                    retorno = retorno.substring(7);
                }
                break;
            default:
                retorno = "";
                break;
        }
        return retorno;
    }

    private void solicitarSenha(final int chave)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Hora Alterada");
        alert.setMessage("A hora atual é anteriror a última hora de acesso ao sistema.\nVerifique a data e hora do sistema antes de procesguir.\nChave de acesso: " + chave);
        alert.setCancelable(false);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);

        alert.setView(input);

        alert.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                senha = input.getText().toString();
                Proseguir();
            }
        });
        alert.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                senha = "00000000";
                Proseguir();
            }
        });
        alert.show();
    }

    private void Proseguir()
    {
        boolean liberado;

        liberado = sl.verificaChave(senha);

        if (liberado) { iniciarSistema(); }
        else { finish(); }
    }

    private void iniciarSistema()
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
//            this.serviceIntialize();

            String versionName = "";
            PackageInfo packageInfo;
            String validade = "";
            ManipulacaoStrings ms = new ManipulacaoStrings();
            try
            {
                packageInfo = getApplicationContext().getPackageManager().getPackageInfo(this.getPackageName(), 0);//Isso pega a versão do aplicativo direto do manifesto
                versionName = "" + packageInfo.versionName /*+ this.configurador.getConfigHor().getAtualizacao()*/;

                if(!versionName.equalsIgnoreCase(this.controle.nomeVersao()))
                {
                    exibirAjuda();
                    this.controle.atualizarVersao(versionName);
                }
            }
            catch (PackageManager.NameNotFoundException e) { e.printStackTrace(); }
        }
    }
/**************************************************************************************************/
/******************************END ACTIVITY ACCESS METHODS*****************************************/
/**************************************************************************************************/
    public void DisplyaUpdateAppDialog(){
        mMaterialDialog = new MaterialDialog(this)
                /*
                <resources>
        <string name="app_name">Jaguar App</string>
        <string name="dialog_title">Nova Versão do App</string>
        <string name="dialog_message">Está disponível a nova versão do aplicativo Jaguar App, clique no botão abaixo para realizar a atualização. Essa nova versão é mais leve e segura.</string>
        <string name="dialog_positive_label">Atualizar</string>
        <string name="dialog_negative_label">Depois</string>
    </resources>
                 */
                .setTitle( "Nova Versão do App" )
                .setMessage( "Está disponível a nova versão do aplicativo Jaguar App, clique no botão abaixo para realizar a atualização. Essa nova versão é mais leve e segura." )
                .setCanceledOnTouchOutside(false)
                .setPositiveButton( "Atualizar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String packageName = getPackageName();
                        Intent intent;

                        try {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
                            startActivity( intent );
                        }
                        catch (android.content.ActivityNotFoundException e) {
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
                            startActivity( intent );
                        }
                    }
                })
                .setNegativeButton( "Depois", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                    }
                });

        mMaterialDialog.show();
    }

    private PackageInfo packageInfo(){
        PackageInfo pinfo = null;
        try {
            String packageName = this.getPackageName();
            pinfo = this.getPackageManager().getPackageInfo(packageName, 0);
        }
        catch(PackageManager.NameNotFoundException e){}

        return pinfo;
    }

    public void showUpdateAppDialog( int actuallyAppVersion ){
        SPLocalBase splb = new SPLocalBase();
        int versionNumber = packageInfo().versionCode;

        if( actuallyAppVersion > versionNumber
                && splb.is24hrsDelayed(this) ){

            this.DisplyaUpdateAppDialog();
        }
    }

    public void showUpdateAppDialog( String actuallyAppVersion ){
        SPLocalBase splb = new SPLocalBase();
        String versionName = packageInfo().versionName;

        if( !actuallyAppVersion.equals(versionName)
                && splb.is24hrsDelayed(this) ){

            this.DisplyaUpdateAppDialog();
        }
    }

    public void showUpdateAppDialog(){
        SPLocalBase splb = new SPLocalBase();
        int versionNumber = packageInfo().versionCode;
        int versionToCompare = splb.getVersion(this);

        if( versionToCompare > versionNumber ){
            //&& SPTimer.is24hrsDelayed(activity) ){

            this.DisplyaUpdateAppDialog();
        }
    }

    private class VersionRequester extends AsyncTask<Void, Void, String>
    {
        /*
        private WeakReference<Presenter> presenter;

        public VersionRequester( Presenter p ){
            presenter = new WeakReference<>( p );
        }
        */

        @Override
        protected String doInBackground(Void... voids) {
            String version = null;
            String packageName = getPackageName();
            try{
                version = Jsoup
                        .connect("https://play.google.com/store/apps/details?id=" + packageName)
                        .get()
                        .select("div[itemprop=\"softwareVersion\"]")
                        .text()
                        .trim();
                /*
                version = Jsoup
                        .connect("https://play.google.com/store/apps/details?id=br.thiengocalopsita")
                        .get()
                        .select("div[itemprop=\"softwareVersion\"]")
                        .text()
                        .trim();
                */
            }
            catch (IOException e){}

            return version;
        }

        @Override
        protected void onPostExecute(String version) {
            super.onPostExecute(version);
            showUpdateAppDialog( version );
        }
    }

    private class SPLocalBase {
        private static final String PREF = "PREFERENCES";
        private static final String TIME_KEY = "time";
        private static final String VERSION_KEY = "version";
        private static final long DELAY = 24*60*60*1000;

        private /*static*/ void saveTime(Context context){
            SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
            sp.edit().putLong(TIME_KEY, System.currentTimeMillis() + DELAY).apply();
        }

        public /*static*/ boolean is24hrsDelayed(Context context){
            SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
            Long time = sp.getLong(TIME_KEY, 0);
            Long timeCompare = System.currentTimeMillis();

            if( time < timeCompare ){
                saveTime(context);
                return true;
            }
            return false;
        }

        public /*static*/ void saveVersion(Context context, int version){
            SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
            sp.edit().putInt(VERSION_KEY, version).apply();
        }

        public /*static*/ int getVersion(Context context){
            SharedPreferences sp = context.getSharedPreferences(PREF, Context.MODE_PRIVATE);
            return sp.getInt(VERSION_KEY, 0);
        }
    }

    public ArrayList<Meta> buscarListaMetas()
    {
        ConsultaGerencial cg = new ConsultaGerencial(getApplicationContext());
        return cg.buscarListaMetas();
    }

    public float buscarMetaIdeal()
    {
        ConsultaGerencial cg = new ConsultaGerencial(getApplicationContext());
        return cg.buscarMetaIdeal();
    }

    public String buscarMetaTotal(int tipo)
    {
        ConsultaGerencial cg = new ConsultaGerencial(getApplicationContext());
        return Formatacao.format2d(cg.buscarMetaTotal(tipo));
    }

    public String buscarMeta(int posicao, int campo, int tipo)
    {
        ConsultaGerencial cg = new ConsultaGerencial(getApplicationContext());
        cg.buscarListaMetas();
        return cg.buscarMeta(posicao, campo, tipo);
    }

/*
    private class JsonHttpRequest extends JsonHttpResponseHandler {
        public static final String URI = "http://192.168.25.221:8888/jaguar-app/ctrl/CtrlAdmin.php";
        public static final String METODO_KEY = "metodo";
        public static final String METODO_JAGUARS = "get-jaguars";

        private Presenter presenter;

        public JsonHttpRequest( Presenter presenter ){
            this.presenter = presenter;
        }

        @Override
        public void onStart() {
            presenter.showProgressBar( true );
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            try{
                presenter.showUpdateAppDialog( response.getInt("version") );
                onSuccess(statusCode, headers, response.getJSONArray("jaguars"));
            }
            catch(JSONException e){}
        }

        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
            Gson gson = new Gson();
            ArrayList<Jaguar> jaguars = new ArrayList<>();
            Jaguar j;

            for( int i = 0; i < response.length(); i++ ){
                try{
                    j = gson.fromJson( response.getJSONObject( i ).toString(), Jaguar.class );
                    jaguars.add( j );
                }
                catch(JSONException e){}
            }

            presenter.updateListaRecycler( jaguars );
            //new VersionRequester(presenter).execute();
        }

        @Override
        public void onFinish() {
            presenter.showProgressBar( false );
        }
    }
*/
}
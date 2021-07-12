package br.com.sulpasso.sulpassomobile.util.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorConexao;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorEmpresa;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorHorarios;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorUsuario;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipularArquivos;
import br.com.sulpasso.sulpassomobile.util.funcoes.WebMail;

/**
 * Created by Lucas on 13/06/2018 - 15:55 as part of the project SulpassoMobile.
 */
public class Email extends Service
{
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private String data_arquivos = "";

    private ConfiguradorConexao conexao;
    private ConfiguradorHorarios horarios;
    private ConfiguradorUsuario vendedor;
    private ConfiguradorEmpresa empresa;

    private final class ServiceHandler extends Handler
    {
        public ServiceHandler(Looper looper){ super(looper); }

        @Override
        public void handleMessage(Message msg)
        {
            boolean finish = false;
            long endTime;
            int email_enviado;
            String email_data;
            String data;
            Calendar today;

            ConfiguradorDataAccess cda = new ConfiguradorDataAccess(getApplicationContext());
            ManipulacaoStrings ms = new ManipulacaoStrings();

            today = Calendar.getInstance();
            int hour;
            int minutes;
            int day;
            int month;
            int year;
            int hour_email;
            int minutes_email;
            String horaFinal;

            try { conexao = cda.getConexao(); }
            catch (Exception ex) { /*****/ }

            try { horarios = cda.getHorario(); }
            catch (Exception ex) { /*****/ }

            try { vendedor = cda.getUsuario(); }
            catch (Exception ex) { /*****/ }

            try { empresa = cda.getEmpresa(); }
            catch (Exception ex) { /*****/ }

            day = today.get(Calendar.DAY_OF_MONTH);
            month = today.get(Calendar.MONTH);
            year = today.get(Calendar.YEAR);

            data = ms.comEsquerda(String.valueOf(day), "0", 2) + "/" +
                    ms.comEsquerda(String.valueOf(month + 1), "0", 2) + "/" +
                    ms.comEsquerda(String.valueOf(year), "0", 4);

            hour_email = Integer.parseInt(horarios.getFinalTarde().substring(0, 2));
            minutes_email = Integer.parseInt(horarios.getFinalTarde().substring(3));

            while (!finish)
            {
                boolean email_antigo = false;//Essa váriavel não está sendo utilizada na verdade

                if(email_antigo)
                {
                    synchronized (this)
                    {
                        try
                        {
                            //esse código está incorreto
                            data_arquivos = ms.comEsquerda((day - 1 < 1? "1" : String.valueOf(day -1)), "0", 2) + "/" +
                                    ms.comEsquerda(String.valueOf(month + 1), "0", 2) + "/" +
                                    ms.comEsquerda(String.valueOf(year), "0", 4);

                            finish = create_email();

                            cda.alterarDataEmail(ms.dataBanco(data));
                        }
                        catch (Exception e) { /*****/ }
                    }
                }

                if (/*email_enviado == 1 && email_data.equalsIgnoreCase(data)*/
                        conexao.getDataEmailEnviado() != null && ms.dataVisual(conexao.getDataEmailEnviado()).equalsIgnoreCase(data))
                {
                    finish = true;
                }
                else
                {
                    /*
                    try { cda.alterarDataEmail(ms.dataBanco(data)); }
                    catch (UpdateExeption updateExeption) { updateExeption.printStackTrace(); }
                    */

                    today = Calendar.getInstance();
                    hour = today.get(Calendar.HOUR_OF_DAY);
                    minutes = today.get(Calendar.MINUTE);
                    if (hour == hour_email)
                    {
                        if (minutes >= minutes_email)
                        {
                            synchronized (this)
                            {
                                try
                                {
                                    data_arquivos = ms.comEsquerda(String.valueOf(day), "0", 2) + "/" +
                                            ms.comEsquerda(String.valueOf(month + 1), "0", 2) + "/" +
                                            ms.comEsquerda(String.valueOf(year), "0", 4);

                                    finish = create_email();
                                    if (finish)
                                    {
                                        cda.alterarDataEmail(ms.dataBanco(data));
                                    }
                                }
                                catch (Exception e) { /*****/ }
                            }
                        }
                    } else
                    {
                        if (hour > hour_email)
                        {
                            synchronized (this)
                            {
                                try
                                {
                                    data_arquivos = ms.comEsquerda(String.valueOf(day), "0", 2) + "/" +
                                            ms.comEsquerda(String.valueOf(month + 1), "0", 2) + "/" +
                                            ms.comEsquerda(String.valueOf(year), "0", 4);

                                    finish = create_email();
                                    if (finish) { cda.alterarDataEmail(ms.dataBanco(data)); }
                                }
                                catch (Exception e) { /*****/ }
                            }
                        }
                        else
                        {
                            endTime = System.currentTimeMillis() + 120 * 1000;
                            while(System.currentTimeMillis() < endTime)
                            {
                                synchronized (this)
                                {
                                    try
                                    {
                                        System.out.println("" + ((endTime - System.currentTimeMillis())/1000));
                                        wait(endTime - System.currentTimeMillis());
                                        System.out.println("" + ((endTime - System.currentTimeMillis())/1000));
                                    }
                                    catch (Exception e) { /*****/ }
                                }
                            }
                        }
                    }
                }
            }

            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate()
    {
        HandlerThread thread = new HandlerThread("ServiceStartArguments", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(this, "Service starting", Toast.LENGTH_SHORT).show();

        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onDestroy(){ Toast.makeText(this, "Email encaminhado aos supervisores.", Toast.LENGTH_LONG).show(); }

    private boolean create_email()
    {
        ManipularArquivos anexos = new ManipularArquivos(getApplicationContext());
        String path = Environment.getExternalStorageDirectory() + "/MobileVenda";
        String name = "PlanoVisita.txt";
        String name1 = "ProdutoFoco.txt";
        String name2 = "ResumoDia.txt";
        String name3 = "Graficos.txt";

        String vistas = "PlanoVisita.txt";
        String foco = "ProdutoFoco.txt";
        String resumo = "ResumoDia.txt";
        String graficos = "Graficos.txt";

        try { vistas = anexos.plano_visitas(name, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { /*****/ }

        try { foco = anexos.produtos_foco(name1, data_arquivos, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { /*****/ }

        try { resumo = anexos.resumo_dia(name2, data_arquivos, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { /*****/ }

        try { graficos = anexos.graficos(name3, data_arquivos, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { /*****/ }

        WebMail wm = new WebMail();
        boolean ret = false;

        try
        {
            ret = wm.postData(vendedor.getCodigo(), empresa.getCodigo(), 1, vistas);
            ret = wm.postData(vendedor.getCodigo(), empresa.getCodigo(), 2, foco);
            ret = wm.postData(vendedor.getCodigo(), empresa.getCodigo(), 3, resumo);
            ret = wm.postData(vendedor.getCodigo(), empresa.getCodigo(), 4, graficos);

            ret = wm.sendMail(vendedor.getCodigo(), empresa.getCodigo());
        }
        catch (UnsupportedEncodingException e) { e.printStackTrace(); }

        return ret;
    }
}
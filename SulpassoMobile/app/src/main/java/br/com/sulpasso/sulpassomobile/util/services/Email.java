package br.com.sulpasso.sulpassomobile.util.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

import br.com.sulpasso.sulpassomobile.exeption.UpdateExeption;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorConexao;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorHorarios;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorUsuario;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.EMailler;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipularArquivos;

/**
 * Created by Lucas on 13/06/2018 - 15:55 as part of the project SulpassoMobile.
 */
public class Email extends Service
{
    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private int cod_vendedor = 0;
    private String nome_vendedor = "";
    private String email_from;
    private String email_pass;
    private String email_to1;
    private String email_to2;
    private String email_to3;
    private String data_arquivos = "";

    private ConfiguradorConexao conexao;
    private ConfiguradorHorarios horarios;
    private ConfiguradorUsuario vendedor;

    //Handler that receives messages from the thread
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

            /*
            email_from = email.emailFrom();
            email_pass = email.emailPass();
            email_to1 = email.email1();
            email_to2 = email.email2();
            email_to3 = email.email3();
            cod_vendedor = email.cod_vendedor();
            nome_vendedor = email.nome_vendedor();
            email_enviado = email.envio();
            email_data = email.data();
            hour_email = Integer.parseInt(email.email_hour());
            minutes_email = Integer.parseInt(email.email_minutes());
            email.close();
            */

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
                boolean email_antigo = false;
                /*
                email.open();
                email_antigo = email.enviar_email_ontem();
                email.close();
                */
                if(email_antigo)
                {
                    try
                    {
                        data_arquivos = ms.comEsquerda((day - 1 < 1? "1" : String.valueOf(day -1)), "0", 2) + "/" +
                                ms.comEsquerda(String.valueOf(month + 1), "0", 2) + "/" +
                                ms.comEsquerda(String.valueOf(year), "0", 4);

                        finish = create_email();

                        cda.alterarDataEmail(ms.dataBanco(data));
                    }
                    catch (Exception e) { /*****/ }
                }

                String dataEmail = conexao.getDataEmailEnviado();
                if (/*email_enviado == 1 && email_data.equalsIgnoreCase(data)*/
                        conexao.getDataEmailEnviado() != null && conexao.getDataEmailEnviado().equalsIgnoreCase(data))
                {
                    Toast.makeText(getApplicationContext(), "Email already sent...", Toast.LENGTH_SHORT).show();
                    finish = true;
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Email NOT sent YET...", Toast.LENGTH_SHORT).show();
                    try { cda.alterarDataEmail(ms.dataBanco(data)); }
                    catch (UpdateExeption updateExeption) { updateExeption.printStackTrace(); }

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
                                    if (finish) { cda.alterarDataEmail(ms.dataBanco(data)); }
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
                            Toast.makeText(getApplicationContext(), "Wating for the rigth hour", Toast.LENGTH_SHORT).show();
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

            //Stop the servise using the startId, so that we don't stop
            //the service in the middle of handling another job
            stopSelf(msg.arg1);
        }
    }

    @Override
    public void onCreate()
    {
        // Start up the thread runnig the service. Note that we create a
        // separete thread because the service normaly runs in the process's
        // main thread, which we don't want to block. We also make it
        // background priority so CPU-intensive worck will disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments", android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Toast.makeText(this, "Service starting", Toast.LENGTH_SHORT).show();

        // For each start request, send a message to start a job an deliver the
        // start ID so we know which request we're stopping when we finish the job
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        //Return null becouse don't acept binded events
        return null;
    }

    @Override
    public void onDestroy(){ Toast.makeText(this, "Service done", Toast.LENGTH_LONG).show(); }

    //Metodo que ira criar e enviar o email própriamente dito
    //indica para handlerMessage se deve ou não encerrar o serviço (e-mail enviado ou não)
    private boolean create_email()
    {
        //Cria os arquivos para anexar ao email
//        CriarArquivosEmail anexos = new CriarArquivosEmail(this_);
        ManipularArquivos anexos = new ManipularArquivos(getApplicationContext());
        String path = Environment.getExternalStorageDirectory() + "/MobileVenda";
        String name = "PlanoVisita.txt";
        String name1 = "ProdutoFoco.txt";
        String name2 = "ResumoDia.txt";
        String name3 = "Graficos.txt";

        try { anexos.criar_plano_visitas(name, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { /*****/ }

        try { anexos.criar_produtos_foco(name1, data_arquivos, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { /*****/ }

        try { anexos.criar_resumo_dia(name2, data_arquivos, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { /*****/ }

        try { anexos.criar_graficos(name3, data_arquivos, vendedor.getCodigo(), vendedor.getNome()); }
        catch (Exception e) { /*****/ }
        //Fim da criação dos arquivos para anexo

        //Inicia o cliente de e-mail
        EMailler m = new EMailler(conexao.getEmailSender(), conexao.getEmailPswd());

        String[] toArr = {conexao.getEmail1(), conexao.getEmail2(), conexao.getEmail3()};
        m.set_to(toArr);
        m.set_from(conexao.getEmailSender());
        m.set_subject("Email relatorios vendedor " + vendedor.getCodigo()  + " - " + vendedor.getNome());
        m.set_body("Segue anexo os arquivos de Plano de Visitas, Produto Foco, Resumo do dia do representante.");

        try
        {
            //Anexa os arquivos criados anteriormente ao email
            //O limite de espaço do provedor de email deve ser respeitado (normalmente 25m)
            try
            {
//				m.addAttachment("/sdcard/MobileVenda/PW0007.007");
//				m.addAttachment("/sdcard/MobileVenda/PW0007.008");
//				m.addAttachment("/sdcard/MobileVenda/PW0007.010");
                m.addAttachment(name, path + "/" + name);
                m.addAttachment(name1, path + "/" + name1);
                m.addAttachment(name2, path + "/" + name2);
                m.addAttachment(name3, path + "/" + name3);
            }
            catch (Exception e) { /*****/ }

            if (m.send())
            {//a chamada ao metodo que envia o e-mail. Retorna true caso tena sucesso e false caso contrario
                Toast.makeText(getBaseContext(), "Email was sent successfully.", Toast.LENGTH_LONG).show();
                return true;
            }
            else
            {
                Toast.makeText(getBaseContext(), "There was a problem sending the email.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        catch (Exception e)
        {
            Log.e("MailApp", "Could not send email", e);
            e.printStackTrace();
            return false;
        }
        finally
        {
            anexos.excluirArquivosLocal();
            try { anexos.excluir_arquivos_sd(name); }
            catch (Exception e2) { /*****/ }
            try { anexos.excluir_arquivos_sd(name1); }
            catch (Exception e2) { /*****/ }
            try { anexos.excluir_arquivos_sd(name2); }
            catch (Exception e2) { /*****/ }
            try { anexos.excluir_arquivos_sd(name3); }
            catch (Exception e2) { /*****/ }
        }
    }
}
package br.com.sulpasso.sulpassomobile.util.funcoes;

import android.net.Uri;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Lucas on 21/08/2018 - 16:57 as part of the project SulpassoMobile.
 */
public class WebMail
{
    public String postData2(int user, int emp, int type, String dados)  throws UnsupportedEncodingException
    {
        String data = URLEncoder.encode("user", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(user), "UTF-8");

        data += "&" + URLEncoder.encode("emp", "UTF-8") + "="
                + URLEncoder.encode(String.valueOf(emp), "UTF-8");

        data += "&" + URLEncoder.encode("type", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(type), "UTF-8");

        data += "&" + URLEncoder.encode("dados", "UTF-8")
                + "=" + URLEncoder.encode(dados, "UTF-8");

        BufferedReader reader=null;

        Uri.Builder params = new Uri.Builder();
        params.appendQueryParameter("user", String.valueOf(user));
        params.appendQueryParameter("emp", String.valueOf(emp));
        params.appendQueryParameter("type", String.valueOf(type));
        params.appendQueryParameter("dados", String.valueOf(dados));

        // Send data
        try
        {
            // Defined URL  where to send data
            //URL url = new URL("http://www.sulpasso.com.br/SulpassoWeb/Server/Vendas/salvarDados.php");
            URL url = new URL("http://sulpasso.com.br/SulpassoWeb/Server/Vendas/salvarDados.php");

            HttpURLConnection hCon;
            hCon = (HttpURLConnection) url.openConnection();
            hCon.setRequestMethod("POST");;
            hCon.setDoOutput(true);
            hCon.setDoInput(true);
            hCon.setChunkedStreamingMode(0);
//            hCon.connect();


            OutputStream out = new BufferedOutputStream(hCon.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    out, "UTF-8"));
            writer.write(params.build().getEncodedQuery());
            writer.flush();



            int code = hCon.getResponseCode();

            return String.valueOf(code);
  /*
            if (code !=  201) {
                throw new IOException("Invalid response from server: " + code);
            }


/*
            OutputStreamWriter wr = new OutputStreamWriter(hCon.getOutputStream());
            wr.write( params.build().getEncodedQuery() );
            wr.flush();
            wr.close();
* /

            InputStream in = hCon.getInputStream();
            InputStreamReader inr = new InputStreamReader(in);

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(hCon.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }
                    */
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return ex.getMessage();
        }
        finally
        {
            try
            {
                //reader.close();
                //return "deu tudo certo";
            }

            catch(Exception ex) {return ex.getMessage();}
        }

    }


    public String postData3(int user, int emp, int type, String dados)  throws UnsupportedEncodingException
    {
        /*
        É preciso acrecentar essa linha no manifest.xml para que seja liberado o acesso a http em ambiente de produção
        (tem uma outra forma de fazer também mas essa resolve o problema)
        android:usesCleartextTraffic="true"
        */

        String data = URLEncoder.encode("user", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(user), "UTF-8");

        data += "&" + URLEncoder.encode("emp", "UTF-8") + "="
                + URLEncoder.encode(String.valueOf(emp), "UTF-8");

        data += "&" + URLEncoder.encode("type", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(type), "UTF-8");

        data += "&" + URLEncoder.encode("dados", "UTF-8")
                + "=" + URLEncoder.encode(dados, "UTF-8");

        BufferedReader reader=null;

        StringBuilder sb = new StringBuilder();

        // Send data
        try
        {
            // Defined URL  where to send data
            //URL url = new URL("http://www.sulpasso.com.br/SulpassoWeb/Server/Vendas/salvarDados.php");
            URL url = new URL("http://sulpasso.com.br/SulpassoWeb/Server/Vendas/salvarDados.php");

            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return ex.getMessage();
        }
        finally
        {
            try
            {
                reader.close();
                return sb.toString();
            }

            catch(Exception ex) {return ex.getMessage();}
        }
    }


    public boolean postData(int user, int emp, int type, String dados)  throws UnsupportedEncodingException
    {
        String data = URLEncoder.encode("user", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(user), "UTF-8");

        data += "&" + URLEncoder.encode("emp", "UTF-8") + "="
                + URLEncoder.encode(String.valueOf(emp), "UTF-8");

        data += "&" + URLEncoder.encode("type", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(type), "UTF-8");

        data += "&" + URLEncoder.encode("dados", "UTF-8")
                + "=" + URLEncoder.encode(dados, "UTF-8");

        BufferedReader reader=null;

        // Send data
        try
        {
            // Defined URL  where to send data
            //URL url = new URL("http://www.sulpasso.com.br/SulpassoWeb/Server/Vendas/salvarDados.php");
            URL url = new URL("http://sulpasso.com.br/SulpassoWeb/Server/Vendas/salvarDados.php");

            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return false;
        }
        finally
        {
            try
            {
                reader.close();
                return true;
            }

            catch(Exception ex) {return false;}
        }
    }

    public boolean sendMail(int user, int emp)  throws UnsupportedEncodingException
    {
        String data = URLEncoder.encode("user", "UTF-8")
                + "=" + URLEncoder.encode(String.valueOf(user), "UTF-8");

        data += "&" + URLEncoder.encode("emp", "UTF-8") + "="
                + URLEncoder.encode(String.valueOf(emp), "UTF-8");

        BufferedReader reader=null;

        // Send data
        try
        {
            // Defined URL  where to send data
            URL url = new URL("http://www.sulpasso.com.br/SulpassoWeb/Server/Vendas/enviarEmail.php");

            // Send POST data request
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            // Get the server response
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            wr.write( data );
            wr.flush();

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }
        }
        catch(Exception ex) { return false; }
        finally
        {
            try
            {
                reader.close();
                return true;
            }

            catch(Exception ex) {return false;}
        }
    }

    private static String converterInputStreamToString(InputStream is)
    {
        StringBuffer buffer = new StringBuffer();
        try
        {
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while((linha = br.readLine())!=null){ buffer.append(linha); }

            br.close();
        }
        catch(IOException e){ e.printStackTrace(); }

        return buffer.toString();
    }
}
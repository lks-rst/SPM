package br.com.sulpasso.sulpassomobile.util.funcoes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Lucas on 21/08/2018 - 16:57 as part of the project SulpassoMobile.
 */
public class WebMail
{
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

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL("http://www.sulpasso.com.br/SulpassoWeb/Server/Vendas/salvarDados.php");

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


            text = sb.toString();
        }
        catch(Exception ex)
        {
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

        String text = "";
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

            text = sb.toString();
        }
        catch(Exception ex)
        {
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


    /*
    public boolean postData(int user, int emp, int type, String dados)
    {
        URL url = null;
        int codigoResposta;
        InputStream is;
        String retorno = "";

        try
        {
            url = new URL("http://www.sulpasso.com.br/SulpassoWeb/Server/Vendas/enviarEmail.php");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");

            urlConnection.addRequestProperty("user", String.valueOf(user));
            urlConnection.addRequestProperty("emp", String.valueOf(emp));
            urlConnection.addRequestProperty("type", String.valueOf(type));
            urlConnection.addRequestProperty("dados", dados);

            urlConnection.connect();

            codigoResposta = urlConnection.getResponseCode();
            if(codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST){
                is = urlConnection.getInputStream();
            }else{
                is = urlConnection.getErrorStream();
            }

            retorno = converterInputStreamToString(is);
            is.close();
            urlConnection.disconnect();


            return true;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }
    */

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

    /*
    public void postData()
    {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://www.yoursite.com/script.php");

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id", "12345"));
            nameValuePairs.add(new BasicNameValuePair("stringdata", "Hi"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }
    */
}
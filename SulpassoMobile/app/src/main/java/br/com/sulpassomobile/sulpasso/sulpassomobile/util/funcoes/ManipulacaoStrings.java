package br.com.sulpassomobile.sulpasso.sulpassomobile.util.funcoes;

/**
 * Created by Lucas on 05/01/2017 - 17:37 as part of the project SulpassoMobile.
 */
public class ManipulacaoStrings
{
    public String comDireita(String value, String caracter, int n)
    {
        String s = value.trim();
        StringBuffer resp = new StringBuffer();

        int fim = n - s.length();

        for (int x = 0; x < fim; x++)
            resp.append(caracter);

        return (s + resp).substring(0, n);
    }

    public String comEsquerda(String value, String caracter, int n)
    {
        String s = value.trim();
        StringBuffer resp = new StringBuffer();

        int fim = n - s.length();

        for (int x = 0; x < fim; x++)
            resp.append(caracter);

        return (resp + s).substring(0, n);
    }

    public String dataVisual(String data)
    {
        String nova_data = "";
        String[] datas;

        try
        {
            datas = data.split("-");
            nova_data = datas[2] + "/" + datas[1] + "/" + datas[0];
        }
        catch (Exception e) { nova_data = data; }

        return nova_data;
    }

    public String dataToConvert(String data)
    {
        String nova_data = "";
        String[] datas;

        try
        {
            datas = data.split("-");
            nova_data = datas[2] + "-" + datas[1] + "-" + datas[0];
        }
        catch (Exception e) { nova_data = data; }

        return nova_data;
    }

    public String dataBanco(String data)
    {
        String nova_data = "";
        String[] datas;

        try
        {
            datas = data.split("/");
            nova_data = datas[2] + "-" + datas[1] + "-" + datas[0];
        }
        catch (Exception e) { nova_data = data; }
        return nova_data;
    }
}
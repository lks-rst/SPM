package br.com.sulpasso.sulpassomobile.util.funcoes;

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

    public String removeCaracteresEspeciais(String origem)
    {
        String retorno = "";

//        retorno =  Normalizer.normalize(origem, Normalizer.Form.NFD).replaceAll("[^a-zA-Z]", "");

        retorno =  this.removeSpecialCharacters(origem);

        return retorno;
    }

    private boolean isSpecialCharacter(int b)
    {
        if(b == 32 )
        {
                return false;
        }
        else
        {
            if((b > 31 && b <= 47 ) || (b >= 58 && b <= 64) || (b >= 91 && b <= 96) || (b >= 123 && b <= 126) || b > 126)
                return true;
            else
                return false;
        }
    }

    public String removeSpecialCharacters(String a)
    {
        StringBuffer s=new StringBuffer(a);

        int lenvar=s.length();
        String myString="";

        for(int i=0; i < lenvar; i++)
        {
            if(!isSpecialCharacter(s.charAt(i))) { myString+=s.charAt(i); }
        }

        return myString;
    }

    public static String trata (String passa)
    {
        passa = passa.replaceAll("[ÂÀÁÄÃ]","A");
        passa = passa.replaceAll("[âãàáä]","a");
        passa = passa.replaceAll("[ÊÈÉË]","E");
        passa = passa.replaceAll("[êèéë]","e");
        passa = passa.replaceAll("ÎÍÌÏ","I");
        passa = passa.replaceAll("îíìï","i");
        passa = passa.replaceAll("[ÔÕÒÓÖ]","O");
        passa = passa.replaceAll("[ôõòóö]","o");
        passa = passa.replaceAll("[ÛÙÚÜ]","U");
        passa = passa.replaceAll("[ûúùü]","u");
        passa = passa.replaceAll("Ç","C");
        passa = passa.replaceAll("ç","c");
        passa = passa.replaceAll("[ýÿ]","y");
        passa = passa.replaceAll("Ý","Y");
        passa = passa.replaceAll("ñ","n");
        passa = passa.replaceAll("Ñ","N");
        passa = passa.replaceAll("['<>\\|/]","");

        return passa;
    }
}
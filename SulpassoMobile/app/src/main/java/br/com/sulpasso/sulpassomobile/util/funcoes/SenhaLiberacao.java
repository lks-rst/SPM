package br.com.sulpasso.sulpassomobile.util.funcoes;

import java.util.Calendar;

/**
 * Created by Lucas on 16/01/2018 - 17:04 as part of the project SulpassoMobile.
 */
public class SenhaLiberacao
{
    private String chave;
    private String str_chave_valor;
    private String str_chave_valorNew;
    //private final int NUMERO = 91026;
    private final int NUMERO = 17842;
    private int tipo;

    private ManipulacaoStrings ms;

    Calendar cal = Calendar.getInstance();
    int day;
    int month;
    int sun;
    String pswd;


    String strQtd;
    String strVal;
    String strMsQV;
    String strMsch;



    /**
     * Calcula randomicamente a chave de acesso para a senha quando não é utilizado valor
     */
    public SenhaLiberacao()
    {
        this.chave = "" + (10 + (int) (Math.random()*1000));
        this.tipo = 0;
        this.ms = new ManipulacaoStrings();
    }

    /**
     * Calcula randomicamente a chave de acesso para a senha quando a liberação de valor especial
     */
    public SenhaLiberacao(float valor)
    {
        boolean inicio_valor = false;

        this.str_chave_valor = "";

        String str_valor;
        this.chave = "" + (1 + (int) (Math.random()*10000));
        String str_chave = "" + chave;

        this.ms = new ManipulacaoStrings();

        str_valor = Formatacao.format3(valor, 2).replace(".", "").replace(",", "");

        if (str_valor.length() > str_chave.length())
        {
            str_chave = this.ms.comEsquerda(str_chave, "0", str_valor.length());
            inicio_valor = false;
        }
        else
        {
            str_valor = this.ms.comEsquerda(str_valor, "0", str_chave.length());
            inicio_valor = true;
        }

        for (int i = 0; i < str_valor.length(); i++)
        {
            this.str_chave_valor += str_valor.substring(i, (i + 1)) + str_chave.substring(i, (i + 1));
        }

        this.tipo = 1;
    }

    /**
     * Calcula randomicamente a chave de acesso para a senha quando a liberação de valor especial
     * considerando o valor e a quantidade informadas
     */
    public SenhaLiberacao(float valor, float quantidade)
    {
        boolean inicio_valor = false;

        this.str_chave_valor = "";

        this.ms = new ManipulacaoStrings();

        String str_valor;
        String str_qtd;
        String str_qtdV;
        this.chave = "" + (101 + (int) (Math.random() * 10000));
        //String str_chave = "" + mascaraChave(Integer.parseInt(this.chave));
        String str_chave = "" + calculaChave(Integer.parseInt(this.chave));

        str_valor = Formatacao.format3(valor, 2).replace(".", "").replace(",", "");
        str_qtd = Formatacao.format3(quantidade, 2).replace(".", "").replace(",", "");

        this.strQtd = str_qtd;
        this.strVal = str_valor;

        str_qtdV = "" + mascaraValores(str_valor, str_qtd);

        strMsQV = str_qtd;

        this.str_chave_valor = mascaraValores(str_qtdV, str_chave);

        strMsch = str_chave;

        this.tipo = 2;
    }

    /**
     * @return uma String contendo a chave de acesso
     */
    public String getChave()
    {
        if (tipo == 1 || tipo == 2) return str_chave_valor; /*+
                "\nQ" + this.strQtd+ " - V " +
        this.strVal+ " - QV " +
        this.strMsQV+ " - " +
        this.strMsch + " - MCH" +
               "\n" +  chave;*/
        else return chave;
    }

    public void setChave(int chave) { this.chave = "" + chave; }

    /**
     * Faz a verificação da chave de acesso contra a senha digitada para liberação de configuração
     *
     * @param senha
     * @return true para senha correta
     */
    public Boolean verificaChave(String senha)
    {
        int arroba = senha.indexOf("@");

        if(arroba != -1) { return true; }
        else
        {
            String subsenha = senha.substring(0/*, (senha.length() - 4)*/);

            day = cal.get(Calendar.DAY_OF_MONTH);
            month = cal.get(Calendar.MONTH) + 1;
            sun = (Integer.parseInt(chave) + day + NUMERO) * month;

            pswd = "" + sun;

            if (pswd.equals(subsenha))
                return true;

            return false;
        }
    }

    /**
     * Faz a verificação da chave de acesso contra a senha digitada para liberação de valor
     *
     * @param senha
     * @return true para senha correta
     */
    public Boolean verificaChavePedido(String senha)
    {
        /*
        int constMult = 9;
        int sun = 0;
        int sti = 0;
        for(int i = 0; i < this.str_chave_valor.length(); i++)
        {
            if (constMult < 1)
                constMult = 9;

            sti = Integer.parseInt(this.str_chave_valor.substring(i, i + 1));
            sun += sti * constMult--;
        }

        int rest = sun % 11;
        rest = rest * 11;
        sun = sun - rest;


        day = cal.get(Calendar.DAY_OF_MONTH);
        sun = (Integer.parseInt(chave) * day);

        pswd = "" + sun;

        if (pswd.equals(senha))
            return true;

        return false;
        */

        return this.chave.equals(senha);
    }

    /**
     * Calculo para mascarar a quantidade
     *
     * @param quantidade
     * @return
     */
    private int mascaraQuantidade(int quantidade)
    {
        day = cal.get(Calendar.DAY_OF_MONTH);

        sun = ((quantidade * day) + NUMERO);

        return sun;
    }

    /**
     * Calculo para mascarar a quantidade
     *
     * @param quantidade
     * @return
     */
    private String mascaraQuantidade(String quantidade, String valor)
    {
        StringBuilder nq = new StringBuilder();

        if(valor.length() > quantidade.length())
        {
            ms.comEsquerda(quantidade, "0", valor.length());
            nq.append(misturar(valor, quantidade, false));
            nq.append("1");
        }
        else
        {
            ms.comEsquerda(valor, "0", quantidade.length());
            nq.append(misturar(quantidade, valor, false));
            nq.append("2");
        }

        return nq.toString();
    }

    /**
     * Calculo para mascarar a quantidade
     *
     * @return
     */
    private int mascaraChave(int chave)
    {
        day = cal.get(Calendar.DAY_OF_MONTH);

        sun = (chave * day) + NUMERO;

        return sun;
    }

    /**
     * Calculo para mascarar a quantidade
     *
     * @return
     */
    private String mascaraChave(String chave, String valor)
    {
        StringBuilder nq = new StringBuilder();

        if(valor.length() > chave.length())
        {
            ms.comEsquerda(chave, "0", valor.length());
            nq.append(misturar(valor, chave, false));
            nq.append("1");
        }
        else
        {
            ms.comEsquerda(valor, "0", chave.length());
            nq.append(misturar(chave, valor, false));
            nq.append("2");
        }

        return nq.toString();
    }

    /**
     * Calculo para mascarar a chave
     *
     * @return
     */
    private int calculaChave(int chave)
    {
        day = cal.get(Calendar.DAY_OF_MONTH);

        sun = (chave * day) + NUMERO;

        return sun;
    }

    /**
     * Calculo para misturar duas strings via de regra, o primeiro item é sempre valor
     *
     * @param str1
     * @param str2
     * @return
     */
    private String mascaraValores(String str1, String str2)
    {
        StringBuilder nq = new StringBuilder();

        if(str1.length() > str2.length())
        {
            str2 = ms.comEsquerda(str2, "0", str1.length());
            nq.append(misturar(str1, str2, false));
            nq.append("1");
        }
        else
        {
            str1 = ms.comEsquerda(str1, "0", str2.length());
            nq.append(misturar(str2, str1, false));
            nq.append("2");
        }

        return nq.toString();
    }

    /**
     *
     * @param str1
     * @param str2
     * @return
     */
    private String misturar(String str1, String str2, boolean reverse)
    {
        StringBuilder nq = new StringBuilder();
        int i = 0;

        for (i = 0; i < str1.length(); i++)
        {
            nq.append(str1.substring(i, i+1));

            if(!reverse)
                nq.append(str2.substring(i, i+1));
            else
                nq.append(str2.substring(str2.length() - i + 1, str2.length() - i));
        }

        return nq.toString();
    }

}
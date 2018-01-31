package br.com.sulpasso.sulpassomobile.util.funcoes;

import java.util.Calendar;

/**
 * Created by Lucas on 16/01/2018 - 17:04 as part of the project SulpassoMobile.
 */
public class SenhaLiberacao
{
    private String chave;
    private String str_chave_valor;
    private final int NUMERO = 91026;
    private int tipo;

    private ManipulacaoStrings ms;

    Calendar cal = Calendar.getInstance();
    int day;
    int month;
    int sun;
    String pswd;

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
        this.chave = "" + (1 + (int) (Math.random()*1000));
        String str_chave = "" + mascaraChave(Integer.parseInt(this.chave));

        str_valor = Formatacao.format3(valor, 2).replace(".", "").replace(",", "");
        str_qtd = Formatacao.format3(quantidade, 2).replace(".", "").replace(",", "");

        str_qtd = "" + mascaraQuantidade(str_qtd, str_valor);

        this.str_chave_valor = mascaraChave(str_chave, str_qtd);

        this.tipo = 2;
    }

    /**
     * @return uma String contendo a chave de acesso
     */
    public String getChave()
    {
        if (tipo == 1 || tipo == 2) return str_chave_valor;
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
        day = cal.get(Calendar.DAY_OF_MONTH);
        sun = (Integer.parseInt(chave) * day);

        pswd = "" + sun;

        if (pswd.equals(senha))
            return true;

        return false;
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
        int dif = valor.length() > quantidade.length() ? valor.length() - quantidade.length() :
                quantidade.length() - valor.length();

        int i = 0;

        for (i = 0; i < valor.length(); i++)
        {
            nq.append(valor.substring(i, i+1));

            if (i < quantidade.length()){ nq.append(quantidade.substring(i, i+1)); }
        }

        if (i < quantidade.length())
        {
            nq.append(quantidade.substring(i));
            nq.append("1");
        }
        else { nq.append("0"); }

        nq.append("" + dif);

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

        sun = (chave * day);

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
        int dif = valor.length() > chave.length() ? valor.length() - chave.length() : chave.length() - valor.length();
        int i = 0;

        for (i = 0; i < valor.length(); i++)
        {
            nq.append(valor.substring(i, i+1));

            if (i < chave.length()){ nq.append(chave.substring(i, i+1)); }
        }

        if (i < chave.length())
        {
            nq.append(chave.substring(i));
            nq.append("1");
        }
        else { nq.append("0"); }

        nq.append("" + dif);

        return nq.toString();
    }
}
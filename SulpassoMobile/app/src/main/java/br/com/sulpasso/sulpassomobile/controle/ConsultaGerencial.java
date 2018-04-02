package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Mensagem;
import br.com.sulpasso.sulpassomobile.modelo.Meta;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.MensagemDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.MetaDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;

/**
 * Created by Lucas on 20/03/2017 - 10:10 as part of the project SulpassoMobile.
 */
public class ConsultaGerencial
{
    private Context ctx;
    private Graficos graficos;
    private PlanoVisitas planoVisitas;

    ArrayList<Meta> metas = new ArrayList<>();

    public static final int META = 0;
    public static final int REALIZADO = 1;
    public static final int PERCENTUAL = 2;

    public static final int PESO = 0;
    public static final int CLIENTE = 1;
    public static final int FATURAMENTO = 2;
    public static final int CONTRIBUICAO = 3;

    public ConsultaGerencial(Context ctx)
    {
        this.ctx = ctx;
    }

    public void criarGraficos()
    {
        this.graficos = new Graficos(this.ctx);
        this.graficos.gerarGraficos();
    }

    public ArrayList<String> criarPlano(String data)
    {
        this.planoVisitas = new PlanoVisitas(this.ctx);
        return this.planoVisitas.gerarPlano(data);
    }

    public ArrayList<Mensagem> buscarMensagens()
    {
        ArrayList<Mensagem> mensagens = new ArrayList<>();

        MensagemDataAccess mda = new MensagemDataAccess(this.ctx);
        try { mensagens = (ArrayList<Mensagem>) mda.getAll(); }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> retorno = new ArrayList<>();

        return mensagens;
    }

    public int percentualGrafico(int campo, int mes, int max)
    {
        return this.graficos.percentualGrafico(campo, mes, max);
    }

    public ArrayList<Meta> buscarListaMetas()
    {

        MetaDataAccess mda = new MetaDataAccess(this.ctx);
        try { this.metas = (ArrayList<Meta>) mda.getAll(); }
        catch (GenercicException e) { e.printStackTrace(); }

        return this.metas;
    }

    public float buscarMetaIdeal()
    {
        int weekends = 0;
        float days = 0;
        float uteis = 0;

        Date today = new Date();
        Date until_today = new Date();

        for (int i = 1; i <= today.getDate(); i++)
        {
            until_today.setDate(i);
            days = i;

            if(until_today.getDay() == 0 || until_today.getDay() == 6){ weekends++; }
        }

        days -= weekends;

        MetaDataAccess mda = new MetaDataAccess(this.ctx);

        uteis = mda.getDiasUteis();

        float percentual = days/uteis;
        float percentual_ = percentual * 100;

        return percentual_;
    }

    public float buscarMetaTotal(int tipo)
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.ctx);

        float meta = 0;

        try { meta = cda.getMeta(tipo); }
        catch (Exception ex) { meta = 0; }

        return meta;
    }

    public String buscarMeta(int posicao, int campo, int tipo)
    {
        Meta m = this.metas.get(posicao);
        float ret = 0;

        if(tipo == META)
        {
            switch (campo)
            {
                case PESO :
                    ret = m.getMeta_v();
                    break;
                case CLIENTE :
                    ret = m.getMeta_c();
                    break;
                case FATURAMENTO :
                    ret = m.getMeta_f();
                    break;
                case CONTRIBUICAO :
                    ret = m.getMeta_co();
                    break;
            }
        }
        else if(tipo == REALIZADO)
        {
            switch (campo)
            {
                case PESO :
                    ret = m.getRealizado_v();
                    break;
                case CLIENTE :
                    ret = m.getRealizado_c();
                    break;
                case FATURAMENTO :
                    ret = m.getRealizado_f();
                    break;
                case CONTRIBUICAO :
                    ret = m.getRealizado_co();
                    break;
            }
        } else
        {
            switch (campo)
            {
                case PESO :
                    try { ret = ((m.getRealizado_v() * 100) / m.getMeta_v()); }
                    catch (Exception e) { ret = -1; }
                    break;

                case CLIENTE :
                    try { ret = ((m.getRealizado_c() * 100) / m.getMeta_c()); }
                    catch (Exception e) { ret = -1; }
                    break;

                case FATURAMENTO :
                    try { ret = ((m.getRealizado_f() * 100) / m.getMeta_f()); }
                    catch (Exception e) { ret = -1; }
                    break;

                case CONTRIBUICAO :
                    try { ret = ((m.getRealizado_co() * 100) / m.getMeta_co()); }
                    catch (Exception e) { ret = -1; }
                    break;
            }
        }

        return Formatacao.format2d(ret);
    }


    public ArrayList<String> buscarMensagensStr()
    {
        ArrayList<String> retorno = new ArrayList<>();
        ArrayList<Mensagem> mensagens = new ArrayList<>();
        mensagens = this.buscarMensagens();

        for(Mensagem msg : mensagens)
        {
            retorno.add(msg.toDisplay());
            Toast.makeText(this.ctx, msg.toString(), Toast.LENGTH_LONG).show();
        }


        return retorno;
    }
}

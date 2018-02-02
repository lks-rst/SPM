package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Mensagem;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.MensagemDataAccess;

/**
 * Created by Lucas on 20/03/2017 - 10:10 as part of the project SulpassoMobile.
 */
public class ConsultaGerencial
{
    private Context ctx;

    public ConsultaGerencial(Context ctx)
    {
        this.ctx = ctx;
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

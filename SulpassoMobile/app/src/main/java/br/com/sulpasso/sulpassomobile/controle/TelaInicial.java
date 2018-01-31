package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Mensagem;
import br.com.sulpasso.sulpassomobile.modelo.TiposVenda;
import br.com.sulpasso.sulpassomobile.persistencia.queries.MensagemDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.TipoVendaDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.ManipulacaoStrings;

/**
 * Created by Lucas on 13/12/2016 - 08:29 as part of the project SulpassoMobile.
 */
public class TelaInicial
{
    private br.com.sulpasso.sulpassomobile.controle.ConfigurarSistema configurador;
    private Context ctx;

    public TelaInicial(Context ctx)
    {
        this.ctx = ctx;
        this.configurador = new br.com.sulpasso.sulpassomobile.controle.ConfigurarSistema(ctx);
        try { this.configurador.carregarConfiguracoesInicial(); }
        catch (GenercicException e) { this.configurador = null; }
    }

    public int fragmentoCentral()
    {
        return this.configurador.getConfigTel().getTelaInicial();
    }

    public String validade()
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();
        return ms.dataVisual(this.configurador.getConfigVda().getValidade());
    }

    public String versao()
    {
        String versionName = "";
        PackageInfo packageInfo;
        String validade = "";
        ManipulacaoStrings ms = new ManipulacaoStrings();
        try
        {
            packageInfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 0);//Isso pega a versão do aplicativo direto do manifesto
            versionName = "Versão: " + packageInfo.versionName /*+ this.configurador.getConfigHor().getAtualizacao()*/;

            validade = this.configurador.buscarSequencias();
            validade = ms.comEsquerda(validade, "0", 4);
        }
        catch (PackageManager.NameNotFoundException e) { e.printStackTrace(); }

        return (versionName + validade);
    }

    public boolean controleAcesso() { return !(configurador.getConfigEmp().getLogin() == 0); }

    public String nomeEmpresa() { return this.configurador.getConfigEmp().getNome(); }

    public String enderecoEmpresa() { return this.configurador.getConfigEmp().getEndereco(); }

    public String foneEmpresa() { return this.configurador.getConfigEmp().getFone(); }

    public String emailEmpresa() { return this.configurador.getConfigEmp().getEmail(); }

    public String siteEmpresa() { return this.configurador.getConfigEmp().getSite(); }

    public String dataHora(int tipo)
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();
        switch (tipo)
        {
            case 0 :
                return this.configurador.getConfigHor().getInicioManha();
            case 1 :
                return this.configurador.getConfigHor().getInicioTarde();
            case 2 :
                return this.configurador.getConfigHor().getFinalManha();
            case 3 :
                return this.configurador.getConfigHor().getFinalTarde();
            case 4 :
                return this.configurador.getConfigHor().getDataAtualizacao();
            default :
                return ms.dataToConvert(this.configurador.getConfigVda().getValidade());
        }
    }

    public boolean novoAcesso(String time)
    {
        return this.configurador.updateAcesso(time);
    }

    public String vendedor()
    {
        return this.configurador.getConfigUsr().getCodigo() + " - "
            + this.configurador.getConfigUsr().getNome();
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

    public int tipoVenda()
    {
        int tipo_dialogo = 0;

        if (this.configurador.getConfigTel().getEfetuaTroca()){ tipo_dialogo = 13; }
        else{ tipo_dialogo = 1; }

        if (this.configurador.getConfigTel().getVendasDireta())
        {
            if (tipo_dialogo == 13){ tipo_dialogo = 123; }
            else{ tipo_dialogo = 12; }
        }

        return tipo_dialogo;
    }

    public ArrayList<TiposVenda> buscarDiretas()
    {
        ArrayList<TiposVenda> diretas = new ArrayList<>();
        TipoVendaDataAccess tvda = new TipoVendaDataAccess(this.ctx);

        try { diretas = (ArrayList<TiposVenda>) tvda.getAll(); }
        catch (GenercicException e) { e.printStackTrace(); }

        return diretas;
    }

    public ArrayList<String> buscarDiretasStr()
    {
        ArrayList<TiposVenda> diretas = new ArrayList<>();
        ArrayList<String> lista = new ArrayList<>();
        diretas = this.buscarDiretas();

        for(int i = 0; i < diretas.size(); i++)
        {
            lista.add(diretas.get(i).getReferencia());
        }

        return lista;
    }
}
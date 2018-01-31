package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;

/**
 * Created by Lucas on 13/12/2016 - 08:29 as part of the project SulpassoMobile.
 */
public class TelaInicial
{
    private ConfigurarSistema configurador;
    private Context ctx;

    public TelaInicial(Context ctx)
    {
        this.ctx = ctx;
        this.configurador = new ConfigurarSistema(ctx);
        try { this.configurador.carregarConfiguracoesInicial(); }
        catch (GenercicException e) { e.printStackTrace(); }
    }

    public int fragmentoCentral()
    {
        return this.configurador.getConfigTel().getTelaInicial();
    }

    public String validade()
    {
        return this.configurador.getConfigVda().getValidade();
    }

    public boolean controleAcesso()
    {
        return true;
    }

    public String nomeEmpresa() { return this.configurador.getConfigEmp().getNome(); }

    public String enderecoEmpresa() { return this.configurador.getConfigEmp().getEndereco(); }

    public String foneEmpresa() { return this.configurador.getConfigEmp().getFone(); }

    public String emailEmpresa() { return this.configurador.getConfigEmp().getEmail(); }

    public String siteEmpresa() { return this.configurador.getConfigEmp().getSite(); }
}
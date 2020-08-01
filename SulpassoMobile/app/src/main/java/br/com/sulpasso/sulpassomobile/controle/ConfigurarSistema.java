package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.widget.Toast;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.UpdateExeption;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorConexao;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorEmpresa;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorHorarios;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorTelas;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorUsuario;
import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorVendas;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;

/**
 * Created by Lucas on 02/08/2016.
 */
public class ConfigurarSistema
{
    private Context context;
    private float saldoAtual;

    private ConfiguradorEmpresa configEmp;
    private ConfiguradorUsuario configUsr;
    private ConfiguradorVendas configVda;
    private ConfiguradorConexao configCon;
    private ConfiguradorHorarios configHor;
    private ConfiguradorTelas configTel;

    public ConfigurarSistema(Context context)
    {
        this.context = context;
    }

    public ConfiguradorEmpresa getConfigEmp() { return configEmp; }

    public void setConfigEmp(ConfiguradorEmpresa configEmp) { this.configEmp = configEmp; }

    public ConfiguradorUsuario getConfigUsr() { return configUsr; }

    public void setConfigUsr(ConfiguradorUsuario configUsr) { this.configUsr = configUsr; }

    public ConfiguradorVendas getConfigVda() { return configVda; }

    public void setConfigVda(ConfiguradorVendas configVda) { this.configVda = configVda; }

    public ConfiguradorConexao getConfigCon() { return configCon; }

    public void setConfigCon(ConfiguradorConexao configCon) { this.configCon = configCon; }

    public ConfiguradorHorarios getConfigHor() { return configHor; }

    public void setConfigHor(ConfiguradorHorarios configHor) { this.configHor = configHor; }

    public ConfiguradorTelas getConfigTel() { return configTel; }

    public void setConfigTel(ConfiguradorTelas configTel) { this.configTel = configTel; }

    public void carregarConfiguracoesVenda() throws GenercicException
    {
        this.configUsr = new ConfiguradorUsuario();
        this.configVda = new ConfiguradorVendas();
        this.configHor = new ConfiguradorHorarios();
        this.configTel = new ConfiguradorTelas();
        this.configEmp = new ConfiguradorEmpresa();

        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);

        this.configUsr = cda.getUsuario();
        this.configVda = cda.getVenda();
        this.configHor = cda.getHorario();
        this.configTel = cda.getTelas();
        this.configEmp = cda.getEmpresa();

        this.saldoAtual = Float.parseFloat(this.getConfiguracao(1));
    }

    public void carregarConfiguracoesGeral()
    {
        this.configEmp = new ConfiguradorEmpresa();
        this.configUsr = new ConfiguradorUsuario();
        this.configVda = new ConfiguradorVendas();
        this.configCon = new ConfiguradorConexao();
        this.configHor = new ConfiguradorHorarios();
        this.configTel = new ConfiguradorTelas();
    }

    public void carregarConfiguracoesInicial() throws GenercicException
    {
        this.configEmp = new ConfiguradorEmpresa();
        this.configVda = new ConfiguradorVendas();
        this.configHor = new ConfiguradorHorarios();
        this.configTel = new ConfiguradorTelas();
        this.configUsr = new ConfiguradorUsuario();

        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);

        this.configEmp = cda.getEmpresa();
        this.configVda = cda.getVenda();
        this.configHor = cda.getHorario();
        this.configTel = cda.getTelas();
        this.configUsr = cda.getUsuario();
    }

    public float getSaldoAtual() { return saldoAtual; }

    public void setSaldoAtual(float saldoAtual)
    {
        this.saldoAtual = saldoAtual;
    }

    public float pedidoMinimo()
    {
        return this.getValorMinimo();
    }

    /*
    public int tipoDesconto() { return this.getTipoDesconto(); }
    */

    public int formaDesconto() { return this.getFormaDesconto(); }

    public Boolean alteraValor(String campo)
    {
        if(this.podeModificarPreco())
        {
            if(campo.equals("v"))
                if(this.getTipoDesconto() == 1)
                    return true;
                else
                    return false;
            else
                if(this.getTipoDesconto() == 1)
                    return false;
                else
                    return true;
        }
        else
        {
            return false;
        }
    }

    public Boolean alteraValorFim(int campo)
    {
        if(campo == R.id.ffpEdtFrete)
            return this.configVda.getFrete();
        else
        if(campo == R.id.ffpEdtDesconto)
            return this.configUsr.getDescontoPedido() > 0;
        else
            return false;
    }

    public float descontoMaximo() { return this.getDesconto(); }

    public boolean contribuicaoIdeal()
    {
        return this.verifyContribuicao();
    }

    public String buscarFlex()
    {
        return this.getConfiguracao(1);
    }

    public String buscarSequencias()
    {
        return this.getConfiguracao(4);
    }

    public String getMarkup()
    {
        return this.getConfiguracao(2);
    }

    public boolean updateAcesso(String time)
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);

        try
        {
            boolean ret = cda.updateAcesso(time);
            return ret;
        }
        catch (GenercicException g)
        {
            return false;
        }
    }

    public boolean updateVersao(String versao)
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);

        try
        {
            boolean ret = cda.updateVersao(versao);
            return ret;
        }
        catch (GenercicException g)
        {
            return false;
        }
    }

    /**
     * Retorna o tipo de modificação de preço possivel para o vendedor, se por alteração do preço
     * real do item ou por desconto percentual aplicado ao item.
     *
     * @return 1 para alteração de valor 0 para desconto percentual
     */
    private int getTipoDesconto() { return (this.configVda.getDescontoPecentual() ? 0 : 1); }

    /**
     * Retorna a forma de desconto a ser utilizada, com base no saldo flex ou com base na contribuição
     *
     * @return 1 para saldo flex 0 para contribuição
     */
    private int getFormaDesconto() { return this.configUsr.getTipoFlex() ? 0 : 1; }

    /**
     * Verifica se está configurada a alteração de preços para o sistema, não considera as
     * configurações do cliente ou do item que esta sendo vendido
     *
     * @return true - false
     */
    private Boolean podeModificarPreco() { return this.configVda.getAlteraPrecos(); }

    private float getValorMinimo() { return this.configUsr.getPedidoMinimo(); }

    private float getDesconto() { return this.configUsr.getDescontoItem(); }

    private Boolean verifyContribuicao()
    {
        Toast.makeText(context, "Contribuição atual = " + this.configUsr.getContribuicao() +
                "\nContribuiçao ideal = " + this.configUsr.getContribuicaoIdeal(), Toast.LENGTH_LONG).show();

        if(this.configUsr.getContribuicao() >= this.configUsr.getContribuicaoIdeal())
            return true;

        return false;
    }

    private String getConfiguracao(int data)
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);
        switch (data)
        {
            case 1:
                return String.valueOf(this.getConfigUsr().getSaldo());
            case 2:
                return "40";
            case 3:
                return cda.buscarValidade();
            case 4:
                return String.valueOf(cda.buscarSequencias(0));
            default :
                return "--";
        }
    }

    public int consultaClientesAbertura()
    {
        return this.configVda.getGerenciarVisitas() ? 5 : 0;
    }

    public void atualizarSaldoNegativo()
    {
        this.configUsr.setSaldo(0);
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);

        try
        {
            cda.updateSaldo("0");
        } catch (UpdateExeption updateExeption) {
            updateExeption.printStackTrace();
        }
    }
}

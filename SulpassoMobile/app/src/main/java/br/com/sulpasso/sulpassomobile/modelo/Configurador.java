package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 05/12/2016 - 09:11 as part of the project SulpassoMobile.
 */
public class Configurador
{
    private ConfiguradorEmpresa empresa;
    private ConfiguradorUsuario usuario;
    private ConfiguradorVendas vendas;
    private ConfiguradorHorarios horarios;
    private ConfiguradorConexao conexao;
    private ConfiguradorTelas telas;
    private ConfiguradorResumo resumo;

    public ConfiguradorConexao getConexao() { return conexao; }

    public void setConexao(ConfiguradorConexao conexao) { this.conexao = conexao; }

    public ConfiguradorEmpresa getEmpresa() { return empresa; }

    public void setEmpresa(ConfiguradorEmpresa empresa) { this.empresa = empresa; }

    public ConfiguradorVendas getVendas() { return vendas; }

    public void setVendas(ConfiguradorVendas vendas) { this.vendas = vendas; }

    public ConfiguradorUsuario getUsuario() { return usuario; }

    public void setUsuario(ConfiguradorUsuario usuario) { this.usuario = usuario; }

    public ConfiguradorHorarios getHorarios() { return horarios; }

    public void setHorarios(ConfiguradorHorarios horarios) { this.horarios = horarios; }

    public ConfiguradorTelas getTelas() { return telas; }

    public void setTelas(ConfiguradorTelas telas) { this.telas = telas; }

    public ConfiguradorResumo getResumo() { return resumo; }

    public void setResumo(ConfiguradorResumo resumo) { this.resumo = resumo; }

    @Override
    public String toString() {
        return "{\"Configurador\" : {" +
                "\"conexao\" : " + conexao +
                ", \"empresa\" : " + empresa +
                ", \"vendas\" : " + vendas +
                ", \"usuario\" : " + usuario +
                ", \"horarios\" : " + horarios +
                ", \"telas\" : " + telas +
                "}}";
    }
}
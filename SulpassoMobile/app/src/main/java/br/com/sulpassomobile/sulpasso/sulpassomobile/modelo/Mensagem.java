package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 08/11/2016 - 13:29 as part of the project SulpassoMobile.
 */
public class Mensagem
{
    private int codigo;
    private String mensagem;
    private String assunto;
    private String validade;
    private String usuario;
    private String envio;

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getMensagem() { return mensagem; }

    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public String getAssunto() { return assunto; }

    public void setAssunto(String assunto) { this.assunto = assunto; }

    public String getValidade() { return validade; }

    public void setValidade(String validade) { this.validade = validade; }

    public String getUsuario() { return usuario; }

    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getEnvio() { return envio; }

    public void setEnvio(String envio) { this.envio = envio; }
}
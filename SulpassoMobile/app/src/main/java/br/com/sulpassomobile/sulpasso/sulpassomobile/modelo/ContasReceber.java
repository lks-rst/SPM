package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 08/11/2016 - 11:30 as part of the project SulpassoMobile.
 */
public class ContasReceber
{
    private int cliente;
    private String documento;
    private String emissao;
    private String vencimento;
    private String tipo;
    private float valor;

    public int getCliente() { return cliente; }

    public void setCliente(int cliente) { this.cliente = cliente; }

    public String getDocumento() { return documento; }

    public void setDocumento(String documento) { this.documento = documento; }

    public String getEmissao() { return emissao; }

    public void setEmissao(String emissao) { this.emissao = emissao; }

    public String getVencimento() { return vencimento; }

    public void setVencimento(String vencimento) { this.vencimento = vencimento; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public float getValor() { return valor; }

    public void setValor(float valor) { this.valor = valor; }
}
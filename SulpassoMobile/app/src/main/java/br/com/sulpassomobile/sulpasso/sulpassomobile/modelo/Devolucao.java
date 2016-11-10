package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

import java.util.ArrayList;

/**
 * Created by Lucas on 08/11/2016 - 09:34 as part of the project SulpassoMobile.
 */
public class Devolucao
{
    private Cliente cliente;
    private String documento;
    private ArrayList<ItensVendidos> itensDevolvidos;
    private String dataDevolucao;
    private String motivo;

    public Cliente getCliente() { return cliente; }

    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public String getDocumento() { return documento; }

    public void setDocumento(String documento) { this.documento = documento; }

    public ArrayList<ItensVendidos> getItensDevolvidos() { return itensDevolvidos; }

    public void setItensDevolvidos(ArrayList<ItensVendidos> itensDevolvidos)
    {
        this.itensDevolvidos = itensDevolvidos;
    }

    public String getDataDevolucao() { return dataDevolucao; }

    public void setDataDevolucao(String dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public String getMotivo() { return motivo; }

    public void setMotivo(String motivo) { this.motivo = motivo; }
}

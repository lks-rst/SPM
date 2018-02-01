package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 16/11/2017 - 10:36 as part of the project SulpassoMobile.
 */
public class Visita
{
    private int ped;
    private int cli;
    private int motivo;
    private String data;
    private String hora;
    private int dia;
    private String venda;


    public int getPed() { return ped; }

    public void setPed(int ped) { this.ped = ped; }

    public int getCli() { return cli; }

    public void setCli(int cli) { this.cli = cli; }

    public int getMotivo() { return motivo; }

    public void setMotivo(int motivo) { this.motivo = motivo; }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }

    public String getHora() { return hora; }

    public void setHora(String hora) { this.hora = hora; }

    public int getDia() { return dia; }

    public void setDia(int dia) { this.dia = dia; }

    public String getVenda() { return venda; }

    public void setVenda(String venda) { this.venda = venda; }
}

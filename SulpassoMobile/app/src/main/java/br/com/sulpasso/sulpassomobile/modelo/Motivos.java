package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 08/11/2016 - 09:55 as part of the project SulpassoMobile.
 */
public class Motivos
{
    private int codigo;
    private String motivo;

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getMotivo() { return motivo; }

    public void setMotivo(String motivo) { this.motivo = motivo; }

    @Override
    public String toString() {
        return "Motivos{" +
                "codigo=" + codigo +
                ", motivo='" + motivo + '\'' +
                '}';
    }

    public String toDisplay() { return motivo + " - " + codigo; }
}
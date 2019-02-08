package br.com.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 10/10/2016.
 *
 * Tabloides
 */
public class CampanhaGrupo
{
    private Grupo grupo;
    private int quantidade;
    private float desconto;
    private float descontoAplicado;
    private float valorFixo;
    private int quantidadeVendida;

    public CampanhaGrupo()
    {
        this.descontoAplicado = 0;
        this.quantidadeVendida = 0;
    }

    public Grupo getGrupo() { return grupo; }

    public void setGrupo(Grupo grupo) { this.grupo = grupo; }

    public int getQuantidade() { return quantidade; }

    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public float getDesconto() { return desconto; }

    public void setDesconto(float desconto) { this.desconto = desconto; }

    public float getValorFixo() { return valorFixo; }

    public void setValorFixo(float valorFixo) { this.valorFixo = valorFixo; }

    public float getDescontoAplicado() { return descontoAplicado; }

    public void setDescontoAplicado(float descontoAplicado) { this.descontoAplicado = descontoAplicado; }

    public int getQuantidadeVendida() { return quantidadeVendida; }

    public void setQuantidadeVendida(int quantidadeVendida) { this.quantidadeVendida = quantidadeVendida; }

    @Override
    public String toString() {
        return "CampanhaGrupo{" +
                "grupo=" + grupo +
                ", quantidade=" + quantidade +
                ", desconto=" + desconto +
                '}';
    }

    public String toDisplay() {
        return grupo.getDescricao() +
                " Qtd.:" + quantidade +
                " Desc.:" + desconto;
    }
}
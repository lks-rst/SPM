package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.Date;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.modelo.Visita;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VisitaDataAccess;

/**
 * Created by Lucas on 02/08/2016.
 */
public class SalvarPedido
{
    private float desconto;
    private float frete;
    private float total;

    public float getDesconto() { return desconto; }

    public void setDesconto(float desconto) { this.desconto = desconto; }

    public void setFrete(float frete) { this.frete = frete; }

    public void setTotal(float total) { this.total = total; }

    public float calcularTotal()
    {
        /*return this.total - (this.total * (this.desconto / 100)) + this.frete;*/
        return this.total - this.desconto + this.frete;
    }

    public Boolean verificarMinimo(float natureza, float configuracao)
    {
        float minimo = 0;

        minimo = natureza > 0 ? natureza : configuracao;

        return this.total >= minimo;
    }

    public Boolean verificarSaldo(float saldo)
    {
        if(this.desconto > 0)
            return (saldo - this.desconto >= 0);
        else
            return true;
    }

    public Boolean verificarJustificativa(int justificativa, Boolean controlar, int dia)
    {
        Date today = new Date();
        int day = today.getDay();
        day+=1;

        String dias;
        String p;
        String s;
        String t;

        try { dias = String.valueOf(dia); p = dias.substring(0, 1); s = dias.substring(1, 2); t = dias.substring(2); }
        catch (Exception e) { p = "0"; s = "0"; t = "0"; }

        if(!controlar)
            return true;
        else if ((Integer.parseInt(p) == day) || (Integer.parseInt(s) == day) || (Integer.parseInt(t) == day) || ((justificativa != 500) && (justificativa != 0)))
            return true;
        else return false;
    }

    public Boolean salvarPedido(Context ctx, Venda venda/*, passar motivo venda (justificativa)*/)
    {
        VendaDataAccess vda = new VendaDataAccess(ctx);
        VisitaDataAccess vida = new VisitaDataAccess(ctx);
        Visita v = new Visita();
        v.setVenda("S");
        v.setCli(venda.getCliente().getCodigoCliente());
        v.setData(venda.getData());
        v.setHora(venda.getHora());
        v.setMotivo(500);

        /*
        TODO: Alterar para acrescentar o salvamento dos valores de Volume e contribuição no pedido
         */

        try
        {
            try
            {
                if(venda.getCodigo() <= 0)
                {
                    venda.setCodigo(vda.buscarCodigo());
                    v.setPed(vda.buscarCodigo());
                }
            }
            catch (Exception ex)
            {
                venda.setCodigo(vda.buscarCodigo());
                v.setPed(vda.buscarCodigo());
            }

            vda.insert(venda);
            vida.insert(v);

            return true;
        }
        catch (GenercicException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean justificarPedido(Context ctx, Venda venda)
    {
        VisitaDataAccess vida = new VisitaDataAccess(ctx);
        Visita v = new Visita();
        v.setVenda("N");
        v.setCli(venda.getCliente().getCodigoCliente());
        v.setData(venda.getData());
        v.setHora(venda.getHora());
        v.setMotivo(venda.getJustificativa());
        v.setPed(0);

        try
        {
            vida.insert(v);

            return true;
        }
        catch (GenercicException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void atualizarSaldo(Context ctx, float saldo)
    {
        VendaDataAccess vda = new VendaDataAccess(ctx);

        try
        {
            vda.atualizarSaldo(saldo);
        }
        catch (GenercicException e)
        {
            e.printStackTrace();
        }
    }

    public Boolean verificarItens() { return null; }

    public float getFrete() { return frete; }

    public float getTotal() { return total; }

    private Boolean minimoNatureza(int natureza)
    {
        return true;
    }

    private Boolean minimoConfiguracao()
    {
        return true;
    }

    private Boolean verificarCampanhas() {
        return null;
    }

    private Boolean verificarTabloides() {
        return null;
    }

    private void listarCampanhas() { /*****/ }

    private void listarTabloides() { /*****/ }

    private void aplicarDescontoTabloide(float desconto, int codigo) { /*****/ }

    private void aplicaDescontoCampanh(float desconto, int codigo) { /*****/ }
}
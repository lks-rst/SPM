package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;
import br.com.sulpasso.sulpassomobile.util.modelos.GenericItemFour;

/**
 * Created by Lucas on 05/03/2018 - 11:32 as part of the project SulpassoMobile.
 */
public class PlanoVisitas
{
    private ClienteDataAccess cda;
    private VendaDataAccess vda;
    private Context ctx;
    ArrayList<String> plano;


    public PlanoVisitas(Context ctx)
    {
        this.ctx = ctx;
        this.cda = new ClienteDataAccess(ctx);
        this.vda = new VendaDataAccess(ctx);
    }

    public ArrayList<String> gerarPlano(String data)
    {
        this.plano = new ArrayList<>();

/***************** SEGUNDA *********/
/**0**/ plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesDia(2)));
/**1**/ plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesPositivadosDia(2, data)));
/**2**/ plano.add(Formatacao.format2d(this.percentual(plano.get(1), plano.get(0))));
/**3**/ plano.add(Formatacao.format2d(this.cda.buscarPlanoForaDia(2, data)));
/**4**/ plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesVisitadosDia(2, data)));
/**5**/ plano.add(Formatacao.format2d(this.clientesNaoPositivados(plano.get(0), plano.get(1), plano.get(4))));
/**6**/ plano.add(Formatacao.format2d(this.cda.buscarValorTotalPedidosDia(2, data)));
/**7**/ plano.add(Formatacao.format2d(this.cda.buscarVolumePedidosDia(2, data)));
/***************** TERÇA *********/
/**8**/ plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesDia(3)));
/**9**/ plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesPositivadosDia(3, data)));
/**10**/plano.add(Formatacao.format2d(this.percentual(plano.get(9), plano.get(8))));
/**11**/plano.add(Formatacao.format2d(this.cda.buscarPlanoForaDia(3, data)));
/**12**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesVisitadosDia(3, data)));
/**13**/plano.add(Formatacao.format2d(this.clientesNaoPositivados(plano.get(8), plano.get(9), plano.get(12))));
/**14**/plano.add(Formatacao.format2d(this.cda.buscarValorTotalPedidosDia(3, data)));
/**15**/plano.add(Formatacao.format2d(this.cda.buscarVolumePedidosDia(3, data)));
/***************** QUARTA *********/
/**16**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesDia(4)));
/**17**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesPositivadosDia(4, data)));
/**18**/plano.add(Formatacao.format2d(this.percentual(plano.get(17), plano.get(16))));
/**19**/plano.add(Formatacao.format2d(this.cda.buscarPlanoForaDia(4, data)));
/**20**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesVisitadosDia(4, data)));
/**21**/plano.add(Formatacao.format2d(this.clientesNaoPositivados(plano.get(16), plano.get(17), plano.get(20))));
/**22**/plano.add(Formatacao.format2d(this.cda.buscarValorTotalPedidosDia(4, data)));
/**23**/plano.add(Formatacao.format2d(this.cda.buscarVolumePedidosDia(4, data)));
/***************** QUINTA *********/
/**24**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesDia(5)));
/**25**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesPositivadosDia(5, data)));
/**26**/plano.add(Formatacao.format2d(this.percentual(plano.get(25), plano.get(24))));
/**27**/plano.add(Formatacao.format2d(this.cda.buscarPlanoForaDia(5, data)));
/**28**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesVisitadosDia(5, data)));
/**29**/plano.add(Formatacao.format2d(this.clientesNaoPositivados(plano.get(24), plano.get(25), plano.get(28))));
/**30**/plano.add(Formatacao.format2d(this.cda.buscarValorTotalPedidosDia(5, data)));
/**31**/plano.add(Formatacao.format2d(this.cda.buscarVolumePedidosDia(5, data)));
/***************** SEXTA *********/
/**32**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesDia(6)));
/**33**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesPositivadosDia(6, data)));
/**34**/plano.add(Formatacao.format2d(this.percentual(plano.get(33), plano.get(32))));
/**35**/plano.add(Formatacao.format2d(this.cda.buscarPlanoForaDia(6, data)));
/**36**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesVisitadosDia(6, data)));
/**37**/plano.add(Formatacao.format2d(this.clientesNaoPositivados(plano.get(32), plano.get(33), plano.get(36))));
/**38**/plano.add(Formatacao.format2d(this.cda.buscarValorTotalPedidosDia(6, data)));
/**39**/plano.add(Formatacao.format2d(this.cda.buscarVolumePedidosDia(6, data)));
/***************** SABADO *********/
/**40**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesDia(7)));
/**41**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesPositivadosDia(7, data)));
/**42**/plano.add(Formatacao.format2d(this.percentual(plano.get(41), plano.get(40))));
/**43**/plano.add(Formatacao.format2d(this.cda.buscarPlanoForaDia(7, data)));
/**44**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesVisitadosDia(7, data)));
/**45**/plano.add(Formatacao.format2d(this.clientesNaoPositivados(plano.get(40), plano.get(41), plano.get(44))));
/**46**/plano.add(Formatacao.format2d(this.cda.buscarValorTotalPedidosDia(7, data)));
/**47**/plano.add(Formatacao.format2d(this.cda.buscarVolumePedidosDia(7, data)));
/***************** DOMINGO *********/
/**48**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesDia(0)));
/**49**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesPositivadosDia(0, data)));
/**50**/plano.add(Formatacao.format2d(this.percentual(plano.get(49), plano.get(48))));
/**51**/plano.add(Formatacao.format2d(this.cda.buscarPlanoForaDia(0, data)));
/**52**/plano.add(Formatacao.format2d(this.cda.buscarPlanoClientesVisitadosDia(0, data)));
/**53**/plano.add(Formatacao.format2d(this.clientesNaoPositivados(plano.get(48), plano.get(49), plano.get(51))));
/**54**/plano.add(Formatacao.format2d(this.cda.buscarValorTotalPedidosDia(0, data)));
/**55**/plano.add(Formatacao.format2d(this.cda.buscarVolumePedidosDia(0, data)));
/***************** Totais *********/
/**56**/plano.add(Formatacao.format2d(this.calcularTotais(0)));
/**57**/plano.add(Formatacao.format2d(this.calcularTotais(1)));
/**58**/plano.add(Formatacao.format2d(this.calcularTotais(2)));
/**59**/plano.add(Formatacao.format2d(this.calcularTotais(3)));
/**60**/plano.add(Formatacao.format2d(this.calcularTotais(4)));
/**61**/plano.add(Formatacao.format2d(this.calcularTotais(5)));
/**62**/plano.add(Formatacao.format2d(this.calcularTotais(6)));
/**63**/plano.add(Formatacao.format2d(this.calcularTotais(7)));

        return plano;
    }

    public float percentual(String base, String total)
    {
        return (Float.parseFloat(base) / Float.parseFloat(total)) * 100;
    }

    public float clientesNaoPositivados(String total, String venda, String visitas)
    {
        return (Float.parseFloat(total)  - (Float.parseFloat(venda) + Float.parseFloat(visitas)));
    }

    public float calcularTotais(int campo)
    {
        float total = 0;

        total += Float.parseFloat(this.plano.get((campo)));
        total += Float.parseFloat(this.plano.get((campo + 8)));
        total += Float.parseFloat(this.plano.get((campo + 16)));
        total += Float.parseFloat(this.plano.get((campo + 24)));
        total += Float.parseFloat(this.plano.get((campo + 32)));
        total += Float.parseFloat(this.plano.get((campo + 40)));
        total += Float.parseFloat(this.plano.get((campo + 48)));

        return total;
    }

    public ArrayList<GenericItemFour> listarNaoPositivados(String data)
    {
        return this.cda.listarNaoPositivadosData(data);
    }
}

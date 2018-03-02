package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import br.com.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Venda;

/**
 * Created by Lucas on 02/03/2018 - 09:08 as part of the project SulpassoMobile.
 */
public class Graficos
{
    private GraficoModel graficoModel;
    private VendaDataAccess vda;

    private final int WIDTH_PEDIDOS = 300;
    private final int WIDTH_CLIENTES = 200;
    private final int WIDTH_PEDIDO_MEDIO = 500;
    private final int WIDTH_MEDIA_ITENS = 10;
    private final int WIDTH_FREQUENCIA = 4;
    private final int WIDTH_ACELERADOR = 14;
    private final int WIDTH_PRECO_MEDIO = 30;
    private final int WIDTH_TOTAL = 600;

    private String where2 = " where " + Venda.TIPO + " not like 'TR' and " +
            Venda.DATA + " > date('now', 'start of month', '-2 month') and " +
            Venda.DATA + " < date('now', 'start of month', '-1 month', '-1 day')";

    private String where1 = " where " + Venda.TIPO + " not like 'TR' and " +
            Venda.DATA + " > date('now', 'start of month', '-1 month') and " +
            Venda.DATA + " < date('now', 'start of month', '-1 day')";

    private String where0 = " where " + Venda.TIPO + " not like 'TR' and " +
            Venda.DATA + " > date('now', 'start of month')";



    public Graficos(Context ctx)
    {
        this.graficoModel = new GraficoModel();
        this.vda = new VendaDataAccess(ctx);
    }

    public void  gerarGraficos()
    {
        this.graficoModel.setF_pedidos1(this.vda.totalPedidos(this.where0));
        this.graficoModel.setF_pedidos2(this.vda.totalPedidos(this.where1));
        this.graficoModel.setF_pedidos3(this.vda.totalPedidos(this.where2));

        this.graficoModel.setF_clientes1(this.vda.totalClientes(this.where0));
        this.graficoModel.setF_clientes2(this.vda.totalClientes(this.where1));
        this.graficoModel.setF_clientes3(this.vda.totalClientes(this.where2));

        this.graficoModel.setF_pedido_medio1
                (this.calcularPedidoMedio(this.where0, this.graficoModel.getF_pedidos1()));
        this.graficoModel.setF_pedido_medio2
                (this.calcularPedidoMedio(this.where1, this.graficoModel.getF_pedidos2()));
        this.graficoModel.setF_pedido_medio3
                (this.calcularPedidoMedio(this.where2, this.graficoModel.getF_pedidos3()));

        this.graficoModel.setF_itens_pedido1
                (this.calcularMediaItens(this.where0, this.graficoModel.getF_pedidos1()));
        this.graficoModel.setF_itens_pedido2
                (this.calcularMediaItens(this.where1, this.graficoModel.getF_pedidos2()));
        this.graficoModel.setF_itens_pedido3
                (this.calcularMediaItens(this.where2, this.graficoModel.getF_pedidos3()));

        this.graficoModel.setF_frequencia1
                (this.calcularFrequencia(this.graficoModel.getF_clientes1(), this.graficoModel.getF_pedidos1()));
        this.graficoModel.setF_frequencia2
                (this.calcularFrequencia(this.graficoModel.getF_clientes2(), this.graficoModel.getF_pedidos2()));
        this.graficoModel.setF_frequencia3
                (this.calcularFrequencia(this.graficoModel.getF_clientes3(), this.graficoModel.getF_pedidos3()));

        this.graficoModel.setF_acelerdador1
                (this.calcularAceleracao(this.graficoModel.getF_itens_pedido1(), this.graficoModel.getF_frequencia1()));
        this.graficoModel.setF_acelerdador2
                (this.calcularAceleracao(this.graficoModel.getF_itens_pedido2(), this.graficoModel.getF_frequencia2()));
        this.graficoModel.setF_acelerdador3
                (this.calcularAceleracao(this.graficoModel.getF_itens_pedido3(), this.graficoModel.getF_frequencia3()));
    }

    public int percentualGrafico(int campo, int mes, int max)
    {
        switch (campo)
        {
            case 1:
                switch (mes)
                {
                    case 1:
                        return this.calcularPercentual(this.graficoModel.getF_pedidos1(), WIDTH_PEDIDOS, max);
                    case 2:
                        return this.calcularPercentual(this.graficoModel.getF_pedidos2(), WIDTH_PEDIDOS, max);
                    default:
                        return this.calcularPercentual(this.graficoModel.getF_pedidos3(), WIDTH_PEDIDOS, max);
                }
            case 2:
                switch (mes)
                {
                    case 1:
                        return this.calcularPercentual(this.graficoModel.getF_clientes1(), WIDTH_CLIENTES, max);
                    case 2:
                        return this.calcularPercentual(this.graficoModel.getF_clientes2(), WIDTH_CLIENTES, max);
                    default:
                        return this.calcularPercentual(this.graficoModel.getF_clientes3(), WIDTH_CLIENTES, max);
                }
            case 3:
                switch (mes)
                {
                    case 1:
                        return this.calcularPercentual(this.graficoModel.getF_pedido_medio1(), WIDTH_PEDIDO_MEDIO, max);
                    case 2:
                        return this.calcularPercentual(this.graficoModel.getF_pedido_medio2(), WIDTH_PEDIDO_MEDIO, max);
                    default:
                        return this.calcularPercentual(this.graficoModel.getF_pedido_medio3(), WIDTH_PEDIDO_MEDIO, max);
                }
            case 4:
                switch (mes)
                {
                    case 1:
                        return this.calcularPercentual(this.graficoModel.getF_itens_pedido1(), WIDTH_MEDIA_ITENS, max);
                    case 2:
                        return this.calcularPercentual(this.graficoModel.getF_itens_pedido2(), WIDTH_MEDIA_ITENS, max);
                    default:
                        return this.calcularPercentual(this.graficoModel.getF_itens_pedido3(), WIDTH_MEDIA_ITENS, max);
                }
            case 5:
                switch (mes)
                {
                    case 1:
                        return this.calcularPercentual(this.graficoModel.getF_frequencia1(), WIDTH_FREQUENCIA, max);
                    case 2:
                        return this.calcularPercentual(this.graficoModel.getF_frequencia2(), WIDTH_FREQUENCIA, max);
                    default:
                        return this.calcularPercentual(this.graficoModel.getF_frequencia3(), WIDTH_FREQUENCIA, max);
                }
            default:
                switch (mes)
                {
                    case 1:
                        return this.calcularPercentual(this.graficoModel.getF_acelerdador1(), WIDTH_ACELERADOR, max);
                    case 2:
                        return this.calcularPercentual(this.graficoModel.getF_acelerdador2(), WIDTH_ACELERADOR, max);
                    default:
                        return this.calcularPercentual(this.graficoModel.getF_acelerdador3(), WIDTH_ACELERADOR, max);
                }
        }
    }

    private int calcularPercentual(float valor, int tamanho, int max)
    {
        float percentual = 0;
        int width = tamanho;

        try { percentual = ((valor / width) * 100); }
        catch (Exception e) { percentual = 0; }

        try { width = (int) ((percentual * max) / 100); }
        catch (Exception e) { width = 0; }

        return width;
    }

    private float calcularPedidoMedio(String where, float qtdPedido)
    {
        return vda.valorPedidos(where) / qtdPedido;
    }

    private float calcularMediaItens(String where, float qtdPedido)
    {
        return vda.mediaItens(where) / qtdPedido;
    }

    private float calcularFrequencia(float pedidos, float clientes)
    {
        return pedidos / clientes;
    }

    private float calcularAceleracao(float itens, float frequencia)
    {
        return itens + frequencia;
    }

    private class GraficoModel
    {
        private float f_pedidos1;
        private float f_pedidos2;
        private float f_pedidos3;

        private float f_clientes1;
        private float f_clientes2;
        private float f_clientes3;

        private float f_pedido_medio1;
        private float f_pedido_medio2;
        private float f_pedido_medio3;

        private float f_itens_pedido1;
        private float f_itens_pedido2;
        private float f_itens_pedido3;

        private float f_frequencia1;
        private float f_frequencia2;
        private float f_frequencia3;

        private float f_acelerdador1;
        private float f_acelerdador2;
        private float f_acelerdador3;

        private float f_preco_medio1;
        private float f_preco_medio2;
        private float f_preco_medio3;

        public float getF_pedidos1() { return f_pedidos1; }

        public void setF_pedidos1(float f_pedidos1) { this.f_pedidos1 = f_pedidos1; }

        public float getF_pedidos2() { return f_pedidos2; }

        public void setF_pedidos2(float f_pedidos2) { this.f_pedidos2 = f_pedidos2; }

        public float getF_pedidos3() { return f_pedidos3; }

        public void setF_pedidos3(float f_pedidos3) { this.f_pedidos3 = f_pedidos3; }

        public float getF_clientes1() { return f_clientes1; }

        public void setF_clientes1(float f_clientes1) { this.f_clientes1 = f_clientes1; }

        public float getF_clientes2() { return f_clientes2; }

        public void setF_clientes2(float f_clientes2) { this.f_clientes2 = f_clientes2; }

        public float getF_clientes3() { return f_clientes3; }

        public void setF_clientes3(float f_clientes3) { this.f_clientes3 = f_clientes3; }

        public float getF_pedido_medio1() { return f_pedido_medio1; }

        public void setF_pedido_medio1(float f_pedido_medio1) { this.f_pedido_medio1 = f_pedido_medio1; }

        public float getF_pedido_medio2() { return f_pedido_medio2; }

        public void setF_pedido_medio2(float f_pedido_medio2) { this.f_pedido_medio2 = f_pedido_medio2; }

        public float getF_pedido_medio3() { return f_pedido_medio3; }

        public void setF_pedido_medio3(float f_pedido_medio3) { this.f_pedido_medio3 = f_pedido_medio3; }

        public float getF_itens_pedido1() { return f_itens_pedido1; }

        public void setF_itens_pedido1(float f_itens_pedido1) { this.f_itens_pedido1 = f_itens_pedido1; }

        public float getF_itens_pedido2() { return f_itens_pedido2; }

        public void setF_itens_pedido2(float f_itens_pedido2) { this.f_itens_pedido2 = f_itens_pedido2; }

        public float getF_itens_pedido3() { return f_itens_pedido3; }

        public void setF_itens_pedido3(float f_itens_pedido3) { this.f_itens_pedido3 = f_itens_pedido3; }

        public float getF_frequencia1() { return f_frequencia1; }

        public void setF_frequencia1(float f_frequencia1) { this.f_frequencia1 = f_frequencia1; }

        public float getF_frequencia2() { return f_frequencia2; }

        public void setF_frequencia2(float f_frequencia2) { this.f_frequencia2 = f_frequencia2; }

        public float getF_frequencia3() { return f_frequencia3; }

        public void setF_frequencia3(float f_frequencia3) { this.f_frequencia3 = f_frequencia3; }

        public float getF_acelerdador1() { return f_acelerdador1; }

        public void setF_acelerdador1(float f_acelerdador1) { this.f_acelerdador1 = f_acelerdador1; }

        public float getF_acelerdador2() { return f_acelerdador2; }

        public void setF_acelerdador2(float f_acelerdador2) { this.f_acelerdador2 = f_acelerdador2; }

        public float getF_acelerdador3() { return f_acelerdador3; }

        public void setF_acelerdador3(float f_acelerdador3) { this.f_acelerdador3 = f_acelerdador3; }

        public float getF_preco_medio1() { return f_preco_medio1; }

        public void setF_preco_medio1(float f_preco_medio1) { this.f_preco_medio1 = f_preco_medio1; }

        public float getF_preco_medio2() { return f_preco_medio2; }

        public void setF_preco_medio2(float f_preco_medio2) { this.f_preco_medio2 = f_preco_medio2; }

        public float getF_preco_medio3() { return f_preco_medio3; }

        public void setF_preco_medio3(float f_preco_medio3) { this.f_preco_medio3 = f_preco_medio3; }
    }
}
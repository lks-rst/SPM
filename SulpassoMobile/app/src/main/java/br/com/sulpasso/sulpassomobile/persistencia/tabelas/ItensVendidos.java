package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 17/08/2016.
 */
public class ItensVendidos
{
    public static final String TABELA = "ItensVendidos";
    public static final String ITEM = "Item";
    public static final String PEDIDO = "Pedido";
    public static final String VALORTABELA = "ValorTabela";
    public static final String VALORDIGITADO = "ValorDigitado";
    public static final String QUANTIDADE = "Quantidade";
    public static final String DESCONTOCG = "DescontoCampanhaGrupo";
    public static final String DESCONTOCP = "DescontoCampanhaProduto";
    public static final String DESCONTO = "Desconto";
    public static final String VALORLIQUIDO = "ValorLiquido";
    public static final String FLEX = "Flex";
    public static final String TOTAL = "Total";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(ITEM);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(PEDIDO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(VALORTABELA);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(VALORDIGITADO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(QUANTIDADE);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(DESCONTO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(DESCONTOCP);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(DESCONTOCG);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(VALORLIQUIDO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(FLEX);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(TOTAL);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(ITEM);
        builder.append(", ");
        builder.append(PEDIDO);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
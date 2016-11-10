package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 16:42 as part of the project SulpassoMobile.
 */
public class PrePedidoDireta
{
    public static final String TABELA = "PrePedidoDireta";
    public static final String CLIENTE = "Cliente";
    public static final String PRODUTO = "Produto";
    public static final String QUANTIDADE = "QuantidadeSolicitada";
    public static final String VALOR = "ValorSolicitado";
    public static final String QUANTIDADE2 = "QuantidadeEntregue";
    public static final String VALOR2 = "ValorEntregue";
    public static final String DATA = "Data";
    public static final String NOTA = "Nota";
    public static final String PEDIDO = "Pedido";
    public static final String TIPO = "Tipo";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(CLIENTE);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(PRODUTO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(QUANTIDADE);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(DATA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(VALOR);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(QUANTIDADE2);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(VALOR2);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(NOTA);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(PEDIDO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(TIPO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(CLIENTE);
        builder.append(", ");
        builder.append(PRODUTO);
        builder.append(", ");
        builder.append(NOTA);
        builder.append(", ");
        builder.append(PEDIDO);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
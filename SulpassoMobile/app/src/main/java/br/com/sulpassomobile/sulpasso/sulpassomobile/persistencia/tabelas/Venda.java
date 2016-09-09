package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Venda
{
    public static final String TABELA = "Pedidos";
    public static final String CODIGO = "CodigoPedido";
    public static final String CLIENTE = "CodigoCliente";
    public static final String TAB = "TabelaPedido";
    public static final String OBSERVACAO = "Observacao";
    public static final String OBSERVACAONOTA = "ObservacaoNota";
    public static final String JUSTIFICATIVA = "ObservacaoDesconto";
    public static final String DESCONTO = "DescontoPedidos";
    public static final String TOTAL = "ValorPedido";
    public static final String HORA = "HoraPedidos";
    public static final String DATA = "DataPedidos";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(CODIGO);
        builder.append(Types.INTEIRO);
        builder.append(Types.PRIMARY_KEY);
        builder.append(",");
        builder.append(CLIENTE);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(TAB);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(OBSERVACAO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(OBSERVACAONOTA);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(JUSTIFICATIVA);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(DESCONTO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(TOTAL);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(HORA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(DATA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}

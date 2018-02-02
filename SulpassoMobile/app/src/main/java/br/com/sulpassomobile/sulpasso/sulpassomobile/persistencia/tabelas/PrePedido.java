package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 16:39 as part of the project SulpassoMobile.
 */
public class PrePedido
{
    public static final String TABELA = "PrePedido";
    public static final String CLIENTE = "Cliente";
    public static final String PRODUTO = "Produto";
    public static final String QUANTIDADE = "Quantidade";
    public static final String DATA = "Data";
    public static final String VALOR = "Valor";

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
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(CLIENTE);
        builder.append(", ");
        builder.append(PRODUTO);
        builder.append(", ");
        builder.append(QUANTIDADE);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
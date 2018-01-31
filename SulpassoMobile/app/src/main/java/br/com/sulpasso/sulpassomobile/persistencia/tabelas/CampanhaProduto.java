package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 10/10/2016.
 */
public class CampanhaProduto
{
    public static final String TABELA = "CampanhaProduto";
    public static final String CODIGO = "Codigo";
    public static final String QUANTIDADE = "Quantidade";
    public static final String DESCONTO = "Desconto";
    public static final String ITEM = "Item";


    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(CODIGO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(QUANTIDADE);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(DESCONTO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(ITEM);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(CODIGO);
        builder.append(", ");
        builder.append(ITEM);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}

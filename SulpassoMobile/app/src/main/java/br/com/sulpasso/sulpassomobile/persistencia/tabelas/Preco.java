package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Preco
{
    public static final String TABELA = "Precos";
    public static final String CODIGO = "TabelaPrecos";
    public static final String PRECO = "Preco";
    public static final String PRODUTO = "CodigoProduto";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(CODIGO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(PRECO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(PRODUTO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(CODIGO);
        builder.append(", ");
        builder.append(PRODUTO);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}

package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Estoque
{
    public static final String TABELA = "Estoque";
    public static final String ESTOQUE = "Estoque";
    public static final String EMPRESA = "Empresa";
    public static final String PRODUTO = "CodigoProduto";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(ESTOQUE);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(EMPRESA);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(PRODUTO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(EMPRESA);
        builder.append(", ");
        builder.append(PRODUTO);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}

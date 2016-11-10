package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:52 as part of the project SulpassoMobile.
 */
public class Mix
{
    public static final String TABELA = "Mix";
    public static final String TIPOLOGIA = "Tipologia";
    public static final String PRODUTO = "Produto";
    public static final String TIPO = "Tipo";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(TIPOLOGIA);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(PRODUTO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(TIPO);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(TIPOLOGIA);
        builder.append(", ");
        builder.append(PRODUTO);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
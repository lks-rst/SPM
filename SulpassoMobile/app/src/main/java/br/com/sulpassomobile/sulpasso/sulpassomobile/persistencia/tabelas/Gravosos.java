package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:19 as part of the project SulpassoMobile.
 */
public class Gravosos
{
    public static final String TABELA = "Gravosos";
    public static final String ITEM = "Item";
    public static final String QUANTIDADE = "Quantidade";
    public static final String FABRICACAO = "Fabricacao";
    public static final String VALIDADE = "Validade";
    public static final String DIAS = "Dias";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(ITEM);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(QUANTIDADE);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(FABRICACAO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(VALIDADE);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(", ");
        builder.append(DIAS);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(ITEM);
        builder.append(", ");
        builder.append(FABRICACAO);
        builder.append(", ");
        builder.append(VALIDADE);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
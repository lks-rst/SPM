package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:31 as part of the project SulpassoMobile.
 */
public class Kit
{
    public static final String TABELA = "Kit";
    public static final String KIT = "Kit";
    public static final String ITEM = "Item";
    public static final String DESC = "Descricao";
    public static final String QUANTIDADE = "Quantidade";
    public static final String VALOR = "Valor";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(KIT);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(ITEM);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(DESC);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(QUANTIDADE);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(VALOR);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(KIT);
        builder.append(", ");
        builder.append(ITEM);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
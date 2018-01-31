package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 15/09/2016.
 */
public class Promocao
{
    public static final String TABELA = "Promocoes";
    public static final String ITEM = "Item";
    public static final String QUANTIDADE = "Quantidade";
    public static final String VALOR = "Valor";

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
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(VALOR);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(ITEM);
        builder.append(",");
        builder.append(QUANTIDADE);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}

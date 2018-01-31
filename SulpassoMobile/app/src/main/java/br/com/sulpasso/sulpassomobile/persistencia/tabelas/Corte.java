package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:55 as part of the project SulpassoMobile.
 */
public class Corte
{
    public static final String TABELA = "Cortes";
    public static final String CLIENTE = "Cliente";
    public static final String PRODUTO = "Produto";
    public static final String QUANTIDADE = "Quantidade";
    public static final String DATA = "Data";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(CLIENTE);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(PRODUTO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(QUANTIDADE);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(DATA);
        builder.append(Types.CHAR);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}
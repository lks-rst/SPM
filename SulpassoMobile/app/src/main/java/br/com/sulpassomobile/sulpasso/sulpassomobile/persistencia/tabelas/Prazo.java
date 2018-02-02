package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Prazo
{
    public static final String TABELA = "Prazo";
    public static final String CODIGO = "CodigoPrazo";
    public static final String TAB = "TabelaPrazo";
    public static final String DESDOBRAMENTO = "Desdobramento";

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
        builder.append(TAB);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(DESDOBRAMENTO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}

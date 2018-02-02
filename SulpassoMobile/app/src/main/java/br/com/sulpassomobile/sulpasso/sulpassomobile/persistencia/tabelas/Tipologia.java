package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:13 as part of the project SulpassoMobile.
 */
public class Tipologia
{
    public static final String TABELA = "Tipologia";
    public static final String CODIGO = "Codigo";
    public static final String DESCRICAO = "Descricao";

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
        builder.append(DESCRICAO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}

package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:44 as part of the project SulpassoMobile.
 */
public class Atividade
{
    public static final String TABELA = "Atividade";
    public static final String CODIGO = "CodigoAtividade";
    public static final String ATIVIDADE = "Atividade";

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
        builder.append(ATIVIDADE);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}
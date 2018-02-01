package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 10/10/2016.
 */
public class Grupo
{
    public static final String TABELA = "Grupos";
    public static final String GRUPO = "Grupo";
    public static final String SUB = "SubGrupo";
    public static final String DIV = "Divisao";
    public static final String DESC = "Descricao";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(GRUPO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(SUB);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(DIV);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(DESC);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(GRUPO);
        builder.append(", ");
        builder.append(SUB);
        builder.append(", ");
        builder.append(DIV);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
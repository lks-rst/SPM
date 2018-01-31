package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 18:00 as part of the project SulpassoMobile.
 */
public class GrupoBloqueadoNatureza
{
    public static final String TABELA = "GruposBloqueadosNatureza";
    public static final String GRUPO = "Grupo";
    public static final String SUB = "SubGrupo";
    public static final String DIV = "Divisao";
    public static final String NATUREZA = "Natureza";

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
        builder.append(NATUREZA);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(GRUPO);
        builder.append(", ");
        builder.append(SUB);
        builder.append(", ");
        builder.append(DIV);
        builder.append(",");
        builder.append(NATUREZA);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
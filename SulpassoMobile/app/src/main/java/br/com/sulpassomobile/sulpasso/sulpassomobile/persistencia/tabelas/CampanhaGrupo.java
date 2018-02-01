package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 10/10/2016.
 */
public class CampanhaGrupo
{
    public static final String TABELA = "CampanhaGrupo";
    public static final String GRUPO = "Grupo";
    public static final String SUB = "SubGrupo";
    public static final String DIV = "Divisao";
    public static final String QUANTIDADE = "Quantidade";
    public static final String DESCONTO = "Desconto";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(GRUPO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(SUB);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(DIV);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(QUANTIDADE);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(DESCONTO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(GRUPO);
        builder.append(", ");
        builder.append(SUB);
        builder.append(", ");
        builder.append(DIV);
        builder.append(", ");
        builder.append(QUANTIDADE);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}

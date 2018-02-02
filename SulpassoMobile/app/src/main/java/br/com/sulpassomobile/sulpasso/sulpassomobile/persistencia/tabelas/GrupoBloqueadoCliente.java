package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:46 as part of the project SulpassoMobile.
 */
public class GrupoBloqueadoCliente
{
    public static final String TABELA = "GruposBloqueadosCliente";
    public static final String GRUPO = "Grupo";
    public static final String SUB = "SubGrupo";
    public static final String DIV = "Divisao";
    public static final String CLIENTE = "Cliente";

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
        builder.append(CLIENTE);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(GRUPO);
        builder.append(", ");
        builder.append(SUB);
        builder.append(", ");
        builder.append(DIV);
        builder.append(",");
        builder.append(CLIENTE);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
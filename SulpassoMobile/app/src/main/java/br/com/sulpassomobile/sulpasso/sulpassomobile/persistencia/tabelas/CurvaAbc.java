package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:34 as part of the project SulpassoMobile.
 */
public class CurvaAbc
{
    public static final String TABELA = "CurvaAbc";
    public static final String CLIENTE = "Cliente";
    public static final String PESO1 = "Peso_1";
    public static final String PESO2 = "Peso_2";
    public static final String FATURAMENTO1 = "Faturamento_1";
    public static final String FATURAMENTO2 = "Faturamento_2";
    public static final String CONTRIBUICAO1 = "Contribuicao_1";
    public static final String CONTRIBUICAO2 = "Contribuicao_2";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(CLIENTE);
        builder.append(Types.INTEIRO);
        builder.append(Types.PRIMARY_KEY);
        builder.append(",");
        builder.append(PESO1);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(PESO2);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(FATURAMENTO1);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(FATURAMENTO2);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(CONTRIBUICAO1);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(CONTRIBUICAO2);
        builder.append(Types.FLOAT);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}
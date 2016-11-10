package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 10/10/2016.
 */
public class Cidade
{
    public static final String TABELA = "Cidade";
    public static final String CODIGO = "CodigoCidade";
    public static final String CIDADE = "Cidade";
    public static final String UF = "Estado";
    public static final String CEP = "Cep";

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
        builder.append(CIDADE);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(UF);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(CEP);
        builder.append(Types.CHAR);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}
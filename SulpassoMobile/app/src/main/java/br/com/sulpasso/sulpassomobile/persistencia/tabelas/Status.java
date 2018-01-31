package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:58 as part of the project SulpassoMobile.
 */
public class Status
{
    public static final String TABELA = "Status";
    public static final String PEDIDO = "Pedido";
    public static final String DATA = "Data";
    public static final String NOTA = "Nota";
    public static final String STATUS = "Status";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(PEDIDO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(DATA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(NOTA);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(STATUS);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}
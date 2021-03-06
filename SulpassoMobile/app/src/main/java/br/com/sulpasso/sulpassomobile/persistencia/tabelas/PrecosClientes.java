package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:41 as part of the project SulpassoMobile.
 */
public class PrecosClientes
{
    public static final String TABELA = "PrecosClientes";
    public static final String CLIENTE = "Cliente";
    public static final String ITEM = "Item";
    public static final String VALOR = "Valor";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(CLIENTE);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(ITEM);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(VALOR);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(CLIENTE);
        builder.append(", ");
        builder.append(ITEM);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
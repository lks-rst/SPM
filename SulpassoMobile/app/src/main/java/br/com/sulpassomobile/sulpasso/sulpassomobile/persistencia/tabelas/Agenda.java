package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Agenda
{
    public static final String TABELA = "Agenda";
    public static final String CODIGO = "CodigoAgenda";
    public static final String CLIENTE = "ClienteAgendado";
    public static final String DATA = "DataAgenda";
    public static final String HORA = "HoraAgenda";

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
        builder.append(CLIENTE);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(DATA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(HORA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}

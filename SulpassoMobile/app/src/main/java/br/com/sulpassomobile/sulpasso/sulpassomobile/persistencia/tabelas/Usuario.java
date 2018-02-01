package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Usuario
{
    public static final String TABELA = "Usuario";
    public static final String CODIGO = "CodigoUsuario";
    public static final String NOME = "Nome";
    public static final String SENHA = "Senha";
    public static final String LOGIN = "Login";
    public static final String SALDO = "Flex";
    public static final String COMISSAO = "Comissao";
    public static final String CONTRIBUICAO = "Contribuicao";

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
        builder.append(NOME);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(SENHA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(LOGIN);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(SALDO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(COMISSAO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(CONTRIBUICAO);
        builder.append(Types.FLOAT);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}

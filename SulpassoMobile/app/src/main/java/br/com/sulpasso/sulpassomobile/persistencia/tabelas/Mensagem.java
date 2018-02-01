package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:24 as part of the project SulpassoMobile.
 */
public class Mensagem
{
    public static final String TABELA = "Mensagem";
    public static final String CODIGO = "CodigoMensagem";
    public static final String MENSAGEM = "Mensagem";
    public static final String ASSUNTO = "Assunto";
    public static final String USUARIO = "Usuario";
    public static final String ENVIO = "Envio";
    public static final String VALIDADE = "Validade";

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
        builder.append(MENSAGEM);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(ASSUNTO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(USUARIO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(ENVIO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(VALIDADE);
        builder.append(Types.CHAR);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}

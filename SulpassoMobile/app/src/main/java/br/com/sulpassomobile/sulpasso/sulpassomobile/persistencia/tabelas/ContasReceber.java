package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:15 as part of the project SulpassoMobile.
 */
public class ContasReceber
{
    public static final String TABELA = "Contas";
    public static final String CLIENTE = "Cliente";
    public static final String DOC = "Documento";
    public static final String EMISSAO = "Emissao";
    public static final String VENCIMENTO = "Vencimento";
    public static final String VALOR = "Valor";
    public static final String TIPO = "TipoDocumento";

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
        builder.append(DOC);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(EMISSAO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(VENCIMENTO);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(VALOR);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(", ");
        builder.append(TIPO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(CLIENTE);
        builder.append(", ");
        builder.append(DOC);
        builder.append(", ");
        builder.append(TIPO);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}

package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 09:47 as part of the project SulpassoMobile.
 */
public class Devolucao
{
    public static final String TABELA = "Devolucao";
    public static final String CLIENTE = "ClienteDevolucao";
    public static final String PRODUTO = "ProdutoDevolvido";
    public static final String DOCUMENTO = "Documento";
    public static final String QUANTIDADE = "Quantidade";
    public static final String VALOR = "Valor";
    public static final String DATA = "Data";
    public static final String MOTIVO = "motivo";

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
        builder.append(PRODUTO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(DOCUMENTO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(", ");
        builder.append(QUANTIDADE);
        builder.append(Types.FLOAT);
        builder.append(", ");
        builder.append(VALOR);
        builder.append(Types.FLOAT);
        builder.append(", ");
        builder.append(DATA);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(MOTIVO);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(CLIENTE);
        builder.append(", ");
        builder.append(PRODUTO);
        builder.append(", ");
        builder.append(DOCUMENTO);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}
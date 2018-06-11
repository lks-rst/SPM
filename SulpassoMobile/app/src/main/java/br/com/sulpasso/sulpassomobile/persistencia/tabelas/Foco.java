package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 06/06/2018 - 15:08 as part of the project SulpassoMobile.
 */
public class Foco
{
    public static final String TABELA = "ProdutosFoco";
    public static final String CODIGO = "CodigoProduto";

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

        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}

package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 10/10/2016.
 */
public class TiposVenda
{
    public static final String TABELA = "TiposVenda";
    public static final String CODIGO = "CodigoTv";
    public static final String DESCRICAO = "TipoVenda";
    public static final String MINIMO = "PedidoMinimo";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(CODIGO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(Types.PRIMARY_KEY);
        builder.append(",");
        builder.append(DESCRICAO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(MINIMO);
        builder.append(Types.FLOAT);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}

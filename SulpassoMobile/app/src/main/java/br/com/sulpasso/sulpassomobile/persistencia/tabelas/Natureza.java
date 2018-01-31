package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Natureza
{
    public static final String TABELA = "Natureza";
    public static final String CODIGO = "CodigoNatureza";
    public static final String DESCRICAO = "DescricaoNatureza";
    public static final String PRAZO = "PrazoNatureza";
    public static final String MINIMO = "MinimoNatureza";
    public static final String BANCO = "BancoNatureza";
    public static final String VENDA = "VendaNatureza";

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
        builder.append(DESCRICAO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(PRAZO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(MINIMO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(BANCO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(VENDA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}

package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Item
{
    public static final String TABELA = "Produtos";
    public static final String CODIGO = "CodigoProduto";
    public static final String BARRAS = "CodigoBarras";
    public static final String DESCRICAO = "DescricaoProduto";
    public static final String REFERENCIA = "ReferenciaProduto";
    public static final String COMPLEMENTO = "Complemento";
    public static final String UNIDADEVENDA = "UnidadeVenda";
    public static final String UNIDADE = "Unidade";
    public static final String DESCONTO = "DescontoProduto";
    public static final String MINIMO = "MinimoProduto";
    public static final String FLEX = "FlexProduto";

    public static final String QUANTIDADECAIXA = "QuantidadeCaixa";
    public static final String CONTRIBUICAO = "Contribuicao";
    public static final String PESO = "Peso";
    public static final String PESOCD = "PesoCd";
    public static final String FAIXA = "Faixa";
    public static final String GRUPO = "CodigoGrupo";
    public static final String SUBGRUPO = "CodigoSGrupo";
    public static final String DIVISAO = "CodigoDivisao";

    public static final String CUSTO = "Custo";
    public static final String ESTOQUE = "Estoque";

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
        builder.append(BARRAS);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(DESCRICAO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(REFERENCIA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(COMPLEMENTO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(UNIDADEVENDA);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(UNIDADE);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(DESCONTO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(MINIMO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(FLEX);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(QUANTIDADECAIXA);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(CONTRIBUICAO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(PESO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(PESOCD);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(FAIXA);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(GRUPO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(SUBGRUPO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(DIVISAO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(CUSTO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(ESTOQUE);
        builder.append(Types.FLOAT);

        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }

    public static String AlterarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("ALTER TABLE ");
        builder.append(TABELA);
        builder.append(" ADD COLUMN ");
        builder.append(ESTOQUE);
        builder.append(Types.FLOAT);
        builder.append(" DEFAULT 0;");

        stmt = builder.toString();
        return stmt;
    }
}

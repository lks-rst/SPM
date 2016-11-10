package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 08/11/2016 - 17:06 as part of the project SulpassoMobile.
 */
public class Meta
{
    public static final String TABELA = "Metas";
    public static final String FAMILIA = "Familia";
    public static final String CLIENTE_R = "ClienteRealizado";
    public static final String CLIENTE_M = "ClienteMeta";
    public static final String VOLUME_R = "VolumeRealizado";
    public static final String VOLUME_M = "VolumeMeta";
    public static final String CONTRIBUICAO_R = "ContribuicaoRealizado";
    public static final String CONTRIBUICAO_M = "ContribuicaoMeta";
    public static final String FATURAMENTO_R = "FaturamentoRealizado";
    public static final String FATURAMENTO_M = "FaturamentoMeta";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(FAMILIA);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(CLIENTE_R);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(CLIENTE_M);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(VOLUME_R);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(VOLUME_M);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(CONTRIBUICAO_R);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(CONTRIBUICAO_M);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(FATURAMENTO_R);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(FATURAMENTO_M);
        builder.append(Types.FLOAT_NOT_NULL);

        stmt = builder.toString();
        return stmt;
    }
}
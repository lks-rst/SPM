package br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Cliente
{
    public static final String TABELA = "Clientes";
    public static final String CODIGO = "CodigoCliente";
    public static final String RAZAO = "RazaoCliente";
    public static final String FANTASIA = "FantasiaCliente";
    public static final String CGC = "CNPJ";
    public static final String IE = "IE";
    public static final String NATUREZA = "CodigoNatureza";
    public static final String PRAZO = "CodigoPrazo";
    public static final String TAB = "TabelaPrecos";
    public static final String FONE = "FoneCliente";
    public static final String CELULAR = "Celular";
    public static final String EMAIL = "Email";
    public static final String ENDERECO = "EnderecoCliente";
    public static final String BAIRRO = "BairroCliente";
    public static final String CIDADE = "CodigoCidade";
    public static final String MENSAGEM = "Mensagem";
    public static final String CEP = "Cep";
    public static final String ROTEIRO = "Roteiro";
    public static final String ALTERA = "AlteraPrazo";
    public static final String ESPECIAL = "Especial";
    public static final String SITUACAO = "Situacao";
    public static final String ATIVIDADE = "CodigoAtividade";
    public static final String BANCO = "CodigoBanco";
    public static final String VISITA = "Visita";

    public static final String VENDIDO = "Vendido";
    public static final String MEDIA = "MediaCompras";
    public static final String INDUSTRIALIZADOS = "MediaIndustrializados";
    public static final String REALIZADO = "Realizado";
    public static final String ULTIMA = "DataUltimaCompra";
    public static final String TIPO = "TipoCliente";
    public static final String CPF = "Cpf";
    public static final String NUMERO = "NumeroResidencia";
    public static final String SALDOCREDITO = "SaldoCredito";
    public static final String LIMITE = "LimiteCredito";
    public static final String METAPESO = "MetaPeso";
    public static final String CONSUMIDOR = "Consumidor";
    public static final String CONTATO = "Contato";
    public static final String SALDO = "Saldo";



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
        builder.append(RAZAO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(FANTASIA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(CGC);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(IE);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(FONE);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(EMAIL);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(ENDERECO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(BAIRRO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(CIDADE);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(NATUREZA);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(PRAZO);
        builder.append(Types.INTEIRO);


        builder.append(",");
        builder.append(MEDIA);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(INDUSTRIALIZADOS);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(REALIZADO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(ULTIMA);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(TIPO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(CPF);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(NUMERO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(MENSAGEM);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(VENDIDO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(CEP);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(SALDOCREDITO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(LIMITE);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(METAPESO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(CELULAR);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(CONSUMIDOR);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(TAB);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(CONTATO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(ROTEIRO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(ALTERA);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(ESPECIAL);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(VISITA);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(SALDO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(SITUACAO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(ATIVIDADE);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(BANCO);
        builder.append(Types.INTEIRO);

        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }
}

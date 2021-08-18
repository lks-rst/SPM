package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 22/11/2017 - 17:01 as part of the project SulpassoMobile.
 */
public class ClienteNovo
{
    public static final String TABELA = "cliente_novo";
    public static final String COD = "cli_novo_cod";
    public static final String NOME = "cli_novo_nome";
    public static final String FANTASIA = "cli_novo_fantasia";
    public static final String CGC = "cli_novo_cgc";
    public static final String IE = "cli_novo_ie";
    public static final String UF = "cli_novo_uf";
    public static final String CONTATO = "cli_novo_contato";
    public static final String TIPO = "cli_novo_tipologia";
    public static final String BAIRRO = "cli_novo_bairro";
    public static final String END = "cli_novo_endereco";
    public static final String NUM = "cli_novo_numero";
    public static final String CEP = "cli_novo_cep";
    public static final String FONE = "cli_novo_telefone";
    public static final String CEL = "cli_novo_celular";
    public static final String MAIL = "cli_novo_email";
    public static final String ANIVER = "cli_novo_aniversario";
    public static final String LEGAL = "cli_novo_representante_legal";
    public static final String CPF = "cli_novo_cpf";
    public static final String RG = "cli_novo_rg";
    public static final String PGTO = "cli_novo_pagamento";
    public static final String BANCO = "cli_novo_banco";
    public static final String VISITA = "cli_novo_visita";
    public static final String ZONA = "cli_novo_zona";
    public static final String POTENCIAL = "cli_novo_potencial";
    public static final String OBS = "cli_novo_observacao";
    public static final String ENVIO = "cli_novo_envio";
    public static final String DATA = "cli_novo_data";
    public static final String ATIV = "cli_novo_atividade";
    public static final String AREA = "cli_novo_area";
    public static final String CID_COD = "cli_novo_cid_cod";

    public static final String T_PESSOA = "cli_novo_t_pessoa";
    public static final String COMERCIAL1 = "cli_novo_comercial1";
    public static final String COMERCIAL1_FONE = "cli_novo_comercial1_fone";
    public static final String COMERCIAL2 = "cli_novo_comercial2";
    public static final String COMERCIAL2_FONE = "cli_novo_comercial2_fone";
    public static final String COMERCIAL3 = "cli_novo_comercial3";
    public static final String COMERCIAL3_FONE = "cli_novo_comercial3_fone";
    public static final String COMERCIAL_ABERTURA = "cli_novo_comercial_abertura";

    /*Campos novos, para detalhes verificar a classe do modelo*/
    public static final String NC1 = "cli_novo_nc1";
    public static final String NC2 = "cli_novo_nc2";
    public static final String NC3 = "cli_novo_nc3";
    public static final String NC4 = "cli_novo_nc4";
    public static final String NC5 = "cli_novo_nc5";
    public static final String NC6 = "cli_novo_nc6";
    public static final String NC7 = "cli_novo_nc7";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(COD);
        builder.append(Types.INTEIRO);
        builder.append(Types.PRIMARY_KEY);
        builder.append(", ");
        builder.append(NOME);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(", ");
        builder.append(FANTASIA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(", ");
        builder.append(CGC);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(", ");
        builder.append(IE);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(", ");
        builder.append(UF);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(CID_COD);
        builder.append(Types.INTEIRO);
        builder.append(", ");
        builder.append(CONTATO);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(TIPO);
        builder.append(Types.INTEIRO);
        builder.append(", ");
        builder.append(BAIRRO);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(END);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(NUM);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(CEP);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(FONE);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(CEL);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(MAIL);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(ANIVER);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(LEGAL);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(CPF);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(RG);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(PGTO);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(BANCO);
        builder.append(Types.INTEIRO);
        builder.append(", ");
        builder.append(VISITA);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(ZONA);
        builder.append(Types.INTEIRO);
        builder.append(", ");
        builder.append(POTENCIAL);
        builder.append(Types.FLOAT);
        builder.append(", ");
        builder.append(OBS);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(ENVIO);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(DATA);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(ATIV);
        builder.append(Types.INTEIRO);
        builder.append(", ");
        builder.append(AREA);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(T_PESSOA);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(COMERCIAL1);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(COMERCIAL1_FONE);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(COMERCIAL2);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(COMERCIAL2_FONE);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(COMERCIAL3);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(COMERCIAL3_FONE);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(COMERCIAL_ABERTURA);
        builder.append(Types.CHAR);
        builder.append(", ");
        /*Campos novos (verificar a classe de modelo)*/
        builder.append(NC1);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(NC2);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(NC3);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(NC4);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(NC5);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(NC6);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(NC7);
        builder.append(Types.CHAR);
        builder.append(");");

        stmt = builder.toString();
        return stmt;
    }

    /*Esses são os métodos que atualizam a tabela se for um update de sistema e não uma instalação nova*/
    public static String AlterarTabela1()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("ALTER TABLE ");
        builder.append(TABELA);
        builder.append(" ADD COLUMN ");
        builder.append(NC1);
        builder.append(Types.CHAR);
        builder.append(" DAFAULT '0';");


        stmt = builder.toString();
        return stmt;
    }

    public static String AlterarTabela2()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("ALTER TABLE ");
        builder.append(TABELA);
        builder.append(" ADD COLUMN ");
        builder.append(NC2);
        builder.append(Types.CHAR);
        builder.append(" DAFAULT '0';");


        stmt = builder.toString();
        return stmt;
    }

    public static String AlterarTabela3()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("ALTER TABLE ");
        builder.append(TABELA);
        builder.append(" ADD COLUMN ");
        builder.append(NC3);
        builder.append(Types.CHAR);
        builder.append(" DAFAULT '0';");


        stmt = builder.toString();
        return stmt;
    }

    public static String AlterarTabela4()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("ALTER TABLE ");
        builder.append(TABELA);
        builder.append(" ADD COLUMN ");
        builder.append(NC4);
        builder.append(Types.CHAR);
        builder.append(" DAFAULT '0';");


        stmt = builder.toString();
        return stmt;
    }

    public static String AlterarTabela5()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("ALTER TABLE ");
        builder.append(TABELA);
        builder.append(" ADD COLUMN ");
        builder.append(NC5);
        builder.append(Types.CHAR);
        builder.append(" DAFAULT '0';");


        stmt = builder.toString();
        return stmt;
    }

    public static String AlterarTabela6()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("ALTER TABLE ");
        builder.append(TABELA);
        builder.append(" ADD COLUMN ");
        builder.append(NC6);
        builder.append(Types.CHAR);
        builder.append(" DAFAULT '0';");


        stmt = builder.toString();
        return stmt;
    }

    public static String AlterarTabela7()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("ALTER TABLE ");
        builder.append(TABELA);
        builder.append(" ADD COLUMN ");
        builder.append(NC7);
        builder.append(Types.CHAR);
        builder.append(" DAFAULT '0';");


        stmt = builder.toString();
        return stmt;
    }

}
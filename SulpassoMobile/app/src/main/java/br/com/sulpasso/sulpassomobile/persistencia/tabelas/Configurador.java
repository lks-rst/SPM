package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 05/12/2016 - 10:20 as part of the project SulpassoMobile.
 */
public class Configurador
{
    public static final String TABELA = "Configurador";

    /* Strings de referencia da parte da empresa no configurador */
    public static final String CODIGO = "CodigoEmpresa";
    public static final String NOME = "NomeEmpresa";
    public static final String ENDERECO = "EnderecoEmpresa";
    public static final String FONE = "FoneEmpresa";
    public static final String CIDADE = "CidadeEmpresa";
    public static final String EMAIL = "EmailEmpresa";
    public static final String SITE = "SiteEmpresa";
    public static final String ENVIADO_EMAIL = "EmailEnviado";
    /* Strings de referencia da parte do usuario no configurador */
    public static final String CODIGO_USUARIO = "CodigoUsuario";
    public static final String NOME_USUARIO = "NomeUsuario";
    public static final String SENHA = "SenhaUsuario";
    public static final String TIPOFLEX = "TipoFlex";
    public static final String VALORFLEX = "ValorFlexMostrado";
    public static final String PEDIDOMINIMO = "PedidoMinimo";
    public static final String DESCONTOCLIENTE = "DescontoCliente";
    public static final String DESCONTOITEM = "DescontoItem";
    public static final String DESCONTOPEDIDO = "DescontoPedido";
    public static final String CONTRIBUICAOIDEAL = "ContribuicaoIdeal";
    public static final String TABELAMINIMO = "TabelaMinimo";
    public static final String TABELATROCA = "TabelaTroca";
    public static final String ROTEIRO = "Roteiro";
    public static final String TIPOORDENACAO = "TipoOrdenacao";
    public static final String TIPOBUSCA = "TipoBusca";
    public static final String SALDO = "SaldoVendedor";
    public static final String COMISSAO = "ComissaoVendedor";
    public static final String CONTRIBUICAO = "ContribuicaoVendedor";
    public static final String LOGIN = "ControleAcesso";
    /* Strings de referencia da parte da venda no configurador */
    public static final String GERENCIARVISITAS = "GerenciarVisitas";
    public static final String VENDERDEVEDORES = "VenderDevedores";
    public static final String ALTERANATUREZAINICIO = "AlteraNaturezaInicio";
    public static final String ALTERANATUREZAFIM = "AlteraNaturezaFim";
    public static final String ALTERAPRAZOINICIO = "AlteraPrazosInicio";
    public static final String ALTERAPRAZOFIM = "AlteraPrazosFim";
    public static final String RECALCULARPRECO = "RecalculaPrecos";
    public static final String ALTERAPRECOS = "AlteraPrecos";
    public static final String LIMITECREDITO = "LimiteCredito";
    public static final String FLEXGERAL = "FlexGeral";
    public static final String FLEXVENDA = "FlexVenda";
    public static final String FLEXITEM = "FlexItem";
    public static final String CONTROLAESTOQUE = "ControlaEstoque";
    public static final String DESCONTOGRUPO = "DescontoGrupo";
    public static final String FRETE = "Frete";
    public static final String SOLICITASENHA = "SolicitaSenha";
    public static final String ESPECIALALTERAVALOR = "ClienteEspecialAlteraValor";
    public static final String MINIMOPRAZO = "MinimoPrazo";
    public static final String CONTROLAGPS = "ControlaLocalizacao";
    public static final String TIPODESCONTO = "TipoDesconto";
    public static final String VALIDADE = "ValidadeDados";

    public static final String MINIMOITENS = "MinimoItensDiferentes";
    /* Strings de referencia da parte dos horarios no configurador */
    public static final String INICIOMANHA = "InicioManha";
    public static final String FINALMANHA = "FinalManha";
    public static final String INICIOTARDE = "InicioTarde";
    public static final String FINALTARDE = "FinalTarde";
    public static final String TEMPOPEDIDOS = "TempoPedidos";
    public static final String TEMPOCLIENTES = "TempoClientes";
    public static final String MAXIMOITENS = "MaximoItem";
    public static final String HORA = "HoraSistema";
    public static final String PEDIDOS = "SequenciaPedidos";
    public static final String CLIENTES = "SequenciaClientes";
    public static final String ATUALIZACAO = "SequenciaAtualizacao";
    public static final String TIMEATUALIZACAO = "DataHoraAtualizacoes";
    /* Strings de referencia da parte de conexão no configurador */
    public static final String SERVERFTP = "ServerFtp";
    public static final String SERVERFTP2 = "ServerFtpInterno";
    public static final String SERVERWEB = "ServerWeb";
    public static final String FTPUSER = "FtpUsr";
    public static final String FTPPSWD = "FtpPswd";
    public static final String UPLOADFOLDER = "UploadFolder";
    public static final String DOWNLOADFOLDER = "DownloadFolder";
    public static final String EMAILSENDER = "EmailEnviar";
    public static final String EMAILPSWD = "EmailPswd";
    public static final String EMAIL1 = "Email1";
    public static final String EMAIL2 = "Email2";
    public static final String EMAIL3 = "Email3";
    public static final String CONECTIONTYPE = "ConectionType";
    public static final String EMPRESACLIENTE = "EmpresaConexao";
    /* Strings de referencia da parte de telas no configurador */
    public static final String EFETUATROCA = "EfetuaTroca";
    public static final String VENDASDIRETA = "VendasDireta";
    public static final String MIXIDEAL = "MixIdeal";
    public static final String MOSTRAMETA = "MostraMeta";
    public static final String CADASTROCLIENTE = "CadastroCliente";
    public static final String TIPOFOCO = "TipoFoco";
    public static final String PESQUISAPEDIDOS = "PesquisaPedidos";
    public static final String PESQUISAITENS = "PesquisaItens";
    public static final String PESQUISACLIENTES = "PesquisaClientes";
    public static final String PESQUISAGERAL = "PesquisaGeral";
    public static final String TELAINICIAL = "TelaInicial";

    public static final String VENDA = "TotalVendas";

    public static final String ATUALIZADO = "CodigoAtualizacao";

    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(CODIGO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(NOME);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(ENDERECO);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(FONE);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(CIDADE);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(EMAIL);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(SITE);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(ENVIADO_EMAIL);
        builder.append(Types.CHAR);

        builder.append(",");
        builder.append(CODIGO_USUARIO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(NOME_USUARIO);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(SENHA);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(TIPOFLEX);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(VALORFLEX);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(PEDIDOMINIMO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(DESCONTOCLIENTE);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(DESCONTOITEM);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(DESCONTOPEDIDO);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(CONTRIBUICAOIDEAL);
        builder.append(Types.FLOAT_NOT_NULL);
        builder.append(",");
        builder.append(TABELAMINIMO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(TABELATROCA);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(ROTEIRO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(TIPOORDENACAO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(TIPOBUSCA);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(SALDO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(COMISSAO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(CONTRIBUICAO);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(VENDA);
        builder.append(Types.FLOAT);
        builder.append(",");
        builder.append(LOGIN);
        builder.append(Types.INTEIRO);

        builder.append(",");
        builder.append(GERENCIARVISITAS);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(VENDERDEVEDORES);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(ALTERANATUREZAINICIO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(ALTERANATUREZAFIM);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(ALTERAPRAZOINICIO);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(ALTERAPRAZOFIM);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(RECALCULARPRECO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(ALTERAPRECOS);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(LIMITECREDITO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(FLEXGERAL);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(FLEXVENDA);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(FLEXITEM);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(CONTROLAESTOQUE);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(DESCONTOGRUPO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(FRETE);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(SOLICITASENHA);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(ESPECIALALTERAVALOR);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(MINIMOPRAZO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(CONTROLAGPS);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(TIPODESCONTO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(VALIDADE);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(MINIMOITENS);
        builder.append(Types.INTEIRO);


        builder.append(",");
        builder.append(INICIOMANHA);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(INICIOTARDE);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(FINALMANHA);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(FINALTARDE);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(TEMPOPEDIDOS);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(TEMPOCLIENTES);
        builder.append(Types.INTEIRO_NOT_NULL);
        builder.append(",");
        builder.append(MAXIMOITENS);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(HORA);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(PEDIDOS);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(CLIENTES);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(ATUALIZACAO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(TIMEATUALIZACAO);
        builder.append(Types.CHAR);

        builder.append(",");
        builder.append(SERVERFTP);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(SERVERFTP2);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(SERVERWEB);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(FTPUSER);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(FTPPSWD);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(UPLOADFOLDER);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(DOWNLOADFOLDER);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(EMAILSENDER);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(EMAILPSWD);
        builder.append(Types.CHAR_NOT_NULL);
        builder.append(",");
        builder.append(EMAIL1);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(EMAIL2);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(EMAIL3);
        builder.append(Types.CHAR);
        builder.append(",");
        builder.append(CONECTIONTYPE);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(EMPRESACLIENTE);
        builder.append(Types.CHAR);

        builder.append(",");
        builder.append(EFETUATROCA);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(VENDASDIRETA);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(MIXIDEAL);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(MOSTRAMETA);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(CADASTROCLIENTE);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(TIPOFOCO);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(PESQUISAPEDIDOS);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(PESQUISAITENS);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(PESQUISACLIENTES);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(PESQUISAGERAL);
        builder.append(Types.INTEIRO);
        builder.append(",");
        builder.append(TELAINICIAL);
        builder.append(Types.INTEIRO);

        builder.append(",");
        builder.append( ATUALIZADO);
        builder.append(Types.CHAR);
        builder.append(" DAFAULT '1.5');");

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
        builder.append(VENDA);
        builder.append(Types.FLOAT);
        builder.append(" DEFAULT 0;");

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
        builder.append(ENVIADO_EMAIL);
        builder.append(Types.CHAR);
        builder.append(" DEFAULT '2018-06-13';");

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
        builder.append(ATUALIZADO);
        builder.append(Types.CHAR);
        builder.append(" DAFAULT '1.5';");

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
        builder.append(MINIMOITENS);
        builder.append(Types.INTEIRO);
        builder.append(" DAFAULT '1';");


        stmt = builder.toString();
        return stmt;
    }
}
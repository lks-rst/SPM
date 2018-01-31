package br.com.sulpassomobile.sulpasso.sulpassomobile.util.Enumarations;

/**
 * Created by Lucas on 04/11/2016 - 10:49 as part of the project SulpassoMobile.
 */
public enum Tabelas
{
    DEVOLUCAO(0), COMISSAO(1), METAS(8), CTAS_RECEBER(11), MENSAGENS(17), ATIVIDADE(27), CORTE(44),
    STATUS(45), APLICACAO(48), CADASTRO_EMPRESA(49), MENSAGENS_MSG(99),

    OLHO_IMPOSTOS(50), CIDADE(9), BANCO(18), TRANSPORTADORA(51),

    MOTIVOS(2), PRE_PEDIDO(3), PRE_PEDIDO_CD(4), CLIENTES(5), SALDO_FLEX(6), TIPO_VENDA(7),
    TIPOLOGIA(10), VALIDADE(12), PRODUTO(13), MIX(14), PROMOCOES(15), GRAVOSOS(16), GRUPO(19),
    NATUREZA(20), KIT(22), TOTALIZADORES(23), TABELA_CLIENTE(25), PRAZOS(28), TABELA_PRECOS(33),
    ESTOQUE(43), DESC_GRUPO(46), DESC_CAMP(47), RESTRICAO_CLIENTE(69), RESTRICAO_GRUPO(90),
    INVALIDO(-1);

    

    private int value;

    private Tabelas(int value) { this.value = value; }

    public int getValue() { return this.value; }

    public static Tabelas getTipoFromInt(int valor)
    {
        switch (valor)
        {
            case 0 :
                return DEVOLUCAO;
            case 1 :
                return COMISSAO;
            case 2 :
                return MOTIVOS;
            case 3 :
                return PRE_PEDIDO;
            case 4 :
                return PRE_PEDIDO_CD;
            case 5 :
                return CLIENTES;
            case 6 :
                return SALDO_FLEX;
            case 7 :
                return TIPO_VENDA;
            case 8 :
                return METAS;
            case 9 :
                return CIDADE;
            case 10 :
                return TIPOLOGIA;
            case 11 :
                return CTAS_RECEBER;
            case 12 :
                return VALIDADE;
            case 13 :
                return PRODUTO;
            case 14 :
                return MIX;
            case 15 :
                return PROMOCOES;
            case 16 :
                return GRAVOSOS;
            case 17 :
                return MENSAGENS;
            case 18 :
                return BANCO;
            case 19 :
                return GRUPO;
            case 20 :
                return NATUREZA;
            case 22 :
                return KIT;
            case 23 :
                return TOTALIZADORES;
            case 25 :
                return TABELA_CLIENTE;
            case 27 :
                return ATIVIDADE;
            case 28 :
                return PRAZOS;
            case 33 :
                return TABELA_PRECOS;
            case 43 :
                return ESTOQUE;
            case 44 :
                return CORTE;
            case 45 :
                return STATUS;
            case 46 :
                return DESC_GRUPO;
            case 47 :
                return DESC_CAMP;
            case 48 :
                return APLICACAO;
            case 49 :
                return CADASTRO_EMPRESA;
            case 50 :
                return OLHO_IMPOSTOS;
            case 51 :
                return TRANSPORTADORA;
            case 69 :
                return RESTRICAO_CLIENTE;
            case 90 :
                return RESTRICAO_GRUPO;
            case 99 :
                return MENSAGENS_MSG;
            default:
                return INVALIDO;
            /*
            TODO: Rever esse default esta retornando um valor valido isso não pode ocorrer
             */
        }
    }
}

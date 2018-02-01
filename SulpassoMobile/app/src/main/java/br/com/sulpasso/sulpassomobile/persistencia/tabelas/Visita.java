package br.com.sulpasso.sulpassomobile.persistencia.tabelas;

import br.com.sulpasso.sulpassomobile.persistencia.database.Types;

/**
 * Created by Lucas on 16/11/2017 - 10:41 as part of the project SulpassoMobile.
 */
public class Visita
{
    public static final String TABELA = "Visitas";
    public static final String PEDIDO = "Pedido";
    public static final String CLIENTE = "Cliente";
    public static final String MOT = "Motivo";
    public static final String DATA = "Data";
    public static final String HORA = "Hora";
    public static final String DIA = "Dia";
    public static final String VDA = "Venda";
    public static final String ENVIADO = "Enviado";


    public static String CriarTabela()
    {
        String stmt;
        StringBuilder builder = new StringBuilder();

        builder.append("Create table ");
        builder.append(TABELA);
        builder.append("(");
        builder.append(PEDIDO);
        builder.append(Types.INTEIRO);
        builder.append(", ");
        builder.append(CLIENTE);
        builder.append(Types.INTEIRO);
        builder.append(", ");
        builder.append(MOT);
        builder.append(Types.INTEIRO);
        builder.append(", ");
        builder.append(DATA);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(HORA);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(DIA);
        builder.append(Types.INTEIRO);
        builder.append(", ");
        builder.append(VDA);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(ENVIADO);
        builder.append(Types.CHAR);
        builder.append(", ");
        builder.append(Types.PRIMARY_KEY_MULT);
        builder.append(CLIENTE);
        builder.append(", ");
        builder.append(PEDIDO);
        builder.append("));");

        stmt = builder.toString();
        return stmt;
    }
}

package br.com.sulpassomobile.sulpasso.sulpassomobile.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Estoque;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Preco;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.EstoqueDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrecoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;

/*
	Todo: verificar data e hora do sistema;

*/
	
public class Inicial extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        inserirDadosHardCoded();
        listDataHardCoded();
        ((TextView) findViewById(R.id.vendas)).setText(this.listDataHardCoded());
    }

    public void abrirVendas(View v)
    {
        Intent i = new Intent(getApplicationContext(), Pedido.class);
        startActivity(i);
    }

    public void listarVendas(View v)
    {
        ((TextView) findViewById(R.id.vendas)).setText(this.listDataHardCoded());
    }

    private String listDataHardCoded()
    {
        String retorno = "";
        ClienteDataAccess cda = new ClienteDataAccess(getApplicationContext());
        ItemDataAccess ida = new ItemDataAccess(getApplicationContext());
        VendaDataAccess vda = new VendaDataAccess(getApplicationContext());

        NaturezaDataAccess nda = new NaturezaDataAccess(getApplicationContext());
        PrazoDataAccess pda = new PrazoDataAccess(getApplicationContext());
        PrecoDataAccess tda = new PrecoDataAccess(getApplicationContext());
        EstoqueDataAccess eda = new EstoqueDataAccess(getApplicationContext());

        List<Cliente> clientes;
        List<Item> itens;
        List<Venda> vendas;

        List<Natureza> naturezas;
        List<Prazo> prazos;
        List<Preco> precos;
        List<Estoque> estoques;

        try {
            clientes = cda.getAll();
            System.out.println(clientes.toString());
            /*for(Cliente c : clientes) { System.out.println(c.toString()); }*/
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            itens = ida.getAll();
            System.out.println(itens.toString());
            /*for(Item i : itens) { System.out.println(i.toString()); }*/
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            vendas = vda.getAll();

            for(Venda v : vendas) { retorno += v.toDisplay() + "\n";System.out.println(v.toString()); }
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            naturezas = nda.buscarTodos();
            System.out.println(naturezas.toString());
            /*for(Natureza v : naturezas) { System.out.println(v.toString()); }*/
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            prazos = pda.buscarTodos();
            System.out.println(prazos.toString());
            /*for(Prazo v : prazos) { System.out.println(v.toString()); }*/
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            precos = tda.buscarTodos();
            System.out.println(precos.toString());
            /*for(Preco v : precos) { System.out.println(v.toString()); }*/
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        try {
            estoques = eda.buscarTodos();

            for(Estoque v : estoques) { System.out.println(v.toString()); }
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        return retorno;
    }

    private void inserirDadosHardCoded()
    {
        ClienteDataAccess cda = new ClienteDataAccess(getApplicationContext());
        NaturezaDataAccess nda = new NaturezaDataAccess(getApplicationContext());
        PrazoDataAccess pda = new PrazoDataAccess(getApplicationContext());

        ItemDataAccess ida = new ItemDataAccess(getApplicationContext());
        PrecoDataAccess tda = new PrecoDataAccess(getApplicationContext());
        EstoqueDataAccess eda = new EstoqueDataAccess(getApplicationContext());

        String cli1 = "050002852ILVA T. GONCALVES JAHN                                                     0000WILSON                   00/00/0000AUREA DE OLIVEIRA, 222                       VICTOR ISLER        00121  005154 3313-2304                  00010755570001540050104NCD        SS      620000000061000000000                    000815000000000000000714000000000000/00/00000030000000000000S091/0184410 99020-390xml.jahn@yahoo.com.br                             001J10000000000N222                                                                                                                                                                                                                                             ";
        String cli2 = "050011827SUPERMERCADO NITEROI LTDA                                                  00001                        00/00/0000RUA NITEROI, 561                             VALINHOS            00114  005154 33272185    1              00934697810001490090104NCDFS              636100000000000000300                    000357300000032200000589000000000000/00/00000030000000000000S091/0131716 99042-540novo.milenio@annex.com.br                         001J10000000000N561                                                                                                                                                                                                                                             ";
        String cli3 = "050031208ADEMIR LOPES DE LIMA                         COMERCIAL LIMA                0000SALETE                   23/03/0000R.VITORIO TREVISAN, 280                      OPERARIO            00107  000754 33561161    99171392       00007572700001410090502N                  000000000000000000000                    000000000000000000000000000000000000/00/00000015000000000000S171/0003798 95370-000decioba@outonet-.com.br                           001J43327249091N280                                                                                                                                                                                                                                             ";
        String cli4 = "050070130SUPER LUVISA LTDA                            COMPREBEM                     0000TIM                      00/00/0000AV.PRES.VARGAS 1121                          VL.RODRIGUES        00128  00519189-2162-                    00057838440001700080104NCD                610000000000000000000                    003716540000000000000129000000000000/00/00000100000000000000S091/0251932 99070-000nf-e@superluvisa.com.br                           001J00711689075N1121  FABRICIO(CONTADOR)-33135192,ESTAMOS AGUARDANDO A MUDANCA NO SEFAZ DO ENDERECO PARA O IGAI.                                                                                                                                                ";
        String cli5 = "050140406RESTAURANTE GIGANTAO LTDA                    MASTER GRILL                  0000ROMANI                   24/08/2016RUA MORON 1559                               CENTRO              00107  0051054  3046 55   9993 5556      00108391820001420030401N                  000000000000000000200                    000000000000000000000000000000000000/00/00000020000000000000N091/0300801 99010-033franciele.razao@via rs.net                        001J20407270000N1559                                                                                                                                                                                                                                            ";
        String cli6 = "050100650COTRIJAL COOP.AGROP.INDL                     COTRIJAL                      0000JAIR/ARTHUR              00/00/0000AV.DR.VALDOMIRO GRAEFF 813                   CENTRO              00128  012354-33322500    00000          00914955490041470080102NCD        SS      640000000061000000000                    001092360000000000002619000000000000/00/00000300000000000000S0810038625  99470-000nfe@cotrijal.com.br                               001J10000000000N813                                                                                                                                                                                                                                             ";
        String cli7 = "050089826MACARI & MACARI LTDA                         SUPERMERCADO MACARI           0000PAULINO/ELIANE           00/00/0000AV.BENJAMIN CONSTANT 2575                    CENTRO              00114  00375433581579                    00086751440001500080103NCD                630000000000000000000                    001241440000116400002407000000000000/00/00000040000000000000S0710056540  95300-000maccarilagoa@hotmail.com                          001J51634260953N2575                                                                                                                                                                                                                                            ";
        String cli8 = "050080500VALENTE & DI DOMENICO LTDA                   SUPERM.VALENTE                0000MANINHO                  28/09/1963AV.JULIO BORELLA 2211                        CENTRO              00107  004054 3342 6262                  00074923180001860050104NCD        SS      630000000061000000000                    001276810000127800001515000000000000/00/00000040000000000000S075/0047364 99150-000nfe@valentesuper.com.br                           001J19175820072N2211                                                                                                                                                                                                                                            ";
        String cli9 = "050067504DOUGLAS ANDRE COAN & CIA LTDA                TRIANGULO BELA VISTA          0000DOUGLAS/SANDRA           04/10/1974RUA ANTONIO BUSNELLO, 199 S.B                BELA VISTA          00107  002154 35193635    54 96766463    00053138840001580080504NCDFS    LL        636100006100000000000                    000588980000000000000060000000000000/00/00000020000000000000S039/0120723 99700-000douglastriangulo@hotmail.com                      001J77767594068N199   PONTUAL-100%.MAIOR CREDITO-2700.MAIOR FATURA-5400                             FORN-COCA-COLA,BRF                                                                                                                                          ";
        String cli10 = "050041208ADEMIR LOPES DE LIMA                         COMERCIAL LIMA                0000SALETE                   23/03/0000R.VITORIO TREVISAN, 280                      OPERARIO            00107  000754 33561161    99171392       00007572700001410090502N                  000000000000000000000                    000000000000000000000000000000000000/00/00000015000000000000S171/0003798 95370-000decioba@outonet-.com.br                           001J43327249091N280                                                                                                                                                                                                                                             ";
        try{ cda.inserir(cli1); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ cda.inserir(cli2); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ cda.inserir(cli3); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ cda.inserir(cli4); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ cda.inserir(cli5); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ cda.inserir(cli6); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ cda.inserir(cli7); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ cda.inserir(cli8); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ cda.inserir(cli9); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ cda.inserir(cli10); }
        catch (GenercicException ge) { ge.printStackTrace(); }

        String n1 = "20001VENDA A PRAZO       DV02000";
        String n2 = "20002VENDA A VISTA       AV02000";
        String n3 = "20003VENDA A CHEQUE      NV02000";
        String n4 = "20004SEM FRETE           DV10000";
        String n5 = "20012BONIFICACAO         0 00100";
        try { nda.inserir(n1); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { nda.inserir(n2); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { nda.inserir(n3); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { nda.inserir(n4); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { nda.inserir(n5); }
        catch (GenercicException e) { e.printStackTrace(); }

        String p1 = "2800001000-000-000-000";
        String p2 = "2800501005-000-000-000";
        String p3 = "2800701007-000-000-000";
        String p4 = "2801302007-014-000-000";
        String p5 = "2801402014-000-000-000";
        String p6 = "2801503007-014-021-000";
        String p7 = "2801603014-021-000-000";
        String p8 = "2801703021-028-000-000";
        String p9 = "2802103021-000-000-000";
        String p10 = "2802204014-021-028-000";
        String p11 = "2802804028-000-000-000";
        try { pda.inserir(p1); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { pda.inserir(p2); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { pda.inserir(p3); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { pda.inserir(p4); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { pda.inserir(p5); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { pda.inserir(p6); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { pda.inserir(p7); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { pda.inserir(p8); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { pda.inserir(p9); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { pda.inserir(p10); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { pda.inserir(p11); }
        catch (GenercicException e) { e.printStackTrace(); }

        String item1 = "13000001919        PO REFRESCO FRUTAU LARANJA    15x25G AUREA  001099001DPDP0000000001010000000000039007896180783283  S0001700100000000210690100000000003045000000000000010165010760";
        String item2 = "130000353353       CACAU EM PO 100% MELKEN 200G  HARALD        001007001UNUN0000000001010000002000020007897077808294  S0001700100000000180500000000000003045000000000000010165010760";
        String item3 = "130000701          GORDURA VEGETAL FRY 810 15KG  COAMO 15 KG   001003001BDBD0000000001010000000000156207896279600729  S9000700100000000151620000000483501945000000000000010165010760";
        String item4 = "1300010581058      POP CORN COBER YOKI MANTEIGA  30 X 130G YOKI001099001UNUN0000000003010000001300047807891095015306  S0000000000000000200819000000000001345000000000000010165010760";
        String item5 = "130001732160012    ACUCAR CRISTAL 5KG ALTO ALEGREFD 6 X 5      001004001UNUN0000000006010000050000301807896508200010  S0000000000000000170199000000073500220000000000000060000060000";
        String item6 = "13000206299956     AFFETTO TRUFA PECCIN 448GR    8 X 448 GR    001018001DPDP0000000001010000000000044707896306616556  S0000000000000000190532000000000001345000000000000010165010760";
        String item7 = "130002399B3        BISC AMANT C/ GOIABA 24X350G  GOIABINHA     001001004UNUN0000000001010000003500091407898210380905  S0001200100000000190531000000035202745003352001200010165010760";
        String item8 = "13000306999328     BALA TRI 2 FRUTAS SORTIDO 600GPECCIN 8 X 600001018002PCPC0000000001010000000000054307896306612640  S0000000000000000170490200000000001345000000000000010165010760";
        String item9 = "130003727054       TEMP P GALETO 1KG SABOR DO VALSABOR DO VALE 001099001UNUN0000000002010000010000101007898939850369  S0000000000000000210390210000000001345000000000000010165010760";
        String item10 = "13000546085040     MAIONESE HEINZ 215 GR         CX C/8        001011007UNUN0000000002010000002100019207896102594041  S0000000000000000210390110000000001345000000000000010165010760";
        try{ ida.inserir(item1); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ ida.inserir(item2); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ ida.inserir(item3); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ ida.inserir(item4); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ ida.inserir(item5); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ ida.inserir(item6); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ ida.inserir(item7); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ ida.inserir(item8); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ ida.inserir(item9); }
        catch (GenercicException ge) { ge.printStackTrace(); }
        try{ ida.inserir(item10); }
        catch (GenercicException ge) { ge.printStackTrace(); }

        String pr1 = "33000001902000728";
        String pr2 = "33000001903000735";
        String pr3 = "33000001904000735";
        String pr4 = "33000001905000735";
        String pr5 = "33000035301000969";
        String pr6 = "33000035302000979";
        String pr7 = "33000035303000989";
        String pr8 = "33000035304000989";
        String pr9 = "33000070101007688";
        String pr10 = "33000070102007767";
        String pr11 = "33000070103007845";
        String pr12 = "33000070104007845";
        String pr13 = "33000105801000265";
        String pr14 = "33000105802000267";
        String pr15 = "33000105803000270";
        String pr16 = "33000105804000270";
        String pr17 = "33000173201000865";
        String pr18 = "33000173202000874";
        String pr19 = "33000173203000883";
        String pr20 = "33000206201001250";
        String pr21 = "33000206202001262";
        String pr22 = "33000206203001275";
        String pr23 = "33000239901000423";
        String pr24 = "33000239902000428";
        String pr25 = "33000306901000711";
        String pr26 = "33000306902000718";
        String pr27 = "33000372701000183";
        String pr28 = "33000372702000185";
        String pr29 = "33000372703000187";
        String pr30 = "33000546001000783";
        String pr31 = "33000546002000791";
        String pr32 = "33000546003000799";
        String pr33 = "33000546004000799";
        String pr34 = "33000546005000799";
        String pr35 = "33000546006000799";
        String pr36 = "33000546007000799";
        String pr37 = "33000546008000799";
        String pr38 = "33000546009000799";
        String pr39 = "33000546010000799";
        String pr40 = "33000546011000799";
        String pr41 = "33000546012000799";
        String pr42 = "33000546019000652";
        try { tda.inserir(pr1); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr2); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr3); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr4); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr5); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr6); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr7); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr8); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr9); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr10); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr11); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr12); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr13); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr14); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr15); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr16); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr17); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr18); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr19); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr20); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr21); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr22); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr23); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr24); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr25); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr26); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr27); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr28); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr29); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr30); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr31); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr32); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr33); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr34); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr35); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr36); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr37); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr38); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr39); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr40); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr41); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { tda.inserir(pr42); }
        catch (GenercicException e) { e.printStackTrace(); }

        String e1 = "430000019 000000004000001";
        String e2 = "430000701 000000040900001";
        String e3 = "430001732 000000207000001";
        String e4 = "430002062 000000009200001";
        String e5 = "430002399 000000193100001";
        String e6 = "430003069 000000004000001";
        String e7 = "430003727 000000017700001";
        String e8 = "430005460 000000000100001";
        try { eda.inserir(e1); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { eda.inserir(e2); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { eda.inserir(e3); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { eda.inserir(e4); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { eda.inserir(e5); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { eda.inserir(e6); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { eda.inserir(e7); }
        catch (GenercicException e) { e.printStackTrace(); }
        try { eda.inserir(e8); }
        catch (GenercicException e) { e.printStackTrace(); }
    }
}
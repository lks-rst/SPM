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
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.EstoqueDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrecoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;

/*
	Todo: verificar data e hora do sistema antes de abrir os pedidos;

    Todo: Criar as classes referentes a cidade (modelo, dataAccess)
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

        PromocaoDataAccess proda = new PromocaoDataAccess(getApplicationContext());

        List<Cliente> clientes;
        List<Item> itens;
        List<Venda> vendas;

        List<Natureza> naturezas;
        List<Prazo> prazos;
        List<Preco> precos;
        List<Estoque> estoques;

        List<Promocao> promocoes;

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

        try {
            promocoes = proda.buscarTodos();

            for(Promocao v : promocoes) { System.out.println(v.toString()); }
        }
        catch (GenercicException ge) { ge.printStackTrace(); }

        return retorno;
    }

    private void inserirDadosHardCoded() {
        ClienteDataAccess cda = new ClienteDataAccess(getApplicationContext());
        NaturezaDataAccess nda = new NaturezaDataAccess(getApplicationContext());
        PrazoDataAccess pda = new PrazoDataAccess(getApplicationContext());

        ItemDataAccess ida = new ItemDataAccess(getApplicationContext());
        PrecoDataAccess tda = new PrecoDataAccess(getApplicationContext());
        EstoqueDataAccess eda = new EstoqueDataAccess(getApplicationContext());

        PromocaoDataAccess proda = new PromocaoDataAccess(getApplicationContext());

        String[] cli1 =
        {
            "050055450ALEXANDRE CARRET DE AZEVEDO ME               ICE CIA                       0000ALEXANDRE                00/00/0000RUA ANDRADE NEVES, 1214                      CENTRO              00200D 001153 3302.8120   53 9116.2781   00221411420001010020401N                  000000000000000000000                    000000000000000000000000000000000000/00/00000000000000000000S0930455673  96020-080                                                  001J00161515002N1214     ",
            "050055972LUCIMAR COELHO LATORRE                       SHEKINAH                      0002JANET LATORRE            12/05/2015MORADA DA COLINA 23                          FRAGATA (GOTUZ      00107B 00493275-1894                     00153229050001550090502N                  000000000000000000200                    000000000000000000000000000000000000/00/00000000000000000000N0930441249  96001-970janetlatorrehotmail.com                           001J62015559000N23       ",
            "050056227JOSEANE BASTOS DA SILVA                      SHULZ ALIMENTOS               0002JOSEANE DA SILVA         02/06/2015BR 293 KM 14 2150                            PARK FRAGATA        00114  000953 8439-57 6                  00193214930001900090502S                  000000000000000000300                    000460660000000000000000000000000015/09/20160011300000000000SISENTO      96160-000joseane.almeida13@gmail.com                       001J00320992071N2150     ",
            "050056413JOSE ALEXANDRE ROSCHILDT AZOCAR ME           MERCADO MODELO E EST          0001ALEXANDRE                24/06/2015PRACA PIRATININO DE ALMEIDA 12               CENTRO              00200  0049539131-1474                   00120321340001730020502S                  000000000000000000200                    000243460000000000000000000000000016/09/20160009740000000000N0930455894  96015-290zeazocar@gmail.com                                001J61052108091N12       ",
            "050056430J MARQUES CRUZ                               ACOUGUE BELA CARNE            0001CRISTIANO LEITE          30/06/2015AV JUCELINO KUBICHEK DE OLIV43               JARDIM AMERICA      00200  0009538409-2219                   00222172480001410020503S                  000000000000000000400                    001208920000000000000000000000000015/09/20160053050000000000N2350016913  96160-000freitas_turismo@outlook.com                       001J11111111111N43       ",
            "050056650NELSON NORNBERG                              RESTAURANTE NORENBER          0001NELSON                   23/07/2015MARECHAL DEODORO 705                         CENTRO              00200  0049538115-4505                   00915472650001600020401S                  000000000000000000200                    000085340000000000000000000000000013/09/20160000725000000000N930166221   96020-220                                                  001J11111111111N705      ",
            "050056871JORGE ALBERTO DIAS BOTELHO ME                KROC PAO                      0001JORGE BOTELHO OU         25/08/2015EUCLIDES LOPES VASCONCELOS 130               PARK FRAGATA        00200  0009533274-4601                   00156074780001510020501S                  000000000000000000200                    000111230000000000000000000000000026/08/20160006480000000000S2350016786  96160-000pedreiradiasbotelho@gmail.com                     001J11111111111N1300     ",
            "050056995CARINA BANDEIRA DA LUZ                       PADARIA VITORIA               0001CARINA OU JUNIOR         02/09/2015ERNANI DA ROSA 657                           JARDIM AMERICA      00114  0009538446-9332                   00212156470001000090501S                  000000000000000000400                    000273670000000000000000000000000008/09/20160005420000000000N2350017057  96160-000joao.junior049@gmail.com                          001J01407594095N657      ",
            "050057738DIONATAN DE AVILA SOUZA                      CASA DE CARNES E CON          0003DINATAN SOUZA            10/11/2015EDIMUNDO PERES = ESQUINA AV NA               CENTRO              00114  0009533275-2028                   00207649670001570090503S                  000000000000000000300                    000686010000000000000000000000000011/08/20160023990000000000NISENTO      96160-000dionatansouza138@gmail.com                        001J01656957060N2150     ",
            "050057916ATAIDES GONCALVES ME                         RESTAURANTE PRIMAVER          0001TAIDE                    30/11/2015DUQUE DE CAXIAS 348                          FRAGATA             00107  004932785240                      00234556650001950090401S                  000000000000000000200                    000181370000000000000000000000000016/09/20160013851000000000N0930458770  96030-001                                                  001J06580858087N348      ",
            "050058033JARDEL SILVEIRA DOMINGUES                    CASA DE CARNES JD             0002JARDEL                   09/12/2015CIDADE DE RIO GRANDE 286                     JARDIM AMERICA      00114  0009538457-4505                   00166381380001500090503S                  000000000000000000400                    001295780000000000000000000000000015/09/20160014250000000000N2350017383  96160-000                                                  001J92276709049N286      ",
            "050058041V&P COMERCIO DE ALIMENTOS LTDA               BOCATTINO                     0000LEANDRO                  01/11/1111AV FERREIRA VIANA  1526                      AREAL               00114  0049533026-4453    (53)8469-2449  00237310760001920090401S                  000000000000000000100                    000429570000000000000000000000000016/09/20160042650000000000N0930458990  96085-000                                                  001J21111111111N 1526    ",
            "050058114SHERLOCK RESTAURANTE LTDA                    CHERLOCK PUB                  0397ANDERSON OU ELIN         14/12/20153 DE MAIO 866                                CENTRO              00107  0049538136-0012    (53)8419-0604  00237516010001310090401S                  000000000000000000200                    000287830000000000000000000000000013/09/20160002250000000000N0930458958  96010-620cherlockpub@outlook.com.br                        001J01201244080N866      ",
            "050058378SERGIO RENATO MEDEIROS GUIMARAES 35332255004 MERCADO E CASA DE CA          0003SERGIO MEDEIROS          05/01/2016RUA JOAO PEREZ, 340                          CENTRO              00107  00093275-1894                     00127024430001030090503S                  000000000000000000400                    000662100000000000000000000000000015/09/20160000000000000000NISENTO      96160-000renatomedeiros49@yahoo.com.br                     001J35332255004N340      ",
            "050058521MARINES MACIEL NUNES                         JK MACIEL                     0003JADER                    19/01/2016JOSE LUIZ ESLABAO 162                        PARQUE FRAGATA      00107B 0009538403-0784                   00237474660001500090502N                  000000000000000000300                    000000000000000000000000000000000000/00/00000000000000000000SISENTO      96160-000                                                  001J76777855068N162      ",
            "050058556MOISES RODRIGUES VERGARA ME                  MINE MERCADO VITORIA          0002CLAUDETE                 19/01/2016FRANCISCO LODOLINO DA CUNHA 75               CASA BOM            00114  0009533275-2008                   00215389200001380090502S                  000000000000000000300                    000311140000000000000000000000000008/09/20160000000000000000N2350016751  96160-000                                                  001J00000000001N75       ",
            "050058572ANGELITA GARCEZ DE LIMA                      MINE MERCADO FAMILIA          0001ANGELITA GARCEZ          20/01/2016JOAQUIM MACIEL 527                           JARDIM AMERICA      00200  0009533275-5676                   00236321150001020020502S                  000000000000000000400                    000337640000000000000000000000000029/07/20160000000000000000NISENTO      96160-000angelitagarcez@outlook.com                        001J94279519072N527      ",
            "050058815RESTAURANTE LAS ANDRADAS LTDA                RESTAURANTE ANDRADAS          0021ROGERIO MILECH           15/02/2016ANDRADES NEVES 3871                          CENTRO              00114B 00495381182100                    00234313410001170090401N                  000000000000000000200                    000000000000000000000000000000000000/00/00000050000000000000N0930458400  96020-080contato@bufftnutribem.com.br                      001J11111111111N3871     ",
            "050058831ALEXANDRE TEIXEIRA DE MELO                   CIA DA CHURRASCO              0004ALEXANDRE MELO           16/02/2016EDMUNDO PEREZ 2150                           CENTRO              00114  000953  3275-202                  00235462400001910090503S                  000000000000000000300                    000957360000000000000000000000000031/08/20160000000000000000NISENTO      96160-000acougueciadochurrasco@gmail.com                   001J11111111111N2150     ",
            "050059218SANDRO POLLNOW CASTRO                        RESTAURANTE DA PRODU          0000VANESSA                  06/03/2016BR 392 KM 76 12501                           FRAGATA             00107  0049533271-7085    (53)8435-0160  00112466840001220090401S                  000000000000000000200                    000610540000000000000000000000000002/09/20160000000000000000N0930416228  96050-500restaurantedaproducao@gmail.com                   001J02582696005N12501    ",
            "050059340JUSSARA GONCALVES                            COMERCIAL JJ                  0004JUSSARA GONCALVE         14/03/2016AV PERIMETRAL 2157                           CENTRO              00200  00275384161635                    00238532590001800020502N                  000000000000000000200                    000264100000000000000000000000000008/07/20160000000000000000SISENTO      96490-000                                                  001J01486593062N2157     "
        };

        for (int i = 0; i < cli1.length; i++)
        {
            try { cda.inserir(cli1[i]); }
            catch (GenercicException ge) { ge.printStackTrace(); }
        }

        String[] naturezas =
        {
            "20001VENDA A PRAZO       DV13000000",
            "20002VENDA A VISTA       AV06500   ",
            "20003VENDA A VISTA-CHEQUENV06500   "
        };

        for (int i = 0; i < naturezas.length; i++)
        {
            try { nda.inserir(naturezas[i]); }
            catch (GenercicException e) { e.printStackTrace(); }
        }

        String[] prazos =
        {
            "2800001000-000-000-000",
            "2800701007-000-000-000",
            "2801402014-000-000-000",
            "2801702017-000-000-000",
            "2802103021-000-000-000",
            "2802804028-000-000-000"
        };

        for (int i = 0; i < naturezas.length; i++)
        {
            try { pda.inserir(prazos[i]); }
            catch (GenercicException e) { e.printStackTrace(); }
        }

        String[] item1 =
        {
            "130006300PCOL      PRESUNTO S/CAPA LEBON 2X3.50KGCX C/ 2X3.50KG001001001KGKG0001000007010000010000070007896589554095  S0000000000000000160249000000078601263000000000000010165010760",
            "130008060LINGV     LINGUICA SUINA CONG. VIVAZ 5KGCX C/ 2X5KG   013002004KGUN0000000001050000050000100007898957199235  S0000000000000000160100000000064101163000000000000010165010760",
            "130008079LINTV     LINGUICA TOSCANA CONG VIVAZ 5KCX C/ 2X5 KG  013002004KGUN0000000001050000050000100007898957199228  S0000000000000000160100000000073201163000000000000010165010760",
            "130008109PI4QV     PIZZA 4 QUEIJOS VIVAZ 450G    CX C/ 12X450G 013002001UNUN0000000004010000004500054007898957199020  S0000000000000000190590900000069000238000000000000060000060000",
            "130008117PIFRV     PIZZA FRANGO VIVAZ 450        CX C/ 12X450G 013002001UNUN0000000004010000004500054007898957199037  S0000000000000000190590900000063301163000000000000010165010760",
            "130008125PICLV     PIZZA CALABRESA VIVAZ 450G    CX C/ 12X450G 013002001UNUN0000000004010000004500054007898957199044  S0000000000000000190590900000057901163000000000000010165010760",
            "130008133PIPOV     PIZZA PORTUGUESA VIVAZ 450G   CX C/ 12X450G 013002001UNUN0000000004010000004500054007898957199051  S0000000000000000190590900000061101163000000000000010165010760",
            "130008141PICHV     PIZZA CHOCOLATE VIVAZ 450     CX C/ 12X450G 013002001UNUN0000000004010000004500054007898957199068  S0000000000000000190590900000071101163000000000000010165010760",
            "130008150LAFRAV    LASANHA DE FRANGO VIVAZ 500G  CX C/ 10X500G 013002002UNUN0000000005010000005000050007898957199075  S0000000000000000190220000000059200238000000000000060000060000",
            "130008168LABOLV    LASANHA BOLONHESA VIVAZ 500G  CX C/ 10X500G 013002002UNUN0000000005010000005000050007898957199082  S0000000000000000190220000000061600238000000000000060000060000",
            "130008184LA4QUV    LASANHA 4 QUEIJOS VIVAZ 500G  CX C/ 10X500G 013002002UNUN0000000005010000005000050007898957199099  S0000000000000000190220000000065300238000000000000060000060000",
            "130024449MATRAV    MANDIOCA TRADIC. VIVAZ 500G   CX C/ 12X500G 013002003UNUN0000000012010000005000060007898957199372  S0001800100000000071410000000018002038000000000000060000060000",
            "130024457MAPALV    MANDIOCA PALITO VIVAZ 500G    CX C/ 12X500G 013002003UNUN0000000012010000005000060007898957199389  S0001800100000000071410000000022402038000000000000060000060000",
            "130038660MDQ       MARG.QUALY S/SAL 24X250 GR                  001002001CXCX0000000001010000060000060000000000038660  S0000000000000000151710000000460700338000000000000060000060000",
            "130038679MQU       MARG.QUALY S/SAL 12X500 GR                  001002001CXCX0000000001010000060000060000000000038679  S0000000000000000151710000000459400338000000000000060000060000",
            "130039373MDQS      MARG.QUALY C/SAL 24X250 GR                  001002001CXCX0000000001010000060000060000000000039373  N0000000000000000151710000000460500238000000000000060000060000",
            "130039381MQUS      MARG.QUALY C/SAL 12X500 GR                  001002001CXCX0000000001010000060000060000000000039381  N0000000000000000151710000000436100238000000000000060000060000",
            "130041874ALMBG     ALMONDEGA BOVINA 240X25G                    001001001KGCX0000000001060000060000060000000000041874  S0000000000000000160250000000097201263000000000000010165010760",
            "130045179BAKV      BACON MANTA VIVAZ             CX C/ 10 KG   013001001KGKG0001000005010000010000100007893000526044  S0000000000000000021019000000092101263000000000000010165010760",
            "130047830PFFIF     FILE PEITO FGO IQF LEBON 12X1               006001001KGUN0000000012010000010000120007897408104767  S0000000000000000020714000000073600238000000000000060000060000",
            "130048313AD4115    SOBREPALETA SUINA CONGELADA                 006002001KGCX0000000001150000150000150007898612311637  S1000000000000000020329000000085900218000000000000060000060000",
            "130048356AD4114    PERNIL SUINO CONG S/O ADELLE                006002001KGKG0001000020010000010000200007898612311330  S0000000000000000020329000000071900218000000000000060000060000",
            "130050270MQUSL     MARG.QUALY LIGHT 12X500GR C/SA              001002001CXCX0000000001010000060000060000000000050270  S0000000000000000151710000000459500338000000000000060000060000",
            "130050814FCG       GALETO CONGELADO CX 10KG                    006001001KGCX0000000001100000100000100007893000621206  S0000000000000000020712000000063500338000000000000060000060000",
            "130085014GELSAB    GELATINA SEM SABOR 24G                      008003002UNUN0000000010010000000200007207898409952173  S0001800100000000210690290000013003113000000000000010165010760",
            "130256609AD4119    COSTELA SUINA TIRAS C/O ADELLE              006002001KGCX0000000001150000150000150007898612312061  S0000000000000000020329000000096001143000000000000010165010760",
            "130261262PFFB      FILE PEITO FRANGO BAND. 12X1KG              006001001KGUN0000000012010000010000120000000000261262  S0000000000000000020714000000073800238000000000000060000060000",
            "130270652MQDSL     MARG.QUALY LIGHT 24X250G C/SAL              001002001CXCX0000000001010000060000060000000000270652  S0000000000000000151710000000463100338000000000000060000060000",
            "130285218MTPN      MORTADELA C/TOUCINHO PC 3.50KGCX C/ 4X3.50KG001001001KGUN0000000002035000035000140000000000285218  S0000000000000000160100000000030201263000000000000010165010760",
            "130297038BBCAR     BEM BATATA CARINHAS 400G                    004002001UNUN0000000012010000004000096000000000297038  S0000000000000000200490000000033901143000000000000010165010760",
            "130300845AD4802    BISTECA SUINA CONG 2X5K ADELLE              006002001KGSC0000000001100000000000100000000000300845  S0000000000000000020329000000080800218000000000000060000060000",
            "130330990DRMGIF    COXINHA DA ASA IQF 1KG                      006001001KGUN0000000012010000010000120007897408104194  S0000000000000000020714000000070800238000000000000060000060000",
            "130342157LTOSP     LINGUICA TOSCANA LEBON 18X800GCX C/ 18X800G 001001001UNUN0000000018010000008000144007896589511777  S1000000000000000160100000000064601263000000000000010165010760",
            "130813699MB25      MAIS BATATA TRAD. 9MM 6X2.50KG              004001001KGCX0000000001150000150000150007898921567084  S9001800100000000200410000000033203063000000000000010165010760",
            "130812102MBTG      MAIS BATATA TRAD. 9MM 7X2.00KG              004001001KGCX0000000001140000140000140000000000812102  S9001800100000000200410000000031502963000000000000010165010760"
        };

        for (int i = 0; i < item1.length; i++)
        {
            try { ida.inserir(item1[i]); }
            catch (GenercicException ge) { ge.printStackTrace(); }
        }

        String[] pr1 =
        {
            "33034215701000915", "33034215702000915", "33034215703000915", "33034215704000915",
            "33034215783000759", "33033099004000879", "33033099001000879", "33033099002000879",
            "33033099003000879", "33030084501000979", "33030084502000979", "33030084503000979",
            "33030084504000979", "33029703801000489", "33029703802000489", "33029703803000489",
            "33029703804000489", "33028521801000559", "33028521802000559", "33028521803000559",
            "33028521804000559", "33028521850000499", "33027065201005280", "33027065202005280",
            "33027065203005280", "33027065204005280", "33027065283004782", "33026126201000858",
            "33026126202000858", "33026126203000858", "33026126204000858", "33025660901001129",
            "33025660902001129", "33025660903001129", "33025660904001129", "33008501401000244",
            "33008501402000244", "33008501403000244", "33008501404000244", "33008501480000254",
            "33008501482000254", "33005081401000779", "33005081402000779", "33005081403000779",
            "33005081404000779", "33005027001005280", "33005027002005280", "33005027003005280",
            "33005027004005280", "33005027083004782", "33004831301000998", "33004831302000998",
            "33004831303000998", "33004831304000998", "33004831301000998", "33004831302000998",
            "33004831303000998", "33004831304000998", "33004783001000899", "33004783002000899",
            "33004783003000899", "33004783004000899", "33004517901001169", "33004517902001169",
            "33004517903001169", "33004517904001169", "33004517983001329", "33004187401001329",
            "33004187402001329", "33004187403001329", "33004187404001329", "33004187483001099",
            "33003938101005280", "33003938102005280", "33003938103005280", "33003938104005280",
            "33003938183004782", "33003937301005280", "33003937302005280", "33003937303005280",
            "33003937304005280", "33003937383004782", "33003867901005280", "33003867902005280",
            "33003867903005280", "33003867904005280", "33003867983004782", "33000630001001229",
            "33000806001000879", "33000807901000995", "33000810901000748", "33000811701000748",
            "33000812501000748", "33000813301000748", "33000814101000788", "33000815001000685",
            "33000816801000685", "33000818401000685", "33002444901000315", "33002445701000345",
            "33003866001005280", "33000630002001229", "33000806002000879", "33000807902000995",
            "33000810902000748", "33000811702000748", "33000812502000748", "33000813302000748",
            "33000814102000788", "33000815002000685", "33000816802000685", "33000818402000685",
            "33002444902000315", "33002445702000345", "33003866002005280", "33000630003001229",
            "33000806003000879", "33000807903000995", "33000810903000748", "33000811703000748",
            "33000812503000748", "33000813303000748", "33000814103000788", "33000815003000685",
            "33000816803000685", "33000818403000685", "33002444903000315", "33002445703000345",
            "33003866003005280", "33000630004001229", "33000806004000879", "33000807904000995",
            "33000810904000748", "33000811704000748", "33000812504000748", "33000813304000748",
            "33000814104000788", "33000815004000685", "33000816804000685", "33000818404000685",
            "33002444904000315", "33002445704000345", "33003866004005280", "33003866083004782",
            "33000630083001079", "33000630050001090", "33081369901000649", "33081369902000649",
            "33081369903000649", "33081369904000649", "33081210201000649", "33081210202000649",
            "33081210203000649", "33081210204000649"
        };

        for(int i = 0; i < pr1.length; i++)
        {
            try { tda.inserir(pr1[i]); }
            catch (GenercicException e) { e.printStackTrace(); }
        }

        String[] e1 =
        {
            "430006300 000000092514001", "430008060 000000018500001", "430008079 000000032500001",
            "430008109 000000024300001", "430008117 000000077300001", "430008125 000000058200001",
            "430008133 000000019400001", "430008141 000000120600001", "430008150 000000013500001",
            "430008168 000000027500001", "430008184 000000004900001", "430024449 000000039600001",
            "430024457 000000013100001", "430038660 000000001200001", "430039373 000000003600001",
            "430039381 000000019100001", "430041874 000000044400001", "430045179 000000024687001",
            "430047830 000000012500001", "430048313 000000033000001", "430048356 000000075063001",
            "430050270 000000003100001", "430050814 000000092000001", "430085014 000000028500001",
            "430256609 000000009000001", "430261262 000000075600001", "430270652 000000002200001",
            "430285218 000000047600001", "430297038 000000185700001", "430300845 000000003000001",
            "430330990 000000046300001", "430342157 000000095400001", "430812102 000000572600001",
            "430813699 000000235500001"
        };

        for(int i = 0; i < e1.length; i++)
        {
            try { eda.inserir(e1[i]); }
            catch (GenercicException e) { e.printStackTrace(); }
        }

        String[] pro1 =
        {
            "150812102000050000000630",
            "150812102000100000000610",
            "150812102000150000000590",
            "150813699000050000000630",
            "150813699000100000000610",
            "150813699000150000000590"
        };

        for(int i = 0; i < pro1.length; i++)
        {
            try{ proda.inserir(pro1[i]); }
            catch (GenercicException ge) { ge.printStackTrace(); }
        }
    }
}
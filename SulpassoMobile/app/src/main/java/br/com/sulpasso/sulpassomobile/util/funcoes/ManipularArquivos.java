package br.com.sulpasso.sulpassomobile.util.funcoes;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import br.com.sulpasso.sulpassomobile.controle.ConsultaFoco;
import br.com.sulpasso.sulpassomobile.controle.Graficos;
import br.com.sulpasso.sulpassomobile.controle.PlanoVisitas;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.ClienteNovo;
import br.com.sulpasso.sulpassomobile.modelo.Configurador;
import br.com.sulpasso.sulpassomobile.modelo.Foco;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.modelo.Visita;
import br.com.sulpasso.sulpassomobile.persistencia.queries.AtividadeDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.BancoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.CampanhaGrupoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.CampanhaProdutoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.CidadeDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ContasReceberDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.CorteDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.CurvaAbcDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.DevolucaoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.EstoqueDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.GravososDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.GrupoBloqueadoClienteDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.GrupoBloqueadoNaturezaDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.GrupoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.KitDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.MensagemDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.MetaDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.MixDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.MotivosDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PrePedidoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PrePedidoDiretaDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PrecoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PrecosClientesDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.StatusDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.TipoVendaDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.TipologiaDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VisitaDataAccess;
import br.com.sulpasso.sulpassomobile.util.Enumarations.Tabelas;
import br.com.sulpasso.sulpassomobile.util.modelos.GenericItemFour;

/**
 * Created by Lucas on 04/11/2016 - 10:01 as part of the project SulpassoMobile.
 *
 */
public class ManipularArquivos
{
    private ArrayList<String> devolucoes = null;
    private ArrayList<String> motivos = null;
    private ArrayList<String> prePedidos = null;
    private ArrayList<String> prePedidosDiretas = null;
    private ArrayList<String> metas = null;
    private ArrayList<String> tipologias = null;
    private ArrayList<String> contasReceber = null;
    private ArrayList<String> mix = null;
    private ArrayList<String> gravosos = null;
    private ArrayList<String> mensagens = null;
    private ArrayList<String> mensagens_msg = null;
    private ArrayList<String> curvaAbc = null;
    private ArrayList<String> precosClientes = null;
    private ArrayList<String> atividades = null;
    private ArrayList<String> clientesRestricoes = null;
    private ArrayList<String> cortes = null;
    private ArrayList<String> status = null;
    private ArrayList<String> naturezasRestricoes = null;

    private ArrayList<String> clientes = null;
    private ArrayList<String> promocoes = null;
    private ArrayList<String> produtos = null;
    private ArrayList<String> cidades = null;
    private ArrayList<String> bancos = null;
    private ArrayList<String> tipos_vendas = null;
    private ArrayList<String> grupos = null;
    private ArrayList<String> naturezas = null;
    private ArrayList<String> kits = null;
    private ArrayList<String> prazos = null;
    private ArrayList<String> precos = null;
    private ArrayList<String> estoque = null;
    private ArrayList<String> desc_grupos = null;
    private ArrayList<String> desc_campanhas = null;
    private ArrayList<String> validade = null;
    private ArrayList<String> saldo = null;
    private ArrayList<String> comissao = null;

    private ArrayList<String> aplicacao = null;

    private ArrayList<String> errosIserir = null;

    private Context context;

    private String nomeArquivo;

    public ManipularArquivos(Context context)
    {
        this.context = context;
        this.errosIserir = new ArrayList<>();
    }

    public String getNomeArquivo() { return nomeArquivo; }

    public void setNomeArquivo(String nomeArquivo) { this.nomeArquivo = nomeArquivo; }

    public File AbrirArquivosExterno()
    {
        File f;

        int version;

        try { version = Integer.valueOf(Build.VERSION.SDK); }
        catch (Exception ev){ version = 3; }

        /*
        if(version >= 19)
        {
            f = new File("/storage/emulated/0/MobileVenda/", this.nomeArquivo);
        }
        else
        {
            f = new File(Environment.getExternalStorageDirectory() + "/MobileVenda/", this.nomeArquivo);
        }
        */
        f = new File(Environment.getExternalStorageDirectory() + "/MobileVenda/", this.nomeArquivo);

        return (f);
        //return (new File(Environment.getExternalStorageDirectory() + "/MobileVenda", "PW0006.257"));
        //return (new File(Environment.getExternalStorageDirectory() + "/MobileVenda", "PW0020.319"));
        //return (new File(Environment.getExternalStorageDirectory() + "/MobileVenda", "PW0015.492"));
    }

    public File AbrirArquivosConfiguracao()
    {
        int version;
        File f;

        try { version = Integer.valueOf(Build.VERSION.SDK); }
        catch (Exception ev){ version = 3; }

        if(version == 19)
        {
            f = new File("/storage/emulated/0/MobileVenda/", "configurador.json");
        }
        else
        {
            f = new File(Environment.getExternalStorageDirectory() + "/MobileVenda/", "configurador.json");
        }
        //return (new File(Environment.getExternalStorageDirectory() + "/MobileVenda", "PW0020.319"));
        return (f);
    }

    public void separarStrings() throws GenercicException
    {
        FileInputStream arquivo_leitura = null;
        Scanner strem_leitura = null;
        int codTabela = 0;

        try
        {
            arquivo_leitura = new FileInputStream(AbrirArquivosExterno());
            strem_leitura = new Scanner(arquivo_leitura);

            while (strem_leitura.hasNext())
            {
                String s = strem_leitura.nextLine();

                try
                {
                    if (s.substring(0,2).equalsIgnoreCase("XX")){ codTabela = -1; }
                    else { codTabela = Integer.parseInt(s.substring(0,2)); }
                }
                catch (Exception ex) { codTabela = -1; }

                Tabelas tb = Tabelas.getTipoFromInt(codTabela);

                switch (tb)
                {
                    case DEVOLUCAO :
                        if(devolucoes == null) { devolucoes = new ArrayList<>(); }
                        devolucoes.add(s);
                        break;
                    case MOTIVOS :
                        if(motivos == null) { motivos = new ArrayList<>(); }
                        motivos.add(s);
                        break;
                    case PRE_PEDIDO :
                        if(prePedidos == null) { prePedidos = new ArrayList<>(); }
                        prePedidos.add(s);
                        break;
                    case PRE_PEDIDO_CD :
                        if(prePedidosDiretas == null) { prePedidosDiretas = new ArrayList<>(); }
                        prePedidosDiretas.add(s);
                        break;
                    case METAS :
                        if(metas == null) { metas = new ArrayList<>(); }
                        metas.add(s);
                        break;
                    case TIPOLOGIA :
                        if(tipologias == null) { tipologias = new ArrayList<>(); }
                        tipologias.add(s);
                        break;
                    case CTAS_RECEBER :
                        if(contasReceber == null) { contasReceber = new ArrayList<>(); }
                        contasReceber.add(s);
                        break;
                    case MIX :
                        if(mix == null) { mix = new ArrayList<>(); }
                        mix.add(s);
                        break;
                    case GRAVOSOS :
                        if(gravosos == null) { gravosos = new ArrayList<>(); }
                        gravosos.add(s);
                        break;
                    case MENSAGENS :
                        if(mensagens == null) { mensagens = new ArrayList<>(); }
                        mensagens.add(s);
                        break;
                    case MENSAGENS_MSG :
                        if(mensagens_msg == null) { mensagens_msg = new ArrayList<>(); }
                        mensagens_msg.add(s);
                        break;
                    case TOTALIZADORES :
                        if(curvaAbc == null) { curvaAbc = new ArrayList<>(); }
                        curvaAbc.add(s);
                        break;
                    case TABELA_CLIENTE :
                        if(precosClientes == null) { precosClientes = new ArrayList<>(); }
                        precosClientes.add(s);
                        break;
                    case ATIVIDADE :
                        if(atividades == null) { atividades = new ArrayList<>(); }
                        atividades.add(s);
                        break;
                    case RESTRICAO_CLIENTE :
                        if(clientesRestricoes == null) { clientesRestricoes = new ArrayList<>(); }
                        clientesRestricoes.add(s);
                        break;
                    case CORTE :
                        if(cortes == null) { cortes = new ArrayList<>(); }
                        cortes.add(s);
                        break;
                    case STATUS :
                        if(status == null) { status = new ArrayList<>(); }
                        status.add(s);
                        break;
                    case RESTRICAO_GRUPO :
                        if(naturezasRestricoes == null) { naturezasRestricoes = new ArrayList<>(); }
                        naturezasRestricoes.add(s);
                        break;
                    case CLIENTES :
                        if(clientes == null) { clientes = new ArrayList<>(); }
                        clientes.add(s);
                        break;
                    case PROMOCOES :
                        if(promocoes == null) { promocoes = new ArrayList<>(); }
                        promocoes.add(s);
                        break;
                    case PRODUTO :
                        if(produtos == null) { produtos = new ArrayList<>(); }
                        produtos.add(s);
                        break;
                    case CIDADE :
                        if(cidades == null) { cidades = new ArrayList<>(); }
                        cidades.add(s);
                        break;
                    case BANCO :
                        if(bancos == null) { bancos = new ArrayList<>(); }
                        bancos.add(s);
                        break;
                    case TIPO_VENDA :
                        if(tipos_vendas == null) { tipos_vendas = new ArrayList<>(); }
                        tipos_vendas.add(s);
                        break;
                    case GRUPO :
                        if(grupos == null) { grupos = new ArrayList<>(); }
                        grupos.add(s);
                        break;
                    case NATUREZA :
                        if(naturezas == null) { naturezas = new ArrayList<>(); }
                        naturezas.add(s);
                        break;
                    case KIT :
                        if(kits == null) { kits = new ArrayList<>(); }
                        kits.add(s);
                        break;
                    case PRAZOS :
                        if(prazos == null) { prazos = new ArrayList<>(); }
                        prazos.add(s);
                        break;
                    case TABELA_PRECOS :
                        if(precos == null) { precos = new ArrayList<>(); }
                        precos.add(s);
                        break;
                    case ESTOQUE :
                        if(estoque == null) { estoque = new ArrayList<>(); }
                        estoque.add(s);
                        break;
                    case DESC_GRUPO :
                        if(desc_grupos == null) { desc_grupos = new ArrayList<>(); }
                        desc_grupos.add(s);
                        break;
                    case DESC_CAMP :
                        if(desc_campanhas == null) { desc_campanhas = new ArrayList<>(); }
                        desc_campanhas.add(s);
                        break;
                    case VALIDADE :
                        if(validade == null) { validade = new ArrayList<>(); }
                        validade.add(s);
                        break;
                    case SALDO_FLEX :
                        if(saldo == null) { saldo = new ArrayList<>(); }
                        saldo.add(s);
                        break;
                    case COMISSAO :
                        if(comissao == null) { comissao = new ArrayList<>(); }
                        comissao.add(s);
                        break;
                    case APLICACAO :
                        if(aplicacao == null) { aplicacao = new ArrayList<>(); }
                        aplicacao.add(s);
                        break;
                    default:
                        break;
                }
            }

            strem_leitura.close();
            arquivo_leitura.close();
        }
        catch (Exception ignored) {
            System.out.println("erro " + ignored);
        }
    }

    public JSONObject ArquivoConfiguracao()
    {
        JSONObject jsonObject = null;

        FileInputStream arquivo_leitura = null;
        Scanner strem_leitura = null;

        String s = "";
        try
        {
            arquivo_leitura = new FileInputStream(AbrirArquivosConfiguracao());
            strem_leitura = new Scanner(arquivo_leitura);

            while (strem_leitura.hasNext()) { s += strem_leitura.nextLine(); }

            jsonObject = new JSONObject(s);

            strem_leitura.close();
            arquivo_leitura.close();
            return jsonObject;
        }
        catch (FileNotFoundException e) { return jsonObject; }
        catch (Exception e)
        {
            e.printStackTrace();
            return jsonObject;
        }
        finally
        {
            try
            {
                strem_leitura.close();
                arquivo_leitura.close();
            }
            catch (Exception e2) { }
        }
    }

    public Boolean VerificarArquivo()
    {
        FileInputStream arquivo_leitura = null;
        Scanner strem_leitura = null;
        int nr_total_linhas_arquivo = -1;
        int nr_total_linhas_encontrado = 0;
        boolean finalStatus = false;

        try
        {
            arquivo_leitura = new FileInputStream(AbrirArquivosExterno());
            strem_leitura = new Scanner(arquivo_leitura);

            while (strem_leitura.hasNext())
            {
                nr_total_linhas_encontrado++;
                String s = strem_leitura.nextLine();

                try
                {
                    if (s.substring(0,2).equalsIgnoreCase("XX"))
                    {
                        nr_total_linhas_arquivo = Integer.parseInt(s.substring(2).trim());

                        if (nr_total_linhas_arquivo == (nr_total_linhas_encontrado - 1))
                        {
                            finalStatus = true;
                            break;
                        }
                        else
                        {
                            this.addStringErro("Arquivo de dados inválido.");
                            finalStatus = false;
                            break;
                        }
                    }
                }
                catch (Exception ex)
                {
                    nr_total_linhas_encontrado--;
                }
            }

            strem_leitura.close();
            arquivo_leitura.close();

            return finalStatus;
        }
        catch (FileNotFoundException e)
        {
            this.addStringErro("Arquivo de dados inválido.");
            return false;
        }
        catch (Exception e)
        {
            this.addStringErro("Arquivo de dados inválido.");
            return false;
        }
        finally
        {
            try
            {
                strem_leitura.close();
                arquivo_leitura.close();
            }
            catch (Exception e2) { }
        }
    }

    public void executar(int tabela)
    {
        FileInputStream arquivo_leitura = null;
        Scanner strem_leitura = null;
        int codTabela = tabela;
        Tabelas tb = Tabelas.getTipoFromInt(codTabela);
        ManipulacaoStrings ms = new ManipulacaoStrings();

        switch (tb)
        {
            case DEVOLUCAO :
                DevolucaoDataAccess devda = new DevolucaoDataAccess(context);
                if(devolucoes != null && devolucoes.size() > 0)
                {
                    try { devda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA DEVOLUÇÃO");
                    }

                    for (String s : devolucoes)
                    {
                        if (Integer.parseInt(s.substring(2, 9).trim()) == 0)
                        {
                            try { devda.delete(); }
                            catch (Exception e)
                            {
                                this.addStringErro(e.getMessage());
                                this.addStringErro(s);
                            }
                        }
                        else
                        {
                            try { devda.insert(s); }
                            catch (Exception e)
                            {
                                this.addStringErro(e.getMessage());
                                this.addStringErro(s);
                            }
                        }
                    }
                }
                break;
            case MOTIVOS :
                MotivosDataAccess mda = new MotivosDataAccess(context);
                if(motivos != null && motivos.size() > 0)
                {
                    try { mda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA MOTIVOS");
                    }

                    for (String s : motivos) {
                        try {
                            mda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case PRE_PEDIDO :
                PrePedidoDataAccess ppda = new PrePedidoDataAccess(context);
                if(prePedidos != null && prePedidos.size() > 0)
                {
                    try { ppda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA PREPEDIDOS");
                    }

                    for (String s : prePedidos) {
                        try {
                            ppda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case PRE_PEDIDO_CD :
                PrePedidoDiretaDataAccess ppdda = new PrePedidoDiretaDataAccess(context);
                if(prePedidosDiretas != null && prePedidosDiretas.size() > 0)
                {
                    try { ppdda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA PRE_PEDIDO_CD");
                    }

                    for (String s : prePedidosDiretas) {
                        try {
                            ppdda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case METAS :
                MetaDataAccess metada = new MetaDataAccess(context);
                if(metas != null && metas.size() > 0)
                {
                    try { metada.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA METAS");
                    }

                    for (String s : metas) {
                        try {
                            metada.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case TIPOLOGIA :
                TipologiaDataAccess tipoda = new TipologiaDataAccess(context);
                if(tipologias != null && tipologias.size() > 0)
                {
                    try { tipoda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA TIPOLOGIA");
                    }

                    for (String s : tipologias) {
                        try {
                            tipoda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case CTAS_RECEBER :
                ContasReceberDataAccess crda = new ContasReceberDataAccess(context);
                if(contasReceber != null && contasReceber.size() > 0)
                {
                    try { crda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA CTAS_RECEBER");
                    }

                    for (String s : contasReceber) {
                        try {
                            crda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case MIX :
                MixDataAccess mixda = new MixDataAccess(context);
                if(mix != null && mix.size() > 0)
                {
                    try { mixda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA MIX");
                    }

                    for (String s : mix)
                    {
                        try { mixda.insert(s); }
                        catch (Exception e)
                        {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case GRAVOSOS :
                GravososDataAccess gravda = new GravososDataAccess(context);
                if(gravosos != null && gravosos.size() > 0)
                {
                    try { gravda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA GRAVOSOS");
                    }

                    for (String s : gravosos) {
                        try {
                            gravda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case MENSAGENS :
                MensagemDataAccess msgda = new MensagemDataAccess(context);
                if((mensagens != null && mensagens.size() > 0) &&
                    (mensagens_msg != null && mensagens_msg.size() > 0))
                {
                    try { msgda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA MENSAGENS");
                    }

                    int i = 0;
                    for (String s : mensagens)
                    {
                        try
                        {
                            msgda.insert(s + mensagens_msg.get(i++).substring(2));
                        }
                        catch (Exception e)
                        {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case TOTALIZADORES :
                CurvaAbcDataAccess abcda = new CurvaAbcDataAccess(context);
                if(curvaAbc != null && curvaAbc.size() > 0)
                {
                    try { abcda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA TOTALIZADORES");
                    }

                    for (String s : curvaAbc) {
                        try {
                            abcda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case TABELA_CLIENTE :
                PrecosClientesDataAccess pcda = new PrecosClientesDataAccess(context);
                if(precosClientes != null && precosClientes.size() > 0)
                {
                    try { pcda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA TABELA_CLIENTE");
                    }

                    for (String s : precosClientes) {
                        try {
                            pcda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case ATIVIDADE :
                AtividadeDataAccess atvda = new AtividadeDataAccess(context);
                if(atividades != null && atividades.size() > 0)
                {
                    try { atvda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA ATIVIDADE");
                    }

                    for (String s : atividades) {
                        try {
                            atvda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case RESTRICAO_CLIENTE :
                GrupoBloqueadoClienteDataAccess gbcda = new GrupoBloqueadoClienteDataAccess(context);
                if(clientesRestricoes != null && clientesRestricoes.size() > 0)
                {
                    try { gbcda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA RESTRICAO_CLIENTE");
                    }

                    for (String s : clientesRestricoes) {
                        try {
                            gbcda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case CORTE :
                CorteDataAccess corteda = new CorteDataAccess(context);
                if(cortes != null && cortes.size() > 0)
                {
                    try { corteda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA CORTE");
                    }

                    for (String s : cortes) {
                        try {
                            corteda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case STATUS :
                StatusDataAccess sda = new StatusDataAccess(context);
                if(status != null && status.size() > 0)
                {
                    try { sda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA STATUS");
                    }

                    for (String s : status) {
                        try {
                            sda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case RESTRICAO_GRUPO :
                GrupoBloqueadoNaturezaDataAccess gbnda = new GrupoBloqueadoNaturezaDataAccess(context);
                if(naturezasRestricoes != null && naturezasRestricoes.size() > 0)
                {
                    try { gbnda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA RESTRICAO_GRUPO");
                    }

                    for (String s : naturezasRestricoes) {
                        try {
                            gbnda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case CLIENTES :
                ClienteDataAccess cda = new ClienteDataAccess(context);
                if(clientes != null && clientes.size() > 0)
                {
                    try { cda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA CLIENTES");
                    }

                    for (String s : clientes) {
                        try {
                            cda.inserir(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case PROMOCOES :
                PromocaoDataAccess proda = new PromocaoDataAccess(context);
                if(promocoes != null && promocoes.size() > 0)
                {
                    try { proda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA PROMOCOES");
                    }

                    for (String s : promocoes) {
                        try {
                            proda.inserir(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case PRODUTO :
                ItemDataAccess ida = new ItemDataAccess(context);
                if(produtos != null && produtos.size() > 0)
                {
                    try { ida.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA PRODUTO");
                    }

                    for (String s : produtos) {
                        try {
                            ida.inserir(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case CIDADE :
                CidadeDataAccess cida = new CidadeDataAccess(context);
                if(cidades != null && cidades.size() > 0)
                {
                    try { cida.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA CIDADE");
                    }

                    for (String s : cidades) {
                        try {
                            cida.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case BANCO :
                BancoDataAccess bda = new BancoDataAccess(context);
                if(bancos != null && bancos.size() > 0)
                {
                    try { bda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA BANCO");
                    }

                    for (String s : bancos) {
                        try {
                            bda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case TIPO_VENDA :
                TipoVendaDataAccess tvda = new TipoVendaDataAccess(context);
                if(tipos_vendas != null && tipos_vendas.size() > 0)
                {
                    try { tvda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA TIPO_VENDA");
                    }

                    for (String s : tipos_vendas) {
                        try {
                            tvda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case GRUPO :
                GrupoDataAccess gda = new GrupoDataAccess(context);
                if(grupos != null && grupos.size() > 0)
                {
                    try { gda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA GRUPO");
                    }

                    for (String s : grupos) {
                        try {
                            gda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case NATUREZA :
                NaturezaDataAccess nda = new NaturezaDataAccess(context);
                if(naturezas != null && naturezas.size() > 0)
                {
                    try { nda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA NATUREZA");
                    }

                    for (String s : naturezas) {
                        try {
                            nda.inserir(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case KIT :
                KitDataAccess kda = new KitDataAccess(context);
                if(kits != null && kits.size() > 0)
                {
                    try { kda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA KIT");
                    }

                    for (String s : kits) {
                        try {
                            kda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case PRAZOS :
                PrazoDataAccess pda = new PrazoDataAccess(context);
                if(prazos != null && prazos.size() > 0)
                {
                    try { pda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA PRAZOS");
                    }

                    for (String s : prazos) {
                        try {
                            pda.inserir(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case TABELA_PRECOS :
                PrecoDataAccess tda = new PrecoDataAccess(context);
                if(precos!= null && precos.size() > 0)
                {
                    try { tda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA TABELA_PRECOS");
                    }

                    for (String s : precos) {
                        try {
                            tda.inserir(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case ESTOQUE :
                EstoqueDataAccess eda = new EstoqueDataAccess(context);
                if(estoque != null && estoque.size() > 0)
                {
                    try { eda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA ESTOQUE");
                    }

                    for (String s : estoque) {
                        try {
                            eda.inserir(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case DESC_GRUPO :
                CampanhaGrupoDataAccess cgda = new CampanhaGrupoDataAccess(context);
                if(desc_grupos != null && desc_grupos.size() > 0)
                {
                    try { cgda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA DESC_GRUPO");
                    }

                    for (String s : desc_grupos) {
                        try {
                            cgda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case DESC_CAMP :
                CampanhaProdutoDataAccess cpda = new CampanhaProdutoDataAccess(context);
                if(desc_campanhas != null && desc_campanhas.size() > 0)
                {
                    try { cpda.delete(); }
                    catch (Exception e)
                    {
                        this.addStringErro(e.getMessage());
                        this.addStringErro("ERRO AO EXCLUIR DADOS DA TABELA DESC_CAMP");
                    }

                    for (String s : desc_campanhas) {
                        try {
                            cpda.insert(s);
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case VALIDADE :
                ConfiguradorDataAccess valda = new ConfiguradorDataAccess(context);
                if(validade != null && validade.size() > 0)
                {
                    for (String s : validade)
                    {
                        try {
                            valda.updateValidade(ms.dataBanco(s.substring(2, 12)));
                        } catch (Exception e) {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case SALDO_FLEX :
                ConfiguradorDataAccess saldoda = new ConfiguradorDataAccess(context);
                if(saldo != null && saldo.size() > 0)
                {
                    for (String s : saldo)
                    {
                        if (s.substring(9).equals("1"))
                        {
                            try { saldoda.updateSaldo(s.substring(2, 9)); } catch (Exception e)
                            {
                                this.addStringErro(e.getMessage());
                                this.addStringErro(s);
                            }
                        }
                    }
                }
                break;
            case COMISSAO :
                ConfiguradorDataAccess comissaoda = new ConfiguradorDataAccess(context);
                if(comissao != null && comissao.size() > 0)
                {
                    for (String s : comissao)
                    {
                        try
                        {
                            comissaoda.updateVenda(s.substring(2, 12).trim());
                            comissaoda.updateComissao(s.substring(12, 22).trim());
                            comissaoda.updateContribuicao(s.substring(22).trim());
                        } catch (Exception e)
                        {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            case APLICACAO :
                ItemDataAccess ida2 = new ItemDataAccess(context);
                if(aplicacao != null && aplicacao.size() > 0)
                {
                    for (String s : aplicacao)
                    {
                        try { ida2.updateItem(s.substring(2).trim()); }
                        catch (Exception e)
                        {
                            this.addStringErro(e.getMessage());
                            this.addStringErro(s);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    public boolean criarArquivoErros()
    {
        if(errosIserir.size() > 0)
        {
            this.escreverArquivoErros();
            return true;
        }
        else
            return false;
    }

    public void addStringErro(String erro)
    {
        Date data = new Date();
        String dataHora =
            data.getHours() + "/" +
                data.getMinutes() + ":" +
                data.getSeconds() + " -- " +
                (data.getDate()) + "/" +
                (data.getMonth() + 1) + "/" +
                (data.getYear() + 1900);

        erro = dataHora + " >>> " + erro;

        if(this.errosIserir == null)
            this.errosIserir = new ArrayList<>();

        this.errosIserir.add(erro);
    }

    public void inserirConfiguracao(Configurador configurador)
    {
        ConfiguradorDataAccess cda = new ConfiguradorDataAccess(this.context);

        if(configurador.getConexao() == null || configurador.getEmpresa() == null ||
            configurador.getHorarios() == null || configurador.getVendas() == null ||
            configurador.getTelas() == null || configurador.getUsuario() == null )
        {
            try
            {
                cda.insert(configurador.getConexao());
            }
            catch (GenercicException e)
            {
                e.printStackTrace();
                this.addStringErro(e.getMessage());
            }

            try
            {
                cda.insert(configurador.getEmpresa());
            }
            catch (GenercicException e)
            {
                e.printStackTrace();
                this.addStringErro(e.getMessage());
            }

            try
            {
                cda.insert(configurador.getHorarios());
            }
            catch (GenercicException e)
            {
                e.printStackTrace();
                this.addStringErro(e.getMessage());
            }

            try
            {
                cda.insert(configurador.getVendas());
            }
            catch (GenercicException e)
            {
                e.printStackTrace();
                this.addStringErro(e.getMessage());
            }

            try
            {
                cda.insert(configurador.getTelas());
            }
            catch (GenercicException e)
            {
                e.printStackTrace();
                this.addStringErro(e.getMessage());
            }

            try
            {
                cda.insert(configurador.getUsuario());
            }
            catch (GenercicException e)
            {
                e.printStackTrace();
                this.addStringErro(e.getMessage());
            }
        }
        else
        {
            boolean insert = false;
            try
            {
                if(cda.getEmpresa() != null) { insert = false; }
                else { insert = true; }
            }
            catch (Exception e) { insert = false; }

            if(insert)
            {
                try { cda.insert(configurador); }
                catch (GenercicException e)
                {
                    e.printStackTrace();
                    this.addStringErro(e.getMessage());
                }
            }
            else
            {
                try { cda.update(configurador); }
                catch (GenercicException e)
                {
                    e.printStackTrace();
                    this.addStringErro(e.getMessage());
                }
            }
        }
    }

    private void escreverArquivoErros()
    {
        try
        {
            int version;

            try { version = Integer.valueOf(Build.VERSION.SDK); }
            catch (Exception ev){ version = 3; }

            File imgFile;

            if(version == 19) { imgFile = new File("/storage/emulated/0/MobileVenda", "ERROS.txt"); }
            else { imgFile = new File("sdcard/MobileVenda", "ERROS.txt"); }

            FileOutputStream fOut = new FileOutputStream(imgFile);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            for (int i = 0; i < errosIserir.size(); i++){ osw.append(errosIserir.get(i) + "\n"); }

            osw.close();
            fOut.close();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void excluirArquivosLocal()
    {
        int version;
        File pasta;

        try { version = Integer.valueOf(Build.VERSION.SDK); }
        catch (Exception ev){ version = 3; }

        if(version == 19) { pasta = new File("/storage/emulated/0//MobileVenda"); }
        else { pasta = new File("sdcard/MobileVenda"); }

//        File pasta = new File(Environment.getExternalStorageDirectory() + "/MobileVenda");
        String[] arqs_sd = pasta.list();

        for (int i = 0; i < arqs_sd.length; i++)
        {
            if (arqs_sd[i].length() > 3)
            {
                if (arqs_sd[i].substring(0, 2).equalsIgnoreCase("pw"))
                {
                    this.nomeArquivo = arqs_sd[i];
                    File arquivo = AbrirArquivosExterno();

                    if (arquivo.exists())
                        arquivo.delete();

                }
            }
        }
    }

    public void excluir_arquivos_sd(String nome_arquivo)
    {
        this.nomeArquivo = nome_arquivo;

        File arquivo = AbrirArquivosExterno();

        if (arquivo.exists())
            arquivo.delete();

    }

    public void escreverVendas(ArrayList<Venda> listaVendas)
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();
        StringBuilder builder = new StringBuilder();
        builder.delete(0, builder.length());
        
        try 
        {
            int line_count = 0;
            FileOutputStream fOut = new FileOutputStream(AbrirArquivosExterno());
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            VisitaDataAccess vda = new VisitaDataAccess(this.context);

            for(Venda v : listaVendas)
            {
                builder.delete(0, builder.length());

                builder.append("P");
                builder.append(ms.comEsquerda("" + v.getCodigo(), "0", 7));
                builder.append(ms.comEsquerda("" + v.getCliente().getCodigoCliente(), "0", 7));
                builder.append(ms.comEsquerda("" + v.getTabela(), "0", 2));
                builder.append(ms.comEsquerda("" + v.getNatureza(), "0", 3));

                String[] desdobramentos = v.getPrazo().getDesdobramento().split("-");

                builder.append(ms.comEsquerda("" + desdobramentos[0], "0", 3));
                builder.append(ms.comEsquerda("" + desdobramentos[1], "0", 3));
                builder.append(ms.comEsquerda("" + desdobramentos[2], "0", 3));
                builder.append(ms.comEsquerda("" + desdobramentos[3], "0", 3));
                builder.append(ms.dataVisual(v.getData()).replace("/", "-") + v.getHora());
                builder.append(ms.comEsquerda((Formatacao.format2d(v.getDesconto())).replace(".", "").replace(",", ""), "0", 6));
                /*
                builder.append(ms.comEsquerda(("" + Formatacao.format3(v.getDesconto(), 2)).replace(".", "").replace(",", ""), "0", 6));
                 */
                builder.append(ms.comDireita("" + v.getObservacaDesconto(), " ", 25));
                builder.append(ms.comDireita(v.getTipo(), " ", 2));

                String obs = v.getObservacao().replaceAll("\n", " ");
                obs = obs.replaceAll("\t", " ");
                obs = obs.replaceAll("\r", " ");

                builder.append(ms.comDireita("" + obs, " ", 114));

                obs = v.getObservacaoNota().replaceAll("\n", " ");
                obs = obs.replaceAll("\t", " ");
                obs = obs.replaceAll("\r", " ");

                builder.append(ms.comDireita("" + obs, " ", 50));
                builder.append(ms.comEsquerda("" + v.getBanco(), "0", 3));
                builder.append(ms.comEsquerda("" + 9, "0", 15));
                builder.append(ms.comEsquerda("" + 0, "0", 15));
                /*
                builder.append(ms.comEsquerda("" + v.getLatitude(), "0", 15));
                builder.append(ms.comEsquerda("" + v.getLongitude(), "0", 15));
                 */

                osw.append(builder.toString());

                osw.append(/*"P" + "" + v.toString() + */"\n");
                line_count++;

                ArrayList<ItensVendidos> itens = v.getItens();
                for(ItensVendidos i : itens)
                {
                    /*
                    if(i.getValorLiquido() < i.getValorTabela() && (i.getDescontoCG() == 0)*//* && (i.getFlag_desconto_campanha() == 1) *//*)
                    {
                        float desconto = ((i.getValorTabela() - i.getValorLiquido()) / i.getValorTabela()) * 100;
                        i.setDescontoCG(desconto);
                    }
                    */

                    ItemDataAccess ida = new ItemDataAccess(this.context);

                    if(i.getValorLiquido() < i.getValorTabela() && (i.getDescontoCG() == 0) && i.isDescontoCampanha())
                    {
                        float desconto = ((i.getValorTabela() - i.getValorLiquido()) / i.getValorTabela()) * 100;
                        i.setDescontoCG(desconto);
                    }
                    builder.delete(0, builder.length());

                    String referencia = ida.getItemStr(i.getItem());
                    referencia = referencia.substring(0, referencia.indexOf(" . "));
                    
                    builder.append("I");
                    builder.append(ms.comEsquerda("" + v.getCodigo(), "0", 7));
                    builder.append(ms.comEsquerda("" + v.getCliente().getCodigoCliente(), "0", 7));
                    builder.append(ms.comDireita("" + referencia, " ", 10));
                    //builder.append(ms.comEsquerda(("" + /*(int)*/Formatacao.format2d(i.getEstoque())).replace(".", ""), "0", 6) +
                    builder.append(ms.comEsquerda(("" + (int)i.getQuantidade()).replace(".", ""), "0", 4));
                    builder.append(ms.comEsquerda((Formatacao.format2d(i.getValorLiquido())).replace(".", "").replace(",", ""), "0", 9));
                    builder.append(ms.comEsquerda((Formatacao.format2d(i.getValorTabela())).replace(".", "").replace(",", ""), "0", 9));
                    builder.append(ms.comEsquerda((Formatacao.format2d(i.getDesconto())).replace(".", ""), "0", 6));
                    builder.append(ms.comDireita("0", " ", 3));
                    builder.append(ms.comEsquerda(("0").replace(".", ""), "0", 4));
                    builder.append(ms.comEsquerda(("0").replace(".", ""), "0", 9));
                    builder.append(ms.comDireita((i.isDigitadoSenha() ? "1" : "0"), "0", 1));
                    /*
                    builder.append(ms.comDireita("" + i.getIcm(), " ", 3));
                    builder.append(ms.comEsquerda(("" + i.getAliq()).replace(".", ""), "0", 4));
                    builder.append(ms.comEsquerda(("" + i.getBase()).replace(".", ""), "0", 9));
                    */
                    builder.append(ms.comEsquerda("" + i.getItem(), "0", 6));
                    builder.append(ms.comEsquerda((Formatacao.format2d(i.getDescontoCG())).replace(".", "").replace(",", ""), "0", 5));
                    builder.append(ms.comEsquerda((Formatacao.format2d(i.getValorDigitado())).replace(".", "").replace(",", ""), "0", 9));
                    builder.append(ms.comDireita((i.isDescontoCampanha() ? "1" : "0"), "0", 1));
                    builder.append(ms.comEsquerda((Formatacao.format2d(i.getValorMinimo())).replace(".", "").replace(",", ""), "0", 9));
                    builder.append(ms.comEsquerda((Formatacao.format2d(i.getQuantidadeEspecifica())).replace(".", "").replace(",", ""), "0", 6));

                    osw.append(builder.toString());
                    osw.append("\n");
                    line_count++;
                }

                builder.delete(0, builder.length());

                Visita visita = new Visita();
                vda.setSearchData(v.getCodigo().toString());

                visita = vda.getByData();
                builder.delete(0, builder.length());

                builder.append("V");
                builder.append(ms.comEsquerda("" +  visita.getPed(), "0", 7));
                builder.append(ms.comEsquerda(("" + visita.getCli()).replace(".", ""), "0", 7));
                builder.append(ms.comEsquerda(("" + visita.getMotivo()).replace(".", ""), "0", 3));
                //builder.append(ms.comEsquerda(("" + /*(int)*/Formatacao.format2d(i.getEstoque())).replace(".", ""), "0", 6) +
                builder.append(ms.comEsquerda(("" + ms.dataVisual(visita.getData().replace("/", "-"))), " ", 10));
                builder.append(ms.comEsquerda(("" + visita.getHora()), " ", 8));
                builder.append(ms.comEsquerda(("" + visita.getDia()), "0", 2));
                builder.append(ms.comEsquerda(("" + v.getTipo()), "0", 2));
                builder.append(ms.comDireita(visita.getVenda(), " ", 1));

                osw.append(builder.toString());
                osw.append("\n");
                line_count++;
            }

            vda.setSearchData("0");
            ArrayList<Visita> visitas = new ArrayList<Visita>();
            visitas = (ArrayList<Visita>) vda.getGetNotSent();

            for (Visita visita : visitas)
            {
                builder.delete(0, builder.length());

                visita = vda.getByData();
                builder.delete(0, builder.length());

                builder.append("V");
                builder.append(ms.comEsquerda("" +  visita.getPed(), "0", 7));
                builder.append(ms.comEsquerda(("" + visita.getCli()).replace(".", ""), "0", 7));
                builder.append(ms.comEsquerda(("" + visita.getMotivo()).replace(".", ""), "0", 3));
                //builder.append(ms.comEsquerda(("" + /*(int)*/Formatacao.format2d(i.getEstoque())).replace(".", ""), "0", 6) +
                builder.append(ms.comEsquerda(("" + ms.dataVisual(visita.getData().replace("/", "-"))), " ", 10));
                builder.append(ms.comEsquerda(("" + visita.getHora()), " ", 8));
                builder.append(ms.comEsquerda(("" + visita.getDia()), "0", 2));
                builder.append(ms.comEsquerda("", "0", 2));
                builder.append(ms.comDireita(visita.getVenda(), " ", 1));

                osw.append(builder.toString());
                osw.append("\n");
                line_count++;
            }
                
            osw.append("X" + ms.comEsquerda(("" + line_count), "0", 3));

            osw.close();
            fOut.close();
        }
        catch (Exception e) { this.addStringErro(e.getMessage()); }
    }

    public void escreverClientes(ArrayList<ClienteNovo> listaClientes)
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();
        StringBuilder builder = new StringBuilder();
        builder.delete(0, builder.length());

        try
        {
            int line_count = 0;
            FileOutputStream fOut = new FileOutputStream(AbrirArquivosExterno());
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            for(ClienteNovo c : listaClientes)
            {
                builder.delete(0, builder.length());

                builder.append(ms.comDireita("" + c.getNome(), " ", 45));
                builder.append(ms.comDireita("" + c.getFantazia(), " ", 20));
                builder.append(ms.comDireita("" + c.getEndereco(), " ", 45));
                builder.append(ms.comDireita("" + c.getBairro(), " ", 30));
                builder.append(ms.comEsquerda("" + c.getCidade(), "0", 4));
                builder.append(ms.comDireita("" + c.getFone(), " ", 14));
                builder.append(ms.comDireita("" + c.getCell(), " ", 16));
                builder.append(ms.comEsquerda("" + c.getCgc().replace(".", "").replace("/", "").replace("-", ""), "0", 14));
                builder.append(ms.comDireita("" + c.getIe(), " ", 10));
                builder.append(ms.comDireita("" + c.getContato(), " ", 25));
                builder.append(ms.comDireita("" + c.getEmail(), " ", 35));
                builder.append(ms.comDireita("" + c.getAniversario(), " ", 10));
                builder.append(ms.comDireita("" + c.getCep(), " ", 9));
                builder.append(ms.comDireita("" + c.getT_pessoa(), " ", 1));
                builder.append(ms.comDireita("" + c.getRep(), " ", 30));
                builder.append(ms.comDireita("" + c.getIdent().replace(".", "").replace("/", "").replace("-", ""), " ", 10));
                builder.append(ms.comEsquerda("" + c.getZona(), "0", 4));
                builder.append(ms.comDireita("" + c.getVisita(), " ", 1));
                builder.append(ms.comEsquerda("" + c.getCpf().replace(".", "").replace("/", "").replace("-", ""), "0", 11));
                builder.append(ms.comEsquerda("" + c.getTipologia(), "0", 4));
                builder.append(ms.comEsquerda("" + c.getPagto(), "0", 3));
                builder.append(ms.comEsquerda("" + c.getBanco(), "0", 3));
                builder.append(ms.comEsquerda("" + ("" + Formatacao.format2d(c.getPotencial())).replace(".", "").replace(",", ""), "0", 8));
                builder.append(ms.comDireita("" + c.getObs(), " ", 75));
                builder.append(ms.comDireita("" + c.getNr_res(), " ", 6));
                builder.append(ms.comEsquerda("" + c.getAtividade(), "0", 4));
                builder.append(ms.comEsquerda("" + c.getArea(), "0", 2));
                builder.append(ms.comDireita("" + c.getComercial1(), " ", 45));
                builder.append(ms.comDireita("" + c.getComercial1_fone(), " ", 10));
                builder.append(ms.comDireita("" + c.getComercial2(), " ", 45));
                builder.append(ms.comDireita("" + c.getComercial2_fone(), " ", 10));
                builder.append(ms.comDireita("" + c.getComercial3(), " ", 45));
                builder.append(ms.comDireita("" + c.getComercial3_fone(), " ", 10));
                builder.append(ms.comDireita("" + c.getComercial_abertura(), " ", 10));

                osw.append(builder.toString());
                osw.append("\n");
                line_count++;
            }

            osw.append("9" + ms.comEsquerda(("" + line_count), "0", 3));

            osw.close();
            fOut.close();
        }
        catch (Exception e) { this.addStringErro(e.getMessage()); }
    }

    public void criar_plano_visitas(String name, int vendedor, String nome)
    {
        PlanoVisitas plano = new PlanoVisitas(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();

        Date today = new Date();
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");
        String data = sf.format(today);

        ArrayList<String> lPlano =  plano.gerarPlano(data);
        ArrayList<GenericItemFour> np =  plano.listarNaoPositivados(data);

        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;

        try
        {
            this.nomeArquivo = name;
            File fn = AbrirArquivosExterno();
//            if(fn.exists())
//            {
                fOut = new FileOutputStream(fn);
                osw = new OutputStreamWriter(fOut);
//            }
//            else
//            {
//                String s = "Não criou o arquivo";
//            }

            osw.append("" + ms.comDireita("_", "_", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("PLANO DE VISISTA", " ", 84));
            osw.append("" + ms.comDireita("DATA: " + data, " ", 26));
            osw.append("\n");
            osw.append("" + ms.comDireita("VENDEDOR: " + vendedor + " - " + nome, " ", 84));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("DIA", " ", 12));
            osw.append("" + ms.comDireita("CLIENTES", " ", 10));
            osw.append("" + ms.comDireita("POSITIV", " ", 12));
            osw.append("" + ms.comDireita("%", " ", 9));
            osw.append("" + ms.comDireita("F.DIA", " ", 9));
            osw.append("" + ms.comDireita("JUSTIF", " ", 11));
            osw.append("" + ms.comDireita("NAO POSIT", " ", 13));
            osw.append("" + ms.comDireita("FATURAMENTO", " ", 17));
            osw.append("" + ms.comDireita("VOLUME", " ", 17));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("SEGUNDA", " ", 15));
            osw.append("" + ms.comDireita(lPlano.get(0), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(1), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(2) /*valor_percentual(Formatacao.format3(f_perc_cli_seg, 2))*/, " ", 11));
            osw.append("" + ms.comDireita(lPlano.get(3), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(4), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(5), " ", 12));
            osw.append("" + ms.comDireita(lPlano.get(6), " ", 17));
            osw.append("" + ms.comDireita(lPlano.get(7), " ", 17));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("TERCA", " ", 15));
            osw.append("" + ms.comDireita(lPlano.get(8), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(9), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(10) /*valor_percentual(Formatacao.format3(f_perc_cli_ter, 2))*/, " ", 11));
            osw.append("" + ms.comDireita(lPlano.get(11), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(12), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(13), " ", 12));
            osw.append("" + ms.comDireita(lPlano.get(14), " ", 17));
            osw.append("" + ms.comDireita(lPlano.get(15), " ", 17));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("QUARTA", " ", 15));
            osw.append("" + ms.comDireita(lPlano.get(16), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(17), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(18) /*valor_percentual(Formatacao.format3(f_perc_cli_qua, 2))*/, " ", 11));
            osw.append("" + ms.comDireita(lPlano.get(19), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(20), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(21), " ", 12));
            osw.append("" + ms.comDireita(lPlano.get(22), " ", 17));
            osw.append("" + ms.comDireita(lPlano.get(23), " ", 17));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("QUINTA", " ", 15));
            osw.append("" + ms.comDireita(lPlano.get(24), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(25), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(26) /*valor_percentual(Formatacao.format3(f_perc_cli_qui, 2))*/, " ", 11));
            osw.append("" + ms.comDireita(lPlano.get(27), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(28), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(29), " ", 12));
            osw.append("" + ms.comDireita(lPlano.get(30), " ", 17));
            osw.append("" + ms.comDireita(lPlano.get(31), " ", 17));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("SEXTA", " ", 15));
            osw.append("" + ms.comDireita(lPlano.get(32), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(33), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(34) /*valor_percentual(Formatacao.format3(f_perc_cli_sex, 2))*/, " ", 11));
            osw.append("" + ms.comDireita(lPlano.get(35), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(36), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(37), " ", 12));
            osw.append("" + ms.comDireita(lPlano.get(38), " ", 17));
            osw.append("" + ms.comDireita(lPlano.get(39), " ", 17));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("SABADO", " ", 15));
            osw.append("" + ms.comDireita(lPlano.get(40), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(41), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(42) /*valor_percentual(Formatacao.format3(f_perc_cli_sab, 2))*/, " ", 11));
            osw.append("" + ms.comDireita(lPlano.get(43), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(44), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(45), " ", 12));
            osw.append("" + ms.comDireita(lPlano.get(46), " ", 17));
            osw.append("" + ms.comDireita(lPlano.get(47), " ", 17));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("SEM VISITA", " ", 15));
            osw.append("" + ms.comDireita(lPlano.get(48), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(49), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(50) /*valor_percentual(Formatacao.format3(f_perc_cli_sv, 2))*/, " ", 11));
            osw.append("" + ms.comDireita(lPlano.get(51), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(52), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(53), " ", 12));
            osw.append("" + ms.comDireita(lPlano.get(54), " ", 17));
            osw.append("" + ms.comDireita(lPlano.get(55), " ", 17));
            osw.append("\n");
            osw.append("" + ms.comDireita("_", "_", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("TOTAL", " ", 15));
            osw.append("" + ms.comDireita(lPlano.get(56), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(57), " ", 9));
            osw.append("" + ms.comDireita(lPlano.get(58) /*valor_percentual(Formatacao.format3(f_perc_cli_tot, 2))*/, " ", 11));
            osw.append("" + ms.comDireita(lPlano.get(59), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(60), " ", 10));
            osw.append("" + ms.comDireita(lPlano.get(61), " ", 12));
            osw.append("" + ms.comDireita(lPlano.get(62), " ", 17));
            osw.append("" + ms.comDireita(lPlano.get(63), " ", 17));
            osw.append("\n");
            osw.append("\n");
            osw.append("\n");
            osw.append("" + ms.comDireita("_", "_", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("_", "_", 110));
            osw.append("\n");
            osw.append("" + ms.comDireita("CLIENTE NAO POSITIVADOS", " ", 110));

            if(np.size() < 1)
            {
                osw.append("\n");
                osw.append("\n");
                osw.append("" + ms.comDireita(ms.comEsquerda("NAO EXISTEM CLIENTES NAO POSITIVADOS", "*", 74), "*", 110));
                osw.append("\n");
            }
            else
            {
                for (int i = 0; i < np.size(); i++)
                {
                    osw.append("\n");
                    osw.append("\n");
                    osw.append("" + ms.comDireita("" + np.get(i).getDataOne(), " ", 10));
                    osw.append("" + ms.comDireita("" + np.get(i).getDataTwo(), " ", 40));
                    osw.append("" + ms.comDireita("" + np.get(i).getDataThre(), " ", 25));
                    osw.append("" + ms.comDireita("" + ms.dataVisual(np.get(i).getDataFour()), " ", 10));
                }
            }

            osw.close();
            fOut.close();
        }
        catch (Exception e) { /*****/ }
    }

    public void criar_produtos_foco(String name, String data, int vendedor, String nome)
    {
        ConsultaFoco foco = new ConsultaFoco(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();

        Date today = new Date();
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<Foco> lista_foco;
        lista_foco = foco.buscarFoco();

        this.nomeArquivo = name;
        try
        {
            FileOutputStream fOut = new FileOutputStream(AbrirArquivosExterno());
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.append(ms.comDireita("_", "_", 110));
            osw.append("\n");
            osw.append(ms.comDireita("PRODUTO FOCO - PRODUTO", " ", 75));
            osw.append(ms.comDireita("DATA: " + ms.dataVisual(data), " ", 35));
            osw.append("\n");
            osw.append(ms.comDireita("VENDEDOR: " + vendedor + " - " + nome, " ", 110));
            osw.append("\n");
            osw.append(ms.comDireita("-", "-", 110));
            osw.append("\n");
            osw.append(ms.comDireita("PRODUTO", " ", 14));
            osw.append(ms.comDireita("DESCRICAO", " ", 34));
            osw.append(ms.comDireita("VOLUME", " ", 17));
            osw.append(ms.comDireita("FATURAMENTO", " ", 17));
            osw.append(ms.comDireita("CLIENTES", " ", 29));
            osw.append("\n");

            //ESSA PARTE TEM UM FOR PARA TODOS OS PRODUTOS FOCO
            float f_Tvolume = 0;
            float f_Tfaturamento = 0;
            int i_Tclientes = 0;

            for (int i = 0; i < lista_foco.size(); i++)
            {
                Foco prod = lista_foco.get(i);

                f_Tvolume += prod.getVolume();
                f_Tfaturamento += prod.getValor();
                i_Tclientes += prod.getClientes();

                osw.append(ms.comDireita("-", "-", 110));
                osw.append("\n");
                osw.append(ms.comDireita(prod.getReferencia(), " ", 14));
                osw.append(ms.comDireita(prod.getDescricao(), " ", 34));
                osw.append(ms.comDireita("" + prod.getVolume(), " ", 17));
                osw.append(ms.comDireita("" + prod.getValor(), " ", 17));
                osw.append(ms.comDireita("" + prod.getClientes(), " ", 29));
                osw.append("\n");
            }

            //ATÉ AQUI VEM O FOR...
            osw.append(ms.comDireita("_", "_", 110));
            osw.append("\n");
            osw.append(ms.comDireita("TOTAL", " ", 48));
            osw.append(ms.comDireita("" + f_Tvolume, " ", 17));
            osw.append(ms.comDireita("" + f_Tfaturamento, " ", 17));
            osw.append(ms.comDireita("" + i_Tclientes, " ", 29));
            osw.close();
            fOut.close();
        }
        catch (Exception e) { /*****/ }
    }

    public void criar_resumo_dia(String name, String data, int vendedor, String nome)
    {
        /*
        int cod_vendedor;
        String nome_vendedor;
        Inserir_Pedidos buscar_pedidos;
//		ArrayList<Pedido> lista_pedidos;

        Date today = new Date();
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");

        buscar_pedidos = new Inserir_Pedidos(contexto);

        String lbl_grp_1;
        String lbl_sgrp_11;
        String lbl_sgrp_12;
        String lbl_sgrp_13;
        String lbl_grp_2;
        String lbl_grp_21;
        String lbl_grp_22;
        String lbl_grp_23;
        String lbl_grp_3;
        String lbl_grp_31;
        String lbl_grp_32;
        String lbl_grp_33;

        String str_v_gpr1;
        String str_v_gpr11;
        String str_v_gpr12;
        String str_v_gpr13;
        String str_v_gpr2;
        String str_v_gpr21;
        String str_v_gpr22;
        String str_v_gpr23;
        String str_v_gpr3;
        String str_v_gpr31;
        String str_v_gpr32;
        String str_v_gpr33;

        String str_f_gpr1;
        String str_f_gpr11;
        String str_f_gpr12;
        String str_f_gpr13;
        String str_f_gpr2;
        String str_f_gpr21;
        String str_f_gpr22;
        String str_f_gpr23;
        String str_f_gpr3;
        String str_f_gpr31;
        String str_f_gpr32;
        String str_f_gpr33;

        String str_c_gpr1;
        String str_c_gpr11;
        String str_c_gpr12;
        String str_c_gpr13;
        String str_c_gpr2;
        String str_c_gpr21;
        String str_c_gpr22;
        String str_c_gpr23;
        String str_c_gpr3;
        String str_c_gpr31;
        String str_c_gpr32;
        String str_c_gpr33;

        String str_v_outros;
        String str_v_total;
        String str_f_outros;
        String str_f_total;
        String str_c_outros;
        String str_c_total;


        buscar_pedidos.open();
        cod_vendedor = buscar_pedidos.getCodVendedor();
        nome_vendedor = buscar_pedidos.getNomeVendedor();
        buscar_pedidos.close();

        buscar_pedidos.open();
//		lista_pedidos = buscar_pedidos.pedidos_reenviar(data, data);
        lbl_grp_1 = buscar_pedidos.grupo_1();
        lbl_grp_2 = buscar_pedidos.grupo_2();
        lbl_grp_3 = buscar_pedidos.grupo_3();

        lbl_sgrp_11 = buscar_pedidos.grupo_11();
        lbl_sgrp_12 = buscar_pedidos.grupo_12();
        lbl_sgrp_13 = buscar_pedidos.grupo_13();

        lbl_grp_21 = buscar_pedidos.grupo_21();
        lbl_grp_22 = buscar_pedidos.grupo_22();
        lbl_grp_23 = buscar_pedidos.grupo_23();

        lbl_grp_31 = buscar_pedidos.grupo_31();
        lbl_grp_32 = buscar_pedidos.grupo_32();
        lbl_grp_33 = buscar_pedidos.grupo_33();

        str_c_gpr1 = "" + buscar_pedidos.c_grupo_1(LerArquivos.StrDataBanco(data));
        str_c_gpr11 = "" + buscar_pedidos.c_grupo_11(LerArquivos.StrDataBanco(data));
        str_c_gpr12 = "" + buscar_pedidos.c_grupo_12(LerArquivos.StrDataBanco(data));
        str_c_gpr13 = "" + buscar_pedidos.c_grupo_13(LerArquivos.StrDataBanco(data));
        str_c_gpr2 = "" + buscar_pedidos.c_grupo_2(LerArquivos.StrDataBanco(data));
        str_c_gpr21 = "" + buscar_pedidos.c_grupo_21(LerArquivos.StrDataBanco(data));
        str_c_gpr22 = "" + buscar_pedidos.c_grupo_22(LerArquivos.StrDataBanco(data));
        str_c_gpr23 = "" + buscar_pedidos.c_grupo_23(LerArquivos.StrDataBanco(data));
        str_c_gpr3 = "" + buscar_pedidos.c_grupo_3(LerArquivos.StrDataBanco(data));
        str_c_gpr31 = "" + buscar_pedidos.c_grupo_31(LerArquivos.StrDataBanco(data));
        str_c_gpr32 = "" + buscar_pedidos.c_grupo_32(LerArquivos.StrDataBanco(data));
        str_c_gpr33 = "" + buscar_pedidos.c_grupo_33(LerArquivos.StrDataBanco(data));

        str_v_gpr1 = "" + buscar_pedidos.v_grupo_1(LerArquivos.StrDataBanco(data));
        str_v_gpr11 = "" + buscar_pedidos.v_grupo_11(LerArquivos.StrDataBanco(data));
        str_v_gpr12 = "" + buscar_pedidos.v_grupo_12(LerArquivos.StrDataBanco(data));
        str_v_gpr13 = "" + buscar_pedidos.v_grupo_13(LerArquivos.StrDataBanco(data));
        str_v_gpr2 = "" + buscar_pedidos.v_grupo_2(LerArquivos.StrDataBanco(data));
        str_v_gpr21 = "" + buscar_pedidos.v_grupo_21(LerArquivos.StrDataBanco(data));
        str_v_gpr22 = "" + buscar_pedidos.v_grupo_22(LerArquivos.StrDataBanco(data));
        str_v_gpr23 = "" + buscar_pedidos.v_grupo_23(LerArquivos.StrDataBanco(data));
        str_v_gpr3 = "" + buscar_pedidos.v_grupo_3(LerArquivos.StrDataBanco(data));
        str_v_gpr31 = "" + buscar_pedidos.v_grupo_31(LerArquivos.StrDataBanco(data));
        str_v_gpr32 = "" + buscar_pedidos.v_grupo_32(LerArquivos.StrDataBanco(data));
        str_v_gpr33 = "" + buscar_pedidos.v_grupo_33(LerArquivos.StrDataBanco(data));

        str_f_gpr1 = "" + buscar_pedidos.f_grupo_1(LerArquivos.StrDataBanco(data));
        str_f_gpr11 = "" + buscar_pedidos.f_grupo_11(LerArquivos.StrDataBanco(data));
        str_f_gpr12 = "" + buscar_pedidos.f_grupo_12(LerArquivos.StrDataBanco(data));
        str_f_gpr13 = "" + buscar_pedidos.f_grupo_13(LerArquivos.StrDataBanco(data));
        str_f_gpr2 = "" + buscar_pedidos.f_grupo_2(LerArquivos.StrDataBanco(data));
        str_f_gpr21 = "" + buscar_pedidos.f_grupo_21(LerArquivos.StrDataBanco(data));
        str_f_gpr22 = "" + buscar_pedidos.f_grupo_22(LerArquivos.StrDataBanco(data));
        str_f_gpr23 = "" + buscar_pedidos.f_grupo_23(LerArquivos.StrDataBanco(data));
        str_f_gpr3 = "" + buscar_pedidos.f_grupo_3(LerArquivos.StrDataBanco(data));
        str_f_gpr31 = "" + buscar_pedidos.f_grupo_31(LerArquivos.StrDataBanco(data));
        str_f_gpr32 = "" + buscar_pedidos.f_grupo_32(LerArquivos.StrDataBanco(data));
        str_f_gpr33 = "" + buscar_pedidos.f_grupo_33(LerArquivos.StrDataBanco(data));

        str_c_outros = "" + buscar_pedidos.c_grupo_outros(LerArquivos.StrDataBanco(data));
        str_c_total = "" + buscar_pedidos.c_grupo_total(LerArquivos.StrDataBanco(data));

        str_v_outros = "" + buscar_pedidos.v_grupo_outros(LerArquivos.StrDataBanco(data));
        str_v_total = "" + buscar_pedidos.v_total(LerArquivos.StrDataBanco(data));

        str_f_outros = "" + buscar_pedidos.f_grupo_outros(LerArquivos.StrDataBanco(data));
        str_f_total = "" + buscar_pedidos.f_grupo_total(LerArquivos.StrDataBanco(data));
//		str_f_total = "" + (Float.parseFloat(str_f_gpr1) + Float.parseFloat(str_f_gpr2) + Float.parseFloat(str_f_outros));

        buscar_pedidos.close();

        Date d = new Date();



        try {
            FileOutputStream fOut = new FileOutputStream(AbrirArquivosExterno(name));
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.append("" + StrComDireita("_", "_", 110));
            osw.append("\n");
            osw.append("" + StrComDireita("RESUMO DO DIA", " ", 83));
            osw.append("" + StrComDireita("DATA: " + StrDataVisual(data) + " - " +
                    d.getHours() + ":" + d.getMinutes(), " ", 27));
            osw.append("\n");
            osw.append("" + StrComDireita("VENDEDOR: " + cod_vendedor + " - " + nome_vendedor, " ", 110));
            osw.append("\n");
            osw.append("" + StrComDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + StrComDireita("FAMILIA", " ", 41));
            osw.append("" + StrComDireita("VOLUME", " ", 17));
            osw.append("" + StrComDireita("FATURAMENTO", " ", 17));
            osw.append("" + StrComDireita("CLIENTES", " ", 35));
            osw.append("\n");
            osw.append("" + StrComDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_1, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr1, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr1, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr1, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_sgrp_11, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr11, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr11, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr11, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_sgrp_12, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr12, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr12, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr12, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_sgrp_13, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr13, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr13, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr13, " ", 35));
            osw.append("\n");
            osw.append("" + StrComDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_2, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr2, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr2, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr2, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_21, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr21, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr21, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr21, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_22, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr22, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr22, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr22, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_23, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr23, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr23, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr23, " ", 35));
            osw.append("\n");
            osw.append("" + StrComDireita("-", "-", 110));
            osw.append("\n");

            osw.append("" + StrComDireita(lbl_grp_3, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr3, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr3, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr3, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_31, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr31, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr31, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr31, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_32, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr32, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr32, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr32, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_33, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr33, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr33, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr33, " ", 35));
            osw.append("\n");
            osw.append("" + StrComDireita("-", "-", 110));
            osw.append("\n");

            osw.append("" + StrComDireita("OUTROS", " ", 41));
            osw.append("" + StrComDireita(str_v_outros, " ", 17));
            osw.append("" + StrComDireita(str_f_outros, " ", 17));
            osw.append("" + StrComDireita(str_c_outros, " ", 35));
            osw.append("\n");
            osw.append("" + StrComDireita("_", "_", 110));
            osw.append("\n");
            osw.append("" + StrComDireita("TOTAL", " ", 41));
            osw.append("" + StrComDireita(str_v_total, " ", 17));
            osw.append("" + StrComDireita(str_f_total, " ", 17));
            osw.append("" + StrComDireita(str_c_total, " ", 35));
            osw.append("\n");

            osw.close();
            fOut.close();
        } catch (Exception e) {

        }
        */
    }

    public void criar_graficos(String name, String data, int vendedor, String nome)
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();
        Graficos grafico = new Graficos(this.context);
        grafico.gerarGraficos();
        this.nomeArquivo = name;

        Date today = new Date();
        SimpleDateFormat sf;
        int month = today.getMonth();
        String month_name;
        String month_name2;
        String month_name3;
        sf = new SimpleDateFormat("dd/MM/yyyy");

        switch(month)
        {
            case 0 :
                month_name = "JANEIRO";
                month_name2 = "DEZEMBRO";
                month_name3 = "NOVEMBRO";
                break;
            case 1 :
                month_name = "FEVEREIRO";
                month_name2 = "JANEIRO";
                month_name3 = "DEZEMBRO";
                break;
            case 2 :
                month_name = "MARÇO";
                month_name2 = "FEVEREIRO";
                month_name3 = "JANEIRO";
                break;
            case 3 :
                month_name = "ABRIL";
                month_name2 = "MARÇO";
                month_name3 = "FEVEREIRO";
                break;
            case 4 :
                month_name = "MAIO";
                month_name2 = "ABRIL";
                month_name3 = "MARÇO";
                break;
            case 5 :
                month_name = "JUNHO";
                month_name2 = "MAIO";
                month_name3 = "ABRIL";
                break;
            case 6 :
                month_name = "JULHO";
                month_name2 = "JUNHO";
                month_name3 = "MAIO";
                break;
            case 7 :
                month_name = "AGOSTO";
                month_name2 = "JULHO";
                month_name3 = "JUNHO";
                break;
            case 8 :
                month_name = "SETEMBRO";
                month_name2 = "AGOSTO";
                month_name3 = "JULHO";
                break;
            case 9 :
                month_name = "OUTUBRO";
                month_name2 = "SETEMBRO";
                month_name3 = "AGOSTO";
                break;
            case 10 :
                month_name = "NOVEMBRO";
                month_name2 = "OUTUBRO";
                month_name3 = "SETEMBRO";
                break;
            case 11 :
                month_name = "DEZEMBRO";
                month_name2 = "NOVEMBRO";
                month_name3 = "OUTRUBRO";
                break;
            default :
                month_name = "DEZEMBRO";
                month_name2 = "NOVEMBRO";
                month_name3 = "OUTRUBRO";
                break;
        }

        try
        {
            FileOutputStream fOut = new FileOutputStream(AbrirArquivosExterno());
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.append(ms.comDireita("_", "_", 110));
            osw.append("\n");
            osw.append(ms.comDireita("GRAFCICOS", " ", 75));
            osw.append(ms.comDireita("DATA: " + ms.dataVisual(data), " ", 35));
            osw.append("\n");
            osw.append(ms.comDireita("VENDEDOR: " + vendedor + " - " + nome, " ", 110));
            osw.append("\n");
            osw.append(ms.comDireita("-", "-", 110));
            osw.append("\n");

            osw.append(ms.comDireita("TOTAL DE PEDIDOS", " ", 30));
            osw.append("\n");
            osw.append(ms.comDireita(month_name, " ", 15));
            osw.append(ms.comDireita(month_name2, " ", 15));
            osw.append(ms.comDireita(month_name3, " ", 15));
            osw.append("\n");
            osw.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(1, 1)), "0", 10), " ", 15));
            osw.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(1, 2)), "0", 10), " ", 15));
            osw.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(1, 3)), "0", 10), " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");

            osw.append("" + ms.comDireita("CLIENTES MOVIMENTADOS", " ", 30));
            osw.append("\n");
            osw.append("" + ms.comDireita(month_name, " ", 15));
            osw.append("" + ms.comDireita(month_name2, " ", 15));
            osw.append("" + ms.comDireita(month_name3, " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(2, 1)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(2, 2)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(2, 3)), "0", 10), " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");

            osw.append("" + ms.comDireita("PEDIDO MEDIO", " ", 30));
            osw.append("\n");
            osw.append("" + ms.comDireita(month_name, " ", 15));
            osw.append("" + ms.comDireita(month_name2, " ", 15));
            osw.append("" + ms.comDireita(month_name3, " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(3, 1)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(3, 2)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(3, 3)), "0", 10), " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");

            osw.append("" + ms.comDireita("MEDIA ITENS POR PEDIDO", " ", 30));
            osw.append("\n");
            osw.append("" + ms.comDireita(month_name, " ", 15));
            osw.append("" + ms.comDireita(month_name2, " ", 15));
            osw.append("" + ms.comDireita(month_name3, " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(4, 1)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(4, 2)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(4, 3)), "0", 10), " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");

            osw.append("" + ms.comDireita("FREQUENCIA DE COMPRAS", " ", 30));
            osw.append("\n");
            osw.append("" + ms.comDireita(month_name, " ", 15));
            osw.append("" + ms.comDireita(month_name2, " ", 15));
            osw.append("" + ms.comDireita(month_name3, " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(5, 1)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(5, 2)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(5, 3)), "0", 10), " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");

            osw.append("" + ms.comDireita("ACELERADOR (MED. ITENS + FREQUENCIA)", " ", 50));
            osw.append("\n");
            osw.append("" + ms.comDireita(month_name, " ", 15));
            osw.append("" + ms.comDireita(month_name2, " ", 15));
            osw.append("" + ms.comDireita(month_name3, " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(6, 1)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(6, 2)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(6, 3)), "0", 10), " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita("-", "-", 110));
            osw.append("\n");

            osw.append("" + ms.comDireita("PREÇO MÉDIO", " ", 30));
            osw.append("\n");
            osw.append("" + ms.comDireita(month_name, " ", 15));
            osw.append("" + ms.comDireita(month_name2, " ", 15));
            osw.append("" + ms.comDireita(month_name3, " ", 15));
            osw.append("\n");
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(7, 1)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(7, 2)), "0", 10), " ", 15));
            osw.append("" + ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(7, 3)), "0", 10), " ", 15));
            osw.append("\n");

            osw.append("" + ms.comDireita("_", "_", 110));

            osw.close();
            fOut.close();
        }
        catch (Exception e) { /*****/ }
    }

    public String plano_visitas(String name, int vendedor, String nome)
    {
        PlanoVisitas plano = new PlanoVisitas(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();

        Date today = new Date();
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");
        String data = sf.format(today);

        ArrayList<String> lPlano =  plano.gerarPlano(data);
        ArrayList<GenericItemFour> np =  plano.listarNaoPositivados(data);

        StringBuilder sb = new StringBuilder();

        try
        {
            this.nomeArquivo = name;

            sb.append(ms.comDireita("_", "_", 110));
            sb.append("\n");
            sb.append(ms.comDireita("PLANO DE VISISTA", " ", 84));
            sb.append(ms.comDireita("DATA: " + data, " ", 26));
            sb.append("\n");
            sb.append(ms.comDireita("VENDEDOR: " + vendedor + " - " + nome, " ", 84));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");
            sb.append(ms.comDireita("DIA", " ", 12));
            sb.append(ms.comDireita("CLIENTES", " ", 10));
            sb.append(ms.comDireita("POSITIV", " ", 12));
            sb.append(ms.comDireita("%", " ", 9));
            sb.append(ms.comDireita("F.DIA", " ", 9));
            sb.append(ms.comDireita("JUSTIF", " ", 11));
            sb.append(ms.comDireita("NAO POSIT", " ", 13));
            sb.append(ms.comDireita("FATURAMENTO", " ", 17));
            sb.append(ms.comDireita("VOLUME", " ", 17));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");
            sb.append(ms.comDireita("SEGUNDA", " ", 15));
            sb.append(ms.comDireita(lPlano.get(0), " ", 9));
            sb.append(ms.comDireita(lPlano.get(1), " ", 9));
            sb.append(ms.comDireita(lPlano.get(2) /*valor_percentual(Formatacao.format3(f_perc_cli_seg, 2))*/, " ", 11));
            sb.append(ms.comDireita(lPlano.get(3), " ", 10));
            sb.append(ms.comDireita(lPlano.get(4), " ", 10));
            sb.append(ms.comDireita(lPlano.get(5), " ", 12));
            sb.append(ms.comDireita(lPlano.get(6), " ", 17));
            sb.append(ms.comDireita(lPlano.get(7), " ", 17));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");
            sb.append(ms.comDireita("TERCA", " ", 15));
            sb.append(ms.comDireita(lPlano.get(8), " ", 9));
            sb.append(ms.comDireita(lPlano.get(9), " ", 9));
            sb.append(ms.comDireita(lPlano.get(10) /*valor_percentual(Formatacao.format3(f_perc_cli_ter, 2))*/, " ", 11));
            sb.append(ms.comDireita(lPlano.get(11), " ", 10));
            sb.append(ms.comDireita(lPlano.get(12), " ", 10));
            sb.append(ms.comDireita(lPlano.get(13), " ", 12));
            sb.append(ms.comDireita(lPlano.get(14), " ", 17));
            sb.append(ms.comDireita(lPlano.get(15), " ", 17));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");
            sb.append(ms.comDireita("QUARTA", " ", 15));
            sb.append(ms.comDireita(lPlano.get(16), " ", 9));
            sb.append(ms.comDireita(lPlano.get(17), " ", 9));
            sb.append(ms.comDireita(lPlano.get(18) /*valor_percentual(Formatacao.format3(f_perc_cli_qua, 2))*/, " ", 11));
            sb.append(ms.comDireita(lPlano.get(19), " ", 10));
            sb.append(ms.comDireita(lPlano.get(20), " ", 10));
            sb.append(ms.comDireita(lPlano.get(21), " ", 12));
            sb.append(ms.comDireita(lPlano.get(22), " ", 17));
            sb.append(ms.comDireita(lPlano.get(23), " ", 17));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");
            sb.append(ms.comDireita("QUINTA", " ", 15));
            sb.append(ms.comDireita(lPlano.get(24), " ", 9));
            sb.append(ms.comDireita(lPlano.get(25), " ", 9));
            sb.append(ms.comDireita(lPlano.get(26) /*valor_percentual(Formatacao.format3(f_perc_cli_qui, 2))*/, " ", 11));
            sb.append(ms.comDireita(lPlano.get(27), " ", 10));
            sb.append(ms.comDireita(lPlano.get(28), " ", 10));
            sb.append(ms.comDireita(lPlano.get(29), " ", 12));
            sb.append(ms.comDireita(lPlano.get(30), " ", 17));
            sb.append(ms.comDireita(lPlano.get(31), " ", 17));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");
            sb.append(ms.comDireita("SEXTA", " ", 15));
            sb.append(ms.comDireita(lPlano.get(32), " ", 9));
            sb.append(ms.comDireita(lPlano.get(33), " ", 9));
            sb.append(ms.comDireita(lPlano.get(34) /*valor_percentual(Formatacao.format3(f_perc_cli_sex, 2))*/, " ", 11));
            sb.append(ms.comDireita(lPlano.get(35), " ", 10));
            sb.append(ms.comDireita(lPlano.get(36), " ", 10));
            sb.append(ms.comDireita(lPlano.get(37), " ", 12));
            sb.append(ms.comDireita(lPlano.get(38), " ", 17));
            sb.append(ms.comDireita(lPlano.get(39), " ", 17));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");
            sb.append(ms.comDireita("SABADO", " ", 15));
            sb.append(ms.comDireita(lPlano.get(40), " ", 9));
            sb.append(ms.comDireita(lPlano.get(41), " ", 9));
            sb.append(ms.comDireita(lPlano.get(42) /*valor_percentual(Formatacao.format3(f_perc_cli_sab, 2))*/, " ", 11));
            sb.append(ms.comDireita(lPlano.get(43), " ", 10));
            sb.append(ms.comDireita(lPlano.get(44), " ", 10));
            sb.append(ms.comDireita(lPlano.get(45), " ", 12));
            sb.append(ms.comDireita(lPlano.get(46), " ", 17));
            sb.append(ms.comDireita(lPlano.get(47), " ", 17));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");
            sb.append(ms.comDireita("SEM VISITA", " ", 15));
            sb.append(ms.comDireita(lPlano.get(48), " ", 9));
            sb.append(ms.comDireita(lPlano.get(49), " ", 9));
            sb.append(ms.comDireita(lPlano.get(50) /*valor_percentual(Formatacao.format3(f_perc_cli_sv, 2))*/, " ", 11));
            sb.append(ms.comDireita(lPlano.get(51), " ", 10));
            sb.append(ms.comDireita(lPlano.get(52), " ", 10));
            sb.append(ms.comDireita(lPlano.get(53), " ", 12));
            sb.append(ms.comDireita(lPlano.get(54), " ", 17));
            sb.append(ms.comDireita(lPlano.get(55), " ", 17));
            sb.append("\n");
            sb.append(ms.comDireita("_", "_", 110));
            sb.append("\n");
            sb.append(ms.comDireita("TOTAL", " ", 15));
            sb.append(ms.comDireita(lPlano.get(56), " ", 9));
            sb.append(ms.comDireita(lPlano.get(57), " ", 9));
            sb.append(ms.comDireita(lPlano.get(58) /*valor_percentual(Formatacao.format3(f_perc_cli_tot, 2))*/, " ", 11));
            sb.append(ms.comDireita(lPlano.get(59), " ", 10));
            sb.append(ms.comDireita(lPlano.get(60), " ", 10));
            sb.append(ms.comDireita(lPlano.get(61), " ", 12));
            sb.append(ms.comDireita(lPlano.get(62), " ", 17));
            sb.append(ms.comDireita(lPlano.get(63), " ", 17));
            sb.append("\n");
            sb.append("\n");
            sb.append("\n");
            sb.append(ms.comDireita("_", "_", 110));
            sb.append("\n");
            sb.append(ms.comDireita("_", "_", 110));
            sb.append("\n");
            sb.append(ms.comDireita("CLIENTE NAO POSITIVADOS", " ", 110));

            if(np.size() < 1)
            {
                sb.append("\n");
                sb.append("\n");
                sb.append(ms.comDireita(ms.comEsquerda("NAO EXISTEM CLIENTES NAO POSITIVADOS", "*", 74), "*", 110));
                sb.append("\n");
            }
            else
            {
                for (int i = 0; i < np.size(); i++)
                {
                    sb.append("\n");
                    sb.append("\n");
                    sb.append(ms.comDireita("" + np.get(i).getDataOne(), " ", 10));
                    sb.append(ms.comDireita("" + np.get(i).getDataTwo(), " ", 40));
                    sb.append(ms.comDireita("" + np.get(i).getDataThre(), " ", 25));
                    sb.append(ms.comDireita(ms.dataVisual(np.get(i).getDataFour()), " ", 10));
                }
            }
        }
        catch (Exception e) { /*****/ }

        return sb.toString();
    }

    public String produtos_foco(String name, String data, int vendedor, String nome)
    {
        ConsultaFoco foco = new ConsultaFoco(this.context);
        ManipulacaoStrings ms = new ManipulacaoStrings();

        Date today = new Date();
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");

        ArrayList<Foco> lista_foco;
        lista_foco = foco.buscarFoco();

        StringBuilder sb = new StringBuilder();

        try
        {
            sb.append(ms.comDireita("_", "_", 110));
            sb.append("\n");
            sb.append(ms.comDireita("PRODUTO FOCO - PRODUTO", " ", 75));
            sb.append(ms.comDireita("DATA: " + ms.dataVisual(data), " ", 35));
            sb.append("\n");
            sb.append(ms.comDireita("VENDEDOR: " + vendedor + " - " + nome, " ", 110));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");
            sb.append(ms.comDireita("PRODUTO", " ", 14));
            sb.append(ms.comDireita("DESCRICAO", " ", 34));
            sb.append(ms.comDireita("VOLUME", " ", 17));
            sb.append(ms.comDireita("FATURAMENTO", " ", 17));
            sb.append(ms.comDireita("CLIENTES", " ", 29));
            sb.append("\n");

            //ESSA PARTE TEM UM FOR PARA TODOS OS PRODUTOS FOCO
            float f_Tvolume = 0;
            float f_Tfaturamento = 0;
            int i_Tclientes = 0;

            for (int i = 0; i < lista_foco.size(); i++)
            {
                Foco prod = lista_foco.get(i);

                f_Tvolume += prod.getVolume();
                f_Tfaturamento += prod.getValor();
                i_Tclientes += prod.getClientes();

                sb.append(ms.comDireita("-", "-", 110));
                sb.append("\n");
                sb.append(ms.comDireita(prod.getReferencia(), " ", 14));
                sb.append(ms.comDireita(prod.getDescricao(), " ", 34));
                sb.append(ms.comDireita(String.valueOf(prod.getVolume()), " ", 17));
                sb.append(ms.comDireita(String.valueOf(prod.getValor()), " ", 17));
                sb.append(ms.comDireita(String.valueOf(prod.getClientes()), " ", 29));
                sb.append("\n");
            }

            //ATÉ AQUI VEM O FOR...
            sb.append(ms.comDireita("_", "_", 110));
            sb.append("\n");
            sb.append(ms.comDireita("TOTAL", " ", 48));
            sb.append(ms.comDireita(String.valueOf(f_Tvolume), " ", 17));
            sb.append(ms.comDireita(String.valueOf(f_Tfaturamento), " ", 17));
            sb.append(ms.comDireita(String.valueOf(i_Tclientes), " ", 29));
        }
        catch (Exception e) { /*****/ }

        return sb.toString();
    }

    public String resumo_dia(String name, String data, int vendedor, String nome)
    {
        StringBuilder sb = new StringBuilder();
        /*
        int cod_vendedor;
        String nome_vendedor;
        Inserir_Pedidos buscar_pedidos;
//		ArrayList<Pedido> lista_pedidos;

        Date today = new Date();
        SimpleDateFormat sf;
        sf = new SimpleDateFormat("dd/MM/yyyy");

        buscar_pedidos = new Inserir_Pedidos(contexto);

        String lbl_grp_1;
        String lbl_sgrp_11;
        String lbl_sgrp_12;
        String lbl_sgrp_13;
        String lbl_grp_2;
        String lbl_grp_21;
        String lbl_grp_22;
        String lbl_grp_23;
        String lbl_grp_3;
        String lbl_grp_31;
        String lbl_grp_32;
        String lbl_grp_33;

        String str_v_gpr1;
        String str_v_gpr11;
        String str_v_gpr12;
        String str_v_gpr13;
        String str_v_gpr2;
        String str_v_gpr21;
        String str_v_gpr22;
        String str_v_gpr23;
        String str_v_gpr3;
        String str_v_gpr31;
        String str_v_gpr32;
        String str_v_gpr33;

        String str_f_gpr1;
        String str_f_gpr11;
        String str_f_gpr12;
        String str_f_gpr13;
        String str_f_gpr2;
        String str_f_gpr21;
        String str_f_gpr22;
        String str_f_gpr23;
        String str_f_gpr3;
        String str_f_gpr31;
        String str_f_gpr32;
        String str_f_gpr33;

        String str_c_gpr1;
        String str_c_gpr11;
        String str_c_gpr12;
        String str_c_gpr13;
        String str_c_gpr2;
        String str_c_gpr21;
        String str_c_gpr22;
        String str_c_gpr23;
        String str_c_gpr3;
        String str_c_gpr31;
        String str_c_gpr32;
        String str_c_gpr33;

        String str_v_outros;
        String str_v_total;
        String str_f_outros;
        String str_f_total;
        String str_c_outros;
        String str_c_total;


        buscar_pedidos.open();
        cod_vendedor = buscar_pedidos.getCodVendedor();
        nome_vendedor = buscar_pedidos.getNomeVendedor();
        buscar_pedidos.close();

        buscar_pedidos.open();
//		lista_pedidos = buscar_pedidos.pedidos_reenviar(data, data);
        lbl_grp_1 = buscar_pedidos.grupo_1();
        lbl_grp_2 = buscar_pedidos.grupo_2();
        lbl_grp_3 = buscar_pedidos.grupo_3();

        lbl_sgrp_11 = buscar_pedidos.grupo_11();
        lbl_sgrp_12 = buscar_pedidos.grupo_12();
        lbl_sgrp_13 = buscar_pedidos.grupo_13();

        lbl_grp_21 = buscar_pedidos.grupo_21();
        lbl_grp_22 = buscar_pedidos.grupo_22();
        lbl_grp_23 = buscar_pedidos.grupo_23();

        lbl_grp_31 = buscar_pedidos.grupo_31();
        lbl_grp_32 = buscar_pedidos.grupo_32();
        lbl_grp_33 = buscar_pedidos.grupo_33();

        str_c_gpr1 = "" + buscar_pedidos.c_grupo_1(LerArquivos.StrDataBanco(data));
        str_c_gpr11 = "" + buscar_pedidos.c_grupo_11(LerArquivos.StrDataBanco(data));
        str_c_gpr12 = "" + buscar_pedidos.c_grupo_12(LerArquivos.StrDataBanco(data));
        str_c_gpr13 = "" + buscar_pedidos.c_grupo_13(LerArquivos.StrDataBanco(data));
        str_c_gpr2 = "" + buscar_pedidos.c_grupo_2(LerArquivos.StrDataBanco(data));
        str_c_gpr21 = "" + buscar_pedidos.c_grupo_21(LerArquivos.StrDataBanco(data));
        str_c_gpr22 = "" + buscar_pedidos.c_grupo_22(LerArquivos.StrDataBanco(data));
        str_c_gpr23 = "" + buscar_pedidos.c_grupo_23(LerArquivos.StrDataBanco(data));
        str_c_gpr3 = "" + buscar_pedidos.c_grupo_3(LerArquivos.StrDataBanco(data));
        str_c_gpr31 = "" + buscar_pedidos.c_grupo_31(LerArquivos.StrDataBanco(data));
        str_c_gpr32 = "" + buscar_pedidos.c_grupo_32(LerArquivos.StrDataBanco(data));
        str_c_gpr33 = "" + buscar_pedidos.c_grupo_33(LerArquivos.StrDataBanco(data));

        str_v_gpr1 = "" + buscar_pedidos.v_grupo_1(LerArquivos.StrDataBanco(data));
        str_v_gpr11 = "" + buscar_pedidos.v_grupo_11(LerArquivos.StrDataBanco(data));
        str_v_gpr12 = "" + buscar_pedidos.v_grupo_12(LerArquivos.StrDataBanco(data));
        str_v_gpr13 = "" + buscar_pedidos.v_grupo_13(LerArquivos.StrDataBanco(data));
        str_v_gpr2 = "" + buscar_pedidos.v_grupo_2(LerArquivos.StrDataBanco(data));
        str_v_gpr21 = "" + buscar_pedidos.v_grupo_21(LerArquivos.StrDataBanco(data));
        str_v_gpr22 = "" + buscar_pedidos.v_grupo_22(LerArquivos.StrDataBanco(data));
        str_v_gpr23 = "" + buscar_pedidos.v_grupo_23(LerArquivos.StrDataBanco(data));
        str_v_gpr3 = "" + buscar_pedidos.v_grupo_3(LerArquivos.StrDataBanco(data));
        str_v_gpr31 = "" + buscar_pedidos.v_grupo_31(LerArquivos.StrDataBanco(data));
        str_v_gpr32 = "" + buscar_pedidos.v_grupo_32(LerArquivos.StrDataBanco(data));
        str_v_gpr33 = "" + buscar_pedidos.v_grupo_33(LerArquivos.StrDataBanco(data));

        str_f_gpr1 = "" + buscar_pedidos.f_grupo_1(LerArquivos.StrDataBanco(data));
        str_f_gpr11 = "" + buscar_pedidos.f_grupo_11(LerArquivos.StrDataBanco(data));
        str_f_gpr12 = "" + buscar_pedidos.f_grupo_12(LerArquivos.StrDataBanco(data));
        str_f_gpr13 = "" + buscar_pedidos.f_grupo_13(LerArquivos.StrDataBanco(data));
        str_f_gpr2 = "" + buscar_pedidos.f_grupo_2(LerArquivos.StrDataBanco(data));
        str_f_gpr21 = "" + buscar_pedidos.f_grupo_21(LerArquivos.StrDataBanco(data));
        str_f_gpr22 = "" + buscar_pedidos.f_grupo_22(LerArquivos.StrDataBanco(data));
        str_f_gpr23 = "" + buscar_pedidos.f_grupo_23(LerArquivos.StrDataBanco(data));
        str_f_gpr3 = "" + buscar_pedidos.f_grupo_3(LerArquivos.StrDataBanco(data));
        str_f_gpr31 = "" + buscar_pedidos.f_grupo_31(LerArquivos.StrDataBanco(data));
        str_f_gpr32 = "" + buscar_pedidos.f_grupo_32(LerArquivos.StrDataBanco(data));
        str_f_gpr33 = "" + buscar_pedidos.f_grupo_33(LerArquivos.StrDataBanco(data));

        str_c_outros = "" + buscar_pedidos.c_grupo_outros(LerArquivos.StrDataBanco(data));
        str_c_total = "" + buscar_pedidos.c_grupo_total(LerArquivos.StrDataBanco(data));

        str_v_outros = "" + buscar_pedidos.v_grupo_outros(LerArquivos.StrDataBanco(data));
        str_v_total = "" + buscar_pedidos.v_total(LerArquivos.StrDataBanco(data));

        str_f_outros = "" + buscar_pedidos.f_grupo_outros(LerArquivos.StrDataBanco(data));
        str_f_total = "" + buscar_pedidos.f_grupo_total(LerArquivos.StrDataBanco(data));
//		str_f_total = "" + (Float.parseFloat(str_f_gpr1) + Float.parseFloat(str_f_gpr2) + Float.parseFloat(str_f_outros));

        buscar_pedidos.close();

        Date d = new Date();



        try {
            FileOutputStream fOut = new FileOutputStream(AbrirArquivosExterno(name));
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.append("" + StrComDireita("_", "_", 110));
            osw.append("\n");
            osw.append("" + StrComDireita("RESUMO DO DIA", " ", 83));
            osw.append("" + StrComDireita("DATA: " + StrDataVisual(data) + " - " +
                    d.getHours() + ":" + d.getMinutes(), " ", 27));
            osw.append("\n");
            osw.append("" + StrComDireita("VENDEDOR: " + cod_vendedor + " - " + nome_vendedor, " ", 110));
            osw.append("\n");
            osw.append("" + StrComDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + StrComDireita("FAMILIA", " ", 41));
            osw.append("" + StrComDireita("VOLUME", " ", 17));
            osw.append("" + StrComDireita("FATURAMENTO", " ", 17));
            osw.append("" + StrComDireita("CLIENTES", " ", 35));
            osw.append("\n");
            osw.append("" + StrComDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_1, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr1, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr1, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr1, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_sgrp_11, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr11, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr11, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr11, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_sgrp_12, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr12, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr12, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr12, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_sgrp_13, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr13, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr13, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr13, " ", 35));
            osw.append("\n");
            osw.append("" + StrComDireita("-", "-", 110));
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_2, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr2, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr2, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr2, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_21, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr21, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr21, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr21, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_22, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr22, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr22, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr22, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_23, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr23, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr23, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr23, " ", 35));
            osw.append("\n");
            osw.append("" + StrComDireita("-", "-", 110));
            osw.append("\n");

            osw.append("" + StrComDireita(lbl_grp_3, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr3, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr3, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr3, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_31, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr31, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr31, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr31, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_32, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr32, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr32, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr32, " ", 35));
            osw.append("\n");
            osw.append("\n");
            osw.append("" + StrComDireita(lbl_grp_33, " ", 41));
            osw.append("" + StrComDireita(str_v_gpr33, " ", 17));
            osw.append("" + StrComDireita(str_f_gpr33, " ", 17));
            osw.append("" + StrComDireita(str_c_gpr33, " ", 35));
            osw.append("\n");
            osw.append("" + StrComDireita("-", "-", 110));
            osw.append("\n");

            osw.append("" + StrComDireita("OUTROS", " ", 41));
            osw.append("" + StrComDireita(str_v_outros, " ", 17));
            osw.append("" + StrComDireita(str_f_outros, " ", 17));
            osw.append("" + StrComDireita(str_c_outros, " ", 35));
            osw.append("\n");
            osw.append("" + StrComDireita("_", "_", 110));
            osw.append("\n");
            osw.append("" + StrComDireita("TOTAL", " ", 41));
            osw.append("" + StrComDireita(str_v_total, " ", 17));
            osw.append("" + StrComDireita(str_f_total, " ", 17));
            osw.append("" + StrComDireita(str_c_total, " ", 35));
            osw.append("\n");

            osw.close();
            fOut.close();
        } catch (Exception e) {

        }
        */

        return sb.toString();
    }

    public String graficos(String name, String data, int vendedor, String nome)
    {
        ManipulacaoStrings ms = new ManipulacaoStrings();
        Graficos grafico = new Graficos(this.context);
        grafico.gerarGraficos();
        this.nomeArquivo = name;

        Date today = new Date();
        SimpleDateFormat sf;
        int month = today.getMonth();
        String month_name;
        String month_name2;
        String month_name3;
        sf = new SimpleDateFormat("dd/MM/yyyy");

        StringBuilder sb = new StringBuilder();

        switch(month)
        {
            case 0 :
                month_name = "JANEIRO";
                month_name2 = "DEZEMBRO";
                month_name3 = "NOVEMBRO";
                break;
            case 1 :
                month_name = "FEVEREIRO";
                month_name2 = "JANEIRO";
                month_name3 = "DEZEMBRO";
                break;
            case 2 :
                month_name = "MARÇO";
                month_name2 = "FEVEREIRO";
                month_name3 = "JANEIRO";
                break;
            case 3 :
                month_name = "ABRIL";
                month_name2 = "MARÇO";
                month_name3 = "FEVEREIRO";
                break;
            case 4 :
                month_name = "MAIO";
                month_name2 = "ABRIL";
                month_name3 = "MARÇO";
                break;
            case 5 :
                month_name = "JUNHO";
                month_name2 = "MAIO";
                month_name3 = "ABRIL";
                break;
            case 6 :
                month_name = "JULHO";
                month_name2 = "JUNHO";
                month_name3 = "MAIO";
                break;
            case 7 :
                month_name = "AGOSTO";
                month_name2 = "JULHO";
                month_name3 = "JUNHO";
                break;
            case 8 :
                month_name = "SETEMBRO";
                month_name2 = "AGOSTO";
                month_name3 = "JULHO";
                break;
            case 9 :
                month_name = "OUTUBRO";
                month_name2 = "SETEMBRO";
                month_name3 = "AGOSTO";
                break;
            case 10 :
                month_name = "NOVEMBRO";
                month_name2 = "OUTUBRO";
                month_name3 = "SETEMBRO";
                break;
            case 11 :
                month_name = "DEZEMBRO";
                month_name2 = "NOVEMBRO";
                month_name3 = "OUTRUBRO";
                break;
            default :
                month_name = "DEZEMBRO";
                month_name2 = "NOVEMBRO";
                month_name3 = "OUTRUBRO";
                break;
        }

        try {
            sb.append(ms.comDireita("_", "_", 110));
            sb.append("\n");
            sb.append(ms.comDireita("GRAFCICOS", " ", 75));
            sb.append(ms.comDireita("DATA: " + ms.dataVisual(data), " ", 35));
            sb.append("\n");
            sb.append(ms.comDireita("VENDEDOR: " + vendedor + " - " + nome, " ", 110));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");

            sb.append(ms.comDireita("TOTAL DE PEDIDOS", " ", 30));
            sb.append("\n");
            sb.append(ms.comDireita(month_name, " ", 15));
            sb.append(ms.comDireita(month_name2, " ", 15));
            sb.append(ms.comDireita(month_name3, " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(1, 1)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(1, 2)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(1, 3)), "0", 10), " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");

            sb.append(ms.comDireita("CLIENTES MOVIMENTADOS", " ", 30));
            sb.append("\n");
            sb.append(ms.comDireita(month_name, " ", 15));
            sb.append(ms.comDireita(month_name2, " ", 15));
            sb.append(ms.comDireita(month_name3, " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(2, 1)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(2, 2)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(2, 3)), "0", 10), " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");

            sb.append(ms.comDireita("PEDIDO MEDIO", " ", 30));
            sb.append("\n");
            sb.append(ms.comDireita(month_name, " ", 15));
            sb.append(ms.comDireita(month_name2, " ", 15));
            sb.append(ms.comDireita(month_name3, " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(3, 1)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(3, 2)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(3, 3)), "0", 10), " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");

            sb.append(ms.comDireita("MEDIA ITENS POR PEDIDO", " ", 30));
            sb.append("\n");
            sb.append(ms.comDireita(month_name, " ", 15));
            sb.append(ms.comDireita(month_name2, " ", 15));
            sb.append(ms.comDireita(month_name3, " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(4, 1)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(4, 2)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(4, 3)), "0", 10), " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");

            sb.append(ms.comDireita("FREQUENCIA DE COMPRAS", " ", 30));
            sb.append("\n");
            sb.append(ms.comDireita(month_name, " ", 15));
            sb.append(ms.comDireita(month_name2, " ", 15));
            sb.append(ms.comDireita(month_name3, " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(5, 1)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(5, 2)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(5, 3)), "0", 10), " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");

            sb.append(ms.comDireita("ACELERADOR (MED. ITENS + FREQUENCIA)", " ", 50));
            sb.append("\n");
            sb.append(ms.comDireita(month_name, " ", 15));
            sb.append(ms.comDireita(month_name2, " ", 15));
            sb.append(ms.comDireita(month_name3, " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(6, 1)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(6, 2)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(6, 3)), "0", 10), " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita("-", "-", 110));
            sb.append("\n");

            sb.append(ms.comDireita("PREÇO MÉDIO", " ", 30));
            sb.append("\n");
            sb.append(ms.comDireita(month_name, " ", 15));
            sb.append(ms.comDireita(month_name2, " ", 15));
            sb.append(ms.comDireita(month_name3, " ", 15));
            sb.append("\n");
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(7, 1)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(7, 2)), "0", 10), " ", 15));
            sb.append(ms.comDireita(ms.comEsquerda(String.valueOf(grafico.percentualGrafico(7, 3)), "0", 10), " ", 15));
            sb.append("\n");

            sb.append(ms.comDireita("_", "_", 110));
        }
        catch (Exception e) { /*****/ }

        return sb.toString();
    }
}
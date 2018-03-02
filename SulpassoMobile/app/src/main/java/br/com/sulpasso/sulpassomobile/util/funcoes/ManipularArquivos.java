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
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.ClienteNovo;
import br.com.sulpasso.sulpassomobile.modelo.Configurador;
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

        if(version == 19)
        {
            f = new File("/storage/emulated/0/MobileVenda/", this.nomeArquivo);
        }
        else
        {
            f = new File(Environment.getExternalStorageDirectory() + "/MobileVenda/", this.nomeArquivo);
        }

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

                        if (nr_total_linhas_arquivo == (nr_total_linhas_encontrado - 1)){ return true; }
                        else
                        {
                            this.addStringErro("Arquivo de dados inválido.");
                            return false;
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
            return false;
            /*
            TODO: acrescentar uma flag de finalização no arquivo e retornar para o fluxo principal apenas de um ponto do arquivo
             */
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

                    for (String s : mix) {
                        try {
                            mixda.insert(s);
                        } catch (Exception e) {
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
                        try {
                            comissaoda.updateComissao(s.substring(12, 22).trim());
                            comissaoda.updateContribuicao(s.substring(22).trim());
                        } catch (Exception e) {
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
        File pasta = new File(Environment.getExternalStorageDirectory() + "/MobileVenda");
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
                builder.append(ms.comEsquerda(("" + v.getDesconto()).replace(".", "").replace(",", ""), "0", 6));
                /*
                builder.append(ms.comEsquerda(("" + Formatacao.format3(v.getDesconto(), 2)).replace(".", "").replace(",", ""), "0", 6));
                 */
                builder.append(ms.comDireita("" + v.getObservacaDesconto(), " ", 25));
                builder.append(ms.comDireita(v.getTipo(), " ", 2));
                builder.append(ms.comDireita("" + v.getObservacao(), " ", 114));
                builder.append(ms.comDireita("" + v.getObservacaoNota(), " ", 50));
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
                    if(i.getValorLiquido() < i.getValorTabela() && (i.getDescontoCG() == 0)/* &&
                            (i.getFlag_desconto_campanha() == 1) */)
                    {
                        float desconto = ((i.getValorTabela() - i.getValorLiquido()) / i.getValorTabela()) * 100;
                        i.setDescontoCG(desconto);
                    }
                    builder.delete(0, builder.length());
                    
                    builder.append("I");
                    builder.append(ms.comEsquerda("" + v.getCodigo(), "0", 7));
                    builder.append(ms.comEsquerda("" + v.getCliente().getCodigoCliente(), "0", 7));
                    builder.append(ms.comDireita("" + i.getItem(), " ", 10));
                    //builder.append(ms.comEsquerda(("" + /*(int)*/Formatacao.format2d(i.getEstoque())).replace(".", ""), "0", 6) +
                    builder.append(ms.comEsquerda(("" + (int)i.getQuantidade()).replace(".", ""), "0", 4));
                    builder.append(ms.comEsquerda(("" + i.getValorLiquido()).replace(".", "").replace(",", ""), "0", 9));
                    builder.append(ms.comEsquerda(("" + i.getValorTabela()).replace(".", "").replace(",", ""), "0", 9));
                    builder.append(ms.comEsquerda(("" + i.getDesconto()).replace(".", ""), "0", 6));
                    builder.append(ms.comDireita("0", " ", 3));
                    builder.append(ms.comEsquerda(("0").replace(".", ""), "0", 4));
                    builder.append(ms.comEsquerda(("0").replace(".", ""), "0", 9));
                    builder.append(ms.comDireita("0", "0", 1));
                    /*
                    builder.append(ms.comDireita("" + i.getIcm(), " ", 3));
                    builder.append(ms.comEsquerda(("" + i.getAliq()).replace(".", ""), "0", 4));
                    builder.append(ms.comEsquerda(("" + i.getBase()).replace(".", ""), "0", 9));
                    builder.append(ms.comDireita("" + i.getSenha_desc(), "0", 1));
                    */
                    builder.append(ms.comEsquerda("" + i.getItem(), "0", 6));
                    builder.append(ms.comEsquerda(("" +  i.getDescontoCG()).replace(".", "").replace(",", ""), "0", 5));
                    builder.append(ms.comEsquerda(("" + i.getValorDigitado()).replace(".", "").replace(",", ""), "0", 9));
                    builder.append(ms.comDireita("0", "0", 1));
                    builder.append(ms.comEsquerda("0", "0", 9));
                    builder.append(ms.comEsquerda("", "0", 6));
                    /*
                    builder.append(ms.comDireita("" + i.getFlag_desconto_campanha(), "0", 1));
                    builder.append(ms.comEsquerda(("" + Formatacao.format3(i.getValor_50(), 2)).replace(".", "").replace(",", ""), "0", 9));
                    builder.append(ms.comEsquerda(("" + Formatacao.format3(i.getQtd_belem(), 2)).replace(".", "").replace(",", ""), "0", 6));
                    */
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
                
            osw.append("XX" + ms.comEsquerda(("" + line_count), "0", 3));

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

            osw.append("9" + ms.comEsquerda(("" + line_count), "0", 6));

            osw.close();
            fOut.close();
        }
        catch (Exception e) { this.addStringErro(e.getMessage()); }
    }
}
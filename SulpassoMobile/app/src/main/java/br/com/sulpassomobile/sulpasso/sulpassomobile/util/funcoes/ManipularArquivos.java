package br.com.sulpassomobile.sulpasso.sulpassomobile.util.funcoes;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.AtividadeDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.BancoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CampanhaGrupoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CampanhaProdutoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CidadeDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ContasReceberDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CorteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CurvaAbcDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.DevolucaoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.EstoqueDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.GravososDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.GrupoBloqueadoClienteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.GrupoBloqueadoNaturezaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.GrupoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.KitDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.MensagemDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.MetaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.MixDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.MotivosDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrePedidoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrePedidoDiretaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrecoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrecosClientesDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.StatusDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.TipoVendaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.TipologiaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.util.Enumarations.Tabelas;

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

    private ArrayList<String> errosIserir = null;

    private Context context;

    public ManipularArquivos(Context context)
    {
        this.context = context;
        this.errosIserir = new ArrayList<>();
    }

    public File AbrirArquivosExterno()
    {
        return (new File(Environment.getExternalStorageDirectory() + "/MobileVenda", "PW0020.319"));
        //return (new File(Environment.getExternalStorageDirectory() + "/MobileVenda", "PW0015.492"));
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

                if (s.substring(0,2).equalsIgnoreCase("XX")){ codTabela = -1; }
                else { codTabela = Integer.parseInt(s.substring(0,2)); }

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
                    default:
                        break;
                }
            }

            strem_leitura.close();
            arquivo_leitura.close();
        }
        catch (Exception e) { }
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

                if (s.substring(0,2).equalsIgnoreCase("XX"))
                {
                    nr_total_linhas_arquivo = Integer.parseInt(s.substring(2).trim());

                    if (nr_total_linhas_arquivo == (nr_total_linhas_encontrado - 1)){ return true; }
                    else { return false; }
                }
            }

            strem_leitura.close();
            arquivo_leitura.close();
            return false;
        }
        catch (FileNotFoundException e) { return false; }
        catch (Exception e) { return false; }
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

        switch (tb)
        {
            case DEVOLUCAO :
                DevolucaoDataAccess devda = new DevolucaoDataAccess(context);
                if(devolucoes != null && devolucoes.size() > 0)
                {
                    for (String s : devolucoes) {
                        try {
                            devda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case MOTIVOS :
                MotivosDataAccess mda = new MotivosDataAccess(context);
                if(motivos != null && motivos.size() > 0)
                {
                    for (String s : motivos) {
                        try {
                            mda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case PRE_PEDIDO :
                PrePedidoDataAccess ppda = new PrePedidoDataAccess(context);
                if(prePedidos != null && prePedidos.size() > 0)
                {
                    for (String s : prePedidos) {
                        try {
                            ppda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case PRE_PEDIDO_CD :
                PrePedidoDiretaDataAccess ppdda = new PrePedidoDiretaDataAccess(context);
                if(prePedidosDiretas != null && prePedidosDiretas.size() > 0)
                {
                    for (String s : prePedidosDiretas) {
                        try {
                            ppdda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case METAS :
                MetaDataAccess metada = new MetaDataAccess(context);
                if(metas != null && metas.size() > 0)
                {
                    for (String s : metas) {
                        try {
                            metada.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case TIPOLOGIA :
                TipologiaDataAccess tipoda = new TipologiaDataAccess(context);
                if(tipologias != null && tipologias.size() > 0)
                {
                    for (String s : tipologias) {
                        try {
                            tipoda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case CTAS_RECEBER :
                ContasReceberDataAccess crda = new ContasReceberDataAccess(context);
                if(contasReceber != null && contasReceber.size() > 0)
                {
                    for (String s : contasReceber) {
                        try {
                            crda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case MIX :
                MixDataAccess mixda = new MixDataAccess(context);
                if(mix != null && mix.size() > 0)
                {
                    for (String s : mix) {
                        try {
                            mixda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case GRAVOSOS :
                GravososDataAccess gravda = new GravososDataAccess(context);
                if(gravosos != null && gravosos.size() > 0)
                {
                    for (String s : gravosos) {
                        try {
                            gravda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case MENSAGENS :
                MensagemDataAccess msgda = new MensagemDataAccess(context);
                if(mensagens != null && mensagens.size() > 0)
                {
                    for (String s : mensagens) {
                        try {
                            msgda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case TOTALIZADORES :
                CurvaAbcDataAccess abcda = new CurvaAbcDataAccess(context);
                if(curvaAbc != null && curvaAbc.size() > 0)
                {
                    for (String s : curvaAbc) {
                        try {
                            abcda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case TABELA_CLIENTE :
                PrecosClientesDataAccess pcda = new PrecosClientesDataAccess(context);
                if(precosClientes != null && precosClientes.size() > 0)
                {
                    for (String s : precosClientes) {
                        try {
                            pcda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case ATIVIDADE :
                AtividadeDataAccess atvda = new AtividadeDataAccess(context);
                if(atividades != null && atividades.size() > 0)
                {
                    for (String s : atividades) {
                        try {
                            atvda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case RESTRICAO_CLIENTE :
                GrupoBloqueadoClienteDataAccess gbcda = new GrupoBloqueadoClienteDataAccess(context);
                if(clientesRestricoes != null && clientesRestricoes.size() > 0)
                {
                    for (String s : clientesRestricoes) {
                        try {
                            gbcda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case CORTE :
                CorteDataAccess corteda = new CorteDataAccess(context);
                if(cortes != null && cortes.size() > 0)
                {
                    for (String s : cortes) {
                        try {
                            corteda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case STATUS :
                StatusDataAccess sda = new StatusDataAccess(context);
                if(status != null && status.size() > 0)
                {
                    for (String s : status) {
                        try {
                            sda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case RESTRICAO_GRUPO :
                GrupoBloqueadoNaturezaDataAccess gbnda = new GrupoBloqueadoNaturezaDataAccess(context);
                if(naturezasRestricoes != null && naturezasRestricoes.size() > 0)
                {
                    for (String s : naturezasRestricoes) {
                        try {
                            gbnda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case CLIENTES :
                ClienteDataAccess cda = new ClienteDataAccess(context);
                if(clientes != null && clientes.size() > 0)
                {
                    for (String s : clientes) {
                        try {
                            cda.inserir(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case PROMOCOES :
                PromocaoDataAccess proda = new PromocaoDataAccess(context);
                if(promocoes != null && promocoes.size() > 0)
                {
                    for (String s : promocoes) {
                        try {
                            proda.inserir(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case PRODUTO :
                ItemDataAccess ida = new ItemDataAccess(context);
                if(produtos != null && produtos.size() > 0)
                {
                    for (String s : produtos) {
                        try {
                            ida.inserir(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case CIDADE :
                CidadeDataAccess cida = new CidadeDataAccess(context);
                if(cidades != null && cidades.size() > 0)
                {
                    for (String s : cidades) {
                        try {
                            cida.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case BANCO :
                BancoDataAccess bda = new BancoDataAccess(context);
                if(bancos != null && bancos.size() > 0)
                {
                    for (String s : bancos) {
                        try {
                            bda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case TIPO_VENDA :
                TipoVendaDataAccess tvda = new TipoVendaDataAccess(context);
                if(tipos_vendas != null && tipos_vendas.size() > 0)
                {
                    for (String s : tipos_vendas) {
                        try {
                            tvda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case GRUPO :
                GrupoDataAccess gda = new GrupoDataAccess(context);
                if(grupos != null && grupos.size() > 0)
                {
                    for (String s : grupos) {
                        try {
                            gda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case NATUREZA :
                NaturezaDataAccess nda = new NaturezaDataAccess(context);
                if(naturezas != null && naturezas.size() > 0)
                {
                    for (String s : naturezas) {
                        try {
                            nda.inserir(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case KIT :
                KitDataAccess kda = new KitDataAccess(context);
                if(kits != null && kits.size() > 0)
                {
                    for (String s : kits) {
                        try {
                            kda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case PRAZOS :
                PrazoDataAccess pda = new PrazoDataAccess(context);
                if(prazos != null && prazos.size() > 0)
                {
                    for (String s : prazos) {
                        try {
                            pda.inserir(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case TABELA_PRECOS :
                PrecoDataAccess tda = new PrecoDataAccess(context);
                if(precos!= null && precos.size() > 0)
                {
                    for (String s : precos) {
                        try {
                            tda.inserir(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case ESTOQUE :
                EstoqueDataAccess eda = new EstoqueDataAccess(context);
                if(estoque != null && estoque.size() > 0)
                {
                    for (String s : estoque) {
                        try {
                            eda.inserir(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case DESC_GRUPO :
                CampanhaGrupoDataAccess cgda = new CampanhaGrupoDataAccess(context);
                if(desc_grupos != null && desc_grupos.size() > 0)
                {
                    for (String s : desc_grupos) {
                        try {
                            cgda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
                        }
                    }
                }
                break;
            case DESC_CAMP :
                CampanhaProdutoDataAccess cpda = new CampanhaProdutoDataAccess(context);
                if(desc_campanhas != null && desc_campanhas.size() > 0)
                {
                    for (String s : desc_campanhas) {
                        try {
                            cpda.insert(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
                            errosIserir.add(s);
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

    private void escreverArquivoErros()
    {
        try
        {
            FileOutputStream fOut = new FileOutputStream(
                new File(Environment.getExternalStorageDirectory() + "/MobileVenda", "ERROS.txt"));
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            for (int i = 0; i < errosIserir.size(); i++){ osw.append(errosIserir.get(i) + "\n"); }

            osw.close();
            fOut.close();
        }
        catch (Exception e) { e.printStackTrace(); }
    }
}
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
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.BancoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CampanhaGrupoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CampanhaProdutoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CidadeDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.EstoqueDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.GrupoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrecoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.TipoVendaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.util.Enumarations.Tabelas;

/**
 * Created by Lucas on 04/11/2016 - 10:01 as part of the project SulpassoMobile.
 *
 */
public class ManipularArquivos
{
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
        return (new File(Environment.getExternalStorageDirectory() + "/MobileVenda", "PW0015.450"));
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
            case CLIENTES :
                ClienteDataAccess cda = new ClienteDataAccess(context);
                if(clientes != null && clientes.size() > 0)
                {
                    for (String s : clientes) {
                        try {
                            cda.inserir(s);
                        } catch (Exception e) {
                            errosIserir.add(e.getMessage());
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
                        }
                    }
                }
                break;
            case KIT :
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
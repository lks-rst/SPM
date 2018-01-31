package br.com.sulpassomobile.sulpasso.sulpassomobile.views;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import br.com.sulpassomobile.sulpasso.sulpassomobile.R;
import br.com.sulpassomobile.sulpasso.sulpassomobile.controle.TelaInicial;
import br.com.sulpassomobile.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Banco;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.CampanhaProduto;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cidade;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Configurador;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorConexao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorEmpresa;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorHorarios;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorTelas;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorUsuario;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.ConfiguradorVendas;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Estoque;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Grupo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Preco;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.TiposVenda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.BancoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CampanhaGrupoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CampanhaProdutoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.CidadeDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ConfiguradorDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.EstoqueDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.GrupoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.ItemDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PrecoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.TipoVendaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;
import br.com.sulpassomobile.sulpasso.sulpassomobile.views.fragments.ConsultaItensKits;

/**
	Todo: verificar data e hora do sistema antes de abrir os pedidos;

    TODO: Verificar os itens que não devem ser inseridos no banco de dados;

    TODO: Verificar o retorno da tela de login encerrar o sistema caso o retorno seja falso;
*/
	
public class Inicial extends AppCompatActivity
{
    private TelaInicial controle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        this.controle = new TelaInicial(getApplicationContext());

        Configurador confg = new Configurador();
        confg.setEmpresa(new ConfiguradorEmpresa());
        confg.setUsuario(new ConfiguradorUsuario());
        confg.setVendas(new ConfiguradorVendas());
        confg.setConexao(new ConfiguradorConexao());
        confg.setHorarios(new ConfiguradorHorarios());
        confg.setTelas(new ConfiguradorTelas());

        System.out.println(confg.toString());
        System.out.println();

        ConfiguradorDataAccess config = new ConfiguradorDataAccess(this);
        try {
            config.insert(confg);
        } catch (GenercicException e) {
            e.printStackTrace();
        }

        this.fragmentoCentral();

        if(this.controle.controleAcesso())
        {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(i, 0);
        }
/*
        // Start lengthy operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (mProgressStatus < 100) {
                    mProgressStatus = doWork(mProgressStatus);
                    // Update the progress bar
                    mHandler.post(new Runnable() {
                        public void run() {
                            mProgressStatus++;
                            mProgress.setProgress(mProgressStatus);
                        }
                    });
                }
            }
        }).start();
*/

//        inserirDadosHardCoded();
//        listDataHardCoded();
//        ((TextView) findViewById(R.id.vendas)).setText(this.listDataHardCoded());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.inicial_agenda :
                break;
            case R.id.inicial_atualizar :
                Intent ia = new Intent(getApplicationContext(), Atualizacao.class);
                startActivity(ia);
                break;
            case R.id.inicial_clientes :
                break;

            case R.id.consultas_clientes :
                Intent cc = new Intent(getApplicationContext(), ConsultasClientes.class);
                startActivity(cc);
                break;
            case R.id.consultas_gerenciais :
                Intent cg = new Intent(getApplicationContext(), ConsultasGerenciais.class);
                startActivity(cg);
                break;
            case R.id.consultas_itens :
                Intent ci = new Intent(getApplicationContext(), ConsultasItens.class);
                startActivity(ci);
                break;
            case R.id.consultas_pedidos:
                Intent cp = new Intent(getApplicationContext(), ConsultasPedidos.class);
                startActivity(cp);
                break;

            case R.id.inicial_pedidos :
                Intent i = new Intent(getApplicationContext(), Pedido.class);
                startActivity(i);
                break;
            case R.id.inicial_sair :
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public String dadosEmpresaCliente(int campo)
    {
        String retorno = "";
        switch (campo)
        {
            case R.id.tela_inicial_txt_emp_nome:
                retorno = this.controle.nomeEmpresa();
                break;
            case R.id.tela_inicial_txt_emp_sub:
                retorno = this.controle.enderecoEmpresa();
                break;
            case R.id.tela_inicial_txt_emp_fone:
                retorno = this.controle.foneEmpresa();
                break;
            case R.id.tela_inicial_txt_emp_mail:
                retorno = this.controle.emailEmpresa();
                break;
            case R.id.tela_inicial_txt_emp_page:
                retorno = this.controle.siteEmpresa();
                break;
            default:
                retorno = "";
                break;
        }
        return retorno;
    }

    public String desenvolvedor(int campo)
    {
        String retorno = "";
        switch (campo)
        {
            case R.id.tela_inicial_txt_validade:
                retorno = "Validade: " + this.controle.validade();
                break;
            case R.id.tela_inicial_txt_versao:
                retorno = "Versão: 5.0";
                break;
            default:
                retorno = "";
                break;
        }
        return retorno;
    }

    private void fragmentoCentral()
    {
        Fragment fragment = null;
        switch (this.controle.fragmentoCentral())
        {
            case 0:
                fragment = new ConsultaItensKits();
                break;
            default:
                break;
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
            {
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.fadein, R.anim.fadeout, R.anim.fadein, R.anim.fadeout)
                        .replace(R.id.fragmentInicial, fragment).addToBackStack(null).commit();
            }
            else
            {
                fragmentManager.beginTransaction().replace(R.id.fragmentInicial, fragment).addToBackStack(null).commit();
            }
        }
        else
        {
            System.out.println("Não existem dados para exibir na tela inicial");
            Log.e("MainActivity", "Error in creating fragment");
        }
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

        BancoDataAccess bda = new BancoDataAccess(getApplicationContext());
        CampanhaGrupoDataAccess cgda = new CampanhaGrupoDataAccess(getApplicationContext());
        CampanhaProdutoDataAccess cpda = new CampanhaProdutoDataAccess(getApplicationContext());
        CidadeDataAccess cida = new CidadeDataAccess(getApplicationContext());
        GrupoDataAccess gda = new GrupoDataAccess(getApplicationContext());
        TipoVendaDataAccess tvda = new TipoVendaDataAccess(getApplicationContext());

        List<Cliente> clientes;
        List<Item> itens;
        List<Venda> vendas;

        List<Natureza> naturezas;
        List<Prazo> prazos;
        List<Preco> precos;
        List<Estoque> estoques;

        List<Promocao> promocoes;

        List<Banco> bancos;
        List<CampanhaGrupo> campanhaGrupos;
        List<CampanhaProduto> campanhaProdutos;
        List<Cidade> cidades;
        List<Grupo> grupos;
        List<TiposVenda> tiposVendas;

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

    private void inserirDadosHardCoded()
    {

    }
}
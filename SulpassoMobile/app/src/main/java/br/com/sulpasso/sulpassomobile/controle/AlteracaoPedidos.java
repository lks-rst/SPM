package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.modelo.Venda;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ClienteDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.VendaDataAccess;

/**
 * Created by Lucas on 21/03/2017 - 16:58 as part of the project SulpassoMobile.
 */
public class AlteracaoPedidos extends EfetuarPedidos
{
    /**
     * @param ctx
     * @param tipo
     ************************************************************************************************/
    public AlteracaoPedidos(Context ctx, String tipo) { super(ctx, tipo); }

    @Override
    public ArrayList<String> listarCLientes(int tipo, String dados) throws GenercicException
    {
        ArrayList<String> lista = super.controleClientes.exibirLista(2, super.venda.getCliente().getRazao());

        super.codigoNatureza = super.venda.getNatureza();
        super.codigoPrazo = super.venda.getPrazo().getCodigo();

        return lista;
    }

    @Override
    public ArrayList<String> listarNaturezas(Boolean especial) throws GenercicException
    {
        this.getNaturezasList(especial);
        ArrayList<String> lista = new ArrayList<>();
        for(Natureza n : super.listaNaturezas) lista.add(n.toDisplay());

        return lista;
    }

    @Override
    public ArrayList<String> listarPrazos(int position) throws GenercicException
    {
        String prazo = super.getPrazoNatureza(position);
        this.getPrazosList(prazo);
        ArrayList<String> lista = new ArrayList<>();
        for(Prazo p : super.listaPrazos) lista.add(p.toDisplay());

        return lista;
    }

    @Override
    public Boolean permitirClick(int id) { return false; }

    @Override
    public String selecionarCliente(int posicao)
    {
        super.venda.setCliente(super.controleClientes.getCliente(posicao));
        super.codigoNatureza = super.controleClientes.getCliente(posicao).getNatureza();
        super.codigoPrazo = super.controleClientes.getCliente(posicao).getPrazo();

        return super.controleClientes.getCliente(posicao).toString();
    }

    @Override
    public int buscarNatureza()
    {
        for (Natureza n : super.listaNaturezas)
            if (n.getCodigo() == super.codigoNatureza)
                return super.listaNaturezas.indexOf(n);

        return -1;
    }

    public int buscarPrazo()
    {
        for (Prazo p : super.listaPrazos)
            if (p.getCodigo() == super.codigoPrazo)
                return super.listaPrazos.indexOf(p);

        return -1;
    }

    public void setPrazo(int posicao)
    {
        super.codigoPrazo = super.listaPrazos.get(posicao).getCodigo();

        for (Prazo p : super.listaPrazos)
        {
            if (p.getCodigo() == super.codigoPrazo)
            {
                super.tabela = super.listaPrazos.get(super.listaPrazos.indexOf(p)).getTabela();
                super.venda.setTabela(super.tabela);
                break;
            }
        }
    }

    @Override
    public Boolean temValorMinimo() {
        return null;
    }

    @Override
    public Boolean temPromocao() {
        return null;
    }

    @Override
    public void buscarPromocoes() { /*****/ }

    @Override
    public String buscarMinimoTabela() {
        return null;
    }

    @Override
    public Boolean alteraValor(String campo) {
        return null;
    }

    @Override
    public Boolean alteraValorFim(int campo) {
        return null;
    }

    @Override
    public String getValor() {
        return null;
    }

    @Override
    public String getQtdMinimaVenda() {
        return null;
    }

    @Override
    public String getUnidade() {
        return null;
    }

    @Override
    public String getUnidadeVenda() {
        return null;
    }

    @Override
    public String getCodigoBarras() {
        return null;
    }

    @Override
    public String getQtdCaixa() {
        return null;
    }

    @Override
    public String getValorUnitario() {
        return null;
    }

    public String getEstoque() { return super.controleDigitacao.getEstoque(); }

    @Override
    public String getMarkup() {
        return null;
    }

    @Override
    public String calcularTotal() {
        return null;
    }

    @Override
    public void setQuantidade(String quantidade) { /*****/ }

    @Override
    public Boolean confirmarItem() {
        return null;
    }

    @Override
    public int finalizarPedido() {
        return 0;
    }

    @Override
    public String buscarDadosVenda(int campo)
    {
        String retorno;
        switch (campo)
        {
            case R.id.fdcEdtDca :
                retorno = String.format(super.context.getResources().getString(R.string.str_flex)
                        , String.valueOf(super.controleConfiguracao.buscarFlex()));
                break;
            default:
                retorno = "--";
        }
        return retorno;
    }

    @Override
    public String buscarDadosCliente(int campo)
    {
        String retorno;
        switch (campo)
        {
            case R.id.fdcEdtMedia :
                retorno = String.format(super.context.getResources().getString(R.string.str_media)
                        , String.valueOf(super.venda.getCliente().getMediaCompras()));
                break;
            case R.id.fdcEdtQuantidade :
                retorno = String.format(super.context.getResources().getString(R.string.str_qtd)
                        , String.valueOf(super.venda.getCliente().getMediaIndustrializados()));
                break;
            case R.id.fdcEdtLimite :
                retorno = String.valueOf(super.venda.getCliente().getLimiteCredito());
                break;
            case R.id.fdcEdtReal :
                retorno = String.valueOf(super.venda.getCliente().getRealizado());
                break;
            case R.id.fdcEdtMeta :
                retorno = String.valueOf(super.venda.getCliente().getMetaPeso());
                break;
            case R.id.fdcEdtUltima :
                retorno = super.venda.getCliente().getDataUltimaCompra();
                break;
            case R.id.fdcEdtCel :
                retorno = super.venda.getCliente().getCelular();
                break;
            case R.id.fdcEdtFone :
                retorno = super.venda.getCliente().getTelefone();
                break;
            case R.id.fdcEdtEnd :
                retorno = super.venda.getCliente().getEndereco();
                break;
            case R.id.fdcEdtUf :
                retorno = super.buscarEstado(super.venda.getCliente().getCodigoCidade());
                break;
            case R.id.fdcEdtCidade :
                retorno = super.buscarCidade(super.venda.getCliente().getCodigoCidade());
                break;
            default:
                retorno = "--";
        }
        return retorno;
    }

    @Override
    public int verificarTabloides() {
        return 0;
    }

    @Override
    public int aplicarDescontoTabloide(float percentual, int posicao) {
        return 0;
    }

    @Override
    public ArrayList<CampanhaGrupo> getCampanhaGrupos() {
        return null;
    }

    @Override
    protected void getNaturezasList(Boolean especial) throws GenercicException
    {
        NaturezaDataAccess nda = new NaturezaDataAccess(super.context);

        if (especial)
            super.listaNaturezas = nda.buscarRestrito();
        else
            super.listaNaturezas = nda.buscarTodos();
    }

    protected void getPrazosList(String prazo) throws GenercicException
    {
        PrazoDataAccess pda = new PrazoDataAccess(super.context);

        if (prazo.equalsIgnoreCase("0") || prazo.equalsIgnoreCase("a"))
            super.listaPrazos = pda.buscarRestrito();
        else
            super.listaPrazos = pda.buscarTodos();
    }

    protected Boolean naturezaIsClicable()
    {
        boolean clienteLiberado = super.controleClientes.clienteAlteraTabela();

        if(clienteLiberado && (super.itensVendidos.size() <= 0))
        {
            if(super.controleConfiguracao.getConfigVda().getAlteraNaturezaInicio()) { return true; }
            else { return false; }
        }
        else { return false; }
    }

    protected Boolean prazoIsClicable()
    {
        boolean clienteLiberado = super.controleClientes.clienteAlteraTabela();

        if(clienteLiberado && (super.itensVendidos.size() <= 0))
        {
            if(super.controleConfiguracao.getConfigVda().getAlteraPrazoInicio()) { return true; }
            else { return false; }
        }
        else { return false; }
    }

    protected Boolean naturezaIsClicableEnd()
    {
        boolean clienteLiberado = super.controleClientes.clienteAlteraTabela();

        if(clienteLiberado)
        {
            if(super.controleConfiguracao.getConfigVda().getAlteraNaturezaFim()) { return true; }
            else { return false; }
        }
        else { return false; }
    }

    protected Boolean prazoIsClicableEnd()
    {
        boolean clienteLiberado = super.controleClientes.clienteAlteraTabela();

        if(clienteLiberado)
        {
            if(super.controleConfiguracao.getConfigVda().getAlteraPrazoFim()) { return true; }
            else { return false; }
        }
        else { return false; }
    }

    protected Boolean finalizarItem()
    {
        if(super.controleDigitacao.valorMaximo(super.context))
        {
            if(super.controleConfiguracao.formaDesconto() == 0)
            {
                if (super.controleConfiguracao.contribuicaoIdeal())
                    return true;
                else
                    return false;
            }
            else
            {
                float saldo = super.controleConfiguracao.getSaldoAtual();
                if(saldo - super.controleDigitacao.diferencaFlex(super.context) >= 0)
                    return true;
                else
                    return false;
            }
        }
        else { return false; }
    }

    @Override
    public boolean verificarPrepedido() { return false; }

    @Override
    public PrePedido detalharPrePedido() { return null; }

    @Override
    public ArrayList<String> listarCidades() {
        return null;
    }

    @Override
    public float calcularPpc(float valor, float markup, float desconto) {
        return 0;
    }

    @Override
    public void buscarVenda(int codVenda)
    {
        Venda v;
        VendaDataAccess vda = new VendaDataAccess(super.context);
        ClienteDataAccess cda = new ClienteDataAccess(super.context);

        v = vda.buscarVendaAlteracao(codVenda);

        try { v.setCliente(cda.buscarCliente(v.getCodigoCliente())); }
        catch (ReadExeption readExeption) { readExeption.printStackTrace(); }

        super.venda = v;
    }
}
package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaProduto;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;

/**
 * Created by Lucas on 21/03/2017 - 16:57 as part of the project SulpassoMobile.
 */
public class VendaDireta extends EfetuarPedidos {

    /**
     * @param ctx
     * @param tipo
     ************************************************************************************************/
    public VendaDireta(Context ctx, String tipo) { super(ctx, tipo); }

    @Override
    public ArrayList<String> listarCLientes(int tipo, String dados) throws GenercicException {
        return null;
    }

    @Override
    public ArrayList<String> listarNaturezas(Boolean especial) throws GenercicException {
        return null;
    }

    @Override
    public ArrayList<String> listarPrazos(int position) throws GenercicException {
        return null;
    }

    @Override
    public ArrayList<String> listarPrazos(boolean position) throws GenercicException
    {
        return null;
    }

    @Override
    public Boolean permitirClick(int id) {
        return null;
    }

    @Override
    public String selecionarCliente(int posicao) {
        return null;
    }

    @Override
    public int buscarNatureza() {
        return 0;
    }

    @Override
    public int buscarPrazo() {
        return 0;
    }

    @Override
    public void setPrazo(int posicao) {

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
    public void buscarPromocoes() {

    }

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
    public String getValorTabela() { return super.controleDigitacao.getValorTabela(); }

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
    public void setQuantidade(String quantidade) {

    }

    @Override
    public Boolean confirmarItem() {
        return null;
    }

    @Override
    public int finalizarPedido(Boolean justificar) {
        return 0;
    }

    @Override
    public String buscarDadosVenda(int campo) {
        return null;
    }

    @Override
    public String buscarDadosCliente(int campo) {
        return null;
    }

    @Override
    public int verificarTabloides() {
        return 0;
    }

    @Override
    public int verificarCampanhas() {
        return 0;
    }

    @Override
    public int aplicarDescontoTabloide(float percentual, int posicao, int tipo) {
        return 0;
    }

    @Override
    public ArrayList<CampanhaGrupo> getCampanhaGrupos() {
        return null;
    }

    @Override
    public ArrayList<CampanhaProduto> getCampanhaProdutos() {
        return null;
    }

    @Override
    protected void getNaturezasList(Boolean especial) throws GenercicException {

    }

    @Override
    protected void getPrazosList(String prazo) throws GenercicException {

    }

    @Override
    protected Boolean naturezaIsClicable() {
        return null;
    }

    @Override
    protected Boolean prazoIsClicable() {
        return null;
    }

    @Override
    protected Boolean naturezaIsClicableEnd() {
        return null;
    }

    @Override
    protected Boolean prazoIsClicableEnd() {
        return null;
    }

    @Override
    protected Boolean finalizarItem() {
        return null;
    }

    @Override
    public boolean verificarPrepedido()
    {
        return false;
    }

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
    public void buscarVenda(int codVenda) {

    }

    public Boolean mostraFlexItem(){ return false; }

    public Boolean mostraFlexVenda(){ return false; }
}

package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpasso.sulpassomobile.modelo.Item;
import br.com.sulpasso.sulpassomobile.modelo.ItensVendidos;
import br.com.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpasso.sulpassomobile.modelo.Prazo;
import br.com.sulpasso.sulpassomobile.modelo.PrePedido;
import br.com.sulpasso.sulpassomobile.modelo.Promocao;
import br.com.sulpasso.sulpassomobile.persistencia.queries.NaturezaDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PrazoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PrePedidoDataAccess;
import br.com.sulpasso.sulpassomobile.persistencia.queries.PromocaoDataAccess;
import br.com.sulpasso.sulpassomobile.views.fragments.alertas.AlertaMixFaltante;

/**
 * Created by Lucas on 21/03/2017 - 16:49 as part of the project SulpassoMobile.
 */
public class PedidoNormal extends EfetuarPedidos
{

/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    /**
     * @param ctx
     * @param tipo
     ************************************************************************************************/
    public PedidoNormal(Context ctx, String tipo) { super(ctx, tipo); }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    public void selecionarCliente() { /*****/ }

    public void selecionarNatureza() { /*****/ }

    public void selecionarPrazo() { /*****/ }

    public void aplicarDesconto(float desconto) { /*****/ }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    public ArrayList<String> listarCLientes(int tipo, String dados) throws GenercicException
    {
        return super.controleClientes.exibirLista(tipo, dados);
    }

    public ArrayList<String> listarNaturezas(Boolean especial) throws GenercicException
    {
        this.getNaturezasList(especial);
        ArrayList<String> lista = new ArrayList<>();
        for(Natureza n : super.listaNaturezas) lista.add(n.toDisplay());

        return lista;
    }

    public ArrayList<String> listarPrazos(int position) throws GenercicException
    {
        String prazo = super.getPrazoNatureza(position);
        this.getPrazosList(prazo);
        ArrayList<String> lista = new ArrayList<>();
        for(Prazo p : super.listaPrazos) lista.add(p.toDisplay());

        return lista;
    }

    public Boolean permitirClick(int id)
    {
        Boolean click = false;

        switch (id)
        {
            case R.id.fdcSpnrClientes :
                click = super.clientIsClicable();
                break;
            case R.id.fdcSpnrNaturezas :
                click = this.naturezaIsClicable();
                break;
            case R.id.fdcSpnrPrazos :
                click = this.prazoIsClicable();
                break;
            case R.id.ffpSpnrNaturezas :
                click = this.naturezaIsClicableEnd();
                break;
            case R.id.ffpSpnrPrazos :
                click = this.prazoIsClicableEnd();
                break;
        }

        return click;
    }

    public String selecionarCliente(int posicao)
    {
        super.venda.setCliente(super.controleClientes.getCliente(posicao));
        super.codigoNatureza = super.controleClientes.getCliente(posicao).getNatureza();
        super.codigoPrazo = super.controleClientes.getCliente(posicao).getPrazo();
        super.venda.setCodigoCliente(super.venda.getCliente().getCodigoCliente());

        return super.controleClientes.getCliente(posicao).toString();
    }

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

        if(String.valueOf(super.venda.getCliente().getEspecial()).equalsIgnoreCase("E"))
        {
            super.tabela = super.venda.getCliente().getTabela();
            super.venda.setTabela(super.tabela);
        }
        else
        {
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
    }

    public Boolean temValorMinimo()
    {
        return super.controleDigitacao.temMinimo();
    }

    public Boolean temPromocao()
    {
        return super.controleDigitacao.temPromocao();
    }

    public void buscarPromocoes()
    {
        PromocaoDataAccess pda = new PromocaoDataAccess(super.context);
        ArrayList<Promocao> promocoes = new ArrayList<>();
        try { promocoes = pda.buscarPromocao(super.controleDigitacao.getItem().getCodigo()); }
        catch (GenercicException e) { e.printStackTrace(); }

        ArrayList<String> lista = new ArrayList<>();

        if(promocoes.size() > 0)
            for (Promocao p : promocoes)
                lista.add(p.toDisplay());
        else
            lista.add(super.context.getString(R.string.sem_promocoes));

        Toast.makeText(super.context
                ,"Valores promocionais encontrados para o item:\n" + lista.toString()
                , Toast.LENGTH_LONG).show();
    }

    public String buscarMinimoTabela()
    {
        Toast.makeText(super.context
                ,"Valor minimo encontrado para o item: " + super.controleDigitacao.buscarMinimo()
                , Toast.LENGTH_LONG).show();

        return super.controleDigitacao.buscarMinimo();
    }

    public Boolean alteraValor(String campo)
    {
        return super.controleConfiguracao.alteraValor(campo);
        /*
        if(campo.equalsIgnoreCase("v")) { return super.controleConfiguracao.alteraValor(campo); }
        else
        {
            return !super.controleConfiguracao.alteraValor(campo);
        }
        */
    }

    public Boolean alteraValorFim(int campo)
    {
        return super.controleConfiguracao.alteraValorFim(campo);
    }

    public String getValor() { return super.controleDigitacao.getValor(); }

    public String getQtdMinimaVenda() { return super.controleDigitacao.getQtdMinimaVenda(); }

    public String getUnidade() { return super.controleDigitacao.getUnidade(); }

    public String getUnidadeVenda() { return super.controleDigitacao.getUnidadeVenda(); }

    public String getCodigoBarras() { return super.controleDigitacao.getCodigoBarras(); }

    public String getQtdCaixa() { return super.controleDigitacao.getQtdCaixa(); }

    public String getEstoque() { return super.controleDigitacao.getEstoque(); }

    public String getValorUnitario() { return super.controleDigitacao.getValorUnitario(); }

    public String getMarkup() { return super.controleConfiguracao.getMarkup(); }

    public String calcularTotal() { return String.valueOf(super.controleDigitacao.calcularTotal()); }

    public void setQuantidade(String quantidade) { super.controleDigitacao.setQuantidade(Float.parseFloat(quantidade)); }

    public Boolean confirmarItem()
    {
        Boolean alteracao = false;
        int posicao = -1;

        ItensVendidos item = super.controleDigitacao.confirmarItem(
                super.controleConfiguracao.descontoMaximo(), super.controleConfiguracao.alteraValor("d"), super.context, super.senha);

        if(item != null)
        {
            if(this.finalizarItem())
            {
                for (ItensVendidos i : super.itensVendidos)
                {
                    if (item.equals(i))
                    {
                        alteracao = true;
                        posicao = super.itensVendidos.indexOf(i);
                        break;
                    }
                }

                if (alteracao)
                {
                    if(super.itensVendidos.get(posicao).getFlex() > 0 && !super.itensVendidos.get(posicao).isDigitadoSenha())
                    {
                        super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() +
                                (super.itensVendidos.get(posicao).getFlex() * super.itensVendidos.get(posicao).getQuantidade()));
                    }

                    super.itensVendidos.set(posicao, item);
                    /*
                    TODO: Replicar as alterações para os demais tipos de pedido;
                     */
                    Toast.makeText(context, "Item " + item.getItem() + " alterado!", Toast.LENGTH_LONG).show();
                }
                else { super.itensVendidos.add(item); }

                try
                {
                    if(super.itensVendidos.get(super.itensVendidos.size() - 1).getFlex() > 0 && !super.itensVendidos.get(super.itensVendidos.size() - 1).isDigitadoSenha())
                    {
                        super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() -
                                (super.itensVendidos.get(super.itensVendidos.size() - 1).getFlex() * super.itensVendidos.get(super.itensVendidos.size() - 1).getQuantidade()));
                    }
                }
                catch (Exception exeption)
                {
                    if(super.itensVendidos.get(0).getFlex() > 0 && !super.itensVendidos.get(0).isDigitadoSenha())
                    {
                        super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() -
                                (super.itensVendidos.get(0).getFlex() * super.itensVendidos.get(0).getQuantidade()));
                    }
                }

                return true;
            }
            else return false;
        }
        else return false;
    }

    public int finalizarPedido()
    {
        NaturezaDataAccess nda = new NaturezaDataAccess(super.context);

        /*
        if(super.controleSalvar.getDesconto() > 0 && super.venda.getObservacaDesconto().length() < 20)
        {
            Toast.makeText(super.context, "É necessario acrescentar uma justificativa para o desconto aplicado ao final do pedido", Toast.LENGTH_LONG).show();
            return -1;
        }
        */

        if(super.controleSalvar.verificarMinimo(nda.buscarNatureza(super.codigoNatureza).getMinimo()
                , super.controleConfiguracao.pedidoMinimo()))
        {
            if(super.verificarMix())
            {
                /* TODO: APRESENTAR OS DADOS DE MIX NÃO COMPLETADO */
                Toast.makeText(super.context, "VERIFIQUE OS ITENS AINDA NÃO VENDIDOS QUE COMPÕE O MIX IDEAL DO CLIENTE", Toast.LENGTH_LONG).show();
                AlertaMixFaltante msg_mix_falt = new AlertaMixFaltante();
                return 0;
            }
            else
            {
                if(super.controleSalvar.verificarSaldo(super.controleConfiguracao.getSaldoAtual()))
                {
                    super.venda.setItens(super.itensVendidos);
                    super.venda.setValor(Double.parseDouble(String.valueOf(super.valorVendido())));
                    super.venda.setCodigoCliente(super.controleClientes.getCodigoClienteSelecionado());
                    super.venda.setDesconto(Double.parseDouble(String.valueOf(super.controleSalvar.getDesconto())));
                    super.venda.setData(super.dataSistema());
                    super.venda.setHora(super.horaSistema());
                    super.venda.setNatureza(super.codigoNatureza);
                    super.venda.setBanco(super.controleClientes.getBancoCliente());

                    super.venda.setTipo(super.strTipoVenda);

                    Prazo p = new Prazo();
                    p.setCodigo(super.codigoPrazo);
                    super.venda.setPrazo(p);

                    if(super.controleSalvar.salvarPedido(super.context, super.venda))
                    {
                        super.controleSalvar.atualizarSaldo(super.context,((float) (super.controleConfiguracao.getSaldoAtual() - super.venda.getDesconto().floatValue())));

                        float flexItem = 0;
                        float flexItens = 0;
                        float totalFlex = 0;

                        for (ItensVendidos i : super.itensVendidos)
                        {
                            flexItem = i.getFlex();

                            if(flexItem > 0  && !i.isDigitadoSenha())
                                flexItens += (flexItem * i.getQuantidade());
                        }

                        float valorDesconto = super.venda.getDesconto().floatValue();
                        float saldoFinalFlex = flexItens + valorDesconto;

                        totalFlex = (float) (flexItens + super.venda.getDesconto());
                        totalFlex *= -1;

                        Toast t = Toast.makeText(super.context, "Total de flex gerado no pedido = " + String.valueOf(totalFlex), Toast.LENGTH_LONG);
                        t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                        t.show();

                        return 1;
                    }
                    else
                    {
                        Toast.makeText(context, "ATENÇÃO!\nOcorreu uma falha ao salvar os dados.", Toast.LENGTH_LONG).show();
                        return 0;
                    }
                }
                else
                {
                    Toast.makeText(context, "ATENÇÃO!\nDesconto aplicado excede o valor atual de saldo disponível"
                            , Toast.LENGTH_LONG).show();
                    return 0;
                }
            }
        }
        else
        {
            Toast.makeText(context, "ATENÇÃO!\nValor vendido abaixo do valor minimo de venda"
                    , Toast.LENGTH_LONG).show();
            return 0;
        }
    }

    public String buscarDadosVenda(int campo)
    {
        //return super.controleClientes.buscarDadosCliente(campo);
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

    public String buscarDadosCliente(int campo)
    {
        //return super.controleClientes.buscarDadosCliente(campo);
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

            case R.id.aacEdtFant :
                retorno = super.venda.getCliente().getFantasia();
                break;
            case R.id.aacEdtBairro :
                retorno = super.venda.getCliente().getBairro();
                break;
            case R.id.aacEdtCnpj :
                retorno = super.venda.getCliente().getCgc();
                break;
            case R.id.aacEdtCod :
                retorno = String.valueOf(super.venda.getCliente().getCodigoCliente());
                break;
            case R.id.aacEdtIe :
                retorno = super.venda.getCliente().getIe();
                break;
            case R.id.aacEdtBanco :
                retorno = super.buscarBanco(super.venda.getCliente().getBanco());
                break;
            case R.id.aacEdtCep :
                retorno = super.venda.getCliente().getCep();
                break;
            case R.id.aacEdtAniv :
                retorno = "--";
                break;
            case R.id.aacEdtRota :
                retorno = String.valueOf(super.venda.getCliente().getRoteiro());
                break;
            case R.id.aacEdtMail :
                retorno = super.venda.getCliente().getEmail();
                break;
            case R.id.aacEdtContact :
                retorno = super.venda.getCliente().getContato();
                break;
            case R.id.aacEdtMsg :
                retorno = super.venda.getCliente().getMensagem();
                break;
            case R.id.aacCbxSeg :
                retorno = String.valueOf(super.venda.getCliente().getVisita());
                break;

            default:
                retorno = "--";
        }
        return retorno;
    }

    public int verificarTabloides()
    {
        int codigo = super.itensVendidos.get(super.itensVendidos.size() - 1).getItem();
        Item produto = null;
        try { produto = super.controleProdutos.getItemCodigo(codigo); }
        catch (GenercicException e)
        {
            produto = null;
            e.printStackTrace();
        }

        if(produto == null)
            return -1;

        int posicaoGrupo = -1;

        if(super.campanhaGrupos != null && super.campanhaGrupos.size() > 0)
        {
            for(CampanhaGrupo c : super.campanhaGrupos)
            {
                if ((c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() ==
                        produto.getSubGrupo() && c.getGrupo().getDivisao() == produto.getDivisao()) ||
                        (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() ==
                                produto.getSubGrupo() && c.getGrupo().getDivisao() == 0) ||
                        (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() ==
                                0 && c.getGrupo().getDivisao() == 0))
                {
                    posicaoGrupo = super.campanhaGrupos.indexOf(c);
                    break;
                }
            }
        }
        else
        {
            super.campanhaGrupos = new ArrayList<>();
            ConsultaMinimosGravososKitsCampanhas campanhas = new ConsultaMinimosGravososKitsCampanhas(super.context);
            try
            {
                CampanhaGrupo camp = campanhas.buscarCampanha(codigo);

                if(camp == null)
                    posicaoGrupo = -1;
                else
                {
                    super.campanhaGrupos.add(camp);
                    posicaoGrupo = 0;
                }
            }
            catch (GenercicException e)
            {
                e.printStackTrace();
                posicaoGrupo = -1;
            }
        }

        if(posicaoGrupo != -1)
        {
            super.campanhaGrupos.get(posicaoGrupo).setQuantidadeVendida(
                    super.campanhaGrupos.get(posicaoGrupo).getQuantidadeVendida() +
                            (int) super.itensVendidos.get(super.itensVendidos.size() -1).getQuantidade());

            if(super.campanhaGrupos.get(posicaoGrupo).getQuantidadeVendida() >= super.campanhaGrupos.get(posicaoGrupo).getQuantidade())
            {
                if(super.campanhaGrupos.get(posicaoGrupo).getDescontoAplicado() > 0)
                {
                    super.itensVendidos.get(super.itensVendidos.size() - 1).setDescontoCG(super.campanhaGrupos.get(posicaoGrupo).getDescontoAplicado());

                    super.itensVendidos.get(super.itensVendidos.size() - 1).setValorLiquido(
                            super.itensVendidos.get(super.itensVendidos.size() - 1).getValorDigitado() -
                                    ((super.itensVendidos.get(super.itensVendidos.size() - 1).getValorDigitado() *
                                            super.campanhaGrupos.get(posicaoGrupo).getDescontoAplicado()) / 100));

                    super.itensVendidos.get(super.itensVendidos.size() - 1).setTotal(super.calcularTotal
                            (
                                    super.itensVendidos.get(super.itensVendidos.size() - 1).getQuantidade(),
                                    super.itensVendidos.get(super.itensVendidos.size() - 1).getValorDigitado(),
                                    super.itensVendidos.get(super.itensVendidos.size() - 1).getDesconto(),
                                    super.itensVendidos.get(super.itensVendidos.size() - 1).getDescontoCG(),
                                    super.itensVendidos.get(super.itensVendidos.size() - 1).getDescontoCP(), 0
                            ));

                    return -1;
                }
                else
                    return posicaoGrupo;
            }
            else
                return -1;
        }
        else { return -1; }
    }

    public int aplicarDescontoTabloide(float percentual, int posicao)
    {
        if(percentual >= 0)
        {
            if (super.campanhaGrupos.get(posicao).getDesconto() >= percentual)
            {
                super.campanhaGrupos.get(posicao).setDescontoAplicado(percentual);

                for(int i = 0; i < super.itensVendidos.size(); i++)
                {
                    Item produto = null;
                    try { produto = super.controleProdutos.getItemCodigo(super.itensVendidos.get(i).getItem()); }
                    catch (GenercicException e) { e.printStackTrace(); }

                    if((super.campanhaGrupos.get(posicao).getGrupo().getGrupo() == produto.getGrupo() &&
                            super.campanhaGrupos.get(posicao).getGrupo().getSubGrupo() == produto.getSubGrupo() &&
                            super.campanhaGrupos.get(posicao).getGrupo().getDivisao() == produto.getDivisao()) ||
                            (super.campanhaGrupos.get(posicao).getGrupo().getGrupo() == produto.getGrupo() &&
                                    super.campanhaGrupos.get(posicao).getGrupo().getSubGrupo() == produto.getSubGrupo() &&
                                    super.campanhaGrupos.get(posicao).getGrupo().getDivisao() == 0) ||
                            (super.campanhaGrupos.get(posicao).getGrupo().getGrupo() == produto.getGrupo() &&
                                    super.campanhaGrupos.get(posicao).getGrupo().getSubGrupo() == 0 &&
                                    super.campanhaGrupos.get(posicao).getGrupo().getDivisao() == 0))
                    {
                        super.itensVendidos.get(i).setDescontoCG(percentual);
                        super.itensVendidos.get(i).setValorLiquido(super.itensVendidos.get(i)
                                .getValorDigitado() - ((super.itensVendidos.get(i).getValorDigitado() * percentual) / 100));
                        super.itensVendidos.get(i).setTotal(super.calcularTotal(
                                super.itensVendidos.get(i).getQuantidade(), super.itensVendidos.get(i).getValorDigitado(),
                                super.itensVendidos.get(i).getDesconto(), super.itensVendidos.get(i).getDescontoCG(),
                                super.itensVendidos.get(i).getDescontoCP(), 0));
                        /*
                        super.itensVendidos.get(i).setTotal(
                            super.itensVendidos.get(i).getValorLiquido() * super.itensVendidos.get(i).getQuantidade());
                         */
                    }
                }
                return -1;
            }
            else
            {
                Toast.makeText(context, "Desconto acima do permitido", Toast.LENGTH_LONG).show();
                return posicao;
            }
        }
        else
        {
            Toast.makeText(context, "Desconto não pode ser zero (0)!", Toast.LENGTH_LONG).show();
            return posicao;
        }
    }

    public ArrayList<String> listarCidades() { return super.controleClientes.listarCidades(); }

    public float calcularPpc(float valor, float markup, float desconto)
    {
        return valor + (valor * (markup / 100));
    }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
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
        if(EfetuarPedidos.senha)
        {
            return true;
        }
        else
        {
            if(super.controleDigitacao.valorMaximo(super.context))
            {
                if(super.controleConfiguracao.formaDesconto() == 0)
                {
                    if (super.controleConfiguracao.contribuicaoIdeal())
                        return true;
                    else
                    {
                        EfetuarPedidos.erro = true;
                        EfetuarPedidos.strErro = "Contribuição atual não permite desconto!\nPor favor verifique.";
                        return false;
                    }
                }
                else
                {
                    float saldo = super.controleConfiguracao.getSaldoAtual();

                    if(saldo - (super.controleDigitacao.diferencaFlex(super.context) *
                            super.controleDigitacao.getQuantidade()) >= 0)
                    {
                        return true;
                    }
                    else
                    {
                        EfetuarPedidos.erro = true;
                        EfetuarPedidos.strErro = "Saldo insuficiente!\nPor favor verifique.";
                        return false;
                    }
                }
            }
            else
            {
                EfetuarPedidos.erro = true;
                EfetuarPedidos.strErro = "Valor acima do permitido!\nPor favor verifique.";
                return false;
            }
        }
    }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    public ArrayList<CampanhaGrupo> getCampanhaGrupos() { return campanhaGrupos; }

    public boolean verificarPrepedido()
    {
        boolean retorno = false;

        PrePedidoDataAccess pda = new PrePedidoDataAccess(super.context);
        try { retorno = pda.getClienteByCod(super.venda.getCliente().getCodigoCliente()); }
        catch (GenercicException e)
        {
            e.printStackTrace();
            retorno = false;
        }

        return retorno;
    }

    public PrePedido detalharPrePedido()
    {
        PrePedido retorno = null;

        ArrayList<PrePedido> pp = new ArrayList<>();

        PrePedidoDataAccess pda = new PrePedidoDataAccess(super.context);
        try { pp = pda.getByData(super.venda.getCliente().getCodigoCliente()); }
        catch (GenercicException e) { e.printStackTrace(); }

        retorno =  pp.get(0);

        super.prePedido = retorno;

        return retorno;
    }

    @Override
    public void buscarVenda(int codVenda)
    {

    }
}
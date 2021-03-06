package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaProduto;
import br.com.sulpasso.sulpassomobile.modelo.Grupo;
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
import br.com.sulpasso.sulpassomobile.util.funcoes.Formatacao;
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
        ArrayList<String> lista = new ArrayList<>();

        if(super.verificarNaturezaBrinde(position))
        {
            String prazo = super.getPrazoNatureza(position);
            this.getPrazosList(prazo);
            for(Prazo p : super.listaPrazos) lista.add(p.toDisplay());
        }
        else
        {
            lista = null;
        }

        return lista;
    }

    public ArrayList<String> listarPrazos(boolean especial) throws GenercicException
    {
        ArrayList<String> lista = new ArrayList<>();

        this.getPrazosList();
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
        if(!(super.itensVendidos() > 0))
        {
            super.venda.setCliente(super.controleClientes.getCliente(posicao));
            super.codigoNatureza = super.controleClientes.getCliente(posicao).getNatureza();
            super.codigoPrazo = super.controleClientes.getCliente(posicao).getPrazo();
            super.venda.setCodigoCliente(super.venda.getCliente().getCodigoCliente());
        }

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

    public String getValorTabela() { return super.controleDigitacao.getValorTabela(); }

    public String getQtdMinimaVenda() { return super.controleDigitacao.getQtdMinimaVenda(); }

    public String getUnidade() { return super.controleDigitacao.getUnidade(); }

    public String getUnidadeVenda() { return super.controleDigitacao.getUnidadeVenda(); }

    public String getCodigoBarras() { return super.controleDigitacao.getCodigoBarras(); }

    public String getQtdCaixa() { return super.controleDigitacao.getQtdCaixa(); }

    public String getEstoque() { return super.controleDigitacao.getEstoque(); }

    public String getValorUnitario() { return super.controleDigitacao.getValorUnitario(); }

    public String getMarkup() { return super.controleConfiguracao.getMarkup(); }

    public String calcularTotal() { return Formatacao.format2d(super.controleDigitacao.calcularTotal()); }

    public void setQuantidade(String quantidade) { super.controleDigitacao.setQuantidade(Float.parseFloat(quantidade)); }

    public Boolean confirmarItem()
    {
        Boolean alteracao = false;
        int posicao = -1;
        int especialTipo = -1;

        especialTipo = String.valueOf(super.venda.getCliente().getEspecial()).equalsIgnoreCase("E")  ?
                super.controleConfiguracao.getConfigVda().getEspecialAlteraValor2() : -1;

        /*
        ItensVendidos item = super.controleDigitacao.confirmarItem(
                super.controleConfiguracao.descontoMaximo(), super.controleConfiguracao.alteraValor("d"), super.context, super.senha);
        */
        ItensVendidos item = super.controleDigitacao.confirmarItem(
                super.controleConfiguracao.descontoMaximo(), super.controleConfiguracao.alteraValor("d"), super.context, super.senha,
                super.codigoNatureza, super.controleConfiguracao.getConfigEmp().getCodigo(),
                super.controleConfiguracao.getConfigHor().getMaximoItens(), this.getClass(), super.venda.getTipo(), especialTipo);

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
                    if(super.itensVendidos.get(posicao).isDigitadoSenha()) { /*****/ }
                    else
                    {
                        if(super.itensVendidos.get(posicao).getFlex() > 0)
                        {
                            /*
                            GAMBIARRA PRA CLIENTE MALA
                             */
                            if(super.codigoNatureza == 21 && super.controleConfiguracao.getConfigEmp().getCodigo() == 7)
                            {
                                super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() +
                                        (super.itensVendidos.get(posicao).getFlex()));
                            }
                            else
                            {
                                super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() +
                                        (super.itensVendidos.get(posicao).getFlex() * super.itensVendidos.get(posicao).getQuantidade()));
                            }
                        }
                    }

                    /*
                    if(super.itensVendidos.get(posicao).getFlex() > 0 && !super.itensVendidos.get(posicao).isDigitadoSenha())
                    {
                        super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() +
                                (super.itensVendidos.get(posicao).getFlex() * super.itensVendidos.get(posicao).getQuantidade()));
                    }
                    */

                    super.itensVendidos.set(posicao, item);
                    /*
                    TODO: Replicar as alterações para os demais tipos de pedido;
                     */
                    Toast.makeText(context, "Item " + item.getItem() + " alterado!", Toast.LENGTH_LONG).show();
                }
                else { super.itensVendidos.add(item); }

                try
                {
                    if(super.itensVendidos.get(super.itensVendidos.size() - 1).isDigitadoSenha()) { /*****/ }
                    else
                    {
                        if(super.itensVendidos.get(super.itensVendidos.size() - 1).getFlex() > 0)
                        {
                            /*
                            GAMBIARRA PRA CLIENTE MALA
                             */
                            if(super.codigoNatureza == 21 && super.controleConfiguracao.getConfigEmp().getCodigo() == 7)
                            {
                                super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() -
                                        (super.itensVendidos.get(super.itensVendidos.size() - 1).getFlex()));
                            }
                            else
                            {
                                super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() -
                                    (super.itensVendidos.get(super.itensVendidos.size() - 1).getFlex() * super.itensVendidos.get(super.itensVendidos.size() - 1).getQuantidade()));
                            }
                        }
                    }

                    /*
                    if(super.itensVendidos.get(super.itensVendidos.size() - 1).getFlex() > 0 && !super.itensVendidos.get(super.itensVendidos.size() - 1).isDigitadoSenha())
                    {
                        super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() -
                                (super.itensVendidos.get(super.itensVendidos.size() - 1).getFlex() * super.itensVendidos.get(super.itensVendidos.size() - 1).getQuantidade()));
                    }
                    */
                }
                catch (Exception exeption)
                {
                    if(super.itensVendidos.get(0).isDigitadoSenha()) { /*****/ }
                    else
                    {
                        if(super.itensVendidos.get(0).getFlex() > 0)
                        {
                            /*
                            GAMBIARRA PRA CLIENTE MALA
                             */
                            if(super.codigoNatureza == 21 && super.controleConfiguracao.getConfigEmp().getCodigo() == 7)
                            {
                                super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() -
                                        (super.itensVendidos.get(0).getFlex()));
                            }
                            else
                            {
                                super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() -
                                        (super.itensVendidos.get(0).getFlex() * super.itensVendidos.get(0).getQuantidade()));
                            }
                        }
                    }

                    /*
                    if(super.itensVendidos.get(0).getFlex() > 0 && !super.itensVendidos.get(0).isDigitadoSenha())
                    {
                        super.controleConfiguracao.setSaldoAtual(super.controleConfiguracao.getSaldoAtual() -
                                (super.itensVendidos.get(0).getFlex() * super.itensVendidos.get(0).getQuantidade()));
                    }
                    */
                }

                return true;
            }
            else return false;
        }
        else return false;
    }

    public int finalizarPedido(Boolean justificar)
    {
        if(justificar)
        {
            if(super.controleSalvar.justificarPedido(super.context, super.venda))
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        else
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

                            if(this.mostraFlexVenda())
                            {
                                Toast t = Toast.makeText(super.context, "Total de flex gerado no pedido = " +  Formatacao.format2d(totalFlex), Toast.LENGTH_LONG);
                                t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                t.show();
                            }

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
                /*
                if(super.controleSalvar.verificarJustificativa(super.venda.getJustificativa(),
                        super.controleConfiguracao.getConfigVda().getGerenciarVisitas(),
                        super.venda.getCliente().getVisita()))
                {
                    if(super.verificarMix())
                    {
                        *//* TODO: APRESENTAR OS DADOS DE MIX NÃO COMPLETADO *//*
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

                                if(this.mostraFlexVenda())
                                {
                                    Toast t = Toast.makeText(super.context, "Total de flex gerado no pedido = " + String.valueOf(totalFlex), Toast.LENGTH_LONG);
                                    t.setGravity(Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL, 0);
                                    t.show();
                                }

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
                    Toast.makeText(context, "ATENÇÃO!\nEscolha uma justificativa para o pedido fora da data padrão."
                            , Toast.LENGTH_LONG).show();
                    return 0;
                }
                */
            }
            else
            {
                Toast.makeText(context, "ATENÇÃO!\nValor vendido abaixo do valor minimo de venda"
                        , Toast.LENGTH_LONG).show();
                return 0;
            }
        }
    }

    public String buscarDadosVenda(int campo)
    {
        //return super.controleClientes.buscarDadosCliente(campo);
        String retorno;
        switch (campo)
        {
            case R.id.fdcEdtDca :
                String actualValFlex = Formatacao.format2d(Float.parseFloat(super.controleConfiguracao.buscarFlex()));
                retorno = String.format(super.context.getResources().getString(R.string.str_flex)
                        , actualValFlex);

                if(Float.parseFloat(actualValFlex) < 0)
                {
                    super.controleConfiguracao.atualizarSaldoNegativo();

                    retorno = String.format(super.context.getResources().getString(R.string.str_flex)
                            , Formatacao.format2d(0));
                }

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
            case R.id.fdcTxtStatus :
                retorno = String.valueOf(super.venda.getCliente().getEspecial() + " - " + super.venda.getCliente().getSituacao());
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
        int posicaoCampanhaP = -1;

        if(super.campanhaGrupos != null && super.campanhaGrupos.size() > 0)
        {
            for(CampanhaGrupo c : super.campanhaGrupos) /*O código nesse for está duplicado resolver*/
            {
                if ((c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == produto.getSubGrupo() && c.getGrupo().getDivisao() == produto.getDivisao()) ||
                        (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == produto.getSubGrupo() && c.getGrupo().getDivisao() == 0) ||
                        (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == 0                     && c.getGrupo().getDivisao() == 0))
                {
                    int totalCampanha = c.getQuantidadeVendida();
                    totalCampanha -= this.getQuantidadeRemover();

                    if(totalCampanha < c.getQuantidade())
                    {
                        for(ItensVendidos iv : this.itensVendidos)
                        {
                            Grupo gIv = this.controleProdutos.getGrupoItem(iv.getItem());
                            if(gIv.equals(c.getGrupo()))
                            {
                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setDescontoCG(0);
                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).
                                        setDescontoCampanha(this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCP() > 0);

                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setValorLiquido(
                                        this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getValorDigitado());

                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setTotal(this.calcularTotal
                                        (
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getQuantidade(),
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getValorDigitado(),
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDesconto(),
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCG(),
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCP(), 0,
                                                this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getItem()
                                        ));
                            }
                        }

                        this.campanhaGrupos.get(this.campanhaGrupos.indexOf(c)).setQuantidadeVendida(totalCampanha);
                        this.campanhaGrupos.get(this.campanhaGrupos.indexOf(c)).setDescontoAplicado(0);
                    }
                }
            }

            posicaoGrupo = -1;
            posicaoCampanhaP = -1;

            for(CampanhaGrupo c : super.campanhaGrupos)
            {
                if ((c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == produto.getSubGrupo() && c.getGrupo().getDivisao() == produto.getDivisao()) ||
                    (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == produto.getSubGrupo() && c.getGrupo().getDivisao() == 0) ||
                    (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == 0                     && c.getGrupo().getDivisao() == 0))
                {
                    float qt = super.itensVendidos.get(super.itensVendidos.size() - 1).getQuantidade();

                    if(c.getQuantidade() <= (c.getQuantidadeVendida() + qt))
                    {
                        super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).setQuantidadeVendida(
                                super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).getQuantidadeVendida() + (int)qt);
                        posicaoGrupo = super.campanhaGrupos.indexOf(c);
                    }
                    else
                    {
                        super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).setQuantidadeVendida(
                                super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).getQuantidadeVendida() + (int)qt);

                        if(posicaoGrupo == -1)
                            posicaoGrupo = -2;
                    }
                }
            }

            if(posicaoGrupo == -1)
            {
                ConsultaMinimosGravososKitsCampanhas campanhas = new ConsultaMinimosGravososKitsCampanhas(super.context);
                try
                {
//                    CampanhaGrupo camp = campanhas.buscarCampanha(codigo);
                    ArrayList<CampanhaGrupo> camps = campanhas.buscarCampanhas(codigo);
                    int alterado = -1;

                    if(camps == null)
                        posicaoGrupo = -1;
                    else
                    {
                        if(super.campanhaGrupos == null || super.campanhaGrupos.size() == 0)
                        {
                            super.campanhaGrupos.addAll(camps);
                            alterado = 0;
                        }
                        else
                        {
                            for (CampanhaGrupo cp : camps)
                            {
                                int pos = -1;
                                for (CampanhaGrupo scp: super.campanhaGrupos)
                                {
                                    if(scp.equals(cp))
                                    {
                                        pos = super.campanhaGrupos.indexOf(scp);
                                        break;
                                    }
                                }
                                if(pos != -1)
                                {
                                    alterado = pos;
                                    //alterado = super.campanhaGrupos.size();
                                    //super.campanhaGrupos.add(cp);
                                }
                                else
                                {
                                    super.campanhaGrupos.add(cp);
                                }

                            }
                        }

                        /*
                        if(alterado != -1)
                        {
                            for(int i = 0; i < super.campanhaGrupos.size(); i++)
                            {
                                CampanhaGrupo c = super.campanhaGrupos.get(i);
                                if ((c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == produto.getSubGrupo() && c.getGrupo().getDivisao() == produto.getDivisao()) ||
                                        (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == produto.getSubGrupo() && c.getGrupo().getDivisao() == 0) ||
                                        (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == 0                     && c.getGrupo().getDivisao() == 0))
                                {
                                    float qt = super.itensVendidos.get(super.itensVendidos.size() - 1).getQuantidade();

                                    if(c.getQuantidade() <= (c.getQuantidadeVendida() + qt))
                                    {
                                        super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).setQuantidadeVendida(
                                                super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).getQuantidadeVendida() + (int)qt);
                                        posicaoGrupo = super.campanhaGrupos.indexOf(c);
                                    }
                                    else
                                    {
                                        super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).setQuantidadeVendida(
                                                super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).getQuantidadeVendida() + (int)qt);

                                        if(posicaoGrupo == -1)
                                            posicaoGrupo = -2;
                                    }
                                }
                            }
                        }
                        */
                        for(int i = 0; i < super.campanhaGrupos.size(); i++)
                        {
                            CampanhaGrupo c = super.campanhaGrupos.get(i);
                            if ((c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == produto.getSubGrupo() && c.getGrupo().getDivisao() == produto.getDivisao()) ||
                                    (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == produto.getSubGrupo() && c.getGrupo().getDivisao() == 0) ||
                                    (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == 0                     && c.getGrupo().getDivisao() == 0))
                            {
                                float qt = super.itensVendidos.get(super.itensVendidos.size() - 1).getQuantidade();

                                if(c.getQuantidade() <= (c.getQuantidadeVendida() + qt))
                                {
                                    super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).setQuantidadeVendida(
                                            super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).getQuantidadeVendida() + (int)qt);
                                    posicaoGrupo = super.campanhaGrupos.indexOf(c);
                                }
                                else
                                {
                                    super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).setQuantidadeVendida(
                                            super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).getQuantidadeVendida() + (int)qt);

                                    if(posicaoGrupo == -1)
                                        posicaoGrupo = -2;
                                }
                            }
                        }
                    }
                }
                catch (GenercicException e)
                {
                    e.printStackTrace();
                    posicaoGrupo = -1;
                }
            }
        }
        else
        {
            super.campanhaGrupos = new ArrayList<>();
            ConsultaMinimosGravososKitsCampanhas campanhas = new ConsultaMinimosGravososKitsCampanhas(super.context);
            try
            {
//                CampanhaGrupo camp = campanhas.buscarCampanha(codigo);

                ArrayList<CampanhaGrupo> camps = campanhas.buscarCampanhas(codigo);

                if(camps == null)
                    posicaoGrupo = -1;
                else
                {
                    super.campanhaGrupos.addAll(camps);

                    for(CampanhaGrupo c : super.campanhaGrupos)
                    {
                        if ((c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == produto.getSubGrupo() && c.getGrupo().getDivisao() == produto.getDivisao()) ||
                            (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == produto.getSubGrupo() && c.getGrupo().getDivisao() == 0) ||
                            (c.getGrupo().getGrupo() == produto.getGrupo() && c.getGrupo().getSubGrupo() == 0                     && c.getGrupo().getDivisao() == 0))
                        {
                            float qt = super.itensVendidos.get(super.itensVendidos.size() - 1).getQuantidade();

                            if(c.getQuantidade() <= (c.getQuantidadeVendida() + qt))
                            {
                                super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).setQuantidadeVendida(
                                        super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).getQuantidadeVendida() + (int)qt);
                                posicaoGrupo = super.campanhaGrupos.indexOf(c);
                            }
                            else
                            {
                                super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).setQuantidadeVendida(
                                        super.campanhaGrupos.get(super.campanhaGrupos.indexOf(c)).getQuantidadeVendida() + (int)qt);

                                if(posicaoGrupo == -1)
                                    posicaoGrupo = -2;
                            }
                        }
                    }
                }
            }
            catch (GenercicException e)
            {
                e.printStackTrace();
                posicaoGrupo = -1;
            }
        }

        if(posicaoGrupo > -1)
        {
            if(super.campanhaGrupos.get(posicaoGrupo).getDescontoAplicado() > 0)
            {
                super.itensVendidos.get(super.itensVendidos.size() - 1).setDescontoCG(super.campanhaGrupos.get(posicaoGrupo).getDescontoAplicado());
                super.itensVendidos.get(super.itensVendidos.size() - 1).setDescontoCampanha(true);

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
                                super.itensVendidos.get(super.itensVendidos.size() - 1).getDescontoCP(), 0,
                                super.itensVendidos.get(super.itensVendidos.size() - 1).getItem()
                        ));

                return -1;
            }
            else
                return posicaoGrupo;
        }
        else
        {
            return -1;
        }
    }

    public int  verificarCampanhas()
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

        int posicaoCampanhaP = -1;

        if(super.campanhaProdutos != null && super.campanhaProdutos.size() > 0)
        {
            for(CampanhaProduto cp : super.campanhaProdutos)
            {
                for(Integer item : cp.getItens())
                {
                    if(item == produto.getCodigo())
                    {
                        float totalCampanha = cp.getQuantidadeVendida();
                        totalCampanha -= this.getQuantidadeRemover();

                        if(totalCampanha < cp.getQuantidade()/* && cp.getDescontoAplicado() > 0*/)
                        {
                            for(ItensVendidos iv : this.itensVendidos)
                            {
                                Grupo gIv = this.controleProdutos.getGrupoItem(iv.getItem());
                                if(iv.getItem() == item)
                                {
                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setDescontoCP(0);
                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).
                                            setDescontoCampanha(this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCG() > 0);

                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setValorLiquido(
                                            this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getValorDigitado());

                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).setTotal(this.calcularTotal
                                            (
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getQuantidade(),
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getValorDigitado(),
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDesconto(),
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCG(),
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getDescontoCP(), 0,
                                                    this.itensVendidos.get(this.itensVendidos.indexOf(iv)).getItem()
                                            ));
                                }
                            }

                            this.campanhaProdutos.get(this.campanhaProdutos.indexOf(cp)).setQuantidadeVendida(totalCampanha);
                            this.campanhaProdutos.get(this.campanhaProdutos.indexOf(cp)).setDescontoAplicado(0);
                        }
                    }
                }
            }


            posicaoCampanhaP = -1;
            for(CampanhaProduto cp : super.campanhaProdutos)
            {
                for(int nrI = 0; nrI < cp.getItens().size(); nrI++)
                {
                    int codigoCampanha = cp.getItens().get(nrI);
                    if (codigoCampanha == produto.getCodigo())
                    {
                        posicaoCampanhaP = super.campanhaProdutos.indexOf(cp);
                        break;
                    }

                    if(posicaoCampanhaP != -1)
                        break;
                }

                if(posicaoCampanhaP != -1)
                    break;
            }

            if(posicaoCampanhaP == -1)
            {
                ConsultaMinimosGravososKitsCampanhas campanhas = new ConsultaMinimosGravososKitsCampanhas(super.context);
                try
                {
                    CampanhaProduto camp = campanhas.buscarCampanhaP(codigo);

                    if(camp == null)
                        posicaoCampanhaP = -1;
                    else
                    {
                        super.campanhaProdutos.add(camp);
                        posicaoCampanhaP = (super.campanhaProdutos.size() - 1);
                    }
                }
                catch (GenercicException e)
                {
                    e.printStackTrace();
                    posicaoCampanhaP = -1;
                }
            }
        }
        else
        {
            super.campanhaProdutos = new ArrayList<>();
            ConsultaMinimosGravososKitsCampanhas campanhas = new ConsultaMinimosGravososKitsCampanhas(super.context);
            try
            {
                CampanhaProduto camp = campanhas.buscarCampanhaP(codigo);

                if(camp == null)
                    posicaoCampanhaP = -1;
                else
                {
                    super.campanhaProdutos.add(camp);
                    posicaoCampanhaP = 0;
                }
            }
            catch (GenercicException e)
            {
                e.printStackTrace();
                posicaoCampanhaP = -1;
            }
        }

        if(posicaoCampanhaP != -1)
        {
            super.campanhaProdutos.get(posicaoCampanhaP).setQuantidadeVendida(
                    super.campanhaProdutos.get(posicaoCampanhaP).getQuantidadeVendida() +
                    (int) super.itensVendidos.get(super.itensVendidos.size() -1).getQuantidade());

            if(super.campanhaProdutos.get(posicaoCampanhaP).getQuantidadeVendida() >= super.campanhaProdutos.get(posicaoCampanhaP).getQuantidade())
            {
                if(super.campanhaProdutos.get(posicaoCampanhaP).getDescontoAplicado() > 0)
                {
                    super.itensVendidos.get(super.itensVendidos.size() - 1).setDescontoCP(super.campanhaProdutos.get(posicaoCampanhaP).getDescontoAplicado());
                    super.itensVendidos.get(super.itensVendidos.size() - 1).setDescontoCampanha(true);

                    super.itensVendidos.get(super.itensVendidos.size() - 1).setTotal(super.calcularTotal(
                            super.itensVendidos.get(super.itensVendidos.size() - 1).getQuantidade(), super.itensVendidos.get(super.itensVendidos.size() - 1).getValorDigitado(),
                            super.itensVendidos.get(super.itensVendidos.size() - 1).getDesconto(), super.itensVendidos.get(super.itensVendidos.size() - 1).getDescontoCG(),
                            super.itensVendidos.get(super.itensVendidos.size() - 1).getDescontoCP(), 0,
                            super.itensVendidos.get(super.itensVendidos.size() - 1).getItem()));

                    return -1;
                }
                else
                    return posicaoCampanhaP;
            }
            else
                return -1;
        }
        else
        {
            return -1;
        }
    }

    public boolean buscarPorCampanhas(int pos)
    {
        boolean encontrado = false;
        Item i = super.controleProdutos.getItem(pos);
        Grupo g = new Grupo();
        g = super.controleProdutos.getGrupoItem(i.getCodigo());

        /* As consultas de item não estão trazendo o grupo (provavelmente redução de consumo de memória) da pra remover os campos das classes relacionadas
        g.setGrupo(i.getGrupo());
        g.setSubGrupo(i.getSubGrupo());
        g.setDivisao(i.getDivisao());
        */

        if(super.campanhaGrupos != null && super.campanhaGrupos.size() > 0)
            for (CampanhaGrupo cg : super.campanhaGrupos)
            {
                if(cg.getGrupo().equals(g))
                    return true;
            }

        if(super.campanhaProdutos != null && super.campanhaProdutos.size() > 0)
            for (CampanhaProduto cp : super.campanhaProdutos)
            {
                for(Integer it : cp.getItens())
                    if (i.getCodigo() == it)
                        return true;
            }

        return encontrado;
    }

    public int aplicarDescontoTabloide(float percentual, int posicao, int tipo)
    {
        if(percentual >= 0)
        {
            if(tipo == 0)
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
                            super.itensVendidos.get(i).setDescontoCampanha(true);
                        /*
                        super.itensVendidos.get(i).setValorLiquido(super.itensVendidos.get(i)
                                .getValorDigitado() - ((super.itensVendidos.get(i).getValorDigitado() * percentual) / 100));
                        */

                            super.itensVendidos.get(i).setValorLiquido(
                                    super.itensVendidos.get(i).getValorDigitado() -
                                            ((super.itensVendidos.get(i).getValorDigitado() *
                                                    percentual) / 100));



                            super.itensVendidos.get(i).setTotal(super.calcularTotal(
                                    super.itensVendidos.get(i).getQuantidade(), super.itensVendidos.get(i).getValorDigitado(),
                                    super.itensVendidos.get(i).getDesconto(), super.itensVendidos.get(i).getDescontoCG(),
                                    super.itensVendidos.get(i).getDescontoCP(), 0,
                                    super.itensVendidos.get(i).getItem()));
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
                if (super.campanhaProdutos.get(posicao).getDesconto() >= percentual)
                {
                    super.campanhaProdutos.get(posicao).setDescontoAplicado(percentual);

                    for(int i = 0; i < super.itensVendidos.size(); i++)
                    {
                        Item produto = null;
                        try { produto = super.controleProdutos.getItemCodigo(super.itensVendidos.get(i).getItem()); }
                        catch (GenercicException e) { e.printStackTrace(); }

                        for (int j = 0; j < super.campanhaProdutos.get(posicao).getItens().size(); j++)
                        {
                            int codigo = super.campanhaProdutos.get(posicao).getItens().get(j);
                            if(codigo == produto.getCodigo())
                            {
                                super.itensVendidos.get(i).setDescontoCP(percentual);
                                super.itensVendidos.get(i).setDescontoCampanha(true);

                                super.itensVendidos.get(i).setValorLiquido(
                                        super.itensVendidos.get(i).getValorDigitado() -
                                                ((super.itensVendidos.get(i).getValorDigitado() *
                                                        percentual) / 100));

                                super.itensVendidos.get(i).setTotal(super.calcularTotal(
                                        super.itensVendidos.get(i).getQuantidade(), super.itensVendidos.get(i).getValorDigitado(),
                                        super.itensVendidos.get(i).getDesconto(), super.itensVendidos.get(i).getDescontoCG(),
                                        super.itensVendidos.get(i).getDescontoCP(), 0,
                                        super.itensVendidos.get(i).getItem()));
                            }
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
        nda.setSearchData(super.codigoNatureza);

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

    protected void getPrazosList() throws GenercicException
    {
        PrazoDataAccess pda = new PrazoDataAccess(super.context);

        pda.setSearchType(1);
        pda.setSearchData(super.codigoPrazo);

        super.listaPrazos = pda.buscarRestrito();
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
            if(super.controleDigitacao.valorMaximo(super.context, this.getClass()))
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

                    /*
                    GAMBIARRA PRA CLIENTE MALA
                     */
                    if(super.codigoNatureza == 21 && super.controleConfiguracao.getConfigEmp().getCodigo() == 7)
                    {
                        if(saldo - super.controleDigitacao.calcularTotal() >= 0)
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
                    else
                    {
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
            }
            else
            {
                EfetuarPedidos.erro = true;
                EfetuarPedidos.strErro = "Valor acima do permitido!\nPor favor verifique.";
                return false;
            }
        }
    }

    public Boolean mostraFlexItem() { return super.controleConfiguracao.getConfigVda().getFlexItem(); }

    public Boolean mostraFlexVenda() { return super.controleConfiguracao.getConfigVda().getFlexVenda(); }
/**************************************************************************************************/
/*****************************                                        *****************************/
/**************************************************************************************************/
    public ArrayList<CampanhaGrupo> getCampanhaGrupos() { return campanhaGrupos; }

    public ArrayList<CampanhaProduto> getCampanhaProdutos() { return campanhaProdutos; }

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
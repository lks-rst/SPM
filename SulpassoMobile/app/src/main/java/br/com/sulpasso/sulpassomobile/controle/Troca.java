package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.R;
import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaGrupo;
import br.com.sulpasso.sulpassomobile.modelo.CampanhaProduto;
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

/**
 * Created by Lucas on 21/03/2017 - 16:57 as part of the project SulpassoMobile.
 */
public class Troca extends EfetuarPedidos {

    /**
     * @param ctx
     * @param tipo
     ************************************************************************************************/
    public Troca(Context ctx, String tipo) { super(ctx, tipo); }

    @Override
    public ArrayList<String> listarCLientes(int tipo, String dados) throws GenercicException
    {
        return super.controleClientes.exibirLista(tipo, dados);
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

    @Override
    public ArrayList<String> listarPrazos(boolean position) throws GenercicException
    {
        ArrayList<String> lista = new ArrayList<>();

        this.getPrazosList();
        for(Prazo p : super.listaPrazos) lista.add(p.toDisplay());

        return lista;
    }

    @Override
    public Boolean permitirClick(int id)
    {
        Boolean click = false;

        switch (id)
        {
            case R.id.fdcSpnrClientes :
                click = super.clientIsClicable();
                break;
            case R.id.fdcSpnrNaturezas :
                click = false;
                break;
            case R.id.fdcSpnrPrazos :
                click = false;
                break;
            case R.id.ffpSpnrNaturezas :
                click = false;
                break;
            case R.id.ffpSpnrPrazos :
                click = false;
                break;
        }

        return click;
    }

    @Override
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
        return 0;
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

        /*Verificar no configurador*/

        super.tabela = 8;
        super.venda.setTabela(super.tabela);
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
        /*
        ItensVendidos item = super.controleDigitacao.confirmarItem(
                super.controleConfiguracao.descontoMaximo(), super.controleConfiguracao.alteraValor("d"), super.context, super.senha);
        */

        ItensVendidos item = super.controleDigitacao.confirmarItem(
                super.controleConfiguracao.descontoMaximo(), super.controleConfiguracao.alteraValor("d"), super.context, super.senha,
                super.codigoNatureza, super.controleConfiguracao.getConfigEmp().getCodigo(), super.controleConfiguracao.getConfigHor().getMaximoItens(),
                this.getClass(), "TR", 2);

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
                    super.itensVendidos.set(posicao, item);

                    Toast.makeText(context, "Item " + item.getItem() + " alterado!", Toast.LENGTH_LONG).show();
                }
                else { super.itensVendidos.add(item); }

                return true;
            }
            else return false;
        }
        else return false;
    }

    public int finalizarPedido(Boolean justificar)
    {
        super.venda.setItens(super.itensVendidos);
        super.venda.setValor(Double.parseDouble(String.valueOf(super.valorVendido())));
        super.venda.setCodigoCliente(super.controleClientes.getCodigoClienteSelecionado());
        super.venda.setDesconto(Double.parseDouble(String.valueOf(super.controleSalvar.getDesconto())));
        super.venda.setData(super.dataSistema());
        super.venda.setHora(super.horaSistema());
        super.venda.setNatureza(super.codigoNatureza);
        super.venda.setBanco(super.controleClientes.getBancoCliente());

        super.venda.setTipo("TR");

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

    public String buscarDadosVenda(int campo)
    {
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

                    /*
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
                    */

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

    public int verificarCampanhas()
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



    public boolean buscarPorCampanhas(int pos){return false;}
/**************************************************************************************************/
/*****************************                                        *****************************/
    /**************************************************************************************************/
    protected void getNaturezasList(Boolean especial) throws GenercicException
    {
        NaturezaDataAccess nda = new NaturezaDataAccess(super.context);

        super.listaNaturezas = nda.buscarAVista();
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
            /*Melhorar isso*/
            if(super.controleDigitacao.valorMaximo(super.context, this.getClass()))
            {
                return true;
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

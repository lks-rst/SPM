package br.com.sulpassomobile.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.HashMap;

/*
    #TODO: Criar a função para verificação da forma de desconto, saldo flex ou contribuição.
    #TODO: Criar a função para verificação do tipo de desconto, percentual ou alteração do valor.
    #TODO: Criar função para validação do saldo do vendedor.
    #TODO: Criar função para verificação do valor minimo de venda.
    TODO: Criar função para busca do Map com as configurações de venda.
    #TODO: Criar função para busca do percentual maximo de desconto em um item (configuração geral).
 */

/**
 * Created by Lucas on 02/08/2016.
 */
public class ConfigurarSistema
{
    private Context context;
    private float saldoAtual;

    private HashMap<String, String> configuracoesVenda;

    public ConfigurarSistema(Context context)
    {
        this.context = context;
        this.getConfiguracoesVenda();
        this.saldoAtual = Float.parseFloat(this.getConfiguracao(1));
    }

    public float getSaldoAtual() { return saldoAtual; }

    public void setSaldoAtual(float saldoAtual)
    {
        this.saldoAtual = saldoAtual;
    }

    public float pedidoMinimo()
    {
        return this.getValorMinimo();
    }

    /*
    public int tipoDesconto() { return this.getTipoDesconto(); }
    */

    public int formaDesconto() { return this.getFormaDesconto(); }

    public Boolean alteraValor(String campo)
    {
        if(this.podeModificarPreco())
        {
            if(campo.equals("v"))
                if(this.getTipoDesconto() == 1)
                    return true;
                else
                    return false;
            else
                if(this.getTipoDesconto() == 1)
                    return false;
                else
                    return true;
        }
        else
        {
            return false;
        }
    }

    public float descontoMaximo() { return this.getDesconto(); }

    public boolean contribuicaoIdeal()
    {
        return this.verifyContribuicao();
    }

    public String buscarFlex()
    {
        return this.getConfiguracao(1);
    }
    /**
     * Retorna o tipo de modificação de preço possivel para o vendedor, se por alteração do preço
     * real do item ou por desconto percentual aplicado ao item.
     *
     * @return 1 para alteração de valor 0 para desconto percentual
     */
    private int getTipoDesconto() { return 1; }

    /**
     * Retorna a forma de desconto a ser utilizada, com base no saldo flex ou com base na contribuição
     *
     * @return 1 para saldo flez 0 para contribuição
     */
    private int getFormaDesconto() { return 1; }

    /**
     * Verifica se esta configurada a alteração de preços para o sistema, não considera as
     * configurações do cliente ou do item que esta sendo vendido
     *
     * @return true - false
     */
    private Boolean podeModificarPreco() { return true; }

    private float getValorMinimo() { return 50; }

    private float getDesconto() { return 10; }

    private Boolean verifyContribuicao() { return true; }

    private void getConfiguracoesVenda() { /*****/ }

    private String getConfiguracao(int data)
    {
        switch (data)
        {
            case 1:
                return "50.00";
            default :
                return "--";
        }
    }
}

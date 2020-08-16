package br.com.sulpasso.sulpassomobile.modelo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lucas on 05/12/2016 - 09:10 as part of the project SulpassoMobile.
 */
public class ConfiguradorVendas
{
    private Boolean gerenciarVisitas;
    private Boolean venderDevedores;
    private Boolean alteraNaturezaInicio;
    private Boolean alteraNaturezaFim;
    private Boolean alteraPrazoInicio;
    private Boolean alteraPrazoFim;
    private Boolean recalcularPreco;
    private Boolean alteraPrecos;
    private Boolean limiteCredito;
    private Boolean tipoFlex;
    private Boolean flexVenda;
    private Boolean flexItem;
    private Boolean controlaEstoque;
    private Boolean descontoGrupo;
    private Boolean frete;
    private Boolean solicitaSenha;
    private Boolean especialAlteraValor;
    private int especialAlteraValor2;
    private Boolean minimoPrazo;
    private Boolean controlaGps;
    private Boolean descontoPecentual;
    private String validade;
    private int minimoItensDiferentes;

    public ConfiguradorVendas()
    {
    }

    public ConfiguradorVendas(JSONObject venda) throws JSONException
    {
        this.gerenciarVisitas = (venda.getInt("gerenciarVisitas") == 1 ? true : false);
        this.venderDevedores = (venda.getInt("venderDevedores") == 1 ? true : false);
        this.alteraNaturezaInicio = (venda.getInt("alteraNaturezaInicio") == 1 ? true : false);
        this.alteraNaturezaFim = (venda.getInt("alteraNaturezaFim") == 1 ? true : false);
        this.alteraPrazoInicio = (venda.getInt("alteraPrazoInicio") == 1 ? true : false);
        this.alteraPrazoFim = (venda.getInt("alteraPrazoFim") == 1 ? true : false);
        this.recalcularPreco = (venda.getInt("recalcularPreco") == 1 ? true : false);
        this.alteraPrecos = (venda.getInt("alteraPrecos") == 1 ? true : false);
        this.limiteCredito = (venda.getInt("limiteCredito") == 1 ? true : false);
        this.tipoFlex = (venda.getInt("tipoFlex") == 1 ? true : false);
        this.flexVenda = (venda.getInt("flexVenda") == 1 ? true : false);
        this.flexItem = (venda.getInt("flexItem") == 1 ? true : false);
        this.controlaEstoque = (venda.getInt("controlaEstoque") == 1 ? true : false);
        this.descontoGrupo = (venda.getInt("descontoGrupo") == 1 ? true : false);
        this.frete = (venda.getInt("frete") == 1 ? true : false);
        this.solicitaSenha = (venda.getInt("solicitaSenha") == 1 ? true : false);
        this.especialAlteraValor = (venda.getInt("especialAlteraValor") == 1 ? true : false);
        this.especialAlteraValor2 = venda.getInt("especialAlteraValor");
        this.minimoPrazo = (venda.getInt("minimoPrazo") == 1 ? true : false);
        this.controlaGps = (venda.getInt("controlaGps") == 1 ? true : false);
        this.descontoPecentual = (venda.getInt("descontoPecentual") == 1 ? true : false);
        this.validade = venda.getString("validade");
        this.minimoItensDiferentes = venda.getInt("itensDiferentes");
    }

    public Boolean getGerenciarVisitas() { return gerenciarVisitas; }

    public void setGerenciarVisitas(Boolean gerenciarVisitas) { this.gerenciarVisitas = gerenciarVisitas; }

    public void setGerenciarVisitas(int gerenciarVisitas) { this.gerenciarVisitas = gerenciarVisitas == 1 ? true : false; }

    public Boolean getVenderDevedores() { return venderDevedores; }

    public void setVenderDevedores(Boolean venderDevedores) { this.venderDevedores = venderDevedores; }

    public Boolean getAlteraNaturezaInicio() { return alteraNaturezaInicio; }

    public void setAlteraNaturezaInicio(Boolean alteraNaturezaInicio) { this.alteraNaturezaInicio = alteraNaturezaInicio; }

    public Boolean getAlteraNaturezaFim() { return alteraNaturezaFim; }

    public void setAlteraNaturezaFim(Boolean alteraNaturezaFim) { this.alteraNaturezaFim = alteraNaturezaFim; }

    public Boolean getAlteraPrazoInicio() { return alteraPrazoInicio; }

    public void setAlteraPrazoInicio(Boolean alteraPrazoInicio) { this.alteraPrazoInicio = alteraPrazoInicio; }

    public Boolean getAlteraPrazoFim() { return alteraPrazoFim; }

    public void setAlteraPrazoFim(Boolean alteraPrazoFim) { this.alteraPrazoFim = alteraPrazoFim; }

    public Boolean getRecalcularPreco() { return recalcularPreco; }

    public void setRecalcularPreco(Boolean recalcularPreco) { this.recalcularPreco = recalcularPreco; }

    public Boolean getAlteraPrecos() { return alteraPrecos; }

    public void setAlteraPrecos(Boolean alteraPrecos) { this.alteraPrecos = alteraPrecos; }

    public Boolean getLimiteCredito() { return limiteCredito; }

    public void setLimiteCredito(Boolean limiteCredito) { this.limiteCredito = limiteCredito; }

    public Boolean getTipoFlex() { return tipoFlex; }

    public void setTipoFlex(Boolean tipoFlex) { this.tipoFlex = tipoFlex; }

    public Boolean getFlexVenda() { return flexVenda; }

    public void setFlexVenda(Boolean flexVenda) { this.flexVenda = flexVenda; }

    public Boolean getFlexItem() { return flexItem; }

    public void setFlexItem(Boolean flexItem) { this.flexItem = flexItem; }

    public Boolean getControlaEstoque() { return controlaEstoque; }

    public void setControlaEstoque(Boolean controlaEstoque) { this.controlaEstoque = controlaEstoque; }

    public Boolean getDescontoGrupo() { return descontoGrupo; }

    public void setDescontoGrupo(Boolean descontoGrupo) { this.descontoGrupo = descontoGrupo; }

    public Boolean getFrete() { return frete; }

    public void setFrete(Boolean frete) { this.frete = frete; }

    public Boolean getSolicitaSenha() { return solicitaSenha; }

    public void setSolicitaSenha(Boolean solicitaSenha) { this.solicitaSenha = solicitaSenha; }

    public Boolean getEspecialAlteraValor() { return especialAlteraValor; }

    public void setEspecialAlteraValor(Boolean especialAlteraValor) { this.especialAlteraValor = especialAlteraValor; }

    public Boolean getMinimoPrazo() { return minimoPrazo; }

    public void setMinimoPrazo(Boolean minimoPrazo) { this.minimoPrazo = minimoPrazo; }

    public Boolean getControlaGps() { return controlaGps; }

    public void setControlaGps(Boolean controlaGps) { this.controlaGps = controlaGps; }

    public Boolean getDescontoPecentual() { return descontoPecentual; }

    public void setDescontoPecentual(Boolean descontoPecentual) { this.descontoPecentual = descontoPecentual; }

    public void setDescontoPecentual(int descontoPecentual) { this.descontoPecentual = descontoPecentual == 1 ? true : false; }

    public String getValidade() { return validade; }

    public void setValidade(String validade) { this.validade = validade; }

    public int getEspecialAlteraValor2() { return especialAlteraValor2; }

    public void setEspecialAlteraValor2(int especialAlteraValor2) { this.especialAlteraValor2 = especialAlteraValor2; }

    public int getMinimoItensDiferentes() {
        return minimoItensDiferentes;
    }

    public void setMinimoItensDiferentes(int minimoItensDiferentes) {
        this.minimoItensDiferentes = minimoItensDiferentes;
    }

    @Override
    public String toString() {
        return "{" +
                "\"gerenciarVisitas\":\"" + gerenciarVisitas +
                "\", \"venderDevedores\":\"" + venderDevedores +
                "\", \"alteraNaturezaInicio\":\"" + alteraNaturezaInicio +
                "\", \"alteraNaturezaFim\":\"" + alteraNaturezaFim +
                "\", \"alteraPrazoInicio\":\"" + alteraPrazoInicio +
                "\", \"alteraPrazoFim\":\"" + alteraPrazoFim +
                "\", \"recalcularPreco\":\"" + recalcularPreco +
                "\", \"alteraPrecos\":\"" + alteraPrecos +
                "\", \"limiteCredito\":\"" + limiteCredito +
                "\", \"tipoFlex\":\"" + tipoFlex +
                "\", \"flexVenda\":\"" + flexVenda +
                "\", \"flexItem\":\"" + flexItem +
                "\", \"controlaEstoque\":\"" + controlaEstoque +
                "\", \"descontoGrupo\":\"" + descontoGrupo +
                "\", \"frete\":\"" + frete +
                "\", \"solicitaSenha\":\"" + solicitaSenha +
                "\", \"especialAlteraValor\":\"" + especialAlteraValor +
                "\", \"minimoPrazo\":\"" + minimoPrazo +
                "\", \"controlaGps\":\"" + controlaGps +
                "\", \"descontoPecentual\":\"" + descontoPecentual +
                "\", \"validade\":\"" + validade +
                "\"}";
    }
}
package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

import java.util.ArrayList;

/**
 * Created by Lucas on 02/08/2016.
 */
public class Cliente
{
    private String bairro;
    private String endereco;
    private float mediaCompras;
    private float mediaIndustrializados;
    private float realizado;
    private String dataUltimaCompra;
    private String ie;
    private String email;
    private char tipo;
    private String cpf;
    private String numero;
    private String mensagem;
    private Integer vendido;
    private String cep;
    private float saldoCredito;
    private float limiteCredito;
    private float metaPeso;
    private String celular;
    private String telefone;
    private String consumidor;
    private String razao;
    private Integer tabela;
    private String cgc;
    private String contato;
    private Integer roteiro;
    private char alteraPrazo;
    private char especial;
    private Integer visita;
    private float saldo;
    private char situacao;
    private Integer codigoCliente;
    private String fantasia;
    private Integer atividade;
    private Integer codigoCidade;
    private Integer prazo;
    private Integer natureza;
    private Integer banco;

    private ArrayList<ContasReceber> contas;
/****************************************                  ****************************************/
/****************************************                  ****************************************/
/****************************************                  ****************************************/
    public String getBairro() { return bairro; }

    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) { this.endereco = endereco; }

    public float getMediaCompras() { return mediaCompras; }

    public void setMediaCompras(float mediaCompras) { this.mediaCompras = mediaCompras; }

    public float getMediaIndustrializados() { return mediaIndustrializados; }

    public void setMediaIndustrializados(float mediaIndustrializados) { this.mediaIndustrializados = mediaIndustrializados; }

    public float getRealizado() { return realizado; }

    public void setRealizado(float realizado) { this.realizado = realizado; }

    public String getDataUltimaCompra() { return dataUltimaCompra; }

    public void setDataUltimaCompra(String dataUltimaCompra) { this.dataUltimaCompra = dataUltimaCompra; }

    public String getIe() { return ie; }

    public void setIe(String ie) { this.ie = ie; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public char getTipo() { return tipo; }

    public void setTipo(char tipo) { this.tipo = tipo; }

    public String getCpf() { return cpf; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getNumero() { return numero; }

    public void setNumero(String numero) { this.numero = numero; }

    public String getMensagem() { return mensagem; }

    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public Integer getVendido() { return vendido; }

    public void setVendido(Integer vendido) { this.vendido = vendido; }

    public String getCep() { return cep; }

    public void setCep(String cep) { this.cep = cep; }

    public float getSaldoCredito() { return saldoCredito; }

    public void setSaldoCredito(float saldoCredito) { this.saldoCredito = saldoCredito; }

    public float getLimiteCredito() { return limiteCredito; }

    public void setLimiteCredito(float limiteCredito) { this.limiteCredito = limiteCredito; }

    public float getMetaPeso() { return metaPeso; }

    public void setMetaPeso(float metaPeso) { this.metaPeso = metaPeso; }

    public String getCelular() { return celular; }

    public void setCelular(String celular) { this.celular = celular; }

    public String getTelefone() { return telefone; }

    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getConsumidor() { return consumidor; }

    public void setConsumidor(String consumidor) { this.consumidor = consumidor; }

    public String getRazao() { return razao; }

    public void setRazao(String razao) { this.razao = razao; }

    public Integer getTabela() { return tabela; }

    public void setTabela(Integer tabela) { this.tabela = tabela; }

    public String getCgc() { return cgc; }

    public void setCgc(String cgc) { this.cgc = cgc; }

    public String getContato() { return contato; }

    public void setContato(String contato) { this.contato = contato; }

    public Integer getRoteiro() { return roteiro; }

    public void setRoteiro(Integer roteiro) { this.roteiro = roteiro; }

    public char getAlteraPrazo() { return alteraPrazo; }

    public void setAlteraPrazo(char alteraPrazo) { this.alteraPrazo = alteraPrazo; }

    public char getEspecial() { return especial; }

    public void setEspecial(char especial) { this.especial = especial; }

    public Integer getVisita() { return visita; }

    public void setVisita(Integer visita) { this.visita = visita; }

    public float getSaldo() { return saldo; }

    public void setSaldo(float saldo) { this.saldo = saldo; }

    public char getSituacao() { return situacao; }

    public void setSituacao(char situacao) { this.situacao = situacao; }

    public Integer getCodigoCliente() { return codigoCliente; }

    public void setCodigoCliente(Integer codigoCliente) { this.codigoCliente = codigoCliente; }

    public String getFantasia() { return fantasia; }

    public void setFantasia(String fantasia) { this.fantasia = fantasia; }

    public Integer getAtividade() { return atividade; }

    public void setAtividade(Integer atividade) { this.atividade = atividade; }

    public Integer getCodigoCidade() { return codigoCidade; }

    public void setCodigoCidade(Integer codigoCidade) { this.codigoCidade = codigoCidade; }

    public Integer getPrazo() { return prazo; }

    public void setPrazo(Integer prazo) { this.prazo = prazo; }

    public Integer getNatureza() { return natureza; }

    public void setNatureza(Integer natureza) { this.natureza = natureza; }

    public Integer getBanco() { return banco; }

    public void setBanco(Integer banco) { this.banco = banco; }

    public ArrayList<ContasReceber> getContas() { return contas; }

    public void setContas(ArrayList<ContasReceber> contas) { this.contas = contas; }
/****$************************************                  ****************************************/
/****************************************                  ****************************************/
/****************************************                  ****************************************/
    @Override
    public String toString()
    {
        return "Cliente{" +
                "bairro='" + bairro + '\'' +
                ", endereco='" + endereco + '\'' +
                ", mediaCompras=" + mediaCompras +
                ", mediaIndustrializados=" + mediaIndustrializados +
                ", realizado=" + realizado +
                ", dataUltimaCompra='" + dataUltimaCompra + '\'' +
                ", ie='" + ie + '\'' +
                ", email='" + email + '\'' +
                ", tipo=" + tipo +
                ", cpf='" + cpf + '\'' +
                ", numero='" + numero + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", vendido=" + vendido +
                ", cep='" + cep + '\'' +
                ", saldoCredito=" + saldoCredito +
                ", limiteCredito=" + limiteCredito +
                ", metaPeso=" + metaPeso +
                ", celular='" + celular + '\'' +
                ", telefone='" + telefone + '\'' +
                ", consumidor='" + consumidor + '\'' +
                ", razao='" + razao + '\'' +
                ", tabela=" + tabela +
                ", cgc=" + cgc +
                ", contato='" + contato + '\'' +
                ", roteiro=" + roteiro +
                ", alteraPrazo=" + alteraPrazo +
                ", especial=" + especial +
                ", visita=" + visita +
                ", saldo=" + saldo +
                ", situacao=" + situacao +
                ", codigoCliente=" + codigoCliente +
                ", fantasia='" + fantasia + '\'' +
                ", atividade=" + atividade +
                ", codigoCidade=" + codigoCidade +
                ", prazo=" + prazo +
                ", natureza=" + natureza +
                ", banco=" + banco +
                '}';
    }

    public String toDisplay()
    {
        return codigoCliente + " - " + razao.trim();
    }
}
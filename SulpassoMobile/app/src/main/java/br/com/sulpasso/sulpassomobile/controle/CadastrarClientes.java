package br.com.sulpasso.sulpassomobile.controle;

import android.content.Context;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.modelo.Atividade;
import br.com.sulpasso.sulpassomobile.modelo.Banco;
import br.com.sulpasso.sulpassomobile.modelo.Cidade;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.ClienteNovo;
import br.com.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpasso.sulpassomobile.modelo.Tipologia;
import br.com.sulpasso.sulpassomobile.persistencia.queries.ClienteNovoDataAccess;

/**
 * Created by Lucas on 02/08/2016.
 */
public class CadastrarClientes
{
    private int tipo;

    private ClienteNovoDataAccess cda;
    private ClienteNovo cliente;
    private ArrayList<ClienteNovo> clientes;

    public CadastrarClientes(Context ctx)
    {
        this.cda = new ClienteNovoDataAccess(ctx);
        this.cliente = new ClienteNovo();
        this.tipo = 1;
    }

    public int getTipo() { return tipo; }

    public void setTipo(int tipo) { this.tipo = tipo; }

    public void setCliente(ClienteNovo cliente) { this.cliente = cliente; }

    public boolean validarDocumento(String data, int t)
    {
        if (t == 1)
            return this.validar_cgc(data);
        else
            return this.validar_cpf(data);
    }

    public boolean cadastro_obrigatorio() { return this.cda.cadastro_obrigatorio(); }

    public ArrayList<String > listar_estados() { return this.cda.listar_estados(); }

    public ArrayList<Atividade> listar_atividades() { return this.cda.listar_atividades(); }

    public ArrayList<Banco> listar_bancos() { return this.cda.listar_bancos(); }

    public ArrayList<Cidade> listar_cidades(String s) { return this.cda.listar_cidades(s); }

    public ArrayList<Natureza> listar_naturezas() { return this.cda.listar_naturezas(); }

    public ArrayList<Tipologia> listar_tipologias() { return this.cda.listar_tipologias(); }

    public ArrayList<Cliente> listar_clientes() { return this.cda.listar_clientes(); }

    public int getEmpreza() { return this.cda.getEmpreza(); }

    public void salvarCadastro()
    {
        this.cda.salvarCliente(this.cliente);
    }


    //	http://www.devmedia.com.br/validando-o-cnpj-em-uma-aplicacao-java/22374
    private Boolean validar_cgc(String cgc)
    {
        Boolean valido = false;

        cgc = cgc.replace("/", "");
        cgc = cgc.replace("-", "");
        cgc = cgc.replace(".", "");

        if (cgc.length() == 14)
        {
            if (cgc.equalsIgnoreCase("11111111111111") ||
                    cgc.equalsIgnoreCase("22222222222222") ||
                    cgc.equalsIgnoreCase("33333333333333") ||
                    cgc.equalsIgnoreCase("44444444444444") ||
                    cgc.equalsIgnoreCase("55555555555555") ||
                    cgc.equalsIgnoreCase("66666666666666") ||
                    cgc.equalsIgnoreCase("77777777777777") ||
                    cgc.equalsIgnoreCase("88888888888888") ||
                    cgc.equalsIgnoreCase("99999999999999") ||
                    cgc.equalsIgnoreCase("00000000000000")){
            }
            else
            {
                String dig13, dig14;
                int sm, i, r, num, peso;

                try
                {
//					calculando o primeiro digito
                    sm = 0;
                    peso = 2;

                    for (i = 11; i >= 0; i--)
                    {
                        num = Integer.parseInt(cgc.substring(i, i + 1));
                        sm = sm + (num * peso);
                        peso = peso + 1;
                        if (peso == 10)
                            peso = 2;
                    }

                    r = sm % 11;

                    if ((r == 0) || (r == 1))
                        dig13 = "0";
                    else
                        dig13 = "" + (11 - r);

//					calculando o segundo digito
                    sm = 0;
                    peso = 2;

                    for (i = 12; i >= 0; i--)
                    {
                        num = Integer.parseInt(cgc.substring(i, i + 1));
                        sm = sm + (num * peso);
                        peso = peso + 1;
                        if (peso == 10)
                            peso = 2;
                    }

                    r = sm % 11;

                    if ((r == 0) || (r == 1))
                        dig14 = "0";
                    else
                        dig14 = "" + (11 - r);

                    if ((dig13.equalsIgnoreCase(cgc.substring(12, 13))) &&
                            (dig14.equalsIgnoreCase(cgc.substring(13, 14))))
                        valido = true;
                    else
                        valido = false;
                }
                catch (Exception e) { valido = false; }
            }
        }

        return valido;
    }

    //	http://www.devmedia.com.br/validando-o-cpf-em-uma-aplicacao-java/22097
    private Boolean validar_cpf(String cgc)
    {
        Boolean valido = false;

        cgc = cgc.replace("/", "");
        cgc = cgc.replace("-", "");
        cgc = cgc.replace(".", "");

        if (cgc.length() == 11){
            if (cgc.equalsIgnoreCase("11111111111111") ||
                    cgc.equalsIgnoreCase("22222222222222") ||
                    cgc.equalsIgnoreCase("33333333333333") ||
                    cgc.equalsIgnoreCase("44444444444444") ||
                    cgc.equalsIgnoreCase("55555555555555") ||
                    cgc.equalsIgnoreCase("66666666666666") ||
                    cgc.equalsIgnoreCase("77777777777777") ||
                    cgc.equalsIgnoreCase("88888888888888") ||
                    cgc.equalsIgnoreCase("99999999999999") ||
                    cgc.equalsIgnoreCase("00000000000000")){
            }
            else
            {
                String dig10, dig11;
                int sm, i, r, num, peso;

                try
                {
                    //calculando primeiro digito
                    sm = 0;
                    peso = 10;

                    for (i = 0; i < 9; i++)
                    {
                        num = Integer.parseInt(cgc.substring(i,(i + 1)));
                        sm = sm + (num * peso);
                        peso-=1;
                    }

                    r = 11 - (sm % 11);
                    if ((r == 10) || (r == 11))
                        dig10 = "0";
                    else
                        dig10 = "" + r;

                    //calculando segundo digito
                    sm = 0;
                    peso = 11;

                    for (i = 0; i < 10; i++)
                    {
                        num = Integer.parseInt(cgc.substring(i, (i + 1)));
                        sm = sm + (num * peso);
                        peso-=1;
                    }
                    r = 11 - (sm % 11);
                    if ((r == 10) || (r == 11))
                        dig11 = "0";
                    else
                        dig11 = "" + r;

//					verifica se os digitos calculados sonferem com os digitos informados
                    if (dig10.equalsIgnoreCase(cgc.substring(9, 10)) &&
                            dig11.equalsIgnoreCase(cgc.substring(10, 11)))
                        valido = true;
                    else
                        valido = false;
                }
                catch (Exception e) { valido = false; }
            }
        }

        return valido;
    }


}
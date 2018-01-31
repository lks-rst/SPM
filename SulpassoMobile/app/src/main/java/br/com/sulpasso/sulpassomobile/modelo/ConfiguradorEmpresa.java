package br.com.sulpasso.sulpassomobile.modelo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lucas on 05/12/2016 - 09:08 as part of the project SulpassoMobile.
 */
public class ConfiguradorEmpresa
{
    private int codigo;
    private String nome;
    private String endereco;
    private String fone;
    private String cidade;
    private String email;
    private String site;
    private int login;

    public ConfiguradorEmpresa()
    {
        /*
        this.codigo = 1;
        this.nome = "ZDC";
        this.endereco = "Nao sei";
        this.fone = "fone";
        this.cidade = "Passo Fundo / RS";
        this.email = "email";
        this.site = "webpage";
        */
    }

    public ConfiguradorEmpresa(JSONObject empresa) throws JSONException
    {
        this.codigo = empresa.getInt("codigo");
        this.nome = empresa.getString("nome");
        this.endereco = empresa.getString("endereco");
        this.fone = empresa.getString("fone");
        this.cidade = empresa.getString("cidade");
        this.email = empresa.getString("email");
        this.site = empresa.getString("site");
        this.login = empresa.getInt("login");
    }

    public int getCodigo() { return codigo; }

    public void setCodigo(int codigo) { this.codigo = codigo; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }

    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getFone() { return fone; }

    public void setFone(String fone) { this.fone = fone; }

    public String getCidade() { return cidade; }

    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getSite() { return site; }

    public void setSite(String site) { this.site = site; }

    public int getLogin() { return login; }

    public void setLogin(int login) { this.login = login; }

    @Override
    public String toString()
    {
        return "{" +
                "\"codigo\":\"" + codigo +
                "\", \"nome\":\"" + nome + '"' +
                ", \"endereco\":\"" + endereco + '"' +
                ", \"fone\":\"" + fone + '"' +
                ", \"cidade\":\"" + cidade + '"' +
                ", \"email\":\"" + email + '"' +
                ", \"site\":\"" + site + '"' +
                ", \"login\":\"" + login + '"' +
                '}';
    }
}
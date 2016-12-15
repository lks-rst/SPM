package br.com.sulpassomobile.sulpasso.sulpassomobile.modelo;

/**
 * Created by Lucas on 05/12/2016 - 09:08 as part of the project SulpassoMobile.
 */
public class ConfiguradorEmpresa
{
    /*
        TODO: Remover as inicializações dos dados após verificação;
     */
    private int codigo;
    private String nome;
    private String endereco;
    private String fone;
    private String cidade;
    private String email;
    private String site;

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

    @Override
    public String toString() {
        return "{" +
                "\"codigo\":\"" + codigo +
                "\", \"nome\":\"" + nome + '"' +
                ", \"endereco\":\"" + endereco + '"' +
                ", \"fone\":\"" + fone + '"' +
                ", \"cidade\":\"" + cidade + '"' +
                ", \"email\":\"" + email + '"' +
                ", \"site\":\"" + site + '"' +
                '}';
    }
}
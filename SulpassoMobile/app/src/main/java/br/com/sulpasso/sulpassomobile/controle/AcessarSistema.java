package br.com.sulpasso.sulpassomobile.controle;

import br.com.sulpasso.sulpassomobile.modelo.Usuario;

/**
 * Created by Lucas on 01/08/2016.
 */
public class AcessarSistema
{
    private String user;
    private String password;
    private Usuario usuario;

    public void setUser(String user) { this.user = user; }

    public void setPassword(String password) { this.password = password; }

    public Usuario acessarSistema() { return null; }

    private Boolean verificarUsuario(String usuario){ return false; }

    private int buscarCodigoUsuario(String usuario) { return 0; }

    private Boolean verificarSenha(int usuario, String senha) { return false; }
}

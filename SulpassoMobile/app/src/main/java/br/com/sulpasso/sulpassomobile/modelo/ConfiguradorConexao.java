package br.com.sulpasso.sulpassomobile.modelo;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Lucas on 05/12/2016 - 09:09 as part of the project SulpassoMobile.
 */
public class ConfiguradorConexao
{
    private String serverFtp;
    private String serverFtp2;
    private String serverWeb;
    private String ftpUser;
    private String ftpPswd;
    private String uploadFolder;
    private String downloadFolder;
    private String emailSender;
    private String emailPswd;
    private String email1;
    private String email2;
    private String email3;
    private String empresa;
    private int conectionType;

    public ConfiguradorConexao()
    {
        /*
        this.serverFtp = "caminhoFTP";
        this.serverFtp2 = "caminho2";
        this.serverWeb = "web server";
        this.ftpUser = "usuarioFTP";
        this.ftpPswd = "senhaFtp";
        this.uploadFolder = "BAIXA";
        this.downloadFolder = "ENVIO";
        this.emailSender = "remetente";
        this.emailPswd = "SenhaEmail";
        this.email1 = "destinatario";
        this.email2 = "destinatario";
        this.email3 = "destinatario";
        this.conectionType = 0;
        */
    }

    public ConfiguradorConexao(JSONObject empresa) throws JSONException
    {
        this.serverFtp = empresa.getString("serverFtp");
        this.serverFtp2 = empresa.getString("serverFtp2");
        this.serverWeb = empresa.getString("serverWeb");
        this.ftpUser = empresa.getString("ftpUser");
        this.ftpPswd = empresa.getString("ftpPswd");
        this.uploadFolder = empresa.getString("uploadFolder");
        this.downloadFolder = empresa.getString("downloadFolder");
        this.emailSender = empresa.getString("emailSender");
        this.emailPswd = empresa.getString("emailPswd");
        this.email1 = empresa.getString("email1");
        this.email2 = empresa.getString("email2");
        this.email3 = empresa.getString("email3");
        this.conectionType = empresa.getInt("conectionType");
        this.empresa = empresa.getString("empresaCliente");
    }

    public String getServerFtp() { return serverFtp; }

    public void setServerFtp(String serverFtp) { this.serverFtp = serverFtp; }

    public String getServerFtp2() { return serverFtp2; }

    public void setServerFtp2(String serverFtp2) { this.serverFtp2 = serverFtp2; }

    public String getServerWeb() { return serverWeb; }

    public void setServerWeb(String serverWeb) { this.serverWeb = serverWeb; }

    public String getFtpUser() { return ftpUser; }

    public void setFtpUser(String ftpUser) { this.ftpUser = ftpUser; }

    public String getFtpPswd() { return ftpPswd; }

    public void setFtpPswd(String ftpPswd) { this.ftpPswd = ftpPswd; }

    public String getUploadFolder() { return uploadFolder; }

    public void setUploadFolder(String uploadFolder) { this.uploadFolder = uploadFolder; }

    public String getDownloadFolder() { return downloadFolder; }

    public void setDownloadFolder(String downloadFolder) { this.downloadFolder = downloadFolder; }

    public String getEmailSender() { return emailSender; }

    public void setEmailSender(String emailSender) { this.emailSender = emailSender; }

    public String getEmailPswd() { return emailPswd; }

    public void setEmailPswd(String emailPswd) { this.emailPswd = emailPswd; }

    public String getEmail1() { return email1; }

    public void setEmail1(String email1) { this.email1 = email1; }

    public String getEmail2() { return email2; }

    public void setEmail2(String email2) { this.email2 = email2; }

    public String getEmail3() { return email3; }

    public void setEmail3(String email3) { this.email3 = email3; }

    public String getEmpresa() { return empresa; }

    public void setEmpresa(String empresa) { this.empresa = empresa; }

    public int getConectionType() { return conectionType; }

    public void setConectionType(int conectionType) { this.conectionType = conectionType; }

    @Override
    public String toString() {
        return "{" +
                "\"serverFtp\":\"" + serverFtp + '"' +
                ", \"serverFtp2\":\"" + serverFtp2 + '"' +
                ", \"serverWeb\":\"" + serverWeb + '"' +
                ", \"ftpUser\":\"" + ftpUser + '"' +
                ", \"ftpPswd\":\"" + ftpPswd + '"' +
                ", \"uploadFolder\":\"" + uploadFolder + '"' +
                ", \"downloadFolder\":\"" + downloadFolder + '"' +
                ", \"emailSender\":\"" + emailSender + '"' +
                ", \"emailPswd\":\"" + emailPswd + '"' +
                ", \"email1\":\"" + email1 + '"' +
                ", \"email2\":\"" + email2 + '"' +
                ", \"email3\":\"" + email3 + '"' +
                ", \"empresa\":\"" + empresa + '"' +
                ", \"conectionType\":\"" + conectionType +
                "\"}";
    }
}
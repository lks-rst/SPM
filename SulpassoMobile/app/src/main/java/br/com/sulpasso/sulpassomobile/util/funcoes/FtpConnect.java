package br.com.sulpasso.sulpassomobile.util.funcoes;

import android.os.Build;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.modelo.ConfiguradorConexao;

/**
 * Created by Lucas on 06/01/2017 - 08:47 as part of the project SulpassoMobile.
 */
public class FtpConnect
{
    private FTPClient cliente;
    private ConfiguradorConexao server;

    public FtpConnect(ConfiguradorConexao server) { this.server = server; }

    /**
     *
     * Faz a conexão e login no servidor FTP
     *
     * Site de referencia para os metodos que trabalham com FTP
     * http://escoladeandroid.blogspot.com.br/2012/06/efetuando-download-via-ftp.html
     *
     */
    public Boolean Conectar()
    {
        boolean status = false;
        try
        {
            cliente = new FTPClient();

            // conectando no host
            cliente.connect(this.server.getServerFtp(), 21);

            //verifica se a conexão está ok
            if (FTPReply.isPositiveCompletion(cliente.getReplyCode()))
            {
                //efetua login
                status = cliente.login(this.server.getFtpUser(), this.server.getFtpPswd());

                cliente.setFileType(FTP.BINARY_FILE_TYPE);
                cliente.enterLocalPassiveMode();
            }
        }
        catch(Exception e)
        {
            Log.i("TAG", "Erro: não foi possível conectar ao host " + this.server.getServerFtp());
            e.printStackTrace();
            try
            {
                cliente = new FTPClient();
                cliente.connect(this.server.getServerFtp2(), 21);

                if (FTPReply.isPositiveCompletion(cliente.getReplyCode()))
                {
                    status = cliente.login(this.server.getFtpUser(), this.server.getFtpPswd());

                    cliente.setFileType(FTP.BINARY_FILE_TYPE);
                    cliente.enterLocalPassiveMode();
                }
            }
            catch(Exception e2)
            {
                Log.i("TAG", "Erro: não foi possível conectar ao host " + this.server.getServerFtp2());
                e2.printStackTrace();
                status = false;
            }
        }
        return status;
    }

    /**
     * Finaliza a conexão ftp
     *
     * @return true se tudo ocorrer corretamente ao finalizar a conexão
     * @return false se ocorrer algum erro ao finalizar a conexão
     */
    public Boolean Desconectar()
    {
        try
        {
            cliente.logout();
            cliente.disconnect();

        }
        catch (Exception e) { return false; }

        return true;
    }

    /**
     * Verifica se existem os arquivos para serem baixados
     *
     * @param nome_arquivo -- o nome do arquivo que se esta buscando
     * @return true se conseguir listar e encontrar o arquivo procurado
     * @return false se não conseguir listar ou encontrar não o arquivo procurado
     */
    public Boolean BuscarArquivos(String nome_arquivo)
    {
        Boolean encontrou_arquivo = false;

        try
        {
            String[] nomes_arquivos = cliente.listNames();
            FTPFile[] arquivo_ftp = cliente.listFiles();

            System.out.println("TIMESTAMP RETORNADO PELO ARQUIVO NO FTP -- " + arquivo_ftp[1].getTimestamp());
            System.out.println("TIMESTAMP RETORNADO PELO ARQUIVO NO FTP -- " + arquivo_ftp[1].getTimestamp());
            System.out.println("TIMESTAMP RETORNADO PELO ARQUIVO NO FTP -- " + arquivo_ftp[1].getTimestamp());
            System.out.println("TIMESTAMP RETORNADO PELO ARQUIVO NO FTP -- " + arquivo_ftp[1].getTimestamp());

            for (String nome : nomes_arquivos)
            {
                System.out.println(nome);
                if ( nome.equals(nome_arquivo))
                {
                    encontrou_arquivo = true;
                    System.out.println("Encontrou arquivo " + nome + " == " + nome_arquivo);
                }
            }

        }
        catch (Exception e) { encontrou_arquivo = false; }

        return encontrou_arquivo;
    }

    /**
     * Baixa o arquivo do diretório remoto
     *
     * @param arquivo_origem -- o nome do arquivo que deverá ser baixado
     * @param arquivo_destino      -- o nome do arquivo que sera armazenado
     * @return arquivo -- true se o arquivo foi baixado corretamente false se ocorreu algum erro
     */
    public Boolean BaixarSd(String arquivo_origem, String arquivo_destino)
    {
        try
        {
            File f;

            int version;

            try { version = Integer.valueOf(Build.VERSION.SDK); }
            catch (Exception ev){ version = 3; }

            if(version >= 19)
            {
                f = new File("/storage/emulated/0/MobileVenda/" + arquivo_origem);
            }
            else
            {
                f = new File(Environment.getExternalStorageDirectory() + "/MobileVenda/" + arquivo_origem);
            }
//        arquivo = getExternalFilesDir("MobileVenda");


            FileOutputStream destino = new FileOutputStream(f, false);
            cliente.setFileType(FTP.BINARY_FILE_TYPE);
            cliente.enterLocalPassiveMode();

            Boolean status = cliente.retrieveFile(arquivo_origem, destino);

            destino.flush();
            destino.close();
            return status;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Faz o upload do arquivo local para o diretporio remoto
     *
     * @param arquivo_origem   -- o nome do arquivo local
     * @param arquivo_destino  -- arquivo a ser armazenado no diretório remoto
     * @return true se conseguir envar o arquivo
     * @return false se não conseguir mandar o arquivo
     */
    public Boolean Mandar(String arquivo_origem, String arquivo_destino)
    {
        try
        {
            File arquivo = new File(Environment.getExternalStorageDirectory() + "/MobileVenda/" + arquivo_origem);

            if (arquivo.exists())
            {
                System.out.println("EXISTE O ARQUIVO PARA ENVIAR");
                FileInputStream origem = new FileInputStream(arquivo);
                cliente.setFileTransferMode(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
                cliente.setFileType(org.apache.commons.net.ftp.FTPClient.BINARY_FILE_TYPE);
                Boolean status = cliente.storeFile(arquivo_destino, origem);
                origem.close();
                return status;
            }
            else
            {
                System.out.println("NAO EXISTE O ARQUIVO PARA ENVIAR");
                return false;
            }
        }
        catch (Exception e)
        {
            System.out.println("Erro ao enviar arquivo");
            System.out.println(e.getMessage());
            System.out.println("Erro ao enviar arquivo");
            System.out.println(e.getLocalizedMessage());
            System.out.println("Erro ao enviar arquivo");
            e.printStackTrace();
            return false;
        }
    }

    public Boolean ApagarArquivo(String arquivo)
    {
        try { return cliente.deleteFile(arquivo); }
        catch (Exception e) { return false; }
    }

    public Boolean BuscarDiretorio(String diretorio)
    {
        try
        {
            FTPFile[] diretoios = cliente.listDirectories();

            for (int i = 0; i < diretoios.length; i++)
            {
                if ( diretoios[i].getName().equals(diretorio))
                    return true;
            }

            return false;
        }
        catch (Exception e) { return false; }
    }

    public Boolean MudarDiretorio(String diretorio)
    {
        try
        {
            cliente.changeWorkingDirectory(diretorio);
            return true;
        }
        catch (Exception e) { return false; }
    }

    public ArrayList<String> ListarArquivos(int nr_seq, int cod_vendedor)
    {
        ArrayList<String> arq_pw = new ArrayList<String>();
        int cod_nome = 0;
        int seq_nome = 0;

        try
        {
            String[] nomes_arquivos = cliente.listNames();

            for (String nome : nomes_arquivos)
            {
                System.out.println(nome);

                if (nome.length() > 3)
                {
                    cod_nome = (Integer.parseInt(nome.substring(2, nome.length() - 4)));
                    seq_nome = (Integer.parseInt(nome.substring(nome.length() - 3)));

                    if (nome.substring(0, 2).equalsIgnoreCase("pw")  &&
                            (cod_nome == cod_vendedor) && (seq_nome >= (nr_seq + 1)))
                    {
                        arq_pw.add(nome);
//						break;
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("ERRO");
            e.printStackTrace();
        }
        return arq_pw;
    }

    public Boolean ListarArquivos(String nome_arq)
    {
        Boolean arq_transferido = false;
        try
        {
            String[] nomes_arquivos = cliente.listNames();

            for (String nome : nomes_arquivos)
            {
                System.out.println(nome);
                if (nome.equalsIgnoreCase(nome_arq))
                {
                    arq_transferido = true;
                    break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("ERRO");
            e.printStackTrace();
            arq_transferido = false;
        }
        return arq_transferido;
    }

    public String ListarDiretorio()
    {
        try { return  "DIRETORIO ATUAL = " + cliente.printWorkingDirectory(); }
        catch (Exception e)
        {
            e.printStackTrace();
            return "ERRO";
        }
    }

    public Boolean baixarConfiguracao(String empresa, int usuario)
    {
        if(this.MudarDiretorio("CLIENTES"))
        {
            if(this.MudarDiretorio(empresa.toUpperCase()))
            {
                if(this.MudarDiretorio(String.valueOf(usuario)))
                {
                    if(this.BaixarSd("configurador.json", "")) { return true; }
                    else { return false; }
                }
                else { return false; }
            }
            else { return false; }
        }
        else { return false; }
    }
}
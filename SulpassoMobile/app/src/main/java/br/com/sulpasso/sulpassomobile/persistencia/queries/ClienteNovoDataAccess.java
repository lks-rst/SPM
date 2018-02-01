package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Atividade;
import br.com.sulpasso.sulpassomobile.modelo.Banco;
import br.com.sulpasso.sulpassomobile.modelo.Cidade;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.ClienteNovo;
import br.com.sulpasso.sulpassomobile.modelo.Natureza;
import br.com.sulpasso.sulpassomobile.modelo.Tipologia;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;
import br.com.sulpasso.sulpassomobile.persistencia.tabelas.Configurador;
import br.com.sulpasso.sulpassomobile.util.Enumarations.TiposBuscaCliente;

/**
 * Created by Lucas on 22/11/2017 - 09:56 as part of the project SulpassoMobile.
 */
public class ClienteNovoDataAccess {
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private String searchData;

    public ClienteNovoDataAccess(Context context) {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public void setSearchType(int searchType) {
        this.searchType = searchType;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    public ArrayList<Cliente> getAll() throws GenercicException {
        return this.searchAll();
    }

    public Cliente buscarCliente(int id) throws ReadExeption {
        return this.search(id);
    }

    public Boolean inserir(String data) throws GenercicException {
        return this.inserir(this.dataConverter(data));
    }

    public Boolean inserir(Cliente cliente) throws GenercicException {
        return this.insertCliente(cliente);
    }

    public Boolean delete() throws GenercicException {
        return this.apagar(null);
    }

    public int buscarBanco(int codigoCliente, int t) {
        if (t == 0) return this.buscarBancoCliente(codigoCliente);
        else return this.buscarBancoNatureza(this.buscarNaturezaCliente(codigoCliente));
    }

    private Boolean insertCliente(Cliente cliente) throws InsertionExeption {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("Insert or replace into ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
        this.sBuilder.append("(");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CGC);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.IE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.NATUREZA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.PRAZO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TAB);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FONE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CELULAR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.EMAIL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ENDERECO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.BAIRRO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.MENSAGEM);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CEP);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ROTEIRO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ALTERA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ESPECIAL);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.SITUACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ATIVIDADE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.BANCO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.VISITA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.VENDIDO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.MEDIA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.INDUSTRIALIZADOS);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.REALIZADO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ULTIMA);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TIPO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CPF);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.NUMERO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.SALDOCREDITO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.LIMITE);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.METAPESO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CONSUMIDOR);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CONTATO);
        this.sBuilder.append(", ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.SALDO);
        this.sBuilder.append(") VALUES ('");
        this.sBuilder.append(cliente.getCodigoCliente());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getRazao());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getFantasia());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getCgc());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getIe());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getNatureza());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getPrazo());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getTabela());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getTelefone());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getCelular());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getEmail());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getEndereco());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getBairro());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getCodigoCidade());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getMensagem());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getCep());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getRoteiro());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getAlteraPrazo());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getEspecial());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getSituacao());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getAtividade());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getBanco());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getVisita());
        this.sBuilder.append("', '");
        this.sBuilder.append(0);
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getMediaCompras());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getMediaIndustrializados());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getRealizado());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getDataUltimaCompra());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getTipo());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getCpf());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getNumero());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getSaldo());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getLimiteCredito());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getMetaPeso());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getConsumidor());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getContato());
        this.sBuilder.append("', '");
        this.sBuilder.append(cliente.getSaldo());

        this.sBuilder.append("');");

        try {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        } catch (Exception e) {
            throw new InsertionExeption(e.getMessage());
        }
    }

    private ArrayList searchByData(int type, String data) throws ReadExeption {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
        this.sBuilder.append(" WHERE ");

        TiposBuscaCliente tb = TiposBuscaCliente.getTipoFromInt(this.searchType);
        switch (tb) {
            case RAZAO:
                this.sBuilder.append(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO);
                break;
            default:
                this.sBuilder.append(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA);
                break;
        }

        this.sBuilder.append(" LIKE( '");
        this.sBuilder.append(data);
        this.sBuilder.append("');");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Cliente cliente = new Cliente();

            cliente.setCodigoCliente(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO)));
            cliente.setRazao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));

            lista.add(cliente);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchByData(int type, int data) throws ReadExeption {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
        this.sBuilder.append(" WHERE ");

        TiposBuscaCliente tb = TiposBuscaCliente.getTipoFromInt(this.searchType);
        switch (tb) {
            case CIDADE:
                this.sBuilder.append(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CIDADE);
                break;
            default:
                this.sBuilder.append(
                        br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CIDADE);
                break;
        }

        this.sBuilder.append(" = ");
        this.sBuilder.append(data);
        this.sBuilder.append(");");


        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Cliente cliente = new Cliente();

            cliente.setCodigoCliente(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO)));
            cliente.setRazao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));

            lista.add(cliente);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchAll() throws ReadExeption {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
        this.sBuilder.append(" ORDER BY ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Cliente cliente = new Cliente();

            cliente.setCodigoCliente(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO)));
            cliente.setRazao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));
            cliente.setFantasia(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FANTASIA)));
            cliente.setCgc(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CGC)));
            cliente.setIe(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.IE)));
            cliente.setNatureza(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.NATUREZA)));
            cliente.setPrazo(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.PRAZO)));
            cliente.setTabela(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TAB)));
            cliente.setTelefone(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.FONE)));
            cliente.setCelular(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CELULAR)));
            cliente.setEmail(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.EMAIL)));
            cliente.setEndereco(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ENDERECO)));
            cliente.setBairro(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.BAIRRO)));
            cliente.setCodigoCidade(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CIDADE)));
            cliente.setMensagem(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.MENSAGEM)));
            cliente.setRoteiro(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ROTEIRO)));
            cliente.setCep(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CEP)));
            cliente.setAlteraPrazo(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ALTERA)).charAt(0));
            cliente.setEspecial(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ESPECIAL)).charAt(0));
            cliente.setSituacao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.SITUACAO)).charAt(0));
            cliente.setAtividade(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ATIVIDADE)));
            cliente.setBanco(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.BANCO)));
            cliente.setVisita(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.VISITA)));

            cliente.setMetaPeso(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.METAPESO)));
            cliente.setMediaCompras(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.MEDIA)));
            cliente.setRealizado(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.REALIZADO)));
            cliente.setLimiteCredito(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.LIMITE)));
            cliente.setDataUltimaCompra(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ULTIMA)));

            lista.add(cliente);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchSchedule() throws ReadExeption {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Cliente cliente = new Cliente();

            cliente.setCodigoCliente(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO)));
            cliente.setRazao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));

            lista.add(cliente);
            c.moveToNext();
        }

        return lista;
    }

    private ArrayList searchVisitDay() throws ReadExeption {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Cliente cliente = new Cliente();

            cliente.setCodigoCliente(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO)));
            cliente.setRazao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));

            lista.add(cliente);
            c.moveToNext();
        }

        return lista;
    }

    private int sarchConverte() {
        try {
            return Integer.parseInt(this.searchData);
        } catch (Exception e) {
            return 1;
        }
    }

    private Cliente dataConverter(String cliente) {
        Cliente c = new Cliente();

        int natureza;
        int prazo;

        try {
            natureza = Integer.parseInt(cliente.substring(188, 191).trim());
            prazo = Integer.parseInt(cliente.substring(191, 193).trim());
        } catch (Exception e) {
            natureza = 0;
            prazo = 0;
        }

        String tipoPessoa = "";
        String cpf = "";
        String consumidor = "";
        String numeroEstabelecimento = "";

        try {
            tipoPessoa = cliente.substring(447, 448).trim();
            cpf = cliente.substring(448, 459).trim();
            consumidor = cliente.substring(459, 460).trim();
            numeroEstabelecimento = cliente.substring(460, 466).trim();
        } catch (Exception e) {
            tipoPessoa = "";
            cpf = "";
            consumidor = "";
            numeroEstabelecimento = "";
        }

        c.setCodigoCliente(Integer.parseInt(cliente.substring(2, 9)));
        c.setRazao(cliente.substring(9, 54));
        c.setFantasia(cliente.substring(54, 84));
        c.setRoteiro(Integer.parseInt(cliente.substring(84, 88)));
        c.setContato(cliente.substring(88, 113));
        c.setEndereco(cliente.substring(123, 168));
        c.setBairro(cliente.substring(168, 188));
        c.setNatureza(natureza);
        c.setPrazo(prazo);
        c.setSituacao(cliente.substring(193, 194).charAt(0));
        c.setEspecial(cliente.substring(194, 195).charAt(0));
        c.setCodigoCidade(Integer.parseInt(cliente.substring(195, 199)));
        c.setTelefone(cliente.substring(199, 214));
        c.setCelular(cliente.substring(214, 229));
        c.setTabela(Integer.parseInt(cliente.substring(229, 231)));
        c.setCgc(cliente.substring(231, 245));
        c.setBanco(Integer.parseInt(cliente.substring(245, 248)));
        c.setTipo(cliente.substring(248, 252));
        c.setVisita(Integer.parseInt(cliente.substring(289, 292)));
        c.setMediaCompras(Float.parseFloat(cliente.substring(312, 320)));
        c.setMediaIndustrializados(Integer.parseInt(cliente.substring(320, 322)));
        c.setMetaPeso(Float.parseFloat(cliente.substring(322, 330)) / 100);
        c.setRealizado(Float.parseFloat(cliente.substring(330, 338)) / 100);
        c.setSaldo(Float.parseFloat(cliente.substring(338, 346)) / 100);
        c.setDataUltimaCompra(cliente.substring(346, 356));
        c.setLimiteCredito(Float.parseFloat(cliente.substring(356, 364)) / 100);
        c.setSaldoCredito(Float.parseFloat(cliente.substring(364, 372)) / 100);
        c.setAlteraPrazo(cliente.substring(372, 373).charAt(0));
        c.setIe(cliente.substring(376, 385));
        c.setCep(cliente.substring(385, 394));
        c.setEmail(cliente.substring(394, 444));
        c.setCpf(cpf);
        c.setConsumidor(consumidor);
        c.setNumero(numeroEstabelecimento);
        c.setMensagem(cliente.substring(466));

        return c;
    }

    private Cliente search(int id) throws ReadExeption {
        Cliente cliente = new Cliente();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(id);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            cliente.setCodigoCliente(
                    c.getInt(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO)));
            cliente.setRazao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));
            cliente.setAlteraPrazo(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ALTERA))
                            .charAt(0));
            cliente.setEspecial(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ESPECIAL))
                            .charAt(0));
            cliente.setSituacao(
                    c.getString(c.getColumnIndex(
                            br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.SITUACAO))
                            .charAt(0));

            c.moveToNext();
        }

        return cliente;
    }

    private Boolean apagar(Cliente d) throws GenercicException {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("DELETE FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);

        if (d != null) {
            this.sBuilder.append(" WHERE ");
            this.sBuilder.append(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);
            this.sBuilder.append(" = '");
            d.getCodigoCliente();
            this.sBuilder.append("'");
        }

        this.sBuilder.append(";");

        try {
            this.db.execSQL(this.sBuilder.toString());
            return true;
        } catch (Exception e) {
            throw new InsertionExeption(e.getMessage());
        }
    }

    private int buscarBancoCliente(int cliente) {
        int banco = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.BANCO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(cliente);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            banco = c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.BANCO));
        }

        return banco;
    }

    private int buscarBancoNatureza(int natureza) {
        int banco = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.BANCO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            banco = c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.BANCO));
        }

        return banco;
    }

    private int buscarNaturezaCliente(int cliente) {
        int natureza = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.NATUREZA);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            natureza = c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.NATUREZA));
        }

        return natureza;
    }


    public void salvarCliente(ClienteNovo cliente) {
        ContentValues valores = new ContentValues();
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.NOME, cliente.getNome());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.FANTASIA, cliente.getFantazia());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.UF, cliente.getUf());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.END, cliente.getEndereco());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.BAIRRO, cliente.getBairro());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CID_COD, cliente.getCidade());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.FONE, cliente.getFone());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CEL, cliente.getCell());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CGC, cliente.getCgc());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.IE, cliente.getIe());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CONTATO, cliente.getContato());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.MAIL, cliente.getEmail());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ANIVER, cliente.getAniversario());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CEP, cliente.getCep());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.T_PESSOA, cliente.getT_pessoa());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.LEGAL, cliente.getRep());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.RG, cliente.getIdent());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ZONA, cliente.getZona());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.VISITA, cliente.getVisita());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CPF, cliente.getCpf());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.TIPO, cliente.getTipologia());

        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.BANCO, cliente.getBanco());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.PGTO, cliente.getPagto());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.POTENCIAL, cliente.getPotencial());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.OBS, cliente.getObs());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.NUM, cliente.getNr_res());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ATIV, cliente.getAtividade());
//		valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.get, cliente.getCli_);
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.AREA, cliente.getArea());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL1, cliente.getComercial1());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL1_FONE, cliente.getComercial1_fone());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL1, cliente.getComercial2());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL1_FONE, cliente.getComercial2_fone());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL1, cliente.getComercial3());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL1_FONE, cliente.getComercial3_fone());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL_ABERTURA, cliente.getComercial_abertura());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.DATA, cliente.getData());
        valores.put(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ENVIO, 0);


        try {
            db.insert(br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.TABELA,
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COD, valores);
        } catch (Exception e) { /*****/}
    }

    public boolean cadastro_obrigatorio() {
        Cliente cliente = new Cliente();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(Configurador.CADASTROCLIENTE);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        try {
            return c.getInt(0) == 1 ? true : false;
        } catch (Exception e) {
            return true;
        }
    }

    public ArrayList<String> listar_estados() {
        ArrayList<String> ufs = new ArrayList<String>();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT DISTINCT  ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.UF);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            ufs.add(c.getString(0));
            c.moveToNext();
        }

        return ufs;
    }

    public ArrayList<Atividade> listar_atividades() {
        ArrayList<Atividade> atividades = new ArrayList<Atividade>();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Atividade.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            Atividade atividade = new Atividade();

            atividade.setCodigo(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Atividade.CODIGO)));
            atividade.setDescricao(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Atividade.ATIVIDADE)));

            atividades.add(atividade);

            c.moveToNext();
        }

        return atividades;
    }

    public ArrayList<Banco> listar_bancos() {
        ArrayList<Banco> bancos = new ArrayList<Banco>();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Banco.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            Banco banco = new Banco();

            banco.setCodigo(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Banco.CODIGO)));
            banco.setBanco(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Banco.BANCO)));

            bancos.add(banco);

            c.moveToNext();
        }

        return bancos;
    }

    public ArrayList<Cidade> listar_cidades(String s) {
        ArrayList<Cidade> cidades = new ArrayList<Cidade>();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.UF);
        this.sBuilder.append(" LIKE '");
        this.sBuilder.append(s);
        this.sBuilder.append("' ORDER BY ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CIDADE);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            Cidade cid = new Cidade();

            cid.setCodigo(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CODIGO)));
            cid.setUf(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.UF)));
            cid.setCep(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CEP)));
            cid.setNome(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cidade.CIDADE)));

            cidades.add(cid);

            c.moveToNext();
        }

        return cidades;
    }

    public ArrayList<Natureza> listar_naturezas() {
        ArrayList<Natureza> naturezas = new ArrayList<Natureza>();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            Natureza nat = new Natureza();

            nat.setCodigo(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.CODIGO)));
            nat.setDescricao(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Natureza.DESCRICAO)));

            naturezas.add(nat);

            c.moveToNext();
        }

        return naturezas;
    }

    public ArrayList<Tipologia> listar_tipologias() {
        ArrayList<Tipologia> tipologias = new ArrayList<Tipologia>();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Tipologia.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            Tipologia tipo = new Tipologia();

            tipo.setTipologia(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Tipologia.CODIGO)));
            tipo.setDescricao(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Tipologia.DESCRICAO)));

            tipologias.add(tipo);

            c.moveToNext();
        }

        return tipologias;
    }

    public ArrayList<Cliente> listar_clientes() {
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO);
        this.sBuilder.append(", ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.SITUACAO);
        this.sBuilder.append(", ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ROTEIRO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            Cliente cliente = new Cliente();

            cliente.setCodigoCliente(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.CODIGO)));
            cliente.setRazao(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.RAZAO)));
            cliente.setSituacao(c.getString(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.SITUACAO)).charAt(0));
            cliente.setRoteiro(c.getInt(c.getColumnIndex(br.com.sulpasso.sulpassomobile.persistencia.tabelas.Cliente.ROTEIRO)));

            clientes.add(cliente);

            c.moveToNext();
        }

        return clientes;
    }

    public int getEmpreza()
    {
        int valor = 0;

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT ");
        this.sBuilder.append(Configurador.CODIGO);
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(Configurador.TABELA);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();

        try {
            valor = c.getInt(0);
        } catch (Exception e) {
            valor = 0;
        }

        return valor;
    }

    public ArrayList<ClienteNovo> getByData() throws GenercicException
    {
        ArrayList lista = new ArrayList();

        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT * FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.TABELA);
        this.sBuilder.append(" WHERE ");

        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ENVIO);

        this.sBuilder.append(" = '");
        this.sBuilder.append(this.searchType);
        this.sBuilder.append("';");

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            ClienteNovo cliente = new ClienteNovo();

            cliente.setNome(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.NOME)));
            cliente.setFantazia(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.FANTASIA)));
            cliente.setEndereco(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.END)));
            cliente.setUf(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.UF)));
            cliente.setBairro(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.BAIRRO)));
            cliente.setCidade(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CID_COD)));
            cliente.setFone(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.FONE)));
            cliente.setCell(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CEL)));
            cliente.setCgc(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CGC)));
            cliente.setIe(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.IE)));
            cliente.setContato(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CONTATO)));
            cliente.setEmail(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.MAIL)));
            cliente.setAniversario(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ANIVER)));
            cliente.setCep(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CEP)));
            cliente.setT_pessoa(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.T_PESSOA)));
            cliente.setRep(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.LEGAL)));
            cliente.setIdent(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.RG)));

            cliente.setZona(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ZONA)));
            cliente.setVisita(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.VISITA)));
            cliente.setCpf(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.CPF)));
            cliente.setTipologia(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.TIPO)));

            cliente.setPagto(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.PGTO)));
            cliente.setBanco(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.BANCO)));

            cliente.setPotencial(c.getFloat(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.POTENCIAL)));

            cliente.setObs(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.OBS)));
            cliente.setNr_res(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.NUM)));
            cliente.setAtividade(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ATIV)));

            cliente.setArea(c.getInt(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.AREA)));

            cliente.setComercial1(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL1)));
            cliente.setComercial1_fone(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL1_FONE)));
            cliente.setComercial2(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL2)));
            cliente.setComercial2_fone(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL2_FONE)));
            cliente.setComercial3(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL3)));
            cliente.setComercial3_fone(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL3_FONE)));
            cliente.setComercial_abertura(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.COMERCIAL_ABERTURA)));
            cliente.setData(c.getString(c.getColumnIndex(
                    br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.DATA)));

            lista.add(cliente);
            c.moveToNext();
        }

        return lista;
    }

    public int totalClientesAbertos()
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("SELECT COUNT()");
        this.sBuilder.append(" FROM ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.TABELA);
        this.sBuilder.append(" WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ENVIO);
        this.sBuilder.append(" = ");
        this.sBuilder.append(0);

        Cursor c = this.db.rawQuery(this.sBuilder.toString(), null);
        c.moveToFirst();

        try { return c.getInt(0); }
        catch (Exception exception) { return 1; }
    }

    public void atualizarClientes() throws GenercicException
    {
        this.sBuilder.delete(0, this.sBuilder.length());
        this.sBuilder.append("UPDATE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.TABELA);
        this.sBuilder.append(" SET ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ENVIO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(1);
        this.sBuilder.append("' WHERE ");
        this.sBuilder.append(
                br.com.sulpasso.sulpassomobile.persistencia.tabelas.ClienteNovo.ENVIO);
        this.sBuilder.append(" = '");
        this.sBuilder.append(0);
        this.sBuilder.append("';");

        try { db.execSQL(this.sBuilder.toString()); }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
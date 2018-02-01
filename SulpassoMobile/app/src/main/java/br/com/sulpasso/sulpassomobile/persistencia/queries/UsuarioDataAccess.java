package br.com.sulpasso.sulpassomobile.persistencia.queries;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.sulpasso.sulpassomobile.exeption.GenercicException;
import br.com.sulpasso.sulpassomobile.exeption.InsertionExeption;
import br.com.sulpasso.sulpassomobile.exeption.ReadExeption;
import br.com.sulpasso.sulpassomobile.modelo.Cliente;
import br.com.sulpasso.sulpassomobile.modelo.Usuario;
import br.com.sulpasso.sulpassomobile.persistencia.database.SimplySalePersistencySingleton;

/**
 * Created by Lucas on 15/08/2016.
 */
public class UsuarioDataAccess
{
    private Context ctx;
    private StringBuilder sBuilder;
    private SQLiteDatabase db;
    private int searchType;
    private int searchData;

    public UsuarioDataAccess(Context context)
    {
        this.ctx = context;
        this.sBuilder = new StringBuilder();
        this.db = SimplySalePersistencySingleton.getDb(context);
    }

    public void setSearchType(int searchType) { this.searchType = searchType; }

    public void setSearchData(int searchData) { this.searchData = searchData; }

    public List getAll() throws GenercicException
    {
        return new ArrayList();
    }

    public List getByData() throws GenercicException
    {
        return new ArrayList();
    }

    public Boolean insert(String data) throws GenercicException
    {
        return false;
    }

    public Boolean insert(Usuario usuario) throws GenercicException
    {
        return true;
    }

    private Cliente dataConverter(String usuario)
    {
        return new Cliente();
    }

    private Boolean inserirCliente(Usuario usuario) throws InsertionExeption
    {
        return false;
    }

    private List getByData(String data, int type) throws ReadExeption
    {
        return new ArrayList();
    }
}
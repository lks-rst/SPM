package br.com.sulpasso.sulpassomobile.persistencia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Lucas on 02/08/2016.
 */
public class SimplySaleDataBaseCreate
{
    private SQLiteDatabase database;
    private br.com.sulpasso.sulpassomobile.persistencia.database.SimplySaleDataBase sqlHelperDb;

    /**
     * Create the squema and populate the tables
     * @param context
     */
    public SimplySaleDataBaseCreate(Context context)
    {
        sqlHelperDb = new br.com.sulpasso.sulpassomobile.persistencia.database.SimplySaleDataBase(context);
    }

    /**
     * Open the data base on read and write state.
     * @return the data base previuslycreated
     */
    public SQLiteDatabase getDatabase()
    {
        database = sqlHelperDb.getWritableDatabase();
        return database;
    }

    /**
     * Fecha conexão.
     */
    public void closed()
    {
        if (sqlHelperDb != null)
        {
            sqlHelperDb.close();
            database.close();
        }
    }
}

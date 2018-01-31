package br.com.sulpasso.sulpassomobile.persistencia.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.telecom.Connection;

/**
 * Created by Lucas on 15/08/2016.
 */
public class SimplySalePersistencySingleton
{
    private static SimplySalePersistencySingleton instance;
    private static SQLiteDatabase db;
    private static Connection conn;

    private SimplySalePersistencySingleton() { }

    private static SimplySalePersistencySingleton getInstance(Context context)
    {
        if(instance == null)
        {
            synchronized (SimplySalePersistencySingleton.class)
            {
                instance = new SimplySalePersistencySingleton();

                br.com.sulpasso.sulpassomobile.persistencia.database.SimplySaleDataBaseCreate dbCreator = new br.com.sulpasso.sulpassomobile.persistencia.database.SimplySaleDataBaseCreate(context);
                db = dbCreator.getDatabase();
            }
        }
        return instance;
    }

    public static SQLiteDatabase getDb(Context contexto)
    {
        if(instance == null) { SimplySalePersistencySingleton.getInstance(contexto); }
        return db;
    }
}

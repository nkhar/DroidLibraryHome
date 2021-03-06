package com.vulgivagus.androidapp.learningfragmentsapex;

/**
 * Created by Nika on 11.02.2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.vulgivagus.androidapp.learningfragmentsapex.data.Author;
import com.vulgivagus.androidapp.learningfragmentsapex.data.Book;
import com.vulgivagus.androidapp.learningfragmentsapex.data.SimpleData;

import java.sql.SQLException;


/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "DroidLibrary.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 2;

    // the DAO object we use to access the SimpleData table
    private Dao<SimpleData, Integer> simpleDao = null;
    private Dao<Author, Integer> authorDao;
    private Dao<Book, Integer> bookDao;
   /*
    private RuntimeExceptionDao<SimpleData, Integer> simpleRuntimeDao = null;
     */

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, SimpleData.class);
            TableUtils.createTable(connectionSource, Author.class);
            TableUtils.createTable(connectionSource, Book.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            // throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
        // RuntimeExceptionDao<SimpleData, Integer> dao = getSimpleDataDao();
        //  long millis = System.currentTimeMillis();
        // create some entries in the onCreate
        // SimpleData simple = new SimpleData(millis);
        // dao.create(simple);
        // simple = new SimpleData(millis + 1);
        //   dao.create(simple);
        //  Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate: " + millis);
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, SimpleData.class, true);
            TableUtils.dropTable(connectionSource, Author.class, true);
            TableUtils.dropTable(connectionSource, Book.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            // throw new RuntimeException(e);
        }
    }

    // Create the getDao methods of all database tables to access those from android code.
    // Insert, delete, read, update everything will be happened through DAOs

    public Dao<SimpleData, Integer> getSimpleDataDao() throws SQLException {
        if (simpleDao == null) {
            simpleDao = getDao(SimpleData.class);
        }
        return simpleDao;
    }

    public Dao<Author, Integer> getAuthorDao() throws SQLException {
        if (authorDao == null) {
            authorDao = getDao(Author.class);
        }
        return authorDao;
    }

    public Dao<Book, Integer> getBookDao() throws SQLException {
        if (bookDao == null) {
            bookDao = getDao(Book.class);
        }
        return bookDao;
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    /** public Dao<SimpleData, Integer> getDao() throws SQLException {
     if (simpleDao == null) {
     simpleDao = getDao(SimpleData.class);
     }
     return simpleDao;
     } /

     /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    /**  public RuntimeExceptionDao<SimpleData, Integer> getSimpleDataDao() {
     if (simpleRuntimeDao == null) {
     simpleRuntimeDao = getRuntimeExceptionDao(SimpleData.class);
     }
     return simpleRuntimeDao;
     } */

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        simpleDao = null;
        authorDao = null;
        bookDao = null;
        // simpleRuntimeDao = null;
    }
}
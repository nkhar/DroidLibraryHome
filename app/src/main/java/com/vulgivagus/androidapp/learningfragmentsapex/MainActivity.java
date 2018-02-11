package com.vulgivagus.androidapp.learningfragmentsapex;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.vulgivagus.androidapp.learningfragmentsapex.data.Author;
import com.vulgivagus.androidapp.learningfragmentsapex.data.SimpleData;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView mTextView;

    private final String LOG_TAG = "MainActivity";
    private final static int MAX_NUM_TO_CREATE = 8;

    // Reference of DatabaseHelper class to access its DAOs and other components
    private DatabaseHelper databaseHelper = null;

    // Declaration of DAO to interact with corresponding table
    private Dao<SimpleData, Integer> simpleDao;

    List<SimpleData> list = null;

    // Declaration of DAO to interact with corresponding Author table
    private Dao<Author, Integer> authorDao;
    List<Author> authorList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mTextView = findViewById(R.id.contentId);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_authors) {
            mTextView.setText("Authors");
            doSampleDatabaseStuff("onAuthorsSelected", mTextView);
            // Handle the camera action
        } else if (id == R.id.nav_books) {
            mTextView.setText("Books");
            doSampleDatabaseStuff("onBooksSelected", mTextView);

        } else if (id == R.id.nav_genres) {
            mTextView.setText("Genres");
            doSampleDatabaseStuff("onGenresSelected", mTextView);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // This is how, DatabaseHelper can be initialized for future use
    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, DatabaseHelper.class);
        }
        return databaseHelper;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

		/*
		 * You'll need this in your class to release the helper when done.
		 */
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    /**
     * Do our sample database stuff.
     */
    private void doSampleDatabaseStuff(String action, TextView tv) {
        // get our dao
        /**
         * RuntimeExceptionDao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
         *
         */
        // query for all of the data objects in the database


        // This is how, a reference of DAO object can be done
        try {

            simpleDao = getHelper().getSimpleDataDao();
            //List<SimpleData>
            list = simpleDao.queryForAll();
            Log.d(LOG_TAG, "WE got DAO");

            // our string builder for building the content-view
            StringBuilder sb = new StringBuilder();
            sb.append("Found ").append(list.size()).append(" entries in DB in ").append(action).append("()\n");

            // if we already have items in the database
            int simpleC = 1;
            for (SimpleData simple : list) {
                sb.append('#').append(simpleC).append(": ").append(simple).append('\n');
                simpleC++;
            }
            sb.append("------------------------------------------\n");
            sb.append("Deleted ids:");

            for (SimpleData simple : list) {
                simpleDao.delete(simple);
                sb.append(' ').append(simple.id);
                Log.i(LOG_TAG, "deleting simple(" + simple.id + ")");
                simpleC++;
            }
            sb.append('\n');
            sb.append("------------------------------------------\n");

            int createNum;
            do {
                createNum = new Random().nextInt(MAX_NUM_TO_CREATE) + 1;
            } while (createNum == list.size());
            sb.append("Creating ").append(createNum).append(" new entries:\n");
            for (int i = 0; i < createNum; i++) {
                // create a new simple object
                long millis = System.currentTimeMillis();
                SimpleData simple = new SimpleData(millis);
                // store it in the database
                simpleDao.create(simple);

                Log.i(LOG_TAG, "created simple(" + millis + ")");
                // output it
                sb.append('#').append(i + 1).append(": ");
                sb.append(simple).append('\n');
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    // ignore
                }
            }




            tv.setText(sb.toString());
            Log.i(LOG_TAG, "Done with page at " + System.currentTimeMillis());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

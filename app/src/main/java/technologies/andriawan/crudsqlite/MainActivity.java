package technologies.andriawan.crudsqlite;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    protected Cursor cursor;
    SQLHelper dbHelper;
    Button export;
    Button Mimport;
    Button read;
    Button Filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        export = (Button) findViewById(R.id.export);
        Mimport = (Button) findViewById(R.id.Mimport);
        read = (Button) findViewById(R.id.read_data);
        Filter = (Button) findViewById(R.id.filter);


        export.setOnClickListener(this);
        Mimport.setOnClickListener(this);
        read.setOnClickListener(this);
        Filter.setOnClickListener(this);

        //creating a new folder for the database to be backuped to
        File direct = new File(Environment.getExternalStorageDirectory() + "/crudSqlite");

        if(!direct.exists())
        {
            if(direct.mkdir())
            {
                //directory is created;
            }

        }


        dbHelper = new SQLHelper(this);

        try {
            dbHelper.createDataBase();
        } catch (Exception ioe) {
            Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_LONG).show();
        }
    }


    //exporting database
    private void exportDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "/data/" + "technologies.andriawan.crudsqlite" + "/databases/master.db";
                String backupDBPath  = "crudSqlite/backup";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }



    //importing database
    private void importDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "/data/" + "technologies.andriawan.crudsqlite" + "/databases/master.db";
                String backupDBPath  = "crudSqlite/backup";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }

    public void read_data()
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        cursor = db.rawQuery("SELECT * FROM aclass",null);

        cursor.moveToFirst();
        for (int cc=0; cc < cursor.getCount(); cc++)
        {
            cursor.moveToPosition(cc);
            Toast.makeText(getApplicationContext(), cursor.getString(2), Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        if(view == export){
            exportDB();
        }

        if(view == Mimport){
            importDB();
        }

        if (view == read){
            read_data();
        }

        if (view == Filter){
            startActivity(new Intent(this,FilterAction.class));
        }

    }
}

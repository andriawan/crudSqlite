package technologies.andriawan.crudsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by root on 28/03/16.
 */
public class SQLHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "master.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE = "kontak";
    private static String DB_PATH;

    private Context myContext;

    public SQLHelper(Context context ) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
        myContext = context;
        DB_PATH = "/data/data/" + myContext.getPackageName() + "/" + "databases/";
    }

    public void createDataBase() throws IOException{
        if(DataBaseisExist()){
            //do nothing - database already exist
            Toast.makeText(myContext, "Database Sudah Ada", Toast.LENGTH_LONG).show();
        }
        else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
                Toast.makeText(myContext, "Database Berhasil Diimport Dari Assets", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }


    private boolean DataBaseisExist(){
        SQLiteDatabase checkDB = null;
        try{
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){
            //database does't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        if(checkDB != null )return true ;else return false;
    }

    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DATABASE_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

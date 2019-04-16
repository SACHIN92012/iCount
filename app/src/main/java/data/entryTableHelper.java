package data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class entryTableHelper extends SQLiteOpenHelper{

    public static final String LOG_TAG = dataDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "shelter2.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link dataDbHelper}.
     *
     * @param context of the app
     */
    public entryTableHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_PETS_TABLE2 =  "CREATE TABLE " + dataContract.dataEntry.TABLE_NAME2 + " ("
                + dataContract.dataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + dataContract.dataEntry.COLUMN_NAME + " TEXT , "
                + dataContract.dataEntry.ENTRY_TEXT+" TEXT ,"
                + dataContract.dataEntry.COLUMN_BUILDING_NUMBER + " INTEGER  DEFAULT 0 ,"
                + dataContract.dataEntry.ENTRY_NUMBER+" INTEGER ,"
                + dataContract.dataEntry.IS_ENTERED+" INTEGER ,"
                + dataContract.dataEntry.ROLL_NO+" TEXT ,"
                + dataContract.dataEntry.ENTRY_TIME+" TEXT);";


        String Block[]=new String[7];

        for(int i=0;i<7;i++)
        {
            Block[i] =  "CREATE TABLE " + dataContract.dataEntry.BLOCK[i] + " ("
                    + dataContract.dataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + dataContract.dataEntry.COLUMN_NAME + " TEXT , "
                    + dataContract.dataEntry.ENTRY_TEXT+" TEXT ,"
                    + dataContract.dataEntry.COLUMN_BUILDING_NUMBER + " INTEGER  DEFAULT 0 ,"
                    + dataContract.dataEntry.ENTRY_NUMBER+" INTEGER ,"
                    + dataContract.dataEntry.IS_ENTERED+" INTEGER ,"
                    + dataContract.dataEntry.ROLL_NO+" TEXT ,"
                    + dataContract.dataEntry.ENTRY_TIME+" TEXT);";
        }


        String SQL_CREATE_PETS_TABLE4 =  "CREATE TABLE " + dataContract.dataEntry.NUMBER_TABLE + " ("
                + dataContract.dataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + dataContract.dataEntry.ENTRY_TIME+" TEXT );";


        String SQL_CREATE_PETS_TABLE3 =  "CREATE TABLE " + dataContract.dataEntry.BEST_TABLE + " ("
                + dataContract.dataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + dataContract.dataEntry.COLUMN_NAME + " TEXT , "
                + dataContract.dataEntry.ENTRY_TEXT+" TEXT ,"
                + dataContract.dataEntry.COLUMN_BUILDING_NUMBER + " INTEGER  DEFAULT 0 ,"
                + dataContract.dataEntry.ENTRY_NUMBER+" INTEGER ,"
                + dataContract.dataEntry.IS_ENTERED+" INTEGER ,"
                + dataContract.dataEntry.ENTRY_TIME+" TEXT);";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE2);
        //db.execSQL(SQL_CREATE_PETS_TABLE3);
        db.execSQL(SQL_CREATE_PETS_TABLE4);

        for(int i=0;i<7;i++)
        {
            db.execSQL(Block[i]);
        }
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }

}

package data;

import android.provider.BaseColumns;

public final class dataContract {

    private dataContract(){}

    public static final class dataEntry implements BaseColumns {

        public final static String TABLE_NAME = "data";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_BUILDING_NUMBER= "buildingNumber";

        public final static String TABLE_NAME2="EntryTable";
        public final static String ENTRY_TEXT="EntryText";
        public final static String ENTRY_NUMBER="EntryNumber";
        public final static String ENTRY_TIME="EntryTime";
        public final static String IS_ENTERED="isEntered";
        public final static String BEST_TABLE="BestTable";
        public final static String TABLE_NAME3="ExitTable";

        public final static String NUMBER_TABLE="NumberTable";
        public final static String NUMBER="number";
    }

}

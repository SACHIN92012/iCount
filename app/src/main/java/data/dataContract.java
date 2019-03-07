package data;

import android.provider.BaseColumns;

public final class dataContract {

    private dataContract(){}

    public static final class dataEntry implements BaseColumns {

        public final static String TABLE_NAME = "data";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_BUILDING_NUMBER= "buildingNumber";

    }

}

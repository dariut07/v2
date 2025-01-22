package ro.ase.moneysaver;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {ContUser.class,Buget.class, Cheltuiala.class,Tranzactie.class},version = 3,exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDB extends RoomDatabase {
    private static final String dbName="app.db";
    private static AppDB instance;

    public static AppDB getInstance(Context context) {
        if(instance==null){
            instance= Room.databaseBuilder(context,AppDB.class,dbName).allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();

        }
        return instance;
    }
    public abstract UserDAO getUserDAO();
    public abstract BugetDAO getBugetDAO();
}

package com.example.geotrack;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.LocationListener;
 
public class DataBase extends  SQLiteOpenHelper {
	String sqlCreate = "CREATE TABLE Positions (date TEXT PRIMARY KEY, latitude DOUBLE, longitude DOUBLE)";
	
	public DataBase(Context contexto, String nombre,CursorFactory factory, int version) {
		super(contexto, nombre, factory, version);
	}
	public DataBase(LocationListener contexto, String nombre,CursorFactory factory, int version) {
		super((Context) contexto, nombre, factory, version);
	}
	
	@Override
    public void onCreate(SQLiteDatabase db) {
        //Execute SQL sentence to create the table.
        db.execSQL(sqlCreate);
    }
	@Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        //Delete last version of the table
        db.execSQL("DROP TABLE IF EXISTS Usuarios");
        //Create a new version of the table
        db.execSQL(sqlCreate);
    }
}

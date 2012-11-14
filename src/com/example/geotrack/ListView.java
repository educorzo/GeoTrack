package com.example.geotrack;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SimpleAdapter;

public class ListView extends ListActivity {
	ArrayList<HashMap<String,String>> Eventos;
	String[] from=new String[] {"Date","Latitude","Longitude"};
	int[] to=new int[]{R.id.date,R.id.Latitude,R.id.Longitude};
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        
        DataBase usdbh = new DataBase(this, "DB", null, 1);
        SQLiteDatabase db = usdbh.getWritableDatabase();
        Eventos = new ArrayList<HashMap<String, String>>();
        if(db != null){
        	String[] args = new String[] {"1"};
        	Cursor c= db.rawQuery("SELECT * FROM Positions WHERE '1'=?",args);
        	Log.wtf("NUMERO", ""+c.getCount());
        	if (c.moveToFirst()) {
        		int i=1;
        		 do {
        			 HashMap<String,String> datosEvento=new HashMap<String, String>();
        			 datosEvento.put("Date", c.getString(0));
        			 datosEvento.put("Latitude", ""+c.getInt(1));
        			 datosEvento.put("Longitude",""+c.getInt(2));
        			 datosEvento.put("id", ""+i);
        			 Eventos.add(datosEvento);
        			 i++;
        	     } while(c.moveToNext());
        	}
        	db.close();
        }
        SimpleAdapter ListadoAdapter=new SimpleAdapter(this, Eventos, R.layout.row, from, to);
        setListAdapter(ListadoAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list_view, menu);
        return true;
    }
    
    /*
     * 
     *Some point has been clicked and it go to map
     * 
     */
    protected void onListItemClick(android.widget.ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(this,Map.class);
        intent.putExtra("com.example.geotrack.ListView",Eventos.get(position).get("Date"));
        startActivity(intent);
    }
}

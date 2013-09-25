package com.example.pruebalista;
import DB.src.DBAdapter;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


public class VerTodo extends Activity {
	private DBAdapter mDbHelper;
	TextView g;
	private Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.visor);
		try {
		
		mDbHelper = new DBAdapter(this);
		mDbHelper.open();
		
		g = (TextView) findViewById(R.id.tvDatos);
		String bd; 
		bd = "Circulo\n";
		
		cursor = mDbHelper.fetchAll();
 		 		
 		if (cursor != null && cursor.moveToFirst()) {
			
			//showSelectedNumber(name, type, number);
			
			 do {
				 String name = cursor.getString(1);
					String number = cursor.getString(2);
					String correo = cursor.getString(3);
					bd += name +" " + number + " "+ correo +"\n";
					 g.setText(bd)	;
	 		     } while(cursor.moveToNext());
	       
 		}
			
		} catch (Exception hj){
			Toast.makeText(this, hj.toString(), Toast.LENGTH_LONG).show();
		}
 		
 		
 		/*
 		String[] from = new String[] { mDbHelper.KEY_NUMERO };
 		int[] to = new int[] { R.id. };
 
 		//Creamos un array adapter para desplegar cada una de las filas
 		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,R.layout.row, cursor, from, to);
 		setListAdapter(notes);*/
		
		
		
	
		
		
		
	}
	
}


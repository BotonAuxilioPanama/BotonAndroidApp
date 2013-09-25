package com.example.pruebalista;


import DB.src.DBAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
Button b , btRojo, btAmarillo, btVerde, btSUME;
Intent callIntent;
String APLAFA_Num = "318345";
String  User_Num;
private DBAdapter mDbHelper;

private Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btRojo = (Button) findViewById(R.id.btRojo);
		btAmarillo = (Button) findViewById(R.id.btAmarillo);
		btVerde = (Button) findViewById(R.id.btVerde);
		btSUME = (Button) findViewById(R.id.btSUME);
		
		btAmarillo.setBackgroundResource(R.drawable.botama);
		btVerde.setBackgroundResource(R.drawable.botverd);
		btRojo.setBackgroundResource(R.drawable.botroj);
		
		
		btRojo.setOnClickListener(this);
		btAmarillo.setOnClickListener(this);
		btVerde.setOnClickListener(this);
		btSUME.setOnClickListener(this);
		
		User_Num = getPhoneNumber();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		 menu.add(Menu.NONE, 1 , Menu.NONE, "+Circulo").setIcon(android.R.drawable.ic_input_add);
	     menu.add(Menu.NONE, 2, Menu.NONE, "Directorio").setIcon(android.R.drawable.ic_dialog_info);
	    // menu.add(Menu.NONE, 3, Menu.NONE, "Registrar").setIcon(R.drawable.ic_launcher);
	     menu.add(Menu.NONE, 4, Menu.NONE, "Ver Circulo").setIcon(android.R.drawable.ic_menu_agenda);
		
		return true;
		
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case 1:
        	Intent i = new Intent(this, Inserta.class );
            startActivity(i);
            return true;
        case 2:
        	AlertDialog alertDialog;
        	alertDialog = new AlertDialog.Builder(this).create();
        	alertDialog.setTitle("Lineas de Ayuda");
        	alertDialog.setMessage("Telefonos de Ayuda\nPolicia Naccional 104\nBomberos  103\nMIDES 147\n"+ User_Num);
        	alertDialog.show();
        	return true;
        case 3:
        	Intent ik = new Intent(this, NuevoUsuarioActivity.class );
            startActivity(ik);
            return true;
        case 4:
        	Intent g = new Intent(this, VerCirculo.class);
        	startActivity(g);
        	
        	
//        	Intent il = new Intent(this, VerTodo.class );
//            startActivity(il);
            //aki para ver los numeros a los ke les mando el mensaje            
            return true;
        	
        default:
            return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()){
		case R.id.btVerde:
			alertar("Alerta Verde Indica conflictos ocasionales");
			 enviarsms(APLAFA_Num, "VidAux\nAlerta Verde. Remitente: " + User_Num);
			break;
		
		case R.id.btRojo:
			alertar("Alerta Roja Indica violencia bajo lesiones");
			 enviarsms(APLAFA_Num, "VidAux\nAlerta Roja. Remitente: " + User_Num);
			break;
			
		case R.id.btAmarillo:
			alertar("Alerta Amarilla Indica acción bajo amenaza");
			enviarsms(APLAFA_Num, "VidAux\nAlerta Amarilla. Remitente: " + User_Num);
			break;
	    	
		case R.id.btSUME:
			call();
			break;
	
		}
		
	
	}
	
	public void call(){
		try{
			callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:*312"));
			startActivity(callIntent);
		}catch (ActivityNotFoundException v){
			Log.e("dialing-example", "Call failed", v);
		}
	}
	
	public void alertar (String msg){
		try {
			
			mDbHelper = new DBAdapter(this);
			mDbHelper.open();
			
			cursor = mDbHelper.fetchAll();
	 		 		
	 		if (cursor != null && cursor.moveToFirst()) {
				 do {
	 				String number = cursor.getString(2);
	 		          enviarsms(number, msg);
	 		     } while(cursor.moveToNext());
	 			
	 		}
				
			} catch (Exception hj){
				Toast.makeText(this, hj.toString(), Toast.LENGTH_LONG).show();
			}
			
	}
	
	private void enviarsms (String numero, String mensaje){
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(numero, null, mensaje, null, null);
		Toast.makeText(this, "Enviado a:" + numero , Toast.LENGTH_LONG).show();
		
		}
	
	private String getPhoneNumber(){
		  TelephonyManager mTelephonyManager;
		  mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); 
		  return mTelephonyManager.getLine1Number();
		}

	
	}


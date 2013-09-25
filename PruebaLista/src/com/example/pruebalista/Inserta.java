package com.example.pruebalista;

import DB.src.DBAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Inserta extends Activity implements OnClickListener {
	Button btGuardar, btBuscar;
	EditText etnombre, etnumero, etcorreo;
	private Long mRowId;
	private DBAdapter mDbHelper;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insertanumero);

		mDbHelper = new DBAdapter(this);
		mDbHelper.open();

		btGuardar = (Button) findViewById(R.id.buttonGuardar);
		btBuscar = (Button) findViewById(R.id.ButtonBuscar);
		etnombre = (EditText) findViewById(R.id.Editnombre);
		etnumero = (EditText) findViewById(R.id.editnumero);
		//etcorreo = (EditText) findViewById(R.id.EditTextCorreo);

		btGuardar.setOnClickListener(this);
		btBuscar.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ButtonBuscar:
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
			startActivityForResult(intent, 1);
			break;

		case R.id.buttonGuardar:
			saveState();
			
			finish();
		}

	}
	public void saveState() 
    {
 
		
		String name = etnombre.getText().toString();
		String num = etnumero.getText().toString();
		String correo = ""; //etcorreo.getText().toString();
		//mRowId = null;
		
		try {
//									
			if (mRowId == null) {
				long id = mDbHelper.createItem(name, num, correo);
				if (id > 0) {
					mRowId = id;
					Toast.makeText(Inserta.this, "Inserción Correcta", Toast.LENGTH_SHORT).show();
				}
			} else {
			
				mDbHelper.updateItem(mRowId, name, num, correo);
				Toast.makeText(Inserta.this, "Contacto Actualizado", Toast.LENGTH_SHORT).show();
			}
//			
		
	} catch (Exception hg) {
			hg.printStackTrace();

			Toast.makeText(Inserta.this, hg.toString(), Toast.LENGTH_SHORT).show();
		}

		}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			Uri uri = data.getData();

			if (uri != null) {
				Cursor c = null;
				try {
					c = getContentResolver()
							.query(uri,
									new String[] {
											ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
											ContactsContract.CommonDataKinds.Phone.NUMBER,
											 },
									null, null, null);

					if (c != null && c.moveToFirst()) {
						String name = c.getString(0);
						String number = c.getString(1);
						showSelectedNumber(name, number);
					}
				} finally {
					if (c != null) {
						c.close();
					}
				}
			}
		}
	}

	public void showSelectedNumber(String name, String number) {
		etnombre.setText(name);
		etnumero.setText(number);

		
	}

	
}

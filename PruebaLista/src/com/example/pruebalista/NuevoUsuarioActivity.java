package com.example.pruebalista;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NuevoUsuarioActivity  extends Activity  {

	private ProgressDialog progresdiag;
	
	JSONParser jsonParser = new JSONParser();
//    EditText nombre;
    EditText cedula;
//    EditText inputDesc;
 
    // url to create new product
    private static String url_create_user = "http://www.vdproject.tk/crear_usuario.php";
 
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_usuario);
 
        // Edit Text
//        nombre = (EditText) findViewById(R.id.crearUsuario);
        cedula = (EditText) findViewById(R.id.cedulaET);
//        inputDesc = (EditText) findViewById(R.id.inputDesc);
 
        // Create button
        Button btnCreateUser = (Button) findViewById(R.id.aceptarBtn);
 
        // button click event
        btnCreateUser.setOnClickListener(new View.OnClickListener() {
 
           
            public void onClick(View view) {
                // creating new product in background thread
                new CreateNewUser().execute();
            }
        });
    }
 
    /**
     * Background Async Task to Create new product
     * */
    class CreateNewUser extends AsyncTask<String, String, String> {
 
        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progresdiag = new ProgressDialog(NuevoUsuarioActivity.this);
            progresdiag.setMessage("Creando usuario..");
            progresdiag.setIndeterminate(false);
            progresdiag.setCancelable(true);
            progresdiag.show();
        }
 
        /**
         * Creating product
         * */
        protected String doInBackground(String... args) {
            String cedulaNum = cedula.getText().toString();
            
 
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("cedula", cedulaNum));
 
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                    "POST", params);
            
            Log.d("Create Response", json.toString());
 
            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                    finish();
                } else {
                    // fallo de creacion de usuario
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
        	progresdiag.dismiss();
        }
 
    }
}

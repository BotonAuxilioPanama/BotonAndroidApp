package DB.src;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	//Campos de la BD
		public static final String KEY_ROWID = "_id";
		public static final String KEY_NOMBRE = "nombre";
		public static final String KEY_NUMERO = "numero";
		public static final String KEY_CORREO = "correo";
		private static final String DATABASE_TABLE = "circulo";
		private Context context;
		private SQLiteDatabase database;
		private DataBaseHelper dbHelper;
	 
		public DBAdapter(Context context) {
			this.context = context;
		}
	 
		public DBAdapter open() throws SQLException {
			dbHelper = new DataBaseHelper(context);
			database = dbHelper.getWritableDatabase();
			return this;
		}
	 
		public void close() {
			dbHelper.close();
		}
	 
		/**
		 * Crea una nueva tarea, si esto va bien retorna la
		 * rowId de la tarea, de lo contrario retorna -1
		 */
		public long createItem(String nombre, String numero, String correo) {
			ContentValues initialValues = createContentValues(nombre, numero,correo);
	 
			return database.insert(DATABASE_TABLE, null, initialValues);
		}
	 
		//Actualizar 
		public boolean updateItem(long rowId, String nombre, String numero, String correo) {
			ContentValues updateValues = createContentValues(nombre, numero,correo);
	 
			return database.update(DATABASE_TABLE, updateValues, KEY_ROWID + "="+ rowId, null) > 0;
		}
	 
		//Borrar
		public boolean deleteItem(long rowId) {
			return database.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
		}
	 
		//Returna un Cursor que contiene todos los items
		public Cursor fetchAll() {
			return database.query(DATABASE_TABLE, new String[] { 
					KEY_ROWID,
					KEY_NOMBRE, 
					KEY_NUMERO, 
					KEY_CORREO }, 
					null, null, null,null, null);
		}
	 
		//Returna un Cursor que contiene la info del item
		public Cursor fetchItem(long rowId) throws SQLException {
			Cursor mCursor = database.query(
					true, DATABASE_TABLE, new String[] {
					KEY_ROWID,
					KEY_NOMBRE, 
					KEY_NUMERO, 
					KEY_CORREO },
					KEY_ROWID + "=" + rowId, 
					null, null, null, null, null);
			if (mCursor != null) {
				mCursor.moveToFirst();
			}
			return mCursor;
			
		}
	 
		private ContentValues createContentValues(String nombre, String numero,	String correo) {
			ContentValues values = new ContentValues();
			values.put(KEY_NOMBRE, nombre);
			values.put(KEY_NUMERO, numero);
			values.put(KEY_CORREO, correo);
			return values;
			
		}
		
		
}

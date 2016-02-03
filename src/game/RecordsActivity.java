package game;

import com.example.car.R;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class RecordsActivity extends Activity {

	private SQLiteDatabase dataBase;
	private Cursor cursor;
	private TextView txtRecords;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_records);
		
		dataBase = openOrCreateDatabase("Records_DB", MODE_PRIVATE, null);		
		cursor = dataBase.rawQuery("SELECT * FROM RECORDS ORDER BY Score DESC", null);
		cursor.moveToFirst();
		
		txtRecords = (TextView) findViewById(R.id.textView_Records);
		
		int i = 0;
		String str = "";
		while (!cursor.isAfterLast()) {
			i++;
			str += Integer.toString(i) + "  " + cursor.getString(cursor.getColumnIndex("Name")) + "\t" +
					cursor.getInt(cursor.getColumnIndex("Score")) + "\n";
			cursor.moveToNext();
		}
		txtRecords.setText(str);
	}
	
	public void onBtnClearRecords(View w) {
		cursor = dataBase.rawQuery("SELECT * FROM RECORDS", null);
		cursor.moveToFirst();
	}

	public void onBtnCancel(View w) {
		finish();
	}
	
}

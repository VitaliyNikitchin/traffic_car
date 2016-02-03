package game;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.car.R;


public class EndGameActivity extends Activity {

	private int score;
	private int hits;
	private int meters;
	private int level;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_game);
		this.setFinishOnTouchOutside(false);
		
		Intent intent = getIntent();
		TextView textView_hits = (TextView) findViewById(R.id.textView_hits);
		TextView textView_meters = (TextView) findViewById(R.id.textView_meters);
		TextView textView_level = (TextView) findViewById(R.id.textView_level);
		TextView textView_score = (TextView) findViewById(R.id.textView_score);
		
		hits = intent.getIntExtra("hits", 0);
		meters = intent.getIntExtra("meters", 0);
		level = intent.getIntExtra("level", 0);
		score = hits + meters;
		
		textView_hits.setText(textView_hits.getText().toString() + Integer.toString(hits));
		textView_meters.setText(textView_meters.getText().toString() + Integer.toString(meters));
		textView_level.setText(textView_level.getText().toString() + Integer.toString(level));
		textView_score.setText(textView_score.getText().toString() + Integer.toString(score));
	}

	public void onBtnCancelClick(View w) {
		finish();		
	}
	
	public void onBtnSaveClick(View w) {
		EditText playerName = (EditText) findViewById(R.id.editText_playerName);

		SQLiteDatabase dataBase = openOrCreateDatabase("Records_DB", MODE_PRIVATE, null);
		dataBase.execSQL("CREATE TABLE IF NOT EXISTS RECORDS (Name VARCHAR, Score int);");
		
		//dataBase.execSQL("INSERT INTO RECORDS VALUES ('Vetal', 1203);");
		dataBase.execSQL(insertIntoTable(playerName.getText().toString(), score));
		dataBase.close();
		
		Toast.makeText(this, "Record saved", Toast.LENGTH_SHORT).show();
		finish();
	}
	
	private String insertIntoTable(String name, int number) {
		String str;
		str = "INSERT INTO RECORDS VALUES ('" + name + "', "  + Integer.toString(number) + ");";
		return str;
	}
	
}

package game;

import com.example.car.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	public void onBtnStart(View w) {
		Intent client = new Intent(this, PlayActivity.class);
		startActivity(client);
	}

	public void onBtnRecords(View w) {			
		startActivity(new Intent(this, RecordsActivity.class));
	}

	public void onBtnRules(View w) {		
		startActivity(new Intent(this, RuleActivity.class));
	}

	public void onBtnSettings(View w) {
		startActivity(new Intent(this, SettingsActivity.class));
	}
	
	public void onBtnExite(View w) {
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}

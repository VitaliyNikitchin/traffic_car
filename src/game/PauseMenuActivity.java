package game;

import com.example.car.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PauseMenuActivity extends Activity {

	private Intent intent;
	private AlertDialog.Builder builder;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_pause);
		this.setFinishOnTouchOutside(false);
		
		intent = new Intent();
		builder = new AlertDialog.Builder(this);
	}

	public void onBtnResumeClick(View w) {
		intent.putExtra("pressedButton", "btnResume");
		setResult(RESULT_OK, intent);
		finish();
	}

	public void onBtnRestartClick(View w) {
		builder.setTitle("Restart");
		builder.setMessage("Are you sure you want to restart ?");
		builder.setNegativeButton("NO", null);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				intent.putExtra("pressedButton", "btnRestart");
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		AlertDialog ad = builder.create();
		ad.show();
	}

	public void onBtnSettingsClick(View w) {
		startActivity(new Intent(this, SettingsActivity.class));
	}

	public void onBtnQuitCkick(View w) {	
		builder.setTitle("Quite");
		builder.setMessage("Are you sure you want to quit ?");
		builder.setNegativeButton("NO", null);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				intent.putExtra("pressedButton", "btnQuite");
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		AlertDialog ad = builder.create();
		ad.show();
	}
	
	@Override
	public void onBackPressed() {
		intent.putExtra("pressedButton", "btnResume");
		setResult(RESULT_OK, intent);
		finish();
	}	
}

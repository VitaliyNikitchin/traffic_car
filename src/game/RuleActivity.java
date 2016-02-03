package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.car.R;


public class RuleActivity extends Activity {

	final String FILENAME = "file";
	private TextView text;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rule);
		
		text = (TextView) findViewById(R.id.textView_rules);;
		this.readFile();
	}

	
	public void onOkBtn(View w) {
		finish();
	}
	

	private void readFile() {
		try {
			InputStream inputStream = getResources().openRawResource(R.raw.rules_file);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

			String str = "";
			while ((str = br.readLine()) != null) {	
				text.setText(text.getText() + str);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

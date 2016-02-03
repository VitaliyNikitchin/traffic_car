package game;

import com.example.car.R;

import screenSize.ScreenSize;
import sprites.Sprite;
import sprites.SpritesList;
import sprites.MyCar;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class PlayActivity extends ActionBarActivity {
	
	private Sprite myCar;
	private DrawThread drawThread;
	private ManagerThread managerThread;
	private ActionBar actionBar;
	
	private boolean itemVisibility[];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);				
		new ScreenSize(size.x, size.y);	//N4 - (768, 1184); N10 - (2560, 1504); UMI - (1080, 1920);
		myCar = new MyCar(BitmapFactory.decodeResource(getResources(), R.drawable.my_car));
		setContentView(new MySurfaceView(this));		
		
		itemVisibility = new boolean[3];
		for (int i = 0; i < 3; i++)
			itemVisibility[i] = true;
		
		this.actionBar = getSupportActionBar();
		actionBar.setTitle("level 1");
		actionBar.setSubtitle("Score: 0");
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {return;}
		notifyThreads();
		switch (data.getStringExtra("pressedButton")) {
			case "btnResume": {
				break;
			}
			case "btnQuite": {
				threadsStop();
				finish();
			}
			case "btnRestart": {
				threadsStop();				
				myCar = new MyCar(BitmapFactory.decodeResource(getResources(), R.drawable.my_car));
				setContentView(new MySurfaceView(this));
				
				for (int i = 0; i < 3; i++)
					itemVisibility[i] = true;
				invalidateOptionsMenu();
			}
		}
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    int x = (int)event.getX();
	  //int y = (int)event.getY();
	    switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	        	if (x < ScreenSize.getScreenWidth() / 2) {
	        		myCar.changeTrace(-1);	        		
	        		
	        	} else {
	        		myCar.changeTrace(1);
	        	}
	    }
	    return false;
	}
	
	
	@Override
	public void onBackPressed() {
		threadsWait();
		Intent intent = new Intent(this, PauseMenuActivity.class);
		startActivityForResult(intent, 1);
	}		
	
	
	public void finishActivity(int meters, int hits, int level) {
		Intent intent = new Intent(this, EndGameActivity.class);
		
		intent.putExtra("meters", meters);
		intent.putExtra("hits", hits);
		intent.putExtra("level", level);
		startActivity(intent);
		
		finish();
	}
	
	
	public void setItemVisibility(int j, boolean v) {
		itemVisibility[j] = v;
	}
	
	public void setScore(int score) {
		actionBar.setSubtitle("Score: " + Integer.toString(score));
	}
	
	public void setLevel(int l) {
		actionBar.setTitle("Level" + Integer.toString(l));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.play, menu);
		
		for (int i = 0; i < itemVisibility.length; i++)
			menu.getItem(i).setVisible(itemVisibility[i]);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (R.id.itemPause == item.getItemId()) {			
			threadsWait();
			Intent intent = new Intent(this, PauseMenuActivity.class);
			startActivityForResult(intent, 1);			
			return true;
		}				
		return super.onOptionsItemSelected(item);
	}
	
	private void threadsWait() {
		drawThread.setPauseFlag(true);
		managerThread.setPauseFlag(true);
	}
	
	private void notifyThreads() {
		synchronized (drawThread) {
			drawThread.setPauseFlag(false);
			drawThread.notify();
		}
		synchronized (managerThread) {							
			managerThread.setPauseFlag(false);
			managerThread.notify();
		}
	}
	
	private void threadsStop() {
		boolean retry = true;
	    drawThread.setRunning(false);
	    managerThread.setRunning(false);
	    while (retry) {
	    	try {
	    		drawThread.join();
	    		managerThread.join();
	            retry = false;
	        } catch (InterruptedException e) {}
	    }
	}
	
	private class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{

		private SpritesList spritesList;
		private Context context;
		
		public MySurfaceView(Context context) {
			super(context);	
			this.context = context;
			spritesList = new SpritesList(myCar);
			getHolder().addCallback(this);			
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			drawThread = new DrawThread(getHolder(), spritesList, myCar, getResources());
			managerThread = new ManagerThread(spritesList, drawThread, getResources(), (PlayActivity) context);
			
			managerThread.setRunning(true);
			drawThread.setRunning(true);
			managerThread.start();				       
	        drawThread.start();
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			threadsStop();			
		}

	}
}

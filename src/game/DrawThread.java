package game;

import com.example.car.R;

import screenSize.ScreenSize;
import sprites.Sprite;
import sprites.SpritesList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

	private boolean runFlag = false;
	private volatile boolean pauseFlag = false;
	private SurfaceHolder surfaceHolder;
	private Canvas canvas;
	private SpritesList spritesList;
	private Bitmap background;
	private Bitmap line;
	private Sprite myCar;

	public DrawThread(SurfaceHolder surfaceHolder, SpritesList spritesList,
			Sprite myCar, Resources resources) {
		this.surfaceHolder = surfaceHolder;
		this.myCar = myCar;
		this.spritesList = spritesList;
		
		loadBackgrounds(resources);
	}

	public void setRunning(boolean run) {
		runFlag = run;
	}

	public void setPauseFlag(boolean fl) {
		pauseFlag = fl;
	}
	
	private void loadBackgrounds(Resources resources) throws IllegalArgumentException{
		
		Bitmap backgroundSource = BitmapFactory.decodeResource(resources, R.drawable.background_0);
		float scale = (float) backgroundSource.getWidth() / ScreenSize.getScreenWidth();
        int newWidth = Math.round(backgroundSource.getWidth() / scale);
        int newHeight = Math.round(backgroundSource.getHeight() / scale);
        background = Bitmap.createScaledBitmap(backgroundSource, newWidth, newHeight, false);
        
        Bitmap lineSource = BitmapFactory.decodeResource(resources, R.drawable.background_1);
        lineSource = Bitmap.createScaledBitmap(lineSource, newWidth, newHeight, false);
        //background = Bitmap.createScaledBitmap(lineSource, newWidth, newHeight, true);
        line = Bitmap.createBitmap(lineSource, (int) ScreenSize.getScreenWidth() / 6 - 2, 14, 
        			3, (int) ScreenSize.getScreenHeigth() - 14, null, false);
	}

	@Override
	public void run() {
		
		int backgroundCounter = 0;
		while (runFlag) {
			if (!pauseFlag) {
				canvas = null;
				try {
					canvas = surfaceHolder.lockCanvas(null);
					synchronized (surfaceHolder) {
						synchronized (spritesList) {
							//canvas.drawColor(Color.BLUE);
							canvas.drawBitmap(background, 0, 0, null);
							
							for(int i = 1; i < 6; i++)
								canvas.drawBitmap(line, (int)ScreenSize.getScreenWidth() / 6 * i - 2, backgroundCounter, null);
							if (backgroundCounter >= 90) 							
								backgroundCounter = 0;								
							else	backgroundCounter += 30;	

							myCar.showCar(canvas);
							spritesList.moveForward();
							spritesList.showCar(canvas);								
						}
					}
					
				} finally {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
				
				try {
					currentThread();
					Thread.sleep(10);
				} catch (InterruptedException e) {}
				
			} else {
				synchronized (this) {
					while (pauseFlag) {
					try {
						wait();
					} catch (InterruptedException e) {}
					}

				}

			}

		}
	}
	

}

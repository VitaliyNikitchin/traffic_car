package sprites;

import screenSize.ScreenSize;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class MyCar extends Sprite{
	
	private Bitmap myCar;
	private Matrix matrix;
	private float coord_X;
	private float coord_Y;
	private float trace;
	private int health;
	
	
	public MyCar(Bitmap car) {
		myCar = car;
		myCar = resize(myCar, (float) ((1f/6f) * ScreenSize.getScreenWidth() * 1.1), 
				(float) ((1f/6f) * ScreenSize.getScreenWidth() * 1.1));
		
		this.health = 3;
		this.trace = 3f;
		this.coord_X = (ScreenSize.getScreenWidth() * (((2 * trace) - 1) / 12)) - myCar.getWidth() / 2;
		this.coord_Y = (ScreenSize.getScreenHeigth() - myCar.getHeight() - ScreenSize.getScreenHeigth() / 8);
		this.matrix =  new Matrix();
		matrix.setTranslate(coord_X, coord_Y);
	}
	
	
	public void showCar(Canvas canvas) {
		matrix.setTranslate(coord_X, coord_Y);
		canvas.drawBitmap(myCar, matrix, null);
	}


	@Override
	public void moveForward() {}


	@Override
	public void changeTrace(float newTrace) {
		if(trace + newTrace > 0 && trace + newTrace < 7){	
			trace += newTrace;
			coord_X = (ScreenSize.getScreenWidth() * ((2 * trace - 1) / 12)) - myCar.getWidth() / 2;
		}
	}


	@Override
	public boolean isRunning() {		
		return true;
	}
	
	@Override
	public float getCoord_X() {
		return coord_X;
	}

	@Override
	public float getCoord_Y() {
		return coord_Y;
	}

	@Override
	public void setCoord_Y(float y) {
		this.coord_Y = y;		
	}

	
	
	@Override
	public int getBitmapHeight() {
		return myCar.getHeight();
	}
	
	@Override
	public int getBitmapWidth() {
		return myCar.getWidth();
	}

	@Override
	public void setSpeed(int newStep) {}


	@Override
	public int getSpeed() {
		return 0;
	}


	@Override
	public void setHealth(int h) {		
		this.health = h;
	}


	@Override
	public int getHealth() {
		return health;
	}

}

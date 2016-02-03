package sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public abstract class Sprite {
	
	public abstract void moveForward();
	public abstract void changeTrace(float newTrace);
	public abstract boolean isRunning();
	public abstract float getCoord_X(); 
	public abstract float getCoord_Y(); 
	public abstract void setCoord_Y(float y);
	public abstract int getBitmapHeight();
	public abstract int getBitmapWidth();
	public abstract void setSpeed(int newSpeed);
	public abstract int getSpeed();
	public abstract void showCar(Canvas canvas);
	public abstract void setHealth(int h);
	public abstract int getHealth();
	
	protected Bitmap resize(Bitmap bit, float newWidth, float newHeight) {
		int width = bit.getWidth();
		int height = bit.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(bit, 0, 0, width, height, matrix, true);
	}
}

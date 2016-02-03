package sprites;

import java.util.Random;

import screenSize.ScreenSize;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class OpponentCar extends Sprite{

	private Bitmap opponentCar;
	private Matrix matrix;
	private float coord_Y;
	private float coord_X;
	private int health;
	private boolean isRunning;
	private int speed;
	private Random rand;
	
	
	public OpponentCar(Bitmap bit, float trace, float coord_Y) {
		
		opponentCar = resize(bit, (float) ((1f/6f) * ScreenSize.getScreenWidth() * 1.1), 
										  (float) ((1f/6f) * ScreenSize.getScreenWidth() * 1.1));
		
		this.coord_X = ((ScreenSize.getScreenWidth() * ((2 * trace - 1) / 12)) - opponentCar.getWidth() / 2);
		this.coord_Y = coord_Y;
		this.isRunning = true;
		this.rand = new Random();
		this.speed = rand.nextInt(8) + 10;
		this.matrix = new Matrix();
		matrix.setTranslate(coord_X, coord_Y); 
	}
	
	@Override
	public void changeTrace(float newTrace) {
		
	}
		
	@Override
	public void moveForward() {				 
		if (coord_Y > ScreenSize.getScreenHeigth()) {
			isRunning = false;
		}			
		else	coord_Y += speed;		
	}

	@Override
	public void showCar(Canvas canvas) {
		matrix.setTranslate(coord_X, coord_Y);
		if (coord_Y <= ScreenSize.getScreenHeigth() && coord_Y + opponentCar.getHeight() >= -3) {
			canvas.drawBitmap(opponentCar, matrix, null);
		}		 
	}
	
	@Override
	public boolean isRunning() {
		return isRunning;
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
		return opponentCar.getHeight();
	}
	
	@Override
	public int getBitmapWidth() {
		return opponentCar.getWidth();
	}

	@Override
	public void setSpeed(int newspeed) {
		speed = newspeed;		
	}

	@Override
	public int getSpeed() {
		return speed;
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

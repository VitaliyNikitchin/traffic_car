package sprites;

import java.util.ArrayList;
import android.graphics.Canvas;


public class SpritesList {

	private ArrayList<Sprite> list = new ArrayList<Sprite>();
	private Sprite myCar;

	
	public SpritesList(Sprite myCar) {
		this.myCar = myCar;
	}
	
	public void add(Sprite newCar) {
		for (Sprite sprite: list) {
			if ((sprite.getCoord_X() == newCar.getCoord_X()) && 
					(Math.abs(newCar.getCoord_Y() - sprite.getCoord_Y()) <= (sprite.getBitmapHeight()))) {
				newCar.setCoord_Y(newCar.getCoord_Y() - 2 * newCar.getBitmapHeight());
			}
		}
		list.add(newCar);
	}
	
	public void remove(Sprite deadCar) {
		list.remove(deadCar);
	}

	public void checkHitting() {	//between other cars
		//boolean fl = false;
		for (Sprite sprite: list) {
			for (Sprite car1: list) {
				if((car1.getCoord_X() == sprite.getCoord_X()) && 
						(Math.abs(car1.getCoord_Y() - sprite.getCoord_Y()) <= (sprite.getBitmapHeight()))) {

					sprite.setSpeed(car1.getSpeed() /*+1*/);
					//fl = true;
					//break;
				}
			}
			//if (fl) break;			
		}
	}
	
	public boolean testColision() {
		boolean isCrash = false;
		for (Sprite opponentCar: list) {				
			if (myCar.getCoord_X() == opponentCar.getCoord_X() && 
					(Math.abs(myCar.getCoord_Y() - opponentCar.getCoord_Y()) <= (myCar.getBitmapHeight() - 20)))	
			{
				list.remove(opponentCar);
				isCrash = true;
				break;
			}
		}
		return isCrash;
	}
	
	public void moveForward() {
		for (Sprite sprite: list) {
			sprite.moveForward();
		}	
	}

	public void showCar(Canvas canvas) {
		for (Sprite sprite: list) {			
			sprite.showCar(canvas);
		}
	}
	
	public boolean isRunning() {
		boolean run = true;
		for (Sprite sprite: list) {
			if(!sprite.isRunning()) {
				list.remove(sprite);
				run = false;
				break;
			}				
		}
		return run;
	}
	
	public Sprite getMyCar() {
		return myCar;
	}
 
}
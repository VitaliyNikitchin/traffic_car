package game;

import java.util.Random;

import sprites.OpponentCar;
import sprites.SpritesList;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Vibrator;
import com.example.car.R;

public class ManagerThread extends Thread{

	private boolean runFlag = false;
	private boolean isStillPlay;
	private volatile boolean pauseFlag = false;
	private Resources resources;
	private SpritesList spritesList;
	private Random rand;
	private int[] opponentCarMass;
	private Vibrator vibrator;
	//private int currentCarCount;
	private int level;
	private int score;
	private int meters;
	private int hits;
	
	private DrawThread drawThread;
	private PlayActivity playActivity;

	
	
	public ManagerThread(SpritesList spritesList, DrawThread drawThread, Resources resources, PlayActivity playActivity) 
	{
		this.playActivity = playActivity;
		this.spritesList = spritesList;
		this.drawThread = drawThread;
		this.resources = resources;
		this.opponentCarMass = new int[6];				
		this.vibrator = (Vibrator) playActivity.getSystemService(Context.VIBRATOR_SERVICE);
		this.rand = new Random();
		//this.currentCarCount = 6;
		this.meters = 0;
		this.level = 1;
		this.score = 0;
		this.hits = 0;
		this.isStillPlay = true;
		
		this.initializeCarMass();
		this.startLevel_1();
	}
	
	private void initializeCarMass() {
		opponentCarMass[0] = R.drawable.car_1;
		opponentCarMass[1] = R.drawable.car_2;
		opponentCarMass[2] = R.drawable.car_3;
		opponentCarMass[3] = R.drawable.car_4;
		opponentCarMass[4] = R.drawable.car_5;
		opponentCarMass[5] = R.drawable.car_6;
	}
	
	private void startLevel_1() {
		spritesList.add(new OpponentCar(BitmapFactory.decodeResource(resources, opponentCarMass[0]), rand.nextInt(6) + 1, rand.nextInt(50) - 50));
		spritesList.add(new OpponentCar(BitmapFactory.decodeResource(resources, opponentCarMass[1]), rand.nextInt(6) + 1, -100));
		spritesList.add(new OpponentCar(BitmapFactory.decodeResource(resources, opponentCarMass[2]), rand.nextInt(6) + 1, rand.nextInt(50) - 50));
		spritesList.add(new OpponentCar(BitmapFactory.decodeResource(resources, opponentCarMass[3]), rand.nextInt(6) + 1, rand.nextInt(50) - 50));
		spritesList.add(new OpponentCar(BitmapFactory.decodeResource(resources, opponentCarMass[4]), rand.nextInt(6) + 1, -200));
		spritesList.add(new OpponentCar(BitmapFactory.decodeResource(resources, opponentCarMass[5]), rand.nextInt(6) + 1, rand.nextInt(50) - 50));
	}
	
	public void setRunning(boolean run){
		runFlag = run;
	}
	
	public void setPauseFlag(boolean fl) {
		pauseFlag = fl;
	}
	

	public void run() {		
		int iterations = 0;
		
		while(runFlag) {
			if (!pauseFlag) {
			synchronized (spritesList) { 
				if(!spritesList.isRunning()) {										
					spritesList.add(
							new OpponentCar(BitmapFactory.decodeResource(resources, opponentCarMass[rand.nextInt(6)]), 
							rand.nextInt(6) + 1, -40));
				}
				spritesList.checkHitting();
				
				if (spritesList.testColision()) {
					vibrator.vibrate(200);
					spritesList.getMyCar().setHealth(spritesList.getMyCar().getHealth() - 1);
					if (spritesList.getMyCar().getHealth() == 0) {
						drawThread.setRunning(false);
						this.setRunning(false);
						this.isStillPlay = false;		
					}
					playActivity.setItemVisibility(spritesList.getMyCar().getHealth(), false);
					playActivity.invalidateOptionsMenu();
				}
				
				iterations++;
				if (iterations % 40 == 0) {
					meters++;
					score += meters + 10*hits;
					//playActivity.setScore(score);		
				}
				
				if (iterations % 1000 == 0) {
					level++;
					//playActivity.setLevel(level);
					spritesList.add(
							new OpponentCar(BitmapFactory.decodeResource(resources, opponentCarMass[rand.nextInt(6)]), 
							rand.nextInt(6) + 1, -40));
					iterations = 0;
				}
			}
				
			try {
				currentThread();
				Thread.sleep(20);
			} catch (InterruptedException e) {}
			
			}
			else {
        	synchronized (this) {
	        	while (pauseFlag) {
	        	try {
					wait();
				} catch (InterruptedException e) {}
	        	}
        	}
        	}

		} // while(runFlag)
		if (!isStillPlay)
			playActivity.finishActivity(meters, hits, level);	
	}

}

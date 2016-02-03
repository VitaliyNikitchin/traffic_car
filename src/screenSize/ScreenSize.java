package screenSize;

public class ScreenSize {
	
	private static float screenHeigth;
	private static float screenWidth;
	
	
	public ScreenSize(float w, float h) {
		screenWidth = w;
		screenHeigth = h;
	}
	
	public void setScreenSize(float h) {
		screenHeigth = h;
	}
	
	public void setScreenWidth(float w) {
		screenWidth = w;
	}
	
	
	public static float getScreenHeigth() {
		return screenHeigth;
	}
	
	public static float getScreenWidth() {
		return screenWidth;
	}
}

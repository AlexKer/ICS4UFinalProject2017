import java.awt.image.BufferedImage;

import Graphics.Animation;
import Graphics.ImageRetrieve;
public class Player extends GameObject{
	private BufferedImage[] walking={ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_walk1.png")),
									ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_walk2.png"))};
	private BufferedImage[] standing={ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_stand.png"))};
	//get in sprite index associated with string name, then pass in index to get approriate BufferedImage
	private Animation walk=new Animation(walking, 10);
	private Animation stand=new Animation(standing, 10);
	private Animation curAnimation=stand;
	private final int A=-1; private int vx, vy; //A is acceleration
	public Player(int type, int x, int y) {
		super(type, x, y, 120, 205);
	}
	private final int TERMINAL_V=20, GRAVITY=10;
	@Override
	public void move() {
		vy+=GRAVITY;
		if(vy>TERMINAL_V){
			vy=TERMINAL_V;
		}
		changeY(-vy);
		changeX(vx);
		changeY(vy);
		curAnimation.updateAnimation();
	}
	public void changeVx(int dx){ vx+=dx; }
	public void changeVy(int dy){ vy+=dy; }
	public int getVx(){ return vx; }
	public int getVy(){ return vy; }
	public void setVx(int vx0){ vx=vx0; }
	public void setVy(int vy0){ vy=vy0; }
	public Animation getCurAnimation(){ return curAnimation; }
	public void setWalkAnimation(){ curAnimation=walk; }
	public void setStandAnimation(){ curAnimation=stand; }
	public boolean collide(Object obj){
		Platform o=(Platform) obj;
		if(getX()+getWidth()>=o.getX() && 
			getX()<=o.getX()+o.getWidth() &&
			getY()+getHeight()>=o.getY() &&
			getY()<=o.getY()+o.getHeight()){
			return true;
		}
		return false;
	}
}

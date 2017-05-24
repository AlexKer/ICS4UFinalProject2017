import java.awt.Graphics;
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
	private Animation animation=stand;
	private boolean moveUp=false;
	private final int A=-1; private int vx, vy; //A is acceleration
	public Player(int type, int x, int y) {
		super(type, x, y, 120, 205);
	}
	private final int TERMINAL_V=5, GRAVITY=1;
	@Override
	//move by gravity,
	public void move() {
		super.move();
		vy+=GRAVITY;
		if(vy>TERMINAL_V){
			vy=TERMINAL_V;
		}
		animation.updateAnimation();
	}
	public void changeVx(int dx){ vx+=dx; }
	public void changeVy(int dy){ vy+=dy; }
	public int getVx(){ return vx; }
	public int getVy(){ return vy; }
	public void setVx(int vx0){ vx=vx0; }
	public void setVy(int vy0){ vy=vy0; }
	public Animation getAnimation(){ return animation; }
	public boolean isMovingUp(){ return moveUp; } //if true, moving up, otherwise falling
	public void animate(){
		animation=walk;
	    animation.start();
	}
	public void stopAnimate(){
		animation.stop();
	    animation.reset();
	    animation=stand;
	}
	public boolean collide(Object obj){
		Platform o=(Platform) obj;
		if(getX()+getWidth()>=o.getX() && 
			getX()<=o.getX()+o.getWidth() &&
			getY()+getHeight()>=o.getY() &&
			getY()<=o.getY()+o.getHeight()){
			moveUp=true;
			return true;
		}
		return false;
	}
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}

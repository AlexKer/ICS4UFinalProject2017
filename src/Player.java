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
	public Player(int x, int y) {
		super(x, y, 120, 210);
	}
	private final int TERMINAL_V=10, A=5;
	private int gCnt=0; //only activate the effects of gravity every few frames
	@Override
	//move by gravity,
	public void move() {
		super.move();
		if(gCnt<10){
			gCnt++;
		}else{ 
			this.changeVy(A);
			if(this.getVy()>TERMINAL_V){
				this.setVy(TERMINAL_V);
			}
			gCnt=0;
		}
//		if(this.isMovingUp()){
//			this.changeVy(3);
//		}
		if(this.getVy()>0){
			this.toggleMovingUp();
		}
		animation.updateAnimation();
	}
	public Animation getAnimation(){ return animation; }
	public boolean isMovingUp(){ return moveUp; } //if true, moving up, otherwise falling
	public void toggleMovingUp(){ moveUp=!moveUp; }
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

import java.awt.image.BufferedImage;
import Graphics.Animation;
import Graphics.ImageRetrieve;
public class Player extends GameObject{
	//get sprite index associated with string name, 
	//then pass in index to get appropriate BufferedImage in sprite sheet
	private BufferedImage[] walking={ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_walk1.png")),
									ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_walk2.png"))};
	private BufferedImage[] standing={ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_stand.png"))};
	//terminal velocity and acceleration constants for gravity
	private final int TERMINAL_V=10, A=1;
	//two animation objects depending on which keys are pressed or released
	private Animation walk=new Animation(walking, 10);
	private Animation stand=new Animation(standing, 10);
	//animation object to store the current Animation used
	private Animation animation=stand;
	private boolean moveUp=false;
	public Player(int x, int y) {
		super(x, y, 80, 130);
	}
	//calls default super class method of move
	//
	//updates Animation object
	@Override
	public void move() {
		super.move();
		this.changeVy(A);
		if(this.getVy()>TERMINAL_V){
			this.setVy(TERMINAL_V);
		}
		if(this.getVy()>0){
			moveUp=false;
		}else{
			moveUp=true;
		}
		animation.updateAnimation();
	}
	//returns the Animation object
	public Animation getAnimation(){ return animation; }
	////if true, moving up, otherwise falling
	public boolean isMovingUp(){ return moveUp; } 
	public void toggleMovingUp(boolean b){ moveUp=b; } 
	//start Animation object, and change Player's animation to walking
	public void animate(){
		animation=walk;
	    animation.start();
	}
	//stop Animation object, its instance variables are reset
	//Player object is back to the default animation of standing
	public void stopAnimate(){
		animation.stop();
	    animation.reset();
	    animation=stand;
	}
	//collision code of GameObject, check if player touches platform or coin
	public boolean collide(GameObject o){
		if(getX()+getWidth()>=o.getX() && 
			getX()<=o.getX()+o.getWidth() &&
			getY()+getHeight()>=o.getY() &&
			getY()<=o.getY()+o.getHeight()){
			return true;
		}
		return false;
	}
}

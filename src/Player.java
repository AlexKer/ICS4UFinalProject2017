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
		super(x, y, 110, 200);
	}
	private final int TERMINAL_V=20, A=2;
	@Override
	//move by gravity,
	public void move() {
		super.move();
		this.changeVy(A);
		if(this.getVy()>TERMINAL_V){
			this.setVy(TERMINAL_V);
		}
		if(this.getVy()>0){
			this.toggleMovingUp(false);
		}
		animation.updateAnimation();
	}
	public Animation getAnimation(){ return animation; }
	public boolean isMovingUp(){ return moveUp; } //if true, moving up, otherwise falling
	public void toggleMovingUp(boolean b){ moveUp=b; }
	public void animate(){
		animation=walk;
	    animation.start();
	}
	public void stopAnimate(){
		animation.stop();
	    animation.reset();
	    animation=stand;
	}
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

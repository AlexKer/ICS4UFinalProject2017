import java.awt.image.BufferedImage;
import Graphics.Animation;
import Graphics.ImageRetrieve;
public class Player extends GameObject {
	private BufferedImage[] walking={ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_walk1.png")),
									ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_walk2.png"))};
	private BufferedImage[] standing={ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_stand.png"))};
	//get in sprite index associated with string name to store BufferedImage
	private Animation walk=new Animation(walking, 10);
	private Animation stand=new Animation(standing, 10);
	private Animation curAnimation=stand;
	public Player(int x, int y, int type) {
		super(x, y, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update() {
		curAnimation.updateAnimation();
	}
	public Animation getCurAnimation(){ return curAnimation; }
	public void setWalkAnimation(){ curAnimation=walk; }
	public void setStandAnimation(){ curAnimation=stand; }
}

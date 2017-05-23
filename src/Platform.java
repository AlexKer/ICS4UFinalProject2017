import java.awt.image.BufferedImage;
import Graphics.Animation;
import Graphics.ImageRetrieve;
public class Platform extends GameObject{
	private BufferedImage[] platform={ImageRetrieve.getSprite(ImageRetrieve.getIndex("ground_grass.png"))};
	public Platform(int ID, int x, int y) {
		super(ID, x, y, 300, 94);
	}
	private Animation curAnimation=new Animation(platform, 10);

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	public Animation getCurAnimation(){ return curAnimation; }

	
}

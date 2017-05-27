import java.awt.image.BufferedImage;
import Graphics.Animation;
import Graphics.ImageRetrieve;
public class Platform extends GameObject{
	private BufferedImage[] platform={ImageRetrieve.getSprite(ImageRetrieve.getIndex("ground_grass.png"))};
	public Platform(int x, int y) {
		super(x, y, 200/2, 80/2);
	}
	private Animation animation=new Animation(platform, 10);
	public void move(int val) {
		changeY(10);
	}
	public Animation getAnimation(){ return animation; }
	//one image so no need for animation
	@Override
	public void animate() { }
	@Override
	public void stopAnimate() { }
}

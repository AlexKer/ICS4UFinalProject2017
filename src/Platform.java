import java.awt.image.BufferedImage;
import Graphics.Animation;
import Graphics.ImageRetrieve;

public class Platform extends GameObject {
	private BufferedImage[] platform = { ImageRetrieve.getSprite(ImageRetrieve.getIndex("ground_grass.png")) };

	public Platform(int x, int y) {
		super(x, y, 100, 40);
	}

	private Animation animation = new Animation(platform, 10);

	// shift platform downwards by 10 pixels
	@Override
	public void move() {
		changeY(10);
	}

	public Animation getAnimation() {
		return animation;
	}

	// one image for platform so no need for animation, it can be directly drawn
	@Override
	public void animate() {
	}

	@Override
	public void stopAnimate() {
	}
}

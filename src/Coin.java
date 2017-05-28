import java.awt.image.BufferedImage;
import Graphics.Animation;
import Graphics.ImageRetrieve;

public class Coin extends GameObject {
	// use ImageRetrieve class to store different coin images into arrays
	private BufferedImage[] bronzeCoin = { ImageRetrieve.getSprite(ImageRetrieve.getIndex("bronze_1.png")),
			ImageRetrieve.getSprite(ImageRetrieve.getIndex("bronze_2.png")),
			ImageRetrieve.getSprite(ImageRetrieve.getIndex("bronze_3.png")) };
	private BufferedImage[] silverCoin = { ImageRetrieve.getSprite(ImageRetrieve.getIndex("silver_1.png")),
			ImageRetrieve.getSprite(ImageRetrieve.getIndex("silver_2.png")),
			ImageRetrieve.getSprite(ImageRetrieve.getIndex("silver_3.png")) };
	private BufferedImage[] goldCoin = { ImageRetrieve.getSprite(ImageRetrieve.getIndex("gold_1.png")),
			ImageRetrieve.getSprite(ImageRetrieve.getIndex("gold_2.png")),
			ImageRetrieve.getSprite(ImageRetrieve.getIndex("gold_3.png")) };
	private int ID;
	private Animation animation;

	// instantiate coin Animation object depending on which ID is passed in
	public Coin(int x, int y, int ID) {
		super(x, y, 85, 50);
		this.ID = ID;
		switch (ID) {
		case 1:
			animation = new Animation(bronzeCoin, 10);
			break;
		case 2:
			animation = new Animation(silverCoin, 10);
			break;
		case 3:
			animation = new Animation(goldCoin, 10);
			break;
		}
	}

	// move coin downwards if move method is called, i.e. when Player is moving downwards
	// update the Animation object to display the next frame
	public void move() {
		changeY(10);
		animation.updateAnimation();
	}

	// getter for instance variable ID
	public int getID() {
		return ID;
	}

	// getter for Animation object for this coin
	public Animation getAnimation() {
		return animation;
	}

	@Override
	public void animate() {
		animation.start();
	}

	@Override
	public void stopAnimate() {
		animation.stop();
		animation.reset();
	}
}
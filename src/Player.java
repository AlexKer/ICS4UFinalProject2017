import java.awt.image.BufferedImage;
import Graphics.Animation;
import Graphics.ImageRetrieve;

public class Player extends GameObject {
	// 1. get sprite index associated with string name (ImageRetrieve.getIndex)
	// 2. then pass in index to get appropriate BufferedImage in sprite sheet
	// (ImageRetrieve.getSprite)
	private BufferedImage[] walking = { ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_walk1.png")),
			ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_walk2.png")) };
	private BufferedImage[] standing = { ImageRetrieve.getSprite(ImageRetrieve.getIndex("bunny1_stand.png")) };
	// terminal velocity and acceleration constants for gravity
	private final int TERMINAL_V = 10, A = 1;
	// two animation objects depending on which keys are pressed or released
	private Animation walk = new Animation(walking, 10);
	private Animation stand = new Animation(standing, 10);
	// animation object to store the current Animation used
	private Animation animation = stand;
	private boolean moveUp = false;

	public Player(int x, int y) {
		super(x, y, 80, 130);
	}

	// This move() method below does the following...
	// Note: vy=vertical velocity
	// 1.calls super class method of move,
	// changes vy by the acceleration constant.
	// 2.if the updated vy exceeds terminal velocity
	// set vy to the terminal velocity constant.
	// 3.if vy is positive, player is moving down,
	// otherwise player is moving up;
	// these changes are reflected by changing the boolean variable moveUp.
	// 4.update Animation object
	@Override
	public void move() {
		super.move();
		this.changeVy(A);
		if (this.getVy() > TERMINAL_V) {
			this.setVy(TERMINAL_V);
		}
		if (this.getVy() > 0) {
			moveUp = false;
		} else {
			moveUp = true;
		}
		animation.updateAnimation();
	}

	// returns the Animation object
	public Animation getAnimation() {
		return animation;
	}

	// if true, moving up, otherwise falling
	public boolean isMovingUp() {
		return moveUp;
	}

	// changes the moveUp variable to boolean value passed in
	public void toggleMovingUp(boolean b) {
		moveUp = b;
	}

	// start animation, and change Player's animation to walking
	public void animate() {
		animation = walk;
		animation.start();
	}

	// stop and reset Animation object
	// Player object is back to the default animation state of standing
	public void stopAnimate() {
		animation.stop();
		animation.reset();
		animation = stand;
	}

	// check if player collides with a platform or coin (GameObject)
	public boolean collide(GameObject o) {
		if (getX() + getWidth() >= o.getX() && getX() <= o.getX() + o.getWidth() && getY() + getHeight() >= o.getY()
				&& getY() <= o.getY() + o.getHeight()) {
			return true;
		}
		return false;
	}
}

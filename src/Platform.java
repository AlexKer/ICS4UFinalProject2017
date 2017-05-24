import java.awt.Graphics;
import java.awt.image.BufferedImage;

import Graphics.Animation;
import Graphics.ImageRetrieve;
public class Platform extends GameObject{
	private BufferedImage[] platform={ImageRetrieve.getSprite(ImageRetrieve.getIndex("ground_grass.png"))};
	public Platform(int ID, int x, int y) {
		super(ID, x, y, 300, 94);
	}
	private Animation animation=new Animation(platform, 10);

	@Override
	public void move() {
		changeY(5);
	}
	public Animation getAnimation(){ return animation; }
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void animate() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void stopAnimate() {
		// TODO Auto-generated method stub
		
	}

	
}

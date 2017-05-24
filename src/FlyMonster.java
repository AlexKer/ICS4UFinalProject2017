import java.awt.image.BufferedImage;
import Graphics.Animation;
import Graphics.ImageRetrieve;


public class FlyMonster extends Enemy{
//	private BufferedImage[] flying=new BufferedImage[5];
//	for(int i=0;i<5;i++){
//		flying[i]=ImageRetrieve.getSprite(ImageRetrieve.getIndex("wingMan"+i+".png"));
//	}
	private BufferedImage[] flying={ImageRetrieve.getSprite(ImageRetrieve.getIndex("wingMan1.png")),
									ImageRetrieve.getSprite(ImageRetrieve.getIndex("wingMan2.png")),
									ImageRetrieve.getSprite(ImageRetrieve.getIndex("wingMan3.png")),
									ImageRetrieve.getSprite(ImageRetrieve.getIndex("wingMan4.png")),
									ImageRetrieve.getSprite(ImageRetrieve.getIndex("wingMan5.png"))};
	public FlyMonster(int x, int y, int type) {
		super(x, y, type);
		// TODO Auto-generated constructor stub
	}
	Animation animation=new Animation(flying, 10);
	public void animate(){
	    animation.start();
	}
}

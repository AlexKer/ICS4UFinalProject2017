import java.awt.image.BufferedImage;

import Graphics.ImageRetrieve;

public class SpikeMonster extends Enemy{
	public SpikeMonster(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	private BufferedImage[] flying={ImageRetrieve.getSprite(ImageRetrieve.getIndex("spikeMan_walk1.png")),
			ImageRetrieve.getSprite(ImageRetrieve.getIndex("spikeMan_walk2.png"))};
}

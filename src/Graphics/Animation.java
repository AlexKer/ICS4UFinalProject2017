package Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Animation {
	// complete references (entirely copied class)
	// https://gamedev.stackexchange.com/questions/53705/how-can-i-make-a-sprite-sheet-based-animation-system
	private int frameCount, frameDelay, currentFrame, totalFrames;
	private boolean stopped;
	private List<Frame> frames = new ArrayList<Frame>();

	// Animation object constructor, initialize all instance variables
	// loops through BufferedImage array to copy images to the local frames List
	public Animation(BufferedImage[] frames, int frameDelay) {
		this.frameDelay = frameDelay;
		this.stopped = true;
		for (int i = 0; i < frames.length; i++) {
			this.frames.add(new Frame(frames[i]));
		}
		this.frameCount = 0;
		this.frameDelay = frameDelay;
		this.currentFrame = 0;
		this.totalFrames = this.frames.size();
	}

	// start or stop Animation object
	public void start() {
		stopped = false;
	}

	public void stop() {
		stopped = true;
	}

	public void restart() {
		stopped = false;
		currentFrame = 0;
	}

	// reset all instance variables
	public void reset() {
		this.stopped = true;
		this.frameCount = 0;
		this.currentFrame = 0;
	}

	// returns the BufferedImage at the currentFrame index
	public BufferedImage getSprite() {
		return frames.get(currentFrame).getFrame();
	}

	// updates Animation object at each tick (every loop of run method in GamePanel)
	// if Animation is not stopped, increment FrameCount
	// if frameCount is greater than specified delay, go to the next Frame,
	// set frameCount to zero, increment currentFrame
	// if currentFrame is beyond the last index in Frame Object List, set currentFrame to the first index
	public void updateAnimation() {
		if (!stopped) {
			frameCount++;
			if (frameCount > frameDelay) {
				frameCount = 0;
				currentFrame += 1;
				if (currentFrame > totalFrames - 1) {
					currentFrame = 0;
				}
			}
		}

	}
}
package Graphics;
import java.awt.image.BufferedImage;
public class Frame {
	//complete references (entirely copied class)
	//https://gamedev.stackexchange.com/questions/53705/how-can-i-make-a-sprite-sheet-based-animation-system
    private BufferedImage frame;
    public Frame(BufferedImage frame) {
        this.frame = frame;
    }
    //returns the BufferedImage of Frame object
    public BufferedImage getFrame() {
        return frame;
    }
}
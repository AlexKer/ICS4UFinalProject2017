import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;
public class GamePanel extends JPanel implements Runnable, KeyListener{
	BufferedImage player;
	BufferedImage imgBuffer = new BufferedImage(500, 800,
			BufferedImage.TYPE_INT_ARGB);
	Graphics g = imgBuffer.getGraphics();
	Platform[] p=new Platform[10];
	Enemy[] enemies=new Enemy[10];
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
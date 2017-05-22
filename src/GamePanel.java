import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.JPanel;
import Graphics.ImageRetrieve;
public class GamePanel extends JPanel implements Runnable, KeyListener{
	//TODO gravity, powerup, monster, platform, enemy
	BufferedImage imgBuffer = new BufferedImage(500, 800,
			BufferedImage.TYPE_INT_ARGB);
	Graphics g = imgBuffer.getGraphics();
	Platform[] platforms=new Platform[10];
	Enemy[] enemies=new Enemy[10];
	Player p;
	public GamePanel(){
		ImageRetrieve.loadXML();
		ImageRetrieve.loadSpriteSheet();
		p=new Player(20, 30, 1);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int pressed=e.getKeyCode();
		switch (pressed){
			case KeyEvent.VK_W:
			case 38: // up
				p.setWalkAnimation();
			case KeyEvent.VK_A:
			case 37: // left
			
				break;
			case KeyEvent.VK_S:
			case 40: // down

				break;
			case KeyEvent.VK_D:
			case 39: // right

				break;
		
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		p.getCurAnimation().stop();
		p.getCurAnimation().reset();
		p.setStandAnimation();
	}
	@Override
	public void run() {
		p.update();
	}
	public void paintComponent(Graphics g) {
		g.drawImage(p.getCurAnimation().getSprite(), 100, 200, null);
	}
}
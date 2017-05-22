import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import Graphics.ImageRetrieve;
public class GamePanel extends JPanel implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	//TODO gravity, powerup, monster, platform, enemy
	private double SCALE = 0.5;
	BufferedImage imgBuffer = new BufferedImage(600, 800,
			BufferedImage.TYPE_INT_ARGB);
	Graphics g = imgBuffer.getGraphics();
	//!!!!!DO PLAYER AND PLATFORMS, THEN ENEMIES!!!!!
	private Player p;
	private ArrayList<Platform> platforms;
	private ArrayList<Enemy> enemies;
	private int gameState=1; //GameState 1=StartMenu, 2=InGame, 3=GameOver
	private boolean left, right, up, down, jump, fall;
	private double moveSpeed, fallSpeed;
	//constructor
	public GamePanel(){
		ImageRetrieve.loadXML();
		ImageRetrieve.loadSpriteSheet();
		p=new Player(0, 0, 1);
		platforms=new ArrayList<Platform>();
		enemies=new ArrayList<Enemy>();
		//generate starting platforms randmly
		for(int i=0;i<10;i++){ 
			platforms.add(new Platform(1,(int)(Math.random()*600),(int)(Math.random()*800)));
			System.out.println(platforms.get(i).getX()+" "+platforms.get(i).getY());
		}
		//ensure one low one
		platforms.add(new Platform(1,500,200));
	}
	public int[] getRandomPosition(int x, int y, int l, int w){
		int[] a={(int)Math.random()*l, (int)Math.random()*w};
		return a;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("HELLO");
		int pressed=e.getKeyCode();
		switch (pressed){
			case KeyEvent.VK_W:
			case 38: // up
				p.setWalkAnimation();
				System.out.println("WALKING");
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
	public void paint(Graphics g) {
		g.drawImage(p.getCurAnimation().getSprite(), 0, 500, null);
		for(int i=0;i<platforms.size();i++){
			Platform cur=platforms.get(i);
			g.drawImage(cur.getCurAnimation().getSprite(), cur.getX()-cur.getWidth()/2,
					cur.getY()-cur.getHeight()/2, cur.getWidth()/2, cur.getHeight()/2, null);
		}
	}
}
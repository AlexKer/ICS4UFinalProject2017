import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import Graphics.ImageRetrieve;
public class GamePanel extends JPanel implements Runnable, KeyListener{
	//TODO gravity, powerup, monster, platform, enemy
	private static final long serialVersionUID = 1L;
	private boolean moveRight, moveLeft;
	private final int SDV = -10; //Scroll down speed
	BufferedImage imgBuffer = new BufferedImage(600, 800,
			BufferedImage.TYPE_INT_ARGB);
	Graphics g = imgBuffer.getGraphics();
	private Player p;
	private ArrayList<Platform> platforms;
	private ArrayList<Enemy> enemies;
	private int gameState=2; //GameState 1=StartMenu, 2=InGame, 3=GameOver
	private boolean left, right, up, down, jump, fall;
	private double moveSpeed, fallSpeed;
	private int score=0;
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
	@Override
	public void run() {
		//enforce border
		if(p.getX()<0){
			p.setX(0);
		}else if(p.getX()>600-120){
			p.setX(600-120);
		}
		//---horizontal movement with left and right arrow keys---//
		if(moveRight ^ moveLeft){
			System.out.println("HELLO");
			if(moveRight){ p.changeX(50); }
			else if(moveLeft){ p.changeX(-50); }
		}
		//---default movement of player--//
		p.move();
		//move platform down if player is moving up
		
		//if platform moves off the bottom, generate new ones
		
		//call monster move method
		//move platforms horizontally
	}
	public void paint(Graphics g) {
		switch(gameState){
		case 1:
			//g.drawImage(StartScreen, 0, 0, null);
			break;
		case 2:
			//g.drawImage(BackGround, X, Y, null);
			
		}
		g.drawImage(p.getCurAnimation().getSprite(), p.getX(), p.getY(), null);
		for(int i=0;i<platforms.size();i++){
			Platform cur=platforms.get(i);
			g.drawImage(cur.getCurAnimation().getSprite(), cur.getX()-cur.getWidth()/2,
					cur.getY()-cur.getHeight()/2, cur.getWidth()/2, cur.getHeight()/2, null);
		}
	}
	private Platform lastPlatform;
	//method to check whether the player hit any platforms
	public void hitPlatform(){
		for(int i=0;i<platforms.size();i++){
			//platform only effective if falling
			if(p.getVx()>0){
				if(p.collide(platforms.get(i))){
					p.setVy(SDV);
					lastPlatform=platforms.get(i);
				}
			}
			if(p.collide((platforms.get(i)))){
				p.setY(platforms.get(i).getY());
			}
		}
	}
	
	//if player is below screen, hit by enemy, the game is over
	public void checkGameOver(){
		
	}
	//method to spawn platforms
	public void generatePlatforms(){
		
	}
	//method to spawn enemies
	public void spawnEnemies(){
		
	}
	//enemy collision
	@Override
	public void keyPressed(KeyEvent e) {
		int pressed = e.getKeyCode();
		switch (pressed) {
		case 37: // left
			moveLeft = true;
			break;
		case 39: // right
			moveRight = true;
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int pressed = e.getKeyCode();
		switch (pressed) {
		case 37: // left
			moveLeft = false;
			break;
		case 39: // right
			moveRight = false;
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
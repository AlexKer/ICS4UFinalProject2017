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
	BufferedImage imgBuffer = new BufferedImage(600, 800, BufferedImage.TYPE_INT_ARGB);
	Graphics g = imgBuffer.getGraphics();
	private Player p;
	private ArrayList<Platform> platforms;
	private ArrayList<Enemy> enemies;
	private int gameState=2; //GameState 1=StartMenu, 2=InGame, 3=GameOver
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
			generateNewPlatform();
			System.out.println(platforms.get(i).getX()+" "+platforms.get(i).getY());
		}
		//ensure one low one
		platforms.add(new Platform(1,500,200));
	}
	int cnt=100;
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
			if(moveRight){ p.changeX(50); }
			else if(moveLeft){ p.changeX(-50); }
		}
		//---default movement of player--//
		hitPlatform();
		p.move();
		if(checkGameOver()){ gameState=3; }
		
		//move platform down if player is moving up
		if(cnt>0){
			cnt--;
		}else{
			if(p.isMovingUp()){
				for(int i=0;i<platforms.size();i++){
					platforms.get(i).move();
				}
			}
			cnt=100;
		}
		/*
		 * @param
		 * 
		 */
		//if platform moves off the bottom, generate new ones
//		for(int i=0;i<platforms.size();i++){
//			if(platforms.get(i).getY()>800){
//				generateNewPlatform();
//			}
//		}
		//call monster move method
		for(int i=0;i<enemies.size();i++){
			enemies.get(i).animate();
		}
		//move platforms horizontally
	}
	//precondition none:
	//post condition: generates a new ranodm platform and adds to the ArrayList of platforms
	public void generateNewPlatform(){
		platforms.add(new Platform(1,(int)(Math.random()*600),(int)(Math.random()*800)));
	}
	
	public void paint(Graphics g) {
		switch(gameState){
		case 1:
			//g.drawImage(StartScreen, 0, 0, null);
			break;
		case 2:
			//g.drawImage(BackGround, X, Y, null);
			
		}
		g.drawImage(p.getAnimation().getSprite(), p.getX(), p.getY(), null);
		for(int i=0;i<platforms.size();i++){
			Platform cur=platforms.get(i);
			g.drawImage(cur.getAnimation().getSprite(), cur.getX()-cur.getWidth()/2,
					cur.getY()-cur.getHeight()/2, cur.getWidth()/2, cur.getHeight()/2, null);
		}
	}
	private Platform lastPlatform;
	//method to check whether the player hit any platforms
	public void hitPlatform(){
		for(int i=0;i<platforms.size();i++){
			//platform only effective if falling
			if(p.getVy()>0){
				if(p.collide(platforms.get(i))){
					bounce();
					//p.setVy(SDV);
					lastPlatform=platforms.get(i);
				}
			}
			if(p.collide((platforms.get(i)))){
				p.setY(platforms.get(i).getY());
			}
		}
	}
	public void bounce(){
		int cnt=-10;
		while(cnt<0){
			p.setVy(cnt++);
		}
	}
	
	//if player is below screen, hit by enemy, the game is over
	public boolean checkGameOver(){
		if(p.getY()>800){ return true; }
		return false;
	}
	//explain new methods
	//method to spawn enemies
	boolean flag=true;
	public void spawnEnemies(){
		if(flag){
			//for now just general monster, need to load
			//Fly
			//enemies.add(new Enemy())
		}else{
			//Spike
			//enemies.
		}
		flag=!flag;
	}
	//enemy collision
	@Override
	public void keyPressed(KeyEvent e) {
		int pressed = e.getKeyCode();
		switch (pressed) {
		case 37: // left
			moveLeft = true;
			p.animate();
			break;
		case 39: // right
			moveRight = true;
			p.animate();
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		int pressed = e.getKeyCode();
		switch (pressed) {
		case 37: // left
			moveLeft = false;
			p.stopAnimate();
			break;
		case 39: // right
			moveRight = false;
			p.stopAnimate();
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
}
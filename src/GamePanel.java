import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

import Graphics.ImageRetrieve;
public class GamePanel extends JPanel implements Runnable, KeyListener{
	//TODO gravity, powerup, monster, platform, enemy
	private static final long serialVersionUID = 1L;
	private boolean moveRight, moveLeft;
	BufferedImage imgBuffer = new BufferedImage(600, 800, BufferedImage.TYPE_INT_ARGB);
	Graphics g = imgBuffer.getGraphics();
	private BufferedImage startMenuScreen, gameOverScreen;
	private Player p;
	private ArrayList<Platform> platforms;
	private ArrayList<Enemy> enemies;
	private int gameState=2; //GameState 1=StartMenu, 2=InGame, 3=GameOver
	private int score=0;
	public GamePanel() throws IOException, UnsupportedAudioFileException,
	LineUnavailableException {
		ImageRetrieve.loadXML();
		ImageRetrieve.loadSpriteSheet();
		p=new Player(0, 1);
		platforms=new ArrayList<Platform>();
		enemies=new ArrayList<Enemy>();
		for(int i=0;i<10;i++){ 
			generateNewPlatform();
		}
		URL fileURL;
		fileURL=getClass().getResource("Graphics/Hoppy_Bunny_Title.png");
		startMenuScreen=ImageIO.read(fileURL);
		fileURL=getClass().getResource("Graphics/Game_Over.png");
		gameOverScreen=ImageIO.read(fileURL);
		gameState=1;
	}
	private boolean now=false;
	int cnt=100;
	@Override
	public void run() {
		//enforce border
		if(p.getX()<0){
			p.setX(0);
		}else if(p.getX()>600-120){
			p.setX(600-120);
		}
		if(p.getY()<0){
			p.setY(0);
			p.toggleMovingUp();
		}
		//---horizontal movement with left and right arrow keys---//
		//---movement of player--//
		hitPlatform(); //BUG HERE, bounce up does not work
		p.move();
		System.out.println(p.getVy());
		if(moveRight ^ moveLeft){
			if(moveRight){ p.changeX(20); }
			else if(moveLeft){ p.changeX(-20); }
		}
		
		//move platform down if player is moving up
		if(p.isMovingUp()){
			for(int i=0;i<platforms.size();i++){
				platforms.get(i).move();
			}
		}
		//if platform moves off the bottom, generate new ones
		for(int i=0;i<platforms.size();i++){
			if(platforms.get(i).getY()>800){
				platforms.remove(i);
				generateNewPlatform();
			}
		}
		//call monster move method
		//fix monster classes
		for(int i=0;i<enemies.size();i++){
			spawnEnemies();
			enemies.get(i).animate();
		}
		if(checkGameOver()){ 
			gameState=3; 
		}
	}
	//precondition none:
	//post condition: generates a new ranodm platform and adds to the ArrayList of platforms
	public void generateNewPlatform(){
		platforms.add(new Platform((int)(Math.random()*600),(int)(Math.random()*800)));
	}
	
	public void paint(Graphics g) {
		switch(gameState){
		case 1:
			g.drawImage(startMenuScreen, 0, 0, null);
			break;
		case 2:
			System.out.println(p.getX()+" "+p.getY());
			g.drawImage(p.getAnimation().getSprite(), p.getX(), p.getY(), null);
			for(int i=0;i<platforms.size();i++){
				Platform cur=platforms.get(i);
				g.drawImage(cur.getAnimation().getSprite(), cur.getX()-cur.getWidth()/2,
						cur.getY()-cur.getHeight()/2, cur.getWidth()/2, cur.getHeight()/2, null);
			}
			break;
		case 3:
			g.drawImage(gameOverScreen, 0, 0, null);
			break;
		}
	}
	//method to check whether the player hit any platforms
	public void hitPlatform(){
		for(int i=0;i<platforms.size();i++){
			//platform only effective if falling
			if(p.getVy()>0){
				if(p.collide(platforms.get(i))){
					p.toggleMovingUp();
					bounce();
				}
			}
		}
	}
	public void bounce(){
		p.changeVy(-10);
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
			//Fly
			enemies.add(new FlyMonster((int)(Math.random()*600),(int)(Math.random()*800)));
		}else{
			//Spike
			enemies.add(new SpikeMonster((int)(Math.random()*600),(int)(Math.random()*800)));
		}
		flag=!flag;
	}
	//enemy collision
	@Override
	public void keyPressed(KeyEvent e) {
		int pressed = e.getKeyCode();
		switch (pressed) {
		case 80:
		case 82:
			gameState=2;
			break;
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
import java.awt.Color;
import java.awt.Font;
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
	//NO enemies, accessories like JetPack, BubbleShield; no time
	//updated UML diagram
	//https://www.lucidchart.com/invitations/accept/48643b77-6943-4d20-8867-bfddaf273249
	private static final long serialVersionUID = 1L;
	private boolean moveRight, moveLeft;
	BufferedImage imgBuffer = new BufferedImage(600, 800, BufferedImage.TYPE_INT_ARGB);
	Graphics g = imgBuffer.getGraphics();
	private BufferedImage startMenuScreen, inGameScreen, gameOverScreen;
	private Player p;
	private ArrayList<Platform> platforms;
	private ArrayList<Coin> coins;
	private int gameState; //GameState 1=StartMenu, 2=InGame, 3=GameOver
	private int score=0;
	private boolean playerStart=false;
	public GamePanel() throws IOException, UnsupportedAudioFileException,
	LineUnavailableException {
		ImageRetrieve.loadXML();
		ImageRetrieve.loadSpriteSheet();
		p=new Player(1, 600);
		platforms=new ArrayList<Platform>();
		coins=new ArrayList<Coin>();
		URL fileURL;
		fileURL=getClass().getResource("Graphics/Hoppy_Bunny_Title.png");
		startMenuScreen=ImageIO.read(fileURL);
		fileURL=getClass().getResource("Graphics/Game_Over.png");
		gameOverScreen=ImageIO.read(fileURL);
		fileURL=getClass().getResource("Graphics/background-with-clouds.png");
		inGameScreen=ImageIO.read(fileURL);
		gameState=1;
		for(int i=0;i<12;i++){
			platforms.add(new Platform((int)(Math.random()*600),(int)(Math.random()*800)));
		}
	}
	private int numTick=0;
	@Override
	public void run() {
		//enforce border
		if(p.getX()<50){
			p.setX(550);
		}else if(p.getX()>550){
			p.setX(50);
		}
		if(p.getY()<10){
			p.setY(10);
		}
		//---horizontal movement with left and right arrow keys---//
		//---movement of player--//
		hitPlatform(); 
		hitCoin();
		if(playerStart)
			p.move();
		if(moveRight ^ moveLeft){
			if(moveRight){ p.changeX(15); }
			else if(moveLeft){ p.changeX(-15); }
		}
		//only move platforms and coins if Player object is moving upwards
		if(p.isMovingUp()){
			for(int i=0;i<platforms.size();i++){
				platforms.get(i).move();
			}
			for(int i=0;i<coins.size();i++){
				coins.get(i).move();
			}
		}
		//if platform or coin moves off the bottom of the screen
		//remove it from the ArrayList
		for(int i=0;i<platforms.size();i++){
			if(platforms.get(i).getY()>800){
				platforms.remove(i);
			}
		}
		for(int i=0;i<coins.size();i++){
			if(coins.get(i).getY()>800){
				coins.remove(i);
			}
		}
		//if Player pressed space to start the game
		//generate a new Platform object every 15 ticks
		if(playerStart){
			if(numTick>15){
				generateNewPlatform();
				numTick=0;
			}else{
				numTick++;
			}
		}
		if(checkGameOver()){ 
			gameState=3; 
		}
	}
	//randomly generate a ...
	//x location within the width of the screen,
	//y location outside the screen near the top (negative value),
	//these are used then to instantiate a new Platform object and added to platforms ArrayList
	//generate a random
	public void generateNewPlatform(){
		int platformX = (int)(Math.random()*600);
		int platformY = -(int)(Math.random()*8);
		platforms.add(new Platform(platformX, platformY));
		int chance = 1+(int) (Math.random()*30);
		if(chance<=15){
			coins.add(new Coin(platformX + 115, platformY - 80, 1));
		}else if(chance>15 && chance<25){
			coins.add(new Coin(platformX + 115, platformY - 80, 2));
		}else{
			coins.add(new Coin(platformX + 115, platformY - 80, 3));
		}
		coins.get(coins.size()-1).animate();
	}
	public void paint(Graphics g) {
		switch(gameState){
		case 1:
			g.drawImage(startMenuScreen, 0, 0, null);
			break;
		case 2:
			g.drawImage(inGameScreen, 0, 0, 600, 800, null);
			g.drawImage(p.getAnimation().getSprite(), p.getX()-p.getWidth()/2, 
					p.getY()-p.getHeight()/2, p.getWidth(), p.getHeight(), null);
			for(int i=0;i<platforms.size();i++){
				Platform cur=platforms.get(i);
				g.drawImage(cur.getAnimation().getSprite(), cur.getX()-cur.getWidth()/2,
						cur.getY()-cur.getHeight()/2, cur.getWidth(), cur.getHeight(), null);
			}
			for(int i=0;i<coins.size();i++){
				Coin cur=coins.get(i);
				g.drawImage(cur.getAnimation().getSprite(), cur.getX()-cur.getWidth()/2,
						cur.getY()-cur.getHeight()/2, cur.getWidth(), cur.getHeight(), null);
			}
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 24));
			g.drawString("Score: " + score, 10, 30);
			break;
		case 3:
			g.drawImage(gameOverScreen, 0, 0, null);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			g.drawString("Final Score: " + score, 115, 400);
			break;
		}
	}
	//method to check whether the player hit any platforms
	public void hitPlatform(){
		for(int i=0;i<platforms.size();i++){
			//platform only effective if falling
			if(p.getVy()>0){
				if(p.collide(platforms.get(i))){
					p.toggleMovingUp(true);
					p.changeVy(-18);
				}
			}
		}
	}
	public void hitCoin(){
		for(int i=0;i<coins.size();i++){
			if(p.collide(coins.get(i))){
				switch(coins.get(i).getID()){
				case 1:
					score++;
					break;
				case 2:
					score+=2;
					break;
				case 3:
					score+=3;
					break;
				}
				coins.remove(i);
			}
		}
	}	
	//if player is below screen, hit by enemy, the game is over
	public boolean checkGameOver(){
		if(p.getY()>800){ return true; }
		return false;
	}
    public void resetGame() {
    	p=new Player(1, 600);
		platforms=new ArrayList<Platform>();
		coins=new ArrayList<Coin>();
		gameState=1;
		for(int i=0;i<12;i++){
			platforms.add(new Platform((int)(Math.random()*600),(int)(Math.random()*800)));
		}
		gameState=2; 
		score=0;
		playerStart=false;
    }
	@Override
	public void keyPressed(KeyEvent e) {
		int pressed = e.getKeyCode();
		switch (pressed) {
		case 32:
			if(!playerStart){
				playerStart=true;
				p.changeVy(-20);
			}
			break;
		case 80: //P
			gameState=2;
			break;
		case 82: //P
			resetGame();
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
	//stop
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
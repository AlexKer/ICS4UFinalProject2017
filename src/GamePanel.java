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

public class GamePanel extends JPanel implements Runnable, KeyListener {
	// Graphics References: graphics: http://kenney.nl/assets/jumper-pack
	// Updated UML diagram for updated class relationships:
	// https://www.lucidchart.com/invitations/accept/48643b77-6943-4d20-8867-bfddaf273249

	// Explanations for methods and classes:
	/*
	 * Methods changes 
	 * -getRandomPosition: Math.random was more convenient and does the same job 
	 * -gameLoop: renamed to "run"
	 * -move/update/setNextPosition: these three methods are integrated into one abstract method: move 
	 * -checkCollision: renamed to "collide" 
	 * -del: not created b/c GameObjects are directly removed from ArrayList w/ default ".remove(index)" method 
	 * -increaseScore: not created b/c score variable is directly incremented 
	 * -spawnEnemy/setPowerUp/getHp: not created b/c no Enemy and PowerUp classes 
	 * -For new methods added and justifications
	 * please see explanation of their uses directly above method signatures
	 * 
	 * Class changes 
	 * -StartMenu and GameOver: these classes made into gameStates in the GamePanel class 
	 * -Enemy and its subclasses FlyMonster, SpikeMonster: not created b/c no time 
	 * -PowerUp and its subclasses BubbleShield, JetPack" not created b/c no time
	 */
	private static final long serialVersionUID = 1L;
	private boolean moveRight, moveLeft;
	BufferedImage imgBuffer = new BufferedImage(600, 800, BufferedImage.TYPE_INT_ARGB);
	Graphics g = imgBuffer.getGraphics();
	private BufferedImage startMenuScreen, inGameScreen, gameOverScreen;
	private Player p;
	private ArrayList<Platform> platforms;
	private ArrayList<Coin> coins;
	private int gameState; // game state: 1=StartMenu, 2=InGame, 3=GameOver
	private int score = 0;
	private int numTicks = 0;
	private boolean playerStart = false; // checks whether player hit the space
											// bar to make first jump

	public GamePanel() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		// loads XML and sprite sheet
		ImageRetrieve.loadXML();
		ImageRetrieve.loadSpriteSheet();
		// instantiate Player object, ArrayList for platforms and coins
		p = new Player(1, 600);
		platforms = new ArrayList<Platform>();
		coins = new ArrayList<Coin>();
		// loads and store three backgrounds into BufferedImage variables
		URL fileURL;
		fileURL = getClass().getResource("Graphics/Hoppy_Bunny_Title.png");
		startMenuScreen = ImageIO.read(fileURL);
		fileURL = getClass().getResource("Graphics/Game_Over.png");
		gameOverScreen = ImageIO.read(fileURL);
		fileURL = getClass().getResource("Graphics/background-with-clouds.png");
		inGameScreen = ImageIO.read(fileURL);
		// set current gameState to 1, currently in the startMenuScreen
		gameState = 1;
		// loads 12 platforms for player to jump on at the start
		for (int i = 0; i < 12; i++) {
			platforms.add(new Platform((int) (Math.random() * 600), (int) (Math.random() * 800)));
		}
	}

	@Override
	public void run() {
		// enforces boundary rules of the game
		setBounds();
		// check whether player collided with platforms or coins
		hitPlatform();
		hitCoin();
		// only apply gravity and movement code after player has made first jump
		if (playerStart) {
			p.move();
		}
		// change no position if both arrows keys are pressed or both are not pressed
		// only if player is moving left OR right, change x position
		if (moveRight ^ moveLeft) {
			if (moveRight) {
				p.changeX(15);
			} else if (moveLeft) {
				p.changeX(-15);
			}
		}
		//move gameObjects and remove ones that moved off the screen
		moveGameObjects();
		removeGameObjects();
		// if player pressed space to start the game
		// generate a new Platform object every 15 ticks
		if (playerStart) {
			if (numTicks > 15) {
				generateNewPlatform();
				numTicks = 0;
			} else {
				numTicks++;
			}
		}
		if (checkGameOver()) {
			gameState = 3;
		}
	}

	// enforces boundary rules for player
	public void setBounds() {
		// if player moves off the left side of the screen,
		// appear on the right, and vice versa
		if (p.getX() < 50) {
			p.setX(550);
		} else if (p.getX() > 550) {
			p.setX(50);
		}
		// make sure player does not go beyond the top of the screen
		if (p.getY() < 10) {
			p.setY(10);
		}
	}

	// only move platforms and coins downwards if player is moving upwards
	public void moveGameObjects() {
		if (p.isMovingUp()) {
			for (int i = 0; i < platforms.size(); i++) {
				platforms.get(i).move();
			}
			for (int i = 0; i < coins.size(); i++) {
				coins.get(i).move();
			}
		}
	}

	// if platform or coin moves off the bottom of the screen
	// remove it from the ArrayList because it is no longer in use
	public void removeGameObjects() {
		for (int i = 0; i < platforms.size(); i++) {
			if (platforms.get(i).getY() > 800) {
				platforms.remove(i);
			}
		}
		for (int i = 0; i < coins.size(); i++) {
			if (coins.get(i).getY() > 800) {
				coins.remove(i);
			}
		}
	}

	// 1.randomly generate a ...
	// x location within the width of the screen,
	// y location outside the screen near the top (negative value),
	// these are then used to instantiate a new Platform object to add to platforms ArrayList
	// 2. a Coin object is generated randomly (bronze, silver or gold),
	// added to the coins ArrayList, and the animation for that coin is started
	public void generateNewPlatform() {
		int platformX = (int) (Math.random() * 600);
		int platformY = -(int) (Math.random() * 8);
		platforms.add(new Platform(platformX, platformY));
		int chance = 1 + (int) (Math.random() * 30);
		if (chance <= 15) {
			coins.add(new Coin(platformX + 115, platformY - 80, 1));
		} else if (chance > 15 && chance < 25) {
			coins.add(new Coin(platformX + 115, platformY - 80, 2));
		} else {
			coins.add(new Coin(platformX + 115, platformY - 80, 3));
		}
		coins.get(coins.size() - 1).animate();
	}

	// draws the graphics depending on the gameState
	public void paintComponent(Graphics g) {
		switch (gameState) {
		case 1:
			// draws startMenuScreen
			g.drawImage(startMenuScreen, 0, 0, null);
			break;
		case 2:
			// draws inGameScreen
			g.drawImage(inGameScreen, 0, 0, 600, 800, null);
			// draws player
			g.drawImage(p.getAnimation().getSprite(), p.getX() - p.getWidth() / 2, p.getY() - p.getHeight() / 2,
					p.getWidth(), p.getHeight(), null);
			// draws every platform and coin in the ArrayList
			for (int i = 0; i < platforms.size(); i++) {
				Platform cur = platforms.get(i);
				g.drawImage(cur.getAnimation().getSprite(), cur.getX() - cur.getWidth() / 2,
						cur.getY() - cur.getHeight() / 2, cur.getWidth(), cur.getHeight(), null);
			}
			for (int i = 0; i < coins.size(); i++) {
				Coin cur = coins.get(i);
				g.drawImage(cur.getAnimation().getSprite(), cur.getX() - cur.getWidth() / 2,
						cur.getY() - cur.getHeight() / 2, cur.getWidth(), cur.getHeight(), null);
			}
			// draws current score on top right corner
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 24));
			g.drawString("Score: " + score, 10, 30);
			break;
		case 3:
			// draws gameOverScreen background, and final score
			g.drawImage(gameOverScreen, 0, 0, null);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			g.drawString("Final Score: " + score, 115, 400);
			break;
		}
	}

	// check whether the player hits any platforms
	// if player is falling, check whether collision with platforms
	// if there is a collision, bounce player upwards
	public void hitPlatform() {
		for (int i = 0; i < platforms.size(); i++) {
			if (p.getVy() > 0) {
				if (p.collide(platforms.get(i))) {
					p.toggleMovingUp(true);
					p.changeVy(-18);
				}
			}
		}
	}

	// increases score depending which type of coin the player touches
	// remove the coin after it has been obtained
	public void hitCoin() {
		for (int i = 0; i < coins.size(); i++) {
			if (p.collide(coins.get(i))) {
				switch (coins.get(i).getID()) {
				case 1:
					score++;
					break;
				case 2:
					score += 2;
					break;
				case 3:
					score += 3;
					break;
				}
				coins.remove(i);
			}
		}
	}

	// if player is below screen, the game is over/lose
	public boolean checkGameOver() {
		if (p.getY() > 800) {
			return true;
		}
		return false;
	}

	// reset by resetting all variables to default values
	// and making new starting set of 12 platforms
	public void resetGame() {
		p = new Player(1, 600);
		platforms = new ArrayList<Platform>();
		coins = new ArrayList<Coin>();
		gameState = 1;
		for (int i = 0; i < 12; i++) {
			platforms.add(new Platform((int) (Math.random() * 600), (int) (Math.random() * 800)));
		}
		gameState = 2;
		score = 0;
		playerStart = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int pressed = e.getKeyCode();
		switch (pressed) {
		case 32:// space bar for player to make the first jump
				// if used once, set playerStart to true and enable gravity
				// disable use for the rest for the game
			if (!playerStart) {
				playerStart = true;
				p.changeVy(-20);
			}
			break;
		case 80: // P key to start the game
			gameState = 2;
			break;
		case 82: // R key to restart the game
			resetGame();
			gameState = 2;
			break;
		case 37: // left arrow to start animation and movement
			moveLeft = true;
			p.animate();
			break;
		case 39: // right arrow to start animation and movement
			moveRight = true;
			p.animate();
			break;
		}
	}

	// if left or right arrow key is released
	// stop the walking animation and movement for the player
	@Override
	public void keyReleased(KeyEvent e) {
		int pressed = e.getKeyCode();
		switch (pressed) {
		case 37: // left arrow key
			moveLeft = false;
			p.stopAnimate();
			break;
		case 39: // right arrow key
			moveRight = false;
			p.stopAnimate();
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
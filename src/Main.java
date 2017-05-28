import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
public class Main {
	//ref: last year's game
	public static void main(String[] args) throws IOException,
			InterruptedException, UnsupportedAudioFileException,
			LineUnavailableException {
		JFrame myFrame=new JFrame("Hoppy Bunny"); //instantiate JFrame
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Terminates program when window is closed
		myFrame.setSize(600, 800);
		myFrame.setResizable(false);
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);

		GamePanel myPanel = new GamePanel();
		myPanel.setSize(600, 800);
		myFrame.setContentPane(myPanel);
		myPanel.addKeyListener(myPanel);
		myPanel.requestFocus();
		
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
		    cursorImg, new Point(0, 0), "blank cursor");
		myFrame.getContentPane().setCursor(blankCursor);
		
		while(true){
			long before = System.currentTimeMillis();
			myPanel.run();
		
			//repaint the panel, will be constantly updated
			myPanel.repaint();
			long after = System.currentTimeMillis();
			Thread.sleep(Math.max(0, 16 - (after - before)));
			
		}

	}

}

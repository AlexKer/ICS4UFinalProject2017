import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class Main {
	// references: last year's game
	public static void main(String[] args)
			throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException {
		JFrame myFrame = new JFrame("Hoppy Bunny"); // instantiate JFrame
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// terminates program when the window is closed

		myFrame.setSize(600, 800); // set size for the window
		myFrame.setResizable(false); // fixed size
		myFrame.setLocationRelativeTo(null);
		myFrame.setVisible(true);

		GamePanel myPanel = new GamePanel(); // instantiate GamePanel object
		myPanel.setSize(600, 800); // set to the same size
		myFrame.setContentPane(myPanel); // puts GamePanel object into JFrame
		myPanel.addKeyListener(myPanel); // enables keyboard input
		myPanel.requestFocus();

		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
		myFrame.getContentPane().setCursor(blankCursor);
		// hides cursor
		while (true) {
			long before = System.currentTimeMillis();
			myPanel.run(); // run panel infinitely
			// repaint the panel, will be constantly updated
			myPanel.repaint();
			long after = System.currentTimeMillis();
			Thread.sleep(Math.max(0, 16 - (after - before)));
		}

	}

}
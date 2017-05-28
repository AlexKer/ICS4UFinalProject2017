package Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class ImageRetrieve{
	//partial references
	//https://gamedev.stackexchange.com/questions/59649/get-sprites-from-xml-and-spritesheet-in-java
	//https://gamedev.stackexchange.com/questions/53705/how-can-i-make-a-sprite-sheet-based-animation-system
	private final static int spriteNum=117;
	private static BufferedImage spriteSheet;
	private static Element[] spriteData=new Element[spriteNum];
	//parse XML document, store it within NodeList by SubTexture, 
	//fill spriteData array with SubTexture elements
	public static void loadXML(){
		try{
			File file=new File("src/Graphics/spritesheet_jumper.xml");
			DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder=dbFactory.newDocumentBuilder();
			Document doc=dbBuilder.parse(file);
			NodeList subTextureNodeList=doc.getElementsByTagName("SubTexture"); 
			for(int i=0;i<spriteNum;i++){ 
				spriteData[i]=(Element)subTextureNodeList.item(i);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//return spriteData array with SubTexture Elements
	public static Element[] getSpriteData(){
		return spriteData;
	}
	//Read and load sprite sheet png file, only used once at the beginning when game starts
	public static void loadSpriteSheet(){
		try {
			spriteSheet=ImageIO.read(new File("src/Graphics/spritesheet_jumper.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Gets SubTexture Element at index i in spriteData array
	//then use the attributes x, y, width, and height of that Element to find SubImage 
	//within spriteSheet png file to return a BufferedImage
	public static BufferedImage getSprite(int i){
		Element e=spriteData[i];
		return spriteSheet.getSubimage(Integer.parseInt(e.getAttribute("x")),Integer.parseInt(e.getAttribute("y")),
				Integer.parseInt(e.getAttribute("width")),Integer.parseInt(e.getAttribute("height")));
	}
	//loop through SpriteData array to find a SubTexture Element with the same name attribute 
	//as the string passed in, returns the index of that Element
	public static int getIndex(String n){
		for(int i=0;i<spriteNum;i++){
			if(n.equals(spriteData[i].getAttribute("name"))){ 
				return i;
			}
		}
		return 0;
	}
}
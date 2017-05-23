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
	private final static int spriteNum=117;
	private static BufferedImage spriteSheet;
	private static Element[] spriteData=new Element[spriteNum];
	public static void loadXML(){
		try{
			File file=new File("src/Graphics/spritesheet_jumper.xml");
			DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder=dbFactory.newDocumentBuilder();
			Document doc=dbBuilder.parse(file);
			NodeList subTextureNodeList=doc.getElementsByTagName("SubTexture"); //FIXED
			for(int i=0;i<spriteNum;i++){ 
				spriteData[i]=(Element)subTextureNodeList.item(i);
				//System.out.println(spriteData[i].getAttribute("name"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static Element[] getSpriteData(){
		return spriteData;
	}
	public static void loadSpriteSheet(){
		try {
			spriteSheet=ImageIO.read(new File("src/Graphics/spritesheet_jumper.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static BufferedImage getSprite(int i){
		Element e=spriteData[i];
		return spriteSheet.getSubimage(Integer.parseInt(e.getAttribute("x")),Integer.parseInt(e.getAttribute("y")),
				Integer.parseInt(e.getAttribute("width")),Integer.parseInt(e.getAttribute("height")));
	}
	public static int getIndex(String n){
		for(int i=0;i<spriteNum;i++){
			//System.out.println(n+" "+spriteData[i].getAttribute("name"));
			if(n.equals(spriteData[i].getAttribute("name"))){ //NULL POINTER
				return i;
			}
		}
		return 0;
	}
	//references
	//https://gamedev.stackexchange.com/questions/59649/get-sprites-from-xml-and-spritesheet-in-java
	//https://gamedev.stackexchange.com/questions/53705/how-can-i-make-a-sprite-sheet-based-animation-system
}
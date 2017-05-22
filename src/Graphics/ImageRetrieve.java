package Graphics;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
public class ImageRetrieve {
	public static void main(String args[]){
		try{
			File file=new File("src/Graphics/spritesheet_jumper.xml");
			DocumentBuilderFactory dbFactory=DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder=dbFactory.newDocumentBuilder();
			Document doc=dbBuilder.parse(file);
			//NodeList subTextureNodeList
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}


package client.crypto;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import client.util.HashAnImage;


public class PictureXML {


	 private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

	
	public static String getPictureXMLFiles(String putanja, String xml_file) throws NoSuchAlgorithmException {
		
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");
		BufferedImage img = null;
		
		try {
			
			File folder = new File(putanja);
			File[] slike = folder.listFiles();
			
			System.out.println(folder.listFiles().toString());
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			
			Element root = doc.createElement("picture");
			doc.appendChild(root);
			
			
			for(File slika : slike) { 
				System.out.println(slika);
			
				img = ImageIO.read(new File(slika.getPath()));
		
				String checksum = HashAnImage.getFileChecksum(md5Digest, slika);
				System.out.println(checksum);
	
				
				DataBuffer dataBuffer = img.getData().getDataBuffer();
				long sizeBytes = ((long) dataBuffer.getSize()) * 4l;
				long sizeKB = sizeBytes/1024l;
				Element korisnickoIme = doc.createElement("korisnickoIme");
				root.appendChild(korisnickoIme);
				Element podaciOSlici = doc.createElement("podaciOSlici");
				root.appendChild(podaciOSlici);
				
				  Attr attr = doc.createAttribute("naziv");
		          attr.setValue(slika.getPath());
		          Attr attr2 = doc.createAttribute("velicina");
		          attr2.setValue("" + sizeKB);
		          Attr attr3 = doc.createAttribute("hash");
		          attr3.setValue(checksum);
		          podaciOSlici.setAttributeNode(attr);
		          podaciOSlici.setAttributeNode(attr2);
		          podaciOSlici.setAttributeNode(attr3);	

		          LocalDateTime now = LocalDateTime.now();
		          System.out.println(dtf.format(now));
		          
		        Element datumKreiranja = doc.createElement("datumKreiranja");
		        root.appendChild(datumKreiranja);
		        Attr attr4 = doc.createAttribute("datum");
		        attr4.setValue(dtf.format(now));
		        datumKreiranja.setAttributeNode(attr4);
			}
			
			
				Transformer transformer = TransformerFactory.newInstance().newTransformer();
				Result output = new StreamResult(new FileOutputStream(new File(xml_file)));
				Source input = new DOMSource(doc);
	
				transformer.transform(input, output);
			
//              TransformerFactory transformerFactory = TransformerFactory.newInstance();
//              Transformer transformer = transformerFactory.newTransformer();
//              DOMSource domSource = new DOMSource(doc);
//              File outFile = new File("data/data.xml");
//              outFile.createNewFile();
//              StreamResult streamResult = new StreamResult(outFile);
//              transformer.transform(domSource, streamResult);

	          System.out.println("Napravljen je xml fajl.");
			
		}catch (IOException ex) {
			ex.printStackTrace();
		}catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		}catch (TransformerConfigurationException ex) {
			ex.printStackTrace();
		}catch (TransformerException ex) {
			ex.printStackTrace();
		}
		
		
		return null;
	}
	
	

}


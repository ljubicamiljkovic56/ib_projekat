package client.app;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;

import client.crypto.PictureEncrypt;
import client.crypto.PictureUpload;
import client.crypto.PictureXML;
import client.signed.CheckXMLSignEnveloped;
import client.signed.XMLSignEnveloped;
import client.util.ZipFolder;

public class Main {
	public static void main(String[] args) {
		String folder_sa_slikama = "./data/slike";
		String xml_slika = "./temp/data.xml";
		String enc_xml_slika = "./temp/enc_data.xml";
		String signed_xml_slika = "./data/signed_data.xml";
		String folder_za_zipovanje = "./data";
		String zip_fajl = "./data.zip";
		
		try {
			PictureXML.getPictureXMLFiles(folder_sa_slikama, xml_slika);
			
			PictureEncrypt.EncryptXML(xml_slika, enc_xml_slika);
			
			XMLSignEnveloped.Sign(enc_xml_slika, signed_xml_slika);
			
			CheckXMLSignEnveloped.testIt();
			
			ZipFolder.compressDirectory(folder_za_zipovanje, zip_fajl);
			
			PictureUpload.uploadFile(zip_fajl,"http://localhost:8080/upload");
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}



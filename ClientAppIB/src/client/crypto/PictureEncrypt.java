package client.crypto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.varia.ReloadingPropertyConfigurator;
import org.apache.xalan.xsltc.dom.LoadDocument;
import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.EncryptedKey;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.serialize.XHTMLSerializer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import client.signed.CheckXMLSignEnveloped;
import client.signed.XMLSignEnveloped;
import client.util.HashAnImage;
import client.util.ZipFolder;
import client.util.ZipUtil;



public class PictureEncrypt {
//	private static final String IN_FILE = "./data/data_signed.xml";
//	private static final String OUT_FILE = "./enc_data/data_signed_enc.xml";
	private static final String KEY_STORE_FILE = "./temp/slika.jks";

	static {
		Security.addProvider(new BouncyCastleProvider());
		org.apache.xml.security.Init.init();
	}

	public static void EncryptXML(String IN_FILE, String OUT_FILE) {
		Document doc = loadDocument(IN_FILE);
		
		System.out.println("Generating secret key ....");
		SecretKey secretKey = generateDataEncryptionKey();
		
		Certificate cert = readCertificate();
		
		System.out.println("Encrypting....");
		doc = encrypt(doc, secretKey, cert);
		
		saveDocument(doc, OUT_FILE);
		
		System.out.println("Encryption done");
	}
	
	private static Document loadDocument(String file) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(new File(file));

			return document;
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
			return null;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Certificate readCertificate() {
		try {
			KeyStore ks = KeyStore.getInstance("JKS", "SUN");
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(KEY_STORE_FILE));
			ks.load(in, "slika".toCharArray());

			if (ks.isKeyEntry("slika")) {
				Certificate cert = ks.getCertificate("slika");
				return cert;
			} else
				return null;

		} catch (KeyStoreException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (CertificateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	private static void saveDocument(Document doc, String fileName) {
		try {
			File outFile = new File(fileName);
			FileOutputStream f = new FileOutputStream(outFile);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(f);

			transformer.transform(source, result);

			f.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static SecretKey generateDataEncryptionKey() {

		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede"); 
																			
			return keyGenerator.generateKey();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}


	private static Document encrypt(Document doc, SecretKey key, Certificate certificate) {

		try {

			XMLCipher xmlCipher = XMLCipher.getInstance(XMLCipher.TRIPLEDES);
			
			
			xmlCipher.init(XMLCipher.ENCRYPT_MODE, key);

			
			XMLCipher keyCipher = XMLCipher.getInstance(XMLCipher.RSA_v1dot5);
			
			
			keyCipher.init(XMLCipher.WRAP_MODE, certificate.getPublicKey());
			
			EncryptedKey encryptedKey = keyCipher.encryptKey(doc, key);
			
		
			EncryptedData encryptedData = xmlCipher.getEncryptedData();
			
			
			KeyInfo keyInfo = new KeyInfo(doc);
			
			keyInfo.addKeyName("Kriptovani tajni kljuc");
			
			keyInfo.add(encryptedKey);
			
			encryptedData.setKeyInfo(keyInfo);

			NodeList pictures = doc.getElementsByTagName("picture");
			Element picture = (Element) pictures.item(0);

			xmlCipher.doFinal(doc, picture, true);

			return doc;

		} catch (XMLEncryptionException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	public static void main(String[] args) throws MalformedURLException, IOException, NoSuchAlgorithmException {
//		PictureEncrypt encrypt = new PictureEncrypt();
//		encrypt.testIt();
//
//		PictureXML.getPictureXMLFiles("./data/slike");
//		
//		XMLSignEnveloped xmlSign = new XMLSignEnveloped();
//		xmlSign.testIt();
//		
//		CheckXMLSignEnveloped checkXmlSign = new CheckXMLSignEnveloped();
//		checkXmlSign.testIt();
//		
//		ZipUtil.zipFile("./enc_data/data_signed_enc.xml");
//		
//		PictureUpload.uploadFile("./data_signed_enc.xml.zip", "http://localhost:8080/upload");
//
//		ZipFolder zf = new ZipFolder();
//		zf.main(args);
//		System.out.println("Zipovanje foldera..");
//	}

}
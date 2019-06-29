package client.signed;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

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

import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.signature.XMLSignatureException;
import org.apache.xml.security.transforms.TransformationException;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xml.security.utils.Constants;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class XMLSignEnveloped {

//		private static String file1 = "./data/data.xml";
//		private static String file2 = "./data/data_signed.xml";
		private static String kljuc = "./temp/slika.jks";
		
		static {
			  Security.addProvider(new BouncyCastleProvider());
		      org.apache.xml.security.Init.init();
		}
		
		public static void Sign(String file1, String file2) {
			Document doc = loadDocument(file1);
			
			PrivateKey pk = readPrivateKey();
			
			Certificate cert = readCertificate();
	
			System.out.println("Potpisivanje xml-a....");
			doc = signDocument(doc, pk, cert);
			
			saveDocument(doc, file2);
			System.out.println("Potpisivanje xml-a zavrseno");
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
		
		private static Certificate readCertificate() {
			try {

				KeyStore ks = KeyStore.getInstance("JKS", "SUN");

				BufferedInputStream in = new BufferedInputStream(new FileInputStream(kljuc));
				ks.load(in, "slika".toCharArray());
				
				if(ks.isKeyEntry("slika")) {
					Certificate cert = ks.getCertificate("slika");
					return cert;
					
				}
				else
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
		
		private static PrivateKey readPrivateKey() {
			try {
				KeyStore ks = KeyStore.getInstance("JKS", "SUN");
				
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(kljuc));
				ks.load(in, "slika".toCharArray());
				
				if(ks.isKeyEntry("slika")) {
					PrivateKey pk = (PrivateKey) ks.getKey("slika", "slika".toCharArray());
					return pk;
				}
				else
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
			} catch (UnrecoverableKeyException e) {
				e.printStackTrace();
				return null;
			} 
		}
		
		private static Document signDocument(Document doc, PrivateKey privateKey, Certificate cert) {
	      
	      try {
				Element rootEl = doc.getDocumentElement();
				XMLSignature sig = new XMLSignature(doc, null, XMLSignature.ALGO_ID_SIGNATURE_RSA_SHA1);
				
				Transforms transforms = new Transforms(doc);

				transforms.addTransform(Transforms.TRANSFORM_ENVELOPED_SIGNATURE);
				
				transforms.addTransform(Transforms.TRANSFORM_C14N_WITH_COMMENTS);
				    
				sig.addDocument("", transforms, Constants.ALGO_ID_DIGEST_SHA1);
				    
				sig.addKeyInfo(cert.getPublicKey());
				sig.addKeyInfo((X509Certificate) cert);
				    
				rootEl.appendChild(sig.getElement());
				
				sig.sign(privateKey);
				
				return doc;
				
			} catch (TransformationException e) {
				e.printStackTrace();
				return null;
			} catch (XMLSignatureException e) {
				e.printStackTrace();
				return null;
			} catch (DOMException e) {
				e.printStackTrace();
				return null;
			} catch (XMLSecurityException e) {
				e.printStackTrace();
				return null;
			}
		}
//		
//		public static void main(String[] args) {
//			XMLSignEnveloped sign = new XMLSignEnveloped();
//			sign.testIt();
//		}

}
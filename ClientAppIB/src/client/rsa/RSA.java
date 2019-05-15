package client.rsa;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import client.util.Base64;


public class RSA {
	
	private PublicKey  publicKey;
	private PrivateKey privateKey;
	private String data = "data";


	public RSA() {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	public void testIt() {
		System.out.println("Generisanje kljuceva:");
		generateKeys();
		System.out.println("Radi kriptovanje/dekriptovanje:");
		transfer();
		
	}
	
	private void generateKeys() {
        try {
			System.out.println("Kreira generator kljuceva...");
			
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
			keyGen.initialize(512, random);
			
			System.out.println("Kreira par kljuceva...");
			
			KeyPair    pair = keyGen.generateKeyPair();
			publicKey = pair.getPublic();
			privateKey = pair.getPrivate();

        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}
	
	private void transfer() {
			
		try {
			
			System.out.println("Kriptuje se " + data);	
			Cipher rsaCipherEnc = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
			rsaCipherEnc.init(Cipher.ENCRYPT_MODE, publicKey);
			
			byte[] ciphertext = rsaCipherEnc.doFinal(data.getBytes());
			System.out.println("Kriptovan text: " + Base64.encodeToString(ciphertext));
			
			Cipher rsaCipherDec = Cipher.getInstance("RSA/ECB/PKCS1Padding", "BC");
			rsaCipherDec.init(Cipher.DECRYPT_MODE, privateKey);
			
			byte[] receivedTxt = rsaCipherDec.doFinal(ciphertext);
			System.out.println("Primljeni text: " + new String(receivedTxt));

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		RSA test = new RSA();
		test.testIt();
	}
}

package client.symmetricaes;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import client.util.Base64;


public class Symmetric {
private static short BLOCK_SIZE = 16;
	
	private String data = "data";


	public Symmetric() {
	}
	
	public void testIt() {
		System.out.println("Generisanje kljuca:");
		SecretKey secretKey = generateKey();
		IvParameterSpec ivParameterSpec = generateIV();
		System.out.println("Radi kriptovanje/dekriptovanje:");
		transfer(secretKey, ivParameterSpec);
		
	}
	
	private SecretKey generateKey() {
        try {
			KeyGenerator   keyGen = KeyGenerator.getInstance("AES"); 
			SecretKey secretKey = keyGen.generateKey();
			return secretKey;
			
        } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        
        return null;
	}
	
	private IvParameterSpec generateIV() {
		byte []iv = new byte[BLOCK_SIZE];
		
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(iv);
		
		IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
		return ivParameterSpec;
	}
	
	private void transfer(SecretKey secretKey, IvParameterSpec ivParameterSpec) {
			
		try {
			System.out.println("Kriptuje se: " + data);	
			Cipher aesCipherEnc = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCipherEnc.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
			byte[] ciphertext = aesCipherEnc.doFinal(data.getBytes());
			System.out.println("Kriptovan text: " + Base64.encodeToString(ciphertext));
			
			Cipher aesCipherDec = Cipher.getInstance("AES/CBC/PKCS5Padding");
			aesCipherDec.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
			
			byte[] receivedTxt = aesCipherDec.doFinal(ciphertext);
			System.out.println("Primljeni text: " + new String(receivedTxt));

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		Symmetric test = new Symmetric();
		test.testIt();
	}
}

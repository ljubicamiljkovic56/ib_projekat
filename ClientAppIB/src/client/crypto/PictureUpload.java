package client.crypto;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PictureUpload {
	public static void uploadFile(String filename, String url) throws MalformedURLException, IOException{
	    File file = new File(filename);
	    String boundary = Long.toHexString(System.currentTimeMillis());

	    URL url1 = new URL("http://localhost:8080/upload");
	    HttpURLConnection connection = (HttpURLConnection)url1.openConnection();
	    connection.setDoOutput(true);
	    //connection.setRequestMethod("POST");
	    connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
	   
	    


	    
	    try {
	        OutputStream output = connection.getOutputStream();
	        DataOutputStream  writer = new DataOutputStream(output);

	        writer.writeBytes("--" + boundary);
	        writer.writeBytes("\r\n");
	        writer.writeBytes("Content-Disposition: form-data; name=\"description\"\r\n\r\n");
	        writer.writeBytes("Uploaded picture\r\n");

	        writer.writeBytes("--" + boundary + "\r\n");
	        writer.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"\r\n\r\n");
	        writer.write(Files.readAllBytes(Paths.get(filename)));
	        writer.writeBytes("\r\n");
	        writer.writeBytes("--" + boundary + "--\r\n");
	        writer.flush();
	        int statusCode = connection.getResponseCode();
	        System.out.println("status code" + statusCode);
	    } catch (Exception ex){
	        System.out.println("Ex: " + ex.getMessage());
	        System.out.println("Upload.....");
	        ex.printStackTrace();
	    }
	}
	

}

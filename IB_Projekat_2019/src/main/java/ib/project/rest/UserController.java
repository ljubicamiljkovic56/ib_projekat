package ib.project.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

//import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import ib.project.beans.SessionController;
import ib.project.misc.CopyFile;
import ib.project.misc.PictureDecrypt;
import ib.project.misc.UnzipFile;
//import ib.project.misc.FileDownload;
//import ib.project.misc.FileDownload;
import ib.project.model.User;
import ib.project.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(value="api/users")
public class UserController {
	
//	@Resource(name = "sessionScopedBean")
//	private SessionController sessionScopedBean;

	
	@Autowired
	public UserService userService;	
	
	
	@GetMapping(path="user/all")
	public ArrayList<User> getAll(){
		return userService.getAll();
	}
	
	@PostMapping(path="user/register")
	public ResponseEntity<User> dodajUsera( 
			@RequestParam String username, @RequestParam String password, @RequestParam String email){
	
		System.out.println(username + "-" + password);
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		
		try {
			CopyFile.copyFile("./certificate/user.cer", "./cert.data/" + user.getUsername() + ".cer");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			System.out.println("Zapocet proces");
			Process p = Runtime.getRuntime().exec("C:\\Program Files\\Java\\jdk1.8.0_181\\bin\\java -Dprotect=module -DignorePassphrase=true sun.security.tools.keytool.Main -genkeypair -validity 365 -alias " + user.getUsername() + " -keyalg RSA -sigalg SHA1withRSA -keystore " + user.getUsername() + ".jks -storetype JKS -storepass user12345 -keypass user12345 -dname \"CN=novi,OU=new,O=new,L=Novi Sad,ST=Serbia,C=rs\"");
			System.out.println("Proces zavrsen");
			System.out.println(p);
		} catch (IOException e) {
			e.printStackTrace();
	}
		
//		try {
//			System.out.println("Sertifikat");
//			Process p2 = Runtime.getRuntime().exec("java C:\\Program Files\\Java\\jdk1.8.0_181\\bin\\keytool -export -alias " + user.getUsername() + " -file "+ user.getUsername() + ".cer -keystore" + user.getUsername() + ".jks -storepass user12345 > C:\\Users\\Ljubica\\Desktop\\ib_projekat\\ib_projekat\\IB_Projekat_2019\\cert.data | type C:\\Users\\Ljubica\\Desktop\\ib_projekat\\ib_projekat\\IB_Projekat_2019\\cert.data");
//			System.out.println("Gotov sertifikat");
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
		
		user.setCertificate("./cert.data/" + user.getUsername() + ".cer");
		user.setEmail(email);
		user.setActive(false);
		user.setAuthority("Regular");
		userService.dodajUsera(user);
		

	
	return new ResponseEntity<User>(user,HttpStatus.CREATED);
}
	@PostMapping(path="user/accept")
	public ResponseEntity<User> odobriUsera(@RequestParam String username){
	
		System.out.println(username);
		System.out.println("Odobravanje");
	
		
		userService.odobriUsera(username);
		
		
		return new ResponseEntity<User>(HttpStatus.CREATED);
	}
	
	
	@PostMapping(path="user/login")
	public ResponseEntity<String> loginUsera(@RequestParam String username, @RequestParam String password) {
		
		System.out.println(username);
		System.out.println(password);
		
		String userAuth = userService.proveriUsera(username, password);
		
		
		
		return new ResponseEntity<String>(userAuth, HttpStatus.CREATED);
	}

	
//	@GetMapping(value="/get-cert", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//
//	public @ResponseBody byte[] getImageWithMediaType() throws IOException {
//		
//		
//		String username = sessionScopedBean.getUsername();
//		
//		System.out.println(username);
//		
//		String user = userService.getByUsername(username);
//		InputStream in = Files.newInputStream(Paths.get("C:\\Users\\Ljubica\\Desktop"
//				+ "\\ib_projekat\\ib_projekat\\IB_Projekat_2019\\cert.data\\" + user + ".cer"));
//		return IOUtils.toByteArray(in);
//	}
	
	//get certificate
	@GetMapping(value="/get-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)

	public @ResponseBody byte[] getCertificate(@RequestParam String username) throws IOException {
		
		System.out.println(username);
		
		String user = userService.getByUsername(username);
		InputStream in = Files.newInputStream(Paths.get("C:\\Users\\Ljubica\\Desktop"
				+ "\\ib_projekat\\ib_projekat\\IB_Projekat_2019\\src\\main\\resources\\static\\" + user + ".jks"
						+ ""));
		OutputStream out = Files.newOutputStream(Paths.get("C:\\Users\\Ljubica\\Desktop" 
				+ "\\ib_projekat\\ib_projekat\\IB_Projekat_2019\\download\\" + user + ".cer"));
		System.out.println(out);
		return IOUtils.toByteArray(in);
	}
	
	//get jks file
	@GetMapping(value="/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)

	public @ResponseBody byte[] getJKS(@RequestParam String username) throws IOException {
		
		System.out.println(username);
		
		String user = userService.getByUsername(username);
		InputStream in = Files.newInputStream(Paths.get("C:\\Users\\Ljubica\\Desktop"
				+ "\\ib_projekat\\ib_projekat\\IB_Projekat_2019\\src\\main\\resources\\static\\" + user + ".jks"
						+ ""));
		OutputStream out = Files.newOutputStream(Paths.get("C:\\Users\\Ljubica\\Desktop" 
				+ "\\ib_projekat\\ib_projekat\\IB_Projekat_2019\\download\\" + user + ".jks"));
		System.out.println(out);
		return IOUtils.toByteArray(in);
	}
	
	@GetMapping(value="/prikaz_slika") 
	public @ResponseBody byte[] primer() {
		
		String zipFilePath = "/Users/Ljubica/Desktop/ib_projekat/ib_projekat/IB_Projekat_2019/uploads/data.zip";
        
        String destDir = "/Users/Ljubica/Desktop/ib_projekat/ib_projekat/IB_Projekat_2019/uploads/unzipped";
        
		
		System.out.println("Unzip...");
		UnzipFile.unzip(zipFilePath, destDir);
		System.out.println("Unzipped");
		
		System.out.println("Decrypting");
		PictureDecrypt decrypt = new PictureDecrypt();
		decrypt.testIt();
		System.out.println("Decrypted");
		return null;
		}
	
	@GetMapping(value="pregled_slika")
	public @ResponseBody byte[] primer3() {
		
		System.out.println("Prikazi slike na stranici");
		
		return null;
	}
	
	
	
}

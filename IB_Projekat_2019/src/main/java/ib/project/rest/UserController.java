package ib.project.rest;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ib.project.misc.CopyFile;
import ib.project.model.User;
import ib.project.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(value="api/users")
public class UserController {
	
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
}

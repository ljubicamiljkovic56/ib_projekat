package ib.project.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ib.project.model.Authority;
import ib.project.model.User;
import ib.project.service.UserService;

@RestController
@RequestMapping(value="api/users")
public class UserController {
	
	@Autowired
	public UserService userService;

	
	@PostMapping(consumes="application/json")
	public ResponseEntity<User> dodajUsera(@RequestBody User user){
	
		userService.dodajUsera(user);
		
	
	return new ResponseEntity<User>(user, HttpStatus.CREATED);
}
}
